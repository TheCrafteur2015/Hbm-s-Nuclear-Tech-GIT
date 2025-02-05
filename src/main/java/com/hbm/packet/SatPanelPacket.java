package com.hbm.packet;

import java.io.IOException;

import com.hbm.items.tool.ItemSatInterface;
import com.hbm.saveddata.satellites.Satellite;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class SatPanelPacket implements IMessage {
	
	PacketBuffer buffer;
	int type;

	public SatPanelPacket() {

	}

	public SatPanelPacket(Satellite sat) {
		this.type = sat.getID();

		this.buffer = new PacketBuffer(Unpooled.buffer());
		NBTTagCompound nbt = new NBTTagCompound();
		sat.writeToNBT(nbt);
		
		try {
			this.buffer.writeNBTTagCompoundToBuffer(nbt);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.type = buf.readInt();
		
		if (this.buffer == null) {
			this.buffer = new PacketBuffer(Unpooled.buffer());
		}
		this.buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(this.type);
		
		if (this.buffer == null) {
			this.buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(this.buffer);
	}

	public static class Handler implements IMessageHandler<SatPanelPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SatPanelPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft();

			try {
				
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				ItemSatInterface.currentSat = Satellite.create(m.type);
				
				if(nbt != null)
					ItemSatInterface.currentSat.readFromNBT(nbt);
				
			} catch (Exception x) {
			}
			return null;
		}
	}
}
