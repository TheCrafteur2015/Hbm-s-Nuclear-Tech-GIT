package com.hbm.packet;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class AuxParticlePacket implements IMessage {

	double x;
	double y;
	double z;
	int type;

	public AuxParticlePacket()
	{
		
	}

	public AuxParticlePacket(double x, double y, double z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(this.x);
		buf.writeDouble(this.y);
		buf.writeDouble(this.z);
		buf.writeInt(this.type);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacket, IMessage> {
		
		@Override
		public IMessage onMessage(AuxParticlePacket m, MessageContext ctx) {
			
			try {
				
				MainRegistry.proxy.particleControl(m.x, m.y, m.z, m.type);
				
			} catch(Exception x) { }
			
			return null;
		}
	}
}
