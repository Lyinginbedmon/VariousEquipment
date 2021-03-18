package com.lying.variousequipment.item;

import javax.annotation.Nonnull;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;

public class ItemHat extends ArmorItem
{
	private final String name;
	
	public ItemHat(String par1String, ArmorMaterial material, Properties properties)
	{
		super(material, EquipmentSlotType.HEAD, properties.maxStackSize(1));
		this.name = par1String;
	}
	
	@Nonnull
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return Reference.ModInfo.MOD_PREFIX + "textures/models/armor/"+this.name+(type == null ? "" : "_"+type)+".png";
	}
}
