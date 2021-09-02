package com.lying.variousequipment.proxy;

import com.lying.variousequipment.client.RendererHandler;
import com.lying.variousequipment.client.renderer.ColorHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;

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
	
	public PlayerEntity getPlayerEntity(NetworkEvent.Context ctx)
	{
		return ctx.getDirection().getReceptionSide().isServer() ? super.getPlayerEntity(ctx) : Minecraft.getInstance().player;
	}
}
