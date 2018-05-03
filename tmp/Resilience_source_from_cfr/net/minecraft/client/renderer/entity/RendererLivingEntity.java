/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public abstract class RendererLivingEntity
extends Render {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    protected ModelBase mainModel;
    protected ModelBase renderPassModel;
    private static final String __OBFID = "CL_00001012";

    public RendererLivingEntity(ModelBase par1ModelBase, float par2) {
        this.mainModel = par1ModelBase;
        this.shadowSize = par2;
    }

    public void setRenderPassModel(ModelBase par1ModelBase) {
        this.renderPassModel = par1ModelBase;
    }

    private float interpolateRotation(float par1, float par2, float par3) {
        float var4 = par2 - par1;
        while (var4 < -180.0f) {
            var4 += 360.0f;
        }
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return par1 + par3 * var4;
    }

    public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        this.mainModel.onGround = this.renderSwingProgress(par1EntityLivingBase, par9);
        if (this.renderPassModel != null) {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }
        this.mainModel.isRiding = par1EntityLivingBase.isRiding();
        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }
        this.mainModel.isChild = par1EntityLivingBase.isChild();
        if (this.renderPassModel != null) {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }
        try {
            float var13;
            float var20;
            float var19;
            int var18;
            float var22;
            float var10 = this.interpolateRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par9);
            float var11 = this.interpolateRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par9);
            if (par1EntityLivingBase.isRiding() && par1EntityLivingBase.ridingEntity instanceof EntityLivingBase) {
                EntityLivingBase var12 = (EntityLivingBase)par1EntityLivingBase.ridingEntity;
                var10 = this.interpolateRotation(var12.prevRenderYawOffset, var12.renderYawOffset, par9);
                var13 = MathHelper.wrapAngleTo180_float(var11 - var10);
                if (var13 < -85.0f) {
                    var13 = -85.0f;
                }
                if (var13 >= 85.0f) {
                    var13 = 85.0f;
                }
                var10 = var11 - var13;
                if (var13 * var13 > 2500.0f) {
                    var10 += var13 * 0.2f;
                }
            }
            float var26 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par9;
            this.renderLivingAt(par1EntityLivingBase, par2, par4, par6);
            var13 = this.handleRotationFloat(par1EntityLivingBase, par9);
            this.rotateCorpse(par1EntityLivingBase, var13, var10, par9);
            float var14 = 0.0625f;
            GL11.glEnable((int)32826);
            GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
            this.preRenderCallback(par1EntityLivingBase, par9);
            GL11.glTranslatef((float)0.0f, (float)(-24.0f * var14 - 0.0078125f), (float)0.0f);
            float var15 = par1EntityLivingBase.prevLimbSwingAmount + (par1EntityLivingBase.limbSwingAmount - par1EntityLivingBase.prevLimbSwingAmount) * par9;
            float var16 = par1EntityLivingBase.limbSwing - par1EntityLivingBase.limbSwingAmount * (1.0f - par9);
            if (par1EntityLivingBase.isChild()) {
                var16 *= 3.0f;
            }
            if (var15 > 1.0f) {
                var15 = 1.0f;
            }
            GL11.glEnable((int)3008);
            this.mainModel.setLivingAnimations(par1EntityLivingBase, var16, var15, par9);
            this.renderModel(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
            int var17 = 0;
            while (var17 < 4) {
                var18 = this.shouldRenderPass(par1EntityLivingBase, var17, par9);
                if (var18 > 0) {
                    this.renderPassModel.setLivingAnimations(par1EntityLivingBase, var16, var15, par9);
                    this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    if ((var18 & 240) == 16) {
                        this.func_82408_c(par1EntityLivingBase, var17, par9);
                        this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    }
                    if ((var18 & 15) == 15) {
                        var19 = (float)par1EntityLivingBase.ticksExisted + par9;
                        this.bindTexture(RES_ITEM_GLINT);
                        GL11.glEnable((int)3042);
                        var20 = 0.5f;
                        GL11.glColor4f((float)var20, (float)var20, (float)var20, (float)1.0f);
                        GL11.glDepthFunc((int)514);
                        GL11.glDepthMask((boolean)false);
                        int var21 = 0;
                        while (var21 < 2) {
                            GL11.glDisable((int)2896);
                            var22 = 0.76f;
                            GL11.glColor4f((float)(0.5f * var22), (float)(0.25f * var22), (float)(0.8f * var22), (float)1.0f);
                            GL11.glBlendFunc((int)768, (int)1);
                            GL11.glMatrixMode((int)5890);
                            GL11.glLoadIdentity();
                            float var23 = var19 * (0.001f + (float)var21 * 0.003f) * 20.0f;
                            float var24 = 0.33333334f;
                            GL11.glScalef((float)var24, (float)var24, (float)var24);
                            GL11.glRotatef((float)(30.0f - (float)var21 * 60.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                            GL11.glTranslatef((float)0.0f, (float)var23, (float)0.0f);
                            GL11.glMatrixMode((int)5888);
                            this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                            ++var21;
                        }
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        GL11.glMatrixMode((int)5890);
                        GL11.glDepthMask((boolean)true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode((int)5888);
                        GL11.glEnable((int)2896);
                        GL11.glDisable((int)3042);
                        GL11.glDepthFunc((int)515);
                    }
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)3008);
                }
                ++var17;
            }
            GL11.glDepthMask((boolean)true);
            this.renderEquippedItems(par1EntityLivingBase, par9);
            float var27 = par1EntityLivingBase.getBrightness(par9);
            var18 = this.getColorMultiplier(par1EntityLivingBase, var27, par9);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable((int)3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            if ((var18 >> 24 & 255) > 0 || par1EntityLivingBase.hurtTime > 0 || par1EntityLivingBase.deathTime > 0) {
                GL11.glDisable((int)3553);
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glDepthFunc((int)514);
                if (par1EntityLivingBase.hurtTime > 0 || par1EntityLivingBase.deathTime > 0) {
                    GL11.glColor4f((float)var27, (float)0.0f, (float)0.0f, (float)0.4f);
                    this.mainModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    int var28 = 0;
                    while (var28 < 4) {
                        if (this.inheritRenderPass(par1EntityLivingBase, var28, par9) >= 0) {
                            GL11.glColor4f((float)var27, (float)0.0f, (float)0.0f, (float)0.4f);
                            this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                        }
                        ++var28;
                    }
                }
                if ((var18 >> 24 & 255) > 0) {
                    var19 = (float)(var18 >> 16 & 255) / 255.0f;
                    var20 = (float)(var18 >> 8 & 255) / 255.0f;
                    float var30 = (float)(var18 & 255) / 255.0f;
                    var22 = (float)(var18 >> 24 & 255) / 255.0f;
                    GL11.glColor4f((float)var19, (float)var20, (float)var30, (float)var22);
                    this.mainModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    int var29 = 0;
                    while (var29 < 4) {
                        if (this.inheritRenderPass(par1EntityLivingBase, var29, par9) >= 0) {
                            GL11.glColor4f((float)var19, (float)var20, (float)var30, (float)var22);
                            this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                        }
                        ++var29;
                    }
                }
                GL11.glDepthFunc((int)515);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)3553);
            }
            GL11.glDisable((int)32826);
        }
        catch (Exception var25) {
            logger.error("Couldn't render entity", (Throwable)var25);
        }
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable((int)3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable((int)2884);
        GL11.glPopMatrix();
        this.passSpecialRender(par1EntityLivingBase, par2, par4, par6);
    }

    protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.bindEntityTexture(par1EntityLivingBase);
        if (!par1EntityLivingBase.isInvisible()) {
            this.mainModel.render(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
        } else if (!par1EntityLivingBase.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
            GL11.glPushMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.15f);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glAlphaFunc((int)516, (float)0.003921569f);
            this.mainModel.render(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
            GL11.glDisable((int)3042);
            GL11.glAlphaFunc((int)516, (float)0.1f);
            GL11.glPopMatrix();
            GL11.glDepthMask((boolean)true);
        } else {
            this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityLivingBase);
        }
    }

    protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
    }

    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        GL11.glRotatef((float)(180.0f - par3), (float)0.0f, (float)1.0f, (float)0.0f);
        if (par1EntityLivingBase.deathTime > 0) {
            float var5 = ((float)par1EntityLivingBase.deathTime + par4 - 1.0f) / 20.0f * 1.6f;
            if ((var5 = MathHelper.sqrt_float(var5)) > 1.0f) {
                var5 = 1.0f;
            }
            GL11.glRotatef((float)(var5 * this.getDeathMaxRotation(par1EntityLivingBase)), (float)0.0f, (float)0.0f, (float)1.0f);
        } else {
            String var6 = EnumChatFormatting.getTextWithoutFormattingCodes(par1EntityLivingBase.getCommandSenderName());
            if (!(!var6.equals("Dinnerbone") && !var6.equals("Grumm") || par1EntityLivingBase instanceof EntityPlayer && ((EntityPlayer)par1EntityLivingBase).getHideCape())) {
                GL11.glTranslatef((float)0.0f, (float)(par1EntityLivingBase.height + 0.1f), (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
        }
    }

    protected float renderSwingProgress(EntityLivingBase par1EntityLivingBase, float par2) {
        return par1EntityLivingBase.getSwingProgress(par2);
    }

    protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
        return (float)par1EntityLivingBase.ticksExisted + par2;
    }

    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
    }

    protected void renderArrowsStuckInEntity(EntityLivingBase par1EntityLivingBase, float par2) {
        int var3 = par1EntityLivingBase.getArrowCountInEntity();
        if (var3 > 0) {
            EntityArrow var4 = new EntityArrow(par1EntityLivingBase.worldObj, par1EntityLivingBase.posX, par1EntityLivingBase.posY, par1EntityLivingBase.posZ);
            Random var5 = new Random(par1EntityLivingBase.getEntityId());
            RenderHelper.disableStandardItemLighting();
            int var6 = 0;
            while (var6 < var3) {
                GL11.glPushMatrix();
                ModelRenderer var7 = this.mainModel.getRandomModelBox(var5);
                ModelBox var8 = (ModelBox)var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
                var7.postRender(0.0625f);
                float var9 = var5.nextFloat();
                float var10 = var5.nextFloat();
                float var11 = var5.nextFloat();
                float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0f;
                float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0f;
                float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0f;
                GL11.glTranslatef((float)var12, (float)var13, (float)var14);
                var9 = var9 * 2.0f - 1.0f;
                var10 = var10 * 2.0f - 1.0f;
                var11 = var11 * 2.0f - 1.0f;
                float var15 = MathHelper.sqrt_float(var9 * (var9 *= -1.0f) + var11 * (var11 *= -1.0f));
                var4.prevRotationYaw = var4.rotationYaw = (float)(Math.atan2(var9, var11) * 180.0 / 3.141592653589793);
                var4.prevRotationPitch = var4.rotationPitch = (float)(Math.atan2(var10 *= -1.0f, var15) * 180.0 / 3.141592653589793);
                double var16 = 0.0;
                double var18 = 0.0;
                double var20 = 0.0;
                float var22 = 0.0f;
                this.renderManager.func_147940_a(var4, var16, var18, var20, var22, par2);
                GL11.glPopMatrix();
                ++var6;
            }
            RenderHelper.enableStandardItemLighting();
        }
    }

    protected int inheritRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass(par1EntityLivingBase, par2, par3);
    }

    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return -1;
    }

    protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
    }

    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase) {
        return 90.0f;
    }

    protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3) {
        return 0;
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
    }

    protected void passSpecialRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
        GL11.glAlphaFunc((int)516, (float)0.1f);
        if (this.func_110813_b(par1EntityLivingBase)) {
            float var12;
            float var8 = 1.6f;
            float var9 = 0.016666668f * var8;
            double var10 = par1EntityLivingBase.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float f = var12 = par1EntityLivingBase.isSneaking() ? 32.0f : 64.0f;
            if (var10 < (double)(var12 * var12)) {
                String var13 = par1EntityLivingBase.func_145748_c_().getFormattedText();
                if (par1EntityLivingBase.isSneaking()) {
                    FontRenderer var14 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)((float)par2 + 0.0f), (float)((float)par4 + par1EntityLivingBase.height + 0.5f), (float)((float)par6));
                    GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)(- this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)this.renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glScalef((float)(- var9), (float)(- var9), (float)var9);
                    GL11.glDisable((int)2896);
                    GL11.glTranslatef((float)0.0f, (float)(0.25f / var9), (float)0.0f);
                    GL11.glDepthMask((boolean)false);
                    GL11.glEnable((int)3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    Tessellator var15 = Tessellator.instance;
                    GL11.glDisable((int)3553);
                    var15.startDrawingQuads();
                    int var16 = var14.getStringWidth(var13) / 2;
                    var15.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                    var15.addVertex(- var16 - 1, -1.0, 0.0);
                    var15.addVertex(- var16 - 1, 8.0, 0.0);
                    var15.addVertex(var16 + 1, 8.0, 0.0);
                    var15.addVertex(var16 + 1, -1.0, 0.0);
                    var15.draw();
                    GL11.glEnable((int)3553);
                    GL11.glDepthMask((boolean)true);
                    var14.drawString(var13, (- var14.getStringWidth(var13)) / 2, 0.0f, 553648127);
                    GL11.glEnable((int)2896);
                    GL11.glDisable((int)3042);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glPopMatrix();
                } else {
                    this.func_96449_a(par1EntityLivingBase, par2, par4, par6, var13, var9, var10);
                }
            }
        }
    }

    protected boolean func_110813_b(EntityLivingBase par1EntityLivingBase) {
        if (Minecraft.isGuiEnabled() && par1EntityLivingBase != this.renderManager.livingPlayer && !par1EntityLivingBase.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && par1EntityLivingBase.riddenByEntity == null) {
            return true;
        }
        return false;
    }

    protected void func_96449_a(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, String par8Str, float par9, double par10) {
        if (par1EntityLivingBase.isPlayerSleeping()) {
            this.func_147906_a(par1EntityLivingBase, par8Str, par2, par4 - 1.5, par6, 64);
        } else {
            this.func_147906_a(par1EntityLivingBase, par8Str, par2, par4, par6, 64);
        }
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityLivingBase)par1Entity, par2, par4, par6, par8, par9);
    }
}

