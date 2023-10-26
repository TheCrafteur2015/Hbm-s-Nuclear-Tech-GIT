package com.hbm.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
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
public class CommandClearChat extends CommandBase {
	
	@Override
	public String getCommandName() {
		return "clearchat";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return EnumChatFormatting.GREEN + "/clearchat";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (sender instanceof EntityPlayer) {
			GuiNewChat chat = new GuiNewChat(Minecraft.getMinecraft());
			chat.clearChatMessages();
			chat.refreshChat();
			if (CommandDebug.isEnabled())
				chat.printChatMessage(new ChatComponentText("Clearing..."));
		}
	}
	
}