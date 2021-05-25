package com.lying.variousequipment.data;

import javax.annotation.Nullable;

import com.lying.variousequipment.init.VEItems;
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
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod.EventBusSubscriber(modid = Reference.ModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VEItemTags extends ItemTagsProvider
{
    public static final ITag.INamedTag<Item> BACK = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "back"));
    public static final ITag.INamedTag<Item> BELT = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "belt"));
    public static final ITag.INamedTag<Item> BRACELET = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "bracelet"));
    public static final ITag.INamedTag<Item> HANDS = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "hands"));
    public static final ITag.INamedTag<Item> HEAD = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "head"));
    public static final ITag.INamedTag<Item> NECKLACE = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "necklace"));
    
    public static final ITag.INamedTag<Item> HOLY_SYMBOL = ItemTags.createOptional(new ResourceLocation(Reference.ModInfo.MOD_ID, "holy_symbol"));
    
	public VEItemTags(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(dataGenerator, new VEBlockTags(dataGenerator, existingFileHelper), Reference.ModInfo.MOD_ID, existingFileHelper);
	}
	
	@Override
	public String getName()
	{
		return "Various Equipment item tags";
	}
	
	protected void registerTags()
	{
		getOrCreateBuilder(HEAD).add(
				VEItems.BLINDFOLD,
				VEItems.HORNS_DEER,
				VEItems.HORNS_HARTEBEEST,
				VEItems.HORNS_KIRIN,
				VEItems.HORNS_RAM
				);
		getOrCreateBuilder(NECKLACE).add(VEItems.SCARAB_GOLEM);
		
		getOrCreateBuilder(HOLY_SYMBOL).add(
				VEItems.SYMBOL_DIAMOND,
				VEItems.SYMBOL_GOLD,
				VEItems.SYMBOL_IRON,
				VEItems.SYMBOL_STONE,
				VEItems.SYMBOL_WOOD
				);
	}
	
	@SubscribeEvent
    public static void enqueueIMC(final InterModEnqueueEvent event)
    {
        for(SlotTypePreset type : new SlotTypePreset[]
        		{
        			SlotTypePreset.BACK,		// Cloak, cape, or mantle
        			SlotTypePreset.BELT,		// Belt around the waist
        			SlotTypePreset.BRACELET,	// Bracers or bracelets
        			SlotTypePreset.HANDS,		// Glove, pair of gloves, or pair of gauntlets
        			SlotTypePreset.HEAD,		// Pair of eye lenses or goggles
        			SlotTypePreset.NECKLACE		// Amulet, brooch, medallion, necklace, periapt, or scarab
        		})
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().size(2).build());
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
