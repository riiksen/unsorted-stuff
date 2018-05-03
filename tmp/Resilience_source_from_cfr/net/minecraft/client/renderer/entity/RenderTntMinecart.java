/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.init.Blocks;
import org.lwjgl.opengl.GL11;

public class RenderTntMinecart
extends RenderMinecart {
    private static final String __OBFID = "CL_00001029";

    protected void func_147910_a(EntityMinecartTNT p_147912_1_, float p_147912_2_, Block p_147912_3_, int p_147912_4_) {
        int var5 = p_147912_1_.func_94104_d();
        if (var5 > -1 && (float)var5 - p_147912_2_ + 1.0f < 10.0f) {
            float var6 = 1.0f - ((float)var5 - p_147912_2_ + 1.0f) / 10.0f;
            if (var6 < 0.0f) {
                var6 = 0.0f;
            }
            if (var6 > 1.0f) {
                var6 = 1.0f;
            }
            var6 *= var6;
            var6 *= var6;
            float var7 = 1.0f + var6 * 0.3f;
            GL11.glScalef((float)var7, (float)var7, (float)var7);
        }
        super.func_147910_a(p_147912_1_, p_147912_2_, p_147912_3_, p_147912_4_);
        if (var5 > -1 && var5 / 5 % 2 == 0) {
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)772);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)((1.0f - ((float)var5 - p_147912_2_ + 1.0f) / 100.0f) * 0.8f));
            GL11.glPushMatrix();
            this.field_94145_f.renderBlockAsItem(Blocks.tnt, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
        }
    }

    @Override
    protected void func_147910_a(EntityMinecart p_147910_1_, float p_147910_2_, Block p_147910_3_, int p_147910_4_) {
        this.func_147910_a((EntityMinecartTNT)p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
    }
}

