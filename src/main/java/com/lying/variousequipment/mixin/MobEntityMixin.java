package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lying.variousequipment.api.IShieldBreakItem;
import com.lying.variousequipment.init.VEBlocks;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

@Mixin(MobEntity.class)
public class MobEntityMixin extends LivingEntityMixin
{
	private int timeUntilNextGuano = getRNG().nextInt(6000) + 6000;
	
	@Inject(method = "livingTick()V", at = @At("RETURN"), cancellable = false)
	public void livingTick(final CallbackInfo ci)
	{
		if(getType() != EntityType.BAT) return;
		
		BatEntity bat = (BatEntity)(Object)this;
		World world = bat.getEntityWorld();
		if(!world.isRemote && --timeUntilNextGuano <= 0)
		{
			bat.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (getRNG().nextFloat() - getRNG().nextFloat()) * 0.2F + 1.0F);
	        bat.entityDropItem(VEBlocks.GUANO);
	        this.timeUntilNextGuano = getRNG().nextInt(6000) + 6000;
		}
	}
	
	@Inject(method = "func_233655_a_(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
	private void applyShieldBreak(PlayerEntity player, ItemStack hitByItem, ItemStack heldItem, final CallbackInfo ci)
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
