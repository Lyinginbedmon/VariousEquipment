package com.lying.variousequipment.data.recipes;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.bauble.ItemHorns.Antenna;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AntennaRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<AntennaRecipe> SERIALIZER = new SpecialRecipeSerializer<>(AntennaRecipe::new);
	
	public AntennaRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		List<ItemStack> antennae = Lists.newArrayList();
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.ANTENNA)
					antennae.add(stack);
				else
					return false;
			}
		}
		
		switch(antennae.size())
		{
			case 0:
				return false;
			case 1:
				ItemStack single = antennae.get(0);
				if(Antenna.getType(single) == Antenna.Type.SINGLE)
					return false;
				else
					return true;
			case 2:
				ItemStack ant1 = antennae.get(0);
				ItemStack ant2 = antennae.get(1);
				if(Antenna.getType(ant1) != Antenna.Type.SINGLE || Antenna.getType(ant2) != Antenna.Type.SINGLE)
					return false;
				else
					return true;
			default:
				return false;
		}
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack output = ItemStack.EMPTY;
		
		List<ItemStack> antennae = Lists.newArrayList();
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.ANTENNA)
					antennae.add(stack);
				else
					return output;
			}
		}
		
		switch(antennae.size())
		{
			case 0:
				return output;
			case 1:
				ItemStack single = antennae.get(0);
				if(Antenna.getType(single) == Antenna.Type.SINGLE)
					return output;
				else
				{
					output = single.copy();
					Antenna.setType(output, Antenna.getType(output) == Antenna.Type.GENICULATE ? Antenna.Type.PLUMOSE : Antenna.Type.GENICULATE);
					return output;
				}
			case 2:
				ItemStack ant1 = antennae.get(0);
				ItemStack ant2 = antennae.get(1);
				if(Antenna.getType(ant1) != Antenna.Type.SINGLE || Antenna.getType(ant2) != Antenna.Type.SINGLE)
					return output;
				else
				{
					output = ant1.copy();
					Antenna.setType(output, Antenna.Type.GENICULATE);
					return output;
				}
			default:
				return output;
		}
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
