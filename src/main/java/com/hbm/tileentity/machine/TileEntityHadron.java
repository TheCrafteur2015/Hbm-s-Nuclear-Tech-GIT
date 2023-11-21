package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockHadronCoil;
import com.hbm.blocks.machine.BlockHadronPlating;
import com.hbm.inventory.container.ContainerHadron;
import com.hbm.inventory.gui.GUIHadron;
import com.hbm.inventory.recipes.HadronRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityHadronDiode.DiodeConfig;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHadron extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {
	
	public long power;
	public static final long maxPower = 10000000;
	
	public boolean isOn = false;
	public boolean analysisOnly = false;
	public int ioMode = 0;
	public static final int MODE_DEFAULT = 0;
	public static final int MODE_HOPPER = 1;
	public static final int MODE_SINGLE = 2;
	
	private int delay;
	public EnumHadronState state = EnumHadronState.IDLE;
	private static final int delaySuccess = 20;
	private static final int delayNoResult = 60;
	private static final int delayError = 100;
	
	public boolean stat_success = false;
	public EnumHadronState stat_state = EnumHadronState.IDLE;
	public int stat_charge = 0;
	public int stat_x = 0;
	public int stat_y = 0;
	public int stat_z = 0;
	
	public TileEntityHadron() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.hadron";
	}
	
	private static final int[] access = new int[] {0, 1, 2, 3};
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
        return TileEntityHadron.access;
    }

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 2 || i == 3;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i != 0 && i != 1) return false;
		
		if(this.ioMode == TileEntityHadron.MODE_SINGLE) {
			return this.slots[i] == null;
		}
		
		//makes sure that equal items like the antimatter capsules are spread out evenly
		if(this.slots[0] != null && this.slots[1] != null && this.slots[0].getItem() == this.slots[1].getItem() && this.slots[0].getItemDamage() == this.slots[1].getItemDamage()) {
			if(i == 0) return this.slots[1].stackSize - this.slots[0].stackSize >= 0;
			if(i == 1) return this.slots[0].stackSize - this.slots[1].stackSize >= 0;
		}
		
		return true;
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(this.slots, 4, this.power, TileEntityHadron.maxPower);
			drawPower();
			
			if(this.delay <= 0 && this.isOn && this.particles.size() < TileEntityHadron.maxParticles && this.slots[0] != null && this.slots[1] != null && this.power >= TileEntityHadron.maxPower * 0.75) {
				
				if(this.ioMode != TileEntityHadron.MODE_HOPPER || (this.slots[0].stackSize > 1 && this.slots[1].stackSize > 1)) {
					ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
					this.particles.add(new Particle(this.slots[0], this.slots[1], dir, this.xCoord, this.yCoord, this.zCoord));
					decrStackSize(0, 1);
					decrStackSize(1, 1);
					this.power -= TileEntityHadron.maxPower * 0.75;
					this.state = EnumHadronState.PROGRESS;
				}
			}
			
			if(this.delay > 0)
				this.delay--;
			else if(this.particles.isEmpty()) {
				this.state = EnumHadronState.IDLE;
			}
			
			if(!this.particles.isEmpty())
				updateParticles();
			
			for(Particle p : this.particlesToRemove) {
				this.particles.remove(p);
			}
			
			this.particlesToRemove.clear();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", this.isOn);
			data.setLong("power", this.power);
			data.setBoolean("analysis", this.analysisOnly);
			data.setInteger("ioMode", this.ioMode);
			data.setByte("state", (byte) this.state.ordinal());
			
			data.setBoolean("stat_success", this.stat_success);
			data.setByte("stat_state", (byte) this.stat_state.ordinal());
			data.setInteger("stat_charge", this.stat_charge);
			data.setInteger("stat_x", this.stat_x);
			data.setInteger("stat_y", this.stat_y);
			data.setInteger("stat_z", this.stat_z);
			networkPack(data, 50);
		}
	}
	
	
	private void process(Particle p) {
		
		ItemStack[] result = HadronRecipes.getOutput(p.item1, p.item2, p.momentum, this.analysisOnly);
		
		if(result == null) {
			this.state = HadronRecipes.returnCode;
			setStats(this.state, p.momentum, false);
			this.delay = TileEntityHadron.delayNoResult;
			this.worldObj.playSoundEffect(p.posX, p.posY, p.posZ, "random.orb", 2, 0.5F);
			return;
		}
		
		if((this.slots[2] == null || (this.slots[2].getItem() == result[0].getItem() && this.slots[2].stackSize < this.slots[2].getMaxStackSize())) &&
				(this.slots[3] == null || (this.slots[3].getItem() == result[1].getItem() && this.slots[3].stackSize < this.slots[3].getMaxStackSize()))) {
			
			for(int i = 2; i <= 3; i++ ) {
				
				//System.out.println("yes");
				if(this.slots[i] == null)
					this.slots[i] = result[i - 2].copy();
				else
					this.slots[i].stackSize++;
			}
			
			if(result[0].getItem() == ModItems.particle_digamma) {
				List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
						AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5)
						.expand(128, 50, 128));
				
				for(EntityPlayer player : players)
					player.triggerAchievement(MainRegistry.achOmega12);
			}
		}
		
		this.worldObj.playSoundEffect(p.posX, p.posY, p.posZ, "random.orb", 2, 1F);
		this.delay = TileEntityHadron.delaySuccess;
		this.state = EnumHadronState.SUCCESS;
		setStats(this.state, p.momentum, true);
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.analysisOnly = data.getBoolean("analysis");
		this.ioMode = data.getInteger("ioMode");
		this.state = EnumHadronState.values()[data.getByte("state")];

		this.stat_success = data.getBoolean("stat_success");
		this.stat_state = EnumHadronState.values()[data.getByte("stat_state")];
		this.stat_charge = data.getInteger("stat_charge");
		this.stat_x = data.getInteger("stat_x");
		this.stat_y = data.getInteger("stat_y");
		this.stat_z = data.getInteger("stat_z");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0)
			this.isOn = !this.isOn;
		if(meta == 1)
			this.analysisOnly = !this.analysisOnly;
		if(meta == 2) {
			this.ioMode++;
			if(this.ioMode > 2) this.ioMode = 0;
		}
		
		markChanged();
	}
	
	private void drawPower() {
		
		for(ForgeDirection dir : getRandomDirs()) {
			
			if(this.power == TileEntityHadron.maxPower)
				return;

			int x = this.xCoord + dir.offsetX * 2;
			int y = this.yCoord + dir.offsetY * 2;
			int z = this.zCoord + dir.offsetZ * 2;
			
			TileEntity te = this.worldObj.getTileEntity(x, y, z);
			
			if(te instanceof TileEntityHadronPower) {
				
				TileEntityHadronPower plug = (TileEntityHadronPower)te;
				
				long toDraw = Math.min(TileEntityHadron.maxPower - this.power, plug.getPower());
				setPower(this.power + toDraw);
				plug.setPower(plug.getPower() - toDraw);
			}
		}
	}
	
	private void finishParticle(Particle p) {
		this.particlesToRemove.add(p);
		
		if(!p.isExpired())
			process(p);
		
		p.expired = true;
	}
	
	static final int maxParticles = 1;
	List<Particle> particles = new ArrayList<>();
	List<Particle> particlesToRemove = new ArrayList<>();
	
	private void updateParticles() {
		
		for(Particle particle : this.particles) {
			particle.update();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.isOn = nbt.getBoolean("isOn");
		this.power = nbt.getLong("power");
		this.analysisOnly = nbt.getBoolean("analysis");
		this.ioMode = nbt.getInteger("ioMode");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isOn", this.isOn);
		nbt.setLong("power", this.power);
		nbt.setBoolean("analysis", this.analysisOnly);
		nbt.setInteger("ioMode", this.ioMode);
	}
	
	public int getPowerScaled(int i) {
		return (int)(this.power * i / TileEntityHadron.maxPower);
	}

	@Override
	public void setPower(long i) {
		this.power = i;
		markDirty();
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityHadron.maxPower;
	}
	
	@Override
	public boolean canConnect(ForgeDirection dir) {
		return false;
	}
	
	private void setStats(EnumHadronState state, int count, boolean success) {
		this.stat_state = state;
		this.stat_charge = count;
		this.stat_success = success;
	}
	
	private void setExpireStats(EnumHadronState state, int count, int x, int y, int z) {
		this.stat_state = state;
		this.stat_charge = count;
		this.stat_x = x;
		this.stat_y = y;
		this.stat_z = z;
		this.stat_success = false;
	}
	
	public class Particle {
		
		//Starting values
		ItemStack item1;
		ItemStack item2;
		ForgeDirection dir;
		int posX;
		int posY;
		int posZ;
		
		//Progressing values
		int momentum;
		int charge;
		int analysis;
		boolean isCheckExempt = false;
		int cl0 = 0;
		int cl1 = 0;
		
		boolean expired = false;
		
		public Particle(ItemStack item1, ItemStack item2, ForgeDirection dir, int posX, int posY, int posZ) {
			this.item1 = item1.copy();
			this.item2 = item2.copy();
			this.item1.stackSize = 1;
			this.item2.stackSize = 1;
			this.dir = dir;
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			
			this.charge = 750;
			this.momentum = 0;
		}
		
		public void expire(EnumHadronState reason) {
			
			if(this.expired)
				return;
			
			this.expired = true;
			TileEntityHadron.this.particlesToRemove.add(this);
			TileEntityHadron.this.worldObj.newExplosion(null, this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, 10, false, false);
			//System.out.println("Last dir: " + dir.name());
			//System.out.println("Last pos: " + posX + " " + posY + " " + posZ);
			//Thread.currentThread().dumpStack();

			TileEntityHadron.this.state = reason;
			TileEntityHadron.this.delay = TileEntityHadron.delayError;
			setExpireStats(reason, this.momentum, this.posX, this.posY, this.posZ);
		}
		
		public boolean isExpired() {
			return this.expired;
		}
		
		public void update() {
			
			if(this.expired) //just in case
				return;

			changeDirection(this);
			makeSteppy(this);
			
			if(!isExpired()) //only important for when the current segment is the core
				checkSegment(this);
			
			this.isCheckExempt = false; //clearing up the exemption we might have held from the previous turn, AFTER stepping
			
			if(this.charge < 0)
				expire(EnumHadronState.ERROR_NO_CHARGE);

			if(this.cl0 > 0) this.cl0--;
			if(this.cl1 > 0) this.cl1--;
		}

		public void incrementCharge(Block block, int meta, int coilVal) {

			if(block == ModBlocks.hadron_cooler) {
				if(meta == 0) this.cl0 += 10;
				if(meta == 1) this.cl1 += 5;
			}
			
			//not the best code ever made but it works, dammit
			if(this.cl1 > 0) {
				
				double mult = 2D - (this.cl1 - 15D) * (this.cl1 - 15D) / 225D;
				mult = Math.max(mult, 0.1D);
				coilVal *= mult;
				
			} else if(this.cl0 > 0) {
				if(this.cl0 > 10) {
					coilVal *= 0.75;
				} else {
					coilVal *= 1.10;
				}
			}
			
			this.momentum += coilVal;
		}
	}
	
	/**
	 * Moves the particle and does all the checks required to do so
	 * Handles diode entering behavior and whatnot
	 * @param p
	 */
	public void makeSteppy(Particle p) {
		
		ForgeDirection dir = p.dir;

		p.posX += dir.offsetX;
		p.posY += dir.offsetY;
		p.posZ += dir.offsetZ;
		
		int x = p.posX;
		int y = p.posY;
		int z = p.posZ;
		
		Block block = this.worldObj.getBlock(x, y, z);
		TileEntity te = this.worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityHadron) {

			if(p.analysis != 3)
				p.expire(EnumHadronState.ERROR_NO_ANALYSIS);
			else
				finishParticle(p);
			
			return;
		}
		
		if(block.getMaterial() != Material.air && block != ModBlocks.hadron_diode)
			p.expire(EnumHadronState.ERROR_OBSTRUCTED_CHANNEL);
		
		if(block == ModBlocks.hadron_diode)
			p.isCheckExempt = true;
		
		if(isValidCoil(this.worldObj.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)))
			p.isCheckExempt = true;
	}
	
	/**
	 * All the checks done *after* the particle moves one tile
	 * @param p
	 */
	public void checkSegment(Particle p) {
		
		ForgeDirection dir = p.dir;
		int x = p.posX;
		int y = p.posY;
		int z = p.posZ;

		//we make a set of axis where the directional axis is 0 and the normals are 1
		//that allows us to easily iterate through a rectangle that is normal to our moving direction
		int dX = 1 - Math.abs(dir.offsetX);
		int dY = 1 - Math.abs(dir.offsetY);
		int dZ = 1 - Math.abs(dir.offsetZ);
		
		//whether the particle has entered an analysis chamber
		//-> all coils have to be air
		//-> all platings have to be analysis chamber walls
		boolean analysis = true;
		
		for(int a = x - dX * 2; a <= x + dX * 2; a++) {
			for(int b = y - dY * 2; b <= y + dY * 2; b++) {
				for(int c = z - dZ * 2; c <= z + dZ * 2;c++) {
					
					Block block = this.worldObj.getBlock(a, b, c);
					int meta = this.worldObj.getBlockMetadata(a, b, c);
					
					/** ignore the center for now */
					if(a == x && b == y && c == z) {
						
						//we are either in a diode or the core - no analysis for you now
						if(block.getMaterial() != Material.air)
							analysis = false;
						
						continue;
					}

					int ix = Math.abs(x - a);
					int iy = Math.abs(y - b);
					int iz = Math.abs(z - c);
					
					/** check coils, all abs deltas are 1 or less */
					if(ix <= 1 && iy <= 1 && iz <= 1) {
						
						//are we exempt from the coil examination? nice, skip checks only for inner magnets, not the corners!
						//coil is air, analysis can remain true
						if((p.isCheckExempt && ix + iy + iz == 1) || (block.getMaterial() == Material.air && analysis)) {
							continue;
						}
						
						//not air -> not an analysis chamber
						analysis = false;
						
						int coilVal = coilValue(block);
						
						//not a valid coil: kablam!
						if(!isValidCoil(block)) {
							p.expire(EnumHadronState.ERROR_EXPECTED_COIL);
						} else {
							p.charge -= coilVal;
							p.incrementCharge(block, meta, coilVal);
						}

						continue;
					}
					
					/** now we check the plating, sum of all local positions being 3 or less gives us the outer plating without corners */
					if(ix + iy + iz <= 3) {
						
						//if the plating is for the analysis chamber, continue no matter what
						if(isAnalysis(block))
							continue;

						//no analysis chamber -> turn off analysis and proceed
						analysis = false;
						
						//a plating? good, continue
						if(isPlating(block))
							continue;
						
						TileEntity te = this.worldObj.getTileEntity(a, b, c);
						
						//power plugs are also ok, might as well succ some energy when passing
						if(te instanceof TileEntityHadronPower) {
							
							TileEntityHadronPower plug = (TileEntityHadronPower)te;
							
							long bit = 10000;							//how much HE one "charge point" is
							int times = (int) (plug.getPower() / bit);	//how many charges the plug has to offer
							
							p.charge += times;
							plug.setPower(plug.getPower() - times * bit);
							
							continue;
						}
						
						//Are we exempt from checking the plating? skip all the plating blocks where branches could be
						if(p.isCheckExempt && ix + iy + iz == 2) {
							continue;
						}
						
						//System.out.println("Was exempt: " + p.isCheckExempt);
						//worldObj.setBlock(a, b, c, Blocks.dirt);

						p.expire(EnumHadronState.ERROR_MALFORMED_SEGMENT);
					}
				}
			}
		}
		
		if(analysis) {
			
			p.analysis++;
			
			//if the analysis chamber is too big, destroy
			if(p.analysis > 3)
				p.expire(EnumHadronState.ERROR_ANALYSIS_TOO_LONG);
			
			if(p.analysis == 2) {
				this.worldObj.playSoundEffect(p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5, "fireworks.blast", 2.0F, 2F);
				this.state = EnumHadronState.ANALYSIS;
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "hadron");
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5), new TargetPoint(this.worldObj.provider.dimensionId, p.posX + 0.5, p.posY + 0.5, p.posZ + 0.5, 25));
			}

			//if operating in line accelerator mode, halt after 2 blocks and staart the reading
			if(this.analysisOnly && p.analysis == 2) {
				finishParticle(p);
			}
			
		} else {

			//if the analysis stops despite being short of 3 steps in the analysis chamber, destroy
			if(p.analysis > 0 && p.analysis < 3)
				p.expire(EnumHadronState.ERROR_ANALYSIS_TOO_SHORT);
		}
	}
	
	/**
	 * Checks whether we can go forward or if we might want to do a turn
	 * Handles the better part of the diode behavior
	 * @param p
	 */
	public void changeDirection(Particle p) {
		
		ForgeDirection dir = p.dir;
		
		int x = p.posX;
		int y = p.posY;
		int z = p.posZ;

		int nx = x + dir.offsetX;
		int ny = y + dir.offsetY;
		int nz = z + dir.offsetZ;
		
		Block next = this.worldObj.getBlock(nx, ny, nz);
		
		TileEntity te = this.worldObj.getTileEntity(nx, ny, nz);
		
		//the next block appears to be a diode, let's see if we can enter
		if(te instanceof TileEntityHadronDiode) {
			TileEntityHadronDiode diode = (TileEntityHadronDiode)te;
			
			if(diode.getConfig(p.dir.getOpposite().ordinal()) != DiodeConfig.IN) {
				//it appears as if we have slammed into the side of a diode, ouch
				p.expire(EnumHadronState.ERROR_DIODE_COLLISION);
			}
			
			//there's a diode ahead, turn off checks so we can make the curve
			p.isCheckExempt = true;
			
			//the *next* block is a diode, we are not in it yet, which means no turning and no check exemption. too bad kiddo.
			return;
		}
		
		//instead of the next TE, we are looking at the current one - the diode (maybe)
		te = this.worldObj.getTileEntity(x, y, z);
		
		//if we are currently in a diode, we might want to consider changing dirs
		if(te instanceof TileEntityHadronDiode) {
			
			//since we are *in* a diode, we might want to call the warrant officer for
			//an exemption for the coil check, because curves NEED holes to turn into, and
			//checking for coils in spaces where there cannot be coils is quite not-good
			p.isCheckExempt = true;
			
			TileEntityHadronDiode diode = (TileEntityHadronDiode)te;
			
			//the direction in which we were going anyway is an output, so we will keep going
			if(diode.getConfig(dir.ordinal()) == DiodeConfig.OUT) {
				return;
				
			//well then, iterate through some random directions and hope a valid output shows up
			} else {
				
				List<ForgeDirection> dirs = getRandomDirs();
				
				for(ForgeDirection d : dirs) {
					
					if(d == dir || d == dir.getOpposite())
						continue;
					
					//looks like we can leave!
					if(diode.getConfig(d.ordinal()) == DiodeConfig.OUT) {
						//set the direction and leave this hellhole
						p.dir = d;
						return;
					}
				}
			}
		}
		
		//next step is air or the core, proceed
		if(next.getMaterial() == Material.air || next == ModBlocks.hadron_core)
			return;
		
		//so, the next block is most certainly a wall. not good. perhaps we could try turning?
		if(isValidCoil(next)) {
			
			ForgeDirection validDir = ForgeDirection.UNKNOWN;
			
			List<ForgeDirection> dirs = getRandomDirs();
			
			//let's look at every direction we could go in
			for(ForgeDirection d : dirs) {
				
				if(d == dir || d == dir.getOpposite())
					continue;
				
				//there is air! we can pass!
				if(this.worldObj.getBlock(x + d.offsetX, y + d.offsetY, z + d.offsetZ).getMaterial() == Material.air) {
					
					if(validDir == ForgeDirection.UNKNOWN) {
						validDir = d;
					
					//it seems like there are two or more possible ways, which is not allowed without a diode
					//sorry kid, nothing personal
					} else {
						p.expire(EnumHadronState.ERROR_BRANCHING_TURN);
						return;
					}
				}
			}
			
			//set the new direction
			p.dir = validDir;
			p.isCheckExempt = true;
			return;
		}

		p.expire(EnumHadronState.ERROR_OBSTRUCTED_CHANNEL);
	}
	
	/**
	 * Dear god please grant me the gift of death and end my eternal torment
	 * @return
	 */
	private List<ForgeDirection> getRandomDirs() {
		
		List<Integer> rands = Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 5} );
		Collections.shuffle(rands);
		List<ForgeDirection> dirs = new ArrayList<>();
		for(Integer i : rands) {
			dirs.add(ForgeDirection.getOrientation(i));
		}
		return dirs;
	}
	
	public boolean isValidCoil(Block b) {
		if((coilValue(b) > 0) || (b == ModBlocks.hadron_cooler)) return true;
		
		return false;
	}
	
	public int coilValue(Block b) {
		
		if(b instanceof BlockHadronCoil)
			return ((BlockHadronCoil)b).factor;
		
		return 0;
	}
	
	public boolean isPlating(Block b) {
		
		return b instanceof BlockHadronPlating ||
				b instanceof BlockHadronCoil ||
				b == ModBlocks.hadron_plating_glass ||
				b == ModBlocks.hadron_analysis_glass ||
				b == ModBlocks.hadron_access;
	}
	
	public boolean isAnalysis(Block b) {
		
		return b == ModBlocks.hadron_analysis ||
				b == ModBlocks.hadron_analysis_glass;
	}
	
	public static enum EnumHadronState {
		IDLE(0x8080ff),
		PROGRESS(0xffff00),
		ANALYSIS(0xffff00),
		NORESULT(0xff8000),
		NORESULT_TOO_SLOW(0xff8000),
		NORESULT_WRONG_INGREDIENT(0xff8000),
		NORESULT_WRONG_MODE(0xff8000),
		SUCCESS(0x00ff00),
		ERROR_NO_CHARGE(0xff0000, true),
		ERROR_NO_ANALYSIS(0xff0000, true),
		ERROR_OBSTRUCTED_CHANNEL(0xff0000, true),
		ERROR_EXPECTED_COIL(0xff0000, true),
		ERROR_MALFORMED_SEGMENT(0xff0000, true),
		ERROR_ANALYSIS_TOO_LONG(0xff0000, true),
		ERROR_ANALYSIS_TOO_SHORT(0xff0000, true),
		ERROR_DIODE_COLLISION(0xff0000, true),
		ERROR_BRANCHING_TURN(0xff0000, true),
		ERROR_GENERIC(0xff0000, true);
		
		public int color;
		public boolean showCoord;
		
		private EnumHadronState(int color) {
			this(color, false);
		}
		
		private EnumHadronState(int color, boolean showCoord) {
			this.color = color;
			this.showCoord = showCoord;
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerHadron(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIHadron(player.inventory, this);
	}
}
