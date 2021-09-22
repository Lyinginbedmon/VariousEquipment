package com.lying.variousequipment.item.crafting;

import com.google.gson.JsonObject;
import com.lying.variousequipment.init.VERecipeTypes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeAdvFurnace extends AbstractCookingRecipe implements IAdvFurnaceRecipe
{
	protected final ItemStack inputStack;
	
	public RecipeAdvFurnace(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn)
	{
		super(VERecipeTypes.FURNACE_TYPE, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
		this.inputStack = null;
	}
	
	public RecipeAdvFurnace(ResourceLocation idIn, String groupIn, ItemStack ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn)
	{
		super(VERecipeTypes.FURNACE_TYPE, idIn, groupIn, Ingredient.EMPTY, resultIn, experienceIn, cookTimeIn);
		this.inputStack = ingredientIn;
	}
	
	public boolean matches(IInventory inv, World worldIn)
	{
		ItemStack input = inv.getStackInSlot(0);
		if(this.inputStack != null)
			return input.getItem() == inputStack.getItem() && input.getDamage() == inputStack.getDamage() && ItemStack.areItemStackTagsEqual(input, inputStack);
		else
			return super.matches(inv, worldIn);
	}
	
	public IRecipeSerializer<?> getSerializer()
	{
		return VERecipeTypes.FURNACE_SERIALIZER;
	}
	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeAdvFurnace>
	{
		public RecipeAdvFurnace read(ResourceLocation recipeId, JsonObject json)
		{
			JsonObject input = JSONUtils.getJsonObject(json, "input");
			boolean usesIngredient = input.has("ingredient");
			Ingredient inputIngredient = usesIngredient ? Ingredient.deserialize(input.get("ingredient")) : null;
			ItemStack inputStack = !usesIngredient ? ShapedRecipe.deserializeItem(input) : null;
			
			ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
			String group = JSONUtils.getString(json, "group", "");
			float exp = JSONUtils.getFloat(json, "experience");
			int time = JSONUtils.getInt(json, "cookingtime");
			return usesIngredient ? new RecipeAdvFurnace(recipeId, group, inputIngredient, output, exp, time) : new RecipeAdvFurnace(recipeId, group, inputStack, output, exp, time);
		}
		
		public RecipeAdvFurnace read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			boolean usesIngredient = buffer.readBoolean();
			Ingredient inputIngredient = usesIngredient ? Ingredient.read(buffer) : null;
			ItemStack inputStack = !usesIngredient ? buffer.readItemStack() : null;
			
			ItemStack output = buffer.readItemStack();
			String group = buffer.readString();
			
			float exp = buffer.readFloat();
			int time = buffer.readInt();
			return usesIngredient ? new RecipeAdvFurnace(recipeId, group, inputIngredient, output, exp, time) : new RecipeAdvFurnace(recipeId, group, inputStack, output, exp, time);
		}
		
		public void write(PacketBuffer buffer, RecipeAdvFurnace recipe)
		{
			buffer.writeBoolean(recipe.ingredient != null);
			if(recipe.ingredient != null)
				recipe.ingredient.write(buffer);
			else
				buffer.writeItemStack(recipe.inputStack, false);
			
			buffer.writeItemStack(recipe.getRecipeOutput(), false);
			buffer.writeString(recipe.getGroup());
			buffer.writeFloat(recipe.experience);
			buffer.writeInt(recipe.cookTime);
		}
	}
}
