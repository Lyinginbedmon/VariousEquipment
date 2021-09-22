package com.lying.variousequipment.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.lying.variousequipment.init.VERecipeTypes;
import com.lying.variousequipment.init.VERecipeTypes.RecipeType;
import com.lying.variousequipment.item.crafting.IAdvFurnaceRecipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin
{
	@SuppressWarnings("unchecked")
	@Inject(method = "getRecipe(Lnet/minecraft/item/crafting/IRecipeType;Lnet/minecraft/inventory/IInventory;Lnet/minecraft/world/World;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
	public <C extends IInventory, T extends IRecipe<C>> void getRecipe(IRecipeType<T> recipeTypeIn, C inventoryIn, World worldIn, final CallbackInfoReturnable<Optional<T>> ci)
	{
		if(recipeTypeIn == RecipeType.SMELTING)
			for(IRecipe<IInventory> recipe : VERecipeTypes.getRecipes(worldIn, VERecipeTypes.FURNACE_TYPE).values())
			{
				Optional<IAdvFurnaceRecipe> adv = VERecipeTypes.FURNACE_TYPE.matches(recipe, worldIn, inventoryIn);
				if(adv.isPresent())
				{
					ci.setReturnValue((Optional<T>)adv);
					ci.cancel();
					return;
				}
			}
	}
}
