package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.container.ContainerFirebox;
import com.hbm.inventory.gui.GUIFirebox;
import com.hbm.lib.RefStrings;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IConfigurableMachine;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityHeaterOven extends TileEntityFireboxBase implements IConfigurableMachine {

	public static int baseHeat = 500;
	public static double timeMult = 0.125D;
	public static int maxHeatEnergy = 500_000;
	public static double heatEff = 0.5D;
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

	public TileEntityHeaterOven() {
		super();
	}

	@Override
	public String getName() {
		return "container.heaterOven";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			tryPullHeat();
		}
		
		super.updateEntity();
	}
	
	protected void tryPullHeat() {
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int toPull = Math.max(Math.min(source.getHeatStored(), getMaxHeat() - this.heatEnergy), 0);
			this.heatEnergy += toPull * TileEntityHeaterOven.heatEff;
			source.useUpHeat(toPull);
		}
	}

	@Override
	public ModuleBurnTime getModule() {
		return TileEntityHeaterOven.burnModule;
	}

	@Override
	public int getBaseHeat() {
		return TileEntityHeaterOven.baseHeat;
	}

	@Override
	public double getTimeMult() {
		return TileEntityHeaterOven.timeMult;
	}

	@Override
	public int getMaxHeat() {
		return TileEntityHeaterOven.maxHeatEnergy;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFirebox(player.inventory, this);
	}

	@SideOnly(Side.CLIENT) private ResourceLocation texture;
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(this.texture == null) this.texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_heating_oven.png");
		return new GUIFirebox(player.inventory, this, this.texture);
	}

	@Override
	public String getConfigName() {
		return "heatingoven";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityHeaterOven.baseHeat = IConfigurableMachine.grab(obj, "I:baseHeat", TileEntityHeaterOven.baseHeat);
		TileEntityHeaterOven.timeMult = IConfigurableMachine.grab(obj, "D:burnTimeMult", TileEntityHeaterOven.timeMult);
		TileEntityHeaterOven.heatEff = IConfigurableMachine.grab(obj, "D:heatPullEff", TileEntityHeaterOven.heatEff);
		TileEntityHeaterOven.maxHeatEnergy = IConfigurableMachine.grab(obj, "I:heatCap", TileEntityHeaterOven.maxHeatEnergy);
		if(obj.has("burnModule")) {
			TileEntityHeaterOven.burnModule.readIfPresent(obj.get("M:burnModule").getAsJsonObject());
		}
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:baseHeat").value(TileEntityHeaterOven.baseHeat);
		writer.name("D:burnTimeMult").value(TileEntityHeaterOven.timeMult);
		writer.name("D:heatPullEff").value(TileEntityHeaterOven.heatEff);
		writer.name("I:heatCap").value(TileEntityHeaterOven.maxHeatEnergy);
		writer.name("M:burnModule").beginObject();
		TileEntityHeaterOven.burnModule.writeConfig(writer);
		writer.endObject();
	}
}
