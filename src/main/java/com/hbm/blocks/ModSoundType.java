package com.hbm.blocks;

import java.util.Random;

import net.minecraft.block.Block;

public class ModSoundType extends Block.SoundType {
	protected final String placeSound;
	protected final String breakSound;
	protected final String stepSound;

	protected ModSoundType(String placeSound, String breakSound, String stepSound, float volume, float pitch) {
		super("", volume, pitch);
		this.placeSound = placeSound;
		this.breakSound = breakSound;
		this.stepSound = stepSound;
	}

	public ModEnvelopedSoundType enveloped() {
		return new ModEnvelopedSoundType(this.placeSound, this.breakSound, this.stepSound, this.volume, this.frequency);
	}

	public ModEnvelopedSoundType enveloped(Random random) {
		return new ModEnvelopedSoundType(this.placeSound, this.breakSound, this.stepSound, this.volume, this.frequency, random);
	}

	@Override
	public String func_150496_b() {
		return this.placeSound;
	}

	@Override
	public String getBreakSound() {
		return this.breakSound;
	}

	@Override
	public String getStepResourcePath() {
		return this.stepSound;
	}

	// creates a sound type with vanilla-like sound strings name-spaced to the mod
	public static ModSoundType mod(String soundName, float volume, float pitch) {
		return new ModSoundType(ModSoundType.modDig(soundName), ModSoundType.modDig(soundName), ModSoundType.modStep(soundName), volume, pitch);
	}

	// these permutations allow creating a sound type with one of the three sounds being custom
	// and the other ones defaulting to vanilla-like sound strings name-spaced to the mod

	public static ModSoundType customPlace(String soundName, String placeSound, float volume, float pitch) {
		return new ModSoundType(placeSound, ModSoundType.modDig(soundName), ModSoundType.modStep(soundName), volume, pitch);
	}

	public static ModSoundType customBreak(String soundName, String breakSound, float volume, float pitch) {
		return new ModSoundType(ModSoundType.modDig(soundName), breakSound, ModSoundType.modStep(soundName), volume, pitch);
	}

	public static ModSoundType customStep(String soundName, String stepSound, float volume, float pitch) {
		return new ModSoundType(ModSoundType.modDig(soundName), ModSoundType.modDig(soundName), stepSound, volume, pitch);
	}

	public static ModSoundType customDig(String soundName, String digSound, float volume, float pitch) {
		return new ModSoundType(digSound, digSound, ModSoundType.modStep(soundName), volume, pitch);
	}

	// these permutations copy sounds from an existing sound type and modify one of the sounds,
	// but with a manual path for the custom sound

	public static ModSoundType customPlace(Block.SoundType from, String placeSound, float volume, float pitch) {
		return new ModSoundType(placeSound, from.getBreakSound(), from.getStepResourcePath(), volume, pitch);
	}

	public static ModSoundType customBreak(Block.SoundType from, String breakSound, float volume, float pitch) {
		return new ModSoundType(from.func_150496_b(), breakSound, from.getStepResourcePath(), volume, pitch);
	}

	public static ModSoundType customStep(Block.SoundType from, String stepSound, float volume, float pitch) {
		return new ModSoundType(from.func_150496_b(), from.getBreakSound(), stepSound, volume, pitch);
	}

	public static ModSoundType customDig(Block.SoundType from, String dig, float volume, float pitch) {
		return new ModSoundType(dig, dig, from.getStepResourcePath(), volume, pitch);
	}

	// customizes all sounds
	public static ModSoundType placeBreakStep(String placeSound, String breakSound, String stepSound, float volume, float pitch) {
		return new ModSoundType(placeSound, breakSound, stepSound, volume, pitch);
	}

	private static String modDig(String soundName) {
		return "hbm:dig." + soundName;
	}

	private static String modStep(String soundName) {
		return "hbm:step." + soundName;
	}

	public static class ModEnvelopedSoundType extends ModSoundType {
		private final Random random;

		ModEnvelopedSoundType(String placeSound, String breakSound, String stepSound, float volume, float pitch, Random random) {
			super(placeSound, breakSound, stepSound, volume, pitch);
			this.random = random;
		}

		ModEnvelopedSoundType(String placeSound, String breakSound, String stepSound, float volume, float pitch) {
			this(placeSound, breakSound, stepSound, volume, pitch, new Random());
		}

		// a bit of a hack, but most of the time, playSound is called with the sound path queried first, and then volume and pitch
		private SubType probableSubType = SubType.PLACE;

		@Override
		public String func_150496_b() {
			this.probableSubType = SubType.PLACE;
			return super.func_150496_b();
		}

		@Override
		public String getBreakSound() {
			this.probableSubType = SubType.BREAK;
			return super.getBreakSound();
		}

		@Override
		public String getStepResourcePath() {
			this.probableSubType = SubType.STEP;
			return super.getStepResourcePath();
		}

		private Envelope volumeEnvelope = null;
		private Envelope pitchEnvelope = null;

		public ModEnvelopedSoundType volumeFunction(Envelope volumeEnvelope) {
			this.volumeEnvelope = volumeEnvelope;
			return this;
		}

		public ModEnvelopedSoundType pitchFunction(Envelope pitchEnvelope) {
			this.pitchEnvelope = pitchEnvelope;
			return this;
		}

		@Override
		public float getVolume() {
			if (this.volumeEnvelope == null)
				return super.getVolume();
			else
				return this.volumeEnvelope.compute(super.getVolume(), this.random, this.probableSubType);
		}

		@Override
		public float getPitch() {
			if (this.pitchEnvelope == null)
				return super.getPitch();
			else
				return this.pitchEnvelope.compute(super.getPitch(), this.random, this.probableSubType);
		}

		@FunctionalInterface
		public interface Envelope {
			float compute(float in, Random rand, SubType type);
		}
	}

	public enum SubType {
		PLACE, BREAK, STEP
	}
}
