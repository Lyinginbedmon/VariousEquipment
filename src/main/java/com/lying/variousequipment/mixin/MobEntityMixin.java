package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lying.variousequipment.api.IShieldBreakItem;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(MobEntity.class)
public class MobEntityMixin
{
	@Inject(method = "func_233655_a_(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
	public void applyShieldBreak(PlayerEntity player, ItemStack hitByItem, ItemStack heldItem, CallbackInfo ci)
	{
		MobEntity mob = (MobEntity)(Object)this;
		if(!hitByItem.isEmpty() && !heldItem.isEmpty() && hitByItem.getItem() instanceof IShieldBreakItem && ((IShieldBreakItem)hitByItem.getItem()).isShield(heldItem))
		{
			float f = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(mob) * 0.05F;
			if(mob.getRNG().nextFloat() < f)
			{
				player.getCooldownTracker().setCooldown(Items.SHIELD, ((IShieldBreakItem)hitByItem.getItem()).breakTime(hitByItem));
				mob.world.setEntityState(player, (byte)30);
			}
		}
	}
}
