package com.lying.variousequipment.client.renderer.entity;

import java.util.Random;

import com.lying.variousequipment.entity.EntityCaltrop;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityCaltropRenderer extends AbstractEntityItemRenderer<EntityCaltrop>
{
	public EntityCaltropRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	public void render(EntityCaltrop entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		float yaw = new Random(entityIn.getUniqueID().getLeastSignificantBits()).nextFloat() * 360F;
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(yaw));
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
	
	public static class RenderFactory implements IRenderFactory<EntityCaltrop>
	{
		public EntityRenderer<? super EntityCaltrop> createRenderFor(EntityRendererManager manager) 
		{
			return new EntityCaltropRenderer(manager);
		}
	}
}
