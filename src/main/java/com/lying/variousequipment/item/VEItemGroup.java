package com.lying.variousequipment.item;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public abstract class VEItemGroup extends ItemGroup
{
	public static final ItemGroup PROPS = new VEItemGroup("props")
	{
		public ItemStack createIcon(){ return Items.BOWL.getDefaultInstance(); }
	};
	public static final ItemGroup GEAR = new VEItemGroup("gear")
	{
		public ItemStack createIcon(){ return Items.IRON_AXE.getDefaultInstance(); }
	};
	
	public VEItemGroup(String labelIn)
	{
		super(Reference.ModInfo.MOD_ID+"."+labelIn);
	}
	
	public abstract ItemStack createIcon();
}
