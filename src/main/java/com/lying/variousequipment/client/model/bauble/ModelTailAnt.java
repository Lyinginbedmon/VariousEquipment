package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailAnt extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	
	public ModelTailAnt()
	{
		super();
		
		tail = ModelUtils.freshRenderer(this);
		tail.setRotationPoint(0F, 11F, 2F);
		tail.setTextureOffset(0, 0).addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
		tail.setTextureOffset(0, 4).addBox(-2.5F, 1F, -2.5F, 5, 6, 5);
		tail.setTextureOffset(20, 4).addBox(-2.5F, 1F, -2.5F, 5, 6, 5, 0.3F);
		tail.setTextureOffset(12, 0).addBox(-1F, 7F, -1F, 2, 1, 2);
		
		this.bipedBody.addChild(tail);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		float wiggleMag = 1F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = ModelUtils.toRadians(85D);
				wiggleMag = 0.5F;
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = ModelUtils.toRadians(15D);
				wiggleMag = 0.7F;
				break;
			case SLEEPING:
				this.tail.rotateAngleX = ModelUtils.toRadians(180D);
				wiggleMag = 0F;
				break;
			default:
				this.tail.rotateAngleX = ModelUtils.toRadians(80D);
				break;
		}
		
		this.tail.rotateAngleX += (MathHelper.cos(ageInTicks * 0.09F) * ModelUtils.toRadians(5D)) * wiggleMag;
		this.tail.rotateAngleY = ((float)Math.PI / 4F) * MathHelper.cos(limbSwing) * limbSwingAmount * 0.5F;
    }
}
