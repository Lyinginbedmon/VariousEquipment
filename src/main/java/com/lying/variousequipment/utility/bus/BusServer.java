package com.lying.variousequipment.utility.bus;

import com.lying.variousequipment.entity.EntityWagon;
import com.lying.variousequipment.init.VEEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BusServer
{
	@SubscribeEvent
	public static void onPlayerReinsInteractEvent(EntityInteract event)
	{
		PlayerEntity player = event.getPlayer();
		Entity ent = event.getTarget();
		if(ent instanceof LivingEntity && !(ent.getType() == VEEntities.WAGON.get()))
			if(checkForWagonReins(player, (LivingEntity)ent))
			{
				event.setCancellationResult(ActionResultType.FAIL);
				event.setCanceled(true);
			}
	}
	
	public static boolean checkForWagonReins(PlayerEntity player, LivingEntity living)
	{
		EntityWagon closestWagon = EntityWagon.getConnectedWagon(player);
		if(closestWagon != null)
		{
			closestWagon.setReinsHolder(living, true);
			return true;
		}
		return false;
	}
}
