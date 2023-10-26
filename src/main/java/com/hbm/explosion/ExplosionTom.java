package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ExplosionTom {
	public int posX;
	public int posY;
	public int posZ;
	public int lastposX = 0;
	public int lastposZ = 0;
	public int radius;
	public int radius2;
	public World worldObj;
	private int n = 1;
	private int nlimit;
	private int shell;
	private int leg;
	private int element;

	public void saveToNbt(NBTTagCompound nbt, String name) {
		nbt.setInteger(name + "posX", this.posX);
		nbt.setInteger(name + "posY", this.posY);
		nbt.setInteger(name + "posZ", this.posZ);
		nbt.setInteger(name + "lastposX", this.lastposX);
		nbt.setInteger(name + "lastposZ", this.lastposZ);
		nbt.setInteger(name + "radius", this.radius);
		nbt.setInteger(name + "radius2", this.radius2);
		nbt.setInteger(name + "n", this.n);
		nbt.setInteger(name + "nlimit", this.nlimit);
		nbt.setInteger(name + "shell", this.shell);
		nbt.setInteger(name + "leg", this.leg);
		nbt.setInteger(name + "element", this.element);
	}

	public void readFromNbt(NBTTagCompound nbt, String name) {
		this.posX = nbt.getInteger(name + "posX");
		this.posY = nbt.getInteger(name + "posY");
		this.posZ = nbt.getInteger(name + "posZ");
		this.lastposX = nbt.getInteger(name + "lastposX");
		this.lastposZ = nbt.getInteger(name + "lastposZ");
		this.radius = nbt.getInteger(name + "radius");
		this.radius2 = nbt.getInteger(name + "radius2");
		this.n = nbt.getInteger(name + "n");
		this.nlimit = nbt.getInteger(name + "nlimit");
		this.shell = nbt.getInteger(name + "shell");
		this.leg = nbt.getInteger(name + "leg");
		this.element = nbt.getInteger(name + "element");
	}

	public ExplosionTom(int x, int y, int z, World world, int rad) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;

		this.worldObj = world;

		this.radius = rad;
		this.radius2 = this.radius * this.radius;

		this.nlimit = this.radius2 * 4;
	}

	public boolean update() {
		breakColumn(this.lastposX, this.lastposZ);
		this.shell = (int) Math.floor((Math.sqrt(this.n) + 1) / 2);
		int shell2 = this.shell * 2;
		this.leg = (int) Math.floor((this.n - (shell2 - 1) * (shell2 - 1)) / shell2);
		this.element = (this.n - (shell2 - 1) * (shell2 - 1)) - shell2 * this.leg - this.shell + 1;
		this.lastposX = this.leg == 0 ? this.shell : this.leg == 1 ? -this.element : this.leg == 2 ? -this.shell : this.element;
		this.lastposZ = this.leg == 0 ? this.element : this.leg == 1 ? this.shell : this.leg == 2 ? -this.element : -this.shell;
		this.n++;
		return this.n > this.nlimit;
	}

	private void breakColumn(int x, int z) {
		int dist = this.radius2 - (x * x + z * z);

		if(dist > 0) {
			int pX = this.posX + x;
			int pZ = this.posZ + z;
			double X = Math.pow((this.posX - pX), 2);
			double Z = Math.pow((this.posZ - pZ), 2);
			double distance = MathHelper.sqrt_double(X + Z); // Distance calculations used for crater rim stuff

			int y = 256;
			int terrain = 63;

			double cA = (terrain - Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z), 2) / 40000) * 13) + this.worldObj.rand.nextInt(2); // Basic crater bowl shape
			double cB = cA + Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z) - 200, 2) / 400) * 13 ;// Crater peak ring
			int craterFloor = (int) (cB + Math.pow(Math.E, -Math.pow(Math.sqrt(x * x + z * z) - 500, 2) / 2000) * 37); // Crater rim
			for(int i = 256; i > 0; i--) {
				if(i == craterFloor || this.worldObj.getBlock(pX, i, pZ) != Blocks.air) {
					y = i;
					break;
				}
			}
			int height = terrain - 14;
			int offset = 20;
			int threshold = (int) ((float) Math.sqrt(x * x + z * z) * (float) (height + offset) / (float) this.radius) + this.worldObj.rand.nextInt(2) - offset;

			while(y > threshold) {

				if(y == 0)
					break;
				if(y <= craterFloor) {

					if(this.worldObj.rand.nextInt(499) < 1) {
						this.worldObj.setBlock(pX, y, pZ, ModBlocks.ore_tektite_osmiridium);
					} else {
						this.worldObj.setBlock(pX, y, pZ, ModBlocks.tektite);
					}

				} else {
					if(y > terrain + 1) {
						if(distance < 500) // used so that old terrain inside crater rim is destroyed, while rim material "floods" terrain outside.
						{
							for(int i = -2; i < 3; i++) {
								for(int j = -2; j < 3; j++) {
									for(int k = -2; k < 3; k++) {
										if(this.worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.water || this.worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.ice || this.worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.snow || this.worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial().getCanBurn()) {
											this.worldObj.setBlockToAir(pX + i, y + j, pZ + k);
											this.worldObj.setBlockToAir(pX, y, pZ);
										}
									}
								}
							}
							this.worldObj.setBlockToAir(pX, y, pZ);
						}
					} else {
						for(int i = -2; i < 3; i++) {
							for(int j = -2; j < 3; j++) {
								for(int k = -2; k < 3; k++) {
									if(this.worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.water || this.worldObj.getBlock(pX + i, y + j, pZ + k).getMaterial() == Material.ice || this.worldObj.getBlock(pX + i, y, pZ + k) == Blocks.air) {
										this.worldObj.setBlock(pX + i, y, pZ + k, Blocks.lava, 0, 2);
										this.worldObj.setBlock(pX, y, pZ, Blocks.lava, 0, 2);
									}
								}
							}
						}
						this.worldObj.setBlock(pX, y, pZ, Blocks.lava, 0, 2);
					}

				}
				y--;
			}
		}
	}
}