package com.lying.variousequipment.client.model.bauble;

import java.util.List;

import com.google.common.collect.Lists;
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
	List<ModelRenderer> markers = Lists.newArrayList();
	
	public ModelWingsDragon()
	{
		super();
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.wingLeftA = ModelUtils.freshRenderer(this);
		this.wingLeftA.setRotationPoint(1.5F, 1.5F, 2F);
		this.wingLeftA.mirror = true;
		this.wingLeftA.setTextureOffset(0, 0).addBox(0F, -0.5F, -0.5F, 12, 1, 1);
			this.bipedBody.addChild(wingLeftA);
			
			this.wingLeftB = ModelUtils.freshRenderer(this);
			this.wingLeftB.setRotationPoint(12F, 0F, 0F);
			this.wingLeftB.mirror = true;
			this.wingLeftB.setTextureOffset(0, 0).addBox(0F, -0.5F, -0.5F, 12, 1, 1);
			this.wingLeftB.rotateAngleZ = ModelUtils.toRadians(19D);
				this.wingLeftA.addChild(wingLeftB);
			
			this.wingLeftC = ModelUtils.freshRenderer(this);
			this.wingLeftC.setRotationPoint(12F, 0F, 0F);
			this.wingLeftC.mirror = true;
			this.wingLeftC.setTextureOffset(0, 2).addBox(0F, -0.5F, -0.5F, 9, 1, 1);
			this.wingLeftC.rotateAngleZ = ModelUtils.toRadians(59D);
				this.wingLeftA.addChild(wingLeftC);
			
			this.wingLeftD = ModelUtils.freshRenderer(this);
			this.wingLeftD.setRotationPoint(12F, 0F, 0F);
			this.wingLeftD.mirror = true;
			this.wingLeftD.setTextureOffset(0, 2).addBox(0F, -0.5F, -0.5F, 9, 1, 1);
			this.wingLeftD.rotateAngleZ = ModelUtils.toRadians(95D);
				this.wingLeftA.addChild(wingLeftD);
		
		this.wingRightA = ModelUtils.freshRenderer(this);
		this.wingRightA.setRotationPoint(-1.5F, 1.5F, 2F);
		this.wingRightA.setTextureOffset(0, 0).addBox(-12F, -0.5F, -0.5F, 12, 1, 1);
			this.bipedBody.addChild(wingRightA);
			
			this.wingRightB = ModelUtils.freshRenderer(this);
			this.wingRightB.setRotationPoint(-12F, 0F, 0F);
			this.wingRightB.setTextureOffset(0, 0).addBox(-12F, -0.5F, -0.5F, 12, 1, 1);
			this.wingRightB.rotateAngleZ = -ModelUtils.toRadians(19D);
				this.wingRightA.addChild(wingRightB);
			
			this.wingRightC = ModelUtils.freshRenderer(this);
			this.wingRightC.setRotationPoint(-12F, 0F, 0F);
			this.wingRightC.setTextureOffset(0, 2).addBox(-9F, -0.5F, -0.5F, 9, 1, 1);
			this.wingRightC.rotateAngleZ = -ModelUtils.toRadians(59D);
				this.wingRightA.addChild(wingRightC);
			
			this.wingRightD = ModelUtils.freshRenderer(this);
			this.wingRightD.setRotationPoint(-12F, 0F, 0F);
			this.wingRightD.setTextureOffset(0, 2).addBox(-9F, -0.5F, -0.5F, 9, 1, 1);
			this.wingRightD.rotateAngleZ = -ModelUtils.toRadians(95D);
				this.wingRightA.addChild(wingRightD);
		
		for(int i=0; i<180; i++)
			markers.add(ModelUtils.freshRenderer(this).addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1));
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
//		return ImmutableList.of(this.bipedBody);
		List<ModelRenderer> list = Lists.newArrayList();
		list.add(this.bipedBody);
		list.addAll(markers.subList(0, 6));
		return list;
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		
//		this.wingLeftA.rotateAngleY = ModelUtils.toRadians(-10D) + (1F + (float)Math.sin(ageInTicks * 0.5F)) * ModelUtils.toRadians(-15D);
//		this.wingRightA.rotateAngleY = -this.wingLeftA.rotateAngleY - ModelUtils.toRadians(180D);
//		this.wingLeftD.rotateAngleZ = ModelUtils.toRadians(95D) + (1F + (float)Math.sin(ageInTicks * 0.5F)) * ModelUtils.toRadians(10D);
		
//		this.wingLeftA.rotateAngleY = -ModelUtils.toRadians(15D);
//		this.wingRightA.rotateAngleY = -this.wingLeftA.rotateAngleY;
		
		float t = 1F + (float)Math.sin(ageInTicks * 0.1F);
		this.wingLeftB.rotateAngleZ = ModelUtils.toRadians(8D) + (ModelUtils.toRadians(11D) * t);
		this.wingLeftC.rotateAngleZ = ModelUtils.toRadians(30D) + (ModelUtils.toRadians(29D) * t);
		this.wingLeftD.rotateAngleZ = ModelUtils.toRadians(70D) + (ModelUtils.toRadians(25D) * t);
		this.wingRightB.rotateAngleZ = -this.wingLeftB.rotateAngleZ;
		
		addWingMembranes(this.wingLeftA, this.wingLeftB, this.wingLeftC, this.wingLeftD, true);
		addWingMembranes(this.wingRightA, this.wingRightB, this.wingRightC, this.wingRightD, false);
    }
	
	private void addWingMembranes(ModelRenderer wingA, ModelRenderer wingB, ModelRenderer wingC, ModelRenderer wingD, boolean isLeft)
	{
		double offset = isLeft ? 1D : -1D;
		Vector3d wingTop = getVectorWithKinematics(Vector3d.ZERO, this.bipedBody, wingA);
		Vector3d wingBot = getVectorWithKinematics(new Vector3d(0, 9, 0), this.bipedBody, wingA);
		Vector3d carpal = getVectorWithKinematics(new Vector3d(12 * offset, 0, 0), this.bipedBody, wingA);
		Vector3d finger1 = getVectorWithKinematics(new Vector3d(12 * offset, 0, 0), this.bipedBody, wingA, wingB);
		Vector3d finger2 = getVectorWithKinematics(new Vector3d(9 * offset, 0, 0), this.bipedBody, wingA, wingC);
		Vector3d finger3 = getVectorWithKinematics(new Vector3d(9 * offset, 0, 0), this.bipedBody, wingA, wingD);
		addSubdividedMembrane(45, 16, 8, 8, wingTop, carpal, finger3, wingBot, isLeft);
		addSubdividedMembrane(45, 16, 8, 8, carpal, carpal, finger2, finger3, isLeft);
		addSubdividedMembrane(45, 16, 8, 8, carpal, carpal, finger1, finger2, isLeft);
		
		if(isLeft)
		{
			Vector3d[] vecs = new Vector3d[]{wingTop, wingBot, carpal, finger1, finger2, finger3};
			for(int i=0; i<vecs.length; i++)
			{
				ModelRenderer marker = this.markers.get(i);
				marker.rotationPointX = (float)vecs[i].x;
				marker.rotationPointY = (float)vecs[i].y;
				marker.rotationPointZ = (float)vecs[i].z;
			}
		}
	}
}
