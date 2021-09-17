package com.lying.variousequipment.init;

import com.lying.variousequipment.reference.Reference;
import com.lying.variousequipment.tileentity.*;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = Reference.ModInfo.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class VETileEntities
{
	public static final TileEntityType<TileEntityCentrifuge> CENTRIFUGE	= TileEntityType.Builder.create(TileEntityCentrifuge::new, VEBlocks.CENTRIFUGE).build(null);
	public static final TileEntityType<TileEntityMixer> MIXER			= TileEntityType.Builder.create(TileEntityMixer::new, VEBlocks.MIXER).build(null);
	
    @SubscribeEvent
	public static void registerTiles(final RegistryEvent.Register<TileEntityType<?>> tileRegistryevent)
	{
    	IForgeRegistry<TileEntityType<?>> registry = tileRegistryevent.getRegistry();
		register(registry, new ResourceLocation(Reference.ModInfo.MOD_ID, "centrifuge"), CENTRIFUGE);
		register(registry, new ResourceLocation(Reference.ModInfo.MOD_ID, "mixer"), MIXER);
	}
    
    private static void register(IForgeRegistry<TileEntityType<?>> registry, ResourceLocation name, IForgeRegistryEntry<TileEntityType<?>> tile)
    {
    	registry.register(tile.setRegistryName(name));
    }
}
