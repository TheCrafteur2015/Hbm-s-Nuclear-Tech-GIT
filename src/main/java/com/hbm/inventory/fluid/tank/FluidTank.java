package com.hbm.inventory.fluid.tank;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

@SuppressWarnings("deprecation")
public class FluidTank {
	
	public static final List<FluidLoadingHandler> loadingHandlers = new ArrayList<>();
	
	static {
		FluidTank.loadingHandlers.add(new FluidLoaderStandard());
		FluidTank.loadingHandlers.add(new FluidLoaderFillableItem());
		FluidTank.loadingHandlers.add(new FluidLoaderInfinite());
	}
	
	FluidType type;
	int fluid;
	int maxFluid;
	@Deprecated public int index = 0;
	int pressure = 0;
	
	public FluidTank(FluidType type, int maxFluid) {
		this.type = type;
		this.maxFluid = maxFluid;
	}
	
	public FluidTank withPressure(int pressure) {
		
		if(this.pressure != pressure) setFill(0);
		
		this.pressure = pressure;
		return this;
	}
	
	@Deprecated // indices are no longer needed
	public FluidTank(FluidType type, int maxFluid, int index) {
		this.type = type;
		this.maxFluid = maxFluid;
		this.index = index;
	}
	
	public void setFill(int i) {
		this.fluid = i;
	}
	
	public void setTankType(FluidType type) {
		
		if(type == null) {
			type = Fluids.NONE;
		}
		
		if(this.type == type)
			return;
		
		this.type = type;
		setFill(0);
	}
	
	public FluidType getTankType() {
		return this.type;
	}
	
	public int getFill() {
		return this.fluid;
	}
	
	public int getMaxFill() {
		return this.maxFluid;
	}
	
	public int getPressure() {
		return this.pressure;
	}
	
	public int changeTankSize(int size) {
		this.maxFluid = size;
		
		if(this.fluid > this.maxFluid) {
			int dif = this.fluid - this.maxFluid;
			this.fluid = this.maxFluid;
			return dif;
		}
			
		return 0;
	}
	
	//Called on TE update
	@Deprecated public void updateTank(TileEntity te) {
		updateTank(te, 100);
	}
	@Deprecated public void updateTank(TileEntity te, int range) {
		updateTank(te.xCoord, te.yCoord, te.zCoord, te.getWorldObj().provider.dimensionId, range);
	}
	@Deprecated public void updateTank(int x, int y, int z, int dim) {
		updateTank(x, y, z, dim, 100);
	}
	@Deprecated public void updateTank(int x, int y, int z, int dim, int range) {
		PacketDispatcher.wrapper.sendToAllAround(new TEFluidPacket(x, y, z, this.fluid, this.index, this.type), new TargetPoint(dim, x, y, z, range));
	}
	
	//Fills tank from canisters
	public boolean loadTank(int in, int out, ItemStack[] slots) {
		
		if((slots[in] == null) || (this.pressure != 0)) return false; //for now, canisters can only be loaded from high-pressure tanks, not unloaded
		
		int prev = getFill();
		
		for(FluidLoadingHandler handler : FluidTank.loadingHandlers) {
			if(handler.emptyItem(slots, in, out, this)) {
				break;
			}
		}
		
		return getFill() > prev;
	}
	
	//Fills canisters from tank
	public boolean unloadTank(int in, int out, ItemStack[] slots) {
		
		if(slots[in] == null)
			return false;
		
		int prev = getFill();
		
		for(FluidLoadingHandler handler : FluidTank.loadingHandlers) {
			if(handler.fillItem(slots, in, out, this)) {
				break;
			}
		}
		
		return getFill() < prev;
	}

	public boolean setType(int in, ItemStack[] slots) {
		return setType(in, in, slots);
	}
	
	/**
	 * Changes the tank type and returns true if successful
	 * @param in
	 * @param out
	 * @param slots
	 * @return
	 */
	public boolean setType(int in, int out, ItemStack[] slots) {
		
		if(slots[in] != null && slots[in].getItem() instanceof IItemFluidIdentifier) {
			IItemFluidIdentifier id = (IItemFluidIdentifier) slots[in].getItem();
			
			if(in == out) {
				FluidType newType = id.getType(null, 0, 0, 0, slots[in]);
				
				if(this.type != newType) {
					this.type = newType;
					this.fluid = 0;
					return true;
				}
				
			} else if(slots[out] == null) {
				FluidType newType = id.getType(null, 0, 0, 0, slots[in]);
				if(this.type != newType) {
					this.type = newType;
					slots[out] = slots[in].copy();
					slots[in] = null;
					this.fluid = 0;
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Renders the fluid texture into a GUI, with the height based on the fill state
	 * @param x the tank's left side
	 * @param y the tank's bottom side (convention from the old system, changing it now would be a pain in the ass)
	 * @param z the GUI's zLevel
	 * @param width
	 * @param height
	 */
	public void renderTank(int x, int y, double z, int width, int height) {
		renderTank(x, y, z, width, height, 0);
	}
	
	public void renderTank(int x, int y, double z, int width, int height, int orientation) {

		GL11.glEnable(GL11.GL_BLEND);
		
		int color = this.type.getTint();
		double r = ((color & 0xff0000) >> 16) / 255D;
		double g = ((color & 0x00ff00) >> 8) / 255D;
		double b = ((color & 0x0000ff) >> 0) / 255D;
		GL11.glColor3d(r, g, b);

		y -= height;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.type.getTexture());
		
		int i = (this.fluid * height) / this.maxFluid;
		
		double minX = x;
		double maxX = x;
		double minY = y;
		double maxY = y;
		
		double minV = 1D - i / 16D;
		double maxV = 1D;
		double minU = 0D;
		double maxU = width / 16D;
		
		if(orientation == 0) {
			maxX += width;
			minY += height - i;
			maxY += height;
		}
		
		if(orientation == 1) {
			i = (this.fluid * width) / this.maxFluid;
			maxX += i;
			maxY += height;
			
			minV = 0;
			maxV = height / 16D;
			minU = 0D;
			maxU = width / 16D;
		}
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(minX, maxY, z, minU, maxV);
		tessellator.addVertexWithUV(maxX, maxY, z, maxU, maxV);
		tessellator.addVertexWithUV(maxX, minY, z, maxU, minV);
		tessellator.addVertexWithUV(minX, minY, z, minU, minV);
		tessellator.draw();

		GL11.glColor3d(1D, 1D, 1D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void renderTankInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			
			List<String> list = new ArrayList<>();
			list.add(this.type.getLocalizedName());
			list.add(this.fluid + "/" + this.maxFluid + "mB");
			
			if(this.pressure != 0) {
				list.add(EnumChatFormatting.RED + "Pressure: " + this.pressure + " PU");
			}
			
			this.type.addInfo(list);
			gui.drawInfo(list.toArray(new String[0]), mouseX, mouseY);
		}
	}

	//Called by TE to save fillstate
	public void writeToNBT(NBTTagCompound nbt, String s) {
		nbt.setInteger(s, this.fluid);
		nbt.setInteger(s + "_max", this.maxFluid);
		nbt.setInteger(s + "_type", this.type.getID());
		nbt.setShort(s + "_p", (short) this.pressure);
	}
	
	//Called by TE to load fillstate
	public void readFromNBT(NBTTagCompound nbt, String s) {
		this.fluid = nbt.getInteger(s);
		int max = nbt.getInteger(s + "_max");
		if(max > 0)
			this.maxFluid = max;
		
		this.fluid = MathHelper.clamp_int(this.fluid, 0, max);
		
		this.type = Fluids.fromName(nbt.getString(s + "_type")); //compat
		if(this.type == Fluids.NONE)
			this.type = Fluids.fromID(nbt.getInteger(s + "_type"));
		
		this.pressure = nbt.getShort(s + "_p");
	}

}
