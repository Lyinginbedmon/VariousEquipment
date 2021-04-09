package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelHornsRam<T extends LivingEntity> extends EntityModel<T>
{
	public ModelRenderer horns;
	
	public ModelHornsRam()
	{
		this.textureHeight = 16;
		this.textureWidth = 32;
		this.horns = ModelUtils.freshRenderer(this);
		this.horns.setRotationPoint(0F, 0F, 0F);
		
		float size = 0.5F;
		ModelRenderer rightRam1 = ModelUtils.freshRenderer(this);
		rightRam1.setTextureOffset(0, 0).addBox(-5.5F, -7.5F, -2F, 1, 2, 3, size);
		rightRam1.setTextureOffset(0, 10).addBox(-5.9F, -5F, 1F, 1, 2, 2, size * 0.5F);
		ModelRenderer rightRam2 = ModelUtils.freshRenderer(this);
		rightRam2.setTextureOffset(0, 5).addBox(-5.7F, -6F, -4.5F, 1, 2, 3, size * 0.75F);
		rightRam2.rotateAngleX = ModelUtils.toRadians(-45D);
		ModelRenderer rightRam3 = ModelUtils.freshRenderer(this);
		rightRam3.setTextureOffset(8, 0).addBox(-6.1F, -1.5F, 1.5F, 1, 1, 2, size * 0.25F);
		rightRam3.rotateAngleX = ModelUtils.toRadians(45D);
		ModelRenderer rightRam4 = ModelUtils.freshRenderer(this);
		rightRam4.setTextureOffset(8, 3).addBox(-6.3F, -2F, -1.5F, 1, 1, 2, 0F);
		rightRam4.rotateAngleX = ModelUtils.toRadians(-7D);
		
		ModelRenderer rightRam = ModelUtils.freshRenderer(this);
		rightRam.setRotationPoint(0F, 1F, 1F);
		rightRam.rotateAngleX = ModelUtils.degree5;
			rightRam.addChild(rightRam1);
			rightRam.addChild(rightRam2);
			rightRam.addChild(rightRam3);
			rightRam.addChild(rightRam4);
		
		ModelRenderer leftRam1 = ModelUtils.freshRenderer(this);
		leftRam1.mirror = true;
		leftRam1.setTextureOffset(0, 0).addBox(4.5F, -7.5F, -2F, 1, 2, 3, size);
		leftRam1.setTextureOffset(0, 10).addBox(4.7F, -5F, 1F, 1, 2, 2, size * 0.5F);
		ModelRenderer leftRam2 = ModelUtils.freshRenderer(this);
		leftRam2.mirror = true;
		leftRam2.setTextureOffset(0, 5).addBox(4.7F, -6F, -4.5F, 1, 2, 3, size * 0.75F);
		leftRam2.rotateAngleX = ModelUtils.toRadians(-45D);
		ModelRenderer leftRam3 = ModelUtils.freshRenderer(this);
		leftRam3.mirror = true;
		leftRam3.setTextureOffset(8, 0).addBox(4.9F, -1.5F, 1.5F, 1, 1, 2, size * 0.25F);
		leftRam3.rotateAngleX = ModelUtils.toRadians(45D);
		ModelRenderer leftRam4 = ModelUtils.freshRenderer(this);
		leftRam4.mirror = true;
		leftRam4.setTextureOffset(8, 3).addBox(5.1F, -2F, -1.5F, 1, 1, 2, 0F);
		leftRam4.rotateAngleX = ModelUtils.toRadians(-7D);
		
		ModelRenderer leftRam = ModelUtils.freshRenderer(this);
		leftRam.setRotationPoint(0F, 1F, 1F);
		leftRam.rotateAngleX = ModelUtils.degree5;
			leftRam.addChild(leftRam1);
			leftRam.addChild(leftRam2);
			leftRam.addChild(leftRam3);
			leftRam.addChild(leftRam4);
		
		this.horns.addChild(rightRam);
		this.horns.addChild(leftRam);
	}
	
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		
	}
	
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		this.horns.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
