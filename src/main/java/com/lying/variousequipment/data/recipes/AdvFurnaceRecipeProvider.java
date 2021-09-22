package com.lying.variousequipment.data.recipes;

import java.util.function.Consumer;

import com.google.gson.JsonObject;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.item.ItemVial;
import com.lying.variousequipment.item.vial.Vial;
import com.lying.variousequipment.reference.Reference;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.ResourceLocation;

/**
 * Provides furnace recipes that can accept arbitrary itemstack inputs instead of just ingredients.
 * @author Lying
 */
public class AdvFurnaceRecipeProvider extends RecipeProvider
{
	public AdvFurnaceRecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}
	
	public String getName(){ return Reference.ModInfo.MOD_NAME+" advanced furnace recipes"; }
	
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
	{
		for(Vial.Builder builder : new Vial.Builder[]{Vial.ALCHEMIST_FIRE, Vial.ANTITOXIN, Vial.TANGLEFOOT_BAG, Vial.THUNDERSTONE})
		{
			Vial vial = builder.create();
			ItemStack input = new ItemStack(VEItems.VIAL_SOLUTION);
			ItemVial.addVialToItemStack(input, builder);
			
			ItemStack output = new ItemStack(vial.isDrinkable() ? VEItems.VIAL_DRINKABLE : VEItems.VIAL_THROWABLE);
			ItemVial.addVialToItemStack(output, builder);
			consumer.accept(new FinishedRecipe(id(vial.getRegistryName().getPath()), "", input, output, 0.7F, 200));
		}
	}
	
	protected ResourceLocation id(String s){ return new ResourceLocation(Reference.ModInfo.MOD_PREFIX+"adv_smelting/" + s); }
	
	public static class FinishedRecipe implements IFinishedRecipe
	{
		private final ResourceLocation id;
		private final ItemStack inputStack;
		private final Ingredient inputIngredient;
		private final ItemStack output;
		private final String group;
		private final float experience;
		private final int cookingtime;
		
		public FinishedRecipe(ResourceLocation idIn, String groupIn, ItemStack inputIn, ItemStack outputIn, float expIn, int timeIn)
		{
			this.id = idIn;
			this.inputStack = inputIn;
			this.inputIngredient = null;
			this.output = outputIn;
			this.group = groupIn;
			this.experience = expIn;
			this.cookingtime = timeIn;
		}
		
		public FinishedRecipe(ResourceLocation idIn, String groupIn, Ingredient inputIn, ItemStack outputIn, float expIn, int timeIn)
		{
			this.id = idIn;
			this.inputStack = null;
			this.inputIngredient = inputIn;
			this.output = outputIn;
			this.group = groupIn;
			this.experience = expIn;
			this.cookingtime = timeIn;
		}
		
		public void serialize(JsonObject json)
		{
			if(this.inputIngredient != null)
			{
				JsonObject input = new JsonObject();
				input.add("ingredient", this.inputIngredient.serialize());
				json.add("input", input);
			}
			else
				json.add("input", serializeStack(inputStack));
			
			json.add("result", serializeStack(output));
			if(!group.isEmpty())
				json.addProperty("group", group);
			json.addProperty("experience", experience);
			json.addProperty("cookingtime", cookingtime);
		}
		
		public ResourceLocation getID(){ return id; }
		
		public IRecipeSerializer<?> getSerializer()
		{
			return VERecipeTypes.FURNACE_SERIALIZER;
		}
		
		public JsonObject getAdvancementJson()
		{
			return null;
		}
		
		public ResourceLocation getAdvancementID()
		{
			return null;
		}
		
		private static JsonObject serializeStack(ItemStack stack)
		{
			CompoundNBT nbt = stack.write(new CompoundNBT());
			byte c = nbt.getByte("Count");
			if(c != 1)
				nbt.putByte("count", c);
			
			nbt.remove("Count");
			renameTag(nbt, "id", "item");
			renameTag(nbt, "tag", "nbt");
			
			Dynamic<INBT> dyn = new Dynamic<>(NBTDynamicOps.INSTANCE, nbt);
			return dyn.convert(JsonOps.INSTANCE).getValue().getAsJsonObject();
		}
		
		private static void renameTag(CompoundNBT nbt, String oldName, String newName)
		{
			INBT tag = nbt.get(oldName);
			if (tag != null)
			{
				nbt.remove(oldName);
				nbt.put(newName, tag);
			}
		}
	}
}
