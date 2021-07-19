package com.lying.variousequipment.item.bauble;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.init.VEItems;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class ItemCostume extends ItemBaublePersistent
{
	public ItemCostume(Properties properties)
	{
		super(properties.maxStackSize(1));
	}
	
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack){ return true; }
	
	public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack){ return true; }
	
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
			int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		for(ItemStack component : getComponents(stack))
			if(component.getItem() instanceof ItemCosmetic)
			{
				ItemCosmetic cosmetic = (ItemCosmetic)component.getItem();
				if(canRender(identifier, index, living, component))
					cosmetic.render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, component);
			}
	}
	
	@OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags)
	{
		for(ItemStack component : getComponents(stack))
			tooltip.add(component.getDisplayName());
	}
	
	public static List<ItemStack> getComponents(ItemStack stack)
	{
		List<ItemStack> components = Lists.newArrayList(); 
		if(stack.hasTag())
		{
			ListNBT list = stack.getTag().getList("Items", 10);
			NonNullList<ItemStack> items = NonNullList.withSize(list.size(), ItemStack.EMPTY);
			ItemStackHelper.loadAllItems(stack.getTag(), items);
			items.forEach((item) -> { if(!item.isEmpty()) components.add(item); });
		}
		
		return components;
	}
	
	public static void addComponents(ItemStack stack, List<ItemStack> compsIn)
	{
		if(stack.getItem() != VEItems.COSTUME)
			return;
		
		List<ItemStack> existing = getComponents(stack);
		existing.addAll(compsIn);
		
		NonNullList<ItemStack> finalComp = NonNullList.from(ItemStack.EMPTY, existing.toArray(new ItemStack[0]));
		CompoundNBT stackData = stack.hasTag() ? stack.getTag() : new CompoundNBT();
		ItemStackHelper.saveAllItems(stackData, finalComp);
		stack.setTag(stackData);
	}
	
	// FIXME Prevent costume being left over when used in addition recipe
	
	@Override
	public boolean hasContainerItem(ItemStack stack){ return getComponents(stack).size() > 1; }
	
	@Override
	public ItemStack getContainerItem(ItemStack stack)
	{
		List<ItemStack> components = getComponents(stack);
		
		ItemStack clone = stack.copy();
		CompoundNBT stackData = stack.getTag();
		stackData.remove("Items");
		clone.setTag(stackData);
		
		components.remove(components.size() - 1);
		addComponents(clone, components);
		return clone;
	}
}
