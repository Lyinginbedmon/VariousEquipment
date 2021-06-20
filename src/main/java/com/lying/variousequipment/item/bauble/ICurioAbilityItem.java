package com.lying.variousequipment.item.bauble;

import com.lying.variousoddities.api.event.GatherAbilitiesEvent;
import com.lying.variousoddities.capabilities.LivingData;
import com.lying.variousoddities.species.abilities.AbilityBlind;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public interface ICurioAbilityItem extends ICurioItem
{
	public default void onEquip(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		LivingData.forEntity(livingEntity).getAbilities().markForRecache();
	}
	
	public default void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		LivingData.forEntity(livingEntity).getAbilities().markForRecache();
	}
	
	public boolean shouldAddAbilitiesTo(LivingEntity entity);
	
	public void addAbilitiesTo(GatherAbilitiesEvent event);
	
	public default void onAbilityGather(GatherAbilitiesEvent event)
	{
		if(event.getEntityLiving() == null)
			return;
		
		if(!event.hasAbility(AbilityBlind.REGISTRY_NAME))
		{
			LivingEntity entity = event.getEntityLiving();
			
			// FIXME Temp solution to world load crashes
			try
			{
				if(shouldAddAbilitiesTo(entity))
					addAbilitiesTo(event);
			}
			catch(Exception e){ }
		}
	}
}
