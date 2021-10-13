package com.lying.variousequipment.utility.bus;

import java.util.HashMap;
import java.util.Map;

import com.lying.variousequipment.entity.EntityWagon;
import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.utility.world.ManagerScarecrows;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BusServer
{
	private static Map<RegistryKey<World>, ManagerScarecrows> scarecrowManagers = new HashMap<>();
	
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
		if(closestWagon != null && EntityWagon.getConnectedWagon(living) == null)
		{
			closestWagon.setReinsHolder(living, true);
			return true;
		}
		return false;
	}
	
	public static ManagerScarecrows getScarecrowManager(World worldIn)
	{
		if(worldIn.isRemote) return null;
		RegistryKey<World> worldID = worldIn.getDimensionKey();
		ManagerScarecrows manager = scarecrowManagers.containsKey(worldID) ? scarecrowManagers.get(worldID) : new ManagerScarecrows(worldIn);
		if(!scarecrowManagers.containsKey(worldID))
			scarecrowManagers.put(worldID, manager);
		return manager;
	}
	
	@SubscribeEvent
	public static void onWorldTick(WorldTickEvent event)
	{
		World world = event.world;
		if(world.isRemote) return;
		
		RegistryKey<World> worldID = world.getDimensionKey();
		
		ManagerScarecrows manager = scarecrowManagers.containsKey(worldID) ? scarecrowManagers.get(worldID) : new ManagerScarecrows(world);
		manager.tick();
		if(!scarecrowManagers.containsKey(worldID))
			scarecrowManagers.put(worldID, manager);
	}
}
