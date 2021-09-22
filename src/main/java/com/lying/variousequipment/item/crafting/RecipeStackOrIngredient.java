package com.lying.variousequipment.item.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;

public abstract class RecipeStackOrIngredient
{
	protected final ItemStack inputStack;
	protected final Ingredient inputIngredient;
	
	protected RecipeStackOrIngredient(ItemStack stackIn, Ingredient ingredientIn)
	{
		this.inputStack = stackIn;
		this.inputIngredient = ingredientIn;
	}
	
	public boolean matches(ItemStack inv, World worldIn)
	{
		if(this.inputIngredient != null)
			return this.inputIngredient.test(inv);
		else
			return inv.getItem() == inputStack.getItem() && inv.getDamage() == inputStack.getDamage() && ItemStack.areItemStackTagsEqual(inv, inputStack);
	}
}
