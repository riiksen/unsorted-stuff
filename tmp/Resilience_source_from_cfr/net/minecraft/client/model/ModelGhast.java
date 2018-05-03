/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.model;

import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelGhast
extends ModelBase {
    ModelRenderer body;
    ModelRenderer[] tentacles = new ModelRenderer[9];
    private static final String __OBFID = "CL_00000839";

    public ModelGhast() {
        int var1 = -16;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        this.body.rotationPointY += (float)(24 + var1);
        Random var2 = new Random(1660);
        int var3 = 0;
        while (var3 < this.tentacles.length) {
            this.tentacles[var3] = new ModelRenderer(this, 0, 0);
            float var4 = (((float)(var3 % 3) - (float)(var3 / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            float var5 = ((float)(var3 / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int var6 = var2.nextInt(7) + 8;
            this.tentacles[var3].addBox(-1.0f, 0.0f, -1.0f, 2, var6, 2);
            this.tentacles[var3].rotationPointX = var4;
            this.tentacles[var3].rotationPointZ = var5;
            this.tentacles[var3].rotationPointY = 31 + var1;
            ++var3;
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        int var8 = 0;
        while (var8 < this.tentacles.length) {
            this.tentacles[var8].rotateAngleX = 0.2f * MathHelper.sin(par3 * 0.3f + (float)var8) + 0.4f;
            ++var8;
        }
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.6f, (float)0.0f);
        this.body.render(par7);
        ModelRenderer[] var8 = this.tentacles;
        int var9 = var8.length;
        int var10 = 0;
        while (var10 < var9) {
            ModelRenderer var11 = var8[var10];
            var11.render(par7);
            ++var10;
        }
        GL11.glPopMatrix();
    }
}

