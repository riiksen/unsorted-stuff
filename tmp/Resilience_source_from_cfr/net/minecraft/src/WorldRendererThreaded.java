/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.Config;
import net.minecraft.src.IWrUpdateControl;
import net.minecraft.src.IWrUpdateListener;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorField;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.WrDisplayListAllocator;
import net.minecraft.src.WrUpdateControl;
import net.minecraft.src.WrUpdates;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

public class WorldRendererThreaded
extends WorldRenderer {
    private int glRenderListWork;
    private int glRenderListBoundingBox;
    public boolean[] tempSkipRenderPass = new boolean[2];
    public TesselatorVertexState tempVertexState;
    private Tessellator tessellatorWork = null;

    public WorldRendererThreaded(World par1World, List par2List, int par3, int par4, int par5, int par6) {
        super(par1World, par2List, par3, par4, par5, par6);
        RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
        this.glRenderListWork = renderGlobal.displayListAllocator.allocateDisplayLists(2);
        this.glRenderListBoundingBox = this.glRenderList + 2;
    }

    @Override
    public void setPosition(int px, int py, int pz) {
        if (this.isUpdating) {
            WrUpdates.finishCurrentUpdate();
        }
        super.setPosition(px, py, pz);
    }

    public void updateRenderer() {
        if (this.worldObj != null) {
            this.updateRenderer(null);
            this.finishUpdate();
        }
    }

    public void updateRenderer(IWrUpdateListener updateListener) {
        if (this.worldObj != null) {
            this.needsUpdate = false;
            int xMin = this.posX;
            int yMin = this.posY;
            int zMin = this.posZ;
            int xMax = this.posX + 16;
            int yMax = this.posY + 16;
            int zMax = this.posZ + 16;
            int hashset = 0;
            while (hashset < this.tempSkipRenderPass.length) {
                this.tempSkipRenderPass[hashset] = true;
                ++hashset;
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
            int one = 1;
            ChunkCache chunkcache = new ChunkCache(this.worldObj, xMin - one, yMin - one, zMin - one, xMax + one, yMax + one, zMax + one, one);
            if (!chunkcache.extendedLevelsInChunkCache()) {
                ++chunksUpdated;
                RenderBlocks hashset1 = new RenderBlocks(chunkcache);
                this.bytesDrawn = 0;
                this.tempVertexState = null;
                this.tessellator = Tessellator.instance;
                boolean hasForge = Reflector.ForgeHooksClient.exists();
                WrUpdateControl uc = new WrUpdateControl();
                int renderPass = 0;
                while (renderPass < 2) {
                    uc.setRenderPass(renderPass);
                    boolean renderNextPass = false;
                    boolean hasRenderedBlocks = false;
                    boolean hasGlList = false;
                    int y = yMin;
                    while (y < yMax) {
                        if (hasRenderedBlocks && updateListener != null) {
                            updateListener.updating(uc);
                            this.tessellator = Tessellator.instance;
                        }
                        int z = zMin;
                        while (z < zMax) {
                            int x = xMin;
                            while (x < xMax) {
                                Block block = chunkcache.getBlock(x, y, z);
                                if (block.getMaterial() != Material.air) {
                                    TileEntity blockPass;
                                    int var32;
                                    boolean canRender;
                                    if (!hasGlList) {
                                        hasGlList = true;
                                        this.preRenderBlocksThreaded(renderPass);
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
                                        hasRenderedBlocks |= hashset1.renderBlockByRenderType(block, x, y, z);
                                        if (block.getRenderType() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
                                            hashset1.setRenderFromInside(true);
                                            hashset1.setRenderAllFaces(true);
                                            hashset1.renderBlockByRenderType(block, x, y, z);
                                            hashset1.setRenderFromInside(false);
                                            hashset1.setRenderAllFaces(false);
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
                        this.tempSkipRenderPass[renderPass] = false;
                    }
                    if (hasGlList) {
                        if (updateListener != null) {
                            updateListener.updating(uc);
                        }
                        this.tessellator = Tessellator.instance;
                        this.postRenderBlocksThreaded(renderPass, this.renderGlobal.renderViewEntity);
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
        }
    }

    protected void preRenderBlocksThreaded(int renderpass) {
        GL11.glNewList((int)(this.glRenderListWork + renderpass), (int)4864);
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

    protected void postRenderBlocksThreaded(int renderpass, EntityLivingBase entityLiving) {
        if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.tempSkipRenderPass[renderpass]) {
            this.tempVertexState = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
        }
        this.bytesDrawn += this.tessellator.draw();
        this.tessellator.setRenderingChunk(false);
        if (!Config.isFastRender()) {
            GL11.glPopMatrix();
        }
        GL11.glEndList();
        this.tessellator.setTranslation(0.0, 0.0, 0.0);
    }

    public void finishUpdate() {
        int temp = this.glRenderList;
        this.glRenderList = this.glRenderListWork;
        this.glRenderListWork = temp;
        int lightCache = 0;
        while (lightCache < 2) {
            if (!this.skipRenderPass[lightCache]) {
                GL11.glNewList((int)(this.glRenderListWork + lightCache), (int)4864);
                GL11.glEndList();
            }
            ++lightCache;
        }
        lightCache = 0;
        while (lightCache < 2) {
            this.skipRenderPass[lightCache] = this.tempSkipRenderPass[lightCache];
            ++lightCache;
        }
        boolean bl = this.skipAllRenderPasses = this.skipRenderPass[0] && this.skipRenderPass[1];
        if (this.needsBoxUpdate && !this.skipAllRenderPasses()) {
            GL11.glNewList((int)this.glRenderListBoundingBox, (int)4864);
            RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
            GL11.glEndList();
            this.needsBoxUpdate = false;
        }
        this.vertexState = this.tempVertexState;
        this.isVisible = true;
        this.isVisibleFromPosition = false;
        if (Reflector.LightCache.exists()) {
            Object var3 = Reflector.getFieldValue(Reflector.LightCache_cache);
            Reflector.callVoid(var3, Reflector.LightCache_clear, new Object[0]);
            Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
        }
        this.updateFinished();
    }

    @Override
    public void callOcclusionQueryList() {
        GL11.glCallList((int)this.glRenderListBoundingBox);
    }
}

