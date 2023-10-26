package com.hbm.render.loader;

public class HmfController {
	
	public static double modoloMod = 100000D;
	public static double quotientMod = 5000D;
	
	public static void setMod(double modolo, double quotient) {
		HmfController.modoloMod = modolo;
		HmfController.quotientMod = quotient;
	}
	
	public static void resetMod() {
		HmfController.modoloMod = 100000D;
		HmfController.quotientMod = 5000D;
	}

}
