package com.lying.variousequipment.client.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.crafting.IAlembicRecipe;
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

public class RecipeCategoryAlembic implements IRecipeCategory<IAlembicRecipe>
{
	public static final ResourceLocation UID = new ResourceLocation(Reference.ModInfo.MOD_ID, "alembic");
	private final IDrawable background;
	private final String localizedName;
	private final IDrawable icon;
	
	public RecipeCategoryAlembic(IGuiHelper guiHelper)
	{
		background = guiHelper.createBlankDrawable(96, 44);
		localizedName = I18n.format("vareqp.jei.alembic");
		icon = guiHelper.createDrawableIngredient(new ItemStack(VEItems.ALEMBIC));
	}
	
	public ResourceLocation getUid(){ return UID; }
	public Class<? extends IAlembicRecipe> getRecipeClass() { return IAlembicRecipe.class; }
	public String getTitle() { return localizedName; }
	public IDrawable getBackground() { return background; }
	public IDrawable getIcon() { return icon; }
	
	@Override
	public void setIngredients(IAlembicRecipe recipe, IIngredients ingredients)
	{
		List<List<ItemStack>> list = new ArrayList<>();
		for(Ingredient ingr : recipe.getIngredients())
			list.add(Arrays.asList(ingr.getMatchingStacks()));
		
		ingredients.setInputLists(VanillaTypes.ITEM, list);
		
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}
	
	@Override
	public void setRecipe(IRecipeLayout layout, IAlembicRecipe recipe, IIngredients ingredients)
	{
		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		switch(inputs.size())
		{
			case 1:
				layout.getItemStacks().init(0, true, 18, 0);
				layout.getItemStacks().set(0, inputs.get(0));
				break;
			case 2:
				layout.getItemStacks().init(0, true, 9, 0);
				layout.getItemStacks().set(0, inputs.get(0));
				
				layout.getItemStacks().init(1, true, 27, 0);
				layout.getItemStacks().set(1, inputs.get(1));
				break;
			case 3:
				for(int i=0; i<3; i++)
				{
					layout.getItemStacks().init(i, true, i*18, 0);
					layout.getItemStacks().set(i, inputs.get(i));
				}
				break;
		}
		
		List<ItemStack> outputSlot = recipe.getOutputSlotContents();
		if(!outputSlot.isEmpty())
		{
			layout.getItemStacks().init(4, true, 37, 25);
			layout.getItemStacks().set(4, outputSlot);
		}
		
		layout.getItemStacks().init(7, false, 68, 25);
		layout.getItemStacks().set(7, recipe.getRecipeOutput());
	}

}
