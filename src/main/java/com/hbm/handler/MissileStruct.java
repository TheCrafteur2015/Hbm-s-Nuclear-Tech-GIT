package com.hbm.handler;

import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.PartType;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MissileStruct {

	public ItemMissile warhead;
	public ItemMissile fuselage;
	public ItemMissile fins;
	public ItemMissile thruster;
	
	public MissileStruct() { }
	
	public MissileStruct(ItemStack w, ItemStack f, ItemStack s, ItemStack t) {

		if(w != null && w.getItem() instanceof ItemMissile)
			this.warhead = (ItemMissile) w.getItem();
		if(f != null && f.getItem() instanceof ItemMissile)
			this.fuselage = (ItemMissile) f.getItem();
		if(s != null && s.getItem() instanceof ItemMissile)
			this.fins = (ItemMissile) s.getItem();
		if(t != null && t.getItem() instanceof ItemMissile)
			this.thruster = (ItemMissile) t.getItem();
	}
	
	public MissileStruct(Item w, Item f, Item s, Item t) {

		if(w instanceof ItemMissile)
			this.warhead = (ItemMissile) w;
		if(f instanceof ItemMissile)
			this.fuselage = (ItemMissile) f;
		if(s instanceof ItemMissile)
			this.fins = (ItemMissile) s;
		if(t instanceof ItemMissile)
			this.thruster = (ItemMissile) t;
	}
	
	public void writeToByteBuffer(ByteBuf buf) {


		if(this.warhead != null && this.warhead.type == PartType.WARHEAD)
			buf.writeInt(Item.getIdFromItem(this.warhead));
		else
			buf.writeInt(0);
		
		if(this.fuselage != null && this.fuselage.type == PartType.FUSELAGE)
			buf.writeInt(Item.getIdFromItem(this.fuselage));
		else
			buf.writeInt(0);
		
		if(this.fins != null && this.fins.type == PartType.FINS)
			buf.writeInt(Item.getIdFromItem(this.fins));
		else
			buf.writeInt(0);
		
		if(this.thruster != null && this.thruster.type == PartType.THRUSTER)
			buf.writeInt(Item.getIdFromItem(this.thruster));
		else
			buf.writeInt(0);
	}
	
	public static MissileStruct readFromByteBuffer(ByteBuf buf) {
		
		MissileStruct multipart = new MissileStruct();

		int w = buf.readInt();
		int f = buf.readInt();
		int s = buf.readInt();
		int t = buf.readInt();
		
		if(w != 0)
			multipart.warhead = (ItemMissile) Item.getItemById(w);
		
		if(f != 0)
			multipart.fuselage = (ItemMissile) Item.getItemById(f);
		
		if(s != 0)
			multipart.fins = (ItemMissile) Item.getItemById(s);
		
		if(t != 0)
			multipart.thruster = (ItemMissile) Item.getItemById(t);
		
		return multipart;
	}

}
