/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.logger;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.io.PrintStream;

public class ResilienceLogger {
    public void plain(String s) {
        System.out.println("[" + Resilience.getInstance().getName() + "] " + s);
    }

    public void info(String s) {
        System.out.println("[" + Resilience.getInstance().getName() + "] [INFO] " + s);
    }

    public void warning(String s) {
        System.out.println("[" + Resilience.getInstance().getName() + "] [WARNING] " + s);
    }

    public void irc(String s) {
        System.out.println("[" + Resilience.getInstance().getName() + "] [IRC] " + s);
    }

    public void plainChat(String s) {
        Resilience.getInstance().getInvoker().addChatMessage("\u00a7f[\u00a73" + Resilience.getInstance().getName() + "\u00a7f] " + s);
    }

    public void infoChat(String s) {
        Resilience.getInstance().getInvoker().addChatMessage("\u00a7f[\u00a73" + Resilience.getInstance().getName() + "\u00a7f] [\u00a7bINFO\u00a7f] " + s);
    }

    public void warningChat(String s) {
        Resilience.getInstance().getInvoker().addChatMessage("\u00a7f[\u00a73" + Resilience.getInstance().getName() + "\u00a7f] [\u00a7cWARNING\u00a7f] " + s);
    }

    public void ircChat(String s) {
        Resilience.getInstance().getInvoker().addChatMessage("\u00a7f[\u00a7bIRC\u00a7f] " + s);
    }
}

