package com.lying.variousequipment.item;

import com.lying.variousequipment.entity.EntityCaltrop;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemCaltrop extends Item
{
	public ItemCaltrop(Item.Properties properties)
	{
		super(properties.maxStackSize(8).group(VEItemGroup.GEAR));
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if(!worldIn.isRemote)
		{
			EntityCaltrop caltropEntity = new EntityCaltrop(worldIn, playerIn);
			caltropEntity.setItem(itemstack);
			caltropEntity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0F, 0.75F, 1F);
			worldIn.addEntity(caltropEntity);
		}
		
		playerIn.addStat(Stats.ITEM_USED.get(this));
		if(!playerIn.abilities.isCreativeMode)
			itemstack.shrink(1);
		
		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
}
