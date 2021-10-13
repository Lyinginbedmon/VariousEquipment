package com.lying.variousequipment.client.model.entity;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;
import com.lying.variousequipment.entity.EntityScarecrow;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelScarecrow extends BipedModel<EntityScarecrow>
{
	public ModelScarecrow(float modelSize)
	{
		super(1F);
		
		textureHeight = 64;
		textureWidth = 64;
		
		bipedHead = ModelUtils.freshRenderer(this);
		bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8);
		bipedHead.setTextureOffset(32, 0).addBox(-4F, -8F, -4F, 8, 8, 8, 0.5F);
			// Head edge straws
			ModelRenderer headStraws1 = ModelUtils.freshRenderer(this);
			headStraws1.rotateAngleY = ModelUtils.toRadians(45D);
			headStraws1.setTextureOffset(8, 16).addBox(5.5F, -8F, 0F, 2, 8, 0);
			headStraws1.mirror = true;
			headStraws1.setTextureOffset(8, 16).addBox(-7.5F, -8F, 0F, 2, 8, 0);
				bipedHead.addChild(headStraws1);
			ModelRenderer headStraws2 = ModelUtils.freshRenderer(this);
			headStraws2.rotateAngleY = ModelUtils.toRadians(-45D);
			headStraws2.setTextureOffset(8, 24).addBox(5.5F, -8F, 0F, 2, 8, 0);
			headStraws2.mirror = true;
			headStraws2.setTextureOffset(8, 24).addBox(-7.5F, -8F, 0F, 2, 8, 0);
				bipedHead.addChild(headStraws2);
			
			ModelRenderer headStraws3 = ModelUtils.freshRenderer(this);
			headStraws3.rotateAngleX = ModelUtils.toRadians(-45D);
			headStraws3.setTextureOffset(0, 42).addBox(-4F, 2.5F, -3F, 8, 3, 0);
				bipedHead.addChild(headStraws3);
			ModelRenderer headStraws4 = ModelUtils.freshRenderer(this);
			headStraws4.rotateAngleX = ModelUtils.toRadians(45D);
			headStraws4.mirror = true;
			headStraws4.setTextureOffset(0, 45).addBox(-4F, 2.5F, 3F, 8, 3, 0);
				bipedHead.addChild(headStraws4);
			ModelRenderer headStraws5 = ModelUtils.freshRenderer(this);
			headStraws5.rotateAngleZ = ModelUtils.toRadians(45D);
			headStraws5.setTextureOffset(0, 34).addBox(-3F, 2.5F, -4F, 0, 3, 8);
				bipedHead.addChild(headStraws5);
			ModelRenderer headStraws6 = ModelUtils.freshRenderer(this);
			headStraws6.rotateAngleZ = ModelUtils.toRadians(-45D);
			headStraws6.mirror = true;
			headStraws6.setTextureOffset(0, 37).addBox(3F, 2.5F, -4F, 0, 3, 8);
				bipedHead.addChild(headStraws6);
		
		bipedBody = ModelUtils.freshRenderer(this);
		bipedBody.setTextureOffset(0, 16).addBox(-1F, 0F, -1F, 2, 24, 2);
		bipedBody.setTextureOffset(16, 16).addBox(-3.5F, 0F, -3.5F, 7, 5, 7);
		bipedBody.setTextureOffset(16, 28).addBox(-4F, 5F, -4F, 8, 9, 8, 0.5F);
		
		bipedRightArm = ModelUtils.freshRenderer(this);
		bipedRightArm.setRotationPoint(-5F, 3F, 0F);
		bipedRightArm.setTextureOffset(44, 16).addBox(-1F, -4F, -1F, 2, 12, 2);
		bipedRightArm.setTextureOffset(52, 16).addBox(-1F, -4F, -1F, 2, 12, 2, 0.5F);
		
		bipedLeftArm = ModelUtils.freshRenderer(this);
		bipedLeftArm.setRotationPoint(5F, 3F, 0F);
		bipedLeftArm.mirror = true;
		bipedLeftArm.setTextureOffset(44, 16).addBox(-1F, -4F, -1F, 2, 12, 2);
		bipedLeftArm.setTextureOffset(52, 16).addBox(-1F, -4F, -1F, 2, 12, 2, 0.5F);
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedBody, this.bipedRightArm, this.bipedLeftArm);
	}
	
	public void setRotationAngles(EntityScarecrow entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
    	Random rand = new Random(entityIn.getUniqueID().getMostSignificantBits());
    	float vol = ModelUtils.toRadians(entityIn.isBurnt() ? 12D : 4D);
    	bipedHead.rotateAngleX = vol * (rand.nextBoolean() ? 1F : -1F);
    	bipedHead.rotateAngleZ = vol * (rand.nextBoolean() ? 1F : -1F);
    	
    	if(entityIn.isBurnt())
    	{
    		bipedRightArm.rotateAngleZ = ModelUtils.toRadians(48D + rand.nextDouble() * 32D);
    		bipedLeftArm.rotateAngleZ = -ModelUtils.toRadians(48D + rand.nextDouble() * 32D);
    	}
    	else
    	{
    		bipedRightArm.rotateAngleZ = ModelUtils.toRadians(64D);
    		bipedLeftArm.rotateAngleZ = ModelUtils.toRadians(-64D);
    	}
	}
}
