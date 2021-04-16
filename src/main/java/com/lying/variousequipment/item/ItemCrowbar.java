package com.lying.variousequipment.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCrowbar extends SwordItem
{
	public ItemCrowbar(Properties propertiesIn)
	{
		super(ItemTier.STONE, 1, -3.25F, propertiesIn.maxDamage(16).setNoRepair());
	}
	
	public ActionResultType onItemUse(ItemUseContext context)
	{
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		PlayerEntity player = context.getPlayer();
		Hand hand = context.getHand();
		
		ItemStack heldItem = player.getHeldItem(hand);
		BlockState state = worldIn.getBlockState(pos);
		if(!worldIn.isRemote && state.getMaterial() == Material.IRON)
		{
			boolean shouldDamage = false;
			
	    	if(state.getBlock() instanceof DoorBlock)
	    	{
	    		BlockPos doorLower = state.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? pos : pos.offset(Direction.DOWN);
	    		((DoorBlock)state.getBlock()).openDoor(worldIn, worldIn.getBlockState(doorLower), doorLower, !(Boolean)state.get(DoorBlock.OPEN).booleanValue());
	    		shouldDamage = true;
	    	}
	    	else if(state.getBlock() instanceof TrapDoorBlock)
	    	{
	    		boolean open = !(Boolean)state.get(TrapDoorBlock.OPEN).booleanValue();
	            state = state.with(TrapDoorBlock.OPEN, open);
	            worldIn.setBlockState(pos, state, 2);
	            shouldDamage = true;
	            worldIn.playSound(player, pos, open ? SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN : SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    	}
	    	
	    	if(shouldDamage)
	    	{
	    		heldItem.damageItem(player.getRNG().nextInt(6) == 0 ? 1 : 0, player, e -> e.sendBreakAnimation(hand));
	            return ActionResultType.SUCCESS;
	    	}
		}
		
		return ActionResultType.PASS;
	}
}
