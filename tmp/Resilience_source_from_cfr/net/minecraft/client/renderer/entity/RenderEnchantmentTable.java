/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEnchantmentTable
extends TileEntitySpecialRenderer {
    private static final ResourceLocation field_147540_b = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private ModelBook field_147541_c = new ModelBook();
    private static final String __OBFID = "CL_00000966";

    public void renderTileEntityAt(TileEntityEnchantmentTable p_147539_1_, double p_147539_2_, double p_147539_4_, double p_147539_6_, float p_147539_8_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)p_147539_2_ + 0.5f), (float)((float)p_147539_4_ + 0.75f), (float)((float)p_147539_6_ + 0.5f));
        float var9 = (float)p_147539_1_.field_145926_a + p_147539_8_;
        GL11.glTranslatef((float)0.0f, (float)(0.1f + MathHelper.sin(var9 * 0.1f) * 0.01f), (float)0.0f);
        float var10 = p_147539_1_.field_145928_o - p_147539_1_.field_145925_p;
        while (var10 >= 3.1415927f) {
            var10 -= 6.2831855f;
        }
        while (var10 < -3.1415927f) {
            var10 += 6.2831855f;
        }
        float var11 = p_147539_1_.field_145925_p + var10 * p_147539_8_;
        GL11.glRotatef((float)((- var11) * 180.0f / 3.1415927f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)80.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        this.bindTexture(field_147540_b);
        float var12 = p_147539_1_.field_145931_j + (p_147539_1_.field_145933_i - p_147539_1_.field_145931_j) * p_147539_8_ + 0.25f;
        float var13 = p_147539_1_.field_145931_j + (p_147539_1_.field_145933_i - p_147539_1_.field_145931_j) * p_147539_8_ + 0.75f;
        var12 = (var12 - (float)MathHelper.truncateDoubleToInt(var12)) * 1.6f - 0.3f;
        var13 = (var13 - (float)MathHelper.truncateDoubleToInt(var13)) * 1.6f - 0.3f;
        if (var12 < 0.0f) {
            var12 = 0.0f;
        }
        if (var13 < 0.0f) {
            var13 = 0.0f;
        }
        if (var12 > 1.0f) {
            var12 = 1.0f;
        }
        if (var13 > 1.0f) {
            var13 = 1.0f;
        }
        float var14 = p_147539_1_.field_145927_n + (p_147539_1_.field_145930_m - p_147539_1_.field_145927_n) * p_147539_8_;
        GL11.glEnable((int)2884);
        this.field_147541_c.render(null, var9, var12, var13, var14, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityEnchantmentTable)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

