/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 */
package net.minecraft.client.settings;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookGuiIngame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.BlockUtils;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.CustomSky;
import net.minecraft.src.NaturalTextures;
import net.minecraft.src.RandomMobs;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.TextureUtils;
import net.minecraft.src.WrUpdaterSmooth;
import net.minecraft.src.WrUpdaterThreaded;
import net.minecraft.src.WrUpdates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GameSettings {
    private static final Logger logger = LogManager.getLogger();
    private static final Gson gson = new Gson();
    private static final ParameterizedType typeListString = new ParameterizedType(){
        private static final String __OBFID = "CL_00000651";

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{String.class};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    };
    private static final String[] GUISCALES = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
    private static final String[] PARTICLES = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
    private static final String[] AMBIENT_OCCLUSIONS = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
    public float mouseSensitivity = 0.5f;
    public boolean invertMouse;
    public int renderDistanceChunks = -1;
    public boolean viewBobbing = true;
    public boolean anaglyph;
    public boolean advancedOpengl;
    public boolean fboEnable = true;
    public int limitFramerate = 120;
    public boolean fancyGraphics = true;
    public int ambientOcclusion = 2;
    public int ofFogType = 1;
    public float ofFogStart = 0.8f;
    public int ofMipmapType = 0;
    public boolean ofLoadFar = false;
    public int ofPreloadedChunks = 0;
    public boolean ofOcclusionFancy = false;
    public boolean ofSmoothFps = false;
    public boolean ofSmoothWorld = Config.isSingleProcessor();
    public boolean ofLazyChunkLoading = Config.isSingleProcessor();
    public float ofAoLevel = 1.0f;
    public int ofAaLevel = 0;
    public int ofClouds = 0;
    public float ofCloudsHeight = 0.0f;
    public int ofTrees = 0;
    public int ofGrass = 0;
    public int ofRain = 0;
    public int ofWater = 0;
    public int ofDroppedItems = 0;
    public int ofBetterGrass = 3;
    public int ofAutoSaveTicks = 4000;
    public boolean ofLagometer = false;
    public boolean ofProfiler = false;
    public boolean ofWeather = true;
    public boolean ofSky = true;
    public boolean ofStars = true;
    public boolean ofSunMoon = true;
    public int ofChunkUpdates = 1;
    public int ofChunkLoading = 0;
    public boolean ofChunkUpdatesDynamic = false;
    public int ofTime = 0;
    public boolean ofClearWater = false;
    public boolean ofDepthFog = true;
    public boolean ofBetterSnow = false;
    public String ofFullscreenMode = "Default";
    public boolean ofSwampColors = true;
    public boolean ofRandomMobs = true;
    public boolean ofSmoothBiomes = true;
    public boolean ofCustomFonts = true;
    public boolean ofCustomColors = true;
    public boolean ofCustomSky = true;
    public boolean ofShowCapes = true;
    public int ofConnectedTextures = 2;
    public boolean ofNaturalTextures = false;
    public boolean ofFastMath = false;
    public boolean ofFastRender = true;
    public int ofTranslucentBlocks = 2;
    public int ofAnimatedWater = 0;
    public int ofAnimatedLava = 0;
    public boolean ofAnimatedFire = true;
    public boolean ofAnimatedPortal = true;
    public boolean ofAnimatedRedstone = true;
    public boolean ofAnimatedExplosion = true;
    public boolean ofAnimatedFlame = true;
    public boolean ofAnimatedSmoke = true;
    public boolean ofVoidParticles = true;
    public boolean ofWaterParticles = true;
    public boolean ofRainSplash = true;
    public boolean ofPortalParticles = true;
    public boolean ofPotionParticles = true;
    public boolean ofDrippingWaterLava = true;
    public boolean ofAnimatedTerrain = true;
    public boolean ofAnimatedItems = true;
    public boolean ofAnimatedTextures = true;
    public static final int DEFAULT = 0;
    public static final int FAST = 1;
    public static final int FANCY = 2;
    public static final int OFF = 3;
    public static final int ANIM_ON = 0;
    public static final int ANIM_GENERATED = 1;
    public static final int ANIM_OFF = 2;
    public static final int CL_DEFAULT = 0;
    public static final int CL_SMOOTH = 1;
    public static final int CL_THREADED = 2;
    public static final String DEFAULT_STR = "Default";
    public KeyBinding ofKeyBindZoom;
    private File optionsFileOF;
    public boolean clouds = true;
    public List resourcePacks = new ArrayList();
    public EntityPlayer.EnumChatVisibility chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
    public boolean chatColours = true;
    public boolean chatLinks = true;
    public boolean chatLinksPrompt = true;
    public float chatOpacity = 1.0f;
    public boolean serverTextures = true;
    public boolean snooperEnabled = true;
    public boolean fullScreen;
    public boolean enableVsync = true;
    public boolean hideServerAddress;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus = true;
    public boolean showCape = true;
    public boolean touchscreen;
    public int overrideWidth;
    public int overrideHeight;
    public boolean heldItemTooltips = true;
    public float chatScale = 1.0f;
    public float chatWidth = 1.0f;
    public float chatHeightUnfocused = 0.44366196f;
    public float chatHeightFocused = 1.0f;
    public boolean showInventoryAchievementHint = true;
    public int mipmapLevels = 4;
    public int anisotropicFiltering = 1;
    private Map mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
    public KeyBinding keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
    public KeyBinding keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
    public KeyBinding keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
    public KeyBinding keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
    public KeyBinding keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
    public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
    public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
    public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
    public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
    public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
    public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
    public KeyBinding keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
    public KeyBinding keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
    public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
    public KeyBinding keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
    public KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
    public KeyBinding keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
    public KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
    public KeyBinding[] keyBindsHotbar = new KeyBinding[]{new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")};
    public KeyBinding[] keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[]{this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint}, (Object[])this.keyBindsHotbar);
    protected Minecraft mc;
    private File optionsFile;
    public EnumDifficulty difficulty = EnumDifficulty.NORMAL;
    public boolean hideGUI;
    public int thirdPersonView;
    public boolean showDebugInfo;
    public boolean showDebugProfilerChart;
    public String lastServer = "";
    public boolean noclip;
    public boolean smoothCamera;
    public boolean debugCamEnable;
    public float noclipRate = 1.0f;
    public float debugCamRate = 1.0f;
    public float fovSetting;
    public float gammaSetting;
    public float saturation;
    public int guiScale;
    public int particleSetting;
    public String language = "en_US";
    public boolean forceUnicodeFont = false;
    private static final String __OBFID = "CL_00000650";

    public GameSettings(Minecraft par1Minecraft, File par2File) {
        this.mc = par1Minecraft;
        this.optionsFile = new File(par2File, "options.txt");
        this.optionsFileOF = new File(par2File, "optionsof.txt");
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29, "key.categories.misc");
        this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, (Object)this.ofKeyBindZoom);
        Options.RENDER_DISTANCE.setValueMax(32.0f);
        this.renderDistanceChunks = par1Minecraft.isJava64bit() ? 12 : 8;
        this.loadOptions();
        Config.initGameSettings(this);
    }

    public GameSettings() {
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29, "key.categories.misc");
        this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, (Object)this.ofKeyBindZoom);
    }

    public static String getKeyDisplayString(int par0) {
        return par0 < 0 ? I18n.format("key.mouseButton", par0 + 101) : Keyboard.getKeyName((int)par0);
    }

    public static boolean isKeyDown(KeyBinding par0KeyBinding) {
        return par0KeyBinding.getKeyCode() == 0 ? false : (par0KeyBinding.getKeyCode() < 0 ? Mouse.isButtonDown((int)(par0KeyBinding.getKeyCode() + 100)) : Keyboard.isKeyDown((int)par0KeyBinding.getKeyCode()));
    }

    public void setKeyCodeSave(KeyBinding p_151440_1_, int p_151440_2_) {
        p_151440_1_.setKeyCode(p_151440_2_);
        this.saveOptions();
    }

    public void setOptionFloatValue(Options par1EnumOptions, float par2) {
        int var3;
        if (par1EnumOptions == Options.CLOUD_HEIGHT) {
            this.ofCloudsHeight = par2;
        }
        if (par1EnumOptions == Options.AO_LEVEL) {
            this.ofAoLevel = par2;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.SENSITIVITY) {
            this.mouseSensitivity = par2;
        }
        if (par1EnumOptions == Options.FOV) {
            this.fovSetting = par2;
        }
        if (par1EnumOptions == Options.GAMMA) {
            this.gammaSetting = par2;
        }
        if (par1EnumOptions == Options.FRAMERATE_LIMIT) {
            this.limitFramerate = (int)par2;
            this.enableVsync = false;
            if (this.limitFramerate <= 0) {
                this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                this.enableVsync = true;
            }
            this.updateVSync();
        }
        if (par1EnumOptions == Options.CHAT_OPACITY) {
            this.chatOpacity = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED) {
            this.chatHeightFocused = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED) {
            this.chatHeightUnfocused = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_WIDTH) {
            this.chatWidth = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_SCALE) {
            this.chatScale = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.ANISOTROPIC_FILTERING) {
            var3 = this.anisotropicFiltering;
            this.anisotropicFiltering = (int)par2;
            if ((float)var3 != par2) {
                this.mc.getTextureMapBlocks().func_147632_b(this.anisotropicFiltering);
                this.mc.scheduleResourcesRefresh();
            }
        }
        if (par1EnumOptions == Options.MIPMAP_LEVELS) {
            var3 = this.mipmapLevels;
            this.mipmapLevels = (int)par2;
            if ((float)var3 != par2) {
                this.mc.getTextureMapBlocks().func_147633_a(this.mipmapLevels);
                this.mc.scheduleResourcesRefresh();
            }
        }
        if (par1EnumOptions == Options.RENDER_DISTANCE) {
            this.renderDistanceChunks = (int)par2;
        }
    }

    public void setOptionValue(Options par1EnumOptions, int par2) {
        if (par1EnumOptions == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    this.ofFogType = 2;
                    if (Config.isFancyFogAvailable()) break;
                    this.ofFogType = 3;
                    break;
                }
                case 2: {
                    this.ofFogType = 3;
                    break;
                }
                case 3: {
                    this.ofFogType = 1;
                    break;
                }
                default: {
                    this.ofFogType = 1;
                }
            }
        }
        if (par1EnumOptions == Options.FOG_START) {
            this.ofFogStart += 0.2f;
            if (this.ofFogStart > 0.81f) {
                this.ofFogStart = 0.2f;
            }
        }
        if (par1EnumOptions == Options.MIPMAP_TYPE) {
            ++this.ofMipmapType;
            if (this.ofMipmapType > 3) {
                this.ofMipmapType = 0;
            }
            TextureUtils.refreshBlockTextures();
        }
        if (par1EnumOptions == Options.LOAD_FAR) {
            this.ofLoadFar = !this.ofLoadFar;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.PRELOADED_CHUNKS) {
            this.ofPreloadedChunks += 2;
            if (this.ofPreloadedChunks > 8) {
                this.ofPreloadedChunks = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.SMOOTH_FPS) {
            boolean bl = this.ofSmoothFps = !this.ofSmoothFps;
        }
        if (par1EnumOptions == Options.SMOOTH_WORLD) {
            this.ofSmoothWorld = !this.ofSmoothWorld;
            Config.updateAvailableProcessors();
            if (!Config.isSingleProcessor()) {
                this.ofSmoothWorld = false;
            }
            Config.updateThreadPriorities();
        }
        if (par1EnumOptions == Options.CLOUDS) {
            ++this.ofClouds;
            if (this.ofClouds > 3) {
                this.ofClouds = 0;
            }
        }
        if (par1EnumOptions == Options.TREES) {
            ++this.ofTrees;
            if (this.ofTrees > 2) {
                this.ofTrees = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.GRASS) {
            ++this.ofGrass;
            if (this.ofGrass > 2) {
                this.ofGrass = 0;
            }
            RenderBlocks.fancyGrass = Config.isGrassFancy();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.DROPPED_ITEMS) {
            ++this.ofDroppedItems;
            if (this.ofDroppedItems > 2) {
                this.ofDroppedItems = 0;
            }
        }
        if (par1EnumOptions == Options.RAIN) {
            ++this.ofRain;
            if (this.ofRain > 3) {
                this.ofRain = 0;
            }
        }
        if (par1EnumOptions == Options.WATER) {
            ++this.ofWater;
            if (this.ofWater > 2) {
                this.ofWater = 0;
            }
        }
        if (par1EnumOptions == Options.ANIMATED_WATER) {
            ++this.ofAnimatedWater;
            if (this.ofAnimatedWater > 2) {
                this.ofAnimatedWater = 0;
            }
        }
        if (par1EnumOptions == Options.ANIMATED_LAVA) {
            ++this.ofAnimatedLava;
            if (this.ofAnimatedLava > 2) {
                this.ofAnimatedLava = 0;
            }
        }
        if (par1EnumOptions == Options.ANIMATED_FIRE) {
            boolean bl = this.ofAnimatedFire = !this.ofAnimatedFire;
        }
        if (par1EnumOptions == Options.ANIMATED_PORTAL) {
            boolean bl = this.ofAnimatedPortal = !this.ofAnimatedPortal;
        }
        if (par1EnumOptions == Options.ANIMATED_REDSTONE) {
            boolean bl = this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
        }
        if (par1EnumOptions == Options.ANIMATED_EXPLOSION) {
            boolean bl = this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
        }
        if (par1EnumOptions == Options.ANIMATED_FLAME) {
            boolean bl = this.ofAnimatedFlame = !this.ofAnimatedFlame;
        }
        if (par1EnumOptions == Options.ANIMATED_SMOKE) {
            boolean bl = this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
        }
        if (par1EnumOptions == Options.VOID_PARTICLES) {
            boolean bl = this.ofVoidParticles = !this.ofVoidParticles;
        }
        if (par1EnumOptions == Options.WATER_PARTICLES) {
            boolean bl = this.ofWaterParticles = !this.ofWaterParticles;
        }
        if (par1EnumOptions == Options.PORTAL_PARTICLES) {
            boolean bl = this.ofPortalParticles = !this.ofPortalParticles;
        }
        if (par1EnumOptions == Options.POTION_PARTICLES) {
            boolean bl = this.ofPotionParticles = !this.ofPotionParticles;
        }
        if (par1EnumOptions == Options.DRIPPING_WATER_LAVA) {
            boolean bl = this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
        }
        if (par1EnumOptions == Options.ANIMATED_TERRAIN) {
            boolean bl = this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
        }
        if (par1EnumOptions == Options.ANIMATED_TEXTURES) {
            boolean bl = this.ofAnimatedTextures = !this.ofAnimatedTextures;
        }
        if (par1EnumOptions == Options.ANIMATED_ITEMS) {
            boolean bl = this.ofAnimatedItems = !this.ofAnimatedItems;
        }
        if (par1EnumOptions == Options.RAIN_SPLASH) {
            boolean bl = this.ofRainSplash = !this.ofRainSplash;
        }
        if (par1EnumOptions == Options.LAGOMETER) {
            boolean bl = this.ofLagometer = !this.ofLagometer;
        }
        if (par1EnumOptions == Options.AUTOSAVE_TICKS) {
            this.ofAutoSaveTicks *= 10;
            if (this.ofAutoSaveTicks > 40000) {
                this.ofAutoSaveTicks = 40;
            }
        }
        if (par1EnumOptions == Options.BETTER_GRASS) {
            ++this.ofBetterGrass;
            if (this.ofBetterGrass > 3) {
                this.ofBetterGrass = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CONNECTED_TEXTURES) {
            ++this.ofConnectedTextures;
            if (this.ofConnectedTextures > 3) {
                this.ofConnectedTextures = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.WEATHER) {
            boolean bl = this.ofWeather = !this.ofWeather;
        }
        if (par1EnumOptions == Options.SKY) {
            boolean bl = this.ofSky = !this.ofSky;
        }
        if (par1EnumOptions == Options.STARS) {
            boolean bl = this.ofStars = !this.ofStars;
        }
        if (par1EnumOptions == Options.SUN_MOON) {
            boolean bl = this.ofSunMoon = !this.ofSunMoon;
        }
        if (par1EnumOptions == Options.CHUNK_UPDATES) {
            ++this.ofChunkUpdates;
            if (this.ofChunkUpdates > 5) {
                this.ofChunkUpdates = 1;
            }
        }
        if (par1EnumOptions == Options.CHUNK_LOADING) {
            ++this.ofChunkLoading;
            if (this.ofChunkLoading > 2) {
                this.ofChunkLoading = 0;
            }
            this.updateChunkLoading();
        }
        if (par1EnumOptions == Options.CHUNK_UPDATES_DYNAMIC) {
            boolean bl = this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
        }
        if (par1EnumOptions == Options.TIME) {
            ++this.ofTime;
            if (this.ofTime > 3) {
                this.ofTime = 0;
            }
        }
        if (par1EnumOptions == Options.CLEAR_WATER) {
            this.ofClearWater = !this.ofClearWater;
            this.updateWaterOpacity();
        }
        if (par1EnumOptions == Options.DEPTH_FOG) {
            boolean bl = this.ofDepthFog = !this.ofDepthFog;
        }
        if (par1EnumOptions == Options.AA_LEVEL) {
            this.ofAaLevel = 0;
        }
        if (par1EnumOptions == Options.PROFILER) {
            boolean bl = this.ofProfiler = !this.ofProfiler;
        }
        if (par1EnumOptions == Options.BETTER_SNOW) {
            this.ofBetterSnow = !this.ofBetterSnow;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.SWAMP_COLORS) {
            this.ofSwampColors = !this.ofSwampColors;
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.RANDOM_MOBS) {
            this.ofRandomMobs = !this.ofRandomMobs;
            RandomMobs.resetTextures();
        }
        if (par1EnumOptions == Options.SMOOTH_BIOMES) {
            this.ofSmoothBiomes = !this.ofSmoothBiomes;
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CUSTOM_FONTS) {
            this.ofCustomFonts = !this.ofCustomFonts;
            this.mc.fontRenderer.onResourceManagerReload(Config.getResourceManager());
            this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
        }
        if (par1EnumOptions == Options.CUSTOM_COLORS) {
            this.ofCustomColors = !this.ofCustomColors;
            CustomColorizer.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CUSTOM_SKY) {
            this.ofCustomSky = !this.ofCustomSky;
            CustomSky.update();
        }
        if (par1EnumOptions == Options.SHOW_CAPES) {
            this.ofShowCapes = !this.ofShowCapes;
            this.mc.renderGlobal.updateCapes();
        }
        if (par1EnumOptions == Options.NATURAL_TEXTURES) {
            this.ofNaturalTextures = !this.ofNaturalTextures;
            NaturalTextures.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.FAST_MATH) {
            MathHelper.fastMath = this.ofFastMath = !this.ofFastMath;
        }
        if (par1EnumOptions == Options.FAST_RENDER) {
            this.ofFastRender = !this.ofFastRender;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS) {
            this.ofTranslucentBlocks = this.ofTranslucentBlocks == 1 ? 2 : 1;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.LAZY_CHUNK_LOADING) {
            this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
            Config.updateAvailableProcessors();
            if (!Config.isSingleProcessor()) {
                this.ofLazyChunkLoading = false;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.FULLSCREEN_MODE) {
            int index;
            List<String> modeList = Arrays.asList(Config.getFullscreenModes());
            this.ofFullscreenMode = this.ofFullscreenMode.equals("Default") ? modeList.get(0) : ((index = modeList.indexOf(this.ofFullscreenMode)) < 0 ? "Default" : (++index >= modeList.size() ? "Default" : modeList.get(index)));
        }
        if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS) {
            boolean bl = this.heldItemTooltips = !this.heldItemTooltips;
        }
        if (par1EnumOptions == Options.INVERT_MOUSE) {
            boolean bl = this.invertMouse = !this.invertMouse;
        }
        if (par1EnumOptions == Options.GUI_SCALE) {
            this.guiScale = this.guiScale + par2 & 3;
        }
        if (par1EnumOptions == Options.PARTICLES) {
            this.particleSetting = (this.particleSetting + par2) % 3;
        }
        if (par1EnumOptions == Options.VIEW_BOBBING) {
            boolean bl = this.viewBobbing = !this.viewBobbing;
        }
        if (par1EnumOptions == Options.RENDER_CLOUDS) {
            boolean bl = this.clouds = !this.clouds;
        }
        if (par1EnumOptions == Options.FORCE_UNICODE_FONT) {
            this.forceUnicodeFont = !this.forceUnicodeFont;
            this.mc.fontRenderer.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont);
        }
        if (par1EnumOptions == Options.ADVANCED_OPENGL) {
            if (!Config.isOcclusionAvailable()) {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            } else if (!this.advancedOpengl) {
                this.advancedOpengl = true;
                this.ofOcclusionFancy = false;
            } else if (!this.ofOcclusionFancy) {
                this.ofOcclusionFancy = true;
            } else {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            }
            this.mc.renderGlobal.setAllRenderersVisible();
        }
        if (par1EnumOptions == Options.FBO_ENABLE) {
            boolean bl = this.fboEnable = !this.fboEnable;
        }
        if (par1EnumOptions == Options.ANAGLYPH) {
            this.anaglyph = !this.anaglyph;
            this.mc.refreshResources();
        }
        if (par1EnumOptions == Options.DIFFICULTY) {
            this.difficulty = EnumDifficulty.getDifficultyEnum(this.difficulty.getDifficultyId() + par2 & 3);
        }
        if (par1EnumOptions == Options.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = (this.ambientOcclusion + par2) % 3;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CHAT_VISIBILITY) {
            this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + par2) % 3);
        }
        if (par1EnumOptions == Options.CHAT_COLOR) {
            boolean bl = this.chatColours = !this.chatColours;
        }
        if (par1EnumOptions == Options.CHAT_LINKS) {
            boolean bl = this.chatLinks = !this.chatLinks;
        }
        if (par1EnumOptions == Options.CHAT_LINKS_PROMPT) {
            boolean bl = this.chatLinksPrompt = !this.chatLinksPrompt;
        }
        if (par1EnumOptions == Options.USE_SERVER_TEXTURES) {
            boolean bl = this.serverTextures = !this.serverTextures;
        }
        if (par1EnumOptions == Options.SNOOPER_ENABLED) {
            boolean bl = this.snooperEnabled = !this.snooperEnabled;
        }
        if (par1EnumOptions == Options.SHOW_CAPE) {
            boolean bl = this.showCape = !this.showCape;
        }
        if (par1EnumOptions == Options.TOUCHSCREEN) {
            boolean bl = this.touchscreen = !this.touchscreen;
        }
        if (par1EnumOptions == Options.USE_FULLSCREEN) {
            boolean bl = this.fullScreen = !this.fullScreen;
            if (this.mc.isFullScreen() != this.fullScreen) {
                this.mc.toggleFullscreen();
            }
        }
        if (par1EnumOptions == Options.ENABLE_VSYNC) {
            this.enableVsync = !this.enableVsync;
            Display.setVSyncEnabled((boolean)this.enableVsync);
        }
        this.saveOptions();
    }

    public float getOptionFloatValue(Options par1EnumOptions) {
        return par1EnumOptions == Options.CLOUD_HEIGHT ? this.ofCloudsHeight : (par1EnumOptions == Options.AO_LEVEL ? this.ofAoLevel : (par1EnumOptions == Options.FRAMERATE_LIMIT ? ((float)this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync ? 0.0f : (float)this.limitFramerate) : (par1EnumOptions == Options.FOV ? this.fovSetting : (par1EnumOptions == Options.GAMMA ? this.gammaSetting : (par1EnumOptions == Options.SATURATION ? this.saturation : (par1EnumOptions == Options.SENSITIVITY ? this.mouseSensitivity : (par1EnumOptions == Options.CHAT_OPACITY ? this.chatOpacity : (par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED ? this.chatHeightFocused : (par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED ? this.chatHeightUnfocused : (par1EnumOptions == Options.CHAT_SCALE ? this.chatScale : (par1EnumOptions == Options.CHAT_WIDTH ? this.chatWidth : (par1EnumOptions == Options.FRAMERATE_LIMIT ? (float)this.limitFramerate : (par1EnumOptions == Options.ANISOTROPIC_FILTERING ? (float)this.anisotropicFiltering : (par1EnumOptions == Options.MIPMAP_LEVELS ? (float)this.mipmapLevels : (par1EnumOptions == Options.RENDER_DISTANCE ? (float)this.renderDistanceChunks : 0.0f)))))))))))))));
    }

    public boolean getOptionOrdinalValue(Options par1EnumOptions) {
        switch (SwitchOptions.optionIds[par1EnumOptions.ordinal()]) {
            case 1: {
                return this.invertMouse;
            }
            case 2: {
                return this.viewBobbing;
            }
            case 3: {
                return this.anaglyph;
            }
            case 4: {
                return this.advancedOpengl;
            }
            case 5: {
                return this.fboEnable;
            }
            case 6: {
                return this.clouds;
            }
            case 7: {
                return this.chatColours;
            }
            case 8: {
                return this.chatLinks;
            }
            case 9: {
                return this.chatLinksPrompt;
            }
            case 10: {
                return this.serverTextures;
            }
            case 11: {
                return this.snooperEnabled;
            }
            case 12: {
                return this.fullScreen;
            }
            case 13: {
                return this.enableVsync;
            }
            case 14: {
                return this.showCape;
            }
            case 15: {
                return this.touchscreen;
            }
            case 16: {
                return this.forceUnicodeFont;
            }
        }
        return false;
    }

    private static String getTranslation(String[] par0ArrayOfStr, int par1) {
        if (par1 < 0 || par1 >= par0ArrayOfStr.length) {
            par1 = 0;
        }
        return I18n.format(par0ArrayOfStr[par1], new Object[0]);
    }

    public String getKeyBinding(Options par1EnumOptions) {
        String var2 = String.valueOf(I18n.format(par1EnumOptions.getEnumString(), new Object[0])) + ": ";
        if (par1EnumOptions == Options.RENDER_DISTANCE) {
            int var33 = (int)this.getOptionFloatValue(par1EnumOptions);
            String var41 = "Tiny";
            int baseDist = 2;
            if (var33 >= 4) {
                var41 = "Short";
                baseDist = 4;
            }
            if (var33 >= 8) {
                var41 = "Normal";
                baseDist = 8;
            }
            if (var33 >= 16) {
                var41 = "Far";
                baseDist = 16;
            }
            if (var33 >= 32) {
                var41 = "Extreme";
                baseDist = 32;
            }
            int diff = this.renderDistanceChunks - baseDist;
            String descr = var41;
            if (diff > 0) {
                descr = String.valueOf(var41) + "+";
            }
            return String.valueOf(var2) + var33 + " " + descr;
        }
        if (par1EnumOptions == Options.ADVANCED_OPENGL) {
            return !this.advancedOpengl ? String.valueOf(var2) + "OFF" : (this.ofOcclusionFancy ? String.valueOf(var2) + "Fancy" : String.valueOf(var2) + "Fast");
        }
        if (par1EnumOptions == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
                case 3: {
                    return String.valueOf(var2) + "OFF";
                }
            }
            return String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.FOG_START) {
            return String.valueOf(var2) + this.ofFogStart;
        }
        if (par1EnumOptions == Options.MIPMAP_TYPE) {
            switch (this.ofMipmapType) {
                case 0: {
                    return String.valueOf(var2) + "Nearest";
                }
                case 1: {
                    return String.valueOf(var2) + "Linear";
                }
                case 2: {
                    return String.valueOf(var2) + "Bilinear";
                }
                case 3: {
                    return String.valueOf(var2) + "Trilinear";
                }
            }
            return String.valueOf(var2) + "Nearest";
        }
        if (par1EnumOptions == Options.LOAD_FAR) {
            return this.ofLoadFar ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.PRELOADED_CHUNKS) {
            return this.ofPreloadedChunks == 0 ? String.valueOf(var2) + "OFF" : String.valueOf(var2) + this.ofPreloadedChunks;
        }
        if (par1EnumOptions == Options.SMOOTH_FPS) {
            return this.ofSmoothFps ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.SMOOTH_WORLD) {
            return this.ofSmoothWorld ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.CLOUDS) {
            switch (this.ofClouds) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
                case 3: {
                    return String.valueOf(var2) + "OFF";
                }
            }
            return String.valueOf(var2) + "Default";
        }
        if (par1EnumOptions == Options.TREES) {
            switch (this.ofTrees) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
            }
            return String.valueOf(var2) + "Default";
        }
        if (par1EnumOptions == Options.GRASS) {
            switch (this.ofGrass) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
            }
            return String.valueOf(var2) + "Default";
        }
        if (par1EnumOptions == Options.DROPPED_ITEMS) {
            switch (this.ofDroppedItems) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
            }
            return String.valueOf(var2) + "Default";
        }
        if (par1EnumOptions == Options.RAIN) {
            switch (this.ofRain) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
                case 3: {
                    return String.valueOf(var2) + "OFF";
                }
            }
            return String.valueOf(var2) + "Default";
        }
        if (par1EnumOptions == Options.WATER) {
            switch (this.ofWater) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
                case 3: {
                    return String.valueOf(var2) + "OFF";
                }
            }
            return String.valueOf(var2) + "Default";
        }
        if (par1EnumOptions == Options.ANIMATED_WATER) {
            switch (this.ofAnimatedWater) {
                case 1: {
                    return String.valueOf(var2) + "Dynamic";
                }
                case 2: {
                    return String.valueOf(var2) + "OFF";
                }
            }
            return String.valueOf(var2) + "ON";
        }
        if (par1EnumOptions == Options.ANIMATED_LAVA) {
            switch (this.ofAnimatedLava) {
                case 1: {
                    return String.valueOf(var2) + "Dynamic";
                }
                case 2: {
                    return String.valueOf(var2) + "OFF";
                }
            }
            return String.valueOf(var2) + "ON";
        }
        if (par1EnumOptions == Options.ANIMATED_FIRE) {
            return this.ofAnimatedFire ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_PORTAL) {
            return this.ofAnimatedPortal ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_REDSTONE) {
            return this.ofAnimatedRedstone ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_EXPLOSION) {
            return this.ofAnimatedExplosion ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_FLAME) {
            return this.ofAnimatedFlame ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_SMOKE) {
            return this.ofAnimatedSmoke ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.VOID_PARTICLES) {
            return this.ofVoidParticles ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.WATER_PARTICLES) {
            return this.ofWaterParticles ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.PORTAL_PARTICLES) {
            return this.ofPortalParticles ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.POTION_PARTICLES) {
            return this.ofPotionParticles ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.DRIPPING_WATER_LAVA) {
            return this.ofDrippingWaterLava ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_TERRAIN) {
            return this.ofAnimatedTerrain ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_TEXTURES) {
            return this.ofAnimatedTextures ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.ANIMATED_ITEMS) {
            return this.ofAnimatedItems ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.RAIN_SPLASH) {
            return this.ofRainSplash ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.LAGOMETER) {
            return this.ofLagometer ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.AUTOSAVE_TICKS) {
            return this.ofAutoSaveTicks <= 40 ? String.valueOf(var2) + "Default (2s)" : (this.ofAutoSaveTicks <= 400 ? String.valueOf(var2) + "20s" : (this.ofAutoSaveTicks <= 4000 ? String.valueOf(var2) + "3min" : String.valueOf(var2) + "30min"));
        }
        if (par1EnumOptions == Options.BETTER_GRASS) {
            switch (this.ofBetterGrass) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
            }
            return String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.CONNECTED_TEXTURES) {
            switch (this.ofConnectedTextures) {
                case 1: {
                    return String.valueOf(var2) + "Fast";
                }
                case 2: {
                    return String.valueOf(var2) + "Fancy";
                }
            }
            return String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.WEATHER) {
            return this.ofWeather ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.SKY) {
            return this.ofSky ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.STARS) {
            return this.ofStars ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.SUN_MOON) {
            return this.ofSunMoon ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.CHUNK_UPDATES) {
            return String.valueOf(var2) + this.ofChunkUpdates;
        }
        if (par1EnumOptions == Options.CHUNK_LOADING) {
            return this.ofChunkLoading == 1 ? String.valueOf(var2) + "Smooth" : (this.ofChunkLoading == 2 ? String.valueOf(var2) + "Multi-Core" : String.valueOf(var2) + "Default");
        }
        if (par1EnumOptions == Options.CHUNK_UPDATES_DYNAMIC) {
            return this.ofChunkUpdatesDynamic ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.TIME) {
            return this.ofTime == 1 ? String.valueOf(var2) + "Day Only" : (this.ofTime == 3 ? String.valueOf(var2) + "Night Only" : String.valueOf(var2) + "Default");
        }
        if (par1EnumOptions == Options.CLEAR_WATER) {
            return this.ofClearWater ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.DEPTH_FOG) {
            return this.ofDepthFog ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.AA_LEVEL) {
            return this.ofAaLevel == 0 ? String.valueOf(var2) + "OFF" : String.valueOf(var2) + this.ofAaLevel;
        }
        if (par1EnumOptions == Options.PROFILER) {
            return this.ofProfiler ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.BETTER_SNOW) {
            return this.ofBetterSnow ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.SWAMP_COLORS) {
            return this.ofSwampColors ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.RANDOM_MOBS) {
            return this.ofRandomMobs ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.SMOOTH_BIOMES) {
            return this.ofSmoothBiomes ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.CUSTOM_FONTS) {
            return this.ofCustomFonts ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.CUSTOM_COLORS) {
            return this.ofCustomColors ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.CUSTOM_SKY) {
            return this.ofCustomSky ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.SHOW_CAPES) {
            return this.ofShowCapes ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.NATURAL_TEXTURES) {
            return this.ofNaturalTextures ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.FAST_MATH) {
            return this.ofFastMath ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.FAST_RENDER) {
            return this.ofFastRender ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS) {
            return this.ofTranslucentBlocks == 1 ? String.valueOf(var2) + "Fast" : String.valueOf(var2) + "Fancy";
        }
        if (par1EnumOptions == Options.LAZY_CHUNK_LOADING) {
            return this.ofLazyChunkLoading ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.FULLSCREEN_MODE) {
            return String.valueOf(var2) + this.ofFullscreenMode;
        }
        if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS) {
            return this.heldItemTooltips ? String.valueOf(var2) + "ON" : String.valueOf(var2) + "OFF";
        }
        if (par1EnumOptions == Options.FRAMERATE_LIMIT) {
            float var32 = this.getOptionFloatValue(par1EnumOptions);
            return var32 == 0.0f ? String.valueOf(var2) + "VSync" : (var32 == par1EnumOptions.valueMax ? String.valueOf(var2) + I18n.format("options.framerateLimit.max", new Object[0]) : String.valueOf(var2) + (int)var32 + " fps");
        }
        if (par1EnumOptions.getEnumFloat()) {
            float var32 = this.getOptionFloatValue(par1EnumOptions);
            float var4 = par1EnumOptions.normalizeValue(var32);
            return par1EnumOptions == Options.SENSITIVITY ? (var4 == 0.0f ? String.valueOf(var2) + I18n.format("options.sensitivity.min", new Object[0]) : (var4 == 1.0f ? String.valueOf(var2) + I18n.format("options.sensitivity.max", new Object[0]) : String.valueOf(var2) + (int)(var4 * 200.0f) + "%")) : (par1EnumOptions == Options.FOV ? (var4 == 0.0f ? String.valueOf(var2) + I18n.format("options.fov.min", new Object[0]) : (var4 == 1.0f ? String.valueOf(var2) + I18n.format("options.fov.max", new Object[0]) : String.valueOf(var2) + (int)(70.0f + var4 * 40.0f))) : (par1EnumOptions == Options.FRAMERATE_LIMIT ? (var32 == par1EnumOptions.valueMax ? String.valueOf(var2) + I18n.format("options.framerateLimit.max", new Object[0]) : String.valueOf(var2) + (int)var32 + " fps") : (par1EnumOptions == Options.GAMMA ? (var4 == 0.0f ? String.valueOf(var2) + I18n.format("options.gamma.min", new Object[0]) : (var4 == 1.0f ? String.valueOf(var2) + I18n.format("options.gamma.max", new Object[0]) : String.valueOf(var2) + "+" + (int)(var4 * 100.0f) + "%")) : (par1EnumOptions == Options.SATURATION ? String.valueOf(var2) + (int)(var4 * 400.0f) + "%" : (par1EnumOptions == Options.CHAT_OPACITY ? String.valueOf(var2) + (int)(var4 * 90.0f + 10.0f) + "%" : (par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED ? String.valueOf(var2) + GuiNewChat.func_146243_b(var4) + "px" : (par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED ? String.valueOf(var2) + GuiNewChat.func_146243_b(var4) + "px" : (par1EnumOptions == Options.CHAT_WIDTH ? String.valueOf(var2) + GuiNewChat.func_146233_a(var4) + "px" : (par1EnumOptions == Options.RENDER_DISTANCE ? String.valueOf(var2) + (int)var32 + " chunks" : (par1EnumOptions == Options.ANISOTROPIC_FILTERING ? (var32 == 1.0f ? String.valueOf(var2) + I18n.format("options.off", new Object[0]) : String.valueOf(var2) + (int)var32) : (par1EnumOptions == Options.MIPMAP_LEVELS ? (var32 == 0.0f ? String.valueOf(var2) + I18n.format("options.off", new Object[0]) : String.valueOf(var2) + (int)var32) : (var4 == 0.0f ? String.valueOf(var2) + I18n.format("options.off", new Object[0]) : String.valueOf(var2) + (int)(var4 * 100.0f) + "%"))))))))))));
        }
        if (par1EnumOptions.getEnumBoolean()) {
            boolean var31 = this.getOptionOrdinalValue(par1EnumOptions);
            return var31 ? String.valueOf(var2) + I18n.format("options.on", new Object[0]) : String.valueOf(var2) + I18n.format("options.off", new Object[0]);
        }
        if (par1EnumOptions == Options.DIFFICULTY) {
            return String.valueOf(var2) + I18n.format(this.difficulty.getDifficultyResourceKey(), new Object[0]);
        }
        if (par1EnumOptions == Options.GUI_SCALE) {
            return String.valueOf(var2) + GameSettings.getTranslation(GUISCALES, this.guiScale);
        }
        if (par1EnumOptions == Options.CHAT_VISIBILITY) {
            return String.valueOf(var2) + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]);
        }
        if (par1EnumOptions == Options.PARTICLES) {
            return String.valueOf(var2) + GameSettings.getTranslation(PARTICLES, this.particleSetting);
        }
        if (par1EnumOptions == Options.AMBIENT_OCCLUSION) {
            return String.valueOf(var2) + GameSettings.getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion);
        }
        if (par1EnumOptions == Options.GRAPHICS) {
            if (this.fancyGraphics) {
                return String.valueOf(var2) + I18n.format("options.graphics.fancy", new Object[0]);
            }
            String var3 = "options.graphics.fast";
            return String.valueOf(var2) + I18n.format("options.graphics.fast", new Object[0]);
        }
        return var2;
    }

    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            BufferedReader var9 = new BufferedReader(new FileReader(this.optionsFile));
            String var2 = "";
            this.mapSoundLevels.clear();
            while ((var2 = var9.readLine()) != null) {
                try {
                    String[] var8 = var2.split(":");
                    if (var8[0].equals("mouseSensitivity")) {
                        this.mouseSensitivity = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("fov")) {
                        this.fovSetting = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("gamma")) {
                        this.gammaSetting = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("saturation")) {
                        this.saturation = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("invertYMouse")) {
                        this.invertMouse = var8[1].equals("true");
                    }
                    if (var8[0].equals("renderDistance")) {
                        this.renderDistanceChunks = Integer.parseInt(var8[1]);
                    }
                    if (var8[0].equals("guiScale")) {
                        this.guiScale = Integer.parseInt(var8[1]);
                    }
                    if (var8[0].equals("particles")) {
                        this.particleSetting = Integer.parseInt(var8[1]);
                    }
                    if (var8[0].equals("bobView")) {
                        this.viewBobbing = var8[1].equals("true");
                    }
                    if (var8[0].equals("anaglyph3d")) {
                        this.anaglyph = var8[1].equals("true");
                    }
                    if (var8[0].equals("advancedOpengl")) {
                        this.advancedOpengl = var8[1].equals("true");
                    }
                    if (var8[0].equals("maxFps")) {
                        this.limitFramerate = Integer.parseInt(var8[1]);
                        this.enableVsync = false;
                        if (this.limitFramerate <= 0) {
                            this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                            this.enableVsync = true;
                        }
                        this.updateVSync();
                    }
                    if (var8[0].equals("fboEnable")) {
                        this.fboEnable = var8[1].equals("true");
                    }
                    if (var8[0].equals("difficulty")) {
                        this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(var8[1]));
                    }
                    if (var8[0].equals("fancyGraphics")) {
                        this.fancyGraphics = var8[1].equals("true");
                    }
                    if (var8[0].equals("ao")) {
                        this.ambientOcclusion = var8[1].equals("true") ? 2 : (var8[1].equals("false") ? 0 : Integer.parseInt(var8[1]));
                    }
                    if (var8[0].equals("clouds")) {
                        this.clouds = var8[1].equals("true");
                    }
                    if (var8[0].equals("resourcePacks")) {
                        this.resourcePacks = (List)gson.fromJson(var2.substring(var2.indexOf(58) + 1), (Type)typeListString);
                        if (this.resourcePacks == null) {
                            this.resourcePacks = new ArrayList();
                        }
                    }
                    if (var8[0].equals("lastServer") && var8.length >= 2) {
                        this.lastServer = var2.substring(var2.indexOf(58) + 1);
                    }
                    if (var8[0].equals("lang") && var8.length >= 2) {
                        this.language = var8[1];
                    }
                    if (var8[0].equals("chatVisibility")) {
                        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(var8[1]));
                    }
                    if (var8[0].equals("chatColors")) {
                        this.chatColours = var8[1].equals("true");
                    }
                    if (var8[0].equals("chatLinks")) {
                        this.chatLinks = var8[1].equals("true");
                    }
                    if (var8[0].equals("chatLinksPrompt")) {
                        this.chatLinksPrompt = var8[1].equals("true");
                    }
                    if (var8[0].equals("chatOpacity")) {
                        this.chatOpacity = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("serverTextures")) {
                        this.serverTextures = var8[1].equals("true");
                    }
                    if (var8[0].equals("snooperEnabled")) {
                        this.snooperEnabled = var8[1].equals("true");
                    }
                    if (var8[0].equals("fullscreen")) {
                        this.fullScreen = var8[1].equals("true");
                    }
                    if (var8[0].equals("enableVsync")) {
                        this.enableVsync = var8[1].equals("true");
                        this.updateVSync();
                    }
                    if (var8[0].equals("hideServerAddress")) {
                        this.hideServerAddress = var8[1].equals("true");
                    }
                    if (var8[0].equals("advancedItemTooltips")) {
                        this.advancedItemTooltips = var8[1].equals("true");
                    }
                    if (var8[0].equals("pauseOnLostFocus")) {
                        this.pauseOnLostFocus = var8[1].equals("true");
                    }
                    if (var8[0].equals("showCape")) {
                        this.showCape = var8[1].equals("true");
                    }
                    if (var8[0].equals("touchscreen")) {
                        this.touchscreen = var8[1].equals("true");
                    }
                    if (var8[0].equals("overrideHeight")) {
                        this.overrideHeight = Integer.parseInt(var8[1]);
                    }
                    if (var8[0].equals("overrideWidth")) {
                        this.overrideWidth = Integer.parseInt(var8[1]);
                    }
                    if (var8[0].equals("heldItemTooltips")) {
                        this.heldItemTooltips = var8[1].equals("true");
                    }
                    if (var8[0].equals("chatHeightFocused")) {
                        this.chatHeightFocused = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("chatHeightUnfocused")) {
                        this.chatHeightUnfocused = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("chatScale")) {
                        this.chatScale = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("chatWidth")) {
                        this.chatWidth = this.parseFloat(var8[1]);
                    }
                    if (var8[0].equals("showInventoryAchievementHint")) {
                        this.showInventoryAchievementHint = var8[1].equals("true");
                    }
                    if (var8[0].equals("mipmapLevels")) {
                        this.mipmapLevels = Integer.parseInt(var8[1]);
                    }
                    if (var8[0].equals("anisotropicFiltering")) {
                        this.anisotropicFiltering = Integer.parseInt(var8[1]);
                    }
                    if (var8[0].equals("forceUnicodeFont")) {
                        this.forceUnicodeFont = var8[1].equals("true");
                    }
                    KeyBinding[] var4 = this.keyBindings;
                    int var5 = var4.length;
                    int var6 = 0;
                    while (var6 < var5) {
                        KeyBinding var10 = var4[var6];
                        if (var8[0].equals("key_" + var10.getKeyDescription())) {
                            var10.setKeyCode(Integer.parseInt(var8[1]));
                        }
                        ++var6;
                    }
                    SoundCategory[] var111 = SoundCategory.values();
                    var5 = var111.length;
                    var6 = 0;
                    while (var6 < var5) {
                        SoundCategory var11 = var111[var6];
                        if (var8[0].equals("soundCategory_" + var11.getCategoryName())) {
                            this.mapSoundLevels.put(var11, Float.valueOf(this.parseFloat(var8[1])));
                        }
                        ++var6;
                    }
                }
                catch (Exception var91) {
                    logger.warn("Skipping bad option: " + var2);
                    var91.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            var9.close();
        }
        catch (Exception var101) {
            logger.error("Failed to load options", (Throwable)var101);
        }
        this.loadOfOptions();
    }

    private float parseFloat(String par1Str) {
        return par1Str.equals("true") ? 1.0f : (par1Str.equals("false") ? 0.0f : Float.parseFloat(par1Str));
    }

    public void saveOptions() {
        Object var6;
        if (Reflector.FMLClientHandler.exists() && (var6 = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0])) != null && Reflector.callBoolean(var6, Reflector.FMLClientHandler_isLoading, new Object[0])) {
            return;
        }
        try {
            PrintWriter var81 = new PrintWriter(new FileWriter(this.optionsFile));
            var81.println("invertYMouse:" + this.invertMouse);
            var81.println("mouseSensitivity:" + this.mouseSensitivity);
            var81.println("fov:" + this.fovSetting);
            var81.println("gamma:" + this.gammaSetting);
            var81.println("saturation:" + this.saturation);
            var81.println("renderDistance:" + Config.limit(this.renderDistanceChunks, 2, 16));
            var81.println("guiScale:" + this.guiScale);
            var81.println("particles:" + this.particleSetting);
            var81.println("bobView:" + this.viewBobbing);
            var81.println("anaglyph3d:" + this.anaglyph);
            var81.println("advancedOpengl:" + this.advancedOpengl);
            var81.println("maxFps:" + this.limitFramerate);
            var81.println("fboEnable:" + this.fboEnable);
            var81.println("difficulty:" + this.difficulty.getDifficultyId());
            var81.println("fancyGraphics:" + this.fancyGraphics);
            var81.println("ao:" + this.ambientOcclusion);
            var81.println("clouds:" + this.clouds);
            var81.println("resourcePacks:" + gson.toJson((Object)this.resourcePacks));
            var81.println("lastServer:" + this.lastServer);
            var81.println("lang:" + this.language);
            var81.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
            var81.println("chatColors:" + this.chatColours);
            var81.println("chatLinks:" + this.chatLinks);
            var81.println("chatLinksPrompt:" + this.chatLinksPrompt);
            var81.println("chatOpacity:" + this.chatOpacity);
            var81.println("serverTextures:" + this.serverTextures);
            var81.println("snooperEnabled:" + this.snooperEnabled);
            var81.println("fullscreen:" + this.fullScreen);
            var81.println("enableVsync:" + this.enableVsync);
            var81.println("hideServerAddress:" + this.hideServerAddress);
            var81.println("advancedItemTooltips:" + this.advancedItemTooltips);
            var81.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
            var81.println("showCape:" + this.showCape);
            var81.println("touchscreen:" + this.touchscreen);
            var81.println("overrideWidth:" + this.overrideWidth);
            var81.println("overrideHeight:" + this.overrideHeight);
            var81.println("heldItemTooltips:" + this.heldItemTooltips);
            var81.println("chatHeightFocused:" + this.chatHeightFocused);
            var81.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
            var81.println("chatScale:" + this.chatScale);
            var81.println("chatWidth:" + this.chatWidth);
            var81.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
            var81.println("mipmapLevels:" + this.mipmapLevels);
            var81.println("anisotropicFiltering:" + this.anisotropicFiltering);
            var81.println("forceUnicodeFont:" + this.forceUnicodeFont);
            KeyBinding[] var2 = this.keyBindings;
            int var3 = var2.length;
            int var4 = 0;
            while (var4 < var3) {
                KeyBinding var7 = var2[var4];
                var81.println("key_" + var7.getKeyDescription() + ":" + var7.getKeyCode());
                ++var4;
            }
            SoundCategory[] var9 = SoundCategory.values();
            var3 = var9.length;
            var4 = 0;
            while (var4 < var3) {
                SoundCategory var8 = var9[var4];
                var81.println("soundCategory_" + var8.getCategoryName() + ":" + this.getSoundLevel(var8));
                ++var4;
            }
            var81.close();
        }
        catch (Exception var71) {
            logger.error("Failed to save options", (Throwable)var71);
        }
        this.saveOfOptions();
        this.sendSettingsToServer();
    }

    public float getSoundLevel(SoundCategory p_151438_1_) {
        return this.mapSoundLevels.containsKey((Object)p_151438_1_) ? ((Float)this.mapSoundLevels.get((Object)p_151438_1_)).floatValue() : 1.0f;
    }

    public void setSoundLevel(SoundCategory p_151439_1_, float p_151439_2_) {
        this.mc.getSoundHandler().setSoundLevel(p_151439_1_, p_151439_2_);
        this.mapSoundLevels.put(p_151439_1_, Float.valueOf(p_151439_2_));
    }

    public void sendSettingsToServer() {
        if (this.mc.thePlayer != null) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, this.difficulty, this.showCape));
        }
    }

    public boolean shouldRenderClouds() {
        if (this.renderDistanceChunks >= 4 && this.clouds) {
            return true;
        }
        return false;
    }

    public void loadOfOptions() {
        try {
            File exception = this.optionsFileOF;
            if (!exception.exists()) {
                exception = this.optionsFile;
            }
            if (!exception.exists()) {
                return;
            }
            BufferedReader bufferedreader = new BufferedReader(new FileReader(exception));
            String s = "";
            while ((s = bufferedreader.readLine()) != null) {
                try {
                    String[] exception1 = s.split(":");
                    if (exception1[0].equals("ofRenderDistanceChunks") && exception1.length >= 2) {
                        this.renderDistanceChunks = Integer.valueOf(exception1[1]);
                        this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 32);
                    }
                    if (exception1[0].equals("ofFogType") && exception1.length >= 2) {
                        this.ofFogType = Integer.valueOf(exception1[1]);
                        this.ofFogType = Config.limit(this.ofFogType, 1, 3);
                    }
                    if (exception1[0].equals("ofFogStart") && exception1.length >= 2) {
                        this.ofFogStart = Float.valueOf(exception1[1]).floatValue();
                        if (this.ofFogStart < 0.2f) {
                            this.ofFogStart = 0.2f;
                        }
                        if (this.ofFogStart > 0.81f) {
                            this.ofFogStart = 0.8f;
                        }
                    }
                    if (exception1[0].equals("ofMipmapType") && exception1.length >= 2) {
                        this.ofMipmapType = Integer.valueOf(exception1[1]);
                        this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
                    }
                    if (exception1[0].equals("ofLoadFar") && exception1.length >= 2) {
                        this.ofLoadFar = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofPreloadedChunks") && exception1.length >= 2) {
                        this.ofPreloadedChunks = Integer.valueOf(exception1[1]);
                        if (this.ofPreloadedChunks < 0) {
                            this.ofPreloadedChunks = 0;
                        }
                        if (this.ofPreloadedChunks > 8) {
                            this.ofPreloadedChunks = 8;
                        }
                    }
                    if (exception1[0].equals("ofOcclusionFancy") && exception1.length >= 2) {
                        this.ofOcclusionFancy = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofSmoothFps") && exception1.length >= 2) {
                        this.ofSmoothFps = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofSmoothWorld") && exception1.length >= 2) {
                        this.ofSmoothWorld = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAoLevel") && exception1.length >= 2) {
                        this.ofAoLevel = Float.valueOf(exception1[1]).floatValue();
                        this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0f, 1.0f);
                    }
                    if (exception1[0].equals("ofClouds") && exception1.length >= 2) {
                        this.ofClouds = Integer.valueOf(exception1[1]);
                        this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                    }
                    if (exception1[0].equals("ofCloudsHeight") && exception1.length >= 2) {
                        this.ofCloudsHeight = Float.valueOf(exception1[1]).floatValue();
                        this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0f, 1.0f);
                    }
                    if (exception1[0].equals("ofTrees") && exception1.length >= 2) {
                        this.ofTrees = Integer.valueOf(exception1[1]);
                        this.ofTrees = Config.limit(this.ofTrees, 0, 2);
                    }
                    if (exception1[0].equals("ofGrass") && exception1.length >= 2) {
                        this.ofGrass = Integer.valueOf(exception1[1]);
                        this.ofGrass = Config.limit(this.ofGrass, 0, 2);
                    }
                    if (exception1[0].equals("ofDroppedItems") && exception1.length >= 2) {
                        this.ofDroppedItems = Integer.valueOf(exception1[1]);
                        this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
                    }
                    if (exception1[0].equals("ofRain") && exception1.length >= 2) {
                        this.ofRain = Integer.valueOf(exception1[1]);
                        this.ofRain = Config.limit(this.ofRain, 0, 3);
                    }
                    if (exception1[0].equals("ofWater") && exception1.length >= 2) {
                        this.ofWater = Integer.valueOf(exception1[1]);
                        this.ofWater = Config.limit(this.ofWater, 0, 3);
                    }
                    if (exception1[0].equals("ofAnimatedWater") && exception1.length >= 2) {
                        this.ofAnimatedWater = Integer.valueOf(exception1[1]);
                        this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
                    }
                    if (exception1[0].equals("ofAnimatedLava") && exception1.length >= 2) {
                        this.ofAnimatedLava = Integer.valueOf(exception1[1]);
                        this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
                    }
                    if (exception1[0].equals("ofAnimatedFire") && exception1.length >= 2) {
                        this.ofAnimatedFire = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedPortal") && exception1.length >= 2) {
                        this.ofAnimatedPortal = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedRedstone") && exception1.length >= 2) {
                        this.ofAnimatedRedstone = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedExplosion") && exception1.length >= 2) {
                        this.ofAnimatedExplosion = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedFlame") && exception1.length >= 2) {
                        this.ofAnimatedFlame = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedSmoke") && exception1.length >= 2) {
                        this.ofAnimatedSmoke = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofVoidParticles") && exception1.length >= 2) {
                        this.ofVoidParticles = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofWaterParticles") && exception1.length >= 2) {
                        this.ofWaterParticles = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofPortalParticles") && exception1.length >= 2) {
                        this.ofPortalParticles = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofPotionParticles") && exception1.length >= 2) {
                        this.ofPotionParticles = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofDrippingWaterLava") && exception1.length >= 2) {
                        this.ofDrippingWaterLava = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedTerrain") && exception1.length >= 2) {
                        this.ofAnimatedTerrain = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedTextures") && exception1.length >= 2) {
                        this.ofAnimatedTextures = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAnimatedItems") && exception1.length >= 2) {
                        this.ofAnimatedItems = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofRainSplash") && exception1.length >= 2) {
                        this.ofRainSplash = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofLagometer") && exception1.length >= 2) {
                        this.ofLagometer = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAutoSaveTicks") && exception1.length >= 2) {
                        this.ofAutoSaveTicks = Integer.valueOf(exception1[1]);
                        this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
                    }
                    if (exception1[0].equals("ofBetterGrass") && exception1.length >= 2) {
                        this.ofBetterGrass = Integer.valueOf(exception1[1]);
                        this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
                    }
                    if (exception1[0].equals("ofConnectedTextures") && exception1.length >= 2) {
                        this.ofConnectedTextures = Integer.valueOf(exception1[1]);
                        this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
                    }
                    if (exception1[0].equals("ofWeather") && exception1.length >= 2) {
                        this.ofWeather = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofSky") && exception1.length >= 2) {
                        this.ofSky = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofStars") && exception1.length >= 2) {
                        this.ofStars = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofSunMoon") && exception1.length >= 2) {
                        this.ofSunMoon = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofChunkUpdates") && exception1.length >= 2) {
                        this.ofChunkUpdates = Integer.valueOf(exception1[1]);
                        this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
                    }
                    if (exception1[0].equals("ofChunkLoading") && exception1.length >= 2) {
                        this.ofChunkLoading = Integer.valueOf(exception1[1]);
                        this.ofChunkLoading = Config.limit(this.ofChunkLoading, 0, 2);
                        this.updateChunkLoading();
                    }
                    if (exception1[0].equals("ofChunkUpdatesDynamic") && exception1.length >= 2) {
                        this.ofChunkUpdatesDynamic = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofTime") && exception1.length >= 2) {
                        this.ofTime = Integer.valueOf(exception1[1]);
                        this.ofTime = Config.limit(this.ofTime, 0, 3);
                    }
                    if (exception1[0].equals("ofClearWater") && exception1.length >= 2) {
                        this.ofClearWater = Boolean.valueOf(exception1[1]);
                        this.updateWaterOpacity();
                    }
                    if (exception1[0].equals("ofDepthFog") && exception1.length >= 2) {
                        this.ofDepthFog = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofAaLevel") && exception1.length >= 2) {
                        this.ofAaLevel = Integer.valueOf(exception1[1]);
                        this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
                    }
                    if (exception1[0].equals("ofProfiler") && exception1.length >= 2) {
                        this.ofProfiler = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofBetterSnow") && exception1.length >= 2) {
                        this.ofBetterSnow = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofSwampColors") && exception1.length >= 2) {
                        this.ofSwampColors = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofRandomMobs") && exception1.length >= 2) {
                        this.ofRandomMobs = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofSmoothBiomes") && exception1.length >= 2) {
                        this.ofSmoothBiomes = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofCustomFonts") && exception1.length >= 2) {
                        this.ofCustomFonts = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofCustomColors") && exception1.length >= 2) {
                        this.ofCustomColors = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofCustomSky") && exception1.length >= 2) {
                        this.ofCustomSky = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofShowCapes") && exception1.length >= 2) {
                        this.ofShowCapes = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofNaturalTextures") && exception1.length >= 2) {
                        this.ofNaturalTextures = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofLazyChunkLoading") && exception1.length >= 2) {
                        this.ofLazyChunkLoading = Boolean.valueOf(exception1[1]);
                    }
                    if (exception1[0].equals("ofFullscreenMode") && exception1.length >= 2) {
                        this.ofFullscreenMode = exception1[1];
                    }
                    if (exception1[0].equals("ofFastMath") && exception1.length >= 2) {
                        MathHelper.fastMath = this.ofFastMath = Boolean.valueOf(exception1[1]).booleanValue();
                    }
                    if (exception1[0].equals("ofFastRender") && exception1.length >= 2) {
                        this.ofFastRender = Boolean.valueOf(exception1[1]);
                    }
                    if (!exception1[0].equals("ofTranslucentBlocks") || exception1.length < 2) continue;
                    this.ofTranslucentBlocks = Integer.valueOf(exception1[1]);
                    this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 1, 2);
                }
                catch (Exception var5) {
                    Config.dbg("Skipping bad option: " + s);
                    var5.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader.close();
        }
        catch (Exception var6) {
            Config.warn("Failed to load options");
            var6.printStackTrace();
        }
    }

    public void saveOfOptions() {
        try {
            PrintWriter exception = new PrintWriter(new FileWriter(this.optionsFileOF));
            exception.println("ofRenderDistanceChunks:" + this.renderDistanceChunks);
            exception.println("ofFogType:" + this.ofFogType);
            exception.println("ofFogStart:" + this.ofFogStart);
            exception.println("ofMipmapType:" + this.ofMipmapType);
            exception.println("ofLoadFar:" + this.ofLoadFar);
            exception.println("ofPreloadedChunks:" + this.ofPreloadedChunks);
            exception.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
            exception.println("ofSmoothFps:" + this.ofSmoothFps);
            exception.println("ofSmoothWorld:" + this.ofSmoothWorld);
            exception.println("ofAoLevel:" + this.ofAoLevel);
            exception.println("ofClouds:" + this.ofClouds);
            exception.println("ofCloudsHeight:" + this.ofCloudsHeight);
            exception.println("ofTrees:" + this.ofTrees);
            exception.println("ofGrass:" + this.ofGrass);
            exception.println("ofDroppedItems:" + this.ofDroppedItems);
            exception.println("ofRain:" + this.ofRain);
            exception.println("ofWater:" + this.ofWater);
            exception.println("ofAnimatedWater:" + this.ofAnimatedWater);
            exception.println("ofAnimatedLava:" + this.ofAnimatedLava);
            exception.println("ofAnimatedFire:" + this.ofAnimatedFire);
            exception.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
            exception.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
            exception.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
            exception.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
            exception.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
            exception.println("ofVoidParticles:" + this.ofVoidParticles);
            exception.println("ofWaterParticles:" + this.ofWaterParticles);
            exception.println("ofPortalParticles:" + this.ofPortalParticles);
            exception.println("ofPotionParticles:" + this.ofPotionParticles);
            exception.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
            exception.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
            exception.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
            exception.println("ofAnimatedItems:" + this.ofAnimatedItems);
            exception.println("ofRainSplash:" + this.ofRainSplash);
            exception.println("ofLagometer:" + this.ofLagometer);
            exception.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
            exception.println("ofBetterGrass:" + this.ofBetterGrass);
            exception.println("ofConnectedTextures:" + this.ofConnectedTextures);
            exception.println("ofWeather:" + this.ofWeather);
            exception.println("ofSky:" + this.ofSky);
            exception.println("ofStars:" + this.ofStars);
            exception.println("ofSunMoon:" + this.ofSunMoon);
            exception.println("ofChunkUpdates:" + this.ofChunkUpdates);
            exception.println("ofChunkLoading:" + this.ofChunkLoading);
            exception.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
            exception.println("ofTime:" + this.ofTime);
            exception.println("ofClearWater:" + this.ofClearWater);
            exception.println("ofDepthFog:" + this.ofDepthFog);
            exception.println("ofAaLevel:" + this.ofAaLevel);
            exception.println("ofProfiler:" + this.ofProfiler);
            exception.println("ofBetterSnow:" + this.ofBetterSnow);
            exception.println("ofSwampColors:" + this.ofSwampColors);
            exception.println("ofRandomMobs:" + this.ofRandomMobs);
            exception.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
            exception.println("ofCustomFonts:" + this.ofCustomFonts);
            exception.println("ofCustomColors:" + this.ofCustomColors);
            exception.println("ofCustomSky:" + this.ofCustomSky);
            exception.println("ofShowCapes:" + this.ofShowCapes);
            exception.println("ofNaturalTextures:" + this.ofNaturalTextures);
            exception.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
            exception.println("ofFullscreenMode:" + this.ofFullscreenMode);
            exception.println("ofFastMath:" + this.ofFastMath);
            exception.println("ofFastRender:" + this.ofFastRender);
            exception.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
            exception.close();
        }
        catch (Exception var2) {
            Config.warn("Failed to save options");
            var2.printStackTrace();
        }
    }

    public void resetSettings() {
        this.renderDistanceChunks = 8;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.advancedOpengl = false;
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.enableVsync = false;
        this.updateVSync();
        this.mipmapLevels = 4;
        this.anisotropicFiltering = 1;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = true;
        this.fovSetting = 0.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.heldItemTooltips = true;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofLoadFar = false;
        this.ofPreloadedChunks = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        Config.updateAvailableProcessors();
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 2;
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.mc.renderGlobal.updateCapes();
        this.updateWaterOpacity();
        this.mc.renderGlobal.setAllRenderersVisible();
        this.mc.refreshResources();
        this.saveOptions();
    }

    public void updateVSync() {
        Display.setVSyncEnabled((boolean)this.enableVsync);
    }

    private void updateWaterOpacity() {
        IChunkProvider cp;
        if (this.mc.getIntegratedServer() != null) {
            Config.waterOpacityChanged = true;
        }
        int opacity = 3;
        if (this.ofClearWater) {
            opacity = 1;
        }
        BlockUtils.setLightOpacity(Blocks.water, opacity);
        BlockUtils.setLightOpacity(Blocks.flowing_water, opacity);
        if (this.mc.theWorld != null && (cp = this.mc.theWorld.getChunkProvider()) != null) {
            int x = -512;
            while (x < 512) {
                int z = -512;
                while (z < 512) {
                    Chunk c;
                    if (cp.chunkExists(x, z) && (c = cp.provideChunk(x, z)) != null && !(c instanceof EmptyChunk)) {
                        ExtendedBlockStorage[] ebss = c.getBlockStorageArray();
                        int i = 0;
                        while (i < ebss.length) {
                            NibbleArray na;
                            ExtendedBlockStorage ebs = ebss[i];
                            if (ebs != null && (na = ebs.getSkylightArray()) != null) {
                                byte[] data = na.data;
                                int d = 0;
                                while (d < data.length) {
                                    data[d] = 0;
                                    ++d;
                                }
                            }
                            ++i;
                        }
                        c.generateSkylightMap();
                    }
                    ++z;
                }
                ++x;
            }
            this.mc.renderGlobal.loadRenderers();
        }
    }

    public void updateChunkLoading() {
        switch (this.ofChunkLoading) {
            case 1: {
                WrUpdates.setWrUpdater(new WrUpdaterSmooth());
                break;
            }
            case 2: {
                WrUpdates.setWrUpdater(new WrUpdaterThreaded());
                break;
            }
            default: {
                WrUpdates.setWrUpdater(null);
            }
        }
        if (this.mc.renderGlobal != null) {
            this.mc.renderGlobal.loadRenderers();
        }
    }

    public void setAllAnimations(boolean flag) {
        int animVal;
        this.ofAnimatedWater = animVal = flag ? 0 : 2;
        this.ofAnimatedLava = animVal;
        this.ofAnimatedFire = flag;
        this.ofAnimatedPortal = flag;
        this.ofAnimatedRedstone = flag;
        this.ofAnimatedExplosion = flag;
        this.ofAnimatedFlame = flag;
        this.ofAnimatedSmoke = flag;
        this.ofVoidParticles = flag;
        this.ofWaterParticles = flag;
        this.ofRainSplash = flag;
        this.ofPortalParticles = flag;
        this.ofPotionParticles = flag;
        this.particleSetting = flag ? 0 : 2;
        this.ofDrippingWaterLava = flag;
        this.ofAnimatedTerrain = flag;
        this.ofAnimatedItems = flag;
        this.ofAnimatedTextures = flag;
    }

    public static enum Options {
        INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true),
        SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false),
        FOV("FOV", 2, "FOV", 2, "options.fov", true, false),
        GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false),
        SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false),
        RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0f, 16.0f, 1.0f),
        VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true),
        ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true),
        ADVANCED_OPENGL("ADVANCED_OPENGL", 8, "ADVANCED_OPENGL", 8, "options.advancedOpengl", false, true),
        FRAMERATE_LIMIT("FRAMERATE_LIMIT", 9, "FRAMERATE_LIMIT", 9, "options.framerateLimit", true, false, 0.0f, 260.0f, 5.0f),
        FBO_ENABLE("FBO_ENABLE", 10, "FBO_ENABLE", 10, "options.fboEnable", false, true),
        DIFFICULTY("DIFFICULTY", 11, "DIFFICULTY", 11, "options.difficulty", false, false),
        GRAPHICS("GRAPHICS", 12, "GRAPHICS", 12, "options.graphics", false, false),
        AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 13, "AMBIENT_OCCLUSION", 13, "options.ao", false, false),
        GUI_SCALE("GUI_SCALE", 14, "GUI_SCALE", 14, "options.guiScale", false, false),
        RENDER_CLOUDS("RENDER_CLOUDS", 15, "RENDER_CLOUDS", 15, "options.renderClouds", false, true),
        PARTICLES("PARTICLES", 16, "PARTICLES", 16, "options.particles", false, false),
        CHAT_VISIBILITY("CHAT_VISIBILITY", 17, "CHAT_VISIBILITY", 17, "options.chat.visibility", false, false),
        CHAT_COLOR("CHAT_COLOR", 18, "CHAT_COLOR", 18, "options.chat.color", false, true),
        CHAT_LINKS("CHAT_LINKS", 19, "CHAT_LINKS", 19, "options.chat.links", false, true),
        CHAT_OPACITY("CHAT_OPACITY", 20, "CHAT_OPACITY", 20, "options.chat.opacity", true, false),
        CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 21, "CHAT_LINKS_PROMPT", 21, "options.chat.links.prompt", false, true),
        USE_SERVER_TEXTURES("USE_SERVER_TEXTURES", 22, "USE_SERVER_TEXTURES", 22, "options.serverTextures", false, true),
        SNOOPER_ENABLED("SNOOPER_ENABLED", 23, "SNOOPER_ENABLED", 23, "options.snooper", false, true),
        USE_FULLSCREEN("USE_FULLSCREEN", 24, "USE_FULLSCREEN", 24, "options.fullscreen", false, true),
        ENABLE_VSYNC("ENABLE_VSYNC", 25, "ENABLE_VSYNC", 25, "options.vsync", false, true),
        SHOW_CAPE("SHOW_CAPE", 26, "SHOW_CAPE", 26, "options.showCape", false, true),
        TOUCHSCREEN("TOUCHSCREEN", 27, "TOUCHSCREEN", 27, "options.touchscreen", false, true),
        CHAT_SCALE("CHAT_SCALE", 28, "CHAT_SCALE", 28, "options.chat.scale", true, false),
        CHAT_WIDTH("CHAT_WIDTH", 29, "CHAT_WIDTH", 29, "options.chat.width", true, false),
        CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 30, "CHAT_HEIGHT_FOCUSED", 30, "options.chat.height.focused", true, false),
        CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 31, "CHAT_HEIGHT_UNFOCUSED", 31, "options.chat.height.unfocused", true, false),
        MIPMAP_LEVELS("MIPMAP_LEVELS", 32, "MIPMAP_LEVELS", 32, "options.mipmapLevels", true, false, 0.0f, 4.0f, 1.0f),
        ANISOTROPIC_FILTERING("ANISOTROPIC_FILTERING", 33, "ANISOTROPIC_FILTERING", 33, "options.anisotropicFiltering", true, false, 1.0f, 16.0f, 0.0f, (Object)null, (Object)null){
            private static final String __OBFID = "CL_00000654";

            @Override
            protected float snapToStep(float p_148264_1_) {
                return MathHelper.roundUpToPowerOfTwo((int)p_148264_1_);
            }
        }
        ,
        FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 34, "FORCE_UNICODE_FONT", 34, "options.forceUnicodeFont", false, true),
        FOG_FANCY("FOG_FANCY", 35, "FOG", 999, "Fog", false, false),
        FOG_START("FOG_START", 36, "", 999, "Fog Start", false, false),
        MIPMAP_TYPE("MIPMAP_TYPE", 37, "", 999, "Mipmap Type", false, false),
        LOAD_FAR("LOAD_FAR", 38, "", 999, "Load Far", false, false),
        PRELOADED_CHUNKS("PRELOADED_CHUNKS", 39, "", 999, "Preloaded Chunks", false, false),
        SMOOTH_FPS("SMOOTH_FPS", 40, "", 999, "Smooth FPS", false, false),
        CLOUDS("CLOUDS", 41, "", 999, "Clouds", false, false),
        CLOUD_HEIGHT("CLOUD_HEIGHT", 42, "", 999, "Cloud Height", true, false),
        TREES("TREES", 43, "", 999, "Trees", false, false),
        GRASS("GRASS", 44, "", 999, "Grass", false, false),
        RAIN("RAIN", 45, "", 999, "Rain & Snow", false, false),
        WATER("WATER", 46, "", 999, "Water", false, false),
        ANIMATED_WATER("ANIMATED_WATER", 47, "", 999, "Water Animated", false, false),
        ANIMATED_LAVA("ANIMATED_LAVA", 48, "", 999, "Lava Animated", false, false),
        ANIMATED_FIRE("ANIMATED_FIRE", 49, "", 999, "Fire Animated", false, false),
        ANIMATED_PORTAL("ANIMATED_PORTAL", 50, "", 999, "Portal Animated", false, false),
        AO_LEVEL("AO_LEVEL", 51, "", 999, "Smooth Lighting Level", true, false),
        LAGOMETER("LAGOMETER", 52, "", 999, "Lagometer", false, false),
        AUTOSAVE_TICKS("AUTOSAVE_TICKS", 53, "", 999, "Autosave", false, false),
        BETTER_GRASS("BETTER_GRASS", 54, "", 999, "Better Grass", false, false),
        ANIMATED_REDSTONE("ANIMATED_REDSTONE", 55, "", 999, "Redstone Animated", false, false),
        ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 56, "", 999, "Explosion Animated", false, false),
        ANIMATED_FLAME("ANIMATED_FLAME", 57, "", 999, "Flame Animated", false, false),
        ANIMATED_SMOKE("ANIMATED_SMOKE", 58, "", 999, "Smoke Animated", false, false),
        WEATHER("WEATHER", 59, "", 999, "Weather", false, false),
        SKY("SKY", 60, "", 999, "Sky", false, false),
        STARS("STARS", 61, "", 999, "Stars", false, false),
        SUN_MOON("SUN_MOON", 62, "", 999, "Sun & Moon", false, false),
        CHUNK_UPDATES("CHUNK_UPDATES", 63, "", 999, "Chunk Updates", false, false),
        CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 64, "", 999, "Dynamic Updates", false, false),
        TIME("TIME", 65, "", 999, "Time", false, false),
        CLEAR_WATER("CLEAR_WATER", 66, "", 999, "Clear Water", false, false),
        SMOOTH_WORLD("SMOOTH_WORLD", 67, "", 999, "Smooth World", false, false),
        DEPTH_FOG("DEPTH_FOG", 68, "", 999, "Depth Fog", false, false),
        VOID_PARTICLES("VOID_PARTICLES", 69, "", 999, "Void Particles", false, false),
        WATER_PARTICLES("WATER_PARTICLES", 70, "", 999, "Water Particles", false, false),
        RAIN_SPLASH("RAIN_SPLASH", 71, "", 999, "Rain Splash", false, false),
        PORTAL_PARTICLES("PORTAL_PARTICLES", 72, "", 999, "Portal Particles", false, false),
        POTION_PARTICLES("POTION_PARTICLES", 73, "", 999, "Potion Particles", false, false),
        PROFILER("PROFILER", 74, "", 999, "Debug Profiler", false, false),
        DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 75, "", 999, "Dripping Water/Lava", false, false),
        BETTER_SNOW("BETTER_SNOW", 76, "", 999, "Better Snow", false, false),
        FULLSCREEN_MODE("FULLSCREEN_MODE", 77, "", 999, "Fullscreen Mode", false, false),
        ANIMATED_TERRAIN("ANIMATED_TERRAIN", 78, "", 999, "Terrain Animated", false, false),
        ANIMATED_ITEMS("ANIMATED_ITEMS", 79, "", 999, "Items Animated", false, false),
        SWAMP_COLORS("SWAMP_COLORS", 80, "", 999, "Swamp Colors", false, false),
        RANDOM_MOBS("RANDOM_MOBS", 81, "", 999, "Random Mobs", false, false),
        SMOOTH_BIOMES("SMOOTH_BIOMES", 82, "", 999, "Smooth Biomes", false, false),
        CUSTOM_FONTS("CUSTOM_FONTS", 83, "", 999, "Custom Fonts", false, false),
        CUSTOM_COLORS("CUSTOM_COLORS", 84, "", 999, "Custom Colors", false, false),
        SHOW_CAPES("SHOW_CAPES", 85, "", 999, "Show Capes", false, false),
        CONNECTED_TEXTURES("CONNECTED_TEXTURES", 86, "", 999, "Connected Textures", false, false),
        AA_LEVEL("AA_LEVEL", 87, "", 999, "Antialiasing", false, false),
        ANIMATED_TEXTURES("ANIMATED_TEXTURES", 88, "", 999, "Textures Animated", false, false),
        NATURAL_TEXTURES("NATURAL_TEXTURES", 89, "", 999, "Natural Textures", false, false),
        CHUNK_LOADING("CHUNK_LOADING", 90, "", 999, "Chunk Loading", false, false),
        HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 91, "", 999, "Held Item Tooltips", false, false),
        DROPPED_ITEMS("DROPPED_ITEMS", 92, "", 999, "Dropped Items", false, false),
        LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 93, "", 999, "Lazy Chunk Loading", false, false),
        CUSTOM_SKY("CUSTOM_SKY", 94, "", 999, "Custom Sky", false, false),
        FAST_MATH("FAST_MATH", 95, "", 999, "Fast Math", false, false),
        FAST_RENDER("FAST_RENDER", 96, "", 999, "Fast Render", false, false),
        TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 97, "", 999, "Translucent Blocks", false, false);
        
        private final boolean enumFloat;
        private final boolean enumBoolean;
        private final String enumString;
        private final float valueStep;
        private float valueMin;
        private float valueMax;
        private static final Options[] $VALUES;
        private static final Options[] $VALUES$;
        private static final String __OBFID = "CL_00000653";

        static {
            $VALUES = new Options[]{INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, FBO_ENABLE, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, RENDER_CLOUDS, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, USE_SERVER_TEXTURES, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, SHOW_CAPE, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, ANISOTROPIC_FILTERING, FORCE_UNICODE_FONT};
            $VALUES$ = new Options[]{INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, FBO_ENABLE, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, RENDER_CLOUDS, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, USE_SERVER_TEXTURES, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, SHOW_CAPE, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, ANISOTROPIC_FILTERING, FORCE_UNICODE_FONT, FOG_FANCY, FOG_START, MIPMAP_TYPE, LOAD_FAR, PRELOADED_CHUNKS, SMOOTH_FPS, CLOUDS, CLOUD_HEIGHT, TREES, GRASS, RAIN, WATER, ANIMATED_WATER, ANIMATED_LAVA, ANIMATED_FIRE, ANIMATED_PORTAL, AO_LEVEL, LAGOMETER, AUTOSAVE_TICKS, BETTER_GRASS, ANIMATED_REDSTONE, ANIMATED_EXPLOSION, ANIMATED_FLAME, ANIMATED_SMOKE, WEATHER, SKY, STARS, SUN_MOON, CHUNK_UPDATES, CHUNK_UPDATES_DYNAMIC, TIME, CLEAR_WATER, SMOOTH_WORLD, DEPTH_FOG, VOID_PARTICLES, WATER_PARTICLES, RAIN_SPLASH, PORTAL_PARTICLES, POTION_PARTICLES, PROFILER, DRIPPING_WATER_LAVA, BETTER_SNOW, FULLSCREEN_MODE, ANIMATED_TERRAIN, ANIMATED_ITEMS, SWAMP_COLORS, RANDOM_MOBS, SMOOTH_BIOMES, CUSTOM_FONTS, CUSTOM_COLORS, SHOW_CAPES, CONNECTED_TEXTURES, AA_LEVEL, ANIMATED_TEXTURES, NATURAL_TEXTURES, CHUNK_LOADING, HELD_ITEM_TOOLTIPS, DROPPED_ITEMS, LAZY_CHUNK_LOADING, CUSTOM_SKY, FAST_MATH, FAST_RENDER, TRANSLUCENT_BLOCKS};
        }

        public static Options getEnumOptions(int par0) {
            Options[] var1 = Options.values();
            int var2 = var1.length;
            int var3 = 0;
            while (var3 < var2) {
                Options var4 = var1[var3];
                if (var4.returnEnumOrdinal() == par0) {
                    return var4;
                }
                ++var3;
            }
            return null;
        }

        private Options(String var1, int var2, String par1Str, int par2, String par3Str, int par4, String par5, boolean bl, boolean bl2) {
            this(string, n, var1, var2, par1Str, par2, par3Str, (boolean)par4, (boolean)par5, 0.0f, 1.0f, 0.0f);
        }

        private Options(String var1, int var2, String p_i45004_1_, int p_i45004_2_, String p_i45004_3_, int p_i45004_4_, String p_i45004_5_, boolean p_i45004_6_, boolean p_i45004_7_, float p_i45004_8_, float f, float f2) {
            this.enumString = p_i45004_3_;
            this.enumFloat = p_i45004_4_;
            this.enumBoolean = p_i45004_5_;
            this.valueMin = (float)p_i45004_6_ ? 1 : 0;
            this.valueMax = (float)p_i45004_7_ ? 1 : 0;
            this.valueStep = p_i45004_8_;
        }

        public boolean getEnumFloat() {
            return this.enumFloat;
        }

        public boolean getEnumBoolean() {
            return this.enumBoolean;
        }

        public int returnEnumOrdinal() {
            return this.ordinal();
        }

        public String getEnumString() {
            return this.enumString;
        }

        public float getValueMax() {
            return this.valueMax;
        }

        public void setValueMax(float p_148263_1_) {
            this.valueMax = p_148263_1_;
        }

        public float normalizeValue(float p_148266_1_) {
            return MathHelper.clamp_float((this.snapToStepClamp(p_148266_1_) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
        }

        public float denormalizeValue(float p_148262_1_) {
            return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0f, 1.0f));
        }

        public float snapToStepClamp(float p_148268_1_) {
            p_148268_1_ = this.snapToStep(p_148268_1_);
            return MathHelper.clamp_float(p_148268_1_, this.valueMin, this.valueMax);
        }

        protected float snapToStep(float p_148264_1_) {
            if (this.valueStep > 0.0f) {
                p_148264_1_ = this.valueStep * (float)Math.round(p_148264_1_ / this.valueStep);
            }
            return p_148264_1_;
        }

        private Options(String var1, int var2, String p_i45005_1_, int p_i45005_2_, String p_i45005_3_, int p_i45005_4_, String p_i45005_5_, boolean p_i45005_6_, boolean p_i45005_7_, float p_i45005_8_, float p_i45005_9_, float f, Object object) {
            this(string, n, var1, var2, p_i45005_1_, p_i45005_2_, p_i45005_3_, (boolean)p_i45005_4_, (boolean)p_i45005_5_, (float)p_i45005_6_ ? 1 : 0, (float)p_i45005_7_ ? 1 : 0, p_i45005_8_);
        }

        private Options(String x0, int x1, String x2, int x3, String x4, int x5, String x6, boolean x7, boolean x8, float x9, float x10, float x11, Object object, Object object2) {
            this(string, n, x0, x1, x2, x3, x4, (boolean)x5, (boolean)x6, (float)x7 ? 1 : 0, (float)x8 ? 1 : 0, x9, (Object)x10);
        }

        /* synthetic */ Options(String string, int n, String string2, int n2, String string3, int n3, String string4, boolean bl, boolean bl2, float f, float f2, float f3, Object object, Object object2, Options options) {
            Options options2;
            options2(string, n, string2, n2, string3, n3, string4, bl, bl2, f, f2, f3, object, object2);
        }

    }

    static final class SwitchOptions {
        static final int[] optionIds = new int[Options.values().length];
        private static final String __OBFID = "CL_00000652";

        static {
            try {
                SwitchOptions.optionIds[Options.INVERT_MOUSE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.VIEW_BOBBING.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.ANAGLYPH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.ADVANCED_OPENGL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.FBO_ENABLE.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.RENDER_CLOUDS.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.CHAT_COLOR.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.CHAT_LINKS.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.CHAT_LINKS_PROMPT.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.USE_SERVER_TEXTURES.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.SNOOPER_ENABLED.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.USE_FULLSCREEN.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.ENABLE_VSYNC.ordinal()] = 13;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.SHOW_CAPE.ordinal()] = 14;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.TOUCHSCREEN.ordinal()] = 15;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchOptions.optionIds[Options.FORCE_UNICODE_FONT.ordinal()] = 16;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchOptions() {
        }
    }

}

