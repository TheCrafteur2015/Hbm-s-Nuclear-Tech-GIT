package com.hbm.packet;

import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

@Deprecated
public class TEFluidPacket implements IMessage {

	int x;
	int y;
	int z;
	int fill;
	int index;
	int type;

	public TEFluidPacket() { }

	public TEFluidPacket(int x, int y, int z, int fill, int index, FluidType type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.fill = fill;
		this.index = index;
		this.type = type.getID();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.fill = buf.readInt();
		this.index = buf.readInt();
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.fill);
		buf.writeInt(this.index);
		buf.writeInt(this.type);
	}

	public static class Handler implements IMessageHandler<TEFluidPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEFluidPacket m, MessageContext ctx) {
			try{
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof IFluidContainer) {
					
				IFluidContainer gen = (IFluidContainer) te;
				gen.setFillForSync(m.fill, m.index);
				gen.setTypeForSync(Fluids.fromID(m.type), m.index);
			}
			} catch(Exception x) { }
			return null;
		}
	}
}
