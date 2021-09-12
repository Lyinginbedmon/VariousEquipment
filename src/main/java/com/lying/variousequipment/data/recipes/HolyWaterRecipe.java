package com.lying.variousequipment.data.recipes;

import com.lying.variousequipment.data.VEItemTags;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemVial;
import com.lying.variousequipment.item.vial.Vial;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class HolyWaterRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<HolyWaterRecipe> SERIALIZER = new SpecialRecipeSerializer<>(HolyWaterRecipe::new);
	
	public HolyWaterRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		ItemStack symbol = ItemStack.EMPTY;
		int waterCount = 0;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(VEItemTags.HOLY_SYMBOL.contains(stack.getItem()))
				{
					if(symbol.isEmpty())
						symbol = stack;
					else
						return false;
				}
				else if(stack.getItem() == Items.POTION)
				{
					if(PotionUtils.getPotionFromItem(stack) == Potions.WATER)
						waterCount++;
					else
						return false;
				}
				else
					return false;
			}
		}
		
		return !symbol.isEmpty() && waterCount > 0;
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack symbol = ItemStack.EMPTY;
		int waterCount = 0;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(VEItemTags.HOLY_SYMBOL.contains(stack.getItem()))
				{
					if(symbol.isEmpty())
						symbol = stack;
					else
						return ItemStack.EMPTY;
				}
				else if(stack.getItem() == Items.POTION)
				{
					if(PotionUtils.getPotionFromItem(stack) == Potions.WATER)
						waterCount++;
					else
						return ItemStack.EMPTY;
				}
				else
					return ItemStack.EMPTY;
			}
		}
		if(symbol.isEmpty() || waterCount == 0)
			return ItemStack.EMPTY;
		
		ItemStack output = new ItemStack(VEItems.VIAL_THROWABLE, waterCount);
		ItemVial.addVialToItemStack(output, Vial.HOLY_WATER);
		return output;
	}
	
	public boolean canFit(int width, int height)
	{
		return width * height >= 2;
	}
	
	public IRecipeSerializer<?> getSerializer()
	{
		return SERIALIZER;
	}
}
