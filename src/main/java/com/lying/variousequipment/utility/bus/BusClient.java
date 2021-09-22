package com.lying.variousequipment.utility.bus;

import com.lying.variousequipment.data.VEItemTags;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemHatHood;
import com.lying.variousequipment.network.PacketHandler;
import com.lying.variousequipment.network.PacketItemShake;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
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
	
	@SubscribeEvent
	public static void onPlayerShakeItem(PlayerInteractEvent.LeftClickEmpty event)
	{
		ItemStack heldItem = event.getItemStack();
		if(VEItemTags.SHAKEABLES.contains(heldItem.getItem()))
			PacketHandler.sendToServer(new PacketItemShake(event.getPlayer()));
	}
}