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
    public static final ITag.INamedTag<Item> COSMETIC = ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, "cosmetic"));
    
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
				VEItems.BLINDFOLD_FAKE,
				VEItems.EARS_WOLF,
				VEItems.EARS_CAT,
				VEItems.EARS_RABBIT,
				VEItems.ANTENNA,
				VEItems.GILLS_AXOLOTL,
				VEItems.HORNS_DEER,
				VEItems.HORNS_HARTEBEEST,
				VEItems.HORNS_KIRIN,
				VEItems.HORNS_RAM,
				VEItems.HORNS_KIRIN_STORM,
				VEItems.NOSE_PIG,
				VEItems.NOSE_PIGLIN,
				VEItems.EARS_PIGLIN,
				VEItems.NOSE_VILLAGER,
				VEItems.NOSE_WITCH,
				VEItems.EARS_ELF,
				VEItems.EARS_GOBLIN
				);
		getOrCreateBuilder(NECKLACE).add(VEItems.SCARAB_GOLEM);
		getOrCreateBuilder(BACK).add(
				VEItems.TAIL_KOBOLD,
				VEItems.TAIL_KIRIN,
				VEItems.TAIL_WOLF,
				VEItems.TAIL_CAT,
				VEItems.TAIL_FOX,
				VEItems.TAIL_RAT,
				VEItems.TAIL_CAT_2,
				VEItems.TAIL_DRAGON,
				VEItems.TAIL_DEVIL,
				VEItems.TAIL_RABBIT,
				VEItems.TAIL_HORSE,
				VEItems.TAIL_DRAGONFLY,
				VEItems.TAIL_ANT,
				VEItems.TAIL_LIZARD,
				VEItems.TAIL_LIZARD2
				);
		
		getOrCreateBuilder(HOLY_SYMBOL).add(
				VEItems.SYMBOL_DIAMOND,
				VEItems.SYMBOL_GOLD,
				VEItems.SYMBOL_IRON,
				VEItems.SYMBOL_STONE,
				VEItems.SYMBOL_WOOD
				);
		
		getOrCreateBuilder(COSMETIC).add(
				VEItems.COSTUME,
				VEItems.EARS_WOLF,
				VEItems.EARS_CAT,
				VEItems.EARS_RABBIT,
				VEItems.GILLS_AXOLOTL,
				VEItems.ANTENNA,
				VEItems.HORNS_DEER,
				VEItems.HORNS_HARTEBEEST,
				VEItems.HORNS_KIRIN,
				VEItems.HORNS_RAM,
				VEItems.HORNS_KIRIN_STORM,
				VEItems.TAIL_KOBOLD,
				VEItems.TAIL_KIRIN,
				VEItems.TAIL_WOLF,
				VEItems.TAIL_CAT,
				VEItems.TAIL_FOX,
				VEItems.TAIL_RAT,
				VEItems.TAIL_CAT_2,
				VEItems.TAIL_DRAGON,
				VEItems.TAIL_DEVIL,
				VEItems.TAIL_RABBIT,
				VEItems.TAIL_HORSE,
				VEItems.TAIL_DRAGONFLY,
				VEItems.TAIL_ANT,
				VEItems.TAIL_LIZARD,
				VEItems.TAIL_LIZARD2,
				VEItems.NOSE_PIG,
				VEItems.NOSE_PIGLIN,
				VEItems.EARS_PIGLIN,
				VEItems.NOSE_VILLAGER,
				VEItems.NOSE_WITCH,
				VEItems.EARS_ELF,
				VEItems.EARS_GOBLIN
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
        registerCustomCurio("cosmetic", 4, true, false, null);
    }
	
	private static void registerCustomCurio(final String identifier, final int slots, final boolean isEnabled, final boolean isHidden, @Nullable final ResourceLocation icon)
	{
		final SlotTypeMessage.Builder message = new SlotTypeMessage.Builder(identifier);
		message.size(slots);
		if(!isEnabled)
			message.lock();
		
		if(isHidden)
			message.hide();
		
		if (icon != null)
			message.icon(icon);
		
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> message.build());
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
