package com.lying.variousequipment.item.vial;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class VialAntitoxin extends Vial
{
	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Reference.ModInfo.MOD_ID, "antitoxin");
	
	public VialAntitoxin()
	{
		super(VialType.DRINKABLE, 8, 7745999);
	}
	
	public ResourceLocation getRegistryName(){ return REGISTRY_NAME; }
	
	public boolean canReturnBottle(){ return true; }
	
	public void onDrink(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
    	entityLiving.removeActivePotionEffect(Effects.POISON);
    	entityLiving.removeActivePotionEffect(Effects.WITHER);
	}
	
	public static class Builder extends Vial.Builder
	{
		public Builder(){ super(REGISTRY_NAME); }
		
		public Vial create()
		{
			return new VialAntitoxin();
		}
	}
}
