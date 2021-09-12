package com.lying.variousequipment.init;

import com.lying.variousequipment.item.vial.Vial;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VERegistryHandler
{
	public VERegistryHandler(){ }
	
	@SubscribeEvent
	public void initRegistries(RegistryEvent.NewRegistry event)
	{
		VERegistries.init();
	}
	
	@SubscribeEvent
    public void onRegisterVials(RegistryEvent.Register<Vial.Builder> event)
    {
		Vial.onRegisterOperations(event);
    }
}
