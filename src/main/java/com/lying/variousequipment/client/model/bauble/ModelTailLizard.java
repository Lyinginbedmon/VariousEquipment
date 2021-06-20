package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailLizard extends ModelTail<LivingEntity>
{
	ModelRenderer tail1, tail2, tail3;
	
	public ModelTailLizard()
	{
		super();
		
		tail1 = ModelUtils.freshRenderer(this);
		tail1.setRotationPoint(0F, 10F, 2F);
		tail1.setTextureOffset(0, 0).addBox(-1F, -0.5F, -1F, 2, 4, 2);
		tail1.setTextureOffset(0, 9).addBox(-1F, -0.5F, -1F, 2, 4, 2, 0.3F);
		
		tail2 = ModelUtils.freshRenderer(this);
		tail2.setRotationPoint(0F, 3F, 0F);
		tail2.setTextureOffset(8, 0).addBox(-1F, 0F, -1F, 2, 8, 2, -0.05F);
			tail1.addChild(tail2);
		
		tail3 = ModelUtils.freshRenderer(this);
		tail3.setRotationPoint(0F, 7.5F, 0F);
		tail3.setTextureOffset(16, 0).addBox(-1F, 0F, -1F, 2, 4, 2, -0.1F);
		tail3.setTextureOffset(16, 9).addBox(-0.5F, 3.75F, -0.5F, 1, 5, 1);
			tail2.addChild(tail3);
		
		ModelRenderer barb1R = ModelUtils.freshRenderer(this);
		barb1R.setTextureOffset(20, 9).addBox(-0.8F, 0F, 0F, 2, 4, 0);
		barb1R.rotateAngleZ = ModelUtils.toRadians(40D);
			tail3.addChild(barb1R);
		
		ModelRenderer barb1L = ModelUtils.freshRenderer(this);
		barb1L.mirror = true;
		barb1L.setTextureOffset(20, 9).addBox(-1.2F, 0F, 0F, 2, 4, 0);
		barb1L.rotateAngleZ = ModelUtils.toRadians(-40D);
			tail3.addChild(barb1L);
		
		this.bipedBody.addChild(tail1);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail1.rotateAngleX = ModelUtils.toRadians(80D);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail1.rotateAngleX = ModelUtils.toRadians(20D);
				break;
			case SLEEPING:
				this.tail1.rotateAngleX = ModelUtils.toRadians(180D);
				break;
			default:
				this.tail1.rotateAngleX = ModelUtils.toRadians(60D);
				break;
		}
		
		this.tail1.rotateAngleX += (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F) + ModelUtils.toRadians(15D) * limbSwingAmount;
		this.tail2.rotateAngleX = Math.abs(this.tail1.rotateAngleX) * -0.666F;
		this.tail3.rotateAngleX = this.tail2.rotateAngleX * -1.3F;
		
		this.tail1.rotateAngleY = ModelUtils.toRadians(45D) * MathHelper.cos(limbSwing) * limbSwingAmount * 0.5F;
    }
}
