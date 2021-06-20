package com.lying.variousequipment.item.bauble;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.IEventListenerItem;
import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.api.event.GatherAbilitiesEvent;
import com.lying.variousoddities.capabilities.LivingData;
import com.lying.variousoddities.species.abilities.AbilityBlind;
import com.lying.variousoddities.species.abilities.AbilityRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import top.theillusivec4.curios.api.CuriosApi;

public class ItemBlindfold extends ArmorItem implements ICurioAbilityItem, IEventListenerItem
{
	public static final UUID BLINDFOLD_UUID = UUID.fromString("4d75daeb-e3a2-4e1f-ab8a-82fd5f553ec2");
	
	@OnlyIn(Dist.CLIENT)
	private BipedModel<LivingEntity> model;
	
	public ItemBlindfold(Properties builderIn)
	{
		super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, builderIn);
	}
	
	public void addListeners(IEventBus bus)
	{
		bus.addListener(this::onAbilityGather);
	}
	
	public boolean shouldAddAbilitiesTo(LivingEntity entity)
	{
		ItemStack headStack = entity.getItemStackFromSlot(EquipmentSlotType.HEAD);
		boolean addBlindness = headStack != null && !headStack.isEmpty() && headStack.getItem() == VEItems.BLINDFOLD;
		if(!addBlindness)
		{
			Optional<ImmutableTriple<String, Integer, ItemStack>> blindfoldCurio = CuriosApi.getCuriosHelper().findEquippedCurio(VEItems.BLINDFOLD, entity);
			if(blindfoldCurio != null && blindfoldCurio.isPresent())
				addBlindness = true;
		}
		return addBlindness;
	}
	
	public void addAbilitiesTo(GatherAbilitiesEvent event)
	{
		event.addAbility(AbilityRegistry.getAbility(AbilityBlind.REGISTRY_NAME, new CompoundNBT()).setSourceId(BLINDFOLD_UUID));
	}
	
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(entityIn instanceof LivingEntity)
		{
			LivingEntity living = (LivingEntity)entityIn;
			if(living.hasItemInSlot(EquipmentSlotType.HEAD) && living.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == VEItems.BLINDFOLD)
			{
				if(!hasBlindAbility(living, false))
					LivingData.forEntity(living).getAbilities().markForRecache();
			}
			else if(hasBlindAbility(living, true) && !CuriosApi.getCuriosHelper().findEquippedCurio(this, living).isPresent())
				LivingData.forEntity(living).getAbilities().markForRecache();
		}
	}
	
	private boolean hasBlindAbility(LivingEntity living, boolean fromBlindfold)
	{
		return AbilityRegistry.hasAbility(living, AbilityBlind.REGISTRY_NAME) && (!fromBlindfold || AbilityRegistry.getAbilityByName(living, AbilityBlind.REGISTRY_NAME).getSourceId().equals(BLINDFOLD_UUID));
	}
	
	public boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity)
	{
		return true;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, M original)
	{
		return (M) new BipedModel<>(0.5F);
	}
	
	@Nonnull
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return Reference.ModInfo.MOD_PREFIX + "textures/models/armor/blindfold.png";
	}
	
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack){ return true; }
	
	@OnlyIn(Dist.CLIENT)
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(!(this.model instanceof BipedModel))
			this.model = new BipedModel<LivingEntity>(0.5F);
		
		ResourceLocation texture = new ResourceLocation(getArmorTexture(stack, living, getEquipmentSlot(), null));
		model.setLivingAnimations(living, limbSwing, limbSwingAmount, partialTicks);
		model.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		ModelRenderer head = model.getModelHead();
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(texture), false, stack.hasEffect());
		head.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
	}
}
