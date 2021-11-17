package com.lying.variousequipment.item.bauble;

import java.util.EnumSet;

import com.lying.variousequipment.client.model.bauble.ModelLegsDragon;
import com.lying.variousequipment.client.model.bauble.ModelLegsKobold;
import com.lying.variousequipment.client.model.bauble.ModelLegsNaga;
import com.lying.variousequipment.client.model.bauble.ModelLegsPeg;
import com.lying.variousequipment.client.model.bauble.ModelLegsPeg1;
import com.lying.variousequipment.client.model.bauble.ModelLegsPeg2;
import com.lying.variousequipment.client.model.bauble.ModelLegsSatyr;
import com.lying.variousequipment.client.model.bauble.ModelLegsSpider;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;

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
	
	public static class Peg extends ItemLegs
	{
		private final int defaultColor;
		private Object modelLeg2 = null;
		
		public Peg(int colourIn, Properties properties)
		{
			super(properties);
			this.defaultColor = colourIn;
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel() { return new ModelLegsPeg1(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack) { return new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/"+getRegistryName().getPath()+".png"); }
		
		public int getDefaultColor(){ return this.defaultColor; }
		
		public EnumSet<LimbType> getLimbTypes(ItemStack stackIn){ return EnumSet.of(getLimbsFromStack(stackIn)); }
		
		public boolean canRenderWithArmour(ItemStack stack, EquipmentSlotType typeIn){ return typeIn != EquipmentSlotType.FEET; }
		
		@OnlyIn(Dist.CLIENT)
		@SuppressWarnings({ "unchecked" })
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(model == null)
				model = getModel();
			if(modelLeg2 == null)
				modelLeg2 = new ModelLegsPeg2();
			if(!(living instanceof AbstractClientPlayerEntity))
				return;
			
			AbstractClientPlayerEntity player = (AbstractClientPlayerEntity)living;
			((ModelLegsPeg)model).setLeg(getLegFromStack(stack));
			
			((ModelLegsPeg)modelLeg2).setLeg(getLegFromStack(stack));
			((ModelLegsPeg2)modelLeg2).bipedRightLegwear.showModel = player.isWearing(PlayerModelPart.RIGHT_PANTS_LEG);
			((ModelLegsPeg2)modelLeg2).bipedLeftLegwear.showModel = player.isWearing(PlayerModelPart.LEFT_PANTS_LEG);
			
			BipedModel<LivingEntity> legModel = (BipedModel<LivingEntity>)modelLeg2;
			ResourceLocation skin = player.getLocationSkin();
			legModel.setRotationAngles(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			ICurio.RenderHelper.followBodyRotations(player, legModel);
			boolean isVisible = !player.isInvisible();
			boolean invisibleToClient = !isVisible && !player.isInvisibleToPlayer(Minecraft.getInstance().player);
			boolean isGlowing = Minecraft.getInstance().isEntityGlowing(player);
			matrixStackIn.push();
				RenderType renderType = getRenderType(player, skin, legModel, isVisible, invisibleToClient, isGlowing);
				IVertexBuilder vertexBuilder = renderTypeBuffer.getBuffer(renderType);
				legModel.render(matrixStackIn, vertexBuilder, 15728640, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, invisibleToClient ? 0.15F : 1F);
			matrixStackIn.pop();
			
			super.render(identifier, index, matrixStackIn, renderTypeBuffer, light, player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
		}
		
		@OnlyIn(Dist.CLIENT)
		private RenderType getRenderType(LivingEntity living, ResourceLocation texture, BipedModel<LivingEntity> model, boolean visible, boolean invisible, boolean glowing)
		{
			if(invisible)
				return RenderType.getItemEntityTranslucentCull(texture);
			else if(visible)
				return model.getRenderType(texture);
			else
				return glowing ? RenderType.getOutline(texture) : null;
		}
		
		public static Leg getLegFromStack(ItemStack stack)
		{
			CompoundNBT stackData = stack.getOrCreateTag();
			if(stackData.contains("Leg", 8))
				return Leg.fromString(stackData.getString("Leg"));
			setLeg(stack, Leg.RIGHT);
			return Leg.RIGHT;
		}
		
		public ItemStack getDefaultInstance()
		{
			return setLeg(super.getDefaultInstance(), Leg.RIGHT);
		}
		
		public ITextComponent getDisplayName(ItemStack stack)
		{
			return new TranslationTextComponent(getTranslationKey(), getLegFromStack(stack).localized());
		}
		
		public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
		{
			if(isInGroup(group))
				for(Leg leg : Leg.values())
					items.add(setLeg(new ItemStack(this), leg));
		}
		
		private static LimbType getLimbsFromStack(ItemStack stack)
		{
			return getLegFromStack(stack) == Leg.LEFT ? LimbType.LEG_LEFT : LimbType.LEG_RIGHT;
		}
		
		public static ItemStack setLeg(ItemStack stack, Leg leg)
		{
			CompoundNBT stackData = stack.getOrCreateTag();
			stackData.putString("Leg", leg.getString());
			stack.setTag(stackData);
			return stack;
		}
		
		public static enum Leg implements IStringSerializable
		{
			LEFT,
			RIGHT;
			
			public String getString(){ return name().toLowerCase(); }
			
			public static Leg fromString(String nameIn)
			{
				return nameIn.equalsIgnoreCase(LEFT.getString()) ? LEFT : RIGHT;
			}
			
			public ITextComponent localized(){ return new TranslationTextComponent("enum.vareqp.leg."+getString()); }
		}
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
