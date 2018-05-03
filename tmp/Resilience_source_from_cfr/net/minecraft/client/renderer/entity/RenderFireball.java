/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderFireball
extends Render {
    private float field_77002_a;
    private static final String __OBFID = "CL_00000995";

    public RenderFireball(float par1) {
        this.field_77002_a = par1;
    }

    public void doRender(EntityFireball par1EntityFireball, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        this.bindEntityTexture(par1EntityFireball);
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        GL11.glEnable((int)32826);
        float var10 = this.field_77002_a;
        GL11.glScalef((float)(var10 / 1.0f), (float)(var10 / 1.0f), (float)(var10 / 1.0f));
        IIcon var11 = Items.fire_charge.getIconFromDamage(0);
        Tessellator var12 = Tessellator.instance;
        float var13 = var11.getMinU();
        float var14 = var11.getMaxU();
        float var15 = var11.getMinV();
        float var16 = var11.getMaxV();
        float var17 = 1.0f;
        float var18 = 0.5f;
        float var19 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(- this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        var12.startDrawingQuads();
        var12.setNormal(0.0f, 1.0f, 0.0f);
        var12.addVertexWithUV(0.0f - var18, 0.0f - var19, 0.0, var13, var16);
        var12.addVertexWithUV(var17 - var18, 0.0f - var19, 0.0, var14, var16);
        var12.addVertexWithUV(var17 - var18, 1.0f - var19, 0.0, var14, var15);
        var12.addVertexWithUV(0.0f - var18, 1.0f - var19, 0.0, var13, var15);
        var12.draw();
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityFireball par1EntityFireball) {
        return TextureMap.locationItemsTexture;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityFireball)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityFireball)par1Entity, par2, par4, par6, par8, par9);
    }
}

