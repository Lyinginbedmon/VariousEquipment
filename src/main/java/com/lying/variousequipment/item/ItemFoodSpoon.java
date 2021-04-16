package com.lying.variousequipment.item;

import java.util.List;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemFoodSpoon extends Item
{
	public ItemFoodSpoon(Properties properties)
	{
		super(properties.food(Foods.MELON_SLICE).maxStackSize(1));
	}
	
	public boolean hasEffect(ItemStack stackIn){ return true; }
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		entityLiving.onFoodEaten(worldIn, stack.copy());
		return stack;
	}
	
	@OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags)
	{
		tooltip.add(new TranslationTextComponent("item."+Reference.ModInfo.MOD_ID+"."+getRegistryName().getPath()+".tooltip").modifyStyle((style) -> { return style.applyFormatting(TextFormatting.GRAY); }));
	}
}
