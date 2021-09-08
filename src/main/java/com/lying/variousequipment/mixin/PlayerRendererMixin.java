package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.lying.variousequipment.api.ISightedItem;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel.ArmPose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin
{
	@Inject(method = "func_241741_a_(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/renderer/entity/model/BipedModel$ArmPose;", at = @At("HEAD"), cancellable = true)
	private static void getArmPose(AbstractClientPlayerEntity player, Hand hand, final CallbackInfoReturnable<ArmPose> ci)
	{
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getItem() instanceof ISightedItem)
			if(((ISightedItem)stack.getItem()).aimDownSights(stack))
				ci.setReturnValue(ArmPose.CROSSBOW_HOLD);
	}
}
