package com.lying.variousequipment.init;

import java.util.Map;

import com.lying.variousequipment.data.recipes.AntennaRecipe;
import com.lying.variousequipment.data.recipes.CoatItemRecipe;
import com.lying.variousequipment.data.recipes.CostumeRecipe;
import com.lying.variousequipment.data.recipes.HolyWaterRecipe;
import com.lying.variousequipment.data.recipes.KitsuneTailRecipe;
import com.lying.variousequipment.data.recipes.RepeatingCrossbowRecipe;
import com.lying.variousequipment.data.recipes.ScreenRecipe;
import com.lying.variousequipment.item.crafting.IMixerRecipe;
import com.lying.variousequipment.item.crafting.RecipeMixer;
import com.lying.variousequipment.mixin.AccessorRecipeManager;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class VERecipeTypes
{
	public static final IRecipeType<IMixerRecipe> MIXER_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<RecipeMixer> MIXER_SERIALIZER = new RecipeMixer.Serializer();
	
	public static void onRecipeSerializerRegistry(RegistryEvent.Register<IRecipeSerializer<?>> event)
	{
    	IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
		ResourceLocation id = new ResourceLocation(Reference.ModInfo.MOD_ID, "mixer");
		Registry.register(Registry.RECIPE_TYPE, id, MIXER_TYPE);
		registry.register(MIXER_SERIALIZER.setRegistryName(id));
		
    	registry.register(CoatItemRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "coat_item")));
    	registry.register(HolyWaterRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "holy_water")));
    	registry.register(KitsuneTailRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "kitsune_tail")));
    	registry.register(AntennaRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "antennae")));
    	registry.register(CostumeRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "costume")));
    	registry.register(ScreenRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "screens")));
    	registry.register(RepeatingCrossbowRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "reload_repeating_crossbow")));
	}
	
	public static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T>
	{
		public String toString(){ return Registry.RECIPE_TYPE.getKey(this).toString(); }
	}
	
	public static <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, IRecipe<C>> getRecipes(World world, IRecipeType<T> type)
	{
		return ((AccessorRecipeManager)world.getRecipeManager()).getVERecipes(type);
	}
}
