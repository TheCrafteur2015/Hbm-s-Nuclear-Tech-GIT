package com.hbm.packet;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IChatComponent;

public class PlayerInformPacket implements IMessage {

	boolean fancy;
	private String dmesg = "";
	private int id;
	private IChatComponent component;
	private int millis = 0;

	public PlayerInformPacket() { }

	public PlayerInformPacket(String dmesg, int id) {
		this.fancy = false;
		this.dmesg = dmesg;
		this.id = id;
	}

	public PlayerInformPacket(IChatComponent component, int id) {
		this.fancy = true;
		this.component = component;
		this.id = id;
	}

	public PlayerInformPacket(String dmesg, int id, int millis) {
		this.fancy = false;
		this.dmesg = dmesg;
		this.millis = millis;
		this.id = id;
	}

	public PlayerInformPacket(IChatComponent component, int id, int millis) {
		this.fancy = true;
		this.component = component;
		this.millis = millis;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		this.millis = buf.readInt();
		this.fancy = buf.readBoolean();
		
		if(!this.fancy) {
			this.dmesg = ByteBufUtils.readUTF8String(buf);
		} else {
			this.component = IChatComponent.Serializer.func_150699_a(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
		buf.writeInt(this.millis);
		buf.writeBoolean(this.fancy);
		if(!this.fancy) {
			ByteBufUtils.writeUTF8String(buf, this.dmesg);
		} else {
			ByteBufUtils.writeUTF8String(buf, IChatComponent.Serializer.func_150696_a(this.component));
		}
	}

	public static class Handler implements IMessageHandler<PlayerInformPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PlayerInformPacket m, MessageContext ctx) {
			try {
				
				if(m.millis == 0)
					MainRegistry.proxy.displayTooltip(m.fancy ? m.component.getFormattedText() : m.dmesg, m.id);
				else
					MainRegistry.proxy.displayTooltip(m.fancy ? m.component.getFormattedText() : m.dmesg, m.millis, m.id);
				
			} catch (Exception x) { }
			return null;
		}
	}
}
