package com.lying.variousequipment.item.crafting;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VERecipeTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeAlembic implements IAlembicRecipe
{
	private final ResourceLocation id;
	
	private final NonNullList<Ingredient> ingredients;
	private final Ingredient outputIngredient;
	private final ItemStack outputStack;
	
	private final ItemStack result;
	private final String group;
	
	private RecipeAlembic(ResourceLocation idIn, @Nullable String groupIn, ItemStack outStaIn, Ingredient outIngIn, ItemStack resultIn, Ingredient... ingredientsIn)
	{
		this.id = idIn;
		this.result = resultIn;
		
		this.outputStack = outStaIn;
		this.outputIngredient = outIngIn;
		
		ingredients = NonNullList.withSize(Math.min(3, ingredientsIn.length), Ingredient.EMPTY);
		for(int i=0; i<ingredients.size(); i++)
			ingredients.set(i, ingredientsIn[i]);
		
		this.group = groupIn;
	}
	
	public RecipeAlembic(ResourceLocation idIn, @Nullable String groupIn, ItemStack inputIn, ItemStack resultIn, Ingredient... ingredientsIn)
	{
		this(idIn, groupIn, inputIn, null, resultIn, ingredientsIn);
	}
	
	public RecipeAlembic(ResourceLocation idIn, @Nullable String groupIn, Ingredient inputIn, ItemStack resultIn, Ingredient... ingredientsIn)
	{
		this(idIn, groupIn, ItemStack.EMPTY, inputIn, resultIn, ingredientsIn);
	}
	
	public RecipeAlembic(ResourceLocation idIn, @Nullable String groupIn, ItemStack resultIn, Ingredient... ingredientsIn)
	{
		this(idIn, groupIn, ItemStack.EMPTY, null, resultIn, ingredientsIn);
	}
	
	public ResourceLocation getId()
	{
		return id;
	}
	
	public IRecipeSerializer<?> getSerializer()
	{
		return VERecipeTypes.ALEMBIC_SERIALIZER;
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return result;
	}
	
	public boolean matches(NonNullList<ItemStack> boiling, ItemStack outputSlot)
	{
		// Check if all ingredients are filled
		List<ItemStack> inputs = Lists.newArrayList();
		boiling.forEach((stack) -> { if(!stack.isEmpty()) inputs.add(stack); });
		if(!(inputs.size() == ingredients.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.ingredients) != null))
			return false;
		
		// Check if output slot is valid
		boolean outputMatch = true;
		if(this.outputIngredient != null)
			outputMatch = this.outputIngredient.test(outputSlot);
		else if(!this.outputStack.isEmpty())
			outputMatch = outputSlot.getItem() == outputStack.getItem() && outputSlot.getDamage() == outputStack.getDamage() && ItemStack.areItemStackTagsEqual(outputSlot, outputStack);
		else
			outputMatch = outputSlot.isEmpty();
		
		return outputMatch;
	}
	
	public String getGroup(){ return group; }
	
	public ItemStack getIcon(){ return new ItemStack(VEBlocks.ALEMBIC); }
	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeAlembic>
	{
		public RecipeAlembic read(ResourceLocation recipeId, JsonObject json)
		{
			JsonObject input = JSONUtils.getJsonObject(json, "input");
			
			ItemStack outputSlotStack = ItemStack.EMPTY;
			Ingredient outputSlotIng = null;
			if(input.has("collection"))
			{
				JsonObject collection = JSONUtils.getJsonObject(input, "collection");
				if(collection.has("ingredient"))
					outputSlotIng = Ingredient.deserialize(collection.get("ingredient"));
				outputSlotStack = ShapedRecipe.deserializeItem(collection);
			}
			
			Ingredient[] ingredients;
			if(input.has("ingredients"))
			{
				JsonArray ingredientArray = input.getAsJsonArray("ingredients");
				List<Ingredient> ingredientList = Lists.newArrayList();
				for(int i=0; i<ingredientArray.size(); i++)
				{
					Ingredient ingredient = Ingredient.deserialize(ingredientArray.get(i));
					if(!ingredient.hasNoMatchingItems())
						ingredientList.add(ingredient);
				}
				ingredients = ingredientList.toArray(new Ingredient[0]);
			}
			else
				ingredients = new Ingredient[0];
			
			ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
			String group = JSONUtils.getString(json, "group", "");
			
			return new RecipeAlembic(recipeId, group, outputSlotStack, outputSlotIng, result, ingredients);
		}
		
		public RecipeAlembic read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			ItemStack outputSlotStack = buffer.readItemStack();
			Ingredient outputSlotIng = buffer.readBoolean() ? Ingredient.read(buffer) : null;
			
			Ingredient[] ingredients = new Ingredient[buffer.readInt()];
			for(int i=0; i<ingredients.length; ++i)
				ingredients[i] = Ingredient.read(buffer);
			
			ItemStack result = buffer.readItemStack();
			String group = buffer.readString();
			return new RecipeAlembic(recipeId, group, outputSlotStack, outputSlotIng, result, ingredients);
		}
		
		public void write(PacketBuffer buffer, RecipeAlembic recipe)
		{
			buffer.writeItemStack(recipe.outputStack);
			buffer.writeBoolean(recipe.outputIngredient != null);
			if(recipe.outputIngredient != null)
				recipe.outputIngredient.write(buffer);
			
			buffer.writeInt(recipe.ingredients.size());
			for(Ingredient ingredient : recipe.ingredients)
				ingredient.write(buffer);
			
			buffer.writeItemStack(recipe.getRecipeOutput(), false);
			buffer.writeString(recipe.getGroup());
		}
	}
}
