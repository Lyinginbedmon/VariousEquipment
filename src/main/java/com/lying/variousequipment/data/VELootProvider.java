package com.lying.variousequipment.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.init.VELootTables;
import com.lying.variousequipment.reference.Reference;
import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.common.data.ExistingFileHelper;

public class VELootProvider extends LootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();
    private final ExistingFileHelper existingFileHelper;
    
	public VELootProvider(DataGenerator dataGeneratorIn, ExistingFileHelper existingFileHelperIn)
	{
		super(dataGeneratorIn);
		this.existingFileHelper = existingFileHelperIn;
	}
	
	@Override
	public String getName()
	{
		return "Various Equipment loot tables";
	}
	
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        tables.clear();
        addMagicItemTables();
        addEntityLootTables();
        addChestLootTables();
        return tables;
    }
    
    private void addMagicItemTables()
    {
        addChestLootTable(VELootTables.MAGIC_ITEM_MINOR.getPath(), LootTable.builder().addLootPool(LootPool.builder().name("main")
                .rolls(ConstantRange.of(1))
                    .addEntry(itemEntry(VEItems.SCARAB_GOLEM, 3))
                    .addEntry(itemEntry(VEItems.FOOD_SPOON, 5))
                    .addEntry(itemEntry(VEItems.COATING_SILVER, 8))
                    .addEntry(itemEntry(VEItems.SALVE_STONE, 12).acceptFunction(SetCount.builder(RandomValueRange.of(1F, 4F))))
                )
        );
        
        addChestLootTable(VELootTables.MAGIC_ITEM_MEDIUM.getPath(), LootTable.builder().addLootPool(LootPool.builder().name("main")
                .rolls(ConstantRange.of(1))
                    .addEntry(itemEntry(VEItems.STONE_LUCK, 2))
                )
        );
        
        addChestLootTable(VELootTables.MAGIC_ITEM_MAJOR.getPath(), LootTable.builder().addLootPool(LootPool.builder().name("main")
                .rolls(ConstantRange.of(1))
                )
        );
        
        addChestLootTable(VELootTables.COSMETICS.getPath(), LootTable.builder().addLootPool(LootPool.builder().name("main")
        		.rolls(ConstantRange.of(1))
        			.addEntry(itemEntry(VEItems.EARS_WOLF, 5))
        			.addEntry(itemEntry(VEItems.EARS_RABBIT, 5))
        			.addEntry(itemEntry(VEItems.ANTENNA, 5))
        			.addEntry(itemEntry(VEItems.GILLS_AXOLOTL, 5))
        			.addEntry(itemEntry(VEItems.HORNS_DEER, 5))
        			.addEntry(itemEntry(VEItems.HORNS_HARTEBEEST, 5))
        			.addEntry(itemEntry(VEItems.HORNS_RAM, 5))
        			.addEntry(itemEntry(VEItems.TAIL_WOLF, 5))
        			.addEntry(itemEntry(VEItems.TAIL_FOX, 3))
        			.addEntry(itemEntry(VEItems.TAIL_KIRIN, 1))
        			.addEntry(itemEntry(VEItems.TAIL_KOBOLD, 5))
        			.addEntry(itemEntry(VEItems.TAIL_RAT, 5))
        			.addEntry(itemEntry(VEItems.TAIL_DRAGON, 1))
        			.addEntry(itemEntry(VEItems.TAIL_DEVIL, 3))
        			.addEntry(itemEntry(VEItems.TAIL_RABBIT, 5))
        			.addEntry(itemEntry(VEItems.TAIL_HORSE, 5))
        			.addEntry(itemEntry(VEItems.TAIL_DRAGONFLY, 5))
        			.addEntry(itemEntry(VEItems.TAIL_ANT, 5))
        			.addEntry(itemEntry(VEItems.TAIL_LIZARD, 5))
        			.addEntry(itemEntry(VEItems.EARS_PIGLIN, 5))
        			.addEntry(itemEntry(VEItems.NOSE_PIG, 5))
        			.addEntry(itemEntry(VEItems.NOSE_VILLAGER, 5))));
    }
    
    private void addEntityLootTables()
    {
    	addEntityLootTable("inject/entities/pig", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
						.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_PIG, 1))));
    	addEntityLootTable("inject/entities/piglin", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_PIGLIN, 1))
    					.addEntry(itemEntry(VEItems.EARS_PIGLIN, 1))));
    	addEntityLootTable("inject/entities/piglin_brute", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_PIGLIN, 1))
    					.addEntry(itemEntry(VEItems.EARS_PIGLIN, 1))));
    	addEntityLootTable("inject/entities/zombified_piglin", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_PIGLIN, 1))
    					.addEntry(itemEntry(VEItems.EARS_PIGLIN, 1))));
    	addEntityLootTable("inject/entities/wolf", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
						.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.EARS_WOLF, 1))
    					.addEntry(itemEntry(VEItems.TAIL_WOLF, 1))));
    	addEntityLootTable("inject/entities/cat", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
						.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.EARS_CAT, 1))
    					.addEntry(itemEntry(VEItems.TAIL_CAT, 1))));
    	addEntityLootTable("inject/entities/ocelot", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
						.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.EARS_CAT, 1))
    					.addEntry(itemEntry(VEItems.TAIL_CAT, 1))));
    	addEntityLootTable("inject/entities/witch", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_WITCH, 1))));
    	addEntityLootTable("inject/entities/villager", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_VILLAGER, 1))));
    	addEntityLootTable("inject/entities/illusioner", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_VILLAGER, 1).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), (nbt) -> {
    						CompoundNBT display = new CompoundNBT();
    						display.putInt("color", 6844014);
    						nbt.put("display", display);
    				    }))))));
    	addEntityLootTable("inject/entities/evoker", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_VILLAGER, 1).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), (nbt) -> {
    						CompoundNBT display = new CompoundNBT();
    						display.putInt("color", 6844014);
    						nbt.put("display", display);
    				    }))))));
    	addEntityLootTable("inject/entities/vindicator", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_VILLAGER, 1).acceptFunction(SetNBT.builder(Util.make(new CompoundNBT(), (nbt) -> {
    						CompoundNBT display = new CompoundNBT();
    						display.putInt("color", 6844014);
    						nbt.put("display", display);
    				    }))))));
    	addEntityLootTable("inject/entities/wandering_trader", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.NOSE_VILLAGER, 1))));
    	addEntityLootTable("inject/entities/rabbit", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.EARS_RABBIT, 1))
    					.addEntry(itemEntry(VEItems.TAIL_RABBIT, 1))));
    	addEntityLootTable("inject/entities/fox", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.TAIL_FOX, 1))));
    	addEntityLootTable("inject/entities/ender_dragon", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.TAIL_DRAGON, 1))));
    	addEntityLootTable("inject/entities/horse", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.TAIL_HORSE, 5))
    					.addEntry(itemEntry(VEItems.HORNS_DEER, 1))
    					.addEntry(itemEntry(VEItems.HORNS_HARTEBEEST, 1))));
    	addEntityLootTable("inject/entities/sheep", LootTable.builder().addLootPool(
    			LootPool.builder()
    					.name("main")
    					.rolls(ConstantRange.of(1))
    					.acceptCondition(RandomChance.builder(0.05F))
    					.acceptCondition(KilledByPlayer.builder())
    					.addEntry(itemEntry(VEItems.HORNS_RAM, 1))));
    	
//    	addEntityLootTable("inject/entities/kobold", LootTable.builder().addLootPool(
//    			LootPool.builder()
//    					.name("main")
//    					.rolls(ConstantRange.of(1))
//    					.acceptCondition(RandomChance.builder(0.05F))
//    					.acceptCondition(KilledByPlayer.builder())
//    					.addEntry(itemEntry(VEItems.TAIL_KOBOLD, 1))));
    }
    
    private void addChestLootTables()
    {
        addChestLootTable("inject/chests/village/village_weaponsmith", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.06F))
                        .addEntry(itemEntry(VEItems.COATING_SILVER, 1))
                )
        );
        addChestLootTable("inject/chests/simple_dungeon", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                ).addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1)))
        );
        addChestLootTable("inject/chests/abandoned_mineshaft", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MAJOR, 1))
                ).addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1)))
        );
        addChestLootTable("inject/chests/bastion_hoglin_stable", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MAJOR, 1))
                )
        );
        addChestLootTable("inject/chests/bastion_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.75F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MAJOR, 3))
                )
        );
        addChestLootTable("inject/chests/buried_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MAJOR, 1))
                ).addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1)))
        );
        addChestLootTable("inject/chests/desert_pyramid", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.4F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MAJOR, 1))
                ).addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1)))
        );
        addChestLootTable("inject/chests/end_city_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.4F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MAJOR, 1))
                )
        );
        addChestLootTable("inject/chests/jungle_temple", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MAJOR, 1))
                )
        );
        addChestLootTable("inject/chests/nether_bridge", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.20F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 1))
                )
        );
        addChestLootTable("inject/chests/pillager_outpost", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.4F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                )
        );
        addChestLootTable("inject/chests/ruined_portal", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 1))
                ).addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1)))
        );
        addChestLootTable("inject/chests/shipwreck_treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 8))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MEDIUM, 5))
                ).addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1)))
        );
        addChestLootTable("inject/chests/stronghold_corridor", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.35F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 5))
                )
        );
        addChestLootTable("inject/chests/underwater_ruin_big", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.65F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 1))
                ).addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1)))
        );
        addChestLootTable("inject/chests/woodland_mansion", LootTable.builder().addLootPool(
                LootPool.builder()
                        .name("main")
                        .rolls(ConstantRange.of(1))
                        .acceptCondition(RandomChance.builder(0.25F))
                        .addEntry(tableEntry(VELootTables.MAGIC_ITEM_MINOR, 1))
                )
        );
        addChestLootTable("inject/gameplay/fishing/treasure", LootTable.builder().addLootPool(
                LootPool.builder()
                		.name("cosmetics")
                		.rolls(ConstantRange.of(1))
                		.acceptCondition(RandomChance.builder(0.1F))
                		.addEntry(tableEntry(VELootTables.COSMETICS, 1))
                )
        );
    }
    
    private void addLootTable(String location, LootTable.Builder lootTable, LootParameterSet lootParameterSet)
    {
        if(location.startsWith("inject/"))
        {
            String actualLocation = location.replace("inject/", "");
            Preconditions.checkArgument(existingFileHelper.exists(new ResourceLocation("loot_tables/" + actualLocation + ".json"), ResourcePackType.SERVER_DATA), "Loot table %s does not exist in any known data pack", actualLocation);
        }
        tables.add(Pair.of
        		(
        				() -> lootBuilder -> lootBuilder.accept(new ResourceLocation(Reference.ModInfo.MOD_ID, location), lootTable), 
        				lootParameterSet
        		));
    }
    
    private static LootEntry.Builder<?> tableEntry(ResourceLocation table, int weight)
    {
        return TableLootEntry.builder(table).weight(weight);
    }
    
    private static StandaloneLootEntry.Builder<?> itemEntry(Item item, int weight)
    {
        return ItemLootEntry.builder(item).weight(weight);
    }
    
    private void addChestLootTable(String location, LootTable.Builder lootTable) {
        addLootTable(location, lootTable, LootParameterSets.CHEST);
    }
    
    private void addEntityLootTable(String location, LootTable.Builder lootTable)
    {
    	addLootTable(location, lootTable, LootParameterSets.ENTITY);
    }
    
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validateLootTable(validationtracker, loc, table));
    }
}
