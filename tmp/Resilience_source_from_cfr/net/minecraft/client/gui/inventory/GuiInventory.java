/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.inventory;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiInventory
extends InventoryEffectRenderer {
    private float field_147048_u;
    private float field_147047_v;
    private static final String __OBFID = "CL_00000761";

    public GuiInventory(EntityPlayer par1EntityPlayer) {
        super(par1EntityPlayer.inventoryContainer);
        this.field_146291_p = true;
    }

    @Override
    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        } else {
            super.initGui();
        }
    }

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86.0f, 16.0f, 4210752);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        this.field_147048_u = par1;
        this.field_147047_v = par2;
    }

    @Override
    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_147001_a);
        int var4 = this.field_147003_i;
        int var5 = this.field_147009_r;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        GuiInventory.func_147046_a(var4 + 51, var5 + 75, 30, (float)(var4 + 51) - this.field_147048_u, (float)(var5 + 75 - 50) - this.field_147047_v, this.mc.thePlayer);
    }

    public static void func_147046_a(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_) {
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_147046_0_, (float)p_147046_1_, (float)50.0f);
        GL11.glScalef((float)(- p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float var6 = p_147046_5_.renderYawOffset;
        float var7 = p_147046_5_.rotationYaw;
        float var8 = p_147046_5_.rotationPitch;
        float var9 = p_147046_5_.prevRotationYawHead;
        float var10 = p_147046_5_.rotationYawHead;
        GL11.glRotatef((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)((- (float)Math.atan(p_147046_4_ / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        p_147046_5_.renderYawOffset = (float)Math.atan(p_147046_3_ / 40.0f) * 20.0f;
        p_147046_5_.rotationYaw = (float)Math.atan(p_147046_3_ / 40.0f) * 40.0f;
        p_147046_5_.rotationPitch = (- (float)Math.atan(p_147046_4_ / 40.0f)) * 20.0f;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GL11.glTranslatef((float)0.0f, (float)p_147046_5_.yOffset, (float)0.0f);
        RenderManager.instance.playerViewY = 180.0f;
        RenderManager.instance.func_147940_a(p_147046_5_, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        p_147046_5_.renderYawOffset = var6;
        p_147046_5_.rotationYaw = var7;
        p_147046_5_.rotationPitch = var8;
        p_147046_5_.prevRotationYawHead = var9;
        p_147046_5_.rotationYawHead = var10;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable((int)3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
        }
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
        }
    }
}

