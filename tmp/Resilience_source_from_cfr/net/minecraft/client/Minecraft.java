/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.Sys
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.opengl.PixelFormat
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.client;

import com.google.common.collect.Lists;
import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.externals.EventKeyDown;
import com.krispdev.resilience.event.events.player.EventGameShutdown;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.gui.screens.GuiBanned;
import com.krispdev.resilience.gui.screens.GuiFirstTime;
import com.krispdev.resilience.gui.screens.UpdateGamePrompt;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookGuiIngame;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.gui.GuiLogin;
import com.krispdev.resilience.online.gui.GuiRegister;
import com.krispdev.resilience.utilities.Utils;
import com.mojang.authlib.GameProfile;
import io.netty.util.concurrent.GenericFutureListener;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Minecraft
implements IPlayerUsage {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
    public static final boolean isRunningOnMac = Util.getOSType() == Util.EnumOS.MACOS;
    public static byte[] memoryReserve = new byte[10485760];
    private static final List macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
    private final File fileResourcepacks;
    public ServerData currentServerData;
    public TextureManager renderEngine;
    private static Minecraft theMinecraft;
    public HookPlayerControllerMP playerController;
    private boolean fullscreen;
    private boolean hasCrashed;
    private CrashReport crashReporter;
    public int displayWidth;
    public int displayHeight;
    public Timer timer = new Timer(20.0f);
    private PlayerUsageSnooper usageSnooper;
    public WorldClient theWorld;
    public RenderGlobal renderGlobal;
    public HookEntityClientPlayerMP thePlayer;
    public EntityLivingBase renderViewEntity;
    public Entity pointedEntity;
    public EffectRenderer effectRenderer;
    public Session session;
    private boolean isGamePaused;
    public FontRenderer fontRenderer;
    public FontRenderer standardGalacticFontRenderer;
    public GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;
    private int leftClickCounter;
    private int tempDisplayWidth;
    private int tempDisplayHeight;
    private IntegratedServer theIntegratedServer;
    public GuiAchievement guiAchievement;
    public HookGuiIngame ingameGUI;
    public boolean skipRenderWorld;
    public MovingObjectPosition objectMouseOver;
    public GameSettings gameSettings;
    public MouseHelper mouseHelper;
    public final File mcDataDir;
    private final File fileAssets;
    private final String launchedVersion;
    private final Proxy proxy;
    private ISaveFormat saveLoader;
    public static int debugFPS;
    public int rightClickDelayTimer;
    private boolean refreshTexturePacksScheduled;
    public String serverName;
    public int serverPort;
    boolean isTakingScreenshot;
    public boolean inGameHasFocus;
    long systemTime;
    private int joinPlayerCounter;
    private final boolean jvm64bit;
    private final boolean isDemo;
    private NetworkManager myNetworkManager;
    private boolean integratedServerIsRunning;
    public final Profiler mcProfiler;
    private long field_83002_am;
    private IReloadableResourceManager mcResourceManager;
    private final IMetadataSerializer metadataSerializer_;
    private List defaultResourcePacks;
    private DefaultResourcePack mcDefaultResourcePack;
    private ResourcePackRepository mcResourcePackRepository;
    private LanguageManager mcLanguageManager;
    private Framebuffer mcFramebuffer;
    private TextureMap textureMapBlocks;
    private SoundHandler mcSoundHandler;
    private MusicTicker mcMusicTicker;
    volatile boolean running;
    public String debug;
    long debugUpdateTime;
    int fpsCounter;
    long prevFrameTime;
    private String debugProfilerName;
    private static final String __OBFID = "CL_00000631";

    public Minecraft(Session par1Session, int par2, int par3, boolean par4, boolean par5, File par6File, File par7File, File par8File, Proxy par9Proxy, String par10Str) {
        this.usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getSystemTimeMillis());
        this.systemTime = Minecraft.getSystemTime();
        this.mcProfiler = new Profiler();
        this.field_83002_am = -1;
        this.metadataSerializer_ = new IMetadataSerializer();
        this.defaultResourcePacks = Lists.newArrayList();
        this.running = true;
        this.debug = "";
        this.debugUpdateTime = Minecraft.getSystemTime();
        this.prevFrameTime = -1;
        this.debugProfilerName = "root";
        theMinecraft = this;
        this.mcDataDir = par6File;
        this.fileAssets = par7File;
        this.fileResourcepacks = par8File;
        this.launchedVersion = par10Str;
        this.mcDefaultResourcePack = new DefaultResourcePack(this.fileAssets);
        this.addDefaultResourcePack();
        this.proxy = par9Proxy == null ? Proxy.NO_PROXY : par9Proxy;
        this.startTimerHackThread();
        this.session = par1Session;
        logger.info("Setting user: " + par1Session.getUsername());
        logger.info("(Session ID is " + par1Session.getSessionID() + ")");
        this.isDemo = par5;
        this.displayWidth = par2;
        this.displayHeight = par3;
        this.tempDisplayWidth = par2;
        this.tempDisplayHeight = par3;
        this.fullscreen = par4;
        this.jvm64bit = Minecraft.isJvm64bit();
        ImageIO.setUseCache(false);
        Bootstrap.func_151354_b();
    }

    private static boolean isJvm64bit() {
        String[] var0;
        String[] var1 = var0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        int var2 = var0.length;
        int var3 = 0;
        while (var3 < var2) {
            String var4 = var1[var3];
            String var5 = System.getProperty(var4);
            if (var5 != null && var5.contains("64")) {
                return true;
            }
            ++var3;
        }
        return false;
    }

    public static void func_147105_a(String p_147105_0_) {
        try {
            Toolkit var1 = Toolkit.getDefaultToolkit();
            Class var2 = var1.getClass();
            if (var2.getName().equals("sun.awt.X11.XToolkit")) {
                Field var3 = var2.getDeclaredField("awtAppClassName");
                var3.setAccessible(true);
                var3.set(var1, p_147105_0_);
            }
        }
        catch (Exception var1) {
            // empty catch block
        }
    }

    public Framebuffer getFramebuffer() {
        return this.mcFramebuffer;
    }

    private void startTimerHackThread() {
        Thread var1 = new Thread("Timer hack thread"){
            private static final String __OBFID = "CL_00000632";

            @Override
            public void run() {
                while (Minecraft.this.running) {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }
        };
        var1.setDaemon(true);
        var1.start();
    }

    public void crashed(CrashReport par1CrashReport) {
        this.hasCrashed = true;
        this.crashReporter = par1CrashReport;
    }

    public void displayCrashReport(CrashReport par1CrashReport) {
        Utils.setOnline(false);
        File var2 = new File(Minecraft.getMinecraft().mcDataDir, "crash-reports");
        File var3 = new File(var2, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        System.out.println(par1CrashReport.getCompleteReport());
        if (!Resilience.getInstance().getValues().needsUpdate()) {
            EventGameShutdown eventClose = new EventGameShutdown(System.nanoTime());
            eventClose.onEvent();
            if (eventClose.isCancelled()) {
                eventClose.setCancelled(false);
                return;
            }
        }
        if (par1CrashReport.getFile() != null) {
            System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + par1CrashReport.getFile());
            System.exit(-1);
        } else if (par1CrashReport.saveToFile(var3)) {
            System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
            System.exit(-1);
        } else {
            System.out.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }

    public void setServer(String par1Str, int par2) {
        this.serverName = par1Str;
        this.serverPort = par2;
    }

    private void startGame() throws LWJGLException {
        Resilience.getInstance().start();
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
            this.displayWidth = this.gameSettings.overrideWidth;
            this.displayHeight = this.gameSettings.overrideHeight;
        }
        if (this.fullscreen) {
            Display.setFullscreen((boolean)true);
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        } else {
            Display.setDisplayMode((DisplayMode)new DisplayMode(this.displayWidth, this.displayHeight));
        }
        Display.setResizable((boolean)true);
        Display.setTitle((String)"Minecraft 1.7.9");
        logger.info("LWJGL Version: " + Sys.getVersion());
        Util.EnumOS var1 = Util.getOSType();
        if (var1 != Util.EnumOS.MACOS) {
            try {
                Display.setIcon((ByteBuffer[])new ByteBuffer[]{this.readImage(new File(this.fileAssets, "/icons/icon_16x16.png")), this.readImage(new File(this.fileAssets, "/icons/icon_32x32.png"))});
            }
            catch (IOException var6) {
                logger.error("Couldn't set icon", (Throwable)var6);
            }
            if (var1 != Util.EnumOS.WINDOWS) {
                Minecraft.func_147105_a("Minecraft");
            }
        }
        try {
            Display.create((PixelFormat)new PixelFormat().withDepthBits(24));
        }
        catch (LWJGLException var5) {
            logger.error("Couldn't set pixel format", (Throwable)var5);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if (this.fullscreen) {
                this.updateDisplayMode();
            }
            Display.create();
        }
        OpenGlHelper.initializeTextures();
        this.mcFramebuffer = new Framebuffer(this.displayWidth, this.displayHeight, true);
        this.mcFramebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.guiAchievement = new GuiAchievement(this);
        this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
        this.refreshResources();
        this.renderEngine = new TextureManager(this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.renderEngine);
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcMusicTicker = new MusicTicker(this);
        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
        this.loadScreen();
        this.fontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
        if (this.gameSettings.language != null) {
            this.fontRenderer.setUnicodeFlag(this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont);
            this.fontRenderer.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
        }
        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
        this.mcResourceManager.registerReloadListener(this.fontRenderer);
        this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
        this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
        this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.entityRenderer);
        AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat(){
            private static final String __OBFID = "CL_00000639";

            @Override
            public String formatString(String par1Str) {
                try {
                    return String.format(par1Str, GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()));
                }
                catch (Exception var3) {
                    return "Error: " + var3.getLocalizedMessage();
                }
            }
        });
        this.mouseHelper = new MouseHelper();
        this.checkGLError("Pre startup");
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7425);
        GL11.glClearDepth((double)1.0);
        GL11.glEnable((int)2929);
        GL11.glDepthFunc((int)515);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.1f);
        GL11.glCullFace((int)1029);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        this.checkGLError("Startup");
        this.renderGlobal = new RenderGlobal(this);
        this.textureMapBlocks = new TextureMap(0, "textures/blocks");
        this.textureMapBlocks.func_147632_b(this.gameSettings.anisotropicFiltering);
        this.textureMapBlocks.func_147633_a(this.gameSettings.mipmapLevels);
        this.renderEngine.loadTextureMap(TextureMap.locationBlocksTexture, this.textureMapBlocks);
        this.renderEngine.loadTextureMap(TextureMap.locationItemsTexture, new TextureMap(1, "textures/items"));
        GL11.glViewport((int)0, (int)0, (int)this.displayWidth, (int)this.displayHeight);
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
        this.checkGLError("Post startup");
        this.ingameGUI = new HookGuiIngame(this);
        if (this.serverName != null) {
            this.displayGuiScreen(new GuiConnecting(new HookGuiMainMenu(), this, this.serverName, this.serverPort));
        } else if (Resilience.getInstance().getValues().isAccountBanned()) {
            this.displayGuiScreen(new GuiBanned());
        } else if (Resilience.getInstance().getValues().needsUpdate() && !Resilience.getInstance().isFirstTime()) {
            this.displayGuiScreen(new UpdateGamePrompt(new HookGuiMainMenu()));
        } else if (Resilience.getInstance().isFirstTime()) {
            this.displayGuiScreen(new GuiFirstTime());
        } else if (Resilience.getInstance().isFirstTimeOnline()) {
            this.displayGuiScreen(new GuiRegister(new HookGuiMainMenu(), true));
        } else if (!Resilience.getInstance().isNewAccount()) {
            this.displayGuiScreen(new GuiLogin(new HookGuiMainMenu()));
        } else {
            this.displayGuiScreen(new GuiRegister(new HookGuiMainMenu(), false));
        }
        this.loadingScreen = new LoadingScreenRenderer(this);
        if (this.gameSettings.fullScreen && !this.fullscreen) {
            this.toggleFullscreen();
        }
        Display.setVSyncEnabled((boolean)this.gameSettings.enableVsync);
    }

    public void refreshResources() {
        ArrayList var1 = Lists.newArrayList((Iterable)this.defaultResourcePacks);
        for (ResourcePackRepository.Entry var3 : this.mcResourcePackRepository.getRepositoryEntries()) {
            var1.add(var3.getResourcePack());
        }
        if (this.mcResourcePackRepository.func_148530_e() != null) {
            var1.add(this.mcResourcePackRepository.func_148530_e());
        }
        this.mcLanguageManager.parseLanguageMetadata(var1);
        this.mcResourceManager.reloadResources(var1);
        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }

    private void addDefaultResourcePack() {
        this.defaultResourcePacks.add(this.mcDefaultResourcePack);
    }

    private ByteBuffer readImage(File par1File) throws IOException {
        BufferedImage var2 = ImageIO.read(par1File);
        int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
        ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        int[] var5 = var3;
        int var6 = var3.length;
        int var7 = 0;
        while (var7 < var6) {
            int var8 = var5[var7];
            var4.putInt(var8 << 8 | var8 >> 24 & 255);
            ++var7;
        }
        var4.flip();
        return var4;
    }

    private void updateDisplayMode() throws LWJGLException {
        HashSet var1 = new HashSet();
        Collections.addAll(var1, Display.getAvailableDisplayModes());
        DisplayMode var2 = Display.getDesktopDisplayMode();
        if (!var1.contains((Object)var2) && Util.getOSType() == Util.EnumOS.MACOS) {
            block0 : for (DisplayMode var4 : macDisplayModes) {
                boolean var5 = true;
                for (DisplayMode var7 : var1) {
                    if (var7.getBitsPerPixel() != 32 || var7.getWidth() != var4.getWidth() || var7.getHeight() != var4.getHeight()) continue;
                    var5 = false;
                    break;
                }
                if (var5) continue;
                for (DisplayMode var7 : var1) {
                    if (var7.getBitsPerPixel() != 32 || var7.getWidth() != var4.getWidth() / 2 || var7.getHeight() != var4.getHeight() / 2) continue;
                    var2 = var7;
                    continue block0;
                }
            }
        }
        Display.setDisplayMode((DisplayMode)var2);
        this.displayWidth = var2.getWidth();
        this.displayHeight = var2.getHeight();
    }

    private void loadScreen() throws LWJGLException {
        GL11.glEnable((int)3553);
        this.renderEngine.bindTexture(locationMojangPng);
        ScaledResolution var1 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
        int var2 = var1.getScaleFactor();
        Framebuffer var3 = new Framebuffer(var1.getScaledWidth() * var2, var1.getScaledHeight() * var2, true);
        var3.bindFramebuffer(false);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)var1.getScaledWidth(), (double)var1.getScaledHeight(), (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3553);
        this.renderEngine.bindTexture(locationMojangPng);
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.setColorOpaque_I(16777215);
        var4.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        var4.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        var4.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        var4.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        var4.draw();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        var4.setColorOpaque_I(16777215);
        int var5 = 256;
        int var6 = 256;
        this.scaledTessellator((var1.getScaledWidth() - var5) / 2, (var1.getScaledHeight() - var6) / 2, 0, 0, var5, var6);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        var3.unbindFramebuffer();
        var3.framebufferRender(var1.getScaledWidth() * var2, var1.getScaledHeight() * var2);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.1f);
        GL11.glFlush();
        this.func_147120_f();
    }

    public void scaledTessellator(int par1, int par2, int par3, int par4, int par5, int par6) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(par1 + 0, par2 + par6, 0.0, (float)(par3 + 0) * var7, (float)(par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + par6, 0.0, (float)(par3 + par5) * var7, (float)(par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + 0, 0.0, (float)(par3 + par5) * var7, (float)(par4 + 0) * var8);
        var9.addVertexWithUV(par1 + 0, par2 + 0, 0.0, (float)(par3 + 0) * var7, (float)(par4 + 0) * var8);
        var9.draw();
    }

    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }

    public void displayGuiScreen(GuiScreen p_147108_1_) {
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (p_147108_1_ == null && this.theWorld == null) {
            p_147108_1_ = new HookGuiMainMenu();
        } else if (p_147108_1_ == null && this.thePlayer.getHealth() <= 0.0f) {
            p_147108_1_ = new GuiGameOver();
        }
        if (p_147108_1_ instanceof HookGuiMainMenu) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().func_146231_a();
        }
        this.currentScreen = p_147108_1_;
        if (p_147108_1_ != null) {
            this.setIngameNotInFocus();
            ScaledResolution var2 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
            int var3 = var2.getScaledWidth();
            int var4 = var2.getScaledHeight();
            p_147108_1_.setWorldAndResolution(this, var3, var4);
            this.skipRenderWorld = false;
        } else {
            this.mcSoundHandler.func_147687_e();
            this.setIngameFocus();
        }
    }

    private void checkGLError(String par1Str) {
        int var2 = GL11.glGetError();
        if (var2 != 0) {
            String var3 = GLU.gluErrorString((int)var2);
            logger.error("########## GL ERROR ##########");
            logger.error("@ " + par1Str);
            logger.error(String.valueOf(var2) + ": " + var3);
        }
    }

    public void shutdownMinecraftApplet() {
        EventGameShutdown eventClose = new EventGameShutdown(System.nanoTime());
        eventClose.onEvent();
        if (eventClose.isCancelled()) {
            eventClose.setCancelled(false);
            return;
        }
        try {
            logger.info("Stopping!");
            try {
                this.loadWorld(null);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            this.mcSoundHandler.func_147685_d();
        }
        finally {
            Display.destroy();
            if (!this.hasCrashed) {
                Utils.setOnline(false);
                System.exit(0);
            }
        }
        System.gc();
    }

    /*
     * Exception decompiling
     */
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:396)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:474)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2880)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:837)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:217)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:162)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:357)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:769)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:701)
        // org.benf.cfr.reader.Main.doJar(Main.java:134)
        // org.benf.cfr.reader.Main.main(Main.java:189)
        throw new IllegalStateException("Decompilation failed");
    }

    private void runGameLoop() {
        AxisAlignedBB.getAABBPool().cleanPool();
        if (this.theWorld != null) {
            this.theWorld.getWorldVec3Pool().clear();
        }
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        if (this.isGamePaused && this.theWorld != null) {
            float var1 = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = var1;
        } else {
            this.timer.updateTimer();
        }
        if ((this.theWorld == null || this.currentScreen == null) && this.refreshTexturePacksScheduled) {
            this.refreshTexturePacksScheduled = false;
            this.refreshResources();
        }
        long var5 = System.nanoTime();
        this.mcProfiler.startSection("tick");
        int var3 = 0;
        while (var3 < this.timer.elapsedTicks) {
            this.runTick();
            ++var3;
        }
        this.mcProfiler.endStartSection("preRenderErrors");
        long var6 = System.nanoTime() - var5;
        this.checkGLError("Pre render");
        RenderBlocks.fancyGrass = this.gameSettings.fancyGraphics;
        this.mcProfiler.endStartSection("sound");
        this.mcSoundHandler.func_147691_a(this.thePlayer, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection("render");
        GL11.glPushMatrix();
        GL11.glClear((int)16640);
        this.mcFramebuffer.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        GL11.glEnable((int)3553);
        if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
            this.gameSettings.thirdPersonView = 0;
        }
        this.mcProfiler.endSection();
        if (!this.skipRenderWorld) {
            this.mcProfiler.endStartSection("gameRenderer");
            this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
            this.mcProfiler.endSection();
        }
        GL11.glFlush();
        this.mcProfiler.endSection();
        if (!Display.isActive() && this.fullscreen) {
            this.toggleFullscreen();
        }
        if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }
            this.mcProfiler.profilingEnabled = true;
            this.displayDebugInfo(var6);
        } else {
            this.mcProfiler.profilingEnabled = false;
            this.prevFrameTime = System.nanoTime();
        }
        this.guiAchievement.func_146254_a();
        this.mcFramebuffer.unbindFramebuffer();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.mcFramebuffer.framebufferRender(this.displayWidth, this.displayHeight);
        GL11.glPopMatrix();
        this.mcProfiler.startSection("root");
        this.func_147120_f();
        Thread.yield();
        this.screenshotListener();
        this.checkGLError("Post render");
        ++this.fpsCounter;
        this.isGamePaused = this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic();
        while (Minecraft.getSystemTime() >= this.debugUpdateTime + 1000) {
            debugFPS = this.fpsCounter;
            this.debug = String.valueOf(debugFPS) + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
            WorldRenderer.chunksUpdated = 0;
            this.debugUpdateTime += 1000;
            this.fpsCounter = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (this.usageSnooper.isSnooperRunning()) continue;
            this.usageSnooper.startSnooper();
        }
        this.mcProfiler.endSection();
        if (this.isFramerateLimitBelowMax()) {
            Display.sync((int)this.getLimitFramerate());
        }
    }

    public void func_147120_f() {
        Display.update();
        if (!this.fullscreen && Display.wasResized()) {
            int var1 = this.displayWidth;
            int var2 = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != var1 || this.displayHeight != var2) {
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
                this.resize(this.displayWidth, this.displayHeight);
            }
        }
    }

    public int getLimitFramerate() {
        return this.theWorld == null && this.currentScreen != null ? 30 : this.gameSettings.limitFramerate;
    }

    public boolean isFramerateLimitBelowMax() {
        if ((float)this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) {
            return true;
        }
        return false;
    }

    public void freeMemory() {
        try {
            memoryReserve = new byte[0];
            this.renderGlobal.deleteAllDisplayLists();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            System.gc();
            AxisAlignedBB.getAABBPool().clearPool();
            this.theWorld.getWorldVec3Pool().clearAndFreeCache();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            System.gc();
            this.loadWorld(null);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        System.gc();
    }

    private void screenshotListener() {
        if (this.gameSettings.keyBindScreenshot.isPressed()) {
            if (!this.isTakingScreenshot) {
                this.isTakingScreenshot = true;
                this.ingameGUI.getChatGUI().func_146227_a(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.mcFramebuffer));
            }
        } else {
            this.isTakingScreenshot = false;
        }
    }

    private void updateDebugProfilerName(int par1) {
        List var2 = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (var2 != null && !var2.isEmpty()) {
            Profiler.Result var3 = (Profiler.Result)var2.remove(0);
            if (par1 == 0) {
                int var4;
                if (var3.field_76331_c.length() > 0 && (var4 = this.debugProfilerName.lastIndexOf(".")) >= 0) {
                    this.debugProfilerName = this.debugProfilerName.substring(0, var4);
                }
            } else if (--par1 < var2.size() && !((Profiler.Result)var2.get((int)par1)).field_76331_c.equals("unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName = String.valueOf(this.debugProfilerName) + ".";
                }
                this.debugProfilerName = String.valueOf(this.debugProfilerName) + ((Profiler.Result)var2.get((int)par1)).field_76331_c;
            }
        }
    }

    private void displayDebugInfo(long par1) {
        if (this.mcProfiler.profilingEnabled) {
            int var13;
            List var3 = this.mcProfiler.getProfilingData(this.debugProfilerName);
            Profiler.Result var4 = (Profiler.Result)var3.remove(0);
            GL11.glClear((int)256);
            GL11.glMatrixMode((int)5889);
            GL11.glEnable((int)2903);
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)this.displayWidth, (double)this.displayHeight, (double)0.0, (double)1000.0, (double)3000.0);
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            Tessellator var5 = Tessellator.instance;
            int var6 = 160;
            int var7 = this.displayWidth - var6 - 10;
            int var8 = this.displayHeight - var6 * 2;
            GL11.glEnable((int)3042);
            var5.startDrawingQuads();
            var5.setColorRGBA_I(0, 200);
            var5.addVertex((float)var7 - (float)var6 * 1.1f, (float)var8 - (float)var6 * 0.6f - 16.0f, 0.0);
            var5.addVertex((float)var7 - (float)var6 * 1.1f, var8 + var6 * 2, 0.0);
            var5.addVertex((float)var7 + (float)var6 * 1.1f, var8 + var6 * 2, 0.0);
            var5.addVertex((float)var7 + (float)var6 * 1.1f, (float)var8 - (float)var6 * 0.6f - 16.0f, 0.0);
            var5.draw();
            GL11.glDisable((int)3042);
            double var9 = 0.0;
            int var11 = 0;
            while (var11 < var3.size()) {
                float var16;
                float var17;
                float var15;
                Profiler.Result var12 = (Profiler.Result)var3.get(var11);
                var13 = MathHelper.floor_double(var12.field_76332_a / 4.0) + 1;
                var5.startDrawing(6);
                var5.setColorOpaque_I(var12.func_76329_a());
                var5.addVertex(var7, var8, 0.0);
                int var14 = var13;
                while (var14 >= 0) {
                    var15 = (float)((var9 + var12.field_76332_a * (double)var14 / (double)var13) * 3.141592653589793 * 2.0 / 100.0);
                    var16 = MathHelper.sin(var15) * (float)var6;
                    var17 = MathHelper.cos(var15) * (float)var6 * 0.5f;
                    var5.addVertex((float)var7 + var16, (float)var8 - var17, 0.0);
                    --var14;
                }
                var5.draw();
                var5.startDrawing(5);
                var5.setColorOpaque_I((var12.func_76329_a() & 16711422) >> 1);
                var14 = var13;
                while (var14 >= 0) {
                    var15 = (float)((var9 + var12.field_76332_a * (double)var14 / (double)var13) * 3.141592653589793 * 2.0 / 100.0);
                    var16 = MathHelper.sin(var15) * (float)var6;
                    var17 = MathHelper.cos(var15) * (float)var6 * 0.5f;
                    var5.addVertex((float)var7 + var16, (float)var8 - var17, 0.0);
                    var5.addVertex((float)var7 + var16, (float)var8 - var17 + 10.0f, 0.0);
                    --var14;
                }
                var5.draw();
                var9 += var12.field_76332_a;
                ++var11;
            }
            DecimalFormat var19 = new DecimalFormat("##0.00");
            GL11.glEnable((int)3553);
            String var18 = "";
            if (!var4.field_76331_c.equals("unspecified")) {
                var18 = String.valueOf(var18) + "[0] ";
            }
            var18 = var4.field_76331_c.length() == 0 ? String.valueOf(var18) + "ROOT " : String.valueOf(var18) + var4.field_76331_c + " ";
            var13 = 16777215;
            this.fontRenderer.drawStringWithShadow(var18, var7 - var6, var8 - var6 / 2 - 16, var13);
            var18 = String.valueOf(var19.format(var4.field_76330_b)) + "%";
            this.fontRenderer.drawStringWithShadow(var18, var7 + var6 - this.fontRenderer.getStringWidth(var18), var8 - var6 / 2 - 16, var13);
            int var21 = 0;
            while (var21 < var3.size()) {
                Profiler.Result var20 = (Profiler.Result)var3.get(var21);
                String var22 = "";
                var22 = var20.field_76331_c.equals("unspecified") ? String.valueOf(var22) + "[?] " : String.valueOf(var22) + "[" + (var21 + 1) + "] ";
                var22 = String.valueOf(var22) + var20.field_76331_c;
                this.fontRenderer.drawStringWithShadow(var22, var7 - var6, var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
                var22 = String.valueOf(var19.format(var20.field_76332_a)) + "%";
                this.fontRenderer.drawStringWithShadow(var22, var7 + var6 - 50 - this.fontRenderer.getStringWidth(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
                var22 = String.valueOf(var19.format(var20.field_76330_b)) + "%";
                this.fontRenderer.drawStringWithShadow(var22, var7 + var6 - this.fontRenderer.getStringWidth(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
                ++var21;
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }

    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.leftClickCounter = 10000;
        }
    }

    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            KeyBinding.unPressAllKeys();
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }

    public void displayInGameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
                this.mcSoundHandler.func_147689_b();
            }
        }
    }

    private void func_147115_a(boolean p_147115_1_) {
        if (!p_147115_1_) {
            this.leftClickCounter = 0;
        }
        if (this.leftClickCounter <= 0) {
            if (p_147115_1_ && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int var2 = this.objectMouseOver.blockX;
                int var3 = this.objectMouseOver.blockY;
                int var4 = this.objectMouseOver.blockZ;
                if (this.theWorld.getBlock(var2, var3, var4).getMaterial() != Material.air) {
                    this.playerController.onPlayerDamageBlock(var2, var3, var4, this.objectMouseOver.sideHit);
                    if (this.thePlayer.isCurrentToolAdventureModeExempt(var2, var3, var4)) {
                        this.effectRenderer.addBlockHitEffects(var2, var3, var4, this.objectMouseOver.sideHit);
                        this.thePlayer.swingItem();
                    }
                }
            } else {
                this.playerController.resetBlockRemoving();
            }
        }
    }

    public void func_147116_af() {
        if (this.leftClickCounter <= 0) {
            this.thePlayer.swingItem();
            if (this.objectMouseOver == null) {
                logger.error("Null returned as 'hitResult', this shouldn't happen!");
                if (this.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            } else {
                switch (SwitchMovingObjectType.field_151437_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 1: {
                        this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
                        break;
                    }
                    case 2: {
                        int var1 = this.objectMouseOver.blockX;
                        int var2 = this.objectMouseOver.blockY;
                        int var3 = this.objectMouseOver.blockZ;
                        if (this.theWorld.getBlock(var1, var2, var3).getMaterial() == Material.air) {
                            if (!this.playerController.isNotCreative()) break;
                            this.leftClickCounter = 10;
                            break;
                        }
                        this.playerController.clickBlock(var1, var2, var3, this.objectMouseOver.sideHit);
                    }
                }
            }
        }
    }

    public void func_147121_ag() {
        ItemStack var7;
        this.rightClickDelayTimer = 4;
        boolean var1 = true;
        ItemStack var2 = this.thePlayer.inventory.getCurrentItem();
        if (this.objectMouseOver == null) {
            logger.warn("Null returned as 'hitResult', this shouldn't happen!");
        } else {
            switch (SwitchMovingObjectType.field_151437_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                case 1: {
                    if (!this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) break;
                    var1 = false;
                    break;
                }
                case 2: {
                    int var6;
                    int var3 = this.objectMouseOver.blockX;
                    int var4 = this.objectMouseOver.blockY;
                    int var5 = this.objectMouseOver.blockZ;
                    if (this.theWorld.getBlock(var3, var4, var5).getMaterial() == Material.air) break;
                    int n = var6 = var2 != null ? var2.stackSize : 0;
                    if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, var2, var3, var4, var5, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
                        var1 = false;
                        this.thePlayer.swingItem();
                    }
                    if (var2 == null) {
                        return;
                    }
                    if (var2.stackSize == 0) {
                        this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                        break;
                    }
                    if (var2.stackSize == var6 && !this.playerController.isInCreativeMode()) break;
                    this.entityRenderer.itemRenderer.resetEquippedProgress();
                }
            }
        }
        if (var1 && (var7 = this.thePlayer.inventory.getCurrentItem()) != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, var7)) {
            this.entityRenderer.itemRenderer.resetEquippedProgress2();
        }
    }

    public void toggleFullscreen() {
        try {
            boolean bl = this.fullscreen = !this.fullscreen;
            if (this.fullscreen) {
                this.updateDisplayMode();
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            } else {
                Display.setDisplayMode((DisplayMode)new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
                this.displayWidth = this.tempDisplayWidth;
                this.displayHeight = this.tempDisplayHeight;
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            }
            if (this.currentScreen != null) {
                this.resize(this.displayWidth, this.displayHeight);
            } else {
                this.updateFramebufferSize();
            }
            Display.setFullscreen((boolean)this.fullscreen);
            Display.setVSyncEnabled((boolean)this.gameSettings.enableVsync);
            this.func_147120_f();
        }
        catch (Exception var2) {
            logger.error("Couldn't toggle fullscreen", (Throwable)var2);
        }
    }

    private void resize(int par1, int par2) {
        this.displayWidth = par1 <= 0 ? 1 : par1;
        int n = this.displayHeight = par2 <= 0 ? 1 : par2;
        if (this.currentScreen != null) {
            ScaledResolution var3 = new ScaledResolution(this.gameSettings, par1, par2);
            int var4 = var3.getScaledWidth();
            int var5 = var3.getScaledHeight();
            this.currentScreen.setWorldAndResolution(this, var4, var5);
        }
        this.loadingScreen = new LoadingScreenRenderer(this);
        this.updateFramebufferSize();
    }

    private void updateFramebufferSize() {
        this.mcFramebuffer.createBindFramebuffer(this.displayWidth, this.displayHeight);
        if (this.entityRenderer != null) {
            this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void runTick() {
        block85 : {
            block86 : {
                if (this.rightClickDelayTimer > 0) {
                    --this.rightClickDelayTimer;
                }
                this.mcProfiler.startSection("gui");
                if (!this.isGamePaused) {
                    this.ingameGUI.updateTick();
                }
                this.mcProfiler.endStartSection("pick");
                this.entityRenderer.getMouseOver(1.0f);
                this.mcProfiler.endStartSection("gameMode");
                if (!this.isGamePaused && this.theWorld != null) {
                    this.playerController.updateController();
                }
                this.mcProfiler.endStartSection("textures");
                if (!this.isGamePaused) {
                    this.renderEngine.tick();
                }
                if (this.currentScreen == null && this.thePlayer != null) {
                    if (this.thePlayer.getHealth() <= 0.0f) {
                        this.displayGuiScreen(null);
                    } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
                        this.displayGuiScreen(new GuiSleepMP());
                    }
                } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
                    this.displayGuiScreen(null);
                }
                if (this.currentScreen != null) {
                    this.leftClickCounter = 10000;
                }
                if (this.currentScreen != null) {
                    try {
                        this.currentScreen.handleInput();
                    }
                    catch (Throwable var6) {
                        var2 = CrashReport.makeCrashReport(var6, "Updating screen events");
                        var3 = var2.makeCategory("Affected screen");
                        var3.addCrashSectionCallable("Screen name", new Callable(){
                            private static final String __OBFID = "CL_00000640";

                            public String call() {
                                return Minecraft.this.currentScreen.getClass().getCanonicalName();
                            }
                        });
                        throw new ReportedException(var2);
                    }
                    if (this.currentScreen != null) {
                        try {
                            this.currentScreen.updateScreen();
                        }
                        catch (Throwable var5) {
                            var2 = CrashReport.makeCrashReport(var5, "Ticking screen");
                            var3 = var2.makeCategory("Affected screen");
                            var3.addCrashSectionCallable("Screen name", new Callable(){
                                private static final String __OBFID = "CL_00000642";

                                public String call() {
                                    return Minecraft.this.currentScreen.getClass().getCanonicalName();
                                }
                            });
                            throw new ReportedException(var2);
                        }
                    }
                }
                if (this.currentScreen != null && !this.currentScreen.field_146291_p) break block85;
                this.mcProfiler.endStartSection("mouse");
                while (Mouse.next()) {
                    var1 = Mouse.getEventButton();
                    if (Minecraft.isRunningOnMac && var1 == 0 && (Keyboard.isKeyDown((int)29) || Keyboard.isKeyDown((int)157))) {
                        var1 = 1;
                    }
                    KeyBinding.setKeyBindState(var1 - 100, Mouse.getEventButtonState());
                    if (Mouse.getEventButtonState()) {
                        eventClick = new EventOnClick(Mouse.getEventButton());
                        eventClick.onEvent();
                        KeyBinding.onTick(var1 - 100);
                    }
                    if ((var9 = Minecraft.getSystemTime() - this.systemTime) > 200) continue;
                    var4 = Mouse.getEventDWheel();
                    if (var4 != 0) {
                        this.thePlayer.inventory.changeCurrentItem(var4);
                        if (this.gameSettings.noclip) {
                            if (var4 > 0) {
                                var4 = 1;
                            }
                            if (var4 < 0) {
                                var4 = -1;
                            }
                            this.gameSettings.noclipRate += (float)var4 * 0.25f;
                        }
                    }
                    if (this.currentScreen == null) {
                        if (this.inGameHasFocus || !Mouse.getEventButtonState()) continue;
                        this.setIngameFocus();
                        continue;
                    }
                    if (this.currentScreen == null) continue;
                    this.currentScreen.handleMouseInput();
                }
                if (this.leftClickCounter > 0) {
                    --this.leftClickCounter;
                }
                this.mcProfiler.endStartSection("keyboard");
                while (Keyboard.next()) {
                    KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                    if (!Keyboard.getEventKeyState()) continue;
                    KeyBinding.onTick(Keyboard.getEventKey());
                    if (Keyboard.getEventKey() == 0) continue;
                    eventKey = new EventKeyDown(Keyboard.getEventKey());
                    eventKey.onEvent();
                    if (eventKey.isCancelled()) {
                        eventKey.setCancelled(false);
                        continue;
                    }
                    if (this.field_83002_am > 0) {
                        if (Minecraft.getSystemTime() - this.field_83002_am >= 6000) {
                            throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                        }
                        if (!Keyboard.isKeyDown((int)46) || !Keyboard.isKeyDown((int)61)) {
                            this.field_83002_am = -1;
                        }
                    } else if (Keyboard.isKeyDown((int)46) && Keyboard.isKeyDown((int)61)) {
                        this.field_83002_am = Minecraft.getSystemTime();
                    }
                    if (!Keyboard.getEventKeyState()) continue;
                    if (Keyboard.getEventKey() == 62 && this.entityRenderer != null) {
                        this.entityRenderer.deactivateShader();
                    }
                    if (Keyboard.getEventKey() == 87) {
                        this.toggleFullscreen();
                        continue;
                    }
                    if (this.currentScreen != null) {
                        this.currentScreen.handleKeyboardInput();
                    } else {
                        if (Keyboard.getEventKey() == 1) {
                            this.displayInGameMenu();
                        }
                        if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown((int)61)) {
                            this.refreshResources();
                        }
                        if (Keyboard.getEventKey() == 20 && Keyboard.isKeyDown((int)61)) {
                            this.refreshResources();
                        }
                        if (Keyboard.getEventKey() == 33 && Keyboard.isKeyDown((int)61)) {
                            var8 = Keyboard.isKeyDown((int)42) | Keyboard.isKeyDown((int)54);
                            this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, var8 != false ? -1 : 1);
                        }
                        if (Keyboard.getEventKey() == 30 && Keyboard.isKeyDown((int)61)) {
                            this.renderGlobal.loadRenderers();
                        }
                        if (Keyboard.getEventKey() == 35 && Keyboard.isKeyDown((int)61)) {
                            this.gameSettings.advancedItemTooltips = this.gameSettings.advancedItemTooltips == false;
                            this.gameSettings.saveOptions();
                        }
                        if (Keyboard.getEventKey() == 48 && Keyboard.isKeyDown((int)61)) {
                            v0 = RenderManager.field_85095_o = RenderManager.field_85095_o == false;
                        }
                        if (Keyboard.getEventKey() == 25 && Keyboard.isKeyDown((int)61)) {
                            this.gameSettings.pauseOnLostFocus = this.gameSettings.pauseOnLostFocus == false;
                            this.gameSettings.saveOptions();
                        }
                        if (Keyboard.getEventKey() == 59) {
                            v1 = this.gameSettings.hideGUI = this.gameSettings.hideGUI == false;
                        }
                        if (Keyboard.getEventKey() == 61) {
                            this.gameSettings.showDebugInfo = this.gameSettings.showDebugInfo == false;
                            this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                        }
                        if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                            ++this.gameSettings.thirdPersonView;
                            if (this.gameSettings.thirdPersonView > 2) {
                                this.gameSettings.thirdPersonView = 0;
                            }
                        }
                        if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                            v2 = this.gameSettings.smoothCamera = this.gameSettings.smoothCamera == false;
                        }
                    }
                    if (!this.gameSettings.showDebugInfo || !this.gameSettings.showDebugProfilerChart) continue;
                    if (Keyboard.getEventKey() == 11) {
                        this.updateDebugProfilerName(0);
                    }
                    var1 = 0;
                    while (var1 < 9) {
                        if (Keyboard.getEventKey() == 2 + var1) {
                            this.updateDebugProfilerName(var1 + 1);
                        }
                        ++var1;
                    }
                }
                var1 = 0;
                while (var1 < 9) {
                    if (this.gameSettings.keyBindsHotbar[var1].isPressed()) {
                        this.thePlayer.inventory.currentItem = var1;
                    }
                    ++var1;
                }
                var8 = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;
                while (this.gameSettings.keyBindInventory.isPressed()) {
                    if (this.playerController.func_110738_j()) {
                        this.thePlayer.func_110322_i();
                        continue;
                    }
                    this.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    this.displayGuiScreen(new GuiInventory(this.thePlayer));
                }
                while (this.gameSettings.keyBindDrop.isPressed()) {
                    this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
                }
                while (this.gameSettings.keyBindChat.isPressed() && var8) {
                    this.displayGuiScreen(new GuiChat());
                }
                if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && var8) {
                    this.displayGuiScreen(new GuiChat("/"));
                }
                if (!this.thePlayer.isUsingItem()) ** GOTO lbl174
                if (!this.gameSettings.keyBindUseItem.getIsKeyPressed()) {
                    this.playerController.onStoppedUsingItem(this.thePlayer);
                }
                while (this.gameSettings.keyBindAttack.isPressed()) {
                }
                while (this.gameSettings.keyBindUseItem.isPressed()) {
                }
                while (this.gameSettings.keyBindPickBlock.isPressed()) {
                }
                break block86;
lbl-1000: // 1 sources:
                {
                    this.func_147116_af();
lbl174: // 2 sources:
                    ** while (this.gameSettings.keyBindAttack.isPressed())
                }
lbl175: // 2 sources:
                while (this.gameSettings.keyBindUseItem.isPressed()) {
                    this.func_147121_ag();
                }
                while (this.gameSettings.keyBindPickBlock.isPressed()) {
                    this.func_147112_ai();
                }
            }
            if (this.gameSettings.keyBindUseItem.getIsKeyPressed() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
                this.func_147121_ag();
            }
            this.func_147115_a(this.currentScreen == null && this.gameSettings.keyBindAttack.getIsKeyPressed() != false && this.inGameHasFocus != false);
        }
        if (this.theWorld != null) {
            if (this.thePlayer != null) {
                ++this.joinPlayerCounter;
                if (this.joinPlayerCounter == 30) {
                    this.joinPlayerCounter = 0;
                    this.theWorld.joinEntityInSurroundings(this.thePlayer);
                }
            }
            this.mcProfiler.endStartSection("gameRenderer");
            if (!this.isGamePaused) {
                this.entityRenderer.updateRenderer();
            }
            this.mcProfiler.endStartSection("levelRenderer");
            if (!this.isGamePaused) {
                this.renderGlobal.updateClouds();
            }
            this.mcProfiler.endStartSection("level");
            if (!this.isGamePaused) {
                if (this.theWorld.lastLightningBolt > 0) {
                    --this.theWorld.lastLightningBolt;
                }
                this.theWorld.updateEntities();
            }
        }
        if (!this.isGamePaused) {
            this.mcMusicTicker.update();
            this.mcSoundHandler.update();
        }
        if (this.theWorld != null) {
            if (!this.isGamePaused) {
                this.theWorld.setAllowedSpawnTypes(this.theWorld.difficultySetting != EnumDifficulty.PEACEFUL, true);
                try {
                    this.theWorld.tick();
                }
                catch (Throwable var7) {
                    var2 = CrashReport.makeCrashReport(var7, "Exception in world tick");
                    if (this.theWorld == null) {
                        var3 = var2.makeCategory("Affected level");
                        var3.addCrashSection("Problem", "Level is null!");
                        throw new ReportedException(var2);
                    }
                    this.theWorld.addWorldInfoToCrashReport(var2);
                    throw new ReportedException(var2);
                }
            }
            this.mcProfiler.endStartSection("animateTick");
            if (!this.isGamePaused && this.theWorld != null) {
                this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
            }
            this.mcProfiler.endStartSection("particles");
            if (!this.isGamePaused) {
                this.effectRenderer.updateEffects();
            }
        } else if (this.myNetworkManager != null) {
            this.mcProfiler.endStartSection("pendingConnection");
            this.myNetworkManager.processReceivedPackets();
        }
        this.mcProfiler.endSection();
        this.systemTime = Minecraft.getSystemTime();
    }

    public void launchIntegratedServer(String par1Str, String par2Str, WorldSettings par3WorldSettings) {
        this.loadWorld(null);
        System.gc();
        ISaveHandler var4 = this.saveLoader.getSaveLoader(par1Str, false);
        WorldInfo var5 = var4.loadWorldInfo();
        if (var5 == null && par3WorldSettings != null) {
            var5 = new WorldInfo(par3WorldSettings, par1Str);
            var4.saveWorldInfo(var5);
        }
        if (par3WorldSettings == null) {
            par3WorldSettings = new WorldSettings(var5);
        }
        try {
            this.theIntegratedServer = new IntegratedServer(this, par1Str, par2Str, par3WorldSettings);
            this.theIntegratedServer.startServerThread();
            this.integratedServerIsRunning = true;
        }
        catch (Throwable var10) {
            CrashReport var7 = CrashReport.makeCrashReport(var10, "Starting integrated server");
            CrashReportCategory var8 = var7.makeCategory("Starting integrated server");
            var8.addCrashSection("Level ID", par1Str);
            var8.addCrashSection("Level Name", par2Str);
            throw new ReportedException(var7);
        }
        this.loadingScreen.displayProgressMessage(I18n.format("menu.loadingLevel", new Object[0]));
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            String var6 = this.theIntegratedServer.getUserMessage();
            if (var6 != null) {
                this.loadingScreen.resetProgresAndWorkingMessage(I18n.format(var6, new Object[0]));
            } else {
                this.loadingScreen.resetProgresAndWorkingMessage("");
            }
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException var7) {
                // empty catch block
            }
        }
        this.displayGuiScreen(null);
        SocketAddress var11 = this.theIntegratedServer.func_147137_ag().addLocalEndpoint();
        NetworkManager var12 = NetworkManager.provideLocalClient(var11);
        var12.setNetHandler(new NetHandlerLoginClient(var12, this, null));
        var12.scheduleOutboundPacket(new C00Handshake(4, var11.toString(), 0, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
        var12.scheduleOutboundPacket(new C00PacketLoginStart(this.getSession().func_148256_e()), new GenericFutureListener[0]);
        this.myNetworkManager = var12;
    }

    public void loadWorld(WorldClient par1WorldClient) {
        this.loadWorld(par1WorldClient, "");
    }

    public void loadWorld(WorldClient par1WorldClient, String par2Str) {
        if (par1WorldClient == null) {
            NetHandlerPlayClient var3 = this.getNetHandler();
            if (var3 != null) {
                var3.cleanup();
            }
            if (this.theIntegratedServer != null) {
                this.theIntegratedServer.initiateShutdown();
            }
            this.theIntegratedServer = null;
            this.guiAchievement.func_146257_b();
            this.entityRenderer.getMapItemRenderer().func_148249_a();
        }
        this.renderViewEntity = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(par2Str);
            this.loadingScreen.resetProgresAndWorkingMessage("");
        }
        if (par1WorldClient == null && this.theWorld != null) {
            if (this.mcResourcePackRepository.func_148530_e() != null) {
                this.scheduleResourcesRefresh();
            }
            this.mcResourcePackRepository.func_148529_f();
            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }
        this.mcSoundHandler.func_147690_c();
        this.theWorld = par1WorldClient;
        if (par1WorldClient != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(par1WorldClient);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(par1WorldClient);
            }
            if (this.thePlayer == null) {
                this.thePlayer = this.playerController.func_147493_a(par1WorldClient, new StatFileWriter());
                this.playerController.flipPlayer(this.thePlayer);
            }
            this.thePlayer.preparePlayerToSpawn();
            par1WorldClient.spawnEntityInWorld(this.thePlayer);
            this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            this.playerController.setPlayerCapabilities(this.thePlayer);
            this.renderViewEntity = this.thePlayer;
        } else {
            this.saveLoader.flushCache();
            this.thePlayer = null;
        }
        System.gc();
        this.systemTime = 0;
    }

    public String debugInfoRenders() {
        return this.renderGlobal.getDebugInfoRenders();
    }

    public String getEntityDebug() {
        return this.renderGlobal.getDebugInfoEntities();
    }

    public String getWorldProviderName() {
        return this.theWorld.getProviderName();
    }

    public String debugInfoEntities() {
        return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.getDebugLoadedEntities();
    }

    public void setDimensionAndSpawnPlayer(int par1) {
        this.theWorld.setSpawnLocation();
        this.theWorld.removeAllEntities();
        int var2 = 0;
        String var3 = null;
        if (this.thePlayer != null) {
            var2 = this.thePlayer.getEntityId();
            this.theWorld.removeEntity(this.thePlayer);
            var3 = this.thePlayer.func_142021_k();
        }
        this.renderViewEntity = null;
        this.thePlayer = this.playerController.func_147493_a(this.theWorld, this.thePlayer == null ? new StatFileWriter() : this.thePlayer.func_146107_m());
        this.thePlayer.dimension = par1;
        this.renderViewEntity = this.thePlayer;
        this.thePlayer.preparePlayerToSpawn();
        this.thePlayer.func_142020_c(var3);
        this.theWorld.spawnEntityInWorld(this.thePlayer);
        this.playerController.flipPlayer(this.thePlayer);
        this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        this.thePlayer.setEntityId(var2);
        this.playerController.setPlayerCapabilities(this.thePlayer);
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }

    public final boolean isDemo() {
        return this.isDemo;
    }

    public NetHandlerPlayClient getNetHandler() {
        return this.thePlayer != null ? this.thePlayer.sendQueue : null;
    }

    public static boolean isGuiEnabled() {
        if (theMinecraft != null && Minecraft.theMinecraft.gameSettings.hideGUI) {
            return false;
        }
        return true;
    }

    public static boolean isFancyGraphicsEnabled() {
        if (theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics) {
            return true;
        }
        return false;
    }

    public static boolean isAmbientOcclusionEnabled() {
        if (theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0) {
            return true;
        }
        return false;
    }

    private void func_147112_ai() {
        if (this.objectMouseOver != null) {
            int var5;
            Item var2;
            boolean var1 = this.thePlayer.capabilities.isCreativeMode;
            int var3 = 0;
            boolean var4 = false;
            if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                var5 = this.objectMouseOver.blockX;
                int var6 = this.objectMouseOver.blockY;
                int var7 = this.objectMouseOver.blockZ;
                Block var8 = this.theWorld.getBlock(var5, var6, var7);
                if (var8.getMaterial() == Material.air) {
                    return;
                }
                var2 = var8.getItem(this.theWorld, var5, var6, var7);
                if (var2 == null) {
                    return;
                }
                var4 = var2.getHasSubtypes();
                Block var9 = var2 instanceof ItemBlock && !var8.isFlowerPot() ? Block.getBlockFromItem(var2) : var8;
                var3 = var9.getDamageValue(this.theWorld, var5, var6, var7);
            } else {
                if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !var1) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    var2 = Items.painting;
                } else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
                    var2 = Items.lead;
                } else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    EntityItemFrame var10 = (EntityItemFrame)this.objectMouseOver.entityHit;
                    ItemStack var12 = var10.getDisplayedItem();
                    if (var12 == null) {
                        var2 = Items.item_frame;
                    } else {
                        var2 = var12.getItem();
                        var3 = var12.getItemDamage();
                        var4 = true;
                    }
                } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    EntityMinecart var11 = (EntityMinecart)this.objectMouseOver.entityHit;
                    var2 = var11.getMinecartType() == 2 ? Items.furnace_minecart : (var11.getMinecartType() == 1 ? Items.chest_minecart : (var11.getMinecartType() == 3 ? Items.tnt_minecart : (var11.getMinecartType() == 5 ? Items.hopper_minecart : (var11.getMinecartType() == 6 ? Items.command_block_minecart : Items.minecart))));
                } else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    var2 = Items.boat;
                } else {
                    var2 = Items.spawn_egg;
                    var3 = EntityList.getEntityID(this.objectMouseOver.entityHit);
                    var4 = true;
                    if (var3 <= 0 || !EntityList.entityEggs.containsKey(var3)) {
                        return;
                    }
                }
            }
            this.thePlayer.inventory.func_146030_a(var2, var3, var4, var1);
            if (var1) {
                var5 = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + this.thePlayer.inventory.currentItem;
                this.playerController.sendSlotPacket(this.thePlayer.inventory.getStackInSlot(this.thePlayer.inventory.currentItem), var5);
            }
        }
    }

    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport.getCategory().addCrashSectionCallable("Launched Version", new Callable(){
            private static final String __OBFID = "CL_00000643";

            public String call() {
                return Minecraft.this.launchedVersion;
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("LWJGL", new Callable(){
            private static final String __OBFID = "CL_00000644";

            public String call() {
                return Sys.getVersion();
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("OpenGL", new Callable(){
            private static final String __OBFID = "CL_00000645";

            public String call() {
                return String.valueOf(GL11.glGetString((int)7937)) + " GL version " + GL11.glGetString((int)7938) + ", " + GL11.glGetString((int)7936);
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Is Modded", new Callable(){
            private static final String __OBFID = "CL_00000646";

            public String call() {
                String var1 = ClientBrandRetriever.getClientModName();
                return !var1.equals("vanilla") ? "Definitely; Client brand changed to '" + var1 + "'" : (Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Type", new Callable(){
            private static final String __OBFID = "CL_00000647";

            public String call() {
                return "Client (map_client.txt)";
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Resource Packs", new Callable(){
            private static final String __OBFID = "CL_00000633";

            public String call() {
                return Minecraft.this.gameSettings.resourcePacks.toString();
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Current Language", new Callable(){
            private static final String __OBFID = "CL_00000634";

            public String call() {
                return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Profiler Position", new Callable(){
            private static final String __OBFID = "CL_00000635";

            public String call() {
                return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Vec3 Pool Size", new Callable(){
            private static final String __OBFID = "CL_00000636";

            public String call() {
                int var1 = Minecraft.this.theWorld.getWorldVec3Pool().getPoolSize();
                int var2 = 56 * var1;
                int var3 = var2 / 1024 / 1024;
                int var4 = Minecraft.this.theWorld.getWorldVec3Pool().getNextFreeSpace();
                int var5 = 56 * var4;
                int var6 = var5 / 1024 / 1024;
                return String.valueOf(var1) + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
            }
        });
        par1CrashReport.getCategory().addCrashSectionCallable("Anisotropic Filtering", new Callable(){
            private static final String __OBFID = "CL_00000637";

            public String call() {
                return Minecraft.this.gameSettings.anisotropicFiltering == 1 ? "Off (1)" : "On (" + Minecraft.this.gameSettings.anisotropicFiltering + ")";
            }
        });
        if (this.theWorld != null) {
            this.theWorld.addWorldInfoToCrashReport(par1CrashReport);
        }
        return par1CrashReport;
    }

    public static Minecraft getMinecraft() {
        return theMinecraft;
    }

    public void scheduleResourcesRefresh() {
        this.refreshTexturePacksScheduled = true;
    }

    @Override
    public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("fps", debugFPS);
        par1PlayerUsageSnooper.addData("vsync_enabled", this.gameSettings.enableVsync);
        par1PlayerUsageSnooper.addData("display_frequency", Display.getDisplayMode().getFrequency());
        par1PlayerUsageSnooper.addData("display_type", this.fullscreen ? "fullscreen" : "windowed");
        par1PlayerUsageSnooper.addData("run_time", (MinecraftServer.getSystemTimeMillis() - par1PlayerUsageSnooper.getMinecraftStartTimeMillis()) / 60 * 1000);
        par1PlayerUsageSnooper.addData("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
        int var2 = 0;
        for (ResourcePackRepository.Entry var4 : this.mcResourcePackRepository.getRepositoryEntries()) {
            par1PlayerUsageSnooper.addData("resource_pack[" + var2++ + "]", var4.getResourcePackName());
        }
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            par1PlayerUsageSnooper.addData("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }

    @Override
    public void addServerTypeToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("opengl_version", GL11.glGetString((int)7938));
        par1PlayerUsageSnooper.addData("opengl_vendor", GL11.glGetString((int)7936));
        par1PlayerUsageSnooper.addData("client_brand", ClientBrandRetriever.getClientModName());
        par1PlayerUsageSnooper.addData("launched_version", this.launchedVersion);
        ContextCapabilities var2 = GLContext.getCapabilities();
        par1PlayerUsageSnooper.addData("gl_caps[ARB_multitexture]", var2.GL_ARB_multitexture);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_multisample]", var2.GL_ARB_multisample);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_cube_map]", var2.GL_ARB_texture_cube_map);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_blend]", var2.GL_ARB_vertex_blend);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_matrix_palette]", var2.GL_ARB_matrix_palette);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_program]", var2.GL_ARB_vertex_program);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_shader]", var2.GL_ARB_vertex_shader);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_program]", var2.GL_ARB_fragment_program);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_fragment_shader]", var2.GL_ARB_fragment_shader);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_shader_objects]", var2.GL_ARB_shader_objects);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_vertex_buffer_object]", var2.GL_ARB_vertex_buffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_framebuffer_object]", var2.GL_ARB_framebuffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_pixel_buffer_object]", var2.GL_ARB_pixel_buffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_uniform_buffer_object]", var2.GL_ARB_uniform_buffer_object);
        par1PlayerUsageSnooper.addData("gl_caps[ARB_texture_non_power_of_two]", var2.GL_ARB_texture_non_power_of_two);
        par1PlayerUsageSnooper.addData("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger((int)35658));
        par1PlayerUsageSnooper.addData("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger((int)35657));
        par1PlayerUsageSnooper.addData("gl_max_texture_size", Minecraft.getGLMaximumTextureSize());
    }

    public static int getGLMaximumTextureSize() {
        int var0 = 16384;
        while (var0 > 0) {
            GL11.glTexImage2D((int)32868, (int)0, (int)6408, (int)var0, (int)var0, (int)0, (int)6408, (int)5121, (ByteBuffer)null);
            int var1 = GL11.glGetTexLevelParameteri((int)32868, (int)0, (int)4096);
            if (var1 != 0) {
                return var0;
            }
            var0 >>= 1;
        }
        return -1;
    }

    @Override
    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }

    public void setServerData(ServerData par1ServerData) {
        this.currentServerData = par1ServerData;
    }

    public ServerData func_147104_D() {
        return this.currentServerData;
    }

    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }

    public boolean isSingleplayer() {
        if (this.integratedServerIsRunning && this.theIntegratedServer != null) {
            return true;
        }
        return false;
    }

    public IntegratedServer getIntegratedServer() {
        return this.theIntegratedServer;
    }

    public static void stopIntegratedServer() {
        IntegratedServer var0;
        if (theMinecraft != null && (var0 = theMinecraft.getIntegratedServer()) != null) {
            var0.stopServer();
        }
    }

    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }

    public static long getSystemTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public boolean isFullScreen() {
        return this.fullscreen;
    }

    public Session getSession() {
        return this.session;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public TextureManager getTextureManager() {
        return this.renderEngine;
    }

    public IResourceManager getResourceManager() {
        return this.mcResourceManager;
    }

    public ResourcePackRepository getResourcePackRepository() {
        return this.mcResourcePackRepository;
    }

    public LanguageManager getLanguageManager() {
        return this.mcLanguageManager;
    }

    public TextureMap getTextureMapBlocks() {
        return this.textureMapBlocks;
    }

    public boolean isJava64bit() {
        return this.jvm64bit;
    }

    public boolean func_147113_T() {
        return this.isGamePaused;
    }

    public SoundHandler getSoundHandler() {
        return this.mcSoundHandler;
    }

    public MusicTicker.MusicType func_147109_W() {
        return this.currentScreen instanceof GuiWinGame ? MusicTicker.MusicType.CREDITS : (this.thePlayer != null ? (this.thePlayer.worldObj.provider instanceof WorldProviderHell ? MusicTicker.MusicType.NETHER : (this.thePlayer.worldObj.provider instanceof WorldProviderEnd ? (BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : (this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU);
    }

    static final class SwitchMovingObjectType {
        static final int[] field_151437_a = new int[MovingObjectPosition.MovingObjectType.values().length];
        private static final String __OBFID = "CL_00000638";

        static {
            try {
                SwitchMovingObjectType.field_151437_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchMovingObjectType.field_151437_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchMovingObjectType() {
        }
    }

}

