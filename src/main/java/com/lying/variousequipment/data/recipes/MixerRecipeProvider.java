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
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.ResourceLocation;

public class MixerRecipeProvider extends RecipeProvider
{
	public MixerRecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}
	
	public String getName(){ return Reference.ModInfo.MOD_NAME+" mixer recipes"; }
	
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
	{
		for(Vial.Builder builder : Vial.ALL_VIALS)
		{
			Vial vial = builder.create();
			String prefix = vial.getRegistryName().getPath();
			for(int damage=0; damage<3; damage++)
			{
				ItemStack input = new ItemStack(VEItems.VIAL_SOLUTION);
				input.setDamage(damage + 1);
				ItemVial.addVialToItemStack(input, vial);
				
				ItemStack output = new ItemStack(damage == 0 ? (vial.isThrowable() ? VEItems.VIAL_THROWABLE : VEItems.VIAL_DRINKABLE) : VEItems.VIAL_SOLUTION);
				output.setDamage(damage);
				ItemVial.addVialToItemStack(output, vial);
				
				consumer.accept(new FinishedRecipe(id(prefix+"_"+damage), prefix, output, Ingredient.fromStacks(input)));
			}
		}
	}
	
	protected ResourceLocation id(String s){ return new ResourceLocation(Reference.ModInfo.MOD_PREFIX+"mixer/" + s); }
	
	public static class FinishedRecipe implements IFinishedRecipe
	{
		private final ResourceLocation id;
		private final Ingredient input;
		private final ItemStack output;
		private final String group;
		
		public FinishedRecipe(ResourceLocation idIn, String groupIn, ItemStack outputIn, Ingredient inputIn)
		{
			this.id = idIn;
			this.input = inputIn;
			this.output = outputIn;
			this.group = groupIn;
		}
		
		public void serialize(JsonObject json)
		{
			json.add("input", input.serialize());
			json.add("output", serializeStack(output));
			if(!group.isEmpty())
				json.addProperty("group", group);
		}
		
		public ResourceLocation getID(){ return id; }
		
		public IRecipeSerializer<?> getSerializer()
		{
			return VERecipeTypes.MIXER_SERIALIZER;
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
