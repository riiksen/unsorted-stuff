/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.ARBOcclusionQuery
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookGuiIngame;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityEnchantmentTableParticleFX;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.particle.EntityFishWakeFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityFootStepFX;
import net.minecraft.client.particle.EntityHeartFX;
import net.minecraft.client.particle.EntityHugeExplodeFX;
import net.minecraft.client.particle.EntityLargeExplodeFX;
import net.minecraft.client.particle.EntityLavaFX;
import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.particle.EntitySnowShovelFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.client.particle.EntitySuspendFX;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlCapsChecker;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.client.util.RenderDistanceSorter;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemRecord;
import net.minecraft.profiler.Profiler;
import net.minecraft.src.CompactArrayList;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.CustomSky;
import net.minecraft.src.EntitySorterFast;
import net.minecraft.src.RandomMobs;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.WrDisplayListAllocator;
import net.minecraft.src.WrUpdates;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal
implements IWorldAccess {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
    public List tileEntities = new ArrayList();
    public WorldClient theWorld;
    public final TextureManager renderEngine;
    public CompactArrayList worldRenderersToUpdate = new CompactArrayList(100, 0.8f);
    private WorldRenderer[] sortedWorldRenderers;
    private WorldRenderer[] worldRenderers;
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;
    public int glRenderListBase;
    public Minecraft mc;
    public RenderBlocks renderBlocksRg;
    private IntBuffer glOcclusionQueryBase;
    private boolean occlusionEnabled;
    private int cloudTickCounter;
    private int starGLCallList;
    private int glSkyList;
    private int glSkyList2;
    private int minBlockX;
    private int minBlockY;
    private int minBlockZ;
    private int maxBlockX;
    private int maxBlockY;
    private int maxBlockZ;
    public final Map damagedBlocks = new HashMap();
    private final Map mapSoundPositions = Maps.newHashMap();
    private IIcon[] destroyBlockIcons;
    private boolean displayListEntitiesDirty;
    private int displayListEntities;
    private int renderDistanceChunks = -1;
    private int renderEntitiesStartupCounter = 2;
    private int countEntitiesTotal;
    private int countEntitiesRendered;
    private int countEntitiesHidden;
    IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);
    private int renderersLoaded;
    private int renderersBeingClipped;
    private int renderersBeingOccluded;
    private int renderersBeingRendered;
    private int renderersSkippingRenderPass;
    private int dummyRenderInt;
    private int worldRenderersCheckIndex;
    private List glRenderLists = new ArrayList();
    private RenderList[] allRenderLists = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
    double prevSortX = -9999.0;
    double prevSortY = -9999.0;
    double prevSortZ = -9999.0;
    double prevRenderSortX = -9999.0;
    double prevRenderSortY = -9999.0;
    double prevRenderSortZ = -9999.0;
    int prevChunkSortX = -999;
    int prevChunkSortY = -999;
    int prevChunkSortZ = -999;
    int frustumCheckOffset;
    private IntBuffer glListBuffer = BufferUtils.createIntBuffer((int)65536);
    double prevReposX;
    double prevReposY;
    double prevReposZ;
    public Entity renderedEntity;
    private int glListClouds = GLAllocation.generateDisplayLists(1);
    private boolean isFancyGlListClouds = false;
    private int cloudTickCounterGlList = -99999;
    private double cloudPlayerX = 0.0;
    private double cloudPlayerY = 0.0;
    private double cloudPlayerZ = 0.0;
    private int countSortedWorldRenderers = 0;
    private int effectivePreloadedChunks = 0;
    private int vertexResortCounter = 30;
    public WrDisplayListAllocator displayListAllocator = new WrDisplayListAllocator();
    public EntityLivingBase renderViewEntity;
    private long lastMovedTime = System.currentTimeMillis();
    private long lastActionTime = System.currentTimeMillis();
    private static AxisAlignedBB AABB_INFINITE = AxisAlignedBB.getBoundingBox(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private static final String __OBFID = "CL_00000954";

    public RenderGlobal(Minecraft par1Minecraft) {
        int var9;
        this.mc = par1Minecraft;
        this.renderEngine = par1Minecraft.getTextureManager();
        int maxChunkDim = 65;
        int maxChunkHeight = 16;
        int countWorldRenderers = maxChunkDim * maxChunkDim * maxChunkHeight;
        int countStandardRenderLists = countWorldRenderers * 3;
        this.glRenderListBase = GLAllocation.generateDisplayLists(countStandardRenderLists);
        this.displayListEntitiesDirty = false;
        this.displayListEntities = GLAllocation.generateDisplayLists(1);
        this.occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
        if (this.occlusionEnabled) {
            this.occlusionResult.clear();
            this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(maxChunkDim * maxChunkDim * maxChunkHeight);
            this.glOcclusionQueryBase.clear();
            this.glOcclusionQueryBase.position(0);
            this.glOcclusionQueryBase.limit(maxChunkDim * maxChunkDim * maxChunkHeight);
            ARBOcclusionQuery.glGenQueriesARB((IntBuffer)this.glOcclusionQueryBase);
        }
        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList((int)this.starGLCallList, (int)4864);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator var4 = Tessellator.instance;
        this.glSkyList = this.starGLCallList + 1;
        GL11.glNewList((int)this.glSkyList, (int)4864);
        int var6 = 64;
        int var7 = 256 / var6 + 2;
        float var5 = 16.0f;
        int var8 = (- var6) * var7;
        while (var8 <= var6 * var7) {
            var9 = (- var6) * var7;
            while (var9 <= var6 * var7) {
                var4.startDrawingQuads();
                var4.addVertex(var8 + 0, var5, var9 + 0);
                var4.addVertex(var8 + var6, var5, var9 + 0);
                var4.addVertex(var8 + var6, var5, var9 + var6);
                var4.addVertex(var8 + 0, var5, var9 + var6);
                var4.draw();
                var9 += var6;
            }
            var8 += var6;
        }
        GL11.glEndList();
        this.glSkyList2 = this.starGLCallList + 2;
        GL11.glNewList((int)this.glSkyList2, (int)4864);
        var5 = -16.0f;
        var4.startDrawingQuads();
        var8 = (- var6) * var7;
        while (var8 <= var6 * var7) {
            var9 = (- var6) * var7;
            while (var9 <= var6 * var7) {
                var4.addVertex(var8 + var6, var5, var9 + 0);
                var4.addVertex(var8 + 0, var5, var9 + 0);
                var4.addVertex(var8 + 0, var5, var9 + var6);
                var4.addVertex(var8 + var6, var5, var9 + var6);
                var9 += var6;
            }
            var8 += var6;
        }
        var4.draw();
        GL11.glEndList();
    }

    private void renderStars() {
        Random var1 = new Random(10842);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        int var3 = 0;
        while (var3 < 1500) {
            double var4 = var1.nextFloat() * 2.0f - 1.0f;
            double var6 = var1.nextFloat() * 2.0f - 1.0f;
            double var8 = var1.nextFloat() * 2.0f - 1.0f;
            double var10 = 0.15f + var1.nextFloat() * 0.1f;
            double var12 = var4 * var4 + var6 * var6 + var8 * var8;
            if (var12 < 1.0 && var12 > 0.01) {
                var12 = 1.0 / Math.sqrt(var12);
                double var14 = (var4 *= var12) * 100.0;
                double var16 = (var6 *= var12) * 100.0;
                double var18 = (var8 *= var12) * 100.0;
                double var20 = Math.atan2(var4, var8);
                double var22 = Math.sin(var20);
                double var24 = Math.cos(var20);
                double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
                double var28 = Math.sin(var26);
                double var30 = Math.cos(var26);
                double var32 = var1.nextDouble() * 3.141592653589793 * 2.0;
                double var34 = Math.sin(var32);
                double var36 = Math.cos(var32);
                int var38 = 0;
                while (var38 < 4) {
                    double var39 = 0.0;
                    double var41 = (double)((var38 & 2) - 1) * var10;
                    double var43 = (double)((var38 + 1 & 2) - 1) * var10;
                    double var47 = var41 * var36 - var43 * var34;
                    double var49 = var43 * var36 + var41 * var34;
                    double var53 = var47 * var28 + var39 * var30;
                    double var55 = var39 * var28 - var47 * var30;
                    double var57 = var55 * var22 - var49 * var24;
                    double var61 = var49 * var22 + var55 * var24;
                    var2.addVertex(var14 + var57, var16 + var53, var18 + var61);
                    ++var38;
                }
            }
            ++var3;
        }
        var2.draw();
    }

    public void setWorldAndLoadRenderers(WorldClient par1WorldClient) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }
        this.prevSortX = -9999.0;
        this.prevSortY = -9999.0;
        this.prevSortZ = -9999.0;
        this.prevRenderSortX = -9999.0;
        this.prevRenderSortY = -9999.0;
        this.prevRenderSortZ = -9999.0;
        this.prevChunkSortX = -9999;
        this.prevChunkSortY = -9999;
        this.prevChunkSortZ = -9999;
        RenderManager.instance.set(par1WorldClient);
        this.theWorld = par1WorldClient;
        this.renderBlocksRg = new RenderBlocks(par1WorldClient);
        if (par1WorldClient != null) {
            par1WorldClient.addWorldAccess(this);
            this.loadRenderers();
        }
    }

    public void loadRenderers() {
        if (this.theWorld != null) {
            int numChunks;
            int var1;
            Blocks.leaves.func_150122_b(Config.isTreesFancy());
            Blocks.leaves2.func_150122_b(Config.isTreesFancy());
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            WrUpdates.clearAllUpdates();
            if (this.worldRenderers != null) {
                numChunks = 0;
                while (numChunks < this.worldRenderers.length) {
                    this.worldRenderers[numChunks].stopRendering();
                    ++numChunks;
                }
            }
            if ((numChunks = this.mc.gameSettings.renderDistanceChunks) > 10 && numChunks <= 16) {
                numChunks = 10;
            }
            int numChunksFar = 16;
            if (Config.isLoadChunksFar() && numChunks < numChunksFar) {
                numChunks = numChunksFar;
            }
            int maxPreloadedChunks = Config.limit(numChunksFar - numChunks, 0, 8);
            this.effectivePreloadedChunks = Config.limit(Config.getPreloadedChunks(), 0, maxPreloadedChunks);
            int limit = 32;
            if ((numChunks += this.effectivePreloadedChunks) > limit) {
                numChunks = limit;
            }
            this.prevReposX = -9999.0;
            this.prevReposY = -9999.0;
            this.prevReposZ = -9999.0;
            this.renderChunksWide = var1 = numChunks * 2 + 1;
            this.renderChunksTall = 16;
            this.renderChunksDeep = var1;
            this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            this.countSortedWorldRenderers = 0;
            this.displayListAllocator.resetAllocatedLists();
            int var2 = 0;
            int var3 = 0;
            this.minBlockX = 0;
            this.minBlockY = 0;
            this.minBlockZ = 0;
            this.maxBlockX = this.renderChunksWide;
            this.maxBlockY = this.renderChunksTall;
            this.maxBlockZ = this.renderChunksDeep;
            int var10 = 0;
            while (var10 < this.worldRenderersToUpdate.size()) {
                WorldRenderer esf = (WorldRenderer)this.worldRenderersToUpdate.get(var10);
                if (esf != null) {
                    esf.needsUpdate = false;
                }
                ++var10;
            }
            this.worldRenderersToUpdate.clear();
            this.tileEntities.clear();
            var10 = 0;
            while (var10 < this.renderChunksWide) {
                int var15 = 0;
                while (var15 < this.renderChunksTall) {
                    int cz = 0;
                    while (cz < this.renderChunksDeep) {
                        int wri = (cz * this.renderChunksTall + var15) * this.renderChunksWide + var10;
                        this.worldRenderers[wri] = WrUpdates.makeWorldRenderer(this.theWorld, this.tileEntities, var10 * 16, var15 * 16, cz * 16, this.glRenderListBase + var2);
                        if (this.occlusionEnabled) {
                            this.worldRenderers[wri].glOcclusionQuery = this.glOcclusionQueryBase.get(var3);
                        }
                        this.worldRenderers[wri].isWaitingOnOcclusionQuery = false;
                        this.worldRenderers[wri].isVisible = true;
                        this.worldRenderers[wri].isInFrustum = false;
                        this.worldRenderers[wri].chunkIndex = var3++;
                        if (this.theWorld.blockExists(var10 << 4, 0, cz << 4)) {
                            this.worldRenderers[wri].markDirty();
                            this.worldRenderersToUpdate.add(this.worldRenderers[wri]);
                        }
                        var2 += 3;
                        ++cz;
                    }
                    ++var15;
                }
                ++var10;
            }
            if (this.theWorld != null) {
                EntityLivingBase var14 = this.mc.renderViewEntity;
                if (var14 == null) {
                    var14 = this.mc.thePlayer;
                }
                if (var14 != null) {
                    this.markRenderersForNewPosition(MathHelper.floor_double(var14.posX), MathHelper.floor_double(var14.posY), MathHelper.floor_double(var14.posZ));
                    EntitySorterFast var13 = new EntitySorterFast(var14);
                    var13.prepareToSort(this.sortedWorldRenderers, this.countSortedWorldRenderers);
                    Arrays.sort(this.sortedWorldRenderers, 0, this.countSortedWorldRenderers, var13);
                }
            }
            this.renderEntitiesStartupCounter = 2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void renderEntities(EntityLivingBase p_147589_1_, ICamera p_147589_2_, float p_147589_3_) {
        pass = 0;
        if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
            pass = Reflector.callInt(Reflector.MinecraftForgeClient_getRenderPass, new Object[0]);
        }
        hasEntityShouldRenderInPass = Reflector.ForgeEntity_shouldRenderInPass.exists();
        hasTileEntityShouldRenderInPass = Reflector.ForgeTileEntity_shouldRenderInPass.exists();
        if (this.renderEntitiesStartupCounter > 0) {
            if (pass > 0) {
                return;
            }
            --this.renderEntitiesStartupCounter;
            return;
        }
        var4 = p_147589_1_.prevPosX + (p_147589_1_.posX - p_147589_1_.prevPosX) * (double)p_147589_3_;
        var6 = p_147589_1_.prevPosY + (p_147589_1_.posY - p_147589_1_.prevPosY) * (double)p_147589_3_;
        var8 = p_147589_1_.prevPosZ + (p_147589_1_.posZ - p_147589_1_.prevPosZ) * (double)p_147589_3_;
        this.theWorld.theProfiler.startSection("prepare");
        TileEntityRendererDispatcher.instance.func_147542_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, p_147589_3_);
        RenderManager.instance.func_147938_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.pointedEntity, this.mc.gameSettings, p_147589_3_);
        if (pass == 0) {
            this.countEntitiesTotal = 0;
            this.countEntitiesRendered = 0;
            this.countEntitiesHidden = 0;
            var17 = this.mc.renderViewEntity;
            var19 = var17.lastTickPosX + (var17.posX - var17.lastTickPosX) * (double)p_147589_3_;
            oldFancyGraphics = var17.lastTickPosY + (var17.posY - var17.lastTickPosY) * (double)p_147589_3_;
            aabb = var17.lastTickPosZ + (var17.posZ - var17.lastTickPosZ) * (double)p_147589_3_;
            TileEntityRendererDispatcher.staticPlayerX = var19;
            TileEntityRendererDispatcher.staticPlayerY = oldFancyGraphics;
            TileEntityRendererDispatcher.staticPlayerZ = aabb;
            this.theWorld.theProfiler.endStartSection("staticentities");
            if (this.displayListEntitiesDirty) {
                RenderManager.renderPosX = 0.0;
                RenderManager.renderPosY = 0.0;
                RenderManager.renderPosZ = 0.0;
                this.rebuildDisplayListEntities();
            }
            GL11.glMatrixMode((int)5888);
            GL11.glPushMatrix();
            GL11.glTranslated((double)(- var19), (double)(- oldFancyGraphics), (double)(- aabb));
            GL11.glCallList((int)this.displayListEntities);
            GL11.glPopMatrix();
            RenderManager.renderPosX = var19;
            RenderManager.renderPosY = oldFancyGraphics;
            RenderManager.renderPosZ = aabb;
        }
        this.mc.entityRenderer.enableLightmap(p_147589_3_);
        this.theWorld.theProfiler.endStartSection("global");
        var24 = this.theWorld.getLoadedEntityList();
        if (pass == 0) {
            this.countEntitiesTotal = var24.size();
        }
        if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GL11.glDisable((int)2912);
        }
        var18 = 0;
        while (var18 < this.theWorld.weatherEffects.size()) {
            var25 = (Entity)this.theWorld.weatherEffects.get(var18);
            if (!hasEntityShouldRenderInPass || Reflector.callBoolean(var25, Reflector.ForgeEntity_shouldRenderInPass, new Object[]{pass})) {
                ++this.countEntitiesRendered;
                if (var25.isInRangeToRender3d(var4, var6, var8)) {
                    RenderManager.instance.func_147937_a(var25, p_147589_3_);
                }
            }
            ++var18;
        }
        this.theWorld.theProfiler.endStartSection("entities");
        var26 = this.mc.gameSettings.fancyGraphics;
        this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
        var18 = 0;
        while (var18 < var24.size()) {
            block21 : {
                block22 : {
                    var25 = (Entity)var24.get(var18);
                    if (hasEntityShouldRenderInPass && !Reflector.callBoolean(var25, Reflector.ForgeEntity_shouldRenderInPass, new Object[]{pass})) break block21;
                    v0 = te = var25.isInRangeToRender3d(var4, var6, var8) != false && (var25.ignoreFrustumCheck != false || p_147589_2_.isBoundingBoxInFrustum(var25.boundingBox) != false || var25.riddenByEntity == this.mc.thePlayer);
                    if (te) break block22;
                    if (!(var25 instanceof EntityLiving)) break block21;
                    var30 = (EntityLiving)var25;
                    if (var30.getLeashed() && var30.getLeashedToEntity() != null) {
                        teClass = var30.getLeashedToEntity();
                        te = p_147589_2_.isBoundingBoxInFrustum(teClass.boundingBox);
                    }
                }
                if (te && (var25 != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView != 0 || this.mc.renderViewEntity.isPlayerSleeping()) && this.theWorld.blockExists(MathHelper.floor_double(var25.posX), 0, MathHelper.floor_double(var25.posZ))) {
                    ++this.countEntitiesRendered;
                    if (var25.getClass() == EntityItemFrame.class) {
                        var25.renderDistanceWeight = 0.06;
                    }
                    this.renderedEntity = var25;
                    RenderManager.instance.func_147937_a(var25, p_147589_3_);
                    this.renderedEntity = null;
                }
            }
            ++var18;
        }
        this.mc.gameSettings.fancyGraphics = var26;
        this.theWorld.theProfiler.endStartSection("blockentities");
        RenderHelper.enableStandardItemLighting();
        var18 = 0;
        while (var18 < this.tileEntities.size()) {
            block23 : {
                var27 = (TileEntity)this.tileEntities.get(var18);
                if (hasTileEntityShouldRenderInPass && !Reflector.callBoolean(var27, Reflector.ForgeTileEntity_shouldRenderInPass, new Object[]{pass}) || (var29 = this.getTileEntityBoundingBox(var27)) != RenderGlobal.AABB_INFINITE && !p_147589_2_.isBoundingBoxInFrustum(var29)) break block23;
                var28 = var27.getClass();
                if (var28 != TileEntitySign.class || Config.zoomMode) ** GOTO lbl-1000
                block = this.mc.thePlayer;
                distSq = var27.getDistanceFrom(block.posX, block.posY, block.posZ);
                if (distSq > 256.0) {
                    fr = TileEntityRendererDispatcher.instance.func_147548_a();
                    fr.enabled = false;
                    TileEntityRendererDispatcher.instance.func_147544_a(var27, p_147589_3_);
                    fr.enabled = true;
                } else if (var28 != TileEntityChest.class || (var31 = this.theWorld.getBlock(var27.field_145851_c, var27.field_145848_d, var27.field_145849_e)) instanceof BlockChest) {
                    TileEntityRendererDispatcher.instance.func_147544_a(var27, p_147589_3_);
                }
            }
            ++var18;
        }
        this.mc.entityRenderer.disableLightmap(p_147589_3_);
        this.theWorld.theProfiler.endSection();
    }

    public String getDebugInfoRenders() {
        return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
    }

    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersion();
    }

    @Override
    public void onStaticEntitiesChanged() {
        this.displayListEntitiesDirty = true;
    }

    public void rebuildDisplayListEntities() {
        this.theWorld.theProfiler.startSection("staticentityrebuild");
        GL11.glPushMatrix();
        GL11.glNewList((int)this.displayListEntities, (int)4864);
        List var1 = this.theWorld.getLoadedEntityList();
        this.displayListEntitiesDirty = false;
        int var2 = 0;
        while (var2 < var1.size()) {
            Entity var3 = (Entity)var1.get(var2);
            if (RenderManager.instance.getEntityRenderObject(var3).func_147905_a()) {
                this.displayListEntitiesDirty = this.displayListEntitiesDirty || !RenderManager.instance.func_147936_a(var3, 0.0f, true);
            }
            ++var2;
        }
        GL11.glEndList();
        GL11.glPopMatrix();
        this.theWorld.theProfiler.endSection();
    }

    private void markRenderersForNewPosition(int x, int y, int z) {
        x -= 8;
        y -= 8;
        z -= 8;
        this.minBlockX = Integer.MAX_VALUE;
        this.minBlockY = Integer.MAX_VALUE;
        this.minBlockZ = Integer.MAX_VALUE;
        this.maxBlockX = Integer.MIN_VALUE;
        this.maxBlockY = Integer.MIN_VALUE;
        this.maxBlockZ = Integer.MIN_VALUE;
        int blocksWide = this.renderChunksWide * 16;
        int blocksWide2 = blocksWide / 2;
        int ix = 0;
        while (ix < this.renderChunksWide) {
            int blockX = ix * 16;
            int blockXAbs = blockX + blocksWide2 - x;
            if (blockXAbs < 0) {
                blockXAbs -= blocksWide - 1;
            }
            if ((blockX -= (blockXAbs /= blocksWide) * blocksWide) < this.minBlockX) {
                this.minBlockX = blockX;
            }
            if (blockX > this.maxBlockX) {
                this.maxBlockX = blockX;
            }
            int iz = 0;
            while (iz < this.renderChunksDeep) {
                int blockZ = iz * 16;
                int blockZAbs = blockZ + blocksWide2 - z;
                if (blockZAbs < 0) {
                    blockZAbs -= blocksWide - 1;
                }
                if ((blockZ -= (blockZAbs /= blocksWide) * blocksWide) < this.minBlockZ) {
                    this.minBlockZ = blockZ;
                }
                if (blockZ > this.maxBlockZ) {
                    this.maxBlockZ = blockZ;
                }
                int iy = 0;
                while (iy < this.renderChunksTall) {
                    int blockY = iy * 16;
                    if (blockY < this.minBlockY) {
                        this.minBlockY = blockY;
                    }
                    if (blockY > this.maxBlockY) {
                        this.maxBlockY = blockY;
                    }
                    WorldRenderer worldrenderer = this.worldRenderers[(iz * this.renderChunksTall + iy) * this.renderChunksWide + ix];
                    boolean wasNeedingUpdate = worldrenderer.needsUpdate;
                    worldrenderer.setPosition(blockX, blockY, blockZ);
                    if (!wasNeedingUpdate && worldrenderer.needsUpdate) {
                        this.worldRenderersToUpdate.add(worldrenderer);
                    }
                    ++iy;
                }
                ++iz;
            }
            ++ix;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int sortAndRender(EntityLivingBase player, int renderPass, double partialTicks) {
        block31 : {
            block30 : {
                this.renderViewEntity = player;
                profiler = this.theWorld.theProfiler;
                profiler.startSection("sortchunks");
                if (this.worldRenderersToUpdate.size() < 10) {
                    shouldSort = 10;
                    num = 0;
                    while (num < shouldSort) {
                        this.worldRenderersCheckIndex = (this.worldRenderersCheckIndex + 1) % this.worldRenderers.length;
                        ocReq = this.worldRenderers[this.worldRenderersCheckIndex];
                        if (ocReq.needsUpdate && !this.worldRenderersToUpdate.contains(ocReq)) {
                            this.worldRenderersToUpdate.add(ocReq);
                        }
                        ++num;
                    }
                }
                if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks && !Config.isLoadChunksFar()) {
                    this.loadRenderers();
                }
                if (renderPass == 0) {
                    this.renderersLoaded = 0;
                    this.dummyRenderInt = 0;
                    this.renderersBeingClipped = 0;
                    this.renderersBeingOccluded = 0;
                    this.renderersBeingRendered = 0;
                    this.renderersSkippingRenderPass = 0;
                }
                v0 = var33 = this.prevChunkSortX != player.chunkCoordX || this.prevChunkSortY != player.chunkCoordY || this.prevChunkSortZ != player.chunkCoordZ;
                if (!var33) {
                    var34 = player.posX - this.prevSortX;
                    partialX = player.posY - this.prevSortY;
                    partialY = player.posZ - this.prevSortZ;
                    partialZ = var34 * var34 + partialX * partialX + partialY * partialY;
                    v1 = var33 = partialZ > 16.0;
                }
                if (var33) {
                    this.prevChunkSortX = player.chunkCoordX;
                    this.prevChunkSortY = player.chunkCoordY;
                    this.prevChunkSortZ = player.chunkCoordZ;
                    this.prevSortX = player.posX;
                    this.prevSortY = player.posY;
                    this.prevSortZ = player.posZ;
                    var37 = player.posX - this.prevReposX;
                    dReposY = player.posY - this.prevReposY;
                    dReposZ = player.posZ - this.prevReposZ;
                    countResort = var37 * var37 + dReposY * dReposY + dReposZ * dReposZ;
                    num = this.effectivePreloadedChunks * 16;
                    if (countResort > (double)(num * num) + 16.0) {
                        this.prevReposX = player.posX;
                        this.prevReposY = player.posY;
                        this.prevReposZ = player.posZ;
                        this.markRenderersForNewPosition(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
                    }
                    lastIndex = new EntitySorterFast(player);
                    lastIndex.prepareToSort(this.sortedWorldRenderers, this.countSortedWorldRenderers);
                    Arrays.sort(this.sortedWorldRenderers, 0, this.countSortedWorldRenderers, lastIndex);
                    if (Config.isFastRender()) {
                        endIndex = (int)player.posX;
                        stepNum = (int)player.posZ;
                        step = 2000;
                        if (Math.abs(endIndex - WorldRenderer.globalChunkOffsetX) > step || Math.abs(stepNum - WorldRenderer.globalChunkOffsetZ) > step) {
                            WorldRenderer.globalChunkOffsetX = endIndex;
                            WorldRenderer.globalChunkOffsetZ = stepNum;
                            this.loadRenderers();
                        }
                    }
                }
                if (Config.isTranslucentBlocksFancy()) {
                    var34 = player.posX - this.prevRenderSortX;
                    partialX = player.posY - this.prevRenderSortY;
                    partialY = player.posZ - this.prevRenderSortZ;
                    var39 = Math.min(27, this.countSortedWorldRenderers);
                    if (var34 * var34 + partialX * partialX + partialY * partialY > 1.0) {
                        this.prevRenderSortX = player.posX;
                        this.prevRenderSortY = player.posY;
                        this.prevRenderSortZ = player.posZ;
                        var38 = 0;
                        while (var38 < var39) {
                            firstIndex = this.sortedWorldRenderers[var38];
                            if (firstIndex.vertexState != null && firstIndex.sortDistanceToEntitySquared < 1152.0f) {
                                firstIndex.needVertexStateResort = true;
                                if (this.vertexResortCounter > var38) {
                                    this.vertexResortCounter = var38;
                                }
                            }
                            ++var38;
                        }
                    }
                    if (this.vertexResortCounter < var39) {
                        while (this.vertexResortCounter < var39) {
                            firstIndex = this.sortedWorldRenderers[this.vertexResortCounter];
                            ++this.vertexResortCounter;
                            if (!firstIndex.needVertexStateResort) continue;
                            firstIndex.updateRendererSort(player);
                            break;
                        }
                    }
                }
                RenderHelper.disableStandardItemLighting();
                WrUpdates.preRender(this, player);
                if (this.mc.gameSettings.ofSmoothFps && renderPass == 0) {
                    GL11.glFinish();
                }
                var36 = 0;
                var35 = 0;
                if (!this.occlusionEnabled || !this.mc.gameSettings.advancedOpengl || this.mc.gameSettings.anaglyph || renderPass != 0) break block30;
                partialX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
                partialY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
                partialZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
                var41 = 0;
                var40 = Math.min(20, this.countSortedWorldRenderers);
                this.checkOcclusionQueryResult(var41, var40, player.posX, player.posY, player.posZ);
                endIndex = var41;
                ** GOTO lbl104
            }
            profiler.endStartSection("render");
            num = var36 + this.renderSortedRenderers(0, this.countSortedWorldRenderers, renderPass, partialTicks);
            break block31;
lbl-1000: // 1 sources:
            {
                this.sortedWorldRenderers[endIndex].isVisible = true;
                ++endIndex;
lbl104: // 2 sources:
                ** while (endIndex < var40)
            }
lbl105: // 1 sources:
            profiler.endStartSection("render");
            num = var36 + this.renderSortedRenderers(var41, var40, renderPass, partialTicks);
            endIndex = var40;
            stepNum = 0;
            var42 = 40;
            switchStep = this.renderChunksWide;
            while (endIndex < this.countSortedWorldRenderers) {
                profiler.endStartSection("occ");
                startIndex = endIndex;
                stepNum = stepNum < switchStep ? ++stepNum : --stepNum;
                if ((endIndex += stepNum * var42) <= startIndex) {
                    endIndex = startIndex + 10;
                }
                if (endIndex > this.countSortedWorldRenderers) {
                    endIndex = this.countSortedWorldRenderers;
                }
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)3008);
                GL11.glDisable((int)2912);
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                GL11.glDepthMask((boolean)false);
                profiler.startSection("check");
                this.checkOcclusionQueryResult(startIndex, endIndex, player.posX, player.posY, player.posZ);
                profiler.endSection();
                GL11.glPushMatrix();
                sumTX = 0.0f;
                sumTY = 0.0f;
                sumTZ = 0.0f;
                k = startIndex;
                while (k < endIndex) {
                    block33 : {
                        block34 : {
                            block35 : {
                                block32 : {
                                    wr = this.sortedWorldRenderers[k];
                                    if (!wr.skipAllRenderPasses()) break block32;
                                    wr.isInFrustum = false;
                                    break block33;
                                }
                                if (wr.isUpdating || wr.needsBoxUpdate) break block34;
                                if (!wr.isInFrustum) break block33;
                                if (!Config.isOcclusionFancy() || wr.isInFrustrumFully) break block35;
                                wr.isVisible = true;
                                break block33;
                            }
                            if (!wr.isInFrustum || wr.isWaitingOnOcclusionQuery) break block33;
                            if (!wr.isVisibleFromPosition) ** GOTO lbl153
                            bbX = Math.abs((float)(wr.visibleFromX - player.posX));
                            tX = bbX + (bbY = Math.abs((float)(wr.visibleFromY - player.posY))) + (bbZ = Math.abs((float)(wr.visibleFromZ - player.posZ)));
                            if ((double)tX < 10.0 + (double)k / 1000.0) {
                                wr.isVisible = true;
                            } else {
                                wr.isVisibleFromPosition = false;
lbl153: // 2 sources:
                                bbX = (float)((double)wr.posXMinus - partialX);
                                bbY = (float)((double)wr.posYMinus - partialY);
                                bbZ = (float)((double)wr.posZMinus - partialZ);
                                tX = bbX - sumTX;
                                tY = bbY - sumTY;
                                tZ = bbZ - sumTZ;
                                if (tX != 0.0f || tY != 0.0f || tZ != 0.0f) {
                                    GL11.glTranslatef((float)tX, (float)tY, (float)tZ);
                                    sumTX += tX;
                                    sumTY += tY;
                                    sumTZ += tZ;
                                }
                                profiler.startSection("bb");
                                ARBOcclusionQuery.glBeginQueryARB((int)35092, (int)wr.glOcclusionQuery);
                                wr.callOcclusionQueryList();
                                ARBOcclusionQuery.glEndQueryARB((int)35092);
                                profiler.endSection();
                                wr.isWaitingOnOcclusionQuery = true;
                                ++var35;
                            }
                            break block33;
                        }
                        wr.isVisible = true;
                    }
                    ++k;
                }
                GL11.glPopMatrix();
                if (this.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)true);
                    } else {
                        GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)true);
                    }
                } else {
                    GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                }
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)2912);
                profiler.endStartSection("render");
                num += this.renderSortedRenderers(startIndex, endIndex, renderPass, partialTicks);
            }
        }
        profiler.endSection();
        WrUpdates.postRender();
        return num;
    }

    private void checkOcclusionQueryResult(int startIndex, int endIndex, double px, double py, double pz) {
        int k = startIndex;
        while (k < endIndex) {
            WorldRenderer wr = this.sortedWorldRenderers[k];
            if (wr.isWaitingOnOcclusionQuery) {
                this.occlusionResult.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB((int)wr.glOcclusionQuery, (int)34919, (IntBuffer)this.occlusionResult);
                if (this.occlusionResult.get(0) != 0) {
                    wr.isWaitingOnOcclusionQuery = false;
                    this.occlusionResult.clear();
                    ARBOcclusionQuery.glGetQueryObjectuARB((int)wr.glOcclusionQuery, (int)34918, (IntBuffer)this.occlusionResult);
                    if (!wr.isUpdating && !wr.needsBoxUpdate) {
                        boolean wasVisible = wr.isVisible;
                        boolean bl = wr.isVisible = this.occlusionResult.get(0) > 0;
                        if (wasVisible && wr.isVisible) {
                            wr.isVisibleFromPosition = true;
                            wr.visibleFromX = px;
                            wr.visibleFromY = py;
                            wr.visibleFromZ = pz;
                        }
                    } else {
                        wr.isVisible = true;
                    }
                }
            }
            ++k;
        }
    }

    private int renderSortedRenderers(int par1, int par2, int par3, double par4) {
        if (Config.isFastRender()) {
            return this.renderSortedRenderersFast(par1, par2, par3, par4);
        }
        this.glRenderLists.clear();
        int var6 = 0;
        int var7 = par1;
        int var8 = par2;
        int var9 = 1;
        if (par3 == 1) {
            var7 = this.countSortedWorldRenderers - 1 - par1;
            var8 = this.countSortedWorldRenderers - 1 - par2;
            var9 = -1;
        }
        int var23 = var7;
        while (var23 != var8) {
            int var22;
            if (par3 == 0) {
                ++this.renderersLoaded;
                if (this.sortedWorldRenderers[var23].skipRenderPass[par3]) {
                    ++this.renderersSkippingRenderPass;
                } else if (!this.sortedWorldRenderers[var23].isInFrustum) {
                    ++this.renderersBeingClipped;
                } else if (this.occlusionEnabled && !this.sortedWorldRenderers[var23].isVisible) {
                    ++this.renderersBeingOccluded;
                } else {
                    ++this.renderersBeingRendered;
                }
            }
            if (!this.sortedWorldRenderers[var23].skipRenderPass[par3] && this.sortedWorldRenderers[var23].isInFrustum && (!this.occlusionEnabled || this.sortedWorldRenderers[var23].isVisible) && (var22 = this.sortedWorldRenderers[var23].getGLCallListForPass(par3)) >= 0) {
                this.glRenderLists.add(this.sortedWorldRenderers[var23]);
                ++var6;
            }
            var23 += var9;
        }
        EntityLivingBase var231 = this.mc.renderViewEntity;
        double var221 = var231.lastTickPosX + (var231.posX - var231.lastTickPosX) * par4;
        double var13 = var231.lastTickPosY + (var231.posY - var231.lastTickPosY) * par4;
        double var15 = var231.lastTickPosZ + (var231.posZ - var231.lastTickPosZ) * par4;
        int var17 = 0;
        int var18 = 0;
        while (var18 < this.allRenderLists.length) {
            this.allRenderLists[var18].resetList();
            ++var18;
        }
        var18 = 0;
        while (var18 < this.glRenderLists.size()) {
            WorldRenderer var19 = (WorldRenderer)this.glRenderLists.get(var18);
            int var20 = -1;
            int var21 = 0;
            while (var21 < var17) {
                if (this.allRenderLists[var21].rendersChunk(var19.posXMinus, var19.posYMinus, var19.posZMinus)) {
                    var20 = var21;
                }
                ++var21;
            }
            if (var20 < 0) {
                var20 = var17++;
                this.allRenderLists[var20].setupRenderList(var19.posXMinus, var19.posYMinus, var19.posZMinus, var221, var13, var15);
            }
            this.allRenderLists[var20].addGLRenderList(var19.getGLCallListForPass(par3));
            ++var18;
        }
        if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GL11.glDisable((int)2912);
        }
        Arrays.sort(this.allRenderLists, new RenderDistanceSorter());
        this.renderAllRenderLists(par3, par4);
        return var6;
    }

    private int renderSortedRenderersFast(int startIndex, int endIndex, int renderPass, double partialTicks) {
        this.glListBuffer.clear();
        int l = 0;
        boolean debug = this.mc.gameSettings.showDebugInfo;
        int loopStart = startIndex;
        int loopEnd = endIndex;
        int loopInc = 1;
        if (renderPass == 1) {
            loopStart = this.countSortedWorldRenderers - 1 - startIndex;
            loopEnd = this.countSortedWorldRenderers - 1 - endIndex;
            loopInc = -1;
        }
        int entityliving = loopStart;
        while (entityliving != loopEnd) {
            int glCallList;
            WorldRenderer partialX = this.sortedWorldRenderers[entityliving];
            if (debug && renderPass == 0) {
                ++this.renderersLoaded;
                if (partialX.skipRenderPass[renderPass]) {
                    ++this.renderersSkippingRenderPass;
                } else if (!partialX.isInFrustum) {
                    ++this.renderersBeingClipped;
                } else if (this.occlusionEnabled && !partialX.isVisible) {
                    ++this.renderersBeingOccluded;
                } else {
                    ++this.renderersBeingRendered;
                }
            }
            if (partialX.isInFrustum && !partialX.skipRenderPass[renderPass] && (!this.occlusionEnabled || partialX.isVisible) && (glCallList = partialX.getGLCallListForPass(renderPass)) >= 0) {
                this.glListBuffer.put(glCallList);
                ++l;
            }
            entityliving += loopInc;
        }
        if (l == 0) {
            return 0;
        }
        if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GL11.glDisable((int)2912);
        }
        this.glListBuffer.flip();
        EntityLivingBase var19 = this.mc.renderViewEntity;
        double var18 = var19.lastTickPosX + (var19.posX - var19.lastTickPosX) * partialTicks - (double)WorldRenderer.globalChunkOffsetX;
        double partialY = var19.lastTickPosY + (var19.posY - var19.lastTickPosY) * partialTicks;
        double partialZ = var19.lastTickPosZ + (var19.posZ - var19.lastTickPosZ) * partialTicks - (double)WorldRenderer.globalChunkOffsetZ;
        this.mc.entityRenderer.enableLightmap(partialTicks);
        GL11.glTranslatef((float)((float)(- var18)), (float)((float)(- partialY)), (float)((float)(- partialZ)));
        GL11.glCallLists((IntBuffer)this.glListBuffer);
        GL11.glTranslatef((float)((float)var18), (float)((float)partialY), (float)((float)partialZ));
        this.mc.entityRenderer.disableLightmap(partialTicks);
        return l;
    }

    public void renderAllRenderLists(int par1, double par2) {
        this.mc.entityRenderer.enableLightmap(par2);
        int var4 = 0;
        while (var4 < this.allRenderLists.length) {
            this.allRenderLists[var4].callLists();
            ++var4;
        }
        this.mc.entityRenderer.disableLightmap(par2);
    }

    public void updateClouds() {
        ++this.cloudTickCounter;
        if (this.cloudTickCounter % 20 == 0) {
            Iterator var1 = this.damagedBlocks.values().iterator();
            while (var1.hasNext()) {
                DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
                int var3 = var2.getCreationCloudUpdateTick();
                if (this.cloudTickCounter - var3 <= 400) continue;
                var1.remove();
            }
        }
    }

    public void renderSky(float par1) {
        WorldProvider var2;
        Object var3;
        if (Reflector.ForgeWorldProvider_getSkyRenderer.exists() && (var3 = Reflector.call(var2 = this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0])) != null) {
            Reflector.callVoid(var3, Reflector.IRenderHandler_render, Float.valueOf(par1), this.theWorld, this.mc);
            return;
        }
        if (this.mc.theWorld.provider.dimensionId == 1) {
            if (!Config.isSkyEnabled()) {
                return;
            }
            GL11.glDisable((int)2912);
            GL11.glDisable((int)3008);
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthMask((boolean)false);
            this.renderEngine.bindTexture(locationEndSkyPng);
            Tessellator var201 = Tessellator.instance;
            int var22 = 0;
            while (var22 < 6) {
                GL11.glPushMatrix();
                if (var22 == 1) {
                    GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                }
                if (var22 == 2) {
                    GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                }
                if (var22 == 3) {
                    GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                }
                if (var22 == 4) {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (var22 == 5) {
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                }
                var201.startDrawingQuads();
                var201.setColorOpaque_I(2631720);
                var201.addVertexWithUV(-100.0, -100.0, -100.0, 0.0, 0.0);
                var201.addVertexWithUV(-100.0, -100.0, 100.0, 0.0, 16.0);
                var201.addVertexWithUV(100.0, -100.0, 100.0, 16.0, 16.0);
                var201.addVertexWithUV(100.0, -100.0, -100.0, 16.0, 0.0);
                var201.draw();
                GL11.glPopMatrix();
                ++var22;
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3008);
        } else if (this.mc.theWorld.provider.isSurfaceWorld()) {
            float var10;
            float var9;
            float var17;
            float var20;
            float var8;
            int var29;
            float var16;
            float var12;
            float var11;
            GL11.glDisable((int)3553);
            Vec3 var21 = this.theWorld.getSkyColor(this.mc.renderViewEntity, par1);
            var21 = CustomColorizer.getSkyColor(var21, this.mc.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0, this.mc.renderViewEntity.posZ);
            float var231 = (float)var21.xCoord;
            float var4 = (float)var21.yCoord;
            float var5 = (float)var21.zCoord;
            if (this.mc.gameSettings.anaglyph) {
                float var23 = (var231 * 30.0f + var4 * 59.0f + var5 * 11.0f) / 100.0f;
                float var24 = (var231 * 30.0f + var4 * 70.0f) / 100.0f;
                var8 = (var231 * 30.0f + var5 * 70.0f) / 100.0f;
                var231 = var23;
                var4 = var24;
                var5 = var8;
            }
            GL11.glColor3f((float)var231, (float)var4, (float)var5);
            Tessellator var241 = Tessellator.instance;
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2912);
            GL11.glColor3f((float)var231, (float)var4, (float)var5);
            if (Config.isSkyEnabled()) {
                GL11.glCallList((int)this.glSkyList);
            }
            GL11.glDisable((int)2912);
            GL11.glDisable((int)3008);
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            float[] var251 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(par1), par1);
            if (var251 != null && Config.isSunMoonEnabled()) {
                GL11.glDisable((int)3553);
                GL11.glShadeModel((int)7425);
                GL11.glPushMatrix();
                GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(MathHelper.sin(this.theWorld.getCelestialAngleRadians(par1)) < 0.0f ? 180.0f : 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                var8 = var251[0];
                var9 = var251[1];
                var10 = var251[2];
                if (this.mc.gameSettings.anaglyph) {
                    var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
                    var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
                    var20 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
                    var8 = var11;
                    var9 = var12;
                    var10 = var20;
                }
                var241.startDrawing(6);
                var241.setColorRGBA_F(var8, var9, var10, var251[3]);
                var241.addVertex(0.0, 100.0, 0.0);
                int var25 = 16;
                var241.setColorRGBA_F(var251[0], var251[1], var251[2], 0.0f);
                var29 = 0;
                while (var29 <= var25) {
                    var20 = (float)var29 * 3.1415927f * 2.0f / (float)var25;
                    var16 = MathHelper.sin(var20);
                    var17 = MathHelper.cos(var20);
                    var241.addVertex(var16 * 120.0f, var17 * 120.0f, (- var17) * 40.0f * var251[3]);
                    ++var29;
                }
                var241.draw();
                GL11.glPopMatrix();
                GL11.glShadeModel((int)7424);
            }
            GL11.glEnable((int)3553);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            GL11.glPushMatrix();
            var8 = 1.0f - this.theWorld.getRainStrength(par1);
            var9 = 0.0f;
            var10 = 0.0f;
            var11 = 0.0f;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)var8);
            GL11.glTranslatef((float)var9, (float)var10, (float)var11);
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(par1), var8);
            GL11.glRotatef((float)(this.theWorld.getCelestialAngle(par1) * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            if (Config.isSunMoonEnabled()) {
                var12 = 30.0f;
                this.renderEngine.bindTexture(locationSunPng);
                var241.startDrawingQuads();
                var241.addVertexWithUV(- var12, 100.0, - var12, 0.0, 0.0);
                var241.addVertexWithUV(var12, 100.0, - var12, 1.0, 0.0);
                var241.addVertexWithUV(var12, 100.0, var12, 1.0, 1.0);
                var241.addVertexWithUV(- var12, 100.0, var12, 0.0, 1.0);
                var241.draw();
                var12 = 20.0f;
                this.renderEngine.bindTexture(locationMoonPhasesPng);
                int var26 = this.theWorld.getMoonPhase();
                int var27 = var26 % 4;
                var29 = var26 / 4 % 2;
                var16 = (float)(var27 + 0) / 4.0f;
                var17 = (float)(var29 + 0) / 2.0f;
                float var18 = (float)(var27 + 1) / 4.0f;
                float var19 = (float)(var29 + 1) / 2.0f;
                var241.startDrawingQuads();
                var241.addVertexWithUV(- var12, -100.0, var12, var18, var19);
                var241.addVertexWithUV(var12, -100.0, var12, var16, var19);
                var241.addVertexWithUV(var12, -100.0, - var12, var16, var17);
                var241.addVertexWithUV(- var12, -100.0, - var12, var18, var17);
                var241.draw();
            }
            GL11.glDisable((int)3553);
            var20 = this.theWorld.getStarBrightness(par1) * var8;
            if (var20 > 0.0f && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
                GL11.glColor4f((float)var20, (float)var20, (float)var20, (float)var20);
                GL11.glCallList((int)this.starGLCallList);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glEnable((int)2912);
            GL11.glPopMatrix();
            GL11.glDisable((int)3553);
            GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
            double var28 = this.mc.thePlayer.getPosition((float)par1).yCoord - this.theWorld.getHorizon();
            if (var28 < 0.0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)0.0f, (float)12.0f, (float)0.0f);
                GL11.glCallList((int)this.glSkyList2);
                GL11.glPopMatrix();
                var10 = 1.0f;
                var11 = - (float)(var28 + 65.0);
                var12 = - var10;
                var241.startDrawingQuads();
                var241.setColorRGBA_I(0, 255);
                var241.addVertex(- var10, var11, var10);
                var241.addVertex(var10, var11, var10);
                var241.addVertex(var10, var12, var10);
                var241.addVertex(- var10, var12, var10);
                var241.addVertex(- var10, var12, - var10);
                var241.addVertex(var10, var12, - var10);
                var241.addVertex(var10, var11, - var10);
                var241.addVertex(- var10, var11, - var10);
                var241.addVertex(var10, var12, - var10);
                var241.addVertex(var10, var12, var10);
                var241.addVertex(var10, var11, var10);
                var241.addVertex(var10, var11, - var10);
                var241.addVertex(- var10, var11, - var10);
                var241.addVertex(- var10, var11, var10);
                var241.addVertex(- var10, var12, var10);
                var241.addVertex(- var10, var12, - var10);
                var241.addVertex(- var10, var12, - var10);
                var241.addVertex(- var10, var12, var10);
                var241.addVertex(var10, var12, var10);
                var241.addVertex(var10, var12, - var10);
                var241.draw();
            }
            if (this.theWorld.provider.isSkyColored()) {
                GL11.glColor3f((float)(var231 * 0.2f + 0.04f), (float)(var4 * 0.2f + 0.04f), (float)(var5 * 0.6f + 0.1f));
            } else {
                GL11.glColor3f((float)var231, (float)var4, (float)var5);
            }
            if (this.mc.gameSettings.renderDistanceChunks <= 4) {
                GL11.glColor3f((float)this.mc.entityRenderer.fogColorRed, (float)this.mc.entityRenderer.fogColorGreen, (float)this.mc.entityRenderer.fogColorBlue);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)(- (float)(var28 - 16.0)), (float)0.0f);
            if (Config.isSkyEnabled()) {
                GL11.glCallList((int)this.glSkyList2);
            }
            GL11.glPopMatrix();
            GL11.glEnable((int)3553);
            GL11.glDepthMask((boolean)true);
        }
    }

    public void renderClouds(float par1) {
        if (!Config.isCloudsOff()) {
            WorldProvider partialTicks;
            Object var2;
            if (Reflector.ForgeWorldProvider_getCloudRenderer.exists() && (var2 = Reflector.call(partialTicks = this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0])) != null) {
                Reflector.callVoid(var2, Reflector.IRenderHandler_render, Float.valueOf(par1), this.theWorld, this.mc);
                return;
            }
            if (this.mc.theWorld.provider.isSurfaceWorld()) {
                if (Config.isCloudsFancy()) {
                    this.renderCloudsFancy(par1);
                } else {
                    double dc;
                    double exactPlayerZ1;
                    float partialTicks1 = par1;
                    par1 = 0.0f;
                    GL11.glDisable((int)2884);
                    float var21 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)par1);
                    int var3 = 32;
                    int var4 = 256 / var3;
                    Tessellator var5 = Tessellator.instance;
                    this.renderEngine.bindTexture(locationCloudsPng);
                    GL11.glEnable((int)3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    if (this.isFancyGlListClouds || this.cloudTickCounter >= this.cloudTickCounterGlList + 20) {
                        float var10;
                        GL11.glNewList((int)this.glListClouds, (int)4864);
                        Vec3 entityliving = this.theWorld.getCloudColour(par1);
                        float exactPlayerX = (float)entityliving.xCoord;
                        float var8 = (float)entityliving.yCoord;
                        float exactPlayerY = (float)entityliving.zCoord;
                        if (this.mc.gameSettings.anaglyph) {
                            var10 = (exactPlayerX * 30.0f + var8 * 59.0f + exactPlayerY * 11.0f) / 100.0f;
                            float exactPlayerZ = (exactPlayerX * 30.0f + var8 * 70.0f) / 100.0f;
                            float var12 = (exactPlayerX * 30.0f + exactPlayerY * 70.0f) / 100.0f;
                            exactPlayerX = var10;
                            var8 = exactPlayerZ;
                            exactPlayerY = var12;
                        }
                        var10 = 4.8828125E-4f;
                        exactPlayerZ1 = (float)this.cloudTickCounter + par1;
                        dc = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)par1 + exactPlayerZ1 * 0.029999999329447746;
                        double cdx = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)par1;
                        int cdz = MathHelper.floor_double(dc / 2048.0);
                        int var18 = MathHelper.floor_double(cdx / 2048.0);
                        float var19 = this.theWorld.provider.getCloudHeight() - var21 + 0.33f;
                        var19 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
                        float var20 = (float)((dc -= (double)(cdz * 2048)) * (double)var10);
                        float var211 = (float)((cdx -= (double)(var18 * 2048)) * (double)var10);
                        var5.startDrawingQuads();
                        var5.setColorRGBA_F(exactPlayerX, var8, exactPlayerY, 0.8f);
                        int var22 = (- var3) * var4;
                        while (var22 < var3 * var4) {
                            int var23 = (- var3) * var4;
                            while (var23 < var3 * var4) {
                                var5.addVertexWithUV(var22 + 0, var19, var23 + var3, (float)(var22 + 0) * var10 + var20, (float)(var23 + var3) * var10 + var211);
                                var5.addVertexWithUV(var22 + var3, var19, var23 + var3, (float)(var22 + var3) * var10 + var20, (float)(var23 + var3) * var10 + var211);
                                var5.addVertexWithUV(var22 + var3, var19, var23 + 0, (float)(var22 + var3) * var10 + var20, (float)(var23 + 0) * var10 + var211);
                                var5.addVertexWithUV(var22 + 0, var19, var23 + 0, (float)(var22 + 0) * var10 + var20, (float)(var23 + 0) * var10 + var211);
                                var23 += var3;
                            }
                            var22 += var3;
                        }
                        var5.draw();
                        GL11.glEndList();
                        this.isFancyGlListClouds = false;
                        this.cloudTickCounterGlList = this.cloudTickCounter;
                        this.cloudPlayerX = this.mc.renderViewEntity.prevPosX;
                        this.cloudPlayerY = this.mc.renderViewEntity.prevPosY;
                        this.cloudPlayerZ = this.mc.renderViewEntity.prevPosZ;
                    }
                    EntityLivingBase entityliving1 = this.mc.renderViewEntity;
                    double exactPlayerX1 = entityliving1.prevPosX + (entityliving1.posX - entityliving1.prevPosX) * (double)partialTicks1;
                    double exactPlayerY1 = entityliving1.prevPosY + (entityliving1.posY - entityliving1.prevPosY) * (double)partialTicks1;
                    exactPlayerZ1 = entityliving1.prevPosZ + (entityliving1.posZ - entityliving1.prevPosZ) * (double)partialTicks1;
                    dc = (float)(this.cloudTickCounter - this.cloudTickCounterGlList) + partialTicks1;
                    float cdx1 = (float)(exactPlayerX1 - this.cloudPlayerX + dc * 0.03);
                    float cdy = (float)(exactPlayerY1 - this.cloudPlayerY);
                    float cdz1 = (float)(exactPlayerZ1 - this.cloudPlayerZ);
                    GL11.glTranslatef((float)(- cdx1), (float)(- cdy), (float)(- cdz1));
                    GL11.glCallList((int)this.glListClouds);
                    GL11.glTranslatef((float)cdx1, (float)cdy, (float)cdz1);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)2884);
                }
            }
        }
    }

    public boolean hasCloudFog(double par1, double par3, double par5, float par7) {
        return false;
    }

    public void renderCloudsFancy(float par1) {
        float cdz;
        float partialTicks = par1;
        par1 = 0.0f;
        GL11.glDisable((int)2884);
        float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)par1);
        Tessellator var3 = Tessellator.instance;
        float var4 = 12.0f;
        float var5 = 4.0f;
        double var6 = (float)this.cloudTickCounter + par1;
        double var8 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)par1 + var6 * 0.029999999329447746) / (double)var4;
        double var10 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)par1) / (double)var4 + 0.33000001311302185;
        float var12 = this.theWorld.provider.getCloudHeight() - var2 + 0.33f;
        var12 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
        int var13 = MathHelper.floor_double(var8 / 2048.0);
        int var14 = MathHelper.floor_double(var10 / 2048.0);
        var8 -= (double)(var13 * 2048);
        var10 -= (double)(var14 * 2048);
        this.renderEngine.bindTexture(locationCloudsPng);
        GL11.glEnable((int)3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        if (!this.isFancyGlListClouds || this.cloudTickCounter >= this.cloudTickCounterGlList + 20) {
            float exactPlayerZ;
            float var20;
            float var19;
            GL11.glNewList((int)this.glListClouds, (int)4864);
            Vec3 entityliving = this.theWorld.getCloudColour(par1);
            float exactPlayerX = (float)entityliving.xCoord;
            float var17 = (float)entityliving.yCoord;
            float exactPlayerY = (float)entityliving.zCoord;
            if (this.mc.gameSettings.anaglyph) {
                var19 = (exactPlayerX * 30.0f + var17 * 59.0f + exactPlayerY * 11.0f) / 100.0f;
                var20 = (exactPlayerX * 30.0f + var17 * 70.0f) / 100.0f;
                exactPlayerZ = (exactPlayerX * 30.0f + exactPlayerY * 70.0f) / 100.0f;
                exactPlayerX = var19;
                var17 = var20;
                exactPlayerY = exactPlayerZ;
            }
            var19 = (float)(var8 * 0.0);
            var20 = (float)(var10 * 0.0);
            exactPlayerZ = 0.00390625f;
            var19 = (float)MathHelper.floor_double(var8) * exactPlayerZ;
            var20 = (float)MathHelper.floor_double(var10) * exactPlayerZ;
            float dc = (float)(var8 - (double)MathHelper.floor_double(var8));
            float var23 = (float)(var10 - (double)MathHelper.floor_double(var10));
            int cdx = 8;
            int cdy = 4;
            cdz = 9.765625E-4f;
            GL11.glScalef((float)var4, (float)1.0f, (float)var4);
            int var27 = 0;
            while (var27 < 2) {
                if (var27 == 0) {
                    GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                } else if (this.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)true);
                    } else {
                        GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)true);
                    }
                } else {
                    GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                }
                int var28 = - cdy + 1;
                while (var28 <= cdy) {
                    int var29 = - cdy + 1;
                    while (var29 <= cdy) {
                        int var34;
                        var3.startDrawingQuads();
                        float var30 = var28 * cdx;
                        float var31 = var29 * cdx;
                        float var32 = var30 - dc;
                        float var33 = var31 - var23;
                        if (var12 > - var5 - 1.0f) {
                            var3.setColorRGBA_F(exactPlayerX * 0.7f, var17 * 0.7f, exactPlayerY * 0.7f, 0.8f);
                            var3.setNormal(0.0f, -1.0f, 0.0f);
                            var3.addVertexWithUV(var32 + 0.0f, var12 + 0.0f, var33 + (float)cdx, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                            var3.addVertexWithUV(var32 + (float)cdx, var12 + 0.0f, var33 + (float)cdx, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                            var3.addVertexWithUV(var32 + (float)cdx, var12 + 0.0f, var33 + 0.0f, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                            var3.addVertexWithUV(var32 + 0.0f, var12 + 0.0f, var33 + 0.0f, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                        }
                        if (var12 <= var5 + 1.0f) {
                            var3.setColorRGBA_F(exactPlayerX, var17, exactPlayerY, 0.8f);
                            var3.setNormal(0.0f, 1.0f, 0.0f);
                            var3.addVertexWithUV(var32 + 0.0f, var12 + var5 - cdz, var33 + (float)cdx, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                            var3.addVertexWithUV(var32 + (float)cdx, var12 + var5 - cdz, var33 + (float)cdx, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                            var3.addVertexWithUV(var32 + (float)cdx, var12 + var5 - cdz, var33 + 0.0f, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                            var3.addVertexWithUV(var32 + 0.0f, var12 + var5 - cdz, var33 + 0.0f, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                        }
                        var3.setColorRGBA_F(exactPlayerX * 0.9f, var17 * 0.9f, exactPlayerY * 0.9f, 0.8f);
                        if (var28 > -1) {
                            var3.setNormal(-1.0f, 0.0f, 0.0f);
                            var34 = 0;
                            while (var34 < cdx) {
                                var3.addVertexWithUV(var32 + (float)var34 + 0.0f, var12 + 0.0f, var33 + (float)cdx, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)var34 + 0.0f, var12 + var5, var33 + (float)cdx, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)var34 + 0.0f, var12 + var5, var33 + 0.0f, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)var34 + 0.0f, var12 + 0.0f, var33 + 0.0f, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                                ++var34;
                            }
                        }
                        if (var28 <= 1) {
                            var3.setNormal(1.0f, 0.0f, 0.0f);
                            var34 = 0;
                            while (var34 < cdx) {
                                var3.addVertexWithUV(var32 + (float)var34 + 1.0f - cdz, var12 + 0.0f, var33 + (float)cdx, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)var34 + 1.0f - cdz, var12 + var5, var33 + (float)cdx, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + (float)cdx) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)var34 + 1.0f - cdz, var12 + var5, var33 + 0.0f, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)var34 + 1.0f - cdz, var12 + 0.0f, var33 + 0.0f, (var30 + (float)var34 + 0.5f) * exactPlayerZ + var19, (var31 + 0.0f) * exactPlayerZ + var20);
                                ++var34;
                            }
                        }
                        var3.setColorRGBA_F(exactPlayerX * 0.8f, var17 * 0.8f, exactPlayerY * 0.8f, 0.8f);
                        if (var29 > -1) {
                            var3.setNormal(0.0f, 0.0f, -1.0f);
                            var34 = 0;
                            while (var34 < cdx) {
                                var3.addVertexWithUV(var32 + 0.0f, var12 + var5, var33 + (float)var34 + 0.0f, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)cdx, var12 + var5, var33 + (float)var34 + 0.0f, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)cdx, var12 + 0.0f, var33 + (float)var34 + 0.0f, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + 0.0f, var12 + 0.0f, var33 + (float)var34 + 0.0f, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                ++var34;
                            }
                        }
                        if (var29 <= 1) {
                            var3.setNormal(0.0f, 0.0f, 1.0f);
                            var34 = 0;
                            while (var34 < cdx) {
                                var3.addVertexWithUV(var32 + 0.0f, var12 + var5, var33 + (float)var34 + 1.0f - cdz, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)cdx, var12 + var5, var33 + (float)var34 + 1.0f - cdz, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + (float)cdx, var12 + 0.0f, var33 + (float)var34 + 1.0f - cdz, (var30 + (float)cdx) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                var3.addVertexWithUV(var32 + 0.0f, var12 + 0.0f, var33 + (float)var34 + 1.0f - cdz, (var30 + 0.0f) * exactPlayerZ + var19, (var31 + (float)var34 + 0.5f) * exactPlayerZ + var20);
                                ++var34;
                            }
                        }
                        var3.draw();
                        ++var29;
                    }
                    ++var28;
                }
                ++var27;
            }
            GL11.glEndList();
            this.isFancyGlListClouds = true;
            this.cloudTickCounterGlList = this.cloudTickCounter;
            this.cloudPlayerX = this.mc.renderViewEntity.prevPosX;
            this.cloudPlayerY = this.mc.renderViewEntity.prevPosY;
            this.cloudPlayerZ = this.mc.renderViewEntity.prevPosZ;
        }
        EntityLivingBase var36 = this.mc.renderViewEntity;
        double var37 = var36.prevPosX + (var36.posX - var36.prevPosX) * (double)partialTicks;
        double var38 = var36.prevPosY + (var36.posY - var36.prevPosY) * (double)partialTicks;
        double var40 = var36.prevPosZ + (var36.posZ - var36.prevPosZ) * (double)partialTicks;
        double var39 = (float)(this.cloudTickCounter - this.cloudTickCounterGlList) + partialTicks;
        float var41 = (float)(var37 - this.cloudPlayerX + var39 * 0.03);
        float var42 = (float)(var38 - this.cloudPlayerY);
        cdz = (float)(var40 - this.cloudPlayerZ);
        GL11.glTranslatef((float)(- var41), (float)(- var42), (float)(- cdz));
        GL11.glCallList((int)this.glListClouds);
        GL11.glTranslatef((float)var41, (float)var42, (float)cdz);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
    }

    public boolean updateRenderers(EntityLivingBase entityliving, boolean flag) {
        this.renderViewEntity = entityliving;
        if (WrUpdates.hasWrUpdater()) {
            return WrUpdates.updateRenderers(this, entityliving, flag);
        }
        if (this.worldRenderersToUpdate.size() <= 0) {
            return false;
        }
        int num = 0;
        int maxNum = Config.getUpdatesPerFrame();
        if (Config.isDynamicUpdates() && !this.isMoving(entityliving)) {
            maxNum *= 3;
        }
        int NOT_IN_FRUSTRUM_MUL = 4;
        int numValid = 0;
        WorldRenderer wrBest = null;
        float distSqBest = Float.MAX_VALUE;
        int indexBest = -1;
        int maxDiffDistSq = 0;
        while (maxDiffDistSq < this.worldRenderersToUpdate.size()) {
            WorldRenderer i = (WorldRenderer)this.worldRenderersToUpdate.get(maxDiffDistSq);
            if (i != null) {
                ++numValid;
                if (!i.needsUpdate) {
                    this.worldRenderersToUpdate.set(maxDiffDistSq, null);
                } else {
                    float wr = i.distanceToEntitySquared(entityliving);
                    if (wr <= 256.0f && this.isActingNow()) {
                        i.updateRenderer(entityliving);
                        i.needsUpdate = false;
                        this.worldRenderersToUpdate.set(maxDiffDistSq, null);
                        ++num;
                    } else {
                        if (wr > 256.0f && num >= maxNum) break;
                        if (!i.isInFrustum) {
                            wr *= (float)NOT_IN_FRUSTRUM_MUL;
                        }
                        if (wrBest == null) {
                            wrBest = i;
                            distSqBest = wr;
                            indexBest = maxDiffDistSq;
                        } else if (wr < distSqBest) {
                            wrBest = i;
                            distSqBest = wr;
                            indexBest = maxDiffDistSq;
                        }
                    }
                }
            }
            ++maxDiffDistSq;
        }
        if (wrBest != null) {
            wrBest.updateRenderer(entityliving);
            wrBest.needsUpdate = false;
            this.worldRenderersToUpdate.set(indexBest, null);
            float var16 = distSqBest / 5.0f;
            int var15 = 0;
            while (var15 < this.worldRenderersToUpdate.size() && ++num < maxNum) {
                WorldRenderer var17 = (WorldRenderer)this.worldRenderersToUpdate.get(var15);
                if (var17 != null) {
                    float diffDistSq;
                    float distSq = var17.distanceToEntitySquared(entityliving);
                    if (!var17.isInFrustum) {
                        distSq *= (float)NOT_IN_FRUSTRUM_MUL;
                    }
                    if ((diffDistSq = Math.abs(distSq - distSqBest)) < var16) {
                        var17.updateRenderer(entityliving);
                        var17.needsUpdate = false;
                        this.worldRenderersToUpdate.set(var15, null);
                        ++num;
                    }
                }
                ++var15;
            }
        }
        if (numValid == 0) {
            this.worldRenderersToUpdate.clear();
        }
        this.worldRenderersToUpdate.compact();
        return true;
    }

    public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityPlayer par2EntityPlayer, float par3) {
        this.drawBlockDamageTexture(par1Tessellator, par2EntityPlayer, par3);
    }

    public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityLivingBase par2EntityPlayer, float par3) {
        double var4 = par2EntityPlayer.lastTickPosX + (par2EntityPlayer.posX - par2EntityPlayer.lastTickPosX) * (double)par3;
        double var6 = par2EntityPlayer.lastTickPosY + (par2EntityPlayer.posY - par2EntityPlayer.lastTickPosY) * (double)par3;
        double var8 = par2EntityPlayer.lastTickPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.lastTickPosZ) * (double)par3;
        if (!this.damagedBlocks.isEmpty()) {
            OpenGlHelper.glBlendFunc(774, 768, 1, 0);
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
            GL11.glPushMatrix();
            GL11.glPolygonOffset((float)-3.0f, (float)-3.0f);
            GL11.glEnable((int)32823);
            GL11.glEnable((int)3008);
            par1Tessellator.startDrawingQuads();
            par1Tessellator.setTranslation(- var4, - var6, - var8);
            par1Tessellator.disableColor();
            Iterator var10 = this.damagedBlocks.values().iterator();
            while (var10.hasNext()) {
                double var14;
                double var16;
                DestroyBlockProgress var11 = (DestroyBlockProgress)var10.next();
                double var12 = (double)var11.getPartialBlockX() - var4;
                if (var12 * var12 + (var14 = (double)var11.getPartialBlockY() - var6) * var14 + (var16 = (double)var11.getPartialBlockZ() - var8) * var16 > 1024.0) {
                    var10.remove();
                    continue;
                }
                Block var18 = this.theWorld.getBlock(var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ());
                if (var18.getMaterial() == Material.air) continue;
                this.renderBlocksRg.renderBlockUsingTexture(var18, var11.getPartialBlockX(), var11.getPartialBlockY(), var11.getPartialBlockZ(), this.destroyBlockIcons[var11.getPartialBlockDamage()]);
            }
            par1Tessellator.draw();
            par1Tessellator.setTranslation(0.0, 0.0, 0.0);
            GL11.glDisable((int)3008);
            GL11.glPolygonOffset((float)0.0f, (float)0.0f);
            GL11.glDisable((int)32823);
            GL11.glEnable((int)3008);
            GL11.glDepthMask((boolean)true);
            GL11.glPopMatrix();
        }
    }

    public void drawSelectionBox(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, float par4) {
        if (par3 == 0 && par2MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.4f);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDepthMask((boolean)false);
            float var5 = 0.002f;
            Block var6 = this.theWorld.getBlock(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
            if (var6.getMaterial() != Material.air) {
                var6.setBlockBoundsBasedOnState(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                double var7 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * (double)par4;
                double var9 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * (double)par4;
                double var11 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * (double)par4;
                RenderGlobal.drawOutlinedBoundingBox(var6.getSelectedBoundingBoxFromPool(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand(var5, var5, var5).getOffsetBoundingBox(- var7, - var9, - var11), -1);
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
        }
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB p_147590_0_, int p_147590_1_) {
        Tessellator var2 = Tessellator.instance;
        var2.startDrawing(3);
        if (p_147590_1_ != -1) {
            var2.setColorOpaque_I(p_147590_1_);
        }
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.draw();
        var2.startDrawing(3);
        if (p_147590_1_ != -1) {
            var2.setColorOpaque_I(p_147590_1_);
        }
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.draw();
        var2.startDrawing(1);
        if (p_147590_1_ != -1) {
            var2.setColorOpaque_I(p_147590_1_);
        }
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.draw();
    }

    public void markBlocksForUpdate(int par1, int par2, int par3, int par4, int par5, int par6) {
        int var7 = MathHelper.bucketInt(par1, 16);
        int var8 = MathHelper.bucketInt(par2, 16);
        int var9 = MathHelper.bucketInt(par3, 16);
        int var10 = MathHelper.bucketInt(par4, 16);
        int var11 = MathHelper.bucketInt(par5, 16);
        int var12 = MathHelper.bucketInt(par6, 16);
        int var13 = var7;
        while (var13 <= var10) {
            int var14 = var13 % this.renderChunksWide;
            if (var14 < 0) {
                var14 += this.renderChunksWide;
            }
            int var15 = var8;
            while (var15 <= var11) {
                int var16 = var15 % this.renderChunksTall;
                if (var16 < 0) {
                    var16 += this.renderChunksTall;
                }
                int var17 = var9;
                while (var17 <= var12) {
                    int var19;
                    WorldRenderer var20;
                    int var18 = var17 % this.renderChunksDeep;
                    if (var18 < 0) {
                        var18 += this.renderChunksDeep;
                    }
                    if ((var20 = this.worldRenderers[var19 = (var18 * this.renderChunksTall + var16) * this.renderChunksWide + var14]) != null && !var20.needsUpdate) {
                        this.worldRenderersToUpdate.add(var20);
                        var20.markDirty();
                    }
                    ++var17;
                }
                ++var15;
            }
            ++var13;
        }
    }

    @Override
    public void markBlockForUpdate(int p_147586_1_, int p_147586_2_, int p_147586_3_) {
        this.markBlocksForUpdate(p_147586_1_ - 1, p_147586_2_ - 1, p_147586_3_ - 1, p_147586_1_ + 1, p_147586_2_ + 1, p_147586_3_ + 1);
    }

    @Override
    public void markBlockForRenderUpdate(int p_147588_1_, int p_147588_2_, int p_147588_3_) {
        this.markBlocksForUpdate(p_147588_1_ - 1, p_147588_2_ - 1, p_147588_3_ - 1, p_147588_1_ + 1, p_147588_2_ + 1, p_147588_3_ + 1);
    }

    @Override
    public void markBlockRangeForRenderUpdate(int p_147585_1_, int p_147585_2_, int p_147585_3_, int p_147585_4_, int p_147585_5_, int p_147585_6_) {
        this.markBlocksForUpdate(p_147585_1_ - 1, p_147585_2_ - 1, p_147585_3_ - 1, p_147585_4_ + 1, p_147585_5_ + 1, p_147585_6_ + 1);
    }

    public void clipRenderersByFrustum(ICamera par1ICamera, float par2) {
        int var3 = 0;
        while (var3 < this.countSortedWorldRenderers) {
            WorldRenderer wr = this.sortedWorldRenderers[var3];
            if (!wr.skipAllRenderPasses()) {
                wr.updateInFrustum(par1ICamera);
            }
            ++var3;
        }
    }

    @Override
    public void playRecord(String par1Str, int par2, int par3, int par4) {
        ChunkCoordinates var5 = new ChunkCoordinates(par2, par3, par4);
        ISound var6 = (ISound)this.mapSoundPositions.get(var5);
        if (var6 != null) {
            this.mc.getSoundHandler().func_147683_b(var6);
            this.mapSoundPositions.remove(var5);
        }
        if (par1Str != null) {
            ItemRecord var7 = ItemRecord.func_150926_b(par1Str);
            if (var7 != null) {
                this.mc.ingameGUI.setRecordPlayingMessage(var7.func_150927_i());
            }
            PositionedSoundRecord var8 = PositionedSoundRecord.func_147675_a(new ResourceLocation(par1Str), par2, par3, par4);
            this.mapSoundPositions.put(var5, var8);
            this.mc.getSoundHandler().playSound(var8);
        }
    }

    @Override
    public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9) {
    }

    @Override
    public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10) {
    }

    @Override
    public void spawnParticle(String par1Str, final double par2, final double par4, final double par6, double par8, double par10, double par12) {
        try {
            this.doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
        }
        catch (Throwable var17) {
            CrashReport var15 = CrashReport.makeCrashReport(var17, "Exception while adding particle");
            CrashReportCategory var16 = var15.makeCategory("Particle being added");
            var16.addCrashSection("Name", par1Str);
            var16.addCrashSectionCallable("Position", new Callable(){
                private static final String __OBFID = "CL_00000955";

                public String call() {
                    return CrashReportCategory.func_85074_a(par2, par4, par6);
                }
            });
            throw new ReportedException(var15);
        }
    }

    public EntityFX doSpawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
        if (this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null) {
            int var14 = this.mc.gameSettings.particleSetting;
            if (var14 == 1 && this.theWorld.rand.nextInt(3) == 0) {
                var14 = 2;
            }
            double var15 = this.mc.renderViewEntity.posX - par2;
            double var17 = this.mc.renderViewEntity.posY - par4;
            double var19 = this.mc.renderViewEntity.posZ - par6;
            EntityFX var21 = null;
            if (par1Str.equals("hugeexplosion")) {
                if (Config.isAnimatedExplosion()) {
                    var21 = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    this.mc.effectRenderer.addEffect(var21);
                }
            } else if (par1Str.equals("largeexplode")) {
                if (Config.isAnimatedExplosion()) {
                    var21 = new EntityLargeExplodeFX(this.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12);
                    this.mc.effectRenderer.addEffect(var21);
                }
            } else if (par1Str.equals("fireworksSpark")) {
                var21 = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer);
                this.mc.effectRenderer.addEffect(var21);
            }
            if (var21 != null) {
                return var21;
            }
            double var22 = 16.0;
            double d3 = 16.0;
            if (par1Str.equals("crit")) {
                var22 = 196.0;
            }
            if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22) {
                return null;
            }
            if (var14 > 1) {
                return null;
            }
            if (par1Str.equals("bubble")) {
                var21 = new EntityBubbleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                CustomColorizer.updateWaterFX(var21, this.theWorld);
            } else if (par1Str.equals("suspended")) {
                if (Config.isWaterParticles()) {
                    var21 = new EntitySuspendFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                }
            } else if (par1Str.equals("depthsuspend")) {
                if (Config.isVoidParticles()) {
                    var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                }
            } else if (par1Str.equals("townaura")) {
                var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                CustomColorizer.updateMyceliumFX(var21);
            } else if (par1Str.equals("crit")) {
                var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            } else if (par1Str.equals("magicCrit")) {
                var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                var21.setRBGColorF(var21.getRedColorF() * 0.3f, var21.getGreenColorF() * 0.8f, var21.getBlueColorF());
                var21.nextTextureIndexX();
            } else if (par1Str.equals("smoke")) {
                if (Config.isAnimatedSmoke()) {
                    var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                }
            } else if (par1Str.equals("mobSpell")) {
                if (Config.isPotionParticles()) {
                    var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0, 0.0, 0.0);
                    var21.setRBGColorF((float)par8, (float)par10, (float)par12);
                }
            } else if (par1Str.equals("mobSpellAmbient")) {
                if (Config.isPotionParticles()) {
                    var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0, 0.0, 0.0);
                    var21.setAlphaF(0.15f);
                    var21.setRBGColorF((float)par8, (float)par10, (float)par12);
                }
            } else if (par1Str.equals("spell")) {
                if (Config.isPotionParticles()) {
                    var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                }
            } else if (par1Str.equals("instantSpell")) {
                if (Config.isPotionParticles()) {
                    var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
                }
            } else if (par1Str.equals("witchMagic")) {
                if (Config.isPotionParticles()) {
                    var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
                    float var26 = this.theWorld.rand.nextFloat() * 0.5f + 0.35f;
                    var21.setRBGColorF(1.0f * var26, 0.0f * var26, 1.0f * var26);
                }
            } else if (par1Str.equals("note")) {
                var21 = new EntityNoteFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            } else if (par1Str.equals("portal")) {
                if (Config.isPortalParticles()) {
                    var21 = new EntityPortalFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    CustomColorizer.updatePortalFX(var21);
                }
            } else if (par1Str.equals("enchantmenttable")) {
                var21 = new EntityEnchantmentTableParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            } else if (par1Str.equals("explode")) {
                if (Config.isAnimatedExplosion()) {
                    var21 = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                }
            } else if (par1Str.equals("flame")) {
                if (Config.isAnimatedFlame()) {
                    var21 = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                }
            } else if (par1Str.equals("lava")) {
                var21 = new EntityLavaFX(this.theWorld, par2, par4, par6);
            } else if (par1Str.equals("footstep")) {
                var21 = new EntityFootStepFX(this.renderEngine, this.theWorld, par2, par4, par6);
            } else if (par1Str.equals("splash")) {
                var21 = new EntitySplashFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                CustomColorizer.updateWaterFX(var21, this.theWorld);
            } else if (par1Str.equals("wake")) {
                var21 = new EntityFishWakeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            } else if (par1Str.equals("largesmoke")) {
                if (Config.isAnimatedSmoke()) {
                    var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5f);
                }
            } else if (par1Str.equals("cloud")) {
                var21 = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            } else if (par1Str.equals("reddust")) {
                if (Config.isAnimatedRedstone()) {
                    var21 = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
                    CustomColorizer.updateReddustFX(var21, this.theWorld, var15, var17, var19);
                }
            } else if (par1Str.equals("snowballpoof")) {
                var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Items.snowball);
            } else if (par1Str.equals("dripWater")) {
                if (Config.isDrippingWaterLava()) {
                    var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.water);
                }
            } else if (par1Str.equals("dripLava")) {
                if (Config.isDrippingWaterLava()) {
                    var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.lava);
                }
            } else if (par1Str.equals("snowshovel")) {
                var21 = new EntitySnowShovelFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            } else if (par1Str.equals("slime")) {
                var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Items.slime_ball);
            } else if (par1Str.equals("heart")) {
                var21 = new EntityHeartFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            } else if (par1Str.equals("angryVillager")) {
                var21 = new EntityHeartFX(this.theWorld, par2, par4 + 0.5, par6, par8, par10, par12);
                var21.setParticleTextureIndex(81);
                var21.setRBGColorF(1.0f, 1.0f, 1.0f);
            } else if (par1Str.equals("happyVillager")) {
                var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                var21.setParticleTextureIndex(82);
                var21.setRBGColorF(1.0f, 1.0f, 1.0f);
            } else if (par1Str.startsWith("iconcrack_")) {
                String[] var28 = par1Str.split("_", 3);
                int var27 = Integer.parseInt(var28[1]);
                if (var28.length > 2) {
                    int var261 = Integer.parseInt(var28[2]);
                    var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.getItemById(var27), var261);
                } else {
                    var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.getItemById(var27), 0);
                }
            } else if (par1Str.startsWith("blockcrack_")) {
                String[] var28 = par1Str.split("_", 3);
                Block var271 = Block.getBlockById(Integer.parseInt(var28[1]));
                int var261 = Integer.parseInt(var28[2]);
                var21 = new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, var271, var261).applyRenderColor(var261);
            } else if (par1Str.startsWith("blockdust_")) {
                String[] var28 = par1Str.split("_", 3);
                Block var271 = Block.getBlockById(Integer.parseInt(var28[1]));
                int var261 = Integer.parseInt(var28[2]);
                var21 = new EntityBlockDustFX(this.theWorld, par2, par4, par6, par8, par10, par12, var271, var261).applyRenderColor(var261);
            }
            if (var21 != null) {
                this.mc.effectRenderer.addEffect(var21);
            }
            return var21;
        }
        return null;
    }

    @Override
    public void onEntityCreate(Entity par1Entity) {
        RandomMobs.entityLoaded(par1Entity);
    }

    @Override
    public void onEntityDestroy(Entity par1Entity) {
    }

    public void deleteAllDisplayLists() {
        GLAllocation.deleteDisplayLists(this.glRenderListBase);
        this.displayListAllocator.deleteDisplayLists();
    }

    @Override
    public void broadcastSound(int par1, int par2, int par3, int par4, int par5) {
        Random var6 = this.theWorld.rand;
        switch (par1) {
            case 1013: 
            case 1018: {
                if (this.mc.renderViewEntity == null) break;
                double var7 = (double)par2 - this.mc.renderViewEntity.posX;
                double var9 = (double)par3 - this.mc.renderViewEntity.posY;
                double var11 = (double)par4 - this.mc.renderViewEntity.posZ;
                double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
                double var15 = this.mc.renderViewEntity.posX;
                double var17 = this.mc.renderViewEntity.posY;
                double var19 = this.mc.renderViewEntity.posZ;
                if (var13 > 0.0) {
                    var15 += var7 / var13 * 2.0;
                    var17 += var9 / var13 * 2.0;
                    var19 += var11 / var13 * 2.0;
                }
                if (par1 == 1013) {
                    this.theWorld.playSound(var15, var17, var19, "mob.wither.spawn", 1.0f, 1.0f, false);
                    break;
                }
                if (par1 != 1018) break;
                this.theWorld.playSound(var15, var17, var19, "mob.enderdragon.end", 5.0f, 1.0f, false);
            }
        }
    }

    @Override
    public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6) {
        Random var7 = this.theWorld.rand;
        Block var8 = null;
        switch (par2) {
            case 1000: {
                this.theWorld.playSound(par3, par4, par5, "random.click", 1.0f, 1.0f, false);
                break;
            }
            case 1001: {
                this.theWorld.playSound(par3, par4, par5, "random.click", 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.theWorld.playSound(par3, par4, par5, "random.bow", 1.0f, 1.2f, false);
                break;
            }
            case 1003: {
                if (Math.random() < 0.5) {
                    this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "random.door_open", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                    break;
                }
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "random.door_close", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1004: {
                this.theWorld.playSound((float)par3 + 0.5f, (float)par4 + 0.5f, (float)par5 + 0.5f, "random.fizz", 0.5f, 2.6f + (var7.nextFloat() - var7.nextFloat()) * 0.8f, false);
                break;
            }
            case 1005: {
                if (Item.getItemById(par6) instanceof ItemRecord) {
                    this.theWorld.playRecord("records." + ((ItemRecord)Item.getItemById((int)par6)).field_150929_a, par3, par4, par5);
                    break;
                }
                this.theWorld.playRecord(null, par3, par4, par5);
                break;
            }
            case 1007: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.ghast.charge", 10.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1008: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.ghast.fireball", 10.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1009: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.ghast.fireball", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1010: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.zombie.wood", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1011: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.zombie.metal", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1012: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.zombie.woodbreak", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1014: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.wither.shoot", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1015: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.bat.takeoff", 0.05f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.zombie.infect", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "mob.zombie.unfect", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.theWorld.playSound((float)par3 + 0.5f, (float)par4 + 0.5f, (float)par5 + 0.5f, "random.anvil_break", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1021: {
                this.theWorld.playSound((float)par3 + 0.5f, (float)par4 + 0.5f, (float)par5 + 0.5f, "random.anvil_use", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1022: {
                this.theWorld.playSound((float)par3 + 0.5f, (float)par4 + 0.5f, (float)par5 + 0.5f, "random.anvil_land", 0.3f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2000: {
                int var34 = par6 % 3 - 1;
                int var10 = par6 / 3 % 3 - 1;
                double var11 = (double)par3 + (double)var34 * 0.6 + 0.5;
                double var13 = (double)par4 + 0.5;
                double var36 = (double)par5 + (double)var10 * 0.6 + 0.5;
                int var44 = 0;
                while (var44 < 10) {
                    double var43 = var7.nextDouble() * 0.2 + 0.01;
                    double var45 = var11 + (double)var34 * 0.01 + (var7.nextDouble() - 0.5) * (double)var10 * 0.5;
                    double var22 = var13 + (var7.nextDouble() - 0.5) * 0.5;
                    double var41 = var36 + (double)var10 * 0.01 + (var7.nextDouble() - 0.5) * (double)var34 * 0.5;
                    double var26 = (double)var34 * var43 + var7.nextGaussian() * 0.01;
                    double var28 = -0.03 + var7.nextGaussian() * 0.01;
                    double var30 = (double)var10 * var43 + var7.nextGaussian() * 0.01;
                    this.spawnParticle("smoke", var45, var22, var41, var26, var28, var30);
                    ++var44;
                }
                return;
            }
            case 2001: {
                var8 = Block.getBlockById(par6 & 4095);
                if (var8.getMaterial() != Material.air) {
                    this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var8.stepSound.func_150495_a()), (var8.stepSound.func_150497_c() + 1.0f) / 2.0f, var8.stepSound.func_150494_d() * 0.8f, (float)par3 + 0.5f, (float)par4 + 0.5f, (float)par5 + 0.5f));
                }
                this.mc.effectRenderer.func_147215_a(par3, par4, par5, var8, par6 >> 12 & 255);
                break;
            }
            case 2002: {
                double var9 = par3;
                double var11 = par4;
                double var13 = par5;
                String var15 = "iconcrack_" + Item.getIdFromItem(Items.potionitem) + "_" + par6;
                int var16 = 0;
                while (var16 < 8) {
                    this.spawnParticle(var15, var9, var11, var13, var7.nextGaussian() * 0.15, var7.nextDouble() * 0.2, var7.nextGaussian() * 0.15);
                    ++var16;
                }
                var16 = Items.potionitem.getColorFromDamage(par6);
                float var17 = (float)(var16 >> 16 & 255) / 255.0f;
                float var18 = (float)(var16 >> 8 & 255) / 255.0f;
                float var19 = (float)(var16 >> 0 & 255) / 255.0f;
                String var20 = "spell";
                if (Items.potionitem.isEffectInstant(par6)) {
                    var20 = "instantSpell";
                }
                int var40 = 0;
                while (var40 < 100) {
                    double var22 = var7.nextDouble() * 4.0;
                    double var41 = var7.nextDouble() * 3.141592653589793 * 2.0;
                    double var26 = Math.cos(var41) * var22;
                    double var28 = 0.01 + var7.nextDouble() * 0.5;
                    double var30 = Math.sin(var41) * var22;
                    EntityFX var46 = this.doSpawnParticle(var20, var9 + var26 * 0.1, var11 + 0.3, var13 + var30 * 0.1, var26, var28, var30);
                    if (var46 != null) {
                        float var33 = 0.75f + var7.nextFloat() * 0.25f;
                        var46.setRBGColorF(var17 * var33, var18 * var33, var19 * var33);
                        var46.multiplyVelocity((float)var22);
                    }
                    ++var40;
                }
                this.theWorld.playSound((double)par3 + 0.5, (double)par4 + 0.5, (double)par5 + 0.5, "game.potion.smash", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2003: {
                double var9 = (double)par3 + 0.5;
                double var11 = par4;
                double var13 = (double)par5 + 0.5;
                String var15 = "iconcrack_" + Item.getIdFromItem(Items.ender_eye);
                int var16 = 0;
                while (var16 < 8) {
                    this.spawnParticle(var15, var9, var11, var13, var7.nextGaussian() * 0.15, var7.nextDouble() * 0.2, var7.nextGaussian() * 0.15);
                    ++var16;
                }
                double var21 = 0.0;
                while (var21 < 6.283185307179586) {
                    this.spawnParticle("portal", var9 + Math.cos(var21) * 5.0, var11 - 0.4, var13 + Math.sin(var21) * 5.0, Math.cos(var21) * -5.0, 0.0, Math.sin(var21) * -5.0);
                    this.spawnParticle("portal", var9 + Math.cos(var21) * 5.0, var11 - 0.4, var13 + Math.sin(var21) * 5.0, Math.cos(var21) * -7.0, 0.0, Math.sin(var21) * -7.0);
                    var21 += 0.15707963267948966;
                }
                return;
            }
            case 2004: {
                int var40 = 0;
                while (var40 < 20) {
                    double var22 = (double)par3 + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double var41 = (double)par4 + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double var26 = (double)par5 + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle("smoke", var22, var41, var26, 0.0, 0.0, 0.0);
                    this.theWorld.spawnParticle("flame", var22, var41, var26, 0.0, 0.0, 0.0);
                    ++var40;
                }
                return;
            }
            case 2005: {
                ItemDye.func_150918_a(this.theWorld, par3, par4, par5, par6);
                break;
            }
            case 2006: {
                var8 = this.theWorld.getBlock(par3, par4, par5);
                if (var8.getMaterial() == Material.air) break;
                double var21 = Math.min(0.2f + (float)par6 / 15.0f, 10.0f);
                if (var21 > 2.5) {
                    var21 = 2.5;
                }
                int var23 = (int)(150.0 * var21);
                int var24 = 0;
                while (var24 < var23) {
                    float var25 = MathHelper.randomFloatClamp(var7, 0.0f, 6.2831855f);
                    double var26 = MathHelper.randomFloatClamp(var7, 0.75f, 1.0f);
                    double var28 = 0.20000000298023224 + var21 / 100.0;
                    double var30 = (double)(MathHelper.cos(var25) * 0.2f) * var26 * var26 * (var21 + 0.2);
                    double var32 = (double)(MathHelper.sin(var25) * 0.2f) * var26 * var26 * (var21 + 0.2);
                    this.theWorld.spawnParticle("blockdust_" + Block.getIdFromBlock(var8) + "_" + this.theWorld.getBlockMetadata(par3, par4, par5), (float)par3 + 0.5f, (float)par4 + 1.0f, (float)par5 + 0.5f, var30, var28, var32);
                    ++var24;
                }
                break block0;
            }
        }
    }

    @Override
    public void destroyBlockPartially(int p_147587_1_, int p_147587_2_, int p_147587_3_, int p_147587_4_, int p_147587_5_) {
        if (p_147587_5_ >= 0 && p_147587_5_ < 10) {
            DestroyBlockProgress var6 = (DestroyBlockProgress)this.damagedBlocks.get(p_147587_1_);
            if (var6 == null || var6.getPartialBlockX() != p_147587_2_ || var6.getPartialBlockY() != p_147587_3_ || var6.getPartialBlockZ() != p_147587_4_) {
                var6 = new DestroyBlockProgress(p_147587_1_, p_147587_2_, p_147587_3_, p_147587_4_);
                this.damagedBlocks.put(p_147587_1_, var6);
            }
            var6.setPartialBlockDamage(p_147587_5_);
            var6.setCloudUpdateTick(this.cloudTickCounter);
        } else {
            this.damagedBlocks.remove(p_147587_1_);
        }
    }

    public void registerDestroyBlockIcons(IIconRegister par1IconRegister) {
        this.destroyBlockIcons = new IIcon[10];
        int var2 = 0;
        while (var2 < this.destroyBlockIcons.length) {
            this.destroyBlockIcons[var2] = par1IconRegister.registerIcon("destroy_stage_" + var2);
            ++var2;
        }
    }

    public void setAllRenderersVisible() {
        if (this.worldRenderers != null) {
            int i = 0;
            while (i < this.worldRenderers.length) {
                this.worldRenderers[i].isVisible = true;
                ++i;
            }
        }
    }

    public boolean isMoving(EntityLivingBase entityliving) {
        boolean moving = this.isMovingNow(entityliving);
        if (moving) {
            this.lastMovedTime = System.currentTimeMillis();
            return true;
        }
        if (System.currentTimeMillis() - this.lastMovedTime < 2000) {
            return true;
        }
        return false;
    }

    private boolean isMovingNow(EntityLivingBase entityliving) {
        double maxDiff = 0.001;
        return entityliving.isSneaking() ? true : ((double)entityliving.prevSwingProgress > maxDiff ? true : (this.mc.mouseHelper.deltaX != 0 ? true : (this.mc.mouseHelper.deltaY != 0 ? true : (Math.abs(entityliving.posX - entityliving.prevPosX) > maxDiff ? true : (Math.abs(entityliving.posY - entityliving.prevPosY) > maxDiff ? true : Math.abs(entityliving.posZ - entityliving.prevPosZ) > maxDiff)))));
    }

    public boolean isActing() {
        boolean acting = this.isActingNow();
        if (acting) {
            this.lastActionTime = System.currentTimeMillis();
            return true;
        }
        if (System.currentTimeMillis() - this.lastActionTime < 500) {
            return true;
        }
        return false;
    }

    public boolean isActingNow() {
        return Mouse.isButtonDown((int)0) ? true : Mouse.isButtonDown((int)1);
    }

    public int renderAllSortedRenderers(int renderPass, double partialTicks) {
        return this.renderSortedRenderers(0, this.countSortedWorldRenderers, renderPass, partialTicks);
    }

    public void updateCapes() {
        if (this.theWorld != null) {
            boolean showCapes = Config.isShowCapes();
            List playerList = this.theWorld.playerEntities;
            int i = 0;
            while (i < playerList.size()) {
                Entity entity = (Entity)playerList.get(i);
                if (entity instanceof AbstractClientPlayer) {
                    AbstractClientPlayer player = (AbstractClientPlayer)entity;
                    player.getTextureCape().enabled = showCapes;
                }
                ++i;
            }
        }
    }

    public AxisAlignedBB getTileEntityBoundingBox(TileEntity te) {
        if (!te.hasWorldObj()) {
            return AABB_INFINITE;
        }
        Block blockType = te.getBlockType();
        if (blockType == Blocks.enchanting_table) {
            return AxisAlignedBB.getAABBPool().getAABB(te.field_145851_c, te.field_145848_d, te.field_145849_e, te.field_145851_c + 1, te.field_145848_d + 1, te.field_145849_e + 1);
        }
        if (blockType != Blocks.chest && blockType != Blocks.trapped_chest) {
            AxisAlignedBB blockAabb;
            if (Reflector.ForgeTileEntity_getRenderBoundingBox.exists() && (blockAabb = (AxisAlignedBB)Reflector.call(te, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object[0])) != null) {
                return blockAabb;
            }
            if (blockType != null && blockType != Blocks.beacon && (blockAabb = blockType.getCollisionBoundingBoxFromPool(te.getWorldObj(), te.field_145851_c, te.field_145848_d, te.field_145849_e)) != null) {
                return blockAabb;
            }
            return AABB_INFINITE;
        }
        return AxisAlignedBB.getAABBPool().getAABB(te.field_145851_c - 1, te.field_145848_d, te.field_145849_e - 1, te.field_145851_c + 2, te.field_145848_d + 2, te.field_145849_e + 2);
    }

    public void addToSortedWorldRenderers(WorldRenderer wr) {
        if (!wr.inSortedWorldRenderers) {
            int countGreater;
            int pos = this.countSortedWorldRenderers;
            float distSq = wr.sortDistanceToEntitySquared = wr.distanceToEntitySquared(this.renderViewEntity);
            if (this.countSortedWorldRenderers > 0) {
                countGreater = 0;
                int high = this.countSortedWorldRenderers - 1;
                int mid = (countGreater + high) / 2;
                while (countGreater <= high) {
                    mid = (countGreater + high) / 2;
                    WorldRenderer wrMid = this.sortedWorldRenderers[mid];
                    if (distSq < wrMid.sortDistanceToEntitySquared) {
                        high = mid - 1;
                        continue;
                    }
                    countGreater = mid + 1;
                }
                pos = countGreater > mid ? mid + 1 : mid;
            }
            if ((countGreater = this.countSortedWorldRenderers - pos) > 0) {
                System.arraycopy(this.sortedWorldRenderers, pos, this.sortedWorldRenderers, pos + 1, countGreater);
            }
            this.sortedWorldRenderers[pos] = wr;
            wr.inSortedWorldRenderers = true;
            ++this.countSortedWorldRenderers;
        }
    }

}

