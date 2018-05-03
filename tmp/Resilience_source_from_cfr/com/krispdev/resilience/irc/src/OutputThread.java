/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.irc.src;

import com.krispdev.resilience.irc.src.PircBot;
import com.krispdev.resilience.irc.src.Queue;
import java.io.BufferedWriter;

public class OutputThread
extends Thread {
    private PircBot _bot = null;
    private Queue _outQueue = null;

    OutputThread(PircBot bot, Queue outQueue) {
        this._bot = bot;
        this._outQueue = outQueue;
        this.setName(this.getClass() + "-Thread");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void sendRawLine(PircBot bot, BufferedWriter bwriter, String line) {
        if (line.length() > bot.getMaxLineLength() - 2) {
            line = line.substring(0, bot.getMaxLineLength() - 2);
        }
        BufferedWriter bufferedWriter = bwriter;
        synchronized (bufferedWriter) {
            try {
                bwriter.write(String.valueOf(line) + "\r\n");
                bwriter.flush();
                bot.log(">>>" + line);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public void run() {
        try {
            boolean running = true;
            while (running) {
                Thread.sleep(this._bot.getMessageDelay());
                String line = (String)this._outQueue.next();
                if (line != null) {
                    this._bot.sendRawLine(line);
                    continue;
                }
                running = false;
            }
        }
        catch (InterruptedException running) {
            // empty catch block
        }
    }
}

