package com.lying.variousequipment.data.recipes;

import com.lying.variousequipment.item.bauble.ItemPegLeg;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PegLegRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<PegLegRecipe> SERIALIZER = new SpecialRecipeSerializer<>(PegLegRecipe::new);
	
	public PegLegRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		ItemStack pegLeg = ItemStack.EMPTY;
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() instanceof ItemPegLeg)
				{
					if(pegLeg.isEmpty())
						pegLeg = stack;
					else
						return false;
				}
				else
					return false;
			}
		}
		return !pegLeg.isEmpty();
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack pegLeg = ItemStack.EMPTY;
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() instanceof ItemPegLeg)
				{
					if(pegLeg.isEmpty())
						pegLeg = stack;
					else
						return ItemStack.EMPTY;
				}
				else
					return ItemStack.EMPTY;
			}
		}
		
		switch(ItemPegLeg.getLegFromStack(pegLeg))
		{
			case LEFT:	return ItemPegLeg.setLeg(pegLeg.copy(), ItemPegLeg.Leg.RIGHT);
			case RIGHT:	return ItemPegLeg.setLeg(pegLeg.copy(), ItemPegLeg.Leg.LEFT);
		}
		return ItemStack.EMPTY;
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
