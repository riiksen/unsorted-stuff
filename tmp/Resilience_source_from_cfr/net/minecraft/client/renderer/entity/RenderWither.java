/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderWither
extends RenderLiving {
    private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
    private int field_82419_a;
    private static final String __OBFID = "CL_00001034";

    public RenderWither() {
        super(new ModelWither(), 1.0f);
        this.field_82419_a = ((ModelWither)this.mainModel).func_82903_a();
    }

    public void doRender(EntityWither par1EntityWither, double par2, double par4, double par6, float par8, float par9) {
        BossStatus.setBossStatus(par1EntityWither, true);
        int var10 = ((ModelWither)this.mainModel).func_82903_a();
        if (var10 != this.field_82419_a) {
            this.field_82419_a = var10;
            this.mainModel = new ModelWither();
        }
        super.doRender(par1EntityWither, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityWither par1EntityWither) {
        int var2 = par1EntityWither.func_82212_n();
        return var2 > 0 && (var2 > 80 || var2 / 5 % 2 != 1) ? invulnerableWitherTextures : witherTextures;
    }

    protected void preRenderCallback(EntityWither par1EntityWither, float par2) {
        int var3 = par1EntityWither.func_82212_n();
        if (var3 > 0) {
            float var4 = 2.0f - ((float)var3 - par2) / 220.0f * 0.5f;
            GL11.glScalef((float)var4, (float)var4, (float)var4);
        } else {
            GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        }
    }

    protected int shouldRenderPass(EntityWither par1EntityWither, int par2, float par3) {
        if (par1EntityWither.isArmored()) {
            if (par1EntityWither.isInvisible()) {
                GL11.glDepthMask((boolean)false);
            } else {
                GL11.glDepthMask((boolean)true);
            }
            if (par2 == 1) {
                float var4 = (float)par1EntityWither.ticksExisted + par3;
                this.bindTexture(invulnerableWitherTextures);
                GL11.glMatrixMode((int)5890);
                GL11.glLoadIdentity();
                float var5 = MathHelper.cos(var4 * 0.02f) * 3.0f;
                float var6 = var4 * 0.01f;
                GL11.glTranslatef((float)var5, (float)var6, (float)0.0f);
                this.setRenderPassModel(this.mainModel);
                GL11.glMatrixMode((int)5888);
                GL11.glEnable((int)3042);
                float var7 = 0.5f;
                GL11.glColor4f((float)var7, (float)var7, (float)var7, (float)1.0f);
                GL11.glDisable((int)2896);
                GL11.glBlendFunc((int)1, (int)1);
                GL11.glTranslatef((float)0.0f, (float)-0.01f, (float)0.0f);
                GL11.glScalef((float)1.1f, (float)1.1f, (float)1.1f);
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

    protected int inheritRenderPass(EntityWither par1EntityWither, int par2, float par3) {
        return -1;
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityWither)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((EntityWither)par1EntityLivingBase, par2);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityWither)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.inheritRenderPass((EntityWither)par1EntityLivingBase, par2, par3);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityWither)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityWither)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityWither)par1Entity, par2, par4, par6, par8, par9);
    }
}

