package com.lying.variousequipment.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public interface IShieldBreakItem
{
	/** Returns the duration of knocking (in ticks) caused by the given item */
	public default int breakTime(ItemStack stack){ return 100; }
	
	/** Returns true if attacks from this item should cause the given item to be knocked */
	public default boolean isShield(ItemStack stack){ return stack.getItem() == Items.SHIELD; }
}
