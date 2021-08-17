package com.lying.variousequipment.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ForgeMod;

public class ItemDagger extends SwordItem 
{
	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("323b49e5-abad-4130-ab27-cd31de1119c3");
	
	private final IItemTier tier;
	private final float attackDamage;
	private final float attackSpeed;
	
	public ItemDagger(IItemTier tierIn, int attackDamageIn, float attackSpeedIn, Properties builderIn)
	{
		super(tierIn, attackDamageIn, attackSpeedIn, builderIn.group(VEItemGroup.GEAR));
		this.tier = tierIn;
		this.attackDamage = attackDamageIn;
		this.attackSpeed = attackSpeedIn;
	}
	
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack)
	{
		if(equipmentSlot == EquipmentSlotType.MAINHAND)
		{
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
				builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)((float)this.attackDamage + tier.getAttackDamage()), AttributeModifier.Operation.ADDITION));
				builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
				builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", -2D, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getAttributeModifiers(equipmentSlot);
	}
}
