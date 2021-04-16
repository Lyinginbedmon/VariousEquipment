package com.lying.variousequipment.item.bauble;

import javax.annotation.Nonnull;

import com.lying.variousequipment.client.model.bauble.ModelHornsDeer;
import com.lying.variousequipment.client.model.bauble.ModelHornsHartebeest;
import com.lying.variousequipment.client.model.bauble.ModelHornsRam;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;

public abstract class ItemHorns extends ItemBauble implements IDyeableArmorItem
{
	public ItemHorns(Properties properties)
	{
		super(properties.maxStackSize(1).maxDamage(0));
	}
	
	public int getColor(ItemStack stack)
	{
		CompoundNBT stackData = stack.getChildTag("display");
		return stackData != null && stackData.contains("color", 99) ? stackData.getInt("color") : DyeColor.WHITE.getColorValue();
	}
	
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack){ return true; }
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return !living.isInvisible(); }
	
	@Nonnull
	public DropRule getDropRule(LivingEntity livingEntity, ItemStack stack){ return DropRule.ALWAYS_KEEP; }
	
	@OnlyIn(Dist.CLIENT)
	public abstract void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack);
	
	public static class Deer extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/horns_deer.png");
		
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
			
            int i = ((net.minecraft.item.IDyeableArmorItem)VEItems.HORNS_DEER).getColor(stack);
            float r = (float)(i >> 16 & 255) / 255.0F;
            float g = (float)(i >> 8 & 255) / 255.0F;
            float b = (float)(i & 255) / 255.0F;
            
			horns.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
		}
	}
	
	public static class Ram extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/horns_ram.png");
		
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
			
            int i = ((net.minecraft.item.IDyeableArmorItem)VEItems.HORNS_RAM).getColor(stack);
            float r = (float)(i >> 16 & 255) / 255.0F;
            float g = (float)(i >> 8 & 255) / 255.0F;
            float b = (float)(i & 255) / 255.0F;
            
			horns.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
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
	
	public static class Hartebeest extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/horns_hartebeest.png");
		
		@OnlyIn(Dist.CLIENT)
		private Object model;
		
		public Hartebeest(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
				float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(!(this.model instanceof ModelHornsHartebeest))
				this.model = new ModelHornsHartebeest<>();
			
			ModelHornsHartebeest<?> horns = (ModelHornsHartebeest<?>)this.model;
			ICurio.RenderHelper.followHeadRotations(living, horns.horns);
			IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, horns.getRenderType(TEXTURE), false, stack.hasEffect());
			
            int i = ((net.minecraft.item.IDyeableArmorItem)VEItems.HORNS_RAM).getColor(stack);
            float r = (float)(i >> 16 & 255) / 255.0F;
            float g = (float)(i >> 8 & 255) / 255.0F;
            float b = (float)(i & 255) / 255.0F;
            
			horns.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
		}
	}
}
