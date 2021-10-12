package com.lying.variousequipment.client.renderer.entity;

import com.lying.variousequipment.client.model.entity.ModelScarecrow;
import com.lying.variousequipment.client.renderer.entity.layer.LayerScarecrowButtons;
import com.lying.variousequipment.entity.EntityScarecrow;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityScarecrowRenderer extends LivingRenderer<EntityScarecrow, ModelScarecrow>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/entity/scarecrow.png");
	public static final ResourceLocation TEXTURE_BURNT = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/entity/burnt_scarecrow.png");
	
	public EntityScarecrowRenderer(EntityRendererManager rendererManager)
	{
		super(rendererManager, new ModelScarecrow(1F), 0.3F);
		
		addLayer(new LayerScarecrowButtons(this));
	}
	
	public ResourceLocation getEntityTexture(EntityScarecrow entity)
	{
		return entity.isBurnt() ? TEXTURE_BURNT : TEXTURE;
	}
	
	protected boolean canRenderName(EntityScarecrow entity)
	{
		return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
	}
	
	public static class RenderFactory implements IRenderFactory<EntityScarecrow>
	{
		public EntityRenderer<? super EntityScarecrow> createRenderFor(EntityRendererManager manager) 
		{
			return new EntityScarecrowRenderer(manager);
		}
	}
}
