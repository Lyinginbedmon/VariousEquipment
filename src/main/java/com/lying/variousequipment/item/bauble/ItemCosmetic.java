package com.lying.variousequipment.item.bauble;

import com.lying.variousequipment.item.VEItemGroup;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public abstract class ItemCosmetic extends ItemBaublePersistent
{
	public ItemCosmetic(Properties properties)
	{
		super(properties.maxStackSize(1).group(VEItemGroup.PROPS));
	}
	
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack){ return true; }
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return !living.isInvisible(); }
	
	@OnlyIn(Dist.CLIENT)
	public abstract void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack);
}
