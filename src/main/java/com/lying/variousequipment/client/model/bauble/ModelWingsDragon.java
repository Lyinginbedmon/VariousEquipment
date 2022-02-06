package com.lying.variousequipment.client.model.bauble;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.Matrix4d;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelWingsDragon extends ModelWings<LivingEntity>
{
	ModelRenderer wingLeftA, wingLeftB, wingLeftC, wingLeftD;
	ModelRenderer wingRightA, wingRightB, wingRightC, wingRightD;
	ModelRenderer fingertip;
	
	private final Map<ModelRenderer, ModelRenderer[]> partToParents = new HashMap<>();
	
	public ModelWingsDragon()
	{
		super();
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.wingLeftA = ModelUtils.freshRenderer(this);
		this.wingLeftA.setRotationPoint(1.5F, 1.5F, 2F);
		this.wingLeftA.mirror = true;
		this.wingLeftA.setTextureOffset(0, 0).addBox(0F, -0.5F, -0.5F, 12, 1, 1);
			partToParents.put(wingLeftA, new ModelRenderer[]{this.bipedBody});
			this.bipedBody.addChild(wingLeftA);
			
			this.wingLeftB = ModelUtils.freshRenderer(this);
			this.wingLeftB.setRotationPoint(12F, 0F, 0F);
			this.wingLeftB.mirror = true;
			this.wingLeftB.setTextureOffset(0, 0).addBox(0F, -0.5F, -0.5F, 12, 1, 1);
			this.wingLeftB.rotateAngleZ = ModelUtils.toRadians(19D);
				partToParents.put(wingLeftB, new ModelRenderer[]{this.bipedBody, this.wingLeftA});
				this.wingLeftA.addChild(wingLeftB);
			
			this.wingLeftC = ModelUtils.freshRenderer(this);
			this.wingLeftC.setRotationPoint(12F, 0F, 0F);
			this.wingLeftC.mirror = true;
			this.wingLeftC.setTextureOffset(0, 2).addBox(0F, -0.5F, -0.5F, 9, 1, 1);
			this.wingLeftC.rotateAngleZ = ModelUtils.toRadians(59D);
				partToParents.put(wingLeftC, new ModelRenderer[]{this.bipedBody, this.wingLeftA});
				this.wingLeftA.addChild(wingLeftC);
			
			this.wingLeftD = ModelUtils.freshRenderer(this);
			this.wingLeftD.setRotationPoint(12F, 0F, 0F);
			this.wingLeftD.mirror = true;
			this.wingLeftD.setTextureOffset(0, 2).addBox(0F, -0.5F, -0.5F, 9, 1, 1);
			this.wingLeftD.rotateAngleZ = ModelUtils.toRadians(95D);
				partToParents.put(wingLeftD, new ModelRenderer[]{this.bipedBody, this.wingLeftA});
				this.wingLeftA.addChild(wingLeftD);
		
		this.wingRightA = ModelUtils.freshRenderer(this);
		this.wingRightA.setRotationPoint(-1.5F, 1.5F, 2F);
		this.wingRightA.setTextureOffset(0, 0).addBox(0F, -0.5F, -0.5F, 12, 1, 1);
			partToParents.put(wingRightA, new ModelRenderer[]{this.bipedBody});
			this.bipedBody.addChild(wingRightA);
			
			this.wingRightB = ModelUtils.freshRenderer(this);
			this.wingRightB.setRotationPoint(12F, 0F, 0F);
			this.wingRightB.setTextureOffset(0, 0).addBox(0F, -0.5F, -0.5F, 12, 1, 1);
			this.wingRightB.rotateAngleZ = ModelUtils.toRadians(19D);
				partToParents.put(wingRightB, new ModelRenderer[]{this.bipedBody, this.wingRightA});
				this.wingRightA.addChild(wingRightB);
			
			this.wingRightC = ModelUtils.freshRenderer(this);
			this.wingRightC.setRotationPoint(12F, 0F, 0F);
			this.wingRightC.setTextureOffset(0, 2).addBox(0F, -0.5F, -0.5F, 9, 1, 1);
			this.wingRightC.rotateAngleZ = ModelUtils.toRadians(59D);
				partToParents.put(wingRightC, new ModelRenderer[]{this.bipedBody, this.wingRightA});
				this.wingRightA.addChild(wingRightC);
			
			this.wingRightD = ModelUtils.freshRenderer(this);
			this.wingRightD.setRotationPoint(12F, 0F, 0F);
			this.wingRightD.setTextureOffset(0, 2).addBox(0F, -0.5F, -0.5F, 9, 1, 1);
			this.wingRightD.rotateAngleZ = ModelUtils.toRadians(95D);
				partToParents.put(wingRightD, new ModelRenderer[]{this.bipedBody, this.wingRightA});
				this.wingRightA.addChild(wingRightD);
		
		this.fingertip = ModelUtils.freshRenderer(this);
		this.fingertip.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.5F);
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedBody, this.fingertip);
	}
    
	@SuppressWarnings("unused")
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		
//		this.wingLeftA.rotateAngleY = ModelUtils.toRadians(-10D) + (1F + (float)Math.sin(ageInTicks * 0.5F)) * ModelUtils.toRadians(-15D);
//		this.wingRightA.rotateAngleY = -this.wingLeftA.rotateAngleY - ModelUtils.toRadians(180D);
//		this.wingLeftD.rotateAngleZ = ModelUtils.toRadians(95D) + (1F + (float)Math.sin(ageInTicks * 0.5F)) * ModelUtils.toRadians(10D);
		
		// Left wing
			// Top body join
		Vector3d wingTop = new Vector3d(this.wingLeftA.rotationPointX, this.wingLeftA.rotationPointY, this.wingLeftA.rotationPointZ);
			// Furthest point
		Vector3d carpals = wingTop.add(getRotatedOffset(this.wingLeftB, this.wingLeftA));
			// Bottom of furthest point
		Vector3d finger3End = carpals.add(new Vector3d(9, 0, 0).rotateRoll(-this.wingLeftD.rotateAngleZ));
			// Bottom body join
		Vector3d wingBottom = wingTop.add(new Vector3d(0, 9, 0));
//		this.addMembrane(45, 16, 8, 8, wingTop, carpals, finger3End, wingBottom, false);
		
		// First finger to second finger
		Vector3d finger2End = carpals.add(new Vector3d(9, 0, 0).rotateRoll(-this.wingLeftC.rotateAngleZ));
//		this.addMembrane(45, 16, 8, 8, carpals, carpals, finger2End, finger3End, false);
		
		// Second finger to third finger
		Vector3d finger1End = carpals.add(new Vector3d(12, 0, 0).rotateRoll(-this.wingLeftB.rotateAngleZ));
//		this.addMembrane(45, 16, 8, 8, carpals, carpals, finger1End, finger2End, false);
		
		// Calculate exact world position of carpal point, respecting rotation points and angles of parent objects
		Vector3d pos = getVectorWithKinematics(new Vector3d(4, 0, 0), this.bipedBody, this.wingLeftA, this.wingLeftB);
		this.fingertip.rotationPointX = (float)pos.getX();
		this.fingertip.rotationPointY = (float)pos.getY();
		this.fingertip.rotationPointZ = (float)pos.getZ();
    }
	
	public Vector3d getRotatedOffset(ModelRenderer offset, ModelRenderer parent)
	{
		Vector3d rotatedOffset = new Vector3d(offset.rotationPointX, offset.rotationPointY, offset.rotationPointZ);
		return rotatedOffset.rotatePitch(-parent.rotateAngleX).rotateYaw(parent.rotateAngleY).rotateRoll(parent.rotateAngleZ);
	}
	
	public Vector3d getVectorWithKinematics(Vector3d local, ModelRenderer... parentedModels)
	{
		Matrix4d matrix = new Matrix4d();
		for(ModelRenderer parent : parentedModels)
			matrix = matrix.rotate(parent.rotateAngleX, parent.rotateAngleY, parent.rotateAngleZ).translate(parent.rotationPointX, parent.rotationPointY, parent.rotationPointZ);
		return matrix.apply(local);
	}
}
