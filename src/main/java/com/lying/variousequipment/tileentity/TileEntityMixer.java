package com.lying.variousequipment.tileentity;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.init.VETileEntities;
import com.lying.variousequipment.item.crafting.IMixerRecipe;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityMixer extends TileEntitySpinny
{
	public TileEntityMixer()
	{
		super(VETileEntities.MIXER);
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
		List<IMixerRecipe> recipes = getMatchingRecipes(stack);
		if(recipes == null || recipes.isEmpty())
			return;
		
		IMixerRecipe recipe = recipes.get(0);
		setInventorySlotContents(slot, recipe.getRecipeOut(stack));
	}
	
	private List<IMixerRecipe> getMatchingRecipes(ItemStack input)
	{
		List<IMixerRecipe> matchingRecipes = Lists.newArrayList();
		for(IMixerRecipe recipe : mixerRecipes(world))
			if(recipe.matches(input, getWorld()))
				matchingRecipes.add(recipe);
		return matchingRecipes;
	}
	
	private static List<IMixerRecipe> mixerRecipes(World world)
	{
		return VERecipeTypes.getRecipes(world, VERecipeTypes.MIXER_TYPE).values().stream()
				.filter(r -> r instanceof IMixerRecipe)
				.map(r -> (IMixerRecipe) r)
				.collect(Collectors.toList());
	}
}
