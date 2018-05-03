/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelEnderCrystal
extends ModelBase {
    private ModelRenderer cube;
    private ModelRenderer glass;
    private ModelRenderer base;
    private static final String __OBFID = "CL_00000871";

    public ModelEnderCrystal(float par1, boolean par2) {
        this.glass = new ModelRenderer(this, "glass");
        this.glass.setTextureOffset(0, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        if (par2) {
            this.base = new ModelRenderer(this, "base");
            this.base.setTextureOffset(0, 16).addBox(-6.0f, 0.0f, -6.0f, 12, 4, 12);
        }
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        GL11.glPushMatrix();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        GL11.glTranslatef((float)0.0f, (float)-0.5f, (float)0.0f);
        if (this.base != null) {
            this.base.render(par7);
        }
        GL11.glRotatef((float)par3, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)(0.8f + par4), (float)0.0f);
        GL11.glRotatef((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        this.glass.render(par7);
        float var8 = 0.875f;
        GL11.glScalef((float)var8, (float)var8, (float)var8);
        GL11.glRotatef((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        GL11.glRotatef((float)par3, (float)0.0f, (float)1.0f, (float)0.0f);
        this.glass.render(par7);
        GL11.glScalef((float)var8, (float)var8, (float)var8);
        GL11.glRotatef((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        GL11.glRotatef((float)par3, (float)0.0f, (float)1.0f, (float)0.0f);
        this.cube.render(par7);
        GL11.glPopMatrix();
    }
}

