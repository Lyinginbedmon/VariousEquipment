package com.lying.variousequipment.item.vial;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.lying.variousequipment.VariousEquipment;
import com.lying.variousoddities.config.ConfigVO;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class Vial
{
	public static final List<Vial.Builder> ALL_VIALS = Lists.newArrayList();
	public static final List<Vial.Builder> VIALS_THROWABLE = Lists.newArrayList();
	public static final List<Vial.Builder> VIALS_DRINKABLE = Lists.newArrayList();
	
	public static final Vial.Builder HOLY_WATER	= register(new VialHolyWater.Builder());
	public static final Vial.Builder ANTITOXIN	= register(new VialAntitoxin.Builder());
	public static final Vial.Builder ALCHEMIST_FIRE	= register(new VialAlchemistFire.Builder());
	public static final Vial.Builder TANGLEFOOT_BAG	= register(new VialTanglefoot.Builder());
	public static final Vial.Builder THUNDERSTONE	= register(new VialThunderstone.Builder());
	public static final Vial.Builder HEALER_BALM	= register(new VialHealerBalm.Builder());
	public static final Vial.Builder LAVA_STONE	= register(new VialLavaStone.Builder());
	
	private final VialType type;
	private final VialShape shape;
	private final int maxStackSize;
	private final int color;
	
	protected Vial(VialType typeIn, VialShape shapeIn, int stackSize, int colorIn)
	{
		this.type = typeIn;
		this.shape = shapeIn;
		this.maxStackSize = stackSize;
		this.color = colorIn;
	}
	
	protected Vial(VialType typeIn, int stackSize, int colorIn)
	{
		this(typeIn, VialShape.BOTTLE, stackSize, colorIn);
	}
	
	public static Vial.Builder register(Vial.Builder vialIn)
	{
		ALL_VIALS.add(vialIn);
		
		Vial vial = vialIn.create();
		if(vial.isThrowable())
			VIALS_THROWABLE.add(vialIn);
		
		if(vial.isDrinkable())
			VIALS_DRINKABLE.add(vialIn);
		
		return vialIn;
	}
	
	public abstract ResourceLocation getRegistryName();
	
	public final int getColor(){ return this.color; }
	
	public final int maxStackSize(){ return this.maxStackSize; }
	
	public boolean usesDefaultItem(){ return true; }
	
	public VialShape getShape(){ return this.shape; }
	
	public boolean isThrowable(){ return this.type == VialType.THROWABLE; }
	
	public boolean isDrinkable(){ return this.type == VialType.DRINKABLE; }
	
	public boolean canUse(PlayerEntity player, World world){ return true; }
	
	public abstract boolean canDropItem();
	
	public ItemStack getDroppedItem(ItemStack stack, Random rand, World world){ return rand.nextInt(7) == 0 ? new ItemStack(Items.GLASS_BOTTLE) : ItemStack.EMPTY; }
	
	public int getBurnTime(){ return -1; }
	
	/** Applies results of this vial being thrown and striking an object. */
	public boolean onSplash(Entity entityIn, World worldIn, RayTraceResult resultIn){ return true; }
	
	/** Applies results of this vial being consumed by an entity. */
	public void onDrink(ItemStack stack, World worldIn, LivingEntity entityLiving){ }
	
	/** Applies effects of this vial on a live thrown object */
	public void onTick(Entity entityIn, World worldIn){ }
	
	public static enum VialType
	{
		THROWABLE,
		DRINKABLE;
	}
	
	public static enum VialShape
	{
		BOTTLE(0F),
		BAG(1F),
		ROCK(2F);
		
		private final float modelNum;
		
		private VialShape(float modelIn)
		{
			this.modelNum = modelIn;
		}
		
		public float modelNumber(){ return this.modelNum; }
	}
	
	public static abstract class Builder extends ForgeRegistryEntry<Vial.Builder>
	{
		public Builder(@Nonnull ResourceLocation registryName){ setRegistryName(registryName); }
		
		public abstract Vial create();
	}
	
	public static void onRegisterOperations(RegistryEvent.Register<Builder> event)
	{
		IForgeRegistry<Builder> registry = event.getRegistry();
		ALL_VIALS.forEach((vial) -> { registry.register(vial); });
		
		VariousEquipment.log.info("Initialised "+registry.getEntries().size()+" alchemical vials");
		if(ConfigVO.GENERAL.verboseLogs())
			for(ResourceLocation name : registry.getKeys())
				VariousEquipment.log.info("#   "+name.toString());
	}
}
