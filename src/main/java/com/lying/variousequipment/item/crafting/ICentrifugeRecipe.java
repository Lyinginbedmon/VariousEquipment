package com.lying.variousequipment.item.crafting;

import java.util.Random;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface ICentrifugeRecipe extends IRecipe<IInventory>
{
	public static final ResourceLocation TYPE_ID = new ResourceLocation(Reference.ModInfo.MOD_ID, "centrifuge");
	
	public boolean matches(ItemStack inv, World worldIn);
	
	public default ItemStack getCraftingResult(IInventory inv)
	{
		return ItemStack.EMPTY;
	}
	
	public default boolean canFit(int width, int height)
	{
		return false;
	}
	
	public default boolean matches(IInventory inv, World world){ return false; }
	
	public default boolean isDynamic(){ return true; }
	
	public NonNullList<ItemStack> getRecipeOutput(Random rand, World world);
	public ItemStack getAnticipatedOutput();
	
	public NonNullList<ItemStack> getIngredientStacks();
	
	public default IRecipeType<?> getType()
	{
		return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
	}
}
