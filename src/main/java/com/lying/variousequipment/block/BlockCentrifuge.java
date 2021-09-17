package com.lying.variousequipment.block;

import com.lying.variousequipment.tileentity.TileEntityCentrifuge;

import net.minecraft.block.AbstractBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockCentrifuge extends BlockSpinny
{
	public BlockCentrifuge(AbstractBlock.Properties propertiesIn)
	{
		super(propertiesIn);
	}
	
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new TileEntityCentrifuge();
	}
}
