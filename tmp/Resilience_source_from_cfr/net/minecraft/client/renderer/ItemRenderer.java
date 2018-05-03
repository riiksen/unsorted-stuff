/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.module.values.Values;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    private Minecraft mc;
    private ItemStack itemToRender;
    private float equippedProgress;
    private float prevEquippedProgress;
    private RenderBlocks renderBlocksIr = new RenderBlocks();
    private int equippedItemSlot = -1;
    private static final String __OBFID = "CL_00000953";

    public ItemRenderer(Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
    }

    public void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3) {
        GL11.glPushMatrix();
        TextureManager var4 = this.mc.getTextureManager();
        Item var5 = par2ItemStack.getItem();
        Block var6 = Block.getBlockFromItem(var5);
        if (par2ItemStack.getItemSpriteNumber() == 0 && var5 instanceof ItemBlock && RenderBlocks.renderItemIn3d(var6.getRenderType())) {
            var4.bindTexture(var4.getResourceLocation(0));
            if (par2ItemStack != null && par2ItemStack.getItem() instanceof ItemCloth) {
                GL11.glEnable((int)3042);
                GL11.glDepthMask((boolean)false);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                this.renderBlocksIr.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0f);
                GL11.glDepthMask((boolean)true);
                GL11.glDisable((int)3042);
            } else {
                this.renderBlocksIr.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0f);
            }
        } else {
            IIcon var7 = par1EntityLivingBase.getItemIcon(par2ItemStack, par3);
            if (var7 == null) {
                GL11.glPopMatrix();
                return;
            }
            var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
            TextureUtil.func_147950_a(false, false);
            Tessellator var8 = Tessellator.instance;
            float var9 = var7.getMinU();
            float var10 = var7.getMaxU();
            float var11 = var7.getMinV();
            float var12 = var7.getMaxV();
            float var13 = 0.0f;
            float var14 = 0.3f;
            GL11.glEnable((int)32826);
            GL11.glTranslatef((float)(- var13), (float)(- var14), (float)0.0f);
            float var15 = 1.5f;
            GL11.glScalef((float)var15, (float)var15, (float)var15);
            GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)-0.9375f, (float)-0.0625f, (float)0.0f);
            ItemRenderer.renderItemIn2D(var8, var10, var11, var9, var12, var7.getIconWidth(), var7.getIconHeight(), 0.0625f);
            if (par2ItemStack.hasEffect() && par3 == 0) {
                GL11.glDepthFunc((int)514);
                GL11.glDisable((int)2896);
                var4.bindTexture(RES_ITEM_GLINT);
                GL11.glEnable((int)3042);
                OpenGlHelper.glBlendFunc(768, 1, 1, 0);
                float var16 = 0.76f;
                GL11.glColor4f((float)(0.5f * var16), (float)(0.25f * var16), (float)(0.8f * var16), (float)1.0f);
                GL11.glMatrixMode((int)5890);
                GL11.glPushMatrix();
                float var17 = 0.125f;
                GL11.glScalef((float)var17, (float)var17, (float)var17);
                float var18 = (float)(Minecraft.getSystemTime() % 3000) / 3000.0f * 8.0f;
                GL11.glTranslatef((float)var18, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef((float)var17, (float)var17, (float)var17);
                var18 = (float)(Minecraft.getSystemTime() % 4873) / 4873.0f * 8.0f;
                GL11.glTranslatef((float)(- var18), (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
                GL11.glPopMatrix();
                GL11.glMatrixMode((int)5888);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2896);
                GL11.glDepthFunc((int)515);
            }
            GL11.glDisable((int)32826);
            var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
            TextureUtil.func_147945_b();
        }
        GL11.glPopMatrix();
    }

    public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, int par5, int par6, float par7) {
        float var11;
        float var12;
        float var13;
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 0.0f, 1.0f);
        par0Tessellator.addVertexWithUV(0.0, 0.0, 0.0, par1, par4);
        par0Tessellator.addVertexWithUV(1.0, 0.0, 0.0, par3, par4);
        par0Tessellator.addVertexWithUV(1.0, 1.0, 0.0, par3, par2);
        par0Tessellator.addVertexWithUV(0.0, 1.0, 0.0, par1, par2);
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 0.0f, -1.0f);
        par0Tessellator.addVertexWithUV(0.0, 1.0, 0.0f - par7, par1, par2);
        par0Tessellator.addVertexWithUV(1.0, 1.0, 0.0f - par7, par3, par2);
        par0Tessellator.addVertexWithUV(1.0, 0.0, 0.0f - par7, par3, par4);
        par0Tessellator.addVertexWithUV(0.0, 0.0, 0.0f - par7, par1, par4);
        par0Tessellator.draw();
        float var8 = 0.5f * (par1 - par3) / (float)par5;
        float var9 = 0.5f * (par4 - par2) / (float)par6;
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        int var10 = 0;
        while (var10 < par5) {
            var11 = (float)var10 / (float)par5;
            var12 = par1 + (par3 - par1) * var11 - var8;
            par0Tessellator.addVertexWithUV(var11, 0.0, 0.0f - par7, var12, par4);
            par0Tessellator.addVertexWithUV(var11, 0.0, 0.0, var12, par4);
            par0Tessellator.addVertexWithUV(var11, 1.0, 0.0, var12, par2);
            par0Tessellator.addVertexWithUV(var11, 1.0, 0.0f - par7, var12, par2);
            ++var10;
        }
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(1.0f, 0.0f, 0.0f);
        var10 = 0;
        while (var10 < par5) {
            var11 = (float)var10 / (float)par5;
            var12 = par1 + (par3 - par1) * var11 - var8;
            var13 = var11 + 1.0f / (float)par5;
            par0Tessellator.addVertexWithUV(var13, 1.0, 0.0f - par7, var12, par2);
            par0Tessellator.addVertexWithUV(var13, 1.0, 0.0, var12, par2);
            par0Tessellator.addVertexWithUV(var13, 0.0, 0.0, var12, par4);
            par0Tessellator.addVertexWithUV(var13, 0.0, 0.0f - par7, var12, par4);
            ++var10;
        }
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 1.0f, 0.0f);
        var10 = 0;
        while (var10 < par6) {
            var11 = (float)var10 / (float)par6;
            var12 = par4 + (par2 - par4) * var11 - var9;
            var13 = var11 + 1.0f / (float)par6;
            par0Tessellator.addVertexWithUV(0.0, var13, 0.0, par1, var12);
            par0Tessellator.addVertexWithUV(1.0, var13, 0.0, par3, var12);
            par0Tessellator.addVertexWithUV(1.0, var13, 0.0f - par7, par3, var12);
            par0Tessellator.addVertexWithUV(0.0, var13, 0.0f - par7, par1, var12);
            ++var10;
        }
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, -1.0f, 0.0f);
        var10 = 0;
        while (var10 < par6) {
            var11 = (float)var10 / (float)par6;
            var12 = par4 + (par2 - par4) * var11 - var9;
            par0Tessellator.addVertexWithUV(1.0, var11, 0.0, par3, var12);
            par0Tessellator.addVertexWithUV(0.0, var11, 0.0, par1, var12);
            par0Tessellator.addVertexWithUV(0.0, var11, 0.0f - par7, par1, var12);
            par0Tessellator.addVertexWithUV(1.0, var11, 0.0f - par7, par3, var12);
            ++var10;
        }
        par0Tessellator.draw();
    }

    public void renderItemInFirstPerson(float par1) {
        float var15;
        float var13;
        float var14;
        float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * par1;
        HookEntityClientPlayerMP var3 = this.mc.thePlayer;
        float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
        GL11.glPushMatrix();
        GL11.glRotatef((float)var4, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1), (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        HookEntityClientPlayerMP var5 = var3;
        float var6 = var5.prevRenderArmPitch + (var5.renderArmPitch - var5.prevRenderArmPitch) * par1;
        float var7 = var5.prevRenderArmYaw + (var5.renderArmYaw - var5.prevRenderArmYaw) * par1;
        GL11.glRotatef((float)((var3.rotationPitch - var6) * 0.1f), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)((var3.rotationYaw - var7) * 0.1f), (float)0.0f, (float)1.0f, (float)0.0f);
        ItemStack var8 = this.itemToRender;
        if (var8 != null && var8.getItem() instanceof ItemCloth) {
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        }
        int var9 = this.mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
        int var10 = var9 % 65536;
        int var11 = var9 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var10 / 1.0f, (float)var11 / 1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (var8 != null) {
            int var12 = var8.getItem().getColorFromItemStack(var8, 0);
            var13 = (float)(var12 >> 16 & 255) / 255.0f;
            var14 = (float)(var12 >> 8 & 255) / 255.0f;
            var15 = (float)(var12 & 255) / 255.0f;
            GL11.glColor4f((float)var13, (float)var14, (float)var15, (float)1.0f);
        } else {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        if (var8 != null && var8.getItem() == Items.filled_map) {
            float var18;
            GL11.glPushMatrix();
            float var22 = 0.8f;
            var13 = var3.getSwingProgress(par1);
            var14 = MathHelper.sin(var13 * 3.1415927f);
            var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glTranslatef((float)((- var15) * 0.4f), (float)(MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f * 2.0f) * 0.2f), (float)((- var14) * 0.2f));
            var13 = 1.0f - var4 / 45.0f + 0.1f;
            if (var13 < 0.0f) {
                var13 = 0.0f;
            }
            if (var13 > 1.0f) {
                var13 = 1.0f;
            }
            var13 = (- MathHelper.cos(var13 * 3.1415927f)) * 0.5f + 0.5f;
            GL11.glTranslatef((float)0.0f, (float)(0.0f * var22 - (1.0f - var2) * 1.2f - var13 * 0.5f + 0.04f), (float)(-0.9f * var22));
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(var13 * -85.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glEnable((int)32826);
            this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
            int var24 = 0;
            while (var24 < 2) {
                int var28 = var24 * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef((float)-0.0f, (float)-0.6f, (float)(1.1f * (float)var28));
                GL11.glRotatef((float)(-45 * var28), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)59.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)(-65 * var28), (float)0.0f, (float)1.0f, (float)0.0f);
                Render var26 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
                RenderPlayer var25 = (RenderPlayer)var26;
                var18 = 1.0f;
                GL11.glScalef((float)var18, (float)var18, (float)var18);
                var25.renderFirstPersonArm(this.mc.thePlayer);
                GL11.glPopMatrix();
                ++var24;
            }
            var14 = var3.getSwingProgress(par1);
            var15 = MathHelper.sin(var14 * var14 * 3.1415927f);
            float var16 = MathHelper.sin(MathHelper.sqrt_float(var14) * 3.1415927f);
            GL11.glRotatef((float)((- var15) * 20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)((- var16) * 20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)((- var16) * 80.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            float var17 = 0.38f;
            GL11.glScalef((float)var17, (float)var17, (float)var17);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)-1.0f, (float)-1.0f, (float)0.0f);
            var18 = 0.015625f;
            GL11.glScalef((float)var18, (float)var18, (float)var18);
            this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
            Tessellator var31 = Tessellator.instance;
            GL11.glNormal3f((float)0.0f, (float)0.0f, (float)-1.0f);
            var31.startDrawingQuads();
            int var30 = 7;
            var31.addVertexWithUV(0 - var30, 128 + var30, 0.0, 0.0, 1.0);
            var31.addVertexWithUV(128 + var30, 128 + var30, 0.0, 1.0, 1.0);
            var31.addVertexWithUV(128 + var30, 0 - var30, 0.0, 1.0, 0.0);
            var31.addVertexWithUV(0 - var30, 0 - var30, 0.0, 0.0, 0.0);
            var31.draw();
            MapData var21 = Items.filled_map.getMapData(var8, this.mc.theWorld);
            if (var21 != null) {
                this.mc.entityRenderer.getMapItemRenderer().func_148250_a(var21, false);
            }
            GL11.glPopMatrix();
        } else if (var8 != null) {
            float var16;
            float var18;
            float var20;
            GL11.glPushMatrix();
            float var22 = 0.8f;
            if (var3.getItemInUseCount() > 0) {
                EnumAction var23 = var8.getItemUseAction();
                if (var23 == EnumAction.eat || var23 == EnumAction.drink) {
                    var14 = (float)var3.getItemInUseCount() - par1 + 1.0f;
                    var15 = 1.0f - var14 / (float)var8.getMaxItemUseDuration();
                    var16 = 1.0f - var15;
                    var16 = var16 * var16 * var16;
                    var16 = var16 * var16 * var16;
                    var16 = var16 * var16 * var16;
                    float var17 = 1.0f - var16;
                    GL11.glTranslatef((float)0.0f, (float)(MathHelper.abs(MathHelper.cos(var14 / 4.0f * 3.1415927f) * 0.1f) * (float)((double)var15 > 0.2) ? 1 : 0), (float)0.0f);
                    GL11.glTranslatef((float)(var17 * 0.6f), (float)((- var17) * 0.5f), (float)0.0f);
                    GL11.glRotatef((float)(var17 * 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)(var17 * 10.0f), (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glRotatef((float)(var17 * 30.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
            } else {
                var13 = var3.getSwingProgress(par1);
                var14 = MathHelper.sin(var13 * 3.1415927f);
                var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
                GL11.glTranslatef((float)((- var15) * 0.4f), (float)(MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f * 2.0f) * 0.2f), (float)((- var14) * 0.2f));
            }
            GL11.glTranslatef((float)(0.7f * var22), (float)(-0.65f * var22 - (1.0f - var2) * 0.6f), (float)(-0.9f * var22));
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)32826);
            var13 = var3.getSwingProgress(par1);
            var14 = MathHelper.sin(var13 * var13 * 3.1415927f);
            var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glRotatef((float)((- var14) * 20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)((- var15) * 20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)((- var15) * 80.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            var16 = 0.4f;
            GL11.glScalef((float)var16, (float)var16, (float)var16);
            if (var3.getItemInUseCount() > 0) {
                EnumAction var29 = var8.getItemUseAction();
                if (var29 == EnumAction.block) {
                    GL11.glTranslatef((float)-0.5f, (float)0.2f, (float)0.0f);
                    GL11.glRotatef((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)-80.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glRotatef((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                } else if (var29 == EnumAction.bow) {
                    GL11.glRotatef((float)-18.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glRotatef((float)-12.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)-8.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glTranslatef((float)-0.9f, (float)0.2f, (float)0.0f);
                    var18 = (float)var8.getMaxItemUseDuration() - ((float)var3.getItemInUseCount() - par1 + 1.0f);
                    float var19 = var18 / 20.0f;
                    var19 = (var19 * var19 + var19 * 2.0f) / 3.0f;
                    if (var19 > 1.0f) {
                        var19 = 1.0f;
                    }
                    if (var19 > 0.1f) {
                        GL11.glTranslatef((float)0.0f, (float)(MathHelper.sin((var18 - 0.1f) * 1.3f) * 0.01f * (var19 - 0.1f)), (float)0.0f);
                    }
                    GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(var19 * 0.1f));
                    GL11.glRotatef((float)-335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glRotatef((float)-50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glTranslatef((float)0.0f, (float)0.5f, (float)0.0f);
                    var20 = 1.0f + var19 * 0.2f;
                    GL11.glScalef((float)1.0f, (float)1.0f, (float)var20);
                    GL11.glTranslatef((float)0.0f, (float)-0.5f, (float)0.0f);
                    GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                }
            }
            if (var8.getItem().shouldRotateAroundWhenRendering()) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (var8.getItem().requiresMultipleRenderPasses()) {
                this.renderItem(var3, var8, 0);
                int var27 = var8.getItem().getColorFromItemStack(var8, 1);
                var18 = (float)(var27 >> 16 & 255) / 255.0f;
                float var19 = (float)(var27 >> 8 & 255) / 255.0f;
                var20 = (float)(var27 & 255) / 255.0f;
                GL11.glColor4f((float)(1.0f * var18), (float)(1.0f * var19), (float)(1.0f * var20), (float)1.0f);
                this.renderItem(var3, var8, 1);
            } else {
                this.renderItem(var3, var8, 0);
            }
            GL11.glPopMatrix();
        } else if (!var3.isInvisible()) {
            GL11.glPushMatrix();
            float var22 = 0.8f;
            var13 = var3.getSwingProgress(par1);
            var14 = MathHelper.sin(var13 * 3.1415927f);
            var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glTranslatef((float)((- var15) * 0.3f), (float)(MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f * 2.0f) * 0.4f), (float)((- var14) * 0.4f));
            GL11.glTranslatef((float)(0.8f * var22), (float)(-0.75f * var22 - (1.0f - var2) * 0.6f), (float)(-0.9f * var22));
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)32826);
            var13 = var3.getSwingProgress(par1);
            var14 = MathHelper.sin(var13 * var13 * 3.1415927f);
            var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glRotatef((float)(var15 * 70.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)((- var14) * 20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
            GL11.glTranslatef((float)-1.0f, (float)3.6f, (float)3.5f);
            GL11.glRotatef((float)120.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)200.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glTranslatef((float)5.6f, (float)0.0f, (float)0.0f);
            Render var26 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            RenderPlayer var25 = (RenderPlayer)var26;
            float var18 = 1.0f;
            GL11.glScalef((float)var18, (float)var18, (float)var18);
            var25.renderFirstPersonArm(this.mc.thePlayer);
            GL11.glPopMatrix();
        }
        if (var8 != null && var8.getItem() instanceof ItemCloth) {
            GL11.glDisable((int)3042);
        }
        GL11.glDisable((int)32826);
        RenderHelper.disableStandardItemLighting();
    }

    public void renderOverlays(float par1) {
        GL11.glDisable((int)3008);
        if (this.mc.thePlayer.isBurning() && !Resilience.getInstance().getValues().noFireEffectEnabled) {
            this.renderFireInFirstPerson(par1);
        }
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            int var2 = MathHelper.floor_double(this.mc.thePlayer.posX);
            int var3 = MathHelper.floor_double(this.mc.thePlayer.posY);
            int var4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            Block var5 = this.mc.theWorld.getBlock(var2, var3, var4);
            if (this.mc.theWorld.getBlock(var2, var3, var4).isNormalCube()) {
                this.renderInsideOfBlock(par1, var5.getBlockTextureFromSide(2));
            } else {
                int var6 = 0;
                while (var6 < 8) {
                    int var11;
                    int var12;
                    float var7 = ((float)((var6 >> 0) % 2) - 0.5f) * this.mc.thePlayer.width * 0.9f;
                    float var8 = ((float)((var6 >> 1) % 2) - 0.5f) * this.mc.thePlayer.height * 0.2f;
                    float var9 = ((float)((var6 >> 2) % 2) - 0.5f) * this.mc.thePlayer.width * 0.9f;
                    int var10 = MathHelper.floor_float((float)var2 + var7);
                    if (this.mc.theWorld.getBlock(var10, var11 = MathHelper.floor_float((float)var3 + var8), var12 = MathHelper.floor_float((float)var4 + var9)).isNormalCube()) {
                        var5 = this.mc.theWorld.getBlock(var10, var11, var12);
                    }
                    ++var6;
                }
            }
            if (var5.getMaterial() != Material.air) {
                this.renderInsideOfBlock(par1, var5.getBlockTextureFromSide(2));
            }
        }
        if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
            this.renderWarpedTextureOverlay(par1);
        }
        GL11.glEnable((int)3008);
    }

    private void renderInsideOfBlock(float par1, IIcon par2Icon) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator var3 = Tessellator.instance;
        float var4 = 0.1f;
        GL11.glColor4f((float)var4, (float)var4, (float)var4, (float)0.5f);
        GL11.glPushMatrix();
        float var5 = -1.0f;
        float var6 = 1.0f;
        float var7 = -1.0f;
        float var8 = 1.0f;
        float var9 = -0.5f;
        float var10 = par2Icon.getMinU();
        float var11 = par2Icon.getMaxU();
        float var12 = par2Icon.getMinV();
        float var13 = par2Icon.getMaxV();
        var3.startDrawingQuads();
        var3.addVertexWithUV(var5, var7, var9, var11, var13);
        var3.addVertexWithUV(var6, var7, var9, var10, var13);
        var3.addVertexWithUV(var6, var8, var9, var10, var12);
        var3.addVertexWithUV(var5, var8, var9, var11, var12);
        var3.draw();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void renderWarpedTextureOverlay(float par1) {
        this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
        Tessellator var2 = Tessellator.instance;
        float var3 = this.mc.thePlayer.getBrightness(par1);
        GL11.glColor4f((float)var3, (float)var3, (float)var3, (float)0.5f);
        GL11.glEnable((int)3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glPushMatrix();
        float var4 = 4.0f;
        float var5 = -1.0f;
        float var6 = 1.0f;
        float var7 = -1.0f;
        float var8 = 1.0f;
        float var9 = -0.5f;
        float var10 = (- this.mc.thePlayer.rotationYaw) / 64.0f;
        float var11 = this.mc.thePlayer.rotationPitch / 64.0f;
        var2.startDrawingQuads();
        var2.addVertexWithUV(var5, var7, var9, var4 + var10, var4 + var11);
        var2.addVertexWithUV(var6, var7, var9, 0.0f + var10, var4 + var11);
        var2.addVertexWithUV(var6, var8, var9, 0.0f + var10, 0.0f + var11);
        var2.addVertexWithUV(var5, var8, var9, var4 + var10, 0.0f + var11);
        var2.draw();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
    }

    private void renderFireInFirstPerson(float par1) {
        Tessellator var2 = Tessellator.instance;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.9f);
        GL11.glEnable((int)3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        float var3 = 1.0f;
        int var4 = 0;
        while (var4 < 2) {
            GL11.glPushMatrix();
            IIcon var5 = Blocks.fire.func_149840_c(1);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            float var6 = var5.getMinU();
            float var7 = var5.getMaxU();
            float var8 = var5.getMinV();
            float var9 = var5.getMaxV();
            float var10 = (0.0f - var3) / 2.0f;
            float var11 = var10 + var3;
            float var12 = 0.0f - var3 / 2.0f;
            float var13 = var12 + var3;
            float var14 = -0.5f;
            GL11.glTranslatef((float)((float)(- var4 * 2 - 1) * 0.24f), (float)-0.3f, (float)0.0f);
            GL11.glRotatef((float)((float)(var4 * 2 - 1) * 10.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            var2.startDrawingQuads();
            var2.addVertexWithUV(var10, var12, var14, var7, var9);
            var2.addVertexWithUV(var11, var12, var14, var6, var9);
            var2.addVertexWithUV(var11, var13, var14, var6, var8);
            var2.addVertexWithUV(var10, var13, var14, var7, var8);
            var2.draw();
            GL11.glPopMatrix();
            ++var4;
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
    }

    public void updateEquippedItem() {
        float var6;
        float var4;
        float var5;
        boolean var3;
        this.prevEquippedProgress = this.equippedProgress;
        HookEntityClientPlayerMP var1 = this.mc.thePlayer;
        ItemStack var2 = var1.inventory.getCurrentItem();
        boolean bl = var3 = this.equippedItemSlot == var1.inventory.currentItem && var2 == this.itemToRender;
        if (this.itemToRender == null && var2 == null) {
            var3 = true;
        }
        if (var2 != null && this.itemToRender != null && var2 != this.itemToRender && var2.getItem() == this.itemToRender.getItem() && var2.getItemDamage() == this.itemToRender.getItemDamage()) {
            this.itemToRender = var2;
            var3 = true;
        }
        if ((var6 = (var5 = var3 ? 1.0f : 0.0f) - this.equippedProgress) < - (var4 = 0.4f)) {
            var6 = - var4;
        }
        if (var6 > var4) {
            var6 = var4;
        }
        this.equippedProgress += var6;
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = var2;
            this.equippedItemSlot = var1.inventory.currentItem;
        }
    }

    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }

    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
}

