package com.lying.variousequipment.entity;

import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.ItemVialThrowable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityTossedVial extends ProjectileItemEntity implements IRendersAsItem
{
	public EntityTossedVial(EntityType<? extends EntityTossedVial> typeIn, World worldIn)
	{
		super(VEEntities.TOSSED_VIAL, worldIn);
	}
	
    public EntityTossedVial(World worldIn, LivingEntity throwerIn, ItemStack vialStackIn)
    {
        super(VEEntities.TOSSED_VIAL, throwerIn, worldIn);
        setItem(vialStackIn);
    }
    
	public EntityTossedVial(World worldIn, double posX, double posY, double posZ, ItemStack stack)
	{
		super(VEEntities.TOSSED_VIAL, posX, posY, posZ, worldIn);
		setItem(stack);
	}
	
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	protected Item getDefaultItem(){ return VEItems.HOLY_WATER; }
	
	protected float getGravityVelocity(){ return 0.05F; }
	
	protected void onImpact(RayTraceResult result)
	{
		super.onImpact(result);
		if(!this.getEntityWorld().isRemote())
		{
			if(getItem().getItem() instanceof ItemVialThrowable)
			{
				ItemVialThrowable vial = (ItemVialThrowable)getItem().getItem();
				vial.applyEffect(this, getEntityWorld(), result);
				this.getEntityWorld().playEvent(2002, this.getPosition(), vial.getColor());
				
				if(vial.canReturnBottle() && this.rand.nextInt(7) == 0)
					this.entityDropItem(new ItemStack(Items.GLASS_BOTTLE));
			}
			this.remove();
		}
	}
}
