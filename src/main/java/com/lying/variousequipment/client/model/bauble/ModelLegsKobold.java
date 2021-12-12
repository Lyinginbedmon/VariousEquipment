package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelLegsKobold extends BipedModel<LivingEntity>
{
	ModelRenderer rightAnkle, leftAnkle;
	
	public ModelLegsKobold()
	{
		super(1F);
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
	    this.bipedRightLeg.setRotationPoint(-2.4F, 12.0F, 0.0F);
	        // Thigh
	        ModelRenderer thigh = ModelUtils.freshRenderer(this).setTextureOffset(0, 0).addBox(-2F, -2F, -6.5F, 4, 3, 9);
	        thigh.rotateAngleX = (float)(Math.toRadians(35D));
	        rightAnkle = ModelUtils.freshRenderer(this);
	        rightAnkle.setRotationPoint(0F, 2F, -4F);
	        ModelRenderer ankle = ModelUtils.freshRenderer(this).setTextureOffset(26, 0).addBox(-1.5F, -0.5F, 0.5F, 3, 2, 7);
	        ankle.rotateAngleX = (float)(Math.toRadians(-30D));
	        ModelRenderer foot = ModelUtils.freshRenderer(this).setTextureOffset(46, 0).addBox(-1.5F, 8F, 1F, 3, 2, 5);
	        ModelRenderer bridge = ModelUtils.freshRenderer(this).setTextureOffset(46, 7).addBox(-1F, 6.5F, -6.2F, 2, 2, 6);
	        bridge.rotateAngleX = (float)(Math.toRadians(70D));
	    	    rightAnkle.addChild(ankle);
	    	    rightAnkle.addChild(bridge);
	    	    rightAnkle.addChild(foot);
	    this.bipedRightLeg.addChild(thigh);
	    this.bipedRightLeg.addChild(rightAnkle);
	    
		this.bipedLeftLeg = ModelUtils.freshRenderer(this);
		this.bipedLeftLeg.setRotationPoint(2.4F, 12F, 0F);
	        thigh = ModelUtils.freshRenderer(this);
	        thigh.mirror=true;
	        thigh.setTextureOffset(0, 0).addBox(-2F, -2F, -6.5F, 4, 3, 9);
	        thigh.rotateAngleX = (float)(Math.toRadians(35D));
	        leftAnkle = ModelUtils.freshRenderer(this);
	        leftAnkle.setRotationPoint(0F, 2F, -4F);
	        ankle = ModelUtils.freshRenderer(this);
	        ankle.mirror = true;
	        ankle.setTextureOffset(26, 0).addBox(-1.5F, -0.5F, 0.5F, 3, 2, 7);
	        ankle.rotateAngleX = (float)(Math.toRadians(-30D));
	        foot = ModelUtils.freshRenderer(this);
	        foot.mirror = true;
	        foot.setTextureOffset(46, 0).addBox(-1.5F, 8F, 1F, 3, 2, 5);
	        bridge = ModelUtils.freshRenderer(this);
	        bridge.mirror = true;
	        bridge.setTextureOffset(46, 7).addBox(-1F, 6.5F, -6.2F, 2, 2, 6);
	        bridge.rotateAngleX = (float)(Math.toRadians(70D));
	        	leftAnkle.addChild(ankle);
	    	    leftAnkle.addChild(bridge);
	    	    leftAnkle.addChild(foot);
		this.bipedLeftLeg.addChild(thigh);
		this.bipedLeftLeg.addChild(rightAnkle);
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
			
			this.rightAnkle.rotateAngleX = ModelUtils.toRadians(-60D);
			this.leftAnkle.rotateAngleX = ModelUtils.toRadians(-60D);
		}
		else
		{
			this.rightAnkle.rotateAngleX = 0F;
			this.leftAnkle.rotateAngleX = 0F;
		}
	}
}
