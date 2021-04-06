package com.lying.variousequipment.client.model.armor;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelHatWitch extends BipedModel<LivingEntity>
{
	public ModelRenderer witchHat;
	
	public ModelHatWitch(float scale)
	{
		super(scale);
		
		this.bipedHead = ModelUtils.freshRenderer(this);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		
		this.bipedHeadwear = ModelUtils.freshRenderer(this);
		
        this.witchHat = (new ModelRenderer(this)).setTextureSize(64, 32);
        	ModelRenderer hat0 = (new ModelRenderer(this)).setTextureSize(64, 32);
        	hat0.setTextureOffset(0, 0).addBox(-8F, -8F, -8.5F, 16, 2, 16);
        	hat0.rotateAngleX = -ModelUtils.toRadians(7D);
        		this.witchHat.addChild(hat0);
	        ModelRenderer hat1 = (new ModelRenderer(this)).setTextureSize(64, 32);
	        hat1.setTextureOffset(0, 18).addBox(-4.5F, -11.0F, -5.0F, 9, 4, 9);
	        hat1.rotateAngleX = -ModelUtils.toRadians(8D);
	        	this.witchHat.addChild(hat1);
	        ModelRenderer hat2 = (new ModelRenderer(this)).setTextureSize(64, 32);
	        hat2.setTextureOffset(36, 18).addBox(-2.0F, -14.0F, -3.0F, 4, 4, 5);
	        hat2.rotateAngleX = -ModelUtils.toRadians(13D);
	        	this.witchHat.addChild(hat2);
	        ModelRenderer hat3 = (new ModelRenderer(this)).setTextureSize(64, 32);
	        hat3.setTextureOffset(0, 0).addBox(-1.0F, -16.0F, -1.0F, 2, 2, 4);
	        hat3.rotateAngleX = -ModelUtils.toRadians(17D);
	        	this.witchHat.addChild(hat3);
        		this.bipedHead.addChild(this.witchHat);
	}
}
