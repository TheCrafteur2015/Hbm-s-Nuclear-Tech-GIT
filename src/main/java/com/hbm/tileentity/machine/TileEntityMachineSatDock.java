package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.inventory.container.ContainerSatDock;
import com.hbm.inventory.gui.GUISatDock;
import com.hbm.items.ISatChip;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteMiner;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.WeightedRandomObject;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public class TileEntityMachineSatDock extends TileEntity implements ISidedInventory, IGUIProvider {
    private ItemStack[] slots;

    private static final int[] access = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

    private String customName;

    private AxisAlignedBB renderBoundingBox;

    public TileEntityMachineSatDock() {
        this.slots = new ItemStack[16];
    }

    @Override
    public int getSizeInventory() {
        return this.slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.slots[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.slots[i] != null) {
            ItemStack itemStack = this.slots[i];
            this.slots[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.slots[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return hasCustomInventoryName() ? this.customName : "container.satDock";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
            return false;
        } else {
            return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64;
        }
    }

    //You scrubs aren't needed for anything (right now)
    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i != 2 && i != 3 && i != 4 && i != 5;
	}

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (this.slots[i] != null) {
            if (this.slots[i].stackSize <= j) {
                ItemStack itemStack = this.slots[i];
                this.slots[i] = null;
                return itemStack;
            }
            ItemStack itemStack1 = this.slots[i].splitStack(j);
            if (this.slots[i].stackSize == 0) {
                this.slots[i] = null;
            }

            return itemStack1;
        } else {
            return null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList list = nbt.getTagList("items", 10);

        this.slots = new ItemStack[getSizeInventory()];

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound nbt1 = list.getCompoundTagAt(i);
            byte b0 = nbt1.getByte("slot");
            if (b0 >= 0 && b0 < this.slots.length) {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < this.slots.length; i++) {
            if (this.slots[i] != null) {
                NBTTagCompound nbt1 = new NBTTagCompound();
                nbt1.setByte("slot", (byte) i);
                this.slots[i].writeToNBT(nbt1);
                list.appendTag(nbt1);
            }
        }
        nbt.setTag("items", list);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return TileEntityMachineSatDock.access;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, int j) {
        return isItemValidForSlot(i, itemStack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, int j) {
        return true;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            SatelliteSavedData data = SatelliteSavedData.getData(this.worldObj);

            if (this.slots[15] != null) {
                int freq = ISatChip.getFreqS(this.slots[15]);

                Satellite sat = data.getSatFromFreq(freq);

                int delay = 10 * 60 * 1000;

                if (sat instanceof SatelliteMiner) {
                    SatelliteMiner miner = (SatelliteMiner) sat;

                    if (miner.lastOp + delay < System.currentTimeMillis()) {
                        EntityMinerRocket rocket = new EntityMinerRocket(this.worldObj);
                        rocket.posX = this.xCoord + 0.5;
                        rocket.posY = 300;
                        rocket.posZ = this.zCoord + 0.5;

                        rocket.getDataWatcher().updateObject(17, freq);
                        this.worldObj.spawnEntityInWorld(rocket);
                        miner.lastOp = System.currentTimeMillis();
                        data.markDirty();
                    }
                }
            }

            @SuppressWarnings("unchecked")
            List<EntityMinerRocket> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
                    null,
                    AxisAlignedBB.getBoundingBox(this.xCoord - 0.25 + 0.5, this.yCoord + 0.75, this.zCoord - 0.25 + 0.5, this.xCoord + 0.25 + 0.5, this.yCoord + 2, this.zCoord + 0.25 + 0.5),
                    entity -> entity instanceof EntityMinerRocket
            );

            for (EntityMinerRocket rocket : list) {
                if (this.slots[15] != null && ISatChip.getFreqS(this.slots[15]) != rocket.getDataWatcher().getWatchableObjectInt(17)) {
                    rocket.setDead();
                    ExplosionNukeSmall.explode(this.worldObj, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, ExplosionNukeSmall.PARAMS_TOTS);
                    break;
                }

                if (rocket.getDataWatcher().getWatchableObjectInt(16) == 1 && rocket.timer == 50) {
                    Satellite sat = data.getSatFromFreq(ISatChip.getFreqS(this.slots[15]));
                    unloadCargo((SatelliteMiner) sat);
                }
            }

            ejectInto(this.xCoord + 2, this.yCoord, this.zCoord);
            ejectInto(this.xCoord - 2, this.yCoord, this.zCoord);
            ejectInto(this.xCoord, this.yCoord, this.zCoord + 2);
            ejectInto(this.xCoord, this.yCoord, this.zCoord - 2);
        }
    }

    private void unloadCargo(SatelliteMiner satellite) {
        int itemAmount = this.worldObj.rand.nextInt(6) + 10;

        WeightedRandomObject[] cargo = satellite.getCargo();

        for (int i = 0; i < itemAmount; i++) {
            ItemStack stack = ((WeightedRandomObject) WeightedRandom.getRandomItem(this.worldObj.rand, cargo)).asStack();
            addToInv(stack.copy());
        }
    }

    private void addToInv(ItemStack stack) {
        for (int i = 0; i < 15; i++) {
            if (this.slots[i] != null && this.slots[i].getItem() == stack.getItem() && this.slots[i].getItemDamage() == stack.getItemDamage() && this.slots[i].stackSize < this.slots[i].getMaxStackSize()) {
                int toAdd = Math.min(this.slots[i].getMaxStackSize() - this.slots[i].stackSize, stack.stackSize);

                this.slots[i].stackSize += toAdd;
                stack.stackSize -= toAdd;

                if (stack.stackSize <= 0) return;
            }
        }

        for (int i = 0; i < 15; i++) {
            if (this.slots[i] == null) {
                this.slots[i] = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
                return;
            }
        }
    }

    private void ejectInto(int x, int y, int z) {
        TileEntity te = this.worldObj.getTileEntity(x, y, z);

        if (te instanceof IInventory) {
            IInventory chest = (IInventory) te;

            for (int i = 0; i < 15; i++) {
                if (this.slots[i] != null) {
                    for (int j = 0; j < chest.getSizeInventory(); j++) {
                        ItemStack sta = this.slots[i].copy();
                        sta.stackSize = 1;

                        if (chest.getStackInSlot(j) != null && chest.getStackInSlot(j).isItemEqual(this.slots[i]) && ItemStack.areItemStackTagsEqual(chest.getStackInSlot(j), this.slots[i]) &&
                                chest.getStackInSlot(j).stackSize < chest.getStackInSlot(j).getMaxStackSize()) {

                            this.slots[i].stackSize--;

                            if (this.slots[i].stackSize <= 0)
                                this.slots[i] = null;

                            chest.getStackInSlot(j).stackSize++;
                            return;
                        }
                    }
                }
            }

            for (int i = 0; i < 15; i++) {
                if (this.slots[i] != null) {
                    for (int j = 0; j < chest.getSizeInventory(); j++) {
                        ItemStack sta = this.slots[i].copy();
                        sta.stackSize = 1;

                        if (chest.getStackInSlot(j) == null && chest.isItemValidForSlot(j, sta)) {
                            this.slots[i].stackSize--;

                            if (this.slots[i].stackSize <= 0)
                                this.slots[i] = null;

                            chest.setInventorySlotContents(j, sta);
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.renderBoundingBox == null) {
            this.renderBoundingBox = AxisAlignedBB.getBoundingBox(
                    this.xCoord - 1,
                    this.yCoord,
                    this.zCoord - 1,
                    this.xCoord + 2,
                    this.yCoord + 1,
                    this.zCoord + 2
            );
        }

        return this.renderBoundingBox;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerSatDock(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUISatDock(player.inventory, this);
    }
}
