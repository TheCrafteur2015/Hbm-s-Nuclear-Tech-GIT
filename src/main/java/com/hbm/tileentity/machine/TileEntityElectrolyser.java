package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerElectrolyserFluid;
import com.hbm.inventory.container.ContainerElectrolyserMetal;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIElectrolyserFluid;
import com.hbm.inventory.gui.GUIElectrolyserMetal;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.ElectrolyserFluidRecipes;
import com.hbm.inventory.recipes.ElectrolyserFluidRecipes.ElectrolysisRecipe;
import com.hbm.inventory.recipes.ElectrolyserMetalRecipes;
import com.hbm.inventory.recipes.ElectrolyserMetalRecipes.ElectrolysisMetalRecipe;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CrucibleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityElectrolyser extends TileEntityMachineBase implements IEnergyUser, IFluidStandardTransceiver, IControlReceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 20000000;
	public static final int usageBase = 10000;
	public int usage;
	
	public int progressFluid;
	public static final int processFluidTimeBase = 100;
	public int processFluidTime;
	public int progressOre;
	public static final int processOreTimeBase = 1000;
	public int processOreTime;

	public MaterialStack leftStack;
	public MaterialStack rightStack;
	public int maxMaterial = MaterialShapes.BLOCK.q(16);
	
	public FluidTank[] tanks;

	public TileEntityElectrolyser() {
		//0: Battery
		//1-2: Upgrades
		//// FLUID
		//3-4: Fluid ID
		//5-10: Fluid IO
		//11-13: Byproducts
		//// METAL
		//14: Crystal
		//15-20: Outputs
		super(21);
		this.tanks = new FluidTank[4];
		this.tanks[0] = new FluidTank(Fluids.WATER, 16000);
		this.tanks[1] = new FluidTank(Fluids.HYDROGEN, 16000);
		this.tanks[2] = new FluidTank(Fluids.OXYGEN, 16000);
		this.tanks[3] = new FluidTank(Fluids.NITRIC_ACID, 16000);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 14) return ElectrolyserMetalRecipes.getRecipe(itemStack) != null;
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i != 14;
	}

	@Override
	public String getName() {
		return "container.machineElectrolyser";
	}

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityElectrolyser.maxPower);
			this.tanks[0].setType(3, 4, this.slots);
			this.tanks[0].loadTank(5, 6, this.slots);
			this.tanks[1].unloadTank(7, 8, this.slots);
			this.tanks[2].unloadTank(9, 10, this.slots);
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(this.tanks[3].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

					if(this.tanks[1].getFill() > 0) this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					if(this.tanks[2].getFill() > 0) this.sendFluid(this.tanks[2], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			UpgradeManager.eval(this.slots, 1, 2);
			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);

			this.processFluidTime = TileEntityElectrolyser.processFluidTimeBase - TileEntityElectrolyser.processFluidTimeBase * speedLevel / 4;
			this.processOreTime = TileEntityElectrolyser.processOreTimeBase - TileEntityElectrolyser.processOreTimeBase * speedLevel / 4;
			this.usage = TileEntityElectrolyser.usageBase - TileEntityElectrolyser.usageBase * powerLevel / 4;
			
			if(canProcessFluid()) {
				this.progressFluid++;
				this.power -= this.usage;
				
				if(this.progressFluid >= this.processFluidTime) {
					processFluids();
					this.progressFluid = 0;
					markChanged();
				}
			}
			
			if(canProcesMetal()) {
				this.progressOre++;
				this.power -= this.usage;
				
				if(this.progressOre >= this.processOreTime) {
					processMetal();
					this.progressOre = 0;
					markChanged();
				}
			}
			
			if(this.leftStack != null) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
				List<MaterialStack> toCast = new ArrayList<>();
				toCast.add(this.leftStack);
				
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(this.worldObj, this.xCoord + 0.5D + dir.offsetX * 5.875D, this.yCoord + 2D, this.zCoord + 0.5D + dir.offsetZ * 5.875D, 6, true, toCast, MaterialShapes.NUGGET.q(1), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, this.yCoord - (float) (Math.ceil(impact.yCoord) - 0.875) + 2));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5D + dir.offsetX * 5.875D, this.yCoord + 2, this.zCoord + 0.5D + dir.offsetZ * 5.875D), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 50));
					
					if(this.leftStack.amount <= 0) this.leftStack = null;
				}
			}
			
			if(this.rightStack != null) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
				List<MaterialStack> toCast = new ArrayList<>();
				toCast.add(this.rightStack);
				
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(this.worldObj, this.xCoord + 0.5D + dir.offsetX * 5.875D, this.yCoord + 2D, this.zCoord + 0.5D + dir.offsetZ * 5.875D, 6, true, toCast, MaterialShapes.NUGGET.q(1), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, this.yCoord - (float) (Math.ceil(impact.yCoord) - 0.875) + 2));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5D + dir.offsetX * 5.875D, this.yCoord + 2, this.zCoord + 0.5D + dir.offsetZ * 5.875D), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 50));
					
					if(this.rightStack.amount <= 0) this.rightStack = null;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progressFluid", this.progressFluid);
			data.setInteger("progressOre", this.progressOre);
			data.setInteger("usage", this.usage);
			data.setInteger("processFluidTime", this.processFluidTime);
			data.setInteger("processOreTime", this.processOreTime);
			if(this.leftStack != null) {
				data.setInteger("leftType", this.leftStack.material.id);
				data.setInteger("leftAmount", this.leftStack.amount);
			}
			if(this.rightStack != null) {
				data.setInteger("rightType", this.rightStack.material.id);
				data.setInteger("rightAmount", this.rightStack.amount);
			}
			for(int i = 0; i < 4; i++) this.tanks[i].writeToNBT(data, "t" + i);
			networkPack(data, 50);
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord - dir.offsetX * 6, this.yCoord, this.zCoord - dir.offsetZ * 6, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 6 + rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 6 + rot.offsetZ, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 6 - rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 6 - rot.offsetZ, dir.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 6, this.yCoord, this.zCoord + dir.offsetZ * 6, dir),
				new DirPos(this.xCoord + dir.offsetX * 6 + rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ * 6 + rot.offsetZ, dir),
				new DirPos(this.xCoord + dir.offsetX * 6 - rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ * 6 - rot.offsetZ, dir)
		};
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progressFluid = nbt.getInteger("progressFluid");
		this.progressOre = nbt.getInteger("progressOre");
		this.usage = nbt.getInteger("usage");
		this.processFluidTime = nbt.getInteger("processFluidTime");
		this.processOreTime = nbt.getInteger("processOreTime");
		if(nbt.hasKey("leftType")) this.leftStack = new MaterialStack(Mats.matById.get(nbt.getInteger("leftType")), nbt.getInteger("leftAmount"));
		else this.leftStack = null;
		if(nbt.hasKey("rightType")) this.rightStack = new MaterialStack(Mats.matById.get(nbt.getInteger("rightType")), nbt.getInteger("rightAmount"));
		else this.rightStack = null;
		for(int i = 0; i < 4; i++) this.tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	public boolean canProcessFluid() {
		
		if(this.power < this.usage) return false;
		
		ElectrolysisRecipe recipe = ElectrolyserFluidRecipes.recipes.get(this.tanks[0].getTankType());
		
		if((recipe == null) || (recipe.amount > this.tanks[0].getFill()) || (recipe.output1.type == this.tanks[1].getTankType() && recipe.output1.fill + this.tanks[1].getFill() > this.tanks[1].getMaxFill())) return false;
		if(recipe.output2.type == this.tanks[2].getTankType() && recipe.output2.fill + this.tanks[2].getFill() > this.tanks[2].getMaxFill()) return false;
		
		if(recipe.byproduct != null) {
			
			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = this.slots[11 + i];
				ItemStack byproduct = recipe.byproduct[i];
				
				if(slot == null) continue;
				if(!slot.isItemEqual(byproduct)) return false;
				if(slot.stackSize + byproduct.stackSize > slot.getMaxStackSize()) return false;
			}
		}
		
		return true;
	}
	
	public void processFluids() {
		
		ElectrolysisRecipe recipe = ElectrolyserFluidRecipes.recipes.get(this.tanks[0].getTankType());
		this.tanks[0].setFill(this.tanks[0].getFill() - recipe.amount);
		this.tanks[1].setTankType(recipe.output1.type);
		this.tanks[2].setTankType(recipe.output2.type);
		this.tanks[1].setFill(this.tanks[1].getFill() + recipe.output1.fill);
		this.tanks[2].setFill(this.tanks[2].getFill() + recipe.output2.fill);
		
		if(recipe.byproduct != null) {
			
			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = this.slots[11 + i];
				ItemStack byproduct = recipe.byproduct[i];
				
				if(slot == null) {
					this.slots[11 + i] = byproduct.copy();
				} else {
					this.slots[11 + i].stackSize += byproduct.stackSize;
				}
			}
		}
	}
	
	public boolean canProcesMetal() {
		
		if((this.slots[14] == null) || (this.power < this.usage) || (this.tanks[3].getFill() < 100)) return false;
		
		ElectrolysisMetalRecipe recipe = ElectrolyserMetalRecipes.getRecipe(this.slots[14]);
		if(recipe == null) return false;
		
		if(this.leftStack != null) {
			if(recipe.output1.material != this.leftStack.material) return false;
			if(recipe.output1.amount + this.leftStack.amount > this.maxMaterial) return false;
		}
		
		if(this.rightStack != null) {
			if(recipe.output2.material != this.rightStack.material) return false;
			if(recipe.output2.amount + this.rightStack.amount > this.maxMaterial) return false;
		}
		
		if(recipe.byproduct != null) {
			
			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = this.slots[15 + i];
				ItemStack byproduct = recipe.byproduct[i];
				
				if(slot == null) continue;
				if(!slot.isItemEqual(byproduct)) return false;
				if(slot.stackSize + byproduct.stackSize > slot.getMaxStackSize()) return false;
			}
		}
		
		return true;
	}
	
	public void processMetal() {
		
		ElectrolysisMetalRecipe recipe = ElectrolyserMetalRecipes.getRecipe(this.slots[14]);
		
		if(this.leftStack == null) {
			this.leftStack = new MaterialStack(recipe.output1.material, recipe.output1.amount);
		} else {
			this.leftStack.amount += recipe.output1.amount;
		}
		
		if(this.rightStack == null) {
			this.rightStack = new MaterialStack(recipe.output2.material, recipe.output2.amount);
		} else {
			this.rightStack.amount += recipe.output2.amount;
		}
		
		if(recipe.byproduct != null) {
			
			for(int i = 0; i < recipe.byproduct.length; i++) {
				ItemStack slot = this.slots[15 + i];
				ItemStack byproduct = recipe.byproduct[i];
				
				if(slot == null) {
					this.slots[15 + i] = byproduct.copy();
				} else {
					this.slots[15 + i].stackSize += byproduct.stackSize;
				}
			}
		}
		
		this.tanks[3].setFill(this.tanks[3].getFill() - 100);
		decrStackSize(14, 1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.progressFluid = nbt.getInteger("progressFluid");
		this.progressOre = nbt.getInteger("progressOre");
		this.usage = nbt.getInteger("usage");
		this.processFluidTime = nbt.getInteger("processFluidTime");
		this.processOreTime = nbt.getInteger("processOreTime");
		if(nbt.hasKey("leftType")) this.leftStack = new MaterialStack(Mats.matById.get(nbt.getInteger("leftType")), nbt.getInteger("leftAmount"));
		else this.leftStack = null;
		if(nbt.hasKey("rightType")) this.rightStack = new MaterialStack(Mats.matById.get(nbt.getInteger("rightType")), nbt.getInteger("rightAmount"));
		else this.rightStack = null;
		for(int i = 0; i < 4; i++) this.tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setInteger("progressFluid", this.progressFluid);
		nbt.setInteger("progressOre", this.progressOre);
		nbt.setInteger("usage", this.usage);
		nbt.setInteger("processFluidTime", this.processFluidTime);
		nbt.setInteger("processOreTime", this.processOreTime);
		if(this.leftStack != null) {
			nbt.setInteger("leftType", this.leftStack.material.id);
			nbt.setInteger("leftAmount", this.leftStack.amount);
		}
		if(this.rightStack != null) {
			nbt.setInteger("rightType", this.rightStack.material.id);
			nbt.setInteger("rightAmount", this.rightStack.amount);
		}
		for(int i = 0; i < 4; i++) this.tanks[i].writeToNBT(nbt, "t" + i);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 5,
					this.yCoord - 0,
					this.zCoord - 5,
					this.xCoord + 6,
					this.yCoord + 4,
					this.zCoord + 6
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityElectrolyser.maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[1], this.tanks[2]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0], this.tanks[3]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) return new ContainerElectrolyserFluid(player.inventory, this);
		return new ContainerElectrolyserMetal(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) return new GUIElectrolyserFluid(player.inventory, this);
		return new GUIElectrolyserMetal(player.inventory, this);
	}

	@Override
	public void receiveControl(NBTTagCompound data) { }
	
	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {

		if(data.hasKey("sgm")) FMLNetworkHandler.openGui(player, MainRegistry.instance, 1, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		if(data.hasKey("sgf")) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}
}
