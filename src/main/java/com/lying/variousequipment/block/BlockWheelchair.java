package com.lying.variousequipment.block;

import javax.annotation.Nullable;

import com.lying.variousequipment.data.VEItemTags;
import com.lying.variousequipment.init.VETileEntities;
import com.lying.variousequipment.item.bauble.ItemWheelchair;
import com.lying.variousequipment.tileentity.TileEntityWheelchair;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

public class BlockWheelchair extends ContainerBlock
{
	private static final VoxelShape SHAPE = makeCuboidShape(1D, 0D, 1D, 15D, 15D, 15D);
	
	public BlockWheelchair(AbstractBlock.Properties builder)
	{
		super(builder.doesNotBlockMovement().notSolid().noDrops());
	}
	
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new TileEntityWheelchair();
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}
	
	public boolean isTransparent(BlockState state){ return true; }
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		TileEntityWheelchair tile = (TileEntityWheelchair)worldIn.getTileEntity(pos);
		if(tile == null || tile.getStack().isEmpty() || !(tile.getStack().getItem() instanceof ItemWheelchair))
			return ActionResultType.PASS;
		
		int targetSlot = -1;
		ICuriosHelper helper = CuriosApi.getCuriosHelper();
		if(!helper.getCuriosHandler(player).isPresent())
			return ActionResultType.PASS;
		
		ICuriosItemHandler handler = helper.getCuriosHandler(player).orElse(null);
		ICurioStacksHandler stacks = handler.getCurios().get(VEItemTags.COSMETIC.getName().getPath());
		for(int i=0; i<stacks.getSlots(); i++)
			if(stacks.getStacks().getStackInSlot(i).isEmpty())
			{
				targetSlot = i;
				break;
			}
		
		if(targetSlot >= 0)
		{
			player.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
			player.rotationYaw = tile.getYaw();
			player.prevRotationYaw = tile.getYaw();
			
			stacks.getRenders().set(targetSlot, true);
			
			if(worldIn.isRemote)
				return ActionResultType.SUCCESS;
			else
			{
				stacks.getStacks().setStackInSlot(targetSlot, tile.getStack());
				
				tile.setItem(ItemStack.EMPTY);
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				return ActionResultType.CONSUME;
			}
		}
		else
			return ActionResultType.PASS;
	}
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
	{
		TileEntityWheelchair tile = (TileEntityWheelchair)worldIn.getTileEntity(pos);
		tile.setItemAndYaw(stack.copy(), placer.rotationYaw + 180F);
	}
	
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile != null && tile.getType() == VETileEntities.WHEELCHAIR)
		{
			TileEntityWheelchair wheelchair = (TileEntityWheelchair)tile;
			ItemStack stack = wheelchair.getStack();
			if(!stack.isEmpty())
			{
				ItemEntity itemDrop = new ItemEntity(worldIn, (double)pos.getX() + 0.5D, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5D, stack);
				itemDrop.setDefaultPickupDelay();
				worldIn.addEntity(itemDrop);
				wheelchair.setItemAndYaw(ItemStack.EMPTY, wheelchair.getYaw());
			}
		}
	}
	
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return Block.hasEnoughSolidSide(worldIn, pos.offset(Direction.DOWN), Direction.UP);
	}
	
	public PushReaction getPushReaction(BlockState state)
	{
		return PushReaction.DESTROY;
	}
}
