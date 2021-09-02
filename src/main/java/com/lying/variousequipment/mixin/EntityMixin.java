package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.lying.variousequipment.entity.EntityWagon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

@Mixin(Entity.class)
public class EntityMixin
{
	@Inject(method = "isBeingRidden()Z", at = @At("RETURN"), cancellable = true)
	private void isBeingRidden(final CallbackInfoReturnable<Boolean> ci)
	{
		if(!ci.getReturnValueZ())
		{
			Entity ent = (Entity)(Object)this;
			if(!(ent instanceof LivingEntity)) return;
			
			LivingEntity living = (LivingEntity)ent;
			EntityWagon attachedWagon = EntityWagon.getConnectedWagon(living);
			if(attachedWagon != null && attachedWagon.getDriver() != null && attachedWagon.getDriver().isAlive())
				ci.setReturnValue(true);
		}
	}
}
