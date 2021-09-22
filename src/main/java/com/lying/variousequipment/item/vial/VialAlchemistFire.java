package com.lying.variousequipment.item.vial;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class VialAlchemistFire extends Vial
{
	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Reference.ModInfo.MOD_ID, "alchemist_fire");
	private static final int RADIUS = 3;
	
	public VialAlchemistFire()
	{
		super(VialType.THROWABLE, 1, 13508649);
	}
	
	public ResourceLocation getRegistryName(){ return REGISTRY_NAME; }
	
	public boolean canDropItem(){ return false; }
	
	public int getBurnTime(){ return 16 * Reference.Values.TICKS_PER_SECOND * 10; }
	
	public boolean onSplash(Entity entityIn, World worldIn, RayTraceResult resultIn)
	{
		for(int x = -RADIUS; x<=RADIUS; x++)
			for(int y = -(RADIUS / 2); y<=(RADIUS / 2); y++)
				for(int z = -RADIUS; z<=RADIUS; z++)
				{
					BlockPos pos = new BlockPos(entityIn.getPosX() + x, entityIn.getPosY() + y, entityIn.getPosZ() + z);
					if(worldIn.isAirBlock(pos) && worldIn.rand.nextInt(3) == 0) worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
				}
		return super.onSplash(entityIn, worldIn, resultIn);
	}
	
	public static class Builder extends Vial.Builder
	{
		public Builder(){ super(REGISTRY_NAME); }
		
		public Vial create()
		{
			return new VialAlchemistFire();
		}
	}
}
