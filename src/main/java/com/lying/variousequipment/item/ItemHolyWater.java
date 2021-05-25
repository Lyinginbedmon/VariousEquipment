package com.lying.variousequipment.item;

import java.util.List;

import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.species.types.EnumCreatureType;
import com.lying.variousoddities.species.types.Types;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemHolyWater extends ItemVialThrowable
{
	public ItemHolyWater(Properties builder)
	{
		super(builder.maxStackSize(8), 3694022);
	}
	
	public void applyEffect(Entity entityIn, World worldIn, RayTraceResult resultIn)
	{
		AxisAlignedBB boundingBox = entityIn.getBoundingBox().grow(4, 2, 4);
		List<LivingEntity> affected = worldIn.getEntitiesWithinAABB(LivingEntity.class, boundingBox);
		if(!affected.isEmpty())
			affected.forEach((living) -> 
				{
					Types types = EnumCreatureType.getTypes(living);
					if(types.isUndead() || types.isEvil())
					{
						if(types.isUndead())
							living.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 0, false, false));
						else
							living.addPotionEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 0, false, false));
						living.addPotionEffect(new EffectInstance(Effects.WEAKNESS, Reference.Values.TICKS_PER_SECOND * 10, 0));
					}
					else if(types.isHoly())
					{
						living.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, 0, false, false));
						living.addPotionEffect(new EffectInstance(Effects.STRENGTH, Reference.Values.TICKS_PER_SECOND * 10, 0));
					}
				});
	}
	
	public boolean hasInstantEffect(){ return true; }
	
	public boolean canReturnBottle(){ return true; }
}
