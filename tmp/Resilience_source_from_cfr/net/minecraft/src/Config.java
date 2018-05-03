/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.Sys
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.opengl.PixelFormat
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.src;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.init.Blocks;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.TextureUtils;
import net.minecraft.src.VersionCheckThread;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Config {
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.7.2";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "D1";
    public static final String VERSION = "OptiFine_1.7.2_HD_U_D1";
    private static String newRelease = null;
    private static GameSettings gameSettings = null;
    private static Minecraft minecraft = null;
    private static boolean initialized = false;
    private static Thread minecraftThread = null;
    private static DisplayMode desktopDisplayMode = null;
    private static int antialiasingLevel = 0;
    private static int availableProcessors = 0;
    public static boolean zoomMode = false;
    private static int texturePackClouds = 0;
    public static boolean waterOpacityChanged = false;
    private static boolean fullscreenModeChecked = false;
    private static boolean desktopModeChecked = false;
    private static PrintStream systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
    public static final Boolean DEF_FOG_FANCY = true;
    public static final Float DEF_FOG_START = Float.valueOf(0.2f);
    public static final Boolean DEF_OPTIMIZE_RENDER_DISTANCE = false;
    public static final Boolean DEF_OCCLUSION_ENABLED = false;
    public static final Integer DEF_MIPMAP_LEVEL = 0;
    public static final Integer DEF_MIPMAP_TYPE = 9984;
    public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1f);
    public static final Boolean DEF_LOAD_CHUNKS_FAR = false;
    public static final Integer DEF_PRELOADED_CHUNKS = 0;
    public static final Integer DEF_CHUNKS_LIMIT = 25;
    public static final Integer DEF_UPDATES_PER_FRAME = 3;
    public static final Boolean DEF_DYNAMIC_UPDATES = false;

    public static String getVersion() {
        return "OptiFine_1.7.2_HD_U_D1";
    }

    public static void initGameSettings(GameSettings settings) {
        gameSettings = settings;
        minecraft = Minecraft.getMinecraft();
        desktopDisplayMode = Display.getDesktopDisplayMode();
        Config.updateAvailableProcessors();
    }

    public static void initDisplay() {
        Config.checkInitialized();
        antialiasingLevel = Config.gameSettings.ofAaLevel;
        Config.checkDisplaySettings();
        Config.checkDisplayMode();
        minecraftThread = Thread.currentThread();
        Config.updateThreadPriorities();
    }

    public static void checkInitialized() {
        if (!initialized && Display.isCreated()) {
            initialized = true;
            Config.checkOpenGlCaps();
            Config.startVersionCheckThread();
        }
    }

    private static void checkOpenGlCaps() {
        Config.log("");
        Config.log(Config.getVersion());
        Config.log((String)((Object)new Date()));
        Config.log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        Config.log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        Config.log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        Config.log("LWJGL: " + Sys.getVersion());
        Config.log("OpenGL: " + GL11.glGetString((int)7937) + " version " + GL11.glGetString((int)7938) + ", " + GL11.glGetString((int)7936));
        int ver = Config.getOpenGlVersion();
        String verStr = "" + ver / 10 + "." + ver % 10;
        Config.log("OpenGL Version: " + verStr);
        if (!GLContext.getCapabilities().OpenGL12) {
            Config.log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }
        if (!GLContext.getCapabilities().GL_NV_fog_distance) {
            Config.log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }
        if (!GLContext.getCapabilities().GL_ARB_occlusion_query) {
            Config.log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
        int maxTexSize = Minecraft.getGLMaximumTextureSize();
        Config.dbg("Maximum texture size: " + maxTexSize + "x" + maxTexSize);
    }

    public static boolean isFancyFogAvailable() {
        return GLContext.getCapabilities().GL_NV_fog_distance;
    }

    public static boolean isOcclusionAvailable() {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }

    private static int getOpenGlVersion() {
        return !GLContext.getCapabilities().OpenGL11 ? 10 : (!GLContext.getCapabilities().OpenGL12 ? 11 : (!GLContext.getCapabilities().OpenGL13 ? 12 : (!GLContext.getCapabilities().OpenGL14 ? 13 : (!GLContext.getCapabilities().OpenGL15 ? 14 : (!GLContext.getCapabilities().OpenGL20 ? 15 : (!GLContext.getCapabilities().OpenGL21 ? 20 : (!GLContext.getCapabilities().OpenGL30 ? 21 : (!GLContext.getCapabilities().OpenGL31 ? 30 : (!GLContext.getCapabilities().OpenGL32 ? 31 : (!GLContext.getCapabilities().OpenGL33 ? 32 : (!GLContext.getCapabilities().OpenGL40 ? 33 : 40)))))))))));
    }

    public static void updateThreadPriorities() {
        try {
            ThreadGroup e = Thread.currentThread().getThreadGroup();
            if (e == null) {
                return;
            }
            int num = (e.activeCount() + 10) * 2;
            Thread[] ts = new Thread[num];
            e.enumerate(ts, false);
            int prioMc = 5;
            int prioSrv = 5;
            if (Config.isSmoothWorld()) {
                prioSrv = 3;
            }
            minecraftThread.setPriority(prioMc);
            int i = 0;
            while (i < ts.length) {
                Thread t = ts[i];
                if (t != null && Config.equals(t.getName(), "Server thread") && t.getPriority() != prioSrv) {
                    t.setPriority(prioSrv);
                    Config.dbg("Set server thread priority: " + prioSrv + ", " + t);
                }
                ++i;
            }
        }
        catch (Throwable var7) {
            Config.dbg(String.valueOf(var7.getClass().getName()) + ": " + var7.getMessage());
        }
    }

    public static boolean isMinecraftThread() {
        if (Thread.currentThread() == minecraftThread) {
            return true;
        }
        return false;
    }

    private static void startVersionCheckThread() {
        VersionCheckThread vct = new VersionCheckThread();
        vct.start();
    }

    public static int getMipmapType() {
        if (gameSettings == null) {
            return DEF_MIPMAP_TYPE;
        }
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9984;
            }
            case 1: {
                return 9986;
            }
            case 2: {
                if (Config.isMultiTexture()) {
                    return 9985;
                }
                return 9986;
            }
            case 3: {
                if (Config.isMultiTexture()) {
                    return 9987;
                }
                return 9986;
            }
        }
        return 9984;
    }

    public static boolean isUseAlphaFunc() {
        float alphaFuncLevel = Config.getAlphaFuncLevel();
        if (alphaFuncLevel > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5f) {
            return true;
        }
        return false;
    }

    public static float getAlphaFuncLevel() {
        return DEF_ALPHA_FUNC_LEVEL.floatValue();
    }

    public static boolean isFogFancy() {
        return !Config.isFancyFogAvailable() ? false : Config.gameSettings.ofFogType == 2;
    }

    public static boolean isFogFast() {
        if (Config.gameSettings.ofFogType == 1) {
            return true;
        }
        return false;
    }

    public static boolean isFogOff() {
        if (Config.gameSettings.ofFogType == 3) {
            return true;
        }
        return false;
    }

    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }

    public static boolean isOcclusionEnabled() {
        return Config.gameSettings.advancedOpengl;
    }

    public static boolean isOcclusionFancy() {
        return !Config.isOcclusionEnabled() ? false : Config.gameSettings.ofOcclusionFancy;
    }

    public static boolean isLoadChunksFar() {
        return Config.gameSettings.ofLoadFar;
    }

    public static int getPreloadedChunks() {
        return Config.gameSettings.ofPreloadedChunks;
    }

    public static void dbg(String s) {
        systemOut.print("[OptiFine] ");
        systemOut.println(s);
    }

    public static void warn(String s) {
        systemOut.print("[OptiFine] [WARN] ");
        systemOut.println(s);
    }

    public static void error(String s) {
        systemOut.print("[OptiFine] [ERROR] ");
        systemOut.println(s);
    }

    public static void log(String s) {
        Config.dbg(s);
    }

    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }

    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }

    public static boolean isRainFancy() {
        return Config.gameSettings.ofRain == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofRain == 2;
    }

    public static boolean isWaterFancy() {
        return Config.gameSettings.ofWater == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofWater == 2;
    }

    public static boolean isRainOff() {
        if (Config.gameSettings.ofRain == 3) {
            return true;
        }
        return false;
    }

    public static boolean isCloudsFancy() {
        return Config.gameSettings.ofClouds != 0 ? Config.gameSettings.ofClouds == 2 : (texturePackClouds != 0 ? texturePackClouds == 2 : Config.gameSettings.fancyGraphics);
    }

    public static boolean isCloudsOff() {
        if (Config.gameSettings.ofClouds == 3) {
            return true;
        }
        return false;
    }

    public static void updateTexturePackClouds() {
        texturePackClouds = 0;
        IResourceManager rm = Config.getResourceManager();
        if (rm != null) {
            try {
                InputStream e = rm.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
                if (e == null) {
                    return;
                }
                Properties props = new Properties();
                props.load(e);
                e.close();
                String cloudStr = props.getProperty("clouds");
                if (cloudStr == null) {
                    return;
                }
                Config.dbg("Texture pack clouds: " + cloudStr);
                cloudStr = cloudStr.toLowerCase();
                if (cloudStr.equals("fast")) {
                    texturePackClouds = 1;
                }
                if (cloudStr.equals("fancy")) {
                    texturePackClouds = 2;
                }
            }
            catch (Exception e) {
                // empty catch block
            }
        }
    }

    public static boolean isTreesFancy() {
        return Config.gameSettings.ofTrees == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofTrees == 2;
    }

    public static boolean isGrassFancy() {
        return Config.gameSettings.ofGrass == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofGrass == 2;
    }

    public static boolean isDroppedItemsFancy() {
        return Config.gameSettings.ofDroppedItems == 0 ? Config.gameSettings.fancyGraphics : Config.gameSettings.ofDroppedItems == 2;
    }

    public static int limit(int val, int min, int max) {
        return val < min ? min : (val > max ? max : val);
    }

    public static float limit(float val, float min, float max) {
        return val < min ? min : (val > max ? max : val);
    }

    public static float limitTo1(float val) {
        return val < 0.0f ? 0.0f : (val > 1.0f ? 1.0f : val);
    }

    public static boolean isAnimatedWater() {
        if (Config.gameSettings.ofAnimatedWater != 2) {
            return true;
        }
        return false;
    }

    public static boolean isGeneratedWater() {
        if (Config.gameSettings.ofAnimatedWater == 1) {
            return true;
        }
        return false;
    }

    public static boolean isAnimatedPortal() {
        return Config.gameSettings.ofAnimatedPortal;
    }

    public static boolean isAnimatedLava() {
        if (Config.gameSettings.ofAnimatedLava != 2) {
            return true;
        }
        return false;
    }

    public static boolean isGeneratedLava() {
        if (Config.gameSettings.ofAnimatedLava == 1) {
            return true;
        }
        return false;
    }

    public static boolean isAnimatedFire() {
        return Config.gameSettings.ofAnimatedFire;
    }

    public static boolean isAnimatedRedstone() {
        return Config.gameSettings.ofAnimatedRedstone;
    }

    public static boolean isAnimatedExplosion() {
        return Config.gameSettings.ofAnimatedExplosion;
    }

    public static boolean isAnimatedFlame() {
        return Config.gameSettings.ofAnimatedFlame;
    }

    public static boolean isAnimatedSmoke() {
        return Config.gameSettings.ofAnimatedSmoke;
    }

    public static boolean isVoidParticles() {
        return Config.gameSettings.ofVoidParticles;
    }

    public static boolean isWaterParticles() {
        return Config.gameSettings.ofWaterParticles;
    }

    public static boolean isRainSplash() {
        return Config.gameSettings.ofRainSplash;
    }

    public static boolean isPortalParticles() {
        return Config.gameSettings.ofPortalParticles;
    }

    public static boolean isPotionParticles() {
        return Config.gameSettings.ofPotionParticles;
    }

    public static boolean isDepthFog() {
        return Config.gameSettings.ofDepthFog;
    }

    public static float getAmbientOcclusionLevel() {
        return Config.gameSettings.ofAoLevel;
    }

    private static Method getMethod(Class cls, String methodName, Object[] params) {
        Method[] methods = cls.getMethods();
        int i = 0;
        while (i < methods.length) {
            Method m = methods[i];
            if (m.getName().equals(methodName) && m.getParameterTypes().length == params.length) {
                return m;
            }
            ++i;
        }
        Config.warn("No method found for: " + cls.getName() + "." + methodName + "(" + Config.arrayToString(params) + ")");
        return null;
    }

    public static String arrayToString(Object[] arr) {
        if (arr == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(arr.length * 5);
        int i = 0;
        while (i < arr.length) {
            Object obj = arr[i];
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(obj));
            ++i;
        }
        return buf.toString();
    }

    public static String arrayToString(int[] arr) {
        if (arr == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(arr.length * 5);
        int i = 0;
        while (i < arr.length) {
            int x = arr[i];
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(String.valueOf(x));
            ++i;
        }
        return buf.toString();
    }

    public static Minecraft getMinecraft() {
        return minecraft;
    }

    public static TextureManager getTextureManager() {
        return minecraft.getTextureManager();
    }

    public static IResourceManager getResourceManager() {
        return minecraft.getResourceManager();
    }

    public static InputStream getResourceStream(ResourceLocation location) throws IOException {
        return Config.getResourceStream(minecraft.getResourceManager(), location);
    }

    public static InputStream getResourceStream(IResourceManager resourceManager, ResourceLocation location) throws IOException {
        IResource res = resourceManager.getResource(location);
        return res == null ? null : res.getInputStream();
    }

    public static IResource getResource(ResourceLocation location) throws IOException {
        return minecraft.getResourceManager().getResource(location);
    }

    public static boolean hasResource(ResourceLocation location) {
        try {
            IResource e = Config.getResource(location);
            if (e != null) {
                return true;
            }
            return false;
        }
        catch (IOException var2) {
            return false;
        }
    }

    public static boolean hasResource(IResourceManager resourceManager, ResourceLocation location) {
        try {
            IResource e = resourceManager.getResource(location);
            if (e != null) {
                return true;
            }
            return false;
        }
        catch (IOException var3) {
            return false;
        }
    }

    public static IResourcePack[] getResourcePacks() {
        ResourcePackRepository rep = minecraft.getResourcePackRepository();
        List entries = rep.getRepositoryEntries();
        ArrayList<IResourcePack> list = new ArrayList<IResourcePack>();
        for (ResourcePackRepository.Entry entry : entries) {
            list.add(entry.getResourcePack());
        }
        IResourcePack[] rps1 = list.toArray(new IResourcePack[list.size()]);
        return rps1;
    }

    public static String getResourcePackNames() {
        IResourcePack[] rps = Config.getResourcePacks();
        if (rps.length <= 0) {
            return Config.getDefaultResourcePack().getPackName();
        }
        Object[] names = new String[rps.length];
        int nameStr = 0;
        while (nameStr < rps.length) {
            names[nameStr] = rps[nameStr].getPackName();
            ++nameStr;
        }
        String var3 = Config.arrayToString(names);
        return var3;
    }

    public static IResourcePack getDefaultResourcePack() {
        return Config.minecraft.getResourcePackRepository().rprDefaultResourcePack;
    }

    public static boolean isFromDefaultResourcePack(ResourceLocation loc) {
        IResourcePack rp = Config.getDefiningResourcePack(loc);
        if (rp == Config.getDefaultResourcePack()) {
            return true;
        }
        return false;
    }

    public static IResourcePack getDefiningResourcePack(ResourceLocation loc) {
        IResourcePack[] rps = Config.getResourcePacks();
        int i = rps.length - 1;
        while (i >= 0) {
            IResourcePack rp = rps[i];
            if (rp.resourceExists(loc)) {
                return rp;
            }
            --i;
        }
        if (Config.getDefaultResourcePack().resourceExists(loc)) {
            return Config.getDefaultResourcePack();
        }
        return null;
    }

    public static RenderGlobal getRenderGlobal() {
        return minecraft == null ? null : Config.minecraft.renderGlobal;
    }

    public static int getMaxDynamicTileWidth() {
        return 64;
    }

    public static IIcon getSideGrassTexture(IBlockAccess blockAccess, int x, int y, int z, int side, IIcon icon) {
        if (!Config.isBetterGrass()) {
            return icon;
        }
        IIcon fullIcon = TextureUtils.iconGrassTop;
        Block destBlock = Blocks.grass;
        if (icon == TextureUtils.iconMyceliumSide) {
            fullIcon = TextureUtils.iconMyceliumTop;
            destBlock = Blocks.mycelium;
        }
        if (Config.isBetterGrassFancy()) {
            --y;
            switch (side) {
                case 2: {
                    --z;
                    break;
                }
                case 3: {
                    ++z;
                    break;
                }
                case 4: {
                    --x;
                    break;
                }
                case 5: {
                    ++x;
                }
            }
            Block block = blockAccess.getBlock(x, y, z);
            if (block != destBlock) {
                return icon;
            }
        }
        return fullIcon;
    }

    public static IIcon getSideSnowGrassTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (!Config.isBetterGrass()) {
            return TextureUtils.iconGrassSideSnowed;
        }
        if (Config.isBetterGrassFancy()) {
            switch (side) {
                case 2: {
                    --z;
                    break;
                }
                case 3: {
                    ++z;
                    break;
                }
                case 4: {
                    --x;
                    break;
                }
                case 5: {
                    ++x;
                }
            }
            Block block = blockAccess.getBlock(x, y, z);
            if (block != Blocks.snow_layer && block != Blocks.snow) {
                return TextureUtils.iconGrassSideSnowed;
            }
        }
        return TextureUtils.iconSnow;
    }

    public static boolean isBetterGrass() {
        if (Config.gameSettings.ofBetterGrass != 3) {
            return true;
        }
        return false;
    }

    public static boolean isBetterGrassFancy() {
        if (Config.gameSettings.ofBetterGrass == 2) {
            return true;
        }
        return false;
    }

    public static boolean isWeatherEnabled() {
        return Config.gameSettings.ofWeather;
    }

    public static boolean isSkyEnabled() {
        return Config.gameSettings.ofSky;
    }

    public static boolean isSunMoonEnabled() {
        return Config.gameSettings.ofSunMoon;
    }

    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }

    public static void sleep(long ms) {
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        }
        catch (InterruptedException var3) {
            var3.printStackTrace();
        }
    }

    public static boolean isTimeDayOnly() {
        if (Config.gameSettings.ofTime == 1) {
            return true;
        }
        return false;
    }

    public static boolean isTimeDefault() {
        if (Config.gameSettings.ofTime != 0 && Config.gameSettings.ofTime != 2) {
            return false;
        }
        return true;
    }

    public static boolean isTimeNightOnly() {
        if (Config.gameSettings.ofTime == 3) {
            return true;
        }
        return false;
    }

    public static boolean isClearWater() {
        return Config.gameSettings.ofClearWater;
    }

    public static int getAntialiasingLevel() {
        return antialiasingLevel;
    }

    public static boolean between(int val, int min, int max) {
        if (val >= min && val <= max) {
            return true;
        }
        return false;
    }

    public static boolean isMultiTexture() {
        return false;
    }

    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }

    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }

    public static Dimension getFullscreenDimension() {
        if (desktopDisplayMode == null) {
            return null;
        }
        if (gameSettings == null) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        String dimStr = Config.gameSettings.ofFullscreenMode;
        if (dimStr.equals("Default")) {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        String[] dimStrs = Config.tokenize(dimStr, " x");
        return dimStrs.length < 2 ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(Config.parseInt(dimStrs[0], -1), Config.parseInt(dimStrs[1], -1));
    }

    public static int parseInt(String str, int defVal) {
        try {
            return str == null ? defVal : Integer.parseInt(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static float parseFloat(String str, float defVal) {
        try {
            return str == null ? defVal : Float.parseFloat(str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static String[] tokenize(String str, String delim) {
        StringTokenizer tok = new StringTokenizer(str, delim);
        ArrayList<String> list = new ArrayList<String>();
        while (tok.hasMoreTokens()) {
            String strs = tok.nextToken();
            list.add(strs);
        }
        String[] strs1 = list.toArray(new String[list.size()]);
        return strs1;
    }

    public static DisplayMode getDesktopDisplayMode() {
        return desktopDisplayMode;
    }

    public static DisplayMode[] getFullscreenDisplayModes() {
        try {
            DisplayMode[] e = Display.getAvailableDisplayModes();
            ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
            int fsModes = 0;
            while (fsModes < e.length) {
                DisplayMode comp = e[fsModes];
                if (desktopDisplayMode == null || comp.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && comp.getFrequency() == desktopDisplayMode.getFrequency()) {
                    list.add(comp);
                }
                ++fsModes;
            }
            DisplayMode[] var5 = list.toArray((T[])new DisplayMode[list.size()]);
            Comparator var6 = new Comparator(){

                public int compare(Object o1, Object o2) {
                    DisplayMode dm1 = (DisplayMode)o1;
                    DisplayMode dm2 = (DisplayMode)o2;
                    return dm1.getWidth() != dm2.getWidth() ? dm2.getWidth() - dm1.getWidth() : (dm1.getHeight() != dm2.getHeight() ? dm2.getHeight() - dm1.getHeight() : 0);
                }
            };
            Arrays.sort(var5, var6);
            return var5;
        }
        catch (Exception var4) {
            var4.printStackTrace();
            return new DisplayMode[]{desktopDisplayMode};
        }
    }

    public static String[] getFullscreenModes() {
        DisplayMode[] modes = Config.getFullscreenDisplayModes();
        String[] names = new String[modes.length];
        int i = 0;
        while (i < modes.length) {
            String name;
            DisplayMode mode = modes[i];
            names[i] = name = "" + mode.getWidth() + "x" + mode.getHeight();
            ++i;
        }
        return names;
    }

    public static DisplayMode getDisplayMode(Dimension dim) throws LWJGLException {
        DisplayMode[] modes = Display.getAvailableDisplayModes();
        int i = 0;
        while (i < modes.length) {
            DisplayMode dm = modes[i];
            if (dm.getWidth() == dim.width && dm.getHeight() == dim.height && (desktopDisplayMode == null || dm.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && dm.getFrequency() == desktopDisplayMode.getFrequency())) {
                return dm;
            }
            ++i;
        }
        return desktopDisplayMode;
    }

    public static boolean isAnimatedTerrain() {
        return Config.gameSettings.ofAnimatedTerrain;
    }

    public static boolean isAnimatedItems() {
        return Config.gameSettings.ofAnimatedItems;
    }

    public static boolean isAnimatedTextures() {
        return Config.gameSettings.ofAnimatedTextures;
    }

    public static boolean isSwampColors() {
        return Config.gameSettings.ofSwampColors;
    }

    public static boolean isRandomMobs() {
        return Config.gameSettings.ofRandomMobs;
    }

    public static void checkGlError(String loc) {
        int i = GL11.glGetError();
        if (i != 0) {
            String text = GLU.gluErrorString((int)i);
            Config.dbg("OpenGlError: " + i + " (" + text + "), at: " + loc);
        }
    }

    public static boolean isSmoothBiomes() {
        return Config.gameSettings.ofSmoothBiomes;
    }

    public static boolean isCustomColors() {
        return Config.gameSettings.ofCustomColors;
    }

    public static boolean isCustomSky() {
        return Config.gameSettings.ofCustomSky;
    }

    public static boolean isCustomFonts() {
        return Config.gameSettings.ofCustomFonts;
    }

    public static boolean isShowCapes() {
        return Config.gameSettings.ofShowCapes;
    }

    public static boolean isConnectedTextures() {
        if (Config.gameSettings.ofConnectedTextures != 3) {
            return true;
        }
        return false;
    }

    public static boolean isNaturalTextures() {
        return Config.gameSettings.ofNaturalTextures;
    }

    public static boolean isConnectedTexturesFancy() {
        if (Config.gameSettings.ofConnectedTextures == 2) {
            return true;
        }
        return false;
    }

    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }

    public static boolean isTranslucentBlocksFancy() {
        if (Config.gameSettings.ofTranslucentBlocks == 2) {
            return true;
        }
        return false;
    }

    public static String[] readLines(File file) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader((InputStream)fis, "ASCII");
        BufferedReader br = new BufferedReader(isr);
        do {
            String lines;
            if ((lines = br.readLine()) == null) {
                String[] lines1 = list.toArray(new String[list.size()]);
                return lines1;
            }
            list.add(lines);
        } while (true);
    }

    public static String readFile(File file) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        return Config.readInputStream(fin, "ASCII");
    }

    public static String readInputStream(InputStream in) throws IOException {
        return Config.readInputStream(in, "ASCII");
    }

    public static String readInputStream(InputStream in, String encoding) throws IOException {
        InputStreamReader inr = new InputStreamReader(in, encoding);
        BufferedReader br = new BufferedReader(inr);
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static GameSettings getGameSettings() {
        return gameSettings;
    }

    public static String getNewRelease() {
        return newRelease;
    }

    public static void setNewRelease(String newRelease1) {
        newRelease = newRelease1;
    }

    public static int compareRelease(String rel1, String rel2) {
        String[] rels2;
        int rev2;
        String branch2;
        String[] rels1 = Config.splitRelease(rel1);
        String branch1 = rels1[0];
        if (!branch1.equals(branch2 = (rels2 = Config.splitRelease(rel2))[0])) {
            return branch1.compareTo(branch2);
        }
        int rev1 = Config.parseInt(rels1[1], -1);
        if (rev1 != (rev2 = Config.parseInt(rels2[1], -1))) {
            return rev1 - rev2;
        }
        String suf1 = rels1[2];
        String suf2 = rels2[2];
        return suf1.compareTo(suf2);
    }

    private static String[] splitRelease(String relStr) {
        if (relStr != null && relStr.length() > 0) {
            String branch = relStr.substring(0, 1);
            if (relStr.length() <= 1) {
                return new String[]{branch, "", ""};
            }
            int pos = 1;
            while (pos < relStr.length() && Character.isDigit(relStr.charAt(pos))) {
                ++pos;
            }
            String revision = relStr.substring(1, pos);
            if (pos >= relStr.length()) {
                return new String[]{branch, revision, ""};
            }
            String suffix = relStr.substring(pos);
            return new String[]{branch, revision, suffix};
        }
        return new String[]{"", "", ""};
    }

    public static int intHash(int x) {
        x = x ^ 61 ^ x >> 16;
        x += x << 3;
        x ^= x >> 4;
        x *= 668265261;
        x ^= x >> 15;
        return x;
    }

    public static int getRandom(int x, int y, int z, int face) {
        int rand = Config.intHash(face + 37);
        rand = Config.intHash(rand + x);
        rand = Config.intHash(rand + z);
        rand = Config.intHash(rand + y);
        return rand;
    }

    public static WorldServer getWorldServer() {
        if (minecraft == null) {
            return null;
        }
        WorldClient world = Config.minecraft.theWorld;
        if (world == null) {
            return null;
        }
        IntegratedServer is = minecraft.getIntegratedServer();
        if (is == null) {
            return null;
        }
        WorldProvider wp = world.provider;
        if (wp == null) {
            return null;
        }
        int wd = wp.dimensionId;
        WorldServer ws = is.worldServerForDimension(wd);
        return ws;
    }

    public static int getAvailableProcessors() {
        return availableProcessors;
    }

    public static void updateAvailableProcessors() {
        availableProcessors = Runtime.getRuntime().availableProcessors();
    }

    public static boolean isSingleProcessor() {
        if (Config.getAvailableProcessors() <= 1) {
            return true;
        }
        return false;
    }

    public static boolean isSmoothWorld() {
        return !Config.isSingleProcessor() ? false : Config.gameSettings.ofSmoothWorld;
    }

    public static boolean isLazyChunkLoading() {
        return !Config.isSingleProcessor() ? false : Config.gameSettings.ofLazyChunkLoading;
    }

    public static int getChunkViewDistance() {
        if (gameSettings == null) {
            return 10;
        }
        int chunkDistance = Config.gameSettings.renderDistanceChunks;
        return chunkDistance <= 16 ? 10 : chunkDistance;
    }

    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 ? true : (o1 == null ? false : o1.equals(o2));
    }

    public static void checkDisplaySettings() {
        if (Config.getAntialiasingLevel() > 0) {
            int samples = Config.getAntialiasingLevel();
            DisplayMode displayMode = Display.getDisplayMode();
            Config.dbg("FSAA Samples: " + samples);
            try {
                Display.destroy();
                Display.setDisplayMode((DisplayMode)displayMode);
                Display.create((PixelFormat)new PixelFormat().withDepthBits(24).withSamples(samples));
            }
            catch (LWJGLException var9) {
                Config.warn("Error setting FSAA: " + samples + "x");
                var9.printStackTrace();
                try {
                    Display.setDisplayMode((DisplayMode)displayMode);
                    Display.create((PixelFormat)new PixelFormat().withDepthBits(24));
                }
                catch (LWJGLException var8) {
                    var8.printStackTrace();
                    try {
                        Display.setDisplayMode((DisplayMode)displayMode);
                        Display.create();
                    }
                    catch (LWJGLException var7) {
                        var7.printStackTrace();
                    }
                }
            }
            if (Util.getOSType() != Util.EnumOS.MACOS) {
                try {
                    File e = new File(Config.minecraft.mcDataDir, "assets");
                    ByteBuffer bufIcon16 = Config.readIconImage(new File(e, "/icons/icon_16x16.png"));
                    ByteBuffer bufIcon32 = Config.readIconImage(new File(e, "/icons/icon_32x32.png"));
                    ByteBuffer[] buf = new ByteBuffer[]{bufIcon16, bufIcon32};
                    Display.setIcon((ByteBuffer[])buf);
                }
                catch (IOException var6) {
                    Config.dbg(String.valueOf(var6.getClass().getName()) + ": " + var6.getMessage());
                }
            }
        }
    }

    private static ByteBuffer readIconImage(File par1File) throws IOException {
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

    public static void checkDisplayMode() {
        try {
            if (minecraft.isFullScreen()) {
                if (fullscreenModeChecked) {
                    return;
                }
                fullscreenModeChecked = true;
                desktopModeChecked = false;
                DisplayMode e = Display.getDisplayMode();
                Dimension dim = Config.getFullscreenDimension();
                if (dim == null) {
                    return;
                }
                if (e.getWidth() == dim.width && e.getHeight() == dim.height) {
                    return;
                }
                DisplayMode newMode = Config.getDisplayMode(dim);
                if (newMode == null) {
                    return;
                }
                Display.setDisplayMode((DisplayMode)newMode);
                Config.minecraft.displayWidth = Display.getDisplayMode().getWidth();
                Config.minecraft.displayHeight = Display.getDisplayMode().getHeight();
                if (Config.minecraft.displayWidth <= 0) {
                    Config.minecraft.displayWidth = 1;
                }
                if (Config.minecraft.displayHeight <= 0) {
                    Config.minecraft.displayHeight = 1;
                }
                if (Config.minecraft.currentScreen != null) {
                    ScaledResolution sr = new ScaledResolution(Config.minecraft.gameSettings, Config.minecraft.displayWidth, Config.minecraft.displayHeight);
                    int sw = sr.getScaledWidth();
                    int sh = sr.getScaledHeight();
                    Config.minecraft.currentScreen.setWorldAndResolution(minecraft, sw, sh);
                }
                Config.minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
                Config.updateFramebufferSize();
                Display.setFullscreen((boolean)true);
                Config.minecraft.gameSettings.updateVSync();
                GL11.glEnable((int)3553);
            } else {
                if (desktopModeChecked) {
                    return;
                }
                desktopModeChecked = true;
                fullscreenModeChecked = false;
                Config.minecraft.gameSettings.updateVSync();
                Display.update();
                GL11.glEnable((int)3553);
            }
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    private static void updateFramebufferSize() {
        minecraft.getFramebuffer().createBindFramebuffer(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        if (Config.minecraft.entityRenderer != null) {
            Config.minecraft.entityRenderer.updateShaderGroupSize(Config.minecraft.displayWidth, Config.minecraft.displayHeight);
        }
    }

    public static Object[] addObjectToArray(Object[] arr, Object obj) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        int arrLen = arr.length;
        int newLen = arrLen + 1;
        Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        newArr[arrLen] = obj;
        return newArr;
    }

}

