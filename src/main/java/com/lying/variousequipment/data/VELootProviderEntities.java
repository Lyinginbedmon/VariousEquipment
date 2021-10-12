package com.lying.variousequipment.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class VELootProviderEntities implements IDataProvider
{
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private final DataGenerator generator;
	private final Map<EntityType<?>, Function<EntityType<?>, LootTable.Builder>> functionTable = new HashMap<>();
	
	public VELootProviderEntities(DataGenerator generator)
	{
		this.generator = generator;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void act(DirectoryCache cache) throws IOException
	{
		Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();
		for (EntityType<?> b : Registry.ENTITY_TYPE)
		{
			ResourceLocation id = Registry.ENTITY_TYPE.getKey(b);
			if (!Reference.ModInfo.MOD_ID.equals(id.getNamespace()))
				continue;
			
			Function<EntityType<?>, LootTable.Builder> func = functionTable.getOrDefault(b, VELootProviderEntities::empty);
			tables.put(id, func.apply(b));
		}

		for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet())
		{
			Path path = getPath(generator.getOutputFolder(), e.getKey());
			IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.ENTITY).build()), path);
		}
	}
	
	protected static Path getPath(Path root, ResourceLocation id)
	{
		return root.resolve("data/" + id.getNamespace() + "/loot_tables/entities/" + id.getPath() + ".json");
	}
	
	protected static LootTable.Builder empty(EntityType<?> b)
	{
		return LootTable.builder();
	}
	
	@SuppressWarnings("unused")
	private static StandaloneLootEntry.Builder<?> itemEntry(Block item)
	{
		return itemEntry(item.asItem());
	}
	
	private static StandaloneLootEntry.Builder<?> itemEntry(Item item)
	{
		return ItemLootEntry.builder(item);
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "Various Equipment entity loot tables";
	}
}