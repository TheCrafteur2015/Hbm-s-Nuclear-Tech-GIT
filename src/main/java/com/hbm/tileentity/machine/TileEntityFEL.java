package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineSILEX;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.container.ContainerFEL;
import com.hbm.inventory.gui.GUIFEL;
import com.hbm.items.machine.ItemFELCrystal;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFEL extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {
	
	public long power;
	public static final long maxPower = 20000000;
	public static final int powerReq = 1250;
	public EnumWavelengths mode = EnumWavelengths.NULL;
	public boolean isOn;
	public boolean missingValidSilex = true	;
	public int distance;
	public List<EntityLivingBase> entities = new ArrayList<>();
	
	
	public TileEntityFEL() {
		super(2);
	}

	@Override
	public String getName() {
		return "container.machineFEL";
	}

	@SuppressWarnings({ "incomplete-switch", "unchecked" })
	@Override
	@Spaghetti ("What the fuck were you thinking")
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			trySubscribe(this.worldObj, this.xCoord + dir.offsetX * -5, this.yCoord + 1, this.zCoord + dir.offsetZ  * -5, dir.getOpposite());
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityFEL.maxPower);
			
			if(this.isOn && !(this.slots[1] == null)) {
				
				if(this.slots[1].getItem() instanceof ItemFELCrystal) {
					
					ItemFELCrystal crystal = (ItemFELCrystal) this.slots[1].getItem();
					this.mode = crystal.wavelength;
					
				} else { this.mode = EnumWavelengths.NULL; }
				
			} else { this.mode = EnumWavelengths.NULL; }
			
			int range = 24;
			boolean silexSpacing = false;
			
			int req = (int) (TileEntityFEL.powerReq * ((this.mode.ordinal() == 0) ? 0 : Math.pow(3, this.mode.ordinal())));
			
			if(this.isOn && this.mode != EnumWavelengths.NULL && this.power < req) {
				this.power = 0;
			}
			
			if(this.isOn && this.power >= req && this.mode != EnumWavelengths.NULL) {
				
				int distance = this.distance-1;
				double blx = Math.min(this.xCoord, this.xCoord + dir.offsetX * distance) + 0.2;
				double bux = Math.max(this.xCoord, this.xCoord + dir.offsetX * distance) + 0.8;
				double bly = Math.min(this.yCoord, 1 + this.yCoord + dir.offsetY * distance) + 0.2;
				double buy = Math.max(this.yCoord, 1 + this.yCoord + dir.offsetY * distance) + 0.8;
				double blz = Math.min(this.zCoord, this.zCoord + dir.offsetZ * distance) + 0.2;
				double buz = Math.max(this.zCoord, this.zCoord + dir.offsetZ * distance) + 0.8;
				
				List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(blx, bly, blz, bux, buy, buz));
				
				for(EntityLivingBase entity : list) {
					switch(this.mode) {
					case VISIBLE: entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 60 * 60 * 65536, 0));
					case IR:
					case UV: entity.setFire(10); break;
					case GAMMA: ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.CREATIVE, 25); break;
					case DRX: ContaminationUtil.applyDigammaData(entity, 0.1F); break;
					}
				}
				
				this.power -= req;
				for(int i = 3; i < range; i++) {
				
					int x = this.xCoord + dir.offsetX * i;
					int y = this.yCoord + 1;
					int z = this.zCoord + dir.offsetZ * i;
					
					Block b = this.worldObj.getBlock(x, y, z);
					
					if(!(b.getMaterial().isOpaque()) && b != Blocks.tnt) {
						this.distance = range;
						silexSpacing = false;
						continue;
					}
					
					if(b == ModBlocks.machine_silex) {
					
						TileEntity te = this.worldObj.getTileEntity(x + dir.offsetX, this.yCoord, z + dir.offsetZ);
					
						if(te instanceof TileEntitySILEX) {
							TileEntitySILEX silex = (TileEntitySILEX) te;
							int meta = silex.getBlockMetadata() - BlockDummyable.offset;
							if(rotationIsValid(meta, getBlockMetadata() - BlockDummyable.offset) && i >= 5 && silexSpacing == false	) {
								if(silex.mode != this.mode) {
									silex.mode = this.mode;
									this.missingValidSilex = false;
									silexSpacing = true;
									continue;
								} 
							} else {
								MachineSILEX silexBlock = (MachineSILEX)silex.getBlockType();
								silexBlock.breakBlock(this.worldObj, silex.xCoord, silex.yCoord, silex.zCoord, silexBlock, 0);
								this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, x + 0.5, y + 0.5, z + 0.5, new ItemStack(Item.getItemFromBlock(ModBlocks.machine_silex))));
							} 
						}
						
					} else if(b.getMaterial().isOpaque() || b == Blocks.tnt) {
						
						this.distance = i;
						
						if(b.getMaterial().isLiquid()) {
							this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 1.0F, 1.0F);
							this.worldObj.setBlockToAir(x, y, z);
							break;
						} 
						
						float hardness = b.getExplosionResistance(null);
						if(hardness < 75 && this.worldObj.rand.nextInt(5) == 0) {
							this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 1.0F, 1.0F);
							Block block = (this.mode != EnumWavelengths.DRX) ? Blocks.fire : (MainRegistry.polaroidID == 11) ? ModBlocks.digamma_matter : ModBlocks.fire_digamma;
							this.worldObj.setBlock(x, y, z, block);
							if(this.mode == EnumWavelengths.DRX)
								this.worldObj.setBlock(x, y-1, z, ModBlocks.ash_digamma);
						}
						break;
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setString("mode", this.mode.toString());
			data.setBoolean("isOn", this.isOn);
			data.setBoolean("valid", this.missingValidSilex);
			data.setInteger("distance", this.distance);
			networkPack(data, 250);
		}
	}
	
	public boolean rotationIsValid(int silexMeta, int felMeta) {
		ForgeDirection silexDir = ForgeDirection.getOrientation(silexMeta);
		ForgeDirection felDir = ForgeDirection.getOrientation(felMeta);
		if(silexDir == felDir || silexDir == felDir.getOpposite()) {
			return true;
		}
		 
		return false;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.mode = EnumWavelengths.valueOf(nbt.getString("mode"));
		this.isOn = nbt.getBoolean("isOn");
		this.distance = nbt.getInteger("distance");
		this.missingValidSilex = nbt.getBoolean("valid");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 2){
			this.isOn = !this.isOn;
		}
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityFEL.maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.mode = EnumWavelengths.valueOf(nbt.getString("mode"));
		this.isOn = nbt.getBoolean("isOn");
		this.missingValidSilex = nbt.getBoolean("valid");
		this.distance = nbt.getInteger("distance");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setString("mode", this.mode.toString());
		nbt.setBoolean("isOn", this.isOn);
		nbt.setBoolean("valid", this.missingValidSilex);
		nbt.setInteger("distance", this.distance);
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

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityFEL.maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFEL(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFEL(player.inventory, this);
	}
}