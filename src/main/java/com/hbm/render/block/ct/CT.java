package com.hbm.render.block.ct;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class CT {
	
	/* LEXICAL ORDER */
	// 1 2 3
	// 4 # 5
	// 6 7 8
	
	/* ROTATIONAL ORDER */
	// 1 2 3
	// 8 # 4
	// 7 6 5

	public static final int l = 0;	//left
	public static final int r = 1;	//right
	public static final int t = 0;	//top
	public static final int b = 2;	//bottom
	public static final int f = 0;	//full/unconnected
	public static final int c = 4;	//connected
	public static final int j = 8;	//junction
	public static final int h = 12;	//horizontal
	public static final int v = 16;	//vertical
	
	public static final int ftl = CT.f | CT.t | CT.l;
	public static final int ftr = CT.f | CT.t | CT.r;
	public static final int fbl = CT.f | CT.b | CT.l;
	public static final int fbr = CT.f | CT.b | CT.r;
	public static final int ctl = CT.c | CT.t | CT.l;
	public static final int ctr = CT.c | CT.t | CT.r;
	public static final int cbl = CT.c | CT.b | CT.l;
	public static final int cbr = CT.c | CT.b | CT.r;
	public static final int jtl = CT.j | CT.t | CT.l;
	public static final int jtr = CT.j | CT.t | CT.r;
	public static final int jbl = CT.j | CT.b | CT.l;
	public static final int jbr = CT.j | CT.b | CT.r;
	public static final int htl = CT.h | CT.t | CT.l;
	public static final int htr = CT.h | CT.t | CT.r;
	public static final int hbl = CT.h | CT.b | CT.l;
	public static final int hbr = CT.h | CT.b | CT.r;
	public static final int vtl = CT.v | CT.t | CT.l;
	public static final int vtr = CT.v | CT.t | CT.r;
	public static final int vbl = CT.v | CT.b | CT.l;
	public static final int vbr = CT.v | CT.b | CT.r;

	public static boolean isL(int i) {
		return (i & CT.l) != 0;
	}
	public static boolean isR(int i) {
		return (i & CT.r) != 0;
	}
	public static boolean isT(int i) {
		return (i & CT.t) != 0;
	}
	public static boolean isB(int i) {
		return (i & CT.b) != 0;
	}
	
	public static boolean isF(int i) {
		return i >= CT.f && i < CT.f + 4;
	}
	public static boolean isC(int i) {
		return i >= CT.c && i < CT.c + 4;
	}
	public static boolean isJ(int i) {
		return i >= CT.j && i < CT.j + 4;
	}
	public static boolean isH(int i) {
		return i >= CT.h && i < CT.h + 4;
	}
	public static boolean isV(int i) {
		return i >= CT.v && i < CT.v + 4;
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
	/*  _____________________
	 * / I am in great pain. \
	 * \   Please kill me.   /
	 *  ---------------------
	 *    \
	 *     \        \
	 *      \       _\^
	 *       \    _- oo\
	 *            \---- \______
	 *                 \       )\
	 *                 ||-----|| \
	 *                 ||     ||
	 */

}
