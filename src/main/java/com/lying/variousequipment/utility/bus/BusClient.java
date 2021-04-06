package com.lying.variousequipment.utility.bus;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemHatHood;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BusClient
{
	@SubscribeEvent
	public static void onRenderHoodedPlayer(RenderNameplateEvent event)
	{
		Entity ent = event.getEntity();
		if(ent instanceof LivingEntity)
		{
			LivingEntity entity = (LivingEntity)ent;
			if(entity.hasItemInSlot(EquipmentSlotType.HEAD))
			{
				ItemStack helmet = entity.getItemStackFromSlot(EquipmentSlotType.HEAD);
				if(helmet.getItem() == VEItems.HAT_HOOD && ItemHatHood.getIsUp(helmet))
					event.setResult(Result.DENY);
			}
		}
	}
}
