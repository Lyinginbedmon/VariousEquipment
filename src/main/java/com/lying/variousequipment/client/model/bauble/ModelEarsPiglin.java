package com.lying.variousequipment.client.model.bauble;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelEarsPiglin<T extends LivingEntity> extends ModelHorns<T>
{
	ModelRenderer earRight, earLeft;
	
	public ModelEarsPiglin()
	{
		super();
		
		this.earLeft = new ModelRenderer(this);
		this.earLeft.setRotationPoint(4F, -6.0F, 0.0F);
		this.earLeft.setTextureOffset(10, 0).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F);
		this.horns.addChild(this.earLeft);
		
		this.earRight = new ModelRenderer(this);
		this.earRight.setRotationPoint(-4F, -6.0F, 0.0F);
		this.earRight.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F);
		this.horns.addChild(this.earRight);
	}
	
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		float f1 = ageInTicks * 0.1F + limbSwing * 0.5F;
		float f2 = 0.08F + limbSwingAmount * 0.4F;
		this.earLeft.rotateAngleZ = (-(float)Math.PI / 6F) - MathHelper.cos(f1 * 1.2F) * f2;
		this.earRight.rotateAngleZ = ((float)Math.PI / 6F) + MathHelper.cos(f1) * f2;
	}
}
