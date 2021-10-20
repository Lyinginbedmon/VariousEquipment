package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lying.variousequipment.entity.EntityScarecrow;
import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.utility.bus.BusServer;
import com.lying.variousequipment.utility.world.ManagerScarecrows;

import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;

@Mixin(ServerWorld.class)
public class ServerWorldMixin
{
	@Inject(method = "onEntityAdded(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"))
	private void onEntityAdded(Entity entityIn, final CallbackInfo ci)
	{
		if(entityIn.getType() == VEEntities.SCARECROW.get())
		{
			ManagerScarecrows manager = BusServer.getScarecrowManager((ServerWorld)(Object)this);
			if(manager == null)
				return;
			manager.addScarecrow((EntityScarecrow)entityIn);
		}
	}
	
	@Inject(method = "removeEntity(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"))
	public void removeEntity(Entity entityIn, final CallbackInfo ci)
	{
		if(entityIn.getType() == VEEntities.SCARECROW.get())
		{
			ManagerScarecrows manager = BusServer.getScarecrowManager((ServerWorld)(Object)this);
			if(manager == null)
				return;
			manager.removeScarecrow((EntityScarecrow)entityIn);
		}
	}
}
