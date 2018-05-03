/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.relations.EnemyManager;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.StringValue;
import java.text.DecimalFormat;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public abstract class Render {
    private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    protected RenderManager renderManager;
    protected RenderBlocks field_147909_c = new RenderBlocks();
    protected float shadowSize;
    protected float shadowOpaque = 1.0f;
    private boolean field_147908_f = false;
    private static final String __OBFID = "CL_00000992";

    public abstract void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9);

    protected abstract ResourceLocation getEntityTexture(Entity var1);

    public boolean func_147905_a() {
        return this.field_147908_f;
    }

    protected void bindEntityTexture(Entity par1Entity) {
        this.bindTexture(this.getEntityTexture(par1Entity));
    }

    protected void bindTexture(ResourceLocation par1ResourceLocation) {
        this.renderManager.renderEngine.bindTexture(par1ResourceLocation);
    }

    private void renderEntityOnFire(Entity par1Entity, double par2, double par4, double par6, float par8) {
        GL11.glDisable((int)2896);
        IIcon var9 = Blocks.fire.func_149840_c(0);
        IIcon var10 = Blocks.fire.func_149840_c(1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        float var11 = par1Entity.width * 1.4f;
        GL11.glScalef((float)var11, (float)var11, (float)var11);
        Tessellator var12 = Tessellator.instance;
        float var13 = 0.5f;
        float var14 = 0.0f;
        float var15 = par1Entity.height / var11;
        float var16 = (float)(par1Entity.posY - par1Entity.boundingBox.minY);
        GL11.glRotatef((float)(- this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(-0.3f + (float)((int)var15) * 0.02f));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var17 = 0.0f;
        int var18 = 0;
        var12.startDrawingQuads();
        while (var15 > 0.0f) {
            IIcon var19 = var18 % 2 == 0 ? var9 : var10;
            this.bindTexture(TextureMap.locationBlocksTexture);
            float var20 = var19.getMinU();
            float var21 = var19.getMinV();
            float var22 = var19.getMaxU();
            float var23 = var19.getMaxV();
            if (var18 / 2 % 2 == 0) {
                float var24 = var22;
                var22 = var20;
                var20 = var24;
            }
            var12.addVertexWithUV(var13 - var14, 0.0f - var16, var17, var22, var23);
            var12.addVertexWithUV(- var13 - var14, 0.0f - var16, var17, var20, var23);
            var12.addVertexWithUV(- var13 - var14, 1.4f - var16, var17, var20, var21);
            var12.addVertexWithUV(var13 - var14, 1.4f - var16, var17, var22, var21);
            var15 -= 0.45f;
            var16 -= 0.45f;
            var13 *= 0.9f;
            var17 += 0.03f;
            ++var18;
        }
        var12.draw();
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
    }

    private void renderShadow(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        this.renderManager.renderEngine.bindTexture(shadowTextures);
        World var10 = this.getWorldFromRenderManager();
        GL11.glDepthMask((boolean)false);
        float var11 = this.shadowSize;
        if (par1Entity instanceof EntityLiving) {
            EntityLiving var12 = (EntityLiving)par1Entity;
            var11 *= var12.getRenderSizeModifier();
            if (var12.isChild()) {
                var11 *= 0.5f;
            }
        }
        double var35 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * (double)par9;
        double var14 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * (double)par9 + (double)par1Entity.getShadowSize();
        double var16 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * (double)par9;
        int var18 = MathHelper.floor_double(var35 - (double)var11);
        int var19 = MathHelper.floor_double(var35 + (double)var11);
        int var20 = MathHelper.floor_double(var14 - (double)var11);
        int var21 = MathHelper.floor_double(var14);
        int var22 = MathHelper.floor_double(var16 - (double)var11);
        int var23 = MathHelper.floor_double(var16 + (double)var11);
        double var24 = par2 - var35;
        double var26 = par4 - var14;
        double var28 = par6 - var16;
        Tessellator var30 = Tessellator.instance;
        var30.startDrawingQuads();
        int var31 = var18;
        while (var31 <= var19) {
            int var32 = var20;
            while (var32 <= var21) {
                int var33 = var22;
                while (var33 <= var23) {
                    Block var34 = var10.getBlock(var31, var32 - 1, var33);
                    if (var34.getMaterial() != Material.air && var10.getBlockLightValue(var31, var32, var33) > 3) {
                        this.func_147907_a(var34, par2, par4 + (double)par1Entity.getShadowSize(), par6, var31, var32, var33, par8, var11, var24, var26 + (double)par1Entity.getShadowSize(), var28);
                    }
                    ++var33;
                }
                ++var32;
            }
            ++var31;
        }
        var30.draw();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glDepthMask((boolean)true);
    }

    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }

    private void func_147907_a(Block p_147907_1_, double p_147907_2_, double p_147907_4_, double p_147907_6_, int p_147907_8_, int p_147907_9_, int p_147907_10_, float p_147907_11_, float p_147907_12_, double p_147907_13_, double p_147907_15_, double p_147907_17_) {
        double var20;
        Tessellator var19 = Tessellator.instance;
        if (p_147907_1_.renderAsNormalBlock() && (var20 = ((double)p_147907_11_ - (p_147907_4_ - ((double)p_147907_9_ + p_147907_15_)) / 2.0) * 0.5 * (double)this.getWorldFromRenderManager().getLightBrightness(p_147907_8_, p_147907_9_, p_147907_10_)) >= 0.0) {
            if (var20 > 1.0) {
                var20 = 1.0;
            }
            var19.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float)var20);
            double var22 = (double)p_147907_8_ + p_147907_1_.getBlockBoundsMinX() + p_147907_13_;
            double var24 = (double)p_147907_8_ + p_147907_1_.getBlockBoundsMaxX() + p_147907_13_;
            double var26 = (double)p_147907_9_ + p_147907_1_.getBlockBoundsMinY() + p_147907_15_ + 0.015625;
            double var28 = (double)p_147907_10_ + p_147907_1_.getBlockBoundsMinZ() + p_147907_17_;
            double var30 = (double)p_147907_10_ + p_147907_1_.getBlockBoundsMaxZ() + p_147907_17_;
            float var32 = (float)((p_147907_2_ - var22) / 2.0 / (double)p_147907_12_ + 0.5);
            float var33 = (float)((p_147907_2_ - var24) / 2.0 / (double)p_147907_12_ + 0.5);
            float var34 = (float)((p_147907_6_ - var28) / 2.0 / (double)p_147907_12_ + 0.5);
            float var35 = (float)((p_147907_6_ - var30) / 2.0 / (double)p_147907_12_ + 0.5);
            var19.addVertexWithUV(var22, var26, var28, var32, var34);
            var19.addVertexWithUV(var22, var26, var30, var32, var35);
            var19.addVertexWithUV(var24, var26, var30, var33, var35);
            var19.addVertexWithUV(var24, var26, var28, var33, var34);
        }
    }

    public static void renderOffsetAABB(AxisAlignedBB par0AxisAlignedBB, double par1, double par3, double par5) {
        GL11.glDisable((int)3553);
        Tessellator var7 = Tessellator.instance;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var7.startDrawingQuads();
        var7.setTranslation(par1, par3, par5);
        var7.setNormal(0.0f, 0.0f, -1.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(0.0f, 0.0f, 1.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0f, -1.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0f, 1.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.setNormal(-1.0f, 0.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(1.0f, 0.0f, 0.0f);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setTranslation(0.0, 0.0, 0.0);
        var7.draw();
        GL11.glEnable((int)3553);
    }

    public static void renderAABB(AxisAlignedBB par0AxisAlignedBB) {
        Tessellator var1 = Tessellator.instance;
        var1.startDrawingQuads();
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.draw();
    }

    public void setRenderManager(RenderManager par1RenderManager) {
        this.renderManager = par1RenderManager;
    }

    public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        float var12;
        double var10;
        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0f && !par1Entity.isInvisible() && (var12 = (float)((1.0 - (var10 = this.renderManager.getDistanceToCamera(par1Entity.posX, par1Entity.posY, par1Entity.posZ)) / 256.0) * (double)this.shadowOpaque)) > 0.0f) {
            this.renderShadow(par1Entity, par2, par4, par6, var12, par9);
        }
        if (par1Entity.canRenderOnFire()) {
            this.renderEntityOnFire(par1Entity, par2, par4, par6, par9);
        }
    }

    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }

    public void updateIcons(IIconRegister par1IconRegister) {
    }

    protected void func_147906_a(EntityLivingBase par1EntityLivingBase, String par2Str, double par3, double par5, double par7, int par9) {
        boolean names = Resilience.getInstance().getValues().namesEnabled;
        boolean isFriend = FriendManager.isFriend(par2Str);
        boolean isEnemy = EnemyManager.isEnemy(par2Str);
        for (Friend friend : Friend.friendList) {
            par2Str = par2Str.replaceAll("(?i)" + friend.getName(), friend.getAlias());
        }
        if (Resilience.getInstance().getValues().nameProtectEnabled) {
            par2Str = par2Str.replaceAll("(?i)" + Minecraft.getMinecraft().session.getUsername(), Resilience.getInstance().getValues().nameProtectAlias.getValue());
        }
        double var10 = par1EntityLivingBase.getDistanceSqToEntity(this.renderManager.livingPlayer);
        Minecraft.getMinecraft();
        double var100 = par1EntityLivingBase.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
        if (names) {
            par2Str = String.valueOf(par2Str) + " \u00a78[\u00a7a" + Math.round(var100) + "\u00a78] \u00a78[\u00a7c" + new DecimalFormat("#.#").format(par1EntityLivingBase.getHealth() / 2.0f) + "\u00a78]";
        }
        if (var10 <= (names ? 90000.0 : (double)(par9 * par9))) {
            FontRenderer var12 = this.getFontRendererFromRenderManager();
            float var13 = 1.6f;
            float var14 = names ? 0.016666668f * var13 * 2.0f : 0.016666668f * var13;
            GL11.glPushMatrix();
            if (names) {
                Minecraft.getMinecraft().entityRenderer.disableLightmap(1.0);
            }
            GL11.glTranslatef((float)((float)par3 + 0.0f), (float)((float)par5 + par1EntityLivingBase.height + 0.5f), (float)((float)par7));
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(- this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)(- var14), (float)(- var14), (float)var14);
            GL11.glDisable((int)2896);
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            Tessellator var15 = Tessellator.instance;
            int var16 = names ? -10 : 0;
            if (par2Str.equals("deadmau5")) {
                var16 = -10;
            }
            int var17 = var12.getStringWidth(par2Str) / 2;
            GL11.glDisable((int)2896);
            if (names) {
                Utils.drawBetterRect(- var17 - 3, var16 - 3, 3 + var17, var16 + 10, par1EntityLivingBase.isSneaking() ? 1440758833 : 1428563494, isFriend ? 1427076325 : (isEnemy ? 1439432704 : 1437248170));
            }
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            var15.startDrawingQuads();
            var15.setColorRGBA_F(0.0f, names ? 0.0f : 0.0f, names ? 0.0f : 0.0f, names ? 0.25f : 0.25f);
            var15.addVertex(- var17 - 1, -1 + var16, 0.0);
            var15.addVertex(- var17 - 1, 8 + var16, 0.0);
            var15.addVertex(var17 + 1, 8 + var16, 0.0);
            var15.addVertex(var17 + 1, -1 + var16, 0.0);
            var15.draw();
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3553);
            var12.drawString(par2Str, (- var12.getStringWidth(par2Str)) / 2, var16, names ? -4276546 : -1);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            var12.drawString(par2Str, (- var12.getStringWidth(par2Str)) / 2, var16, names ? -4276546 : -1);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
            Minecraft.getMinecraft().entityRenderer.enableLightmap(1.0);
        }
    }
}

