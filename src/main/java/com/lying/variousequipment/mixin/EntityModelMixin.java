package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.renderer.entity.model.EntityModel;

@Mixin(EntityModel.class)
public class EntityModelMixin
{
	@Shadow
	public boolean isSitting;
}
