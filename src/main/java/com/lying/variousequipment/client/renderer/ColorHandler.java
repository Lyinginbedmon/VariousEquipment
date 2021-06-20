package com.lying.variousequipment.client.renderer;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemVialThrowable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.IDyeableArmorItem;

public class ColorHandler
{
	public static void registerColorHandlers()
	{
		ItemColors registry = Minecraft.getInstance().getItemColors();
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_ARCHFEY);
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_MASK);
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_HOOD);
		registry.register((stack, val) -> { return val == 0 ? ((DyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HAT_WITCH);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.EARS_CAT);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.EARS_WOLF);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.GILLS_AXOLOTL);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.ANTENNA);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HORNS_DEER);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HORNS_RAM);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HORNS_KIRIN);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HORNS_KIRIN_STORM);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.HORNS_HARTEBEEST);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_KIRIN);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_KOBOLD);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_CAT);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_WOLF);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_FOX);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_RAT);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_CAT_2);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_DRAGON);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_DEVIL);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_RABBIT);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_HORSE);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_DRAGONFLY);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_ANT);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_LIZARD);
		registry.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, VEItems.TAIL_LIZARD2);
		registry.register((stack, val) -> { return val == 0 ? ((ItemVialThrowable)stack.getItem()).getColor() : -1; }, VEItems.HOLY_WATER);
	}
}
