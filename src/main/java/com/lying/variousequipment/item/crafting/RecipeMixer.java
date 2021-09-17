package com.lying.variousequipment.item.crafting;

import java.util.Objects;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
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
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeMixer implements IMixerRecipe
{
	private final ResourceLocation id;
	private final ItemStack output;
	private final Ingredient input;
	private final String group;
	
	public RecipeMixer(ResourceLocation idIn, @Nullable String groupIn, ItemStack outputIn, Ingredient inputIn)
	{
		this.id = idIn;
		this.output = outputIn;
		this.input = inputIn;
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
	
	public boolean matches(ItemStack inv, World worldIn)
	{
		return input.test(inv);
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
			JsonElement input = Objects.requireNonNull(json.get("input"));
			Ingredient ing = Ingredient.deserialize(input);
			ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
			String group = JSONUtils.getString(json, "group", "");
			
			return new RecipeMixer(recipeId, group, output, ing);
		}
		
		public RecipeMixer read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			Ingredient input = Ingredient.read(buffer);
			ItemStack output = buffer.readItemStack();
			String group = buffer.readString();
			return new RecipeMixer(recipeId, group, output, input);
		}
		
		public void write(PacketBuffer buffer, RecipeMixer recipe)
		{
			recipe.getIngredients().get(0).write(buffer);
			buffer.writeItemStack(recipe.getRecipeOutput(), false);
			buffer.writeString(recipe.getGroup());
		}
	}
}
