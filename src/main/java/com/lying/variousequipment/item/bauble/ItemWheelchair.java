package com.lying.variousequipment.item.bauble;

import java.util.Optional;

import com.lying.variousequipment.client.model.bauble.ModelWheelchair;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.WoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

public class ItemWheelchair extends ItemCosmetic
{
	private final WoodType woodType;
	
	public ItemWheelchair(Properties properties, WoodType wheelIn)
	{
		super(properties);
		this.woodType = wheelIn;
	}
	
	public ResourceLocation getTexture(ItemStack stack)
	{
		return new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/"+stack.getItem().getRegistryName().getPath()+".png");
	}
	
	public static Item getWheelFromItem(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemWheelchair)
		{
			WoodType type = ((ItemWheelchair)stack.getItem()).woodType;
			if(type == WoodType.OAK)
				return VEItems.WHEEL_OAK;
			else if(type == WoodType.SPRUCE)
				return VEItems.WHEEL_SPRUCE;
			else if(type == WoodType.BIRCH)
				return VEItems.WHEEL_BIRCH;
			else if(type == WoodType.ACACIA)
				return VEItems.WHEEL_ACACIA;
			else if(type == WoodType.JUNGLE)
				return VEItems.WHEEL_JUNGLE;
			else if(type == WoodType.DARK_OAK)
				return VEItems.WHEEL_DARK_OAK;
			else if(type == WoodType.CRIMSON)
				return VEItems.WHEEL_CRIMSON;
			else if(type == WoodType.WARPED)
				return VEItems.WHEEL_WARPED;
		}
		return VEItems.WHEEL_OAK;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
			int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(living.isPassenger())
			return;
		
		ModelWheelchair model = new ModelWheelchair();
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, model.getRenderType(getTexture(stack)), false, stack.hasEffect());
		model.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		model.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
		
		boolean isSneaking = living.getPose() == Pose.CROUCHING;
		float spin = limbSwing * 45F;
		ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
		
		double wheelSpacing = 0.4D;
		ItemStack wheel = new ItemStack(getWheelFromItem(stack));
		// Right wheel
		matrixStackIn.push();
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180F));
			matrixStackIn.translate(-wheelSpacing, -1D, isSneaking ? -0.375D : 0D);
			matrixStackIn.push();
				matrixStackIn.scale(1F, 1F, 1F);
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F));
				matrixStackIn.rotate(Vector3f.XP.rotationDegrees(10F));
				matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(spin));
				itemRender.renderItem(wheel, TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrixStackIn, renderTypeBuffer);
			matrixStackIn.pop();
		matrixStackIn.pop();
		
		// Left wheel
		matrixStackIn.push();
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180F));
			matrixStackIn.translate(wheelSpacing, -1D, isSneaking ? -0.375D : 0D);
			matrixStackIn.push();
				matrixStackIn.scale(1F, 1F, 1F);
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F));
				matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-10F));
				matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(spin));
				itemRender.renderItem(wheel, TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrixStackIn, renderTypeBuffer);
			matrixStackIn.pop();
		matrixStackIn.pop();
	}
	
	public void onEquip(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		if(livingEntity.isPassenger())
			livingEntity.dismount();
	}
	
	public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		if(livingEntity.isPassenger() && livingEntity.getRNG().nextInt(20) == 0)
		{
			if(livingEntity.getType() == EntityType.PLAYER)
				((PlayerEntity)livingEntity).addItemStackToInventory(stack.copy());
			else
				livingEntity.entityDropItem(stack.copy());
			
			if(livingEntity.getType() == EntityType.PLAYER)
			{
				PlayerEntity player = (PlayerEntity)livingEntity;
				ICuriosHelper helper = CuriosApi.getCuriosHelper();
				if(helper.getCuriosHandler(player).isPresent())
				{
					ICuriosItemHandler handler = helper.getCuriosHandler(player).orElse(null);
					Optional<ICurioStacksHandler> stackHandler = handler.getStacksHandler(identifier);
					if(stackHandler.isPresent())
						stackHandler.get().getStacks().extractItem(index, stack.getCount(), false);
				}
			}
		}
	}
}
