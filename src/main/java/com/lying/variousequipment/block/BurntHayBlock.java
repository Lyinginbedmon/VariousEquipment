package com.lying.variousequipment.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BurntHayBlock extends RotatedPillarBlock
{
	public BurntHayBlock(Properties properties)
	{
		super(properties);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double posX = (double)pos.getX() + rand.nextDouble();
		double posY = (double)pos.getY() + rand.nextDouble();
		double posZ = (double)pos.getZ() + rand.nextDouble();
		worldIn.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0D, 0D, 0D);
	}
}
