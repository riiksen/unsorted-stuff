/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufOutputStream
 *  io.netty.buffer.Unpooled
 *  io.netty.handler.codec.base64.Base64
 *  org.apache.commons.lang3.Validate
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.Packet;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer
implements ICommandSender,
Runnable,
IPlayerUsage {
    private static final Logger logger = LogManager.getLogger();
    private static MinecraftServer mcServer;
    private final ISaveFormat anvilConverterForAnvilFile;
    private final PlayerUsageSnooper usageSnooper;
    private final File anvilFile;
    private final List tickables;
    private final ICommandManager commandManager;
    public final Profiler theProfiler;
    private final NetworkSystem field_147144_o;
    private final ServerStatusResponse field_147147_p;
    private final Random field_147146_q;
    private int serverPort;
    public WorldServer[] worldServers;
    private ServerConfigurationManager serverConfigManager;
    private boolean serverRunning;
    private boolean serverStopped;
    private int tickCounter;
    protected final Proxy serverProxy;
    public String currentTask;
    public int percentDone;
    private boolean onlineMode;
    private boolean canSpawnAnimals;
    private boolean canSpawnNPCs;
    private boolean pvpEnabled;
    private boolean allowFlight;
    private String motd;
    private int buildLimit;
    private int field_143008_E;
    public final long[] tickTimeArray;
    public long[][] timeOfLastDimensionTick;
    private KeyPair serverKeyPair;
    private String serverOwner;
    private String folderName;
    private String worldName;
    private boolean isDemo;
    private boolean enableBonusChest;
    private boolean worldIsBeingDeleted;
    private String field_147141_M;
    private boolean serverIsRunning;
    private long timeOfLastWarning;
    private String userMessage;
    private boolean startProfiling;
    private boolean isGamemodeForced;
    private final MinecraftSessionService field_147143_S;
    private long field_147142_T;
    private static final String __OBFID = "CL_00001462";

    public MinecraftServer(File p_i45281_1_, Proxy p_i45281_2_) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, MinecraftServer.getSystemTimeMillis());
        this.tickables = new ArrayList();
        this.theProfiler = new Profiler();
        this.field_147147_p = new ServerStatusResponse();
        this.field_147146_q = new Random();
        this.serverPort = -1;
        this.serverRunning = true;
        this.field_143008_E = 0;
        this.tickTimeArray = new long[100];
        this.field_147141_M = "";
        this.field_147142_T = 0;
        mcServer = this;
        this.serverProxy = p_i45281_2_;
        this.anvilFile = p_i45281_1_;
        this.field_147144_o = new NetworkSystem(this);
        this.commandManager = new ServerCommandManager();
        this.anvilConverterForAnvilFile = new AnvilSaveConverter(p_i45281_1_);
        this.field_147143_S = new YggdrasilAuthenticationService(p_i45281_2_, UUID.randomUUID().toString()).createMinecraftSessionService();
    }

    protected abstract boolean startServer() throws IOException;

    protected void convertMapIfNeeded(String par1Str) {
        if (this.getActiveAnvilConverter().isOldMapFormat(par1Str)) {
            logger.info("Converting map!");
            this.setUserMessage("menu.convertingLevel");
            this.getActiveAnvilConverter().convertMapFormat(par1Str, new IProgressUpdate(){
                private long field_96245_b;
                private static final String __OBFID = "CL_00001417";
                {
                    this.field_96245_b = System.currentTimeMillis();
                }

                @Override
                public void displayProgressMessage(String par1Str) {
                }

                @Override
                public void resetProgressAndMessage(String par1Str) {
                }

                @Override
                public void setLoadingProgress(int par1) {
                    if (System.currentTimeMillis() - this.field_96245_b >= 1000) {
                        this.field_96245_b = System.currentTimeMillis();
                        logger.info("Converting... " + par1 + "%");
                    }
                }

                @Override
                public void func_146586_a() {
                }

                @Override
                public void resetProgresAndWorkingMessage(String par1Str) {
                }
            });
        }
    }

    protected synchronized void setUserMessage(String par1Str) {
        this.userMessage = par1Str;
    }

    public synchronized String getUserMessage() {
        return this.userMessage;
    }

    protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str) {
        WorldSettings var8;
        this.convertMapIfNeeded(par1Str);
        this.setUserMessage("menu.loadingLevel");
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        ISaveHandler var7 = this.anvilConverterForAnvilFile.getSaveLoader(par1Str, true);
        WorldInfo var9 = var7.loadWorldInfo();
        if (var9 == null) {
            var8 = new WorldSettings(par3, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), par5WorldType);
            var8.func_82750_a(par6Str);
        } else {
            var8 = new WorldSettings(var9);
        }
        if (this.enableBonusChest) {
            var8.enableBonusChest();
        }
        int var10 = 0;
        while (var10 < this.worldServers.length) {
            int var11 = 0;
            if (var10 == 1) {
                var11 = -1;
            }
            if (var10 == 2) {
                var11 = 1;
            }
            this.worldServers[var10] = var10 == 0 ? (this.isDemo() ? new DemoWorldServer(this, var7, par2Str, var11, this.theProfiler) : new WorldServer(this, var7, par2Str, var11, var8, this.theProfiler)) : new WorldServerMulti(this, var7, par2Str, var11, var8, this.worldServers[0], this.theProfiler);
            this.worldServers[var10].addWorldAccess(new WorldManager(this, this.worldServers[var10]));
            if (!this.isSinglePlayer()) {
                this.worldServers[var10].getWorldInfo().setGameType(this.getGameType());
            }
            this.serverConfigManager.setPlayerManager(this.worldServers);
            ++var10;
        }
        this.func_147139_a(this.func_147135_j());
        this.initialWorldChunkLoad();
    }

    protected void initialWorldChunkLoad() {
        boolean var1 = true;
        boolean var2 = true;
        boolean var3 = true;
        boolean var4 = true;
        int var5 = 0;
        this.setUserMessage("menu.generatingTerrain");
        int var6 = 0;
        logger.info("Preparing start region for level " + var6);
        WorldServer var7 = this.worldServers[var6];
        ChunkCoordinates var8 = var7.getSpawnPoint();
        long var9 = MinecraftServer.getSystemTimeMillis();
        int var11 = -192;
        while (var11 <= 192 && this.isServerRunning()) {
            int var12 = -192;
            while (var12 <= 192 && this.isServerRunning()) {
                long var13 = MinecraftServer.getSystemTimeMillis();
                if (var13 - var9 > 1000) {
                    this.outputPercentRemaining("Preparing spawn area", var5 * 100 / 625);
                    var9 = var13;
                }
                ++var5;
                var7.theChunkProviderServer.loadChunk(var8.posX + var11 >> 4, var8.posZ + var12 >> 4);
                var12 += 16;
            }
            var11 += 16;
        }
        this.clearCurrentTask();
    }

    public abstract boolean canStructuresSpawn();

    public abstract WorldSettings.GameType getGameType();

    public abstract EnumDifficulty func_147135_j();

    public abstract boolean isHardcore();

    public abstract int func_110455_j();

    protected void outputPercentRemaining(String par1Str, int par2) {
        this.currentTask = par1Str;
        this.percentDone = par2;
        logger.info(String.valueOf(par1Str) + ": " + par2 + "%");
    }

    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = 0;
    }

    protected void saveAllWorlds(boolean par1) {
        if (!this.worldIsBeingDeleted) {
            WorldServer[] var2 = this.worldServers;
            int var3 = var2.length;
            int var4 = 0;
            while (var4 < var3) {
                WorldServer var5 = var2[var4];
                if (var5 != null) {
                    if (!par1) {
                        logger.info("Saving chunks for level '" + var5.getWorldInfo().getWorldName() + "'/" + var5.provider.getDimensionName());
                    }
                    try {
                        var5.saveAllChunks(true, null);
                    }
                    catch (MinecraftException var7) {
                        logger.warn(var7.getMessage());
                    }
                }
                ++var4;
            }
        }
    }

    public void stopServer() {
        if (!this.worldIsBeingDeleted) {
            logger.info("Stopping server");
            if (this.func_147137_ag() != null) {
                this.func_147137_ag().terminateEndpoints();
            }
            if (this.serverConfigManager != null) {
                logger.info("Saving players");
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }
            logger.info("Saving worlds");
            this.saveAllWorlds(false);
            int var1 = 0;
            while (var1 < this.worldServers.length) {
                WorldServer var2 = this.worldServers[var1];
                var2.flush();
                ++var1;
            }
            if (this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }

    public boolean isServerRunning() {
        return this.serverRunning;
    }

    public void initiateShutdown() {
        this.serverRunning = false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        try {
            if (!this.startServer()) {
                this.finalTick(null);
                return;
            }
            var1 = MinecraftServer.getSystemTimeMillis();
            var50 = 0;
            this.field_147147_p.func_151315_a(new ChatComponentText(this.motd));
            this.field_147147_p.func_151321_a(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.7.2", 4));
            this.func_147138_a(this.field_147147_p);
            ** GOTO lbl72
        }
        catch (Throwable var48) {
            MinecraftServer.logger.error("Encountered an unexpected exception", var48);
            var2 = null;
            var2 = var48 instanceof ReportedException != false ? this.addServerInfoToCrashReport(((ReportedException)var48).getCrashReport()) : this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var48));
            var3 = new File(new File(this.getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
            if (var2.saveToFile(var3)) {
                MinecraftServer.logger.error("This crash report has been saved to: " + var3.getAbsolutePath());
            } else {
                MinecraftServer.logger.error("We were unable to save this crash report to disk.");
            }
            this.finalTick(var2);
            try {
                try {
                    this.stopServer();
                    this.serverStopped = true;
                    return;
                }
                catch (Throwable var46) {
                    MinecraftServer.logger.error("Exception stopping the server", var46);
                    this.systemExitNow();
                    return;
                }
            }
            finally {
                this.systemExitNow();
            }
        }
        finally {
            block26 : {
                try {
                    try {
                        this.stopServer();
                        this.serverStopped = true;
                    }
                    catch (Throwable var46) {
                        MinecraftServer.logger.error("Exception stopping the server", var46);
                        this.systemExitNow();
                        break block26;
                    }
                }
                catch (Throwable var11_13) {
                    this.systemExitNow();
                    throw var11_13;
                }
                this.systemExitNow();
            }
        }
lbl-1000: // 1 sources:
        {
            block27 : {
                var5 = MinecraftServer.getSystemTimeMillis();
                var7 = var5 - var1;
                if (var7 > 2000 && var1 - this.timeOfLastWarning >= 15000) {
                    MinecraftServer.logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[]{var7, var7 / 50});
                    var7 = 2000;
                    this.timeOfLastWarning = var1;
                }
                if (var7 < 0) {
                    MinecraftServer.logger.warn("Time ran backwards! Did the system time change?");
                    var7 = 0;
                }
                var50 += var7;
                var1 = var5;
                if (!this.worldServers[0].areAllPlayersAsleep()) ** GOTO lbl68
                this.tick();
                var50 = 0;
                break block27;
lbl-1000: // 1 sources:
                {
                    var50 -= 50;
                    this.tick();
lbl68: // 2 sources:
                    ** while (var50 > 50)
                }
            }
            Thread.sleep(1);
            this.serverIsRunning = true;
lbl72: // 2 sources:
            ** while (this.serverRunning)
        }
lbl73: // 1 sources:
    }

    private void func_147138_a(ServerStatusResponse p_147138_1_) {
        File var2 = this.getFile("server-icon.png");
        if (var2.isFile()) {
            ByteBuf var3 = Unpooled.buffer();
            try {
                BufferedImage var4 = ImageIO.read(var2);
                Validate.validState((boolean)(var4.getWidth() == 64), (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                Validate.validState((boolean)(var4.getHeight() == 64), (String)"Must be 64 pixels high", (Object[])new Object[0]);
                ImageIO.write((RenderedImage)var4, "PNG", (OutputStream)new ByteBufOutputStream(var3));
                ByteBuf var5 = Base64.encode((ByteBuf)var3);
                p_147138_1_.func_151320_a("data:image/png;base64," + var5.toString(Charsets.UTF_8));
            }
            catch (Exception var6) {
                logger.error("Couldn't load server icon", (Throwable)var6);
            }
        }
    }

    protected File getDataDirectory() {
        return new File(".");
    }

    protected void finalTick(CrashReport par1CrashReport) {
    }

    protected void systemExitNow() {
    }

    public void tick() {
        long var1 = System.nanoTime();
        AxisAlignedBB.getAABBPool().cleanPool();
        ++this.tickCounter;
        if (this.startProfiling) {
            this.startProfiling = false;
            this.theProfiler.profilingEnabled = true;
            this.theProfiler.clearProfiling();
        }
        this.theProfiler.startSection("root");
        this.updateTimeLightAndEntities();
        if (var1 - this.field_147142_T >= 5000000000L) {
            this.field_147142_T = var1;
            this.field_147147_p.func_151319_a(new ServerStatusResponse.PlayerCountData(this.getMaxPlayers(), this.getCurrentPlayerCount()));
            GameProfile[] var3 = new GameProfile[java.lang.Math.min(this.getCurrentPlayerCount(), 12)];
            int var4 = MathHelper.getRandomIntegerInRange(this.field_147146_q, 0, this.getCurrentPlayerCount() - var3.length);
            int var5 = 0;
            while (var5 < var3.length) {
                var3[var5] = ((EntityPlayerMP)this.serverConfigManager.playerEntityList.get(var4 + var5)).getGameProfile();
                ++var5;
            }
            Collections.shuffle(Arrays.asList(var3));
            this.field_147147_p.func_151318_b().func_151330_a(var3);
        }
        if (this.tickCounter % 900 == 0) {
            this.theProfiler.startSection("save");
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(true);
            this.theProfiler.endSection();
        }
        this.theProfiler.startSection("tallying");
        this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - var1;
        this.theProfiler.endSection();
        this.theProfiler.startSection("snooper");
        if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
            this.usageSnooper.startSnooper();
        }
        if (this.tickCounter % 6000 == 0) {
            this.usageSnooper.addMemoryStatsToSnooper();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }

    public void updateTimeLightAndEntities() {
        this.theProfiler.startSection("levels");
        int var1 = 0;
        while (var1 < this.worldServers.length) {
            long var2 = System.nanoTime();
            if (var1 == 0 || this.getAllowNether()) {
                WorldServer var4 = this.worldServers[var1];
                this.theProfiler.startSection(var4.getWorldInfo().getWorldName());
                this.theProfiler.startSection("pools");
                var4.getWorldVec3Pool().clear();
                this.theProfiler.endSection();
                if (this.tickCounter % 20 == 0) {
                    this.theProfiler.startSection("timeSync");
                    this.serverConfigManager.func_148537_a(new S03PacketTimeUpdate(var4.getTotalWorldTime(), var4.getWorldTime(), var4.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), var4.provider.dimensionId);
                    this.theProfiler.endSection();
                }
                this.theProfiler.startSection("tick");
                try {
                    var4.tick();
                }
                catch (Throwable var8) {
                    CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception ticking world");
                    var4.addWorldInfoToCrashReport(var6);
                    throw new ReportedException(var6);
                }
                try {
                    var4.updateEntities();
                }
                catch (Throwable var7) {
                    CrashReport var6 = CrashReport.makeCrashReport(var7, "Exception ticking world entities");
                    var4.addWorldInfoToCrashReport(var6);
                    throw new ReportedException(var6);
                }
                this.theProfiler.endSection();
                this.theProfiler.startSection("tracker");
                var4.getEntityTracker().updateTrackedEntities();
                this.theProfiler.endSection();
                this.theProfiler.endSection();
            }
            this.timeOfLastDimensionTick[var1][this.tickCounter % 100] = System.nanoTime() - var2;
            ++var1;
        }
        this.theProfiler.endStartSection("connection");
        this.func_147137_ag().networkTick();
        this.theProfiler.endStartSection("players");
        this.serverConfigManager.sendPlayerInfoToAllPlayers();
        this.theProfiler.endStartSection("tickables");
        var1 = 0;
        while (var1 < this.tickables.size()) {
            ((IUpdatePlayerListBox)this.tickables.get(var1)).update();
            ++var1;
        }
        this.theProfiler.endSection();
    }

    public boolean getAllowNether() {
        return true;
    }

    public void startServerThread() {
        new Thread("Server thread"){
            private static final String __OBFID = "CL_00001418";

            @Override
            public void run() {
                MinecraftServer.this.run();
            }
        }.start();
    }

    public File getFile(String par1Str) {
        return new File(this.getDataDirectory(), par1Str);
    }

    public void logWarning(String par1Str) {
        logger.warn(par1Str);
    }

    public WorldServer worldServerForDimension(int par1) {
        return par1 == -1 ? this.worldServers[1] : (par1 == 1 ? this.worldServers[2] : this.worldServers[0]);
    }

    public String getMinecraftVersion() {
        return "1.7.2";
    }

    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }

    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }

    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }

    public String getServerModName() {
        return "vanilla";
    }

    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable(){
            private static final String __OBFID = "CL_00001419";

            public String call() {
                return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
        });
        if (this.worldServers != null && this.worldServers.length > 0 && this.worldServers[0] != null) {
            par1CrashReport.getCategory().addCrashSectionCallable("Vec3 Pool Size", new Callable(){
                private static final String __OBFID = "CL_00001420";

                public String call() {
                    int var1 = MinecraftServer.this.worldServers[0].getWorldVec3Pool().getPoolSize();
                    int var2 = 56 * var1;
                    int var3 = var2 / 1024 / 1024;
                    int var4 = MinecraftServer.this.worldServers[0].getWorldVec3Pool().getNextFreeSpace();
                    int var5 = 56 * var4;
                    int var6 = var5 / 1024 / 1024;
                    return String.valueOf(var1) + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
                }
            });
        }
        if (this.serverConfigManager != null) {
            par1CrashReport.getCategory().addCrashSectionCallable("Player Count", new Callable(){
                private static final String __OBFID = "CL_00001780";

                public String call() {
                    return String.valueOf(MinecraftServer.this.serverConfigManager.getCurrentPlayerCount()) + " / " + MinecraftServer.this.serverConfigManager.getMaxPlayers() + "; " + MinecraftServer.access$1((MinecraftServer)MinecraftServer.this).playerEntityList;
                }
            });
        }
        return par1CrashReport;
    }

    public List getPossibleCompletions(ICommandSender par1ICommandSender, String par2Str) {
        ArrayList<String> var3 = new ArrayList<String>();
        if (par2Str.startsWith("/")) {
            boolean var10 = !(par2Str = par2Str.substring(1)).contains(" ");
            List var11 = this.commandManager.getPossibleCommands(par1ICommandSender, par2Str);
            if (var11 != null) {
                for (String var13 : var11) {
                    if (var10) {
                        var3.add("/" + var13);
                        continue;
                    }
                    var3.add(var13);
                }
            }
            return var3;
        }
        String[] var4 = par2Str.split(" ", -1);
        String var5 = var4[var4.length - 1];
        String[] var6 = this.serverConfigManager.getAllUsernames();
        int var7 = var6.length;
        int var8 = 0;
        while (var8 < var7) {
            String var9 = var6[var8];
            if (CommandBase.doesStringStartWith(var5, var9)) {
                var3.add(var9);
            }
            ++var8;
        }
        return var3;
    }

    public static MinecraftServer getServer() {
        return mcServer;
    }

    @Override
    public String getCommandSenderName() {
        return "Server";
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {
        logger.info(p_145747_1_.getUnformattedText());
    }

    @Override
    public boolean canCommandSenderUseCommand(int par1, String par2Str) {
        return true;
    }

    public ICommandManager getCommandManager() {
        return this.commandManager;
    }

    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }

    public String getServerOwner() {
        return this.serverOwner;
    }

    public void setServerOwner(String par1Str) {
        this.serverOwner = par1Str;
    }

    public boolean isSinglePlayer() {
        if (this.serverOwner != null) {
            return true;
        }
        return false;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String par1Str) {
        this.folderName = par1Str;
    }

    public void setWorldName(String par1Str) {
        this.worldName = par1Str;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public void setKeyPair(KeyPair par1KeyPair) {
        this.serverKeyPair = par1KeyPair;
    }

    public void func_147139_a(EnumDifficulty p_147139_1_) {
        int var2 = 0;
        while (var2 < this.worldServers.length) {
            WorldServer var3 = this.worldServers[var2];
            if (var3 != null) {
                if (var3.getWorldInfo().isHardcoreModeEnabled()) {
                    var3.difficultySetting = EnumDifficulty.HARD;
                    var3.setAllowedSpawnTypes(true, true);
                } else if (this.isSinglePlayer()) {
                    var3.difficultySetting = p_147139_1_;
                    var3.setAllowedSpawnTypes(var3.difficultySetting != EnumDifficulty.PEACEFUL, true);
                } else {
                    var3.difficultySetting = p_147139_1_;
                    var3.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                }
            }
            ++var2;
        }
    }

    protected boolean allowSpawnMonsters() {
        return true;
    }

    public boolean isDemo() {
        return this.isDemo;
    }

    public void setDemo(boolean par1) {
        this.isDemo = par1;
    }

    public void canCreateBonusChest(boolean par1) {
        this.enableBonusChest = par1;
    }

    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverterForAnvilFile;
    }

    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = true;
        this.getActiveAnvilConverter().flushCache();
        int var1 = 0;
        while (var1 < this.worldServers.length) {
            WorldServer var2 = this.worldServers[var1];
            if (var2 != null) {
                var2.flush();
            }
            ++var1;
        }
        this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }

    public String func_147133_T() {
        return this.field_147141_M;
    }

    @Override
    public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("whitelist_enabled", false);
        par1PlayerUsageSnooper.addData("whitelist_count", 0);
        par1PlayerUsageSnooper.addData("players_current", this.getCurrentPlayerCount());
        par1PlayerUsageSnooper.addData("players_max", this.getMaxPlayers());
        par1PlayerUsageSnooper.addData("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
        par1PlayerUsageSnooper.addData("uses_auth", this.onlineMode);
        par1PlayerUsageSnooper.addData("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
        par1PlayerUsageSnooper.addData("run_time", (MinecraftServer.getSystemTimeMillis() - par1PlayerUsageSnooper.getMinecraftStartTimeMillis()) / 60 * 1000);
        par1PlayerUsageSnooper.addData("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
        int var2 = 0;
        int var3 = 0;
        while (var3 < this.worldServers.length) {
            if (this.worldServers[var3] != null) {
                WorldServer var4 = this.worldServers[var3];
                WorldInfo var5 = var4.getWorldInfo();
                par1PlayerUsageSnooper.addData("world[" + var2 + "][dimension]", var4.provider.dimensionId);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][mode]", (Object)((Object)var5.getGameType()));
                par1PlayerUsageSnooper.addData("world[" + var2 + "][difficulty]", (Object)((Object)var4.difficultySetting));
                par1PlayerUsageSnooper.addData("world[" + var2 + "][hardcore]", var5.isHardcoreModeEnabled());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_name]", var5.getTerrainType().getWorldTypeName());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_version]", var5.getTerrainType().getGeneratorVersion());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][height]", this.buildLimit);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][chunks_loaded]", var4.getChunkProvider().getLoadedChunkCount());
                ++var2;
            }
            ++var3;
        }
        par1PlayerUsageSnooper.addData("worlds", var2);
    }

    @Override
    public void addServerTypeToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("singleplayer", this.isSinglePlayer());
        par1PlayerUsageSnooper.addData("server_brand", this.getServerModName());
        par1PlayerUsageSnooper.addData("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        par1PlayerUsageSnooper.addData("dedicated", this.isDedicatedServer());
    }

    @Override
    public boolean isSnooperEnabled() {
        return true;
    }

    public abstract boolean isDedicatedServer();

    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean par1) {
        this.onlineMode = par1;
    }

    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }

    public void setCanSpawnAnimals(boolean par1) {
        this.canSpawnAnimals = par1;
    }

    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }

    public void setCanSpawnNPCs(boolean par1) {
        this.canSpawnNPCs = par1;
    }

    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }

    public void setAllowPvp(boolean par1) {
        this.pvpEnabled = par1;
    }

    public boolean isFlightAllowed() {
        return this.allowFlight;
    }

    public void setAllowFlight(boolean par1) {
        this.allowFlight = par1;
    }

    public abstract boolean isCommandBlockEnabled();

    public String getMOTD() {
        return this.motd;
    }

    public void setMOTD(String par1Str) {
        this.motd = par1Str;
    }

    public int getBuildLimit() {
        return this.buildLimit;
    }

    public void setBuildLimit(int par1) {
        this.buildLimit = par1;
    }

    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }

    public void setConfigurationManager(ServerConfigurationManager par1ServerConfigurationManager) {
        this.serverConfigManager = par1ServerConfigurationManager;
    }

    public void setGameType(WorldSettings.GameType par1EnumGameType) {
        int var2 = 0;
        while (var2 < this.worldServers.length) {
            MinecraftServer.getServer().worldServers[var2].getWorldInfo().setGameType(par1EnumGameType);
            ++var2;
        }
    }

    public NetworkSystem func_147137_ag() {
        return this.field_147144_o;
    }

    public boolean serverIsInRunLoop() {
        return this.serverIsRunning;
    }

    public boolean getGuiEnabled() {
        return false;
    }

    public abstract String shareToLAN(WorldSettings.GameType var1, boolean var2);

    public int getTickCounter() {
        return this.tickCounter;
    }

    public void enableProfiling() {
        this.startProfiling = true;
    }

    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(0, 0, 0);
    }

    @Override
    public World getEntityWorld() {
        return this.worldServers[0];
    }

    public int getSpawnProtectionSize() {
        return 16;
    }

    public boolean isBlockProtected(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        return false;
    }

    public boolean getForceGamemode() {
        return this.isGamemodeForced;
    }

    public Proxy getServerProxy() {
        return this.serverProxy;
    }

    public static long getSystemTimeMillis() {
        return System.currentTimeMillis();
    }

    public int func_143007_ar() {
        return this.field_143008_E;
    }

    public void func_143006_e(int par1) {
        this.field_143008_E = par1;
    }

    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }

    public boolean func_147136_ar() {
        return true;
    }

    public MinecraftSessionService func_147130_as() {
        return this.field_147143_S;
    }

    public ServerStatusResponse func_147134_at() {
        return this.field_147147_p;
    }

    public void func_147132_au() {
        this.field_147142_T = 0;
    }

}

