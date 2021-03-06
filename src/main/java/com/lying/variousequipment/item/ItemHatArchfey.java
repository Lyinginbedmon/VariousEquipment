package com.lying.variousequipment.item;

import com.lying.variousequipment.client.model.armor.ModelHatArchfey;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHatArchfey extends ItemHat
{
	public ItemHatArchfey(Properties properties)
	{
		super("hat_archfey", ArmorMaterial.GOLD, properties.isImmuneToFire().maxDamage(0));
	}
	
	public int getColor(ItemStack stack)
	{
		return hasColor(stack) ? super.getColor(stack) : DyeColor.RED.getColorValue();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, M original)
	{
		return (M) new ModelHatArchfey(1.0F);
	}
}
