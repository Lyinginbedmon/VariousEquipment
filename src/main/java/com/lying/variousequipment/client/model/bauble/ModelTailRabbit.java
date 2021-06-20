package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailRabbit extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	
	public ModelTailRabbit()
	{
		super();
		
		tail = ModelUtils.freshRenderer(this);
		tail.setRotationPoint(0F, 10F, 2F);
		tail.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, -0.2F, 3, 3, 2);
		tail.rotateAngleX = ModelUtils.toRadians(6D);
		
		this.bipedBody.addChild(tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		float wiggleMag = 1F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				wiggleMag = 0.5F;
				break;
			case SWIMMING:
			case FALL_FLYING:
				wiggleMag = 0.7F;
				break;
			case SLEEPING:
				wiggleMag = 0F;
				break;
			default:
				break;
		}
		
		this.tail.rotateAngleY = (MathHelper.cos(ageInTicks * 0.09F) * ModelUtils.toRadians(5D)) * wiggleMag;
    }
}
