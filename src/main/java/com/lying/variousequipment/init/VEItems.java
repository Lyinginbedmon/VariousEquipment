package com.lying.variousequipment.init;

import java.util.ArrayList;
import java.util.List;

import com.lying.variousequipment.item.*;
import com.lying.variousequipment.item.VEItemGroup;
import com.lying.variousequipment.item.bauble.*;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.ModInfo.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class VEItems
{
	private static final List<Item> ITEMS = new ArrayList<>();
	private static final List<BlockItem> BLOCK_ITEMS = new ArrayList<>();
	
	// Items
	public static final Item HAT_ARCHFEY	= register("hat_archfey", new ItemHatArchfey(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HAT_MASK		= register("hat_fey_mask", new ItemHatFeyMask(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_RAM		= register("horns_ram", new ItemHorns.Ram(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_DEER		= register("horns_deer", new ItemHorns.Deer(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HORNS_KIRIN	= register("horns_kirin", new ItemHorns.Kirin(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HAT_WITCH		= register("hat_witch", new ItemHatWitch(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item HAT_HOOD		= register("hat_hood", new ItemHatHood(new Item.Properties().group(VEItemGroup.GEAR)));
	
	// Curios
	public static final Item SCARAB_GOLEM	= register("scarab_golem", new ItemScarabGolem(new Item.Properties().group(VEItemGroup.GEAR)));
	
	public static Item register(String nameIn, Item itemIn)
	{
		itemIn.setRegistryName(Reference.ModInfo.MOD_PREFIX+nameIn);
		ITEMS.add(itemIn);
		return itemIn;
	}
	
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
    {
    	IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
    	registry.registerAll(ITEMS.toArray(new Item[0]));
    	registry.registerAll(BLOCK_ITEMS.toArray(new Item[0]));
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void registerProperties()
    {
    	ItemModelsProperties.registerProperty(HAT_HOOD, new ResourceLocation(Reference.ModInfo.MOD_ID, "is_up"), (stack, world, entity) -> { return ItemHatHood.getIsUp(stack) ? 1F : 0F; });
    }
}
