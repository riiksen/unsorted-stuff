/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.server.integrated;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class IntegratedPlayerList
extends ServerConfigurationManager {
    private NBTTagCompound hostPlayerData;
    private static final String __OBFID = "CL_00001128";

    public IntegratedPlayerList(IntegratedServer par1IntegratedServer) {
        super(par1IntegratedServer);
        this.viewDistance = 10;
    }

    @Override
    protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP) {
        if (par1EntityPlayerMP.getCommandSenderName().equals(this.getServerInstance().getServerOwner())) {
            this.hostPlayerData = new NBTTagCompound();
            par1EntityPlayerMP.writeToNBT(this.hostPlayerData);
        }
        super.writePlayerData(par1EntityPlayerMP);
    }

    @Override
    public String func_148542_a(SocketAddress p_148542_1_, GameProfile p_148542_2_) {
        return p_148542_2_.getName().equalsIgnoreCase(this.getServerInstance().getServerOwner()) && this.getPlayerForUsername(p_148542_2_.getName()) != null ? "That name is already taken." : super.func_148542_a(p_148542_1_, p_148542_2_);
    }

    @Override
    public IntegratedServer getServerInstance() {
        return (IntegratedServer)super.getServerInstance();
    }

    @Override
    public NBTTagCompound getHostPlayerData() {
        return this.hostPlayerData;
    }
}

