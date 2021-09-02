package com.lying.variousequipment.network;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.lying.variousequipment.VariousEquipment;
import com.lying.variousequipment.entity.EntityWagon;
import com.lying.variousequipment.proxy.CommonProxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketWagonReins
{
	private int wagonId;
	private int holderEntityId;
	
	public PacketWagonReins(){ }
	public PacketWagonReins(Entity wagonIn, @Nullable Entity holderIn)
	{
		this.wagonId = wagonIn.getEntityId();
		this.holderEntityId = holderIn != null ? holderIn.getEntityId() : 0;
	}
	
	public static PacketWagonReins decode(PacketBuffer par1Buffer)
	{
		PacketWagonReins packet = new PacketWagonReins();
		packet.wagonId = par1Buffer.readInt();
		packet.holderEntityId = par1Buffer.readInt();
		return packet;
	}
	
	public static void encode(PacketWagonReins msg, PacketBuffer par1Buffer)
	{
		par1Buffer.writeInt(msg.wagonId);
		par1Buffer.writeInt(msg.holderEntityId);
	}
	
	public static void handle(PacketWagonReins msg, Supplier<NetworkEvent.Context> cxt)
	{
		NetworkEvent.Context context = cxt.get();
		if(!context.getDirection().getReceptionSide().isServer())
		{
			PlayerEntity player = ((CommonProxy)VariousEquipment.proxy).getPlayerEntity(context);
			World world = player.getEntityWorld();
			Entity wagon = world.getEntityByID(msg.wagonId);
			Entity entity = world.getEntityByID(msg.holderEntityId);
			if(entity instanceof LivingEntity && wagon instanceof EntityWagon)
				((EntityWagon)wagon).setReinsHolder(entity, false);
		}
		
		context.setPacketHandled(true);
	}
}
