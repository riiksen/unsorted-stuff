/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorField;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRendererOF
extends ItemRenderer {
    private Minecraft mc = null;
    private RenderBlocks renderBlocksIr = null;
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private static Field ItemRenderer_renderBlockInstance = Reflector.getFieldByType(ItemRenderer.class, RenderBlocks.class);

    public ItemRendererOF(Minecraft par1Minecraft) {
        super(par1Minecraft);
        this.mc = par1Minecraft;
        if (ItemRenderer_renderBlockInstance == null) {
            Config.error("ItemRenderOF not initialized");
        }
        try {
            this.renderBlocksIr = (RenderBlocks)ItemRenderer_renderBlockInstance.get(this);
        }
        catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
        }
    }

    @Override
    public void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3) {
        GL11.glPushMatrix();
        TextureManager var4 = this.mc.getTextureManager();
        Item var5 = par2ItemStack.getItem();
        Block var6 = Block.getBlockFromItem(var5);
        Object type = null;
        Object customRenderer = null;
        if (Reflector.MinecraftForgeClient_getItemRenderer.exists()) {
            type = Reflector.getFieldValue(Reflector.ItemRenderType_EQUIPPED);
            customRenderer = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, par2ItemStack, type);
        }
        if (customRenderer != null) {
            var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
            Reflector.callVoid(Reflector.ForgeHooksClient_renderEquippedItem, type, customRenderer, this.renderBlocksIr, par1EntityLivingBase, par2ItemStack);
        } else if (par2ItemStack.getItemSpriteNumber() == 0 && var5 instanceof ItemBlock && RenderBlocks.renderItemIn3d(var6.getRenderType())) {
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
            ItemRendererOF.renderItemIn2D(var8, var10, var11, var9, var12, var7.getIconWidth(), var7.getIconHeight(), 0.0625f);
            boolean renderEffect = false;
            if (Reflector.ForgeItemStack_hasEffect.exists()) {
                renderEffect = Reflector.callBoolean(par2ItemStack, Reflector.ForgeItemStack_hasEffect, par3);
            } else {
                boolean bl = renderEffect = par2ItemStack.hasEffect() && par3 == 0;
            }
            if (renderEffect) {
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
                ItemRendererOF.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 16, 16, 0.0625f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef((float)var17, (float)var17, (float)var17);
                var18 = (float)(Minecraft.getSystemTime() % 4873) / 4873.0f * 8.0f;
                GL11.glTranslatef((float)(- var18), (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)10.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                ItemRendererOF.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 16, 16, 0.0625f);
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
}

