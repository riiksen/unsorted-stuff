/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.buffer.ByteBufProcessor
 *  io.netty.util.ReferenceCounted
 */
package net.minecraft.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class PacketBuffer
extends ByteBuf {
    private final ByteBuf field_150794_a;
    private static final String __OBFID = "CL_00001251";

    public PacketBuffer(ByteBuf p_i45154_1_) {
        this.field_150794_a = p_i45154_1_;
    }

    public static int getVarIntSize(int p_150790_0_) {
        return (p_150790_0_ & -128) == 0 ? 1 : ((p_150790_0_ & -16384) == 0 ? 2 : ((p_150790_0_ & -2097152) == 0 ? 3 : ((p_150790_0_ & -268435456) == 0 ? 4 : 5)));
    }

    public int readVarIntFromBuffer() {
        byte var3;
        int var1 = 0;
        int var2 = 0;
        do {
            var3 = this.readByte();
            var1 |= (var3 & 127) << var2++ * 7;
            if (var2 <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((var3 & 128) == 128);
        return var1;
    }

    public void writeVarIntToBuffer(int p_150787_1_) {
        while ((p_150787_1_ & -128) != 0) {
            this.writeByte(p_150787_1_ & 127 | 128);
            p_150787_1_ >>>= 7;
        }
        this.writeByte(p_150787_1_);
    }

    public void writeNBTTagCompoundToBuffer(NBTTagCompound p_150786_1_) throws IOException {
        if (p_150786_1_ == null) {
            this.writeShort(-1);
        } else {
            byte[] var2 = CompressedStreamTools.compress(p_150786_1_);
            this.writeShort((short)var2.length);
            this.writeBytes(var2);
        }
    }

    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        short var1 = this.readShort();
        if (var1 < 0) {
            return null;
        }
        byte[] var2 = new byte[var1];
        this.readBytes(var2);
        return CompressedStreamTools.decompress(var2);
    }

    public void writeItemStackToBuffer(ItemStack p_150788_1_) throws IOException {
        if (p_150788_1_ == null) {
            this.writeShort(-1);
        } else {
            this.writeShort(Item.getIdFromItem(p_150788_1_.getItem()));
            this.writeByte(p_150788_1_.stackSize);
            this.writeShort(p_150788_1_.getItemDamage());
            NBTTagCompound var2 = null;
            if (p_150788_1_.getItem().isDamageable() || p_150788_1_.getItem().getShareTag()) {
                var2 = p_150788_1_.stackTagCompound;
            }
            this.writeNBTTagCompoundToBuffer(var2);
        }
    }

    public ItemStack readItemStackFromBuffer() throws IOException {
        ItemStack var1 = null;
        short var2 = this.readShort();
        if (var2 >= 0) {
            byte var3 = this.readByte();
            short var4 = this.readShort();
            var1 = new ItemStack(Item.getItemById(var2), (int)var3, (int)var4);
            try {
                var1.stackTagCompound = this.readNBTTagCompoundFromBuffer();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return var1;
    }

    public String readStringFromBuffer(int p_150789_1_) throws IOException {
        int var2 = this.readVarIntFromBuffer();
        if (var2 > p_150789_1_ * 4) {
            throw new IOException("The received encoded string buffer length is longer than maximum allowed (" + var2 + " > " + p_150789_1_ * 4 + ")");
        }
        if (var2 < 0) {
            throw new IOException("The received encoded string buffer length is less than zero! Weird string!");
        }
        String var3 = new String(this.readBytes(var2).array(), Charsets.UTF_8);
        if (var3.length() > p_150789_1_) {
            throw new IOException("The received string length is longer than maximum allowed (" + var2 + " > " + p_150789_1_ + ")");
        }
        return var3;
    }

    public void writeStringToBuffer(String p_150785_1_) throws IOException {
        byte[] var2 = p_150785_1_.getBytes(Charsets.UTF_8);
        if (var2.length > 32767) {
            throw new IOException("String too big (was " + p_150785_1_.length() + " bytes encoded, max " + 32767 + ")");
        }
        this.writeVarIntToBuffer(var2.length);
        this.writeBytes(var2);
    }

    public int capacity() {
        return this.field_150794_a.capacity();
    }

    public ByteBuf capacity(int p_capacity_1_) {
        return this.field_150794_a.capacity(p_capacity_1_);
    }

    public int maxCapacity() {
        return this.field_150794_a.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.field_150794_a.alloc();
    }

    public ByteOrder order() {
        return this.field_150794_a.order();
    }

    public ByteBuf order(ByteOrder p_order_1_) {
        return this.field_150794_a.order(p_order_1_);
    }

    public ByteBuf unwrap() {
        return this.field_150794_a.unwrap();
    }

    public boolean isDirect() {
        return this.field_150794_a.isDirect();
    }

    public int readerIndex() {
        return this.field_150794_a.readerIndex();
    }

    public ByteBuf readerIndex(int p_readerIndex_1_) {
        return this.field_150794_a.readerIndex(p_readerIndex_1_);
    }

    public int writerIndex() {
        return this.field_150794_a.writerIndex();
    }

    public ByteBuf writerIndex(int p_writerIndex_1_) {
        return this.field_150794_a.writerIndex(p_writerIndex_1_);
    }

    public ByteBuf setIndex(int p_setIndex_1_, int p_setIndex_2_) {
        return this.field_150794_a.setIndex(p_setIndex_1_, p_setIndex_2_);
    }

    public int readableBytes() {
        return this.field_150794_a.readableBytes();
    }

    public int writableBytes() {
        return this.field_150794_a.writableBytes();
    }

    public int maxWritableBytes() {
        return this.field_150794_a.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.field_150794_a.isReadable();
    }

    public boolean isReadable(int p_isReadable_1_) {
        return this.field_150794_a.isReadable(p_isReadable_1_);
    }

    public boolean isWritable() {
        return this.field_150794_a.isWritable();
    }

    public boolean isWritable(int p_isWritable_1_) {
        return this.field_150794_a.isWritable(p_isWritable_1_);
    }

    public ByteBuf clear() {
        return this.field_150794_a.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.field_150794_a.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.field_150794_a.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.field_150794_a.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.field_150794_a.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.field_150794_a.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.field_150794_a.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int p_ensureWritable_1_) {
        return this.field_150794_a.ensureWritable(p_ensureWritable_1_);
    }

    public int ensureWritable(int p_ensureWritable_1_, boolean p_ensureWritable_2_) {
        return this.field_150794_a.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
    }

    public boolean getBoolean(int p_getBoolean_1_) {
        return this.field_150794_a.getBoolean(p_getBoolean_1_);
    }

    public byte getByte(int p_getByte_1_) {
        return this.field_150794_a.getByte(p_getByte_1_);
    }

    public short getUnsignedByte(int p_getUnsignedByte_1_) {
        return this.field_150794_a.getUnsignedByte(p_getUnsignedByte_1_);
    }

    public short getShort(int p_getShort_1_) {
        return this.field_150794_a.getShort(p_getShort_1_);
    }

    public int getUnsignedShort(int p_getUnsignedShort_1_) {
        return this.field_150794_a.getUnsignedShort(p_getUnsignedShort_1_);
    }

    public int getMedium(int p_getMedium_1_) {
        return this.field_150794_a.getMedium(p_getMedium_1_);
    }

    public int getUnsignedMedium(int p_getUnsignedMedium_1_) {
        return this.field_150794_a.getUnsignedMedium(p_getUnsignedMedium_1_);
    }

    public int getInt(int p_getInt_1_) {
        return this.field_150794_a.getInt(p_getInt_1_);
    }

    public long getUnsignedInt(int p_getUnsignedInt_1_) {
        return this.field_150794_a.getUnsignedInt(p_getUnsignedInt_1_);
    }

    public long getLong(int p_getLong_1_) {
        return this.field_150794_a.getLong(p_getLong_1_);
    }

    public char getChar(int p_getChar_1_) {
        return this.field_150794_a.getChar(p_getChar_1_);
    }

    public float getFloat(int p_getFloat_1_) {
        return this.field_150794_a.getFloat(p_getFloat_1_);
    }

    public double getDouble(int p_getDouble_1_) {
        return this.field_150794_a.getDouble(p_getDouble_1_);
    }

    public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
    }

    public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }

    public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }

    public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
    }

    public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }

    public ByteBuf getBytes(int p_getBytes_1_, ByteBuffer p_getBytes_2_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
    }

    public ByteBuf getBytes(int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_) throws IOException {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }

    public int getBytes(int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_) throws IOException {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }

    public ByteBuf setBoolean(int p_setBoolean_1_, boolean p_setBoolean_2_) {
        return this.field_150794_a.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
    }

    public ByteBuf setByte(int p_setByte_1_, int p_setByte_2_) {
        return this.field_150794_a.setByte(p_setByte_1_, p_setByte_2_);
    }

    public ByteBuf setShort(int p_setShort_1_, int p_setShort_2_) {
        return this.field_150794_a.setShort(p_setShort_1_, p_setShort_2_);
    }

    public ByteBuf setMedium(int p_setMedium_1_, int p_setMedium_2_) {
        return this.field_150794_a.setMedium(p_setMedium_1_, p_setMedium_2_);
    }

    public ByteBuf setInt(int p_setInt_1_, int p_setInt_2_) {
        return this.field_150794_a.setInt(p_setInt_1_, p_setInt_2_);
    }

    public ByteBuf setLong(int p_setLong_1_, long p_setLong_2_) {
        return this.field_150794_a.setLong(p_setLong_1_, p_setLong_2_);
    }

    public ByteBuf setChar(int p_setChar_1_, int p_setChar_2_) {
        return this.field_150794_a.setChar(p_setChar_1_, p_setChar_2_);
    }

    public ByteBuf setFloat(int p_setFloat_1_, float p_setFloat_2_) {
        return this.field_150794_a.setFloat(p_setFloat_1_, p_setFloat_2_);
    }

    public ByteBuf setDouble(int p_setDouble_1_, double p_setDouble_2_) {
        return this.field_150794_a.setDouble(p_setDouble_1_, p_setDouble_2_);
    }

    public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
    }

    public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }

    public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }

    public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
    }

    public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }

    public ByteBuf setBytes(int p_setBytes_1_, ByteBuffer p_setBytes_2_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
    }

    public int setBytes(int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_) throws IOException {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }

    public int setBytes(int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_) throws IOException {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }

    public ByteBuf setZero(int p_setZero_1_, int p_setZero_2_) {
        return this.field_150794_a.setZero(p_setZero_1_, p_setZero_2_);
    }

    public boolean readBoolean() {
        return this.field_150794_a.readBoolean();
    }

    public byte readByte() {
        return this.field_150794_a.readByte();
    }

    public short readUnsignedByte() {
        return this.field_150794_a.readUnsignedByte();
    }

    public short readShort() {
        return this.field_150794_a.readShort();
    }

    public int readUnsignedShort() {
        return this.field_150794_a.readUnsignedShort();
    }

    public int readMedium() {
        return this.field_150794_a.readMedium();
    }

    public int readUnsignedMedium() {
        return this.field_150794_a.readUnsignedMedium();
    }

    public int readInt() {
        return this.field_150794_a.readInt();
    }

    public long readUnsignedInt() {
        return this.field_150794_a.readUnsignedInt();
    }

    public long readLong() {
        return this.field_150794_a.readLong();
    }

    public char readChar() {
        return this.field_150794_a.readChar();
    }

    public float readFloat() {
        return this.field_150794_a.readFloat();
    }

    public double readDouble() {
        return this.field_150794_a.readDouble();
    }

    public ByteBuf readBytes(int p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }

    public ByteBuf readSlice(int p_readSlice_1_) {
        return this.field_150794_a.readSlice(p_readSlice_1_);
    }

    public ByteBuf readBytes(ByteBuf p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }

    public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_) {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
    }

    public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }

    public ByteBuf readBytes(byte[] p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }

    public ByteBuf readBytes(byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }

    public ByteBuf readBytes(ByteBuffer p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }

    public ByteBuf readBytes(OutputStream p_readBytes_1_, int p_readBytes_2_) throws IOException {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
    }

    public int readBytes(GatheringByteChannel p_readBytes_1_, int p_readBytes_2_) throws IOException {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
    }

    public ByteBuf skipBytes(int p_skipBytes_1_) {
        return this.field_150794_a.skipBytes(p_skipBytes_1_);
    }

    public ByteBuf writeBoolean(boolean p_writeBoolean_1_) {
        return this.field_150794_a.writeBoolean(p_writeBoolean_1_);
    }

    public ByteBuf writeByte(int p_writeByte_1_) {
        return this.field_150794_a.writeByte(p_writeByte_1_);
    }

    public ByteBuf writeShort(int p_writeShort_1_) {
        return this.field_150794_a.writeShort(p_writeShort_1_);
    }

    public ByteBuf writeMedium(int p_writeMedium_1_) {
        return this.field_150794_a.writeMedium(p_writeMedium_1_);
    }

    public ByteBuf writeInt(int p_writeInt_1_) {
        return this.field_150794_a.writeInt(p_writeInt_1_);
    }

    public ByteBuf writeLong(long p_writeLong_1_) {
        return this.field_150794_a.writeLong(p_writeLong_1_);
    }

    public ByteBuf writeChar(int p_writeChar_1_) {
        return this.field_150794_a.writeChar(p_writeChar_1_);
    }

    public ByteBuf writeFloat(float p_writeFloat_1_) {
        return this.field_150794_a.writeFloat(p_writeFloat_1_);
    }

    public ByteBuf writeDouble(double p_writeDouble_1_) {
        return this.field_150794_a.writeDouble(p_writeDouble_1_);
    }

    public ByteBuf writeBytes(ByteBuf p_writeBytes_1_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_);
    }

    public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }

    public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }

    public ByteBuf writeBytes(byte[] p_writeBytes_1_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_);
    }

    public ByteBuf writeBytes(byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }

    public ByteBuf writeBytes(ByteBuffer p_writeBytes_1_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_);
    }

    public int writeBytes(InputStream p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }

    public int writeBytes(ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }

    public ByteBuf writeZero(int p_writeZero_1_) {
        return this.field_150794_a.writeZero(p_writeZero_1_);
    }

    public int indexOf(int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_) {
        return this.field_150794_a.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
    }

    public int bytesBefore(byte p_bytesBefore_1_) {
        return this.field_150794_a.bytesBefore(p_bytesBefore_1_);
    }

    public int bytesBefore(int p_bytesBefore_1_, byte p_bytesBefore_2_) {
        return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
    }

    public int bytesBefore(int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_) {
        return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
    }

    public int forEachByte(ByteBufProcessor p_forEachByte_1_) {
        return this.field_150794_a.forEachByte(p_forEachByte_1_);
    }

    public int forEachByte(int p_forEachByte_1_, int p_forEachByte_2_, ByteBufProcessor p_forEachByte_3_) {
        return this.field_150794_a.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
    }

    public int forEachByteDesc(ByteBufProcessor p_forEachByteDesc_1_) {
        return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_);
    }

    public int forEachByteDesc(int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteBufProcessor p_forEachByteDesc_3_) {
        return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
    }

    public ByteBuf copy() {
        return this.field_150794_a.copy();
    }

    public ByteBuf copy(int p_copy_1_, int p_copy_2_) {
        return this.field_150794_a.copy(p_copy_1_, p_copy_2_);
    }

    public ByteBuf slice() {
        return this.field_150794_a.slice();
    }

    public ByteBuf slice(int p_slice_1_, int p_slice_2_) {
        return this.field_150794_a.slice(p_slice_1_, p_slice_2_);
    }

    public ByteBuf duplicate() {
        return this.field_150794_a.duplicate();
    }

    public int nioBufferCount() {
        return this.field_150794_a.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.field_150794_a.nioBuffer();
    }

    public ByteBuffer nioBuffer(int p_nioBuffer_1_, int p_nioBuffer_2_) {
        return this.field_150794_a.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
    }

    public ByteBuffer internalNioBuffer(int p_internalNioBuffer_1_, int p_internalNioBuffer_2_) {
        return this.field_150794_a.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
    }

    public ByteBuffer[] nioBuffers() {
        return this.field_150794_a.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int p_nioBuffers_1_, int p_nioBuffers_2_) {
        return this.field_150794_a.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
    }

    public boolean hasArray() {
        return this.field_150794_a.hasArray();
    }

    public byte[] array() {
        return this.field_150794_a.array();
    }

    public int arrayOffset() {
        return this.field_150794_a.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.field_150794_a.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.field_150794_a.memoryAddress();
    }

    public String toString(Charset p_toString_1_) {
        return this.field_150794_a.toString(p_toString_1_);
    }

    public String toString(int p_toString_1_, int p_toString_2_, Charset p_toString_3_) {
        return this.field_150794_a.toString(p_toString_1_, p_toString_2_, p_toString_3_);
    }

    public int hashCode() {
        return this.field_150794_a.hashCode();
    }

    public boolean equals(Object par1Obj) {
        return this.field_150794_a.equals(par1Obj);
    }

    public int compareTo(ByteBuf par1Obj) {
        return this.field_150794_a.compareTo(par1Obj);
    }

    public String toString() {
        return this.field_150794_a.toString();
    }

    public ByteBuf retain(int p_retain_1_) {
        return this.field_150794_a.retain(p_retain_1_);
    }

    public ByteBuf retain() {
        return this.field_150794_a.retain();
    }

    public int refCnt() {
        return this.field_150794_a.refCnt();
    }

    public boolean release() {
        return this.field_150794_a.release();
    }

    public boolean release(int p_release_1_) {
        return this.field_150794_a.release(p_release_1_);
    }
}

