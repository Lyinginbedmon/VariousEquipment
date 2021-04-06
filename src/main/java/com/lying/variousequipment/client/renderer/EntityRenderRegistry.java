package com.lying.variousequipment.client.renderer;

import com.lying.variousequipment.VariousEquipment;

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
//		if(ConfigVO.GENERAL.verboseLogs())
			VariousEquipment.log.info("Registering renderers");
		
//		registerRenderer(VOEntities.SPELL, new EntitySpellRenderer.RenderFactory());
	}
	
	@SuppressWarnings("unused")
	private static <T extends Entity> void registerRenderer(EntityType<T> entityClass, IRenderFactory<? super T> renderFactory)
	{
		if(renderFactory == null)
		{
//			if(ConfigVO.GENERAL.verboseLogs())
				VariousEquipment.log.error("  -# Tried to register null renderer for "+entityClass.getRegistryName()+" #");
		}
		else
		{
			RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
//			if(ConfigVO.GENERAL.verboseLogs())
				VariousEquipment.log.info("  -Registered "+entityClass.getRegistryName()+" renderer");
		}
	}
}
