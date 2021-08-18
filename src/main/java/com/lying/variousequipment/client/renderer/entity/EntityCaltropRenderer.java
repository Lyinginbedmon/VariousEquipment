package com.lying.variousequipment.client.renderer.entity;

import com.lying.variousequipment.entity.EntityCaltrop;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityCaltropRenderer extends AbstractEntityItemRenderer<EntityCaltrop>
{
	public EntityCaltropRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}
	
	public static class RenderFactory implements IRenderFactory<EntityCaltrop>
	{
		public EntityRenderer<? super EntityCaltrop> createRenderFor(EntityRendererManager manager) 
		{
			return new EntityCaltropRenderer(manager);
		}
	}
}
