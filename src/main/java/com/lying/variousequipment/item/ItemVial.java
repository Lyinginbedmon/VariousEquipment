package com.lying.variousequipment.item;

import net.minecraft.item.Item;

public abstract class ItemVial extends Item
{
	private final int color;
	
	public ItemVial(Item.Properties properties, int colorIn)
	{
		super(properties);
		color = colorIn;
	}
	
	public ItemVial(Item.Properties properties)
	{
		this(properties.maxStackSize(8), 0);
	}
	
	public int getColor(){ return color; }
}
