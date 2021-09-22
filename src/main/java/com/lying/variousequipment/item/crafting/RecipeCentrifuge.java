package com.lying.variousequipment.item.crafting;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VERecipeTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class RecipeCentrifuge extends RecipeStackOrIngredient implements ICentrifugeRecipe
{
	private final ResourceLocation id;
	
	private final ItemStack outputStack;
	private final ResourceLocation outputTable;
	
	private final String group;
	
	public RecipeCentrifuge(ResourceLocation idIn, @Nullable String groupIn, ItemStack outputIn, ItemStack inputIn)
	{
		super(inputIn, null);
		this.id = idIn;
		this.outputStack = outputIn;
		this.outputTable = null;
		this.group = groupIn;
	}
	
	public RecipeCentrifuge(ResourceLocation idIn, @Nullable String groupIn, ResourceLocation outputIn, ItemStack inputIn)
	{
		super(inputIn, null);
		this.id = idIn;
		this.outputStack = null;
		this.outputTable = outputIn;
		this.group = groupIn;
	}
	
	public RecipeCentrifuge(ResourceLocation idIn, @Nullable String groupIn, ItemStack outputIn, Ingredient inputIn)
	{
		super(null, inputIn);
		this.id = idIn;
		this.outputStack = outputIn;
		this.outputTable = null;
		this.group = groupIn;
	}
	
	public RecipeCentrifuge(ResourceLocation idIn, @Nullable String groupIn, ResourceLocation outputIn, Ingredient inputIn)
	{
		super(null, inputIn);
		this.id = idIn;
		this.outputStack = null;
		this.outputTable = outputIn;
		this.group = groupIn;
	}
	
	public ResourceLocation getId()
	{
		return id;
	}
	
	public IRecipeSerializer<?> getSerializer()
	{
		return VERecipeTypes.CENTRIFUGE_SERIALIZER;
	}
	
	public ItemStack getRecipeOutput()
	{
		return outputStack == null ? ItemStack.EMPTY : outputStack;
	}
	
	@Override
	public NonNullList<ItemStack> getRecipeOutput(Random rand, World world)
	{
		if(this.outputStack != null)
			return NonNullList.withSize(1, outputStack);
		
		if(this.outputTable != null)
		{
			MinecraftServer server = world.getServer();
			if(server == null) return NonNullList.withSize(1, ItemStack.EMPTY);
			
			LootTable table = server.getLootTableManager().getLootTableFromLocation(this.outputTable);
			LootContext.Builder context = (new LootContext.Builder((ServerWorld)world)).withRandom(rand);
			List<ItemStack> loot = table.generate(context.build(LootParameterSets.EMPTY));
			return NonNullList.from(ItemStack.EMPTY, loot.toArray(new ItemStack[0]));
		}
		return NonNullList.withSize(1, ItemStack.EMPTY);
	}
	
	public String getGroup(){ return group; }
	
	public ItemStack getIcon(){ return new ItemStack(VEBlocks.CENTRIFUGE); }
	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeCentrifuge>
	{
		public RecipeCentrifuge read(ResourceLocation recipeId, JsonObject json)
		{
			String group = JSONUtils.getString(json, "group", "");
			
			JsonObject input = JSONUtils.getJsonObject(json, "input");
			boolean usesIngredient = input.has("ingredient");
			Ingredient inputIngredient = usesIngredient ? Ingredient.deserialize(input.get("ingredient")) : null;
			ItemStack inputStack = !usesIngredient ? ShapedRecipe.deserializeItem(input) : null;
			
			JsonObject output = JSONUtils.getJsonObject(json, "output");
			if(output.has("table"))
			{
				ResourceLocation table = new ResourceLocation(JSONUtils.getString(output, "table"));
				if(usesIngredient)
					return new RecipeCentrifuge(recipeId, group, table, inputIngredient);
				else
					return new RecipeCentrifuge(recipeId, group, table, inputStack);
			}
			else
			{
				ItemStack outputStack = ShapedRecipe.deserializeItem(output);
				if(usesIngredient)
					return new RecipeCentrifuge(recipeId, group, outputStack, inputIngredient);
				else
					return new RecipeCentrifuge(recipeId, group, outputStack, inputStack);
			}
		}
		
		public RecipeCentrifuge read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			boolean usesIngredient = buffer.readBoolean();
			Ingredient inputIngredient = usesIngredient ? Ingredient.read(buffer) : null;
			ItemStack inputStack = !usesIngredient ? buffer.readItemStack() : null;
			
			String group = buffer.readString();
			if(buffer.readBoolean())
			{
				ResourceLocation table = new ResourceLocation(buffer.readString());
				if(usesIngredient)
					return new RecipeCentrifuge(recipeId, group, table, inputIngredient);
				else
					return new RecipeCentrifuge(recipeId, group, table, inputStack);
			}
			else
			{
				ItemStack outputStack = buffer.readItemStack();
				if(usesIngredient)
					return new RecipeCentrifuge(recipeId, group, outputStack, inputIngredient);
				else
					return new RecipeCentrifuge(recipeId, group, outputStack, inputStack);
			}
		}
		
		public void write(PacketBuffer buffer, RecipeCentrifuge recipe)
		{
			buffer.writeBoolean(recipe.inputIngredient != null);
			if(recipe.inputIngredient != null)
				recipe.inputIngredient.write(buffer);
			else
				buffer.writeItemStack(recipe.inputStack, false);
			
			buffer.writeString(recipe.getGroup());
			
			buffer.writeBoolean(recipe.outputTable != null);
			if(recipe.outputStack != null)
				buffer.writeItemStack(recipe.getRecipeOutput(), false);
			else if(recipe.outputTable != null)
				buffer.writeString(recipe.outputTable.toString());
		}
	}
}
