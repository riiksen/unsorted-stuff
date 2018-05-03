/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.minecraft.block.Block;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class S22PacketMultiBlockChange
extends Packet {
    private static final Logger logger = LogManager.getLogger();
    private ChunkCoordIntPair field_148925_b;
    private byte[] field_148926_c;
    private int field_148924_d;
    private static final String __OBFID = "CL_00001290";

    public S22PacketMultiBlockChange() {
    }

    public S22PacketMultiBlockChange(int p_i45181_1_, short[] p_i45181_2_, Chunk p_i45181_3_) {
        this.field_148925_b = new ChunkCoordIntPair(p_i45181_3_.xPosition, p_i45181_3_.zPosition);
        this.field_148924_d = p_i45181_1_;
        int var4 = 4 * p_i45181_1_;
        try {
            ByteArrayOutputStream var5 = new ByteArrayOutputStream(var4);
            DataOutputStream var6 = new DataOutputStream(var5);
            int var7 = 0;
            while (var7 < p_i45181_1_) {
                int var8 = p_i45181_2_[var7] >> 12 & 15;
                int var9 = p_i45181_2_[var7] >> 8 & 15;
                int var10 = p_i45181_2_[var7] & 255;
                var6.writeShort(p_i45181_2_[var7]);
                var6.writeShort((short)((Block.getIdFromBlock(p_i45181_3_.func_150810_a(var8, var10, var9)) & 4095) << 4 | p_i45181_3_.getBlockMetadata(var8, var10, var9) & 15));
                ++var7;
            }
            this.field_148926_c = var5.toByteArray();
            if (this.field_148926_c.length != var4) {
                throw new RuntimeException("Expected length " + var4 + " doesn't match received length " + this.field_148926_c.length);
            }
        }
        catch (IOException var11) {
            logger.error("Couldn't create bulk block update packet", (Throwable)var11);
            this.field_148926_c = null;
        }
    }

    @Override
    public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
        this.field_148925_b = new ChunkCoordIntPair(p_148837_1_.readInt(), p_148837_1_.readInt());
        this.field_148924_d = p_148837_1_.readShort() & 65535;
        int var2 = p_148837_1_.readInt();
        if (var2 > 0) {
            this.field_148926_c = new byte[var2];
            p_148837_1_.readBytes(this.field_148926_c);
        }
    }

    @Override
    public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_148925_b.chunkXPos);
        p_148840_1_.writeInt(this.field_148925_b.chunkZPos);
        p_148840_1_.writeShort((short)this.field_148924_d);
        if (this.field_148926_c != null) {
            p_148840_1_.writeInt(this.field_148926_c.length);
            p_148840_1_.writeBytes(this.field_148926_c);
        } else {
            p_148840_1_.writeInt(0);
        }
    }

    public void processPacket(INetHandlerPlayClient p_148923_1_) {
        p_148923_1_.handleMultiBlockChange(this);
    }

    @Override
    public String serialize() {
        return String.format("xc=%d, zc=%d, count=%d", this.field_148925_b.chunkXPos, this.field_148925_b.chunkZPos, this.field_148924_d);
    }

    public ChunkCoordIntPair func_148920_c() {
        return this.field_148925_b;
    }

    public byte[] func_148921_d() {
        return this.field_148926_c;
    }

    public int func_148922_e() {
        return this.field_148924_d;
    }

    @Override
    public void processPacket(INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}

