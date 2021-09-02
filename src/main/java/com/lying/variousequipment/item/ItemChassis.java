package com.lying.variousequipment.item;

import com.lying.variousequipment.entity.EntityWagon;
import com.lying.variousequipment.init.VEEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemChassis extends BlockItem
{
	public ItemChassis(Block blockIn, Properties builder)
	{
		super(blockIn, builder.group(VEItemGroup.GEAR));
	}
	
	public ActionResultType onItemUse(ItemUseContext context)
	{
		World world = context.getWorld();
		if(!(world instanceof ServerWorld))
			return ActionResultType.SUCCESS;
		
		ItemStack stack = context.getItem();
		BlockPos pos = context.getPos();
		Direction facing = context.getFace();
		
		float spawnYaw = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getPlacementYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
		BlockState state = world.getBlockState(pos);
		BlockPos spawnPos = state.getCollisionShape(world, pos).isEmpty() ? pos : pos.offset(facing);
		
		EntityWagon wagon = VEEntities.WAGON.get().create(world);
		wagon.setPartInSlot(0, stack.copy().split(1));
		wagon.setPositionAndRotation(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D, spawnYaw, 0F);
		if(world.hasNoCollisions(wagon))
		{
			PlayerEntity player = context.getPlayer();
			if(player != null && !player.abilities.isCreativeMode)
				stack.shrink(1);
			
			world.addEntity(wagon);
			world.playSound((PlayerEntity)null, wagon.getPosX(), wagon.getPosY(), wagon.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
			
			return ActionResultType.CONSUME;
		}
		
		return ActionResultType.PASS;
	}
}
