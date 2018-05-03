/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer
extends TileEntitySpecialRenderer {
    private static final String __OBFID = "CL_00000968";

    public void renderTileEntityAt(TileEntityMobSpawner p_147518_1_, double p_147518_2_, double p_147518_4_, double p_147518_6_, float p_147518_8_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)p_147518_2_ + 0.5f), (float)((float)p_147518_4_), (float)((float)p_147518_6_ + 0.5f));
        TileEntityMobSpawnerRenderer.func_147517_a(p_147518_1_.func_145881_a(), p_147518_2_, p_147518_4_, p_147518_6_, p_147518_8_);
        GL11.glPopMatrix();
    }

    public static void func_147517_a(MobSpawnerBaseLogic p_147517_0_, double p_147517_1_, double p_147517_3_, double p_147517_5_, float p_147517_7_) {
        Entity var8 = p_147517_0_.func_98281_h();
        if (var8 != null) {
            var8.setWorld(p_147517_0_.getSpawnerWorld());
            float var9 = 0.4375f;
            GL11.glTranslatef((float)0.0f, (float)0.4f, (float)0.0f);
            GL11.glRotatef((float)((float)(p_147517_0_.field_98284_d + (p_147517_0_.field_98287_c - p_147517_0_.field_98284_d) * (double)p_147517_7_) * 10.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)-30.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.4f, (float)0.0f);
            GL11.glScalef((float)var9, (float)var9, (float)var9);
            var8.setLocationAndAngles(p_147517_1_, p_147517_3_, p_147517_5_, 0.0f, 0.0f);
            RenderManager.instance.func_147940_a(var8, 0.0, 0.0, 0.0, 0.0f, p_147517_7_);
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityMobSpawner)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}

