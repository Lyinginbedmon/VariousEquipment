package com.lying.variousequipment.entity;

import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.init.VELootTables;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityScarecrow extends LivingEntity
{
	private static final Predicate<Entity> IS_RIDEABLE_MINECART = (entity) -> {
		return entity instanceof AbstractMinecartEntity && ((AbstractMinecartEntity)entity).canBeRidden();
	};
	
    protected static final DataParameter<Byte> COLOR = EntityDataManager.<Byte>createKey(EntityScarecrow.class, DataSerializers.BYTE);
    protected static final DataParameter<Boolean> BURNT = EntityDataManager.<Boolean>createKey(EntityScarecrow.class, DataSerializers.BOOLEAN);
	
	public long punchCooldown;
	
	public EntityScarecrow(EntityType<? extends EntityScarecrow> typeIn, World worldIn)
	{
		super(typeIn, worldIn);
	}
    
	public void registerData()
	{
		super.registerData();
		getDataManager().register(COLOR, Byte.valueOf((byte)DyeColor.CYAN.getId()));
		getDataManager().register(BURNT, false);
	}
	
    @OnlyIn(Dist.CLIENT)
    public DyeColor getColor()
    {
        return DyeColor.byId(((Byte)this.dataManager.get(COLOR)).byteValue());
    }
    
    public void setColor(DyeColor colour)
    {
    	this.dataManager.set(COLOR, Byte.valueOf((byte)colour.getId()));
    }
	
	public Iterable<ItemStack> getArmorInventoryList(){ return Lists.newArrayList(); }
	
	public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn){ return ItemStack.EMPTY; }
	
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack){ }
	
	public HandSide getPrimaryHand(){ return HandSide.RIGHT; }
	
	public boolean canBePushed(){ return false; }
	
	protected void collideWithEntity(Entity entityIn){ }
	
	protected void collideWithNearbyEntities()
	{
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(), IS_RIDEABLE_MINECART);
		for(Entity ent : list)
			if(getDistanceSq(ent) <= 0.2D)
				ent.applyEntityCollision(this);
	}
	
	public boolean canDespawn(double distanceToClosestPlayer){ return false; }
	
	public boolean isBurnt(){ return getDataManager().get(BURNT).booleanValue(); }
	
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putByte("Color", (byte)this.getColor().getId());
		if(isBurnt())
			compound.putBoolean("Burnt", true);
	}
	
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		this.setColor(DyeColor.byId(compound.getByte("Color")));
		getDataManager().set(BURNT, compound.getBoolean("Burnt"));
	}
	
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		if(itemstack.getItem() != Items.NAME_TAG)
		{
			if (player.isSpectator())
				return ActionResultType.SUCCESS;
			else if(player.world.isRemote)
				return ActionResultType.CONSUME;
			else
			{
				if(!itemstack.isEmpty() && itemstack.getItem() instanceof DyeItem)
				{
					if(!player.isCreative())
						itemstack.shrink(1);
					setColor(((DyeItem)itemstack.getItem()).getDyeColor());
					return ActionResultType.SUCCESS;
				}
				
				return ActionResultType.PASS;
			}
		}
		else
			return ActionResultType.PASS;
	}
	
	@SuppressWarnings("deprecation")
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(!this.world.isRemote && !this.removed)
		{
			if(DamageSource.OUT_OF_WORLD.equals(source))
			{
				this.remove();
				return false;
			}
			else if(source.isFireDamage() && this.isPotionActive(Effects.FIRE_RESISTANCE))
				return false;
			else if(!this.isInvulnerableTo(source))
			{
				if(source.isExplosion())
				{
					this.onBrokenBy(source);
					this.remove();
					return false;
				}
				else if(DamageSource.IN_FIRE.equals(source))
				{
					if(this.isBurning())
						this.damageScarecrow(source, 0.15F);
					else
						this.setFire(5);
				
					return false;
				}
				else if(DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5F)
				{
					this.damageScarecrow(source, 4.0F);
					return false;
				}
				else
				{
					boolean isArrow = source.getImmediateSource() instanceof AbstractArrowEntity;
					boolean hasPiercing = isArrow && ((AbstractArrowEntity)source.getImmediateSource()).getPierceLevel() > 0;
					boolean fromPlayer = "player".equals(source.getDamageType());
					if (!fromPlayer && !isArrow)
						return false;
					else if (source.getTrueSource() instanceof PlayerEntity && !((PlayerEntity)source.getTrueSource()).abilities.allowEdit)
						return false;
					else if (source.isCreativePlayer())
					{
						this.playBrokenSound();
						this.playParticles();
						this.remove();
						return hasPiercing;
					}
					else
					{
						long i = this.world.getGameTime();
						if (i - this.punchCooldown > 5L && !isArrow)
						{
							this.world.setEntityState(this, (byte)32);
							this.punchCooldown = i;
						}
						else
						{
							this.breakScarecrow(source);
							this.playParticles();
							this.remove();
						}
						
						return true;
					}
				}
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public void onKillCommand(){ this.remove(); }
	
	public boolean hitByEntity(Entity entityIn)
	{
		return entityIn instanceof PlayerEntity && !this.world.isBlockModifiable((PlayerEntity)entityIn, getPosition());
	}
	
	public boolean canBeHitWithPotion(){ return false; }
	
	public boolean attackable(){ return false; }
	
	public void func_241841_a(ServerWorld world, LightningBoltEntity bolt)
	{
		getDataManager().set(BURNT, true);
		addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, Reference.Values.TICKS_PER_MINUTE));
	}
	
	private void damageScarecrow(DamageSource source, float amount)
	{
		if(source == DamageSource.LIGHTNING_BOLT)
			return;
		
		float health = getHealth();
		health -= amount;
		if(health <= 0.5F)
		{
			onBrokenBy(source);
			this.remove();
		}
		else
			setHealth(health);
	}
	
	private void onBrokenBy(DamageSource source)
	{
		playBrokenSound();
		spawnDrops(source);
	}
	
	private void playBrokenSound()
	{
		this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
	}
	
	private void playParticles()
	{
		if(this.world instanceof ServerWorld)
			((ServerWorld)this.world).spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()), this.getPosX(), this.getPosYHeight(0.6666666666666666D), this.getPosZ(), 10, (double)(this.getWidth() / 4.0F), (double)(this.getHeight() / 4.0F), (double)(this.getWidth() / 4.0F), 0.05D);
	}
	
	private void breakScarecrow(DamageSource source)
	{
		if(isBurnt())
		{
			LootTable table = this.world.getServer().getLootTableManager().getLootTableFromLocation(VELootTables.BURNT_SCARECROW);
			table.generate(getLootContextBuilder(false, DamageSource.LIGHTNING_BOLT).build(LootParameterSets.ENTITY)).forEach(this::entityDropItem);
		}
		else
			Block.spawnAsEntity(this.world, getPosition(), new ItemStack(VEItems.SCARECROW));
		onBrokenBy(source);
	}
	
	public void livingTick()
	{
		if(isBurnt() && getRNG().nextInt(20) == 0)
			for(int i = 0; i < 2; ++i)
				this.world.addParticle(ParticleTypes.SMOKE, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), 0.0D, 0.0D, 0.0D);
		
		super.livingTick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 32)
		{
			if (this.world.isRemote)
			{
				this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
				this.punchCooldown = this.world.getGameTime();
			}
		}
		else
			super.handleStatusUpdate(id);
	}
	
	/**
	 * Checks if the entity is in range to render.
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		double d0 = this.getBoundingBox().getAverageEdgeLength() * 4.0D;
		if(Double.isNaN(d0) || d0 == 0.0D)
			d0 = 4.0D;
		
		d0 = d0 * 64.0D;
		return distance < d0 * d0;
	}
}
