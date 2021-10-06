package com.lying.variousequipment.item;

import com.lying.variousequipment.entity.EntityScarecrow;
import com.lying.variousequipment.init.VEEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemScarecrow extends Item
{
	public ItemScarecrow(Item.Properties propertiesIn)
	{
		super(propertiesIn);
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	public ActionResultType onItemUse(ItemUseContext context)
	{
		Direction direction = context.getFace();
		if(direction == Direction.DOWN)
			return ActionResultType.FAIL;
		else
		{
			World world = context.getWorld();
			BlockItemUseContext useContext = new BlockItemUseContext(context);
			BlockPos placePos = useContext.getPos();
			ItemStack itemstack = context.getItem();
			Vector3d vector3d = Vector3d.copyCenteredHorizontally(placePos);
			AxisAlignedBB bounds = VEEntities.SCARECROW.get().getSize().func_242285_a(vector3d.getX(), vector3d.getY(), vector3d.getZ());
			if (world.hasNoCollisions((Entity)null, bounds, (entity) -> {
			return true;
			}) && world.getEntitiesWithinAABBExcludingEntity((Entity)null, bounds).isEmpty())
			{
				if (world instanceof ServerWorld)
				{
					ServerWorld serverworld = (ServerWorld)world;
					EntityScarecrow scarecrow = VEEntities.SCARECROW.get().create(serverworld, itemstack.getTag(), (ITextComponent)null, context.getPlayer(), placePos, SpawnReason.SPAWN_EGG, true, true);
					if(scarecrow == null)
						return ActionResultType.FAIL;
					
					serverworld.func_242417_l(scarecrow);
					scarecrow.setLocationAndAngles(scarecrow.getPosX(), scarecrow.getPosY(), scarecrow.getPosZ(), MathHelper.wrapDegrees(context.getPlacementYaw() - 180F), 0.0F);
					world.addEntity(scarecrow);
					world.playSound((PlayerEntity)null, scarecrow.getPosX(), scarecrow.getPosY(), scarecrow.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
				}
				
				itemstack.shrink(1);
				return ActionResultType.func_233537_a_(world.isRemote);
			}
			else
				return ActionResultType.FAIL;
		}
	}
}
