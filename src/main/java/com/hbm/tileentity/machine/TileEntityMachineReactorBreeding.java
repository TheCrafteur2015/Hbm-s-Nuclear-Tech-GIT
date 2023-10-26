package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.ReactorResearch;
import com.hbm.inventory.container.ContainerMachineReactorBreeding;
import com.hbm.inventory.gui.GUIMachineReactorBreeding;
import com.hbm.inventory.recipes.BreederRecipes;
import com.hbm.inventory.recipes.BreederRecipes.BreederRecipe;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityMachineReactorBreeding extends TileEntityMachineBase implements SimpleComponent, IGUIProvider {

	public int flux;
	public float progress;
	
	private static final int[] slots_io = new int[] { 0, 1 };

	public TileEntityMachineReactorBreeding() {
		super(2);
	}
	
	@Override
	public String getName() {
		return "container.reactorBreeding";
	}

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			this.flux = 0;
			getInteractions();
			
			if(canProcess()) {
				
				this.progress += 0.0025F * (this.flux / BreederRecipes.getOutput(this.slots[0]).flux);
				
				if(this.progress >= 1.0F) {
					this.progress = 0F;
					processItem();
					markDirty();
				}
			} else {
				this.progress = 0.0F;
			}
						
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("flux", this.flux);
			data.setFloat("progress", this.progress);
			networkPack(data, 20);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.flux = data.getInteger("flux");
		this.progress = data.getFloat("progress");
	}
	
	public void getInteractions() {
		
		for(byte d = 2; d < 6; d++) {
			ForgeDirection dir = ForgeDirection.getOrientation(d);
			
			Block b = this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
			@SuppressWarnings("unused")
			TileEntity te = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
			
			if(b == ModBlocks.reactor_research) {

				int[] pos = ((ReactorResearch) ModBlocks.reactor_research).findCore(this.worldObj, this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);

				if(pos != null) {

					TileEntity tile = this.worldObj.getTileEntity(pos[0], pos[1], pos[2]);

					if(tile instanceof TileEntityReactorResearch) {

						TileEntityReactorResearch reactor = (TileEntityReactorResearch) tile;

						this.flux += reactor.totalFlux;
					}
				}
			}
		}
	}

	public boolean canProcess() {
		
		if(this.slots[0] == null)
			return false;
		
		BreederRecipe recipe = BreederRecipes.getOutput(this.slots[0]);
		
		if((recipe == null) || (this.flux < recipe.flux))
			return false;

		if(this.slots[1] == null)
			return true;

		if(!this.slots[1].isItemEqual(recipe.output))
			return false;

		if(this.slots[1].stackSize < this.slots[1].getMaxStackSize())
			return true;
		else
			return false;
	}

	private void processItem() {
		
		if(canProcess()) {
			
			BreederRecipe rec = BreederRecipes.getOutput(this.slots[0]);
			
			if(rec == null)
				return;
			
			ItemStack itemStack = rec.output;

			if(this.slots[1] == null) {
				this.slots[1] = itemStack.copy();
			} else if(this.slots[1].isItemEqual(itemStack)) {
				this.slots[1].stackSize += itemStack.stackSize;
			}

			this.slots[0].stackSize--;
				
			if(this.slots[0].stackSize <= 0) {
				this.slots[0] = null;
			}
		}
	}

	

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityMachineReactorBreeding.slots_io;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}

	public int getProgressScaled(int i) {
		return (int) (this.progress * i);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.flux = nbt.getInteger("flux");
		this.progress = nbt.getFloat("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("flux", this.flux);
		nbt.setFloat("progress", this.progress);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord,
					this.yCoord,
					this.zCoord,
					this.xCoord + 1,
					this.yCoord + 3,
					this.zCoord + 1
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "breeding_reactor";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFlux(Context context, Arguments args) {
		return new Object[] {this.flux};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getProgress(Context context, Arguments args) {
		return new Object[] {this.progress};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {this.flux, this.progress};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineReactorBreeding(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineReactorBreeding(player.inventory, this);
	}
}
