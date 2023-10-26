package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineRadar;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TERadarPacket implements IMessage {

	int x;
	int y;
	int z;
	int conX;
	int conY;
	int conZ;
	int alt;

	public TERadarPacket() {

	}

	public TERadarPacket(int x, int y, int z, int conX, int conY, int conZ, int alt) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.conX = conX;
		this.conY = conY;
		this.conZ = conZ;
		this.alt = alt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.conX = buf.readInt();
		this.conY = buf.readInt();
		this.conZ = buf.readInt();
		this.alt = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.conX);
		buf.writeInt(this.conY);
		buf.writeInt(this.conZ);
		buf.writeInt(this.alt);
	}

	public static class Handler implements IMessageHandler<TERadarPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TERadarPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			try {
				if (te != null && te instanceof TileEntityMachineRadar) {

					TileEntityMachineRadar radar = (TileEntityMachineRadar) te;
					radar.nearbyMissiles.add(new int[]{m.conX, m.conY, m.conZ, m.alt});
				}
			} catch (Exception x) {
			}
			return null;
		}
	}
}
