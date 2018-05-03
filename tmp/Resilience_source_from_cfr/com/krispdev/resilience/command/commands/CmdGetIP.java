/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.InetAddress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;

public class CmdGetIP
extends Command {
    public CmdGetIP() {
        super("getip", "", "Copy's the server IP to your clipboard as well as displaying it");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (this.mc.currentServerData != null) {
            ServerAddress serverAddress = new ServerAddress(this.mc.currentServerData.serverIP, this.mc.currentServerData.field_82821_f);
            Resilience.getInstance().getLogger().infoChat("Server IP \u00a7b" + InetAddress.getByName(serverAddress.getIP()).getHostAddress() + " \u00a7f(\u00a7b" + this.mc.currentServerData.serverIP + "\u00a7f)" + " has been copied to you clipboard.");
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(InetAddress.getByName(serverAddress.getIP()).getHostAddress()), null);
        } else {
            Resilience.getInstance().getLogger().warningChat("Error, Server not found!");
        }
        return true;
    }
}

