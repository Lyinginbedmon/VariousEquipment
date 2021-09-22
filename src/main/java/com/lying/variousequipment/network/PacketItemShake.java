package com.lying.variousequipment.network;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import com.lying.variousequipment.VariousEquipment;
import com.lying.variousequipment.api.IShakeableItem;
import com.lying.variousequipment.item.crafting.IMixerRecipe;
import com.lying.variousequipment.proxy.CommonProxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketItemShake
{
	private UUID playerID;
	
	public PacketItemShake(){ }
	public PacketItemShake(PlayerEntity player)
	{
		this.playerID = player.getUniqueID();
	}
	
	public static PacketItemShake decode(PacketBuffer par1Buffer)
	{
		PacketItemShake packet = new PacketItemShake();
		packet.playerID = par1Buffer.readUniqueId();
		return packet;
	}
	
	public static void encode(PacketItemShake msg, PacketBuffer par1Buffer)
	{
		par1Buffer.writeUniqueId(msg.playerID);
	}
	
	public static void handle(PacketItemShake msg, Supplier<NetworkEvent.Context> cxt)
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
				if(heldStack.getItem() instanceof IShakeableItem)
					((IShakeableItem)heldStack.getItem()).onItemShaken(heldStack, world, shaker);
				else
				{
					List<IMixerRecipe> recipes = IShakeableItem.getMatchingRecipes(heldStack, world);
					if(!recipes.isEmpty())
					{
						IMixerRecipe recipe = recipes.get(0);
						shaker.addItemStackToInventory(recipe.getRecipeOut(heldStack));
						heldStack.shrink(1);
					}
				}
			}
		}
		
		context.setPacketHandled(true);
	}
}
