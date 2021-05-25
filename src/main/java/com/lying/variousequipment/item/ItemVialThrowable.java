package com.lying.variousequipment.item;

import com.lying.variousequipment.entity.EntityTossedVial;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class ItemVialThrowable extends ItemVial
{
	public static final IDispenseItemBehavior DISPENSER_BEHAVIOUR = new DefaultDispenseItemBehavior()
    {
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
            return (new ProjectileDispenseBehavior()
            {
                protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
                {
                    return new EntityTossedVial(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
                }
                
                protected float getProjectileInaccuracy()
                {
                    return super.getProjectileInaccuracy() * 0.5F;
                }
                
                protected float getProjectileVelocity()
                {
                    return super.getProjectileVelocity() * 1.25F;
                }
            }).dispense(source, stack);
        }
    };
    
	public ItemVialThrowable(Item.Properties properties, int colorIn)
	{
		super(properties, colorIn);
        DispenserBlock.registerDispenseBehavior(this, DISPENSER_BEHAVIOUR);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(!worldIn.isRemote())
		{
			EntityTossedVial entity = new EntityTossedVial(worldIn, playerIn, itemstack);
			entity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20F, 0.5F, 1F);
			worldIn.addEntity(entity);
		}
		
		playerIn.addStat(Stats.ITEM_USED.get(this));
		if(!playerIn.abilities.isCreativeMode)
			itemstack.shrink(1);
		
		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
    
    /** Applies the appropriate effect for this vial from the given vial entity under the given collision event
     * @param entityIn The tossed vial entity at the root of the effect
     * @param worldIn The world the effect is happening in
     * @param resultIn The ray trace describing the collision event
     */
	public abstract void applyEffect(Entity entityIn, World worldIn, RayTraceResult resultIn);
	
	/** Determines the type of particle emitted on impact */
	public abstract boolean hasInstantEffect();
	
	/** Determines if the tossed entity should ever return an empty bottle */
	public abstract boolean canReturnBottle();
}
