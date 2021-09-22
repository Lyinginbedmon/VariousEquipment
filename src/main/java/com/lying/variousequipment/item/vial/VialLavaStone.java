package com.lying.variousequipment.item.vial;

import java.util.Random;

import com.lying.variousequipment.init.VEBlocks;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class VialLavaStone extends Vial
{
	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Reference.ModInfo.MOD_ID, "lava_stone");
	
	public VialLavaStone()
	{
		super(VialType.THROWABLE, VialShape.BAG, 16, 2893343);
	}
	
	public ResourceLocation getRegistryName(){ return REGISTRY_NAME; }
	
	public boolean canDropItem(){ return true; }
	
	public ItemStack getDroppedItem(ItemStack stack, Random rand, World world)
	{
		return stack.copy();
	}
	
	public boolean onSplash(Entity entityIn, World worldIn, RayTraceResult resultIn){ return false; }
	
	public void onTick(Entity entityIn, World worldIn)
	{
		BlockPos pos = entityIn.getPosition();
		if(worldIn.getBlockState(pos).getBlock() == Blocks.LAVA)
		{
			if(!worldIn.isRemote)
				worldIn.setBlockState(pos, VEBlocks.LAVA_STONE.getDefaultState());
			entityIn.remove(false);
		}
	}
	
	public static class Builder extends Vial.Builder
	{
		public Builder(){ super(REGISTRY_NAME); }
		
		public Vial create()
		{
			return new VialLavaStone();
		}
	}
}
