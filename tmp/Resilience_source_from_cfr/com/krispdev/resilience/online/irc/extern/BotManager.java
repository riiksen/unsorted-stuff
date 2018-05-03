/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.online.irc.extern;

import com.krispdev.resilience.online.irc.extern.PublicBot;

public class BotManager
extends Thread {
    private String server = "irc.icq.com";
    public PublicBot bot;
    public String username;

    public BotManager(String username) {
        this.username = username;
        this.bot = new PublicBot(username);
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

    public PublicBot getBot() {
        return this.bot;
    }
}

