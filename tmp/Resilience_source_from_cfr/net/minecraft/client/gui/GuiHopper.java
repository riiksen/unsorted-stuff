/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiHopper
extends GuiContainer {
    private static final ResourceLocation field_147085_u = new ResourceLocation("textures/gui/container/hopper.png");
    private IInventory field_147084_v;
    private IInventory field_147083_w;
    private static final String __OBFID = "CL_00000759";

    public GuiHopper(InventoryPlayer par1InventoryPlayer, IInventory par2IInventory) {
        super(new ContainerHopper(par1InventoryPlayer, par2IInventory));
        this.field_147084_v = par1InventoryPlayer;
        this.field_147083_w = par2IInventory;
        this.field_146291_p = false;
        this.field_147000_g = 133;
    }

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(this.field_147083_w.isInventoryNameLocalized() ? this.field_147083_w.getInventoryName() : I18n.format(this.field_147083_w.getInventoryName(), new Object[0]), 8.0f, 6.0f, 4210752);
        this.fontRendererObj.drawString(this.field_147084_v.isInventoryNameLocalized() ? this.field_147084_v.getInventoryName() : I18n.format(this.field_147084_v.getInventoryName(), new Object[0]), 8.0f, this.field_147000_g - 96 + 2, 4210752);
    }

    @Override
    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_147085_u);
        int var4 = (this.width - this.field_146999_f) / 2;
        int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
    }
}

