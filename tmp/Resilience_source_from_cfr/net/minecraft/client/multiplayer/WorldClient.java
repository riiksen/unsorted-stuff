/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.multiplayer;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSoundMinecart;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkStarterFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.SaveHandlerMP;

public class WorldClient
extends World {
    private NetHandlerPlayClient sendQueue;
    private ChunkProviderClient clientChunkProvider;
    private IntHashMap entityHashSet = new IntHashMap();
    private Set entityList = new HashSet();
    private Set entitySpawnQueue = new HashSet();
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Set previousActiveChunkSet = new HashSet();
    private static final String __OBFID = "CL_00000882";

    public WorldClient(NetHandlerPlayClient p_i45063_1_, WorldSettings p_i45063_2_, int p_i45063_3_, EnumDifficulty p_i45063_4_, Profiler p_i45063_5_) {
        super((ISaveHandler)new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(p_i45063_3_), p_i45063_2_, p_i45063_5_);
        this.sendQueue = p_i45063_1_;
        this.difficultySetting = p_i45063_4_;
        this.setSpawnLocation(8, 64, 8);
        this.mapStorage = p_i45063_1_.mapStorageOrigin;
    }

    @Override
    public void tick() {
        super.tick();
        this.func_82738_a(this.getTotalWorldTime() + 1);
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            this.setWorldTime(this.getWorldTime() + 1);
        }
        this.theProfiler.startSection("reEntryProcessing");
        int var1 = 0;
        while (var1 < 10 && !this.entitySpawnQueue.isEmpty()) {
            Entity var2 = (Entity)this.entitySpawnQueue.iterator().next();
            this.entitySpawnQueue.remove(var2);
            if (!this.loadedEntityList.contains(var2)) {
                this.spawnEntityInWorld(var2);
            }
            ++var1;
        }
        this.theProfiler.endStartSection("connection");
        this.sendQueue.onNetworkTick();
        this.theProfiler.endStartSection("chunkCache");
        this.clientChunkProvider.unloadQueuedChunks();
        this.theProfiler.endStartSection("blocks");
        this.func_147456_g();
        this.theProfiler.endSection();
    }

    public void invalidateBlockReceiveRegion(int par1, int par2, int par3, int par4, int par5, int par6) {
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        this.clientChunkProvider = new ChunkProviderClient(this);
        return this.clientChunkProvider;
    }

    @Override
    protected void func_147456_g() {
        super.func_147456_g();
        this.previousActiveChunkSet.retainAll(this.activeChunkSet);
        if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
            this.previousActiveChunkSet.clear();
        }
        int var1 = 0;
        for (ChunkCoordIntPair var3 : this.activeChunkSet) {
            if (this.previousActiveChunkSet.contains(var3)) continue;
            int var4 = var3.chunkXPos * 16;
            int var5 = var3.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var6 = this.getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
            this.func_147467_a(var4, var5, var6);
            this.theProfiler.endSection();
            this.previousActiveChunkSet.add(var3);
            if (++var1 < 10) continue;
            return;
        }
    }

    public void doPreChunk(int par1, int par2, boolean par3) {
        if (par3) {
            this.clientChunkProvider.loadChunk(par1, par2);
        } else {
            this.clientChunkProvider.unloadChunk(par1, par2);
        }
        if (!par3) {
            this.markBlockRangeForRenderUpdate(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
        }
    }

    @Override
    public boolean spawnEntityInWorld(Entity par1Entity) {
        boolean var2 = super.spawnEntityInWorld(par1Entity);
        this.entityList.add(par1Entity);
        if (!var2) {
            this.entitySpawnQueue.add(par1Entity);
        } else if (par1Entity instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)par1Entity));
        }
        return var2;
    }

    @Override
    public void removeEntity(Entity par1Entity) {
        super.removeEntity(par1Entity);
        this.entityList.remove(par1Entity);
    }

    @Override
    protected void onEntityAdded(Entity par1Entity) {
        super.onEntityAdded(par1Entity);
        if (this.entitySpawnQueue.contains(par1Entity)) {
            this.entitySpawnQueue.remove(par1Entity);
        }
    }

    @Override
    protected void onEntityRemoved(Entity par1Entity) {
        super.onEntityRemoved(par1Entity);
        boolean var2 = false;
        if (this.entityList.contains(par1Entity)) {
            if (par1Entity.isEntityAlive()) {
                this.entitySpawnQueue.add(par1Entity);
                var2 = true;
            } else {
                this.entityList.remove(par1Entity);
            }
        }
        if (RenderManager.instance.getEntityRenderObject(par1Entity).func_147905_a() && !var2) {
            this.mc.renderGlobal.onStaticEntitiesChanged();
        }
    }

    public void addEntityToWorld(int par1, Entity par2Entity) {
        Entity var3 = this.getEntityByID(par1);
        if (var3 != null) {
            this.removeEntity(var3);
        }
        this.entityList.add(par2Entity);
        par2Entity.setEntityId(par1);
        if (!this.spawnEntityInWorld(par2Entity)) {
            this.entitySpawnQueue.add(par2Entity);
        }
        this.entityHashSet.addKey(par1, par2Entity);
        if (RenderManager.instance.getEntityRenderObject(par2Entity).func_147905_a()) {
            this.mc.renderGlobal.onStaticEntitiesChanged();
        }
    }

    @Override
    public Entity getEntityByID(int par1) {
        return par1 == this.mc.thePlayer.getEntityId() ? this.mc.thePlayer : (Entity)this.entityHashSet.lookup(par1);
    }

    public Entity removeEntityFromWorld(int par1) {
        Entity var2 = (Entity)this.entityHashSet.removeObject(par1);
        if (var2 != null) {
            this.entityList.remove(var2);
            this.removeEntity(var2);
        }
        return var2;
    }

    public boolean func_147492_c(int p_147492_1_, int p_147492_2_, int p_147492_3_, Block p_147492_4_, int p_147492_5_) {
        this.invalidateBlockReceiveRegion(p_147492_1_, p_147492_2_, p_147492_3_, p_147492_1_, p_147492_2_, p_147492_3_);
        return super.setBlock(p_147492_1_, p_147492_2_, p_147492_3_, p_147492_4_, p_147492_5_, 3);
    }

    @Override
    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
    }

    @Override
    protected void updateWeather() {
        if (!this.provider.hasNoSky) {
            // empty if block
        }
    }

    public void doVoidFogParticles(int par1, int par2, int par3) {
        int var4 = 16;
        Random var5 = new Random();
        int var6 = 0;
        while (var6 < 1000) {
            int var9;
            int var8;
            int var7 = par1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            Block var10 = this.getBlock(var7, var8 = par2 + this.rand.nextInt(var4) - this.rand.nextInt(var4), var9 = par3 + this.rand.nextInt(var4) - this.rand.nextInt(var4));
            if (var10.getMaterial() == Material.air) {
                if (this.rand.nextInt(8) > var8 && this.provider.getWorldHasVoidParticles()) {
                    this.spawnParticle("depthsuspend", (float)var7 + this.rand.nextFloat(), (float)var8 + this.rand.nextFloat(), (float)var9 + this.rand.nextFloat(), 0.0, 0.0, 0.0);
                }
            } else {
                var10.randomDisplayTick(this, var7, var8, var9, var5);
            }
            ++var6;
        }
    }

    public void removeAllEntities() {
        Entity var2;
        int var3;
        int var4;
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            var2 = (Entity)this.unloadedEntityList.get(var1);
            var3 = var2.chunkCoordX;
            var4 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
            }
            ++var1;
        }
        var1 = 0;
        while (var1 < this.unloadedEntityList.size()) {
            this.onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
            ++var1;
        }
        this.unloadedEntityList.clear();
        var1 = 0;
        while (var1 < this.loadedEntityList.size()) {
            block10 : {
                block9 : {
                    var2 = (Entity)this.loadedEntityList.get(var1);
                    if (var2.ridingEntity == null) break block9;
                    if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) break block10;
                    var2.ridingEntity.riddenByEntity = null;
                    var2.ridingEntity = null;
                }
                if (var2.isDead) {
                    var3 = var2.chunkCoordX;
                    var4 = var2.chunkCoordZ;
                    if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                        this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
                    }
                    this.loadedEntityList.remove(var1--);
                    this.onEntityRemoved(var2);
                }
            }
            ++var1;
        }
    }

    @Override
    public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport) {
        CrashReportCategory var2 = super.addWorldInfoToCrashReport(par1CrashReport);
        var2.addCrashSectionCallable("Forced entities", new Callable(){
            private static final String __OBFID = "CL_00000883";

            public String call() {
                return String.valueOf(WorldClient.this.entityList.size()) + " total; " + WorldClient.this.entityList.toString();
            }
        });
        var2.addCrashSectionCallable("Retry entities", new Callable(){
            private static final String __OBFID = "CL_00000884";

            public String call() {
                return String.valueOf(WorldClient.this.entitySpawnQueue.size()) + " total; " + WorldClient.this.entitySpawnQueue.toString();
            }
        });
        var2.addCrashSectionCallable("Server brand", new Callable(){
            private static final String __OBFID = "CL_00000885";

            public String call() {
                return WorldClient.access$2((WorldClient)WorldClient.this).thePlayer.func_142021_k();
            }
        });
        var2.addCrashSectionCallable("Server type", new Callable(){
            private static final String __OBFID = "CL_00000886";

            public String call() {
                return WorldClient.this.mc.getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
            }
        });
        return var2;
    }

    @Override
    public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10) {
        double var11 = this.mc.renderViewEntity.getDistanceSq(par1, par3, par5);
        PositionedSoundRecord var13 = new PositionedSoundRecord(new ResourceLocation(par7Str), par8, par9, (float)par1, (float)par3, (float)par5);
        if (par10 && var11 > 100.0) {
            double var14 = Math.sqrt(var11) / 40.0;
            this.mc.getSoundHandler().playDelayedSound(var13, (int)(var14 * 20.0));
        } else {
            this.mc.getSoundHandler().playSound(var13);
        }
    }

    @Override
    public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound) {
        this.mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, par1, par3, par5, par7, par9, par11, this.mc.effectRenderer, par13NBTTagCompound));
    }

    public void setWorldScoreboard(Scoreboard par1Scoreboard) {
        this.worldScoreboard = par1Scoreboard;
    }

    @Override
    public void setWorldTime(long par1) {
        if (par1 < 0) {
            par1 = - par1;
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
        } else {
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
        }
        super.setWorldTime(par1);
    }

}

