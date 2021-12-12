package com.lying.variousequipment.data.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.lying.variousequipment.data.VEItemTags;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.bauble.ItemWheelchair;
import com.lying.variousequipment.reference.Reference;
import com.mojang.datafixers.util.Pair;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

public class VERecipeProvider extends RecipeProvider
{
	private static final Map<Item, Block> WHEEL_TO_MATS = new HashMap<>();
	private static final Map<Pair<INamedTag<Item>, String>, Pair<Item, Item>> MATS_TO_WEAPONS = new HashMap<>();
	
	public VERecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
	{
		registerSpecialRecipe(consumer, CoatItemRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, HolyWaterRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, KitsuneTailRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, AntennaRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, PegLegRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, WheelchairRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, CostumeRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, RepeatingCrossbowRecipe.SERIALIZER);
		
		// Screens
		for(Block concrete : VEBlocks.CONCRETE_TO_SCREEN.keySet())
		{
			Block screen = VEBlocks.CONCRETE_TO_SCREEN.get(concrete);
			ShapelessRecipeBuilder.shapelessRecipe(screen)
				.addIngredient(concrete).addIngredient(Blocks.GLASS).addIngredient(Items.GLOWSTONE_DUST).setGroup("screens")
				.addCriterion("has_"+concrete.getRegistryName().getPath(), hasItem(concrete)).build(consumer);
		}
		
		ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER, 4)
			.addIngredient(VEItems.BRIMSTONE).addIngredient(VEItems.SALTPETER).addIngredient(Items.CHARCOAL)
			.addCriterion("has_saltpeter", hasItem(VEItems.SALTPETER)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.BLINDFOLD_FAKE)
			.addIngredient(Items.SPECTRAL_ARROW).addIngredient(Items.SPECTRAL_ARROW).addIngredient(VEItems.BLINDFOLD)
			.addCriterion("has_blindfold", hasItem(VEItems.BLINDFOLD)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.HORNS_KIRIN_STORM)
			.addIngredient(VEItems.HORNS_KIRIN).addIngredient(Items.NETHER_STAR)
			.addCriterion("has_horns_kirin", hasItem(VEItems.HORNS_KIRIN)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.HORNS_KIRIN)
			.addIngredient(VEItems.HORNS_RAM).addIngredient(VEItems.HORNS_DEER)
			.addCriterion("has_horns", hasItems(VEItems.HORNS_DEER, VEItems.HORNS_RAM)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEBlocks.MISSING_BLOCK)
			.addIngredient(VEBlocks.SCREEN_MAGENTA).addIngredient(VEBlocks.SCREEN_BLACK)
			.addCriterion("has_screens", hasItems(VEBlocks.SCREEN_MAGENTA, VEBlocks.SCREEN_BLACK)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.NEEDLE_BONE, 8)
			.addIngredient(Items.BONE).setGroup("needles").addIngredient(Items.FLINT)
			.addCriterion("has_bone", hasItem(Tags.Items.BONES)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.NOSE_PIGLIN)
			.addIngredient(VEItems.NOSE_PIG).addIngredient(Items.BONE)
			.addCriterion("has_snout", hasItem(VEItems.NOSE_PIG)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.NOSE_WITCH)
			.addIngredient(VEItems.NOSE_VILLAGER).addIngredient(Blocks.BROWN_MUSHROOM)
			.addCriterion("has_nose", hasItem(VEItems.NOSE_VILLAGER)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.TAIL_CAT_2)
			.addIngredient(VEItems.TAIL_CAT).addIngredient(VEItems.TAIL_CAT)
			.addCriterion("has_tail_cat", hasItem(VEItems.TAIL_CAT)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.TAIL_LIZARD2)
			.addIngredient(VEItems.TAIL_LIZARD).addIngredient(Items.QUARTZ)
			.addCriterion("has_tail_lizard", hasItem(VEItems.TAIL_LIZARD)).build(consumer);
		registerSwapRecipe(VEItems.EARS_CAT, VEItems.EARS_WOLF, "has_ears_cat", "has_ears_wolf", consumer);
		registerSwapRecipe(VEItems.TAIL_CAT, VEItems.TAIL_WOLF, "has_tail_cat", "has_tail_wolf", consumer);
		ShapelessRecipeBuilder.shapelessRecipe(Items.WHEAT, 7)
			.addIngredient(VEItems.BURNT_HAY)
			.addCriterion("has_burnt_hay", hasItem(VEItems.BURNT_HAY)).build(consumer);
		
		ShapedRecipeBuilder.shapedRecipe(VEItems.ALEMBIC)
			.patternLine("Gi ")
			.patternLine("C W")
			.patternLine("   ")
			.key('G', Items.POTION)
			.key('C', Items.COAL)
			.key('i', Tags.Items.INGOTS_IRON)
			.key('W', Items.GLASS_BOTTLE)
			.addCriterion("has_glass_bottle", hasItem(Items.GLASS_BOTTLE)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.BLINDFOLD)
			.patternLine(" S ")
			.patternLine("L L")
			.patternLine(" L ")
			.key('L', VEItemTags.CARPETS)
			.key('S', Tags.Items.STRING)
			.addCriterion("has_string", hasItem(Items.STRING)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.BLOWPIPE)
			.patternLine(" B")
			.patternLine("B ")
			.key('B', Items.BAMBOO)
			.addCriterion("has_bamboo", hasItem(Items.BAMBOO)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.CALTROP, 2)
			.patternLine("N N")
			.patternLine(" N ")
			.patternLine("N N")
			.key('N', Tags.Items.NUGGETS_IRON)
			.addCriterion("has_iron_nugget", hasItem(Tags.Items.NUGGETS_IRON)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.CENTRIFUGE)
			.patternLine(" S ")
			.patternLine("BSB")
			.patternLine("III")
			.key('S', Tags.Items.RODS_WOODEN)
			.key('B', Items.GLASS_BOTTLE)
			.key('I', Tags.Items.INGOTS_GOLD)
			.addCriterion("has_glass_bottle", hasItem(Items.GLASS_BOTTLE)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.CROWBAR)
			.patternLine("iI ")
			.patternLine(" L ")
			.patternLine(" Ii")
			.key('I', Tags.Items.INGOTS_IRON)
			.key('i', Tags.Items.NUGGETS_IRON)
			.key('L', Tags.Items.LEATHER)
			.addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.HAT_HOOD)
			.patternLine(" L ")
			.patternLine("L L")
			.patternLine("L L")
			.key('L', Tags.Items.LEATHER)
			.addCriterion("has_leather", hasItem(Tags.Items.LEATHER)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.HAT_WITCH)
			.patternLine("L  ")
			.patternLine(" L ")
			.patternLine("LLL")
			.key('L', Tags.Items.LEATHER)
			.addCriterion("has_leather", hasItem(Tags.Items.LEATHER)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.MIXER)
			.patternLine(" S ")
			.patternLine("BSB")
			.patternLine("III")
			.key('S', Tags.Items.RODS_WOODEN)
			.key('B', Items.GLASS_BOTTLE)
			.key('I', Tags.Items.INGOTS_IRON)
			.addCriterion("has_glass_bottle", hasItem(Items.GLASS_BOTTLE)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.NEEDLE_IRON, 3).setGroup("needles")
			.patternLine(" B")
			.patternLine("B ")
			.key('B', Tags.Items.NUGGETS_IRON)
			.addCriterion("has_iron_nugget", hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.NOTEPAD)
			.patternLine("I F")
			.patternLine("PPP")
			.patternLine(" S ")
			.key('I', Tags.Items.DYES_BLACK)
			.key('F', Tags.Items.FEATHERS)
			.key('P', Items.PAPER)
			.key('S', ItemTags.WOODEN_SLABS)
			.addCriterion("has_paper", hasItem(Items.PAPER)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.REPEATING_CROSSBOW)
			.patternLine("III")
			.patternLine("PCP")
			.patternLine(" H ")
			.key('I', Tags.Items.INGOTS_IRON)
			.key('P', Items.PISTON)
			.key('C', Items.CROSSBOW)
			.key('H', Items.TRIPWIRE_HOOK)
			.addCriterion("has_crossbow", hasItem(Items.CROSSBOW)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.SCARECROW)
			.patternLine(" H ")
			.patternLine("sHs")
			.patternLine(" s ")
			.key('H', Items.HAY_BLOCK)
			.key('s', Tags.Items.RODS_WOODEN)
			.addCriterion("has_hay_block", hasItem(Items.HAY_BLOCK)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.SHORTBOW)
			.patternLine("NN")
			.patternLine("NS")
			.key('N', Tags.Items.RODS_WOODEN)
			.key('S', Tags.Items.STRING)
			.addCriterion("has_string", hasItem(Tags.Items.STRING)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.LEGS_PEG_WOODEN)
			.patternLine("S")
			.patternLine("R")
			.key('S', ItemTags.WOODEN_SLABS)
			.key('R', Tags.Items.RODS_WOODEN)
			.addCriterion("has_stick", hasItem(Tags.Items.RODS_WOODEN)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.LEGS_PEG_IRON)
			.patternLine("S")
			.patternLine("R")
			.key('S', Tags.Items.INGOTS_IRON)
			.key('R', Tags.Items.RODS_WOODEN)
			.addCriterion("has_stick", hasItem(Tags.Items.RODS_WOODEN)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.LEGS_PEG_GOLDEN)
			.patternLine("S")
			.patternLine("R")
			.key('S', Tags.Items.INGOTS_GOLD)
			.key('R', Tags.Items.RODS_WOODEN)
			.addCriterion("has_stick", hasItem(Tags.Items.RODS_WOODEN)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.LEGS_PEG_NETHERITE)
			.patternLine("S")
			.patternLine("R")
			.key('S', Tags.Items.INGOTS_NETHERITE)
			.key('R', Tags.Items.RODS_WOODEN)
			.addCriterion("has_stick", hasItem(Tags.Items.RODS_WOODEN)).build(consumer);
		
		// Glaives & Daggers
		for(Pair<INamedTag<Item>, String> material : MATS_TO_WEAPONS.keySet())
		{
			Pair<Item, Item> weapons = MATS_TO_WEAPONS.get(material);
			Item glaiveItem = weapons.getFirst();
			Item daggerItem = weapons.getSecond();
			if(glaiveItem != null)
				ShapedRecipeBuilder.shapedRecipe(glaiveItem).setGroup("glaives")
				.patternLine("  X").patternLine(" s ").patternLine("s  ")
				.key('s', Tags.Items.RODS_WOODEN)
				.key('X', material.getFirst())
				.addCriterion(material.getSecond(), hasItem(material.getFirst())).build(consumer);
			
			if(daggerItem != null)
				ShapedRecipeBuilder.shapedRecipe(daggerItem).setGroup("daggers")
					.patternLine("X").patternLine("s")
					.key('s', Tags.Items.RODS_WOODEN)
					.key('X', material.getFirst())
					.addCriterion(material.getSecond(), hasItem(material.getFirst())).build(consumer);
		}
		SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(VEItems.DIAMOND_GLAIVE), Ingredient.fromItems(Items.NETHERITE_INGOT), VEItems.NETHERITE_GLAIVE)
			.addCriterion("has_netherite_ingot", hasItem(Tags.Items.INGOTS_NETHERITE)).build(consumer, "netherite_glaive__smithing");
		SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(VEItems.DIAMOND_DAGGER), Ingredient.fromItems(Items.NETHERITE_INGOT), VEItems.NETHERITE_DAGGER)
			.addCriterion("has_netherite_ingot", hasItem(Items.NETHERITE_INGOT)).build(consumer, "netherite_dagger_smithing");
		
		// Holy Symbols
		ShapedRecipeBuilder.shapedRecipe(VEItems.SYMBOL_DIAMOND).setGroup("holy_symbols")
			.patternLine(" D ").patternLine(" L ").patternLine("D  ")
			.key('D', Tags.Items.GEMS_DIAMOND)
			.key('L', Tags.Items.LEATHER)
			.addCriterion("has_diamond", hasItem(Tags.Items.GEMS_DIAMOND)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.SYMBOL_GOLD).setGroup("holy_symbols")
			.patternLine("GGG").patternLine(" L ").patternLine(" G ")
			.key('G', Tags.Items.INGOTS_GOLD)
			.key('L', Tags.Items.LEATHER)
			.addCriterion("has_gold_ingot", hasItem(Tags.Items.INGOTS_GOLD)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.SYMBOL_IRON).setGroup("holy_symbols")
			.patternLine("III").patternLine(" L ").patternLine("s  ")
			.key('L', Tags.Items.LEATHER)
			.key('s', Tags.Items.RODS_WOODEN)
			.key('I', Tags.Items.INGOTS_IRON)
			.addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.SYMBOL_STONE).setGroup("holy_symbols")
			.patternLine("XX ").patternLine(" XX").patternLine("  X")
			.key('X', Tags.Items.COBBLESTONE)
			.addCriterion("has_cobblestone", hasItem(Tags.Items.COBBLESTONE)).build(consumer);
		ShapedRecipeBuilder.shapedRecipe(VEItems.SYMBOL_WOOD).setGroup("holy_symbols")
			.patternLine("XX").patternLine(" s")
			.key('s', Tags.Items.RODS_WOODEN)
			.key('X', ItemTags.PLANKS)
			.addCriterion("has_planks", hasItem(ItemTags.PLANKS)).build(consumer);
		
		// Wheels
		for(Item wheel : WHEEL_TO_MATS.keySet())
		{
			Block slab = WHEEL_TO_MATS.get(wheel);
			ShapedRecipeBuilder.shapedRecipe(wheel).setGroup("wheels")
				.patternLine(" S ")
				.patternLine("SsS")
				.patternLine(" S ")
				.key('S', slab)
				.key('s', Tags.Items.RODS_WOODEN)
				.addCriterion("has_"+slab.getRegistryName().getPath(), hasItem(slab)).build(consumer);
		}
		
		// Wheelchairs (non-custom recipe)
		for(Item chair : ItemWheelchair.WHEELCHAIRS)
		{
			Item wheel = ItemWheelchair.getDefaultWheelFromItem(new ItemStack(chair));
			Block slab = WHEEL_TO_MATS.get(wheel);
			ShapedRecipeBuilder.shapedRecipe(chair).setGroup("wheelchairs")
				.patternLine(" W ")
				.patternLine("EsE")
				.key('W', Items.RED_WOOL)
				.key('E', wheel)
				.key('s', slab)
				.addCriterion("has_"+wheel.getRegistryName().getPath(), hasItem(wheel)).build(consumer);
		}
	}
	
	protected static void registerSwapRecipe(Item itemA, Item itemB, String criterionA, String criterionB, Consumer<IFinishedRecipe> consumer)
	{
		ShapelessRecipeBuilder.shapelessRecipe(itemA)
			.addIngredient(itemB)
			.addCriterion(criterionA, hasItem(itemB)).build(consumer);
		
		ShapelessRecipeBuilder.shapelessRecipe(itemB)
			.addIngredient(itemA)
			.addCriterion(criterionB, hasItem(itemA)).build(consumer);
	}
	
	protected static InventoryChangeTrigger.Instance hasItems(IItemProvider... items)
	{
		ItemPredicate[] predicates = new ItemPredicate[items.length];
		for(int i=0; i<items.length; i++)
			predicates[i] = ItemPredicate.Builder.create().item(items[i]).build();
		return hasItem(predicates);
	}
	
	@SuppressWarnings("deprecation")
	private void registerSpecialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer)
	{
		ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);
		CustomRecipeBuilder.customRecipe(serializer).build(consumer, new ResourceLocation(Reference.ModInfo.MOD_ID,"dynamic/"+name.getPath()).toString());
	}
	
	@Override
	public String getName()
	{
		return "Various Equipment crafting recipes";
	}
	
	static
	{
		WHEEL_TO_MATS.put(VEItems.WHEEL_ACACIA, Blocks.ACACIA_SLAB);
		WHEEL_TO_MATS.put(VEItems.WHEEL_BIRCH, Blocks.BIRCH_SLAB);
		WHEEL_TO_MATS.put(VEItems.WHEEL_CRIMSON, Blocks.CRIMSON_SLAB);
		WHEEL_TO_MATS.put(VEItems.WHEEL_DARK_OAK, Blocks.DARK_OAK_SLAB);
		WHEEL_TO_MATS.put(VEItems.WHEEL_JUNGLE, Blocks.JUNGLE_SLAB);
		WHEEL_TO_MATS.put(VEItems.WHEEL_OAK, Blocks.OAK_SLAB);
		WHEEL_TO_MATS.put(VEItems.WHEEL_SPRUCE, Blocks.SPRUCE_SLAB);
		WHEEL_TO_MATS.put(VEItems.WHEEL_WARPED, Blocks.WARPED_SLAB);
		
		MATS_TO_WEAPONS.put(Pair.of(Tags.Items.GEMS_DIAMOND, "has_diamond"), Pair.of(VEItems.DIAMOND_GLAIVE, VEItems.DIAMOND_DAGGER));
		MATS_TO_WEAPONS.put(Pair.of(Tags.Items.INGOTS_GOLD, "has_gold_ingot"), Pair.of(VEItems.GOLDEN_GLAIVE, VEItems.GOLDEN_DAGGER));
		MATS_TO_WEAPONS.put(Pair.of(Tags.Items.INGOTS_IRON, "has_iron_ingot"), Pair.of(VEItems.IRON_GLAIVE, VEItems.IRON_DAGGER));
		MATS_TO_WEAPONS.put(Pair.of(Tags.Items.COBBLESTONE, "has_cobblestone"), Pair.of(VEItems.STONE_GLAIVE, VEItems.STONE_DAGGER));
		MATS_TO_WEAPONS.put(Pair.of(ItemTags.PLANKS, "has_planks"), Pair.of(VEItems.WOODEN_GLAIVE, VEItems.WOODEN_DAGGER));
	}
}
