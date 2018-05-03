/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.potion.PotionEffect;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.PlayerPositionComparator;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ServerConfigurationManager {
    private static final Logger logger = LogManager.getLogger();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private final MinecraftServer mcServer;
    public final List playerEntityList = new ArrayList();
    private final BanList bannedPlayers = new BanList(new File("banned-players.txt"));
    private final BanList bannedIPs = new BanList(new File("banned-ips.txt"));
    private final Set ops = new HashSet();
    private final Set whiteListedPlayers = new HashSet();
    private final Map field_148547_k = Maps.newHashMap();
    private IPlayerFileData playerNBTManagerObj;
    private boolean whiteListEnforced;
    protected int maxPlayers;
    protected int viewDistance;
    private WorldSettings.GameType gameType;
    private boolean commandsAllowedForAll;
    private int playerPingIndex;
    private static final String __OBFID = "CL_00001423";

    public ServerConfigurationManager(MinecraftServer par1MinecraftServer) {
        this.mcServer = par1MinecraftServer;
        this.bannedPlayers.setListActive(false);
        this.bannedIPs.setListActive(false);
        this.maxPlayers = 8;
    }

    public void initializeConnectionToPlayer(NetworkManager par1INetworkManager, EntityPlayerMP par2EntityPlayerMP) {
        Entity var11;
        NBTTagCompound var3 = this.readPlayerDataFromFile(par2EntityPlayerMP);
        par2EntityPlayerMP.setWorld(this.mcServer.worldServerForDimension(par2EntityPlayerMP.dimension));
        par2EntityPlayerMP.theItemInWorldManager.setWorld((WorldServer)par2EntityPlayerMP.worldObj);
        String var4 = "local";
        if (par1INetworkManager.getSocketAddress() != null) {
            var4 = par1INetworkManager.getSocketAddress().toString();
        }
        logger.info(String.valueOf(par2EntityPlayerMP.getCommandSenderName()) + "[" + var4 + "] logged in with entity id " + par2EntityPlayerMP.getEntityId() + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
        WorldServer var5 = this.mcServer.worldServerForDimension(par2EntityPlayerMP.dimension);
        ChunkCoordinates var6 = var5.getSpawnPoint();
        this.func_72381_a(par2EntityPlayerMP, null, var5);
        NetHandlerPlayServer var7 = new NetHandlerPlayServer(this.mcServer, par1INetworkManager, par2EntityPlayerMP);
        var7.sendPacket(new S01PacketJoinGame(par2EntityPlayerMP.getEntityId(), par2EntityPlayerMP.theItemInWorldManager.getGameType(), var5.getWorldInfo().isHardcoreModeEnabled(), var5.provider.dimensionId, var5.difficultySetting, this.getMaxPlayers(), var5.getWorldInfo().getTerrainType()));
        var7.sendPacket(new S3FPacketCustomPayload("MC|Brand", this.getServerInstance().getServerModName().getBytes(Charsets.UTF_8)));
        var7.sendPacket(new S05PacketSpawnPosition(var6.posX, var6.posY, var6.posZ));
        var7.sendPacket(new S39PacketPlayerAbilities(par2EntityPlayerMP.capabilities));
        var7.sendPacket(new S09PacketHeldItemChange(par2EntityPlayerMP.inventory.currentItem));
        par2EntityPlayerMP.func_147099_x().func_150877_d();
        par2EntityPlayerMP.func_147099_x().func_150884_b(par2EntityPlayerMP);
        this.func_96456_a((ServerScoreboard)var5.getScoreboard(), par2EntityPlayerMP);
        this.mcServer.func_147132_au();
        ChatComponentTranslation var8 = new ChatComponentTranslation("multiplayer.player.joined", par2EntityPlayerMP.func_145748_c_());
        var8.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.func_148539_a(var8);
        this.playerLoggedIn(par2EntityPlayerMP);
        var7.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
        this.updateTimeAndWeatherForPlayer(par2EntityPlayerMP, var5);
        if (this.mcServer.func_147133_T().length() > 0) {
            par2EntityPlayerMP.func_147095_a(this.mcServer.func_147133_T());
        }
        for (PotionEffect var10 : par2EntityPlayerMP.getActivePotionEffects()) {
            var7.sendPacket(new S1DPacketEntityEffect(par2EntityPlayerMP.getEntityId(), var10));
        }
        par2EntityPlayerMP.addSelfToInternalCraftingInventory();
        if (var3 != null && var3.func_150297_b("Riding", 10) && (var11 = EntityList.createEntityFromNBT(var3.getCompoundTag("Riding"), var5)) != null) {
            var11.forceSpawn = true;
            var5.spawnEntityInWorld(var11);
            par2EntityPlayerMP.mountEntity(var11);
            var11.forceSpawn = false;
        }
    }

    protected void func_96456_a(ServerScoreboard par1ServerScoreboard, EntityPlayerMP par2EntityPlayerMP) {
        HashSet<ScoreObjective> var3 = new HashSet<ScoreObjective>();
        for (ScorePlayerTeam var5 : par1ServerScoreboard.getTeams()) {
            par2EntityPlayerMP.playerNetServerHandler.sendPacket(new S3EPacketTeams(var5, 0));
        }
        int var9 = 0;
        while (var9 < 3) {
            ScoreObjective var10 = par1ServerScoreboard.func_96539_a(var9);
            if (var10 != null && !var3.contains(var10)) {
                List var6 = par1ServerScoreboard.func_96550_d(var10);
                for (Packet var8 : var6) {
                    par2EntityPlayerMP.playerNetServerHandler.sendPacket(var8);
                }
                var3.add(var10);
            }
            ++var9;
        }
    }

    public void setPlayerManager(WorldServer[] par1ArrayOfWorldServer) {
        this.playerNBTManagerObj = par1ArrayOfWorldServer[0].getSaveHandler().getSaveHandler();
    }

    public void func_72375_a(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer) {
        WorldServer var3 = par1EntityPlayerMP.getServerForPlayer();
        if (par2WorldServer != null) {
            par2WorldServer.getPlayerManager().removePlayer(par1EntityPlayerMP);
        }
        var3.getPlayerManager().addPlayer(par1EntityPlayerMP);
        var3.theChunkProviderServer.loadChunk((int)par1EntityPlayerMP.posX >> 4, (int)par1EntityPlayerMP.posZ >> 4);
    }

    public int getEntityViewDistance() {
        return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
    }

    public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP par1EntityPlayerMP) {
        NBTTagCompound var3;
        NBTTagCompound var2 = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
        if (par1EntityPlayerMP.getCommandSenderName().equals(this.mcServer.getServerOwner()) && var2 != null) {
            par1EntityPlayerMP.readFromNBT(var2);
            var3 = var2;
            logger.debug("loading single player");
        } else {
            var3 = this.playerNBTManagerObj.readPlayerData(par1EntityPlayerMP);
        }
        return var3;
    }

    protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP) {
        this.playerNBTManagerObj.writePlayerData(par1EntityPlayerMP);
        StatisticsFile var2 = (StatisticsFile)this.field_148547_k.get(par1EntityPlayerMP.getCommandSenderName());
        if (var2 != null) {
            var2.func_150883_b();
        }
    }

    public void playerLoggedIn(EntityPlayerMP par1EntityPlayerMP) {
        this.func_148540_a(new S38PacketPlayerListItem(par1EntityPlayerMP.getCommandSenderName(), true, 1000));
        this.playerEntityList.add(par1EntityPlayerMP);
        WorldServer var2 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        var2.spawnEntityInWorld(par1EntityPlayerMP);
        this.func_72375_a(par1EntityPlayerMP, null);
        int var3 = 0;
        while (var3 < this.playerEntityList.size()) {
            EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(var4.getCommandSenderName(), true, var4.ping));
            ++var3;
        }
    }

    public void serverUpdateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP) {
        par1EntityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(par1EntityPlayerMP);
    }

    public void playerLoggedOut(EntityPlayerMP par1EntityPlayerMP) {
        par1EntityPlayerMP.triggerAchievement(StatList.leaveGameStat);
        this.writePlayerData(par1EntityPlayerMP);
        WorldServer var2 = par1EntityPlayerMP.getServerForPlayer();
        if (par1EntityPlayerMP.ridingEntity != null) {
            var2.removePlayerEntityDangerously(par1EntityPlayerMP.ridingEntity);
            logger.debug("removing player mount");
        }
        var2.removeEntity(par1EntityPlayerMP);
        var2.getPlayerManager().removePlayer(par1EntityPlayerMP);
        this.playerEntityList.remove(par1EntityPlayerMP);
        this.field_148547_k.remove(par1EntityPlayerMP.getCommandSenderName());
        this.func_148540_a(new S38PacketPlayerListItem(par1EntityPlayerMP.getCommandSenderName(), false, 9999));
    }

    public String func_148542_a(SocketAddress p_148542_1_, GameProfile p_148542_2_) {
        if (this.bannedPlayers.isBanned(p_148542_2_.getName())) {
            BanEntry var6 = (BanEntry)this.bannedPlayers.getBannedList().get(p_148542_2_.getName());
            String var7 = "You are banned from this server!\nReason: " + var6.getBanReason();
            if (var6.getBanEndDate() != null) {
                var7 = String.valueOf(var7) + "\nYour ban will be removed on " + dateFormat.format(var6.getBanEndDate());
            }
            return var7;
        }
        if (!this.isAllowedToLogin(p_148542_2_.getName())) {
            return "You are not white-listed on this server!";
        }
        String var3 = p_148542_1_.toString();
        var3 = var3.substring(var3.indexOf("/") + 1);
        if (this.bannedIPs.isBanned(var3 = var3.substring(0, var3.indexOf(":")))) {
            BanEntry var4 = (BanEntry)this.bannedIPs.getBannedList().get(var3);
            String var5 = "Your IP address is banned from this server!\nReason: " + var4.getBanReason();
            if (var4.getBanEndDate() != null) {
                var5 = String.valueOf(var5) + "\nYour ban will be removed on " + dateFormat.format(var4.getBanEndDate());
            }
            return var5;
        }
        return this.playerEntityList.size() >= this.maxPlayers ? "The server is full!" : null;
    }

    public EntityPlayerMP func_148545_a(GameProfile p_148545_1_) {
        ArrayList<EntityPlayerMP> var2 = new ArrayList<EntityPlayerMP>();
        int var3 = 0;
        while (var3 < this.playerEntityList.size()) {
            EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
            if (var4.getCommandSenderName().equalsIgnoreCase(p_148545_1_.getName())) {
                var2.add(var4);
            }
            ++var3;
        }
        for (EntityPlayerMP var4 : var2) {
            var4.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
        }
        ItemInWorldManager var6 = this.mcServer.isDemo() ? new DemoWorldManager(this.mcServer.worldServerForDimension(0)) : new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
        return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), p_148545_1_, var6);
    }

    public EntityPlayerMP respawnPlayer(EntityPlayerMP par1EntityPlayerMP, int par2, boolean par3) {
        ChunkCoordinates var9;
        par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(par1EntityPlayerMP);
        par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removeEntityFromAllTrackingPlayers(par1EntityPlayerMP);
        par1EntityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(par1EntityPlayerMP);
        this.playerEntityList.remove(par1EntityPlayerMP);
        this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension).removePlayerEntityDangerously(par1EntityPlayerMP);
        ChunkCoordinates var4 = par1EntityPlayerMP.getBedLocation();
        boolean var5 = par1EntityPlayerMP.isSpawnForced();
        par1EntityPlayerMP.dimension = par2;
        ItemInWorldManager var6 = this.mcServer.isDemo() ? new DemoWorldManager(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension)) : new ItemInWorldManager(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension));
        EntityPlayerMP var7 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), par1EntityPlayerMP.getGameProfile(), var6);
        var7.playerNetServerHandler = par1EntityPlayerMP.playerNetServerHandler;
        var7.clonePlayer(par1EntityPlayerMP, par3);
        var7.setEntityId(par1EntityPlayerMP.getEntityId());
        WorldServer var8 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        this.func_72381_a(var7, par1EntityPlayerMP, var8);
        if (var4 != null) {
            var9 = EntityPlayer.verifyRespawnCoordinates(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), var4, var5);
            if (var9 != null) {
                var7.setLocationAndAngles((float)var9.posX + 0.5f, (float)var9.posY + 0.1f, (float)var9.posZ + 0.5f, 0.0f, 0.0f);
                var7.setSpawnChunk(var4, var5);
            } else {
                var7.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0f));
            }
        }
        var8.theChunkProviderServer.loadChunk((int)var7.posX >> 4, (int)var7.posZ >> 4);
        while (!var8.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty()) {
            var7.setPosition(var7.posX, var7.posY + 1.0, var7.posZ);
        }
        var7.playerNetServerHandler.sendPacket(new S07PacketRespawn(var7.dimension, var7.worldObj.difficultySetting, var7.worldObj.getWorldInfo().getTerrainType(), var7.theItemInWorldManager.getGameType()));
        var9 = var8.getSpawnPoint();
        var7.playerNetServerHandler.setPlayerLocation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
        var7.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(var9.posX, var9.posY, var9.posZ));
        var7.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(var7.experience, var7.experienceTotal, var7.experienceLevel));
        this.updateTimeAndWeatherForPlayer(var7, var8);
        var8.getPlayerManager().addPlayer(var7);
        var8.spawnEntityInWorld(var7);
        this.playerEntityList.add(var7);
        var7.addSelfToInternalCraftingInventory();
        var7.setHealth(var7.getHealth());
        return var7;
    }

    public void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int par2) {
        int var3 = par1EntityPlayerMP.dimension;
        WorldServer var4 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        WorldServer var5 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(par1EntityPlayerMP.dimension, par1EntityPlayerMP.worldObj.difficultySetting, par1EntityPlayerMP.worldObj.getWorldInfo().getTerrainType(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
        var4.removePlayerEntityDangerously(par1EntityPlayerMP);
        par1EntityPlayerMP.isDead = false;
        this.transferEntityToWorld(par1EntityPlayerMP, var3, var4, var5);
        this.func_72375_a(par1EntityPlayerMP, var4);
        par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
        par1EntityPlayerMP.theItemInWorldManager.setWorld(var5);
        this.updateTimeAndWeatherForPlayer(par1EntityPlayerMP, var5);
        this.syncPlayerInventory(par1EntityPlayerMP);
        for (PotionEffect var7 : par1EntityPlayerMP.getActivePotionEffects()) {
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(par1EntityPlayerMP.getEntityId(), var7));
        }
    }

    public void transferEntityToWorld(Entity par1Entity, int par2, WorldServer par3WorldServer, WorldServer par4WorldServer) {
        double var5 = par1Entity.posX;
        double var7 = par1Entity.posZ;
        double var9 = 8.0;
        double var11 = par1Entity.posX;
        double var13 = par1Entity.posY;
        double var15 = par1Entity.posZ;
        float var17 = par1Entity.rotationYaw;
        par3WorldServer.theProfiler.startSection("moving");
        if (par1Entity.dimension == -1) {
            par1Entity.setLocationAndAngles(var5 /= var9, par1Entity.posY, var7 /= var9, par1Entity.rotationYaw, par1Entity.rotationPitch);
            if (par1Entity.isEntityAlive()) {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        } else if (par1Entity.dimension == 0) {
            par1Entity.setLocationAndAngles(var5 *= var9, par1Entity.posY, var7 *= var9, par1Entity.rotationYaw, par1Entity.rotationPitch);
            if (par1Entity.isEntityAlive()) {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        } else {
            ChunkCoordinates var18 = par2 == 1 ? par4WorldServer.getSpawnPoint() : par4WorldServer.getEntrancePortalLocation();
            var5 = var18.posX;
            par1Entity.posY = var18.posY;
            var7 = var18.posZ;
            par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, 90.0f, 0.0f);
            if (par1Entity.isEntityAlive()) {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        }
        par3WorldServer.theProfiler.endSection();
        if (par2 != 1) {
            par3WorldServer.theProfiler.startSection("placing");
            var5 = MathHelper.clamp_int((int)var5, -29999872, 29999872);
            var7 = MathHelper.clamp_int((int)var7, -29999872, 29999872);
            if (par1Entity.isEntityAlive()) {
                par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
                par4WorldServer.getDefaultTeleporter().placeInPortal(par1Entity, var11, var13, var15, var17);
                par4WorldServer.spawnEntityInWorld(par1Entity);
                par4WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
            par3WorldServer.theProfiler.endSection();
        }
        par1Entity.setWorld(par4WorldServer);
    }

    public void sendPlayerInfoToAllPlayers() {
        if (++this.playerPingIndex > 600) {
            this.playerPingIndex = 0;
        }
        if (this.playerPingIndex < this.playerEntityList.size()) {
            EntityPlayerMP var1 = (EntityPlayerMP)this.playerEntityList.get(this.playerPingIndex);
            this.func_148540_a(new S38PacketPlayerListItem(var1.getCommandSenderName(), true, var1.ping));
        }
    }

    public void func_148540_a(Packet p_148540_1_) {
        int var2 = 0;
        while (var2 < this.playerEntityList.size()) {
            ((EntityPlayerMP)this.playerEntityList.get((int)var2)).playerNetServerHandler.sendPacket(p_148540_1_);
            ++var2;
        }
    }

    public void func_148537_a(Packet p_148537_1_, int p_148537_2_) {
        int var3 = 0;
        while (var3 < this.playerEntityList.size()) {
            EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
            if (var4.dimension == p_148537_2_) {
                var4.playerNetServerHandler.sendPacket(p_148537_1_);
            }
            ++var3;
        }
    }

    public String getPlayerListAsString() {
        String var1 = "";
        int var2 = 0;
        while (var2 < this.playerEntityList.size()) {
            if (var2 > 0) {
                var1 = String.valueOf(var1) + ", ";
            }
            var1 = String.valueOf(var1) + ((EntityPlayerMP)this.playerEntityList.get(var2)).getCommandSenderName();
            ++var2;
        }
        return var1;
    }

    public String[] getAllUsernames() {
        String[] var1 = new String[this.playerEntityList.size()];
        int var2 = 0;
        while (var2 < this.playerEntityList.size()) {
            var1[var2] = ((EntityPlayerMP)this.playerEntityList.get(var2)).getCommandSenderName();
            ++var2;
        }
        return var1;
    }

    public BanList getBannedPlayers() {
        return this.bannedPlayers;
    }

    public BanList getBannedIPs() {
        return this.bannedIPs;
    }

    public void addOp(String par1Str) {
        this.ops.add(par1Str.toLowerCase());
    }

    public void removeOp(String par1Str) {
        this.ops.remove(par1Str.toLowerCase());
    }

    public boolean isAllowedToLogin(String par1Str) {
        par1Str = par1Str.trim().toLowerCase();
        if (this.whiteListEnforced && !this.ops.contains(par1Str) && !this.whiteListedPlayers.contains(par1Str)) {
            return false;
        }
        return true;
    }

    public boolean isPlayerOpped(String par1Str) {
        if (!(this.ops.contains(par1Str.trim().toLowerCase()) || this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(par1Str) || this.commandsAllowedForAll)) {
            return false;
        }
        return true;
    }

    public EntityPlayerMP getPlayerForUsername(String par1Str) {
        EntityPlayerMP var3;
        Iterator var2 = this.playerEntityList.iterator();
        do {
            if (var2.hasNext()) continue;
            return null;
        } while (!(var3 = (EntityPlayerMP)var2.next()).getCommandSenderName().equalsIgnoreCase(par1Str));
        return var3;
    }

    public List findPlayers(ChunkCoordinates par1ChunkCoordinates, int par2, int par3, int par4, int par5, int par6, int par7, Map par8Map, String par9Str, String par10Str, World par11World) {
        if (this.playerEntityList.isEmpty()) {
            return null;
        }
        List var12 = new ArrayList();
        boolean var13 = par4 < 0;
        boolean var14 = par9Str != null && par9Str.startsWith("!");
        boolean var15 = par10Str != null && par10Str.startsWith("!");
        int var16 = par2 * par2;
        int var17 = par3 * par3;
        par4 = MathHelper.abs_int(par4);
        if (var14) {
            par9Str = par9Str.substring(1);
        }
        if (var15) {
            par10Str = par10Str.substring(1);
        }
        int var18 = 0;
        while (var18 < this.playerEntityList.size()) {
            block11 : {
                EntityPlayerMP var19;
                block13 : {
                    block12 : {
                        String var21;
                        var19 = (EntityPlayerMP)this.playerEntityList.get(var18);
                        if (par11World != null && var19.worldObj != par11World || par9Str != null && var14 == par9Str.equalsIgnoreCase(var19.getCommandSenderName())) break block11;
                        if (par10Str == null) break block12;
                        Team var20 = var19.getTeam();
                        String string = var21 = var20 == null ? "" : var20.getRegisteredName();
                        if (var15 == par10Str.equalsIgnoreCase(var21)) break block11;
                    }
                    if (par1ChunkCoordinates == null || par2 <= 0 && par3 <= 0) break block13;
                    float var22 = par1ChunkCoordinates.getDistanceSquaredToChunkCoordinates(var19.getPlayerCoordinates());
                    if (par2 > 0 && var22 < (float)var16 || par3 > 0 && var22 > (float)var17) break block11;
                }
                if (!(!this.func_96457_a(var19, par8Map) || par5 != WorldSettings.GameType.NOT_SET.getID() && par5 != var19.theItemInWorldManager.getGameType().getID() || par6 > 0 && var19.experienceLevel < par6 || var19.experienceLevel > par7)) {
                    ((List)var12).add(var19);
                }
            }
            ++var18;
        }
        if (par1ChunkCoordinates != null) {
            Collections.sort(var12, new PlayerPositionComparator(par1ChunkCoordinates));
        }
        if (var13) {
            Collections.reverse(var12);
        }
        if (par4 > 0) {
            var12 = ((List)var12).subList(0, Math.min(par4, ((List)var12).size()));
        }
        return var12;
    }

    private boolean func_96457_a(EntityPlayer par1EntityPlayer, Map par2Map) {
        if (par2Map != null && par2Map.size() != 0) {
            boolean var6;
            int var10;
            Map.Entry var4;
            Iterator var3 = par2Map.entrySet().iterator();
            do {
                Scoreboard var7;
                ScoreObjective var8;
                if (!var3.hasNext()) {
                    return true;
                }
                var4 = var3.next();
                String var5 = (String)var4.getKey();
                var6 = false;
                if (var5.endsWith("_min") && var5.length() > 4) {
                    var6 = true;
                    var5 = var5.substring(0, var5.length() - 4);
                }
                if ((var8 = (var7 = par1EntityPlayer.getWorldScoreboard()).getObjective(var5)) == null) {
                    return false;
                }
                Score var9 = par1EntityPlayer.getWorldScoreboard().func_96529_a(par1EntityPlayer.getCommandSenderName(), var8);
                var10 = var9.getScorePoints();
                if (var10 >= (Integer)var4.getValue() || !var6) continue;
                return false;
            } while (var10 <= (Integer)var4.getValue() || var6);
            return false;
        }
        return true;
    }

    public void func_148541_a(double p_148541_1_, double p_148541_3_, double p_148541_5_, double p_148541_7_, int p_148541_9_, Packet p_148541_10_) {
        this.func_148543_a(null, p_148541_1_, p_148541_3_, p_148541_5_, p_148541_7_, p_148541_9_, p_148541_10_);
    }

    public void func_148543_a(EntityPlayer p_148543_1_, double p_148543_2_, double p_148543_4_, double p_148543_6_, double p_148543_8_, int p_148543_10_, Packet p_148543_11_) {
        int var12 = 0;
        while (var12 < this.playerEntityList.size()) {
            double var14;
            double var16;
            double var18;
            EntityPlayerMP var13 = (EntityPlayerMP)this.playerEntityList.get(var12);
            if (var13 != p_148543_1_ && var13.dimension == p_148543_10_ && (var14 = p_148543_2_ - var13.posX) * var14 + (var16 = p_148543_4_ - var13.posY) * var16 + (var18 = p_148543_6_ - var13.posZ) * var18 < p_148543_8_ * p_148543_8_) {
                var13.playerNetServerHandler.sendPacket(p_148543_11_);
            }
            ++var12;
        }
    }

    public void saveAllPlayerData() {
        int var1 = 0;
        while (var1 < this.playerEntityList.size()) {
            this.writePlayerData((EntityPlayerMP)this.playerEntityList.get(var1));
            ++var1;
        }
    }

    public void addToWhiteList(String par1Str) {
        this.whiteListedPlayers.add(par1Str);
    }

    public void removeFromWhitelist(String par1Str) {
        this.whiteListedPlayers.remove(par1Str);
    }

    public Set getWhiteListedPlayers() {
        return this.whiteListedPlayers;
    }

    public Set getOps() {
        return this.ops;
    }

    public void loadWhiteList() {
    }

    public void updateTimeAndWeatherForPlayer(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer) {
        par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(par2WorldServer.getTotalWorldTime(), par2WorldServer.getWorldTime(), par2WorldServer.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
        if (par2WorldServer.isRaining()) {
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0f));
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, par2WorldServer.getRainStrength(1.0f)));
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, par2WorldServer.getWeightedThunderStrength(1.0f)));
        }
    }

    public void syncPlayerInventory(EntityPlayerMP par1EntityPlayerMP) {
        par1EntityPlayerMP.sendContainerToPlayer(par1EntityPlayerMP.inventoryContainer);
        par1EntityPlayerMP.setPlayerHealthUpdated();
        par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(par1EntityPlayerMP.inventory.currentItem));
    }

    public int getCurrentPlayerCount() {
        return this.playerEntityList.size();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public String[] getAvailablePlayerDat() {
        return this.mcServer.worldServers[0].getSaveHandler().getSaveHandler().getAvailablePlayerDat();
    }

    public void setWhiteListEnabled(boolean par1) {
        this.whiteListEnforced = par1;
    }

    public List getPlayerList(String par1Str) {
        ArrayList<EntityPlayerMP> var2 = new ArrayList<EntityPlayerMP>();
        for (EntityPlayerMP var4 : this.playerEntityList) {
            if (!var4.getPlayerIP().equals(par1Str)) continue;
            var2.add(var4);
        }
        return var2;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public MinecraftServer getServerInstance() {
        return this.mcServer;
    }

    public NBTTagCompound getHostPlayerData() {
        return null;
    }

    public void setGameType(WorldSettings.GameType par1EnumGameType) {
        this.gameType = par1EnumGameType;
    }

    private void func_72381_a(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP, World par3World) {
        if (par2EntityPlayerMP != null) {
            par1EntityPlayerMP.theItemInWorldManager.setGameType(par2EntityPlayerMP.theItemInWorldManager.getGameType());
        } else if (this.gameType != null) {
            par1EntityPlayerMP.theItemInWorldManager.setGameType(this.gameType);
        }
        par1EntityPlayerMP.theItemInWorldManager.initializeGameType(par3World.getWorldInfo().getGameType());
    }

    public void setCommandsAllowedForAll(boolean par1) {
        this.commandsAllowedForAll = par1;
    }

    public void removeAllPlayers() {
        int var1 = 0;
        while (var1 < this.playerEntityList.size()) {
            ((EntityPlayerMP)this.playerEntityList.get((int)var1)).playerNetServerHandler.kickPlayerFromServer("Server closed");
            ++var1;
        }
    }

    public void func_148544_a(IChatComponent p_148544_1_, boolean p_148544_2_) {
        this.mcServer.addChatMessage(p_148544_1_);
        this.func_148540_a(new S02PacketChat(p_148544_1_, p_148544_2_));
    }

    public void func_148539_a(IChatComponent p_148539_1_) {
        this.func_148544_a(p_148539_1_, true);
    }

    public StatisticsFile func_148538_i(String p_148538_1_) {
        StatisticsFile var2 = (StatisticsFile)this.field_148547_k.get(p_148538_1_);
        if (var2 == null) {
            var2 = new StatisticsFile(this.mcServer, new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats/" + p_148538_1_ + ".json"));
            var2.func_150882_a();
            this.field_148547_k.put(p_148538_1_, var2);
        }
        return var2;
    }
}

