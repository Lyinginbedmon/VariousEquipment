package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailDragonfly extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	
	public ModelTailDragonfly()
	{
		super();
		
		tail = ModelUtils.freshRenderer(this);
		tail.setRotationPoint(0F, 11F, 1F);
		tail.setTextureOffset(0, 0).addBox(-1.5F, 0F, -1.5F, 3, 12, 3, 0.2F);
		tail.setTextureOffset(12, 0).addBox(-1.5F, 0F, -1.5F, 3, 12, 3, 0.5F);
		
		this.bipedBody.addChild(tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		float wiggleMag = 1F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = ModelUtils.toRadians(32D);
				wiggleMag = 0.5F;
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = ModelUtils.toRadians(5D);
				wiggleMag = 0.7F;
				break;
			case SLEEPING:
				this.tail.rotateAngleX = ModelUtils.toRadians(180D);
				wiggleMag = 0F;
				break;
			default:
				this.tail.rotateAngleX = ModelUtils.toRadians(60D);
				break;
		}
		
		this.tail.rotateAngleY = (MathHelper.cos(ageInTicks * 0.09F) * ModelUtils.toRadians(5D)) * wiggleMag;
    }
}
