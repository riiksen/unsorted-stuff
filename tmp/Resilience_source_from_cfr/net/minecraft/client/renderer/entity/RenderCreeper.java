/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCreeper
extends RenderLiving {
    private static final ResourceLocation armoredCreeperTextures = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private static final ResourceLocation creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");
    private ModelBase creeperModel = new ModelCreeper(2.0f);
    private static final String __OBFID = "CL_00000985";

    public RenderCreeper() {
        super(new ModelCreeper(), 0.5f);
    }

    protected void preRenderCallback(EntityCreeper par1EntityCreeper, float par2) {
        float var3 = par1EntityCreeper.getCreeperFlashIntensity(par2);
        float var4 = 1.0f + MathHelper.sin(var3 * 100.0f) * var3 * 0.01f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 *= var3;
        var3 *= var3;
        float var5 = (1.0f + var3 * 0.4f) * var4;
        float var6 = (1.0f + var3 * 0.1f) / var4;
        GL11.glScalef((float)var5, (float)var6, (float)var5);
    }

    protected int getColorMultiplier(EntityCreeper par1EntityCreeper, float par2, float par3) {
        float var4 = par1EntityCreeper.getCreeperFlashIntensity(par3);
        if ((int)(var4 * 10.0f) % 2 == 0) {
            return 0;
        }
        int var5 = (int)(var4 * 0.2f * 255.0f);
        if (var5 < 0) {
            var5 = 0;
        }
        if (var5 > 255) {
            var5 = 255;
        }
        int var6 = 255;
        int var7 = 255;
        int var8 = 255;
        return var5 << 24 | var6 << 16 | var7 << 8 | var8;
    }

    protected int shouldRenderPass(EntityCreeper par1EntityCreeper, int par2, float par3) {
        if (par1EntityCreeper.getPowered()) {
            if (par1EntityCreeper.isInvisible()) {
                GL11.glDepthMask((boolean)false);
            } else {
                GL11.glDepthMask((boolean)true);
            }
            if (par2 == 1) {
                float var4 = (float)par1EntityCreeper.ticksExisted + par3;
                this.bindTexture(armoredCreeperTextures);
                GL11.glMatrixMode((int)5890);
                GL11.glLoadIdentity();
                float var5 = var4 * 0.01f;
                float var6 = var4 * 0.01f;
                GL11.glTranslatef((float)var5, (float)var6, (float)0.0f);
                this.setRenderPassModel(this.creeperModel);
                GL11.glMatrixMode((int)5888);
                GL11.glEnable((int)3042);
                float var7 = 0.5f;
                GL11.glColor4f((float)var7, (float)var7, (float)var7, (float)1.0f);
                GL11.glDisable((int)2896);
                GL11.glBlendFunc((int)1, (int)1);
                return 1;
            }
            if (par2 == 2) {
                GL11.glMatrixMode((int)5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode((int)5888);
                GL11.glEnable((int)2896);
                GL11.glDisable((int)3042);
            }
        }
        return -1;
    }

    protected int inheritRenderPass(EntityCreeper par1EntityCreeper, int par2, float par3) {
        return -1;
    }

    protected ResourceLocation getEntityTexture(EntityCreeper par1EntityCreeper) {
        return creeperTextures;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityCreeper)par1EntityLivingBase, par2);
    }

    @Override
    protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3) {
        return this.getColorMultiplier((EntityCreeper)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityCreeper)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.inheritRenderPass((EntityCreeper)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityCreeper)par1Entity);
    }
}

