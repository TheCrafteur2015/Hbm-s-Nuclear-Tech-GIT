package com.hbm.render.item;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.main.ResourceManager;
import com.hbm.render.tileentity.RenderBobble;
import com.hbm.render.tileentity.RenderDemonLamp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Deprecated // implement IItemRendererProvider for TESRs instead!
public class ItemRenderLibrary {

	public static HashMap<Item, ItemRenderBase> renderers = new HashMap<>();
	
	public static void init() {
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.obj_tester), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(2, 2, 2);
			}
			@Override
			public void renderCommon() {
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.soyuz_module_dome_tex); ResourceManager.soyuz_module.renderPart("Dome");
		        ItemRenderLibrary.bindTexture(ResourceManager.soyuz_module_lander_tex); ResourceManager.soyuz_module.renderPart("Capsule");
		        ItemRenderLibrary.bindTexture(ResourceManager.soyuz_module_propulsion_tex); ResourceManager.soyuz_module.renderPart("Propulsion");
		        ItemRenderLibrary.bindTexture(ResourceManager.soyuz_module_solar_tex); ResourceManager.soyuz_module.renderPart("Solar");
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_cyclotron), new ItemRenderBase() {
			@Override
			public void renderInventory() {
					GL11.glScaled(2.25, 2.25, 2.25);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.cyclotron_tex); ResourceManager.cyclotron.renderPart("Body");
				ItemRenderLibrary.bindTexture(ResourceManager.cyclotron_ashes);  ResourceManager.cyclotron.renderPart("B1");
				ItemRenderLibrary.bindTexture(ResourceManager.cyclotron_book); ResourceManager.cyclotron.renderPart("B2");
				ItemRenderLibrary.bindTexture(ResourceManager.cyclotron_gavel); ResourceManager.cyclotron.renderPart("B3");
				ItemRenderLibrary.bindTexture(ResourceManager.cyclotron_coin); ResourceManager.cyclotron.renderPart("B4");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.iter), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.iter_glass); ResourceManager.iter.renderPart("Windows");
		        ItemRenderLibrary.bindTexture(ResourceManager.iter_motor); ResourceManager.iter.renderPart("Motors");
		        ItemRenderLibrary.bindTexture(ResourceManager.iter_rails); ResourceManager.iter.renderPart("Rails");
		        ItemRenderLibrary.bindTexture(ResourceManager.iter_toroidal); ResourceManager.iter.renderPart("Toroidal");
		        ItemRenderLibrary.bindTexture(ResourceManager.iter_torus); ResourceManager.iter.renderPart("Torus");
		        ItemRenderLibrary.bindTexture(ResourceManager.iter_solenoid); ResourceManager.iter.renderPart("Solenoid");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_press), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.press_body_tex); ResourceManager.press_body.renderAll();
				GL11.glTranslated(0, 0.5, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.press_head_tex); ResourceManager.press_head.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_epress), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.epress_body_tex); ResourceManager.epress_body.renderAll();
				GL11.glTranslated(0, 1.5, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.epress_head_tex); ResourceManager.epress_head.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_crystallizer), new ItemRenderBase() {
			@Override
			public void renderNonInv() {
				GL11.glScaled(0.5, 0.5, 0.5);
			}
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(1.75, 1.75, 1.75);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.crystallizer_tex); ResourceManager.crystallizer.renderPart("Body");
		        ItemRenderLibrary.bindTexture(ResourceManager.crystallizer_window_tex); ResourceManager.crystallizer.renderPart("Windows");
		        ItemRenderLibrary.bindTexture(ResourceManager.crystallizer_spinner_tex); ResourceManager.crystallizer.renderPart("Spinner");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_reactor_breeding), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.breeder_tex); ResourceManager.breeder.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_large_turbine), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.turbine_tex); ResourceManager.turbine.renderPart("Body");
		        ItemRenderLibrary.bindTexture(ResourceManager.universal_bright); ResourceManager.turbine.renderPart("Blades");
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_selenium), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.selenium_body_tex); ResourceManager.selenium_body.renderAll();
		        GL11.glTranslated(0.0D, 1.0D, 0.0D);
		        ItemRenderLibrary.bindTexture(ResourceManager.selenium_rotor_tex); ResourceManager.selenium_rotor.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.selenium_piston_tex);
		        for(int i = 0; i < 7; i++) {
		            ResourceManager.selenium_piston.renderAll(); GL11.glRotatef(360F/7F, 0, 0, 1);
		        }
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.reactor_research), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.reactor_small_base_tex); ResourceManager.reactor_small_base.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.reactor_small_rods_tex); ResourceManager.reactor_small_rods.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_industrial_generator), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4, 4, 4);
				GL11.glRotated(90, 0, 1, 0);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(0, 0, -0.5);
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glDisable(GL11.GL_CULL_FACE);
				ItemRenderLibrary.bindTexture(ResourceManager.igen_tex);
				ResourceManager.igen.renderPart("Body");
				ResourceManager.igen.renderPart("Rotor");
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_radgen), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslated(0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.radgen_tex);
				ResourceManager.radgen.renderPart("Base");
				ResourceManager.radgen.renderPart("Rotor");
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor3f(0F, 1F, 0F);
				ResourceManager.radgen.renderPart("Light");
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_fensu), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.fensu_tex); ResourceManager.fensu.renderPart("Base"); ResourceManager.fensu.renderPart("Disc");
		        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		        GL11.glDisable(GL11.GL_LIGHTING);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		        ResourceManager.fensu.renderPart("Lights");
		        GL11.glEnable(GL11.GL_LIGHTING);
		        GL11.glPopAttrib();
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_assembler), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.assembler_body_tex); ResourceManager.assembler_body.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.assembler_slider_tex); ResourceManager.assembler_slider.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.assembler_arm_tex); ResourceManager.assembler_arm.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.assembler_cog_tex);
		        GL11.glPushMatrix();
				GL11.glTranslated(-0.6, 0.75, 1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
		        GL11.glPushMatrix();
				GL11.glTranslated(0.6, 0.75, 1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
		        GL11.glPushMatrix();
				GL11.glTranslated(-0.6, 0.75, -1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
		        GL11.glPushMatrix();
				GL11.glTranslated(0.6, 0.75, -1.0625);
				ResourceManager.assembler_cog.renderAll();
		        GL11.glPopMatrix();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_chemplant), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			@Override
			public void renderCommon() {
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.chemplant_body_tex); ResourceManager.chemplant_body.renderAll();
		        GL11.glShadeModel(GL11.GL_FLAT);
		        ItemRenderLibrary.bindTexture(ResourceManager.chemplant_piston_tex); ResourceManager.chemplant_piston.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.chemplant_spinner_tex);
				GL11.glTranslated(-0.625, 0, 0.625);
		        ResourceManager.chemplant_spinner.renderAll();
				GL11.glTranslated(1.25, 0, 0);
		        ResourceManager.chemplant_spinner.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_well), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.derrick_tex); ResourceManager.derrick.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_pumpjack), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
		        GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(0, 0, 3);
				ItemRenderLibrary.bindTexture(ResourceManager.pumpjack_tex); ResourceManager.pumpjack.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_flare), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.oilflare_tex); ResourceManager.oilflare.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_refinery), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.refinery_tex);  ResourceManager.refinery.renderAll();
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_drill), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(180, 0, 1, 0);
		        GL11.glDisable(GL11.GL_CULL_FACE);
				ItemRenderLibrary.bindTexture(ResourceManager.drill_body_tex); ResourceManager.drill_body.renderAll();
				ItemRenderLibrary.bindTexture(ResourceManager.drill_bolt_tex); ResourceManager.drill_bolt.renderAll();
		        GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_mining_laser), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -0.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				ItemRenderLibrary.bindTexture(ResourceManager.mining_laser_base_tex); ResourceManager.mining_laser.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.mining_laser_pivot_tex); ResourceManager.mining_laser.renderPart("Pivot");
				GL11.glTranslated(0, -1, 0.75);
				GL11.glRotated(90, 1, 0, 0);
				ItemRenderLibrary.bindTexture(ResourceManager.mining_laser_laser_tex); ResourceManager.mining_laser.renderPart("Laser");
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_turbofan), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(2, 2, 2);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turbofan_tex);
				ResourceManager.turbofan.renderPart("Body");
				ResourceManager.turbofan.renderPart("Blades");
				ItemRenderLibrary.bindTexture(ResourceManager.turbofan_back_tex);
				ResourceManager.turbofan.renderPart("Afterburner");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.plasma_heater), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(0, 0, 14);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.iter_microwave); ResourceManager.iter.renderPart("Microwave");
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.tesla), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(6, 6, 6);
			}
			@Override
			public void renderCommon() {
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.tesla_tex); ResourceManager.tesla.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.boxcar), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
	        	ItemRenderLibrary.bindTexture(ResourceManager.boxcar_tex); ResourceManager.boxcar.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.boat), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glRotated(-90, 0, 1, 0);
				GL11.glTranslated(0, 1, 0);
				GL11.glScaled(1.75, 1.75, 1.75);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslatef(0, 0, -3);
	        	ItemRenderLibrary.bindTexture(ResourceManager.duchessgambit_tex); ResourceManager.duchessgambit.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.bomber), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, 1, 0);
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(-90, 0, 1, 0);
				GL11.glScaled(2, 2, 2);
				GL11.glTranslatef(0, 0, -0.25F);
				ItemRenderLibrary.bindTexture(ResourceManager.dornier_0_tex); ResourceManager.dornier.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_gadget), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(0.25, 0, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.bomb_gadget_tex);
		        ResourceManager.bomb_gadget.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_boy), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-1, 0, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.bomb_boy_tex);
		        ResourceManager.bomb_boy.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_prototype), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.bomb_prototype_tex);
		        ResourceManager.bomb_prototype.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_fleija), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
				GL11.glRotated(90, 0, 1, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.bomb_fleija_tex);
		        ResourceManager.bomb_fleija.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_solinium), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(0.5, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.bomb_solinium_tex);
		        ResourceManager.bomb_solinium.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_n2), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.n2_tex);
		        ResourceManager.n2.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_fstbmb), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(1, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.fstbmb_tex);
		        ResourceManager.fstbmb.renderPart("Body");
		        ResourceManager.fstbmb.renderPart("Balefire");
		        GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.nuke_custom), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-1, 0, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.bomb_custom_tex);
		        ResourceManager.bomb_boy.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.crashed_balefire), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, 3, 0);
				GL11.glScaled(2, 2, 2);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.dud_tex);
		        ResourceManager.dud.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.bomb_multi), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(0.75, 0, 0);
				GL11.glScaled(3, 3, 3);
				GL11.glTranslated(0, 0.5, 0);
		        GL11.glRotatef(180, 1F, 0F, 0F);
		        GL11.glRotatef(90, 0F, 1F, 0F);
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.bomb_multi_tex);
		        ResourceManager.bomb_multi.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.mine_ap), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(8, 8, 8);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(6, 6, 6);
		        GL11.glRotatef(22.5F, 0F, 1F, 0F);
	            GL11.glDisable(GL11.GL_CULL_FACE);
				ItemRenderLibrary.bindTexture(ResourceManager.mine_ap_tex);
	        	ResourceManager.mine_ap.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.mine_he), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(6, 6, 6);
			}
			@Override
			public void renderNonInv() {
				GL11.glTranslated(0.25, 0.625, 0);
				GL11.glRotated(45, 0, 1, 0);
				GL11.glRotated(-15, 0, 0, 1);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(4, 4, 4);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.mine_marelet_tex); ResourceManager.mine_marelet.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.mine_shrap), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(6, 6, 6);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(4, 4, 4);
				ItemRenderLibrary.bindTexture(ResourceManager.mine_shrap_tex);
	        	ResourceManager.mine_he.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.mine_fat), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(7, 7, 7);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(0.25, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
	            GL11.glDisable(GL11.GL_CULL_FACE);
				ItemRenderLibrary.bindTexture(ResourceManager.mine_fat_tex);
	        	ResourceManager.mine_fat.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_forcefield), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(6, 6, 6);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.forcefield_base_tex); ResourceManager.radar_body.renderAll();
		        GL11.glTranslated(0, 1D, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.forcefield_top_tex); ResourceManager.forcefield_top.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_missile_assembly), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(10, 10, 10);
			}
			@Override
			public void renderCommon() {
	            GL11.glDisable(GL11.GL_CULL_FACE);
		        ItemRenderLibrary.bindTexture(ResourceManager.missile_assembly_tex); ResourceManager.missile_assembly.renderAll();
	            GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.launch_pad), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.missile_pad_tex); ResourceManager.missile_pad.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.compact_launcher), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				ItemRenderLibrary.bindTexture(ResourceManager.compact_launcher_tex); ResourceManager.compact_launcher.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.launch_table), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				ItemRenderLibrary.bindTexture(ResourceManager.launch_table_base_tex); ResourceManager.launch_table_base.renderAll();
				ItemRenderLibrary.bindTexture(ResourceManager.launch_table_small_pad_tex); ResourceManager.launch_table_small_pad.renderAll();
				GL11.glTranslatef(0F, 0F, 2.5F);
				for(int i = 0; i < 8; i++) {
					GL11.glTranslatef(0F, 1F, 0.F);
					if(i < 6) {
						ItemRenderLibrary.bindTexture(ResourceManager.launch_table_small_scaffold_base_tex); ResourceManager.launch_table_small_scaffold_base.renderAll();
					}
					if(i == 6) {
						ItemRenderLibrary.bindTexture(ResourceManager.launch_table_small_scaffold_connector_tex); ResourceManager.launch_table_small_scaffold_connector.renderAll();
					}
					if(i > 6) {
						ItemRenderLibrary.bindTexture(ResourceManager.launch_table_small_scaffold_base_tex); ResourceManager.launch_table_small_scaffold_empty.renderAll();
					}
				}
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.soyuz_capsule), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
	            GL11.glShadeModel(GL11.GL_SMOOTH);
	        	ItemRenderLibrary.bindTexture(ResourceManager.soyuz_lander_tex); ResourceManager.soyuz_lander.renderPart("Capsule");
	            GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_radar), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glDisable(GL11.GL_CULL_FACE);
				ItemRenderLibrary.bindTexture(ResourceManager.radar_base_tex); ResourceManager.radar.renderPart("Base");
				GL11.glTranslated(-0.125, 0, 0);
				ItemRenderLibrary.bindTexture(ResourceManager.radar_dish_tex); ResourceManager.radar.renderPart("Dish");
				GL11.glEnable(GL11.GL_CULL_FACE);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(6, 6, 6);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, -1, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.uf6_tex); ResourceManager.tank.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(6, 6, 6);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, -1, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.puf6_tex); ResourceManager.tank.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.sat_dock), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, -1, 0);
		        ItemRenderLibrary.bindTexture(ResourceManager.satdock_tex); ResourceManager.satDock.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.vault_door), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.vault_cog_tex); ResourceManager.vault_cog.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.vault_label_101_tex); ResourceManager.vault_label.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.blast_door), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
		        ItemRenderLibrary.bindTexture(ResourceManager.blast_door_base_tex); ResourceManager.blast_door_base.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.blast_door_tooth_tex); ResourceManager.blast_door_tooth.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.blast_door_slider_tex); ResourceManager.blast_door_slider.renderAll();
		        ItemRenderLibrary.bindTexture(ResourceManager.blast_door_block_tex); ResourceManager.blast_door_block.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_microwave), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 4);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-2, -2, 1);
				GL11.glScaled(3, 3, 3);
				ItemRenderLibrary.bindTexture(ResourceManager.microwave_tex);
		        ResourceManager.microwave.renderPart("mainbody_Cube.001");
		        ResourceManager.microwave.renderPart("window_Cube.002");
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_solar_boiler), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
	            GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.solar_tex); ResourceManager.solar_boiler.renderPart("Base");
	            GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.solar_mirror), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(8, 8, 8);
			}
			@Override
			public void renderCommon() {
				ItemRenderLibrary.bindTexture(ResourceManager.solar_mirror_tex);
				ResourceManager.solar_mirror.renderPart("Base");
				GL11.glTranslated(0, 1, 0);
				GL11.glRotated(45, 0, 0, -1);
				GL11.glTranslated(0, -1, 0);
				ResourceManager.solar_mirror.renderPart("Mirror");
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_chekhov), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_chekhov_tex); ResourceManager.turret_chekhov.renderPart("Body");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_chekhov_barrels_tex); ResourceManager.turret_chekhov.renderPart("Barrels");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_friendly), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_friendly_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_friendly_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_chekhov_tex); ResourceManager.turret_chekhov.renderPart("Body");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_chekhov_barrels_tex); ResourceManager.turret_chekhov.renderPart("Barrels");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_jeremy), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_jeremy_tex); ResourceManager.turret_jeremy.renderPart("Gun");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_tauon), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_tauon_tex); ResourceManager.turret_tauon.renderPart("Cannon");
				ResourceManager.turret_tauon.renderPart("Rotor");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_richard), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_richard_tex); ResourceManager.turret_richard.renderPart("Launcher");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_howard), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_ciws_tex); ResourceManager.turret_howard.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_howard_tex); ResourceManager.turret_howard.renderPart("Body");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_howard_barrels_tex); ResourceManager.turret_howard.renderPart("BarrelsTop");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_howard_barrels_tex); ResourceManager.turret_howard.renderPart("BarrelsBottom");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_howard_damaged), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(-0.75, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_rusted); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_ciws_rusted); ResourceManager.turret_howard.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_howard_rusted); ResourceManager.turret_howard_damaged.renderPart("Body");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_howard_barrels_rusted); ResourceManager.turret_howard_damaged.renderPart("BarrelsTop");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_howard_barrels_rusted); ResourceManager.turret_howard_damaged.renderPart("BarrelsBottom");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_silex), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.silex_tex); ResourceManager.silex.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_fel), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2, 2, 2);
			}
			@Override
			public void renderCommon() {
				GL11.glTranslated(1, 0, 0);
				GL11.glRotated(90, 0, -1, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.fel_tex); ResourceManager.fel.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.rbmk_console), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.rbmk_console_tex);
				ResourceManager.rbmk_console.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.rbmk_crane_console), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.rbmk_crane_console_tex);
				ResourceManager.rbmk_crane_console.renderPart("Console_Coonsole");
				ResourceManager.rbmk_crane_console.renderPart("JoyStick");
				ResourceManager.rbmk_crane_console.renderPart("Meter1");
				ResourceManager.rbmk_crane_console.renderPart("Meter2");
				ItemRenderLibrary.bindTexture(ResourceManager.ks23_tex); ResourceManager.rbmk_crane_console.renderPart("Shotgun");
				ItemRenderLibrary.bindTexture(ResourceManager.mini_nuke_tex); ResourceManager.rbmk_crane_console.renderPart("MiniNuke");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.lamp_demon), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(8, 8, 8);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(RenderDemonLamp.tex);
				RenderDemonLamp.demon_lamp.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_storage_drum), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
				ItemRenderLibrary.bindTexture(ResourceManager.waste_drum_tex);
				ResourceManager.waste_drum.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_chungus), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0.5, 0, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotated(90, 0, 1, 0);
				ItemRenderLibrary.bindTexture(ResourceManager.chungus_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.chungus.renderPart("Body");
				ResourceManager.chungus.renderPart("Lever");
				ResourceManager.chungus.renderPart("Blades");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_maxwell), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(-1, -3, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_ciws_tex); ResourceManager.turret_howard.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_maxwell_tex); ResourceManager.turret_maxwell.renderPart("Microwave");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_fritz), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(4, 4, 4);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_base_tex); ResourceManager.turret_chekhov.renderPart("Base");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_carriage_tex); ResourceManager.turret_chekhov.renderPart("Carriage");
				ItemRenderLibrary.bindTexture(ResourceManager.turret_fritz_tex); ResourceManager.turret_fritz.renderPart("Gun");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_bat9000), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2, 2, 2);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.bat9000_tex); ResourceManager.bat9000.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_orbus), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2, 2, 2);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.orbus_tex); ResourceManager.orbus.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.watz), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2, 2, 2);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.watz_tex); ResourceManager.watz.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_fraction_tower), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
				ItemRenderLibrary.bindTexture(ResourceManager.fraction_tower_tex); ResourceManager.fraction_tower.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.fraction_spacer), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
				ItemRenderLibrary.bindTexture(ResourceManager.fraction_spacer_tex); ResourceManager.fraction_spacer.renderAll();
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_tower_small), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.tower_small_tex); ResourceManager.tower_small.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_tower_large), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(4 * 0.95, 4 * 0.95, 4 * 0.95);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.tower_large_tex); ResourceManager.tower_large.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_fracking_tower), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.fracking_tower_tex); ResourceManager.fracking_tower.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.bobblehead), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(10, 10, 10);
			}
			@Override
			public void renderCommonWithStack(ItemStack stack) {
				GL11.glScaled(0.5, 0.5, 0.5);
				RenderBobble.instance.renderBobble(BobbleType.values()[stack.getItemDamage()]);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_deuterium_tower), new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(3, 3, 3);
			}

			@Override
			public void renderCommon() {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.deuterium_tower_tex); ResourceManager.deuterium_tower.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.reactor_zirnox), new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(2.8, 2.8, 2.8);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.zirnox_tex); ResourceManager.zirnox.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_catalytic_cracker), new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(1.8, 1.8, 1.8);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.cracking_tower_tex); ResourceManager.cracking_tower.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_liquefactor), new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.liquefactor_tex); ResourceManager.liquefactor.renderPart("Main");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_solidifier), new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.solidifier_tex); ResourceManager.solidifier.renderPart("Main");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_radiolysis), new ItemRenderBase( ) {
		@Override
		public void renderInventory() {
			GL11.glTranslated(0, -2.5, 0);
			GL11.glScaled(3, 3, 3);
		}
		@Override
		public void renderCommon() {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ItemRenderLibrary.bindTexture(ResourceManager.radiolysis_tex); ResourceManager.radiolysis.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_chemfac), new ItemRenderBase( ) {
		@Override
		public void renderInventory() {
			GL11.glScaled(2.5, 2.5, 2.5);
		}
		@Override
		public void renderCommon() {
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ItemRenderLibrary.bindTexture(ResourceManager.chemfac_tex); ResourceManager.chemfac.renderPart("Main");
			GL11.glShadeModel(GL11.GL_FLAT);
		}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.red_pylon_large), new ItemRenderBase( ) {
		@Override
		public void renderInventory() {
			GL11.glTranslated(0, -5, 0);
			GL11.glScaled(2.25, 2.25, 2.25);
		}
		@Override
		public void renderCommon() {
			GL11.glScaled(0.5, 0.5, 0.5);
			ItemRenderLibrary.bindTexture(ResourceManager.pylon_large_tex); ResourceManager.pylon_large.renderAll();
		}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.substation), new ItemRenderBase( ) {
		@Override
		public void renderInventory() {
			GL11.glTranslated(0, -2.5, 0);
			GL11.glScaled(4.5, 4.5, 4.5);
		}
		@Override
		public void renderCommon() {
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ItemRenderLibrary.bindTexture(ResourceManager.substation_tex); ResourceManager.substation.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.charger), new ItemRenderBase( ) {
		@Override
		public void renderInventory() {
			GL11.glTranslated(0, -7, 0);
			GL11.glScaled(10, 10, 10);
		}
		@Override
		public void renderCommon() {
			GL11.glScaled(2, 2, 2);
			GL11.glTranslated(0.5, 0, 0);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ItemRenderLibrary.bindTexture(ResourceManager.charger_tex);
			ResourceManager.charger.renderPart("Base");
			ResourceManager.charger.renderPart("Slide");
			GL11.glShadeModel(GL11.GL_FLAT);
		}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.machine_assemfac), new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			@Override
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.assemfac_tex); ResourceManager.assemfac.renderPart("Factory");
				for(int i = 1; i < 7; i++) {
					ResourceManager.assemfac.renderPart("Pivot" + i);
					ResourceManager.assemfac.renderPart("Arm" + i);
					ResourceManager.assemfac.renderPart("Piston" + i);
					ResourceManager.assemfac.renderPart("Striker" + i);
				}
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.furnace_iron), new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(5, 5, 5);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
				ItemRenderLibrary.bindTexture(ResourceManager.furnace_iron_tex);
				ResourceManager.furnace_iron.renderPart("Main");
				ResourceManager.furnace_iron.renderPart("Off");
			}});
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.turret_arty), new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(-3, -4, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			@Override
			public void renderCommon() {
				GL11.glRotated(-90, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(ResourceManager.turret_arty_tex);
				ResourceManager.turret_arty.renderPart("Base");
				ResourceManager.turret_arty.renderPart("Carriage");
				GL11.glTranslated(0, 3, 0);
				GL11.glRotated(45, 1, 0, 0);
				GL11.glTranslated(0, -3, 0);
				ResourceManager.turret_arty.renderPart("Cannon");
				ResourceManager.turret_arty.renderPart("Barrel");
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		ItemRenderLibrary.renderers.put(ModItems.gear_large, new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -7, 0);
				GL11.glScaled(6, 6, 6);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glRotated(30, 1, 0, 0);
				GL11.glTranslated(0, 1.375, 0);
				GL11.glRotated(System.currentTimeMillis() % 3600 * 0.1F, 0, 0, 1);
				GL11.glTranslated(0, -1.375, 0);
			}
			@Override
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 0, -0.875);
				
				if(item.getItemDamage() == 0)
					ItemRenderLibrary.bindTexture(ResourceManager.stirling_tex);
				else
					ItemRenderLibrary.bindTexture(ResourceManager.stirling_steel_tex);
				ResourceManager.stirling.renderPart("Cog");
			}});
		
		ItemRenderLibrary.renderers.put(ModItems.sawblade, new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -7, 0);
				GL11.glScaled(6, 6, 6);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glRotated(30, 1, 0, 0);
				GL11.glTranslated(0, 1.375, 0);
				GL11.glRotated(System.currentTimeMillis() % 3600 * 0.2F, 0, 0, 1);
				GL11.glTranslated(0, -1.375, 0);
			}
			@Override
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 0, -0.875);
				ItemRenderLibrary.bindTexture(ResourceManager.sawmill_tex);
				ResourceManager.sawmill.renderPart("Blade");
			}});
		
		ItemRenderLibrary.renderers.put(ModItems.ammo_himars, new ItemRenderBase( ) {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				double scale = 2.75D;
				GL11.glScaled(scale, scale, scale);
				GL11.glRotated(System.currentTimeMillis() % 3600 / 10D, 0, 1, 0);
			}
			@Override
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 1.5, 0);
				GL11.glRotated(-45, 0, 1, 0);
				GL11.glRotated(90, 1, 0, 0);
				HIMARSRocket type = ItemAmmoHIMARS.itemTypes[item.getItemDamage()];
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ItemRenderLibrary.bindTexture(type.texture); 
				if(type.modelType == 0) {
					GL11.glTranslated(0.75, 0, 0);
					ResourceManager.turret_himars.renderPart("RocketStandard");
					GL11.glTranslated(-1.5, 0, 0);
					GL11.glTranslated(0, -3.375D, 0);
					ResourceManager.turret_himars.renderPart("TubeStandard");
				} else {
					GL11.glTranslated(0.75, 0, 0);
					ResourceManager.turret_himars.renderPart("RocketSingle");
					GL11.glTranslated(-1.5, 0, 0);
					GL11.glTranslated(0, -3.375D, 0);
					ResourceManager.turret_himars.renderPart("TubeSingle");
				}
				GL11.glShadeModel(GL11.GL_FLAT);
			}});
		
		//hi there! it seems you are trying to register a new item renderer, most likely for a tile entity.
		//please refer to the comment at the start of the file on how to do this without adding to this gigantic pile of feces.
	
		// My renders
		
		ItemRenderLibrary.renderers.put(Item.getItemFromBlock(ModBlocks.reactor_monitor_1), new ItemRenderBase() {
			@Override
			public void renderNonInv() {
				GL11.glScaled(0.5, 0.5, 0.5);
			}
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(1.75, 1.75, 1.75);
			}
			@Override
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
		        ItemRenderLibrary.bindTexture(ResourceManager.monitor_1_keyboard_tex);
		        ItemRenderLibrary.bindTexture(ResourceManager.monitor_1_tex);
		        ResourceManager.monitor_1.renderPart("Body");
		        GL11.glShadeModel(GL11.GL_FLAT);
			}
		});
		
	}
	
	private static void bindTexture(ResourceLocation res) {
		Minecraft.getMinecraft().renderEngine.bindTexture(res);
	}
}
