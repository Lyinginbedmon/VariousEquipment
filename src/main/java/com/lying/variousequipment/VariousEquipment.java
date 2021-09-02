package com.lying.variousequipment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lying.variousequipment.client.renderer.EntityRenderRegistry;
import com.lying.variousequipment.config.ConfigVE;
import com.lying.variousequipment.data.VEDataGenerators;
import com.lying.variousequipment.init.VEEntities;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.init.VELootTables;
import com.lying.variousequipment.network.PacketHandler;
import com.lying.variousequipment.proxy.ClientProxy;
import com.lying.variousequipment.proxy.IProxy;
import com.lying.variousequipment.proxy.ServerProxy;
import com.lying.variousequipment.reference.Reference;
import com.lying.variousequipment.utility.bus.BusClient;
import com.lying.variousequipment.utility.bus.BusServer;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.ModInfo.MOD_ID)
public class VariousEquipment
{
	public static final Logger log = LogManager.getLogger(Reference.ModInfo.MOD_ID);
	
	public static IProxy proxy = new ServerProxy();
	
	@SuppressWarnings("deprecation")
	public VariousEquipment()
	{
		DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> proxy = new ClientProxy());
		proxy.registerHandlers();
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::doCommonSetup);
        bus.addListener(this::doClientSetup);
        bus.addListener(this::doLoadComplete);
        bus.addListener(VEDataGenerators::onGatherData);
        bus.addListener(VEEntities::registerAttributes);
        
        bus.addGenericListener(Item.class, VEItems::onItemsRegistry);
        bus.addGenericListener(IRecipeSerializer.class, VEItems::onRecipeSerializerRegistry);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigVE.server_spec);
        bus.addListener(this::onConfigEvent);
        
        VEEntities.registerEntityTypes(bus);
        
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(VELootTables::onLootLoadEvent);
	}
	
    private void doCommonSetup(final FMLCommonSetupEvent event)
    {
    	PacketHandler.init();
    	MinecraftForge.EVENT_BUS.register(BusServer.class);
    }
    
    private void doClientSetup(final FMLClientSetupEvent event)
    {
        EntityRenderRegistry.registerEntityRenderers();
        event.enqueueWork(VEItems::registerProperties);
        
        MinecraftForge.EVENT_BUS.register(BusClient.class);
    }
    
    private void onConfigEvent(final ModConfigEvent event)
    {
    	switch(event.getConfig().getType())
    	{
			case CLIENT:
				break;
			case SERVER:
				ConfigVE.updateCache();
				break;
			default:
				break;
    	}
    }
    
    private void doLoadComplete(final FMLLoadCompleteEvent event)
    {
    	proxy.onLoadComplete(event);
    }
    
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
    	
    }
}
