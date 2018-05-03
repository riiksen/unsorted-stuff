/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
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
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MessageDeserializer
extends ByteToMessageDecoder {
    private static final Logger logger = LogManager.getLogger();
    private static final Marker field_150799_b = MarkerManager.getMarker((String)"PACKET_RECEIVED", (Marker)NetworkManager.logMarkerPackets);
    private static final String __OBFID = "CL_00001252";

    protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List p_decode_3_) throws IOException {
        if (p_decode_2_.readableBytes() != 0) {
            PacketBuffer var4 = new PacketBuffer(p_decode_2_);
            int var5 = var4.readVarIntFromBuffer();
            Packet var6 = Packet.generatePacket((BiMap)p_decode_1_.channel().attr(NetworkManager.attrKeyReceivable).get(), var5);
            if (var6 == null) {
                throw new IOException("Bad packet id " + var5);
            }
            var6.readPacketData(var4);
            if (var4.readableBytes() > 0) {
                throw new IOException("Packet was larger than I expected, found " + var4.readableBytes() + " bytes extra whilst reading packet " + var5);
            }
            p_decode_3_.add(var6);
            if (logger.isDebugEnabled()) {
                logger.debug(field_150799_b, " IN: [{}:{}] {}[{}]", new Object[]{p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), var5, var6.getClass().getName(), var6.serialize()});
            }
        }
    }
}

