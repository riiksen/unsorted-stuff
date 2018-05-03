/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookGuiIngame;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class GuiSleepMP
extends GuiChat {
    private static final String __OBFID = "CL_00000697";

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 1) {
            this.func_146418_g();
        } else if (par2 != 28 && par2 != 156) {
            super.keyTyped(par1, par2);
        } else {
            String var3 = this.field_146415_a.getText().trim();
            if (!var3.isEmpty()) {
                this.mc.thePlayer.sendChatMessage(var3);
            }
            this.field_146415_a.setText("");
            this.mc.ingameGUI.getChatGUI().resetScroll();
        }
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 1) {
            this.func_146418_g();
        } else {
            super.actionPerformed(p_146284_1_);
        }
    }

    private void func_146418_g() {
        NetHandlerPlayClient var1 = this.mc.thePlayer.sendQueue;
        var1.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, 3));
    }
}

