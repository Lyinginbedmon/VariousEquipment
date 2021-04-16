package com.lying.variousequipment.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.lying.variousoddities.types.EnumCreatureType;
import com.lying.variousoddities.world.savedata.TypesManager;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemSymbol extends TieredItem
{
	private final IItemTier material;
	
	public static final Predicate<Entity> VALID_CREATURE = new Predicate<Entity>()
	{
		public boolean apply(Entity input)
		{
			if(input.isAlive() && input instanceof LivingEntity)
			{
				LivingEntity living = (LivingEntity)input;
				TypesManager manager = TypesManager.get(input.getEntityWorld());
				if(manager.hasCustomTypes(living))
				{
					for(EnumCreatureType type : manager.getMobTypes(living))
						if(type.hasParentAttribute() && type.getParentAttribute() == CreatureAttribute.UNDEAD)
							return true;
				}
				else
					return living.getCreatureAttribute() == CreatureAttribute.UNDEAD;
			}
			return false;
		}
	};
	
	public ItemSymbol(IItemTier itemTierIn, Properties properties)
	{
		super(itemTierIn, properties);
		this.material = itemTierIn;
	}
    
    public IItemTier getMaterial(){ return material; }
    public static IItemTier getMaterial(ItemStack stack){ return ((ItemSymbol)stack.getItem()).getMaterial(); }
	
	public static double getRadiusFromMaterial(IItemTier materialIn)
	{
		return Math.max(4F, materialIn.getEfficiency()) * 0.5D;
	}
	
	public static float getMaxHealthFromMaterial(IItemTier materialIn)
	{
		return Math.max(0.5F, materialIn.getAttackDamage() * 0.7F);
	}
	
	public static List<LivingEntity> selectValidUndead(LivingEntity user, IItemTier material)
	{
		float maxHealth = user.getMaxHealth() * getMaxHealthFromMaterial(material);
		double maxDist = getRadiusFromMaterial(material);
		
		List<LivingEntity> undead = new ArrayList<LivingEntity>();
		for(Entity ent : user.getEntityWorld().getEntitiesInAABBexcluding(user, user.getBoundingBox().grow(maxDist), VALID_CREATURE))
		{
			LivingEntity entityLiving = (LivingEntity)ent;
			if(entityLiving.getHealth() <= maxHealth) undead.add(entityLiving);
		}
		
		return undead;
	}
	
	public int getUseDuration(ItemStack stack){ return 72000; }
	public UseAction getUseAction(ItemStack stack){ return UseAction.BOW; }
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack heldStack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return ActionResult.resultConsume(heldStack);
	}
	
	public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count)
	{
		boolean canDamage = (livingEntityIn instanceof PlayerEntity ? (!((PlayerEntity)livingEntityIn).isCreative()) : true);
		Random rng = livingEntityIn.getRNG();
		
		Vector3d userPos = new Vector3d(livingEntityIn.getPosX(), livingEntityIn.getPosYEye(), livingEntityIn.getPosZ());
		double maxDist = getRadiusFromMaterial(material);
		
		for(LivingEntity mob : selectValidUndead(livingEntityIn, ((ItemSymbol)stack.getItem()).getMaterial()))
		{
			if(mob.getDistance(livingEntityIn) > maxDist) continue;
			
			Vector3d mobPos = new Vector3d(mob.getPosX(), mob.getPosYEye(), mob.getPosZ());
			Vector3d direction = mobPos.subtract(userPos).normalize();
			
			double moveX = direction.x * mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.0125F;
			double moveY = direction.y * mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.0125F;
			double moveZ = direction.z * mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.0125F;
			
			mob.setMotion(mob.getMotion().add(moveX, moveY, moveZ));
			
			if(canDamage && !worldIn.isRemote)
				stack.damageItem(rng.nextInt(20) == 0 ? 1 : 0, livingEntityIn, e -> e.sendBreakAnimation(livingEntityIn.getActiveHand()));
		}
	}
}
