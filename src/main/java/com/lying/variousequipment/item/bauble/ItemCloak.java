package com.lying.variousequipment.item.bauble;

import javax.annotation.Nullable;

import com.lying.variousequipment.client.model.bauble.ModelCloak;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemCloak extends ItemCosmetic implements IDyeableArmorItem
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/cloak.png");
	private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/cloak_overlay.png");
	
	@Nullable
	protected Object model = null;
	
	public ItemCloak(Properties properties)
	{
		super(properties);
	}
	
	public boolean hasColor(ItemStack stack)
	{
		return stack.getChildTag("display") != null && stack.getChildTag("display").contains("color", 99);
	}
	
	public int getColor(ItemStack stack)
	{
		return hasColor(stack) ? stack.getChildTag("display").getInt("color") : getDefaultColor();
	}
	
	public int getDefaultColor(){ return 10511680; }
	
	@OnlyIn(Dist.CLIENT)
	public BipedModel<LivingEntity> getModel(){ return new ModelCloak(); }
	
	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	
	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getOverlayTexture(ItemStack stack){ return TEXTURE_OVERLAY; }
	
	@SuppressWarnings("unchecked")
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(this.model == null)
			this.model = getModel();
		if(this.model == null)
			return;
		
		BipedModel<LivingEntity> cloak = ((BipedModel<LivingEntity>)this.model); 
		ICurio.RenderHelper.followBodyRotations(living, cloak);
		cloak.setLivingAnimations(living, limbSwing, limbSwingAmount, partialTicks);
		cloak.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, cloak.getRenderType(TEXTURE), false, stack.hasEffect());
		
        int i = getColor(stack);
        float r = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float b = (float)(i & 255) / 255.0F;
        
		cloak.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
		
		vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, cloak.getRenderType(getOverlayTexture(stack)), false, stack.hasEffect());
		cloak.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
	}
}
