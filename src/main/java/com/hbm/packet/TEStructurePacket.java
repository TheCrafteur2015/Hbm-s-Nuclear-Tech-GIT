package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityStructureMarker;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEStructurePacket implements IMessage {

	int x;
	int y;
	int z;
	int type;

	public TEStructurePacket()
	{
		
	}

	public TEStructurePacket(int x, int y, int z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.type);
	}

	public static class Handler implements IMessageHandler<TEStructurePacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEStructurePacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityStructureMarker) {
					
				TileEntityStructureMarker marker = (TileEntityStructureMarker) te;
				marker.type = m.type;
			}
			return null;
		}
	}
}
