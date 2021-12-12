package com.lying.variousequipment.data.recipes;

import java.util.HashMap;
import java.util.Map;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemWheel;
import com.lying.variousequipment.item.bauble.ItemWheelchair;
import com.mojang.datafixers.util.Pair;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WheelchairRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<WheelchairRecipe> SERIALIZER = new SpecialRecipeSerializer<>(WheelchairRecipe::new);
	private static final Map<Item, Item> SLABS_TO_SEATS = new HashMap<>();
	private static final Map<Item, DyeColor> WOOL_TO_COLORS = new HashMap<>();
	
	public WheelchairRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		ItemStack slab = ItemStack.EMPTY;
		
		// Identify slab and its relative position in the crafting grid
		int slabX = -1, slabY = -1;
		for(int x=1; x<inv.getWidth() - 1; x++)
			for(int y=1; y<inv.getHeight(); y++)
			{
				ItemStack stackInSlot = getStackAt(x, y, inv);
				if(!stackInSlot.isEmpty() && SLABS_TO_SEATS.containsKey(stackInSlot.getItem()))
				{
					if(slab.isEmpty())
					{
						slab = stackInSlot;
						slabX = x;
						slabY = y;
					}
					else
						return false;
				}
			}
		if(slab.isEmpty())
			return false;
		
		// Identify other components by relation to slab
		ItemStack wheel1 = getStackAt(slabX - 1, slabY, inv);
		if(!wheel1.isEmpty() && wheel1.getItem() instanceof ItemWheel)
			;
		else
			return false;
		
		ItemStack wheel2 = getStackAt(slabX + 1, slabY, inv);
		if(!wheel2.isEmpty() && wheel2.getItem() instanceof ItemWheel)
			;
		else
			return false;
		
		ItemStack woolItem = getStackAt(slabX, slabY - 1, inv);
		if(!woolItem.isEmpty() && WOOL_TO_COLORS.containsKey(woolItem.getItem()))
			;
		else
			return false;
		
		// Identify if crafting grid contains any other items
		for(int i=0; i<inv.getSizeInventory(); i++)
			if(!inv.getStackInSlot(i).isEmpty())
			{
				int x = i % inv.getWidth();
				int y = Math.floorDiv(i, inv.getWidth());
				
				if(
						x == slabX && y == slabY ||	// Slab slot
						x == slabX && y == (slabY - 1) || // Wool slot
						y == slabY && Math.abs(x - slabX) == 1) // Wheel slots
					;
				else
					return false;
			}
		
		return true;
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack slab = ItemStack.EMPTY;
		ItemStack wool = ItemStack.EMPTY;
		ItemStack wheelL = ItemStack.EMPTY;
		ItemStack wheelR = ItemStack.EMPTY;
		
		// Identify slab and its relative position in the crafting grid
		int slabX = -1, slabY = -1;
		for(int x=1; x<inv.getWidth() - 1; x++)
			for(int y=1; y<inv.getHeight(); y++)
			{
				ItemStack stackInSlot = getStackAt(x, y, inv);
				if(!stackInSlot.isEmpty())
					if(SLABS_TO_SEATS.containsKey(stackInSlot.getItem()))
						if(slab.isEmpty())
						{
							slab = stackInSlot;
							slabX = x;
							slabY = y;
						}
						else
							return ItemStack.EMPTY;
			}
		if(slab.isEmpty())
			return ItemStack.EMPTY;
		
		// Identify other components by relation to slab
		ItemStack wheel1 = getStackAt(slabX - 1, slabY, inv);
		if(!wheel1.isEmpty() && wheel1.getItem() instanceof ItemWheel)
			wheelL = wheel1;
		else
			return ItemStack.EMPTY;
		
		ItemStack wheel2 = getStackAt(slabX + 1, slabY, inv);
		if(!wheel2.isEmpty() && wheel2.getItem() instanceof ItemWheel)
			wheelR = wheel2;
		else
			return ItemStack.EMPTY;
		
		ItemStack woolItem = getStackAt(slabX, slabY - 1, inv);
		if(!woolItem.isEmpty() && WOOL_TO_COLORS.containsKey(woolItem.getItem()))
			wool = woolItem;
		else
			return ItemStack.EMPTY;
		
		// Identify if crafting grid contains any other items
		for(int i=0; i<inv.getSizeInventory(); i++)
			if(!inv.getStackInSlot(i).isEmpty())
			{
				int x = i % inv.getWidth();
				int y = Math.floorDiv(i, inv.getWidth());
				
				if(
						x == slabX && y == slabY ||	// Slab slot
						x == slabX && y == (slabY - 1) || // Wool slot
						y == slabY && Math.abs(x - slabX) == 1) // Wheel slots
					;
				else
					return ItemStack.EMPTY;
			}
		
		ItemStack output = new ItemStack(SLABS_TO_SEATS.get(slab.getItem()));
		((IDyeableArmorItem)output.getItem()).setColor(output, WOOL_TO_COLORS.get(wool.getItem()).getColorValue());
		ItemWheelchair.setWheels(output, Pair.of(wheelL.copy(), wheelR.copy()));
		
		return output;
	}
	
	private ItemStack getStackAt(int column, int row, CraftingInventory inv)
	{
		return inv.getStackInSlot(row*inv.getWidth() + column);
	}
	
	public boolean canFit(int width, int height)
	{
		return width >= 3 && height >= 2;
	}
	
	public IRecipeSerializer<?> getSerializer()
	{
		return SERIALIZER;
	}
	
	static
	{
		SLABS_TO_SEATS.put(Items.OAK_SLAB, VEItems.OAK_WHEELCHAIR);
		SLABS_TO_SEATS.put(Items.SPRUCE_SLAB, VEItems.SPRUCE_WHEELCHAIR);
		SLABS_TO_SEATS.put(Items.BIRCH_SLAB, VEItems.BIRCH_WHEELCHAIR);
		SLABS_TO_SEATS.put(Items.ACACIA_SLAB, VEItems.ACACIA_WHEELCHAIR);
		SLABS_TO_SEATS.put(Items.JUNGLE_SLAB, VEItems.JUNGLE_WHEELCHAIR);
		SLABS_TO_SEATS.put(Items.DARK_OAK_SLAB, VEItems.DARK_OAK_WHEELCHAIR);
		SLABS_TO_SEATS.put(Items.CRIMSON_SLAB, VEItems.CRIMSON_WHEELCHAIR);
		SLABS_TO_SEATS.put(Items.WARPED_SLAB, VEItems.WARPED_WHEELCHAIR);
		
		WOOL_TO_COLORS.put(Items.BLACK_WOOL, DyeColor.BLACK);
		WOOL_TO_COLORS.put(Items.BLUE_WOOL, DyeColor.BLUE);
		WOOL_TO_COLORS.put(Items.BROWN_WOOL, DyeColor.BROWN);
		WOOL_TO_COLORS.put(Items.CYAN_WOOL, DyeColor.CYAN);
		WOOL_TO_COLORS.put(Items.GRAY_WOOL, DyeColor.GRAY);
		WOOL_TO_COLORS.put(Items.GREEN_WOOL, DyeColor.GREEN);
		WOOL_TO_COLORS.put(Items.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE);
		WOOL_TO_COLORS.put(Items.LIGHT_GRAY_WOOL, DyeColor.LIGHT_GRAY);
		WOOL_TO_COLORS.put(Items.LIME_WOOL, DyeColor.LIME);
		WOOL_TO_COLORS.put(Items.MAGENTA_WOOL, DyeColor.MAGENTA);
		WOOL_TO_COLORS.put(Items.ORANGE_WOOL, DyeColor.ORANGE);
		WOOL_TO_COLORS.put(Items.PINK_WOOL, DyeColor.PINK);
		WOOL_TO_COLORS.put(Items.PURPLE_WOOL, DyeColor.PURPLE);
		WOOL_TO_COLORS.put(Items.RED_WOOL, DyeColor.RED);
		WOOL_TO_COLORS.put(Items.WHITE_WOOL, DyeColor.WHITE);
		WOOL_TO_COLORS.put(Items.YELLOW_WOOL, DyeColor.YELLOW);
	}
}
