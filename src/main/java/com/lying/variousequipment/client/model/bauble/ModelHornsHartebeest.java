package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelHornsHartebeest<T extends LivingEntity> extends EntityModel<T>
{
	public ModelRenderer horns;
	
	public ModelHornsHartebeest()
	{
		this.textureHeight = 16;
		this.textureWidth = 32;
		this.horns = ModelUtils.freshRenderer(this);
		this.horns.setRotationPoint(0F, 0F, 0F);
		
		ModelRenderer hornRight = makeHorn(-1F, false);
		hornRight.setRotationPoint(-4F, -6.5F, 0F);
		
		ModelRenderer hornLeft = makeHorn(1F, true);
		hornLeft.setRotationPoint(4F, -6.5F, 0F);
		
		this.horns.addChild(hornRight);
		this.horns.addChild(hornLeft);
	}
	
	public ModelRenderer makeHorn(float direction, boolean mirror)
	{
		double inward = 7.5D;
		ModelRenderer root = ModelUtils.freshRenderer(this);
		root.mirror = mirror;
		root.rotateAngleX = ModelUtils.toRadians(45D);
		root.rotateAngleY = ModelUtils.toRadians(8D) * direction;
			ModelRenderer rootA = ModelUtils.freshRenderer(this);
			rootA.mirror = mirror;
			rootA.rotateAngleZ = ModelUtils.toRadians(13D) * direction;
			rootA.setTextureOffset(0, 0).addBox(-1F, -1F, -1F, 2, 2, 4);
			rootA.setTextureOffset(12, 0).addBox(-1F, -1F, -2F, 2, 2, 2, -0.25F);
		root.addChild(rootA);
		
			ModelRenderer point1 = ModelUtils.freshRenderer(this);
			point1.setRotationPoint(0F, 0F, 3.5F);
			point1.rotateAngleX = ModelUtils.toRadians(-15D);
			point1.rotateAngleY = ModelUtils.toRadians(inward) * -direction;
				ModelRenderer point1a = ModelUtils.freshRenderer(this);
				point1a.mirror = mirror;
				point1a.rotateAngleZ = ModelUtils.toRadians(6.5D) * direction;
				point1a.setTextureOffset(0, 6).addBox(-1F, -1F, -1F, 2, 2, 3, -0.25F);
			point1.addChild(point1a);
			root.addChild(point1);
			
				ModelRenderer point2 = ModelUtils.freshRenderer(this);
				point2.setRotationPoint(0F, 0F, 2F);
				point2.rotateAngleX = ModelUtils.toRadians(-15D);
				point2.rotateAngleY = ModelUtils.toRadians(inward) * -direction;
					ModelRenderer point2a = ModelUtils.freshRenderer(this);
					point2a.mirror = mirror;
					point2a.setTextureOffset(0, 11).addBox(-1F, -1F, -1F, 2, 2, 2, -0.5F);
				point2.addChild(point2a);
				point1.addChild(point2);
		
		return root;
	}
	
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		
	}
	
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		this.horns.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
