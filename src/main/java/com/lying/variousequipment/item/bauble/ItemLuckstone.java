package com.lying.variousequipment.item.bauble;

import java.util.List;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemLuckstone extends ItemBauble
{
	public ItemLuckstone(Properties properties)
	{
		super(properties);
	}
	
	public int getFortuneBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index)
	{
        return 1;
    }
	
	public int getLootingBonus(String identifier, LivingEntity livingEntity, ItemStack curio, int index)
	{
        return getFortuneBonus(identifier, livingEntity, curio, index);
    }
	
	public Rarity getRarity(ItemStack stack){ return Rarity.RARE; }
	
	public boolean hasDescription(){ return true; }
	
	public void addDescription(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags)
	{
		tooltip.add(new TranslationTextComponent("item."+Reference.ModInfo.MOD_ID+"."+getRegistryName().getPath()+".tooltip").modifyStyle((style) -> { return style.applyFormatting(TextFormatting.GREEN); }));
	}
}
