package com.lying.variousequipment.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityVialRenderer extends ItemRenderer
{
	public EntityVialRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
	}
	
	public static class RenderFactory implements IRenderFactory<ItemEntity>
	{
		public EntityRenderer<? super ItemEntity> createRenderFor(EntityRendererManager manager) 
		{
			return new EntityVialRenderer(manager);
		}
	}
}
