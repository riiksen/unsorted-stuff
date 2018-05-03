/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBoat
extends Render {
    private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
    protected ModelBase modelBoat;
    private static final String __OBFID = "CL_00000981";

    public RenderBoat() {
        this.shadowSize = 0.5f;
        this.modelBoat = new ModelBoat();
    }

    public void doRender(EntityBoat par1EntityBoat, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        GL11.glRotatef((float)(180.0f - par8), (float)0.0f, (float)1.0f, (float)0.0f);
        float var10 = (float)par1EntityBoat.getTimeSinceHit() - par9;
        float var11 = par1EntityBoat.getDamageTaken() - par9;
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 0.0f) {
            GL11.glRotatef((float)(MathHelper.sin(var10) * var10 * var11 / 10.0f * (float)par1EntityBoat.getForwardDirection()), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        float var12 = 0.75f;
        GL11.glScalef((float)var12, (float)var12, (float)var12);
        GL11.glScalef((float)(1.0f / var12), (float)(1.0f / var12), (float)(1.0f / var12));
        this.bindEntityTexture(par1EntityBoat);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        this.modelBoat.render(par1EntityBoat, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityBoat par1EntityBoat) {
        return boatTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityBoat)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityBoat)par1Entity, par2, par4, par6, par8, par9);
    }
}

