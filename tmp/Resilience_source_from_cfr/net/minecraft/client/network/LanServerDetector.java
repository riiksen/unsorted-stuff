/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetector {
    private static final AtomicInteger field_148551_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00001133";

    public static class LanServer {
        private String lanServerMotd;
        private String lanServerIpPort;
        private long timeLastSeen;
        private static final String __OBFID = "CL_00001134";

        public LanServer(String par1Str, String par2Str) {
            this.lanServerMotd = par1Str;
            this.lanServerIpPort = par2Str;
            this.timeLastSeen = Minecraft.getSystemTime();
        }

        public String getServerMotd() {
            return this.lanServerMotd;
        }

        public String getServerIpPort() {
            return this.lanServerIpPort;
        }

        public void updateLastSeen() {
            this.timeLastSeen = Minecraft.getSystemTime();
        }
    }

    public static class LanServerList {
        private ArrayList listOfLanServers = new ArrayList();
        boolean wasUpdated;
        private static final String __OBFID = "CL_00001136";

        public synchronized boolean getWasUpdated() {
            return this.wasUpdated;
        }

        public synchronized void setWasNotUpdated() {
            this.wasUpdated = false;
        }

        public synchronized List getLanServers() {
            return Collections.unmodifiableList(this.listOfLanServers);
        }

        public synchronized void func_77551_a(String par1Str, InetAddress par2InetAddress) {
            String var3 = ThreadLanServerPing.getMotdFromPingResponse(par1Str);
            String var4 = ThreadLanServerPing.getAdFromPingResponse(par1Str);
            if (var4 != null) {
                var4 = String.valueOf(par2InetAddress.getHostAddress()) + ":" + var4;
                boolean var5 = false;
                for (LanServer var7 : this.listOfLanServers) {
                    if (!var7.getServerIpPort().equals(var4)) continue;
                    var7.updateLastSeen();
                    var5 = true;
                    break;
                }
                if (!var5) {
                    this.listOfLanServers.add(new LanServer(var3, var4));
                    this.wasUpdated = true;
                }
            }
        }
    }

    public static class ThreadLanServerFind
    extends Thread {
        private final LanServerList localServerList;
        private final InetAddress broadcastAddress;
        private final MulticastSocket socket;
        private static final String __OBFID = "CL_00001135";

        public ThreadLanServerFind(LanServerList par1LanServerList) throws IOException {
            super("LanServerDetector #" + field_148551_a.incrementAndGet());
            this.localServerList = par1LanServerList;
            this.setDaemon(true);
            this.socket = new MulticastSocket(4445);
            this.broadcastAddress = InetAddress.getByName("224.0.2.60");
            this.socket.setSoTimeout(5000);
            this.socket.joinGroup(this.broadcastAddress);
        }

        @Override
        public void run() {
            byte[] var2 = new byte[1024];
            while (!this.isInterrupted()) {
                DatagramPacket var1 = new DatagramPacket(var2, var2.length);
                try {
                    this.socket.receive(var1);
                }
                catch (SocketTimeoutException var5) {
                    continue;
                }
                catch (IOException var6) {
                    logger.error("Couldn't ping server", (Throwable)var6);
                    break;
                }
                String var3 = new String(var1.getData(), var1.getOffset(), var1.getLength());
                logger.debug(var1.getAddress() + ": " + var3);
                this.localServerList.func_77551_a(var3, var1.getAddress());
            }
            try {
                this.socket.leaveGroup(this.broadcastAddress);
            }
            catch (IOException var1) {
                // empty catch block
            }
            this.socket.close();
        }
    }

}

