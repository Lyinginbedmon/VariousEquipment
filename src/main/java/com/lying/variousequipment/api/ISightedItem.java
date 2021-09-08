package com.lying.variousequipment.api;

import net.minecraft.item.ItemStack;

public interface ISightedItem
{
	/** Returns true if the item should render in the middle of the screen */
	public boolean aimDownSights(ItemStack itemStackIn);
	
	public int getDrawTime(ItemStack itemStackIn);
}
