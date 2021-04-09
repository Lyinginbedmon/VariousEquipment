package com.lying.variousequipment.item;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public abstract class VEItemGroup extends ItemGroup
{
	public static final ItemGroup PROPS = new VEItemGroup("props")
	{
		public ItemStack createIcon(){ return VEItems.HAT_ARCHFEY.getDefaultInstance(); }
	};
	public static final ItemGroup GEAR = new VEItemGroup("gear")
	{
		public ItemStack createIcon(){ return VEItems.HAT_WITCH.getDefaultInstance(); }
	};
	
	public VEItemGroup(String labelIn)
	{
		super(Reference.ModInfo.MOD_ID+"."+labelIn);
	}
	
	public abstract ItemStack createIcon();
}
