package com.lying.variousequipment.block;

import com.lying.variousequipment.tileentity.TileEntitySpinny;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class BlockSpinny extends ContainerBlock
{
	protected static final VoxelShape SHAPE = VoxelShapes.or(Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13D, 14.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D));
	
	public BlockSpinny(AbstractBlock.Properties propertiesIn)
	{
		super(propertiesIn.hardnessAndResistance(2F, 5F).notSolid());
	}
	
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}
	
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).getBlock() == Blocks.CAULDRON;
	}
	
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if(!state.isIn(newState.getBlock()))
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof TileEntitySpinny)
			{
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntitySpinny)tile);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}
	}
	
	public boolean hasComparatorInputOverride(BlockState state)
	{
		return true;
	}
	
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos)
	{
		TileEntitySpinny tile = (TileEntitySpinny)worldIn.getTileEntity(pos);
		if(tile.isWorking())
			return 15;
		else
		{
			int signal = 0;
			for(int i=0; i<tile.getSizeInventory(); i++)
				if(tile.canProcess(tile.getStackInSlot(i)))
					signal++;
			
			return signal * 2;
		}
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(worldIn.isRemote)
			return ActionResultType.SUCCESS;
		
		TileEntitySpinny tile = (TileEntitySpinny)worldIn.getTileEntity(pos);
		if(!tile.isWorking())
		{
			ItemStack heldItem = player.getHeldItem(handIn);
			
			// Retrieve last item in tile
			if(!tile.isEmpty() && heldItem.isEmpty())
			{
				for(int i=tile.getSizeInventory() - 1; i>=0; i--)
					if(!tile.getStackInSlot(i).isEmpty() && tile.canExtractItem(i, null, Direction.DOWN))
					{
						player.addItemStackToInventory(tile.removeStackFromSlot(i));
						break;
					}
			}
			// Add held item to tile
			else if(!heldItem.isEmpty())
			{
				for(int i=0; i<tile.getSizeInventory(); i++)
					if(tile.isItemValidForSlot(i, heldItem) && tile.canInsertItem(i, heldItem, null) && tile.getStackInSlot(i).isEmpty())
					{
						ItemStack inserted = heldItem.copy();
						int count = Math.min(heldItem.getCount(), tile.getInventoryStackLimit());
						inserted.setCount(count);
						heldItem.shrink(count);
						tile.setInventorySlotContents(i, inserted);
						break;
					}
			}
		}
		return ActionResultType.CONSUME;
	}
}
