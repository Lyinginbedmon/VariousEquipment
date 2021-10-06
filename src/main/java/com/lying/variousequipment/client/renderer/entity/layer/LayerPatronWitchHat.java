package com.lying.variousequipment.client.renderer.entity.layer;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousoddities.client.renderer.entity.EntityPatronWitchRenderer;
import com.lying.variousoddities.entity.wip.EntityPatronWitch;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerPatronWitchHat extends BipedArmorLayer<EntityPatronWitch, BipedModel<EntityPatronWitch>, BipedModel<EntityPatronWitch>>
{
	private final BipedModel<EntityPatronWitch> modelLeggings;
	private final BipedModel<EntityPatronWitch> modelArmor;
	
	public LayerPatronWitchHat(IEntityRenderer<EntityPatronWitch, BipedModel<EntityPatronWitch>> entityRendererIn)
	{
		this(entityRendererIn, new BipedModel<EntityPatronWitch>(0F), new BipedModel<EntityPatronWitch>(0.5F));
	}
	
	public LayerPatronWitchHat(IEntityRenderer<EntityPatronWitch, BipedModel<EntityPatronWitch>> entityRendererIn, BipedModel<EntityPatronWitch> chestModel, BipedModel<EntityPatronWitch> leggingsModel)
	{
		super(entityRendererIn, chestModel, leggingsModel);
		this.modelArmor = chestModel;
		this.modelLeggings = leggingsModel;
	}
	
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EntityPatronWitch entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.renderItemInSlot(matrixStackIn, bufferIn, entitylivingbaseIn, partialTicks, EquipmentSlotType.CHEST, packedLightIn, this.modelArmor);
		this.renderItemInSlot(matrixStackIn, bufferIn, entitylivingbaseIn, partialTicks, EquipmentSlotType.LEGS, packedLightIn, this.modelLeggings);
		this.renderItemInSlot(matrixStackIn, bufferIn, entitylivingbaseIn, partialTicks, EquipmentSlotType.FEET, packedLightIn, this.modelArmor);
		this.renderItemInSlot(matrixStackIn, bufferIn, entitylivingbaseIn, partialTicks, EquipmentSlotType.HEAD, packedLightIn, this.modelArmor);
	}
	
	protected void renderItemInSlot(MatrixStack matrixStack, IRenderTypeBuffer buffer, EntityPatronWitch witchIn, float partialTicks, EquipmentSlotType slot, int packedLight, BipedModel<EntityPatronWitch> model)
	{
		ItemStack itemstack = witchIn.getItemStackFromSlot(slot);
		if(itemstack.isEmpty())
			switch(EntityPatronWitchRenderer.getCurrentAppearance())
			{
				case ELF:
					itemstack = new ItemStack(VEItems.HAT_ARCHFEY);
					break;
				case HUMAN:
					itemstack = new ItemStack(VEItems.HAT_WITCH);

		    		int hatColor = DyeColor.LIGHT_BLUE.getColorValue();
		            if(witchIn.hasCustomName() && "jeb_".equals(witchIn.getCustomName()))
		            {
			    		int ticks = witchIn.ticksExisted;
			            int colorIndex = ticks / 25 + witchIn.getEntityId();
			            
			            int currentColor = colorIndex % DyeColor.values().length;
			            int nextColor = (colorIndex + 1) % DyeColor.values().length;
			            float progress = ((float)(ticks % 25) + partialTicks) / 25.0F;
			            
			            float[] currentVal = SheepEntity.getDyeRgb(DyeColor.byId(currentColor));
			            float[] nextVal = SheepEntity.getDyeRgb(DyeColor.byId(nextColor));
			            
			            int r = (int)((currentVal[0] * (1.0F - progress) + nextVal[0] * progress) * 255);
			            int g = (int)((currentVal[1] * (1.0F - progress) + nextVal[1] * progress) * 255);
			            int b = (int)((currentVal[2] * (1.0F - progress) + nextVal[2] * progress) * 255);
			            
			            hatColor = (r << 16) | (g << 8) | b;
		            }
		            
		            if(hatColor != ((DyeableArmorItem)itemstack.getItem()).getColor(itemstack))
		            	((DyeableArmorItem)itemstack.getItem()).setColor(itemstack, hatColor);
		            
					break;
				case CHANGELING:
				case CRONE:
				case FOX:
				default:
					break;
			}
		
		if(itemstack.getItem() instanceof ArmorItem)
		{
			ArmorItem armoritem = (ArmorItem)itemstack.getItem();
			if(armoritem.getEquipmentSlot() == slot)
			{
				model = getArmorModelHook(witchIn, itemstack, slot, model);
				this.getEntityModel().setModelAttributes(model);
				this.setModelSlotVisible(model, slot);
				boolean enchanted = itemstack.hasEffect();
				if (armoritem instanceof net.minecraft.item.IDyeableArmorItem)
				{
					int i = ((net.minecraft.item.IDyeableArmorItem)armoritem).getColor(itemstack);
					float f = (float)(i >> 16 & 255) / 255.0F;
					float f1 = (float)(i >> 8 & 255) / 255.0F;
					float f2 = (float)(i & 255) / 255.0F;
					this.renderItem(matrixStack, buffer, packedLight, enchanted, model, f, f1, f2, this.getArmorResource(witchIn, itemstack, slot, null));
					this.renderItem(matrixStack, buffer, packedLight, enchanted, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(witchIn, itemstack, slot, "overlay"));
				}
				else
					this.renderItem(matrixStack, buffer, packedLight, enchanted, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(witchIn, itemstack, slot, null));
			}
		}
	}
	
	protected void renderItem(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, boolean enchanted, BipedModel<EntityPatronWitch> model, float red, float green, float blue, ResourceLocation armorResource)
	{
		IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(buffer, RenderType.getArmorCutoutNoCull(armorResource), false, enchanted);
		model.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
	}
}
