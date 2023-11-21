package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.fluid.tank.FluidTank;

import com.hbm.util.I18nUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class FT_VentRadiation extends FluidTrait {
	
	float radPerMB = 0;
	
	public FT_VentRadiation() { }
	
	public FT_VentRadiation(float rad) {
		this.radPerMB = rad;
	}
	
	public float getRadPerMB() {
		return this.radPerMB;
	}
	
	@Override
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount) {
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, overflowAmount * this.radPerMB);
	}
	
	@Override
	public void addInfo(List<String> info) {
		info.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("hbmfluid.Trait.VentRadiation"));
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("radiation").value(this.radPerMB);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.radPerMB = obj.get("radiation").getAsFloat();
	}
}
