package com.lying.variousequipment.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemShortbow extends BowItem
{
	public ItemShortbow(Properties builder)
	{
		super(builder);
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
	{
		if(entityLiving instanceof PlayerEntity)
		{
			PlayerEntity thePlayer = (PlayerEntity)entityLiving;
			boolean useAmmo = thePlayer.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack ammoStack = thePlayer.findAmmo(stack);
			
			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, thePlayer, i, !ammoStack.isEmpty() || useAmmo);
			if (i < 0) return;
			
			if(!ammoStack.isEmpty() || useAmmo)
			{
				if(ammoStack.isEmpty())
					ammoStack = new ItemStack(Items.ARROW);
				
				float velocity = getArrowVelocity(i);
				if(!((double)velocity < 0.1D))
				{
					boolean cannotPickup = thePlayer.abilities.isCreativeMode || (ammoStack.getItem() instanceof ArrowItem && ((ArrowItem)ammoStack.getItem()).isInfinite(ammoStack, stack, thePlayer));
					if (!worldIn.isRemote)
					{
						ArrowItem arrowitem = (ArrowItem)(ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
						AbstractArrowEntity projectile = arrowitem.createArrow(worldIn, ammoStack, thePlayer);
						projectile = customArrow(projectile);
						projectile.func_234612_a_(thePlayer, thePlayer.rotationPitch, thePlayer.rotationYaw, 0.0F, velocity * 3.0F, 1.0F);
						
						int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
						if(power > 0)
							projectile.setDamage(projectile.getDamage() + (double)power * 0.5D + 0.5D);
						
						int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
						if(punch > 0)
							projectile.setKnockbackStrength(punch);
						
						if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
							projectile.setFire(100);
						
						stack.damageItem(1, thePlayer, (player) -> { player.sendBreakAnimation(thePlayer.getActiveHand()); });
						if(cannotPickup || thePlayer.abilities.isCreativeMode && (ammoStack.getItem() == Items.SPECTRAL_ARROW || ammoStack.getItem() == Items.TIPPED_ARROW))
							projectile.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
						
						worldIn.addEntity(projectile);
					}
					
					worldIn.playSound((PlayerEntity)null, thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
					if(!cannotPickup && !thePlayer.abilities.isCreativeMode)
					{
						ammoStack.shrink(1);
						if(ammoStack.isEmpty())
							thePlayer.inventory.deleteStack(ammoStack);
					}
					
					thePlayer.addStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
	
	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge)
	{
		float vol = (float)charge / 10F;
		vol = (vol * vol + vol * 2F) / 3F;
		if(vol > 0.5F)
			vol = 0.5F;
		
		return vol;
	}
}
