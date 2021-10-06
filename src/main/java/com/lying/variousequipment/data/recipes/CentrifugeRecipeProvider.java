package com.lying.variousequipment.data.recipes;

import java.util.function.Consumer;

import com.google.gson.JsonObject;
import com.lying.variousequipment.data.VELootProvider;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.reference.Reference;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class CentrifugeRecipeProvider extends RecipeProvider
{
	public CentrifugeRecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}
	
	public String getName(){ return Reference.ModInfo.MOD_NAME+" centrifuge recipes"; }
	
	private static final ResourceLocation TABLE_VINES = new ResourceLocation(Reference.ModInfo.MOD_ID, "centrifuge/cobblestone_vines");
	private static final ResourceLocation TABLE_SALT = new ResourceLocation(Reference.ModInfo.MOD_ID, "centrifuge/salt_from_water");
	private static final ResourceLocation TABLE_MILK = new ResourceLocation(Reference.ModInfo.MOD_ID, "centrifuge/fat_from_milk");
	
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
	{
		consumer.accept(new FinishedRecipe(id("gravel"), "", new ResourceLocation("blocks/gravel"), Ingredient.fromTag(Tags.Items.GRAVEL)));
		consumer.accept(new FinishedRecipe(id("melon"), "", new ResourceLocation("blocks/melon"), Ingredient.fromItems(Blocks.MELON)));
		consumer.accept(new FinishedRecipe(id("pumpkin"), "", new ItemStack(Items.PUMPKIN_SEEDS, 4), Ingredient.fromItems(Blocks.PUMPKIN)));
		consumer.accept(new FinishedRecipe(id("hay_block"), "", new ItemStack(Items.WHEAT, 9), Ingredient.fromItems(Blocks.HAY_BLOCK)));
		consumer.accept(new FinishedRecipe(id("slime_block"), "", new ItemStack(Items.SLIME_BALL, 9), Ingredient.fromItems(Blocks.SLIME_BLOCK)));
		consumer.accept(new FinishedRecipe(id("honey_block"), "", new ItemStack(Items.HONEY_BOTTLE, 4), Ingredient.fromItems(Blocks.HONEY_BLOCK)));
		consumer.accept(new FinishedRecipe(id("sandstone"), "", new ItemStack(Blocks.SAND, 4), Ingredient.fromItems(Blocks.SANDSTONE)));
		consumer.accept(new FinishedRecipe(id("red_sandstone"), "", new ItemStack(Blocks.RED_SAND, 4), Ingredient.fromItems(Blocks.RED_SANDSTONE)));
		consumer.accept(new FinishedRecipe(id("iron_block"), "", new ItemStack(Items.IRON_INGOT, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_IRON)));
		consumer.accept(new FinishedRecipe(id("iron_ingot"), "", new ItemStack(Items.IRON_NUGGET, 9), Ingredient.fromTag(Tags.Items.INGOTS_IRON)));
		consumer.accept(new FinishedRecipe(id("gold_block"), "", new ItemStack(Items.GOLD_INGOT, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_GOLD)));
		consumer.accept(new FinishedRecipe(id("gold_ingot"), "", new ItemStack(Items.GOLD_NUGGET, 9), Ingredient.fromTag(Tags.Items.INGOTS_GOLD)));
		consumer.accept(new FinishedRecipe(id("diamond_block"), "", new ItemStack(Items.DIAMOND, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_DIAMOND)));
		consumer.accept(new FinishedRecipe(id("emerald_block"), "", new ItemStack(Items.EMERALD, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_EMERALD)));
		consumer.accept(new FinishedRecipe(id("quartz_block"), "", new ItemStack(Items.QUARTZ, 4), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_QUARTZ)));
		consumer.accept(new FinishedRecipe(id("coal_block"), "", new ItemStack(Items.COAL, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_COAL)));
		consumer.accept(new FinishedRecipe(id("lapis_block"), "", new ItemStack(Items.LAPIS_LAZULI, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_LAPIS)));
		consumer.accept(new FinishedRecipe(id("redstone_block"), "", new ItemStack(Items.REDSTONE, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_REDSTONE)));
		consumer.accept(new FinishedRecipe(id("netherite_block"), "", new ItemStack(Items.NETHERITE_INGOT, 9), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_NETHERITE)));
		consumer.accept(new FinishedRecipe(id("brimstone"), "", new ItemStack(VEItems.BRIMSTONE, 3), Ingredient.fromTag(Tags.Items.NETHERRACK)));
		consumer.accept(new FinishedRecipe(id("cobblestone_vines"), "", TABLE_VINES, Ingredient.fromItems(Blocks.MOSSY_COBBLESTONE)));
		consumer.accept(new FinishedRecipe(id("fat_from_milk"), "", TABLE_MILK, Ingredient.fromItems(Items.MILK_BUCKET)));
		consumer.accept(new FinishedRecipe(id("salt_from_water"), "", TABLE_SALT, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER)));
	}
	
	public static void generateCentrifugeTables(VELootProvider lootProvider)
	{
		lootProvider.addLootTable(TABLE_VINES.getPath(), LootTable.builder().addLootPool(LootPool.builder().name("vines")
                .rolls(RandomValueRange.of(0, 1))
                .addEntry(itemEntry(Blocks.VINE, 1).acceptFunction(SetCount.builder(RandomValueRange.of(1, 2))))).addLootPool(LootPool.builder().name("cobblestone")
                .rolls(ConstantRange.of(1))
                .addEntry(itemEntry(Blocks.COBBLESTONE, 1))
                .addEntry(itemEntry(Blocks.MOSSY_COBBLESTONE, 2))), LootParameterSets.CHEST);
		
		lootProvider.addLootTable(TABLE_SALT.getPath(), LootTable.builder()
				.addLootPool(singleItemPool("bottle", Items.GLASS_BOTTLE))
				.addLootPool(singleItemPool("salt", VEItems.SALT, RandomValueRange.of(1, 2))), LootParameterSets.CHEST);
		
		lootProvider.addLootTable(TABLE_MILK.getPath(), LootTable.builder()
				.addLootPool(singleItemPool("bucket", Items.BUCKET))
				.addLootPool(singleItemPool("fat", VEItems.MILKFAT, RandomValueRange.of(1, 3))), LootParameterSets.CHEST);
	}
	
	private static LootPool.Builder singleItemPool(String nameIn, Item itemIn)
	{
		return LootPool.builder().name(nameIn).rolls(ConstantRange.of(1)).addEntry(itemEntry(itemIn, 1));
	}
	
	private static LootPool.Builder singleItemPool(String nameIn, Item itemIn, IRandomRange countIn)
	{
		return LootPool.builder().name(nameIn).rolls(ConstantRange.of(1)).addEntry(itemEntry(itemIn, 1).acceptFunction(SetCount.builder(countIn)));
	}
	
	private static StandaloneLootEntry.Builder<?> itemEntry(Block item, int weight)
    {
        return itemEntry(item.asItem(), weight);
    }
	
	private static StandaloneLootEntry.Builder<?> itemEntry(Item item, int weight)
    {
        return ItemLootEntry.builder(item).weight(weight);
    }
	
	protected ResourceLocation id(String s){ return new ResourceLocation(Reference.ModInfo.MOD_PREFIX+"centrifuge/" + s); }
	
	public static class FinishedRecipe implements IFinishedRecipe
	{
		private final ResourceLocation id;
		
		private final ItemStack inputStack;
		private final Ingredient inputIngredient;
		
		private final ItemStack outputStack;
		private final ResourceLocation outputTable;
		
		private final String group;
		
		public FinishedRecipe(ResourceLocation idIn, String groupIn, ItemStack outputIn, ItemStack inputIn)
		{
			this.id = idIn;
			this.inputStack = inputIn;
			this.inputIngredient = null;
			this.outputStack = outputIn;
			this.outputTable = null;
			this.group = groupIn;
		}
		
		public FinishedRecipe(ResourceLocation idIn, String groupIn, ResourceLocation outputIn, ItemStack inputIn)
		{
			this.id = idIn;
			this.inputStack = inputIn;
			this.inputIngredient = null;
			this.outputStack = null;
			this.outputTable = outputIn;
			this.group = groupIn;
		}
		
		public FinishedRecipe(ResourceLocation idIn, String groupIn, ItemStack outputIn, Ingredient inputIn)
		{
			this.id = idIn;
			this.inputStack = null;
			this.inputIngredient = inputIn;
			this.outputStack = outputIn;
			this.outputTable = null;
			this.group = groupIn;
		}
		
		public FinishedRecipe(ResourceLocation idIn, String groupIn, ResourceLocation outputIn, Ingredient inputIn)
		{
			this.id = idIn;
			this.inputStack = null;
			this.inputIngredient = inputIn;
			this.outputStack = null;
			this.outputTable = outputIn;
			this.group = groupIn;
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
			
			JsonObject output = new JsonObject();
			if(outputTable != null)
				output.addProperty("table", outputTable.toString());
			else if(output != null)
				output = serializeStack(outputStack);
			json.add("output", output);
			
			if(!group.isEmpty())
				json.addProperty("group", group);
		}
		
		public ResourceLocation getID(){ return id; }
		
		public IRecipeSerializer<?> getSerializer()
		{
			return VERecipeTypes.CENTRIFUGE_SERIALIZER;
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
