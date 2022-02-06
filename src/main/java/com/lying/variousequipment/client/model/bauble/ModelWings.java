package com.lying.variousequipment.client.model.bauble;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lying.variousequipment.client.model.ModelUtils;
import com.lying.variousequipment.client.model.ModelUtils.TexturedQuad;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;

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
}
