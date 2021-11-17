package com.lying.variousequipment.item.bauble;

import java.util.EnumSet;

import com.lying.variousequipment.init.VEItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

/**
 * Interface class which disables the rendering of certain biped parts when this item is worn by players in the curio slots.
 * @author Lying
 *
 */
public interface ILimbCosmetic
{
	/** Returns the type of limb this itemstack belongs to. */
	public EnumSet<LimbType> getLimbTypes(ItemStack stackIn);
	/** Returns true if the rendered model of this itemstack does not conflict with vanilla rendering of armour. */
	public default boolean canRenderWithArmour(ItemStack stackIn, EquipmentSlotType slotIn){ return false; }
	
	/**
	 * Returns true if any visible worn limb cosmetic conflicts with vanilla armour rendering in the given slot.<br>
	 * Allows different limb models to retain armour rendering where possible.
	 */
	public static boolean hasConflictingLimbs(PlayerEntity playerIn, LimbType typeIn, EquipmentSlotType slotIn)
	{
		ICuriosHelper helper = CuriosApi.getCuriosHelper();
		if(helper.getCuriosHandler(playerIn).isPresent())
		{
			ICuriosItemHandler handler = helper.getCuriosHandler(playerIn).orElse(null);
			for(ICurioStacksHandler stacks : handler.getCurios().values())
				for(int i=0; i<stacks.getSlots(); i++)
					if(stacks.getRenders().get(i))
					{
						ItemStack stack = stacks.getStacks().getStackInSlot(i);
						if(!stack.isEmpty())
							if(stackRenderingConflictsWith(stack, typeIn, slotIn))
								return true;
							else if(stack.getItem() == VEItems.COSTUME)
								for(ItemStack component : ItemCostume.getComponents(stack))
									if(stackRenderingConflictsWith(component, typeIn, slotIn))
										return true;
					}
		}
		return false;
	}
	
	public static boolean stackRenderingConflictsWith(ItemStack stack, LimbType type, EquipmentSlotType slot)
	{
		if(stack.getItem() instanceof ILimbCosmetic)
		{
			ILimbCosmetic cosmetic = (ILimbCosmetic)stack.getItem();
			return cosmetic.getLimbTypes(stack).contains(type) && !cosmetic.canRenderWithArmour(stack, slot);
		}
		return false;
	}
	
	/**
	 * Returns true if any visible worn limb cosmetic exists of the given limb type.<br>
	 * Used to disable specific parts of player model during rendering.
	 */
	public static boolean isWearingLimbsOfType(PlayerEntity playerIn, LimbType typeIn)
	{
		ICuriosHelper helper = CuriosApi.getCuriosHelper();
		if(helper.getCuriosHandler(playerIn).isPresent())
		{
			ICuriosItemHandler handler = helper.getCuriosHandler(playerIn).orElse(null);
			if(handler != null)
				for(ICurioStacksHandler stacks : handler.getCurios().values())
					for(int i=0; i<stacks.getSlots(); i++)
						if(stacks.getRenders().get(i))
						{
							ItemStack stack = stacks.getStacks().getStackInSlot(i);
							if(!stack.isEmpty())
								if(isLimbOfType(stack, typeIn))
									return true;
								else if(stack.getItem() == VEItems.COSTUME)
									for(ItemStack component : ItemCostume.getComponents(stack))
										if(isLimbOfType(component, typeIn))
											return true;
						}
		}
		return false;
	}
	
	public static boolean isLimbOfType(ItemStack stack, LimbType type)
	{
		return stack.getItem() instanceof ILimbCosmetic && ((ILimbCosmetic)stack.getItem()).getLimbTypes(stack).contains(type);
	}
	
	public static enum LimbType
	{
		ARM_RIGHT,
		ARM_LEFT,
		LEG_RIGHT,
		LEG_LEFT,
		TORSO,
		HEAD;
		
		public static final EnumSet<LimbType> ARMS = EnumSet.of(ARM_LEFT, ARM_RIGHT);
		public static final EnumSet<LimbType> LEGS = EnumSet.of(LEG_LEFT, LEG_RIGHT);
	}
}
