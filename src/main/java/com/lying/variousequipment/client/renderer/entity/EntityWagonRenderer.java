package com.lying.variousequipment.client.renderer.entity;

import com.lying.variousequipment.entity.EntityWagon;
import com.lying.variousequipment.item.ItemChassis;
import com.lying.variousequipment.item.ItemWheel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityWagonRenderer extends LivingRenderer<EntityWagon, BipedModel<EntityWagon>>
{
	private final Minecraft mc = Minecraft.getInstance();
	private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
	
	public EntityWagonRenderer(EntityRendererManager renderManager, net.minecraft.client.renderer.ItemRenderer itemRendererIn)
	{
		super(renderManager, new BipedModel<EntityWagon>(0F), 2F);
		this.itemRenderer = itemRendererIn;
	}
	
	public void render(EntityWagon entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		matrixStackIn.push();
			if(entityIn.hasWheels())
				matrixStackIn.translate(0D, 0.7D, 0D);
			else
				matrixStackIn.translate(0D, 0.25D, 0D);
			
			float yaw = entityIn.rotationYaw;
			float yawPrev = entityIn.prevRotationYaw;
			float delta = (yaw - yawPrev) * partialTicks;
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180 - (yawPrev + delta)));
			if(!entityIn.isInvisible())
			{
				renderChassis(entityIn.getChassis(), matrixStackIn, bufferIn, packedLightIn);
				
				if(entityIn.hasWheels())
				{
					float spin = entityIn.wheelSpin * 45F;
					
					matrixStackIn.push();
						matrixStackIn.translate(1.6D, 0.05D, -0.75D);
						renderWheel(entityIn.getWheelInSlot(0), spin, matrixStackIn, bufferIn, packedLightIn);
					matrixStackIn.pop();
					
					matrixStackIn.push();
						matrixStackIn.translate(-1.6D, 0.05D, -0.75D);
						renderWheel(entityIn.getWheelInSlot(1), spin, matrixStackIn, bufferIn, packedLightIn);
					matrixStackIn.pop();
					
					matrixStackIn.push();
						matrixStackIn.translate(1.6D, 0.05D, 1.75D);
						renderWheel(entityIn.getWheelInSlot(2), spin, matrixStackIn, bufferIn, packedLightIn);
					matrixStackIn.pop();
					
					matrixStackIn.push();
						matrixStackIn.translate(-1.6D, 0.05D, 1.75D);
						renderWheel(entityIn.getWheelInSlot(3), spin, matrixStackIn, bufferIn, packedLightIn);
					matrixStackIn.pop();
				}
			}
		matrixStackIn.pop();
		
		if(entityIn.getReined())
			drawReins(entityIn, entityIn.getReinsHolder(), partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
	
	private <E extends Entity> void drawReins(EntityWagon wagon, E reinsHolder, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		if(reinsHolder == null) return;
		matrixStackIn.push();
			Vector3d vector3d = reinsHolder.getLeashPosition(partialTicks);
			double d0 = (double)(MathHelper.lerp(partialTicks, wagon.renderYawOffset, wagon.prevRenderYawOffset) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
			Vector3d wagonEyeVec = wagon.func_241205_ce_();
			double d1 = Math.cos(d0) * wagonEyeVec.z + Math.sin(d0) * wagonEyeVec.x;
			double d2 = Math.sin(d0) * wagonEyeVec.z - Math.cos(d0) * wagonEyeVec.x;
			double d3 = MathHelper.lerp((double)partialTicks, wagon.prevPosX, wagon.getPosX()) + d1;
			double d4 = MathHelper.lerp((double)partialTicks, wagon.prevPosY, wagon.getPosY()) + wagonEyeVec.y;
			double d5 = MathHelper.lerp((double)partialTicks, wagon.prevPosZ, wagon.getPosZ()) + d2;
			matrixStackIn.translate(d1, wagonEyeVec.y, d2);
			float f = (float)(vector3d.x - d3);
			float f1 = (float)(vector3d.y - d4);
			float f2 = (float)(vector3d.z - d5);
			IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.getLeash());
			Matrix4f lastMatrix = matrixStackIn.getLast().getMatrix();
			float f4 = MathHelper.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
			float f5 = f2 * f4;
			float f6 = f * f4;
			BlockPos wagonEye = new BlockPos(wagon.getEyePosition(partialTicks));
			BlockPos reinsEye = new BlockPos(reinsHolder.getEyePosition(partialTicks));
			int lightStart = this.getBlockLight(wagon, wagonEye);
			int lightEnd = getLightEnd(reinsHolder, reinsEye);
			int k = wagon.world.getLightFor(LightType.SKY, wagonEye);
			int l = wagon.world.getLightFor(LightType.SKY, reinsEye);
			MobRenderer.renderSide(vertexBuilder, lastMatrix, f, f1, f2, lightStart, lightEnd, k, l, 0.025F, 0.025F, f5, f6);
			MobRenderer.renderSide(vertexBuilder, lastMatrix, f, f1, f2, lightStart, lightEnd, k, l, 0.025F, 0.0F, f5, f6);
		matrixStackIn.pop();
	}
	
	private int getLightEnd(Entity entityIn, BlockPos position)
	{
		return entityIn.isBurning() ? 15 : entityIn.world.getLightFor(LightType.BLOCK, position);
	}
	
	@SuppressWarnings("deprecation")
	private void renderChassis(ItemStack itemstack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		if(itemstack.isEmpty() || !(itemstack.getItem() instanceof ItemChassis))
			return;
		BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
		ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
		ModelResourceLocation modelresourcelocation = new ModelResourceLocation(itemstack.getItem().getRegistryName().toString(), "");
		matrixStackIn.push();
			matrixStackIn.scale(1.333F, 1.333F, 1.333F);
			matrixStackIn.translate(-0.5D, 0D, 0D);
			blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(matrixStackIn.getLast(), bufferIn.getBuffer(Atlases.getSolidBlockType()), (BlockState)null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
		matrixStackIn.pop();
	}
	
	private void renderWheel(ItemStack itemstack, float spin, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		if(itemstack.isEmpty() || !(itemstack.getItem() instanceof ItemWheel)) return;
		matrixStackIn.push();
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F));
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(spin));
			this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.pop();
	}
	
	public Vector3d getRenderOffset(EntityWagon entityIn, float partialTicks)
	{
		return new Vector3d((double)((float)entityIn.getHorizontalFacing().getXOffset() * 0.3F), -0.25D, (double)((float)entityIn.getHorizontalFacing().getZOffset() * 0.3F));
	}

	protected boolean canRenderName(EntityWagon entity)
	{
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public ResourceLocation getEntityTexture(EntityWagon entity)
	{
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
	
	public static class RenderFactory implements IRenderFactory<EntityWagon>
	{
		public EntityRenderer<? super EntityWagon> createRenderFor(EntityRendererManager manager) 
		{
			return new EntityWagonRenderer(manager, Minecraft.getInstance().getItemRenderer());
		}
	}
}
