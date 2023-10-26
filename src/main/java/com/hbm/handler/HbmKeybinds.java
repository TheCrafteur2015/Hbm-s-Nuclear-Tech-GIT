package com.hbm.handler;

import org.lwjgl.input.Keyboard;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.inventory.gui.GUICalculator;
import com.hbm.main.MainRegistry;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.settings.KeyBinding;

public class HbmKeybinds {

	public static final String category = "hbm.key";

	public static KeyBinding calculatorKey = new KeyBinding(HbmKeybinds.category + ".calculator", Keyboard.KEY_N, HbmKeybinds.category);
	public static KeyBinding jetpackKey = new KeyBinding(HbmKeybinds.category + ".toggleBack", Keyboard.KEY_C, HbmKeybinds.category);
	public static KeyBinding hudKey = new KeyBinding(HbmKeybinds.category + ".toggleHUD", Keyboard.KEY_V, HbmKeybinds.category);
	public static KeyBinding reloadKey = new KeyBinding(HbmKeybinds.category + ".reload", Keyboard.KEY_R, HbmKeybinds.category);
	public static KeyBinding dashKey = new KeyBinding(HbmKeybinds.category + ".dash", Keyboard.KEY_LSHIFT, HbmKeybinds.category);
	public static KeyBinding trainKey = new KeyBinding(HbmKeybinds.category + ".trainInv", Keyboard.KEY_R, HbmKeybinds.category);

	public static KeyBinding craneUpKey = new KeyBinding(HbmKeybinds.category + ".craneMoveUp", Keyboard.KEY_UP, HbmKeybinds.category);
	public static KeyBinding craneDownKey = new KeyBinding(HbmKeybinds.category + ".craneMoveDown", Keyboard.KEY_DOWN, HbmKeybinds.category);
	public static KeyBinding craneLeftKey = new KeyBinding(HbmKeybinds.category + ".craneMoveLeft", Keyboard.KEY_LEFT, HbmKeybinds.category);
	public static KeyBinding craneRightKey = new KeyBinding(HbmKeybinds.category + ".craneMoveRight", Keyboard.KEY_RIGHT, HbmKeybinds.category);
	public static KeyBinding craneLoadKey = new KeyBinding(HbmKeybinds.category + ".craneLoad", Keyboard.KEY_RETURN, HbmKeybinds.category);

	public static void register() {
		ClientRegistry.registerKeyBinding(HbmKeybinds.calculatorKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.jetpackKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.hudKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.reloadKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.dashKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.trainKey);

		ClientRegistry.registerKeyBinding(HbmKeybinds.craneUpKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.craneDownKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.craneLeftKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.craneRightKey);
		ClientRegistry.registerKeyBinding(HbmKeybinds.craneLoadKey);
	}
	
	@SubscribeEvent
	public void keyEvent(KeyInputEvent event) {
		if (HbmKeybinds.calculatorKey.getIsKeyPressed()) { // handle the calculator client-side only
			FMLCommonHandler.instance().showGuiScreen(new GUICalculator());
		}
		
		HbmPlayerProps props = HbmPlayerProps.getData(MainRegistry.proxy.me());
		
		for(EnumKeybind key : EnumKeybind.values()) {
			boolean last = props.getKeyPressed(key);
			boolean current = MainRegistry.proxy.getIsKeyPressed(key);
			
			if(last != current) {
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(key, current));
				props.setKeyPressed(key, current);
			}
		}
	}

	public static enum EnumKeybind {
		JETPACK,
		TOGGLE_JETPACK,
		TOGGLE_HEAD,
		RELOAD,
		DASH,
		TRAIN,
		CRANE_UP,
		CRANE_DOWN,
		CRANE_LEFT,
		CRANE_RIGHT,
		CRANE_LOAD
	}
}
