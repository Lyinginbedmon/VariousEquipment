package com.lying.variousequipment.item;

import com.lying.variousequipment.client.model.armor.ModelHatWitch;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHatWitch extends ItemHat implements IDyeableArmorItem
{
	public ItemHatWitch(Properties properties)
	{
		super("hat_witch", ArmorMaterial.LEATHER, properties);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, M original)
	{
		return (M) new ModelHatWitch(1.0F);
	}
}
