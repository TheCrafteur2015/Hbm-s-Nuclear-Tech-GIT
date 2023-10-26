package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityBlastDoor;
import com.hbm.tileentity.machine.TileEntityVaultDoor;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEVaultPacket implements IMessage {

	int x;
	int y;
	int z;
	boolean isOpening;
	int state;
	long sysTime;
	int type;

	public TEVaultPacket() {

	}

	public TEVaultPacket(int x, int y, int z, boolean isOpening, int state, long sysTime, int type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.isOpening = isOpening;
		this.state = state;
		this.sysTime = sysTime;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.isOpening = buf.readBoolean();
		this.state = buf.readInt();
		this.sysTime = buf.readLong();
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeBoolean(this.isOpening);
		buf.writeInt(this.state);
		buf.writeLong(this.sysTime);
		buf.writeInt(this.type);
	}

	public static class Handler implements IMessageHandler<TEVaultPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEVaultPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			try {
				if (te != null && te instanceof TileEntityVaultDoor) {

					TileEntityVaultDoor vault = (TileEntityVaultDoor) te;
					vault.isOpening = m.isOpening;
					vault.state = m.state;
					if(m.sysTime == 1)
						vault.sysTime = System.currentTimeMillis();
					vault.type = m.type;
				}
				
				if (te != null && te instanceof TileEntityBlastDoor) {

					TileEntityBlastDoor vault = (TileEntityBlastDoor) te;
					vault.isOpening = m.isOpening;
					vault.state = m.state;
					if(m.sysTime == 1)
						vault.sysTime = System.currentTimeMillis();
				}
			} catch (Exception x) {
			}
			return null;
		}
	}
}
