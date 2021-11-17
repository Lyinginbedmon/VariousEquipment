package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelLegsPeg1 extends ModelLegsPeg
{
	public ModelLegsPeg1()
	{
		super(0F);
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12F, 0F);
		this.bipedRightLeg.setTextureOffset(0, 0).addBox(-2F, 6F, -2F, 4, 2, 4);
		this.bipedRightLeg.setTextureOffset(0, 6).addBox(-2F, 5.5F, -2F, 4F, 1F, 4F, 0.5F);
			ModelRenderer pegR = ModelUtils.freshRenderer(this);
			pegR.setTextureOffset(16, 0).addBox(-0.5F, 8F, -0.5F, 1, 4, 1);
			pegR.rotateAngleY = ModelUtils.toRadians(45D);
				this.bipedRightLeg.addChild(pegR);
		
		this.bipedLeftLeg = ModelUtils.freshRenderer(this);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.setRotationPoint(1.9F, 12F, 0F);
		this.bipedLeftLeg.setTextureOffset(0, 0).addBox(-2F, 6F, -2F, 4, 2, 4);
		this.bipedLeftLeg.setTextureOffset(0, 6).addBox(-2F, 5.5F, -2F, 4F, 1F, 4F, 0.5F);
			ModelRenderer pegL = ModelUtils.freshRenderer(this);
			pegL.mirror = true;
			pegL.setTextureOffset(16, 0).addBox(-0.5F, 8F, -0.5F, 1, 4, 1);
			pegL.rotateAngleY = ModelUtils.toRadians(45D);
				this.bipedLeftLeg.addChild(pegL);
	}
}
