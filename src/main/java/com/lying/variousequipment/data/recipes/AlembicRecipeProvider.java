package com.lying.variousequipment.data.recipes;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.reference.Reference;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class AlembicRecipeProvider extends RecipeProvider
{
	public AlembicRecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}
	
	public String getName(){ return Reference.ModInfo.MOD_NAME+" alembic recipes"; }
	
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
	{
		consumer.accept(new FinishedRecipe(id("saltpeter"), "", new ItemStack(VEItems.SALTPETER), Ingredient.fromItems(VEBlocks.GUANO)));
		consumer.accept(new FinishedRecipe(id("darkvision_powder"), "", new ItemStack(VEItems.NIGHT_POWDER), Ingredient.fromItems(Items.ENDER_EYE), Ingredient.fromItems(VEItems.SALT), Ingredient.fromItems(Items.GLOWSTONE_DUST)));
	}
	
	protected ResourceLocation id(String s){ return new ResourceLocation(Reference.ModInfo.MOD_PREFIX+"alembic/" + s); }
	
	public static class FinishedRecipe implements IFinishedRecipe
	{
		private final ResourceLocation id;
		
		private final NonNullList<Ingredient> ingredients = NonNullList.withSize(3, Ingredient.EMPTY);
		private final Ingredient outputSlotIng;
		private final ItemStack outputSlotStack;
		
		private final ItemStack output;
		private final String group;
		
		private FinishedRecipe(ResourceLocation idIn, @Nullable String groupIn, ItemStack outStaIn, Ingredient outIngIn, ItemStack resultIn, Ingredient... ingredientsIn)
		{
			this.id = idIn;
			
			this.outputSlotStack = outStaIn;
			this.outputSlotIng = outIngIn;
			
			for(int i=0; i<Math.min(ingredients.size(), ingredientsIn.length); i++)
				ingredients.set(i, ingredientsIn[i]);
			
			this.group = groupIn;
			this.output = resultIn;
		}
		
		public FinishedRecipe(ResourceLocation idIn, @Nullable String groupIn, ItemStack outStaIn, ItemStack resultIn, Ingredient... ingredientsIn)
		{
			this(idIn, groupIn, outStaIn, null, resultIn, ingredientsIn);
		}
		
		public FinishedRecipe(ResourceLocation idIn, @Nullable String groupIn, Ingredient outIngIn, ItemStack resultIn, Ingredient... ingredientsIn)
		{
			this(idIn, groupIn, ItemStack.EMPTY, outIngIn, resultIn, ingredientsIn);
		}
		
		public FinishedRecipe(ResourceLocation idIn, @Nullable String groupIn, ItemStack resultIn, Ingredient... ingredientsIn)
		{
			this(idIn, groupIn, ItemStack.EMPTY, null, resultIn, ingredientsIn);
		}
		
		public void serialize(JsonObject json)
		{
			JsonObject input = new JsonObject();
			
			JsonObject collection = new JsonObject();
			if(outputSlotIng != null)
			{
				collection.add("ingredient", outputSlotIng.serialize());
				input.add("collection", collection);
			}
			else if(!outputSlotStack.isEmpty())
			{
				collection = serializeStack(outputSlotStack);
				input.add("collection", collection);
			}
			
			JsonArray ingredientArray = new JsonArray();
			for(Ingredient ingredient : ingredients)
				if(ingredient != Ingredient.EMPTY)
					ingredientArray.add(ingredient.serialize());
			if(ingredientArray.size() > 0)
				input.add("ingredients", ingredientArray);
			
			json.add("input", input);
			
			json.add("output", serializeStack(output));
			if(!group.isEmpty())
				json.addProperty("group", group);
		}
		
		public ResourceLocation getID(){ return id; }
		
		public IRecipeSerializer<?> getSerializer()
		{
			return VERecipeTypes.ALEMBIC_SERIALIZER;
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
