/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTNTPrimed
extends Render {
    private RenderBlocks blockRenderer = new RenderBlocks();
    private static final String __OBFID = "CL_00001030";

    public RenderTNTPrimed() {
        this.shadowSize = 0.5f;
    }

    public void doRender(EntityTNTPrimed par1EntityTNTPrimed, double par2, double par4, double par6, float par8, float par9) {
        float var10;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        if ((float)par1EntityTNTPrimed.fuse - par9 + 1.0f < 10.0f) {
            var10 = 1.0f - ((float)par1EntityTNTPrimed.fuse - par9 + 1.0f) / 10.0f;
            if (var10 < 0.0f) {
                var10 = 0.0f;
            }
            if (var10 > 1.0f) {
                var10 = 1.0f;
            }
            var10 *= var10;
            var10 *= var10;
            float var11 = 1.0f + var10 * 0.3f;
            GL11.glScalef((float)var11, (float)var11, (float)var11);
        }
        var10 = (1.0f - ((float)par1EntityTNTPrimed.fuse - par9 + 1.0f) / 100.0f) * 0.8f;
        this.bindEntityTexture(par1EntityTNTPrimed);
        this.blockRenderer.renderBlockAsItem(Blocks.tnt, 0, par1EntityTNTPrimed.getBrightness(par9));
        if (par1EntityTNTPrimed.fuse / 5 % 2 == 0) {
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)772);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)var10);
            this.blockRenderer.renderBlockAsItem(Blocks.tnt, 0, 1.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
        }
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityTNTPrimed par1EntityTNTPrimed) {
        return TextureMap.locationBlocksTexture;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityTNTPrimed)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityTNTPrimed)par1Entity, par2, par4, par6, par8, par9);
    }
}

