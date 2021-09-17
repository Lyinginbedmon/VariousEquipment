package com.lying.variousequipment.tileentity;

import javax.annotation.Nullable;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CauldronBlock;
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
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public abstract class TileEntitySpinny extends TileEntity implements ITickableTileEntity, ISidedInventory
{
	private Inventory inventory = new Inventory(getSizeInventory())
			{
				@Override
				public int getInventoryStackLimit(){ return 1; }
			};
	private static final int[] SLOTS = {0, 1, 2, 3};
	protected static final int PROCESS_TIME = Reference.Values.TICKS_PER_SECOND * 3;
	
	protected boolean working = false;
	protected int spinTime = 0;
	
	public TileEntitySpinny(TileEntityType<?> tileEntityType)
	{
		super(tileEntityType);
		this.inventory.addListener(i -> markDirty());
	}
	
	public void tick()
	{
		if(isWorking())
		{
			// Stop working when finished or unable
			if(!hasWork() || !hasSteam())
			{
				setWorking(false);
				setSpinTime(getSpinTime() % Reference.Values.TICKS_PER_SECOND);
			}
			else
			{
				// Apply mixing recipe to contents at regular interval
				if(setSpinTime(getSpinTime() + 1) % PROCESS_TIME == 0)
				{
					BlockPos cauldronPos = getPos().down();
					BlockState cauldronState = getWorld().getBlockState(cauldronPos);
					if(cauldronState.getBlock() != Blocks.CAULDRON)
						setWorking(false);
					else
					{
						for(int i=0; i<getSizeInventory(); i++)
							if(!getStackInSlot(i).isEmpty())
								processItem(getStackInSlot(i), i);
						
						getWorld().setBlockState(cauldronPos, cauldronState.with(CauldronBlock.LEVEL, Math.max(0, cauldronState.get(CauldronBlock.LEVEL).intValue() - 1)));
						if(!getWorld().isRemote)
							this.world.playEvent(1035, getPos(), 0);
						
						
					}
				}
			}
		}
		else
		{
			// Reverse mechanism to initial orientation
			if(hasSpinTime())
				setSpinTime((getSpinTime() + 1) % Reference.Values.TICKS_PER_SECOND);
			
			// Start working when possible
			if(hasWork() && hasSteam() && getWorld().isBlockPowered(getPos()))
				setWorking(true);
		}
	}
	
	public int getSpinTime(){ return this.spinTime; }
	
	public boolean hasSpinTime(){ return this.spinTime > 0; }
	
	public int setSpinTime(int par1Int)
	{
		this.spinTime = Math.abs(par1Int);
		markDirty();
		return this.spinTime;
	}
	
	public int[] getSlotsForFace(Direction side)
	{
		return SLOTS;
	}
	
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction)
	{
		return !isWorking() && direction != Direction.DOWN && canProcess(itemStackIn) && (getStackInSlot(index).isEmpty() || getStackInSlot(index).getCount() < this.inventory.getInventoryStackLimit());
	}
	
	public boolean canExtractItem(int index, ItemStack stack, Direction direction)
	{
		return !isWorking() && direction == Direction.DOWN;
	}
	
	public int getSizeInventory(){ return 4; }
	
	public boolean isEmpty()
	{
		return this.inventory.isEmpty();
	}
	
	public void read(BlockState state, CompoundNBT nbt)
	{
		super.read(state, nbt);
		setWorking(nbt.getBoolean("Working"));
		setSpinTime(nbt.getInt("SpinTime"));
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
		compound.putBoolean("Working", isWorking());
		compound.putInt("SpinTime", getSpinTime());
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
	
	/** Returns true if at least one inventory slot contains an item that needs mixing. */
	public boolean hasWork()
	{
		for(int i=0; i<this.getSizeInventory(); i++)
			if(canProcess(getStackInSlot(i)))
				return true;
		return false;
	}
	
	/** Returns true if the given stack can be mixed to produce an output product */
	public boolean canProcess(ItemStack stack)
	{
		return !stack.isEmpty();
	}
	
	public void processItem(ItemStack stack, int slot)
	{
		
	}
	
	public boolean isWorking(){ return this.working; }
	
	public void setWorking(boolean par1Bool)
	{
		this.working = par1Bool;
		markDirty();
	}
	
	public int getInventoryStackLimit(){ return 1; }
	
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory.getStackInSlot(index); 
	}
	
	public ItemStack decrStackSize(int index, int amount)
	{
		return index >= 0 && index < getSizeInventory() && !getStackInSlot(index).isEmpty() && amount > 0 ? getStackInSlot(index).split(amount) : ItemStack.EMPTY;
	}
	
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = this.inventory.getStackInSlot(index);
		this.inventory.setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}
	
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if(index >= 0 && index < getSizeInventory())
			this.inventory.setInventorySlotContents(index, stack);
	}
	
	public void clear(){ this.inventory.clear(); }
	
	/** Returns true if the device has access to a cauldron with water above a suitable heat source */
	public boolean hasSteam()
	{
		BlockState cauldron = getWorld().getBlockState(getPos().down());
		if(cauldron.getBlock() == Blocks.CAULDRON && cauldron.get(CauldronBlock.LEVEL).intValue() > 0)
		{
			BlockState heat = getWorld().getBlockState(getPos().down(2));
			if(heat.getBlock() == Blocks.FIRE || heat.getBlock() == Blocks.SOUL_FIRE)
				return true;
			else if(heat.getBlock() == Blocks.CAMPFIRE || heat.getBlock() == Blocks.SOUL_CAMPFIRE)
				return heat.get(CampfireBlock.LIT).booleanValue();
			else
				return heat.getBlock() == Blocks.MAGMA_BLOCK || heat.getFluidState().getFluid() == Fluids.LAVA || heat.getFluidState().getFluid() == Fluids.FLOWING_LAVA;
		}
		return false;
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
}
