package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelLegsDragon extends BipedModel<LivingEntity>
{
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
			ModelRenderer ankle = ModelUtils.freshRenderer(this);
			ankle.mirror = true;
			ankle.setTextureOffset(26, 0).addBox(-1F, 3.5F, 0F, 2, 2, 4);
			ankle.rotateAngleX = ModelUtils.toRadians(-30D);
			ankle.rotateAngleZ = ModelUtils.toRadians(-6D);
			ModelRenderer bridge = ModelUtils.freshRenderer(this);
			bridge.mirror = true;
			bridge.setTextureOffset(38, 0).addBox(-1.5F, 3.5F, -9.8F, 3, 2, 6);
			bridge.rotateAngleX = ModelUtils.toRadians(66D);
			bridge.rotateAngleZ = ModelUtils.toRadians(-6D);
			ModelRenderer foot = ModelUtils.freshRenderer(this);
			foot.mirror = true;
			foot.setTextureOffset(26, 6).addBox(-1F, 9F, -3F, 4, 3, 4);
		this.bipedLeftLeg.addChild(thigh);
		this.bipedLeftLeg.addChild(ankle);
		this.bipedLeftLeg.addChild(bridge);
		this.bipedLeftLeg.addChild(foot);
		
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
		this.bipedRightLeg.setRotationPoint(-2.4F, 12F, 0F);
			thigh = ModelUtils.freshRenderer(this).setTextureOffset(0, 0).addBox(-2F, -2.3F, -6.5F, 4, 3, 9);
			thigh.rotateAngleX = ModelUtils.toRadians(60D);
			thigh.rotateAngleZ = ModelUtils.toRadians(6D);
			ankle = ModelUtils.freshRenderer(this).setTextureOffset(26, 0).addBox(-1F, 3.5F, 0F, 2, 2, 4);
			ankle.rotateAngleX = ModelUtils.toRadians(-30D);
			ankle.rotateAngleZ = ModelUtils.toRadians(6D);
			bridge = ModelUtils.freshRenderer(this).setTextureOffset(38, 0).addBox(-1.5F, 3.5F, -9.8F, 3, 2, 6);
			bridge.rotateAngleX = ModelUtils.toRadians(66D);
			bridge.rotateAngleZ = ModelUtils.toRadians(6D);
			foot = ModelUtils.freshRenderer(this).setTextureOffset(26, 6).addBox(-3F, 9F, -3F, 4, 3, 4);
		this.bipedRightLeg.addChild(thigh);
		this.bipedRightLeg.addChild(ankle);
		this.bipedRightLeg.addChild(bridge);
		this.bipedRightLeg.addChild(foot);
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
