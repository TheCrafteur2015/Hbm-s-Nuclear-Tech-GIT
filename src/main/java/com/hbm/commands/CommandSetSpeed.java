package com.hbm.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class CommandSetSpeed extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "setspeed";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/setspeed <speed>";
	}

	@Override
	public void processCommand(ICommandSender entity, String[] args) {
		if (entity instanceof EntityPlayer) {
			try {
				EntityPlayer player = (EntityPlayer) entity;
				int speed = Integer.parseInt(args[0]);
				if (CommandDebug.isEnabled()) {
					player.addChatMessage(new ChatComponentText("Current walk speed: " + player.capabilities.getWalkSpeed()));
					player.addChatMessage(new ChatComponentText("Current fly speed: " + player.capabilities.getFlySpeed()));
				}
				
				player.capabilities.allowEdit = true;
				
				speed = speed < 1 ? 1 : speed > 50 ? 50 : speed;
//				ReflectionHelper.setField(PlayerCapabilities.class, "speedOnGround", player.capabilities, (float) speed);
//				ReflectionHelper.setField(PlayerCapabilities.class, "speedInAir", player.capabilities, (float) speed);
				player.capabilities.setFlySpeed(speed);
				player.capabilities.setPlayerWalkSpeed(speed);
				player.addChatMessage(new ChatComponentText("New speed: " + speed));
			} catch (NumberFormatException e) {
				entity.addChatMessage(new ChatComponentText(this.getCommandUsage(entity)));
			}
		}
	}
	
}