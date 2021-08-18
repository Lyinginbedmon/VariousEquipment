package com.lying.variousequipment.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockBurntTorchWall extends WallTorchBlock
{
	public BlockBurntTorchWall(Properties properties)
	{
		super(properties.doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.WOOD), null);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand){ }
	
	@SuppressWarnings("deprecation")
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		ItemStack itemstack = player.getHeldItem(handIn);
		Item item = itemstack.getItem();
		if(item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE)
			return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		else
		{
			worldIn.setBlockState(pos, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, state.get(WallTorchBlock.HORIZONTAL_FACING)), 11);
			if(!player.isCreative())
			{
				if(item == Items.FLINT_AND_STEEL)
					itemstack.damageItem(1, player, (player1) -> { player1.sendBreakAnimation(handIn); });
				else
					itemstack.shrink(1);
			}
			
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}
	}
}
