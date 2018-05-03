/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderItem
extends Render {
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private RenderBlocks field_147913_i = new RenderBlocks();
    private Random random = new Random();
    public boolean renderWithColor = true;
    public float zLevel;
    public static boolean renderInFrame;
    private static final String __OBFID = "CL_00001003";

    public RenderItem() {
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }

    public void doRender(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9) {
        ItemStack var10 = par1EntityItem.getEntityItem();
        if (var10.getItem() != null) {
            this.bindEntityTexture(par1EntityItem);
            this.random.setSeed(187);
            GL11.glPushMatrix();
            float var11 = MathHelper.sin(((float)par1EntityItem.age + par9) / 10.0f + par1EntityItem.hoverStart) * 0.1f + 0.1f;
            float var12 = (((float)par1EntityItem.age + par9) / 20.0f + par1EntityItem.hoverStart) * 57.295776f;
            int var13 = 1;
            if (par1EntityItem.getEntityItem().stackSize > 1) {
                var13 = 2;
            }
            if (par1EntityItem.getEntityItem().stackSize > 5) {
                var13 = 3;
            }
            if (par1EntityItem.getEntityItem().stackSize > 20) {
                var13 = 4;
            }
            if (par1EntityItem.getEntityItem().stackSize > 40) {
                var13 = 5;
            }
            GL11.glTranslatef((float)((float)par2), (float)((float)par4 + var11), (float)((float)par6));
            GL11.glEnable((int)32826);
            if (var10.getItemSpriteNumber() == 0 && var10.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var10.getItem()).getRenderType())) {
                Block var21 = Block.getBlockFromItem(var10.getItem());
                GL11.glRotatef((float)var12, (float)0.0f, (float)1.0f, (float)0.0f);
                if (renderInFrame) {
                    GL11.glScalef((float)1.25f, (float)1.25f, (float)1.25f);
                    GL11.glTranslatef((float)0.0f, (float)0.05f, (float)0.0f);
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
                float var25 = 0.25f;
                int var24 = var21.getRenderType();
                if (var24 == 1 || var24 == 19 || var24 == 12 || var24 == 2) {
                    var25 = 0.5f;
                }
                if (var21.getRenderBlockPass() > 0) {
                    GL11.glAlphaFunc((int)516, (float)0.1f);
                    GL11.glEnable((int)3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                }
                GL11.glScalef((float)var25, (float)var25, (float)var25);
                int var26 = 0;
                while (var26 < var13) {
                    GL11.glPushMatrix();
                    if (var26 > 0) {
                        float var18 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var25;
                        float var19 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var25;
                        float var20 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var25;
                        GL11.glTranslatef((float)var18, (float)var19, (float)var20);
                    }
                    this.field_147913_i.renderBlockAsItem(var21, var10.getItemDamage(), 1.0f);
                    GL11.glPopMatrix();
                    ++var26;
                }
                if (var21.getRenderBlockPass() > 0) {
                    GL11.glDisable((int)3042);
                }
            } else if (var10.getItemSpriteNumber() == 1 && var10.getItem().requiresMultipleRenderPasses()) {
                if (renderInFrame) {
                    GL11.glScalef((float)0.5128205f, (float)0.5128205f, (float)0.5128205f);
                    GL11.glTranslatef((float)0.0f, (float)-0.05f, (float)0.0f);
                } else {
                    GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                }
                int var23 = 0;
                while (var23 <= 1) {
                    this.random.setSeed(187);
                    IIcon var22 = var10.getItem().getIconFromDamageForRenderPass(var10.getItemDamage(), var23);
                    if (this.renderWithColor) {
                        int var24 = var10.getItem().getColorFromItemStack(var10, var23);
                        float var17 = (float)(var24 >> 16 & 255) / 255.0f;
                        float var18 = (float)(var24 >> 8 & 255) / 255.0f;
                        float var19 = (float)(var24 & 255) / 255.0f;
                        GL11.glColor4f((float)var17, (float)var18, (float)var19, (float)1.0f);
                        this.renderDroppedItem(par1EntityItem, var22, var13, par9, var17, var18, var19);
                    } else {
                        this.renderDroppedItem(par1EntityItem, var22, var13, par9, 1.0f, 1.0f, 1.0f);
                    }
                    ++var23;
                }
            } else {
                if (var10 != null && var10.getItem() instanceof ItemCloth) {
                    GL11.glAlphaFunc((int)516, (float)0.1f);
                    GL11.glEnable((int)3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                }
                if (renderInFrame) {
                    GL11.glScalef((float)0.5128205f, (float)0.5128205f, (float)0.5128205f);
                    GL11.glTranslatef((float)0.0f, (float)-0.05f, (float)0.0f);
                } else {
                    GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                }
                IIcon var14 = var10.getIconIndex();
                if (this.renderWithColor) {
                    int var15 = var10.getItem().getColorFromItemStack(var10, 0);
                    float var16 = (float)(var15 >> 16 & 255) / 255.0f;
                    float var17 = (float)(var15 >> 8 & 255) / 255.0f;
                    float var18 = (float)(var15 & 255) / 255.0f;
                    this.renderDroppedItem(par1EntityItem, var14, var13, par9, var16, var17, var18);
                } else {
                    this.renderDroppedItem(par1EntityItem, var14, var13, par9, 1.0f, 1.0f, 1.0f);
                }
                if (var10 != null && var10.getItem() instanceof ItemCloth) {
                    GL11.glDisable((int)3042);
                }
            }
            GL11.glDisable((int)32826);
            GL11.glPopMatrix();
        }
    }

    protected ResourceLocation getEntityTexture(EntityItem par1EntityItem) {
        return this.renderManager.renderEngine.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
    }

    private void renderDroppedItem(EntityItem par1EntityItem, IIcon par2Icon, int par3, float par4, float par5, float par6, float par7) {
        Tessellator var8 = Tessellator.instance;
        if (par2Icon == null) {
            TextureManager var9 = Minecraft.getMinecraft().getTextureManager();
            ResourceLocation var10 = var9.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
            par2Icon = ((TextureMap)var9.getTexture(var10)).getAtlasSprite("missingno");
        }
        float var25 = par2Icon.getMinU();
        float var26 = par2Icon.getMaxU();
        float var11 = par2Icon.getMinV();
        float var12 = par2Icon.getMaxV();
        float var13 = 1.0f;
        float var14 = 0.5f;
        float var15 = 0.25f;
        if (this.renderManager.options.fancyGraphics) {
            GL11.glPushMatrix();
            if (renderInFrame) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                GL11.glRotatef((float)((((float)par1EntityItem.age + par4) / 20.0f + par1EntityItem.hoverStart) * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            float var16 = 0.0625f;
            float var17 = 0.021875f;
            ItemStack var18 = par1EntityItem.getEntityItem();
            int var19 = var18.stackSize;
            int var24 = var19 < 2 ? 1 : (var19 < 16 ? 2 : (var19 < 32 ? 3 : 4));
            GL11.glTranslatef((float)(- var14), (float)(- var15), (float)(- (var16 + var17) * (float)var24 / 2.0f));
            int var20 = 0;
            while (var20 < var24) {
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(var16 + var17));
                if (var18.getItemSpriteNumber() == 0) {
                    this.bindTexture(TextureMap.locationBlocksTexture);
                } else {
                    this.bindTexture(TextureMap.locationItemsTexture);
                }
                GL11.glColor4f((float)par5, (float)par6, (float)par7, (float)1.0f);
                ItemRenderer.renderItemIn2D(var8, var26, var11, var25, var12, par2Icon.getIconWidth(), par2Icon.getIconHeight(), var16);
                if (var18.hasEffect()) {
                    GL11.glDepthFunc((int)514);
                    GL11.glDisable((int)2896);
                    this.renderManager.renderEngine.bindTexture(RES_ITEM_GLINT);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)768, (int)1);
                    float var21 = 0.76f;
                    GL11.glColor4f((float)(0.5f * var21), (float)(0.25f * var21), (float)(0.8f * var21), (float)1.0f);
                    GL11.glMatrixMode((int)5890);
                    GL11.glPushMatrix();
                    float var22 = 0.125f;
                    GL11.glScalef((float)var22, (float)var22, (float)var22);
                    float var23 = (float)(Minecraft.getSystemTime() % 3000) / 3000.0f * 8.0f;
                    GL11.glTranslatef((float)var23, (float)0.0f, (float)0.0f);
                    GL11.glRotatef((float)-50.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef((float)var22, (float)var22, (float)var22);
                    var23 = (float)(Minecraft.getSystemTime() % 4873) / 4873.0f * 8.0f;
                    GL11.glTranslatef((float)(- var23), (float)0.0f, (float)0.0f);
                    GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode((int)5888);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)2896);
                    GL11.glDepthFunc((int)515);
                }
                ++var20;
            }
            GL11.glPopMatrix();
        } else {
            int var27 = 0;
            while (var27 < par3) {
                GL11.glPushMatrix();
                if (var27 > 0) {
                    float var17 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float var29 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float var28 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef((float)var17, (float)var29, (float)var28);
                }
                if (!renderInFrame) {
                    GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                GL11.glColor4f((float)par5, (float)par6, (float)par7, (float)1.0f);
                var8.startDrawingQuads();
                var8.setNormal(0.0f, 1.0f, 0.0f);
                var8.addVertexWithUV(0.0f - var14, 0.0f - var15, 0.0, var25, var12);
                var8.addVertexWithUV(var13 - var14, 0.0f - var15, 0.0, var26, var12);
                var8.addVertexWithUV(var13 - var14, 1.0f - var15, 0.0, var26, var11);
                var8.addVertexWithUV(0.0f - var14, 1.0f - var15, 0.0, var25, var11);
                var8.draw();
                GL11.glPopMatrix();
                ++var27;
            }
        }
    }

    public void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5) {
        int var6 = par3ItemStack.getItemDamage();
        IIcon var7 = par3ItemStack.getIconIndex();
        GL11.glEnable((int)3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        if (par3ItemStack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(par3ItemStack.getItem()).getRenderType())) {
            par2TextureManager.bindTexture(TextureMap.locationBlocksTexture);
            Block var15 = Block.getBlockFromItem(par3ItemStack.getItem());
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(par4 - 2), (float)(par5 + 3), (float)(-3.0f + this.zLevel));
            GL11.glScalef((float)10.0f, (float)10.0f, (float)10.0f);
            GL11.glTranslatef((float)1.0f, (float)0.5f, (float)1.0f);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)-1.0f);
            GL11.glRotatef((float)210.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            int var9 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);
            float var18 = (float)(var9 >> 16 & 255) / 255.0f;
            float var17 = (float)(var9 >> 8 & 255) / 255.0f;
            float var12 = (float)(var9 & 255) / 255.0f;
            if (this.renderWithColor) {
                GL11.glColor4f((float)var18, (float)var17, (float)var12, (float)1.0f);
            }
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.field_147913_i.useInventoryTint = this.renderWithColor;
            this.field_147913_i.renderBlockAsItem(var15, var6, 1.0f);
            this.field_147913_i.useInventoryTint = true;
            GL11.glPopMatrix();
        } else if (par3ItemStack.getItem().requiresMultipleRenderPasses()) {
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3008);
            par2TextureManager.bindTexture(TextureMap.locationItemsTexture);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(0, 0, 0, 0);
            GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Tessellator var8 = Tessellator.instance;
            var8.startDrawingQuads();
            var8.setColorOpaque_I(-1);
            var8.addVertex(par4 - 2, par5 + 18, this.zLevel);
            var8.addVertex(par4 + 18, par5 + 18, this.zLevel);
            var8.addVertex(par4 + 18, par5 - 2, this.zLevel);
            var8.addVertex(par4 - 2, par5 - 2, this.zLevel);
            var8.draw();
            GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3008);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            int var9 = 0;
            while (var9 <= 1) {
                IIcon var10 = par3ItemStack.getItem().getIconFromDamageForRenderPass(var6, var9);
                int var11 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, var9);
                float var12 = (float)(var11 >> 16 & 255) / 255.0f;
                float var13 = (float)(var11 >> 8 & 255) / 255.0f;
                float var14 = (float)(var11 & 255) / 255.0f;
                if (this.renderWithColor) {
                    GL11.glColor4f((float)var12, (float)var13, (float)var14, (float)1.0f);
                }
                this.renderIcon(par4, par5, var10, 16, 16);
                ++var9;
            }
            GL11.glDisable((int)3008);
            GL11.glEnable((int)2896);
        } else {
            GL11.glDisable((int)2896);
            ResourceLocation var16 = par2TextureManager.getResourceLocation(par3ItemStack.getItemSpriteNumber());
            par2TextureManager.bindTexture(var16);
            if (var7 == null) {
                var7 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(var16)).getAtlasSprite("missingno");
            }
            int var9 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);
            float var18 = (float)(var9 >> 16 & 255) / 255.0f;
            float var17 = (float)(var9 >> 8 & 255) / 255.0f;
            float var12 = (float)(var9 & 255) / 255.0f;
            if (this.renderWithColor) {
                GL11.glColor4f((float)var18, (float)var17, (float)var12, (float)1.0f);
            }
            this.renderIcon(par4, par5, var7, 16, 16);
            GL11.glEnable((int)2896);
        }
        GL11.glEnable((int)2884);
    }

    public void renderItemAndEffectIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, final ItemStack par3ItemStack, int par4, int par5) {
        if (par3ItemStack != null) {
            this.zLevel += 50.0f;
            try {
                this.renderItemIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5);
            }
            catch (Throwable var9) {
                CrashReport var7 = CrashReport.makeCrashReport(var9, "Rendering item");
                CrashReportCategory var8 = var7.makeCategory("Item being rendered");
                var8.addCrashSectionCallable("Item Type", new Callable(){
                    private static final String __OBFID = "CL_00001004";

                    public String call() {
                        return String.valueOf(par3ItemStack.getItem());
                    }
                });
                var8.addCrashSectionCallable("Item Aux", new Callable(){
                    private static final String __OBFID = "CL_00001005";

                    public String call() {
                        return String.valueOf(par3ItemStack.getItemDamage());
                    }
                });
                var8.addCrashSectionCallable("Item NBT", new Callable(){
                    private static final String __OBFID = "CL_00001006";

                    public String call() {
                        return String.valueOf(par3ItemStack.getTagCompound());
                    }
                });
                var8.addCrashSectionCallable("Item Foil", new Callable(){
                    private static final String __OBFID = "CL_00001007";

                    public String call() {
                        return String.valueOf(par3ItemStack.hasEffect());
                    }
                });
                throw new ReportedException(var7);
            }
            if (par3ItemStack.hasEffect()) {
                GL11.glDepthFunc((int)514);
                GL11.glDisable((int)2896);
                GL11.glDepthMask((boolean)false);
                par2TextureManager.bindTexture(RES_ITEM_GLINT);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)0.5f, (float)0.25f, (float)0.8f, (float)1.0f);
                this.renderGlint(par4 * 431278612 + par5 * 32178161, par4 - 2, par5 - 2, 20, 20);
                GL11.glDepthMask((boolean)true);
                GL11.glDisable((int)3008);
                GL11.glEnable((int)2896);
                GL11.glDepthFunc((int)515);
            }
            this.zLevel -= 50.0f;
        }
    }

    private void renderGlint(int par1, int par2, int par3, int par4, int par5) {
        int var6 = 0;
        while (var6 < 2) {
            OpenGlHelper.glBlendFunc(772, 1, 0, 0);
            float var7 = 0.00390625f;
            float var8 = 0.00390625f;
            float var9 = (float)(Minecraft.getSystemTime() % (long)(3000 + var6 * 1873)) / (3000.0f + (float)(var6 * 1873)) * 256.0f;
            float var10 = 0.0f;
            Tessellator var11 = Tessellator.instance;
            float var12 = 4.0f;
            if (var6 == 1) {
                var12 = -1.0f;
            }
            var11.startDrawingQuads();
            var11.addVertexWithUV(par2 + 0, par3 + par5, this.zLevel, (var9 + (float)par5 * var12) * var7, (var10 + (float)par5) * var8);
            var11.addVertexWithUV(par2 + par4, par3 + par5, this.zLevel, (var9 + (float)par4 + (float)par5 * var12) * var7, (var10 + (float)par5) * var8);
            var11.addVertexWithUV(par2 + par4, par3 + 0, this.zLevel, (var9 + (float)par4) * var7, (var10 + 0.0f) * var8);
            var11.addVertexWithUV(par2 + 0, par3 + 0, this.zLevel, (var9 + 0.0f) * var7, (var10 + 0.0f) * var8);
            var11.draw();
            ++var6;
        }
    }

    public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5) {
        this.renderItemOverlayIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5, null);
    }

    public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, String par6Str) {
        if (par3ItemStack != null) {
            if (par3ItemStack.stackSize > 1 || par6Str != null) {
                String var7 = par6Str == null ? String.valueOf(par3ItemStack.stackSize) : par6Str;
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glDisable((int)3042);
                par1FontRenderer.drawStringWithShadow(var7, par4 + 19 - 2 - par1FontRenderer.getStringWidth(var7), par5 + 6 + 3, 16777215);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
            }
            if (par3ItemStack.isItemDamaged()) {
                int var12 = (int)Math.round(13.0 - (double)par3ItemStack.getItemDamageForDisplay() * 13.0 / (double)par3ItemStack.getMaxDamage());
                int var8 = (int)Math.round(255.0 - (double)par3ItemStack.getItemDamageForDisplay() * 255.0 / (double)par3ItemStack.getMaxDamage());
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)3008);
                GL11.glDisable((int)3042);
                Tessellator var9 = Tessellator.instance;
                int var10 = 255 - var8 << 16 | var8 << 8;
                int var11 = (255 - var8) / 4 << 16 | 16128;
                this.renderQuad(var9, par4 + 2, par5 + 13, 13, 2, 0);
                this.renderQuad(var9, par4 + 2, par5 + 13, 12, 1, var11);
                this.renderQuad(var9, par4 + 2, par5 + 13, var12, 1, var10);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
        }
    }

    private void renderQuad(Tessellator par1Tessellator, int par2, int par3, int par4, int par5, int par6) {
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorOpaque_I(par6);
        par1Tessellator.addVertex(par2 + 0, par3 + 0, 0.0);
        par1Tessellator.addVertex(par2 + 0, par3 + par5, 0.0);
        par1Tessellator.addVertex(par2 + par4, par3 + par5, 0.0);
        par1Tessellator.addVertex(par2 + par4, par3 + 0, 0.0);
        par1Tessellator.draw();
    }

    public void renderIcon(int par1, int par2, IIcon par3Icon, int par4, int par5) {
        Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV(par1 + 0, par2 + par5, this.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + par5, this.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + 0, this.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
        var6.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, par3Icon.getMinU(), par3Icon.getMinV());
        var6.draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityItem)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityItem)par1Entity, par2, par4, par6, par8, par9);
    }

}

