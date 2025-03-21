/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelFutureListener
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelInboundHandlerAdapter
 *  io.netty.channel.ChannelPipeline
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import net.minecraft.network.NetworkSystem;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingResponseHandler
extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();
    private NetworkSystem field_151257_b;
    private static final String __OBFID = "CL_00001444";

    public PingResponseHandler(NetworkSystem p_i45286_1_) {
        this.field_151257_b = p_i45286_1_;
    }

    public void channelRead(ChannelHandlerContext p_channelRead_1_, Object p_channelRead_2_) {
        ByteBuf var3 = (ByteBuf)p_channelRead_2_;
        var3.markReaderIndex();
        boolean var4 = true;
        try {
            if (var3.readUnsignedByte() == 254) {
                InetSocketAddress var5 = (InetSocketAddress)p_channelRead_1_.channel().remoteAddress();
                MinecraftServer var6 = this.field_151257_b.func_151267_d();
                int var7 = var3.readableBytes();
                switch (var7) {
                    case 0: {
                        logger.debug("Ping: (<1.3.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                        String var8 = String.format("%s\u00a7%d\u00a7%d", var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
                        this.func_151256_a(p_channelRead_1_, this.func_151255_a(var8));
                        break;
                    }
                    case 1: {
                        if (var3.readUnsignedByte() != 1) {
                            return;
                        }
                        logger.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                        String var8 = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
                        this.func_151256_a(p_channelRead_1_, this.func_151255_a(var8));
                        break;
                    }
                    default: {
                        boolean var16 = var3.readUnsignedByte() == 1;
                        var16 &= var3.readUnsignedByte() == 250;
                        var16 &= "MC|PingHost".equals(new String(var3.readBytes(var3.readShort() * 2).array(), Charsets.UTF_16BE));
                        int var9 = var3.readUnsignedShort();
                        var16 &= var3.readUnsignedByte() >= 73;
                        var16 &= 3 + var3.readBytes(var3.readShort() * 2).array().length + 4 == var9;
                        var16 &= var3.readInt() <= 65535;
                        if (!(var16 &= var3.readableBytes() == 0)) {
                            return;
                        }
                        logger.debug("Ping: (1.6) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                        String var10 = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
                        this.func_151256_a(p_channelRead_1_, this.func_151255_a(var10));
                    }
                }
                var3.release();
                var4 = false;
                return;
            }
        }
        catch (RuntimeException var14) {
            return;
        }
        finally {
            if (var4) {
                var3.resetReaderIndex();
                p_channelRead_1_.channel().pipeline().remove("legacy_query");
                p_channelRead_1_.fireChannelRead(p_channelRead_2_);
            }
        }
    }

    private void func_151256_a(ChannelHandlerContext p_151256_1_, ByteBuf p_151256_2_) {
        p_151256_1_.pipeline().firstContext().writeAndFlush((Object)p_151256_2_).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }

    private ByteBuf func_151255_a(String p_151255_1_) {
        ByteBuf var2 = Unpooled.buffer();
        var2.writeByte(255);
        char[] var3 = p_151255_1_.toCharArray();
        var2.writeShort(var3.length);
        char[] var4 = var3;
        int var5 = var3.length;
        int var6 = 0;
        while (var6 < var5) {
            char var7 = var4[var6];
            var2.writeChar((int)var7);
            ++var6;
        }
        return var2;
    }
}

