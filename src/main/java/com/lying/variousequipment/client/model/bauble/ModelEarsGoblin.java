package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelEarsGoblin<T extends LivingEntity> extends ModelHorns<T>
{
	public ModelEarsGoblin()
	{
		super();
		
			ModelRenderer earLeft = ModelUtils.freshRenderer(this);
			earLeft.setTextureOffset(0, 0).addBox(-0.5F, 0F, -0.5F, 1, 7, 1);
			earLeft.setTextureOffset(4, -5).addBox(0F, -0.5F, -5F, 0, 7, 5);
			earLeft.rotateAngleX = ModelUtils.degree90 + ModelUtils.toRadians(15D);
			earLeft.rotateAngleY = -ModelUtils.toRadians(30D);
			earLeft.setRotationPoint(-3.5F, -6F, -2F);
		this.horns.addChild(earLeft);
			ModelRenderer earRight = ModelUtils.freshRenderer(this);
			earRight.setTextureOffset(14, 0).addBox(-0.5F, 0F, -0.5F, 1, 7, 1);
			earRight.setTextureOffset(18, -5).addBox(0F, -0.5F, -5F, 0, 7, 5);
			earRight.rotateAngleX = ModelUtils.degree90 + ModelUtils.toRadians(15D);
			earRight.rotateAngleY = ModelUtils.toRadians(30D);
			earRight.setRotationPoint(3.5F, -6F, -2F);
		this.horns.addChild(earRight);
	}
}
