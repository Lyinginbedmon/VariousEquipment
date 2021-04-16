package com.lying.variousequipment.data;

import com.lying.variousequipment.data.recipes.VERecipeProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class VEDataGenerators
{
	public static void onGatherData(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if(event.includeServer())
		{
			generator.addProvider(new VEItemTags(generator, existingFileHelper));
			generator.addProvider(new VERecipeProvider(generator));
		}
		if(event.includeClient())
		{
			;
		}
	}
}
