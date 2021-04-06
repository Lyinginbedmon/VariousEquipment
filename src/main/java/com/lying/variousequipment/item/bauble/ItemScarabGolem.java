package com.lying.variousequipment.item.bauble;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ItemScarabGolem extends Item implements ICurioItem
{
	public ItemScarabGolem(Properties properties)
	{
		super(properties.maxStackSize(1));
	}
	
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack){ return true; }
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return true; }
	
	@OnlyIn(Dist.CLIENT)
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		ICurio.RenderHelper.translateIfSneaking(matrixStackIn, living);
		ICurio.RenderHelper.rotateIfSneaking(matrixStackIn, living);
		matrixStackIn.push();
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180F));
			matrixStackIn.translate(0D, -0.2D, living.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty() ? 0.15D : 0.2D);
			matrixStackIn.push();
				matrixStackIn.scale(0.75F, 0.75F, 0.75F);
				ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
				itemRender.renderItem(stack, TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrixStackIn, renderTypeBuffer);
			matrixStackIn.pop();
		matrixStackIn.pop();
	}
}
