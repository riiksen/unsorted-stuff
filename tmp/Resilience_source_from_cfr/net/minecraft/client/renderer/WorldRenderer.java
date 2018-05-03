/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorField;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {
    protected TesselatorVertexState vertexState;
    public World worldObj;
    protected int glRenderList;
    protected Tessellator tessellator = Tessellator.instance;
    public static volatile int chunksUpdated;
    public int posX;
    public int posY;
    public int posZ;
    public int posXMinus;
    public int posYMinus;
    public int posZMinus;
    public int posXClip;
    public int posYClip;
    public int posZClip;
    public boolean isInFrustum;
    public boolean[] skipRenderPass = new boolean[]{true, true};
    public int posXPlus;
    public int posYPlus;
    public int posZPlus;
    public volatile boolean needsUpdate;
    public AxisAlignedBB rendererBoundingBox;
    public int chunkIndex;
    public boolean isVisible;
    public boolean isWaitingOnOcclusionQuery;
    public int glOcclusionQuery;
    public boolean isChunkLit;
    protected boolean isInitialized;
    public List tileEntityRenderers = new ArrayList();
    protected List tileEntities;
    protected int bytesDrawn;
    private static final String __OBFID = "CL_00000942";
    public boolean isVisibleFromPosition = false;
    public double visibleFromX;
    public double visibleFromY;
    public double visibleFromZ;
    public boolean isInFrustrumFully = false;
    protected boolean needsBoxUpdate = false;
    public volatile boolean isUpdating = false;
    public float sortDistanceToEntitySquared;
    protected boolean skipAllRenderPasses = true;
    public boolean inSortedWorldRenderers;
    public boolean needVertexStateResort;
    public RenderGlobal renderGlobal;
    public static int globalChunkOffsetX;
    public static int globalChunkOffsetZ;

    static {
        globalChunkOffsetX = 0;
        globalChunkOffsetZ = 0;
    }

    public WorldRenderer(World par1World, List par2List, int par3, int par4, int par5, int par6) {
        this.renderGlobal = Minecraft.getMinecraft().renderGlobal;
        this.glRenderList = -1;
        this.isInFrustum = false;
        this.isVisible = true;
        this.isInitialized = false;
        this.worldObj = par1World;
        this.vertexState = null;
        this.tileEntities = par2List;
        this.glRenderList = par6;
        this.posX = -999;
        this.setPosition(par3, par4, par5);
        this.needsUpdate = false;
    }

    public void setPosition(int par1, int par2, int par3) {
        if (par1 != this.posX || par2 != this.posY || par3 != this.posZ) {
            this.setDontDraw();
            this.posX = par1;
            this.posY = par2;
            this.posZ = par3;
            this.posXPlus = par1 + 8;
            this.posYPlus = par2 + 8;
            this.posZPlus = par3 + 8;
            this.posXClip = par1 & 1023;
            this.posYClip = par2;
            this.posZClip = par3 & 1023;
            this.posXMinus = par1 - this.posXClip;
            this.posYMinus = par2 - this.posYClip;
            this.posZMinus = par3 - this.posZClip;
            this.rendererBoundingBox = AxisAlignedBB.getBoundingBox(par1, par2, par3, par1 + 16, par2 + 16, par3 + 16);
            this.needsBoxUpdate = true;
            this.markDirty();
            this.isVisibleFromPosition = false;
        }
    }

    protected void setupGLTranslation() {
        GL11.glTranslatef((float)this.posXClip, (float)this.posYClip, (float)this.posZClip);
    }

    public void updateRenderer(EntityLivingBase p_147892_1_) {
        if (this.worldObj != null) {
            if (this.needsBoxUpdate) {
                GL11.glNewList((int)(this.glRenderList + 2), (int)4864);
                RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
                GL11.glEndList();
                this.needsBoxUpdate = false;
            }
            this.isVisible = true;
            this.isVisibleFromPosition = false;
            if (this.needsUpdate) {
                this.needsUpdate = false;
                int xMin = this.posX;
                int yMin = this.posY;
                int zMain = this.posZ;
                int xMax = this.posX + 16;
                int yMax = this.posY + 16;
                int zMax = this.posZ + 16;
                int var26 = 0;
                while (var26 < 2) {
                    this.skipRenderPass[var26] = true;
                    ++var26;
                }
                this.skipAllRenderPasses = true;
                if (Reflector.LightCache.exists()) {
                    Object var29 = Reflector.getFieldValue(Reflector.LightCache_cache);
                    Reflector.callVoid(var29, Reflector.LightCache_clear, new Object[0]);
                    Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
                }
                Chunk.isLit = false;
                HashSet var30 = new HashSet();
                var30.addAll(this.tileEntityRenderers);
                this.tileEntityRenderers.clear();
                Minecraft var9 = Minecraft.getMinecraft();
                EntityLivingBase var10 = var9.renderViewEntity;
                int viewEntityPosX = MathHelper.floor_double(var10.posX);
                int viewEntityPosY = MathHelper.floor_double(var10.posY);
                int viewEntityPosZ = MathHelper.floor_double(var10.posZ);
                int var14 = 1;
                ChunkCache chunkcache = new ChunkCache(this.worldObj, xMin - var14, yMin - var14, zMain - var14, xMax + var14, yMax + var14, zMax + var14, var14);
                if (!chunkcache.extendedLevelsInChunkCache()) {
                    ++chunksUpdated;
                    RenderBlocks var27 = new RenderBlocks(chunkcache);
                    this.bytesDrawn = 0;
                    this.vertexState = null;
                    this.tessellator = Tessellator.instance;
                    boolean hasForge = Reflector.ForgeHooksClient.exists();
                    int renderPass = 0;
                    while (renderPass < 2) {
                        boolean renderNextPass = false;
                        boolean hasRenderedBlocks = false;
                        boolean hasGlList = false;
                        int y = yMin;
                        while (y < yMax) {
                            int z = zMain;
                            while (z < zMax) {
                                int x = xMin;
                                while (x < xMax) {
                                    Block block = chunkcache.getBlock(x, y, z);
                                    if (block.getMaterial() != Material.air) {
                                        TileEntity blockPass;
                                        boolean canRender;
                                        int var32;
                                        if (!hasGlList) {
                                            hasGlList = true;
                                            this.preRenderBlocks(renderPass);
                                        }
                                        boolean hasTileEntity = false;
                                        hasTileEntity = hasForge ? Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, chunkcache.getBlockMetadata(x, y, z)) : block.hasTileEntity();
                                        if (renderPass == 0 && hasTileEntity && TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass = chunkcache.getTileEntity(x, y, z))) {
                                            this.tileEntityRenderers.add(blockPass);
                                        }
                                        if ((var32 = block.getRenderBlockPass()) > renderPass) {
                                            renderNextPass = true;
                                        }
                                        boolean bl = canRender = var32 == renderPass;
                                        if (Reflector.ForgeBlock_canRenderInPass.exists()) {
                                            canRender = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInPass, renderPass);
                                        }
                                        if (canRender) {
                                            hasRenderedBlocks |= var27.renderBlockByRenderType(block, x, y, z);
                                            if (block.getRenderType() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
                                                var27.setRenderFromInside(true);
                                                var27.setRenderAllFaces(true);
                                                var27.renderBlockByRenderType(block, x, y, z);
                                                var27.setRenderFromInside(false);
                                                var27.setRenderAllFaces(false);
                                            }
                                        }
                                    }
                                    ++x;
                                }
                                ++z;
                            }
                            ++y;
                        }
                        if (hasRenderedBlocks) {
                            this.skipRenderPass[renderPass] = false;
                        }
                        if (hasGlList) {
                            this.postRenderBlocks(renderPass, p_147892_1_);
                        } else {
                            hasRenderedBlocks = false;
                        }
                        if (!renderNextPass) break;
                        ++renderPass;
                    }
                }
                HashSet var31 = new HashSet();
                var31.addAll(this.tileEntityRenderers);
                var31.removeAll(var30);
                this.tileEntities.addAll(var31);
                var30.removeAll(this.tileEntityRenderers);
                this.tileEntities.removeAll(var30);
                this.isChunkLit = Chunk.isLit;
                this.isInitialized = true;
                this.skipAllRenderPasses = this.skipRenderPass[0] && this.skipRenderPass[1];
                this.updateFinished();
            }
        }
    }

    protected void preRenderBlocks(int renderpass) {
        GL11.glNewList((int)(this.glRenderList + renderpass), (int)4864);
        this.tessellator.setRenderingChunk(true);
        if (Config.isFastRender()) {
            this.tessellator.startDrawingQuads();
            this.tessellator.setTranslation(- globalChunkOffsetX, 0.0, - globalChunkOffsetZ);
        } else {
            GL11.glPushMatrix();
            this.setupGLTranslation();
            float var2 = 1.000001f;
            GL11.glTranslatef((float)-8.0f, (float)-8.0f, (float)-8.0f);
            GL11.glScalef((float)var2, (float)var2, (float)var2);
            GL11.glTranslatef((float)8.0f, (float)8.0f, (float)8.0f);
            this.tessellator.startDrawingQuads();
            this.tessellator.setTranslation(- this.posX, - this.posY, - this.posZ);
        }
    }

    protected void postRenderBlocks(int renderpass, EntityLivingBase entityLiving) {
        if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.skipRenderPass[renderpass]) {
            this.vertexState = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
        }
        this.bytesDrawn += this.tessellator.draw();
        this.tessellator.setRenderingChunk(false);
        if (!Config.isFastRender()) {
            GL11.glPopMatrix();
        }
        GL11.glEndList();
        this.tessellator.setTranslation(0.0, 0.0, 0.0);
    }

    public void updateRendererSort(EntityLivingBase p_147889_1_) {
        if (this.vertexState != null && !this.skipRenderPass[1]) {
            this.tessellator = Tessellator.instance;
            this.preRenderBlocks(1);
            this.tessellator.setVertexState(this.vertexState);
            this.postRenderBlocks(1, p_147889_1_);
        }
    }

    public float distanceToEntitySquared(Entity par1Entity) {
        float var2 = (float)(par1Entity.posX - (double)this.posXPlus);
        float var3 = (float)(par1Entity.posY - (double)this.posYPlus);
        float var4 = (float)(par1Entity.posZ - (double)this.posZPlus);
        return var2 * var2 + var3 * var3 + var4 * var4;
    }

    public void setDontDraw() {
        int var1 = 0;
        while (var1 < 2) {
            this.skipRenderPass[var1] = true;
            ++var1;
        }
        this.skipAllRenderPasses = true;
        this.isInFrustum = false;
        this.isInitialized = false;
        this.vertexState = null;
    }

    public void stopRendering() {
        this.setDontDraw();
        this.worldObj = null;
    }

    public int getGLCallListForPass(int par1) {
        return this.glRenderList + par1;
    }

    public void updateInFrustum(ICamera par1ICamera) {
        this.isInFrustum = par1ICamera.isBoundingBoxInFrustum(this.rendererBoundingBox);
        this.isInFrustrumFully = this.isInFrustum && Config.isOcclusionFancy() ? par1ICamera.isBoundingBoxInFrustumFully(this.rendererBoundingBox) : false;
    }

    public void callOcclusionQueryList() {
        GL11.glCallList((int)(this.glRenderList + 2));
    }

    public boolean skipAllRenderPasses() {
        return this.skipAllRenderPasses;
    }

    public void markDirty() {
        this.needsUpdate = true;
    }

    protected void updateFinished() {
        if (!this.skipAllRenderPasses && !this.inSortedWorldRenderers) {
            Minecraft.getMinecraft().renderGlobal.addToSortedWorldRenderers(this);
        }
    }
}

