/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  io.netty.bootstrap.AbstractBootstrap
 *  io.netty.bootstrap.ServerBootstrap
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelConfig
 *  io.netty.channel.ChannelException
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelInitializer
 *  io.netty.channel.ChannelOption
 *  io.netty.channel.ChannelPipeline
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.local.LocalAddress
 *  io.netty.channel.local.LocalServerChannel
 *  io.netty.channel.nio.NioEventLoopGroup
 *  io.netty.channel.socket.nio.NioServerSocketChannel
 *  io.netty.handler.timeout.ReadTimeoutHandler
 *  io.netty.util.concurrent.Future
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PingResponseHandler;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem {
    private static final Logger logger = LogManager.getLogger();
    private static final NioEventLoopGroup eventLoops = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty IO #%d").setDaemon(true).build());
    private final MinecraftServer mcServer;
    public volatile boolean isAlive;
    private final List endpoints = Collections.synchronizedList(new ArrayList());
    private final List networkManagers = Collections.synchronizedList(new ArrayList());
    private static final String __OBFID = "CL_00001447";

    public NetworkSystem(MinecraftServer p_i45292_1_) {
        this.mcServer = p_i45292_1_;
        this.isAlive = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addLanEndpoint(InetAddress p_151265_1_, int p_151265_2_) throws IOException {
        List var3 = this.endpoints;
        List list = this.endpoints;
        synchronized (list) {
            this.endpoints.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(NioServerSocketChannel.class)).childHandler((ChannelHandler)new ChannelInitializer(){
                private static final String __OBFID = "CL_00001448";

                protected void initChannel(Channel p_initChannel_1_) {
                    try {
                        p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
                    }
                    catch (ChannelException channelException) {
                        // empty catch block
                    }
                    try {
                        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)false);
                    }
                    catch (ChannelException channelException) {
                        // empty catch block
                    }
                    p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer()).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer());
                    NetworkManager var2 = new NetworkManager(false);
                    NetworkSystem.this.networkManagers.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
                    var2.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.this.mcServer, var2));
                }
            }).group((EventLoopGroup)eventLoops).localAddress(p_151265_1_, p_151265_2_)).bind().syncUninterruptibly());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SocketAddress addLocalEndpoint() {
        ChannelFuture var1;
        List var2 = this.endpoints;
        List list = this.endpoints;
        synchronized (list) {
            var1 = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer(){
                private static final String __OBFID = "CL_00001449";

                protected void initChannel(Channel p_initChannel_1_) {
                    NetworkManager var2 = new NetworkManager(false);
                    var2.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.this.mcServer, var2));
                    NetworkSystem.this.networkManagers.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
                }
            }).group((EventLoopGroup)eventLoops).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
            this.endpoints.add(var1);
        }
        return var1.channel().localAddress();
    }

    public void terminateEndpoints() {
        this.isAlive = false;
        for (ChannelFuture var2 : this.endpoints) {
            var2.channel().close().syncUninterruptibly();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void networkTick() {
        List var1 = this.networkManagers;
        List list = this.networkManagers;
        synchronized (list) {
            Iterator var2 = this.networkManagers.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = (NetworkManager)((Object)var2.next());
                if (!var3.isChannelOpen()) {
                    var2.remove();
                    if (var3.getExitMessage() != null) {
                        var3.getNetHandler().onDisconnect(var3.getExitMessage());
                        continue;
                    }
                    if (var3.getNetHandler() == null) continue;
                    var3.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
                    continue;
                }
                try {
                    var3.processReceivedPackets();
                }
                catch (Exception var8) {
                    if (var3.isLocalChannel()) {
                        CrashReport var10 = CrashReport.makeCrashReport(var8, "Ticking memory connection");
                        CrashReportCategory var6 = var10.makeCategory("Ticking connection");
                        var6.addCrashSectionCallable("Connection", new Callable(){
                            private static final String __OBFID = "CL_00001450";

                            public String call() {
                                return var3.toString();
                            }
                        });
                        throw new ReportedException(var10);
                    }
                    logger.warn("Failed to handle packet for " + var3.getSocketAddress(), (Throwable)var8);
                    final ChatComponentText var5 = new ChatComponentText("Internal server error");
                    var3.scheduleOutboundPacket(new S40PacketDisconnect(var5), new GenericFutureListener(){
                        private static final String __OBFID = "CL_00001451";

                        public void operationComplete(Future p_operationComplete_1_) {
                            var3.closeChannel(var5);
                        }
                    });
                    var3.disableAutoRead();
                }
            }
        }
    }

    public MinecraftServer func_151267_d() {
        return this.mcServer;
    }

}

