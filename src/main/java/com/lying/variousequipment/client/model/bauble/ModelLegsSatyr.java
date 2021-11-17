package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelLegsSatyr extends BipedModel<LivingEntity>
{
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
			ModelRenderer ankle = ModelUtils.freshRenderer(this);
			ankle.mirror = true;
			ankle.setTextureOffset(0, 13).addBox(-1F, 3.5F, -2F, 2, 2, 7);
			ankle.rotateAngleX = ModelUtils.toRadians(-30D);
			ModelRenderer foot = ModelUtils.freshRenderer(this);
			foot.mirror = true;
			foot.setTextureOffset(0, 22).addBox(-1.5F, 2F, -11.8F, 3, 3, 8);
			foot.setTextureOffset(22, 23).addBox(-2.5F, 1.0F, -13F, 5, 5, 5, -0.5F);
			foot.rotateAngleX = ModelUtils.toRadians(70D);
		this.bipedLeftLeg.addChild(thigh);
		this.bipedLeftLeg.addChild(ankle);
		this.bipedLeftLeg.addChild(foot);
		
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
		this.bipedRightLeg.setRotationPoint(-2.4F, 12F, 0F);
			thigh = ModelUtils.freshRenderer(this);
			thigh.setTextureOffset(0, 0).addBox(-2F, -2F, -6.5F, 4, 4, 9);
			thigh.rotateAngleX = ModelUtils.toRadians(35D);
			ankle = ModelUtils.freshRenderer(this);
			ankle.setTextureOffset(0, 13).addBox(-1F, 3.5F, -2F, 2, 2, 7);
			ankle.rotateAngleX = ModelUtils.toRadians(-30D);
			foot = ModelUtils.freshRenderer(this);
			foot.setTextureOffset(0, 22).addBox(-1.5F, 2F, -11.8F, 3, 3, 8);
			foot.setTextureOffset(22, 23).addBox(-2.5F, 1.0F, -13F, 5, 5, 5, -0.5F);
			foot.rotateAngleX = ModelUtils.toRadians(70D);
		this.bipedRightLeg.addChild(thigh);
		this.bipedRightLeg.addChild(ankle);
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
