package com.lying.variousequipment.item.bauble;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.world.savedata.TypesManager;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemScarabGolem extends ItemBauble
{
	private static final Predicate<Entity> VALID_TARGET = new Predicate<Entity>()
	{
		public boolean apply(Entity input)
		{
			return input instanceof LivingEntity ? TypesManager.get(input.getEntityWorld()).isGolem((LivingEntity)input) : false;
		}
	};
	
	public ItemScarabGolem(Properties properties)
	{
		super(properties.maxStackSize(1));
		
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, true, LivingDamageEvent.class, this::onLivingDamage);
	}
	
	public Rarity getRarity(ItemStack stack){ return Rarity.UNCOMMON; }
	
	public boolean hasDescription(){ return true; }
	
	public void addDescription(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags)
	{
		tooltip.add(new TranslationTextComponent("item."+Reference.ModInfo.MOD_ID+"."+getRegistryName().getPath()+".tooltip").modifyStyle((style) -> { return style.applyFormatting(TextFormatting.GREEN); }));
	}
	
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack){ return true; }
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return true; }
	
	@OnlyIn(Dist.CLIENT)
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		ICurio.RenderHelper.translateIfSneaking(matrixStackIn, living);
		ICurio.RenderHelper.rotateIfSneaking(matrixStackIn, living);
		matrixStackIn.push();
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180F));
			matrixStackIn.translate(0D, -0.2D, living.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty() ? 0.15D : 0.2D);
			matrixStackIn.push();
				matrixStackIn.scale(0.75F, 0.75F, 0.75F);
				ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
				itemRender.renderItem(stack, TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrixStackIn, renderTypeBuffer);
			matrixStackIn.pop();
		matrixStackIn.pop();
	}
	
	public boolean isEquipped(@Nullable LivingEntity entity)
	{
		return entity != null && CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
	}
	
	public void onLivingDamage(LivingDamageEvent event)
	{
		if(VALID_TARGET.apply(event.getEntity()))
		{
			DamageSource source = event.getSource();
			if(source.getTrueSource() != null && source.getTrueSource() instanceof LivingEntity && ((ItemScarabGolem)VEItems.SCARAB_GOLEM).isEquipped((LivingEntity)source.getTrueSource()))
				event.setAmount(event.getAmount() * 1.5F);
		}
	}
}
