package com.lying.variousequipment.client.compat.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.item.ItemVial;
import com.lying.variousequipment.reference.Reference;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIVEPlugin implements IModPlugin
{
	private static final ResourceLocation ID = new ResourceLocation(Reference.ModInfo.MOD_ID, "main");
	private static final Comparator<IRecipe<?>> BY_ID = Comparator.comparing(IRecipe::getId);
	
	@Nonnull
	@Override
	public ResourceLocation getPluginUid(){ return ID; }
	
	@Override
	public void registerItemSubtypes(@Nonnull ISubtypeRegistration registry)
	{
		registry.registerSubtypeInterpreter(VEItems.VIAL_DRINKABLE, ItemVial::getSubtype);
		registry.registerSubtypeInterpreter(VEItems.VIAL_SOLUTION, ItemVial::getSubtype);
		registry.registerSubtypeInterpreter(VEItems.VIAL_THROWABLE, ItemVial::getSubtype);
	}
	
	// Recipe types for alembic, mixer, centrifuge, adv. furnace
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		registration.addRecipeCategories(
				new RecipeCategoryAlembic(registration.getJeiHelpers().getGuiHelper()),
				new RecipeCategoryMixer(registration.getJeiHelpers().getGuiHelper()),
				new RecipeCategoryCentrifuge(registration.getJeiHelpers().getGuiHelper())
				);
	}
	
	// Recipes for alembic, mixer, and centrifuge
	@Override
	public void registerRecipes(@Nonnull IRecipeRegistration registry)
	{
		registry.addRecipes(sortRecipes(VERecipeTypes.ALEMBIC_TYPE, BY_ID), RecipeCategoryAlembic.UID);
		registry.addRecipes(sortRecipes(VERecipeTypes.MIXER_TYPE, BY_ID), RecipeCategoryMixer.UID);
		registry.addRecipes(sortRecipes(VERecipeTypes.CENTRIFUGE_TYPE, BY_ID), RecipeCategoryCentrifuge.UID);
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
	{
		registry.addRecipeCatalyst(new ItemStack(VEItems.ALEMBIC), RecipeCategoryAlembic.UID);
		registry.addRecipeCatalyst(new ItemStack(VEItems.MIXER), RecipeCategoryMixer.UID);
		registry.addRecipeCatalyst(new ItemStack(VEItems.CENTRIFUGE), RecipeCategoryCentrifuge.UID);
	}
	
	private static <T extends IRecipe<C>, C extends IInventory> Collection<T> sortRecipes(IRecipeType<T> type, Comparator<? super T> comparator)
	{
		@SuppressWarnings("unchecked")
		Collection<T> recipes = (Collection<T>) VERecipeTypes.getRecipes(Minecraft.getInstance().world, type).values();
		List<T> list = new ArrayList<>(recipes);
		list.sort(comparator);
		return list;
	}
}
