package com.lying.variousequipment.item;

import com.lying.variousequipment.init.VERegistries;
import com.lying.variousequipment.item.vial.Vial;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ItemVial extends Item
{
	public ItemVial(Item.Properties properties)
	{
		super(properties);
	}
	
	public int getColor(ItemStack stack){ return getVialFromItem(stack).getColor(); }
	
	public int getItemStackLimit(ItemStack stack){ return getVialFromItem(stack).maxStackSize(); }
	
	public int getBurnTime(ItemStack stack){ return getVialFromItem(stack).getBurnTime(); }
	
	public ITextComponent getDisplayName(ItemStack stack)
	{
		return new TranslationTextComponent("vial."+Reference.ModInfo.MOD_ID+"."+getVialFromItem(stack).getRegistryName().getPath());
	}
	
	public static Vial getVialFromItem(ItemStack stack)
	{
		CompoundNBT tag = stack.getTag();
		if(tag == null)
			return Vial.HOLY_WATER.create();
		else
		{
			String name = tag.getString("Vial");
			Vial.Builder builder = VERegistries.VIALS.getValue(new ResourceLocation(name));
			return builder == null ? Vial.HOLY_WATER.create() : builder.create();
		}
	}
	
	public static ItemStack addVialToItemStack(ItemStack itemIn, Vial.Builder vialIn)
	{
		ResourceLocation registryName = VERegistries.VIALS.getKey(vialIn);
		if(vialIn == null)
			itemIn.removeChildTag("Vial");
		else
			itemIn.getOrCreateTag().putString("Vial", registryName.toString());
		return itemIn;
	}
}
