package com.hbm.world.generator;

import com.hbm.world.generator.room.JungleDungeonRoom;
import com.hbm.world.generator.room.JungleDungeonRoomArrow;
import com.hbm.world.generator.room.JungleDungeonRoomArrowFire;
import com.hbm.world.generator.room.JungleDungeonRoomFire;
import com.hbm.world.generator.room.JungleDungeonRoomMagic;
import com.hbm.world.generator.room.JungleDungeonRoomMine;
import com.hbm.world.generator.room.JungleDungeonRoomPillar;
import com.hbm.world.generator.room.JungleDungeonRoomPoison;
import com.hbm.world.generator.room.JungleDungeonRoomRad;
import com.hbm.world.generator.room.JungleDungeonRoomRubble;
import com.hbm.world.generator.room.JungleDungeonRoomSlowness;
import com.hbm.world.generator.room.JungleDungeonRoomSpiders;
import com.hbm.world.generator.room.JungleDungeonRoomSpikes;
import com.hbm.world.generator.room.JungleDungeonRoomWeakness;
import com.hbm.world.generator.room.JungleDungeonRoomWeb;
import com.hbm.world.generator.room.JungleDungeonRoomZombie;
import com.hbm.world.generator.room.TestDungeonRoom1;
import com.hbm.world.generator.room.TestDungeonRoom2;
import com.hbm.world.generator.room.TestDungeonRoom3;
import com.hbm.world.generator.room.TestDungeonRoom4;
import com.hbm.world.generator.room.TestDungeonRoom5;
import com.hbm.world.generator.room.TestDungeonRoom6;
import com.hbm.world.generator.room.TestDungeonRoom7;
import com.hbm.world.generator.room.TestDungeonRoom8;

import net.minecraftforge.common.util.ForgeDirection;

public class CellularDungeonFactory {

	public static CellularDungeon meteor;
	public static CellularDungeon jungle;
	
	public static void init() {
		
		CellularDungeonFactory.meteor = new TestDungeon(11, 7, 11, 11, 150, 3);
		CellularDungeonFactory.meteor.rooms.add(new TestDungeonRoom1(CellularDungeonFactory.meteor));
		CellularDungeonFactory.meteor.rooms.add(new TestDungeonRoom2(CellularDungeonFactory.meteor));
		CellularDungeonFactory.meteor.rooms.add(new TestDungeonRoom3(CellularDungeonFactory.meteor));
		CellularDungeonFactory.meteor.rooms.add(new TestDungeonRoom4(CellularDungeonFactory.meteor, new TestDungeonRoom5(CellularDungeonFactory.meteor), ForgeDirection.NORTH));
		CellularDungeonFactory.meteor.rooms.add(new TestDungeonRoom6(CellularDungeonFactory.meteor));
		CellularDungeonFactory.meteor.rooms.add(new TestDungeonRoom7(CellularDungeonFactory.meteor));
		CellularDungeonFactory.meteor.rooms.add(new TestDungeonRoom8(CellularDungeonFactory.meteor));
		
		CellularDungeonFactory.jungle = new JungleDungeon(5, 5, 25, 25, 700, 6);
		for(int i = 0; i < 10; i++) CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoom(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomArrow(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomArrowFire(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomFire(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomMagic(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomMine(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomPillar(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomPoison(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomRad(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomRubble(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomSlowness(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomSpiders(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomSpikes(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomWeakness(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomWeb(CellularDungeonFactory.jungle));
		CellularDungeonFactory.jungle.rooms.add(new JungleDungeonRoomZombie(CellularDungeonFactory.jungle));
	}

}
