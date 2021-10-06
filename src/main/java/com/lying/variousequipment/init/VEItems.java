package com.lying.variousequipment.init;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.item.IEventListenerItem;
import com.lying.variousequipment.item.*;
import com.lying.variousequipment.item.VEItemGroup;
import com.lying.variousequipment.item.bauble.*;
import com.lying.variousequipment.item.vial.Vial;
import com.lying.variousequipment.item.vial.Vial.VialShape;
import com.lying.variousequipment.reference.Reference;
import com.lying.variousoddities.init.VOEnchantments;
import com.lying.variousoddities.item.VOItemGroup;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class VEItems
{
	private static final List<Item> ITEMS = Lists.newArrayList();
	private static final List<BlockItem> BLOCK_ITEMS = Lists.newArrayList();
	
	public static final List<Item> DYEABLES = Lists.newArrayList();
	
	// Items
	public static final Item HAT_ARCHFEY	= register("hat_archfey", new ItemHatArchfey(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item HAT_MASK		= register("hat_fey_mask", new ItemHatFeyMask(new Item.Properties().group(VEItemGroup.PROPS)));
	public static final Item EARS_WOLF		= register("ears_wolf", new ItemHorns.Wolf(new Item.Properties()));
	public static final Item EARS_CAT		= register("ears_cat", new ItemHorns.Cat(new Item.Properties()));
	public static final Item EARS_RABBIT	= register("ears_rabbit", new ItemHorns.Rabbit(new Item.Properties()));
	public static final Item GILLS_AXOLOTL	= register("gills_axolotl", new ItemHorns.Axolotl(new Item.Properties()));
	public static final Item ANTENNA		= register("ant_antenna", new ItemHorns.Antenna(new Item.Properties()));
	public static final Item HORNS_RAM		= register("horns_ram", new ItemHorns.Ram(new Item.Properties()));
	public static final Item HORNS_DEER		= register("horns_deer", new ItemHorns.Deer(new Item.Properties()));
	public static final Item HORNS_KIRIN	= register("horns_kirin", new ItemHorns.Kirin(new Item.Properties()));
	public static final Item HORNS_KIRIN_STORM	= register("horns_kirin_storm", new ItemHorns.KirinStorm(new Item.Properties()));
	public static final Item HORNS_HARTEBEEST	= register("horns_hartebeest", new ItemHorns.Hartebeest(new Item.Properties()));
	public static final Item TAIL_KOBOLD	= register("tail_kobold", new ItemTails.Kobold(new Item.Properties()));
	public static final Item TAIL_KIRIN		= register("tail_kirin", new ItemTails.Kirin(new Item.Properties()));
	public static final Item TAIL_WOLF		= register("tail_wolf", new ItemTails.Wolf(new Item.Properties()));
	public static final Item TAIL_CAT		= register("tail_cat", new ItemTails.Cat(new Item.Properties()));
	public static final Item TAIL_FOX		= register("tail_fox", new ItemTails.Fox(new Item.Properties()));
	public static final Item TAIL_RAT		= register("tail_rat", new ItemTails.Rat(new Item.Properties()));
	public static final Item TAIL_CAT_2		= register("tail_bakeneko", new ItemTails.Cat2(new Item.Properties()));
	public static final Item TAIL_DRAGON	= register("tail_dragon", new ItemTails.Dragon(new Item.Properties()));
	public static final Item TAIL_DEVIL		= register("tail_devil", new ItemTails.Devil(new Item.Properties()));
	public static final Item TAIL_RABBIT	= register("tail_rabbit", new ItemTails.Rabbit(new Item.Properties()));
	public static final Item TAIL_HORSE		= register("tail_horse", new ItemTails.Horse(new Item.Properties()));
	public static final Item TAIL_DRAGONFLY	= register("tail_dragonfly", new ItemTails.Dragonfly(new Item.Properties()));
	public static final Item TAIL_ANT		= register("tail_ant", new ItemTails.Ant(new Item.Properties()));
	public static final Item TAIL_LIZARD	= register("tail_lizard", new ItemTails.Lizard(new Item.Properties()));
	public static final Item TAIL_LIZARD2	= register("tail_lizard_barbed", new ItemTails.Lizard2(new Item.Properties()));
	public static final Item NOSE_PIG		= register("nose_pig", new ItemHorns.NosePig(new Item.Properties()));
	public static final Item NOSE_PIGLIN	= register("nose_piglin", new ItemHorns.NosePiglin(new Item.Properties()));
	public static final Item EARS_PIGLIN	= register("ears_piglin", new ItemHorns.Piglin(new Item.Properties()));
	public static final Item NOSE_VILLAGER	= register("nose_villager", new ItemHorns.NoseVillager(new Item.Properties()));
	public static final Item NOSE_WITCH		= register("nose_witch", new ItemHorns.NoseWitch(new Item.Properties()));
	public static final Item EARS_ELF		= register("ears_pointy", new ItemHorns.EarsElf(new Item.Properties()));
	public static final Item EARS_GOBLIN	= register("ears_goblin", new ItemHorns.EarsGoblin(new Item.Properties()));
	public static final Item THIRD_EYE		= register("third_eye", new ItemThirdEye(new Item.Properties()));
	public static final Item COSTUME		= register("costume", new ItemCostume(new Item.Properties()));
	
	public static final Item HAT_WITCH		= register("hat_witch", new ItemHatWitch(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item HAT_HOOD		= register("hat_hood", new ItemHatHood(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item BLINDFOLD		= register("blindfold", new ItemBlindfold(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item BLINDFOLD_FAKE	= register("cosmetic_blindfold", new ItemBlindfold(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item RING_IRON		= register("iron_ring", new ItemRing("ring_iron", new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item RING_GOLD		= register("gold_ring", new ItemRing("ring_gold", new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item CROWBAR		= register("crowbar", new ItemCrowbar(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_WOOD	= register("wooden_holy_symbol", new ItemSymbol(ItemTier.WOOD, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_STONE	= register("stone_holy_symbol", new ItemSymbol(ItemTier.STONE, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_IRON	= register("iron_holy_symbol", new ItemSymbol(ItemTier.IRON, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_GOLD	= register("golden_holy_symbol", new ItemSymbol(ItemTier.GOLD, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SYMBOL_DIAMOND	= register("diamond_holy_symbol", new ItemSymbol(ItemTier.DIAMOND, new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item VIAL_THROWABLE		= register("vial_throwable", new ItemVialThrowable(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item VIAL_DRINKABLE		= register("vial_drinkable", new ItemVialDrinkable(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item VIAL_SOLUTION		= register("vial_solution", new ItemVialSolution(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item NEEDLE_BONE	= register("needle_bone", new ItemNeedleBone(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item NEEDLE_IRON	= register("needle_iron", new ItemNeedle(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item CALTROP		= register("caltrop", new ItemCaltrop(new Item.Properties()));
	public static final Item REPEATING_CROSSBOW	= register("repeating_crossbow", new ItemCrossbowRepeating(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item NETHERITE_DAGGER	= register("netherite_dagger", new ItemDagger(ItemTier.NETHERITE, 1, -1.2F, (new Item.Properties())));
	public static final Item DIAMOND_DAGGER	= register("diamond_dagger", new ItemDagger(ItemTier.DIAMOND, 1, -1.2F, (new Item.Properties())));
	public static final Item IRON_DAGGER	= register("iron_dagger", new ItemDagger(ItemTier.IRON, 1, -1.2F, (new Item.Properties())));
	public static final Item GOLDEN_DAGGER	= register("golden_dagger", new ItemDagger(ItemTier.GOLD, 1, -1.2F, (new Item.Properties())));
	public static final Item STONE_DAGGER	= register("stone_dagger", new ItemDagger(ItemTier.STONE, 1, -1.2F, (new Item.Properties())));
	public static final Item WOODEN_DAGGER	= register("wooden_dagger", new ItemDagger(ItemTier.WOOD, 1, -1.2F, (new Item.Properties())));
	public static final Item NETHERITE_GLAIVE	= register("netherite_glaive", new ItemGlaive(ItemTier.NETHERITE, 4, -3F, new Item.Properties()));
	public static final Item DIAMOND_GLAIVE	= register("diamond_glaive", new ItemGlaive(ItemTier.DIAMOND, 4, -3F, new Item.Properties()));
	public static final Item IRON_GLAIVE	= register("iron_glaive", new ItemGlaive(ItemTier.IRON, 4, -3.1F, new Item.Properties()));
	public static final Item GOLDEN_GLAIVE	= register("golden_glaive", new ItemGlaive(ItemTier.GOLD, 2, -3F, new Item.Properties()));
	public static final Item STONE_GLAIVE	= register("stone_glaive", new ItemGlaive(ItemTier.STONE, 4, -3.2F, new Item.Properties()));
	public static final Item WOODEN_GLAIVE	= register("wooden_glaive", new ItemGlaive(ItemTier.WOOD, 2, -3.2F, new Item.Properties()));
	public static final Item SHORTBOW		= register("shortbow", new ItemShortbow(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item WHEEL_OAK		= register("oak_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item WHEEL_SPRUCE	= register("spruce_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item WHEEL_BIRCH	= register("birch_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item WHEEL_ACACIA	= register("acacia_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item WHEEL_JUNGLE	= register("jungle_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item WHEEL_DARK_OAK	= register("dark_oak_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item WHEEL_CRIMSON	= register("crimson_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item WHEEL_WARPED	= register("warped_wheel", new ItemWheel(ItemTier.WOOD, new Item.Properties()));
	public static final Item BLOWPIPE		= register("blowpipe", new ItemBlowpipe(new Item.Properties()));
	public static final Item MILKFAT		= register("milkfat", new Item(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SALTPETER		= register("saltpeter", new Item(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SALT			= register("salt", new Item(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item BRIMSTONE		= register("brimstone", new Item(new Item.Properties().group(VEItemGroup.GEAR)));
	public static final Item SUN_STONE		= register("firmament_stone", new Item(new Item.Properties().group(VEItemGroup.GEAR).maxStackSize(1)));
	public static final Item SCARECROW		= register("scarecrow", new ItemScarecrow(new Item.Properties().group(VOItemGroup.BLOCKS).maxStackSize(16)));
	public static final Item NOTEPAD		= register("notepad", new ItemNotepad(new Item.Properties().group(VEItemGroup.GEAR)));
	
	public static final Item SCARAB_GOLEM	= register("scarab_golem", new ItemScarabGolem(new Item.Properties().group(VOItemGroup.LOOT)));
	public static final Item STONE_LUCK		= register("luckstone", new ItemLuckstone(new Item.Properties().group(VOItemGroup.LOOT)));
	public static final Item FOOD_SPOON		= register("sustaining_spoon", new ItemFoodSpoon(new Item.Properties().group(VOItemGroup.LOOT)));
	public static final Item COATING_SILVER	= register("silversheen", new ItemCoating(VOEnchantments.SILVERSHEEN, new Item.Properties().group(VOItemGroup.LOOT)));
	public static final Item SALVE_STONE	= register("stone_salve", new ItemSalveStone(new Item.Properties().group(VOItemGroup.LOOT)));
	
	// Block items
	public static final BlockItem SCREEN_WHITE		= registerBlock("white_screen", VEBlocks.SCREEN_WHITE, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_BLACK		= registerBlock("black_screen", VEBlocks.SCREEN_BLACK, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_BLUE		= registerBlock("blue_screen", VEBlocks.SCREEN_BLUE, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_BROWN		= registerBlock("brown_screen", VEBlocks.SCREEN_BROWN, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_CYAN		= registerBlock("cyan_screen", VEBlocks.SCREEN_CYAN, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_GREEN		= registerBlock("green_screen", VEBlocks.SCREEN_GREEN, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_GRAY		= registerBlock("gray_screen", VEBlocks.SCREEN_GRAY, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_LIGHT_BLUE	= registerBlock("light_blue_screen", VEBlocks.SCREEN_LIGHT_BLUE, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_LIGHT_GRAY	= registerBlock("light_gray_screen", VEBlocks.SCREEN_LIGHT_GRAY, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_LIME		= registerBlock("lime_screen", VEBlocks.SCREEN_LIME, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_MAGENTA	= registerBlock("magenta_screen", VEBlocks.SCREEN_MAGENTA, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_ORANGE		= registerBlock("orange_screen", VEBlocks.SCREEN_ORANGE, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_PINK		= registerBlock("pink_screen", VEBlocks.SCREEN_PINK, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_PURPLE		= registerBlock("purple_screen", VEBlocks.SCREEN_PURPLE, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_RED		= registerBlock("red_screen", VEBlocks.SCREEN_RED, VOItemGroup.BLOCKS);
	public static final BlockItem SCREEN_YELLOW		= registerBlock("yellow_screen", VEBlocks.SCREEN_YELLOW, VOItemGroup.BLOCKS);
	public static final BlockItem MISSING_BLOCK		= registerBlock("missing_block", VEBlocks.MISSING_BLOCK, VOItemGroup.BLOCKS);
	public static final BlockItem CHASSIS_OAK			= registerBlock("oak_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_OAK, new Item.Properties()));
	public static final BlockItem CHASSIS_SPRUCE		= registerBlock("spruce_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_SPRUCE, new Item.Properties()));
	public static final BlockItem CHASSIS_BIRCH			= registerBlock("birch_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_BIRCH, new Item.Properties()));
	public static final BlockItem CHASSIS_ACACIA		= registerBlock("acacia_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_ACACIA, new Item.Properties()));
	public static final BlockItem CHASSIS_JUNGLE		= registerBlock("jungle_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_JUNGLE, new Item.Properties()));
	public static final BlockItem CHASSIS_DARK_OAK		= registerBlock("dark_oak_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_DARK_OAK, new Item.Properties()));
	public static final BlockItem CHASSIS_CRIMSON		= registerBlock("crimson_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_CRIMSON, new Item.Properties()));
	public static final BlockItem CHASSIS_WARPED		= registerBlock("warped_wagon_chassis", new ItemChassis(VEBlocks.WAGON_CHASSIS_WARPED, new Item.Properties()));
	public static final BlockItem CENTRIFUGE		= registerBlock("centrifuge", VEBlocks.CENTRIFUGE, VOItemGroup.BLOCKS);
	public static final BlockItem MIXER				= registerBlock("mixer", VEBlocks.MIXER, VOItemGroup.BLOCKS);
	public static final BlockItem GUANO				= registerBlock("bat_guano", VEBlocks.GUANO, VOItemGroup.BLOCKS);
	public static final BlockItem NIGHT_POWDER		= registerBlock("darkvision_powder", VEBlocks.NIGHT_POWDER, VEItemGroup.GEAR);
	public static final BlockItem ALEMBIC			= registerBlock("alembic", VEBlocks.ALEMBIC, VOItemGroup.BLOCKS);
	
	/** Allows dispensers to empty water buckets into cauldrons */
	public static final IDispenseItemBehavior DISPENSE_BUCKETS = new OptionalDispenseBehavior()
    {
		private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();
		
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
    		CompoundNBT stackData = stack.getOrCreateTag();
    		ItemStack result = new ItemStack(Items.BUCKET);
    		result.setTag(stackData);
    		
        	World world = source.getWorld();
        	BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
        	if(world.getBlockState(pos).getBlock() == Blocks.CAULDRON)
        	{
        		this.setSuccessful(true);
        		world.setBlockState(pos, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, 3));
        		return result;
        	}
        	
    		BucketItem item = (BucketItem)stack.getItem();
    		if(item.tryPlaceContainedLiquid(null, world, pos, null))
    		{
        		this.setSuccessful(true);
    			item.onLiquidPlaced(world, stack, pos);
    			return result;
    		}
    		return defaultBehaviour.dispense(source, stack);
        }
    };
	
	/** Allows dispensers to empty water bottles into cauldrons */
	public static final IDispenseItemBehavior DISPENSE_BOTTLES = new OptionalDispenseBehavior()
    {
		private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();
		
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
        	World world = source.getWorld();
        	BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
    		BlockState state = world.getBlockState(pos);
        	if(PotionUtils.getPotionTypeFromNBT(stack.getOrCreateTag()) == Potions.WATER)
        	{
	        	if(world.getBlockState(pos).getBlock() == Blocks.CAULDRON)
	        	{
	        		setSuccessful(true);
	        		world.setBlockState(pos, state.with(CauldronBlock.LEVEL, Math.min(3, state.get(CauldronBlock.LEVEL) + 1)));
	        		stack.shrink(1);
	        		return new ItemStack(Items.GLASS_BOTTLE);
	        	}
        	}
        	else if(PotionUtils.getPotionTypeFromNBT(stack.getOrCreateTag()) == Potions.EMPTY)
        		if(world.getBlockState(pos).getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) > 0)
        		{
        			this.setSuccessful(true);
        			world.setBlockState(pos, state.with(CauldronBlock.LEVEL, Math.max(0, state.get(CauldronBlock.LEVEL) - 1)));
	    			return glassBottleFill(source, stack, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER));
        		}
        		else if(state.isInAndMatches(BlockTags.BEEHIVES, (blockstate) -> { return blockstate.hasProperty(BeehiveBlock.HONEY_LEVEL); }) && state.get(BeehiveBlock.HONEY_LEVEL) >= 5)
	    		{
	    			this.setSuccessful(true);
	    			((BeehiveBlock)state.getBlock()).takeHoney(world, state, pos, (PlayerEntity)null, BeehiveTileEntity.State.BEE_RELEASED);
	    			return glassBottleFill(source, stack, new ItemStack(Items.HONEY_BOTTLE));
	    		}
	    		else if(world.getFluidState(pos).isTagged(FluidTags.WATER))
	    		{
	    			this.setSuccessful(true);
	    			return glassBottleFill(source, stack, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER));
	    		}
            return super.dispenseStack(source, stack);
        }
        
		private ItemStack glassBottleFill(IBlockSource source, ItemStack empty, ItemStack filled)
		{
			empty.shrink(1);
			if(empty.isEmpty())
				return filled.copy();
			
			if(source.<DispenserTileEntity>getBlockTileEntity().addItemStack(filled.copy()) < 0)
				this.defaultBehaviour.dispense(source, filled.copy());
			
			return empty;
		}
    };
	
	public static Item register(String nameIn, Item itemIn)
	{
		itemIn.setRegistryName(Reference.ModInfo.MOD_PREFIX+nameIn);
		ITEMS.add(itemIn);
		
		if(itemIn instanceof IDyeableArmorItem)
			DYEABLES.add(itemIn);
		
		return itemIn;
	}
	
	public static BlockItem registerBlock(String nameIn, Block blockIn)
	{
		return registerBlock(nameIn, blockIn, VOItemGroup.BLOCKS);
	}
	
	public static BlockItem registerBlock(String nameIn, Block blockIn, ItemGroup group)
	{
		return registerBlock(nameIn, new BlockItem(blockIn, new Item.Properties().group(group)));
	}
	
	public static BlockItem registerBlock(String nameIn, BlockItem itemIn)
	{
		itemIn.setRegistryName(Reference.ModInfo.MOD_PREFIX+nameIn);
		BLOCK_ITEMS.add(itemIn);
		return itemIn;
	}
	
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
    {
    	DispenserBlock.registerDispenseBehavior(Items.WATER_BUCKET, DISPENSE_BUCKETS);
    	DispenserBlock.registerDispenseBehavior(Items.POTION, DISPENSE_BOTTLES);
    	
    	IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
    	registry.registerAll(ITEMS.toArray(new Item[0]));
    	registry.registerAll(BLOCK_ITEMS.toArray(new Item[0]));
    	
    	ITEMS.forEach((item) -> 
    	{
    		if(item instanceof IEventListenerItem)
    			((IEventListenerItem)item).addListeners(MinecraftForge.EVENT_BUS);
    	});
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void registerProperties()
    {
    	ItemModelsProperties.registerProperty(HAT_HOOD, new ResourceLocation(Reference.ModInfo.MOD_ID, "is_up"), (stack, world, entity) -> { return ItemHatHood.getIsUp(stack) ? 1F : 0F; });
    	ItemModelsProperties.registerProperty(REPEATING_CROSSBOW, new ResourceLocation(Reference.ModInfo.MOD_ID, "ammo"), (stack, world, entity) -> { return ItemCrossbowRepeating.hasAmmo(stack) ? (ItemCrossbowRepeating.isNextShotFirework(stack) ? 2 : 1) : 0; });
    	ItemModelsProperties.registerProperty(SHORTBOW, new ResourceLocation("pull"), (stack, world, entity) -> { return entity == null ? 0F : entity.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getItemInUseCount()) / 10.0F; });
	 	ItemModelsProperties.registerProperty(SHORTBOW, new ResourceLocation("pulling"), (stack, world, entity) -> { return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F; });
	 	ItemModelsProperties.registerProperty(VIAL_THROWABLE, new ResourceLocation(Reference.ModInfo.MOD_ID, "type"), (stack, world, entity) -> 
	 		{
	 			Vial vial = ItemVial.getVialFromItem(stack);
	 			return vial != null ? (float)vial.getShape().modelNumber() : VialShape.BOTTLE.modelNumber();
	 		});
	 	ItemModelsProperties.registerProperty(VIAL_SOLUTION, new ResourceLocation(Reference.ModInfo.MOD_ID, "type"), (stack, world, entity) -> 
	 		{
	 			Vial vial = ItemVial.getVialFromItem(stack);
	 			return vial != null ? (float)vial.getShape().modelNumber() : VialShape.BOTTLE.modelNumber(); 
	 		});
	 	ItemModelsProperties.registerProperty(SUN_STONE, new ResourceLocation("side"), (stack, world, entity) ->
	 	{
	 		CompoundNBT stackData = stack.getOrCreateTag();
	 		return stackData.contains("Side", 8) ? (stackData.getString("Side").equalsIgnoreCase("Moon") ? 0F : 1F) : 1F;
	 	});
	 	ItemModelsProperties.registerProperty(SUN_STONE, new ResourceLocation("sunlight"), (stack, world, entity) -> 
	 	{
	 		Entity ent = (Entity)(entity != null ? entity : stack.getAttachedEntity());
	 		if(ent == null)
	 			return 0F;
	 		else
	 		{
	 			if(world == null && ent.getEntityWorld() instanceof ClientWorld)
	 				world = (ClientWorld)ent.getEntityWorld();
	 			
	 			if(world == null)
	 				return 0F;
	 			else if(world.getDimensionType().isNatural())
 				{
	 				BlockPos pos = ent.getPosition();
	 				pos = new BlockPos(pos.getX(), 255, pos.getZ());
	 				
	 				int sunAtPos = world.getLightFor(LightType.SKY, pos) - world.getSkylightSubtracted();
 					float sunAngle = world.getCelestialAngleRadians(1.0F);
 					float f1 = sunAngle < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
 					sunAngle = sunAngle + (f1 - sunAngle) * 0.2F;
 					sunAtPos = Math.round((float)sunAtPos * MathHelper.cos(sunAngle));
 					
 					int skyLight = MathHelper.clamp(sunAtPos, 4, 8) - 4;
 					return (float)skyLight / 4F;
 				}
 				else
 					return (float)Math.random();
	 		}
	 	});
	 	ItemModelsProperties.registerProperty(SUN_STONE, new ResourceLocation("moonlight"), (stack, world, entity) -> 
	 	{
	 		Entity ent = (Entity)(entity != null ? entity : stack.getAttachedEntity());
	 		if(ent == null)
	 			return 0F;
	 		else
	 		{
	 			if(world == null && ent.getEntityWorld() instanceof ClientWorld)
	 				world = (ClientWorld)ent.getEntityWorld();
	 			
	 			if(world == null)
	 				return 0F;
	 			else if(world.getDimensionType().isNatural())
 				{
 					float sunAngle = world.func_242415_f(1.0F) * 360F;
 					if(sunAngle < 100 && sunAngle > 80)
 						return 0.1F - (100-sunAngle)/20 * 0.1F;
 					else if(sunAngle > 260 && sunAngle < 280)
 						return 0.1F - (sunAngle-260)/20 * 0.1F;
 					else if(sunAngle <= 260 && sunAngle >= 100)
 						return 0.2F+((float)world.getMoonPhase()/8F)*0.8F;
 					
 					return 0F;
 				}
 				else
 					return (float)Math.random();
	 		}
	 	});
    }
}
