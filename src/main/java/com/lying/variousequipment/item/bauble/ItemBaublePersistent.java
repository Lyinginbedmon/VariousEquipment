package com.lying.variousequipment.item.bauble;

import javax.annotation.Nonnull;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;

public abstract class ItemBaublePersistent extends ItemBauble
{
	public ItemBaublePersistent(Properties properties)
	{
		super(properties);
	}
	
	@Nonnull
	public DropRule getDropRule(LivingEntity livingEntity, ItemStack stack){ return DropRule.ALWAYS_KEEP; }
}
