package com.lying.variousequipment.item;

import java.util.List;

import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.init.VOPotions;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemSalveStone extends Item
{
	public ItemSalveStone(Properties properties)
	{
		super(properties.maxStackSize(16));
	}
	
	public UseAction getUseAction(ItemStack stack){ return UseAction.EAT; }
	
	public int getUseDuration(ItemStack stack){ return Reference.Values.TICKS_PER_SECOND * 4; }
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
	    ItemStack heldStack = playerIn.getHeldItem(handIn);
	    if(isPetrified(playerIn))
	    {
	       playerIn.setActiveHand(handIn);
	       return ActionResult.resultConsume(heldStack);
	    }
	    return ActionResult.resultFail(heldStack);
	}
	
	public boolean isPetrified(LivingEntity living)
	{
		return living.isPotionActive(VOPotions.PETRIFYING) || living.isPotionActive(VOPotions.PETRIFIED);
	}
	
	public void cureTarget(LivingEntity target)
	{
		target.removePotionEffect(VOPotions.PETRIFYING);
		target.removePotionEffect(VOPotions.PETRIFIED);
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		cureTarget(entityLiving);
		if(entityLiving.getType() == EntityType.PLAYER && ((PlayerEntity)entityLiving).isCreative())
			;
		else
			stack.shrink(1);
		return stack;
	}
	
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
	{
		if(playerIn.getEntityWorld().isRemote)
			return ActionResultType.PASS;
		
		if(isPetrified(target))
		{
			cureTarget(target);
			if(!playerIn.isCreative());
				stack.shrink(1);
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}
	
	@OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags)
	{
		tooltip.add(new TranslationTextComponent("item."+Reference.ModInfo.MOD_ID+"."+getRegistryName().getPath()+".tooltip").modifyStyle((style) -> { return style.applyFormatting(TextFormatting.GRAY); }));
	}
}
