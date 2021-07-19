package com.lying.variousequipment.client.renderer;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemVialThrowable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;

public class ColorHandler
{
	public static void registerColorHandlers()
	{
		ItemColors registry = Minecraft.getInstance().getItemColors();
		
		for(Item item : VEItems.DYEABLES)
			registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, item);
		
		registry.register((stack, val) -> { return val == 0 ? ((ItemVialThrowable)stack.getItem()).getColor() : -1; }, VEItems.HOLY_WATER);
	}
}
