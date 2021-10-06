package com.lying.variousequipment.client.renderer.entity.layer;

import com.lying.variousequipment.client.model.entity.ModelScarecrow;
import com.lying.variousequipment.entity.EntityScarecrow;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerScarecrowButtons extends LayerRenderer<EntityScarecrow, ModelScarecrow>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/entity/scarecrow_buttons.png");
	private static final ModelScarecrow sheepModel = new ModelScarecrow(1F);
	
	public LayerScarecrowButtons(IEntityRenderer<EntityScarecrow, ModelScarecrow> entityRendererIn)
	{
		super(entityRendererIn);
	}
	
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EntityScarecrow scarecrowIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		if(!scarecrowIn.isInvisible())
		{
			float f;
			float f1;
			float f2;
			if(scarecrowIn.hasCustomName() && "jeb_".equals(scarecrowIn.getName().getUnformattedComponentText()))
			{
				int i = scarecrowIn.ticksExisted / 25 + scarecrowIn.getEntityId();
				int j = DyeColor.values().length;
				int k = i % j;
				int l = (i + 1) % j;
				float f3 = ((float)(scarecrowIn.ticksExisted % 25) + partialTicks) / 25.0F;
				float[] afloat1 = SheepEntity.getDyeRgb(DyeColor.byId(k));
				float[] afloat2 = SheepEntity.getDyeRgb(DyeColor.byId(l));
				f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
				f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
				f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
			}
			else
			{
				float[] afloat = SheepEntity.getDyeRgb(scarecrowIn.getColor());
				f = afloat[0];
				f1 = afloat[1];
				f2 = afloat[2];
			}
			
			renderCopyCutoutModel(this.getEntityModel(), sheepModel, TEXTURE, matrixStackIn, bufferIn, packedLightIn, scarecrowIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);
		}
	}
}
