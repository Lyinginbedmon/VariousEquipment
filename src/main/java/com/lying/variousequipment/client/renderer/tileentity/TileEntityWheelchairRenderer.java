package com.lying.variousequipment.client.renderer.tileentity;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.bauble.ItemWheelchair;
import com.lying.variousequipment.tileentity.TileEntityWheelchair;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class TileEntityWheelchairRenderer extends TileEntityRenderer<TileEntityWheelchair>
{
	public TileEntityWheelchairRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}
	
	public void render(TileEntityWheelchair tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		ItemStack stack = tileEntityIn.getStack();
		if(stack.isEmpty())
			stack = new ItemStack(VEItems.OAK_WHEELCHAIR);
		
		matrixStackIn.push();
			matrixStackIn.translate(0.5D, 1.5D, 0.5D);
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180F));
			matrixStackIn.push();
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(tileEntityIn.getYaw()));
				if(stack.getItem() instanceof ItemWheelchair)
					((ItemWheelchair)stack.getItem()).renderStaticItem(matrixStackIn, bufferIn, combinedLightIn, stack);
				else
					Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
			matrixStackIn.pop();
		matrixStackIn.pop();
	}
}
