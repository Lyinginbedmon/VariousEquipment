package com.lying.variousequipment.init;

import java.util.ArrayList;
import java.util.List;

import com.lying.variousequipment.data.recipes.*;
import com.lying.variousequipment.item.*;
import com.lying.variousequipment.item.VEItemGroup;
import com.lying.variousequipment.item.bauble.*;
import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.init.VOEnchantments;
import com.lying.variousoddities.item.VOItemGroup;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class VEItems
{
	private static final List<Item> ITEMS = new ArrayList<>();
	private static final List<BlockItem> BLOCK_ITEMS = new ArrayList<>();
	
	// Items
	public static final Item HAT_ARCHFEY	= register("hat_archfey", new ItemHatArchfey(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HAT_MASK		= register("hat_fey_mask", new ItemHatFeyMask(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item EARS_WOLF		= register("ears_wolf", new ItemHorns.Wolf(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item EARS_CAT		= register("ears_cat", new ItemHorns.Cat(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item GILLS_AXOLOTL	= register("gills_axolotl", new ItemHorns.Axolotl(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item ANTENNA		= register("ant_antenna", new ItemHorns.Antenna(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_RAM		= register("horns_ram", new ItemHorns.Ram(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_DEER		= register("horns_deer", new ItemHorns.Deer(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_KIRIN	= register("horns_kirin", new ItemHorns.Kirin(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_KIRIN_STORM	= register("horns_kirin_storm", new ItemHorns.KirinStorm(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_HARTEBEEST = register("horns_hartebeest", new ItemHorns.Hartebeest(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_KOBOLD	= register("tail_kobold", new ItemTails.Kobold(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_KIRIN		= register("tail_kirin", new ItemTails.Kirin(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_WOLF		= register("tail_wolf", new ItemTails.Wolf(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_CAT		= register("tail_cat", new ItemTails.Cat(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_FOX		= register("tail_fox", new ItemTails.Fox(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_RAT		= register("tail_rat", new ItemTails.Rat(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_CAT_2		= register("tail_bakeneko", new ItemTails.Cat2(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_DRAGON	= register("tail_dragon", new ItemTails.Dragon(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_DEVIL		= register("tail_devil", new ItemTails.Devil(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_RABBIT	= register("tail_rabbit", new ItemTails.Rabbit(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_HORSE		= register("tail_horse", new ItemTails.Horse(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_DRAGONFLY	= register("tail_dragonfly", new ItemTails.Dragonfly(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_ANT		= register("tail_ant", new ItemTails.Ant(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_LIZARD	= register("tail_lizard", new ItemTails.Lizard(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item TAIL_LIZARD2	= register("tail_lizard_barbed", new ItemTails.Lizard2(new Item.Properties().group(VEItemGroup.PROPS)));
	
	public static final Item HAT_WITCH		= register("hat_witch", new ItemHatWitch(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item HAT_HOOD		= register("hat_hood", new ItemHatHood(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item BLINDFOLD		= register("blindfold", new ItemBlindfold(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item BLINDFOLD_FAKE	= register("cosmetic_blindfold", new ItemBlindfold(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item RING_IRON		= register("iron_ring", new ItemRing("ring_iron", new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item RING_GOLD		= register("gold_ring", new ItemRing("ring_gold", new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item CROWBAR		= register("crowbar", new ItemCrowbar(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_WOOD	= register("wooden_holy_symbol", new ItemSymbol(ItemTier.WOOD, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_STONE	= register("stone_holy_symbol", new ItemSymbol(ItemTier.STONE, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_IRON	= register("iron_holy_symbol", new ItemSymbol(ItemTier.IRON, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_GOLD	= register("golden_holy_symbol", new ItemSymbol(ItemTier.GOLD, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_DIAMOND	= register("diamond_holy_symbol", new ItemSymbol(ItemTier.DIAMOND, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item HOLY_WATER		= register("vial_holy", new ItemHolyWater(new Item.Properties().group(VEItemGroup.GEAR)));
	
	public static final Item SCARAB_GOLEM	= register("scarab_golem", new ItemScarabGolem(new Item.Properties().group(VOItemGroup.LOOT)));
	public static final Item STONE_LUCK		= register("luckstone", new ItemLuckstone(new Item.Properties().group(VOItemGroup.LOOT)));
	public static final Item FOOD_SPOON		= register("sustaining_spoon", new ItemFoodSpoon(new Item.Properties().group(VOItemGroup.LOOT)));
	public static final Item COATING_SILVER	= register("silversheen", new ItemCoating(VOEnchantments.SILVERSHEEN, new Item.Properties().group(VOItemGroup.LOOT)));
	
	public static Item register(String nameIn, Item itemIn)
	{
		itemIn.setRegistryName(Reference.ModInfo.MOD_PREFIX+nameIn);
		ITEMS.add(itemIn);
		return itemIn;
	}
	
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
    {
    	IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
    	registry.registerAll(ITEMS.toArray(new Item[0]));
    	registry.registerAll(BLOCK_ITEMS.toArray(new Item[0]));
    	
    	ITEMS.forEach((item) -> 
    	{
    		if(item instanceof IEventListenerItem)
    			((IEventListenerItem)item).addListeners(MinecraftForge.EVENT_BUS);
    	});
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void registerProperties()
    {
    	ItemModelsProperties.registerProperty(HAT_HOOD, new ResourceLocation(Reference.ModInfo.MOD_ID, "is_up"), (stack, world, entity) -> { return ItemHatHood.getIsUp(stack) ? 1F : 0F; });
    }
    
    public static void onRecipeSerializerRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
    	IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
    	registry.register(CoatItemRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "coat_item")));
    	registry.register(HolyWaterRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "holy_water")));
    	registry.register(KitsuneTailRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "kitsune_tail")));
    	registry.register(AntennaRecipe.SERIALIZER.setRegistryName(new ResourceLocation(Reference.ModInfo.MOD_ID, "antennae")));
    }
}
