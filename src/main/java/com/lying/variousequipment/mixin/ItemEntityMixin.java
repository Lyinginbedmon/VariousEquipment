package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lying.variousequipment.block.BlockGuano;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ItemEntity.class)
public class ItemEntityMixin extends EntityMixin
{
	@Shadow
	public ItemStack getItem(){ return ItemStack.EMPTY; }
	
	@Shadow
	public void setItem(ItemStack stack){ }
	
	@Shadow
	public boolean cannotPickup(){ return false; }
	
	public int convertToGuanoTimer = Reference.Values.TICKS_PER_SECOND * 5;
	
	@SuppressWarnings("deprecation")
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tick(final CallbackInfo ci)
	{
		if(cannotPickup() || !isOnGround())
			this.convertToGuanoTimer = Reference.Values.TICKS_PER_SECOND * 5;
		
		ItemStack item = getItem();
		if(item == null || item.isEmpty())
			return;
		
		if(item.getItem() == VEItems.GUANO  && --this.convertToGuanoTimer <= 0)
		{
			BlockPos pos = getPosition();
			World world = getEntityWorld();
			if(world == null || world.isRemote) return;
			
			BlockState state = world.getBlockState(pos);
			if(state.getBlock() == Blocks.AIR && VEBlocks.GUANO.isValidPosition(state, world, pos))
			{
				world.setBlockState(pos, VEBlocks.GUANO.getDefaultState());
				item.shrink(1);
				this.convertToGuanoTimer = Reference.Values.TICKS_PER_SECOND * 5;
			}
			else if(state.getBlock() == VEBlocks.GUANO && state.get(BlockGuano.LAYERS) < 8)
			{
				world.setBlockState(pos, state.with(BlockGuano.LAYERS, Math.min(8, state.get(BlockGuano.LAYERS) + 1)));
				item.shrink(1);
				this.convertToGuanoTimer = Reference.Values.TICKS_PER_SECOND * 5;
			}
			
			if(item.isEmpty())
			{
				setDead();
				ci.cancel();
			}
			else
				setItem(item);
		}
	}
}
