/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 *  io.netty.util.Attribute
 *  io.netty.util.AttributeKey
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.Marker
 *  org.apache.logging.log4j.MarkerManager
 */
package net.minecraft.util;

import com.google.common.collect.BiMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.io.IOException;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MessageSerializer
extends MessageToByteEncoder {
    private static final Logger logger = LogManager.getLogger();
    private static final Marker field_150797_b = MarkerManager.getMarker((String)"PACKET_SENT", (Marker)NetworkManager.logMarkerPackets);
    private static final String __OBFID = "CL_00001253";

    protected void encode(ChannelHandlerContext p_150796_1_, Packet p_150796_2_, ByteBuf p_150796_3_) throws IOException {
        Integer var4 = (Integer)((BiMap)p_150796_1_.channel().attr(NetworkManager.attrKeySendable).get()).inverse().get(p_150796_2_.getClass());
        if (logger.isDebugEnabled()) {
            logger.debug(field_150797_b, "OUT: [{}:{}] {}[{}]", new Object[]{p_150796_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), var4, p_150796_2_.getClass().getName(), p_150796_2_.serialize()});
        }
        if (var4 == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        PacketBuffer var5 = new PacketBuffer(p_150796_3_);
        var5.writeVarIntToBuffer(var4);
        p_150796_2_.writePacketData(var5);
    }

    protected void encode(ChannelHandlerContext p_encode_1_, Object p_encode_2_, ByteBuf p_encode_3_) throws IOException {
        this.encode(p_encode_1_, (Packet)p_encode_2_, p_encode_3_);
    }
}

