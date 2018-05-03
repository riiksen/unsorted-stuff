/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock
extends GuiScreen {
    private static final Logger field_146488_a = LogManager.getLogger();
    private GuiTextField field_146485_f;
    private GuiTextField field_146486_g;
    private final CommandBlockLogic field_146489_h;
    private GuiButton field_146490_i;
    private GuiButton field_146487_r;
    private static final String __OBFID = "CL_00000748";

    public GuiCommandBlock(CommandBlockLogic p_i45032_1_) {
        this.field_146489_h = p_i45032_1_;
    }

    @Override
    public void updateScreen() {
        this.field_146485_f.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.field_146490_i = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0]));
        this.buttonList.add(this.field_146490_i);
        this.field_146487_r = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0]));
        this.buttonList.add(this.field_146487_r);
        this.field_146485_f = new GuiTextField(this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
        this.field_146485_f.func_146203_f(32767);
        this.field_146485_f.setFocused(true);
        this.field_146485_f.setText(this.field_146489_h.func_145753_i());
        this.field_146486_g = new GuiTextField(this.fontRendererObj, this.width / 2 - 150, 135, 300, 20);
        this.field_146486_g.func_146203_f(32767);
        this.field_146486_g.func_146184_c(false);
        this.field_146486_g.setText(this.field_146489_h.func_145753_i());
        if (this.field_146489_h.func_145749_h() != null) {
            this.field_146486_g.setText(this.field_146489_h.func_145749_h().getUnformattedText());
        }
        this.field_146490_i.enabled = this.field_146485_f.getText().trim().length() > 0;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 1) {
                this.mc.displayGuiScreen(null);
            } else if (p_146284_1_.id == 0) {
                block9 : {
                    PacketBuffer var2;
                    var2 = new PacketBuffer(Unpooled.buffer());
                    try {
                        try {
                            var2.writeByte(this.field_146489_h.func_145751_f());
                            this.field_146489_h.func_145757_a(var2);
                            var2.writeStringToBuffer(this.field_146485_f.getText());
                            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", var2));
                        }
                        catch (Exception var7) {
                            field_146488_a.error("Couldn't send command block info", (Throwable)var7);
                            var2.release();
                            break block9;
                        }
                    }
                    catch (Throwable throwable) {
                        var2.release();
                        throw throwable;
                    }
                    var2.release();
                }
                this.mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.field_146485_f.textboxKeyTyped(par1, par2);
        this.field_146486_g.textboxKeyTyped(par1, par2);
        boolean bl = this.field_146490_i.enabled = this.field_146485_f.getText().trim().length() > 0;
        if (par2 != 28 && par2 != 156) {
            if (par2 == 1) {
                this.actionPerformed(this.field_146487_r);
            }
        } else {
            this.actionPerformed(this.field_146490_i);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_146485_f.mouseClicked(par1, par2, par3);
        this.field_146486_g.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 37, 10526880);
        this.field_146485_f.drawTextBox();
        int var4 = 75;
        int var5 = 0;
        FontRenderer var10001 = this.fontRendererObj;
        String var10002 = I18n.format("advMode.nearestPlayer", new Object[0]);
        int var10003 = this.width / 2 - 150;
        int var7 = var5 + 1;
        this.drawString(var10001, var10002, var10003, var4 + var5 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        if (this.field_146486_g.getText().length() > 0) {
            int var6 = var4 + var7 * this.fontRendererObj.FONT_HEIGHT + 20;
            this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, var6, 10526880);
            this.field_146486_g.drawTextBox();
        }
        super.drawScreen(par1, par2, par3);
    }
}

