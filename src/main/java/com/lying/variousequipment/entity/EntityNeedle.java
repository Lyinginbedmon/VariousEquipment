package com.lying.variousequipment.entity;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;
import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemNeedle;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityNeedle extends ProjectileItemEntity
{
	private final Set<EffectInstance> customPotionEffects = Sets.newHashSet();
	
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
	
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		if(!this.customPotionEffects.isEmpty())
		{
			ListNBT listnbt = new ListNBT();
			for(EffectInstance effectinstance : this.customPotionEffects)
				listnbt.add(effectinstance.write(new CompoundNBT()));
			
			compound.put("CustomPotionEffects", listnbt);
		}
	}
	
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		for(EffectInstance effectinstance : PotionUtils.getFullEffectsFromTag(compound))
			this.addEffect(effectinstance);
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
					((ItemNeedle)stack.getItem()).affectEntity(entity, this, getEntityWorld(), trace, damageSuccess);
				
				if(damageSuccess && !this.customPotionEffects.isEmpty())
					for(EffectInstance effect : this.customPotionEffects)
						entity.addPotionEffect(effect);
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
		return VEItems.NEEDLE_IRON;
	}
	
	public void setPotionEffect(ItemStack stack)
	{
		if (stack.getItem() == Items.POTION)
		{
			Collection<EffectInstance> collection = PotionUtils.getFullEffectsFromItem(stack);
			if(!collection.isEmpty())
				for(EffectInstance effectinstance : collection)
					this.customPotionEffects.add(new EffectInstance(effectinstance));
		}
		else
			this.customPotionEffects.clear();
	}
	
	public void addEffect(EffectInstance effect)
	{
		this.customPotionEffects.add(effect);
	}
}
