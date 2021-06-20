package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.util.math.MathHelper;

public class ModelTailDevil extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	ModelRenderer tail2;
	ModelRenderer tail3;
	
	public ModelTailDevil()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 9.5F, 2F);
		this.tail.setTextureOffset(0, 0).addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
		
		this.tail2 = ModelUtils.freshRenderer(this);
		this.tail2.setRotationPoint(0F, 8F, 0F);
		this.tail2.setTextureOffset(4, 0).addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
		this.tail.addChild(this.tail2);
		
		this.tail3 = ModelUtils.freshRenderer(this);
		this.tail3.setRotationPoint(0F, 6F, 0F);
		this.tail3.setTextureOffset(8, 0).addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
		this.tail3.setTextureOffset(0, 9).addBox(-2.5F, 2F, 0.5F, 5, 5, 1, 0.5F);
		this.tail2.addChild(this.tail3);
		
		this.bipedBody.addChild(this.tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		float wiggleMag = 1F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = ((float)Math.PI / 2F);
				wiggleMag = 0.5F;
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(8D);
				wiggleMag = 0.7F;
				break;
			case SLEEPING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(90D);
				this.tail2.rotateAngleX = (float)ModelUtils.toRadians(-90D);
				wiggleMag = 0F;
				break;
			default:
		    	this.tail.rotateAngleX = (float)ModelUtils.toRadians(51.5D);
				break;
		}
		
		float tailBase = ModelUtils.toRadians(47D);
		if((entityIn.getPose() == Pose.SWIMMING) || entityIn.getPose() == Pose.FALL_FLYING)
			tailBase = ModelUtils.toRadians(10D);
		this.tail2.rotateAngleX = this.tail3.rotateAngleX = tailBase + ((float)Math.PI / 4F) * MathHelper.cos(limbSwing) * limbSwingAmount * 0.5F;
		
		this.tail.rotateAngleY = (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F) * wiggleMag;
		this.tail.rotateAngleX += (MathHelper.sin(ageInTicks * 0.067F) * 0.05F) * wiggleMag;
		this.tail2.rotateAngleY = (MathHelper.sin(ageInTicks * 0.09F) * ModelUtils.toRadians(15D)) * wiggleMag;
    }
}
