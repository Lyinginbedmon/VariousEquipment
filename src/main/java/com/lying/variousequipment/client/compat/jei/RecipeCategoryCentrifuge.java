package com.lying.variousequipment.client.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.crafting.ICentrifugeRecipe;
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

public class RecipeCategoryCentrifuge implements IRecipeCategory<ICentrifugeRecipe>
{
	public static final ResourceLocation UID = new ResourceLocation(Reference.ModInfo.MOD_ID, "centrifuge");
	private final IDrawable background;
	private final String localizedName;
	private final IDrawable icon;
	
	public RecipeCategoryCentrifuge(IGuiHelper guiHelper)
	{
		background = guiHelper.createBlankDrawable(96, 44);
		localizedName = I18n.format("vareqp.jei.centrifuge");
		icon = guiHelper.createDrawableIngredient(new ItemStack(VEItems.CENTRIFUGE));
	}
	
	public ResourceLocation getUid(){ return UID; }
	public Class<? extends ICentrifugeRecipe> getRecipeClass() { return ICentrifugeRecipe.class; }
	public String getTitle() { return localizedName; }
	public IDrawable getBackground() { return background; }
	public IDrawable getIcon() { return icon; }
	
	@Override
	public void setIngredients(ICentrifugeRecipe recipe, IIngredients ingredients)
	{
		List<List<ItemStack>> list = new ArrayList<>();
		for(Ingredient ingr : recipe.getIngredients())
			list.add(Arrays.asList(ingr.getMatchingStacks()));
		ingredients.setInputLists(VanillaTypes.ITEM, list);
		
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getAnticipatedOutput());
	}
	
	@Override
	public void setRecipe(IRecipeLayout layout, ICentrifugeRecipe recipe, IIngredients ingredients)
	{
		layout.getItemStacks().init(0, true, 6, 12);
		layout.getItemStacks().set(0, recipe.getIngredientStacks());
		
		List<ItemStack> output = Lists.newArrayList();
		output.add(recipe.getAnticipatedOutput());
		for(int i=0; i<output.size(); i++)
		{
			layout.getItemStacks().init(i + 1, false, 32 + (i%4 * 9), (i/4 * 9));
			layout.getItemStacks().set(i + 1, output.get(i));
		}
	}
}
