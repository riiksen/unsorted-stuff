/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Project
 */
package net.minecraft.client.gui;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class GuiEnchantment
extends GuiContainer {
    private static final ResourceLocation field_147078_C = new ResourceLocation("textures/gui/container/enchanting_table.png");
    private static final ResourceLocation field_147070_D = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private static final ModelBook field_147072_E = new ModelBook();
    private Random field_147074_F = new Random();
    private ContainerEnchantment field_147075_G;
    public int field_147073_u;
    public float field_147071_v;
    public float field_147069_w;
    public float field_147082_x;
    public float field_147081_y;
    public float field_147080_z;
    public float field_147076_A;
    ItemStack field_147077_B;
    private String field_147079_H;
    private static final String __OBFID = "CL_00000757";

    public GuiEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, String par6Str) {
        super(new ContainerEnchantment(par1InventoryPlayer, par2World, par3, par4, par5));
        this.field_147075_G = (ContainerEnchantment)this.field_147002_h;
        this.field_147079_H = par6Str;
    }

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(this.field_147079_H == null ? I18n.format("container.enchant", new Object[0]) : this.field_147079_H, 12.0f, 5.0f, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8.0f, this.field_147000_g - 96 + 2, 4210752);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.func_147068_g();
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        int var4 = (this.width - this.field_146999_f) / 2;
        int var5 = (this.height - this.field_147000_g) / 2;
        int var6 = 0;
        while (var6 < 3) {
            int var7 = par1 - (var4 + 60);
            int var8 = par2 - (var5 + 14 + 19 * var6);
            if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.field_147075_G.enchantItem(this.mc.thePlayer, var6)) {
                this.mc.playerController.sendEnchantPacket(this.field_147075_G.windowId, var6);
            }
            ++var6;
        }
    }

    @Override
    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_147078_C);
        int var4 = (this.width - this.field_146999_f) / 2;
        int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        GL11.glPushMatrix();
        GL11.glMatrixMode((int)5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution var6 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport((int)((var6.getScaledWidth() - 320) / 2 * var6.getScaleFactor()), (int)((var6.getScaledHeight() - 240) / 2 * var6.getScaleFactor()), (int)(320 * var6.getScaleFactor()), (int)(240 * var6.getScaleFactor()));
        GL11.glTranslatef((float)-0.34f, (float)0.23f, (float)0.0f);
        Project.gluPerspective((float)90.0f, (float)1.3333334f, (float)9.0f, (float)80.0f);
        float var7 = 1.0f;
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef((float)0.0f, (float)3.3f, (float)-16.0f);
        GL11.glScalef((float)var7, (float)var7, (float)var7);
        float var8 = 5.0f;
        GL11.glScalef((float)var8, (float)var8, (float)var8);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_147070_D);
        GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        float var9 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * p_146976_1_;
        GL11.glTranslatef((float)((1.0f - var9) * 0.2f), (float)((1.0f - var9) * 0.1f), (float)((1.0f - var9) * 0.25f));
        GL11.glRotatef((float)((- 1.0f - var9) * 90.0f - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        float var10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * p_146976_1_ + 0.25f;
        float var11 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * p_146976_1_ + 0.75f;
        var10 = (var10 - (float)MathHelper.truncateDoubleToInt(var10)) * 1.6f - 0.3f;
        var11 = (var11 - (float)MathHelper.truncateDoubleToInt(var11)) * 1.6f - 0.3f;
        if (var10 < 0.0f) {
            var10 = 0.0f;
        }
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 1.0f) {
            var10 = 1.0f;
        }
        if (var11 > 1.0f) {
            var11 = 1.0f;
        }
        GL11.glEnable((int)32826);
        field_147072_E.render(null, 0.0f, var10, var11, var9, 0.0f, 0.0625f);
        GL11.glDisable((int)32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode((int)5889);
        GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode((int)5888);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        EnchantmentNameParts.instance.reseedRandomGenerator(this.field_147075_G.nameSeed);
        int var12 = 0;
        while (var12 < 3) {
            String var13 = EnchantmentNameParts.instance.generateNewRandomName();
            this.zLevel = 0.0f;
            this.mc.getTextureManager().bindTexture(field_147078_C);
            int var14 = this.field_147075_G.enchantLevels[var12];
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (var14 == 0) {
                this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
            } else {
                String var15 = "" + var14;
                FontRenderer var16 = this.mc.standardGalacticFontRenderer;
                int var17 = 6839882;
                if (this.mc.thePlayer.experienceLevel < var14 && !this.mc.thePlayer.capabilities.isCreativeMode) {
                    this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
                    var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, (var17 & 16711422) >> 1);
                    var16 = this.mc.fontRenderer;
                    var17 = 4226832;
                    var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
                } else {
                    int var18 = p_146976_2_ - (var4 + 60);
                    int var19 = p_146976_3_ - (var5 + 14 + 19 * var12);
                    if (var18 >= 0 && var19 >= 0 && var18 < 108 && var19 < 19) {
                        this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 204, 108, 19);
                        var17 = 16777088;
                    } else {
                        this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 166, 108, 19);
                    }
                    var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, var17);
                    var16 = this.mc.fontRenderer;
                    var17 = 8453920;
                    var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
                }
            }
            ++var12;
        }
    }

    public void func_147068_g() {
        float var5;
        float var4;
        ItemStack var1 = this.field_147002_h.getSlot(0).getStack();
        if (!ItemStack.areItemStacksEqual(var1, this.field_147077_B)) {
            this.field_147077_B = var1;
            do {
                this.field_147082_x += (float)(this.field_147074_F.nextInt(4) - this.field_147074_F.nextInt(4));
            } while (this.field_147071_v <= this.field_147082_x + 1.0f && this.field_147071_v >= this.field_147082_x - 1.0f);
        }
        ++this.field_147073_u;
        this.field_147069_w = this.field_147071_v;
        this.field_147076_A = this.field_147080_z;
        boolean var2 = false;
        int var3 = 0;
        while (var3 < 3) {
            if (this.field_147075_G.enchantLevels[var3] != 0) {
                var2 = true;
            }
            ++var3;
        }
        this.field_147080_z = var2 ? (this.field_147080_z += 0.2f) : (this.field_147080_z -= 0.2f);
        if (this.field_147080_z < 0.0f) {
            this.field_147080_z = 0.0f;
        }
        if (this.field_147080_z > 1.0f) {
            this.field_147080_z = 1.0f;
        }
        if ((var5 = (this.field_147082_x - this.field_147071_v) * 0.4f) < - (var4 = 0.2f)) {
            var5 = - var4;
        }
        if (var5 > var4) {
            var5 = var4;
        }
        this.field_147081_y += (var5 - this.field_147081_y) * 0.9f;
        this.field_147071_v += this.field_147081_y;
    }
}

