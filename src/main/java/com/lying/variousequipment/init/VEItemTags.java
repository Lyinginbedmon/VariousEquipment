package com.lying.variousequipment.init;

import javax.annotation.Nullable;

import com.lying.variousequipment.reference.Reference;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod.EventBusSubscriber(modid = Reference.ModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VEItemTags extends ItemTagsProvider
{
    public static final ITag.INamedTag<Item> NECKLACE = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "necklace"));
    public static final ITag.INamedTag<Item> HEAD = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "head"));
    
	public VEItemTags(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(dataGenerator, new VEBlockTags(dataGenerator, existingFileHelper), Reference.ModInfo.MOD_ID, existingFileHelper);
	}
	
	protected void registerTags()
	{
		getOrCreateBuilder(NECKLACE).add(VEItems.SCARAB_GOLEM);
	}
	
	@SubscribeEvent
    public static void enqueueIMC(final InterModEnqueueEvent event)
    {
        for(SlotTypePreset type : new SlotTypePreset[]{SlotTypePreset.NECKLACE, SlotTypePreset.HEAD})
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
    }
    
	@SubscribeEvent
	public static void onGatherDataEvent(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if(event.includeServer())
		{
			generator.addProvider(new VEItemTags(generator, existingFileHelper));
		}
	}
	
	private static class VEBlockTags extends BlockTagsProvider
	{

		public VEBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper)
		{
			super(generatorIn, Reference.ModInfo.MOD_ID, existingFileHelper);
		}
		
		protected void registerTags(){ }
	}
}
