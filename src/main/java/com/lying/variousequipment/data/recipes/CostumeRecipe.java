package com.lying.variousequipment.data.recipes;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.bauble.ItemCostume;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;

public class CostumeRecipe extends SpecialRecipe
{
	public static final SpecialRecipeSerializer<CostumeRecipe> SERIALIZER = new SpecialRecipeSerializer<>(CostumeRecipe::new);
	
	public CostumeRecipe(ResourceLocation idIn)
	{
		super(idIn);
	}
	
	public boolean matches(CraftingInventory inv, World worldIn)
	{
		List<ItemStack> cosmetics = Lists.newArrayList();
		ItemStack string = ItemStack.EMPTY;
		
		ItemStack costume = ItemStack.EMPTY;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.COSTUME)
				{
					if(costume.isEmpty())
						costume = stack;
					else
						return false;
				}
				else if(stack.getItem() == Items.STRING)
				{
					if(string.isEmpty())
						string = stack;
					else
						return false;
				}
				else if(CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains("cosmetic"))
				{
					if(costume.isEmpty())
						cosmetics.add(stack);
					else
						return false;
				}
				else
					return false;
			}
		}
		
		return (!costume.isEmpty() && cosmetics.isEmpty() && string.isEmpty()) || (costume.isEmpty() && cosmetics.size() >= 2 && !string.isEmpty());
	}
	
	public ItemStack getCraftingResult(CraftingInventory inv)
	{
		List<ItemStack> cosmetics = Lists.newArrayList();
		ItemStack string = ItemStack.EMPTY;
		
		ItemStack costume = ItemStack.EMPTY;
		
		for(int i=0; i<inv.getSizeInventory(); ++i)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty())
			{
				if(stack.getItem() == VEItems.COSTUME)
				{
					if(costume.isEmpty())
						costume = stack;
					else
						return ItemStack.EMPTY;
				}
				else if(stack.getItem() == Items.STRING)
				{
					if(string.isEmpty())
						string = stack;
					else
						return ItemStack.EMPTY;
				}
				else if(CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).contains("cosmetic"))
					cosmetics.add(stack);
				else
					return ItemStack.EMPTY;
			}
		}
		
		// Retrieve cosmetic from costume
		if((cosmetics.isEmpty() && string.isEmpty()) && !costume.isEmpty())
		{
			List<ItemStack> components = ItemCostume.getComponents(costume);
			return components.isEmpty() ? ItemStack.EMPTY : components.get(components.size() - 1);
		}
		// Creating new costume
		else if(!(cosmetics.isEmpty() && string.isEmpty()) && costume.isEmpty())
		{
			ItemStack output = new ItemStack(VEItems.COSTUME);
			ItemCostume.addComponents(output, cosmetics);
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
