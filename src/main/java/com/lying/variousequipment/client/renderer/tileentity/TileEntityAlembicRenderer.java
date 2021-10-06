package com.lying.variousequipment.client.renderer.tileentity;

import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.lying.variousequipment.block.BlockAlembic;
import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.tileentity.TileEntityAlembic;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.util.math.vector.Vector3f;

public class TileEntityAlembicRenderer extends TileEntityRenderer<TileEntityAlembic>
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
	private static final float ITEM_SCALE = 0.4F;
	
	public TileEntityAlembicRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}
	
	public void render(TileEntityAlembic tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		// Render secondary model
		BlockState state = tileEntityIn.getBlockState();
		double itemBounceRate = 0D;
		if(tileEntityIn.isBubbling())
		{
			renderWater(tileEntityIn, (BlockAlembic)VEBlocks.ALEMBIC_ACTIVE, matrixStackIn, bufferIn, combinedLightIn);
			itemBounceRate = 1D;
		}
		else if(tileEntityIn.hasWater())
		{
			renderWater(tileEntityIn, (BlockAlembic)VEBlocks.ALEMBIC_WATER, matrixStackIn, bufferIn, combinedLightIn);
			itemBounceRate = 0.5D;
		}
		
		// Render contained items
		if(tileEntityIn.isEmpty()) return;
    	ItemRenderer renderItem = Minecraft.getInstance().getItemRenderer();
    	
    	matrixStackIn.push();
    		switch(state.get(BlockAlembic.FACING))
    		{
				case WEST:
					matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90F));
	    			matrixStackIn.translate(-1D, 0D, 0D);
					break;
				case SOUTH:
					matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180F));
	    			matrixStackIn.translate(-1D, 0D, -1D);
					break;
				case EAST:
					matrixStackIn.rotate(Vector3f.YP.rotationDegrees(270F));
	    			matrixStackIn.translate(0D, 0D, -1D);
				default:
					break;
    		}
    		
        	matrixStackIn.push();
				matrixStackIn.translate(0D, 0D, 0.44D);
	    		for(int slot = 0; slot < 5; slot++)
	    		{
	    			ItemStack stackInSlot = tileEntityIn.getStackInSlot(slot);
	    			if(stackInSlot.isEmpty()) continue;
	    			
	    			matrixStackIn.push();
		    			switch(TileEntityAlembic.EnumRegion.regionFromSlot(slot))
		    			{
							case FUEL:
				    			matrixStackIn.translate(0.3D, 0.2D, 0D);
								break;
							case OUTPUT:
				    			matrixStackIn.translate(0.92D, 0.2D, 0D);
								break;
							case WATER:
				    			matrixStackIn.translate(0.3D, 0.6D, 0D);
				    			if(!tileEntityIn.hasWater())
				    				matrixStackIn.translate(0D, -0.1D, 0D);
								matrixStackIn.rotate(Vector3f.YP.rotationDegrees(slot * 120F));
								if(itemBounceRate > 0D)
								{
									float time = (float)tileEntityIn.ticksLoaded() + partialTicks;
									matrixStackIn.translate(0D, 0.01D * Math.sin(time + slot * 20 * itemBounceRate), 0D);
								}
								break;
		    				
		    			}
		    			matrixStackIn.push();
			        		matrixStackIn.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
			    			renderItem.renderItem(stackInSlot, TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
		    			matrixStackIn.pop();
	    			matrixStackIn.pop();
	    		}
			matrixStackIn.pop();
		matrixStackIn.pop();
	}
	
	@SuppressWarnings("deprecation")
	private void renderWater(TileEntityAlembic tileEntityIn, BlockAlembic blockIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn)
	{
		BlockState state = tileEntityIn.getBlockState();
		BlockState stateVis = blockIn.getDefaultState()
				.with(BlockAlembic.FACING, state.get(BlockAlembic.FACING));
		
		BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
		ModelManager modelManager = blockRendererDispatcher.getBlockModelShapes().getModelManager();
		ModelResourceLocation visModel = new ModelResourceLocation(blockIn.getRegistryName().toString(), state.getValues().entrySet().stream().map(field_235890_a_).collect(Collectors.joining(",")));
		
		matrixStackIn.push();
			int colour = Minecraft.getInstance().getBlockColors().getColor(Blocks.WATER.getDefaultState(), tileEntityIn.getWorld(), tileEntityIn.getPos(), 0);
	        float r = (float)(colour >> 16 & 255) / 255.0F;
	        float g = (float)(colour >> 8 & 255) / 255.0F;
	        float b = (float)(colour & 255) / 255.0F;
			RenderSystem.enableBlend();
			RenderSystem.color4f(r, g, b, 0.8F);
			blockRendererDispatcher.getBlockModelRenderer().renderModelBrightnessColor(matrixStackIn.getLast(), bufferIn.getBuffer(Atlases.getCutoutBlockType()), stateVis, modelManager.getModel(visModel), r, g, b, combinedLightIn, OverlayTexture.NO_OVERLAY);
		matrixStackIn.pop();
		RenderSystem.disableBlend();
		RenderSystem.color4f(1F, 1F, 1F, 1F);
	}
	
	/** Returns the rotation of the arms at a given point in rotation */
	protected float getRotation(int ticksIn, float partialTicksIn)
	{
		return 40F * ((float)ticksIn + partialTicksIn);
	}
	
	/** Returns the rotation of displayed items at a given yaw value */
	protected float vialRotation(float yaw)
	{
		return (Math.min(180F, yaw) / 180F) * 90F;
	}
}
