package com.lying.variousequipment.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.lying.variousequipment.VariousEquipment;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.proxy.CommonProxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketNotepadUpdate
{
	private UUID playerID;
	private CompoundNBT itemData;
	
	public PacketNotepadUpdate(){ }
	public PacketNotepadUpdate(PlayerEntity player, CompoundNBT compound)
	{
		this.playerID = player.getUniqueID();
		this.itemData = compound;
	}
	
	public static PacketNotepadUpdate decode(PacketBuffer par1Buffer)
	{
		PacketNotepadUpdate packet = new PacketNotepadUpdate();
		packet.playerID = par1Buffer.readUniqueId();
		packet.itemData = par1Buffer.readCompoundTag();
		return packet;
	}
	
	public static void encode(PacketNotepadUpdate msg, PacketBuffer par1Buffer)
	{
		par1Buffer.writeUniqueId(msg.playerID);
		par1Buffer.writeCompoundTag(msg.itemData);
	}
	
	public static void handle(PacketNotepadUpdate msg, Supplier<NetworkEvent.Context> cxt)
	{
		NetworkEvent.Context context = cxt.get();
		if(context.getDirection().getReceptionSide().isServer())
		{
			PlayerEntity player = ((CommonProxy)VariousEquipment.proxy).getPlayerEntity(context);
			World world = player.getEntityWorld();
			PlayerEntity shaker = world.getPlayerByUuid(msg.playerID);
			if(shaker != null)
			{
				ItemStack heldStack = shaker.getHeldItemMainhand();
				if(heldStack.getItem() == VEItems.NOTEPAD)
					heldStack.setTag(msg.itemData);
			}
		}
		
		context.setPacketHandled(true);
	}
}
