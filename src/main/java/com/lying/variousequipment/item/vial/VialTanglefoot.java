package com.lying.variousequipment.item.vial;

import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.init.VOPotions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class VialTanglefoot extends Vial
{
	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Reference.ModInfo.MOD_ID, "tanglefoot_bag");
	private static final double RADIUS = 3D;
	
	public VialTanglefoot()
	{
		super(VialType.THROWABLE, VialShape.BAG, 4, 9953313);
	}
	
	public ResourceLocation getRegistryName(){ return REGISTRY_NAME; }
	
	public boolean canDropItem(){ return true; }
	
	public boolean onSplash(Entity entityIn, World worldIn, RayTraceResult resultIn)
	{
		for(LivingEntity ent : worldIn.getEntitiesWithinAABB(LivingEntity.class, entityIn.getBoundingBox().grow(RADIUS, 1, RADIUS)))
		{
//			if((ent.height * ent.width) >= ConfigVO.General.items.tanglefootMaxSize || !ent.isNonBoss()) continue;
			
			int duration = (ent.getRNG().nextInt(4) + 1) + (ent.getRNG().nextInt(4) + 1);
			ent.addPotionEffect(new EffectInstance(VOPotions.ENTANGLED, Reference.Values.TICKS_PER_SECOND * duration));
		}
		return super.onSplash(entityIn, worldIn, resultIn);
	}
	
	public static class Builder extends Vial.Builder
	{
		public Builder(){ super(REGISTRY_NAME); }
		
		public Vial create()
		{
			return new VialTanglefoot();
		}
	}
}
