package com.lying.variousequipment.data.recipes;

import java.util.function.Consumer;

import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class VERecipeProvider extends RecipeProvider
{
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
		registerSpecialRecipe(consumer, CostumeRecipe.SERIALIZER);
		registerSpecialRecipe(consumer, RepeatingCrossbowRecipe.SERIALIZER);
		
		for(Block concrete : VEBlocks.CONCRETE_TO_SCREEN.keySet())
		{
			Block screen = VEBlocks.CONCRETE_TO_SCREEN.get(concrete);
			ShapelessRecipeBuilder.shapelessRecipe(screen)
				.addIngredient(concrete).addIngredient(Blocks.GLASS).addIngredient(Items.GLOWSTONE_DUST).setGroup("screen")
				.addCriterion("has_item", hasItem(concrete)).build(consumer);
		}
		
		ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER, 4)
			.addIngredient(VEItems.BRIMSTONE).addIngredient(VEItems.SALTPETER).addIngredient(Items.CHARCOAL).setGroup("gunpowder")
			.addCriterion("has_item", hasItem(VEItems.SALTPETER)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.BLINDFOLD_FAKE)
			.addIngredient(Items.SPECTRAL_ARROW).addIngredient(Items.SPECTRAL_ARROW).addIngredient(VEItems.BLINDFOLD).setGroup("cosmetic_blindfold")
			.addCriterion("has_item", hasItem(VEItems.BLINDFOLD)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.HORNS_KIRIN_STORM)
			.addIngredient(VEItems.HORNS_KIRIN).addIngredient(Items.NETHER_STAR).setGroup("horns_kirin_storm")
			.addCriterion("has_item", hasItem(VEItems.HORNS_KIRIN)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.HORNS_KIRIN)
			.addIngredient(VEItems.HORNS_RAM).addIngredient(VEItems.HORNS_DEER).setGroup("horns_kirin")
			.addCriterion("has_item", hasItems(VEItems.HORNS_DEER, VEItems.HORNS_RAM)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEBlocks.MISSING_BLOCK)
			.addIngredient(VEBlocks.SCREEN_MAGENTA).addIngredient(VEBlocks.SCREEN_BLACK).setGroup("missing_block")
			.addCriterion("has_item", hasItems(VEBlocks.SCREEN_MAGENTA, VEBlocks.SCREEN_BLACK)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.NEEDLE_BONE, 8)
			.addIngredient(Items.BONE).addIngredient(Items.FLINT).setGroup("needle")
			.addCriterion("has_item", hasItem(Items.BONE)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.NOSE_PIGLIN)
			.addIngredient(VEItems.NOSE_PIG).addIngredient(Items.BONE).setGroup("nose_piglin")
			.addCriterion("has_item", hasItem(VEItems.NOSE_PIG)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.NOSE_WITCH)
			.addIngredient(VEItems.NOSE_VILLAGER).addIngredient(Blocks.BROWN_MUSHROOM).setGroup("nose_witch")
			.addCriterion("has_item", hasItem(VEItems.NOSE_VILLAGER)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.TAIL_CAT_2)
			.addIngredient(VEItems.TAIL_CAT).addIngredient(VEItems.TAIL_CAT).setGroup("tail_bakeneko")
			.addCriterion("has_item", hasItems(VEItems.TAIL_CAT)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(VEItems.TAIL_LIZARD2)
			.addIngredient(VEItems.TAIL_LIZARD).addIngredient(Items.QUARTZ).setGroup("tail_lizard_barbed")
			.addCriterion("has_item", hasItem(VEItems.TAIL_LIZARD)).build(consumer);
		registerSwapRecipe(VEItems.EARS_CAT, VEItems.EARS_WOLF, consumer);
		registerSwapRecipe(VEItems.TAIL_CAT, VEItems.TAIL_WOLF, consumer);
	}
	
	protected static void registerSwapRecipe(Item itemA, Item itemB, Consumer<IFinishedRecipe> consumer)
	{
		ShapelessRecipeBuilder.shapelessRecipe(itemA)
			.addIngredient(itemB)
			.addCriterion("has_item", hasItem(itemB)).setGroup(itemA.getRegistryName().getPath()).build(consumer);
		
		ShapelessRecipeBuilder.shapelessRecipe(itemB)
			.addIngredient(itemA)
			.addCriterion("has_item", hasItem(itemA)).setGroup(itemB.getRegistryName().getPath()).build(consumer);
	}
	
	protected static EnterBlockTrigger.Instance enteredBlock(Block block)
	{
		return new EnterBlockTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, block, StatePropertiesPredicate.EMPTY);
	}
	
	protected static InventoryChangeTrigger.Instance hasItems(IItemProvider... items)
	{
		ItemPredicate[] predicates = new ItemPredicate[items.length];
		for(int i=0; i<items.length; i++)
			predicates[i] = ItemPredicate.Builder.create().item(items[i]).build();
		return hasItem(predicates);
	}
	
	protected static InventoryChangeTrigger.Instance hasItem(IItemProvider item)
	{
		return hasItem(ItemPredicate.Builder.create().item(item).build());
	}
	
	protected static InventoryChangeTrigger.Instance hasItem(ITag<Item> tag)
	{
		return hasItem(ItemPredicate.Builder.create().tag(tag).build());
	}
	
	protected static InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicate)
	{
		return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, predicate);
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
}
