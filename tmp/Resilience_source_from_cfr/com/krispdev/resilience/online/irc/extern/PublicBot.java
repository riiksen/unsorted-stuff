/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.online.irc.extern;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.irc.src.PircBot;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.gui.GuiGroupChat;
import com.krispdev.resilience.online.irc.extern.BotManager;
import java.io.PrintStream;
import java.util.ArrayList;

public class PublicBot
extends PircBot {
    public String lastLine;
    private ArrayList<String> chatLines = new ArrayList();
    private ArrayList<GuiGroupChat> guis = new ArrayList();
    private int toRemove = -1;

    public PublicBot(String username) {
        this.setName(username);
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        this.chatLines.add(String.valueOf(sender) + ": " + message);
        if (this.toRemove != -1) {
            this.guis.remove(this.toRemove);
            this.toRemove = -1;
        }
        if (Resilience.getInstance().getValues().userChat != null) {
            Resilience.getInstance().getValues().userChat.addLine(String.valueOf(sender) + ": " + message);
        }
        for (GuiGroupChat gui : this.guis) {
            if (!gui.getChannel().equals(channel) || gui.getChannel().equals(Resilience.getInstance().getValues().userChannel)) continue;
            gui.addLine(String.valueOf(sender) + ": " + message);
        }
    }

    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        System.out.println("hello");
        if (channel.equals(Resilience.getInstance().getValues().userChannel) && Resilience.getInstance().getValues().userChat == null && !sender.equals(this.getName())) {
            Resilience.getInstance().getValues().userChat = new GuiGroupChat("My", Resilience.getInstance().getValues().userChannel, Resilience.getInstance().getIRCManager(), true);
            this.guis.add(Resilience.getInstance().getValues().userChat);
        }
        for (GuiGroupChat gui : this.guis) {
            if (!gui.getChannel().equals(channel)) continue;
            gui.addLine(String.valueOf(sender) + " joined the chat!");
        }
    }

    @Override
    protected void onPart(String channel, String sender, String login, String hostname) {
        for (GuiGroupChat gui : this.guis) {
            if (!gui.getChannel().equals(channel)) continue;
            gui.addLine(String.valueOf(sender) + " left the chat.");
        }
    }

    public ArrayList<String> getChatLines() {
        return this.chatLines;
    }

    public String getLastLine() {
        return this.lastLine;
    }

    public void addGui(GuiGroupChat guiGroupChat) {
        this.guis.add(guiGroupChat);
    }

    public void removeGui(GuiGroupChat guiGroupChat) {
        this.toRemove = this.guis.indexOf(guiGroupChat);
    }
}

