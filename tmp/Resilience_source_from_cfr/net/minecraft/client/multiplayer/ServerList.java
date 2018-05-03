/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.multiplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final List servers = new ArrayList();
    private static final String __OBFID = "CL_00000891";

    public ServerList(Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
        this.loadServerList();
    }

    public void loadServerList() {
        try {
            this.servers.clear();
            NBTTagCompound var1 = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
            if (var1 == null) {
                return;
            }
            NBTTagList var2 = var1.getTagList("servers", 10);
            int var3 = 0;
            while (var3 < var2.tagCount()) {
                this.servers.add(ServerData.getServerDataFromNBTCompound(var2.getCompoundTagAt(var3)));
                ++var3;
            }
        }
        catch (Exception var4) {
            logger.error("Couldn't load server list", (Throwable)var4);
        }
    }

    public void saveServerList() {
        try {
            NBTTagList var1 = new NBTTagList();
            for (ServerData var3 : this.servers) {
                var1.appendTag(var3.getNBTCompound());
            }
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setTag("servers", var1);
            CompressedStreamTools.safeWrite(var5, new File(this.mc.mcDataDir, "servers.dat"));
        }
        catch (Exception var4) {
            logger.error("Couldn't save server list", (Throwable)var4);
        }
    }

    public ServerData getServerData(int par1) {
        return (ServerData)this.servers.get(par1);
    }

    public void removeServerData(int par1) {
        this.servers.remove(par1);
    }

    public void addServerData(ServerData par1ServerData) {
        this.servers.add(par1ServerData);
    }

    public int countServers() {
        return this.servers.size();
    }

    public void swapServers(int par1, int par2) {
        ServerData var3 = this.getServerData(par1);
        this.servers.set(par1, this.getServerData(par2));
        this.servers.set(par2, var3);
        this.saveServerList();
    }

    public void func_147413_a(int p_147413_1_, ServerData p_147413_2_) {
        this.servers.set(p_147413_1_, p_147413_2_);
    }

    public static void func_147414_b(ServerData p_147414_0_) {
        ServerList var1 = new ServerList(Minecraft.getMinecraft());
        var1.loadServerList();
        int var2 = 0;
        while (var2 < var1.countServers()) {
            ServerData var3 = var1.getServerData(var2);
            if (var3.serverName.equals(p_147414_0_.serverName) && var3.serverIP.equals(p_147414_0_.serverIP)) {
                var1.func_147413_a(var2, p_147414_0_);
                break;
            }
            ++var2;
        }
        var1.saveServerList();
    }
}

