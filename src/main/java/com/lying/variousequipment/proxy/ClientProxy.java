package com.lying.variousequipment.proxy;

import com.lying.variousequipment.client.RendererHandler;
import com.lying.variousequipment.client.renderer.ColorHandler;

import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy extends CommonProxy
{
	public void registerHandlers()
	{
        FMLJavaModLoadingContext.get().getModEventBus().addListener(RendererHandler::registerTileRenderers);
	}
	
	public void onLoadComplete(FMLLoadCompleteEvent event)
	{
		event.enqueueWork(() -> {ColorHandler.registerColorHandlers();});
	}
}
