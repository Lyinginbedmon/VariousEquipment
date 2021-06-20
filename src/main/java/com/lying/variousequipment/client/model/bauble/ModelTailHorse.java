package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailHorse extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	
	public ModelTailHorse()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 9F, 0F);
		this.tail.setTextureOffset(0, 0).addBox(-1.5F, 0F, 0F, 3, 10, 4, -0.35F);
		
		this.bipedBody.addChild(this.tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(60D);
				this.tail.setRotationPoint(0F, 11F, 0F);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(10D);
				break;
			case SLEEPING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(0D);
				break;
			default:
		    	this.tail.rotateAngleX = (float)ModelUtils.toRadians(30D);
				this.tail.setRotationPoint(0F, 9F, 0F);
				break;
		}
		
		this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
    }
}
