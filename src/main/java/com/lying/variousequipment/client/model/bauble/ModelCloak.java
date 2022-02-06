package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousoddities.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelCloak extends BipedModel<LivingEntity> 
{
	ModelRenderer pauldronLeft, pauldronRight;
	
	public ModelCloak()
	{
		super(0F);
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.bipedBody = ModelUtils.freshRenderer(this);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		ModelRenderer cloakRight = ModelUtils.freshRenderer(this);
		cloakRight.setTextureOffset(0, 0).addBox(-1F, -1F, -3F, 10, 16, 6, 1.1F);
		cloakRight.rotateAngleZ = ModelUtils.toRadians(-4D);
			this.bipedBody.addChild(cloakRight);
		
		this.pauldronRight = ModelUtils.freshRenderer(this);
			ModelRenderer pauldronR2 = ModelUtils.freshRenderer(this);
			pauldronR2.setTextureOffset(32, 0).addBox(-0.5F, -0F, -3F, 10, 5, 6, 1.3F);
			pauldronR2.rotateAngleZ = ModelUtils.toRadians(-4D);
				this.pauldronRight.addChild(pauldronR2);
			this.bipedBody.addChild(this.pauldronRight);
		
		ModelRenderer cloakLeft = ModelUtils.freshRenderer(this);
		cloakLeft.mirror = true;
		cloakLeft.setTextureOffset(0, 0).addBox(-9F, -1F, -3F, 10, 16, 6, 1.1F);
		cloakLeft.rotateAngleZ = ModelUtils.toRadians(4D);
			this.bipedBody.addChild(cloakLeft);
		
		this.pauldronLeft = ModelUtils.freshRenderer(this);
			ModelRenderer pauldronL2 = ModelUtils.freshRenderer(this);
			pauldronL2.mirror = true;
			pauldronL2.setTextureOffset(32, 0).addBox(-9.5F, -0F, -3F, 10, 5, 6, 1.3F);
			pauldronL2.rotateAngleZ = ModelUtils.toRadians(4D);
				this.pauldronLeft.addChild(pauldronL2);
			this.bipedBody.addChild(this.pauldronLeft);
		
		this.bipedLeftArm = ModelUtils.freshRenderer(this);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.setRotationPoint(5F, 2F, 0F);
		this.bipedLeftArm.setTextureOffset(40, 16).addBox(-1F, -2F, -2F, 4F, 12F, 4F, 1.1F);
		
		this.bipedRightArm = ModelUtils.freshRenderer(this);
		this.bipedRightArm.setRotationPoint(-5F, 2F, 0F);
		this.bipedRightArm.setTextureOffset(40, 16).addBox(-3F, -2F, -2F, 4F, 12F, 4F, 1.1F);
	}
	
	protected Iterable<ModelRenderer> getHeadParts()
	{
		return ImmutableList.of();
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedBody, this.bipedLeftArm, this.bipedRightArm);
	}
	
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		adjustPauldron(this.pauldronLeft, this.bipedLeftArm.rotateAngleX);
		adjustPauldron(this.pauldronRight, this.bipedRightArm.rotateAngleX);
	}
	
	private void adjustPauldron(ModelRenderer pauldron, float armRotateX)
	{
		float armPitch = (float)(Math.toDegrees(Math.abs(armRotateX)));
		if(armPitch > 45F)
			armPitch = 1F - ((armPitch - 45F)/45F);
		else
			armPitch /= 45F;
		
		pauldron.rotationPointY = -armPitch;
	}
}
