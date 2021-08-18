package com.lying.variousequipment.client.renderer;

import com.lying.variousequipment.VariousEquipment;
import com.lying.variousequipment.client.renderer.entity.EntityCaltropRenderer;
import com.lying.variousequipment.client.renderer.entity.EntityVialRenderer;
import com.lying.variousequipment.init.VEEntities;
import com.lying.variousoddities.config.ConfigVO;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class EntityRenderRegistry
{
	@SuppressWarnings({ })
	public static void registerEntityRenderers()
	{
		if(ConfigVO.GENERAL.verboseLogs())
			VariousEquipment.log.info("Registering renderers");
		
		registerRenderer(VEEntities.TOSSED_VIAL, new EntityVialRenderer.RenderFactory());
		registerRenderer(VEEntities.CALTROP, new EntityCaltropRenderer.RenderFactory());
	}
	
	private static <T extends Entity> void registerRenderer(EntityType<T> entityClass, IRenderFactory<? super T> renderFactory)
	{
		if(renderFactory == null)
		{
			if(ConfigVO.GENERAL.verboseLogs())
				VariousEquipment.log.error("  -# Tried to register null renderer for "+entityClass.getRegistryName()+" #");
		}
		else
		{
			RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
			if(ConfigVO.GENERAL.verboseLogs())
				VariousEquipment.log.info("  -Registered "+entityClass.getRegistryName()+" renderer");
		}
	}
}
