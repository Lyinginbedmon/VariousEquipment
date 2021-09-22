package com.lying.variousequipment.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockGuano extends Block
{
	public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS_1_8;
	protected static final VoxelShape[] SHAPES = new VoxelShape[]
			{
					VoxelShapes.empty(), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), 
					Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
					Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
					Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
					Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), 
					Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), 
					Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), 
					Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
	
	public BlockGuano(Properties properties)
	{
		super(properties.hardnessAndResistance(0.1F).setRequiresTool().sound(SoundType.SAND));
		this.setDefaultState(this.stateContainer.getBaseState().with(LAYERS, Integer.valueOf(1)));
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPES[state.get(LAYERS)];
	}
	
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPES[state.get(LAYERS) - 1];
	}
	
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos)
	{
		return SHAPES[state.get(LAYERS)];
	}
	
	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
	{
		return SHAPES[state.get(LAYERS)];
	}
	
	public boolean isTransparent(BlockState state){ return state.get(LAYERS) < 8; }
	
	public boolean isToolEffective(BlockState state, ToolType tool){ return tool == ToolType.SHOVEL; }
	
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext)
	{
		int i = state.get(LAYERS);
		if(useContext.getItem().getItem() == this.asItem() && i < 8)
		{
			if(useContext.replacingClickedOnBlock())
				return useContext.getFace() == Direction.UP;
			else
				return true;
		}
		else
			return i == 1;
	}
	
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		if(blockstate.isIn(this))
			return blockstate.with(LAYERS, Integer.valueOf(Math.min(8, blockstate.get(LAYERS) + 1)));
		else
			return super.getStateForPlacement(context);
	}
	
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if(facing == Direction.DOWN && !isValidPosition(stateIn, worldIn, currentPos))
		{
			if(worldIn instanceof World && !worldIn.isRemote())
				spawnDrops(stateIn, (World)worldIn, currentPos);
			return Blocks.AIR.getDefaultState();
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).isOpaqueCube(worldIn, pos.down());
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(LAYERS);
	}
}
