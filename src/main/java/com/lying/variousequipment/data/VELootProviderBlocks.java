package com.lying.variousequipment.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lying.variousequipment.block.BlockGuano;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class VELootProviderBlocks implements IDataProvider
{
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create()
			.enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
	
	private final DataGenerator generator;
	private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public VELootProviderBlocks(DataGenerator generator)
	{
		this.generator = generator;
		
		for(Block b : Registry.BLOCK)
		{
			ResourceLocation id = Registry.BLOCK.getKey(b);
			if(!Reference.ModInfo.MOD_ID.equals(id.getNamespace()))
				continue;
			
			if(b instanceof SlabBlock)
				functionTable.put(b, VELootProviderBlocks::genSlab);
			else
				functionTable.put(b, VELootProviderBlocks::genRegular);
		}
		
		// Empty
		functionTable.put(VEBlocks.WAGON_CHASSIS_ACACIA, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.WAGON_CHASSIS_BIRCH, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.WAGON_CHASSIS_CRIMSON, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.WAGON_CHASSIS_DARK_OAK, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.WAGON_CHASSIS_JUNGLE, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.WAGON_CHASSIS_OAK, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.WAGON_CHASSIS_SPRUCE, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.WAGON_CHASSIS_WARPED, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.SPINNY_ARMS, VELootProviderBlocks::empty);
		functionTable.put(VEBlocks.LAVA_STONE, VELootProviderBlocks::empty);
		
		// Special
		functionTable.put(VEBlocks.GUANO, VELootProviderBlocks::genLayers);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void act(DirectoryCache cache) throws IOException
	{
		Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

		for (Block b : Registry.BLOCK) {
			ResourceLocation id = Registry.BLOCK.getKey(b);
			if (!Reference.ModInfo.MOD_ID.equals(id.getNamespace())) {
				continue;
			}
			Function<Block, LootTable.Builder> func = functionTable.getOrDefault(b, VELootProviderBlocks::genRegular);
			tables.put(id, func.apply(b));
		}

		for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
			Path path = getPath(generator.getOutputFolder(), e.getKey());
			IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
		}
	}
	
	protected static Path getPath(Path root, ResourceLocation id) {
		return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
	}
	
	protected static LootTable.Builder empty(Block b)
	{
		return LootTable.builder();
	}
	
	protected static LootTable.Builder genCopyNbt(Block b, String... tags) {
		LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
		CopyNbt.Builder func = CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY);
		for (String tag : tags) {
			func = func.replaceOperation(tag, "BlockEntityTag." + tag);
		}
		LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
				.acceptCondition(SurvivesExplosion.builder())
				.acceptFunction(func);
		return LootTable.builder().addLootPool(pool);
	}
	
	protected static LootTable.Builder genSilkDrop(IItemProvider silkDrop, IItemProvider normalDrop)
	{
		LootEntry.Builder<?> cobbleDrop = ItemLootEntry.builder(normalDrop).acceptCondition(SurvivesExplosion.builder());
		LootEntry.Builder<?> stoneDrop = ItemLootEntry.builder(silkDrop).acceptCondition(SILK_TOUCH);

		return LootTable.builder().addLootPool(
				LootPool.builder().name("main").rolls(ConstantRange.of(1))
						.addEntry(stoneDrop.alternatively(cobbleDrop)));
	}

	protected static LootTable.Builder genSlab(Block b)
	{
		LootEntry.Builder<?> entry = ItemLootEntry.builder(b)
				.acceptFunction(SetCount.builder(ConstantRange.of(2))
						.acceptCondition(BlockStateProperty.builder(b).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE))))
				.acceptFunction(ExplosionDecay.builder());
		return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));
	}

	protected static LootTable.Builder genRegular(Block b)
	{
		LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
		LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
				.acceptCondition(SurvivesExplosion.builder());
		return LootTable.builder().addLootPool(pool);
	}

	protected static LootTable.Builder genLayers(Block b)
	{
		return genLayers(b, b.asItem());
	}
	
	protected static LootTable.Builder genLayers(Block b, Item i)
	{
		LootEntry.Builder<?> entry = AlternativesLootEntry.builder(
    			(itemEntry(i, 1, 1).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(1)))),
    			(itemEntry(i, 1, 2).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(2)))),
    			(itemEntry(i, 1, 3).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(3)))),
    			(itemEntry(i, 1, 4).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(4)))),
    			(itemEntry(i, 1, 5).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(5)))),
    			(itemEntry(i, 1, 6).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(6)))),
    			(itemEntry(i, 1, 7).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(7)))),
    			(itemEntry(i, 1, 8).acceptCondition(dropWhenValInt(b, BlockGuano.LAYERS, Integer.valueOf(8))))
    			);
		LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
				.acceptCondition(SurvivesExplosion.builder());
		return LootTable.builder().addLootPool(pool);
	}
	
	private static StandaloneLootEntry.Builder<?> itemEntry(Item item, int weight, int count)
	{
		return itemEntry(item, weight).acceptFunction(SetCount.builder(ConstantRange.of(count)));
	}
	
	private static StandaloneLootEntry.Builder<?> itemEntry(Item item, int weight)
	{
		return ItemLootEntry.builder(item).weight(weight);
	}
    
    private static <T extends Comparable<T> & IStringSerializable> BlockStateProperty.Builder dropWhenValInt(Block block, IntegerProperty property, Integer value)
    {
    	return BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(property, value));
    }

	@Nonnull
	@Override
	public String getName()
	{
		return "Various Equipment block loot tables";
	}
}