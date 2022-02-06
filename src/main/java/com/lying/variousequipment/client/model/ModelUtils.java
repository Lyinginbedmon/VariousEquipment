package com.lying.variousequipment.client.model;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class ModelUtils
{
	/** 180 degrees expressed as radians */
	public static final float degree180 = (float)(Math.toRadians(180D));
	/** 90 degrees expressed as radians */
	public static final float degree90 = (float)(Math.toRadians(90D));
	/** 10 degrees expressed as radians */
	public static final float degree10 = (float)(Math.toRadians(10D));
	/** 5 degrees expressed as radians */
	public static final float degree5 = (float)(Math.toRadians(5D));
	
	/** Converts a given double from degrees to radians as a float */
	public static float toRadians(double par1Double){ return (float)(Math.toRadians(par1Double)); }
	
	public static ModelRenderer freshRenderer(Model par1ModelBase){ return new ModelRenderer(par1ModelBase).setTextureSize(par1ModelBase.textureWidth,par1ModelBase.textureHeight); }
	
	public static ModelRenderer clonePosition(ModelRenderer fromModel, ModelRenderer toModel)
	{
		toModel.setRotationPoint(fromModel.rotationPointX, fromModel.rotationPointY, fromModel.rotationPointZ);
		return toModel;
	}
	
	public static ModelRenderer cloneRotation(ModelRenderer fromModel, ModelRenderer toModel)
	{
		toModel.rotateAngleX = fromModel.rotateAngleX;
		toModel.rotateAngleY = fromModel.rotateAngleY;
		toModel.rotateAngleZ = fromModel.rotateAngleZ;
		return toModel;
	}
	
	public static ModelRenderer shiftWithRotation(ModelRenderer par1ModelRenderer, Vector3d angle, Vector3d shift)
	{
//		Vec3d newVec = shift.rotatePitch((float)angle.x).rotateYaw((float)angle.y);
		Vector3d newVec = new Vector3d(0,0,0);
		
		par1ModelRenderer.rotationPointX += newVec.x;
		par1ModelRenderer.rotationPointY += newVec.y;
		par1ModelRenderer.rotationPointZ += newVec.z;
		
		return par1ModelRenderer;
	}
	
	public static Vector3d getAngles(ModelRenderer par1ModelRenderer)
	{
		return new Vector3d(par1ModelRenderer.rotateAngleX, par1ModelRenderer.rotateAngleY, par1ModelRenderer.rotateAngleZ);
	}
	
	public static Vector3d getPosition(ModelRenderer par1ModelRenderer)
	{
		return new Vector3d(par1ModelRenderer.rotationPointX, par1ModelRenderer.rotationPointY, par1ModelRenderer.rotationPointZ);
	}
	
	/** Convenient holder class describing a section of a texture file */
	public static class TextureLocation
	{
		private int xMin, xMax, yMin, yMax;
		
		private float xScale = 1F / 256F;
		private float yScale = 1F / 256F;
		
		public TextureLocation(int xMinIn, int yMinIn, int xSizeIn, int ySizeIn)
		{
			this.xMin = xMinIn;
			this.xMax = xMinIn + xSizeIn;
			
			this.yMin = yMinIn;
			this.yMax = yMinIn + ySizeIn;
		}
		
		public void mirror()
		{
			int oldMax = this.xMax;
			this.xMax = this.xMin;
			this.xMin = oldMax;
		}
		
		public void setScale(int texWidth, int texHeight)
		{
			xScale = 1F / (float)texWidth;
			yScale = 1F / (float)texHeight;
		}
		
		public Pair<Float, Float> textureA(){ return Pair.of((float)xMin * xScale, (float)yMin * yScale); }
		public Pair<Float, Float> textureB(){ return Pair.of((float)xMax * xScale, (float)yMin * yScale); }
		public Pair<Float, Float> textureC(){ return Pair.of((float)xMax * xScale, (float)yMax * yScale); }
		public Pair<Float, Float> textureD(){ return Pair.of((float)xMin * xScale, (float)yMax * yScale); }
	}
	
	public static void drawTexturedTri(MatrixStack matrixStack, IVertexBuilder buffer, Vector3d posA,Vector3d posC, Vector3d posD, int texOffX, int texOffY, int texSizeX, int texSizeY, int texWidth, int texHeight, boolean mirrorIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		renderQuad(matrixStack.getLast(), buffer, TexturedQuad.texturedPlane(texOffX, texOffY, texSizeX, texSizeY, posA, posA, posC, posD, mirrorIn, texWidth, texHeight), packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	public static void drawTexturedPlane(MatrixStack matrixStack, IVertexBuilder buffer, Vector3d posA, Vector3d posB, Vector3d posC, Vector3d posD, int texOffX, int texOffY, int texSizeX, int texSizeY, int texWidth, int texHeight, boolean mirrorIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		renderQuad(matrixStack.getLast(), buffer, TexturedQuad.texturedPlane(texOffX, texOffY, texSizeX, texSizeY, posA, posB, posC, posD, mirrorIn, texWidth, texHeight), packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	public static void renderQuad(MatrixStack.Entry matrixEntryIn, IVertexBuilder bufferIn, @Nullable TexturedQuad quad, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		Matrix4f matrix4f = matrixEntryIn.getMatrix();
		Matrix3f matrix3f = matrixEntryIn.getNormal();
		if(quad == null)
			return;
		
		Vector3f normal = quad.normal.copy();
		normal.transform(matrix3f);
		float normX = normal.getX();
		float normY = normal.getY();
		float normZ = normal.getZ();
		
		for(int i = 0; i < 4; ++i)
		{
			PositionTextureVertex vertex = quad.vertices[i];
			if(vertex == null) continue;
			
			float vertexX = vertex.pos.getX() / 16.0F;
			float vertexY = vertex.pos.getY() / 16.0F;
			float vertexZ = vertex.pos.getZ() / 16.0F;
			Vector4f vertexScaled = new Vector4f(vertexX, vertexY, vertexZ, 1.0F);
			vertexScaled.transform(matrix4f);
			bufferIn.addVertex(vertexScaled.getX(), vertexScaled.getY(), vertexScaled.getZ(), red, green, blue, alpha, vertex.texX, vertex.texY, packedOverlayIn, packedLightIn, normX, normY, normZ);
		}
	}
	
	public static class PositionTextureVertex
	{
		public final Vector3f pos;
		public final float texX, texY;
		
		public PositionTextureVertex(Vector3f posIn, float texXIn, float texYIn)
		{
			this.pos = posIn;
			this.texX = texXIn;
			this.texY = texYIn;
		}
		
		public PositionTextureVertex(float x, float y, float z, float texXIn, float texYIn)
		{
			this(new Vector3f(x, y, z), texXIn, texYIn);
		}
		
		public PositionTextureVertex setTextureUV(float texU, float texV)
		{
			return new PositionTextureVertex(this.pos, texU, texV);
		}
	}
	
	public static class TexturedQuad
	{
		public final PositionTextureVertex[] vertices;
		public final Vector3f normal;
		
		public TexturedQuad(PositionTextureVertex[] verticesIn, float texXMin, float texYMin, float texXMax, float texYMax, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn)
		{
			this.vertices = verticesIn;
			float texXScale = 0F / texWidth;
			float texYScale = 0F / texHeight;
			verticesIn[0] = verticesIn[0].setTextureUV(texXMax / texWidth - texXScale, texYMin / texHeight + texYScale);
			verticesIn[1] = verticesIn[1].setTextureUV(texXMin / texWidth + texXScale, texYMin / texHeight + texYScale);
			verticesIn[2] = verticesIn[2].setTextureUV(texXMin / texWidth + texXScale, texYMax / texHeight - texYScale);
			verticesIn[3] = verticesIn[3].setTextureUV(texXMax / texWidth - texXScale, texYMax / texHeight - texYScale);
			if(mirrorIn)
			{
				int i = verticesIn.length;
				for(int j = 0; j < i / 2; ++j)
				{
					PositionTextureVertex vertex = verticesIn[j];
					verticesIn[j] = verticesIn[i - 1 - j];
					verticesIn[i - 1 - j] = vertex;
				}
			}
			this.normal = directionIn.toVector3f();
			if(mirrorIn)
				this.normal.mul(-1F, 1F, 1F);
		}
		
		public static TexturedQuad texturedPlane(int texOffX, int texOffY, int texXSize, int texYSize, Vector3d posA, Vector3d posB, Vector3d posC, Vector3d posD, boolean mirrorIn, float textureWidth, float textureHeight)
		{
	        PositionTextureVertex vertexA = new PositionTextureVertex((float)posA.x, (float)posA.y, (float)posA.z, 0.0F, 0.0F);
	        PositionTextureVertex vertexB = new PositionTextureVertex((float)posB.x, (float)posB.y, (float)posB.z, 0.0F, 0.0F);
	        PositionTextureVertex vertexC = new PositionTextureVertex((float)posC.x, (float)posC.y, (float)posC.z, 8.0F, 0.0F);
	        PositionTextureVertex vertexD = new PositionTextureVertex((float)posD.x, (float)posD.y, (float)posD.z, 8.0F, 0.0F);
	        
	        return new TexturedQuad(new PositionTextureVertex[]{vertexA, vertexB, vertexC, vertexD}, texOffX, texOffY, texOffX + texXSize, texOffY + texYSize, textureWidth, textureHeight, mirrorIn, Direction.EAST);
		}
	}
}

