package com.lying.variousequipment.client.renderer.tileentity;

import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.tileentity.TileEntitySpinny;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class TileEntitySpinnyRenderer<T extends TileEntitySpinny> extends TileEntityRenderer<T>
{
	private static final ModelResourceLocation ARM_MODEL = new ModelResourceLocation(VEBlocks.SPINNY_ARMS.getRegistryName().toString(), "");
	
	public TileEntitySpinnyRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}
	
	@SuppressWarnings("deprecation")
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
		ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
		
		partialTicks *= tileEntityIn.isWorking() ? 1F : -1F;
		float yaw = tileEntityIn.hasSpinTime() ? getRotation(tileEntityIn.getSpinTime(), partialTicks) : 0F;
		
		matrixStackIn.push();
			matrixStackIn.translate(0.5D, 0.1D, 0.5D);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(yaw));
			matrixStackIn.translate(-0.5D, 0D, -0.5D);
			blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(matrixStackIn.getLast(), bufferIn.getBuffer(Atlases.getSolidBlockType()), (BlockState)null, modelmanager.getModel(ARM_MODEL), 1.0F, 1.0F, 1.0F, combinedLightIn, OverlayTexture.NO_OVERLAY);
		matrixStackIn.pop();
		
		// Render contained items
		if(tileEntityIn.isEmpty()) return;
    	ItemRenderer renderItem = Minecraft.getInstance().getItemRenderer();
    	
    	matrixStackIn.push();
	    	matrixStackIn.translate(0.5D, 0.6D, 0.5D);
    		float vialRot = 0F;
    		if(yaw != 0F)
			{
    			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(yaw));
    			vialRot = vialRotation(yaw);
			}
    		
    		float scale = 0.4F;
    		matrixStackIn.scale(scale, scale, scale);
    		for(int slot = 0; slot < 4; slot++)
    		{
    			ItemStack stackInSlot = tileEntityIn.getStackInSlot(slot);
    			if(stackInSlot.isEmpty()) continue;
    			matrixStackIn.push();
	    			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F * slot));
		    			matrixStackIn.translate(0.9D, 0D, 0D);
		    			matrixStackIn.push();
			    			if(tileEntityIn.isWorking()) matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(vialRot));
			    			renderItem.renderItem(stackInSlot, TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
		    			matrixStackIn.pop();
	    			matrixStackIn.pop();
    		}
		matrixStackIn.pop();
	}
	
	/** Returns the rotation of the arms at a given point in rotation */
	protected float getRotation(int ticksIn, float partialTicksIn)
	{
		return 40F * ((float)ticksIn + partialTicksIn);
	}
	
	/** Returns the rotation of displayed items at a given yaw value */
	protected float vialRotation(float yaw)
	{
		return (Math.min(180F, yaw) / 180F) * 90F;
	}
}
