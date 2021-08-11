package com.lying.variousequipment.data.recipes;

import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemCrossbowRepeating;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RepeatingCrossbowRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<RepeatingCrossbowRecipe> SERIALIZER = new SpecialRecipeSerializer<>(RepeatingCrossbowRecipe::new);
	
	public RepeatingCrossbowRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		ItemStack crossbow = ItemStack.EMPTY;
		List<ItemStack> arrows = Lists.newArrayList();
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.REPEATING_CROSSBOW)
				{
					if(crossbow.isEmpty())
						crossbow = stack;
					else
						return false;
				}
			}
		}
		if(crossbow.isEmpty())
			return false;
		int cap = ItemCrossbowRepeating.remainingCapacity(crossbow);
		if(cap < 1)
			return false;
		
		Predicate<ItemStack> predicate = ((ShootableItem)crossbow.getItem()).getAmmoPredicate();
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.REPEATING_CROSSBOW)
					continue;
				
				if(predicate.test(stack))
				{
					if(arrows.size() < cap)
						arrows.add(stack);
					else
						return false;
				}
				else
					return false;
			}
		}
		return !arrows.isEmpty() && arrows.size() <= ItemCrossbowRepeating.remainingCapacity(crossbow);
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack crossbow = ItemStack.EMPTY;
		List<ItemStack> arrows = Lists.newArrayList();
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.REPEATING_CROSSBOW)
				{
					if(crossbow.isEmpty())
						crossbow = stack;
					else
						return ItemStack.EMPTY;
				}
			}
		}
		if(crossbow.isEmpty() || ItemCrossbowRepeating.remainingCapacity(crossbow) < 1)
			return ItemStack.EMPTY;
		
		Predicate<ItemStack> predicate = ((ShootableItem)crossbow.getItem()).getAmmoPredicate();
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.REPEATING_CROSSBOW)
					continue;
				
				if(predicate.test(stack))
				{
					if(arrows.size() < 6)
						arrows.add(stack);
					else
						return ItemStack.EMPTY;
				}
				else
					return ItemStack.EMPTY;
			}
		}
		
		if(!arrows.isEmpty() && arrows.size() <= ItemCrossbowRepeating.remainingCapacity(crossbow))
		{
			ItemStack output = crossbow.copy();
			for(ItemStack arrow : arrows)
				ItemCrossbowRepeating.addShot(arrow.copy(), output);
			return output;
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
