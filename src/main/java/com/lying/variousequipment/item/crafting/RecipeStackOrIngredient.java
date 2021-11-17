package com.lying.variousequipment.item.crafting;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public abstract class RecipeStackOrIngredient
{
	protected final ItemStack inputStack;
	protected final Ingredient inputIngredient;
	
	private RecipeStackOrIngredient(ItemStack stackIn, Ingredient ingredientIn)
	{
		this.inputStack = stackIn;
		this.inputIngredient = ingredientIn;
	}
	
	protected RecipeStackOrIngredient(@Nonnull ItemStack stackIn)
	{
		this(stackIn, (Ingredient)null);
	}
	
	protected RecipeStackOrIngredient(@Nonnull Ingredient ingredientIn)
	{
		this(ItemStack.EMPTY, ingredientIn);
	}
	
	public boolean matches(ItemStack inv, World worldIn)
	{
		if(this.inputIngredient != null)
			return this.inputIngredient.test(inv);
		else
			return inv.getItem() == inputStack.getItem() && inv.getDamage() == inputStack.getDamage() && ItemStack.areItemStackTagsEqual(inv, inputStack);
	}
	
	public NonNullList<ItemStack> getIngredientStacks()
	{
		if(this.inputStack.isEmpty() && this.inputIngredient != null)
		{
			ItemStack[] matching = this.inputIngredient.getMatchingStacks();
			return NonNullList.from(matching[0], matching);
		}
		else if(this.inputIngredient == null && !this.inputStack.isEmpty())
			return NonNullList.withSize(1, this.inputStack);
		else
			return NonNullList.create();
	}
}
