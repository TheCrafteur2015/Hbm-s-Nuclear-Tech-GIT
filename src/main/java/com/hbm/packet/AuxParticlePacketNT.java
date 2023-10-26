package com.hbm.packet;

import java.io.IOException;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class AuxParticlePacketNT implements IMessage {
	
	PacketBuffer buffer;

	public AuxParticlePacketNT() { }

	public AuxParticlePacketNT(NBTTagCompound nbt, double x, double y, double z) {
		
		this.buffer = new PacketBuffer(Unpooled.buffer());

		nbt.setDouble("posX", x);
		nbt.setDouble("posY", y);
		nbt.setDouble("posZ", z);
		
		try {
			this.buffer.writeNBTTagCompoundToBuffer(nbt);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		if (this.buffer == null) {
			this.buffer = new PacketBuffer(Unpooled.buffer());
		}
		this.buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		if (this.buffer == null) {
			this.buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(this.buffer);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacketNT, IMessage> {
		
		@Override
		public IMessage onMessage(AuxParticlePacketNT m, MessageContext ctx) {
			
			if(Minecraft.getMinecraft().theWorld == null)
				return null;

			
			try {

				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				
				if(nbt != null)
					MainRegistry.proxy.effectNT(nbt);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}

}
