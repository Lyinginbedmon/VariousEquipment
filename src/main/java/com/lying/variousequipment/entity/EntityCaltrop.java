package com.lying.variousequipment.entity;

import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.init.VEItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCaltrop extends ProjectileItemEntity
{
	public EntityCaltrop(EntityType<EntityCaltrop> type, World world)
	{
		super(type, world);
	}
	
	public EntityCaltrop(World worldIn, LivingEntity throwerIn)
	{
		super(VEEntities.CALTROP, throwerIn, worldIn);
	}
	
	protected void registerData(){ }
	
	protected boolean canTriggerWalking(){ return false; }
	
	public void onCollideWithPlayer(PlayerEntity entityIn)
	{
		if(!this.world.isRemote && this.isOnGround() && !entityIn.abilities.disableDamage)
		{
			entityIn.attackEntityFrom(DamageSource.GENERIC, 1 + getEntityWorld().rand.nextInt(3));
			setDead();
		}
	}
	
	protected void onEntityHit(EntityRayTraceResult p_213868_1_)
	{
		
	}
	
	protected void onImpact(RayTraceResult p_213868_1_)
	{
		
	}
	
	protected Item getDefaultItem()
	{
		return VEItems.CALTROP;
	}
}
