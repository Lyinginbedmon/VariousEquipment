package com.lying.variousequipment.tileentity;

import com.lying.variousequipment.init.VETileEntities;

import net.minecraft.entity.player.PlayerEntity;

public class TileEntityCentrifuge extends TileEntitySpinny
{
	public TileEntityCentrifuge()
	{
		super(VETileEntities.CENTRIFUGE);
	}
	
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return false;
	}
}
