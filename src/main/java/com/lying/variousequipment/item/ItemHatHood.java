package com.lying.variousequipment.item;

import javax.annotation.Nonnull;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemHatHood extends ItemHat
{
	public ItemHatHood(Item.Properties properties)
	{
		super("hat_hood", ArmorMaterial.LEATHER, properties);
	}
	
	@Nonnull
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		boolean up = stack.hasTag() && !stack.getTag().isEmpty() && stack.getTag().getBoolean("IsUp");
		return Reference.ModInfo.MOD_PREFIX + "textures/models/armor/hat_hood"+(up ? "_up" : "_down")+(type == null ? "" : "_"+type)+".png";
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean isUp = getIsUp(itemstack);
        
        if(playerIn.isSneaking())
        {
            setIsUp(itemstack, playerIn.hasItemInSlot(EquipmentSlotType.HEAD) ? !isUp : true);
        	return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        else
        {
        	setIsUp(itemstack, !isUp);
	        playerIn.setActiveHand(handIn);
	        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
        }
    }
    
    public static boolean getIsUp(ItemStack itemstack)
    {
        CompoundNBT compound = itemstack.hasTag() ? itemstack.getTag() : new CompoundNBT();
        return compound.contains("IsUp") ? compound.getBoolean("IsUp") : false;
    }
    
    public static ItemStack setIsUp(ItemStack itemstack, boolean isUp)
    {
    	CompoundNBT compound = itemstack.hasTag() ? itemstack.getTag() : new CompoundNBT();
        compound.putBoolean("IsUp", isUp);
        itemstack.setTag(compound);
        return itemstack;
    }
}
