package com.hbm.render.loader;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;

public class HbmGroupObject {
	
    public String name;
    public ArrayList<HbmFace> faces = new ArrayList<>();
    public int glDrawingMode;

    public HbmGroupObject()
    {
        this("");
    }

    public HbmGroupObject(String name)
    {
        this(name, -1);
    }

    public HbmGroupObject(String name, int glDrawingMode)
    {
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    @SideOnly(Side.CLIENT)
    public void render()
    {
        if (this.faces.size() > 0)
        {
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(this.glDrawingMode);
            render(tessellator);
            tessellator.draw();
        }
    }

    @SideOnly(Side.CLIENT)
    public void render(Tessellator tessellator)
    {
        if (this.faces.size() > 0)
        {
            for (HbmFace face : this.faces)
            {
                face.addFaceForRender(tessellator);
            }
        }
    }
}
