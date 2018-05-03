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
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSnowball
extends Render {
    private Item field_94151_a;
    private int field_94150_f;
    private static final String __OBFID = "CL_00001008";

    public RenderSnowball(Item par1Item, int par2) {
        this.field_94151_a = par1Item;
        this.field_94150_f = par2;
    }

    public RenderSnowball(Item par1Item) {
        this(par1Item, 0);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        IIcon var10 = this.field_94151_a.getIconFromDamage(this.field_94150_f);
        if (var10 != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
            GL11.glEnable((int)32826);
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            this.bindEntityTexture(par1Entity);
            Tessellator var11 = Tessellator.instance;
            if (var10 == ItemPotion.func_94589_d("bottle_splash")) {
                int var12 = PotionHelper.func_77915_a(((EntityPotion)par1Entity).getPotionDamage(), false);
                float var13 = (float)(var12 >> 16 & 255) / 255.0f;
                float var14 = (float)(var12 >> 8 & 255) / 255.0f;
                float var15 = (float)(var12 & 255) / 255.0f;
                GL11.glColor3f((float)var13, (float)var14, (float)var15);
                GL11.glPushMatrix();
                this.func_77026_a(var11, ItemPotion.func_94589_d("overlay"));
                GL11.glPopMatrix();
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            }
            this.func_77026_a(var11, var10);
            GL11.glDisable((int)32826);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return TextureMap.locationItemsTexture;
    }

    private void func_77026_a(Tessellator par1Tessellator, IIcon par2Icon) {
        float var3 = par2Icon.getMinU();
        float var4 = par2Icon.getMaxU();
        float var5 = par2Icon.getMinV();
        float var6 = par2Icon.getMaxV();
        float var7 = 1.0f;
        float var8 = 0.5f;
        float var9 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(- this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0f, 1.0f, 0.0f);
        par1Tessellator.addVertexWithUV(0.0f - var8, 0.0f - var9, 0.0, var3, var6);
        par1Tessellator.addVertexWithUV(var7 - var8, 0.0f - var9, 0.0, var4, var6);
        par1Tessellator.addVertexWithUV(var7 - var8, var7 - var9, 0.0, var4, var5);
        par1Tessellator.addVertexWithUV(0.0f - var8, var7 - var9, 0.0, var3, var5);
        par1Tessellator.draw();
    }
}

