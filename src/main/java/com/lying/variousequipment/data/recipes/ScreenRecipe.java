package com.lying.variousequipment.data.recipes;

import java.util.HashMap;
import java.util.Map;

import com.lying.variousequipment.init.VEBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ScreenRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<ScreenRecipe> SERIALIZER = new SpecialRecipeSerializer<>(ScreenRecipe::new);
	
	/** Map of concrete to screen by colour, because concrete itself doesn't have an attached colour variable */
	private static final Map<Block, Block> CONCRETE_TO_SCREEN = new HashMap<>();
	
	public ScreenRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		ItemStack glowstone = ItemStack.EMPTY;
		ItemStack glass = ItemStack.EMPTY;
		ItemStack concrete = ItemStack.EMPTY;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				Item stackItem = stack.getItem();
				if(stackItem == Items.GLOWSTONE_DUST)
				{
					if(glowstone.isEmpty())
						glowstone = stack;
					else
						return false;
				}
				else if(Block.getBlockFromItem(stackItem) == Blocks.GLASS)
				{
					if(glass.isEmpty())
						glass = stack;
					else
						return false;
				}
				else if(CONCRETE_TO_SCREEN.containsKey(Block.getBlockFromItem(stackItem)))
				{
					if(concrete.isEmpty())
						concrete = stack;
					else
						return false;
				}
			}
		}
		
		return !(glowstone.isEmpty() || glass.isEmpty() || concrete.isEmpty());
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack glowstone = ItemStack.EMPTY;
		ItemStack glass = ItemStack.EMPTY;
		ItemStack concrete = ItemStack.EMPTY;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				Item stackItem = stack.getItem();
				if(stackItem == Items.GLOWSTONE_DUST)
				{
					if(glowstone.isEmpty())
						glowstone = stack;
					else
						return ItemStack.EMPTY;
				}
				else if(Block.getBlockFromItem(stackItem) == Blocks.GLASS)
				{
					if(glass.isEmpty())
						glass = stack;
					else
						return ItemStack.EMPTY;
				}
				else if(CONCRETE_TO_SCREEN.containsKey(Block.getBlockFromItem(stackItem)))
				{
					if(concrete.isEmpty())
						concrete = stack;
					else
						return ItemStack.EMPTY;
				}
			}
		}
		
		if(glowstone.isEmpty() || glass.isEmpty() || concrete.isEmpty())
			return ItemStack.EMPTY;
		
		return new ItemStack(CONCRETE_TO_SCREEN.get(Block.getBlockFromItem(concrete.getItem())));
	}
	
	public boolean canFit(int width, int height)
	{
		return width * height >= 3;
	}
	
	public IRecipeSerializer<?> getSerializer()
	{
		return SERIALIZER;
	}
	
	static
	{
		CONCRETE_TO_SCREEN.put(Blocks.BLACK_CONCRETE, VEBlocks.SCREEN_BLACK);
		CONCRETE_TO_SCREEN.put(Blocks.BLUE_CONCRETE, VEBlocks.SCREEN_BLUE);
		CONCRETE_TO_SCREEN.put(Blocks.BROWN_CONCRETE, VEBlocks.SCREEN_BROWN);
		CONCRETE_TO_SCREEN.put(Blocks.CYAN_CONCRETE, VEBlocks.SCREEN_CYAN);
		CONCRETE_TO_SCREEN.put(Blocks.GRAY_CONCRETE, VEBlocks.SCREEN_GRAY);
		CONCRETE_TO_SCREEN.put(Blocks.GREEN_CONCRETE, VEBlocks.SCREEN_GREEN);
		CONCRETE_TO_SCREEN.put(Blocks.LIGHT_BLUE_CONCRETE, VEBlocks.SCREEN_LIGHT_BLUE);
		CONCRETE_TO_SCREEN.put(Blocks.LIGHT_GRAY_CONCRETE, VEBlocks.SCREEN_LIGHT_GRAY);
		CONCRETE_TO_SCREEN.put(Blocks.LIME_CONCRETE, VEBlocks.SCREEN_LIME);
		CONCRETE_TO_SCREEN.put(Blocks.MAGENTA_CONCRETE, VEBlocks.SCREEN_MAGENTA);
		CONCRETE_TO_SCREEN.put(Blocks.ORANGE_CONCRETE, VEBlocks.SCREEN_ORANGE);
		CONCRETE_TO_SCREEN.put(Blocks.PINK_CONCRETE, VEBlocks.SCREEN_PINK);
		CONCRETE_TO_SCREEN.put(Blocks.PURPLE_CONCRETE, VEBlocks.SCREEN_PURPLE);
		CONCRETE_TO_SCREEN.put(Blocks.RED_CONCRETE, VEBlocks.SCREEN_RED);
		CONCRETE_TO_SCREEN.put(Blocks.WHITE_CONCRETE, VEBlocks.SCREEN_WHITE);
		CONCRETE_TO_SCREEN.put(Blocks.YELLOW_CONCRETE, VEBlocks.SCREEN_YELLOW);
	}
}
