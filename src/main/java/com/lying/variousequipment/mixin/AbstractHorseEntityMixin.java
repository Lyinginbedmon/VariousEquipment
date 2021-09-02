package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.lying.variousequipment.entity.EntityWagon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin extends EntityMixin
{
	@Inject(method = "getControllingPassenger()Lnet/minecraft/entity/Entity;", at = @At("RETURN"), cancellable = true)
	private void getControllingPassenger(final CallbackInfoReturnable<Entity> ci)
	{
		AbstractHorseEntity living = (AbstractHorseEntity)(Object)this;
		if(ci.getReturnValue() == null || !ci.getReturnValue().isAlive())
		{
			EntityWagon attachedWagon = EntityWagon.getConnectedWagon(living);
			if(attachedWagon != null && attachedWagon.getDriver() != null && attachedWagon.getDriver().isAlive())
				ci.setReturnValue(attachedWagon.getDriver());
		}
	}
}
