package com.lying.variousequipment.item;

import java.util.function.Predicate;

import com.lying.variousequipment.api.ISightedItem;
import com.lying.variousequipment.entity.EntityNeedle;
import com.lying.variousequipment.init.VEItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemBlowpipe extends BowItem implements ISightedItem, IVanishable
{
	private static final Predicate<ItemStack> AMMO_INV = new Predicate<ItemStack>()
			{
				public boolean test(ItemStack stack)
				{
					return stack.getItem() instanceof ItemNeedle;
				}
			};
	
	public ItemBlowpipe(Properties properties)
	{
		super(properties.group(VEItemGroup.GEAR).maxDamage(384));
	}
	
	public boolean aimDownSights(ItemStack itemStackIn)
	{
		return true;
	}
	
	public int getDrawTime(ItemStack itemStackIn)
	{
		return 15;
	}
	
	public UseAction getUseAction(ItemStack stack){ return UseAction.CROSSBOW; }
	
	public Predicate<ItemStack> getInventoryAmmoPredicate()
	{
		return AMMO_INV;
	}
	
	public int func_230305_d_()
	{
		return 10;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
	{
		if(entityLiving instanceof PlayerEntity)
		{
			PlayerEntity playerentity = (PlayerEntity)entityLiving;
			boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			ItemStack itemstack = playerentity.findAmmo(stack);
			
			int i = this.getUseDuration(stack) - timeLeft;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
			if (i < 0) return;
			
			if (!itemstack.isEmpty() || flag)
			{
				if(itemstack.isEmpty())
					itemstack = new ItemStack(VEItems.NEEDLE_IRON);
				
				float f = getArrowVelocity(i);
				if(!((double)f < 0.1D))
				{
					boolean cannotPickup = playerentity.abilities.isCreativeMode;
					if(!worldIn.isRemote)
					{
						ItemNeedle needle = (ItemNeedle)itemstack.getItem();
						EntityNeedle needleEntity = new EntityNeedle(worldIn, playerentity);
						needleEntity.setItem(itemstack);
						needleEntity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0F, f * 3F, 1F);
						needle.addEffects(needleEntity, playerentity);
						stack.damageItem(1, playerentity, (living) -> {
							living.sendBreakAnimation(playerentity.getActiveHand());
						});
						
						worldIn.addEntity(needleEntity);
					}
					
					worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if(!cannotPickup && !playerentity.abilities.isCreativeMode)
					{
						itemstack.shrink(1);
						if(itemstack.isEmpty())
							playerentity.inventory.deleteStack(itemstack);
					}
				
					playerentity.addStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}
	
	public int getItemEnchantability(){ return 1; }
	
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		return 
				enchantment == Enchantments.UNBREAKING ||
				enchantment == Enchantments.MENDING ||
				enchantment == Enchantments.INFINITY;
	}
}
