package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelLegsDragon extends BipedModel<LivingEntity>
{
	ModelRenderer rightAnkle, leftAnkle;
	
	public ModelLegsDragon()
	{
		super(1F);
		this.textureHeight = 64;
		this.textureWidth = 64;
		
		this.bipedLeftLeg = ModelUtils.freshRenderer(this);
		this.bipedLeftLeg.setRotationPoint(2.4F, 12F, 0F);
			ModelRenderer thigh = ModelUtils.freshRenderer(this);
			thigh.mirror = true;
			thigh.setTextureOffset(0, 0).addBox(-2F, -2.3F, -6.5F, 4, 3, 9);
			thigh.rotateAngleX = ModelUtils.toRadians(60D);
			thigh.rotateAngleZ = ModelUtils.toRadians(-6D);
			leftAnkle = ModelUtils.freshRenderer(this);
			leftAnkle.mirror = true;
			leftAnkle.setTextureOffset(26, 0).addBox(-1F, -1F, -1F, 2, 2, 4);
			leftAnkle.setRotationPoint(0F, 0.5F, -4.5F);
			leftAnkle.rotateAngleX = ModelUtils.toRadians(-90D);
				thigh.addChild(leftAnkle);
			ModelRenderer bridge = ModelUtils.freshRenderer(this);
			bridge.mirror = true;
			bridge.setTextureOffset(38, 0).addBox(-1.5F, 0F, -4.8F, 3, 2, 6);
			bridge.setRotationPoint(0F, 0F, 3F);
			bridge.rotateAngleX = ModelUtils.toRadians(96D);
				leftAnkle.addChild(bridge);
			ModelRenderer foot = ModelUtils.freshRenderer(this);
			foot.mirror = true;
			foot.setTextureOffset(26, 6).addBox(-1.5F, 3.2F, -4F, 4, 3, 4);
			foot.setRotationPoint(0F, 0F, 3F);
			foot.rotateAngleX = ModelUtils.toRadians(30D);
			foot.rotateAngleZ = -thigh.rotateAngleZ;
				leftAnkle.addChild(foot);
		this.bipedLeftLeg.addChild(thigh);
		
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
		this.bipedRightLeg.setRotationPoint(-2.4F, 12F, 0F);
			thigh = ModelUtils.freshRenderer(this).setTextureOffset(0, 0).addBox(-2F, -2.3F, -6.5F, 4, 3, 9);
			thigh.rotateAngleX = ModelUtils.toRadians(60D);
			thigh.rotateAngleZ = ModelUtils.toRadians(6D);
			rightAnkle = ModelUtils.freshRenderer(this).setTextureOffset(26, 0).addBox(-1F, -1F, -1F, 2, 2, 4);
			rightAnkle.setRotationPoint(0F, 0.5F, -4.5F);
			rightAnkle.rotateAngleX = ModelUtils.toRadians(-90D);
				thigh.addChild(rightAnkle);
			bridge = ModelUtils.freshRenderer(this).setTextureOffset(38, 0).addBox(-1.5F, 0F, -4.8F, 3, 2, 6);
			bridge.setRotationPoint(0F, 0F, 3F);
			bridge.rotateAngleX = ModelUtils.toRadians(96D);
				rightAnkle.addChild(bridge);
			foot = ModelUtils.freshRenderer(this).setTextureOffset(26, 6).addBox(-2.5F, 3.2F, -4F, 4, 3, 4);
			foot.setRotationPoint(0F, 0F, 3F);
			foot.rotateAngleX = ModelUtils.toRadians(30D);
			foot.rotateAngleZ = -thigh.rotateAngleZ;
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
			this.bipedRightLeg.rotateAngleX = ModelUtils.toRadians(-60D);
			this.bipedLeftLeg.rotateAngleX = ModelUtils.toRadians(-60D);
			
			this.bipedRightLeg.rotateAngleZ = ModelUtils.toRadians(9D);
			this.bipedLeftLeg.rotateAngleZ = ModelUtils.toRadians(-9D);
			
			this.rightAnkle.rotateAngleX = ModelUtils.toRadians(-120D);
			this.leftAnkle.rotateAngleX = ModelUtils.toRadians(-120D);
		}
		else
		{
			this.rightAnkle.rotateAngleX = ModelUtils.toRadians(-90D);
			this.leftAnkle.rotateAngleX = ModelUtils.toRadians(-90D);
		}
	}
}
