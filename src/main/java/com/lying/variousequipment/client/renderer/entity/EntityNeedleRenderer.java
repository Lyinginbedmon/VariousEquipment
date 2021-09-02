package com.lying.variousequipment.client.renderer.entity;

import com.lying.variousequipment.entity.EntityNeedle;
import com.lying.variousequipment.item.ItemNeedle;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityNeedleRenderer extends EntityRenderer<EntityNeedle>
{
	public EntityNeedleRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	public void render(EntityNeedle entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		matrixStackIn.push();
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(45.0F));
			float scale = 0.0375F;
			matrixStackIn.scale(scale, scale, scale);
			matrixStackIn.translate(-4.0D, 0.0D, 0.0D);
			IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(entityIn)));
			MatrixStack.Entry matrixLast = matrixStackIn.getLast();
			Matrix4f matrix4f = matrixLast.getMatrix();
			Matrix3f matrix3f = matrixLast.getNormal();
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
			this.drawVertex(matrix4f, matrix3f, vertexBuilder, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);
			
			for(int j = 0; j < 4; ++j)
			{
				matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
				this.drawVertex(matrix4f, matrix3f, vertexBuilder, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
				this.drawVertex(matrix4f, matrix3f, vertexBuilder, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
				this.drawVertex(matrix4f, matrix3f, vertexBuilder, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
				this.drawVertex(matrix4f, matrix3f, vertexBuilder, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
			}
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public void drawVertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder vertexBuilder, int offsetX, int offsetY, int offsetZ, float textureX, float textureY, int p_229039_9_, int p_229039_10_, int p_229039_11_, int packedLightIn)
	{
		vertexBuilder.pos(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).color(255, 255, 255, 255).tex(textureX, textureY).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(normals, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).endVertex();
	}
	
	public ResourceLocation getEntityTexture(EntityNeedle entity)
	{
		ItemStack stack = entity.getItem();
		if(stack.getItem() instanceof ItemNeedle)
			return ((ItemNeedle)stack.getItem()).getEntityTexture(entity);
		return TippedArrowRenderer.RES_ARROW;
	}
	
	public static class RenderFactory implements IRenderFactory<EntityNeedle>
	{
		public EntityRenderer<? super EntityNeedle> createRenderFor(EntityRendererManager manager) 
		{
			return new EntityNeedleRenderer(manager);
		}
	}
}
