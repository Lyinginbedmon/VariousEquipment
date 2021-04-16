package com.lying.variousequipment.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ItemCoating extends Item
{
	private final Enchantment enchantment;
	
	public ItemCoating(Enchantment ench, Properties properties)
	{
		super(properties.maxStackSize(1));
		this.enchantment = ench;
	}
	
	public Enchantment getEnchantment(){ return this.enchantment; }
}
