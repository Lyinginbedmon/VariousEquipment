package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailDragon extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	ModelRenderer tail2;
	ModelRenderer tail3;
	
	public ModelTailDragon()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 10F, 2F);
		this.tail.setTextureOffset(0, 0).addBox(-2F, 0F, -2F, 4, 6, 4);
		this.tail.setTextureOffset(0, 0).addBox(-0.5F, 0F, 2F, 1, 2, 1);
		this.tail.setTextureOffset(0, 0).addBox(-0.5F, 3F, 2F, 1, 2, 1);
		this.bipedBody.addChild(tail);
		
		this.tail2 = ModelUtils.freshRenderer(this);
		this.tail2.setRotationPoint(0F, 6F, 0F);
		this.tail2.setTextureOffset(0, 10).addBox(-1.5F, -0.5F, -1.5F, 3, 6, 3);
		this.tail2.setTextureOffset(0, 0).addBox(-0.5F, 0F, 1.5F, 1, 2, 1);
		this.tail2.setTextureOffset(0, 0).addBox(-0.5F, 3F, 1.5F, 1, 2, 1);
		this.tail.addChild(tail2);
		
		this.tail3 = ModelUtils.freshRenderer(this);
		this.tail3.setRotationPoint(0F, 6F, 0F);
		this.tail3.setTextureOffset(0, 19).addBox(-1F, -1F, -1F, 2, 6, 2);
		this.tail3.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, 1F, 1, 2, 1);
		this.tail3.setTextureOffset(0, 0).addBox(-0.5F, 2.5F, 1F, 1, 2, 1);
		this.tail2.addChild(tail3);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
		float wiggleMag = 1F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(60D);
				wiggleMag = 0.5F;
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(10D);
				wiggleMag = 0.7F;
				break;
			case SLEEPING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(90D);
				wiggleMag = 0F;
				break;
			default:
		    	this.tail.rotateAngleX = (float)ModelUtils.toRadians(50D);
				break;
		}
		
		this.tail2.rotateAngleX = this.tail.rotateAngleX * 0.5F;
		this.tail3.rotateAngleX = this.tail2.rotateAngleX * 0.5F;
		
		this.tail.rotateAngleY += (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F) * wiggleMag;
		this.tail.rotateAngleX += (MathHelper.sin(ageInTicks * 0.067F) * 0.05F) * wiggleMag;
    }
}
