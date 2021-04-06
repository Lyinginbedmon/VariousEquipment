package com.lying.variousequipment.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public interface IProxy 
{
	public default void registerHandlers(){ }
	
	public default void onLoadComplete(FMLLoadCompleteEvent event){ }
}