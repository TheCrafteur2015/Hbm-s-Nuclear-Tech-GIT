package com.hbm.blocks.machine;

/*
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IRadResistantBlock;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IDoor;
import com.hbm.interfaces.IKeypadHandler;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.TileEntitySlidingBlastDoorKeypad;
import com.hbm.tileentity.machine.TileEntitySlidingBlastDoor;
import com.hbm.util.KeypadClient;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSlidingBlastDoor extends BlockDummyable implements IRadResistantBlock, ITooltipProvider {
	
	public DoorDecl type;
	
	public BlockSlidingBlastDoor(Material materialIn, DoorDecl dcl) {
		super(materialIn);
		this.type = dcl;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(this == ModBlocks.sliding_blast_door_keypad) {
			return new TileEntitySlidingBlastDoorKeypad();
		}
		if(meta >= 12)
			return new TileEntitySlidingBlastDoor();
		return null;
	}

	
	@Override 
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean ext) {
		float hardness = this.getExplosionResistance(null);
		tooltip.add("§2[Radiation Shielding]§r");
		tooltip.add("§6Blast Resistance: "+hardness+"§r");
		if(this == ModBlocks.sliding_blast_door_keypad){
			tooltip.add("Variant: Keypad");
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer playerIn, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(world.isRemote && te instanceof IKeypadHandler) {
			return handleClickClient(te, pos);
		}
		if(!world.isRemote && !playerIn.isSneaking()) {
			if(world.getBlockState(pos).getBlock() == ModBlocks.sliding_blast_door_keypad)
				return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
			int[] pos1 = findCore(world, pos.getX(), pos.getY(), pos.getZ());
			if(pos1 == null)
				return false;
			TileEntitySlidingBlastDoor door = (TileEntitySlidingBlastDoor) world.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));

			if(door != null) {
				return door.tryToggle(playerIn);
			}
		}
		return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean handleClickClient(TileEntity te, BlockPos pos){
		KeypadClient pad = ((IKeypadHandler) te).getKeypad().client();
		if(pad.isPlayerMouseingOver(pos)) {
			return pad.client().playerClick(pos);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(world.isRemote && te instanceof IKeypadHandler) {
			KeypadClient pad = ((IKeypadHandler) te).getKeypad().client();
			AxisAlignedBB key = pad.rayTrace(pos);
			if(key != null) {
				return key;
			}
		}
		return super.getSelectedBoundingBox(state, world, pos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		AxisAlignedBB box = state.getCollisionBoundingBox(worldIn, pos);
		if(box.minY == 0 && box.maxY == 0)
			return;
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int meta = state.getValue(META);
		if(this == ModBlocks.sliding_blast_door_keypad)
			return FULL_BLOCK_AABB;
		if(hasExtra(meta)) {
			if(source.getBlockState(pos.up()).getBlock() == this) {
				return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
			}
			return new AxisAlignedBB(0, 0.5, 0, 1, 1, 1);
		}
		TileEntity te = source.getTileEntity(pos);
		if(te instanceof TileEntitySlidingBlastDoor && !((TileEntitySlidingBlastDoor) te).shouldUseBB) {
			return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
		}
		return FULL_BLOCK_AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 0, 0, 3, 3 };
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == ModBlocks.sliding_blast_door_2) {
			BlockPos pos = new BlockPos(x, y + 1, z).offset(dir.toEnumFacing().rotateY(), 3);
			BlockPos pos2 = new BlockPos(x, y + 1, z).offset(dir.toEnumFacing().rotateYCCW(), 3);
			int meta = world.getBlockState(pos).getValue(META);
			int meta2 = world.getBlockState(pos2).getValue(META);
			BlockDummyable.safeRem = true;
			world.setBlockState(pos, ModBlocks.sliding_blast_door_keypad.getDefaultState().withProperty(META, meta));
			world.setBlockState(pos2, ModBlocks.sliding_blast_door_keypad.getDefaultState().withProperty(META, meta2+extra));
			BlockDummyable.safeRem = false;
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean isRadResistant(World worldIn, BlockPos blockPos){

		//MainRegistry.logger.info("isRadResistant");
		if (worldIn != null) {
			//MainRegistry.logger.info("checking door @ " + blockPos);
			TileEntity entity = worldIn.getTileEntity(blockPos);
			if (entity != null) {
				//MainRegistry.logger.info("tile entity " + entity);
				if (IDoor.class.isAssignableFrom(entity.getClass())) {
					// Doors should be rad resistant only when closed
					//MainRegistry.logger.info("door state: " + (((IDoor) entity).getState() == IDoor.DoorState.CLOSED));
					//MainRegistry.logger.info("door pos: " + blockPos);
					return ((IDoor) entity).getState() == IDoor.DoorState.CLOSED;
				}
			}
		}

		return true;
	}

	@Override
	public int getResistance() {
		return 1;
	}

}
*/