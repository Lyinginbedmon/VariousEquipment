package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailCat extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	ModelRenderer tail2;
	
	public ModelTailCat()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 9.5F, 2F);
		this.tail.setTextureOffset(0, 0).addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
		
		this.tail2 = ModelUtils.freshRenderer(this);
		this.tail2.setRotationPoint(0F, 8F, 0F);
		this.tail2.setTextureOffset(4, 0).addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
		
		this.tail.addChild(this.tail2);
		
		this.bipedBody.addChild(this.tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    	this.tail.rotateAngleX = (float)ModelUtils.toRadians(51.5D);
		this.tail2.rotateAngleX = 0.8278761F + ((float)Math.PI / 4F) * MathHelper.cos(limbSwing) * limbSwingAmount * 0.5F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = ((float)Math.PI / 2F);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(20D);
				break;
			case SLEEPING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(90D);
				this.tail2.rotateAngleX = (float)ModelUtils.toRadians(-90D);
				break;
			default:
				break;
		}
    }
}
