package com.lying.variousequipment.client.renderer;

import com.lying.variousequipment.VariousEquipment;
import com.lying.variousequipment.client.renderer.entity.EntityCaltropRenderer;
import com.lying.variousequipment.client.renderer.entity.EntityNeedleRenderer;
import com.lying.variousequipment.client.renderer.entity.EntityScarecrowRenderer;
import com.lying.variousequipment.client.renderer.entity.EntityVialRenderer;
import com.lying.variousequipment.client.renderer.entity.EntityWagonRenderer;
import com.lying.variousequipment.client.renderer.entity.layer.LayerPatronWitchHat;
import com.lying.variousequipment.config.ConfigVE;
import com.lying.variousequipment.init.VEEntities;
import com.lying.variousoddities.entity.wip.EntityPatronWitch;
import com.lying.variousoddities.init.VOEntities;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class EntityRenderRegistry
{
	public static void registerEntityRenderers()
	{
		if(ConfigVE.GENERAL.verboseLogs())
			VariousEquipment.log.info("Registering renderers");
		
		registerRenderer(VEEntities.TOSSED_VIAL.get(), new EntityVialRenderer.RenderFactory());
		registerRenderer(VEEntities.CALTROP.get(), new EntityCaltropRenderer.RenderFactory());
		registerRenderer(VEEntities.NEEDLE.get(), new EntityNeedleRenderer.RenderFactory());
		
		registerRenderer(VEEntities.WAGON.get(), new EntityWagonRenderer.RenderFactory());
		registerRenderer(VEEntities.SCARECROW.get(), new EntityScarecrowRenderer.RenderFactory());
	}
	
	private static <T extends Entity> void registerRenderer(EntityType<T> entityClass, IRenderFactory<? super T> renderFactory)
	{
		if(renderFactory == null)
		{
			if(ConfigVE.GENERAL.verboseLogs())
				VariousEquipment.log.error("  -# Tried to register null renderer for "+entityClass.getRegistryName()+" #");
		}
		else
		{
			RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
			if(ConfigVE.GENERAL.verboseLogs())
				VariousEquipment.log.info("  -Registered "+entityClass.getRegistryName()+" renderer");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void appendRenderers(EntityRendererManager renderManager)
	{
		if(ConfigVE.GENERAL.verboseLogs())
			VariousEquipment.log.info("Appending layer renderers");
		
		LivingRenderer witchRenderer = (LivingRenderer<EntityPatronWitch, BipedModel<EntityPatronWitch>>)renderManager.renderers.get(VOEntities.PATRON_WITCH);
		witchRenderer.addLayer(new LayerPatronWitchHat(witchRenderer));
		if(ConfigVE.GENERAL.verboseLogs())
			VariousEquipment.log.info("  -Registered patron witch hat layer");
	}
}
