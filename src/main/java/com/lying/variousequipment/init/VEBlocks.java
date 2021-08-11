package com.lying.variousequipment.init;

import java.util.ArrayList;
import java.util.List;

import com.lying.variousequipment.block.BlockMissing;
import com.lying.variousequipment.block.BlockScreen;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = Reference.ModInfo.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class VEBlocks
{
	private static final List<Block> BLOCKS = new ArrayList<>();
	
	public static final Block MISSING_BLOCK		= register("missing_block", new BlockMissing(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE_TERRACOTTA)));
	public static final Block SCREEN_WHITE		= register("white_screen", new BlockScreen(DyeColor.WHITE, AbstractBlock.Properties.create(Material.ROCK, DyeColor.WHITE)));
	public static final Block SCREEN_BLACK		= register("black_screen", new BlockScreen(DyeColor.BLACK, AbstractBlock.Properties.create(Material.ROCK, DyeColor.BLACK)));
	public static final Block SCREEN_BLUE		= register("blue_screen", new BlockScreen(DyeColor.BLUE, AbstractBlock.Properties.create(Material.ROCK, DyeColor.BLUE)));
	public static final Block SCREEN_BROWN		= register("brown_screen", new BlockScreen(DyeColor.BROWN, AbstractBlock.Properties.create(Material.ROCK, DyeColor.BROWN)));
	public static final Block SCREEN_CYAN		= register("cyan_screen", new BlockScreen(DyeColor.CYAN, AbstractBlock.Properties.create(Material.ROCK, DyeColor.CYAN)));
	public static final Block SCREEN_GRAY		= register("gray_screen", new BlockScreen(DyeColor.GRAY, AbstractBlock.Properties.create(Material.ROCK, DyeColor.GRAY)));
	public static final Block SCREEN_GREEN		= register("green_screen", new BlockScreen(DyeColor.GREEN, AbstractBlock.Properties.create(Material.ROCK, DyeColor.GREEN)));
	public static final Block SCREEN_LIGHT_BLUE	= register("light_blue_screen", new BlockScreen(DyeColor.LIGHT_BLUE, AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE)));
	public static final Block SCREEN_LIGHT_GRAY	= register("light_gray_screen", new BlockScreen(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIGHT_GRAY)));
	public static final Block SCREEN_LIME		= register("lime_screen", new BlockScreen(DyeColor.LIME, AbstractBlock.Properties.create(Material.ROCK, DyeColor.LIME)));
	public static final Block SCREEN_MAGENTA	= register("magenta_screen", new BlockScreen(DyeColor.MAGENTA, AbstractBlock.Properties.create(Material.ROCK, DyeColor.MAGENTA)));
	public static final Block SCREEN_ORANGE		= register("orange_screen", new BlockScreen(DyeColor.ORANGE, AbstractBlock.Properties.create(Material.ROCK, DyeColor.ORANGE)));
	public static final Block SCREEN_PINK		= register("pink_screen", new BlockScreen(DyeColor.PINK, AbstractBlock.Properties.create(Material.ROCK, DyeColor.PINK)));
	public static final Block SCREEN_PURPLE		= register("purple_screen", new BlockScreen(DyeColor.PURPLE, AbstractBlock.Properties.create(Material.ROCK, DyeColor.PURPLE)));
	public static final Block SCREEN_RED		= register("red_screen", new BlockScreen(DyeColor.RED, AbstractBlock.Properties.create(Material.ROCK, DyeColor.RED)));
	public static final Block SCREEN_YELLOW		= register("yellow_screen", new BlockScreen(DyeColor.YELLOW, AbstractBlock.Properties.create(Material.ROCK, DyeColor.YELLOW)));
	
	public static Block register(String nameIn, Block blockIn)
	{
		blockIn.setRegistryName(Reference.ModInfo.MOD_PREFIX+nameIn);
		BLOCKS.add(blockIn);
		return blockIn;
	}
	
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
    {
    	blockRegistryEvent.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }
}
