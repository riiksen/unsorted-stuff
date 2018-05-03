/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLeashKnot
extends Render {
    private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
    private ModelLeashKnot leashKnotModel = new ModelLeashKnot();
    private static final String __OBFID = "CL_00001010";

    public void doRender(EntityLeashKnot par1EntityLeashKnot, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        float var10 = 0.0625f;
        GL11.glEnable((int)32826);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        GL11.glEnable((int)3008);
        this.bindEntityTexture(par1EntityLeashKnot);
        this.leashKnotModel.render(par1EntityLeashKnot, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, var10);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityLeashKnot par1EntityLeashKnot) {
        return leashKnotTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityLeashKnot)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityLeashKnot)par1Entity, par2, par4, par6, par8, par9);
    }
}

