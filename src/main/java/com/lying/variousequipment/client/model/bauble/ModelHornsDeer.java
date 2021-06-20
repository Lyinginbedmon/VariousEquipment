package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelHornsDeer<T extends LivingEntity> extends ModelHorns<T>
{
	public ModelHornsDeer()
	{
		super();
		
		// Antlers
		ModelRenderer leftRoot = ModelUtils.freshRenderer(this);
		leftRoot.mirror = true;
		leftRoot.setTextureOffset(0, 0).addBox(1F, -11F, -4.1F, 1, 4, 1);
		leftRoot.rotateAngleX = ModelUtils.toRadians(-7D);
		leftRoot.rotateAngleZ = ModelUtils.toRadians(7D);
		
		ModelRenderer leftBranch = ModelUtils.freshRenderer(this);
		leftBranch.mirror = true;
		leftBranch.setTextureOffset(4, 0).addBox(-8F, -9.5F, -5.4F, 1, 5, 1);
		leftBranch.rotateAngleX = ModelUtils.toRadians(-26D);
		leftBranch.rotateAngleZ = ModelUtils.toRadians(67D);
		
		ModelRenderer leftBranch2 = ModelUtils.freshRenderer(this);
		leftBranch2.mirror = true;
		leftBranch2.setTextureOffset(8, 0).addBox(5F, -15.5F, -5.3F, 1, 7, 1);
		leftBranch2.rotateAngleX = ModelUtils.toRadians(-22D);
		leftBranch2.rotateAngleZ = ModelUtils.toRadians(-5D);
		
		ModelRenderer leftPoint = ModelUtils.freshRenderer(this);
		leftPoint.mirror = true;
		leftPoint.setTextureOffset(12, 0).addBox(-4F, -16F, -2F, 1, 3, 1);
		leftPoint.rotateAngleX = ModelUtils.toRadians(-6D);
		leftPoint.rotateAngleZ = ModelUtils.toRadians(35D);
		
		this.horns.addChild(leftRoot);
		this.horns.addChild(leftBranch);
		this.horns.addChild(leftBranch2);
		this.horns.addChild(leftPoint);
		
		ModelRenderer rightRoot = ModelUtils.freshRenderer(this);
		rightRoot.setTextureOffset(0, 0).addBox(-2F, -11F, -4.1F, 1, 4, 1);
		rightRoot.rotateAngleX = ModelUtils.toRadians(-7D);
		rightRoot.rotateAngleZ = ModelUtils.toRadians(-7D);
		
		ModelRenderer rightBranch = ModelUtils.freshRenderer(this);
		rightBranch.setTextureOffset(4, 0).addBox(7F, -9.5F, -5.4F, 1, 5, 1);
		rightBranch.rotateAngleX = ModelUtils.toRadians(-26D);
		rightBranch.rotateAngleZ = ModelUtils.toRadians(-67D);
		
		ModelRenderer rightBranch2 = ModelUtils.freshRenderer(this);
		rightBranch2.setTextureOffset(8, 0).addBox(-6F, -15.5F, -5.3F, 1, 7, 1);
		rightBranch2.rotateAngleX = ModelUtils.toRadians(-22D);
		rightBranch2.rotateAngleZ = ModelUtils.toRadians(5D);
		
		ModelRenderer rightPoint = ModelUtils.freshRenderer(this);
		rightPoint.setTextureOffset(12, 0).addBox(3F, -16F, -2F, 1, 3, 1);
		rightPoint.rotateAngleX = ModelUtils.toRadians(-6D);
		rightPoint.rotateAngleZ = ModelUtils.toRadians(-35D);
		
		this.horns.addChild(rightRoot);
		this.horns.addChild(rightBranch);
		this.horns.addChild(rightBranch2);
		this.horns.addChild(rightPoint);
	}
}
