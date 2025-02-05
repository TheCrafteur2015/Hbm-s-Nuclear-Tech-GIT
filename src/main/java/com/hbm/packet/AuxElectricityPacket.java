package com.hbm.packet;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

@Deprecated //use the NBT packet instead
public class AuxElectricityPacket implements IMessage {

	int x;
	int y;
	int z;
	long charge;

	public AuxElectricityPacket()
	{
		
	}

	public AuxElectricityPacket(int x, int y, int z, long charge)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.charge = charge;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.charge = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeLong(this.charge);
	}

	public static class Handler implements IMessageHandler<AuxElectricityPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AuxElectricityPacket m, MessageContext ctx) {
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te instanceof IEnergyUser) {
						
					IEnergyUser gen = (IEnergyUser) te;
					gen.setPower(m.charge);
				}
			} catch (Exception x) { }
			return null;
		}
	}
}
