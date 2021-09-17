package com.lying.variousequipment.block;

import com.lying.variousequipment.tileentity.TileEntityMixer;

import net.minecraft.block.AbstractBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockMixer extends BlockSpinny
{
	public BlockMixer(AbstractBlock.Properties propertiesIn)
	{
		super(propertiesIn);
	}
	
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new TileEntityMixer();
	}
}
