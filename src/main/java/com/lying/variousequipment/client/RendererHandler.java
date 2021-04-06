package com.lying.variousequipment.client;

import com.lying.variousequipment.VariousEquipment;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;

@OnlyIn(Dist.CLIENT)
public class RendererHandler
{
	private static boolean registered = false;
	
	public static void registerTileRenderers(ModelRegistryEvent event)
	{
//		if(ConfigVO.GENERAL.verboseLogs())
			VariousEquipment.log.info("Registering tile entity renderers");
		
		if(!registered)
			registered = true;
		
//		ClientRegistry.bindTileEntityRenderer(VOTileEntities.TABLE_DRAFTING, TileEntityDraftingTableRenderer::new);
	}
}
