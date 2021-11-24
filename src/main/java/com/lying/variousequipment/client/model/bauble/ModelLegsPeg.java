package com.lying.variousequipment.client.model.bauble;

import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.item.bauble.ItemPegLeg.Leg;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public abstract class ModelLegsPeg extends BipedModel<LivingEntity>
{
	public ModelLegsPeg(float modelSize)
	{
		super(modelSize);
	}
	
	public ModelLegsPeg(Function<ResourceLocation, RenderType> renderTypeIn, float modelSizeIn, float yOffsetIn, int textureWidthIn, int textureHeightIn)
	{
		super(renderTypeIn, modelSizeIn, yOffsetIn, textureWidthIn, textureHeightIn);
	}
	
	protected Iterable<ModelRenderer> getHeadParts()
	{
		return ImmutableList.of();
	}
	
	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.bipedRightLeg, this.bipedLeftLeg);
	}
	
	public void setLeg(Leg legIn)
	{
		this.bipedLeftLeg.showModel = legIn == Leg.LEFT;
		this.bipedRightLeg.showModel = legIn == Leg.RIGHT;
	}
}
