package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelLegsPeg2 extends ModelLegsPeg
{
	public ModelRenderer bipedRightLegwear;
	public ModelRenderer bipedLeftLegwear;
	
	public ModelLegsPeg2()
	{
		super(RenderType::getEntityTranslucent, 0F, 0F, 64, 64);
		
		this.bipedRightLeg = ModelUtils.freshRenderer(this);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12F, 0F);
		this.bipedRightLeg.setTextureOffset(0, 16).addBox(-2F, 0F, -2F, 4, 6, 4);
		this.bipedRightLegwear = ModelUtils.freshRenderer(this);
		this.bipedRightLegwear.setTextureOffset(0, 32).addBox(-2F, 0F, -2F, 4, 6, 4, 0.25F);
		this.bipedRightLeg.addChild(bipedRightLegwear);
		
		this.bipedLeftLeg = ModelUtils.freshRenderer(this);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12F, 0F);
		this.bipedLeftLeg.setTextureOffset(16, 48).addBox(-2F, 0F, -2F, 4, 6, 4);
		this.bipedLeftLegwear = ModelUtils.freshRenderer(this);
		this.bipedLeftLegwear.setTextureOffset(0, 48).addBox(-2F, 0F, -2F, 4, 6, 4, 0.25F);
		this.bipedLeftLeg.addChild(bipedLeftLegwear);
	}
}
