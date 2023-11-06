package com.hbm.items.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class ItemDebugStick extends Item {
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			MovingObjectPosition look = Minecraft.getMinecraft().objectMouseOver;
			Block block = world.getBlock(look.blockX, look.blockY, look.blockZ);
			TileEntity tile = world.getTileEntity(look.blockX, look.blockY, look.blockZ);
			if (!(block instanceof BlockAir))
				player.addChatMessage(new ChatComponentText("Debug (Block)=>" + block.getClass()));
			if (tile != null)
				player.addChatMessage(new ChatComponentText("Debug (TileEntity)=>" + tile.getClass()));
		}
		return super.onItemRightClick(stack, world, player);
	}
	
}