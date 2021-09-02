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
//	@Inject(method = "travel(Lnet/minecraft/util/math/vector/Vector3d;)V", at = @At("HEAD"), cancellable = true)
//	private void travel(Vector3d travelVector, final CallbackInfo ci)
//	{
//		LivingEntity living = (LivingEntity)(Object)this;
//		if(living instanceof HorseEntity)
//		{
//			HorseEntity horse = (HorseEntity)living;
//			EntityWagon wagon = EntityWagon.getConnectedWagon(horse);
//			if(wagon == null)
//				return;
//			
//			System.out.println("Riding conditions of "+horse.getDisplayName().getString()+":");
//			System.out.println(" * Wagon: "+true);
//			System.out.println(" * Alive: "+horse.isAlive());
//			if(!horse.isAlive()) return;
//			System.out.println(" * Ridden: "+horse.isBeingRidden());
//			if(!horse.isBeingRidden()) return;
//			System.out.println(" * Steerable: "+horse.canBeSteered());
//			if(!horse.canBeSteered()) return;
//			System.out.println(" * Saddle: "+horse.isHorseSaddled());
//			if(!horse.isHorseSaddled()) return;
//		    LivingEntity rider = (LivingEntity)horse.getControllingPassenger();
//			System.out.println(" * Rider: "+(rider == null ? "NULL" : rider.getDisplayName().getString()));
//		}
//	}
}
