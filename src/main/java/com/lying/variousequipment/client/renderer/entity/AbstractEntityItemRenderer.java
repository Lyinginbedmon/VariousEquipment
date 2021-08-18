package com.lying.variousequipment.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractEntityItemRenderer<T extends ProjectileItemEntity> extends EntityRenderer<T>
{
	private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
	
	public AbstractEntityItemRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
		this.itemRenderer = Minecraft.getInstance().getItemRenderer();
		this.shadowSize = 0.15F;
		this.shadowOpaque = 0.75F;
	}
	
	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		matrixStackIn.push();
			ItemStack itemstack = entityIn.getItem();
			IBakedModel itemModel = this.itemRenderer.getItemModelWithOverrides(itemstack, entityIn.world, (LivingEntity)null);
			boolean is3D = itemModel.isGui3d();
			matrixStackIn.translate(0.0D, (double)0.1F, 0.0D);
			matrixStackIn.push();
				this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, itemModel);
			matrixStackIn.pop();
			if(!is3D)
				matrixStackIn.translate(0.0, 0.0, 0.09375F);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
	
	/**
	 * Returns the location of an entity's texture.
	 */
	@SuppressWarnings("deprecation")
	public ResourceLocation getEntityTexture(T entity){ return AtlasTexture.LOCATION_BLOCKS_TEXTURE; }
}
