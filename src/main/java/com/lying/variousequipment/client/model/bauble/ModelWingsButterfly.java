package com.lying.variousequipment.client.model.bauble;

import com.lying.variousoddities.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelWingsButterfly extends ModelWings<LivingEntity>
{
	ModelRenderer wingLeftA, wingLeftB;
	ModelRenderer wingRightA, wingRightB;
	final ModelRenderer[] wingParts;
	
	public ModelWingsButterfly()
	{
		super();
		this.textureHeight = 16;
		this.textureWidth = 32;
		
		this.wingLeftA = ModelUtils.freshRenderer(this);
		this.wingLeftA.setRotationPoint(1.5F, 0F, 2F);
		this.wingLeftA.mirror = true;
		this.wingLeftA.setTextureOffset(0, -12).addBox(0F, -1F, 0F, 0, 7, 12);
			this.bipedBody.addChild(wingLeftA);
		
		this.wingLeftB = ModelUtils.freshRenderer(this);
		this.wingLeftB.setRotationPoint(1.6F, 5F, 2F);
		this.wingLeftB.mirror = true;
		this.wingLeftB.setTextureOffset(0, -1).addBox(0F, -1F, 0F, 0, 6, 8);
			this.bipedBody.addChild(wingLeftB);
		
		this.wingRightA = ModelUtils.freshRenderer(this);
		this.wingRightA.setRotationPoint(-1.5F, 0F, 2F);
		this.wingRightA.setTextureOffset(0, -12).addBox(0F, -1F, 0F, 0, 7, 12);
			this.bipedBody.addChild(wingRightA);
		
		this.wingRightB = ModelUtils.freshRenderer(this);
		this.wingRightB.setRotationPoint(-1.6F, 5F, 2F);
		this.wingRightB.setTextureOffset(0, -1).addBox(0F, -1F, 0F, 0, 6, 8);
			this.bipedBody.addChild(wingRightB);
		
		this.wingParts = new ModelRenderer[]{wingLeftA, wingLeftB, wingRightA, wingRightB};
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		
		float wingY = 0F;
		float wingUpperX = 0F;
		float wingLowerX = -ModelUtils.toRadians(4D);
		switch(entityIn.getPose())
		{
			case CROUCHING:
				wingY = ModelUtils.toRadians(45D) + (float)(Math.sin(ageInTicks) * ModelUtils.degree5);
				break;
			case SLEEPING:
				wingY = ModelUtils.toRadians(90D);
				break;
			case FALL_FLYING:
				wingY = (float)(Math.sin(limbSwing * 1.1F) + 1F) * ModelUtils.degree90 * 0.5F;
				break;
			case SWIMMING:
				wingUpperX = -ModelUtils.toRadians(12D);
				wingLowerX = -ModelUtils.toRadians(6D);
				wingY = ModelUtils.toRadians(60D);
				break;
			default:
				wingUpperX = -ModelUtils.toRadians(12D);
				wingLowerX = 0F;
				wingY = ModelUtils.toRadians(10D);
				break;
		}
		
		this.wingLeftA.rotateAngleY = this.wingLeftB.rotateAngleY = wingY;
		this.wingRightA.rotateAngleY = this.wingRightB.rotateAngleY = -wingY;
		
		this.wingLeftA.rotateAngleX = this.wingRightA.rotateAngleX = wingUpperX;
		this.wingLeftB.rotateAngleX = this.wingRightB.rotateAngleX = wingLowerX;
    }
}
