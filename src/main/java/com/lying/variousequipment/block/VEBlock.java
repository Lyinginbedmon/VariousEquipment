package com.lying.variousequipment.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class VEBlock extends Block
{
	public static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos){ return false; }
	
	public static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos){ return true; }
	
	public VEBlock(String nameIn, AbstractBlock.Properties properties)
	{
		super(properties);
	}
	
	public VEBlock(String nameIn, Material materialIn)
	{
		this(nameIn, AbstractBlock.Properties.create(materialIn));
	}
	
	public VEBlock(String nameIn, Material materialIn, MaterialColor colorIn)
	{
		this(nameIn, AbstractBlock.Properties.create(materialIn, colorIn));
	}
}
