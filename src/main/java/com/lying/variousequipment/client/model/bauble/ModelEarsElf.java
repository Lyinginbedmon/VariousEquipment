package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelEarsElf<T extends LivingEntity> extends ModelHorns<T>
{
	public ModelEarsElf()
	{
		super();
		
			ModelRenderer earLeft1 = ModelUtils.freshRenderer(this);
			earLeft1.setRotationPoint(4F, -5.5F, 0F);
			earLeft1.setTextureOffset(0, 0).addBox(-0.5F, 0F, -0.5F, 1, 1, 4);
			earLeft1.rotateAngleX = ModelUtils.toRadians(13D);
			earLeft1.rotateAngleY = ModelUtils.toRadians(22D);
		this.horns.addChild(earLeft1);
			ModelRenderer earLeft2 = ModelUtils.freshRenderer(this);
			earLeft2.setRotationPoint(4F, -4.5F, 0F);
			earLeft2.setTextureOffset(0, 2).addBox(0F, 0F, 0F, 0, 2, 3);
			earLeft2.rotateAngleX = ModelUtils.toRadians(13D);
			earLeft2.rotateAngleY = ModelUtils.toRadians(22D);
		this.horns.addChild(earLeft2);
		
			ModelRenderer earRight1 = ModelUtils.freshRenderer(this);
			earRight1.setRotationPoint(-4F, -5.5F, 0F);
			earRight1.setTextureOffset(0, 0).addBox(-0.5F, 0F, -0.5F, 1, 1, 4);
			earRight1.rotateAngleX = ModelUtils.toRadians(13D);
			earRight1.rotateAngleY = ModelUtils.toRadians(-22D);
		this.horns.addChild(earRight1);
			ModelRenderer earRight2 = ModelUtils.freshRenderer(this);
			earRight2.setRotationPoint(-4F, -4.5F, 0F);
			earRight2.setTextureOffset(0, 2).addBox(0F, 0F, 0F, 0, 2, 3);
			earRight2.rotateAngleX = ModelUtils.toRadians(13D);
			earRight2.rotateAngleY = ModelUtils.toRadians(-22D);
		this.horns.addChild(earRight2);
	}
}
