package com.lying.variousequipment.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.lying.variousequipment.api.IShieldBreakItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.ForgeMod;

public class ItemGlaive extends SwordItem implements IShieldBreakItem
{
	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("323b49e5-abad-4130-ab27-cd31de1119c3");
	
	private final IItemTier tier;
	private final float attackDamage;
	private final float attackSpeed;
	
	public ItemGlaive(IItemTier tierIn, int attackDamageIn, float attackSpeedIn, Properties builderIn)
	{
		super(tierIn, attackDamageIn, attackSpeedIn, builderIn.group(VEItemGroup.GEAR));
		this.tier = tierIn;
		this.attackDamage = attackDamageIn;
		this.attackSpeed = attackSpeedIn;
	}
	
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
	{
		double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
		RayTraceResult result = player.pick(reach, 0F, true);
		return result == null ? false : result.getHitVec().distanceTo(player.getEyePosition(0F)) <= 3D;
	}
	
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack)
	{
		if(equipmentSlot == EquipmentSlotType.MAINHAND)
		{
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
				builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)((float)this.attackDamage + tier.getAttackDamage()), AttributeModifier.Operation.ADDITION));
				builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
				builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", 2D, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getAttributeModifiers(equipmentSlot);
	}
}
