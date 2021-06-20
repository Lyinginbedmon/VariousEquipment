package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailRat extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	ModelRenderer tail2;
	ModelRenderer tail3;
	
	public ModelTailRat()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 10F, 2F);
		this.tail.setTextureOffset(0, 0).addBox(-1F, -1F, -1F, 2, 6, 2);
		this.tail.setTextureOffset(8, 0).addBox(-1F, -1F, -1F, 2, 3, 2, 0.3F);
		
		this.tail2 = ModelUtils.freshRenderer(this);
		this.tail2.setRotationPoint(0F, 5F, 0F);
		this.tail2.setTextureOffset(0, 8).addBox(-1F, -0.5F, -1F, 2, 5, 2);
		
		this.tail.addChild(this.tail2);
		
		this.tail3 = ModelUtils.freshRenderer(this);
		this.tail3.setRotationPoint(0F, 5F, 0F);
		this.tail3.setTextureOffset(0, 15).addBox(-1F, -0.5F, -1F, 2, 4, 2);
		
		this.tail2.addChild(this.tail3);
		
		this.bipedBody.addChild(this.tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		this.tail2.rotateAngleX = ModelUtils.toRadians(20D) + ModelUtils.toRadians(45D) * MathHelper.cos(limbSwing) * limbSwingAmount * 0.5F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(60D);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(30D);
				this.tail2.rotateAngleX = ModelUtils.toRadians(10D) + ModelUtils.toRadians(45D) * MathHelper.cos(limbSwing) * limbSwingAmount * 0.35F;
				break;
			case SLEEPING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(90D);
				this.tail2.rotateAngleX = (float)ModelUtils.toRadians(-90D);
				break;
			default:
		    	this.tail.rotateAngleX = (float)ModelUtils.toRadians(30D);
				this.tail.rotateAngleX += ModelUtils.toRadians(35D) * limbSwingAmount;
				break;
		}
		
		this.tail3.rotateAngleX = this.tail2.rotateAngleX * 0.5F;
		
		this.tail2.rotateAngleY = ModelUtils.toRadians(15D) * MathHelper.sin(ageInTicks / 20);
		this.tail3.rotateAngleY = this.tail2.rotateAngleY * 0.5F;
    }
}
