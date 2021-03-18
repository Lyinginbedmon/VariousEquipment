package com.lying.variousequipment.client.model.armor;

import com.lying.variousoddities.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelHatArchfey extends BipedModel<LivingEntity>
{
	public ModelRenderer archfeyHat;
	
	public ModelHatArchfey(float scale)
	{
		super(scale);

		this.bipedHead = ModelUtils.freshRenderer(this);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		
		this.bipedHeadwear = ModelUtils.freshRenderer(this);
		
        this.archfeyHat = (new ModelRenderer(this)).setTextureSize(64, 32);
        this.archfeyHat.setRotationPoint(0F, 1F, 0F);
        this.archfeyHat.setTextureOffset(0, 0).addBox(-5F, -10F, -5F, 10, 4, 10);
        this.archfeyHat.setTextureOffset(0, 14).addBox(-5F, -10F, -5F, 10, 4, 10, 0.25F);
        	ModelRenderer point1 = ModelUtils.freshRenderer(this);
        	point1.mirror = true;
        	point1.rotateAngleX = ModelUtils.toRadians(2D);
        	point1.setTextureOffset(40, 5).addBox(4.5F, -11F, 1.5F, 1, 6, 1);
        	point1.setTextureOffset(48, 5).addBox(4.5F, -11F, 1.5F, 1, 6, 1, 0.25F);
        		this.archfeyHat.addChild(point1);
        	ModelRenderer point2 = ModelUtils.freshRenderer(this);
        	point2.rotateAngleX = ModelUtils.toRadians(-2D);
        	point2.rotateAngleZ = ModelUtils.toRadians(-2D);
        	point2.setTextureOffset(40, 5).addBox(-5.5F, -11F, 0.5F, 1, 6, 1);
        	point2.setTextureOffset(44, 5).addBox(-5.5F, -11F, 0.5F, 1, 6, 1, 0.25F);
        		this.archfeyHat.addChild(point2);
        	ModelRenderer point3 = ModelUtils.freshRenderer(this);
        	point3.mirror = true;
        	point3.setTextureOffset(40, 0).addBox(4.5F, -10F, -3F, 1, 4, 1);
        	point3.setTextureOffset(48, 0).addBox(4.5F, -10F, -3F, 1, 4, 1, 0.25F);
        		this.archfeyHat.addChild(point3);
        	ModelRenderer point4 = ModelUtils.freshRenderer(this);
        	point4.setTextureOffset(40, 0).addBox(-5.5F, -10F, -3F, 1, 4, 1);
        	point4.setTextureOffset(44, 0).addBox(-5.5F, -10F, -3F, 1, 4, 1, 0.25F);
        		this.archfeyHat.addChild(point4);
        	ModelRenderer point5 = ModelUtils.freshRenderer(this);
        	point5.mirror = true;
        	point5.rotateAngleZ = ModelUtils.toRadians(-1D);
        	point5.setTextureOffset(40, 12).addBox(-2.5F, -12F, 4.5F, 1, 8, 1);
        	point5.setTextureOffset(48, 12).addBox(-2.5F, -12F, 4.5F, 1, 8, 1, 0.25F);
        		this.archfeyHat.addChild(point5);
        	ModelRenderer point6 = ModelUtils.freshRenderer(this);
        	point6.rotateAngleZ = ModelUtils.toRadians(1D);
        	point6.setTextureOffset(40, 12).addBox(1.5F, -12F, 4.5F, 1, 8, 1);
        	point6.setTextureOffset(48, 12).addBox(1.5F, -12F, 4.5F, 1, 8, 1, 0.25F);
        		this.archfeyHat.addChild(point6);
        		this.bipedHead.addChild(this.archfeyHat);
	}
}
