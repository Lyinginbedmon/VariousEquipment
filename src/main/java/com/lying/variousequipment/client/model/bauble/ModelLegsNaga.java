package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelLegsNaga extends BipedModel<LivingEntity>
{
	public ModelRenderer tail1;
	public ModelRenderer tail2;
	public ModelRenderer tail3;
	public ModelRenderer tail4;
	public ModelRenderer tail5;
	public ModelRenderer tail6;
	public ModelRenderer tail7;
	public ModelRenderer tail8;
	
	private ModelRenderer[] upperTail;
	private ModelRenderer[] lowerTail;
	
	private ModelRenderer sittingModel;
	private ModelRenderer coil5;
	
	public ModelLegsNaga()
	{
		super(1F);
		this.textureHeight = 64;
		this.textureWidth = 64;
		
		this.tail1 = ModelUtils.freshRenderer(this);
		this.tail1.setRotationPoint(0F, 12F, 0F);
		this.tail1.setTextureOffset(0, 0).addBox(-4F, -1F, -2.5F, 8, 4, 4, 0.1F);
		this.tail1.rotateAngleX = ModelUtils.toRadians(-7.5D);
		
		this.tail2 = ModelUtils.freshRenderer(this);
		this.tail2.setRotationPoint(0F, 3F, -2.5F);
		this.tail2.setTextureOffset(0, 8).addBox(-3.5F, 0F, 0F, 7, 4, 4);
		this.tail2.rotateAngleX = ModelUtils.toRadians(30D);
		this.tail1.addChild(tail2);
		
		this.tail3 = ModelUtils.freshRenderer(this);
		this.tail3.setRotationPoint(0F, 4F, 0F);
		this.tail3.setTextureOffset(0, 16).addBox(-3F, 0F, 0F, 6, 4, 4);
		this.tail2.addChild(tail3);
		
		this.tail4 = ModelUtils.freshRenderer(this);
		this.tail4.setRotationPoint(0F, 4F, 0F);
		this.tail4.setTextureOffset(0, 24).addBox(-3F, 0, 0F, 6, 4, 4);
		this.tail4.rotateAngleX = ModelUtils.toRadians(22.5D);
		this.tail3.addChild(tail4);
		
		this.tail5 = ModelUtils.freshRenderer(this);
		this.tail5.setRotationPoint(0F, 4F, 0F);
		this.tail5.setTextureOffset(0, 32).addBox(-2.5F, 0F, 0.5F, 5, 4, 3);
		this.tail5.rotateAngleX = ModelUtils.toRadians(-45D);
		this.tail4.addChild(tail5);
		
		this.tail6 = ModelUtils.freshRenderer(this);
		this.tail6.setRotationPoint(0F, 4F, 0.5F);
		this.tail6.setTextureOffset(0, 39).addBox(-2F, 0F, 0F, 4, 4, 3);
		this.tail5.addChild(tail6);
		
		this.tail7 = ModelUtils.freshRenderer(this);
		this.tail7.setRotationPoint(0F, 4F, 0F);
		this.tail7.setTextureOffset(0, 46).addBox(-1.5F, 0F, 0.5F, 3, 3, 2);
		this.tail6.addChild(tail7);
		
		this.tail8 = ModelUtils.freshRenderer(this);
		this.tail8.setRotationPoint(0F, 3F, 0.5F);
		this.tail8.setTextureOffset(0, 51).addBox(-1F, 0F, 0F, 2, 2, 2);
		this.tail8.rotateAngleX = ModelUtils.toRadians(22.5D);
		this.tail7.addChild(tail8);
		
		this.upperTail = new ModelRenderer[]{this.tail1, this.tail2, this.tail3, this.tail4};
		this.lowerTail = new ModelRenderer[]{this.tail5, this.tail6, this.tail7, this.tail8};
		
		this.sittingModel = ModelUtils.freshRenderer(this);
		this.sittingModel.setRotationPoint(0F, 12F, 0F);
		this.sittingModel.setTextureOffset(24, 24).addBox(-6.5F, 1.5F, -6F, 6, 3, 7);	// aka coil4
			ModelRenderer coil1 = ModelUtils.freshRenderer(this);
			coil1.setTextureOffset(24, 0).addBox(-4.5F, -1F, -3F, 9, 3, 4, 0.1F);
			coil1.rotateAngleX = ModelUtils.toRadians(-16D);
				this.sittingModel.addChild(coil1);
			ModelRenderer coil2 = ModelUtils.freshRenderer(this);
			coil2.setTextureOffset(24, 7).addBox(-5F, 0F, -4F, 12, 4, 5);
			coil2.rotateAngleZ = ModelUtils.toRadians(3D);
				this.sittingModel.addChild(coil2);
			ModelRenderer coil3 = ModelUtils.freshRenderer(this);
			coil3.setTextureOffset(24, 16).addBox(-7F, -0.5F, 1F, 14, 4, 4);
			coil3.rotateAngleZ = ModelUtils.toRadians(-4D);
				this.sittingModel.addChild(coil3);
			coil5 = ModelUtils.freshRenderer(this);
			coil5.setRotationPoint(-1F, 3F, -5F);
			coil5.setTextureOffset(0, 51).addBox(0F, -1F, -1F, 2, 2, 2, 0.1F);
			coil5.rotateAngleX = ModelUtils.degree90;
			coil5.rotateAngleZ = ModelUtils.toRadians(-16D);
				this.sittingModel.addChild(coil5);
	}
	
	protected Iterable<ModelRenderer> getHeadParts()
	{
		return ImmutableList.of();
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.tail1, this.sittingModel);
	}
	
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		if(!this.isSitting)
		{
			this.sittingModel.showModel = false;
			this.tail1.showModel = true;
			resetUpperTail();
			resetLowerTail(ageInTicks);
			
			switch(entityIn.getPose())
			{
				case SWIMMING:
				case FALL_FLYING:
					for(ModelRenderer part : this.upperTail)
						part.rotateAngleX = ModelUtils.toRadians(30D / 5D);
					
					for(ModelRenderer part : this.lowerTail)
						part.rotateAngleX = ModelUtils.toRadians(-6D);
					
					this.tail8.rotateAngleX = ModelUtils.toRadians(15D);
					break;
				case SLEEPING:
					for(ModelRenderer part : this.upperTail)
						part.rotateAngleX = 0F;
					
					for(ModelRenderer part : this.lowerTail)
						part.rotateAngleX = ModelUtils.toRadians(5D);
					break;
				case CROUCHING:
					this.tail1.setRotationPoint(0F, 14.5F, 5F);
					for(ModelRenderer part : this.upperTail)
						part.rotateAngleX = ModelUtils.toRadians(180D / 4D);
					this.tail1.rotateAngleX -= ModelUtils.toRadians(90D);
					
					for(ModelRenderer part : this.lowerTail)
						part.rotateAngleX = ModelUtils.toRadians(120D / 4D);
					break;
				case SPIN_ATTACK:
				case DYING:
				case STANDING:
				default:
					this.tail5.rotateAngleX = ModelUtils.toRadians(90D - 45D);
					break;
			}
			
			double wiggle = limbSwingAmount * Math.sin(limbSwing);
			for(ModelRenderer part : this.lowerTail)
				part.rotateAngleZ = ModelUtils.toRadians(15D * wiggle);
			
			for(ModelRenderer part : this.upperTail)
				part.rotateAngleZ = ModelUtils.toRadians(5D * wiggle);
		}
		else
		{
			this.sittingModel.showModel = true;
			this.tail1.showModel = false;
			
	    	float time = (((float)Math.sin(ageInTicks / 20)) * 0.5F) * 0.5F;
	    	this.coil5.rotateAngleZ = (time / 3) + ModelUtils.toRadians(-16D);
		}
	}
	
	private void resetUpperTail()
	{
		this.tail1.setRotationPoint(0F, 12F, 0F);
		this.tail2.setRotationPoint(0F, 3F, -2.5F);
		this.tail3.setRotationPoint(0F, 4F, 0F);
		this.tail4.setRotationPoint(0F, 4F, 0F);
		
		this.tail1.rotateAngleX = ModelUtils.toRadians(-7.5D);
		this.tail2.rotateAngleX = ModelUtils.toRadians(30D);
		this.tail3.rotateAngleX = 0F;
		this.tail4.rotateAngleX = ModelUtils.toRadians(22.5D);
	}
	
	private void resetLowerTail(float ageInTicks)
	{
		this.tail5.rotateAngleX = ModelUtils.toRadians(-45D);
		this.tail6.rotateAngleX = 0F;
		this.tail7.rotateAngleX = 0F;
		
    	float time = (((float)Math.sin(ageInTicks / 20)) * 0.5F) * 0.5F;
		this.tail8.rotateAngleX = ModelUtils.toRadians(22.5D) + (time / 3);
	}
}
