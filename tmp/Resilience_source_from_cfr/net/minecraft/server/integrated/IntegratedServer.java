/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.integrated;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.security.KeyPair;
import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.network.NetworkSystem;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedPlayerList;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorConstructor;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.WorldServerMultiOF;
import net.minecraft.src.WorldServerOF;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Session;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer
extends MinecraftServer {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;
    private static final String __OBFID = "CL_00001129";

    public IntegratedServer(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings) {
        super(new File(par1Minecraft.mcDataDir, "saves"), par1Minecraft.getProxy());
        this.setServerOwner(par1Minecraft.getSession().getUsername());
        this.setFolderName(par2Str);
        this.setWorldName(par3Str);
        this.setDemo(par1Minecraft.isDemo());
        this.canCreateBonusChest(par4WorldSettings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigurationManager(new IntegratedPlayerList(this));
        this.mc = par1Minecraft;
        this.theWorldSettings = par4WorldSettings;
        Reflector.callVoid(Reflector.ModLoader_registerServer, this);
    }

    @Override
    protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str) {
        this.convertMapIfNeeded(par1Str);
        ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(par1Str, true);
        if (Reflector.DimensionManager.exists()) {
            Integer[] var9;
            WorldServer var8 = this.isDemo() ? new DemoWorldServer(this, var7, par2Str, 0, this.theProfiler) : new WorldServerOF(this, var7, par2Str, 0, this.theWorldSettings, this.theProfiler);
            Integer[] arr$ = var9 = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
            int len$ = var9.length;
            int i$ = 0;
            while (i$ < len$) {
                int dim = arr$[i$];
                WorldServer world = dim == 0 ? var8 : new WorldServerMultiOF(this, var7, par2Str, dim, this.theWorldSettings, var8, this.theProfiler);
                ((WorldServer)world).addWorldAccess(new WorldManager(this, world));
                if (!this.isSinglePlayer()) {
                    ((WorldServer)world).getWorldInfo().setGameType(this.getGameType());
                }
                if (Reflector.EventBus.exists()) {
                    Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, world);
                }
                ++i$;
            }
            this.getConfigurationManager().setPlayerManager(new WorldServer[]{var8});
        } else {
            this.worldServers = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
            int var15 = 0;
            while (var15 < this.worldServers.length) {
                int var16 = 0;
                if (var15 == 1) {
                    var16 = -1;
                }
                if (var15 == 2) {
                    var16 = 1;
                }
                this.worldServers[var15] = var15 == 0 ? (this.isDemo() ? new DemoWorldServer(this, var7, par2Str, var16, this.theProfiler) : new WorldServerOF(this, var7, par2Str, var16, this.theWorldSettings, this.theProfiler)) : new WorldServerMultiOF(this, var7, par2Str, var16, this.theWorldSettings, this.worldServers[0], this.theProfiler);
                this.worldServers[var15].addWorldAccess(new WorldManager(this, this.worldServers[var15]));
                this.getConfigurationManager().setPlayerManager(this.worldServers);
                ++var15;
            }
        }
        this.func_147139_a(this.func_147135_j());
        this.initialWorldChunkLoad();
    }

    @Override
    protected boolean startServer() throws IOException {
        Object inst;
        logger.info("Starting integrated minecraft server version 1.7.2");
        this.setOnlineMode(false);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        logger.info("Generating keypair");
        this.setKeyPair(CryptManager.createNewKeyPair());
        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists() && !Reflector.callBoolean(inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]), Reflector.FMLCommonHandler_handleServerAboutToStart, this)) {
            return false;
        }
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.func_82749_j());
        this.setMOTD(String.valueOf(this.getServerOwner()) + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
            inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
                return Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerStarting, this);
            }
            Reflector.callVoid(inst, Reflector.FMLCommonHandler_handleServerStarting, this);
        }
        return true;
    }

    @Override
    public void tick() {
        boolean var1 = this.isGamePaused;
        boolean bl = this.isGamePaused = Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().func_147113_T();
        if (!var1 && this.isGamePaused) {
            logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (!this.isGamePaused) {
            super.tick();
        }
    }

    @Override
    public boolean canStructuresSpawn() {
        return false;
    }

    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }

    @Override
    public EnumDifficulty func_147135_j() {
        return this.mc.gameSettings.difficulty;
    }

    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }

    @Override
    protected File getDataDirectory() {
        return this.mc.mcDataDir;
    }

    @Override
    public boolean isDedicatedServer() {
        return false;
    }

    @Override
    protected void finalTick(CrashReport par1CrashReport) {
        this.mc.crashed(par1CrashReport);
    }

    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
        par1CrashReport.getCategory().addCrashSectionCallable("Type", new Callable(){
            private static final String __OBFID = "CL_00001130";

            public String call() {
                return "Integrated Server (map_client.txt)";
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable(){
            private static final String __OBFID = "CL_00001131";

            public String call() {
                String var1 = ClientBrandRetriever.getClientModName();
                if (!var1.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + var1 + "'";
                }
                var1 = IntegratedServer.this.getServerModName();
                return !var1.equals("vanilla") ? "Definitely; Server brand changed to '" + var1 + "'" : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
            }
        });
        return par1CrashReport;
    }

    @Override
    public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        super.addServerStatsToSnooper(par1PlayerUsageSnooper);
        par1PlayerUsageSnooper.addData("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }

    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }

    @Override
    public String shareToLAN(WorldSettings.GameType par1EnumGameType, boolean par2) {
        try {
            int var6 = -1;
            try {
                var6 = HttpUtil.func_76181_a();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (var6 <= 0) {
                var6 = 25564;
            }
            this.func_147137_ag().addLanEndpoint(null, var6);
            logger.info("Started on " + var6);
            this.isPublic = true;
            this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), String.valueOf(var6));
            this.lanServerPing.start();
            this.getConfigurationManager().setGameType(par1EnumGameType);
            this.getConfigurationManager().setCommandsAllowedForAll(par2);
            return String.valueOf(var6);
        }
        catch (IOException var61) {
            return null;
        }
    }

    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    @Override
    public void initiateShutdown() {
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    public boolean getPublic() {
        return this.isPublic;
    }

    @Override
    public void setGameType(WorldSettings.GameType par1EnumGameType) {
        this.getConfigurationManager().setGameType(par1EnumGameType);
    }

    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }

    @Override
    public int func_110455_j() {
        return 4;
    }

}

