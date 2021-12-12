package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelLegsSatyr extends BipedModel<LivingEntity>
{
	ModelRenderer leftAnkle, rightAnkle;
	
	public ModelLegsSatyr()
	{
		super(1F);
		this.textureHeight = 64;
		this.textureWidth = 64;
		
		this.bipedLeftLeg = ModelUtils.freshRenderer(this);
		this.bipedLeftLeg.setRotationPoint(2.4F, 12F, 0F);
			ModelRenderer thigh = ModelUtils.freshRenderer(this);
			thigh.mirror = true;
			thigh.setTextureOffset(0, 0).addBox(-2F, -2F, -6.5F, 4, 4, 9);
			thigh.rotateAngleX = ModelUtils.toRadians(35D);
			leftAnkle = ModelUtils.freshRenderer(this);
			leftAnkle.setRotationPoint(0F, 0F, -5F);
			leftAnkle.mirror = true;
			leftAnkle.setTextureOffset(0, 13).addBox(-1F, -1F, -1F, 2, 2, 7);
				thigh.addChild(leftAnkle);
			ModelRenderer foot = ModelUtils.freshRenderer(this);
			foot.setRotationPoint(0F, 0F, 5F);
			foot.mirror = true;
			foot.setTextureOffset(0, 22).addBox(-1.5F, -1.5F, -6.8F, 3, 3, 8);
			foot.setTextureOffset(22, 23).addBox(-2.5F, -2.5F, -8F, 5, 5, 5, -0.5F);
			foot.rotateAngleX = ModelUtils.toRadians(105D);
				leftAnkle.addChild(foot);
		this.bipedLeftLeg.addChild(thigh);
		
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
		this.bipedRightLeg.setRotationPoint(-2.4F, 12F, 0F);
			thigh = ModelUtils.freshRenderer(this);
			thigh.setTextureOffset(0, 0).addBox(-2F, -2F, -6.5F, 4, 4, 9);
			thigh.rotateAngleX = ModelUtils.toRadians(35D);
			rightAnkle = ModelUtils.freshRenderer(this);
			rightAnkle.setRotationPoint(0F, 0F, -5F);
			rightAnkle.setTextureOffset(0, 13).addBox(-1F, -1F, -1F, 2, 2, 7);
				thigh.addChild(rightAnkle);
			foot = ModelUtils.freshRenderer(this);
			foot.setRotationPoint(0F, 0F, 5F);
			foot.setTextureOffset(0, 22).addBox(-1.5F, -1.5F, -6.8F, 3, 3, 8);
			foot.setTextureOffset(22, 23).addBox(-2.5F, -2.5F, -8F, 5, 5, 5, -0.5F);
			foot.rotateAngleX = ModelUtils.toRadians(105D);
				rightAnkle.addChild(foot);
		this.bipedRightLeg.addChild(thigh);
	}
	
	protected Iterable<ModelRenderer> getHeadParts()
	{
		return ImmutableList.of();
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedRightLeg, this.bipedLeftLeg);
	}
	
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if(this.isSitting)
		{
			this.bipedRightLeg.rotateAngleX = ModelUtils.toRadians(-35D);
			this.bipedLeftLeg.rotateAngleX = ModelUtils.toRadians(-35D);
			
			this.rightAnkle.rotateAngleX = ModelUtils.toRadians(-100D);
			this.leftAnkle.rotateAngleX = ModelUtils.toRadians(-100D);
		}
		else
		{
			this.rightAnkle.rotateAngleX = ModelUtils.toRadians(-65D);
			this.leftAnkle.rotateAngleX = ModelUtils.toRadians(-65D);
		}
	}
}
