package com.hbm.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

public class ParticleBurstPacket implements IMessage {

	int x;
	int y;
	int z;
	int block;
	int meta;

	public ParticleBurstPacket()
	{
		
	}

	public ParticleBurstPacket(int x, int y, int z, int block, int meta)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.meta = meta;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.block = buf.readInt();
		this.meta = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.block);
		buf.writeInt(this.meta);
	}

	public static class Handler implements IMessageHandler<ParticleBurstPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ParticleBurstPacket m, MessageContext ctx) {
			
			try {
				Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(m.x, m.y, m.z, Block.getBlockById(m.block), m.meta);
			} catch(Exception x) { }
			
			return null;
		}
	}
}
