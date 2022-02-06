package com.lying.variousequipment.item.bauble;

import javax.annotation.Nullable;

import com.lying.variousequipment.client.model.bauble.ModelWingsButterfly;
import com.lying.variousequipment.client.model.bauble.ModelWingsDragon;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;

public abstract class ItemWings extends ItemCosmetic implements IDyeableArmorItem
{
	@Nullable
	protected Object model = null;
	
	public ItemWings(Properties properties)
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
	
	public int getDefaultColor(){ return DyeColor.WHITE.getColorValue(); }
	
	@OnlyIn(Dist.CLIENT)
	public abstract BipedModel<LivingEntity> getModel();
	
	@OnlyIn(Dist.CLIENT)
	public abstract ResourceLocation getTexture(ItemStack stack);
	
	@SuppressWarnings("unchecked")
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(this.model == null)
			this.model = getModel();
		
		if(this.model == null)
			return;
		
		BipedModel<LivingEntity> wings = ((BipedModel<LivingEntity>)this.model); 
		ICurio.RenderHelper.followBodyRotations(living, wings);
		wings.setLivingAnimations(living, limbSwing, limbSwingAmount, partialTicks);
		wings.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, wings.getRenderType(getTexture(stack)), false, stack.hasEffect());
		
        int i = getColor(stack);
        float r = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float b = (float)(i & 255) / 255.0F;
        
		wings.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
	}
	
	public static abstract class ItemWingsOverlay extends ItemWings
	{
		public ItemWingsOverlay(Properties properties)
		{
			super(properties);
		}
		
		@SuppressWarnings("unchecked")
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(this.model == null)
				this.model = getModel();
			BipedModel<LivingEntity> wings = ((BipedModel<LivingEntity>)this.model);
			super.render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
			IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, wings.getRenderType(getOverlayTexture(stack)), false, stack.hasEffect());
			wings.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
		}
		
		@OnlyIn(Dist.CLIENT)
		public abstract ResourceLocation getOverlayTexture(ItemStack stack);
	}
	
	/*
	 * Simple (flat shapes or simple animation)
		 * Dragonfly wings
		 * Changeling wings
		 * Elemental wings (glow-in-the-dark)
		 * Ladybug/beetle wings
		 * Clockwork/steampunk
	 * 
	 * Complex (complex shapes, possibly multiple models)
		 * Dragon wings
		 * Feathered wings
		 * Angel wings (4-6 feathered?)
		 * Bat wings
		 * Gargoyle:tm: wings
		 * Demon wings
		 * Witch wings
		 * Phoenix wings (glow-in-the-dark)
	 */
	
	public static class Butterfly extends ItemWingsOverlay
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/wings_butterfly_monarch.png");
		private static final ResourceLocation TEXTURE2 = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/wings_butterfly_monarch_overlay.png");
		
		public Butterfly(Properties properties){ super(properties); }
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelWingsButterfly(); }
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getOverlayTexture(ItemStack stack){ return TEXTURE2; }
		
		public int getDefaultColor(){ return 15413005; }
	}
	
	public static class Dragon extends ItemWings
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/wings_dragon.png");
		
		public Dragon(Properties properties){ super(properties); }
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelWingsDragon(); }
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	}
}
