/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

public class TexturedQuad {
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    private static final String __OBFID = "CL_00000850";

    public TexturedQuad(PositionTextureVertex[] par1ArrayOfPositionTextureVertex) {
        this.vertexPositions = par1ArrayOfPositionTextureVertex;
        this.nVertices = par1ArrayOfPositionTextureVertex.length;
    }

    public TexturedQuad(PositionTextureVertex[] par1ArrayOfPositionTextureVertex, int par2, int par3, int par4, int par5, float par6, float par7) {
        this(par1ArrayOfPositionTextureVertex);
        float var8 = 0.0f / par6;
        float var9 = 0.0f / par7;
        par1ArrayOfPositionTextureVertex[0] = par1ArrayOfPositionTextureVertex[0].setTexturePosition((float)par4 / par6 - var8, (float)par3 / par7 + var9);
        par1ArrayOfPositionTextureVertex[1] = par1ArrayOfPositionTextureVertex[1].setTexturePosition((float)par2 / par6 + var8, (float)par3 / par7 + var9);
        par1ArrayOfPositionTextureVertex[2] = par1ArrayOfPositionTextureVertex[2].setTexturePosition((float)par2 / par6 + var8, (float)par5 / par7 - var9);
        par1ArrayOfPositionTextureVertex[3] = par1ArrayOfPositionTextureVertex[3].setTexturePosition((float)par4 / par6 - var8, (float)par5 / par7 - var9);
    }

    public void flipFace() {
        PositionTextureVertex[] var1 = new PositionTextureVertex[this.vertexPositions.length];
        int var2 = 0;
        while (var2 < this.vertexPositions.length) {
            var1[var2] = this.vertexPositions[this.vertexPositions.length - var2 - 1];
            ++var2;
        }
        this.vertexPositions = var1;
    }

    public void draw(Tessellator par1Tessellator, float par2) {
        Vec3 var3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
        Vec3 var4 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
        Vec3 var5 = var4.crossProduct(var3).normalize();
        par1Tessellator.startDrawingQuads();
        if (this.invertNormal) {
            par1Tessellator.setNormal(- (float)var5.xCoord, - (float)var5.yCoord, - (float)var5.zCoord);
        } else {
            par1Tessellator.setNormal((float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
        }
        int var6 = 0;
        while (var6 < 4) {
            PositionTextureVertex var7 = this.vertexPositions[var6];
            par1Tessellator.addVertexWithUV((float)var7.vector3D.xCoord * par2, (float)var7.vector3D.yCoord * par2, (float)var7.vector3D.zCoord * par2, var7.texturePositionX, var7.texturePositionY);
            ++var6;
        }
        par1Tessellator.draw();
    }
}

