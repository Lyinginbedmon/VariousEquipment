package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousoddities.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelLegsSpider extends BipedModel<LivingEntity>
{
	private ModelRenderer spiderBody;
	private ModelRenderer spiderThorax;
	
	private ModelRenderer[] spiderLegs;
	
	public ModelLegsSpider()
	{
		super(0F);
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.bipedBody = ModelUtils.freshRenderer(this);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
		
		this.spiderBody = ModelUtils.freshRenderer(this);
		this.spiderBody.setRotationPoint(0F, 15F, 0F);
		this.spiderBody.setTextureOffset(0, 0).addBox(-3F, -3F, -3F, 6, 6, 6, 0.5F);
			this.bipedBody.addChild(spiderBody);
		
		this.spiderThorax = ModelUtils.freshRenderer(this);
		this.spiderThorax.setRotationPoint(0F, 0F, 3F);
		this.spiderThorax.setTextureOffset(0, 12).addBox(-5F, -4F, 0F, 10, 8, 12);
			this.spiderBody.addChild(this.spiderThorax);
		
		this.spiderLegs = new ModelRenderer[8];
		for(int i=0; i<spiderLegs.length; i++)
		{
			ModelRenderer leg = ModelUtils.freshRenderer(this);
			leg.setRotationPoint(4F * i%2 == 0 ? -1F : 1F, 0F, 2F - (float)Math.floorDiv(i, 2));
			leg.setTextureOffset(18, 0).addBox(i%2 == 0 ? -15F : -1F, -1F, -1F, 16, 2, 2);
			
			this.spiderLegs[i] = leg;
			this.spiderBody.addChild(leg);
		}
	}
	
	protected Iterable<ModelRenderer> getHeadParts(){ return ImmutableList.of(); }
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedBody);
	}
	
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.spiderBody.rotateAngleX = -this.bipedBody.rotateAngleX;
		
		switch(entityIn.getPose())
		{
			case FALL_FLYING:
			case SLEEPING:
			case SWIMMING:
				this.spiderBody.rotateAngleX -= ModelUtils.toRadians(90D);
				this.spiderThorax.rotateAngleX = ModelUtils.toRadians(10D);
				break;
			case CROUCHING:
			case DYING:
			case SPIN_ATTACK:
			case STANDING:
			default:
				this.spiderThorax.rotateAngleX = this.isSneak ? ModelUtils.toRadians(45D) : 0F;
				break;
		}
		
		for(int i=0; i<spiderLegs.length; i++)
		{
			ModelRenderer leg = spiderLegs[i];
			int row = Math.floorDiv(i, 2);
			int col = i%2;
			leg.rotateAngleZ = (row == 0 || row == 3 ? -ModelUtils.toRadians(45D) : -ModelUtils.toRadians(33.3D)) * (col == 0 ? 1 : -1);
			leg.rotateAngleY = (i > 1 && i < 6 ? ModelUtils.toRadians(22.5D) : ModelUtils.toRadians(45D)) * ((col == 0 ? 1 : -1) * (i > 3 ? -1 : 1));
		}
		
		float[] legYAngles = new float[4];
		legYAngles[0] = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		legYAngles[1] = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		legYAngles[2] = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		legYAngles[3] = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
		
		float[] legZAngles = new float[4];
		legZAngles[0] = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		legZAngles[1] = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		legZAngles[2] = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		legZAngles[3] = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * limbSwingAmount;
		
		for(int i=0; i<spiderLegs.length; i++)
		{
			ModelRenderer leg = spiderLegs[i];
			leg.rotateAngleY += legYAngles[i / 2] * (i%2==0 ? 1F : -1F);
			leg.rotateAngleZ += legZAngles[i / 2] * (i%2==0 ? 1F : -1F);
		}
	}
}
