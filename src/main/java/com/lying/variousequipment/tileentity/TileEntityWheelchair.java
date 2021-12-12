package com.lying.variousequipment.tileentity;

import javax.annotation.Nullable;

import com.lying.variousequipment.init.VETileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class TileEntityWheelchair extends TileEntity
{
	private ItemStack stack = ItemStack.EMPTY;
	private float yaw = 0F;
	
	public TileEntityWheelchair()
	{
		super(VETileEntities.WHEELCHAIR);
	}
	
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		compound.putFloat("Rotation", this.yaw);
		compound.put("Item", this.stack.write(new CompoundNBT()));
		return compound;
	}
	
	public void read(BlockState state, CompoundNBT nbt)
	{
		super.read(state, nbt);
		setItemAndYaw(ItemStack.read(nbt.getCompound("Item")), nbt.getFloat("Rotation"));
	}
	
	public void setItem(ItemStack stackIn)
	{
		this.stack = stackIn.copy();
		markDirty();
	}
	
	public void setItemAndYaw(ItemStack stackIn, float yawIn)
	{
		this.yaw = yawIn;
		this.stack = stackIn.copy();
		markDirty();
	}
	
	public float getYaw(){ return this.yaw; }
	
	public ItemStack getStack(){ return this.stack.copy(); }
	
	public void markDirty()
	{
		super.markDirty();
		if(getWorld() instanceof ServerWorld)
		{
			getWorld().updateComparatorOutputLevel(getPos(), getBlockState().getBlock());
			SUpdateTileEntityPacket packet = getUpdatePacket();
			if(packet != null)
			{
				BlockPos pos = getPos();
				((ServerChunkProvider)getWorld().getChunkProvider()).chunkManager
						.getTrackingPlayers(new ChunkPos(pos), false)
						.forEach(e -> e.connection.sendPacket(packet));
			}
		}
	}
	
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(this.pos, -999, this.getUpdateTag());
	}
	
	public CompoundNBT getUpdateTag()
	{
		return this.write(new CompoundNBT());
	}
	
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
	{
		super.onDataPacket(net, packet);
		this.read(getBlockState(), packet.getNbtCompound());
	}
}
