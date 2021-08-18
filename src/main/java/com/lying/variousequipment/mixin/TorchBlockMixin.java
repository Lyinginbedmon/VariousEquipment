package com.lying.variousequipment.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.lying.variousequipment.config.ConfigVE;
import com.lying.variousequipment.init.VEBlocks;

import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(AbstractBlockState.class)
public class TorchBlockMixin
{
	@Inject(method = "ticksRandomly()Z", at = @At("HEAD"), cancellable = true)
	public void ticksRandomly(final CallbackInfoReturnable<Boolean> ci)
	{
		AbstractBlockState state = (AbstractBlockState)(Object)this;
		Block block = state.getBlock();
		if(block == Blocks.TORCH || block == Blocks.WALL_TORCH)
			ci.setReturnValue(ConfigVE.GENERAL.torchesBurnout());
	}
	
	@Inject(method = "randomTick(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", at = @At("HEAD"))
	public void randomTick(ServerWorld worldIn, BlockPos pos, Random random, final CallbackInfo ci)
	{
		AbstractBlockState state = (AbstractBlockState)(Object)this;
		Block block = state.getBlock();
		if((block == Blocks.TORCH || block == Blocks.WALL_TORCH) && ConfigVE.GENERAL.torchesBurnout())
		{
			if(random.nextInt(Math.max(1, ConfigVE.GENERAL.torchBurnoutRate())) == 0)
			{
				if(block == Blocks.TORCH)
					worldIn.setBlockState(pos, VEBlocks.BURNT_TORCH.getDefaultState());
				else if(block == Blocks.WALL_TORCH)
					worldIn.setBlockState(pos, VEBlocks.BURNT_TORCH_WALL.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, state.get(WallTorchBlock.HORIZONTAL_FACING)));
			}
		}
	}
}
