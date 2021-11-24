package com.lying.variousequipment.item.bauble;

import java.util.EnumSet;

import com.lying.variousequipment.client.model.bauble.ModelLegsDragon;
import com.lying.variousequipment.client.model.bauble.ModelLegsKobold;
import com.lying.variousequipment.client.model.bauble.ModelLegsNaga;
import com.lying.variousequipment.client.model.bauble.ModelLegsSatyr;
import com.lying.variousequipment.client.model.bauble.ModelLegsSpider;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ItemLegs extends ItemTails implements ILimbCosmetic
{
	public ItemLegs(Properties properties)
	{
		super(properties);
	}
	
	public EnumSet<LimbType> getLimbTypes(ItemStack stackIn){ return LimbType.LEGS; }
	
	protected static abstract class ItemLegsOverlay extends ItemTailsOverlay implements ILimbCosmetic
	{
		public ItemLegsOverlay(Properties properties){ super(properties); }
		
		public EnumSet<LimbType> getLimbTypes(ItemStack stackIn){ return LimbType.LEGS; }
	}
	
	/*
	 * Simple replacements
	 * 	Dog legs
	 * 	Fox legs
	 * 	Naga tail
	 * 	Mermaid fin
	 * 
	 * Require height alteration
	 * 	Drider spider body
	 * 	Centaur horse body
	 * 
	 * 	Require rendering player skin
	 * 		Wheelchair
	 */
	
	public static class Satyr extends ItemLegsOverlay
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/legs_satyr.png");
		private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/legs_satyr_overlay.png");
		
		public Satyr(Properties properties){ super(properties); }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelLegsSatyr(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack) { return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getOverlayTexture(ItemStack stack){ return TEXTURE_OVERLAY; }
		
		public int getDefaultColor(){ return 5385497; }
		
		public boolean canRenderWithArmour(ItemStack stack, EquipmentSlotType typeIn)
		{
			return typeIn != EquipmentSlotType.LEGS;
		}
	}
	
	public static class Kobold extends ItemLegsOverlay
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/legs_kobold.png");
		private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/legs_kobold_overlay.png");
		
		public Kobold(Properties properties){ super(properties); }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelLegsKobold(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack) { return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getOverlayTexture(ItemStack stack){ return TEXTURE_OVERLAY; }
		
		public int getDefaultColor(){ return 15037452; }
		
		public boolean canRenderWithArmour(ItemStack stack, EquipmentSlotType typeIn)
		{
			return typeIn != EquipmentSlotType.LEGS;
		}
	}
	
	public static class Dragon extends ItemLegs
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/legs_dragon.png");
		
		public Dragon(Properties properties){ super(properties); }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelLegsDragon(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack) { return TEXTURE; }
		
		public int getDefaultColor(){ return 1908001; }
		
		public boolean canRenderWithArmour(ItemStack stack, EquipmentSlotType typeIn)
		{
			return typeIn != EquipmentSlotType.LEGS;
		}
	}
	
	public static class Naga extends ItemLegs
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/legs_naga.png");
		
		public Naga(Properties properties){ super(properties); }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelLegsNaga(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack) { return TEXTURE; }
		
		public boolean canRenderWithArmour(ItemStack stack, EquipmentSlotType typeIn){ return false; }
	}
	
	public static class Spider extends ItemLegs
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/spider/spider.png");
		private static final ResourceLocation TEXTURE2 = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/legs_spider.png");
		
		public Spider(Properties properties){ super(properties); }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelLegsSpider(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack) { return hasColor(stack) ? TEXTURE2 : TEXTURE; }
		
		public boolean canRenderWithArmour(ItemStack stack, EquipmentSlotType typeIn){ return false; }
	}
}
