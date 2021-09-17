package com.lying.variousequipment.item;

import com.lying.variousequipment.item.vial.Vial;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemVialSolution extends Item
{
	public ItemVialSolution(Properties properties)
	{
		super(properties.maxStackSize(1).maxDamage(5));
	}
	
	public int getColor(ItemStack stack){ return ItemVial.getVialFromItem(stack).getColor(); }
	
	public ITextComponent getDisplayName(ItemStack stack)
	{
		return new TranslationTextComponent("item.vareqp.vial_solution", new TranslationTextComponent("vial."+Reference.ModInfo.MOD_ID+"."+ItemVial.getVialFromItem(stack).getRegistryName().getPath()));
	}
	
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if(this.isInGroup(group))
			for(Vial.Builder vial : Vial.ALL_VIALS)
				items.add(ItemVial.addVialToItemStack(new ItemStack(this), vial));
	}
}
