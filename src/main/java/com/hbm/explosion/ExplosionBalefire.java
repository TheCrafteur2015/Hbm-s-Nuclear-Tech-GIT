package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ExplosionBalefire
{
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
		this.n = Math.max(nbt.getInteger(name + "n"), 1); //prevents invalid read operation
		this.nlimit = nbt.getInteger(name + "nlimit");
		this.shell = nbt.getInteger(name + "shell");
		this.leg = nbt.getInteger(name + "leg");
		this.element = nbt.getInteger(name + "element");
	}
	
	public ExplosionBalefire(int x, int y, int z, World world, int rad)
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.worldObj = world;
		
		this.radius = rad;
		this.radius2 = this.radius * this.radius;

		this.nlimit = this.radius2 * 4;
	}
	
	public boolean update() {
		
		if(this.n == 0) return true;
		
		breakColumn(this.lastposX, this.lastposZ);
		this.shell = (int) Math.floor((Math.sqrt(this.n) + 1) / 2);
		int shell2 = this.shell * 2;
		
		if(shell2 == 0) return true;
		
		this.leg = (int) Math.floor((this.n - (shell2 - 1) * (shell2 - 1)) / shell2);
		this.element = (this.n - (shell2 - 1) * (shell2 - 1)) - shell2 * this.leg - this.shell + 1;
		this.lastposX = this.leg == 0 ? this.shell : this.leg == 1 ? -this.element : this.leg == 2 ? -this.shell : this.element;
		this.lastposZ = this.leg == 0 ? this.element : this.leg == 1 ? this.shell : this.leg == 2 ? -this.element : -this.shell;
		this.n++;
		return this.n > this.nlimit;
	}

	private void breakColumn(int x, int z)
	{
		int dist = (int) (this.radius - Math.sqrt(x * x + z * z));
		
		if (dist > 0) {
			int pX = this.posX + x;
			int pZ = this.posZ + z;
			
			int y  = this.worldObj.getHeightValue(pX, pZ);
			int maxdepth = (int) (10 + this.radius * 0.25);
			int depth = (int) ((maxdepth * dist / this.radius) + (Math.sin(dist * 0.15 + 2) * 2));//
			
			depth = Math.max(y - depth, 0);
			
			while(y > depth) {

				if(this.worldObj.getBlock(pX, y, pZ) == ModBlocks.block_schrabidium_cluster) {
					
					if(this.worldObj.rand.nextInt(10) == 0) {
						this.worldObj.setBlock(pX, y + 1, pZ, ModBlocks.balefire);
						this.worldObj.setBlock(pX, y, pZ, ModBlocks.block_euphemium_cluster, this.worldObj.getBlockMetadata(pX, y, pZ), 3);
					}
					return;
				}
				
				this.worldObj.setBlockToAir(pX, y, pZ);
				
				y--;
			}
			
			if(this.worldObj.rand.nextInt(10) == 0) {
				this.worldObj.setBlock(pX, depth + 1, pZ, ModBlocks.balefire);
				
				if(this.worldObj.getBlock(pX, y, pZ) == ModBlocks.block_schrabidium_cluster)
					this.worldObj.setBlock(pX, y, pZ, ModBlocks.block_euphemium_cluster, this.worldObj.getBlockMetadata(pX, y, pZ), 3);
			}

			for(int i = depth; i > depth - 5; i--) {
				if(this.worldObj.getBlock(pX, i, pZ) == Blocks.stone)
					this.worldObj.setBlock(pX, i, pZ, ModBlocks.sellafield_slaked);
			}
		}
	}

	/*private void breakColumn(int x, int z)
	{
		int dist = this.radius2 - (x * x + z * z);
		if (dist > 0)
		{
			int pX = posX + x;
			int pZ = posZ + z;
			
			int y  = worldObj.getHeightValue(pX, pZ);
			float strength = (float)dist / (float) this.radius;
			
			while(y > 0) {
				
				if(strength <= 10) {
					if(worldObj.rand.nextInt(10) == 0) {
						worldObj.setBlock(pX, y + 1, pZ, ModBlocks.balefire);
						
						if(worldObj.getBlock(pX, y, pZ) == ModBlocks.block_schrabidium_cluster)
							worldObj.setBlock(pX, y, pZ, ModBlocks.block_euphemium_cluster, worldObj.getBlockMetadata(pX, y, pZ), 3);
					}

					if(worldObj.getBlock(pX, y, pZ) == Blocks.stone)
						worldObj.setBlock(pX, y, pZ, ModBlocks.sellafield_slaked);
					if(worldObj.getBlock(pX, y - 1, pZ) == Blocks.stone)
						worldObj.setBlock(pX, y - 1, pZ, ModBlocks.sellafield_slaked);
					if(worldObj.getBlock(pX, y - 2, pZ) == Blocks.stone)
						worldObj.setBlock(pX, y - 2, pZ, ModBlocks.sellafield_slaked);
					if(worldObj.getBlock(pX, y - 3, pZ) == Blocks.stone)
						worldObj.setBlock(pX, y - 3, pZ, ModBlocks.sellafield_slaked);
					if(worldObj.getBlock(pX, y - 4, pZ) == Blocks.stone)
						worldObj.setBlock(pX, y - 4, pZ, ModBlocks.sellafield_slaked);
						
					return;
				}
				
				float hardness = worldObj.getBlock(pX, y, pZ).getBlockHardness(worldObj, pX, y, pZ);
				
				if(worldObj.getBlock(pX, y, pZ).getMaterial().isLiquid())
					hardness = Blocks.air.getBlockHardness(worldObj, pX, y + 1, pZ);
				
				strength -= hardness;
				
				worldObj.setBlockToAir(pX, y, pZ);
				
				y--;
			}
		}
	}*/
}
