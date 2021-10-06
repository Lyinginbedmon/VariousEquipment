package com.lying.variousequipment.block;

import com.lying.variousequipment.tileentity.TileEntityAlembic;
import com.lying.variousequipment.tileentity.TileEntityAlembic.EnumRegion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class BlockAlembic extends ContainerBlock
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	public static final VoxelShape SHAPE_NORTH	= VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 10.0D, 13D, 12.0D), Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 16.0D, 9.0D, 9.0D));
	public static final VoxelShape SHAPE_EAST	= VoxelShapes.or(Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 14.0D, 13D, 10.0D), Block.makeCuboidShape(7.0D, 0.0D, 0.0D, 11.0D, 9.0D, 16.0D));
	public static final VoxelShape SHAPE_SOUTH	= VoxelShapes.or(Block.makeCuboidShape(6.0D, 0.0D, 4.0D, 16.0D, 13D, 14.0D), Block.makeCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 9.0D, 11.0D));
	public static final VoxelShape SHAPE_WEST	= VoxelShapes.or(Block.makeCuboidShape(2.0D, 0.0D, 6.0D, 12.0D, 13D, 16.0D), Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 9.0D, 9.0D, 16.0D));
	
	public BlockAlembic(Properties properties)
	{
		super(properties.sound(SoundType.GLASS));
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}
	
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}
	
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new TileEntityAlembic();
	}
	
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch(state.get(FACING))
		{
			case EAST:
				return SHAPE_EAST;
			case SOUTH:
				return SHAPE_SOUTH;
			case WEST:
				return SHAPE_WEST;
			case NORTH:
			default:
				return SHAPE_NORTH;
		}
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(worldIn.isRemote)
			return ActionResultType.SUCCESS;
		
		TileEntityAlembic tile = (TileEntityAlembic)worldIn.getTileEntity(pos);
		ItemStack heldItem = player.getHeldItem(handIn);
		
		LazyOptional<IFluidHandlerItem> fluidHandlerOptional = heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
		if(fluidHandlerOptional.isPresent())
		{
			IFluidHandlerItem fluidHandler = fluidHandlerOptional.resolve().get();
			if(tile.interactWithItemFluidHandler(fluidHandler))
			{
				heldItem.shrink(1);
				player.addItemStackToInventory(fluidHandler.getContainer());
			}
		}
		else if(heldItem.getItem() == Items.GLASS_BOTTLE && tile.getFluidAmount() == TileEntityAlembic.CAPACITY)
		{
			heldItem.shrink(1);
			player.addItemStackToInventory(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER));
			tile.getTank().drain(new FluidStack(Fluids.WATER, TileEntityAlembic.CAPACITY), FluidAction.EXECUTE);
		}
		else if(heldItem.getItem() == Items.POTION && PotionUtils.getPotionFromItem(heldItem) == Potions.WATER && tile.getFluidAmount() == 0)
		{
			heldItem.shrink(1);
			player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			tile.getTank().fill(new FluidStack(Fluids.WATER, TileEntityAlembic.CAPACITY), FluidAction.EXECUTE);
		}
		else
		{
			EnumRegion target = tile.getSlotByHit(hit);
			if(heldItem.isEmpty())
			{
				for(int slot : target.slots())
				{
					ItemStack stack = tile.getStackInSlot(slot);
					if(!stack.isEmpty())
					{
						player.addItemStackToInventory(stack);
						tile.setInventorySlotContents(slot, ItemStack.EMPTY);
						break;
					}
				}
			}
			else
				for(int slot : target.slots())
					if(tile.getStackInSlot(slot).isEmpty() && tile.isItemValidForSlot(slot, heldItem))
					{
						tile.setInventorySlotContents(slot, heldItem.split(1));
						break;
					}
		}
		return ActionResultType.CONSUME;
	}
}
