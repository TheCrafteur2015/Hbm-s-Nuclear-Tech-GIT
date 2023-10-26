package com.hbm.tileentity.machine.rbmk;

import com.hbm.inventory.container.ContainerRBMKStorage;
import com.hbm.inventory.gui.GUIRBMKStorage;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKStorage extends TileEntityRBMKSlottedBase implements IRBMKLoadable, SimpleComponent {

	public TileEntityRBMKStorage() {
		super(12);
	}

	@Override
	public String getName() {
		return "container.rbmkStorage";
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 10 == 0) {
			
			for(int i = 0; i < this.slots.length - 1; i++) {
				
				if(this.slots[i] == null && this.slots[i + 1] != null) {
					this.slots[i] = this.slots[i + 1];
					this.slots[i + 1] = null;
				}
			}
		}
		
		super.updateEntity();
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.STORAGE;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemRBMKRod;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	}

	@Override
	public boolean canLoad(ItemStack toLoad) {
		return this.slots[11] == null;
	}

	@Override
	public void load(ItemStack toLoad) {
		this.slots[11] = toLoad.copy();
	}

	@Override
	public boolean canUnload() {
		return this.slots[0] != null;
	}

	@Override
	public ItemStack provideNext() {
		return this.slots[0];
	}

	@Override
	public void unload() {
		this.slots[0] = null;
	}

	@Override
	public String getComponentName() {
		return "rbmk_storage_rod";
	}
	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {this.xCoord, this.yCoord, this.zCoord};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {this.heat};
	}


	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getStored(Context context, Arguments args) {
		return new Object[] {this.slots[0], this.slots[1], this.slots[2], this.slots[3], this.slots[4], this.slots[5], this.slots[6], this.slots[7], this.slots[8], this.slots[9], this.slots[10], this.slots[11]};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {this.heat, this.slots[0], this.slots[1], this.slots[2], this.slots[3], this.slots[4], this.slots[5], this.slots[6], this.slots[7], this.slots[8], this.slots[9], this.slots[10], this.slots[11], this.xCoord, this.yCoord, this.zCoord};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKStorage(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKStorage(player.inventory, this);
	}
}
