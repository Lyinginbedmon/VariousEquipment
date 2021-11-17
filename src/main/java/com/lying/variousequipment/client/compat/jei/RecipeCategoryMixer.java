package com.lying.variousequipment.client.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.crafting.IMixerRecipe;
import com.lying.variousequipment.reference.Reference;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class RecipeCategoryMixer implements IRecipeCategory<IMixerRecipe>
{
	public static final ResourceLocation UID = new ResourceLocation(Reference.ModInfo.MOD_ID, "mixer");
	private final IDrawable background;
	private final String localizedName;
	private final IDrawable icon;
	
	public RecipeCategoryMixer(IGuiHelper guiHelper)
	{
		background = guiHelper.createBlankDrawable(96, 44);
		localizedName = I18n.format("vareqp.jei.mixer");
		icon = guiHelper.createDrawableIngredient(new ItemStack(VEItems.MIXER));
	}
	
	public ResourceLocation getUid(){ return UID; }
	public Class<? extends IMixerRecipe> getRecipeClass() { return IMixerRecipe.class; }
	public String getTitle() { return localizedName; }
	public IDrawable getBackground() { return background; }
	public IDrawable getIcon() { return icon; }
	
	@Override
	public void setIngredients(IMixerRecipe recipe, IIngredients ingredients)
	{
		List<List<ItemStack>> list = new ArrayList<>();
		for(Ingredient ingr : recipe.getIngredients())
			list.add(Arrays.asList(ingr.getMatchingStacks()));
		
		ingredients.setInputLists(VanillaTypes.ITEM, list);
		
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}
	
	@Override
	public void setRecipe(IRecipeLayout layout, IMixerRecipe recipe, IIngredients ingredients)
	{
		layout.getItemStacks().init(0, true, 12, 12);
		layout.getItemStacks().set(0, recipe.getIngredientStacks());
		
		layout.getItemStacks().init(1, false, 68, 12);
		layout.getItemStacks().set(1, recipe.getRecipeOutput());
	}

}
