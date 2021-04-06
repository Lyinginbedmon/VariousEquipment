package com.lying.variousequipment.item.bauble;

import com.lying.variousequipment.client.model.bauble.ModelHornsDeer;
import com.lying.variousequipment.client.model.bauble.ModelHornsRam;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class ItemHorns extends Item implements ICurioItem
{
	public ItemHorns(Properties properties)
	{
		super(properties.maxStackSize(1));
	}
	
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack){ return true; }
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return true; }
	
	@OnlyIn(Dist.CLIENT)
	public abstract void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack);
	
	public static class Deer extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/baubles/horns_deer.png");
		
		@OnlyIn(Dist.CLIENT)
		private Object model;
		
		public Deer(Properties properties)
		{
			super(properties);
		}
		
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(!(this.model instanceof ModelHornsDeer))
				this.model = new ModelHornsDeer<>();
			
			ModelHornsDeer<?> horns = (ModelHornsDeer<?>)this.model;
			ICurio.RenderHelper.followHeadRotations(living, horns.horns);
			IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, horns.getRenderType(TEXTURE), false, stack.hasEffect());
			horns.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
		}
	}
	
	public static class Ram extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/baubles/horns_ram.png");
		
		@OnlyIn(Dist.CLIENT)
		private Object model;
		
		public Ram(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
				float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(!(this.model instanceof ModelHornsRam))
				this.model = new ModelHornsRam<>();
			
			ModelHornsRam<?> horns = (ModelHornsRam<?>)this.model;
			ICurio.RenderHelper.followHeadRotations(living, horns.horns);
			IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, horns.getRenderType(TEXTURE), false, stack.hasEffect());
			horns.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
		}
	}
	
	/** Combo of deer antlers and ram horns */
	public static class Kirin extends ItemHorns
	{
		public Kirin(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
				float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			((ItemHorns.Deer)VEItems.HORNS_DEER).render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
			((ItemHorns.Ram)VEItems.HORNS_RAM).render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
		}
	}
}
