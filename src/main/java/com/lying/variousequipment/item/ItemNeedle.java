package com.lying.variousequipment.item;

import java.util.Collection;

import com.lying.variousequipment.entity.EntityNeedle;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class ItemNeedle extends Item
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/entity/projectiles/needle_iron.png");
	
    public ItemNeedle(Properties properties)
    {
		super(properties);
	};
	
	public void addEffects(EntityNeedle needleEntity, PlayerEntity player)
	{
		ItemStack offhand = player.getHeldItemOffhand();
		if(offhand.getItem() == Items.POTION)
		{
			Collection<EffectInstance> collection = PotionUtils.getEffectsFromStack(offhand);
			if(!collection.isEmpty())
				for(EffectInstance effect : collection)
					needleEntity.addEffect(new EffectInstance(effect.getPotion(), Math.min(effect.getDuration(), Reference.Values.TICKS_PER_SECOND * 15), effect.getAmplifier()));
		}
	}
	
	public void affectEntity(LivingEntity entityIn, EntityNeedle projectile, World worldIn, EntityRayTraceResult resultIn, boolean damageSuccess)
	{
		
	}
	
	public ResourceLocation getEntityTexture(EntityNeedle entity){ return TEXTURE; }
}
