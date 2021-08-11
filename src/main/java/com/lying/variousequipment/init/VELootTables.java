package com.lying.variousequipment.init;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.VariousEquipment;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;

public class VELootTables
{
	public static final List<String> INJECTED_LOOT_TABLES = Lists.newArrayList();
	
	public static final ResourceLocation COSMETICS			= new ResourceLocation(Reference.ModInfo.MOD_ID, "cosmetics");
	public static final ResourceLocation MAGIC_ITEM_MINOR	= new ResourceLocation(Reference.ModInfo.MOD_ID, "magic_item_minor");
	public static final ResourceLocation MAGIC_ITEM_MEDIUM	= new ResourceLocation(Reference.ModInfo.MOD_ID, "magic_item_medium");
	public static final ResourceLocation MAGIC_ITEM_MAJOR	= new ResourceLocation(Reference.ModInfo.MOD_ID, "magic_item_major");
	
	public static void onLootLoadEvent(LootTableLoadEvent event)
	{
		if(event.getName().getNamespace().equals("minecraft"))
		{
			String name = event.getName().toString();
			String file = event.getName().getPath();
			if(INJECTED_LOOT_TABLES.contains(file))
			{
				VariousEquipment.log.info("Injecting loot pool to loot table "+name);
				event.getTable().addPool(getInjectPool(file));
			}
		}
	}
	
	private static LootPool getInjectPool(String entryName)
	{
		return LootPool.builder()
				.addEntry(getInjectEntry(entryName, 100))
				.bonusRolls(0, 1)
				.name(Reference.ModInfo.MOD_ID + "_inject")
				.build();
	}
	
	private static LootEntry.Builder<?> getInjectEntry(String name, int weight)
	{
		ResourceLocation table = new ResourceLocation(Reference.ModInfo.MOD_ID, "inject/" + name);
		return TableLootEntry.builder(table)
				.weight(weight);
	}
	
	static
	{
		INJECTED_LOOT_TABLES.add("village/village_weaponsmith");
		INJECTED_LOOT_TABLES.add("chests/simple_dungeon");
		INJECTED_LOOT_TABLES.add("chests/abandoned_mineshaft");
		INJECTED_LOOT_TABLES.add("chests/bastion_hoglin_stable");
		INJECTED_LOOT_TABLES.add("chests/bastion_treasure");
		INJECTED_LOOT_TABLES.add("chests/buried_treasure");
		INJECTED_LOOT_TABLES.add("chests/desert_pyramid");
		INJECTED_LOOT_TABLES.add("chests/end_city_treasure");
		INJECTED_LOOT_TABLES.add("chests/jungle_temple");
		INJECTED_LOOT_TABLES.add("chests/nether_bridge");
		INJECTED_LOOT_TABLES.add("chests/pillager_outpost");
		INJECTED_LOOT_TABLES.add("chests/ruined_portal");
		INJECTED_LOOT_TABLES.add("chests/shipwreck_treasure");
		INJECTED_LOOT_TABLES.add("chests/stronghold_corridor");
		INJECTED_LOOT_TABLES.add("chests/underwater_ruin_big");
		INJECTED_LOOT_TABLES.add("chests/chests/woodland_mansion");
		INJECTED_LOOT_TABLES.add("entities/pig");
		INJECTED_LOOT_TABLES.add("entities/piglin");
		INJECTED_LOOT_TABLES.add("entities/piglin_brute");
		INJECTED_LOOT_TABLES.add("entities/zombified_piglin");
		INJECTED_LOOT_TABLES.add("entities/wolf");
		INJECTED_LOOT_TABLES.add("entities/cat");
		INJECTED_LOOT_TABLES.add("entities/ocelot");
		INJECTED_LOOT_TABLES.add("entities/witch");
		INJECTED_LOOT_TABLES.add("entities/villager");
		INJECTED_LOOT_TABLES.add("entities/illusioner");
		INJECTED_LOOT_TABLES.add("entities/evoker");
		INJECTED_LOOT_TABLES.add("entities/vindicator");
		INJECTED_LOOT_TABLES.add("entities/wandering_trader");
		INJECTED_LOOT_TABLES.add("entities/rabbit");
		INJECTED_LOOT_TABLES.add("entities/fox");
		INJECTED_LOOT_TABLES.add("entities/ender_dragon");
		INJECTED_LOOT_TABLES.add("entities/horse");
		INJECTED_LOOT_TABLES.add("entities/sheep");
		INJECTED_LOOT_TABLES.add("gameplay/fishing/treasure");
		
		INJECTED_LOOT_TABLES.add("entities/kobold");
	}
}