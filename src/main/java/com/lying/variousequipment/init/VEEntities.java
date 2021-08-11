package com.lying.variousequipment.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lying.variousequipment.entity.*;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.IPlacementPredicate;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = Reference.ModInfo.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class VEEntities
{
	private static final Map<EntityType<?>, EntityRegistry> TYPE_PROPERTIES_MAP = new HashMap<>();
	private static final Map<EntityType<?>, EntityRegistry> MISC_PROPERTIES_MAP = new HashMap<>();
	
    public static final List<EntityType<?>> ENTITIES = Lists.newArrayList();
	
	public static final EntityType<EntityTossedVial> TOSSED_VIAL	= register("tossed_vial", EntityTossedVial::new, EntityClassification.MISC, 0.3125F, 0.3125F, 4);
	public static final EntityType<EntityCaltrop> CALTROP			= register("caltrop", EntityCaltrop::new, EntityClassification.MISC, 0.3125F, 0.3125F, 4);
    
	private static <T extends Entity> EntityType<T> register(String name, EntityType.IFactory<T> factory, EntityClassification type, float width, float height, int trackingRange)
	{
        ResourceLocation location = new ResourceLocation(Reference.ModInfo.MOD_ID, name);
        EntityType<T> entity = EntityType.Builder.create(factory, type).size(width, height).setTrackingRange(trackingRange).setUpdateInterval(1).build(location.toString());
        entity.setRegistryName(location);
        
        MISC_PROPERTIES_MAP.put(entity, new EntityRegistry(PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, new IPlacementPredicate<T>()
        {
			public boolean test(EntityType<T> p_test_1_, IServerWorld p_test_2_, SpawnReason p_test_3_, BlockPos p_test_4_, Random p_test_5_)
			{
				return true;
			}}));
        return entity;
	}
    
    public static List<ResourceLocation> getEntityNameList()
    {
    	List<ResourceLocation> names = new ArrayList<>();
    	for(EntityType<?> type : ENTITIES)
    		names.add(type.getRegistryName());
    	return names;
    }
    
    public static EntityType<?> getEntityTypeByName(String nameIn)
    {
    	for(EntityType<?> type : ENTITIES)
    		if(type.getRegistryName().getPath().equalsIgnoreCase(nameIn))
    			return type;
    	return null;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
    {
    	for(EntityType entity : TYPE_PROPERTIES_MAP.keySet())
    	{
            Preconditions.checkNotNull(entity.getRegistryName(), "registryName");
            event.getRegistry().register(entity);
            
            EntityRegistry registry = TYPE_PROPERTIES_MAP.get(entity);
            EntitySpawnPlacementRegistry.register(entity, registry.placementType, registry.heightType, registry.placementPredicate);
    	}
    }
    
    private static class EntityRegistry
    {
    	@SuppressWarnings("rawtypes")
		IPlacementPredicate placementPredicate;
    	PlacementType placementType;
    	Heightmap.Type heightType;
    	
    	@SuppressWarnings("rawtypes")
		public EntityRegistry(PlacementType placeType1, Heightmap.Type placeType2, IPlacementPredicate predicate)
    	{
    		this.placementType = placeType1;
    		this.heightType = placeType2;
    		this.placementPredicate = predicate;
    	}
    }
}
