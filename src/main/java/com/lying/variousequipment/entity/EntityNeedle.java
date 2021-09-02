package com.lying.variousequipment.entity;

import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemNeedle;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityNeedle extends ProjectileItemEntity
{
	public EntityNeedle(EntityType<EntityNeedle> type, World world)
	{
		super(type, world);
	}
	
	public EntityNeedle(World worldIn, LivingEntity throwerIn)
	{
		super(VEEntities.NEEDLE.get(), throwerIn, worldIn);
	}
	
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	protected float getGravityVelocity(){ return 0.01F; }
	
	protected void onEntityHit(EntityRayTraceResult trace)
	{
		if(!getEntityWorld().isRemote)
		{
			if(trace.getEntity() instanceof LivingEntity)
			{
				LivingEntity entity = (LivingEntity)trace.getEntity();
				boolean damageSuccess = entity.attackEntityFrom(DamageSource.GENERIC, 1F);
				
				ItemStack stack = getItem();
				if(stack.getItem() instanceof ItemNeedle)
					((ItemNeedle)stack.getItem()).affectEntity(entity, getEntityWorld(), trace, damageSuccess);
			}
			remove();
		}
	}
	
	protected void onImpact(RayTraceResult result)
	{
		super.onImpact(result);
		if(!getEntityWorld().isRemote())
		{
			getEntityWorld().setEntityState(this, (byte)3);
			remove();
		}
	}
	
	protected Item getDefaultItem()
	{
		return VEItems.NEEDLE_BONE;
	}
}
