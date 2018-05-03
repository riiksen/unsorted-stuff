/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityEnderChestRenderer
extends TileEntitySpecialRenderer {
    private static final ResourceLocation field_147520_b = new ResourceLocation("textures/entity/chest/ender.png");
    private ModelChest field_147521_c = new ModelChest();
    private static final String __OBFID = "CL_00000967";

    public void renderTileEntityAt(TileEntityEnderChest p_147519_1_, double p_147519_2_, double p_147519_4_, double p_147519_6_, float p_147519_8_) {
        int var9 = 0;
        if (p_147519_1_.hasWorldObj()) {
            var9 = p_147519_1_.getBlockMetadata();
        }
        this.bindTexture(field_147520_b);
        GL11.glPushMatrix();
        GL11.glEnable((int)32826);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glTranslatef((float)((float)p_147519_2_), (float)((float)p_147519_4_ + 1.0f), (float)((float)p_147519_6_ + 1.0f));
        GL11.glScalef((float)1.0f, (float)-1.0f, (float)-1.0f);
        GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        int var10 = 0;
        if (var9 == 2) {
            var10 = 180;
        }
        if (var9 == 3) {
            var10 = 0;
        }
        if (var9 == 4) {
            var10 = 90;
        }
        if (var9 == 5) {
            var10 = -90;
        }
        GL11.glRotatef((float)var10, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
        float var11 = p_147519_1_.field_145975_i + (p_147519_1_.field_145972_a - p_147519_1_.field_145975_i) * p_147519_8_;
        var11 = 1.0f - var11;
        var11 = 1.0f - var11 * var11 * var11;
        this.field_147521_c.chestLid.rotateAngleX = - var11 * 3.1415927f / 2.0f;
        this.field_147521_c.renderAll();
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityEnderChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

