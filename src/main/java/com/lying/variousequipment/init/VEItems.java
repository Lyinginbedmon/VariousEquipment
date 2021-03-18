package com.lying.variousequipment.init;

import java.util.ArrayList;
import java.util.List;

import com.lying.variousequipment.item.*;
import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.item.VOItemGroup;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
	public static final Item HAT_WITCH		= register("hat_witch", new ItemHatWitch(new Item.Properties().group(VEItemGroup.GEAR)));
	
	// Block items
//	public static final BlockItem DRAFTING_TABLE	= registerBlock("drafting_table", VOBlocks.TABLE_DRAFTING, VOItemGroup.LOOT);
	
	public static Item register(String nameIn, Item itemIn)
	{
		itemIn.setRegistryName(Reference.ModInfo.MOD_PREFIX+nameIn);
		ITEMS.add(itemIn);
		return itemIn;
	}
	
	public static BlockItem registerBlock(String nameIn, Block blockIn)
	{
		return registerBlock(nameIn, blockIn, VOItemGroup.BLOCKS);
	}
	
	public static BlockItem registerBlock(String nameIn, Block blockIn, ItemGroup group)
	{
		return registerBlock(nameIn, new BlockItem(blockIn, new Item.Properties().group(group)));
	}
	
	public static BlockItem registerBlock(String nameIn, BlockItem itemIn)
	{
		itemIn.setRegistryName(Reference.ModInfo.MOD_PREFIX+nameIn);
		BLOCK_ITEMS.add(itemIn);
		return itemIn;
	}
	
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
    {
    	IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
    	registry.registerAll(ITEMS.toArray(new Item[0]));
    	registry.registerAll(BLOCK_ITEMS.toArray(new Item[0]));
    }
}
