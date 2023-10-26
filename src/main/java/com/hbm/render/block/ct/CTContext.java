package com.hbm.render.block.ct;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class CTContext {
	
	public static CTFace[] faces;
	
	//dim 1: 6x faces (forgeDir)
	//dim 2: 8x neighbors (TL, TC, TR, CL, CR, BL, BC, BR)
	//dim 3: 3x coord (x/y/z, [-1;1])
	public static int[][][] access = new int[][][] {
		CTContext.lcfs(ForgeDirection.SOUTH, ForgeDirection.WEST),	//DOWN guess
		CTContext.lcfs(ForgeDirection.NORTH, ForgeDirection.WEST),	//UP guess
		CTContext.lcfs(ForgeDirection.UP, ForgeDirection.EAST),		//NORTH
		CTContext.lcfs(ForgeDirection.UP, ForgeDirection.WEST),		//SOUTH
		CTContext.lcfs(ForgeDirection.UP, ForgeDirection.NORTH),		//WEST
		CTContext.lcfs(ForgeDirection.UP, ForgeDirection.SOUTH)		//EAST
	};
	
	//lexical coordinates from side
	private static int[][] lcfs(ForgeDirection up, ForgeDirection left) {
		
		ForgeDirection down = up.getOpposite();
		ForgeDirection right = left.getOpposite();
		
		int[][] lexicalCoordinates = new int[][] {
			CTContext.cfs(up, left),
			CTContext.cfs(up),
			CTContext.cfs(up, right),
			CTContext.cfs(left),
			CTContext.cfs(right),
			CTContext.cfs(down, left),
			CTContext.cfs(down),
			CTContext.cfs(down, right),
		};
		
		return lexicalCoordinates;
	}
	
	//coordinates from sides
	private static int[] cfs(ForgeDirection... dirs) {
		int x = 0;
		int y = 0;
		int z = 0;
		
		for(ForgeDirection dir : dirs) {
			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
		}
		
		return new int[] {x, y, z};
	}
	
	/**
	 * Generates the six CTFaces and fills the faces array, the faces contain the information on what icon types should be used
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 */
	public static void loadContext(IBlockAccess world, int x, int y, int z, Block block) {
		
		CTContext.faces = new CTFace[6];
		
		for(int i = 0; i < 6; i++) {
			
			boolean[] cons = new boolean[8];
			int[][] dirs = CTContext.access[i];
			
			for(int j = 0; j < 8; j++) {
				
				int[] coord = dirs[j];
				Block neighbor = world.getBlock(x + coord[0], y + coord[1], z + coord[2]);
				
				if(((IBlockCT) block).canConnect(world, x + coord[0], y + coord[1], z + coord[2], neighbor)) {
					cons[j] = true;
				}
			}
			
			/*
			 * 0 1 2
			 * 3   4
			 * 5 6 7
			 */
			
			int itl = CT.t | CT.l | CTContext.cornerType(cons[3], cons[0], cons[1]);
			int itr = CT.t | CT.r | CTContext.cornerType(cons[4], cons[2], cons[1]);
			int ibl = CT.b | CT.l | CTContext.cornerType(cons[3], cons[5], cons[6]);
			int ibr = CT.b | CT.r | CTContext.cornerType(cons[4], cons[7], cons[6]);
			
			CTContext.faces[i] = new CTFace(world, x, y, z, (IBlockCT)block, itl, itr, ibl, ibr);
		}
	}
	
	/**
	 * Returns the overarching texture type based on whether the horizontal, vertical and corner are connected
	 * @param hor
	 * @param corner
	 * @param vert
	 * @return the bitmask for the full texture type that should be used (f/c/j/h/v)
	 */
	public static int cornerType(boolean hor, boolean corner, boolean vert) {
		
		/*
		 * C - V
		 * |
		 * H
		 */
		
		if(vert && hor && corner)
			return CT.c;
		else if(vert && hor)
			return CT.j;
		else if(vert)
			return CT.v;
		else if(hor)
			return CT.h;
		else
			return CT.f;
	}
	
	/**
	 * @return whether there's currently a context available
	 */
	public static boolean isContextLoaded() {
		return CTContext.faces != null;
	}
	
	/**
	 * Gets rid of the current context
	 */
	public static void dropContext() {
		CTContext.faces = null;
	}
	
	/**
	 * The buffered context information for a single side. Also stores the block itself for easier access.
	 * @author hbm
	 *
	 */
	public static class CTFace {

		IBlockAccess world;
		int x;
		int y;
		int z;
		IBlockCT ct;
		int index_tl;
		int index_tr;
		int index_bl;
		int index_br;
		
		public CTFace() { }
		
		public CTFace(IBlockAccess world, int x, int y, int z, IBlockCT block, int i, int j, int k, int l) {
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
			this.ct = block;
			this.index_tl = i;
			this.index_tr = j;
			this.index_bl = k;
			this.index_br = l;
		}
		
		public IIcon getTopLeft() { return this.ct.getFragments(this.world, this.x, this.y, this.z)[this.index_tl]; }
		public IIcon getTopRight() { return this.ct.getFragments(this.world, this.x, this.y, this.z)[this.index_tr]; }
		public IIcon getBottomLeft() { return this.ct.getFragments(this.world, this.x, this.y, this.z)[this.index_bl]; }
		public IIcon getBottomRight() { return this.ct.getFragments(this.world, this.x, this.y, this.z)[this.index_br]; }
	}
}
