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
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorField;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.WrDisplayListAllocator;
import net.minecraft.src.WrUpdateState;
import net.minecraft.src.WrUpdates;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

public class WorldRendererSmooth
extends WorldRenderer {
    private WrUpdateState updateState = new WrUpdateState();
    public int activeSet = 0;
    public int[] activeListIndex = new int[2];
    public int[][][] glWorkLists = new int[2][2][16];
    public boolean[] tempSkipRenderPass = new boolean[2];
    public TesselatorVertexState tempVertexState;

    public WorldRendererSmooth(World par1World, List par2List, int par3, int par4, int par5, int par6) {
        super(par1World, par2List, par3, par4, par5, par6);
    }

    private void checkGlWorkLists() {
        if (this.glWorkLists[0][0][0] <= 0) {
            int glWorkBase = this.renderGlobal.displayListAllocator.allocateDisplayLists(64);
            int set = 0;
            while (set < 2) {
                int setBase = glWorkBase + set * 2 * 16;
                int pass = 0;
                while (pass < 2) {
                    int passBase = setBase + pass * 16;
                    int t = 0;
                    while (t < 16) {
                        this.glWorkLists[set][pass][t] = passBase + t;
                        ++t;
                    }
                    ++pass;
                }
                ++set;
            }
        }
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
            this.updateRenderer(0);
            this.finishUpdate();
        }
    }

    public boolean updateRenderer(long finishTime) {
        if (this.worldObj == null) {
            return true;
        }
        this.needsUpdate = false;
        if (!this.isUpdating) {
            if (this.needsBoxUpdate) {
                GL11.glNewList((int)(this.glRenderList + 2), (int)4864);
                RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
                GL11.glEndList();
                this.needsBoxUpdate = false;
            }
            if (Reflector.LightCache.exists()) {
                Object xMin = Reflector.getFieldValue(Reflector.LightCache_cache);
                Reflector.callVoid(xMin, Reflector.LightCache_clear, new Object[0]);
                Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
            }
            Chunk.isLit = false;
        }
        int var27 = this.posX;
        int yMin = this.posY;
        int zMin = this.posZ;
        int xMax = this.posX + 16;
        int yMax = this.posY + 16;
        int zMax = this.posZ + 16;
        ChunkCache chunkcache = null;
        RenderBlocks renderblocks = null;
        HashSet setOldEntityRenders = null;
        int viewEntityPosX = 0;
        int viewEntityPosY = 0;
        int viewEntityPosZ = 0;
        if (!this.isUpdating) {
            int setNewEntityRenderers = 0;
            while (setNewEntityRenderers < 2) {
                this.tempSkipRenderPass[setNewEntityRenderers] = true;
                ++setNewEntityRenderers;
            }
            this.tempVertexState = null;
            Minecraft var31 = Minecraft.getMinecraft();
            EntityLivingBase renderPass = var31.renderViewEntity;
            viewEntityPosX = MathHelper.floor_double(renderPass.posX);
            viewEntityPosY = MathHelper.floor_double(renderPass.posY);
            viewEntityPosZ = MathHelper.floor_double(renderPass.posZ);
            int renderNextPass = 1;
            chunkcache = new ChunkCache(this.worldObj, var27 - renderNextPass, yMin - renderNextPass, zMin - renderNextPass, xMax + renderNextPass, yMax + renderNextPass, zMax + renderNextPass, renderNextPass);
            renderblocks = new RenderBlocks(chunkcache);
            setOldEntityRenders = new HashSet();
            setOldEntityRenders.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
        }
        if (this.isUpdating || !chunkcache.extendedLevelsInChunkCache()) {
            this.bytesDrawn = 0;
            this.tessellator = Tessellator.instance;
            boolean var30 = Reflector.ForgeHooksClient.exists();
            this.checkGlWorkLists();
            int var28 = 0;
            while (var28 < 2) {
                boolean var32 = false;
                boolean hasRenderedBlocks = false;
                boolean hasGlList = false;
                int y = yMin;
                while (y < yMax) {
                    if (this.isUpdating) {
                        this.isUpdating = false;
                        chunkcache = this.updateState.chunkcache;
                        renderblocks = this.updateState.renderblocks;
                        setOldEntityRenders = this.updateState.setOldEntityRenders;
                        viewEntityPosX = this.updateState.viewEntityPosX;
                        viewEntityPosY = this.updateState.viewEntityPosY;
                        viewEntityPosZ = this.updateState.viewEntityPosZ;
                        var28 = this.updateState.renderPass;
                        y = this.updateState.y;
                        var32 = this.updateState.flag;
                        hasRenderedBlocks = this.updateState.hasRenderedBlocks;
                        hasGlList = this.updateState.hasGlList;
                        if (hasGlList) {
                            this.preRenderBlocksSmooth(var28);
                        }
                    } else if (hasGlList && finishTime != 0 && System.nanoTime() - finishTime > 0 && this.activeListIndex[var28] < 15) {
                        if (hasRenderedBlocks) {
                            this.tempSkipRenderPass[var28] = false;
                        }
                        this.postRenderBlocksSmooth(var28, this.renderGlobal.renderViewEntity, false);
                        int[] arrn = this.activeListIndex;
                        int n = var28;
                        arrn[n] = arrn[n] + 1;
                        this.updateState.chunkcache = chunkcache;
                        this.updateState.renderblocks = renderblocks;
                        this.updateState.setOldEntityRenders = setOldEntityRenders;
                        this.updateState.viewEntityPosX = viewEntityPosX;
                        this.updateState.viewEntityPosY = viewEntityPosY;
                        this.updateState.viewEntityPosZ = viewEntityPosZ;
                        this.updateState.renderPass = var28;
                        this.updateState.y = y;
                        this.updateState.flag = var32;
                        this.updateState.hasRenderedBlocks = hasRenderedBlocks;
                        this.updateState.hasGlList = hasGlList;
                        this.isUpdating = true;
                        return false;
                    }
                    int z = zMin;
                    while (z < zMax) {
                        int x = var27;
                        while (x < xMax) {
                            Block block = chunkcache.getBlock(x, y, z);
                            if (block.getMaterial() != Material.air) {
                                TileEntity blockPass;
                                int var33;
                                boolean canRender;
                                if (!hasGlList) {
                                    hasGlList = true;
                                    this.preRenderBlocksSmooth(var28);
                                }
                                boolean hasTileEntity = false;
                                hasTileEntity = var30 ? Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, chunkcache.getBlockMetadata(x, y, z)) : block.hasTileEntity();
                                if (var28 == 0 && hasTileEntity && TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass = chunkcache.getTileEntity(x, y, z))) {
                                    this.tileEntityRenderers.add(blockPass);
                                }
                                if ((var33 = block.getRenderBlockPass()) > var28) {
                                    var32 = true;
                                }
                                boolean bl = canRender = var33 == var28;
                                if (Reflector.ForgeBlock_canRenderInPass.exists()) {
                                    canRender = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInPass, var28);
                                }
                                if (canRender) {
                                    hasRenderedBlocks |= renderblocks.renderBlockByRenderType(block, x, y, z);
                                    if (block.getRenderType() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
                                        renderblocks.setRenderFromInside(true);
                                        renderblocks.setRenderAllFaces(true);
                                        renderblocks.renderBlockByRenderType(block, x, y, z);
                                        renderblocks.setRenderFromInside(false);
                                        renderblocks.setRenderAllFaces(false);
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
                    this.tempSkipRenderPass[var28] = false;
                }
                if (hasGlList) {
                    this.postRenderBlocksSmooth(var28, this.renderGlobal.renderViewEntity, true);
                } else {
                    hasRenderedBlocks = false;
                }
                if (!var32) break;
                ++var28;
            }
        }
        HashSet var29 = new HashSet();
        var29.addAll(this.tileEntityRenderers);
        var29.removeAll(setOldEntityRenders);
        this.tileEntities.addAll(var29);
        setOldEntityRenders.removeAll(this.tileEntityRenderers);
        this.tileEntities.removeAll(setOldEntityRenders);
        this.isChunkLit = Chunk.isLit;
        this.isInitialized = true;
        ++chunksUpdated;
        this.isVisible = true;
        this.isVisibleFromPosition = false;
        this.skipRenderPass[0] = this.tempSkipRenderPass[0];
        this.skipRenderPass[1] = this.tempSkipRenderPass[1];
        this.skipAllRenderPasses = this.skipRenderPass[0] && this.skipRenderPass[1];
        this.vertexState = this.tempVertexState;
        this.isUpdating = false;
        this.updateFinished();
        return true;
    }

    protected void preRenderBlocksSmooth(int renderpass) {
        GL11.glNewList((int)this.glWorkLists[this.activeSet][renderpass][this.activeListIndex[renderpass]], (int)4864);
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

    protected void postRenderBlocksSmooth(int renderpass, EntityLivingBase entityLiving, boolean updateFinished) {
        if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.tempSkipRenderPass[renderpass]) {
            TesselatorVertexState tsv = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
            if (this.tempVertexState == null) {
                this.tempVertexState = tsv;
            } else {
                this.tempVertexState.addTessellatorVertexState(tsv);
            }
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
        int i;
        int list;
        int pass = 0;
        while (pass < 2) {
            if (!this.skipRenderPass[pass]) {
                GL11.glNewList((int)(this.glRenderList + pass), (int)4864);
                i = 0;
                while (i <= this.activeListIndex[pass]) {
                    list = this.glWorkLists[this.activeSet][pass][i];
                    GL11.glCallList((int)list);
                    ++i;
                }
                GL11.glEndList();
            }
            ++pass;
        }
        this.activeSet = this.activeSet == 0 ? 1 : 0;
        pass = 0;
        while (pass < 2) {
            if (!this.skipRenderPass[pass]) {
                i = 0;
                while (i <= this.activeListIndex[pass]) {
                    list = this.glWorkLists[this.activeSet][pass][i];
                    GL11.glNewList((int)list, (int)4864);
                    GL11.glEndList();
                    ++i;
                }
            }
            ++pass;
        }
        pass = 0;
        while (pass < 2) {
            this.activeListIndex[pass] = 0;
            ++pass;
        }
    }
}

