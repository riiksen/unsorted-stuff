/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer
extends TileEntitySpecialRenderer {
    private static final ResourceLocation field_147513_b = new ResourceLocation("textures/entity/sign.png");
    private final ModelSign field_147514_c = new ModelSign();
    private static final String __OBFID = "CL_00000970";

    public void renderTileEntityAt(TileEntitySign p_147512_1_, double p_147512_2_, double p_147512_4_, double p_147512_6_, float p_147512_8_) {
        float var12;
        Block var9 = p_147512_1_.getBlockType();
        GL11.glPushMatrix();
        float var10 = 0.6666667f;
        if (var9 == Blocks.standing_sign) {
            GL11.glTranslatef((float)((float)p_147512_2_ + 0.5f), (float)((float)p_147512_4_ + 0.75f * var10), (float)((float)p_147512_6_ + 0.5f));
            float var11 = (float)(p_147512_1_.getBlockMetadata() * 360) / 16.0f;
            GL11.glRotatef((float)(- var11), (float)0.0f, (float)1.0f, (float)0.0f);
            this.field_147514_c.signStick.showModel = true;
        } else {
            int var16 = p_147512_1_.getBlockMetadata();
            var12 = 0.0f;
            if (var16 == 2) {
                var12 = 180.0f;
            }
            if (var16 == 4) {
                var12 = 90.0f;
            }
            if (var16 == 5) {
                var12 = -90.0f;
            }
            GL11.glTranslatef((float)((float)p_147512_2_ + 0.5f), (float)((float)p_147512_4_ + 0.75f * var10), (float)((float)p_147512_6_ + 0.5f));
            GL11.glRotatef((float)(- var12), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.3125f, (float)-0.4375f);
            this.field_147514_c.signStick.showModel = false;
        }
        this.bindTexture(field_147513_b);
        GL11.glPushMatrix();
        GL11.glScalef((float)var10, (float)(- var10), (float)(- var10));
        this.field_147514_c.renderSign();
        GL11.glPopMatrix();
        FontRenderer var17 = this.func_147498_b();
        var12 = 0.016666668f * var10;
        GL11.glTranslatef((float)0.0f, (float)(0.5f * var10), (float)(0.07f * var10));
        GL11.glScalef((float)var12, (float)(- var12), (float)var12);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)(-1.0f * var12));
        GL11.glDepthMask((boolean)false);
        int var13 = 0;
        int var14 = 0;
        while (var14 < p_147512_1_.field_145915_a.length) {
            String var15 = p_147512_1_.field_145915_a[var14];
            if (var14 == p_147512_1_.field_145918_i) {
                var15 = "> " + var15 + " <";
                var17.drawString(var15, (- var17.getStringWidth(var15)) / 2, var14 * 10 - p_147512_1_.field_145915_a.length * 5, var13);
            } else {
                var17.drawString(var15, (- var17.getStringWidth(var15)) / 2, var14 * 10 - p_147512_1_.field_145915_a.length * 5, var13);
            }
            ++var14;
        }
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntitySign)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

