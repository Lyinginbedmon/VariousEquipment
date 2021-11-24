package com.lying.variousequipment.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lying.variousequipment.item.bauble.ItemWheelchair;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

@OnlyIn(Dist.CLIENT)
@Mixin(BipedModel.class)
public class BipedModelMixin extends EntityModelMixin
{
	@Inject(method = "setLivingAnimations(Lnet/minecraft/entity/LivingEntity;FFF)V", at = @At("HEAD"))
	public void setLivingAnimations(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick, final CallbackInfo ci)
	{
		if(entityIn.getType() == EntityType.PLAYER)
		{
			PlayerEntity player = (PlayerEntity)entityIn;
			ICuriosHelper helper = CuriosApi.getCuriosHelper();
			if(helper.getCuriosHandler(player).isPresent())
			{
				ICuriosItemHandler handler = helper.getCuriosHandler(player).orElse(null);
				for(ICurioStacksHandler stacks : handler.getCurios().values())
					for(int i=0; i<stacks.getSlots(); i++)
						if(stacks.getRenders().get(i))
						{
							ItemStack stack = stacks.getStacks().getStackInSlot(i);
							if(!stack.isEmpty())
							{
								if(stack.getItem() instanceof ItemWheelchair)
									isSitting = true;
							}
						}
			}
		}
	}
}
