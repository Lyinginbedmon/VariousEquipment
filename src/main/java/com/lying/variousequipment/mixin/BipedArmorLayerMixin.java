package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lying.variousequipment.item.bauble.ILimbCosmetic;
import com.lying.variousequipment.item.bauble.ILimbCosmetic.LimbType;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

@Mixin(BipedArmorLayer.class)
public class BipedArmorLayerMixin
{
	private PlayerEntity renderingPlayer = null;
	
	@SuppressWarnings("rawtypes")
	@Inject(method = "func_241739_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V", at = @At("HEAD"), cancellable = true)
	public void renderArmourInSlot(MatrixStack p_241739_1_, IRenderTypeBuffer p_241739_2_, LivingEntity entityIn, EquipmentSlotType slotIn, int p_241739_5_, BipedModel p_241739_6_, final CallbackInfo ci)
	{
		if(entityIn.getType() == EntityType.PLAYER)
			renderingPlayer = (PlayerEntity)entityIn;
		else
			renderingPlayer = null;
	}
	
	@SuppressWarnings("rawtypes")
	@Inject(method = "setModelSlotVisible(Lnet/minecraft/client/renderer/entity/model/BipedModel;Lnet/minecraft/inventory/EquipmentSlotType;)V", at = @At("RETURN"))
	public void setModelSlotVisible(BipedModel modelIn, EquipmentSlotType slotIn, final CallbackInfo ci)
	{
		if(renderingPlayer != null)
			for(LimbType type : LimbType.values())
				if(ILimbCosmetic.hasConflictingLimbs(renderingPlayer, type, slotIn))
					switch(type)
					{
						case ARM_LEFT:
							modelIn.bipedLeftArm.showModel = false;
							break;
						case ARM_RIGHT:
							modelIn.bipedRightArm.showModel = false;
							break;
						case HEAD:
							modelIn.bipedHead.showModel = false;
							modelIn.bipedHeadwear.showModel = false;
							break;
						case LEG_LEFT:
							modelIn.bipedLeftLeg.showModel = false;
							break;
						case LEG_RIGHT:
							modelIn.bipedRightLeg.showModel = false;
							break;
						case TORSO:
							modelIn.bipedBody.showModel = false;
							break;
					}
	}
}
