/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.irc.src;

import com.krispdev.resilience.irc.src.DccChat;
import com.krispdev.resilience.irc.src.DccFileTransfer;
import com.krispdev.resilience.irc.src.PircBot;
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

public class DccManager {
    private PircBot _bot;
    private Vector _awaitingResume = new Vector();

    DccManager(PircBot bot) {
        this._bot = bot;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean processRequest(String nick, String login, String hostname, String request) {
        StringTokenizer tokenizer = new StringTokenizer(request);
        tokenizer.nextToken();
        String type = tokenizer.nextToken();
        String filename = tokenizer.nextToken();
        if (type.equals("SEND")) {
            long address = Long.parseLong(tokenizer.nextToken());
            int port = Integer.parseInt(tokenizer.nextToken());
            long size = -1;
            try {
                size = Long.parseLong(tokenizer.nextToken());
            }
            catch (Exception exception) {
                // empty catch block
            }
            DccFileTransfer transfer = new DccFileTransfer(this._bot, this, nick, login, hostname, type, filename, address, port, size);
            this._bot.onIncomingFileTransfer(transfer);
        } else if (type.equals("RESUME")) {
            int port = Integer.parseInt(tokenizer.nextToken());
            long progress = Long.parseLong(tokenizer.nextToken());
            DccFileTransfer transfer = null;
            Vector vector = this._awaitingResume;
            synchronized (vector) {
                int i = 0;
                while (i < this._awaitingResume.size()) {
                    transfer = (DccFileTransfer)this._awaitingResume.elementAt(i);
                    if (transfer.getNick().equals(nick) && transfer.getPort() == port) {
                        this._awaitingResume.removeElementAt(i);
                        break;
                    }
                    ++i;
                }
            }
            if (transfer != null) {
                transfer.setProgress(progress);
                this._bot.sendCTCPCommand(nick, "DCC ACCEPT file.ext " + port + " " + progress);
            }
        } else if (type.equals("ACCEPT")) {
            int port = Integer.parseInt(tokenizer.nextToken());
            long progress = Long.parseLong(tokenizer.nextToken());
            DccFileTransfer transfer = null;
            Vector vector = this._awaitingResume;
            synchronized (vector) {
                int i = 0;
                while (i < this._awaitingResume.size()) {
                    transfer = (DccFileTransfer)this._awaitingResume.elementAt(i);
                    if (transfer.getNick().equals(nick) && transfer.getPort() == port) {
                        this._awaitingResume.removeElementAt(i);
                        break;
                    }
                    ++i;
                }
            }
            if (transfer != null) {
                transfer.doReceive(transfer.getFile(), true);
            }
        } else if (type.equals("CHAT")) {
            long address = Long.parseLong(tokenizer.nextToken());
            int port = Integer.parseInt(tokenizer.nextToken());
            final DccChat chat = new DccChat(this._bot, nick, login, hostname, address, port);
            new Thread(){

                @Override
                public void run() {
                    DccManager.this._bot.onIncomingChatRequest(chat);
                }
            }.start();
        } else {
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addAwaitingResume(DccFileTransfer transfer) {
        Vector vector = this._awaitingResume;
        synchronized (vector) {
            this._awaitingResume.addElement(transfer);
        }
    }

    void removeAwaitingResume(DccFileTransfer transfer) {
        this._awaitingResume.removeElement(transfer);
    }

}

