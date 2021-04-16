package com.lying.variousequipment.data.recipes;

import com.lying.variousequipment.item.ItemCoating;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CoatItemRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<CoatItemRecipe> SERIALIZER = new SpecialRecipeSerializer<>(CoatItemRecipe::new);
	
	public CoatItemRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		ItemStack coating = ItemStack.EMPTY;
		ItemStack weapon = ItemStack.EMPTY;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() instanceof ItemCoating)
				{
					if(coating.isEmpty())
						coating = stack;
					else
						return false;
				}
				else if(weapon.isEmpty())
					weapon = stack;
				else
					return false;
			}
		}
		if(coating.isEmpty() || weapon.isEmpty())
			return false;
		
		Enchantment ench = ((ItemCoating)coating.getItem()).getEnchantment();
		return EnchantmentHelper.getEnchantmentLevel(ench, weapon) == 0 && ench.canApply(weapon);
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		ItemStack coating = ItemStack.EMPTY;
		ItemStack weapon = ItemStack.EMPTY;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() instanceof ItemCoating)
				{
					if(coating.isEmpty())
						coating = stack;
					else
						return ItemStack.EMPTY;
				}
				else if(weapon.isEmpty())
					weapon = stack;
				else
					return ItemStack.EMPTY;
			}
		}
		if(coating.isEmpty() || weapon.isEmpty())
			return ItemStack.EMPTY;
		
		ItemStack stack = weapon.copy();
		Enchantment ench = ((ItemCoating)coating.getItem()).getEnchantment();
		stack.addEnchantment(ench, 1);
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
