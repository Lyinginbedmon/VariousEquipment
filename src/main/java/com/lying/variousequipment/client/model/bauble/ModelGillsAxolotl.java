package com.lying.variousequipment.client.model.bauble;

import com.lying.variousoddities.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelGillsAxolotl<T extends LivingEntity> extends ModelHorns<T>
{
	ModelRenderer top;
	ModelRenderer right;
	ModelRenderer left;
	
	public ModelGillsAxolotl()
	{
		super();
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.horns = ModelUtils.freshRenderer(this);
		this.horns.setRotationPoint(0F, 0F, 0F);
		
		this.top = ModelUtils.freshRenderer(this);
		this.top.setRotationPoint(0F, -8F, 1F);
		this.top.setTextureOffset(0, 0).addBox(-4F, -3F, 0F, 8, 3, 0);
		
		this.right = ModelUtils.freshRenderer(this);
		this.right.setRotationPoint(-4F, 0F, 1F);
		this.right.setTextureOffset(0, 3).addBox(-3F, -10F, 0F, 3, 10, 0);
		
		this.left = ModelUtils.freshRenderer(this);
		this.left.setRotationPoint(4F, 0F, 1F);
		this.left.mirror = true;
		this.left.setTextureOffset(0, 3).addBox(0F, -10F, 0F, 3, 10, 0);
		
		this.horns.addChild(top);
		this.horns.addChild(right);
		this.horns.addChild(left);
	}
	
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		switch(entityIn.getPose())
		{
			case CROUCHING:
			case SWIMMING:
			case FALL_FLYING:
				this.top.rotateAngleX = (float)ModelUtils.toRadians(-21D);
		    	this.right.rotateAngleY = (float)ModelUtils.toRadians(21D);
		    	this.left.rotateAngleY = (float)ModelUtils.toRadians(-21D);
				break;
			default:
		    	this.top.rotateAngleX = (float)ModelUtils.toRadians(21D);
		    	this.right.rotateAngleY = (float)ModelUtils.toRadians(-21D);
		    	this.left.rotateAngleY = (float)ModelUtils.toRadians(21D);
				break;
		}
		this.top.rotateAngleX += ((float)Math.sin(ageInTicks / 20)) * 0.25F;
		this.right.rotateAngleY += ((float)Math.sin(ageInTicks / 20)) * 0.3F;
		this.left.rotateAngleY -= ((float)Math.sin(ageInTicks / 20)) * 0.3F;
	}
}
