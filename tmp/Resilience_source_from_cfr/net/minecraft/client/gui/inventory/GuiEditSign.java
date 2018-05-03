/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.inventory;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEditSign
extends GuiScreen {
    protected String field_146850_a = "Edit sign message:";
    private TileEntitySign field_146848_f;
    private int field_146849_g;
    private int field_146851_h;
    private GuiButton field_146852_i;
    private static final String __OBFID = "CL_00000764";

    public GuiEditSign(TileEntitySign par1TileEntitySign) {
        this.field_146848_f = par1TileEntitySign;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_146852_i = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done");
        this.buttonList.add(this.field_146852_i);
        this.field_146848_f.func_145913_a(false);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
        NetHandlerPlayClient var1 = this.mc.getNetHandler();
        if (var1 != null) {
            var1.addToSendQueue(new C12PacketUpdateSign(this.field_146848_f.field_145851_c, this.field_146848_f.field_145848_d, this.field_146848_f.field_145849_e, this.field_146848_f.field_145915_a));
        }
        this.field_146848_f.func_145913_a(true);
    }

    @Override
    public void updateScreen() {
        ++this.field_146849_g;
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled && p_146284_1_.id == 0) {
            this.field_146848_f.onInventoryChanged();
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 200) {
            this.field_146851_h = this.field_146851_h - 1 & 3;
        }
        if (par2 == 208 || par2 == 28 || par2 == 156) {
            this.field_146851_h = this.field_146851_h + 1 & 3;
        }
        if (par2 == 14 && this.field_146848_f.field_145915_a[this.field_146851_h].length() > 0) {
            this.field_146848_f.field_145915_a[this.field_146851_h] = this.field_146848_f.field_145915_a[this.field_146851_h].substring(0, this.field_146848_f.field_145915_a[this.field_146851_h].length() - 1);
        }
        if (ChatAllowedCharacters.isAllowedCharacter(par1) && this.field_146848_f.field_145915_a[this.field_146851_h].length() < 15) {
            this.field_146848_f.field_145915_a[this.field_146851_h] = String.valueOf(this.field_146848_f.field_145915_a[this.field_146851_h]) + par1;
        }
        if (par2 == 1) {
            this.actionPerformed(this.field_146852_i);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146850_a, this.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2), (float)0.0f, (float)50.0f);
        float var4 = 93.75f;
        GL11.glScalef((float)(- var4), (float)(- var4), (float)(- var4));
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        Block var5 = this.field_146848_f.getBlockType();
        if (var5 == Blocks.standing_sign) {
            float var6 = (float)(this.field_146848_f.getBlockMetadata() * 360) / 16.0f;
            GL11.glRotatef((float)var6, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-1.0625f, (float)0.0f);
        } else {
            int var8 = this.field_146848_f.getBlockMetadata();
            float var7 = 0.0f;
            if (var8 == 2) {
                var7 = 180.0f;
            }
            if (var8 == 4) {
                var7 = 90.0f;
            }
            if (var8 == 5) {
                var7 = -90.0f;
            }
            GL11.glRotatef((float)var7, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-1.0625f, (float)0.0f);
        }
        if (this.field_146849_g / 6 % 2 == 0) {
            this.field_146848_f.field_145918_i = this.field_146851_h;
        }
        TileEntityRendererDispatcher.instance.func_147549_a(this.field_146848_f, -0.5, -0.75, -0.5, 0.0f);
        this.field_146848_f.field_145918_i = -1;
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
    }
}

