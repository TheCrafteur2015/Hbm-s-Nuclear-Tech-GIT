package com.hbm.packet;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketDispatcher {

	//Mark 1 Packet Sending Device
	public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(RefStrings.MODID);

	@SuppressWarnings("deprecation")
	public static final void registerPackets()
	{
		int i = 0;
		
		//Machine type for marker rendering
		PacketDispatcher.wrapper.registerMessage(TEStructurePacket.Handler.class, TEStructurePacket.class, i++, Side.CLIENT);
		//Mining drill rotation for rendering
		PacketDispatcher.wrapper.registerMessage(TEDrillPacket.Handler.class, TEDrillPacket.class, i++, Side.CLIENT);
		//Mining drill torque for sounds
		PacketDispatcher.wrapper.registerMessage(TEDrillSoundPacket.Handler.class, TEDrillSoundPacket.class, i++, Side.CLIENT);
		
		//Missile type for rendering
		PacketDispatcher.wrapper.registerMessage(TEMissilePacket.Handler.class, TEMissilePacket.class, i++, Side.CLIENT);
		//Fluid packet for GUI
		PacketDispatcher.wrapper.registerMessage(TEFluidPacket.Handler.class, TEFluidPacket.class, i++, Side.CLIENT);
		//Sound packet that keeps client and server separated
		PacketDispatcher.wrapper.registerMessage(LoopedSoundPacket.Handler.class, LoopedSoundPacket.class, i++, Side.CLIENT);
		//Signals server to consume items and create template
		PacketDispatcher.wrapper.registerMessage(ItemFolderPacket.Handler.class, ItemFolderPacket.class, i++, Side.SERVER);
		//Electricity gauge for GUI rendering
		PacketDispatcher.wrapper.registerMessage(AuxElectricityPacket.Handler.class, AuxElectricityPacket.class, i++, Side.CLIENT);
		//Siren packet for looped sounds
		PacketDispatcher.wrapper.registerMessage(TESirenPacket.Handler.class, TESirenPacket.class, i++, Side.CLIENT);
		//Signals server to change ItemStacks
		PacketDispatcher.wrapper.registerMessage(ItemDesignatorPacket.Handler.class, ItemDesignatorPacket.class, i++, Side.SERVER);
		//Siren packet for looped sounds
		PacketDispatcher.wrapper.registerMessage(TERadarPacket.Handler.class, TERadarPacket.class, i++, Side.CLIENT);
		//Signals server to perform orbital strike, among other things
		PacketDispatcher.wrapper.registerMessage(SatLaserPacket.Handler.class, SatLaserPacket.class, i++, Side.SERVER);
		//Universal package for sending small info packs back to server
		PacketDispatcher.wrapper.registerMessage(AuxButtonPacket.Handler.class, AuxButtonPacket.class, i++, Side.SERVER);
		//Siren packet for looped sounds
		PacketDispatcher.wrapper.registerMessage(TEVaultPacket.Handler.class, TEVaultPacket.class, i++, Side.CLIENT);
		//Packet to send sat info to players
		PacketDispatcher.wrapper.registerMessage(SatPanelPacket.Handler.class, SatPanelPacket.class, i++, Side.CLIENT);
		//Packet to send block break particles
		PacketDispatcher.wrapper.registerMessage(ParticleBurstPacket.Handler.class, ParticleBurstPacket.class, i++, Side.CLIENT);
		//Packet to send chunk radiation info to individual players
		PacketDispatcher.wrapper.registerMessage(ExtPropPacket.Handler.class, ExtPropPacket.class, i++, Side.CLIENT);
		//Entity sound packet that keeps client and server separated
		PacketDispatcher.wrapper.registerMessage(LoopedEntitySoundPacket.Handler.class, LoopedEntitySoundPacket.class, i++, Side.CLIENT);
		//Packet for force fields
		PacketDispatcher.wrapper.registerMessage(TEFFPacket.Handler.class, TEFFPacket.class, i++, Side.CLIENT);
		//Sends button information for ItemGunBase
		PacketDispatcher.wrapper.registerMessage(GunButtonPacket.Handler.class, GunButtonPacket.class, i++, Side.SERVER);
		//Packet to send block break particles
		PacketDispatcher.wrapper.registerMessage(AuxParticlePacket.Handler.class, AuxParticlePacket.class, i++, Side.CLIENT);
		//Signals server to buy offer from bobmazon
		PacketDispatcher.wrapper.registerMessage(ItemBobmazonPacket.Handler.class, ItemBobmazonPacket.class, i++, Side.SERVER);
		//Packet to send missile multipart information to TEs
		PacketDispatcher.wrapper.registerMessage(TEMissileMultipartPacket.Handler.class, TEMissileMultipartPacket.class, i++, Side.CLIENT);
		//Aux Particle Packet, New Technology: like the APP but with NBT
		PacketDispatcher.wrapper.registerMessage(AuxParticlePacketNT.Handler.class, AuxParticlePacketNT.class, i++, Side.CLIENT);
		//Signals server to do coord based satellite stuff
		PacketDispatcher.wrapper.registerMessage(SatCoordPacket.Handler.class, SatCoordPacket.class, i++, Side.SERVER);
		//Triggers gun animations of the client
		PacketDispatcher.wrapper.registerMessage(GunAnimationPacket.Handler.class, GunAnimationPacket.class, i++, Side.CLIENT);
		//Sends a funi text to display like a music disc announcement
		PacketDispatcher.wrapper.registerMessage(PlayerInformPacket.Handler.class, PlayerInformPacket.class, i++, Side.CLIENT);
		//Universal keybind packet
		PacketDispatcher.wrapper.registerMessage(KeybindPacket.Handler.class, KeybindPacket.class, i++, Side.SERVER);
		//Packet to send NBT data from clients to serverside TEs
		PacketDispatcher.wrapper.registerMessage(NBTControlPacket.Handler.class, NBTControlPacket.class, i++, Side.SERVER);
		//Packet to send for anvil recipes to be crafted
		PacketDispatcher.wrapper.registerMessage(AnvilCraftPacket.Handler.class, AnvilCraftPacket.class, i++, Side.SERVER);
		//Sends a funi text to display like a music disc announcement
		PacketDispatcher.wrapper.registerMessage(TEDoorAnimationPacket.Handler.class, TEDoorAnimationPacket.class, i++, Side.CLIENT);
		//Does ExVNT standard player knockback
		PacketDispatcher.wrapper.registerMessage(ExplosionKnockbackPacket.Handler.class, ExplosionKnockbackPacket.class, i++, Side.CLIENT);
		//just go fuck yourself already
		PacketDispatcher.wrapper.registerMessage(ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket.Handler.class, ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket.class, i++, Side.CLIENT);
		//Packet to send NBT data from clients to the serverside held item
		PacketDispatcher.wrapper.registerMessage(NBTItemControlPacket.Handler.class, NBTItemControlPacket.class, i++, Side.SERVER);
		//sends a button press to the held item, assuming it is an ISyncButtons
		PacketDispatcher.wrapper.registerMessage(SyncButtonsPacket.Handler.class, SyncButtonsPacket.class, i++, Side.SERVER);
		//General syncing for global values
		PacketDispatcher.wrapper.registerMessage(PermaSyncPacket.Handler.class, PermaSyncPacket.class, i++, Side.CLIENT);
		//Syncs biome information for single positions or entire chunks
		PacketDispatcher.wrapper.registerMessage(BiomeSyncPacket.Handler.class, BiomeSyncPacket.class, i++, Side.CLIENT);

		//Tile sync
		PacketDispatcher.wrapper.registerMessage(AuxGaugePacket.Handler.class, AuxGaugePacket.class, i++, Side.CLIENT);	//The horrid one
		PacketDispatcher.wrapper.registerMessage(NBTPacket.Handler.class, NBTPacket.class, i++, Side.CLIENT);			//The convenient but laggy one
		PacketDispatcher.wrapper.registerMessage(BufPacket.Handler.class, BufPacket.class, i++, Side.CLIENT);			//The not-so-convenient but not laggy one
	}
	
}
