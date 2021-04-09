package com.lying.variousequipment.item;

import com.lying.variousequipment.client.model.armor.ModelHatMask;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHatFeyMask extends ItemHat
{
	public ItemHatFeyMask(Properties properties)
	{
		super("hat_fey_mask", ArmorMaterial.LEATHER, properties);
	}
	
	public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity)
	{
		return true;
	}
	
	public int getColor(ItemStack stack)
	{
		return hasColor(stack) ? super.getColor(stack) : DyeColor.WHITE.getColorValue();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, M original)
	{
		return (M) new ModelHatMask(1.0F);
	}
}
