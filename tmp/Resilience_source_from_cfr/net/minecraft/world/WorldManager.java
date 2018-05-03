/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class WorldManager
implements IWorldAccess {
    private MinecraftServer mcServer;
    private WorldServer theWorldServer;
    private static final String __OBFID = "CL_00001433";

    public WorldManager(MinecraftServer par1MinecraftServer, WorldServer par2WorldServer) {
        this.mcServer = par1MinecraftServer;
        this.theWorldServer = par2WorldServer;
    }

    @Override
    public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
    }

    @Override
    public void onEntityCreate(Entity par1Entity) {
        this.theWorldServer.getEntityTracker().addEntityToTracker(par1Entity);
    }

    @Override
    public void onEntityDestroy(Entity par1Entity) {
        this.theWorldServer.getEntityTracker().removeEntityFromAllTrackingPlayers(par1Entity);
    }

    @Override
    public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9) {
        this.mcServer.getConfigurationManager().func_148541_a(par2, par4, par6, par8 > 1.0f ? (double)(16.0f * par8) : 16.0, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(par1Str, par2, par4, par6, par8, par9));
    }

    @Override
    public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10) {
        this.mcServer.getConfigurationManager().func_148543_a(par1EntityPlayer, par3, par5, par7, par9 > 1.0f ? (double)(16.0f * par9) : 16.0, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(par2Str, par3, par5, par7, par9, par10));
    }

    @Override
    public void markBlockRangeForRenderUpdate(int p_147585_1_, int p_147585_2_, int p_147585_3_, int p_147585_4_, int p_147585_5_, int p_147585_6_) {
    }

    @Override
    public void markBlockForUpdate(int p_147586_1_, int p_147586_2_, int p_147586_3_) {
        this.theWorldServer.getPlayerManager().func_151250_a(p_147586_1_, p_147586_2_, p_147586_3_);
    }

    @Override
    public void markBlockForRenderUpdate(int p_147588_1_, int p_147588_2_, int p_147588_3_) {
    }

    @Override
    public void playRecord(String par1Str, int par2, int par3, int par4) {
    }

    @Override
    public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6) {
        this.mcServer.getConfigurationManager().func_148543_a(par1EntityPlayer, par3, par4, par5, 64.0, this.theWorldServer.provider.dimensionId, new S28PacketEffect(par2, par3, par4, par5, par6, false));
    }

    @Override
    public void broadcastSound(int par1, int par2, int par3, int par4, int par5) {
        this.mcServer.getConfigurationManager().func_148540_a(new S28PacketEffect(par1, par2, par3, par4, par5, true));
    }

    @Override
    public void destroyBlockPartially(int p_147587_1_, int p_147587_2_, int p_147587_3_, int p_147587_4_, int p_147587_5_) {
        for (EntityPlayerMP var7 : this.mcServer.getConfigurationManager().playerEntityList) {
            double var12;
            double var8;
            double var10;
            if (var7 == null || var7.worldObj != this.theWorldServer || var7.getEntityId() == p_147587_1_ || (var8 = (double)p_147587_2_ - var7.posX) * var8 + (var10 = (double)p_147587_3_ - var7.posY) * var10 + (var12 = (double)p_147587_4_ - var7.posZ) * var12 >= 1024.0) continue;
            var7.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(p_147587_1_, p_147587_2_, p_147587_3_, p_147587_4_, p_147587_5_));
        }
    }

    @Override
    public void onStaticEntitiesChanged() {
    }
}

