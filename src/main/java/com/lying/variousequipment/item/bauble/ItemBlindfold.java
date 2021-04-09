package com.lying.variousequipment.item.bauble;

import javax.annotation.Nonnull;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ItemBlindfold extends ArmorItem implements ICurioItem
{
	@OnlyIn(Dist.CLIENT)
	private BipedModel<LivingEntity> model;
	
	public ItemBlindfold(Properties builderIn)
	{
		super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, builderIn);
	}
	
	public static boolean isBlindfolded(LivingEntity entity)
	{
		return entity.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == VEItems.BLINDFOLD || CuriosApi.getCuriosHelper().findEquippedCurio(VEItems.BLINDFOLD, entity).isPresent();
	}
	
	public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity)
	{
		return true;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, M original)
	{
		return (M) new BipedModel<>(0.5F);
	}
	
	@Nonnull
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return Reference.ModInfo.MOD_PREFIX + "textures/models/armor/blindfold.png";
	}
	
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack){ return true; }
	
	@OnlyIn(Dist.CLIENT)
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(!(this.model instanceof BipedModel))
			this.model = new BipedModel<LivingEntity>(0.5F);
		
		ResourceLocation texture = new ResourceLocation(getArmorTexture(stack, living, getEquipmentSlot(), null));
		model.setLivingAnimations(living, limbSwing, limbSwingAmount, partialTicks);
		model.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		ModelRenderer head = model.getModelHead();
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(texture), false, stack.hasEffect());
		head.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
	}
}
