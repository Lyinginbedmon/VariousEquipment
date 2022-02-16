package com.lying.variousequipment.client.model.bauble;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lying.variousequipment.client.model.Matrix4d;
import com.lying.variousequipment.client.model.ModelUtils;
import com.lying.variousequipment.client.model.ModelUtils.TexturedQuad;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public abstract class ModelWings<T extends LivingEntity> extends BipedModel<T>
{
	protected List<TexturedQuad> membranes = Lists.newArrayList();
	
	public ModelWings()
	{
		super(0F);
		
		this.bipedBody = ModelUtils.freshRenderer(this);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
	protected Iterable<ModelRenderer> getHeadParts()
	{
		return ImmutableList.of();
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedBody);
	}
	
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		this.membranes.forEach((membrane) -> { ModelUtils.renderQuad(matrixStackIn.getLast(), bufferIn, membrane, packedLightIn, packedOverlayIn, red, green, blue, alpha); });
		this.membranes.clear();
	}
	
	public void addMembrane(int texOffX, int texOffY, int sizeX, int sizeY, Vector3d posA, Vector3d posB, Vector3d posC, Vector3d posD, boolean mirrorIn)
	{
		this.membranes.add(TexturedQuad.texturedPlane(texOffX, texOffY, sizeX, sizeY, posA, posB, posC, posD, mirrorIn, this.textureWidth, this.textureHeight));
	}
	
	/** Adds 4 membranes across a given plane, allowing for more natural distortion */
	public void addSubdividedMembrane(int texOffX, int texOffY, int sizeX, int sizeY, Vector3d posA, Vector3d posB, Vector3d posC, Vector3d posD, boolean mirrorIn)
	{
		Vector3d topLeft =	posA;
		Vector3d midLeft =	posA.add(posD.subtract(posA).scale(0.5D));
		Vector3d botLeft =	posD;
		
		Vector3d topMid =	posA.add(posB.subtract(posA).scale(0.5D));
		Vector3d botMid =	posD.add(posC.subtract(posD).scale(0.5D));
		Vector3d midMid =	posA.add(posB).add(posC).add(posD).scale(1 / 4D);
		
		Vector3d topRight = posB;
		Vector3d midRight =	posB.add(posC.subtract(posB).scale(0.5D));
		Vector3d botRight = posC;
		
		sizeX *= 0.5F;
		sizeY *= 0.5F;
		float minX = (float)texOffX;
		float midX = minX + sizeX;
		float minY = (float)texOffY;
		float midY = minY + sizeY;
		
		this.membranes.add(TexturedQuad.texturedPlane(minX, minY, sizeX, sizeY, topMid, topLeft, midLeft, midMid, mirrorIn, this.textureWidth, this.textureHeight));
		this.membranes.add(TexturedQuad.texturedPlane(midX, minY, sizeX, sizeY, topRight, topMid, midMid, midRight, mirrorIn, this.textureWidth, this.textureHeight));
		this.membranes.add(TexturedQuad.texturedPlane(minX, midY, sizeX, sizeY, midMid, midLeft, botLeft, botMid, mirrorIn, this.textureWidth, this.textureHeight));
		this.membranes.add(TexturedQuad.texturedPlane(midX, midY, sizeX, sizeY, midRight, midMid, botMid, botRight, mirrorIn, this.textureWidth, this.textureHeight));
	}
	
	public Vector3d getVectorWithKinematics(Vector3d local, ModelRenderer... parentedModels)
	{
		Matrix4d matrix = new Matrix4d();
		for(ModelRenderer parent : parentedModels)
			matrix = applyModelToMatrix(matrix, parent);
		return matrix.apply(local);
	}
	
	public Matrix4d applyModelToMatrix(Matrix4d matrix, ModelRenderer model)
	{
		float s = 8F / 16F;
		Matrix4d matrix2 = new Matrix4d()
				.rotate(Vector3f.YP.rotation(model.rotateAngleY * s))
				.rotate(Vector3f.ZP.rotation(model.rotateAngleZ * s))
				.rotate(Vector3f.XP.rotation(model.rotateAngleX * s))
				.translate(model.rotationPointX, model.rotationPointY, model.rotationPointZ);
		return matrix2.mul(matrix);
	}
}
