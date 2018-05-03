/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class RenderItemFrame
extends Render {
    private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
    private final RenderBlocks field_147916_f = new RenderBlocks();
    private final Minecraft field_147917_g = Minecraft.getMinecraft();
    private IIcon field_94147_f;
    private static final String __OBFID = "CL_00001002";

    @Override
    public void updateIcons(IIconRegister par1IconRegister) {
        this.field_94147_f = par1IconRegister.registerIcon("itemframe_background");
    }

    public void doRender(EntityItemFrame par1EntityItemFrame, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        double var10 = par1EntityItemFrame.posX - par2 - 0.5;
        double var12 = par1EntityItemFrame.posY - par4 - 0.5;
        double var14 = par1EntityItemFrame.posZ - par6 - 0.5;
        int var16 = par1EntityItemFrame.field_146063_b + Direction.offsetX[par1EntityItemFrame.hangingDirection];
        int var17 = par1EntityItemFrame.field_146064_c;
        int var18 = par1EntityItemFrame.field_146062_d + Direction.offsetZ[par1EntityItemFrame.hangingDirection];
        GL11.glTranslated((double)((double)var16 - var10), (double)((double)var17 - var12), (double)((double)var18 - var14));
        if (par1EntityItemFrame.getDisplayedItem() != null && par1EntityItemFrame.getDisplayedItem().getItem() == Items.filled_map) {
            this.func_147915_b(par1EntityItemFrame);
        } else {
            this.renderFrameItemAsBlock(par1EntityItemFrame);
        }
        this.func_82402_b(par1EntityItemFrame);
        GL11.glPopMatrix();
        this.func_147914_a(par1EntityItemFrame, par2 + (double)((float)Direction.offsetX[par1EntityItemFrame.hangingDirection] * 0.3f), par4 - 0.25, par6 + (double)((float)Direction.offsetZ[par1EntityItemFrame.hangingDirection] * 0.3f));
    }

    protected ResourceLocation getEntityTexture(EntityItemFrame par1EntityItemFrame) {
        return null;
    }

    private void func_147915_b(EntityItemFrame p_147915_1_) {
        GL11.glPushMatrix();
        GL11.glRotatef((float)p_147915_1_.rotationYaw, (float)0.0f, (float)1.0f, (float)0.0f);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Block var2 = Blocks.planks;
        float var3 = 0.0625f;
        float var4 = 1.0f;
        float var5 = var4 / 2.0f;
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5 + 0.0625f, 0.5f - var5 + 0.0625f, var3, 0.5f + var5 - 0.0625f, 0.5f + var5 - 0.0625f);
        this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        this.field_147916_f.clearOverrideBlockTexture();
        this.field_147916_f.unlockBlockBounds();
        GL11.glPopMatrix();
        this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3 + 1.0E-4f, var3 + 0.5f - var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f + var5 - var3, 0.5f - var5, var3 + 1.0E-4f, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3, 0.5f + var5, var3 + 0.5f - var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f + var5 - var3, var3, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        this.field_147916_f.unlockBlockBounds();
        this.field_147916_f.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }

    private void renderFrameItemAsBlock(EntityItemFrame par1EntityItemFrame) {
        GL11.glPushMatrix();
        GL11.glRotatef((float)par1EntityItemFrame.rotationYaw, (float)0.0f, (float)1.0f, (float)0.0f);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Block var2 = Blocks.planks;
        float var3 = 0.0625f;
        float var4 = 0.75f;
        float var5 = var4 / 2.0f;
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5 + 0.0625f, 0.5f - var5 + 0.0625f, var3 * 0.5f, 0.5f + var5 - 0.0625f, 0.5f + var5 - 0.0625f);
        this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        this.field_147916_f.clearOverrideBlockTexture();
        this.field_147916_f.unlockBlockBounds();
        GL11.glPopMatrix();
        this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3 + 1.0E-4f, var3 + 0.5f - var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f + var5 - var3, 0.5f - var5, var3 + 1.0E-4f, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3, 0.5f + var5, var3 + 0.5f - var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f + var5 - var3, var3, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        this.field_147916_f.unlockBlockBounds();
        this.field_147916_f.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }

    private void func_82402_b(EntityItemFrame par1EntityItemFrame) {
        ItemStack var2 = par1EntityItemFrame.getDisplayedItem();
        if (var2 != null) {
            EntityItem var3 = new EntityItem(par1EntityItemFrame.worldObj, 0.0, 0.0, 0.0, var2);
            Item var4 = var3.getEntityItem().getItem();
            var3.getEntityItem().stackSize = 1;
            var3.hoverStart = 0.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(-0.453125f * (float)Direction.offsetX[par1EntityItemFrame.hangingDirection]), (float)-0.18f, (float)(-0.453125f * (float)Direction.offsetZ[par1EntityItemFrame.hangingDirection]));
            GL11.glRotatef((float)(180.0f + par1EntityItemFrame.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-90 * par1EntityItemFrame.getRotation()), (float)0.0f, (float)0.0f, (float)1.0f);
            switch (par1EntityItemFrame.getRotation()) {
                case 1: {
                    GL11.glTranslatef((float)-0.16f, (float)-0.16f, (float)0.0f);
                    break;
                }
                case 2: {
                    GL11.glTranslatef((float)0.0f, (float)-0.32f, (float)0.0f);
                    break;
                }
                case 3: {
                    GL11.glTranslatef((float)0.16f, (float)-0.16f, (float)0.0f);
                }
            }
            if (var4 == Items.filled_map) {
                this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
                Tessellator var5 = Tessellator.instance;
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                float var6 = 0.0078125f;
                GL11.glScalef((float)var6, (float)var6, (float)var6);
                switch (par1EntityItemFrame.getRotation()) {
                    case 0: {
                        GL11.glTranslatef((float)-64.0f, (float)-87.0f, (float)-1.5f);
                        break;
                    }
                    case 1: {
                        GL11.glTranslatef((float)-66.5f, (float)-84.5f, (float)-1.5f);
                        break;
                    }
                    case 2: {
                        GL11.glTranslatef((float)-64.0f, (float)-82.0f, (float)-1.5f);
                        break;
                    }
                    case 3: {
                        GL11.glTranslatef((float)-61.5f, (float)-84.5f, (float)-1.5f);
                    }
                }
                GL11.glNormal3f((float)0.0f, (float)0.0f, (float)-1.0f);
                MapData var7 = Items.filled_map.getMapData(var3.getEntityItem(), par1EntityItemFrame.worldObj);
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-1.0f);
                if (var7 != null) {
                    this.field_147917_g.entityRenderer.getMapItemRenderer().func_148250_a(var7, true);
                }
            } else {
                TextureAtlasSprite var13;
                if (var4 == Items.compass) {
                    TextureManager var12 = Minecraft.getMinecraft().getTextureManager();
                    var12.bindTexture(TextureMap.locationItemsTexture);
                    TextureAtlasSprite var14 = ((TextureMap)var12.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName());
                    if (var14 instanceof TextureCompass) {
                        TextureCompass var15 = (TextureCompass)var14;
                        double var8 = var15.currentAngle;
                        double var10 = var15.angleDelta;
                        var15.currentAngle = 0.0;
                        var15.angleDelta = 0.0;
                        var15.updateCompass(par1EntityItemFrame.worldObj, par1EntityItemFrame.posX, par1EntityItemFrame.posZ, MathHelper.wrapAngleTo180_float(180 + par1EntityItemFrame.hangingDirection * 90), false, true);
                        var15.currentAngle = var8;
                        var15.angleDelta = var10;
                    }
                }
                RenderItem.renderInFrame = true;
                RenderManager.instance.func_147940_a(var3, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                RenderItem.renderInFrame = false;
                if (var4 == Items.compass && (var13 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName())).getFrameCount() > 0) {
                    var13.updateAnimation();
                }
            }
            GL11.glPopMatrix();
        }
    }

    protected void func_147914_a(EntityItemFrame p_147914_1_, double p_147914_2_, double p_147914_4_, double p_147914_6_) {
        if (Minecraft.isGuiEnabled() && p_147914_1_.getDisplayedItem() != null && p_147914_1_.getDisplayedItem().hasDisplayName() && this.renderManager.field_147941_i == p_147914_1_) {
            float var12;
            float var8 = 1.6f;
            float var9 = 0.016666668f * var8;
            double var10 = p_147914_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float f = var12 = p_147914_1_.isSneaking() ? 32.0f : 64.0f;
            if (var10 < (double)(var12 * var12)) {
                String var13 = p_147914_1_.getDisplayedItem().getDisplayName();
                if (p_147914_1_.isSneaking()) {
                    FontRenderer var14 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)((float)p_147914_2_ + 0.0f), (float)((float)p_147914_4_ + p_147914_1_.height + 0.5f), (float)((float)p_147914_6_));
                    GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)(- this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)this.renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glScalef((float)(- var9), (float)(- var9), (float)var9);
                    GL11.glDisable((int)2896);
                    GL11.glTranslatef((float)0.0f, (float)(0.25f / var9), (float)0.0f);
                    GL11.glDepthMask((boolean)false);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
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
                }
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityItemFrame)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityItemFrame)par1Entity, par2, par4, par6, par8, par9);
    }
}

