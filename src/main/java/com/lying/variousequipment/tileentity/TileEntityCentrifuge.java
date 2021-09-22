package com.lying.variousequipment.tileentity;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.init.VETileEntities;
import com.lying.variousequipment.item.crafting.ICentrifugeRecipe;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCentrifuge extends TileEntitySpinny
{
	public TileEntityCentrifuge()
	{
		super(VETileEntities.CENTRIFUGE);
	}
	
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return false;
	}
	
	public boolean canProcess(ItemStack stack)
	{
		return !getMatchingRecipes(stack).isEmpty();
	}
	
	public void processItem(ItemStack stack, int slot)
	{
		List<ICentrifugeRecipe> recipes = getMatchingRecipes(stack);
		if(recipes == null || recipes.isEmpty())
			return;
		
		setInventorySlotContents(slot, ItemStack.EMPTY);
		if(getWorld().isRemote) return;
		
		BlockPos pos = getPos();
		ICentrifugeRecipe recipe = recipes.get(0);
		NonNullList<ItemStack> products = recipe.getRecipeOutput(getWorld().rand, getWorld());
		for(ItemStack drop : products)
		{
			if(canProcess(drop) && getStackInSlot(slot).isEmpty())
				setInventorySlotContents(slot, drop.split(getInventoryStackLimit()));
			
			if(!drop.isEmpty())
			{
				ItemEntity entity = new ItemEntity(this.world, pos.getX(), pos.getY() + 1D, pos.getZ(), drop);
				entity.setDefaultPickupDelay();
				getWorld().addEntity(entity);
			}
		}
	}
	
	private List<ICentrifugeRecipe> getMatchingRecipes(ItemStack input)
	{
		List<ICentrifugeRecipe> matchingRecipes = Lists.newArrayList();
		for(ICentrifugeRecipe recipe : centrifugeRecipes(world))
			if(recipe.matches(input, getWorld()))
				matchingRecipes.add(recipe);
		return matchingRecipes;
	}
	
	private static List<ICentrifugeRecipe> centrifugeRecipes(World world)
	{
		return VERecipeTypes.getRecipes(world, VERecipeTypes.CENTRIFUGE_TYPE).values().stream()
				.filter(r -> r instanceof ICentrifugeRecipe)
				.map(r -> (ICentrifugeRecipe) r)
				.collect(Collectors.toList());
	}
}
