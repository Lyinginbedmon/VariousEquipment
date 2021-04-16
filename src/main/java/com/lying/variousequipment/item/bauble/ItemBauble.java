package com.lying.variousequipment.item.bauble;

import java.util.List;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class ItemBauble extends Item implements ICurioItem
{
	public ItemBauble(Properties properties)
	{
		super(properties);
	}
	
	public boolean hasDescription(){ return false; }
	
	@OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags)
	{
		if(hasDescription())
			addDescription(stack, world, tooltip, flags);
    }
	
	public void addDescription(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags)
	{
		tooltip.add(new TranslationTextComponent("item."+Reference.ModInfo.MOD_ID+"."+getRegistryName().getPath()+".tooltip").modifyStyle((style) -> { return style.applyFormatting(TextFormatting.GRAY); }));
	}
}
