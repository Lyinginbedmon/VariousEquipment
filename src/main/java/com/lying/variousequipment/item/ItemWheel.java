package com.lying.variousequipment.item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;

public class ItemWheel extends Item
{
	private final ItemTier tier;
	
	public ItemWheel(ItemTier tier, Properties properties)
	{
		super(properties.group(VEItemGroup.GEAR).maxDamage(tier.getMaxUses()));
		this.tier = tier;
	}
	
	public int getItemEnchantability(){ return tier.getEnchantability(); }
	
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		return 
				enchantment == Enchantments.UNBREAKING ||
				enchantment == Enchantments.FEATHER_FALLING ||
				enchantment == Enchantments.FIRE_PROTECTION ||
				enchantment == Enchantments.BLAST_PROTECTION ||
				enchantment == Enchantments.PROTECTION ||
				enchantment == Enchantments.FROST_WALKER;
	}
}
