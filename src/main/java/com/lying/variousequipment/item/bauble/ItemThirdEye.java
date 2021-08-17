package com.lying.variousequipment.item.bauble;

import org.lwjgl.opengl.GL11;

import com.lying.variousequipment.item.VEItemGroup;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemThirdEye extends ItemCosmetic implements IDyeableArmorItem
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/third_eye.png");
	private static final float EYE_SIZE = 0.25F;
	
	public ItemThirdEye(Properties properties)
	{
		super(properties.group(VEItemGroup.PROPS));
	}
	
	public int getColor(ItemStack stack)
	{
		CompoundNBT stackData = stack.getChildTag("display");
		return stackData != null && stackData.contains("color", 99) ? stackData.getInt("color") : getDefaultColor();
	}
	
	public int getDefaultColor(){ return 902901; }
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return true; }
	
	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
			int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		matrixStackIn.push();
			// Remove entity rotation
			float entityYaw = living.prevRenderYawOffset + (living.renderYawOffset - living.prevRenderYawOffset) * partialTicks;
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(360F - entityYaw));
			// Translate to above head
			matrixStackIn.translate(0D, -0.75D, 0D);
			matrixStackIn.push();
				// Rotate to face camera
				Minecraft mc = Minecraft.getInstance();
				Vector3f viewVec = mc.getRenderManager().info.getViewVector();
				double pitch = Math.asin(-viewVec.getY());
				double yaw = Math.atan2(viewVec.getX(), viewVec.getZ());
				matrixStackIn.rotate(Vector3f.YP.rotation((float)-yaw));
				matrixStackIn.rotate(Vector3f.XN.rotation((float)-pitch));
				
				RenderSystem.disableCull();
				RenderSystem.enableBlend();
				RenderSystem.disableLighting();
				mc.getTextureManager().bindTexture(TEXTURE);
				
		        int i = getColor(stack);
		        float r = (float)(i >> 16 & 255) / 255.0F;
		        float g = (float)(i >> 8 & 255) / 255.0F;
		        float b = (float)(i & 255) / 255.0F;
		        RenderSystem.color3f(r, g, b);
		        
				Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
				drawEye(matrix4f, (int)(ageInTicks + partialTicks));
				RenderSystem.disableBlend();
				RenderSystem.enableLighting();
		    matrixStackIn.pop();
	    matrixStackIn.pop();
	}
	
	private static void drawEye(Matrix4f matrix4f, int index)
	{
		index = Math.max(0, index % 40 - 30);
		int frame = index > 5 ? 4 - (index % 6) : index % 6;
		float texStart = (1F / 6F) * frame;
		float texEnd = texStart + (1F / 6F);
		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		
		float radius = EYE_SIZE * 0.5F;
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.pos(matrix4f,	-radius,	+radius, 0F).tex(0.0F, texEnd).endVertex();
			buffer.pos(matrix4f,	+radius,	+radius, 0F).tex(1.0F, texEnd).endVertex();
			buffer.pos(matrix4f,	+radius,	-radius, 0F).tex(1.0F, texStart).endVertex();
			buffer.pos(matrix4f,	-radius,	-radius, 0F).tex(0.0F, texStart).endVertex();
		buffer.finishDrawing();
		WorldVertexBufferUploader.draw(buffer);
	}
}
