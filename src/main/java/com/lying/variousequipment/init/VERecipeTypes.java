package com.lying.variousequipment.init;

import java.util.Map;

import com.lying.variousequipment.data.recipes.*;
import com.lying.variousequipment.item.crafting.*;
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
	
	public static final IRecipeType<ICentrifugeRecipe> CENTRIFUGE_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<RecipeCentrifuge> CENTRIFUGE_SERIALIZER = new RecipeCentrifuge.Serializer();
	
	public static final IRecipeType<IAlembicRecipe> ALEMBIC_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<RecipeAlembic> ALEMBIC_SERIALIZER = new RecipeAlembic.Serializer();
	
	public static final IRecipeType<IAdvFurnaceRecipe> FURNACE_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<RecipeAdvFurnace> FURNACE_SERIALIZER = new RecipeAdvFurnace.Serializer();
	
	public static void onRecipeSerializerRegistry(RegistryEvent.Register<IRecipeSerializer<?>> event)
	{
    	IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
		ResourceLocation mixerId = new ResourceLocation(Reference.ModInfo.MOD_ID, "mixer");
		Registry.register(Registry.RECIPE_TYPE, mixerId, MIXER_TYPE);
		registry.register(MIXER_SERIALIZER.setRegistryName(mixerId));
		
		ResourceLocation centrifugeId = new ResourceLocation(Reference.ModInfo.MOD_ID, "centrifuge");
		Registry.register(Registry.RECIPE_TYPE, centrifugeId, CENTRIFUGE_TYPE);
		registry.register(CENTRIFUGE_SERIALIZER.setRegistryName(centrifugeId));
		
		ResourceLocation alembicId = new ResourceLocation(Reference.ModInfo.MOD_ID, "alembic");
		Registry.register(Registry.RECIPE_TYPE, alembicId, ALEMBIC_TYPE);
		registry.register(ALEMBIC_SERIALIZER.setRegistryName(alembicId));
		
		ResourceLocation furnaceId = new ResourceLocation(Reference.ModInfo.MOD_ID, "adv_smelting");
		Registry.register(Registry.RECIPE_TYPE, furnaceId, FURNACE_TYPE);
		registry.register(FURNACE_SERIALIZER.setRegistryName(furnaceId));
		
    	registry.register(CoatItemRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "coat_item")));
    	registry.register(HolyWaterRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "holy_water")));
    	registry.register(KitsuneTailRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "kitsune_tail")));
    	registry.register(AntennaRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "antennae")));
    	registry.register(PegLegRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "peg_leg")));
    	registry.register(CostumeRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "costume")));
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
