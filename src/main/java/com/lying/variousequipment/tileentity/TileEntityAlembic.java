package com.lying.variousequipment.tileentity;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.lying.variousequipment.block.BlockAlembic;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.init.VETileEntities;
import com.lying.variousequipment.item.crafting.IAlembicRecipe;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileEntityAlembic extends TileEntity implements ITickableTileEntity, ISidedInventory
{
	public static final int CAPACITY = (int)(1000D / 3D);
	private Inventory inventory = new Inventory(getSizeInventory())
	{
		@Override
		public int getInventoryStackLimit(){ return 1; }
	};
	
	/*
	 * 0 = Fuel, secondary output slot if contained item has no burn time
	 * 1 = Input slot
	 * 2 = Input slot
	 * 3 = Input slot
	 * 4 = Output slot, secondary input slot (for potions)
	 */
	
	private int ticksLoaded = 0;
	
	private FluidTank waterTank;
	private final LazyOptional<IFluidHandler> handler = LazyOptional.of(() -> waterTank);
	private int burnTime = 0;
	
	private int cookingTime = 0;
	
	public TileEntityAlembic()
	{
		super(VETileEntities.ALEMBIC);
		this.waterTank = new FluidTank(getTankCapacity(0))
				{
					@Override
					public boolean isFluidValid(int tank, @Nonnull FluidStack stack)
					{
						return stack.getFluid() == Fluids.WATER;
					}
					
		            @Override
		            protected void onContentsChanged()
		            {
		                markDirty();
		            }
				};
	}
	
	public int getSizeInventory(){ return 5; }
	
	public boolean isEmpty(){ return inventory.isEmpty(); }
	
	public void read(BlockState state, CompoundNBT nbt)
	{
		super.read(state, nbt);
		this.waterTank.readFromNBT(nbt.getCompound("Water"));
		setBurnTime(nbt.getInt("BurnTime"));
		this.inventory.clear();
		ListNBT contents = nbt.getList("Items", 10);
		for(int i=0; i < contents.size(); i++)
		{
			CompoundNBT stackData = contents.getCompound(i);
			int slot = stackData.getByte("Slot") & 255;
			setInventorySlotContents(slot, ItemStack.read(stackData));
		}
	}
	
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		compound.put("Water", this.waterTank.writeToNBT(new CompoundNBT()));
		compound.putInt("BurnTime", this.burnTime);
		ListNBT contents = new ListNBT();
		for(int i=0; i<getSizeInventory(); i++)
		{
			ItemStack stack = getStackInSlot(i);
			if(stack.isEmpty()) continue;
			CompoundNBT stackData = stack.write(new CompoundNBT());
			stackData.putByte("Slot", (byte)i);
			contents.add(stackData);
		}
		
		if(contents.size() > 0)
			compound.put("Items", contents);
		return compound;
	}
	
	public ItemStack getStackInSlot(int index){ return inventory.getStackInSlot(index); }
	
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack inSlot = getStackInSlot(index);
		ItemStack split = inSlot.split(1);
		setInventorySlotContents(index, inSlot);
		return split;
	}
	
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack inSlot = getStackInSlot(index);
		setInventorySlotContents(index, ItemStack.EMPTY);
		markDirty();
		return inSlot;
	}
	
	public void setInventorySlotContents(int index, ItemStack stack){ inventory.setInventorySlotContents(index, stack); markDirty(); }
	
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		EnumRegion region = EnumRegion.regionFromSlot(slot);
		return region != null && region.isValid.apply(stack);
//		switch(slot)
//		{
//			case 0:
//				return ((IForgeItemStack)stack).getBurnTime() > 0;
//			case 1:
//			case 2:
//			case 3:
//				return BrewingRecipeRegistry.isValidIngredient(stack);
//			case 4:	// Cannot put items in the product slot
//			default:
//				return false;
//		}
	}
	
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return true;
	}
	
	public boolean hasWater(){ return this.waterTank.getFluidAmount() >= CAPACITY; }
	
	public FluidStack getFluid(){ return this.waterTank.getFluid(); }
	public FluidTank getTank(){ return this.waterTank; }
	
	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return handler.cast();
		return super.getCapability(capability, facing);
	}
	
	public int burnTime(){ return this.burnTime; }
	public int setBurnTime(int par1Int){ this.burnTime = Math.abs(par1Int); markDirty(); return this.burnTime; }
	
	public boolean isBubbling(){ return hasWater() && this.burnTime > 0; }
	
	public EnumRegion getSlotByHit(BlockRayTraceResult hit)
	{
		BlockState blockState = getBlockState();
		Direction face = hit.getFace();
		if(face.getHorizontalIndex() < 0) return null;
		
		Direction rear = blockState.get(BlockAlembic.FACING);
		Direction right = rear.rotateY();
		Direction left = right.getOpposite();
		
		Vector3d hitVec = hit.getHitVec();
		BlockPos pos = hit.getPos();
		double hitX = hitVec.x - pos.getX();
		double hitY = hitVec.y - pos.getY();
		double hitZ = hitVec.z - pos.getZ();
		
		if(face == left)
		{
			if(hitY > 0.5F) return EnumRegion.WATER;
			else return EnumRegion.FUEL;
		}
		else if(face == right)
			return hitY < 0.55F ? EnumRegion.OUTPUT : EnumRegion.WATER;
		else
		{
			if(hitY > 0.5F) return EnumRegion.WATER;
			
			switch(rear)
			{
				case SOUTH:
					hitX = 1F - hitX;
					break;
				case EAST:
					hitX = hitZ;
					break;
				case WEST:
					hitX = 1F - hitZ;
					break;
				default:
					break;
			}
			
			return hitX > 0.65F ? EnumRegion.OUTPUT : EnumRegion.FUEL;
		}
	}
	
	public void clear() { inventory.clear(); }
	
	public int[] getSlotsForFace(Direction side)
	{
		if(side.getAxis() == Axis.Y)
		{
			if(side == Direction.UP)
				return EnumRegion.WATER.slots;
			else
				return new int[]{4, 0, 1, 2, 3};
		}
		else
		{
			Direction orientation = getBlockState().get(BlockAlembic.FACING);
			for(int i=0; i<orientation.getHorizontalIndex(); ++i)
				side = side.rotateYCCW();
			
			switch(side)
			{
				case NORTH:
				case SOUTH:
					return EnumRegion.FUEL.slots;
				case WEST:
					return EnumRegion.OUTPUT.slots;
				case EAST:
				default:
					return EnumRegion.WATER.slots;
			}
		}
	}
	
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction)
	{
		EnumRegion region = EnumRegion.regionFromSlot(index);
		return region != null && region.isValid.apply(itemStackIn);
	}
	
	public boolean canExtractItem(int index, ItemStack stack, Direction direction)
	{
		return true;
	}
	
	public void tick()
	{
		this.ticksLoaded++;
		
		if(burnTime == 0)
		{
			if(hasValidRecipe())
			{
				ItemStack fuel = getStackInSlot(0);
				if(!fuel.isEmpty())
				{
					burnTime = net.minecraftforge.common.ForgeHooks.getBurnTime(fuel);
					fuel.shrink(1);
					setInventorySlotContents(0, fuel);
				}
			}
		}
		else if(setBurnTime(burnTime - 1) > 0)
		{
			// Do flame particles
			
			if(hasWater())
			{
				// Do bubble particles
			}
		}
		
		if(hasWater() && burnTime > 0 && hasValidRecipe())
		{
			cookingTime++;
			
			if(hasBrewingRecipe())
			{
				if(cookingTime >= 400)
				{
					this.waterTank.drain(CAPACITY, FluidAction.EXECUTE);
					setInventorySlotContents(4, BrewingRecipeRegistry.getOutput(getStackInSlot(4), getBrewingIngredient()));
					shrinkWaterSlots();
				}
			}
			else if(cookingTime >= 200)
			{
				this.waterTank.drain(CAPACITY, FluidAction.EXECUTE);
				IAlembicRecipe recipe = getCurrentRecipe();
				if(recipe != null)
				{
					setInventorySlotContents(4, recipe.getRecipeOutput());
					shrinkWaterSlots();
				}
			}
		}
		else
			cookingTime = 0;
	}
	
	private void shrinkWaterSlots()
	{
		for(int slot : EnumRegion.WATER.slots)
		{
			ItemStack stack = getStackInSlot(slot);
			if(!stack.isEmpty())
			{
				stack.shrink(1);
				setInventorySlotContents(slot, stack);
				break;
			}
		}
	}
	
	public boolean hasValidRecipe()
	{
		return getCurrentRecipe() != null || hasBrewingRecipe();
	}
	
	public IAlembicRecipe getCurrentRecipe()
	{
		NonNullList<ItemStack> boiling = NonNullList.withSize(EnumRegion.WATER.slots.length, ItemStack.EMPTY);
		for(int i=0; i<EnumRegion.WATER.slots.length; ++i)
			boiling.set(i, getStackInSlot(EnumRegion.WATER.slots[i]));
		ItemStack output = getStackInSlot(EnumRegion.OUTPUT.slots[0]);
		
		List<IAlembicRecipe> matchingRecipes = Lists.newArrayList();
		for(IAlembicRecipe recipe : alembicRecipes(world))
			if(recipe.matches(boiling, output))
				matchingRecipes.add(recipe);
		return matchingRecipes.isEmpty() ? null : matchingRecipes.get(0);
	}
	
	private static List<IAlembicRecipe> alembicRecipes(World world)
	{
		return VERecipeTypes.getRecipes(world, VERecipeTypes.ALEMBIC_TYPE).values().stream()
				.filter(r -> r instanceof IAlembicRecipe)
				.map(r -> (IAlembicRecipe) r)
				.collect(Collectors.toList());
	}
	
	public boolean hasBrewingRecipe()
	{
		if(isEmpty()) return false;
		
		ItemStack brewItem = getBrewingIngredient();
		ItemStack outputStack = getStackInSlot(4);
		return !brewItem.isEmpty() && !outputStack.isEmpty() && BrewingRecipeRegistry.hasOutput(outputStack, brewItem);
	}
	
	/**
	 * Returns the first and only item in the water region of inventory
	 */
	private ItemStack getBrewingIngredient()
	{
		ItemStack brewItem = ItemStack.EMPTY;
		for(int slot : EnumRegion.WATER.slots)
		{
			ItemStack item = getStackInSlot(slot);
			if(!item.isEmpty())
			{
				if(brewItem.isEmpty())
					brewItem = item;
				else
				{
					brewItem = ItemStack.EMPTY;
					break;
				}
			}
		}
		return brewItem;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int ticksLoaded(){ return this.ticksLoaded; }
	
	public static enum EnumRegion
	{
		FUEL(new Predicate<ItemStack>()
		{
			public boolean apply(ItemStack input)
			{
				return net.minecraftforge.common.ForgeHooks.getBurnTime(input) > 0;
			}
		}, 0),
		WATER(Predicates.alwaysTrue(), 1,2,3),
		OUTPUT(Predicates.alwaysTrue(), 4);
		
		private final int[] slots;
		private final Predicate<ItemStack> isValid;
		
		private EnumRegion(Predicate<ItemStack> predicateIn, int... slotsIn)
		{
			this.isValid = predicateIn;
			this.slots = slotsIn;
		}
		
		public int[] slots(){ return this.slots; }
		
		public static EnumRegion regionFromSlot(int slotIn)
		{
			for(EnumRegion region : values())
				for(int slot : region.slots)
					if(slot == slotIn)
						return region;
			return null;
		}
	}
	
	public void markDirty()
	{
		super.markDirty();
		if(getWorld() instanceof ServerWorld)
		{
			getWorld().updateComparatorOutputLevel(getPos(), getBlockState().getBlock());
			SUpdateTileEntityPacket packet = getUpdatePacket();
			if(packet != null)
			{
				BlockPos pos = getPos();
				((ServerChunkProvider)getWorld().getChunkProvider()).chunkManager
						.getTrackingPlayers(new ChunkPos(pos), false)
						.forEach(e -> e.connection.sendPacket(packet));
			}
		}
	}
	
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(this.pos, -999, this.getUpdateTag());
	}
	
	public CompoundNBT getUpdateTag()
	{
		return this.write(new CompoundNBT());
	}
	
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
	{
		super.onDataPacket(net, packet);
		this.read(getBlockState(), packet.getNbtCompound());
	}
	
	public int getTankCapacity(int tank) { return CAPACITY; }
	
	public int getFluidAmount(){ return this.waterTank.getFluidAmount(); }
	
    public boolean interactWithItemFluidHandler(IFluidHandlerItem fluidHandler)
    {
        if(fluidHandler.getTanks() == 0)
            return false;
        
        FluidStack tankFluid = fluidHandler.getFluidInTank(0);
    	FluidStack water = this.waterTank.getFluidInTank(0);
        if(tankFluid.isEmpty())
        {
            if(!water.isEmpty() && fluidHandler.isFluidValid(0, water))
            {
                int amount = fluidHandler.fill(water.copy(), FluidAction.EXECUTE);
                if(amount > 0)
                {
                	water.shrink(amount);
                    this.waterTank.setFluid(water);
                    this.markDirty();
                    return true;
                }
            }
        }
        else if(water.isEmpty() || water.isFluidEqual(tankFluid))
        {
            tankFluid = tankFluid.copy();
            tankFluid.setAmount(this.getTankCapacity(0) - water.getAmount());
            FluidStack amount = fluidHandler.drain(tankFluid, FluidAction.SIMULATE);
            if(!amount.isEmpty() && (water.isEmpty() || water.isFluidEqual(amount)))
            {
                amount = fluidHandler.drain(tankFluid, FluidAction.EXECUTE);
                amount.grow(water.getAmount());
                water = amount;
                this.waterTank.setFluid(water);
                this.markDirty();
                return true;
            }
        }
        return false;
    }
}
