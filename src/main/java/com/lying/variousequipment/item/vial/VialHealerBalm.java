package com.lying.variousequipment.item.vial;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class VialHealerBalm extends Vial
{
	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Reference.ModInfo.MOD_ID, "healer_balm");
	
	public VialHealerBalm()
	{
		super(VialType.DRINKABLE, VialShape.BAG, 4, 15193523);
	}
	
	public ResourceLocation getRegistryName(){ return REGISTRY_NAME; }
	
	public boolean canDropItem(){ return true; }
	
	public boolean canUse(PlayerEntity player, World world){ return player.getHealth() < player.getMaxHealth(); }
	
	public void onDrink(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		if(entityLiving.getHealth() < entityLiving.getMaxHealth())
		{
			entityLiving.heal(2F);
			for(EffectInstance effect : entityLiving.getActivePotionEffects())
				if(effect.getPotion().getEffectType() == EffectType.HARMFUL && entityLiving.getRNG().nextDouble() < 0.13D)
				{
					entityLiving.removeActivePotionEffect(effect.getPotion());
					return;
				}
		}
	}
	
	public static class Builder extends Vial.Builder
	{
		public Builder(){ super(REGISTRY_NAME); }
		
		public Vial create()
		{
			return new VialHealerBalm();
		}
	}
}
