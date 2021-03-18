package com.lying.variousequipment.proxy;

import com.lying.variousoddities.client.RendererHandler;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy extends CommonProxy
{
	public void registerHandlers()
	{
        FMLJavaModLoadingContext.get().getModEventBus().addListener(RendererHandler::registerTileRenderers);
	}
}
