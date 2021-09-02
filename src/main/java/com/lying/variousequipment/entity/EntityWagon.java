package com.lying.variousequipment.entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemChassis;
import com.lying.variousequipment.item.ItemWheel;
import com.lying.variousequipment.network.PacketHandler;
import com.lying.variousequipment.network.PacketWagonReins;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.entity.PartEntity;

public class EntityWagon extends LivingEntity implements IInventoryChangedListener
{
	private static final DataParameter<ItemStack> STACK_CHASSIS = EntityDataManager.createKey(EntityWagon.class, DataSerializers.ITEMSTACK);
	private static final DataParameter<ItemStack> STACK_CANOPY = EntityDataManager.createKey(EntityWagon.class, DataSerializers.ITEMSTACK);
	private static final DataParameter<ItemStack> STACK_WHEEL1 = EntityDataManager.createKey(EntityWagon.class, DataSerializers.ITEMSTACK);
	private static final DataParameter<ItemStack> STACK_WHEEL2 = EntityDataManager.createKey(EntityWagon.class, DataSerializers.ITEMSTACK);
	private static final DataParameter<ItemStack> STACK_WHEEL3 = EntityDataManager.createKey(EntityWagon.class, DataSerializers.ITEMSTACK);
	private static final DataParameter<ItemStack> STACK_WHEEL4 = EntityDataManager.createKey(EntityWagon.class, DataSerializers.ITEMSTACK);
	
	private static final EntitySize SIZE_WHEELS = EntitySize.fixed(3.5F, 1.2F);
	private static final EntitySize SIZE_CHASSIS = EntitySize.fixed(3.5F, 0.75F);
	protected Inventory parts;
	
	public float wheelSpin = 0F;
	
	private final WagonPartEntity[] componentEntities;
	private final WagonPartEntity componentChassis;
	private final WagonPartEntity componentReins;
	private final WagonPartEntity componentWheel0;
	private final WagonPartEntity componentWheel1;
	private final WagonPartEntity componentWheel2;
	private final WagonPartEntity componentWheel3;
	
	private int tickCounter = 0;
	
	@Nullable
	private Entity reinsHolder;
	private int reinsHolderID;
	@Nullable
	private CompoundNBT reinsNBTTag;
	private int reinsTimer = 0;
	
	/*
	 * TO DO
	 * * Can be leashed to horse/donkey
	 * * Apply movement debuff to leashed horse/donkey relative to number of wheels
	 * * Inherit movement speed of leashed horse/donkey (after debuff)
	 * * Multiple seat positions, selected based on right-click position
	 * * Seat position 1 controls leashed horse/donkey
	 * * Front and rear inventory accessed based on seat position
	 * 
	 * STREAM
	 * * Allow wagons to be leashed to horse-type entities
	 * * Translate wagon control to the leashed horse
	 */
	
	public EntityWagon(EntityType<? extends EntityWagon> type, World worldIn)
	{
		super(type, worldIn);
		this.stepHeight = 1F;
		this.initPartInventory();
		
		this.componentChassis = new WagonPartEntity(this, PartType.CHASSIS, -1, 3.5F, 0.1F);
		this.componentReins = new WagonPartEntity(this, PartType.REINS, 0, 1F, 1F);
		this.componentWheel0 = new WagonPartEntity(this, PartType.WHEEL, 2, 1F, 1F);
		this.componentWheel1 = new WagonPartEntity(this, PartType.WHEEL, 3, 1F, 1F);
		this.componentWheel2 = new WagonPartEntity(this, PartType.WHEEL, 4, 1F, 1F);
		this.componentWheel3 = new WagonPartEntity(this, PartType.WHEEL, 5, 1F, 1F);
		
		this.componentEntities = new WagonPartEntity[]{ this.componentChassis, this.componentReins, this.componentWheel0, this.componentWheel1, this.componentWheel2, this.componentWheel3 };
	}
	
	public EntityWagon(World worldIn)
	{
		this(VEEntities.WAGON.get(), worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute getAttributes()
	{
		return LivingEntity.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE, 10D);
	}
	
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(STACK_CHASSIS, ItemStack.EMPTY);
		this.dataManager.register(STACK_CANOPY, ItemStack.EMPTY);
		this.dataManager.register(STACK_WHEEL1, ItemStack.EMPTY);
		this.dataManager.register(STACK_WHEEL2, ItemStack.EMPTY);
		this.dataManager.register(STACK_WHEEL3, ItemStack.EMPTY);
		this.dataManager.register(STACK_WHEEL4, ItemStack.EMPTY);
	}
	
	protected void initPartInventory()
	{
		Inventory inventory = this.parts;
		this.parts = new Inventory(6);
		if(inventory != null)
		{
			inventory.removeListener(this);
			for(int j=0; j<6; ++j)
			{
				ItemStack stack = inventory.getStackInSlot(j);
				if(!stack.isEmpty())
					this.parts.setInventorySlotContents(j, stack.copy());
			}
		}
		this.parts.addListener(this);
		
	}
	
	public boolean canDespawn(double distanceToClosestPlayer){ return false; }
	
	public boolean canBeLeashedTo(PlayerEntity entityIn){ return false; }
	
	public ActionResultType processInitialInteract(PlayerEntity player, Hand hand)
	{
		if(!this.isAlive())
			return ActionResultType.PASS;
		else
		{
			ActionResultType result = this.applyPlayerInteraction(player, hand);
			if(result.isSuccessOrConsume())
				return result;
			else
			{
				result = ActionResultType.PASS;
				return result.isSuccessOrConsume() ? result : super.processInitialInteract(player, hand);
			}
		}
	}
	
	private ActionResultType applyPlayerInteraction(PlayerEntity player, Hand hand)
	{
		// Exclude players in the interior space of the wagon from interacting with components
		if(!getBoundingBox().grow(0D, 1.5D, 0D).intersects(player.getBoundingBox()))
		{
			ItemStack heldItem = player.getHeldItem(hand);
			
			// Targeted inventory slot derived from bounding box hit
			WagonPartEntity component = getTargetedInventoryPart(player);
			if(component != null)
				switch(component.type)
				{
					case CANOPY:
					case WHEEL:
						int slot = component == null ? 0 : component.slot;
						boolean isItemValid = component == null ? PartType.CHASSIS.predicate.apply(heldItem) : component.type.predicate.apply(heldItem);
						
						if(heldItem.getItem() == VEItems.CROWBAR && player.isSecondaryUseActive() && slot != 0 || isItemValid)
						{
							// Component removal
							if(!this.parts.getStackInSlot(slot).isEmpty())
							{
								component.entityDropItem(this.parts.getStackInSlot(slot));
								this.parts.setInventorySlotContents(slot, ItemStack.EMPTY);
							}
							
							// Component replacement
							if(isItemValid)
							{
								this.parts.setInventorySlotContents(slot, heldItem.copy().split(1));
								heldItem.shrink(1);
							}
							
							return ActionResultType.SUCCESS;
						}
						break;
					case REINS:
						if(this.reinsTimer == 0)
						{
							if(this.getReined())
								clearReined(true, !player.abilities.isCreativeMode);
							else if(heldItem.getItem() == Items.LEAD)
							{
								this.setReinsHolder(player, true);
								player.getCooldownTracker().setCooldown(Items.LEAD, Reference.Values.TICKS_PER_SECOND);
								heldItem.shrink(1);
							}
							
							this.reinsTimer = Reference.Values.TICKS_PER_SECOND;
							return ActionResultType.func_233537_a_(this.world.isRemote);
						}
						break;
					case CHASSIS:
					default:
						break;
				}
		}
		
		if(getPassengers().size() < 3 && player != getReinsHolder() && !this.world.isRemote)
			player.startRiding(this);
		
		return ActionResultType.func_233537_a_(this.world.isRemote);
	}
	
	public boolean canCollide(Entity entity){ return false; }
	
	public void applyEntityCollision(Entity entityIn){ }
	
	public boolean canBeCollidedWith(){ return false; }
	
	public boolean canBePushed(){ return false; }
	
	protected void collideWithNearbyEntities(){ }
	
	protected void collideWithEntity(Entity entityIn){ }
	
	/**
	 * 0 - Chassis<br>
	 * 1 - Canopy<br>
	 * 2 - Wheel<br>
	 * 3 - Wheel<br>
	 * 4 - Wheel<br>
	 * 5 - Wheel<br>
	 * @param slot
	 * @return
	 */
	public boolean hasPartInSlot(int slot)
	{
		return !this.parts.getStackInSlot(slot % this.parts.getSizeInventory()).isEmpty();
	}
	
	public ItemStack getChassis()
	{
		return getDataManager().get(STACK_CHASSIS);
	}
	
	public void setFire(int seconds)
	{
		ItemStack chassis = getChassis();
		Block block = Block.getBlockFromItem(chassis.getItem());
		if(chassis.isEmpty() || block.getDefaultState().getMaterial().isFlammable())
			super.setFire(seconds);
	}
	
	public boolean hasWheels()
	{
		for(int i=0; i<4; i++)
			if(!getWheelInSlot(i).isEmpty())
				return true;
		return false;
	}
	
	public ItemStack getWheelInSlot(int slot)
	{
		switch(slot)
		{
			case 0:	return getDataManager().get(STACK_WHEEL1);
			case 1:	return getDataManager().get(STACK_WHEEL2);
			case 2:	return getDataManager().get(STACK_WHEEL3);
			case 3:	return getDataManager().get(STACK_WHEEL4);
		}
		return ItemStack.EMPTY;
	}
	
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		ListNBT partsList = new ListNBT();
		for(int i=0; i<this.parts.getSizeInventory(); ++i)
		{
			ItemStack part = this.parts.getStackInSlot(i);
			CompoundNBT stackData = new CompoundNBT();
			if(!part.isEmpty())
				part.write(stackData);
			
			partsList.add(stackData);
		}
		
		compound.put("PartItems", partsList);
		
		if(this.reinsHolder != null)
		{
			CompoundNBT compound2 = new CompoundNBT();
			if(this.reinsHolder instanceof LivingEntity)
				compound2.putUniqueId("UUID", this.reinsHolder.getUniqueID());
			compound.put("Reins", compound2);
		}
		else if(this.reinsNBTTag != null)
			compound.put("Reins", this.reinsNBTTag.copy());
	}
	
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		if(compound.contains("PartItems", 9))
		{
			this.initPartInventory();
			ListNBT partsList = compound.getList("PartItems", 10);
			for(int i=0; i < this.parts.getSizeInventory(); ++i)
				this.parts.setInventorySlotContents(i, ItemStack.read(partsList.getCompound(i)));
		}
		
		if(compound.contains("Reins", 10))
			this.reinsNBTTag = compound.getCompound("Reins");
	}
	
	public void setPartInSlot(int slot, ItemStack itemStack)
	{
		if(this.parts == null)
			initPartInventory();
		
		this.parts.setInventorySlotContents(slot, itemStack);
	}
	
	public void onInventoryChanged(IInventory invBasic)
	{
		if(this.world.isRemote) return;
		getDataManager().set(STACK_CHASSIS, invBasic.getStackInSlot(0));
		getDataManager().set(STACK_CANOPY, invBasic.getStackInSlot(1));
		getDataManager().set(STACK_WHEEL1, invBasic.getStackInSlot(2));
		getDataManager().set(STACK_WHEEL2, invBasic.getStackInSlot(3));
		getDataManager().set(STACK_WHEEL3, invBasic.getStackInSlot(4));
		getDataManager().set(STACK_WHEEL4, invBasic.getStackInSlot(5));
	}
	
	public WagonPartEntity getTargetedInventoryPart(Entity entityIn)
	{
		Vector3d eyePos = new Vector3d(entityIn.getPosX(), entityIn.getPosYEye(), entityIn.getPosZ());
		Vector3d viewVec = entityIn.getLookVec();
		
		double range = eyePos.distanceTo(getPositionVec());
		Vector3d projected = eyePos.add(viewVec.mul(range, range, range));
		
		PartEntity<?> closestHit = null;
		for(PartEntity<?> component : getParts())
		{
			if(!((WagonPartEntity)component).hasInventory()) continue;
			AxisAlignedBB bounds = component.getBoundingBox();
			if(bounds.intersects(eyePos, projected))
				if(closestHit == null || eyePos.distanceTo(closestHit.getPositionVec()) > eyePos.distanceTo(component.getPositionVec()))
					closestHit = component;
		}
		
		return (WagonPartEntity)closestHit;
	}
	
	@Override
	public boolean isMultipartEntity(){ return true; }
	
	@Override
	public PartEntity<?>[] getParts()
	{
		return this.componentEntities;
	}
	
	public void tick()
	{
		super.tick();
		if(this.reinsTimer > 0)
			--this.reinsTimer;
		
		EntitySize size = getSize(Pose.STANDING);
		AxisAlignedBB bounds = this.getBoundingBox();
		this.setBoundingBox(new AxisAlignedBB(bounds.minX, bounds.minY, bounds.minZ, bounds.minX + (double)size.width, bounds.minY + (double)size.height, bounds.minZ + (double)size.width));
		
		for(PartEntity<?> component : getParts())
		{
			WagonPartEntity part = (WagonPartEntity)component;
			switch(part.type)
			{
				case CANOPY:
					break;
				case WHEEL:
					part.setSize(1F, hasWheels() ? 1.2F : 0.75F);
					break;
				case CHASSIS:
					part.setSize(3.2F, hasWheels() ? 0.5F : 0.1F);
					break;
				default:
					break;
				
			}
		}
		
		Entity reinsHolder = this.getReinsHolder();
		if(reinsHolder != null)
		{
			Vector3d yawToReins = getPositionVec().subtract(reinsHolder.getPositionVec());
			double yaw = (Math.atan2(yawToReins.z, yawToReins.x) * (double)(180F / (float)Math.PI)) + 90.0F;
			
			this.prevRotationYaw = this.rotationYaw;
			this.rotationYaw = (float)yaw;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.renderYawOffset = this.rotationYaw;
			
			double dist = getPositionVec().distanceTo(reinsHolder.getPositionVec());
			double idealDist = 1D + (this.getWidth() + reinsHolder.getWidth()) * 0.5D;
			if(dist > idealDist + 5F)
				this.clearReined(true, true);
			if(dist != idealDist)
			{
				double delta = dist - idealDist;
				Vector3d direction = reinsHolder.getPositionVec().subtract(getPositionVec()).normalize().mul(delta, 0D, delta);
				this.move(MoverType.SELF, direction);
			}
		}
		
		if(!this.world.isRemote && this.tickCounter++ == 100)
		{
			this.tickCounter = 0;
			if(isAlive() && this.parts != null && this.parts.getStackInSlot(0).isEmpty())
			{
				this.dropInventory();
				this.remove();
			}
		}
	}
	
	public void livingTick()
	{
		super.livingTick();
		
		for(PartEntity<?> component : getParts())
		{
			component.prevPosX = component.getPosX();
			component.prevPosY = component.getPosY();
			component.prevPosZ = component.getPosZ();
			component.lastTickPosX = component.getPosX();
			component.lastTickPosY = component.getPosY();
			component.lastTickPosZ = component.getPosZ();
		}
		
		Vector3d forward = getForwardVec();
		Vector3d right = forward.rotateYaw((float)Math.toRadians(90D));
		
		Vector3d axleFront = forward.mul(1.05D, 0D, 1.05D);
		Vector3d axleRear = forward.mul(-1.45D, 0D, -1.45D);
		double width = 1.6D;
		setPartPosition(this.componentChassis, Vector3d.ZERO);
		setPartPosition(this.componentReins, axleFront.add(forward));
		setPartPosition(this.componentWheel0, axleFront.add(right.mul(-width, 0D, -width)));
		setPartPosition(this.componentWheel1, axleFront.add(right.mul(width, 0D, width)));
		setPartPosition(this.componentWheel2, axleRear.add(right.mul(-width, 0D, -width)));
		setPartPosition(this.componentWheel3, axleRear.add(right.mul(width, 0D, width)));
	}
	
	protected void dropInventory()
	{
		this.playSound(SoundEvents.ENTITY_ITEM_FRAME_BREAK, 1.0F, 1.0F);
		for(int i=0; i<this.parts.getSizeInventory(); i++)
		{
			ItemStack stack = this.parts.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				this.entityDropItem(stack.copy());
				this.parts.setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}
	}
	
	public void func_233629_a_(LivingEntity entityIn, boolean isFlying)
	{
		double motionX = entityIn.getPosX() - entityIn.prevPosX;
		double motionY = isFlying ? entityIn.getPosY() - entityIn.prevPosY : 0D;
		double motionZ = entityIn.getPosZ() - entityIn.prevPosZ;
		
		float spin = Math.min(1F, MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ)) * 0.4F;
		
		Vector3d motionVec = new Vector3d(motionX, 0D, motionZ).normalize();
		Vector3d forwardVec = getForwardVec();
		Vector3d travel = forwardVec.subtract(motionVec);
		double yaw = Math.toDegrees(Math.atan2(travel.z, travel.x));
		while(yaw > 180) yaw -= 180D;
		this.wheelSpin += yaw < 90D ? spin : -spin;
	}
	
	public Vector3d getForwardVec(){ return Vector3d.fromPitchYaw(new Vector2f(this.rotationPitch, this.rotationYaw)); }
	
	public void setPartPosition(PartEntity<EntityWagon> part, Vector3d vec)
	{
		this.setPartPosition(part, vec.x, vec.y, vec.z);
	}
	
	public void setPartPosition(PartEntity<EntityWagon> part, double x, double y, double z)
	{
		part.setPosition(getPosX() + x, getPosY() + y, getPosZ() + z);
	}
	
	@Override
	public EntitySize getSize(Pose pose)
	{
		return hasWheels() ? SIZE_WHEELS : SIZE_CHASSIS;
	}
	
	public void recalculateSize()
	{
		super.recalculateSize();
	}
	
	public void move(MoverType typeIn, Vector3d pos)
	{
		if(typeIn == MoverType.SELF && !hasWheels()) return;
		super.move(typeIn, pos);
		
		// Move entities on top of this entity
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().grow(0D, 1D, 0D));
		if(!list.isEmpty())
			for(Entity entity : list)
				if(!entity.noClip && !this.isPassenger(entity))
					entity.move(typeIn, pos);
	}
	
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(source == DamageSource.DROWN || source == DamageSource.IN_WALL || source == DamageSource.STARVE)
			return false;
		
		return super.attackEntityFrom(source, amount);
	}
	
	protected boolean canFitPassenger(Entity passenger)
	{
		return getPassengers().size() < 3;
	}
	
	/** Returns the passenger in the driver seat, if any */
	@Nullable
	public Entity getDriver()
	{
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}
	
	public static EntityWagon getConnectedWagon(LivingEntity living)
	{
		EntityWagon attachedWagon = null;
		for(EntityWagon wagon : living.getEntityWorld().getEntitiesWithinAABB(EntityWagon.class, living.getBoundingBox().grow(32D), EntityWagon::getReined))
			if(wagon.getReinsHolder() == living)
				if(attachedWagon == null || wagon.getDistanceSq(living) < attachedWagon.getDistanceSq(living))
					attachedWagon = wagon;
		
		if(attachedWagon == null || !attachedWagon.isAlive())
			return null;
		
		return attachedWagon;
	}
	
	public boolean canBeSteered(){ return hasWheels() && getDriver() != null; }
	
	public void updatePassenger(Entity passenger)
	{
		if(isPassenger(passenger))
		{
			double height = hasWheels() ? 0.6D : 0.2D;
			int index = getPassengers().size() > 1 ? getPassengers().indexOf(passenger) : 0;
			
			Vector3d seatPos = getPositionVec();
			
			Vector3d forward = getForwardVec();
			Vector3d right = getForwardVec().rotateYaw((float)Math.toRadians(-90D));
			
			Vector3d toFront = new Vector3d(0D, height, 0D).add(forward.mul(1.1D, 0D, 1.1D));
			Vector3d toRear = new Vector3d(0D, height - 0.7D, 0D).add(forward.rotateYaw((float)(Math.toRadians(180D))).mul(2D, 0D, 2D));
			switch(index)
			{
				case 0:	// Middle of seat at front (rotation limited to within 90 yaw of wagon)
					seatPos = getPositionVec().add(toFront);
					break;
				case 1:	// Side of seat at front
					seatPos = getPositionVec().add(toFront).add(right.rotateYaw((float)Math.toRadians(180D)).mul(0.9D, 0D, 0.9D));
					break;
				case 2:	// Rear stoop
					seatPos = getPositionVec().add(toRear).add(right.mul(0.5D, 0D, 0.5D));
					break;
			}
			
			passenger.setPosition(seatPos.x, seatPos.y, seatPos.z);
		}
	}
	
	public Iterable<ItemStack> getArmorInventoryList() { return NonNullList.withSize(4, ItemStack.EMPTY); }
	
	public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) { return ItemStack.EMPTY; }
	
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) { }
	
	public HandSide getPrimaryHand(){ return HandSide.RIGHT; }
	
	protected void updateReinedState()
	{
		if(this.reinsNBTTag != null)
			recreateReins();
		
		if(this.reinsHolder != null)
			if(!this.isAlive() || !this.reinsHolder.isAlive())
				this.clearReined(true, true);
	}
	
	public void clearReined(boolean sendPacket, boolean dropLead)
	{
		if(this.reinsHolder != null)
		{
			this.forceSpawn = false;
			if(!(this.reinsHolder instanceof PlayerEntity))
				this.reinsHolder.forceSpawn = false;
			
			this.reinsHolder = null;
			this.reinsNBTTag = null;
			if(!this.world.isRemote && dropLead)
				this.entityDropItem(Items.LEAD);
			
			if(!this.world.isRemote && sendPacket && this.isServerWorld())
				PacketHandler.sendToNearby(getEntityWorld(), this, new PacketWagonReins(this, (Entity)null));
		}
	}
	
	public boolean canBeReinedTo(PlayerEntity player)
	{
		return !this.getReined();
	}
	
	public boolean getReined(){ return this.reinsHolder != null; }
	
	@Nullable
	public Entity getReinsHolder()
	{
		if(this.reinsHolder == null && this.reinsHolderID != 0 && this.world.isRemote)
			this.reinsHolder = this.world.getEntityByID(this.reinsHolderID);
		return this.reinsHolder;
	}
	
	public void setReinsHolder(Entity entityIn, boolean sendAttachNotification)
	{
		this.reinsHolder = entityIn;
		this.reinsNBTTag = null;
		this.forceSpawn = true;
		if(!(this.reinsHolder instanceof PlayerEntity))
			this.reinsHolder.forceSpawn = true;
		
		if(!this.world.isRemote && sendAttachNotification && this.isServerWorld())
			PacketHandler.sendToNearby(getEntityWorld(), this, new PacketWagonReins(this, this.reinsHolder));
		
		if(this.isPassenger())
			this.stopRiding();
	}
	
	private void recreateReins()
	{
		if(this.reinsNBTTag != null && this.isServerWorld())
		{
			if(this.reinsNBTTag.hasUniqueId("UUID"))
			{
				UUID uuid = this.reinsNBTTag.getUniqueId("UUID");
				Entity entity = ((ServerWorld)this.world).getEntityByUuid(uuid);
				if(entity != null)
				{
					this.setReinsHolder(entity, true);
					return;
				}
			}
			
			if(this.ticksExisted > 100)
			{
				this.entityDropItem(Items.LEAD);
				this.reinsNBTTag = null;
			}
		}
	}
	
	private static class WagonPartEntity extends PartEntity<EntityWagon>
	{
		public final EntityWagon wagon;
		public final PartType type;
		public final int slot;
		private EntitySize size;
		
		public WagonPartEntity(EntityWagon parent, PartType typeIn, int slotIn, float widthIn, float heightIn) {
			super(parent);
			setSize(widthIn, heightIn);
			this.wagon = parent;
			this.type = typeIn;
			this.slot = slotIn;
		}
		
		protected void registerData() { }
		
		protected void readAdditional(CompoundNBT compound){ }
		
		protected void writeAdditional(CompoundNBT compound){ }
		
		public IPacket<?> createSpawnPacket() { throw new UnsupportedOperationException(); }
		
		public boolean hasInventory(){ return this.slot >= 0; }
		
		public EntitySize getSize(Pose poseIn){ return this.size; }
		
		public boolean isEntityEqual(Entity entityIn) { return this == entityIn || entityIn == this.wagon; }
		
		public void setSize(float widthIn, float heightIn)
		{
			this.size = EntitySize.fixed(widthIn, heightIn);
			this.recalculateSize();
		}
		
		public boolean canBeCollidedWith(){ return this.type == PartType.CHASSIS; }
		
		public boolean func_241845_aY(){ return this.type == PartType.CHASSIS; }
		
		public boolean canCollide(Entity entity)
		{
			return this.type == PartType.CHASSIS && (entity.func_241845_aY() || entity.canBePushed()) && !(this.wagon.isRidingSameEntity(entity) || this.wagon.isPassenger(entity));
		}
		
		public void applyEntityCollision(Entity entityIn){ }
	}
	
	private enum PartType
	{
		WHEEL(new Predicate<ItemStack>()
		{
			public boolean apply(ItemStack stack){ return stack.getItem() instanceof ItemWheel; } 
		}),
		CANOPY(new Predicate<ItemStack>()
		{
			public boolean apply(ItemStack stack){ return false; } 
		}),
		CHASSIS(new Predicate<ItemStack>()
		{
			public boolean apply(ItemStack stack){ return stack.getItem() instanceof ItemChassis; } 
		}),
		REINS(new Predicate<ItemStack>()
		{
			public boolean apply(ItemStack stack){ return stack.getItem() == Items.LEAD; }
		});
		
		public final Predicate<ItemStack> predicate;
		
		private PartType(Predicate<ItemStack> itemPredicateIn)
		{
			this.predicate = itemPredicateIn;
		}
	}
}
