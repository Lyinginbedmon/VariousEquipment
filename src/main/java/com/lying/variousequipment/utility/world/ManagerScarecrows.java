package com.lying.variousequipment.utility.world;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.lying.variousequipment.entity.EntityScarecrow;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ManagerScarecrows
{
	private static final Set<Material> MATERIALS = ImmutableSet.of(Material.PLANTS, Material.CACTUS, Material.ORGANIC, Material.LEAVES, Material.GOURD, Material.OCEAN_PLANT, Material.BAMBOO, Material.BAMBOO_SAPLING);
	private final World world;
	
	private int timer = 0;
	private List<EntityScarecrow> scarecrows = Lists.newArrayList();
	
	public ManagerScarecrows(World worldIn)
	{
		this.world = worldIn;
	}
	
	public void tick()
	{
		if(world.isRemote) return;
		
		int randomTickSpeed = world.getGameRules().get(GameRules.RANDOM_TICK_SPEED).get();
		if(randomTickSpeed <= 0)
			return;
		
		int growTime = (int)((3F / (float)randomTickSpeed) * Reference.Values.TICKS_PER_MINUTE);
		if(++timer % growTime == 0 && !scarecrows.isEmpty())
		{
			timer = 0;
			
			int grows = 0;
			List<BlockPos> positions = Lists.newArrayList();
			for(EntityScarecrow scarecrow : scarecrows)
				grows += scarecrow.addCropsToList(positions) ? 1 : 0;
			if(grows == 0)
				return;
			
			grows *= 6;
			Random rand = this.world.rand;
			while(grows-- > 0 && !positions.isEmpty())
			{
				BlockPos pos = positions.get(rand.nextInt(positions.size()));
				BlockState state = world.getBlockState(pos);
				state.randomTick((ServerWorld)this.world, pos, rand);
				world.playEvent(2005, pos, 0);
				
				positions.remove(pos);
			}
		}
	}
	
	public static boolean isGrowable(BlockState state, BlockPos pos, ServerWorld worldIn)
	{
		if(!worldIn.isAreaLoaded(pos, 0))
			return false;
		
		Block block = state.getBlock();
		if(block instanceof SpreadableSnowyDirtBlock)
			return false;
		
		if(block instanceof BushBlock 
				&& !(block instanceof CropsBlock)
				&& !(block instanceof StemBlock)
				&& !(block instanceof SaplingBlock)
				&& !(block instanceof SweetBerryBushBlock))
			return false;
		
		return MATERIALS.contains(state.getMaterial()) && block instanceof IGrowable && ((IGrowable)block).canGrow(worldIn, pos, state, false);
	}
	
	public void addScarecrow(EntityScarecrow entityIn)
	{
		if(!scarecrows.contains(entityIn))
			scarecrows.add(entityIn);
	}
	
	public void removeScarecrow(EntityScarecrow entityIn)
	{
		if(scarecrows.contains(entityIn))
			scarecrows.remove(entityIn);
	}
}
