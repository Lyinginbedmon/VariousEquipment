package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.MoreObjects;
import com.lying.variousequipment.api.ISightedItem;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(FirstPersonRenderer.class)
public abstract class FirstPersonRendererMixin
{
	private final Minecraft mc = Minecraft.getInstance();
	
	@Shadow
	private float prevEquippedProgressMainHand = 0f;
	@Shadow
	private float equippedProgressMainHand = 0f;
	@Shadow
	private ItemStack itemStackMainHand = ItemStack.EMPTY;
	@Shadow
	private float prevEquippedProgressOffHand = 0f;
	@Shadow
	private float equippedProgressOffHand = 0f;
	@Shadow
	private ItemStack itemStackOffHand = ItemStack.EMPTY;
	
	@Shadow
	private void transformSideFirstPerson(MatrixStack matrixStackIn, HandSide handIn, float equippedProg){ }
	
	@Shadow
	private void transformFirstPerson(MatrixStack matrixStackIn, HandSide handIn, float swingProgress){ }
	
	@Shadow
	public void renderItemSide(LivingEntity livingEntityIn, ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, boolean leftHand, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn){ }
	
	@Shadow
	private void renderItemInFirstPerson(AbstractClientPlayerEntity player, float partialTicks, float pitch, Hand handIn, float swingProgress, ItemStack stack, float equippedProgress, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn){ }
	
	@Inject(method = "renderItemInFirstPerson(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V", at = @At("HEAD"), cancellable = true)
	private void renderItemInFirstPerson(AbstractClientPlayerEntity player, float partialTicks, float pitch, Hand handIn, float swingProgress, ItemStack stack, float equippedProgress, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, final CallbackInfo ci)
	{
		boolean flag = handIn == Hand.MAIN_HAND;
	      HandSide handside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
	      matrixStackIn.push();
	      if(stack.getItem() instanceof ISightedItem)
	      {
	  		ci.cancel();
	         boolean flag1 = isSightedItem(stack);
	         boolean flag2 = handside == HandSide.RIGHT;
	         int i = flag2 ? 1 : -1;
	         if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == handIn)
	         {
	            this.transformSideFirstPerson(matrixStackIn, handside, equippedProgress);
	            matrixStackIn.translate((double)((float)i * -0.4785682F), (double)-0.094387F, (double)0.05731531F);
	            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-11.935F));
	            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)i * 65.3F));
	            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)i * -9.785F));
	            float f9 = (float)stack.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - partialTicks + 1.0F);
	            float f13 = f9 / (float)getChargeTime(stack);
	            if (f13 > 1.0F)
	               f13 = 1.0F;
	            
	            if (f13 > 0.1F)
	            {
	               float f16 = MathHelper.sin((f9 - 0.1F) * 1.3F);
	               float f3 = f13 - 0.1F;
	               float f4 = f16 * f3;
	               matrixStackIn.translate((double)(f4 * 0.0F), (double)(f4 * 0.004F), (double)(f4 * 0.0F));
	            }
	            
	            matrixStackIn.translate((double)(f13 * 0.0F), (double)(f13 * 0.0F), (double)(f13 * 0.04F));
	            matrixStackIn.scale(1.0F, 1.0F, 1.0F + f13 * 0.2F);
	            matrixStackIn.rotate(Vector3f.YN.rotationDegrees((float)i * 45.0F));
	         }
	         else
	         {
	            float f = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
	            float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2F));
	            float f2 = -0.2F * MathHelper.sin(swingProgress * (float)Math.PI);
	            matrixStackIn.translate((double)((float)i * f), (double)f1, (double)f2);
	            this.transformSideFirstPerson(matrixStackIn, handside, equippedProgress);
	            this.transformFirstPerson(matrixStackIn, handside, swingProgress);
	            if (flag1 && swingProgress < 0.001F)
	            {
	               matrixStackIn.translate((double)((float)i * -0.641864F), 0.0D, 0.0D);
	               matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)i * 10.0F));
	            }
	         }
	         this.renderItemSide(player, stack, flag2 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag2, matrixStackIn, bufferIn, combinedLightIn);
	      }
	      
	      matrixStackIn.pop();
	}
	
	@Inject(method = "renderItemInFirstPerson(FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer$Impl;Lnet/minecraft/client/entity/player/ClientPlayerEntity;I)V", at = @At("HEAD"), cancellable = true)
	public void renderItemInFirstPerson(float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer.Impl bufferIn, ClientPlayerEntity playerEntityIn, int combinedLightIn, final CallbackInfo ci)
	{
		float f = playerEntityIn.getSwingProgress(partialTicks);
	      Hand hand = MoreObjects.firstNonNull(playerEntityIn.swingingHand, Hand.MAIN_HAND);
	      float f1 = MathHelper.lerp(partialTicks, playerEntityIn.prevRotationPitch, playerEntityIn.rotationPitch);
	      boolean renderMain = true;
	      boolean renderOff = true;
	      boolean shouldOverride = false;
	      if (playerEntityIn.isHandActive())
	      {
	         ItemStack activeItem = playerEntityIn.getActiveItemStack();
	         if (activeItem.getItem() instanceof net.minecraft.item.ShootableItem)
	         {
	            renderMain = playerEntityIn.getActiveHand() == Hand.MAIN_HAND;
	            renderOff = !renderMain;
	         }

	         Hand activeHand = playerEntityIn.getActiveHand();
	         if (activeHand == Hand.MAIN_HAND)
	         {
	            ItemStack itemstack1 = playerEntityIn.getHeldItemOffhand();
	            if (isSightedItem(itemstack1))
	            {
	               renderOff = false;
	               shouldOverride = true;
	            }
	         }
	      }
	      else
	      {
	         ItemStack heldMain = playerEntityIn.getHeldItemMainhand();
	         ItemStack heldOff = playerEntityIn.getHeldItemOffhand();
	         if (isSightedItem(heldMain))
	         {
	            renderOff = !renderMain;
	               shouldOverride = true;
	         }
	         
	         if (isSightedItem(heldOff))
	         {
	            renderMain = !heldMain.isEmpty();
	            renderOff = !renderMain;
	            shouldOverride = true;
	         }
	      }
	      if(!shouldOverride) return;
	      else ci.cancel();
	      
	      float f3 = MathHelper.lerp(partialTicks, playerEntityIn.prevRenderArmPitch, playerEntityIn.renderArmPitch);
	      float f4 = MathHelper.lerp(partialTicks, playerEntityIn.prevRenderArmYaw, playerEntityIn.renderArmYaw);
	      matrixStackIn.rotate(Vector3f.XP.rotationDegrees((playerEntityIn.getPitch(partialTicks) - f3) * 0.1F));
	      matrixStackIn.rotate(Vector3f.YP.rotationDegrees((playerEntityIn.getYaw(partialTicks) - f4) * 0.1F));
	      if(renderMain)
	      {
	         float f5 = hand == Hand.MAIN_HAND ? f : 0.0F;
	         float f2 = 1.0F - MathHelper.lerp(partialTicks, this.prevEquippedProgressMainHand, this.equippedProgressMainHand);
	         if(!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonHand(Hand.MAIN_HAND, matrixStackIn, bufferIn, combinedLightIn, partialTicks, f1, f5, f2, this.itemStackMainHand))
	         this.renderItemInFirstPerson(playerEntityIn, partialTicks, f1, Hand.MAIN_HAND, f5, this.itemStackMainHand, f2, matrixStackIn, bufferIn, combinedLightIn);
	      }
	      
	      if(renderOff)
	      {
	         float f6 = hand == Hand.OFF_HAND ? f : 0.0F;
	         float f7 = 1.0F - MathHelper.lerp(partialTicks, this.prevEquippedProgressOffHand, this.equippedProgressOffHand);
	         if(!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonHand(Hand.OFF_HAND, matrixStackIn, bufferIn, combinedLightIn, partialTicks, f1, f6, f7, this.itemStackOffHand))
	         this.renderItemInFirstPerson(playerEntityIn, partialTicks, f1, Hand.OFF_HAND, f6, this.itemStackOffHand, f7, matrixStackIn, bufferIn, combinedLightIn);
	      }
	      
	      bufferIn.finish();
	}
	
	private static int getChargeTime(ItemStack itemStack)
	{
		return itemStack.getItem() instanceof ISightedItem ? ((ISightedItem)itemStack.getItem()).getDrawTime(itemStack) : 25;
	}
	
	private static boolean isSightedItem(ItemStack itemStack)
	{
		return itemStack.getItem() instanceof ISightedItem && ((ISightedItem)itemStack.getItem()).aimDownSights(itemStack);
	}
}
