/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.chunk.storage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.server.MinecraftServer;

public class RegionFile {
    private static final byte[] emptySector = new byte[4096];
    private final File fileName;
    private RandomAccessFile dataFile;
    private final int[] offsets = new int[1024];
    private final int[] chunkTimestamps = new int[1024];
    private ArrayList sectorFree;
    private int sizeDelta;
    private long lastModified;
    private static final String __OBFID = "CL_00000381";

    public RegionFile(File par1File) {
        this.fileName = par1File;
        this.sizeDelta = 0;
        try {
            int var2;
            int var4;
            if (par1File.exists()) {
                this.lastModified = par1File.lastModified();
            }
            this.dataFile = new RandomAccessFile(par1File, "rw");
            if (this.dataFile.length() < 4096) {
                var2 = 0;
                while (var2 < 1024) {
                    this.dataFile.writeInt(0);
                    ++var2;
                }
                var2 = 0;
                while (var2 < 1024) {
                    this.dataFile.writeInt(0);
                    ++var2;
                }
                this.sizeDelta += 8192;
            }
            if ((this.dataFile.length() & 4095) != 0) {
                var2 = 0;
                while ((long)var2 < (this.dataFile.length() & 4095)) {
                    this.dataFile.write(0);
                    ++var2;
                }
            }
            var2 = (int)this.dataFile.length() / 4096;
            this.sectorFree = new ArrayList(var2);
            int var3 = 0;
            while (var3 < var2) {
                this.sectorFree.add(true);
                ++var3;
            }
            this.sectorFree.set(0, false);
            this.sectorFree.set(1, false);
            this.dataFile.seek(0);
            var3 = 0;
            while (var3 < 1024) {
                this.offsets[var3] = var4 = this.dataFile.readInt();
                if (var4 != 0 && (var4 >> 8) + (var4 & 255) <= this.sectorFree.size()) {
                    int var5 = 0;
                    while (var5 < (var4 & 255)) {
                        this.sectorFree.set((var4 >> 8) + var5, false);
                        ++var5;
                    }
                }
                ++var3;
            }
            var3 = 0;
            while (var3 < 1024) {
                this.chunkTimestamps[var3] = var4 = this.dataFile.readInt();
                ++var3;
            }
        }
        catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    public synchronized DataInputStream getChunkDataInputStream(int par1, int par2) {
        int var6;
        block12 : {
            block11 : {
                int var5;
                int var4;
                block10 : {
                    int var3;
                    block9 : {
                        if (this.outOfBounds(par1, par2)) {
                            return null;
                        }
                        try {
                            var3 = this.getOffset(par1, par2);
                            if (var3 != 0) break block9;
                            return null;
                        }
                        catch (IOException var9) {
                            return null;
                        }
                    }
                    var4 = var3 >> 8;
                    var5 = var3 & 255;
                    if (var4 + var5 <= this.sectorFree.size()) break block10;
                    return null;
                }
                this.dataFile.seek(var4 * 4096);
                var6 = this.dataFile.readInt();
                if (var6 <= 4096 * var5) break block11;
                return null;
            }
            if (var6 > 0) break block12;
            return null;
        }
        byte var7 = this.dataFile.readByte();
        if (var7 == 1) {
            byte[] var8 = new byte[var6 - 1];
            this.dataFile.read(var8);
            return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var8))));
        }
        if (var7 == 2) {
            byte[] var8 = new byte[var6 - 1];
            this.dataFile.read(var8);
            return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var8))));
        }
        return null;
    }

    public DataOutputStream getChunkDataOutputStream(int par1, int par2) {
        return this.outOfBounds(par1, par2) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(par1, par2)));
    }

    protected synchronized void write(int par1, int par2, byte[] par3ArrayOfByte, int par4) {
        try {
            int var5 = this.getOffset(par1, par2);
            int var6 = var5 >> 8;
            int var7 = var5 & 255;
            int var8 = (par4 + 5) / 4096 + 1;
            if (var8 >= 256) {
                return;
            }
            if (var6 != 0 && var7 == var8) {
                this.write(var6, par3ArrayOfByte, par4);
            } else {
                int var11;
                int var9 = 0;
                while (var9 < var7) {
                    this.sectorFree.set(var6 + var9, true);
                    ++var9;
                }
                var9 = this.sectorFree.indexOf(true);
                int var10 = 0;
                if (var9 != -1) {
                    var11 = var9;
                    while (var11 < this.sectorFree.size()) {
                        if (var10 != 0) {
                            var10 = ((Boolean)this.sectorFree.get(var11)).booleanValue() ? ++var10 : 0;
                        } else if (((Boolean)this.sectorFree.get(var11)).booleanValue()) {
                            var9 = var11;
                            var10 = 1;
                        }
                        if (var10 >= var8) break;
                        ++var11;
                    }
                }
                if (var10 >= var8) {
                    var6 = var9;
                    this.setOffset(par1, par2, var9 << 8 | var8);
                    var11 = 0;
                    while (var11 < var8) {
                        this.sectorFree.set(var6 + var11, false);
                        ++var11;
                    }
                    this.write(var6, par3ArrayOfByte, par4);
                } else {
                    this.dataFile.seek(this.dataFile.length());
                    var6 = this.sectorFree.size();
                    var11 = 0;
                    while (var11 < var8) {
                        this.dataFile.write(emptySector);
                        this.sectorFree.add(false);
                        ++var11;
                    }
                    this.sizeDelta += 4096 * var8;
                    this.write(var6, par3ArrayOfByte, par4);
                    this.setOffset(par1, par2, var6 << 8 | var8);
                }
            }
            this.setChunkTimestamp(par1, par2, (int)(MinecraftServer.getSystemTimeMillis() / 1000));
        }
        catch (IOException var12) {
            var12.printStackTrace();
        }
    }

    private void write(int par1, byte[] par2ArrayOfByte, int par3) throws IOException {
        this.dataFile.seek(par1 * 4096);
        this.dataFile.writeInt(par3 + 1);
        this.dataFile.writeByte(2);
        this.dataFile.write(par2ArrayOfByte, 0, par3);
    }

    private boolean outOfBounds(int par1, int par2) {
        if (par1 >= 0 && par1 < 32 && par2 >= 0 && par2 < 32) {
            return false;
        }
        return true;
    }

    private int getOffset(int par1, int par2) {
        return this.offsets[par1 + par2 * 32];
    }

    public boolean isChunkSaved(int par1, int par2) {
        if (this.getOffset(par1, par2) != 0) {
            return true;
        }
        return false;
    }

    private void setOffset(int par1, int par2, int par3) throws IOException {
        this.offsets[par1 + par2 * 32] = par3;
        this.dataFile.seek((par1 + par2 * 32) * 4);
        this.dataFile.writeInt(par3);
    }

    private void setChunkTimestamp(int par1, int par2, int par3) throws IOException {
        this.chunkTimestamps[par1 + par2 * 32] = par3;
        this.dataFile.seek(4096 + (par1 + par2 * 32) * 4);
        this.dataFile.writeInt(par3);
    }

    public void close() throws IOException {
        if (this.dataFile != null) {
            this.dataFile.close();
        }
    }

    class ChunkBuffer
    extends ByteArrayOutputStream {
        private int chunkX;
        private int chunkZ;
        private static final String __OBFID = "CL_00000382";

        public ChunkBuffer(int par2, int par3) {
            super(8096);
            this.chunkX = par2;
            this.chunkZ = par3;
        }

        @Override
        public void close() throws IOException {
            RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
        }
    }

}

