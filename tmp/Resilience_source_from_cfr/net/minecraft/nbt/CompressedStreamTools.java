/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.util.ReportedException;

public class CompressedStreamTools {
    private static final String __OBFID = "CL_00001226";

    public static NBTTagCompound readCompressed(InputStream par0InputStream) throws IOException {
        NBTTagCompound var2;
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(par0InputStream)));
        try {
            var2 = CompressedStreamTools.read(var1);
        }
        finally {
            var1.close();
        }
        return var2;
    }

    public static void writeCompressed(NBTTagCompound par0NBTTagCompound, OutputStream par1OutputStream) throws IOException {
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(par1OutputStream));
        try {
            CompressedStreamTools.write(par0NBTTagCompound, var2);
        }
        finally {
            var2.close();
        }
    }

    public static NBTTagCompound decompress(byte[] par0ArrayOfByte) throws IOException {
        NBTTagCompound var2;
        DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(par0ArrayOfByte))));
        try {
            var2 = CompressedStreamTools.read(var1);
        }
        finally {
            var1.close();
        }
        return var2;
    }

    public static byte[] compress(NBTTagCompound par0NBTTagCompound) throws IOException {
        ByteArrayOutputStream var1;
        var1 = new ByteArrayOutputStream();
        DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));
        try {
            CompressedStreamTools.write(par0NBTTagCompound, var2);
        }
        finally {
            var2.close();
        }
        return var1.toByteArray();
    }

    public static void safeWrite(NBTTagCompound par0NBTTagCompound, File par1File) throws IOException {
        File var2 = new File(String.valueOf(par1File.getAbsolutePath()) + "_tmp");
        if (var2.exists()) {
            var2.delete();
        }
        CompressedStreamTools.write(par0NBTTagCompound, var2);
        if (par1File.exists()) {
            par1File.delete();
        }
        if (par1File.exists()) {
            throw new IOException("Failed to delete " + par1File);
        }
        var2.renameTo(par1File);
    }

    public static void write(NBTTagCompound par0NBTTagCompound, File par1File) throws IOException {
        DataOutputStream var2 = new DataOutputStream(new FileOutputStream(par1File));
        try {
            CompressedStreamTools.write(par0NBTTagCompound, var2);
        }
        finally {
            var2.close();
        }
    }

    public static NBTTagCompound read(File par0File) throws IOException {
        NBTTagCompound var2;
        if (!par0File.exists()) {
            return null;
        }
        DataInputStream var1 = new DataInputStream(new FileInputStream(par0File));
        try {
            var2 = CompressedStreamTools.read(var1);
        }
        finally {
            var1.close();
        }
        return var2;
    }

    public static NBTTagCompound read(DataInput par0DataInput) throws IOException {
        NBTBase var1 = CompressedStreamTools.func_150664_a(par0DataInput, 0);
        if (var1 instanceof NBTTagCompound) {
            return (NBTTagCompound)var1;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void write(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput) throws IOException {
        CompressedStreamTools.func_150663_a(par0NBTTagCompound, par1DataOutput);
    }

    private static void func_150663_a(NBTBase p_150663_0_, DataOutput p_150663_1_) throws IOException {
        p_150663_1_.writeByte(p_150663_0_.getId());
        if (p_150663_0_.getId() != 0) {
            p_150663_1_.writeUTF("");
            p_150663_0_.write(p_150663_1_);
        }
    }

    private static NBTBase func_150664_a(DataInput p_150664_0_, int p_150664_1_) throws IOException {
        byte var2 = p_150664_0_.readByte();
        if (var2 == 0) {
            return new NBTTagEnd();
        }
        p_150664_0_.readUTF();
        NBTBase var3 = NBTBase.func_150284_a(var2);
        try {
            var3.load(p_150664_0_, p_150664_1_);
            return var3;
        }
        catch (IOException var7) {
            CrashReport var5 = CrashReport.makeCrashReport(var7, "Loading NBT data");
            CrashReportCategory var6 = var5.makeCategory("NBT Tag");
            var6.addCrashSection("Tag name", "[UNNAMED TAG]");
            var6.addCrashSection("Tag type", Byte.valueOf(var2));
            throw new ReportedException(var5);
        }
    }
}

