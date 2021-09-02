package com.lying.variousequipment.item;

import com.lying.variousequipment.entity.EntityNeedle;
import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.init.VOPotions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class ItemNeedleBone extends ItemNeedle
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/entity/projectiles/needle_bone.png");
	
	public ItemNeedleBone(Item.Properties builder)
	{
		super(builder.maxStackSize(16));
	}
	
	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		
		if(!worldIn.isRemote)
		{
			EntityNeedle needleEntity = new EntityNeedle(worldIn, playerIn);
			needleEntity.setItem(itemstack);
			needleEntity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 1.0F);
			worldIn.addEntity(needleEntity);
		}
		
		playerIn.addStat(Stats.ITEM_USED.get(this));
		if(!playerIn.abilities.isCreativeMode)
			itemstack.shrink(1);
	
		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
	
	public void affectEntity(LivingEntity entityIn, World worldIn, EntityRayTraceResult resultIn, boolean damageSuccess)
	{
		entityIn.addPotionEffect(new EffectInstance(VOPotions.NEEDLED, Reference.Values.TICKS_PER_SECOND * 30));
	}
	
	public ResourceLocation getEntityTexture(EntityNeedle entity){ return TEXTURE; }
}
