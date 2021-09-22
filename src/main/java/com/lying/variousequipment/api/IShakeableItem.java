package com.lying.variousequipment.api;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.item.crafting.IMixerRecipe;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IShakeableItem
{
	public default void onItemShaken(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		if(!worldIn.isRemote && entityLiving.getType() == EntityType.PLAYER)
		{
			List<IMixerRecipe> recipes = getMatchingRecipes(stack, worldIn);
			if(!recipes.isEmpty())
			{
				IMixerRecipe recipe = recipes.get(0);
				((PlayerEntity)entityLiving).addItemStackToInventory(recipe.getRecipeOut(stack));
				stack.shrink(1);
			}
		}
	}
	
	public static List<IMixerRecipe> getMatchingRecipes(ItemStack input, World world)
	{
		List<IMixerRecipe> matchingRecipes = Lists.newArrayList();
		for(IMixerRecipe recipe : mixerRecipes(world))
			if(recipe.matches(input, world))
				matchingRecipes.add(recipe);
		return matchingRecipes;
	}
	
	public static List<IMixerRecipe> mixerRecipes(World world)
	{
		return VERecipeTypes.getRecipes(world, VERecipeTypes.MIXER_TYPE).values().stream()
				.filter(r -> r instanceof IMixerRecipe)
				.map(r -> (IMixerRecipe) r)
				.collect(Collectors.toList());
	}
}
