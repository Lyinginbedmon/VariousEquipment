package com.lying.variousequipment.item;

import com.lying.variousequipment.item.vial.Vial;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemVialDrinkable extends ItemVial
{
	public ItemVialDrinkable(Properties properties)
	{
		super(properties);
	}
	
	public int getUseDuration(ItemStack stack){ return 32; }
	
	public UseAction getUseAction(ItemStack stack){ return UseAction.DRINK; }
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		Vial vial = ItemVial.getVialFromItem(playerIn.getHeldItem(handIn));
		if(vial != null && vial.canUse(playerIn, worldIn))
			return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
		else
			return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		PlayerEntity player = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
		if(player instanceof ServerPlayerEntity) 
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)player, stack);
		
		if(!worldIn.isRemote)
			ItemVial.getVialFromItem(stack).onDrink(stack, worldIn, entityLiving);
		
		if(player != null)
		{
			player.addStat(Stats.ITEM_USED.get(this));
			if (!player.abilities.isCreativeMode)
				stack.shrink(1);
		}
		
		if(player == null || !player.abilities.isCreativeMode)
		{
			if (stack.isEmpty())
				return new ItemStack(Items.GLASS_BOTTLE);
		
			if (player != null)
				player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		}
		
		return stack;
	}
	
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if(this.isInGroup(group))
			for(Vial.Builder vial : Vial.VIALS_DRINKABLE)
				items.add(ItemVial.addVialToItemStack(new ItemStack(this), vial));
	}
}
