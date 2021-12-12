package com.lying.variousequipment.client;

import com.lying.variousequipment.VariousEquipment;
import com.lying.variousequipment.client.renderer.tileentity.*;
import com.lying.variousequipment.config.ConfigVE;
import com.lying.variousequipment.init.VETileEntities;
import com.lying.variousequipment.tileentity.TileEntityCentrifuge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
public class RendererHandler
{
	private static boolean registered = false;
	
	public static void registerTileRenderers(ModelRegistryEvent event)
	{
		if(ConfigVE.GENERAL.verboseLogs())
			VariousEquipment.log.info("Registering tile entity renderers");
		
		if(!registered)
			registered = true;
		
		ClientRegistry.bindTileEntityRenderer(VETileEntities.CENTRIFUGE, TileEntitySpinnyRenderer<TileEntityCentrifuge>::new);
		ClientRegistry.bindTileEntityRenderer(VETileEntities.MIXER, TileEntityMixerRenderer::new);
		ClientRegistry.bindTileEntityRenderer(VETileEntities.NIGHT_POWDER, TileEntityNightPowderRenderer::new);
		ClientRegistry.bindTileEntityRenderer(VETileEntities.ALEMBIC, TileEntityAlembicRenderer::new);
		ClientRegistry.bindTileEntityRenderer(VETileEntities.WHEELCHAIR, TileEntityWheelchairRenderer::new);
	}
}
