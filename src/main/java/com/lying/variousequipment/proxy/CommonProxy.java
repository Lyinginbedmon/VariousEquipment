package com.lying.variousequipment.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class CommonProxy implements IProxy
{
	public PlayerEntity getPlayerEntity(NetworkEvent.Context ctx)
	{
		return ctx.getSender();
	}
}
