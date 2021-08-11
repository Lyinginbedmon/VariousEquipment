package com.lying.variousequipment.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class BlockScreen extends VEBlock
{
	private final DyeColor color;
	
	public BlockScreen(DyeColor colorIn, Properties properties)
	{
		super("chroma_screen", properties.setRequiresTool().hardnessAndResistance(1.8F, 3600000.0F).setOpaque(VEBlock::isntSolid).notSolid().setLightLevel((state) -> {
		      return 3;
		   }).setAllowsSpawn((state, reader, pos, entity) -> {
			      return false;
		   }).setNeedsPostProcessing(VEBlock::needsPostProcessing).setEmmisiveRendering(VEBlock::needsPostProcessing));
		this.color = colorIn;
	}
	
	public static int getColor(BlockState blockState, IBlockDisplayReader reader, BlockPos pos, int tintIndex)
	{
		if(tintIndex != 0)
			return -1;
		
		switch(((BlockScreen)blockState.getBlock()).color)
		{
			case BLACK:		return 0;
			case BLUE:		return 4310;
			case BROWN:		return 9912064;
			case CYAN:		return 40092;
			case GRAY:		return 5395026;
			case GREEN:		return 3585792;
			case LIGHT_BLUE:	return 42458;
			case LIGHT_GRAY:	return 10329501;
			case LIME:		return 7587584;
			case MAGENTA:	return 16318693;
			case ORANGE:	return 16347136;
			case PINK:		return 15925320;
			case PURPLE:	return 7798968;
			case RED:		return 15863296;
			case YELLOW:	return 16698368;
			default:
				return -1;
		}
	}
}
