package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailCat2 extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	ModelRenderer tail2;
	
	ModelRenderer tail2a;
	ModelRenderer tail2b;
	
	public ModelTailCat2()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 9.5F, 2F);
		this.tail.setTextureOffset(0, 0).addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
		
		this.tail2 = ModelUtils.freshRenderer(this);
		this.tail2.setRotationPoint(0F, 8F, 0F);
			this.tail2a = ModelUtils.freshRenderer(this);
			this.tail2a.setTextureOffset(4, 0).addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
			this.tail2a.rotateAngleZ = ModelUtils.toRadians(22.5D);
			this.tail2.addChild(tail2a);
			this.tail2b = ModelUtils.freshRenderer(this);
			this.tail2b.setTextureOffset(4, 0).addBox(-0.5F, 0F, -0.5F, 1, 8, 1);
			this.tail2b.rotateAngleZ = ModelUtils.toRadians(-22.5D);
			this.tail2.addChild(tail2b);
		
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
		
		tail2a.rotateAngleZ = ModelUtils.toRadians(22.5D) + (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F) * 1;
		tail2a.rotateAngleX = (MathHelper.sin(ageInTicks * 0.067F) * 0.05F) * -1;
		
		tail2b.rotateAngleZ = ModelUtils.toRadians(-22.5D) + (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F) * -1;
		tail2b.rotateAngleX = (MathHelper.sin(ageInTicks * 0.067F) * 0.05F) * 1;
    }
}
