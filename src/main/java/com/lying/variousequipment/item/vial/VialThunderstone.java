package com.lying.variousequipment.item.vial;

import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.init.VODamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class VialThunderstone extends Vial
{
	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Reference.ModInfo.MOD_ID, "thunderstone");
	
	public VialThunderstone()
	{
		super(VialType.THROWABLE, VialShape.ROCK, 8, 13421772);
	}
	
	public ResourceLocation getRegistryName(){ return REGISTRY_NAME; }
	
	public boolean canDropItem(){ return false; }
	
	public boolean onSplash(Entity entityIn, World worldIn, RayTraceResult resultIn)
	{
//		if(!worldIn.isRemote) PacketHandler.sendToNearby(new PacketVialSonic(entityIn.posX, entityIn.posY, entityIn.posZ), worldIn, entityIn);
		
		float damage = 6F;
		double maxDist = 4;
		for(LivingEntity entity : worldIn.getEntitiesWithinAABB(LivingEntity.class, entityIn.getBoundingBox().grow(maxDist)))
			entity.attackEntityFrom(VODamageSource.SONIC, (float)Math.ceil(damage * (1F - entity.getDistance(entityIn) / maxDist)));
		return super.onSplash(entityIn, worldIn, resultIn);
	}
	
	public static class Builder extends Vial.Builder
	{
		public Builder(){ super(REGISTRY_NAME); }
		
		public Vial create()
		{
			return new VialThunderstone();
		}
	}
}
