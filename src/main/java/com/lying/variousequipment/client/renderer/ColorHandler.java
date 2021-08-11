package com.lying.variousequipment.client.renderer;

import com.lying.variousequipment.block.BlockScreen;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemVialThrowable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class ColorHandler
{
	public static void registerColorHandlers()
	{
		BlockColors registryBlock = Minecraft.getInstance().getBlockColors();
		registryBlock.register(BlockScreen::getColor, 
			VEBlocks.SCREEN_WHITE, 
			VEBlocks.SCREEN_BLACK, 
			VEBlocks.SCREEN_BLUE, 
			VEBlocks.SCREEN_BROWN, 
			VEBlocks.SCREEN_CYAN, 
			VEBlocks.SCREEN_GRAY, 
			VEBlocks.SCREEN_GREEN, 
			VEBlocks.SCREEN_LIGHT_BLUE, 
			VEBlocks.SCREEN_LIGHT_GRAY, 
			VEBlocks.SCREEN_LIME, 
			VEBlocks.SCREEN_MAGENTA, 
			VEBlocks.SCREEN_ORANGE, 
			VEBlocks.SCREEN_PINK, 
			VEBlocks.SCREEN_PURPLE, 
			VEBlocks.SCREEN_RED, 
			VEBlocks.SCREEN_YELLOW);
		
		ItemColors registryItems = Minecraft.getInstance().getItemColors();
		
		for(Item item : VEItems.DYEABLES)
			registryItems.register((stack, val) -> { return val == 0 ? ((IDyeableArmorItem)stack.getItem()).getColor(stack) : -1; }, item);
		
		registryItems.register((stack, val) -> { return val == 0 ? ((ItemVialThrowable)stack.getItem()).getColor() : -1; }, VEItems.HOLY_WATER);
		registryItems.register((stack, val) -> { return BlockScreen.getColor(Block.getBlockFromItem(stack.getItem()).getDefaultState(), null, BlockPos.ZERO, 0); }, 
				VEBlocks.SCREEN_WHITE, 
				VEBlocks.SCREEN_BLACK, 
				VEBlocks.SCREEN_BLUE, 
				VEBlocks.SCREEN_BROWN, 
				VEBlocks.SCREEN_CYAN, 
				VEBlocks.SCREEN_GRAY, 
				VEBlocks.SCREEN_GREEN, 
				VEBlocks.SCREEN_LIGHT_BLUE, 
				VEBlocks.SCREEN_LIGHT_GRAY, 
				VEBlocks.SCREEN_LIME, 
				VEBlocks.SCREEN_MAGENTA, 
				VEBlocks.SCREEN_ORANGE, 
				VEBlocks.SCREEN_PINK, 
				VEBlocks.SCREEN_PURPLE, 
				VEBlocks.SCREEN_RED, 
				VEBlocks.SCREEN_YELLOW);
	}
}
