package com.hbm.tileentity.machine.rbmk;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCraneConsole extends TileEntity implements INBTPacketReceiver, SimpleComponent {
	
	public int centerX;
	public int centerY;
	public int centerZ;
	
	public int spanF;
	public int spanB;
	public int spanL;
	public int spanR;
	
	public int height;
	
	public boolean setUpCrane = false;

	public double lastTiltFront = 0;
	public double lastTiltLeft = 0;
	public double tiltFront = 0;
	public double tiltLeft = 0;

	public double lastPosFront = 0;
	public double lastPosLeft = 0;
	public double posFront = 0;
	public double posLeft = 0;
	private static final double speed = 0.05D;
	
	private boolean goesDown = false;
	public double lastProgress = 1D;
	public double progress = 1D;
	
	private ItemStack loadedItem;
	private boolean hasLoaded = false;
	public double loadedHeat;
	public double loadedEnrichment;

	
	@Override
	public void updateEntity() {

		if(this.worldObj.isRemote) {
			this.lastTiltFront = this.tiltFront;
			this.lastTiltLeft = this.tiltLeft;
		}
		
		if(this.goesDown) {
			
			if(this.progress > 0) {
				this.progress -= 0.04D;
			} else {
				this.progress = 0;
				this.goesDown = false;
				
				if(!this.worldObj.isRemote && canTargetInteract()) {
					if(this.loadedItem != null) {
						getColumnAtPos().load(this.loadedItem);
						this.loadedItem = null;
					} else {
						IRBMKLoadable column = getColumnAtPos();
						this.loadedItem = column.provideNext();
						column.unload();
					}
					
					markDirty();
				}
					
			}
		} else if(this.progress != 1) {
			
			this.progress += 0.04D;
			
			if(this.progress > 1D) {
				this.progress = 1D;
			}
		}

		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection side = dir.getRotation(ForgeDirection.UP);
		double minX = this.xCoord + 0.5 - side.offsetX * 1.5;
		double maxX = this.xCoord + 0.5 + side.offsetX * 1.5 + dir.offsetX * 2;
		double minZ = this.zCoord + 0.5 - side.offsetZ * 1.5;
		double maxZ = this.zCoord + 0.5 + side.offsetZ * 1.5 + dir.offsetZ * 2;
		
		List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(
				Math.min(minX, maxX),
				this.yCoord,
				Math.min(minZ, maxZ),
				Math.max(minX, maxX),
				this.yCoord + 2,
				Math.max(minZ, maxZ)));
		this.tiltFront = 0;
		this.tiltLeft = 0;
		
		if(players.size() > 0 && !isCraneLoading()) {
			EntityPlayer player = players.get(0);
			HbmPlayerProps props = HbmPlayerProps.getData(player);
			boolean up = props.getKeyPressed(EnumKeybind.CRANE_UP);
			boolean down = props.getKeyPressed(EnumKeybind.CRANE_DOWN);
			boolean left = props.getKeyPressed(EnumKeybind.CRANE_LEFT);
			boolean right = props.getKeyPressed(EnumKeybind.CRANE_RIGHT);
			
			if(up && !down) {
				this.tiltFront = 30;
				if(!this.worldObj.isRemote) this.posFront += TileEntityCraneConsole.speed;
			}
			if(!up && down) {
				this.tiltFront = -30;
				if(!this.worldObj.isRemote) this.posFront -= TileEntityCraneConsole.speed;
			}
			if(left && !right) {
				this.tiltLeft = 30;
				if(!this.worldObj.isRemote) this.posLeft += TileEntityCraneConsole.speed;
			}
			if(!left && right) {
				this.tiltLeft = -30;
				if(!this.worldObj.isRemote) this.posLeft -= TileEntityCraneConsole.speed;
			}
			
			if(props.getKeyPressed(EnumKeybind.CRANE_LOAD)) {
				this.goesDown = true;
			}
		}
		
		this.posFront = MathHelper.clamp_double(this.posFront, -this.spanB, this.spanF);
		this.posLeft = MathHelper.clamp_double(this.posLeft, -this.spanR, this.spanL);
		
		if(!this.worldObj.isRemote) {
			
			if(this.loadedItem != null && this.loadedItem.getItem() instanceof ItemRBMKRod) {
				this.loadedHeat = ItemRBMKRod.getHullHeat(this.loadedItem);
				this.loadedEnrichment = ItemRBMKRod.getEnrichment(this.loadedItem);
			} else {
				this.loadedHeat = 0;
				this.loadedEnrichment = 0;
			}
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setBoolean("crane", this.setUpCrane);
			
			if(this.setUpCrane) { //no need to send any of this if there's NO FUCKING CRANE THERE
				nbt.setInteger("centerX", this.centerX);
				nbt.setInteger("centerY", this.centerY);
				nbt.setInteger("centerZ", this.centerZ);
				nbt.setInteger("spanF", this.spanF);
				nbt.setInteger("spanB", this.spanB);
				nbt.setInteger("spanL", this.spanL);
				nbt.setInteger("spanR", this.spanR);
				nbt.setInteger("height", this.height);
				nbt.setDouble("posFront", this.posFront);
				nbt.setDouble("posLeft", this.posLeft);
				nbt.setBoolean("loaded", hasItemLoaded());
				nbt.setDouble("loadedHeat", this.loadedHeat);
				nbt.setDouble("loadedEnrichment", this.loadedEnrichment);
			}
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
		}
	}
	
	public boolean hasItemLoaded() {
		
		if(!this.worldObj.isRemote)
			return this.loadedItem != null;
		else
			return this.hasLoaded;
	}
	
	public boolean isCraneLoading() {
		return this.progress != 1D;
	}
	
	public boolean isAboveValidTarget() {
		return getColumnAtPos() != null;
	}
	
	public boolean canTargetInteract() {
		
		IRBMKLoadable column = getColumnAtPos();
		
		if(column == null)
			return false;
		
		if(hasItemLoaded()) {
			return column.canLoad(this.loadedItem);
		} else {
			return column.canUnload();
		}
	}
	
	public IRBMKLoadable getColumnAtPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection left = dir.getRotation(ForgeDirection.DOWN);

		int x = (int)Math.floor(this.centerX - dir.offsetX * this.posFront - left.offsetX * this.posLeft + 0.5D);
		int y = this.centerY - 1;
		int z = (int)Math.floor(this.centerZ - dir.offsetZ * this.posFront - left.offsetZ * this.posLeft + 0.5D);
				
		Block b = this.worldObj.getBlock(x, y, z);
		
		if(b instanceof RBMKBase) {
			
			int[] pos = ((BlockDummyable)b).findCore(this.worldObj, x, y, z);
			if(pos != null) {
				TileEntityRBMKBase column = (TileEntityRBMKBase)this.worldObj.getTileEntity(pos[0], pos[1], pos[2]);
				if(column instanceof IRBMKLoadable) {
					return (IRBMKLoadable) column;
				}
			}
		}
		
		return null;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.lastPosFront = this.posFront;
		this.lastPosLeft = this.posLeft;
		this.lastProgress = this.progress;
		
		this.setUpCrane = nbt.getBoolean("crane");
		this.centerX = nbt.getInteger("centerX");
		this.centerY = nbt.getInteger("centerY");
		this.centerZ = nbt.getInteger("centerZ");
		this.spanF = nbt.getInteger("spanF");
		this.spanB = nbt.getInteger("spanB");
		this.spanL = nbt.getInteger("spanL");
		this.spanR = nbt.getInteger("spanR");
		this.height = nbt.getInteger("height");
		this.posFront = nbt.getDouble("posFront");
		this.posLeft = nbt.getDouble("posLeft");
		this.hasLoaded = nbt.getBoolean("loaded");
		this.posLeft = nbt.getDouble("posLeft");
		this.loadedHeat = nbt.getDouble("loadedHeat");
		this.loadedEnrichment = nbt.getDouble("loadedEnrichment");
	}
	
	public void setTarget(int x, int y, int z) {
		this.centerX = x;
		this.centerY = y + RBMKDials.getColumnHeight(this.worldObj) + 1;
		this.centerZ = z;

		this.spanF = 7;
		this.spanB = 7;
		this.spanL = 7;
		this.spanR = 7;
		
		this.height = 7;
		this.setUpCrane = true;
		
		markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.setUpCrane = nbt.getBoolean("crane");
		this.centerX = nbt.getInteger("centerX");
		this.centerY = nbt.getInteger("centerY");
		this.centerZ = nbt.getInteger("centerZ");
		this.spanF = nbt.getInteger("spanF");
		this.spanB = nbt.getInteger("spanB");
		this.spanL = nbt.getInteger("spanL");
		this.spanR = nbt.getInteger("spanR");
		this.height = nbt.getInteger("height");
		this.posFront = nbt.getDouble("posFront");
		this.posLeft = nbt.getDouble("posLeft");
		
		NBTTagCompound held = nbt.getCompoundTag("held");
		this.loadedItem = ItemStack.loadItemStackFromNBT(held);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("crane", this.setUpCrane);
		nbt.setInteger("centerX", this.centerX);
		nbt.setInteger("centerY", this.centerY);
		nbt.setInteger("centerZ", this.centerZ);
		nbt.setInteger("spanF", this.spanF);
		nbt.setInteger("spanB", this.spanB);
		nbt.setInteger("spanL", this.spanL);
		nbt.setInteger("spanR", this.spanR);
		nbt.setInteger("height", this.height);
		nbt.setDouble("posFront", this.posFront);
		nbt.setDouble("posLeft", this.posLeft);
		
		if(this.loadedItem != null) {
			NBTTagCompound held = new NBTTagCompound();
			this.loadedItem.writeToNBT(held);
			nbt.setTag("held", held);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "rbmk_crane";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] move(Context context, Arguments args) {
		if(this.setUpCrane) {
			String textbruh = args.checkString(0);
			switch(textbruh) {
				case "up":
					this.tiltFront = 30;
					if(!this.worldObj.isRemote) this.posFront += TileEntityCraneConsole.speed;
					break;
				case "down":
					this.tiltFront = -30;
					if(!this.worldObj.isRemote) this.posFront -= TileEntityCraneConsole.speed;
					break;
				case "left":
					this.tiltLeft = 30;
					if(!this.worldObj.isRemote) this.posLeft += TileEntityCraneConsole.speed;
					break;
				case "right":
					this.tiltLeft = -30;
					if(!this.worldObj.isRemote) this.posLeft -= TileEntityCraneConsole.speed;
					break;
			}
			
			return new Object[] {};
		}
		return new Object[] {"Crane not found"};
	}
	
	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] load(Context context, Arguments args) {
		if (this.setUpCrane) {
			this.goesDown = true;
			return new Object[] {};
		}
		return new Object[] {"Crane not found"};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getDepletion(Context context, Arguments args) {
		if(this.loadedItem != null && this.loadedItem.getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getEnrichment(this.loadedItem)};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getXenonPoison(Context context, Arguments args) {
		if(this.loadedItem != null && this.loadedItem.getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getPoison(this.loadedItem)};
		}
		return new Object[] {"N/A"};
	}
}
