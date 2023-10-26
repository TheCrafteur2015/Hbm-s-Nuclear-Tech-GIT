package com.hbm.render.icon;

import java.awt.image.BufferedImage;

public class RGBMutatorInterpolatedComponentRemap implements RGBMutator {

	int sourceLight;
	int sourceDark;
	int targetLight;
	int targetDark;
	
	public RGBMutatorInterpolatedComponentRemap(int sourceLight, int sourceDark, int targetLight, int targetDark) {
		this.sourceLight = sourceLight;
		this.sourceDark = sourceDark;
		this.targetLight = targetLight;
		this.targetDark = targetDark;
	}

	@Override
	public void mutate(BufferedImage image, int frame, int frameCount) {
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				int pix = image.getRGB(x, y);
				int rgb = RGBMutatorInterpolatedComponentRemap.shiftColor(this.sourceLight, this.sourceDark, this.targetLight, this.targetDark, pix);
				image.setRGB(x, y, rgb);
			}
		}
	}
	
	private static int shiftColor(int boundLighter, int boundDarker, int lighter, int darker, int pix) {
		
		int a = (pix & 0xff000000) >> 24;
		int r = (pix & 0xff0000) >> 16;
		int g = (pix & 0xff00) >> 8;
		int b = (pix & 0xff);
		
		int nR = (int) RGBMutatorInterpolatedComponentRemap.shiftComponent(RGBMutatorInterpolatedComponentRemap.compR(lighter), RGBMutatorInterpolatedComponentRemap.compR(darker), RGBMutatorInterpolatedComponentRemap.compR(boundLighter), RGBMutatorInterpolatedComponentRemap.compR(boundDarker), r);
		int nG = (int) RGBMutatorInterpolatedComponentRemap.shiftComponent(RGBMutatorInterpolatedComponentRemap.compG(lighter), RGBMutatorInterpolatedComponentRemap.compG(darker), RGBMutatorInterpolatedComponentRemap.compG(boundLighter), RGBMutatorInterpolatedComponentRemap.compG(boundDarker), g);
		int nB = (int) RGBMutatorInterpolatedComponentRemap.shiftComponent(RGBMutatorInterpolatedComponentRemap.compB(lighter), RGBMutatorInterpolatedComponentRemap.compB(darker), RGBMutatorInterpolatedComponentRemap.compB(boundLighter), RGBMutatorInterpolatedComponentRemap.compB(boundDarker), b);
		
		r = nR & 0xff;
		g = nG & 0xff;
		b = nB & 0xff;
		
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
	
	private static double shiftComponent(int lighter, int darker, int boundLighter, int boundDarker, int component) {
		double scaledComponent = RGBMutatorInterpolatedComponentRemap.getPosFromComp(boundLighter, boundDarker, component);
		return RGBMutatorInterpolatedComponentRemap.getCompFromFunc(lighter, darker, scaledComponent);
	}
	
	private static double getCompFromFunc(int lower, int upper, double interp) {
		double d0 = (double) lower;
		double d1 = (double) upper;
		
		return d0 + interp * (d1 - d0);
	}
	
	private static double getPosFromComp(int lower, int upper, double val) {
		double d0 = (double) lower;
		double d1 = (double) upper;
		
		return (val - d0) / (d1 - d0);
	}

	private static int compR(int col) { return (col & 0xff0000) >> 16; }
	private static int compG(int col) { return (col & 0xff00) >> 8; }
	private static int compB(int col) { return (col & 0xff); }
}
