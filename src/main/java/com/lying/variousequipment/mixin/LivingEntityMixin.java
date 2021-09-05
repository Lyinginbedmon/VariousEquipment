package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lying.variousequipment.entity.EntityWagon;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.util.math.vector.Vector3d;

@Mixin(LivingEntity.class)
public class LivingEntityMixin extends EntityMixin
{
	@Inject(method = "travel(Lnet/minecraft/util/math/vector/Vector3d;)V", at = @At("HEAD"), cancellable = true)
	private void travel(Vector3d travelVector, final CallbackInfo ci)
	{
		LivingEntity living = (LivingEntity)(Object)this;
		if(living instanceof HorseEntity)
		{
			HorseEntity horse = (HorseEntity)living;
			EntityWagon wagon = EntityWagon.getConnectedWagon(horse);
			if(wagon == null)
				return;
			
			LivingEntity rider = (LivingEntity)wagon.getDriver();
			if(rider == null)
				return;
			
			System.out.println("Riding conditions of "+horse.getDisplayName().getString()+":");
			System.out.println("* Side: "+(horse.getEntityWorld().isRemote ? "CLIENT" : "SERVER"));
			System.out.println("* Forward: "+rider.moveForward);
			System.out.println("* Strafe: "+rider.moveStrafing);
		}
	}
}
