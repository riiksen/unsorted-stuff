/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDragon
extends RenderLiving {
    private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
    private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
    private static final ResourceLocation enderDragonEyesTextures = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    protected ModelDragon modelDragon;
    private static final String __OBFID = "CL_00000988";

    public RenderDragon() {
        super(new ModelDragon(0.0f), 0.5f);
        this.modelDragon = (ModelDragon)this.mainModel;
        this.setRenderPassModel(this.mainModel);
    }

    protected void rotateCorpse(EntityDragon par1EntityDragon, float par2, float par3, float par4) {
        float var5 = (float)par1EntityDragon.getMovementOffsets(7, par4)[0];
        float var6 = (float)(par1EntityDragon.getMovementOffsets(5, par4)[1] - par1EntityDragon.getMovementOffsets(10, par4)[1]);
        GL11.glRotatef((float)(- var5), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(var6 * 10.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)1.0f);
        if (par1EntityDragon.deathTime > 0) {
            float var7 = ((float)par1EntityDragon.deathTime + par4 - 1.0f) / 20.0f * 1.6f;
            if ((var7 = MathHelper.sqrt_float(var7)) > 1.0f) {
                var7 = 1.0f;
            }
            GL11.glRotatef((float)(var7 * this.getDeathMaxRotation(par1EntityDragon)), (float)0.0f, (float)0.0f, (float)1.0f);
        }
    }

    protected void renderModel(EntityDragon par1EntityDragon, float par2, float par3, float par4, float par5, float par6, float par7) {
        if (par1EntityDragon.deathTicks > 0) {
            float var8 = (float)par1EntityDragon.deathTicks / 200.0f;
            GL11.glDepthFunc((int)515);
            GL11.glEnable((int)3008);
            GL11.glAlphaFunc((int)516, (float)var8);
            this.bindTexture(enderDragonExplodingTextures);
            this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glAlphaFunc((int)516, (float)0.1f);
            GL11.glDepthFunc((int)514);
        }
        this.bindEntityTexture(par1EntityDragon);
        this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
        if (par1EntityDragon.hurtTime > 0) {
            GL11.glDepthFunc((int)514);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)0.5f);
            this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glDepthFunc((int)515);
        }
    }

    public void doRender(EntityDragon par1EntityDragon, double par2, double par4, double par6, float par8, float par9) {
        BossStatus.setBossStatus(par1EntityDragon, false);
        super.doRender(par1EntityDragon, par2, par4, par6, par8, par9);
        if (par1EntityDragon.healingEnderCrystal != null) {
            float var10 = (float)par1EntityDragon.healingEnderCrystal.innerRotation + par9;
            float var11 = MathHelper.sin(var10 * 0.2f) / 2.0f + 0.5f;
            var11 = (var11 * var11 + var11) * 0.2f;
            float var12 = (float)(par1EntityDragon.healingEnderCrystal.posX - par1EntityDragon.posX - (par1EntityDragon.prevPosX - par1EntityDragon.posX) * (double)(1.0f - par9));
            float var13 = (float)((double)var11 + par1EntityDragon.healingEnderCrystal.posY - 1.0 - par1EntityDragon.posY - (par1EntityDragon.prevPosY - par1EntityDragon.posY) * (double)(1.0f - par9));
            float var14 = (float)(par1EntityDragon.healingEnderCrystal.posZ - par1EntityDragon.posZ - (par1EntityDragon.prevPosZ - par1EntityDragon.posZ) * (double)(1.0f - par9));
            float var15 = MathHelper.sqrt_float(var12 * var12 + var14 * var14);
            float var16 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)par2), (float)((float)par4 + 2.0f), (float)((float)par6));
            GL11.glRotatef((float)((float)(- Math.atan2(var14, var12)) * 180.0f / 3.1415927f - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)((float)(- Math.atan2(var15, var13)) * 180.0f / 3.1415927f - 90.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            Tessellator var17 = Tessellator.instance;
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable((int)2884);
            this.bindTexture(enderDragonCrystalBeamTextures);
            GL11.glShadeModel((int)7425);
            float var18 = 0.0f - ((float)par1EntityDragon.ticksExisted + par9) * 0.01f;
            float var19 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14) / 32.0f - ((float)par1EntityDragon.ticksExisted + par9) * 0.01f;
            var17.startDrawing(5);
            int var20 = 8;
            int var21 = 0;
            while (var21 <= var20) {
                float var22 = MathHelper.sin((float)(var21 % var20) * 3.1415927f * 2.0f / (float)var20) * 0.75f;
                float var23 = MathHelper.cos((float)(var21 % var20) * 3.1415927f * 2.0f / (float)var20) * 0.75f;
                float var24 = (float)(var21 % var20) * 1.0f / (float)var20;
                var17.setColorOpaque_I(0);
                var17.addVertexWithUV(var22 * 0.2f, var23 * 0.2f, 0.0, var24, var19);
                var17.setColorOpaque_I(16777215);
                var17.addVertexWithUV(var22, var23, var16, var24, var18);
                ++var21;
            }
            var17.draw();
            GL11.glEnable((int)2884);
            GL11.glShadeModel((int)7424);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }

    protected ResourceLocation getEntityTexture(EntityDragon par1EntityDragon) {
        return enderDragonTextures;
    }

    protected void renderEquippedItems(EntityDragon par1EntityDragon, float par2) {
        super.renderEquippedItems(par1EntityDragon, par2);
        Tessellator var3 = Tessellator.instance;
        if (par1EntityDragon.deathTicks > 0) {
            RenderHelper.disableStandardItemLighting();
            float var4 = ((float)par1EntityDragon.deathTicks + par2) / 200.0f;
            float var5 = 0.0f;
            if (var4 > 0.8f) {
                var5 = (var4 - 0.8f) / 0.2f;
            }
            Random var6 = new Random(432);
            GL11.glDisable((int)3553);
            GL11.glShadeModel((int)7425);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)1);
            GL11.glDisable((int)3008);
            GL11.glEnable((int)2884);
            GL11.glDepthMask((boolean)false);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)-1.0f, (float)-2.0f);
            int var7 = 0;
            while ((float)var7 < (var4 + var4 * var4) / 2.0f * 60.0f) {
                GL11.glRotatef((float)(var6.nextFloat() * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(var6.nextFloat() * 360.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(var6.nextFloat() * 360.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)(var6.nextFloat() * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(var6.nextFloat() * 360.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(var6.nextFloat() * 360.0f + var4 * 90.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                var3.startDrawing(6);
                float var8 = var6.nextFloat() * 20.0f + 5.0f + var5 * 10.0f;
                float var9 = var6.nextFloat() * 2.0f + 1.0f + var5 * 2.0f;
                var3.setColorRGBA_I(16777215, (int)(255.0f * (1.0f - var5)));
                var3.addVertex(0.0, 0.0, 0.0);
                var3.setColorRGBA_I(16711935, 0);
                var3.addVertex(-0.866 * (double)var9, var8, -0.5f * var9);
                var3.addVertex(0.866 * (double)var9, var8, -0.5f * var9);
                var3.addVertex(0.0, var8, 1.0f * var9);
                var3.addVertex(-0.866 * (double)var9, var8, -0.5f * var9);
                var3.draw();
                ++var7;
            }
            GL11.glPopMatrix();
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glShadeModel((int)7424);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3008);
            RenderHelper.enableStandardItemLighting();
        }
    }

    protected int shouldRenderPass(EntityDragon par1EntityDragon, int par2, float par3) {
        if (par2 == 1) {
            GL11.glDepthFunc((int)515);
        }
        if (par2 != 0) {
            return -1;
        }
        this.bindTexture(enderDragonEyesTextures);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)1, (int)1);
        GL11.glDisable((int)2896);
        GL11.glDepthFunc((int)514);
        int var4 = 61680;
        int var5 = var4 % 65536;
        int var6 = var4 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var5 / 1.0f, (float)var6 / 1.0f);
        GL11.glEnable((int)2896);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        return 1;
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityDragon)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityDragon)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityDragon)par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        this.rotateCorpse((EntityDragon)par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.renderModel((EntityDragon)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityDragon)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
    }
}

