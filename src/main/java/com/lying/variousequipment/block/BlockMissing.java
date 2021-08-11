package com.lying.variousequipment.block;

public class BlockMissing extends VEBlock
{
	public BlockMissing(Properties properties)
	{
		super("missing_block", properties.setRequiresTool().hardnessAndResistance(1.8F, 3600000.0F).setOpaque(VEBlock::isntSolid).notSolid().setLightLevel((state) -> {
		      return 3;
		   }).setAllowsSpawn((state, reader, pos, entity) -> {
			      return false;
		   }).setNeedsPostProcessing(VEBlock::needsPostProcessing).setEmmisiveRendering(VEBlock::needsPostProcessing));
	}
}
