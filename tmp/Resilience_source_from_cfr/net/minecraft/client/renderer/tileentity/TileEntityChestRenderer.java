/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityChestRenderer
extends TileEntitySpecialRenderer {
    private static final ResourceLocation field_147507_b = new ResourceLocation("textures/entity/chest/trapped_double.png");
    private static final ResourceLocation field_147508_c = new ResourceLocation("textures/entity/chest/christmas_double.png");
    private static final ResourceLocation field_147505_d = new ResourceLocation("textures/entity/chest/normal_double.png");
    private static final ResourceLocation field_147506_e = new ResourceLocation("textures/entity/chest/trapped.png");
    private static final ResourceLocation field_147503_f = new ResourceLocation("textures/entity/chest/christmas.png");
    private static final ResourceLocation field_147504_g = new ResourceLocation("textures/entity/chest/normal.png");
    private ModelChest field_147510_h = new ModelChest();
    private ModelChest field_147511_i = new ModelLargeChest();
    private boolean field_147509_j;
    private static final String __OBFID = "CL_00000965";

    public TileEntityChestRenderer() {
        Calendar var1 = Calendar.getInstance();
        if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) {
            this.field_147509_j = true;
        }
    }

    public void renderTileEntityAt(TileEntityChest p_147502_1_, double p_147502_2_, double p_147502_4_, double p_147502_6_, float p_147502_8_) {
        int var9;
        if (!p_147502_1_.hasWorldObj()) {
            var9 = 0;
        } else {
            Block var10 = p_147502_1_.getBlockType();
            var9 = p_147502_1_.getBlockMetadata();
            if (var10 instanceof BlockChest && var9 == 0) {
                ((BlockChest)var10).func_149954_e(p_147502_1_.getWorldObj(), p_147502_1_.field_145851_c, p_147502_1_.field_145848_d, p_147502_1_.field_145849_e);
                var9 = p_147502_1_.getBlockMetadata();
            }
            p_147502_1_.func_145979_i();
        }
        if (p_147502_1_.field_145992_i == null && p_147502_1_.field_145991_k == null) {
            float var13;
            ModelChest var14;
            if (p_147502_1_.field_145990_j == null && p_147502_1_.field_145988_l == null) {
                var14 = this.field_147510_h;
                if (p_147502_1_.func_145980_j() == 1) {
                    this.bindTexture(field_147506_e);
                } else if (this.field_147509_j) {
                    this.bindTexture(field_147503_f);
                } else {
                    this.bindTexture(field_147504_g);
                }
            } else {
                var14 = this.field_147511_i;
                if (p_147502_1_.func_145980_j() == 1) {
                    this.bindTexture(field_147507_b);
                } else if (this.field_147509_j) {
                    this.bindTexture(field_147508_c);
                } else {
                    this.bindTexture(field_147505_d);
                }
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glTranslatef((float)((float)p_147502_2_), (float)((float)p_147502_4_ + 1.0f), (float)((float)p_147502_6_ + 1.0f));
            GL11.glScalef((float)1.0f, (float)-1.0f, (float)-1.0f);
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
            int var11 = 0;
            if (var9 == 2) {
                var11 = 180;
            }
            if (var9 == 3) {
                var11 = 0;
            }
            if (var9 == 4) {
                var11 = 90;
            }
            if (var9 == 5) {
                var11 = -90;
            }
            if (var9 == 2 && p_147502_1_.field_145990_j != null) {
                GL11.glTranslatef((float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (var9 == 5 && p_147502_1_.field_145988_l != null) {
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-1.0f);
            }
            GL11.glRotatef((float)var11, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
            float var12 = p_147502_1_.field_145986_n + (p_147502_1_.field_145989_m - p_147502_1_.field_145986_n) * p_147502_8_;
            if (p_147502_1_.field_145992_i != null && (var13 = p_147502_1_.field_145992_i.field_145986_n + (p_147502_1_.field_145992_i.field_145989_m - p_147502_1_.field_145992_i.field_145986_n) * p_147502_8_) > var12) {
                var12 = var13;
            }
            if (p_147502_1_.field_145991_k != null && (var13 = p_147502_1_.field_145991_k.field_145986_n + (p_147502_1_.field_145991_k.field_145989_m - p_147502_1_.field_145991_k.field_145986_n) * p_147502_8_) > var12) {
                var12 = var13;
            }
            var12 = 1.0f - var12;
            var12 = 1.0f - var12 * var12 * var12;
            var14.chestLid.rotateAngleX = - var12 * 3.1415927f / 2.0f;
            var14.renderAll();
            GL11.glDisable((int)32826);
            GL11.glPopMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

