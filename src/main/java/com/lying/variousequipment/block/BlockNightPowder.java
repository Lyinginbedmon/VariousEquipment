package com.lying.variousequipment.block;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.tileentity.TileEntityNightPowder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@SuppressWarnings("deprecation")
public class BlockNightPowder extends Block implements ITileEntityProvider
{
	public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.REDSTONE_NORTH;
	public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.REDSTONE_EAST;
	public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.REDSTONE_SOUTH;
	public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.REDSTONE_WEST;
	public static final Map<Direction, EnumProperty<RedstoneSide>> FACING_PROPERTY_MAP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
	private final BlockState sideBaseState;
	
	private static final VoxelShape BASE_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
	private static final Map<Direction, VoxelShape> SIDE_TO_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
	private static final Map<Direction, VoxelShape> SIDE_TO_ASCENDING_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.NORTH), Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.SOUTH), Block.makeCuboidShape(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.EAST), Block.makeCuboidShape(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.WEST), Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
	private final Map<BlockState, VoxelShape> stateToShapeMap = Maps.newHashMap();
	
	public BlockNightPowder(Properties properties)
	{
		super(properties.doesNotBlockMovement());
		this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, RedstoneSide.NONE).with(EAST, RedstoneSide.NONE).with(SOUTH, RedstoneSide.NONE).with(WEST, RedstoneSide.NONE));
		this.sideBaseState = this.getDefaultState().with(NORTH, RedstoneSide.SIDE).with(EAST, RedstoneSide.SIDE).with(SOUTH, RedstoneSide.SIDE).with(WEST, RedstoneSide.SIDE);

	      for(BlockState blockstate : this.getStateContainer().getValidStates())
	            this.stateToShapeMap.put(blockstate, this.getShapeForState(blockstate));
	}
	
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new TileEntityNightPowder();
	}
	
	private VoxelShape getShapeForState(BlockState state)
	{
		VoxelShape voxelshape = BASE_SHAPE;
		for(Direction direction : Direction.Plane.HORIZONTAL)
		{
			RedstoneSide redstoneside = state.get(FACING_PROPERTY_MAP.get(direction));
			if(redstoneside == RedstoneSide.SIDE)
				voxelshape = VoxelShapes.or(voxelshape, SIDE_TO_SHAPE.get(direction));
			else if (redstoneside == RedstoneSide.UP)
				voxelshape = VoxelShapes.or(voxelshape, SIDE_TO_ASCENDING_SHAPE.get(direction));
		}
		
		return voxelshape;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return this.stateToShapeMap.get(state);
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, SOUTH, WEST);
	}
	
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return getUpdatedState(context.getWorld(), this.sideBaseState, context.getPos());
	}
	
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if(facing == Direction.DOWN)
			return stateIn;
		else if (facing == Direction.UP)
			return this.getUpdatedState(worldIn, stateIn, currentPos);
		else
		{
			RedstoneSide side = this.getSide(worldIn, currentPos, facing);
			return side.func_235921_b_() == stateIn.get(FACING_PROPERTY_MAP.get(facing)).func_235921_b_() && !areAllSidesValid(stateIn) ? stateIn.with(FACING_PROPERTY_MAP.get(facing), side) : this.getUpdatedState(worldIn, this.sideBaseState.with(FACING_PROPERTY_MAP.get(facing), side), currentPos);
		}
	}
	
	private RedstoneSide getSide(IBlockReader worldIn, BlockPos pos, Direction face)
	{
		return this.recalculateSide(worldIn, pos, face, !worldIn.getBlockState(pos.up()).isNormalCube(worldIn, pos));
	}
	
	public static boolean areAllSidesValid(BlockState state)
	{
		return state.get(NORTH).func_235921_b_() && state.get(EAST).func_235921_b_() && state.get(SOUTH).func_235921_b_() && state.get(WEST).func_235921_b_();
	}
	
	public static boolean areAllSidesInvalid(BlockState state)
	{
		return !state.get(NORTH).func_235921_b_() && !state.get(EAST).func_235921_b_() && !state.get(SOUTH).func_235921_b_() && !state.get(WEST).func_235921_b_();
	}
	
	private BlockState getUpdatedState(IBlockReader worldIn, BlockState state, BlockPos pos)
	{
		boolean isInvalid = areAllSidesInvalid(state);
		state = recalculateFacingState(worldIn, getDefaultState(), pos);
		if(isInvalid && areAllSidesInvalid(state))
			return state;
		else
		{
			boolean north = state.get(NORTH).func_235921_b_();
			boolean south = state.get(SOUTH).func_235921_b_();
			boolean east = state.get(EAST).func_235921_b_();
			boolean west = state.get(WEST).func_235921_b_();
			boolean eastOrWest = !north && !south;
			boolean northOrSouth = !east && !west;
			if(!west && eastOrWest)
				state = state.with(WEST, RedstoneSide.SIDE);
			
			if(!east && eastOrWest)
				state = state.with(EAST, RedstoneSide.SIDE);
			
			if(!north && northOrSouth)
				state = state.with(NORTH, RedstoneSide.SIDE);
			
			if(!south && northOrSouth)
				state = state.with(SOUTH, RedstoneSide.SIDE);
			
			return state;
		}
	}
	
	public void updateDiagonalNeighbors(BlockState state, IWorld worldIn, BlockPos pos, int flags, int recursionLeft)
	{
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		for(Direction direction : Direction.Plane.HORIZONTAL)
		{
			RedstoneSide side = state.get(FACING_PROPERTY_MAP.get(direction));
			if(side != RedstoneSide.NONE && !worldIn.getBlockState(mutablePos.setAndMove(pos, direction)).isIn(this))
			{
				mutablePos.move(Direction.DOWN);
				BlockState mutableState = worldIn.getBlockState(mutablePos);
				if(!mutableState.isIn(Blocks.OBSERVER))
				{
					BlockPos offsetPos = mutablePos.offset(direction.getOpposite());
					BlockState blockstate1 = mutableState.updatePostPlacement(direction.getOpposite(), worldIn.getBlockState(offsetPos), worldIn, mutablePos, offsetPos);
					replaceBlockState(mutableState, blockstate1, worldIn, mutablePos, flags, recursionLeft);
				}
				
				mutablePos.setAndMove(pos, direction).move(Direction.UP);
				BlockState mutableStateUp = worldIn.getBlockState(mutablePos);
				if(!mutableStateUp.isIn(Blocks.OBSERVER))
				{
					BlockPos blockpos1 = mutablePos.offset(direction.getOpposite());
					BlockState blockstate2 = mutableStateUp.updatePostPlacement(direction.getOpposite(), worldIn.getBlockState(blockpos1), worldIn, mutablePos, blockpos1);
					replaceBlockState(mutableStateUp, blockstate2, worldIn, mutablePos, flags, recursionLeft);
				}
			}
		}
	}
	
	private BlockState recalculateFacingState(IBlockReader reader, BlockState state, BlockPos pos)
	{
		boolean belowSolid = !reader.getBlockState(pos.up()).isNormalCube(reader, pos);
		for(Direction direction : Direction.Plane.HORIZONTAL)
			if(!state.get(FACING_PROPERTY_MAP.get(direction)).func_235921_b_())
			{
				RedstoneSide redstoneSide = recalculateSide(reader, pos, direction, belowSolid);
				state = state.with(FACING_PROPERTY_MAP.get(direction), redstoneSide);
			}
		return state;
	}
	
	private RedstoneSide recalculateSide(IBlockReader reader, BlockPos pos, Direction direction, boolean nonNormalCubeAbove)
	{
		BlockPos blockpos = pos.offset(direction);
		BlockState blockstate = reader.getBlockState(blockpos);
		if(nonNormalCubeAbove)
		{
			boolean flag = this.canPlaceOnTopOf(reader, blockpos, blockstate);
			if (flag && canConnectTo(reader.getBlockState(blockpos.up()), reader, blockpos.up(), null) )
			{
				if (blockstate.isSolidSide(reader, blockpos, direction.getOpposite()))
					return RedstoneSide.UP;
				
				return RedstoneSide.SIDE;
			}
		}
		
		return !canConnectTo(blockstate, reader, blockpos, direction) && (blockstate.isNormalCube(reader, blockpos) || !canConnectTo(reader.getBlockState(blockpos.down()), reader, blockpos.down(), null)) ? RedstoneSide.NONE : RedstoneSide.SIDE;
	}
	
	protected static boolean canConnectTo(BlockState blockState, IBlockReader world, BlockPos pos, @Nullable Direction side)
	{
		return blockState.isIn(VEBlocks.NIGHT_POWDER);
	}
	
	private boolean canPlaceOnTopOf(IBlockReader reader, BlockPos pos, BlockState state)
	{
		return state.isSolidSide(reader, pos, Direction.UP);
	}
}
