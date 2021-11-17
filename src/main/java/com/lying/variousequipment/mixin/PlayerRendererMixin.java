package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.lying.variousequipment.api.ISightedItem;
import com.lying.variousequipment.item.bauble.ILimbCosmetic;
import com.lying.variousequipment.item.bauble.ILimbCosmetic.LimbType;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel.ArmPose;
import net.minecraft.client.renderer.entity.model.PlayerModel;
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
	
	@Inject(method = "setModelVisibilities(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;)V", at = @At("RETURN"), cancellable = true)
	private void setModelVisibilities(AbstractClientPlayerEntity player, final CallbackInfo ci)
	{
		PlayerModel<AbstractClientPlayerEntity> model = ((PlayerRenderer)(Object)this).getEntityModel();
		for(LimbType type : LimbType.values())
			if(ILimbCosmetic.isWearingLimbsOfType(player, type))
				switch(type)
				{
					case ARM_LEFT:
						model.bipedLeftArm.showModel = false;
						model.bipedLeftArmwear.showModel = false;
						break;
					case ARM_RIGHT:
						model.bipedRightArm.showModel = false;
						model.bipedRightArmwear.showModel = false;
						break;
					case HEAD:
						model.bipedHead.showModel = false;
						model.bipedHeadwear.showModel = false;
						break;
					case LEG_LEFT:
						model.bipedLeftLeg.showModel = false;
						model.bipedLeftLegwear.showModel = false;
						break;
					case LEG_RIGHT:
						model.bipedRightLeg.showModel = false;
						model.bipedRightLegwear.showModel = false;
						break;
					case TORSO:
						model.bipedBody.showModel = false;
						model.bipedBodyWear.showModel = false;
						break;
				}
	}
}
