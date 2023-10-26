package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Amat;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Delicious;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_LeadContainer;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Leaded;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Liquid;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_NoContainer;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_NoID;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Plasma;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Viscous;

import net.minecraft.world.World;

public abstract class FluidTrait {
	
	public static HashBiMap<String, Class<? extends FluidTrait>> traitNameMap = HashBiMap.create();
	
	static {
		FluidTrait.traitNameMap.put("combustible", FT_Combustible.class);		// x
		FluidTrait.traitNameMap.put("coolable", FT_Coolable.class);			// x
		FluidTrait.traitNameMap.put("corrosive", FT_Corrosive.class);			// x
		FluidTrait.traitNameMap.put("flammable", FT_Flammable.class);			// x
		FluidTrait.traitNameMap.put("heatable", FT_Heatable.class);			// x
		FluidTrait.traitNameMap.put("poison", FT_Poison.class);				// x
		FluidTrait.traitNameMap.put("toxin", FT_Toxin.class);					// x
		FluidTrait.traitNameMap.put("ventradiation", FT_VentRadiation.class);	// x
		FluidTrait.traitNameMap.put("pwrmoderator", FT_PWRModerator.class);	// x

		FluidTrait.traitNameMap.put("gaseous", FT_Gaseous.class);
		FluidTrait.traitNameMap.put("gaseous_art", FT_Gaseous_ART.class);
		FluidTrait.traitNameMap.put("liquid", FT_Liquid.class);
		FluidTrait.traitNameMap.put("viscous", FT_Viscous.class);
		FluidTrait.traitNameMap.put("plasma", FT_Plasma.class);
		FluidTrait.traitNameMap.put("amat", FT_Amat.class);
		FluidTrait.traitNameMap.put("leadcontainer", FT_LeadContainer.class);
		FluidTrait.traitNameMap.put("delicious", FT_Delicious.class);
		FluidTrait.traitNameMap.put("leaded", FT_Leaded.class);
		FluidTrait.traitNameMap.put("noid", FT_NoID.class);
		FluidTrait.traitNameMap.put("nocontainer", FT_NoContainer.class);
	}

	/** Important information that should always be displayed */
	public void addInfo(List<String> info) { }
	/* General names of simple traits which are displayed when holding shift */
	public void addInfoHidden(List<String> info) { }
	
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount) { }

	public void serializeJSON(JsonWriter writer) throws IOException { }
	public void deserializeJSON(JsonObject obj) { }
}
