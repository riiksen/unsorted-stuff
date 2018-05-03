/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class MapItemRenderer {
    private static final ResourceLocation field_148253_a = new ResourceLocation("textures/map/map_icons.png");
    private final TextureManager field_148251_b;
    private final Map field_148252_c = Maps.newHashMap();
    private static final String __OBFID = "CL_00000663";

    public MapItemRenderer(TextureManager p_i45009_1_) {
        this.field_148251_b = p_i45009_1_;
    }

    public void func_148246_a(MapData p_148246_1_) {
        this.func_148248_b(p_148246_1_).func_148236_a();
    }

    public void func_148250_a(MapData p_148250_1_, boolean p_148250_2_) {
        this.func_148248_b(p_148250_1_).func_148237_a(p_148250_2_);
    }

    private Instance func_148248_b(MapData p_148248_1_) {
        Instance var2 = (Instance)this.field_148252_c.get(p_148248_1_.mapName);
        if (var2 == null) {
            var2 = new Instance(p_148248_1_, null);
            this.field_148252_c.put(p_148248_1_.mapName, var2);
        }
        return var2;
    }

    public void func_148249_a() {
        for (Instance var2 : this.field_148252_c.values()) {
            this.field_148251_b.func_147645_c(var2.field_148240_d);
        }
        this.field_148252_c.clear();
    }

    class Instance {
        private final MapData field_148242_b;
        private final DynamicTexture field_148243_c;
        private final ResourceLocation field_148240_d;
        private final int[] field_148241_e;
        private static final String __OBFID = "CL_00000665";

        private Instance(MapData p_i45007_2_) {
            this.field_148242_b = p_i45007_2_;
            this.field_148243_c = new DynamicTexture(128, 128);
            this.field_148241_e = this.field_148243_c.getTextureData();
            this.field_148240_d = MapItemRenderer.this.field_148251_b.getDynamicTextureLocation("map/" + p_i45007_2_.mapName, this.field_148243_c);
            int var3 = 0;
            while (var3 < this.field_148241_e.length) {
                this.field_148241_e[var3] = 0;
                ++var3;
            }
        }

        private void func_148236_a() {
            int var1 = 0;
            while (var1 < 16384) {
                int var2 = this.field_148242_b.colors[var1] & 255;
                this.field_148241_e[var1] = var2 / 4 == 0 ? (var1 + var1 / 128 & 1) * 8 + 16 << 24 : MapColor.mapColorArray[var2 / 4].func_151643_b(var2 & 3);
                ++var1;
            }
            this.field_148243_c.updateDynamicTexture();
        }

        private void func_148237_a(boolean p_148237_1_) {
            int var2 = 0;
            int var3 = 0;
            Tessellator var4 = Tessellator.instance;
            float var5 = 0.0f;
            MapItemRenderer.this.field_148251_b.bindTexture(this.field_148240_d);
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(1, 771, 0, 1);
            GL11.glDisable((int)3008);
            var4.startDrawingQuads();
            var4.addVertexWithUV((float)(var2 + 0) + var5, (float)(var3 + 128) - var5, -0.009999999776482582, 0.0, 1.0);
            var4.addVertexWithUV((float)(var2 + 128) - var5, (float)(var3 + 128) - var5, -0.009999999776482582, 1.0, 1.0);
            var4.addVertexWithUV((float)(var2 + 128) - var5, (float)(var3 + 0) + var5, -0.009999999776482582, 1.0, 0.0);
            var4.addVertexWithUV((float)(var2 + 0) + var5, (float)(var3 + 0) + var5, -0.009999999776482582, 0.0, 0.0);
            var4.draw();
            GL11.glEnable((int)3008);
            GL11.glDisable((int)3042);
            MapItemRenderer.this.field_148251_b.bindTexture(field_148253_a);
            int var6 = 0;
            for (MapData.MapCoord var8 : this.field_148242_b.playersVisibleOnMap.values()) {
                if (p_148237_1_ && var8.iconSize != 1) continue;
                GL11.glPushMatrix();
                GL11.glTranslatef((float)((float)var2 + (float)var8.centerX / 2.0f + 64.0f), (float)((float)var3 + (float)var8.centerZ / 2.0f + 64.0f), (float)-0.02f);
                GL11.glRotatef((float)((float)(var8.iconRotation * 360) / 16.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glScalef((float)4.0f, (float)4.0f, (float)3.0f);
                GL11.glTranslatef((float)-0.125f, (float)0.125f, (float)0.0f);
                float var9 = (float)(var8.iconSize % 4 + 0) / 4.0f;
                float var10 = (float)(var8.iconSize / 4 + 0) / 4.0f;
                float var11 = (float)(var8.iconSize % 4 + 1) / 4.0f;
                float var12 = (float)(var8.iconSize / 4 + 1) / 4.0f;
                var4.startDrawingQuads();
                var4.addVertexWithUV(-1.0, 1.0, (float)var6 * 0.001f, var9, var10);
                var4.addVertexWithUV(1.0, 1.0, (float)var6 * 0.001f, var11, var10);
                var4.addVertexWithUV(1.0, -1.0, (float)var6 * 0.001f, var11, var12);
                var4.addVertexWithUV(-1.0, -1.0, (float)var6 * 0.001f, var9, var12);
                var4.draw();
                GL11.glPopMatrix();
                ++var6;
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.04f);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
        }

        Instance(MapData p_i45008_2_, Object p_i45008_3_) {
            this(p_i45008_2_);
        }
    }

}

