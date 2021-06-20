package com.lying.variousequipment.data.recipes;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.bauble.ItemTails;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class KitsuneTailRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<KitsuneTailRecipe> SERIALIZER = new SpecialRecipeSerializer<>(KitsuneTailRecipe::new);
	
	public KitsuneTailRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		List<ItemStack> tails = Lists.newArrayList();
		int tailTotal = 0;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.TAIL_FOX)
				{
					tails.add(stack);
					tailTotal += ItemTails.Fox.getTails(stack);
				}
				else
					return false;
			}
		}
		
		return tails.size() >= 2 && tailTotal <= 9;
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		List<ItemStack> tails = Lists.newArrayList();
		int tailTotal = 0;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.TAIL_FOX)
				{
					tails.add(stack);
					tailTotal += ItemTails.Fox.getTails(stack);
				}
				else
					return ItemStack.EMPTY;
			}
		}
		if(tails.size() < 2 || tailTotal > 9)
			return ItemStack.EMPTY;
		
		ItemStack stack = tails.get(0).copy();
		ItemTails.Fox.setTails(stack, tailTotal);
		return stack;
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
