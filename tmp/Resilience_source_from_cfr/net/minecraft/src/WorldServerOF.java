/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.src.BlockUtils;
import net.minecraft.src.Config;
import net.minecraft.src.EntityUtils;
import net.minecraft.src.NextTickHashSet;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldServerOF
extends WorldServer {
    private NextTickHashSet pendingTickListEntriesHashSet;
    private TreeSet pendingTickListEntriesTreeSet;
    private List pendingTickListEntriesThisTick = new ArrayList();
    private int lastViewDistance = 0;
    private boolean allChunksTicked = false;
    public Set setChunkCoordsToTickOnce = new HashSet();
    private Set limitedChunkSet = new HashSet();
    private static final Logger logger = LogManager.getLogger();

    public WorldServerOF(MinecraftServer par1MinecraftServer, ISaveHandler par2iSaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, Profiler par6Profiler) {
        super(par1MinecraftServer, par2iSaveHandler, par3Str, par4, par5WorldSettings, par6Profiler);
        this.fixSetNextTicks();
    }

    @Override
    protected void initialize(WorldSettings par1WorldSettings) {
        super.initialize(par1WorldSettings);
        this.fixSetNextTicks();
    }

    private void fixSetNextTicks() {
        try {
            Field[] e = WorldServer.class.getDeclaredFields();
            int posSet = this.findField(e, Set.class, 0);
            int posTreeSet = this.findField(e, TreeSet.class, posSet);
            int posList = this.findField(e, List.class, posTreeSet);
            if (posSet >= 0 && posTreeSet >= 0 && posList >= 0) {
                Field fieldSet = e[posSet];
                Field fieldTreeSet = e[posTreeSet];
                Field fieldList = e[posList];
                fieldSet.setAccessible(true);
                fieldTreeSet.setAccessible(true);
                fieldList.setAccessible(true);
                this.pendingTickListEntriesTreeSet = (TreeSet)fieldTreeSet.get(this);
                this.pendingTickListEntriesThisTick = (List)fieldList.get(this);
                Set oldSet = (Set)fieldSet.get(this);
                if (oldSet instanceof NextTickHashSet) {
                    return;
                }
                this.pendingTickListEntriesHashSet = new NextTickHashSet(oldSet);
                fieldSet.set(this, this.pendingTickListEntriesHashSet);
                Config.dbg("WorldServer.nextTickSet updated");
                return;
            }
            Config.warn("Error updating WorldServer.nextTickSet");
        }
        catch (Exception var9) {
            Config.warn("Error setting WorldServer.nextTickSet: " + var9.getMessage());
        }
    }

    private int findField(Field[] fields, Class cls, int startPos) {
        if (startPos < 0) {
            return -1;
        }
        int i = startPos;
        while (i < fields.length) {
            Field field = fields[i];
            if (field.getType() == cls) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2) {
        if (this.pendingTickListEntriesHashSet != null && this.pendingTickListEntriesTreeSet != null && this.pendingTickListEntriesThisTick != null) {
            ArrayList<NextTickListEntry> var3 = null;
            ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
            int var5 = (var4.chunkXPos << 4) - 2;
            int var6 = var5 + 16 + 2;
            int var7 = (var4.chunkZPos << 4) - 2;
            int var8 = var7 + 16 + 2;
            int var9 = 0;
            while (var9 < 2) {
                Iterator var10;
                if (var9 == 0) {
                    TreeSet var11 = new TreeSet();
                    int dx = -1;
                    while (dx <= 1) {
                        int dz = -1;
                        while (dz <= 1) {
                            HashSet set = this.pendingTickListEntriesHashSet.getNextTickEntriesSet(var4.chunkXPos + dx, var4.chunkZPos + dz);
                            var11.addAll(set);
                            ++dz;
                        }
                        ++dx;
                    }
                    var10 = var11.iterator();
                } else {
                    var10 = this.pendingTickListEntriesThisTick.iterator();
                    if (!this.pendingTickListEntriesThisTick.isEmpty()) {
                        logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
                    }
                }
                while (var10.hasNext()) {
                    NextTickListEntry var15 = (NextTickListEntry)var10.next();
                    if (var15.xCoord < var5 || var15.xCoord >= var6 || var15.zCoord < var7 || var15.zCoord >= var8) continue;
                    if (par2) {
                        this.pendingTickListEntriesHashSet.remove(var15);
                        this.pendingTickListEntriesTreeSet.remove(var15);
                        var10.remove();
                    }
                    if (var3 == null) {
                        var3 = new ArrayList<NextTickListEntry>();
                    }
                    var3.add(var15);
                }
                ++var9;
            }
            return var3;
        }
        return super.getPendingBlockUpdates(par1Chunk, par2);
    }

    @Override
    public void tick() {
        super.tick();
        if (!Config.isTimeDefault()) {
            this.fixWorldTime();
        }
        if (Config.waterOpacityChanged) {
            Config.waterOpacityChanged = false;
            this.updateWaterOpacity();
        }
    }

    @Override
    protected void updateWeather() {
        if (!Config.isWeatherEnabled()) {
            this.fixWorldWeather();
        }
        super.updateWeather();
    }

    private void fixWorldWeather() {
        if (this.worldInfo.isRaining() || this.worldInfo.isThundering()) {
            this.worldInfo.setRainTime(0);
            this.worldInfo.setRaining(false);
            this.setRainStrength(0.0f);
            this.worldInfo.setThunderTime(0);
            this.worldInfo.setThundering(false);
            this.setThunderStrength(0.0f);
            this.func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(2, 0.0f));
            this.func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(7, 0.0f));
            this.func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(8, 0.0f));
        }
    }

    private void fixWorldTime() {
        if (this.worldInfo.getGameType().getID() == 1) {
            long time = this.getWorldTime();
            long timeOfDay = time % 24000;
            if (Config.isTimeDayOnly()) {
                if (timeOfDay <= 1000) {
                    this.setWorldTime(time - timeOfDay + 1001);
                }
                if (timeOfDay >= 11000) {
                    this.setWorldTime(time - timeOfDay + 24001);
                }
            }
            if (Config.isTimeNightOnly()) {
                if (timeOfDay <= 14000) {
                    this.setWorldTime(time - timeOfDay + 14001);
                }
                if (timeOfDay >= 22000) {
                    this.setWorldTime(time - timeOfDay + 24000 + 14001);
                }
            }
        }
    }

    public void updateWaterOpacity() {
        int opacity = 3;
        if (Config.isClearWater()) {
            opacity = 1;
        }
        BlockUtils.setLightOpacity(Blocks.water, opacity);
        BlockUtils.setLightOpacity(Blocks.flowing_water, opacity);
        IChunkProvider cp = this.chunkProvider;
        if (cp != null) {
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
        }
    }

    @Override
    public void updateEntity(Entity par1Entity) {
        if (this.canSkipEntityUpdate(par1Entity) && par1Entity instanceof EntityLivingBase) {
            float el;
            EntityLivingBase elb = (EntityLivingBase)par1Entity;
            int entityAge = EntityUtils.getEntityAge(elb);
            ++entityAge;
            if (elb instanceof EntityMob && (el = elb.getBrightness(1.0f)) > 0.5f) {
                entityAge += 2;
            }
            EntityUtils.setEntityAge(elb, entityAge);
            if (elb instanceof EntityLiving) {
                EntityLiving var5 = (EntityLiving)elb;
                EntityUtils.despawnEntity(var5);
            }
        } else {
            super.updateEntity(par1Entity);
            if (Config.isSmoothWorld()) {
                Thread.currentThread();
                Thread.yield();
            }
        }
    }

    private boolean canSkipEntityUpdate(Entity entity) {
        double dz;
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        EntityLivingBase entityLiving = (EntityLivingBase)entity;
        if (entityLiving.isChild()) {
            return false;
        }
        if (entityLiving.hurtTime > 0) {
            return false;
        }
        if (entity.ticksExisted < 20) {
            return false;
        }
        if (this.playerEntities.size() != 1) {
            return false;
        }
        Entity player = (Entity)this.playerEntities.get(0);
        double dx = Math.abs(entity.posX - player.posX) - 16.0;
        double distSq = dx * dx + (dz = Math.abs(entity.posZ - player.posZ) - 16.0) * dz;
        return !entity.isInRangeToRenderDist(distSq);
    }

    @Override
    protected void setActivePlayerChunksAndCheckLight() {
        super.setActivePlayerChunksAndCheckLight();
        this.limitedChunkSet.clear();
        int viewDistance = this.func_73046_m().getConfigurationManager().getViewDistance();
        if (viewDistance > 10) {
            if (viewDistance != this.lastViewDistance) {
                this.lastViewDistance = viewDistance;
                this.allChunksTicked = false;
            } else if (!this.allChunksTicked) {
                this.allChunksTicked = true;
            } else {
                int i = 0;
                while (i < this.playerEntities.size()) {
                    EntityPlayer player = (EntityPlayer)this.playerEntities.get(i);
                    int pcx = MathHelper.floor_double(player.posX / 16.0);
                    int pcz = MathHelper.floor_double(player.posZ / 16.0);
                    int dist = 10;
                    int cx = - dist;
                    while (cx <= dist) {
                        int cz = - dist;
                        while (cz <= dist) {
                            this.limitedChunkSet.add(new ChunkCoordIntPair(cx + pcx, cz + pcz));
                            ++cz;
                        }
                        ++cx;
                    }
                    ++i;
                }
                if (this.setChunkCoordsToTickOnce.size() > 0) {
                    this.limitedChunkSet.addAll(this.setChunkCoordsToTickOnce);
                    this.setChunkCoordsToTickOnce.clear();
                }
            }
        }
    }

    public void addChunkToTickOnce(int cx, int cz) {
        int viewDistance = this.func_73046_m().getConfigurationManager().getViewDistance();
        if (viewDistance > 10) {
            this.setChunkCoordsToTickOnce.add(new ChunkCoordIntPair(cx, cz));
        }
    }

    @Override
    protected void func_147456_g() {
        Set oldSet = this.activeChunkSet;
        if (this.limitedChunkSet.size() > 0) {
            this.activeChunkSet = this.limitedChunkSet;
        }
        super.func_147456_g();
        this.activeChunkSet = oldSet;
    }
}

