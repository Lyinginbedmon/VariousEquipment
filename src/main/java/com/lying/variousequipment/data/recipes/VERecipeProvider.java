package com.lying.variousequipment.data.recipes;

import java.util.function.Consumer;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class VERecipeProvider extends RecipeProvider
{
	public VERecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
	{
		registerSpecialRecipe(consumer, CoatItemRecipe.SERIALIZER);
	}
	
	@SuppressWarnings("deprecation")
	private void registerSpecialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer)
	{
		ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);
		CustomRecipeBuilder.customRecipe(serializer).build(consumer, new ResourceLocation(Reference.ModInfo.MOD_ID,"dynamic/"+name.getPath()).toString());
	}
	
	@Override
	public String getName()
	{
		return "Various Equipment crafting recipes";
	}
}
