/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiFurnace
extends GuiContainer {
    private static final ResourceLocation field_147087_u = new ResourceLocation("textures/gui/container/furnace.png");
    private TileEntityFurnace field_147086_v;
    private static final String __OBFID = "CL_00000758";

    public GuiFurnace(InventoryPlayer par1InventoryPlayer, TileEntityFurnace par2TileEntityFurnace) {
        super(new ContainerFurnace(par1InventoryPlayer, par2TileEntityFurnace));
        this.field_147086_v = par2TileEntityFurnace;
    }

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        String var3 = this.field_147086_v.isInventoryNameLocalized() ? this.field_147086_v.getInventoryName() : I18n.format(this.field_147086_v.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(var3, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(var3) / 2, 6.0f, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8.0f, this.field_147000_g - 96 + 2, 4210752);
    }

    @Override
    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        int var6;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_147087_u);
        int var4 = (this.width - this.field_146999_f) / 2;
        int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.field_147086_v.func_145950_i()) {
            var6 = this.field_147086_v.func_145955_e(12);
            this.drawTexturedModalRect(var4 + 56, var5 + 36 + 12 - var6, 176, 12 - var6, 14, var6 + 2);
        }
        var6 = this.field_147086_v.func_145953_d(24);
        this.drawTexturedModalRect(var4 + 79, var5 + 34, 176, 14, var6 + 1, 16);
    }
}

