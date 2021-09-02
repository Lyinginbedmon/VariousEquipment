package com.lying.variousequipment.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;

public class ItemWheel extends Item
{
	public ItemWheel(ItemTier tier, Properties properties)
	{
		super(properties.group(VEItemGroup.GEAR).maxDamage(tier.getMaxUses()));
	}
}
