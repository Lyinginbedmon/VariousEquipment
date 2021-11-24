package com.lying.variousequipment.item.bauble;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.lying.variousequipment.client.model.bauble.ModelLegsPeg;
import com.lying.variousequipment.client.model.bauble.ModelLegsPeg1;
import com.lying.variousequipment.client.model.bauble.ModelLegsPeg2;
import com.lying.variousequipment.item.VEItemGroup;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
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

public class ItemPegLeg extends ItemCosmetic implements ILimbCosmetic
{
	@Nullable
	protected Object model = null;
	@Nullable
	protected Object modelLeg2 = null;
	
	public ItemPegLeg(Properties properties)
	{
		super(properties.maxStackSize(1).maxDamage(0).group(VEItemGroup.PROPS));
	}
	
	@OnlyIn(Dist.CLIENT)
	public BipedModel<LivingEntity> getModel() { return new ModelLegsPeg1(); }
	
	@OnlyIn(Dist.CLIENT)
	public ResourceLocation getTexture(ItemStack stack) { return new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/"+getRegistryName().getPath()+".png"); }
	
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
		boolean isVisible = !player.isInvisible();
		boolean invisibleToClient = !isVisible && !player.isInvisibleToPlayer(Minecraft.getInstance().player);
		boolean isGlowing = Minecraft.getInstance().isEntityGlowing(player);
		
		((ModelLegsPeg)model).setLeg(getLegFromStack(stack));
		
		((ModelLegsPeg)modelLeg2).setLeg(getLegFromStack(stack));
		((ModelLegsPeg2)modelLeg2).bipedRightLegwear.showModel = player.isWearing(PlayerModelPart.RIGHT_PANTS_LEG);
		((ModelLegsPeg2)modelLeg2).bipedLeftLegwear.showModel = player.isWearing(PlayerModelPart.LEFT_PANTS_LEG);

		BipedModel<LivingEntity> pegModel = (BipedModel<LivingEntity>)model;
		BipedModel<LivingEntity> legModel = (BipedModel<LivingEntity>)modelLeg2;
		
		ResourceLocation skin = player.getLocationSkin();
		legModel.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
		legModel.setRotationAngles(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		ICurio.RenderHelper.followBodyRotations(player, legModel);
		matrixStackIn.push();
			RenderType renderType = getRenderType(player, skin, legModel, isVisible, invisibleToClient, isGlowing);
			IVertexBuilder vertexBuilder = renderTypeBuffer.getBuffer(renderType);
			legModel.render(matrixStackIn, vertexBuilder, 15728640, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, invisibleToClient ? 0.15F : 1F);
		matrixStackIn.pop();
		
		ResourceLocation texture = getTexture(stack);
		pegModel.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
		pegModel.setRotationAngles(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		ICurio.RenderHelper.followBodyRotations(player, pegModel);
		matrixStackIn.push();
			renderType = getRenderType(player, texture, pegModel, isVisible, invisibleToClient, isGlowing);
			vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, pegModel.getRenderType(getTexture(stack)), false, stack.hasEffect());
			pegModel.render(matrixStackIn, vertexBuilder, 15728640, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, invisibleToClient ? 0.15F : 1F);
		matrixStackIn.pop();
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
