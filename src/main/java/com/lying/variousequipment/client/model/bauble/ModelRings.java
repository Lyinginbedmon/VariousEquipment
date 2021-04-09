package com.lying.variousequipment.client.model.bauble;

import javax.annotation.Nonnull;

import com.lying.variousequipment.client.model.ModelUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelRings extends BipedModel<LivingEntity>
{
	private ModelRenderer[] rings = new ModelRenderer[10];
	
	public ModelRings(float modelSize)
	{
		super(modelSize);
		this.textureHeight = 8;
		this.textureWidth = 8;
		
		this.bipedRightArm = ModelUtils.freshRenderer(this);
		this.bipedRightArm.setRotationPoint(-5F, 2F, 0F);
		
		this.bipedLeftArm = ModelUtils.freshRenderer(this);
		this.bipedLeftArm.setRotationPoint(5F, 2F, 0F);
		
		for(int i=0; i<rings.length; i++)
		{
			ModelRenderer ring = ModelUtils.freshRenderer(this);
			
			float zOffset = -1F;
			switch(MathHelper.floor(i / 2))
			{
				case 1:	zOffset = -1.9F; break;
				case 3:	zOffset = -2.8F; break;
				
				case 2:	zOffset = -0.1F; break;
				case 4:	zOffset = 0.8F; break;
			}
			
			if(i%2 == 0)
			{
				ring.addBox(-1.8F + (float)Math.sin(i) * 0.1F, 8.8F, zOffset, 2, 2, 2, -0.6F);
				this.bipedRightArm.addChild(ring);
			}
			else
			{
				ring.mirror = true;
				ring.addBox(-0.2F + (float)Math.sin(i) * 0.1F, 8.8F, zOffset, 2, 2, 2, -0.6F);
				this.bipedLeftArm.addChild(ring);
			}
			
			rings[i] = ring;
		}
	}
	
	public ModelRenderer getRing(int index){ return rings[index]; }
	
	public void setRing(int index)
	{
		for(int i=0; i<rings.length; i++)
			rings[i].showModel = index == i;
	}
	
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha)
	{
		this.bipedRightArm.render(matrixStack, vertexBuilder, light, overlay);
		this.bipedLeftArm.render(matrixStack, vertexBuilder, light, overlay);
	}
}
