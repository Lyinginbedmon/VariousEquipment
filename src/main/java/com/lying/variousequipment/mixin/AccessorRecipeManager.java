package com.lying.variousequipment.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

@Mixin(RecipeManager.class)
public interface AccessorRecipeManager
{
	@Invoker("getRecipes")
	<C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, IRecipe<C>> getVERecipes(IRecipeType<T> type);
}
