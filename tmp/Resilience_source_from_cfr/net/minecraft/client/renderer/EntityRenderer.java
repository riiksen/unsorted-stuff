/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonSyntaxException
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.util.glu.Project
 */
package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;
import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookGuiIngame;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.RenderUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityRainFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.ItemRendererOF;
import net.minecraft.src.RandomMobs;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.TextureUtils;
import net.minecraft.src.WrUpdates;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class EntityRenderer
implements IResourceManagerReloadListener {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
    public static boolean anaglyphEnable;
    public static int anaglyphField;
    private Minecraft mc;
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private final MapItemRenderer theMapItemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis = new MouseFilter();
    private MouseFilter mouseFilterYAxis = new MouseFilter();
    private MouseFilter mouseFilterDummy1 = new MouseFilter();
    private MouseFilter mouseFilterDummy2 = new MouseFilter();
    private MouseFilter mouseFilterDummy3 = new MouseFilter();
    private MouseFilter mouseFilterDummy4 = new MouseFilter();
    private float thirdPersonDistance = 4.0f;
    private float thirdPersonDistanceTemp = 4.0f;
    private float debugCamYaw;
    private float prevDebugCamYaw;
    private float debugCamPitch;
    private float prevDebugCamPitch;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private float debugCamFOV;
    private float prevDebugCamFOV;
    private float camRoll;
    private float prevCamRoll;
    private final DynamicTexture lightmapTexture;
    private final int[] lightmapColors;
    private final ResourceLocation locationLightMap;
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float fovMultiplierTemp;
    private float bossColorModifier;
    private float bossColorModifierPrev;
    private boolean cloudFog;
    private final IResourceManager resourceManager;
    public ShaderGroup theShaderGroup;
    private static final ResourceLocation[] shaderResourceLocations;
    public static final int shaderCount;
    private int shaderIndex = shaderCount;
    private double cameraZoom = 1.0;
    private double cameraYaw;
    private double cameraPitch;
    private long prevFrameTime = Minecraft.getSystemTime();
    private long renderEndNanoTime;
    private boolean lightmapUpdateNeeded;
    float torchFlickerX;
    float torchFlickerDX;
    float torchFlickerY;
    float torchFlickerDY;
    private Random random = new Random();
    private int rainSoundCounter;
    float[] rainXCoords;
    float[] rainYCoords;
    FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float fogColor2;
    private float fogColor1;
    public int debugViewDirection;
    private boolean initialized = false;
    private World updatedWorld = null;
    private boolean showDebugInfo = false;
    public boolean fogStandard = false;
    private long lastServerTime = 0;
    private int lastServerTicks = 0;
    private int serverWaitTime = 0;
    private int serverWaitTimeCurrent = 0;
    private float avgServerTimeDiff = 0.0f;
    private float avgServerTickDiff = 0.0f;
    public long[] frameTimes = new long[512];
    public long[] tickTimes = new long[512];
    public long[] chunkTimes = new long[512];
    public long[] serverTimes = new long[512];
    public int numRecordedFrameTimes = 0;
    public long prevFrameTimeNano = -1;
    private boolean lastShowDebugInfo = false;
    private boolean showExtendedDebugInfo = false;
    private static final String __OBFID = "CL_00000947";

    static {
        shaderResourceLocations = new ResourceLocation[]{new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json")};
        shaderCount = shaderResourceLocations.length;
    }

    public EntityRenderer(Minecraft p_i45076_1_, IResourceManager p_i45076_2_) {
        this.mc = p_i45076_1_;
        this.resourceManager = p_i45076_2_;
        this.theMapItemRenderer = new MapItemRenderer(p_i45076_1_.getTextureManager());
        this.itemRenderer = new ItemRenderer(p_i45076_1_);
        this.lightmapTexture = new DynamicTexture(16, 16);
        this.locationLightMap = p_i45076_1_.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
    }

    public boolean isShaderActive() {
        if (OpenGlHelper.shadersSupported && this.theShaderGroup != null) {
            return true;
        }
        return false;
    }

    public void deactivateShader() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        this.shaderIndex = shaderCount;
    }

    public void activateNextShader() {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
            if (this.shaderIndex != shaderCount) {
                try {
                    logger.info("Selecting effect " + shaderResourceLocations[this.shaderIndex]);
                    this.theShaderGroup = new ShaderGroup(this.resourceManager, this.mc.getFramebuffer(), shaderResourceLocations[this.shaderIndex]);
                    this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                }
                catch (IOException var2) {
                    logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], (Throwable)var2);
                    this.shaderIndex = shaderCount;
                }
                catch (JsonSyntaxException var3) {
                    logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], (Throwable)var3);
                    this.shaderIndex = shaderCount;
                }
            } else {
                this.theShaderGroup = null;
                logger.info("No effect selected");
            }
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager par1ResourceManager) {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        if (this.shaderIndex != shaderCount) {
            try {
                this.theShaderGroup = new ShaderGroup(par1ResourceManager, this.mc.getFramebuffer(), shaderResourceLocations[this.shaderIndex]);
                this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            }
            catch (IOException var3) {
                logger.warn("Failed to load shader: " + shaderResourceLocations[this.shaderIndex], (Throwable)var3);
                this.shaderIndex = shaderCount;
            }
        }
    }

    public void updateRenderer() {
        float var1;
        float var2;
        if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
        }
        this.updateFovModifierHand();
        this.updateTorchFlicker();
        this.fogColor2 = this.fogColor1;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        this.prevDebugCamYaw = this.debugCamYaw;
        this.prevDebugCamPitch = this.debugCamPitch;
        this.prevDebugCamFOV = this.debugCamFOV;
        this.prevCamRoll = this.camRoll;
        if (this.mc.gameSettings.smoothCamera) {
            var1 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            var2 = var1 * var1 * var1 * 8.0f;
            this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05f * var2);
            this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05f * var2);
            this.smoothCamPartialTicks = 0.0f;
            this.smoothCamYaw = 0.0f;
            this.smoothCamPitch = 0.0f;
        }
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        var1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
        var2 = this.mc.gameSettings.renderDistanceChunks / 16;
        float var3 = var1 * (1.0f - var2) + var2;
        this.fogColor1 += (var3 - this.fogColor1) * 0.1f;
        ++this.rendererUpdateCount;
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();
        this.bossColorModifierPrev = this.bossColorModifier;
        if (BossStatus.hasColorModifier) {
            this.bossColorModifier += 0.05f;
            if (this.bossColorModifier > 1.0f) {
                this.bossColorModifier = 1.0f;
            }
            BossStatus.hasColorModifier = false;
        } else if (this.bossColorModifier > 0.0f) {
            this.bossColorModifier -= 0.0125f;
        }
    }

    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }

    public void updateShaderGroupSize(int p_147704_1_, int p_147704_2_) {
        if (OpenGlHelper.shadersSupported && this.theShaderGroup != null) {
            this.theShaderGroup.createBindFramebuffers(p_147704_1_, p_147704_2_);
        }
    }

    public void getMouseOver(float par1) {
        if (this.mc.renderViewEntity != null && this.mc.theWorld != null) {
            this.mc.pointedEntity = null;
            double var2 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(var2, par1);
            double var4 = var2;
            Vec3 var6 = this.mc.renderViewEntity.getPosition(par1);
            if (this.mc.playerController.extendedReach()) {
                var2 = 6.0;
                var4 = 6.0;
            } else {
                if (var2 > 3.0) {
                    var4 = 3.0;
                }
                var2 = var4;
            }
            if (this.mc.objectMouseOver != null) {
                var4 = this.mc.objectMouseOver.hitVec.distanceTo(var6);
            }
            Vec3 var7 = this.mc.renderViewEntity.getLook(par1);
            Vec3 var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
            this.pointedEntity = null;
            Vec3 var9 = null;
            float var10 = 1.0f;
            List var11 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand(var10, var10, var10));
            double var12 = var4;
            int var14 = 0;
            while (var14 < var11.size()) {
                Entity var15 = (Entity)var11.get(var14);
                if (var15.canBeCollidedWith()) {
                    double var19;
                    float var16 = var15.getCollisionBorderSize();
                    AxisAlignedBB var17 = var15.boundingBox.expand(var16, var16, var16);
                    MovingObjectPosition var18 = var17.calculateIntercept(var6, var8);
                    if (var17.isVecInside(var6)) {
                        if (0.0 < var12 || var12 == 0.0) {
                            this.pointedEntity = var15;
                            var9 = var18 == null ? var6 : var18.hitVec;
                            var12 = 0.0;
                        }
                    } else if (var18 != null && ((var19 = var6.distanceTo(var18.hitVec)) < var12 || var12 == 0.0)) {
                        boolean canRiderInteract = false;
                        if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                            canRiderInteract = Reflector.callBoolean(var15, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                        }
                        if (var15 == this.mc.renderViewEntity.ridingEntity && !canRiderInteract) {
                            if (var12 == 0.0) {
                                this.pointedEntity = var15;
                                var9 = var18.hitVec;
                            }
                        } else {
                            this.pointedEntity = var15;
                            var9 = var18.hitVec;
                            var12 = var19;
                        }
                    }
                }
                ++var14;
            }
            if (this.pointedEntity != null && (var12 < var4 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var9);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
        }
    }

    private void updateFovModifierHand() {
        if (this.mc.renderViewEntity instanceof EntityPlayerSP) {
            EntityPlayerSP var1 = (EntityPlayerSP)this.mc.renderViewEntity;
            this.fovMultiplierTemp = var1.getFOVMultiplier();
        } else {
            this.fovMultiplierTemp = this.mc.thePlayer.getFOVMultiplier();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (this.fovMultiplierTemp - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }

    private float getFOVModifier(float par1, boolean par2) {
        Block var61;
        if (this.debugViewDirection > 0) {
            return 90.0f;
        }
        EntityLivingBase var3 = this.mc.renderViewEntity;
        float var4 = 70.0f;
        if (par2) {
            var4 += this.mc.gameSettings.fovSetting * 40.0f;
            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * par1;
        }
        boolean zoomActive = false;
        if (this.mc.currentScreen == null) {
            zoomActive = this.mc.gameSettings.ofKeyBindZoom.getKeyCode() < 0 ? Mouse.isButtonDown((int)(this.mc.gameSettings.ofKeyBindZoom.getKeyCode() + 100)) : Keyboard.isKeyDown((int)this.mc.gameSettings.ofKeyBindZoom.getKeyCode());
        }
        if (zoomActive) {
            if (!Config.zoomMode) {
                Config.zoomMode = true;
                this.mc.gameSettings.smoothCamera = true;
            }
            if (Config.zoomMode) {
                var4 /= 4.0f;
            }
        } else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.mc.gameSettings.smoothCamera = false;
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
        }
        if (var3.getHealth() <= 0.0f) {
            float var6 = (float)var3.deathTime + par1;
            var4 /= (1.0f - 500.0f / (var6 + 500.0f)) * 2.0f + 1.0f;
        }
        if ((var61 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, par1)).getMaterial() == Material.water) {
            var4 = var4 * 60.0f / 70.0f;
        }
        return var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * par1;
    }

    private void hurtCameraEffect(float par1) {
        float var4;
        if (Resilience.getInstance().getValues().noHurtcamEnabled) {
            return;
        }
        EntityLivingBase var2 = this.mc.renderViewEntity;
        float var3 = (float)var2.hurtTime - par1;
        if (var2.getHealth() <= 0.0f) {
            var4 = (float)var2.deathTime + par1;
            GL11.glRotatef((float)(40.0f - 8000.0f / (var4 + 200.0f)), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        if (var3 >= 0.0f) {
            var3 /= (float)var2.maxHurtTime;
            var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927f);
            var4 = var2.attackedAtYaw;
            GL11.glRotatef((float)(- var4), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)((- var3) * 14.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)var4, (float)0.0f, (float)1.0f, (float)0.0f);
        }
    }

    private void setupViewBobbing(float par1) {
        if (this.mc.renderViewEntity instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)this.mc.renderViewEntity;
            float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            float var4 = - var2.distanceWalkedModified + var3 * par1;
            float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * par1;
            float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * par1;
            GL11.glTranslatef((float)(MathHelper.sin(var4 * 3.1415927f) * var5 * 0.5f), (float)(- Math.abs(MathHelper.cos(var4 * 3.1415927f) * var5)), (float)0.0f);
            GL11.glRotatef((float)(MathHelper.sin(var4 * 3.1415927f) * var5 * 3.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(Math.abs(MathHelper.cos(var4 * 3.1415927f - 0.2f) * var5) * 5.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)var6, (float)1.0f, (float)0.0f, (float)0.0f);
        }
    }

    private void orientCamera(float par1) {
        EntityLivingBase var2 = this.mc.renderViewEntity;
        float var3 = var2.yOffset - 1.62f;
        double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)par1;
        double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)par1 - (double)var3;
        double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)par1;
        GL11.glRotatef((float)(this.prevCamRoll + (this.camRoll - this.prevCamRoll) * par1), (float)0.0f, (float)0.0f, (float)1.0f);
        if (var2.isPlayerSleeping()) {
            var3 = (float)((double)var3 + 1.0);
            GL11.glTranslatef((float)0.0f, (float)0.3f, (float)0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                Block var27 = this.mc.theWorld.getBlock(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, this.mc, var2);
                } else if (var27 == Blocks.bed) {
                    int var11 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                    int var13 = var11 & 3;
                    GL11.glRotatef((float)(var13 * 90), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                GL11.glRotatef((float)(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0f), (float)0.0f, (float)-1.0f, (float)0.0f);
                GL11.glRotatef((float)(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1), (float)-1.0f, (float)0.0f, (float)0.0f);
            }
        } else if (this.mc.gameSettings.thirdPersonView > 0) {
            double var271 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * par1;
            if (this.mc.gameSettings.debugCamEnable) {
                float var28 = this.prevDebugCamYaw + (this.debugCamYaw - this.prevDebugCamYaw) * par1;
                float var281 = this.prevDebugCamPitch + (this.debugCamPitch - this.prevDebugCamPitch) * par1;
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)((float)(- var271)));
                GL11.glRotatef((float)var281, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)var28, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                float var28 = var2.rotationYaw;
                float var281 = var2.rotationPitch;
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    var281 += 180.0f;
                }
                double var14 = (double)((- MathHelper.sin(var28 / 180.0f * 3.1415927f)) * MathHelper.cos(var281 / 180.0f * 3.1415927f)) * var271;
                double var16 = (double)(MathHelper.cos(var28 / 180.0f * 3.1415927f) * MathHelper.cos(var281 / 180.0f * 3.1415927f)) * var271;
                double var18 = (double)(- MathHelper.sin(var281 / 180.0f * 3.1415927f)) * var271;
                int var20 = 0;
                while (var20 < 8) {
                    double var25;
                    float var21 = (var20 & 1) * 2 - 1;
                    float var22 = (var20 >> 1 & 1) * 2 - 1;
                    float var23 = (var20 >> 2 & 1) * 2 - 1;
                    MovingObjectPosition var24 = this.mc.theWorld.rayTraceBlocks(this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 + (double)var21, var6 + (double)var22, var8 + (double)var23), this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4 - var14 + (double)(var21 *= 0.1f) + (double)var23, var6 - var18 + (double)(var22 *= 0.1f), var8 - var16 + (double)(var23 *= 0.1f)));
                    if (var24 != null && (var25 = var24.hitVec.distanceTo(this.mc.theWorld.getWorldVec3Pool().getVecFromPool(var4, var6, var8))) < var271) {
                        var271 = var25;
                    }
                    ++var20;
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
                GL11.glRotatef((float)(var2.rotationPitch - var281), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(var2.rotationYaw - var28), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)((float)(- var271)));
                GL11.glRotatef((float)(var28 - var2.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(var281 - var2.rotationPitch), (float)1.0f, (float)0.0f, (float)0.0f);
            }
        } else {
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.1f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            GL11.glRotatef((float)(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * par1), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * par1 + 180.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        GL11.glTranslatef((float)0.0f, (float)var3, (float)0.0f);
        var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)par1;
        var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)par1 - (double)var3;
        var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)par1;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var6, var8, par1);
    }

    private void setupCameraTransform(float par1, int par2) {
        float var4;
        float clipDistance;
        this.farPlaneDistance = this.mc.gameSettings.renderDistanceChunks * 16;
        if (Config.isFogFancy()) {
            this.farPlaneDistance *= 0.95f;
        }
        if (Config.isFogFast()) {
            this.farPlaneDistance *= 0.83f;
        }
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        float var3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(- par2 * 2 - 1) * var3), (float)0.0f, (float)0.0f);
        }
        if ((clipDistance = this.farPlaneDistance * 2.0f) < 128.0f) {
            clipDistance = 128.0f;
        }
        if (this.mc.theWorld.provider.dimensionId == 1) {
            clipDistance = 256.0f;
        }
        if (this.cameraZoom != 1.0) {
            GL11.glTranslatef((float)((float)this.cameraYaw), (float)((float)(- this.cameraPitch)), (float)0.0f);
            GL11.glScaled((double)this.cameraZoom, (double)this.cameraZoom, (double)1.0);
        }
        Project.gluPerspective((float)this.getFOVModifier(par1, true), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)clipDistance);
        if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            var4 = 0.6666667f;
            GL11.glScalef((float)1.0f, (float)var4, (float)1.0f);
        }
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(par2 * 2 - 1) * 0.1f), (float)0.0f, (float)0.0f);
        }
        this.hurtCameraEffect(par1);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(par1);
        }
        float f = var4 = Resilience.getInstance().getValues().antiNauseaEnabled ? 0.0f : this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;
        if (var4 > 0.0f) {
            int var7 = 20;
            if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
                var7 = 7;
            }
            float var6 = 5.0f / (var4 * var4 + 5.0f) - var4 * 0.04f;
            var6 *= var6;
            GL11.glRotatef((float)(((float)this.rendererUpdateCount + par1) * (float)var7), (float)0.0f, (float)1.0f, (float)1.0f);
            GL11.glScalef((float)(1.0f / var6), (float)1.0f, (float)1.0f);
            GL11.glRotatef((float)((- (float)this.rendererUpdateCount + par1) * (float)var7), (float)0.0f, (float)1.0f, (float)1.0f);
        }
        this.orientCamera(par1);
        if (this.debugViewDirection > 0) {
            int var71 = this.debugViewDirection - 1;
            if (var71 == 1) {
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (var71 == 2) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (var71 == 3) {
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (var71 == 4) {
                GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (var71 == 5) {
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            }
        }
    }

    private void renderHand(float par1, int par2) {
        if (this.debugViewDirection <= 0) {
            GL11.glMatrixMode((int)5889);
            GL11.glLoadIdentity();
            float var3 = 0.07f;
            if (this.mc.gameSettings.anaglyph) {
                GL11.glTranslatef((float)((float)(- par2 * 2 - 1) * var3), (float)0.0f, (float)0.0f);
            }
            if (this.cameraZoom != 1.0) {
                GL11.glTranslatef((float)((float)this.cameraYaw), (float)((float)(- this.cameraPitch)), (float)0.0f);
                GL11.glScaled((double)this.cameraZoom, (double)this.cameraZoom, (double)1.0);
            }
            Project.gluPerspective((float)this.getFOVModifier(par1, false), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * 2.0f));
            if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
                float var4 = 0.6666667f;
                GL11.glScalef((float)1.0f, (float)var4, (float)1.0f);
            }
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            if (this.mc.gameSettings.anaglyph) {
                GL11.glTranslatef((float)((float)(par2 * 2 - 1) * 0.1f), (float)0.0f, (float)0.0f);
            }
            GL11.glPushMatrix();
            this.hurtCameraEffect(par1);
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(par1);
            }
            if (!(this.mc.gameSettings.thirdPersonView != 0 || this.mc.renderViewEntity.isPlayerSleeping() || this.mc.gameSettings.hideGUI || this.mc.playerController.enableEverythingIsScrewedUpMode())) {
                this.enableLightmap(par1);
                this.itemRenderer.renderItemInFirstPerson(par1);
                this.disableLightmap(par1);
            }
            GL11.glPopMatrix();
            if (this.mc.gameSettings.thirdPersonView == 0 && !this.mc.renderViewEntity.isPlayerSleeping()) {
                this.itemRenderer.renderOverlays(par1);
                this.hurtCameraEffect(par1);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(par1);
            }
        }
    }

    public void disableLightmap(double par1) {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable((int)3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public void enableLightmap(double par1) {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glMatrixMode((int)5890);
        GL11.glLoadIdentity();
        float var3 = 0.00390625f;
        GL11.glScalef((float)var3, (float)var3, (float)var3);
        GL11.glTranslatef((float)8.0f, (float)8.0f, (float)8.0f);
        GL11.glMatrixMode((int)5888);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10496);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10496);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    private void updateTorchFlicker() {
        this.torchFlickerDX = (float)((double)this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDY = (float)((double)this.torchFlickerDY + (Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDX = (float)((double)this.torchFlickerDX * 0.9);
        this.torchFlickerDY = (float)((double)this.torchFlickerDY * 0.9);
        this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0f;
        this.torchFlickerY += (this.torchFlickerDY - this.torchFlickerY) * 1.0f;
        this.lightmapUpdateNeeded = true;
    }

    private void updateLightmap(float par1) {
        WorldClient var2 = this.mc.theWorld;
        if (var2 != null) {
            if (CustomColorizer.updateLightmap(var2, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision))) {
                this.lightmapTexture.updateDynamicTexture();
                this.lightmapUpdateNeeded = false;
                return;
            }
            int var3 = 0;
            while (var3 < 256) {
                float var16;
                float var17;
                float var4 = var2.getSunBrightness(1.0f) * 0.95f + 0.05f;
                float var5 = var2.provider.lightBrightnessTable[var3 / 16] * var4;
                float var6 = var2.provider.lightBrightnessTable[var3 % 16] * (this.torchFlickerX * 0.1f + 1.5f);
                if (var2.lastLightningBolt > 0) {
                    var5 = var2.provider.lightBrightnessTable[var3 / 16];
                }
                float var7 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                float var8 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                float var11 = var6 * ((var6 * 0.6f + 0.4f) * 0.6f + 0.4f);
                float var12 = var6 * (var6 * var6 * 0.6f + 0.4f);
                float var13 = var7 + var6;
                float var14 = var8 + var11;
                float var15 = var5 + var12;
                var13 = var13 * 0.96f + 0.03f;
                var14 = var14 * 0.96f + 0.03f;
                var15 = var15 * 0.96f + 0.03f;
                if (this.bossColorModifier > 0.0f) {
                    var16 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * par1;
                    var13 = var13 * (1.0f - var16) + var13 * 0.7f * var16;
                    var14 = var14 * (1.0f - var16) + var14 * 0.6f * var16;
                    var15 = var15 * (1.0f - var16) + var15 * 0.6f * var16;
                }
                if (var2.provider.dimensionId == 1) {
                    var13 = 0.22f + var6 * 0.75f;
                    var14 = 0.28f + var11 * 0.75f;
                    var15 = 0.25f + var12 * 0.75f;
                }
                if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                    var16 = this.getNightVisionBrightness(this.mc.thePlayer, par1);
                    var17 = 1.0f / var13;
                    if (var17 > 1.0f / var14) {
                        var17 = 1.0f / var14;
                    }
                    if (var17 > 1.0f / var15) {
                        var17 = 1.0f / var15;
                    }
                    var13 = var13 * (1.0f - var16) + var13 * var17 * var16;
                    var14 = var14 * (1.0f - var16) + var14 * var17 * var16;
                    var15 = var15 * (1.0f - var16) + var15 * var17 * var16;
                }
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                if (var14 > 1.0f) {
                    var14 = 1.0f;
                }
                if (var15 > 1.0f) {
                    var15 = 1.0f;
                }
                var16 = this.mc.gameSettings.gammaSetting;
                var17 = 1.0f - var13;
                float var18 = 1.0f - var14;
                float var19 = 1.0f - var15;
                var17 = 1.0f - var17 * var17 * var17 * var17;
                var18 = 1.0f - var18 * var18 * var18 * var18;
                var19 = 1.0f - var19 * var19 * var19 * var19;
                var13 = var13 * (1.0f - var16) + var17 * var16;
                var14 = var14 * (1.0f - var16) + var18 * var16;
                var15 = var15 * (1.0f - var16) + var19 * var16;
                var13 = var13 * 0.96f + 0.03f;
                var14 = var14 * 0.96f + 0.03f;
                var15 = var15 * 0.96f + 0.03f;
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                if (var14 > 1.0f) {
                    var14 = 1.0f;
                }
                if (var15 > 1.0f) {
                    var15 = 1.0f;
                }
                if (var13 < 0.0f) {
                    var13 = 0.0f;
                }
                if (var14 < 0.0f) {
                    var14 = 0.0f;
                }
                if (var15 < 0.0f) {
                    var15 = 0.0f;
                }
                int var20 = 255;
                int var21 = (int)(var13 * 255.0f);
                int var22 = (int)(var14 * 255.0f);
                int var23 = (int)(var15 * 255.0f);
                this.lightmapColors[var3] = var20 << 24 | var21 << 16 | var22 << 8 | var23;
                ++var3;
            }
            this.lightmapTexture.updateDynamicTexture();
            this.lightmapUpdateNeeded = false;
        }
    }

    private float getNightVisionBrightness(EntityPlayer par1EntityPlayer, float par2) {
        int var3 = par1EntityPlayer.getActivePotionEffect(Potion.nightVision).getDuration();
        return var3 > 200 ? 1.0f : 0.7f + MathHelper.sin(((float)var3 - par2) * 3.1415927f * 0.2f) * 0.3f;
    }

    public void updateCameraAndRender(float par1) {
        this.mc.mcProfiler.startSection("lightTex");
        if (!this.initialized) {
            TextureUtils.registerResourceListener();
            ItemRendererOF world = new ItemRendererOF(this.mc);
            this.itemRenderer = world;
            RenderManager.instance.itemRenderer = world;
            this.initialized = true;
        }
        Config.checkDisplayMode();
        WorldClient world1 = this.mc.theWorld;
        if (world1 != null && Config.getNewRelease() != null) {
            String var2 = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
            String var13 = String.valueOf(var2) + " " + Config.getNewRelease();
            ChatComponentText var14 = new ChatComponentText("A new \u00a7eOptiFine\u00a7f version is available: \u00a7e" + var13 + "\u00a7f");
            this.mc.ingameGUI.getChatGUI().func_146227_a(var14);
            Config.setNewRelease(null);
        }
        if (this.mc.currentScreen instanceof GuiMainMenu) {
            this.updateMainMenu((GuiMainMenu)this.mc.currentScreen);
        }
        if (this.updatedWorld != world1) {
            RandomMobs.worldChanged(this.updatedWorld, world1);
            Config.updateThreadPriorities();
            this.lastServerTime = 0;
            this.lastServerTicks = 0;
            this.updatedWorld = world1;
        }
        RenderBlocks.fancyGrass = Config.isGrassFancy() || Config.isBetterGrassFancy();
        Blocks.leaves.func_150122_b(Config.isTreesFancy());
        if (this.lightmapUpdateNeeded) {
            this.updateLightmap(par1);
        }
        this.mc.mcProfiler.endSection();
        boolean var21 = Display.isActive();
        if (!(var21 || !this.mc.gameSettings.pauseOnLostFocus || this.mc.gameSettings.touchscreen && Mouse.isButtonDown((int)1))) {
            if (Minecraft.getSystemTime() - this.prevFrameTime > 500) {
                this.mc.displayInGameMenu();
            }
        } else {
            this.prevFrameTime = Minecraft.getSystemTime();
        }
        this.mc.mcProfiler.startSection("mouse");
        if (this.mc.inGameHasFocus && var21) {
            this.mc.mouseHelper.mouseXYChange();
            float var132 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float var141 = var132 * var132 * var132 * 8.0f;
            float var15 = (float)this.mc.mouseHelper.deltaX * var141;
            float var16 = (float)this.mc.mouseHelper.deltaY * var141;
            int var18 = 1;
            if (this.mc.gameSettings.invertMouse) {
                var18 = -1;
            }
            if (this.mc.gameSettings.smoothCamera) {
                this.smoothCamYaw += var15;
                this.smoothCamPitch += var16;
                float var17 = par1 - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = par1;
                var15 = this.smoothCamFilterX * var17;
                var16 = this.smoothCamFilterY * var17;
                this.mc.thePlayer.setAngles(var15, var16 * (float)var18);
            } else {
                this.mc.thePlayer.setAngles(var15, var16 * (float)var18);
            }
        }
        this.mc.mcProfiler.endSection();
        if (!this.mc.skipRenderWorld) {
            anaglyphEnable = this.mc.gameSettings.anaglyph;
            final ScaledResolution var133 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var142 = var133.getScaledWidth();
            int var151 = var133.getScaledHeight();
            final int var161 = Mouse.getX() * var142 / this.mc.displayWidth;
            final int var181 = var151 - Mouse.getY() * var151 / this.mc.displayHeight - 1;
            int var171 = this.mc.gameSettings.limitFramerate;
            if (this.mc.theWorld != null) {
                this.mc.mcProfiler.startSection("level");
                if (this.mc.isFramerateLimitBelowMax()) {
                    this.renderWorld(par1, this.renderEndNanoTime + (long)(1000000000 / var171));
                } else {
                    this.renderWorld(par1, 0);
                }
                if (OpenGlHelper.shadersSupported) {
                    if (this.theShaderGroup != null) {
                        GL11.glMatrixMode((int)5890);
                        GL11.glPushMatrix();
                        GL11.glLoadIdentity();
                        this.theShaderGroup.loadShaderGroup(par1);
                        GL11.glPopMatrix();
                    }
                    this.mc.getFramebuffer().bindFramebuffer(true);
                }
                this.renderEndNanoTime = System.nanoTime();
                this.mc.mcProfiler.endStartSection("gui");
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    GL11.glAlphaFunc((int)516, (float)0.1f);
                    this.mc.ingameGUI.renderGameOverlay(par1, this.mc.currentScreen != null, var161, var181);
                }
                this.mc.mcProfiler.endSection();
            } else {
                GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
                GL11.glMatrixMode((int)5889);
                GL11.glLoadIdentity();
                GL11.glMatrixMode((int)5888);
                GL11.glLoadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }
            if (this.mc.currentScreen != null) {
                GL11.glClear((int)256);
                try {
                    this.mc.currentScreen.drawScreen(var161, var181, par1);
                }
                catch (Throwable var131) {
                    CrashReport var10 = CrashReport.makeCrashReport(var131, "Rendering screen");
                    CrashReportCategory var11 = var10.makeCategory("Screen render details");
                    var11.addCrashSectionCallable("Screen name", new Callable(){
                        private static final String __OBFID = "CL_00000948";

                        public String call() {
                            return EntityRenderer.access$0((EntityRenderer)EntityRenderer.this).currentScreen.getClass().getCanonicalName();
                        }
                    });
                    var11.addCrashSectionCallable("Mouse location", new Callable(){
                        private static final String __OBFID = "CL_00000950";

                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", var161, var181, Mouse.getX(), Mouse.getY());
                        }
                    });
                    var11.addCrashSectionCallable("Screen size", new Callable(){
                        private static final String __OBFID = "CL_00000951";

                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", var133.getScaledWidth(), var133.getScaledHeight(), EntityRenderer.access$0((EntityRenderer)EntityRenderer.this).displayWidth, EntityRenderer.access$0((EntityRenderer)EntityRenderer.this).displayHeight, var133.getScaleFactor());
                        }
                    });
                    throw new ReportedException(var10);
                }
            }
        }
        this.waitForServerThread();
        if (this.mc.gameSettings.showDebugInfo != this.lastShowDebugInfo) {
            this.showExtendedDebugInfo = this.mc.gameSettings.showDebugProfilerChart;
            this.lastShowDebugInfo = this.mc.gameSettings.showDebugInfo;
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.showLagometer(this.mc.mcProfiler.timeTickNano, this.mc.mcProfiler.timeUpdateChunksNano);
        }
        if (this.mc.gameSettings.ofProfiler) {
            this.mc.gameSettings.showDebugProfilerChart = true;
        }
    }

    public void renderWorld(float par1, long par2) {
        this.mc.mcProfiler.startSection("lightTex");
        if (this.lightmapUpdateNeeded) {
            this.updateLightmap(par1);
        }
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.1f);
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        this.mc.mcProfiler.endStartSection("pick");
        this.getMouseOver(par1);
        EntityLivingBase var4 = this.mc.renderViewEntity;
        RenderGlobal var5 = this.mc.renderGlobal;
        EffectRenderer var6 = this.mc.effectRenderer;
        double var7 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)par1;
        double var9 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)par1;
        double var11 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)par1;
        this.mc.mcProfiler.endStartSection("center");
        int var13 = 0;
        while (var13 < 2) {
            EntityPlayer var171;
            if (this.mc.gameSettings.anaglyph) {
                anaglyphField = var13;
                if (anaglyphField == 0) {
                    GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)false);
                } else {
                    GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)false);
                }
            }
            this.mc.mcProfiler.endStartSection("clear");
            GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
            this.updateFogColor(par1);
            GL11.glClear((int)16640);
            GL11.glEnable((int)2884);
            this.mc.mcProfiler.endStartSection("camera");
            boolean wasBobbing = this.mc.gameSettings.viewBobbing;
            this.mc.gameSettings.viewBobbing = false;
            this.setupCameraTransform(par1, var13);
            this.mc.gameSettings.viewBobbing = wasBobbing;
            ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
            this.mc.mcProfiler.endStartSection("frustrum");
            ClippingHelperImpl.getInstance();
            if (!(Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled())) {
                GL11.glDisable((int)3042);
            } else {
                this.setupFog(-1, par1);
                this.mc.mcProfiler.endStartSection("sky");
                var5.renderSky(par1);
            }
            GL11.glEnable((int)2912);
            this.setupFog(1, par1);
            if (this.mc.gameSettings.ambientOcclusion != 0) {
                GL11.glShadeModel((int)7425);
            }
            this.mc.mcProfiler.endStartSection("culling");
            Frustrum var14 = new Frustrum();
            var14.setPosition(var7, var9, var11);
            this.mc.renderGlobal.clipRenderersByFrustum(var14, par1);
            if (var13 == 0) {
                this.mc.mcProfiler.endStartSection("updatechunks");
                while (!this.mc.renderGlobal.updateRenderers(var4, false) && par2 != 0) {
                    long var17 = par2 - System.nanoTime();
                    if (var17 < 0 || var17 > 1000000000) break;
                }
            }
            if (var4.posY < 128.0) {
                this.renderCloudsCheck(var5, par1);
            }
            if (Resilience.getInstance().getValues().wireFrameEnabled) {
                RenderUtils.setup3DLightlessModel();
                GL11.glPolygonMode((int)1032, (int)6913);
                RenderUtils.shutdown3DLightlessModel();
            }
            this.mc.mcProfiler.endStartSection("prepareterrain");
            this.setupFog(0, par1);
            GL11.glEnable((int)2912);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("terrain");
            GL11.glMatrixMode((int)5888);
            GL11.glPushMatrix();
            var5.sortAndRender(var4, 0, par1);
            GL11.glShadeModel((int)7424);
            boolean hasForge = Reflector.ForgeHooksClient.exists();
            if (Resilience.getInstance().getValues().wireFrameEnabled) {
                RenderUtils.setup3DLightlessModel();
                GL11.glPolygonMode((int)1032, (int)6914);
                RenderUtils.shutdown3DLightlessModel();
            }
            if (this.debugViewDirection == 0) {
                GL11.glMatrixMode((int)5888);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                RenderHelper.enableStandardItemLighting();
                this.mc.mcProfiler.endStartSection("entities");
                if (hasForge) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 0);
                }
                var5.renderEntities(var4, var14, par1);
                if (hasForge) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
                }
                RenderHelper.disableStandardItemLighting();
                GL11.glAlphaFunc((int)516, (float)0.1f);
                GL11.glMatrixMode((int)5888);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                if (this.mc.objectMouseOver != null && var4.isInsideOfMaterial(Material.water) && var4 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI) {
                    var171 = (EntityPlayer)var4;
                    GL11.glDisable((int)3008);
                    this.mc.mcProfiler.endStartSection("outline");
                    if (!(hasForge && Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var5, var171, this.mc.objectMouseOver, 0, var171.inventory.getCurrentItem(), Float.valueOf(par1)) || this.mc.gameSettings.hideGUI)) {
                        var5.drawSelectionBox(var171, this.mc.objectMouseOver, 0, par1);
                    }
                    GL11.glEnable((int)3008);
                }
            }
            GL11.glMatrixMode((int)5888);
            GL11.glPopMatrix();
            if (this.cameraZoom == 1.0 && var4 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI && this.mc.objectMouseOver != null && !var4.isInsideOfMaterial(Material.water)) {
                var171 = (EntityPlayer)var4;
                GL11.glDisable((int)3008);
                this.mc.mcProfiler.endStartSection("outline");
                if (!(hasForge && Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var5, var171, this.mc.objectMouseOver, 0, var171.inventory.getCurrentItem(), Float.valueOf(par1)) || this.mc.gameSettings.hideGUI)) {
                    var5.drawSelectionBox(var171, this.mc.objectMouseOver, 0, par1);
                }
                GL11.glEnable((int)3008);
            }
            this.mc.mcProfiler.endStartSection("destroyProgress");
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            var5.drawBlockDamageTexture(Tessellator.instance, var4, par1);
            GL11.glDisable((int)3042);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2884);
            this.mc.mcProfiler.endStartSection("weather");
            this.renderRainSnow(par1);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2884);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc((int)516, (float)0.1f);
            this.setupFog(0, par1);
            GL11.glEnable((int)3042);
            GL11.glDepthMask((boolean)false);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            WrUpdates.resumeBackgroundUpdates();
            if (Config.isWaterFancy()) {
                this.mc.mcProfiler.endStartSection("water");
                if (this.mc.gameSettings.ambientOcclusion != 0) {
                    GL11.glShadeModel((int)7425);
                }
                GL11.glEnable((int)3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                if (this.mc.gameSettings.anaglyph) {
                    if (anaglyphField == 0) {
                        GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)true);
                    } else {
                        GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)true);
                    }
                    var5.renderAllSortedRenderers(1, par1);
                } else {
                    var5.renderAllSortedRenderers(1, par1);
                }
                GL11.glDisable((int)3042);
                GL11.glShadeModel((int)7424);
            } else {
                this.mc.mcProfiler.endStartSection("water");
                var5.renderAllSortedRenderers(1, par1);
            }
            WrUpdates.pauseBackgroundUpdates();
            if (hasForge && this.debugViewDirection == 0) {
                RenderHelper.enableStandardItemLighting();
                this.mc.mcProfiler.endStartSection("entities");
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 1);
                this.mc.renderGlobal.renderEntities(var4, var14, par1);
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
                RenderHelper.disableStandardItemLighting();
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)2912);
            if (var4.posY >= 128.0 + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0f)) {
                this.mc.mcProfiler.endStartSection("aboveClouds");
                this.renderCloudsCheck(var5, par1);
            }
            this.enableLightmap(par1);
            this.mc.mcProfiler.endStartSection("litParticles");
            RenderHelper.enableStandardItemLighting();
            var6.renderLitParticles(var4, par1);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0, par1);
            this.mc.mcProfiler.endStartSection("particles");
            var6.renderParticles(var4, par1);
            this.disableLightmap(par1);
            if (hasForge) {
                this.mc.mcProfiler.endStartSection("FRenderLast");
                Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, var5, Float.valueOf(par1));
            }
            EventOnRender renderEvent = new EventOnRender();
            renderEvent.onEvent();
            this.mc.mcProfiler.endStartSection("hand");
            if (this.cameraZoom == 1.0) {
                GL11.glClear((int)256);
                this.renderHand(par1, var13);
            }
            if (!this.mc.gameSettings.anaglyph) {
                this.mc.mcProfiler.endSection();
                return;
            }
            ++var13;
        }
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
        this.mc.mcProfiler.endSection();
    }

    private void renderCloudsCheck(RenderGlobal par1RenderGlobal, float par2) {
        if (this.mc.gameSettings.shouldRenderClouds()) {
            this.mc.mcProfiler.endStartSection("clouds");
            GL11.glPushMatrix();
            this.setupFog(0, par2);
            GL11.glEnable((int)2912);
            par1RenderGlobal.renderClouds(par2);
            GL11.glDisable((int)2912);
            this.setupFog(1, par2);
            GL11.glPopMatrix();
        }
    }

    private void addRainParticles() {
        float var1 = this.mc.theWorld.getRainStrength(1.0f);
        if (!Config.isRainFancy()) {
            var1 /= 2.0f;
        }
        if (var1 != 0.0f && Config.isRainSplash()) {
            this.random.setSeed((long)this.rendererUpdateCount * 312987231);
            EntityLivingBase var2 = this.mc.renderViewEntity;
            WorldClient var3 = this.mc.theWorld;
            int var4 = MathHelper.floor_double(var2.posX);
            int var5 = MathHelper.floor_double(var2.posY);
            int var6 = MathHelper.floor_double(var2.posZ);
            int var7 = 10;
            double var8 = 0.0;
            double var10 = 0.0;
            double var12 = 0.0;
            int var14 = 0;
            int var15 = (int)(100.0f * var1 * var1);
            if (this.mc.gameSettings.particleSetting == 1) {
                var15 >>= 1;
            } else if (this.mc.gameSettings.particleSetting == 2) {
                var15 = 0;
            }
            int var16 = 0;
            while (var16 < var15) {
                int var17 = var4 + this.random.nextInt(var7) - this.random.nextInt(var7);
                int var18 = var6 + this.random.nextInt(var7) - this.random.nextInt(var7);
                int var19 = var3.getPrecipitationHeight(var17, var18);
                Block var20 = var3.getBlock(var17, var19 - 1, var18);
                BiomeGenBase var21 = var3.getBiomeGenForCoords(var17, var18);
                if (var19 <= var5 + var7 && var19 >= var5 - var7 && var21.canSpawnLightningBolt() && var21.getFloatTemperature(var17, var19, var18) >= 0.15f) {
                    float var22 = this.random.nextFloat();
                    float var23 = this.random.nextFloat();
                    if (var20.getMaterial() == Material.lava) {
                        this.mc.effectRenderer.addEffect(new EntitySmokeFX(var3, (float)var17 + var22, (double)((float)var19 + 0.1f) - var20.getBlockBoundsMinY(), (float)var18 + var23, 0.0, 0.0, 0.0));
                    } else if (var20.getMaterial() != Material.air) {
                        if (this.random.nextInt(++var14) == 0) {
                            var8 = (float)var17 + var22;
                            var10 = (double)((float)var19 + 0.1f) - var20.getBlockBoundsMinY();
                            var12 = (float)var18 + var23;
                        }
                        EntityRainFX fx = new EntityRainFX(var3, (float)var17 + var22, (double)((float)var19 + 0.1f) - var20.getBlockBoundsMinY(), (float)var18 + var23);
                        CustomColorizer.updateWaterFX(fx, var3);
                        this.mc.effectRenderer.addEffect(fx);
                    }
                }
                ++var16;
            }
            if (var14 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
                this.rainSoundCounter = 0;
                if (var10 > var2.posY + 1.0 && var3.getPrecipitationHeight(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posZ)) > MathHelper.floor_double(var2.posY)) {
                    this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.1f, 0.5f, false);
                } else {
                    this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.2f, 1.0f, false);
                }
            }
        }
    }

    protected void renderRainSnow(float par1) {
        WorldProvider var2;
        Object var41;
        if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists() && (var41 = Reflector.call(var2 = this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0])) != null) {
            Reflector.callVoid(var41, Reflector.IRenderHandler_render, Float.valueOf(par1), this.mc.theWorld, this.mc);
            return;
        }
        float var411 = this.mc.theWorld.getRainStrength(par1);
        if (var411 > 0.0f) {
            this.enableLightmap(par1);
            if (this.rainXCoords == null) {
                this.rainXCoords = new float[1024];
                this.rainYCoords = new float[1024];
                int var421 = 0;
                while (var421 < 32) {
                    int var42 = 0;
                    while (var42 < 32) {
                        float var43 = var42 - 16;
                        float var44 = var421 - 16;
                        float var45 = MathHelper.sqrt_float(var43 * var43 + var44 * var44);
                        this.rainXCoords[var421 << 5 | var42] = (- var44) / var45;
                        this.rainYCoords[var421 << 5 | var42] = var43 / var45;
                        ++var42;
                    }
                    ++var421;
                }
            }
            if (Config.isRainOff()) {
                return;
            }
            EntityLivingBase var431 = this.mc.renderViewEntity;
            WorldClient var441 = this.mc.theWorld;
            int var451 = MathHelper.floor_double(var431.posX);
            int var461 = MathHelper.floor_double(var431.posY);
            int var471 = MathHelper.floor_double(var431.posZ);
            Tessellator var8 = Tessellator.instance;
            GL11.glDisable((int)2884);
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc((int)516, (float)0.1f);
            double var9 = var431.lastTickPosX + (var431.posX - var431.lastTickPosX) * (double)par1;
            double var11 = var431.lastTickPosY + (var431.posY - var431.lastTickPosY) * (double)par1;
            double var13 = var431.lastTickPosZ + (var431.posZ - var431.lastTickPosZ) * (double)par1;
            int var15 = MathHelper.floor_double(var11);
            int var16 = 5;
            if (Config.isRainFancy()) {
                var16 = 10;
            }
            boolean var17 = false;
            int var18 = -1;
            float var19 = (float)this.rendererUpdateCount + par1;
            if (Config.isRainFancy()) {
                var16 = 10;
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            var17 = false;
            int var20 = var471 - var16;
            while (var20 <= var471 + var16) {
                int var21 = var451 - var16;
                while (var21 <= var451 + var16) {
                    int var22 = (var20 - var471 + 16) * 32 + var21 - var451 + 16;
                    float var23 = this.rainXCoords[var22] * 0.5f;
                    float var24 = this.rainYCoords[var22] * 0.5f;
                    BiomeGenBase var25 = var441.getBiomeGenForCoords(var21, var20);
                    if (var25.canSpawnLightningBolt() || var25.getEnableSnow()) {
                        int var26 = var441.getPrecipitationHeight(var21, var20);
                        int var27 = var461 - var16;
                        int var28 = var461 + var16;
                        if (var27 < var26) {
                            var27 = var26;
                        }
                        if (var28 < var26) {
                            var28 = var26;
                        }
                        float var29 = 1.0f;
                        int var30 = var26;
                        if (var26 < var15) {
                            var30 = var15;
                        }
                        if (var27 != var28) {
                            double var35;
                            float var32;
                            this.random.setSeed(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761);
                            float var31 = var25.getFloatTemperature(var21, var27, var20);
                            if (var441.getWorldChunkManager().getTemperatureAtHeight(var31, var26) >= 0.15f) {
                                if (var18 != 0) {
                                    if (var18 >= 0) {
                                        var8.draw();
                                    }
                                    var18 = 0;
                                    this.mc.getTextureManager().bindTexture(locationRainPng);
                                    var8.startDrawingQuads();
                                }
                                var32 = ((float)(this.rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 31) + par1) / 32.0f * (3.0f + this.random.nextFloat());
                                double var46 = (double)((float)var21 + 0.5f) - var431.posX;
                                var35 = (double)((float)var20 + 0.5f) - var431.posZ;
                                float var47 = MathHelper.sqrt_double(var46 * var46 + var35 * var35) / (float)var16;
                                float var38 = 1.0f;
                                var8.setBrightness(var441.getLightBrightnessForSkyBlocks(var21, var30, var20, 0));
                                var8.setColorRGBA_F(var38, var38, var38, ((1.0f - var47 * var47) * 0.5f + 0.5f) * var411);
                                var8.setTranslation((- var9) * 1.0, (- var11) * 1.0, (- var13) * 1.0);
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5, var27, (double)((float)var20 - var24) + 0.5, 0.0f * var29, (float)var27 * var29 / 4.0f + var32 * var29);
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5, var27, (double)((float)var20 + var24) + 0.5, 1.0f * var29, (float)var27 * var29 / 4.0f + var32 * var29);
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5, var28, (double)((float)var20 + var24) + 0.5, 1.0f * var29, (float)var28 * var29 / 4.0f + var32 * var29);
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5, var28, (double)((float)var20 - var24) + 0.5, 0.0f * var29, (float)var28 * var29 / 4.0f + var32 * var29);
                                var8.setTranslation(0.0, 0.0, 0.0);
                            } else {
                                if (var18 != 1) {
                                    if (var18 >= 0) {
                                        var8.draw();
                                    }
                                    var18 = 1;
                                    this.mc.getTextureManager().bindTexture(locationSnowPng);
                                    var8.startDrawingQuads();
                                }
                                var32 = ((float)(this.rendererUpdateCount & 511) + par1) / 512.0f;
                                float var48 = this.random.nextFloat() + var19 * 0.01f * (float)this.random.nextGaussian();
                                float var34 = this.random.nextFloat() + var19 * (float)this.random.nextGaussian() * 0.001f;
                                var35 = (double)((float)var21 + 0.5f) - var431.posX;
                                double var49 = (double)((float)var20 + 0.5f) - var431.posZ;
                                float var39 = MathHelper.sqrt_double(var35 * var35 + var49 * var49) / (float)var16;
                                float var40 = 1.0f;
                                var8.setBrightness((var441.getLightBrightnessForSkyBlocks(var21, var30, var20, 0) * 3 + 15728880) / 4);
                                var8.setColorRGBA_F(var40, var40, var40, ((1.0f - var39 * var39) * 0.3f + 0.5f) * var411);
                                var8.setTranslation((- var9) * 1.0, (- var11) * 1.0, (- var13) * 1.0);
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5, var27, (double)((float)var20 - var24) + 0.5, 0.0f * var29 + var48, (float)var27 * var29 / 4.0f + var32 * var29 + var34);
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5, var27, (double)((float)var20 + var24) + 0.5, 1.0f * var29 + var48, (float)var27 * var29 / 4.0f + var32 * var29 + var34);
                                var8.addVertexWithUV((double)((float)var21 + var23) + 0.5, var28, (double)((float)var20 + var24) + 0.5, 1.0f * var29 + var48, (float)var28 * var29 / 4.0f + var32 * var29 + var34);
                                var8.addVertexWithUV((double)((float)var21 - var23) + 0.5, var28, (double)((float)var20 - var24) + 0.5, 0.0f * var29 + var48, (float)var28 * var29 / 4.0f + var32 * var29 + var34);
                                var8.setTranslation(0.0, 0.0, 0.0);
                            }
                        }
                    }
                    ++var21;
                }
                ++var20;
            }
            if (var18 >= 0) {
                var8.draw();
            }
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glAlphaFunc((int)516, (float)0.1f);
            this.disableLightmap(par1);
        }
    }

    public void setupOverlayRendering() {
        ScaledResolution var1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)var1.getScaledWidth_double(), (double)var1.getScaledHeight_double(), (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    private void updateFogColor(float par1) {
        float var17;
        float var22;
        float var11;
        WorldClient var2 = this.mc.theWorld;
        EntityLivingBase var3 = this.mc.renderViewEntity;
        float var4 = 0.25f + 0.75f * (float)this.mc.gameSettings.renderDistanceChunks / 16.0f;
        var4 = 1.0f - (float)Math.pow(var4, 0.25);
        Vec3 var5 = var2.getSkyColor(this.mc.renderViewEntity, par1);
        var5 = CustomColorizer.getWorldSkyColor(var5, var2, this.mc.renderViewEntity, par1);
        float var6 = (float)var5.xCoord;
        float var7 = (float)var5.yCoord;
        float var8 = (float)var5.zCoord;
        Vec3 var9 = var2.getFogColor(par1);
        var9 = CustomColorizer.getWorldFogColor(var9, var2, par1);
        this.fogColorRed = (float)var9.xCoord;
        this.fogColorGreen = (float)var9.yCoord;
        this.fogColorBlue = (float)var9.zCoord;
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            float[] var20;
            Vec3 var19 = MathHelper.sin(var2.getCelestialAngleRadians(par1)) > 0.0f ? var2.getWorldVec3Pool().getVecFromPool(-1.0, 0.0, 0.0) : var2.getWorldVec3Pool().getVecFromPool(1.0, 0.0, 0.0);
            var11 = (float)var3.getLook(par1).dotProduct(var19);
            if (var11 < 0.0f) {
                var11 = 0.0f;
            }
            if (var11 > 0.0f && (var20 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(par1), par1)) != null) {
                this.fogColorRed = this.fogColorRed * (1.0f - var11) + var20[0] * (var11 *= var20[3]);
                this.fogColorGreen = this.fogColorGreen * (1.0f - var11) + var20[1] * var11;
                this.fogColorBlue = this.fogColorBlue * (1.0f - var11) + var20[2] * var11;
            }
        }
        this.fogColorRed += (var6 - this.fogColorRed) * var4;
        this.fogColorGreen += (var7 - this.fogColorGreen) * var4;
        this.fogColorBlue += (var8 - this.fogColorBlue) * var4;
        float var191 = var2.getRainStrength(par1);
        if (var191 > 0.0f) {
            var11 = 1.0f - var191 * 0.5f;
            float var201 = 1.0f - var191 * 0.4f;
            this.fogColorRed *= var11;
            this.fogColorGreen *= var11;
            this.fogColorBlue *= var201;
        }
        if ((var11 = var2.getWeightedThunderStrength(par1)) > 0.0f) {
            float var201 = 1.0f - var11 * 0.5f;
            this.fogColorRed *= var201;
            this.fogColorGreen *= var201;
            this.fogColorBlue *= var201;
        }
        Block var21 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, par1);
        if (this.cloudFog) {
            Vec3 fogYFactor = var2.getCloudColour(par1);
            this.fogColorRed = (float)fogYFactor.xCoord;
            this.fogColorGreen = (float)fogYFactor.yCoord;
            this.fogColorBlue = (float)fogYFactor.zCoord;
        } else if (var21.getMaterial() == Material.water) {
            var22 = (float)EnchantmentHelper.getRespiration(var3) * 0.2f;
            this.fogColorRed = 0.02f + var22;
            this.fogColorGreen = 0.02f + var22;
            this.fogColorBlue = 0.2f + var22;
            Vec3 fogYFactor = CustomColorizer.getUnderwaterColor(this.mc.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0, this.mc.renderViewEntity.posZ);
            if (fogYFactor != null) {
                this.fogColorRed = (float)fogYFactor.xCoord;
                this.fogColorGreen = (float)fogYFactor.yCoord;
                this.fogColorBlue = (float)fogYFactor.zCoord;
            }
        } else if (var21.getMaterial() == Material.lava) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        var22 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * par1;
        this.fogColorRed *= var22;
        this.fogColorGreen *= var22;
        this.fogColorBlue *= var22;
        double fogYFactor1 = var2.provider.getVoidFogYFactor();
        if (!Config.isDepthFog()) {
            fogYFactor1 = 1.0;
        }
        double var14 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)par1) * fogYFactor1;
        if (var3.isPotionActive(Potion.blindness) && !Resilience.getInstance().getValues().antiBlindessEnabled) {
            int var23 = var3.getActivePotionEffect(Potion.blindness).getDuration();
            var14 = var23 < 20 ? (var14 *= (double)(1.0f - (float)var23 / 20.0f)) : 0.0;
        }
        if (var14 < 1.0) {
            if (var14 < 0.0) {
                var14 = 0.0;
            }
            var14 *= var14;
            this.fogColorRed = (float)((double)this.fogColorRed * var14);
            this.fogColorGreen = (float)((double)this.fogColorGreen * var14);
            this.fogColorBlue = (float)((double)this.fogColorBlue * var14);
        }
        if (this.bossColorModifier > 0.0f) {
            float var231 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * par1;
            this.fogColorRed = this.fogColorRed * (1.0f - var231) + this.fogColorRed * 0.7f * var231;
            this.fogColorGreen = this.fogColorGreen * (1.0f - var231) + this.fogColorGreen * 0.6f * var231;
            this.fogColorBlue = this.fogColorBlue * (1.0f - var231) + this.fogColorBlue * 0.6f * var231;
        }
        if (var3.isPotionActive(Potion.nightVision)) {
            float var231 = this.getNightVisionBrightness(this.mc.thePlayer, par1);
            var17 = 1.0f / this.fogColorRed;
            if (var17 > 1.0f / this.fogColorGreen) {
                var17 = 1.0f / this.fogColorGreen;
            }
            if (var17 > 1.0f / this.fogColorBlue) {
                var17 = 1.0f / this.fogColorBlue;
            }
            this.fogColorRed = this.fogColorRed * (1.0f - var231) + this.fogColorRed * var17 * var231;
            this.fogColorGreen = this.fogColorGreen * (1.0f - var231) + this.fogColorGreen * var17 * var231;
            this.fogColorBlue = this.fogColorBlue * (1.0f - var231) + this.fogColorBlue * var17 * var231;
        }
        if (this.mc.gameSettings.anaglyph) {
            float var231 = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            var17 = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            float var18 = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = var231;
            this.fogColorGreen = var17;
            this.fogColorBlue = var18;
        }
        GL11.glClearColor((float)this.fogColorRed, (float)this.fogColorGreen, (float)this.fogColorBlue, (float)0.0f);
    }

    private void setupFog(int par1, float par2) {
        EntityLivingBase var3 = this.mc.renderViewEntity;
        boolean var4 = false;
        this.fogStandard = false;
        if (var3 instanceof EntityPlayer) {
            var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
        }
        if (par1 == 999) {
            GL11.glFog((int)2918, (FloatBuffer)this.setFogColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glFogi((int)2917, (int)9729);
            GL11.glFogf((int)2915, (float)0.0f);
            GL11.glFogf((int)2916, (float)8.0f);
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi((int)34138, (int)34139);
            }
            GL11.glFogf((int)2915, (float)0.0f);
        } else {
            GL11.glFog((int)2918, (FloatBuffer)this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
            GL11.glNormal3f((float)0.0f, (float)-1.0f, (float)0.0f);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Block var5 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, par2);
            if (var3.isPotionActive(Potion.blindness) && !Resilience.getInstance().getValues().antiBlindessEnabled) {
                float var6 = 5.0f;
                int var10 = var3.getActivePotionEffect(Potion.blindness).getDuration();
                if (var10 < 20) {
                    var6 = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - (float)var10 / 20.0f);
                }
                GL11.glFogi((int)2917, (int)9729);
                if (par1 < 0) {
                    GL11.glFogf((int)2915, (float)0.0f);
                    GL11.glFogf((int)2916, (float)(var6 * 0.8f));
                } else {
                    GL11.glFogf((int)2915, (float)(var6 * 0.25f));
                    GL11.glFogf((int)2916, (float)var6);
                }
                if (Config.isFogFancy()) {
                    GL11.glFogi((int)34138, (int)34139);
                }
            } else if (this.cloudFog) {
                GL11.glFogi((int)2917, (int)2048);
                GL11.glFogf((int)2914, (float)0.1f);
            } else if (var5.getMaterial() == Material.water) {
                GL11.glFogi((int)2917, (int)2048);
                if (var3.isPotionActive(Potion.waterBreathing)) {
                    GL11.glFogf((int)2914, (float)0.05f);
                } else {
                    GL11.glFogf((int)2914, (float)(0.1f - (float)EnchantmentHelper.getRespiration(var3) * 0.03f));
                }
                if (Config.isClearWater()) {
                    GL11.glFogf((int)2914, (float)0.02f);
                }
            } else if (var5.getMaterial() == Material.lava) {
                GL11.glFogi((int)2917, (int)2048);
                GL11.glFogf((int)2914, (float)2.0f);
            } else {
                double var101;
                float var6 = this.farPlaneDistance;
                this.fogStandard = true;
                if (Config.isDepthFog() && this.mc.theWorld.provider.getWorldHasVoidParticles() && !var4 && (var101 = (double)((var3.getBrightnessForRender(par2) & 15728640) >> 20) / 16.0 + (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)par2 + 4.0) / 32.0) < 1.0) {
                    float var9;
                    if (var101 < 0.0) {
                        var101 = 0.0;
                    }
                    if ((var9 = 100.0f * (float)(var101 *= var101)) < 5.0f) {
                        var9 = 5.0f;
                    }
                    if (var6 > var9) {
                        var6 = var9;
                    }
                }
                GL11.glFogi((int)2917, (int)9729);
                if (par1 < 0) {
                    GL11.glFogf((int)2915, (float)0.0f);
                    GL11.glFogf((int)2916, (float)var6);
                } else {
                    GL11.glFogf((int)2915, (float)(var6 * Config.getFogStart()));
                    GL11.glFogf((int)2916, (float)var6);
                }
                if (GLContext.getCapabilities().GL_NV_fog_distance) {
                    if (Config.isFogFancy()) {
                        GL11.glFogi((int)34138, (int)34139);
                    }
                    if (Config.isFogFast()) {
                        GL11.glFogi((int)34138, (int)34140);
                    }
                }
                if (this.mc.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
                    var6 = this.farPlaneDistance;
                    GL11.glFogf((int)2915, (float)(var6 * 0.05f));
                    GL11.glFogf((int)2916, (float)var6);
                }
            }
            GL11.glEnable((int)2903);
            GL11.glColorMaterial((int)1028, (int)4608);
        }
    }

    private FloatBuffer setFogColorBuffer(float par1, float par2, float par3, float par4) {
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(par1).put(par2).put(par3).put(par4);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }

    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }

    private void waitForServerThread() {
        this.serverWaitTimeCurrent = 0;
        if (!Config.isSmoothWorld()) {
            this.lastServerTime = 0;
            this.lastServerTicks = 0;
        } else if (this.mc.getIntegratedServer() != null) {
            IntegratedServer srv = this.mc.getIntegratedServer();
            boolean paused = this.mc.func_147113_T();
            if (!paused && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (this.serverWaitTime > 0) {
                    Config.sleep(this.serverWaitTime);
                    this.serverWaitTimeCurrent = this.serverWaitTime;
                }
                long timeNow = System.nanoTime() / 1000000;
                if (this.lastServerTime != 0 && this.lastServerTicks != 0) {
                    long timeDiff = timeNow - this.lastServerTime;
                    if (timeDiff < 0) {
                        this.lastServerTime = timeNow;
                        timeDiff = 0;
                    }
                    if (timeDiff >= 50) {
                        this.lastServerTime = timeNow;
                        int ticks = srv.getTickCounter();
                        int tickDiff = ticks - this.lastServerTicks;
                        if (tickDiff < 0) {
                            this.lastServerTicks = ticks;
                            tickDiff = 0;
                        }
                        if (tickDiff < 1 && this.serverWaitTime < 100) {
                            this.serverWaitTime += 2;
                        }
                        if (tickDiff > 1 && this.serverWaitTime > 0) {
                            --this.serverWaitTime;
                        }
                        this.lastServerTicks = ticks;
                    }
                } else {
                    this.lastServerTime = timeNow;
                    this.lastServerTicks = srv.getTickCounter();
                    this.avgServerTickDiff = 1.0f;
                    this.avgServerTimeDiff = 50.0f;
                }
            } else {
                if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                    Config.sleep(20);
                }
                this.lastServerTime = 0;
                this.lastServerTicks = 0;
            }
        }
    }

    private void showLagometer(long tickTimeNano, long chunkTimeNano) {
        if (this.mc.gameSettings.ofLagometer || this.showExtendedDebugInfo) {
            if (this.prevFrameTimeNano == -1) {
                this.prevFrameTimeNano = System.nanoTime();
            }
            long timeNowNano = System.nanoTime();
            int currFrameIndex = this.numRecordedFrameTimes & this.frameTimes.length - 1;
            this.tickTimes[currFrameIndex] = tickTimeNano;
            this.chunkTimes[currFrameIndex] = chunkTimeNano;
            this.serverTimes[currFrameIndex] = this.serverWaitTimeCurrent;
            this.frameTimes[currFrameIndex] = timeNowNano - this.prevFrameTimeNano;
            ++this.numRecordedFrameTimes;
            this.prevFrameTimeNano = timeNowNano;
            GL11.glClear((int)256);
            GL11.glMatrixMode((int)5889);
            GL11.glPushMatrix();
            GL11.glEnable((int)2903);
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)this.mc.displayWidth, (double)this.mc.displayHeight, (double)0.0, (double)1000.0, (double)3000.0);
            GL11.glMatrixMode((int)5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(1);
            int frameNum = 0;
            while (frameNum < this.frameTimes.length) {
                int lum = (frameNum - this.numRecordedFrameTimes & this.frameTimes.length - 1) * 255 / this.frameTimes.length;
                long heightFrame = this.frameTimes[frameNum] / 200000;
                float baseHeight = this.mc.displayHeight;
                tessellator.setColorOpaque_I(-16777216 + lum * 256);
                tessellator.addVertex((float)frameNum + 0.5f, baseHeight - (float)heightFrame + 0.5f, 0.0);
                tessellator.addVertex((float)frameNum + 0.5f, baseHeight + 0.5f, 0.0);
                long heightTick = this.tickTimes[frameNum] / 200000;
                tessellator.setColorOpaque_I(-16777216 + lum * 65536 + lum * 256 + lum * 1);
                tessellator.addVertex((float)frameNum + 0.5f, (baseHeight -= (float)heightFrame) + 0.5f, 0.0);
                tessellator.addVertex((float)frameNum + 0.5f, baseHeight + (float)heightTick + 0.5f, 0.0);
                long heightChunk = this.chunkTimes[frameNum] / 200000;
                tessellator.setColorOpaque_I(-16777216 + lum * 65536);
                tessellator.addVertex((float)frameNum + 0.5f, (baseHeight += (float)heightTick) + 0.5f, 0.0);
                tessellator.addVertex((float)frameNum + 0.5f, baseHeight + (float)heightChunk + 0.5f, 0.0);
                baseHeight += (float)heightChunk;
                long srvTime = this.serverTimes[frameNum];
                if (srvTime > 0) {
                    long heightSrv = srvTime * 1000000 / 200000;
                    tessellator.setColorOpaque_I(-16777216 + lum * 1);
                    tessellator.addVertex((float)frameNum + 0.5f, baseHeight + 0.5f, 0.0);
                    tessellator.addVertex((float)frameNum + 0.5f, baseHeight + (float)heightSrv + 0.5f, 0.0);
                }
                ++frameNum;
            }
            tessellator.draw();
            GL11.glMatrixMode((int)5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5888);
            GL11.glPopMatrix();
            GL11.glEnable((int)3553);
        }
    }

    private void updateMainMenu(GuiMainMenu mainGui) {
        try {
            String e = null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int day = calendar.get(5);
            int month = calendar.get(2) + 1;
            if (day == 8 && month == 4) {
                e = "Happy birthday, OptiFine!";
            }
            if (day == 14 && month == 8) {
                e = "Happy birthday, sp614x!";
            }
            if (e == null) {
                return;
            }
            Field[] fs = GuiMainMenu.class.getDeclaredFields();
            int i = 0;
            while (i < fs.length) {
                if (fs[i].getType() == String.class) {
                    fs[i].setAccessible(true);
                    fs[i].set(mainGui, e);
                    break;
                }
                ++i;
            }
        }
        catch (Throwable e) {
            // empty catch block
        }
    }

    static /* synthetic */ Minecraft access$0(EntityRenderer entityRenderer) {
        return entityRenderer.mc;
    }

}

