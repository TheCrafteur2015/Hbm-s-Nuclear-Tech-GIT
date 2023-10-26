package com.hbm.packet;

import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TEMissilePacket implements IMessage {

	int x;
	int y;
	int z;
	int type;

	public TEMissilePacket() { }

	@Spaghetti("die")
	public TEMissilePacket(int x, int y, int z, ItemStack stack) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = 0;
		if(stack != null) {
			if(stack.getItem() == ModItems.missile_generic)
				this.type = 1;
			if(stack.getItem() == ModItems.missile_strong)
				this.type = 2;
			if(stack.getItem() == ModItems.missile_cluster)
				this.type = 3;
			if(stack.getItem() == ModItems.missile_nuclear)
				this.type = 4;
			if(stack.getItem() == ModItems.missile_incendiary)
				this.type = 5;
			if(stack.getItem() == ModItems.missile_buster)
				this.type = 6;
			if(stack.getItem() == ModItems.missile_incendiary_strong)
				this.type = 7;
			if(stack.getItem() == ModItems.missile_cluster_strong)
				this.type = 8;
			if(stack.getItem() == ModItems.missile_buster_strong)
				this.type = 9;
			if(stack.getItem() == ModItems.missile_burst)
				this.type = 10;
			if(stack.getItem() == ModItems.missile_inferno)
				this.type = 11;
			if(stack.getItem() == ModItems.missile_rain)
				this.type = 12;
			if(stack.getItem() == ModItems.missile_drill)
				this.type = 13;
			if(stack.getItem() == ModItems.missile_endo)
				this.type = 14;
			if(stack.getItem() == ModItems.missile_exo)
				this.type = 15;
			if(stack.getItem() == ModItems.missile_nuclear_cluster)
				this.type = 16;
			if(stack.getItem() == ModItems.missile_doomsday)
				this.type = 17;
			if(stack.getItem() == ModItems.missile_taint)
				this.type = 18;
			if(stack.getItem() == ModItems.missile_micro)
				this.type = 19;
			if(stack.getItem() == ModItems.missile_carrier)
				this.type = 20;
			if(stack.getItem() == ModItems.missile_anti_ballistic)
				this.type = 21;
			if(stack.getItem() == ModItems.missile_bhole)
				this.type = 22;
			if(stack.getItem() == ModItems.missile_schrabidium)
				this.type = 23;
			if(stack.getItem() == ModItems.missile_emp)
				this.type = 24;
			if(stack.getItem() == ModItems.missile_emp_strong)
				this.type = 25;
			if(stack.getItem() == ModItems.missile_volcano)
				this.type = 26;
			if(stack.getItem() == ModItems.missile_shuttle)
				this.type = 27;
			
		}
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

	public static class Handler implements IMessageHandler<TEMissilePacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEMissilePacket m, MessageContext ctx) {
			
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te != null && te instanceof TileEntityLaunchPad) {
						
					TileEntityLaunchPad gen = (TileEntityLaunchPad)te;
					gen.state = m.type;
				}
			} catch(Exception e) { }
			
			return null;
		}
	}
}
