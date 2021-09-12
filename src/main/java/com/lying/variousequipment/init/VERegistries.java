package com.lying.variousequipment.init;

import com.lying.variousequipment.item.vial.Vial;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

public class VERegistries
{
	private static final ResourceLocation VIAL_REG = new ResourceLocation(Reference.ModInfo.MOD_ID, "vials");
	
	public static final IForgeRegistry<Vial.Builder> VIALS;
	
	static
	{
		VIALS = makeRegistry(VIAL_REG, Vial.Builder.class, Integer.MAX_VALUE >> 5);
	}
	
	public static void init(){ }
	
	private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> makeRegistry(ResourceLocation name, Class<T> type, int max)
	{
        return new RegistryBuilder<T>().setName(name).setType(type).setMaxID(max).create();
    }
}
