package com.hbm.commands;

import java.util.Locale;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class CommandDebug extends CommandBase {
	
	private static boolean debugModeEnabled = false;
	
	@Override
	public String getCommandName() {
		return "ntmdebug";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format(Locale.US, 
                "%1$s/%2$s %3$s- Enable debug mode",
                EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE
        );
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (sender instanceof EntityPlayer) {
			if (args.length != 0) {
				sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
				return;
			}
			CommandDebug.debugModeEnabled = !CommandDebug.debugModeEnabled;
			if (CommandDebug.debugModeEnabled)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Debug mode enabled!"));
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Debug mode disabled!"));
		}
	}
	
	public static boolean isEnabled() {
		return CommandDebug.debugModeEnabled;
	}
	
}