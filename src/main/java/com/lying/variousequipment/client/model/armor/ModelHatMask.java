package com.lying.variousequipment.client.model.armor;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelHatMask extends BipedModel<LivingEntity>
{
	public ModelHatMask(float scale)
	{
		super(scale);
		
        this.bipedHead = ModelUtils.freshRenderer(this);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.bipedHead.setTextureOffset(0, 0).addBox(-4F, -7F, -4F, 8, 4, 2, 0.25F);
        
        ModelRenderer horn = ModelUtils.freshRenderer(this);
        horn.rotateAngleX = ModelUtils.toRadians(45D);
        horn.setTextureOffset(4, 6).addBox(-1F, -8.5F, 1F, 2, 2, 2);
        horn.setTextureOffset(0, 6).addBox(-0.5F, -12F, 1.5F, 1, 4, 1);
        	this.bipedHead.addChild(horn);
        
        this.bipedHeadwear = ModelUtils.freshRenderer(this);
        this.bipedBody = ModelUtils.freshRenderer(this);
        this.bipedRightArm = ModelUtils.freshRenderer(this);
        this.bipedLeftArm = ModelUtils.freshRenderer(this);
        this.bipedRightLeg = ModelUtils.freshRenderer(this);
        this.bipedLeftLeg = ModelUtils.freshRenderer(this);
	}
}
