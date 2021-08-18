package com.lying.variousequipment.client.renderer.entity;

import com.lying.variousequipment.entity.EntityTossedVial;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityVialRenderer extends AbstractEntityItemRenderer<EntityTossedVial>
{
	public EntityVialRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	public static class RenderFactory implements IRenderFactory<EntityTossedVial>
	{
		public EntityRenderer<? super EntityTossedVial> createRenderFor(EntityRendererManager manager) 
		{
			return new EntityVialRenderer(manager);
		}
	}
}
