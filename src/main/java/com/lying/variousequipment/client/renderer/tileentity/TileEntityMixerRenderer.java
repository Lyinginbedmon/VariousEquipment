package com.lying.variousequipment.client.renderer.tileentity;

import com.lying.variousequipment.tileentity.TileEntityMixer;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TileEntityMixerRenderer extends TileEntitySpinnyRenderer<TileEntityMixer>
{
	private static final float SPIN_LENGTH = 180F;
	
	public TileEntityMixerRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}
	
	protected float getRotation(int ticksIn, float partialTicksIn)
	{
		float time = (float)ticksIn + partialTicksIn;
		return (float)Math.sin(time / 5F) * SPIN_LENGTH;
	}
	
	protected float vialRotation(float yaw)
	{
		return (1F - (Math.abs(yaw) / 180F)) * 75F;
	}
}
