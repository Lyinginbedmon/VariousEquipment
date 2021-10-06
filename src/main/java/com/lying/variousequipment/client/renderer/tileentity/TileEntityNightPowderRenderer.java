package com.lying.variousequipment.client.renderer.tileentity;

import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.lying.variousequipment.block.BlockNightPowder;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.tileentity.TileEntityNightPowder;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.potion.Effects;
import net.minecraft.state.Property;

public class TileEntityNightPowderRenderer extends TileEntityRenderer<TileEntityNightPowder>
{
	private static final Function<Entry<Property<?>, Comparable<?>>, String> field_235890_a_ = new Function<Entry<Property<?>, Comparable<?>>, String>()
	{
		public String apply(@Nullable Entry<Property<?>, Comparable<?>> p_apply_1_)
		{
			if (p_apply_1_ == null)
				return "<NULL>";
			else
			{
				Property<?> property = p_apply_1_.getKey();
				return property.getName() + "=" + this.func_235905_a_(property, p_apply_1_.getValue());
			}
		}
		
		@SuppressWarnings("unchecked")
		private <T extends Comparable<T>> String func_235905_a_(Property<T> p_235905_1_, Comparable<?> p_235905_2_)
		{
			return p_235905_1_.getName((T)p_235905_2_);
		}
	};
	
	public TileEntityNightPowderRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}
	
	@SuppressWarnings("deprecation")
	public void render(TileEntityNightPowder tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		if(Minecraft.getInstance().player == null || !Minecraft.getInstance().player.isPotionActive(Effects.NIGHT_VISION))
			return;
		
		BlockState state = tileEntityIn.getBlockState();
		BlockState stateVis = VEBlocks.NIGHT_POWDER_VIS.getDefaultState()
				.with(BlockNightPowder.NORTH, state.get(BlockNightPowder.NORTH))
				.with(BlockNightPowder.EAST, state.get(BlockNightPowder.EAST))
				.with(BlockNightPowder.SOUTH, state.get(BlockNightPowder.SOUTH))
				.with(BlockNightPowder.WEST, state.get(BlockNightPowder.WEST));
		
		BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
		ModelManager modelManager = blockRendererDispatcher.getBlockModelShapes().getModelManager();
		ModelResourceLocation visModel = new ModelResourceLocation(VEBlocks.NIGHT_POWDER_VIS.getRegistryName().toString(), state.getValues().entrySet().stream().map(field_235890_a_).collect(Collectors.joining(",")));
		
		matrixStackIn.push();
			blockRendererDispatcher.getBlockModelRenderer().renderModelBrightnessColor(matrixStackIn.getLast(), bufferIn.getBuffer(Atlases.getCutoutBlockType()), stateVis, modelManager.getModel(visModel), 1.0F, 1.0F, 1.0F, 15, OverlayTexture.NO_OVERLAY);
		matrixStackIn.pop();
	}
}
