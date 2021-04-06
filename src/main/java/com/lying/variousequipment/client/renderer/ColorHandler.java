package com.lying.variousequipment.client.renderer;

import com.lying.variousequipment.init.VEItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.DyeableArmorItem;

public class ColorHandler
{
	public static void registerColorHandlers()
	{
		ItemColors registry = Minecraft.getInstance().getItemColors();
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_ARCHFEY);
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_MASK);
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_HOOD);
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_WITCH);
	}
}
