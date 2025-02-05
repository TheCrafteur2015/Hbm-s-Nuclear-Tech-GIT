package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.container.ContainerFirebox;
import com.hbm.inventory.gui.GUIFirebox;
import com.hbm.lib.RefStrings;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IConfigurableMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityHeaterFirebox extends TileEntityFireboxBase implements IConfigurableMachine {

	public static int baseHeat = 100;
	public static double timeMult = 1D;
	public static int maxHeatEnergy = 100_000;
	public static ModuleBurnTime burnModule = new ModuleBurnTime()
			.setLigniteTimeMod(1.25)
			.setCoalTimeMod(1.25)
			.setCokeTimeMod(1.25)
			.setSolidTimeMod(1.5)
			.setRocketTimeMod(1.5)
			.setBalefireTimeMod(0.5)

			.setLigniteHeatMod(2)
			.setCoalHeatMod(2)
			.setCokeHeatMod(2)
			.setSolidHeatMod(3)
			.setRocketHeatMod(5)
			.setBalefireHeatMod(15);

	public TileEntityHeaterFirebox() {
		super();
	}

	@Override
	public String getName() {
		return "container.heaterFirebox";
	}

	@Override
	public ModuleBurnTime getModule() {
		return TileEntityHeaterFirebox.burnModule;
	}

	@Override
	public int getBaseHeat() {
		return TileEntityHeaterFirebox.baseHeat;
	}

	@Override
	public double getTimeMult() {
		return TileEntityHeaterFirebox.timeMult;
	}

	@Override
	public int getMaxHeat() {
		return TileEntityHeaterFirebox.maxHeatEnergy;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFirebox(player.inventory, this);
	}

	@SideOnly(Side.CLIENT) private ResourceLocation texture;
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(this.texture == null) this.texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_firebox.png");
		return new GUIFirebox(player.inventory, this, this.texture);
	}

	@Override
	public String getConfigName() {
		return "firebox";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityHeaterFirebox.baseHeat = IConfigurableMachine.grab(obj, "I:baseHeat", TileEntityHeaterFirebox.baseHeat);
		TileEntityHeaterFirebox.timeMult = IConfigurableMachine.grab(obj, "D:burnTimeMult", TileEntityHeaterFirebox.timeMult);
		TileEntityHeaterFirebox.maxHeatEnergy = IConfigurableMachine.grab(obj, "I:heatCap", TileEntityHeaterFirebox.maxHeatEnergy);
		if(obj.has("burnModule")) {
			TileEntityHeaterFirebox.burnModule.readIfPresent(obj.get("M:burnModule").getAsJsonObject());
		}
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:baseHeat").value(TileEntityHeaterFirebox.baseHeat);
		writer.name("D:burnTimeMult").value(TileEntityHeaterFirebox.timeMult);
		writer.name("I:heatCap").value(TileEntityHeaterFirebox.maxHeatEnergy);
		writer.name("M:burnModule").beginObject();
		TileEntityHeaterFirebox.burnModule.writeConfig(writer);
		writer.endObject();
	}
}
