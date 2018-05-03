/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  io.netty.handler.codec.CorruptedFrameException
 */
package net.minecraft.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import net.minecraft.network.PacketBuffer;

public class MessageDeserializer2
extends ByteToMessageDecoder {
    private static final String __OBFID = "CL_00001255";

    protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List p_decode_3_) {
        p_decode_2_.markReaderIndex();
        byte[] var4 = new byte[3];
        int var5 = 0;
        while (var5 < var4.length) {
            if (!p_decode_2_.isReadable()) {
                p_decode_2_.resetReaderIndex();
                return;
            }
            var4[var5] = p_decode_2_.readByte();
            if (var4[var5] >= 0) {
                int var6 = new PacketBuffer(Unpooled.wrappedBuffer((byte[])var4)).readVarIntFromBuffer();
                if (p_decode_2_.readableBytes() < var6) {
                    p_decode_2_.resetReaderIndex();
                    return;
                }
                p_decode_3_.add(p_decode_2_.readBytes(var6));
                return;
            }
            ++var5;
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}

