package com.lying.variousequipment.item;

import com.lying.variousequipment.entity.EntityNeedle;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public abstract class ItemNeedle extends Item
{
    public ItemNeedle(Properties properties)
    {
		super(properties);
	}
    
	public abstract void affectEntity(LivingEntity entityIn, World worldIn, EntityRayTraceResult resultIn, boolean damageSuccess);
	
	public abstract ResourceLocation getEntityTexture(EntityNeedle entity);
}
