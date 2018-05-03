/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.irc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.irc.src.PircBot;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.values.Values;

public class MainIRCBot
extends PircBot {
    public MainIRCBot(String username) {
        this.setName(username);
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (channel.equals(Resilience.getInstance().getValues().ircChannel)) {
            if (!Resilience.getInstance().getValues().ircEnabled) {
                return;
            }
            boolean krisp = sender.equals("Krisp_");
            boolean nick = sender.startsWith("XxXN");
            if (nick) {
                sender = sender.replaceFirst("XxXN", "");
            }
            if (sender.equalsIgnoreCase("Krisp_")) {
                sender = "Krisp";
            }
            boolean vip = Donator.isDonator(sender, 5.0f);
            Resilience.getInstance().getLogger().irc(String.valueOf(sender) + ": " + message);
            Resilience.getInstance().getLogger().ircChat(String.valueOf(nick ? "\u00a7f[\u00a73NickName\u00a7f]\u00a7b " : "") + (krisp ? "\u00a7f[\u00a7cOwner\u00a7f] \u00a7b" : (vip ? "\u00a7f[\u00a76VIP\u00a7f]\u00a7b " : "\u00a7b")) + sender + "\u00a78:" + (krisp ? "\u00a7c " : (vip ? "\u00a76 " : "\u00a7f ")) + message);
        }
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        if (!Resilience.getInstance().getValues().ircEnabled) {
            return;
        }
        boolean krisp = sender.equals("Krisp_");
        boolean nick = sender.startsWith("XxXN");
        if (nick) {
            sender = sender.replaceFirst("XxXN", "");
        }
        if (sender.equalsIgnoreCase("Krisp_")) {
            sender = "Krisp";
        }
        boolean vip = Donator.isDonator(sender, 5.0f);
        Resilience.getInstance().getLogger().irc(String.valueOf(sender) + ": " + message);
        Resilience.getInstance().getLogger().ircChat("\u00a7c[PM] " + (nick ? "\u00a7f[\u00a73NickName\u00a7f]\u00a7b " : "") + (krisp ? "\u00a7f[\u00a7cOwner\u00a7f] \u00a7b" : (vip ? "\u00a7f[\u00a76VIP\u00a7f]\u00a7b " : "\u00a7b")) + sender + "\u00a78:" + (krisp ? "\u00a7c " : (vip ? "\u00a76 " : "\u00a7f ")) + message);
    }

    @Override
    protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
        if (!Resilience.getInstance().getValues().ircEnabled) {
            return;
        }
        if (kickerNick.equalsIgnoreCase("Krisp_")) {
            kickerNick = "Krisp";
        }
        if (recipientNick.equalsIgnoreCase(this.getNick())) {
            Resilience.getInstance().getLogger().ircChat("\u00a7cYou have been kicked by \u00a7f" + kickerNick + " \u00a7cfor \u00a7f" + reason);
            Resilience.getInstance().getLogger().ircChat("\u00a7cRestart minecraft to get back into the IRC");
        } else {
            Resilience.getInstance().getLogger().ircChat(String.valueOf(recipientNick) + "\u00a7c has been kicked by \u00a7f" + kickerNick + " \u00a7cfor \u00a7f" + reason);
        }
    }
}

