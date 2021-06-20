package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailWolf extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	
	public ModelTailWolf()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 10F, 1.5F);
		this.tail.setTextureOffset(0, 0).addBox(-1F, 0F, -1F, 2, 8, 2);
		
		this.bipedBody.addChild(tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(60D);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(20D);
				break;
			case SLEEPING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(90D);
				break;
			default:
		    	this.tail.rotateAngleX = (float)ModelUtils.toRadians(50D);
				break;
		}
    }
}
