package com.lying.variousequipment.client.model.bauble;

import com.google.common.collect.ImmutableList;
import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public abstract class ModelTail<T extends LivingEntity> extends BipedModel<T>
{
	public ModelTail()
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
}
