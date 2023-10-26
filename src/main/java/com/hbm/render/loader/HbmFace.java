package com.hbm.render.loader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;

public class HbmFace {
    public Vertex[] vertices;
    public Vertex[] vertexNormals;
    public Vertex faceNormal;
    public TextureCoordinate[] textureCoordinates;

    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator)
    {
        addFaceForRender(tessellator, 0.0005F);
    }

    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator, float textureOffset)
    {
        if (this.faceNormal == null)
        {
            this.faceNormal = calculateFaceNormal();
        }

        tessellator.setNormal(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z);

        float averageU = 0F;
        float averageV = 0F;

        if ((this.textureCoordinates != null) && (this.textureCoordinates.length > 0))
        {
            for (TextureCoordinate textureCoordinate : this.textureCoordinates) {
                averageU += textureCoordinate.u;
                averageV += textureCoordinate.v;
            }

            averageU = averageU / this.textureCoordinates.length;
            averageV = averageV / this.textureCoordinates.length;
        }

        float offsetU, offsetV;

        for (int i = 0; i < this.vertices.length; ++i)
        {

            if ((this.textureCoordinates != null) && (this.textureCoordinates.length > 0))
            {
                offsetU = textureOffset;
                offsetV = textureOffset;

                if (this.textureCoordinates[i].u > averageU)
                {
                    offsetU = -offsetU;
                }
                if (this.textureCoordinates[i].v > averageV)
                {
                    offsetV = -offsetV;
                }

                tessellator.addVertexWithUV(this.vertices[i].x, this.vertices[i].y, this.vertices[i].z, this.textureCoordinates[i].u + offsetU, this.textureCoordinates[i].v + offsetV + (((double)System.currentTimeMillis() % HmfController.modoloMod) / HmfController.quotientMod));
            }
            else
            {
                tessellator.addVertex(this.vertices[i].x, this.vertices[i].y, this.vertices[i].z);
            }
        }
    }

    public Vertex calculateFaceNormal()
    {
        Vec3 v1 = Vec3.createVectorHelper(this.vertices[1].x - this.vertices[0].x, this.vertices[1].y - this.vertices[0].y, this.vertices[1].z - this.vertices[0].z);
        Vec3 v2 = Vec3.createVectorHelper(this.vertices[2].x - this.vertices[0].x, this.vertices[2].y - this.vertices[0].y, this.vertices[2].z - this.vertices[0].z);
        Vec3 normalVector = null;

        normalVector = v1.crossProduct(v2).normalize();

        return new Vertex((float) normalVector.xCoord, (float) normalVector.yCoord, (float) normalVector.zCoord);
    }
}
