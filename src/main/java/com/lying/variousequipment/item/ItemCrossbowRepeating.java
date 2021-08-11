package com.lying.variousequipment.item;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemCrossbowRepeating extends CrossbowItem
{
	public ItemCrossbowRepeating(Properties builder)
	{
		super(builder.maxStackSize(1).maxDamage(326));
	}
	
	public Predicate<ItemStack> getInventoryAmmoPredicate()
	{
		return ARROWS_OR_FIREWORKS;
	}
	
	public int func_230305_d_(){ return 6; }
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(hasAmmo(itemstack))
		{
			fireProjectiles(worldIn, playerIn, handIn, itemstack, playerIn.abilities.isCreativeMode, 1.0F);
			
			int cool = Reference.Values.TICKS_PER_SECOND;
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, itemstack);
			if(i > 0)
				cool -= i * 3;
			playerIn.getCooldownTracker().setCooldown(this, cool);
			
			return ActionResult.resultConsume(itemstack);
		}
		
		return ActionResult.resultFail(itemstack);
	}
	
	private static void fireProjectiles(World worldIn, LivingEntity shooter, Hand handIn, ItemStack crossbow, boolean isCreativeMode, float inaccuracy)
	{
		ItemStack projectile = getNextShot(crossbow);
		float velocity = isNextShotFirework(crossbow) ? 1.6F : 3.15F;
		float[] soundPitches = getRandomSoundPitches(shooter.getRNG());
		fireProjectile(worldIn, shooter, handIn, crossbow, projectile, soundPitches[0], isCreativeMode, velocity, 1.0F, 0F);
		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, crossbow) > 0)
		{
			fireProjectile(worldIn, shooter, handIn, crossbow, projectile, soundPitches[1], isCreativeMode, velocity, 1.0F, -10F);
			fireProjectile(worldIn, shooter, handIn, crossbow, projectile, soundPitches[2], isCreativeMode, velocity, 1.0F, 10F);
		}
		removeNextShot(crossbow);
	}
	
	private static void fireProjectile(World worldIn, LivingEntity shooter, Hand handIn, ItemStack crossbow, ItemStack ammo, float soundPitch, 
			boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle)
	{
		if(!worldIn.isRemote)
		{
			boolean isRocket = ammo.getItem() == Items.FIREWORK_ROCKET;
			ProjectileEntity projectile;
			if(isRocket)
				projectile = new FireworkRocketEntity(worldIn, ammo, shooter, shooter.getPosX(), shooter.getPosYEye() - (double)0.15F, shooter.getPosZ(), true);
			else
			{
				projectile = createArrow(worldIn, shooter, crossbow, ammo);
				if(isCreativeMode || projectileAngle != 0.0F)
					((AbstractArrowEntity)projectile).pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
			}
			
			if(shooter instanceof ICrossbowUser)
			{
				ICrossbowUser archer = (ICrossbowUser)shooter;
				archer.func_230284_a_(archer.getAttackTarget(), crossbow, projectile, projectileAngle);
			}
			else
			{
				Vector3f lookVec = new Vector3f(shooter.getLook(1.0F));
				lookVec.transform(new Quaternion(new Vector3f(shooter.getUpVector(1.0F)), projectileAngle, true));
				projectile.shoot((double)lookVec.getX(), (double)lookVec.getY(), (double)lookVec.getZ(), velocity, inaccuracy);
			}
			
			crossbow.damageItem(isRocket ? 3 : 1, shooter, (player) -> { player.sendBreakAnimation(handIn); });
			worldIn.addEntity(projectile);
			worldIn.playSound((PlayerEntity)null, shooter.getPosX(), shooter.getPosY(), shooter.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
		}
	}
	
	private static AbstractArrowEntity createArrow(World worldIn, LivingEntity shooter, ItemStack crossbow, ItemStack ammo)
	{
		ArrowItem ammoItem = (ArrowItem)(ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
		AbstractArrowEntity projectile = ammoItem.createArrow(worldIn, ammo, shooter);
		if(shooter.getType() == EntityType.PLAYER)
			projectile.setIsCritical(true);
		
		projectile.setHitSound(SoundEvents.ITEM_CROSSBOW_HIT);
		projectile.setShotFromCrossbow(true);
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, crossbow);
		if (i > 0)
			projectile.setPierceLevel((byte)i);
		
		return projectile;
	}
	
	private static float getRandomSoundPitch(boolean flagIn)
	{
		float f = flagIn ? 0.63F : 0.43F;
		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
	}
	
	protected static float[] getRandomSoundPitches(Random rand)
	{
		boolean flag = rand.nextBoolean();
		return new float[]{1.0F, getRandomSoundPitch(flag), getRandomSoundPitch(!flag)};
	}
	
	public boolean isCrossbow(ItemStack stack){ return true; }
	
	public static boolean hasAmmo(ItemStack stack)
	{
		return !getAmmo(stack).isEmpty();
	}
	
	protected static ItemStack getNextShot(ItemStack stack)
	{
		return hasAmmo(stack) ? getAmmo(stack).get(0) : ItemStack.EMPTY;
	}
	
	public static void addShot(ItemStack stack, ItemStack crossbow)
	{
		if(crossbow.getItem() == VEItems.REPEATING_CROSSBOW)
		{
			CompoundNBT stackData = crossbow.hasTag() ? crossbow.getTag() : new CompoundNBT();
			ListNBT ammoList = stackData.contains("Projectiles", 9) ? stackData.getList("Projectiles", 10) : new ListNBT();
			ammoList.add(stack.write(new CompoundNBT()));
			stackData.put("Projectiles", ammoList);
			crossbow.setTag(stackData);
		}
	}
	
	protected static void removeNextShot(ItemStack stack)
	{
		if(stack.getItem() == VEItems.REPEATING_CROSSBOW)
		{
			CompoundNBT stackData = stack.getTag();
			if(stackData != null && stackData.contains("Projectiles", 9))
			{
				ListNBT ammoList = stackData.getList("Projectiles", 10);
				if(ammoList.size() > 1)
				{
					ListNBT ammoList2 = new ListNBT();
					for(int i=1; i<ammoList.size(); ++i)
						ammoList2.add(ammoList.get(i));
					
					stackData.put("Projectiles", ammoList2);
				}
				else
					stackData.remove("Projectiles");
				
				stack.setTag(stackData);
			}
		}
	}
	
	protected static List<ItemStack> getAmmo(ItemStack stack)
	{
		List<ItemStack> projectiles = Lists.newArrayList();
		CompoundNBT stackData = stack.getTag();
		if(stackData != null && stackData.contains("Projectiles", 9))
		{
			ListNBT ammoList = stackData.getList("Projectiles", 10);
			if(ammoList != null)
				for(int i = 0; i < ammoList.size(); ++i)
					projectiles.add(ItemStack.read(ammoList.getCompound(i)));
		}
		return projectiles;
	}
	
	public static int remainingCapacity(ItemStack stack)
	{
		if(stack.getItem() == VEItems.REPEATING_CROSSBOW)
			return 5 - getAmmo(stack).size();
		return -1;
	}
	
	public static boolean isNextShotFirework(ItemStack stack)
	{
		return stack.getItem() == VEItems.REPEATING_CROSSBOW && hasAmmo(stack) && getNextShot(stack).getItem() == Items.FIREWORK_ROCKET;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(hasAmmo(stack))
		{
			List<ItemStack> ammo = getAmmo(stack);
			ItemStack nextShot = ammo.get(0);
			tooltip.add((new TranslationTextComponent("item.minecraft.crossbow.projectile")).appendString(" ").append(nextShot.getTextComponent()));
			if(flagIn.isAdvanced() && nextShot.getItem() == Items.FIREWORK_ROCKET)
			{
				List<ITextComponent> list1 = Lists.newArrayList();
				Items.FIREWORK_ROCKET.addInformation(nextShot, worldIn, list1, flagIn);
				if (!list1.isEmpty())
				{
					for(int i = 0; i < list1.size(); ++i)
						list1.set(i, (new StringTextComponent("  ")).append(list1.get(i)).mergeStyle(TextFormatting.GRAY));
			
					tooltip.addAll(list1);
				}
			}
			
			if(ammo.size() > 1)
				for(int i=1; i<ammo.size(); ++i)
					tooltip.add(new StringTextComponent(" ").append(ammo.get(i).getTextComponent()));
		}
	}
}
