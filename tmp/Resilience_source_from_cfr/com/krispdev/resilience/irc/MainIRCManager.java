/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.irc;

import com.krispdev.resilience.irc.MainIRCBot;

public class MainIRCManager
extends Thread {
    private String server = "irc.freenode.net";
    public MainIRCBot bot;
    public String username;

    public MainIRCManager(String username) {
        this.username = username;
        this.bot = new MainIRCBot(username);
    }

    @Override
    public void run() {
        try {
            this.bot.setVerbose(true);
            this.bot.connect(this.server);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.bot.disconnect();
        }
    }

    public MainIRCBot getBot() {
        return this.bot;
    }
}

