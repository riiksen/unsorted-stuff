/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class CmdRemoteView
extends Command {
    public CmdRemoteView() {
        super("remoteview", " [Player]", "Renders the selected view. Toggle on/off by typing it again.");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (this.mc.renderViewEntity != this.mc.thePlayer) {
            this.mc.renderViewEntity = this.mc.thePlayer;
            Resilience.getInstance().getLogger().infoChat("Now viewing from your player");
            return true;
        }
        String[] args = cmd.split("remoteview ");
        for (Object o : Resilience.getInstance().getInvoker().getEntityList()) {
            if (!(o instanceof EntityOtherPlayerMP)) continue;
            EntityOtherPlayerMP otherPlayer = (EntityOtherPlayerMP)o;
            if (!Resilience.getInstance().getInvoker().getPlayerName(otherPlayer).equalsIgnoreCase(args[1].trim())) continue;
            this.mc.renderViewEntity = otherPlayer;
            Resilience.getInstance().getLogger().infoChat("Now viewing from \u00a7b" + Resilience.getInstance().getInvoker().getPlayerName(otherPlayer) + "\u00a7f's perspective");
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("Error, player not found");
        return false;
    }
}

