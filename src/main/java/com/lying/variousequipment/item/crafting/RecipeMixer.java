package com.lying.variousequipment.item.crafting;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VERecipeTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeMixer extends RecipeStackOrIngredient implements IMixerRecipe
{
	private final ResourceLocation id;
	private final ItemStack output;
	private final String group;
	
	public RecipeMixer(ResourceLocation idIn, @Nullable String groupIn, ItemStack outputIn, ItemStack inputIn)
	{
		super(inputIn, null);
		this.id = idIn;
		this.output = outputIn;
		this.group = groupIn;
	}
	
	public RecipeMixer(ResourceLocation idIn, @Nullable String groupIn, ItemStack outputIn, Ingredient inputIn)
	{
		super(null, inputIn);
		this.id = idIn;
		this.output = outputIn;
		this.group = groupIn;
	}
	
	public ResourceLocation getId()
	{
		return id;
	}
	
	public IRecipeSerializer<?> getSerializer()
	{
		return VERecipeTypes.MIXER_SERIALIZER;
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return output;
	}
	
	public String getGroup(){ return group; }
	
	public ItemStack getIcon(){ return new ItemStack(VEBlocks.MIXER); }
	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeMixer>
	{
		public RecipeMixer read(ResourceLocation recipeId, JsonObject json)
		{
			JsonObject input = JSONUtils.getJsonObject(json, "input");
			boolean usesIngredient = input.has("ingredient");
			Ingredient inputIngredient = usesIngredient ? Ingredient.deserialize(input.get("ingredient")) : null;
			ItemStack inputStack = !usesIngredient ? ShapedRecipe.deserializeItem(input) : null;
			
			ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
			String group = JSONUtils.getString(json, "group", "");
			
			return usesIngredient ? new RecipeMixer(recipeId, group, output, inputIngredient) : new RecipeMixer(recipeId, group, output, inputStack);
		}
		
		public RecipeMixer read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			boolean usesIngredient = buffer.readBoolean();
			Ingredient inputIngredient = usesIngredient ? Ingredient.read(buffer) : null;
			ItemStack inputStack = !usesIngredient ? buffer.readItemStack() : null;
			
			ItemStack output = buffer.readItemStack();
			String group = buffer.readString();
			return usesIngredient ? new RecipeMixer(recipeId, group, output, inputIngredient) : new RecipeMixer(recipeId, group, output, inputStack);
		}
		
		public void write(PacketBuffer buffer, RecipeMixer recipe)
		{
			buffer.writeBoolean(recipe.inputIngredient != null);
			if(recipe.inputIngredient != null)
				recipe.inputIngredient.write(buffer);
			else
				buffer.writeItemStack(recipe.inputStack, false);
			
			buffer.writeItemStack(recipe.getRecipeOutput(), false);
			buffer.writeString(recipe.getGroup());
		}
	}
}
