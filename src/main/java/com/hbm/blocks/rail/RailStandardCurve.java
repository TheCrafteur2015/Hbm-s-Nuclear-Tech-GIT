package com.hbm.blocks.rail;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RailStandardCurve extends BlockDummyable implements IRailNTM {

	public RailStandardCurve() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return RailStandardCurve.renderID;
	}

	@Override
	public Vec3 getSnappingPos(World world, int x, int y, int z, double trainX, double trainY, double trainZ) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, 0, 0, 0, 0, new RailContext());
	}

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info, MoveContext context) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, motionX, motionY, motionZ, speed, info);
	}
	
	/* Very simple function determining the snapping position and adding the motion value to it, if desired. */
	public Vec3 snapAndMove(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info) {
		int[] pos = findCore(world, x, y, z);
		if(pos == null) return Vec3.createVectorHelper(trainX, trainY, trainZ);
		int cX = pos[0];
		int cY = pos[1];
		int cZ = pos[2];
		int meta = world.getBlockMetadata(cX, cY, cZ) - BlockDummyable.offset;
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		double turnRadius = 4D;
		double axisDist = 4.5D;

		Vec3 vec = Vec3.createVectorHelper(trainX, trainY, trainZ);
		double axisX = cX + 0.5  + dir.offsetX * 0.5 + rot.offsetX * axisDist;
		double axisZ = cZ + 0.5  + dir.offsetZ * 0.5 + rot.offsetZ * axisDist;
		
		Vec3 dist = Vec3.createVectorHelper(vec.xCoord - axisX, 0, vec.zCoord - axisZ);
		dist = dist.normalize();
		dist.xCoord *= turnRadius;
		dist.zCoord *= turnRadius;
		
		double moveAngle = Math.atan2(motionX, motionZ) * 180D / Math.PI + 90;
		
		if(speed == 0) {
			info.dist(0).pos(new BlockPos(x, y, z)).yaw((float) moveAngle);
			return Vec3.createVectorHelper(axisX + dist.xCoord, y, axisZ + dist.zCoord);
		}
		
		double angleDeg = Math.atan2(dist.xCoord, dist.zCoord) * 180D / Math.PI + 90;
		if(dir == Library.NEG_X) angleDeg -= 90;
		if(dir == Library.POS_X) angleDeg += 90;
		if(dir == Library.POS_Z) angleDeg += 180;
		angleDeg = MathHelper.wrapAngleTo180_double(angleDeg);
		double length90Deg = turnRadius * Math.PI / 2D;
		double angularChange = speed / length90Deg * 90D;
		
		ForgeDirection moveDir = ForgeDirection.UNKNOWN;
		
		if(Math.abs(motionX) > Math.abs(motionZ)) {
			moveDir = motionX > 0 ? Library.POS_X : Library.NEG_X;
		} else {
			moveDir = motionZ > 0 ? Library.POS_Z : Library.NEG_Z;
		}
		
		if(moveDir == dir || moveDir == rot.getOpposite()) {
			angularChange *= -1;
		}
		
		double effAngle = angleDeg + angularChange;
		moveAngle += angularChange;
		
		if(effAngle > 90) {
			double angleOvershoot = effAngle - 90D;
			moveAngle -= angleOvershoot;
			double lengthOvershoot = angleOvershoot * length90Deg / 90D;
			info.dist(lengthOvershoot * Math.signum(speed * angularChange)).pos(new BlockPos(cX - dir.offsetX * 4 + rot.offsetX * 5, y, cZ - dir.offsetZ * 4 + rot.offsetZ * 5)).yaw((float) moveAngle);
			return Vec3.createVectorHelper(axisX - dir.offsetX * turnRadius, y + 0.1875, axisZ - dir.offsetZ * turnRadius);
		}
		
		if(effAngle < 0) {
			double angleOvershoot = -effAngle;
			moveAngle -= angleOvershoot;
			double lengthOvershoot = angleOvershoot * length90Deg / 90D;
			info.dist(-lengthOvershoot * Math.signum(speed * angularChange)).pos(new BlockPos(cX + dir.offsetX , y, cZ + dir.offsetZ)).yaw((float) moveAngle);
			return Vec3.createVectorHelper(axisX - rot.offsetX * turnRadius, y + 0.1875, axisZ -rot.offsetZ * turnRadius);
		}
		
		double radianChange = angularChange * Math.PI / 180D;
		dist.rotateAroundY((float) radianChange);
		
		return Vec3.createVectorHelper(axisX + dist.xCoord, y + 0.1875, axisZ + dist.zCoord);
	}

	@Override
	public TrackGauge getGauge(World world, int x, int y, int z) {
		return TrackGauge.STANDARD;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 4, 0, 4, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		dir = dir.getOpposite();

		int dX = dir.offsetX;
		int dZ = dir.offsetZ;
		int rX = rot.offsetX;
		int rZ = rot.offsetZ;
		
		return world.getBlock(x + dX, y, z + dZ).isReplaceable(world, x + dX, y, z + dZ) &&
				world.getBlock(x + rX, y, z + rZ).isReplaceable(world, x + rX, y, z + rZ) &&
				world.getBlock(x + dX + rX, y, z + dZ + rZ).isReplaceable(world, x + dX + rX, y, z + dZ + rZ) &&
				world.getBlock(x + dX + rX * 2, y, z + dZ + rZ * 2).isReplaceable(world, x + dX + rX * 2, y, z + dZ + rZ * 2) &&
				world.getBlock(x + dX * 2 + rX, y, z + dZ * 2 + rZ).isReplaceable(world, x + dX * 2 + rX, y, z + dZ * 2 + rZ) &&
				world.getBlock(x + dX * 2 + rX * 2, y, z + dZ * 2 + rZ * 2).isReplaceable(world, x + dX * 2 + rX * 2, y, z + dZ * 2 + rZ * 2) &&
				world.getBlock(x + dX * 3 + rX, y, z + dZ * 3 + rZ).isReplaceable(world, x + dX * 3 + rX, y, z + dZ * 3 + rZ) &&
				world.getBlock(x + dX * 3 + rX * 2, y, z + dZ * 3 + rZ * 2).isReplaceable(world, x + dX * 3 + rX * 2, y, z + dZ * 3 + rZ * 2) &&
				world.getBlock(x + dX * 2 + rX * 3, y, z + dZ * 2 + rZ * 3).isReplaceable(world, x + dX * 2 + rX * 3, y, z + dZ * 2 + rZ * 3) &&
				world.getBlock(x + dX * 3 + rX * 3, y, z + dZ * 3 + rZ * 3).isReplaceable(world, x + dX * 3 + rX * 3, y, z + dZ * 3 + rZ * 3) &&
				world.getBlock(x + dX * 4 + rX * 3, y, z + dZ * 4 + rZ * 3).isReplaceable(world, x + dX * 4 + rX * 3, y, z + dZ * 4 + rZ * 3) &&
				world.getBlock(x + dX * 3 + rX * 4, y, z + dZ * 3 + rZ * 4).isReplaceable(world, x + dX * 3 + rX * 4, y, z + dZ * 3 + rZ * 4) &&
				world.getBlock(x + dX * 4 + rX * 4, y, z + dZ * 4 + rZ * 4).isReplaceable(world, x + dX * 4 + rX * 4, y, z + dZ * 4 + rZ * 4);
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		BlockDummyable.safeRem = true;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		dir = dir.getOpposite();

		int dX = dir.offsetX;
		int dZ = dir.offsetZ;
		int rX = rot.offsetX;
		int rZ = rot.offsetZ;
		
		world.setBlock(x + dX, y, z + dZ, this, dir.ordinal(), 3);
		world.setBlock(x + rX, y, z + rZ, this, rot.ordinal(), 3);
		world.setBlock(x + dX + rX, y, z + dZ + rZ, this, rot.ordinal(), 3);
		world.setBlock(x + dX + rX * 2, y, z + dZ + rZ * 2, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 2 + rX, y, z + dZ * 2 + rZ, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 2 + rX * 2, y, z + dZ * 2 + rZ * 2, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 3 + rX, y, z + dZ * 3 + rZ, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 3 + rX * 2, y, z + dZ * 3 + rZ * 2, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 2 + rX * 3, y, z + dZ * 2 + rZ * 3, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 3 + rX * 3, y, z + dZ * 3 + rZ * 3, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 4 + rX * 3, y, z + dZ * 4 + rZ * 3, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 3 + rX * 4, y, z + dZ * 3 + rZ * 4, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 4 + rX * 4, y, z + dZ * 4 + rZ * 4, this, rot.ordinal(), 3);
		
		BlockDummyable.safeRem = false;
	}
}
