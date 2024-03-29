package com.lying.variousequipment.item.crafting;

import javax.annotation.Nonnull;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface IAlembicRecipe extends IRecipe<IInventory>
{
	public static final ResourceLocation TYPE_ID = new ResourceLocation(Reference.ModInfo.MOD_ID, "alembic");
	
	public boolean matches(NonNullList<ItemStack> boiling, ItemStack outputSlot);
	
	public default ItemStack getCraftingResult(IInventory inv)
	{
		return ItemStack.EMPTY;
	}
	
	public default boolean canFit(int width, int height)
	{
		return false;
	}
	
	public default boolean matches(IInventory inv, World world){ return false; }
	
	public NonNullList<Ingredient> getIngredients();
	public NonNullList<ItemStack> getOutputSlotContents();
	
	public default boolean isDynamic(){ return true; }
	
	public ItemStack getRecipeOutput();
	
	public default ItemStack getRecipeOut(@Nonnull ItemStack input)
	{
		return getRecipeOutput().copy();
	}
	
	public default IRecipeType<?> getType()
	{
		return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
	}
}
