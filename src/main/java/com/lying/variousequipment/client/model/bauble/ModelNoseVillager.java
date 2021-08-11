package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelNoseVillager<T extends LivingEntity> extends ModelHorns<T>
{
	protected ModelRenderer nose;
	
	public ModelNoseVillager()
	{
		super();
		
		this.nose = ModelUtils.freshRenderer(this);
		this.nose.setRotationPoint(0F, -2.5F, 0F);
		this.nose.setTextureOffset(0, 0).addBox(-1F, -1F, -6F, 2, 4, 2);
		
		ModelRenderer mole = ModelUtils.freshRenderer(this);
		mole.setRotationPoint(0F, -3F, 0F);
		mole.setTextureOffset(8, 0).addBox(0F, 3F, -6.75F, 1F, 1F, 1F, -0.25F);
		this.nose.addChild(mole);
		
		this.horns.addChild(this.nose);
	}
	
	public static class Witch<T extends LivingEntity> extends ModelNoseVillager<T>
	{
		public Witch()
		{
			super();
		}
		
		public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
		{
			float f = 0.01F * (float)(entityIn.getEntityId() % 10);
			this.nose.rotateAngleX = MathHelper.sin((float)entityIn.ticksExisted * f) * 4.5F * ((float)Math.PI / 180F);
			this.nose.rotateAngleY = 0.0F;
			this.nose.rotateAngleZ = MathHelper.cos((float)entityIn.ticksExisted * f) * 2.5F * ((float)Math.PI / 180F);
		}
	}
}
