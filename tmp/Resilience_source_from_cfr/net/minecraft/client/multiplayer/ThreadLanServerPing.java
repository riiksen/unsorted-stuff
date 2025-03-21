/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLanServerPing
extends Thread {
    private static final AtomicInteger field_148658_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private final String motd;
    private final DatagramSocket socket;
    private boolean isStopping = true;
    private final String address;
    private static final String __OBFID = "CL_00001137";

    public ThreadLanServerPing(String par1Str, String par2Str) throws IOException {
        super("LanServerPinger #" + field_148658_a.incrementAndGet());
        this.motd = par1Str;
        this.address = par2Str;
        this.setDaemon(true);
        this.socket = new DatagramSocket();
    }

    @Override
    public void run() {
        String var1 = ThreadLanServerPing.getPingResponse(this.motd, this.address);
        byte[] var2 = var1.getBytes();
        while (!this.isInterrupted() && this.isStopping) {
            try {
                InetAddress var3 = InetAddress.getByName("224.0.2.60");
                DatagramPacket var4 = new DatagramPacket(var2, var2.length, var3, 4445);
                this.socket.send(var4);
            }
            catch (IOException var7) {
                logger.warn("LanServerPinger: " + var7.getMessage());
                break;
            }
            try {
                ThreadLanServerPing.sleep(1500);
            }
            catch (InterruptedException var7) {
                // empty catch block
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.isStopping = false;
    }

    public static String getPingResponse(String par0Str, String par1Str) {
        return "[MOTD]" + par0Str + "[/MOTD][AD]" + par1Str + "[/AD]";
    }

    public static String getMotdFromPingResponse(String par0Str) {
        int var1 = par0Str.indexOf("[MOTD]");
        if (var1 < 0) {
            return "missing no";
        }
        int var2 = par0Str.indexOf("[/MOTD]", var1 + "[MOTD]".length());
        return var2 < var1 ? "missing no" : par0Str.substring(var1 + "[MOTD]".length(), var2);
    }

    public static String getAdFromPingResponse(String par0Str) {
        int var1 = par0Str.indexOf("[/MOTD]");
        if (var1 < 0) {
            return null;
        }
        int var2 = par0Str.indexOf("[/MOTD]", var1 + "[/MOTD]".length());
        if (var2 >= 0) {
            return null;
        }
        int var3 = par0Str.indexOf("[AD]", var1 + "[/MOTD]".length());
        if (var3 < 0) {
            return null;
        }
        int var4 = par0Str.indexOf("[/AD]", var3 + "[AD]".length());
        return var4 < var3 ? null : par0Str.substring(var3 + "[AD]".length(), var4);
    }
}

