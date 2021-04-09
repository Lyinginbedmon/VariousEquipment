package com.lying.variousequipment.item.bauble;

import com.lying.variousequipment.client.model.bauble.ModelRings;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ItemRing extends Item implements ICurioItem
{
	private final ResourceLocation texture;
	
	@OnlyIn(Dist.CLIENT)
	private Object model;
	
	public ItemRing(String nameIn, Properties properties)
	{
		super(properties.maxDamage(0).maxStackSize(1));
		texture = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/"+nameIn+".png");
	}
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return true; }
	
	@OnlyIn(Dist.CLIENT)
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(!(this.model instanceof ModelRings))
			this.model = new ModelRings(1F);
		
		ModelRings ringsModel = (ModelRings)this.model;
		
		int slot = CuriosApi.getCuriosHelper().findEquippedCurio(stack.getItem(), living).get().middle;
		ringsModel.setRing(slot);
        ringsModel.setLivingAnimations(living, limbSwing, limbSwingAmount, partialTicks);
        ringsModel.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurio.RenderHelper.followBodyRotations(living, ringsModel);
        IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, ringsModel.getRenderType(texture), false, stack.hasEffect());
        ringsModel.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
