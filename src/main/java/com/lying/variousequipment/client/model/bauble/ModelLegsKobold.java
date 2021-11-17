package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelLegsKobold extends BipedModel<LivingEntity>
{
	public ModelLegsKobold()
	{
		super(1F);
		this.textureHeight = 32;
		this.textureWidth = 64;
		
	        // Thigh
	        ModelRenderer thigh = ModelUtils.freshRenderer(this).setTextureOffset(0, 0).addBox(-2F, -2F, -6.5F, 4, 3, 9);
	        thigh.rotateAngleX = (float)(Math.toRadians(35D));
			
	        // Ankle
	        ModelRenderer ankle = ModelUtils.freshRenderer(this).setTextureOffset(26, 0).addBox(-1.5F, 3.5F, -2F, 3, 2, 7);
	        ankle.rotateAngleX = (float)(Math.toRadians(-30D));
	        
	        // Foot
	        ModelRenderer foot = ModelUtils.freshRenderer(this).setTextureOffset(46, 0).addBox(-1.5F, 10F, -3F, 3, 2, 5);
	        ModelRenderer bridge = ModelUtils.freshRenderer(this).setTextureOffset(46, 7).addBox(-1F, 3.5F, -9.75F, 2, 2, 6);
	        bridge.rotateAngleX = (float)(Math.toRadians(70D));
	        foot.addChild(bridge);
        
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
	    this.bipedRightLeg.setRotationPoint(-2.4F, 12.0F, 0.0F);
	    this.bipedRightLeg.addChild(thigh);
	    this.bipedRightLeg.addChild(ankle);
	    this.bipedRightLeg.addChild(foot);
	    
	        // Thigh
	        thigh = ModelUtils.freshRenderer(this).setTextureOffset(0, 0);
	        thigh.mirror=true;
	        thigh.addBox(-2F, -2F, -6.5F, 4, 3, 9);
	        thigh.rotateAngleX = (float)(Math.toRadians(35D));
			
	        // Ankle
	        ankle = ModelUtils.freshRenderer(this).setTextureOffset(26, 0);
	        ankle.mirror=true;
	        ankle.addBox(-1.5F, 3.5F, -2F, 3, 2, 7);
	        ankle.rotateAngleX = (float)(Math.toRadians(-30D));
	        
	        // Foot
	        foot = ModelUtils.freshRenderer(this).setTextureOffset(46, 0);
	        foot.mirror=true;
	        foot.addBox(-1.5F, 10F, -3F, 3, 2, 5);
	        bridge = ModelUtils.freshRenderer(this).setTextureOffset(46, 7);
	        bridge.mirror=true;
	        bridge.addBox(-1F, 3.5F, -9.75F, 2, 2, 6);
	        bridge.rotateAngleX = (float)(Math.toRadians(70D));
	        foot.addChild(bridge);
	       
		this.bipedLeftLeg = ModelUtils.freshRenderer(this);
		this.bipedLeftLeg.addChild(thigh);
		this.bipedLeftLeg.addChild(ankle);
		this.bipedLeftLeg.addChild(foot);
		this.bipedLeftLeg.setRotationPoint(2.4F, 12F, 0F);
	}
	
	protected Iterable<ModelRenderer> getHeadParts()
	{
		return ImmutableList.of();
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedRightLeg, this.bipedLeftLeg);
	}
}
