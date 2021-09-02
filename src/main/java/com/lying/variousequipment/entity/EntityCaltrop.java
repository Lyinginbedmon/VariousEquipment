package com.lying.variousequipment.entity;

import java.util.List;

import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityCaltrop extends ProjectileItemEntity
{
	protected boolean inGround;
	
	public EntityCaltrop(EntityType<EntityCaltrop> type, World world)
	{
		super(type, world);
	}
	
	public EntityCaltrop(World worldIn, LivingEntity throwerIn)
	{
		super(VEEntities.CALTROP.get(), throwerIn, worldIn);
	}
	
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	protected boolean canTriggerWalking(){ return false; }
	
	protected void onEntityHit(EntityRayTraceResult trace)
	{
		Vector3d motion = getMotion();
		Vector3d hitVec = trace.getHitVec().subtract(getPosX(), getPosY(), getPosZ());
		double vel = motion.length();
		setMotion(hitVec.normalize().scale(vel * -0.75D));
	}
	
	protected void func_230299_a_(BlockRayTraceResult trace)
	{
		super.func_230299_a_(trace);
		
		Vector3d motion = getMotion();
		double vel = motion.length();
		if(trace.getFace() == Direction.UP || vel < 0.1D)
		{
			Vector3d hitVec = trace.getHitVec().subtract(getPosX(), getPosY(), getPosZ());
			setMotion(hitVec);
			Vector3d hitVec2 = hitVec.normalize().scale((double)0.05F);
			setRawPosition(this.getPosX() - hitVec2.x, this.getPosY() - hitVec2.y, this.getPosZ() - hitVec2.z);
			this.inGround = true;
		}
		else
		{
			Direction face = trace.getFace();
			setMotion(motion.normalize().scale(vel * 0.75D).mul(face.getAxis() == Axis.X ? -1 : 1, face.getAxis() == Axis.Y ? -1 : 1, face.getAxis() == Axis.Z ? -1 : 1));
		}
	}
	
	public void tick()
	{
		super.tick();
		World world = getEntityWorld();
		if(this.inGround && !world.isRemote)
		{
			List<LivingEntity> mobs = world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox().grow(0.1D));
			if(!mobs.isEmpty())
				for(LivingEntity victim : mobs)
				{
					if(victim.isInvulnerable() || victim.hurtResistantTime > 0 || victim.getType() == EntityType.PLAYER && ((PlayerEntity)victim).abilities.disableDamage)
						continue;
					
					if(victim.attackEntityFrom(DamageSource.GENERIC, 1 + this.rand.nextInt(3)))
					{
						victim.addPotionEffect(new EffectInstance(Effects.SLOWNESS, Reference.Values.TICKS_PER_SECOND * (1 + this.rand.nextInt(3)), 0, false, false));
						setDead();
						return;
					}
				}
		}
	}
	
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putBoolean("inGround", this.inGround);
	}
	
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		this.inGround = compound.getBoolean("inGround");
	}
	
	protected Item getDefaultItem()
	{
		return VEItems.CALTROP;
	}
}
