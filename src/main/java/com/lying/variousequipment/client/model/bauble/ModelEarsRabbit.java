package com.lying.variousequipment.client.model.bauble;

import com.lying.variousoddities.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelEarsRabbit<T extends LivingEntity> extends ModelHorns<T>
{
	ModelRenderer earLeft, earRight;
	
	public ModelEarsRabbit()
	{
		super();
		
		this.earLeft = ModelUtils.freshRenderer(this);
		this.earLeft.setRotationPoint(0F, -4F, 0F);
		this.earLeft.mirror = true;
		this.earLeft.setTextureOffset(6, 6).addBox(-2.5F, -9F, 1.0F, 2, 5, 1);
		this.earLeft.rotateAngleY = ModelUtils.toRadians(-15D);
		this.horns.addChild(earLeft);
		
		this.earRight = ModelUtils.freshRenderer(this);
		this.earRight.setRotationPoint(0F, -4F, 0F);
		this.earRight.mirror = true;
		this.earRight.setTextureOffset(0, 6).addBox(0.5F, -9F, 1.0F, 2, 5, 1);
		this.earRight.rotateAngleY = ModelUtils.toRadians(15D);
		this.horns.addChild(earRight);
	}
	
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		headPitch = MathHelper.clamp(headPitch, -25F, 15F);
		this.earLeft.rotateAngleX = ModelUtils.toRadians(headPitch);
		this.earRight.rotateAngleX = ModelUtils.toRadians(headPitch);
	}
}
