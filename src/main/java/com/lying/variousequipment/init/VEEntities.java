package com.lying.variousequipment.init;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.entity.EntityCaltrop;
import com.lying.variousequipment.entity.EntityNeedle;
import com.lying.variousequipment.entity.EntityTossedVial;
import com.lying.variousequipment.entity.EntityWagon;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class VEEntities
{
	private static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.ModInfo.MOD_ID);
	
    public static final List<EntityType<?>> ENTITIES = Lists.newArrayList();
    
    public static final RegistryObject<EntityType<EntityTossedVial>> TOSSED_VIAL	= registerType("tossed_vial", EntityTossedVial::new, EntityClassification.MISC, 0.3125F, 0.3125F, 4);
    public static final RegistryObject<EntityType<EntityCaltrop>> CALTROP			= registerType("caltrop", EntityCaltrop::new, EntityClassification.MISC, 0.3125F, 0.3125F, 4);
    public static final RegistryObject<EntityType<EntityNeedle>> NEEDLE				= registerType("needle", EntityNeedle::new, EntityClassification.MISC, 0.3125F, 0.3125F, 4);
    
    public static final RegistryObject<EntityType<EntityWagon>> WAGON				= registerType("wagon", EntityWagon::new, EntityClassification.MISC, 3.5F, 0.75F, 16);
    
	private static <T extends Entity> RegistryObject<EntityType<T>> registerType(String name, EntityType.IFactory<T> factory, EntityClassification type, float width, float height, int trackingRange)
	{
//        MISC_PROPERTIES_MAP.put(entity, new EntityRegistry(PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, new IPlacementPredicate<T>()
//        {
//			public boolean test(EntityType<T> p_test_1_, IServerWorld p_test_2_, SpawnReason p_test_3_, BlockPos p_test_4_, Random p_test_5_)
//			{
//				return true;
//			}}));
        return ENTITY_TYPE.register(name, () -> 
	        {
	            return EntityType.Builder.create(factory, type).size(width, height).setTrackingRange(trackingRange).setUpdateInterval(1).build(name);
        	});
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
    
    public static void registerEntityTypes(IEventBus bus)
    {
    	ENTITY_TYPE.register(bus);
    }
    
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
    	event.put(WAGON.get(), EntityWagon.getAttributes().create());
    }
}
