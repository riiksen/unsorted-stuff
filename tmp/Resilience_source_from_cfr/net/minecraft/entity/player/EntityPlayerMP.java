/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  org.apache.commons.io.Charsets
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.player;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP
extends EntityPlayer
implements ICrafting {
    private static final Logger logger = LogManager.getLogger();
    private String translator = "en_US";
    public NetHandlerPlayServer playerNetServerHandler;
    public final MinecraftServer mcServer;
    public final ItemInWorldManager theItemInWorldManager;
    public double managedPosX;
    public double managedPosZ;
    public final List loadedChunks = new LinkedList();
    public final List destroyedItemsNetCache = new LinkedList();
    private final StatisticsFile field_147103_bO;
    private float field_130068_bO = Float.MIN_VALUE;
    private float lastHealth = -1.0E8f;
    private int lastFoodLevel = -99999999;
    private boolean wasHungry = true;
    private int lastExperience = -99999999;
    private int field_147101_bU = 60;
    private int renderDistance;
    private EntityPlayer.EnumChatVisibility chatVisibility;
    private boolean chatColours = true;
    private long field_143005_bX = 0;
    private int currentWindowId;
    public boolean isChangingQuantityOnly;
    public int ping;
    public boolean playerConqueredTheEnd;
    private static final String __OBFID = "CL_00001440";

    public EntityPlayerMP(MinecraftServer p_i45285_1_, WorldServer p_i45285_2_, GameProfile p_i45285_3_, ItemInWorldManager p_i45285_4_) {
        super(p_i45285_2_, p_i45285_3_);
        p_i45285_4_.thisPlayerMP = this;
        this.theItemInWorldManager = p_i45285_4_;
        this.renderDistance = p_i45285_1_.getConfigurationManager().getViewDistance();
        ChunkCoordinates var5 = p_i45285_2_.getSpawnPoint();
        int var6 = var5.posX;
        int var7 = var5.posZ;
        int var8 = var5.posY;
        if (!p_i45285_2_.provider.hasNoSky && p_i45285_2_.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
            int var9 = Math.max(5, p_i45285_1_.getSpawnProtectionSize() - 6);
            var8 = p_i45285_2_.getTopSolidOrLiquidBlock(var6 += this.rand.nextInt(var9 * 2) - var9, var7 += this.rand.nextInt(var9 * 2) - var9);
        }
        this.mcServer = p_i45285_1_;
        this.field_147103_bO = p_i45285_1_.getConfigurationManager().func_148538_i(this.getCommandSenderName());
        this.stepHeight = 0.0f;
        this.yOffset = 0.0f;
        this.setLocationAndAngles((double)var6 + 0.5, var8, (double)var7 + 0.5, 0.0f, 0.0f);
        while (!p_i45285_2_.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            this.setPosition(this.posX, this.posY + 1.0, this.posZ);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.func_150297_b("playerGameType", 99)) {
            if (MinecraftServer.getServer().getForceGamemode()) {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
            } else {
                this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(par1NBTTagCompound.getInteger("playerGameType")));
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
    }

    @Override
    public void addExperienceLevel(int par1) {
        super.addExperienceLevel(par1);
        this.lastExperience = -1;
    }

    public void addSelfToInternalCraftingInventory() {
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    protected void resetHeight() {
        this.yOffset = 0.0f;
    }

    @Override
    public float getEyeHeight() {
        return 1.62f;
    }

    @Override
    public void onUpdate() {
        this.theItemInWorldManager.updateBlockRemoving();
        --this.field_147101_bU;
        if (this.hurtResistantTime > 0) {
            --this.hurtResistantTime;
        }
        this.openContainer.detectAndSendChanges();
        if (!this.worldObj.isClient && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        while (!this.destroyedItemsNetCache.isEmpty()) {
            int var1 = Math.min(this.destroyedItemsNetCache.size(), 127);
            int[] var2 = new int[var1];
            Iterator var3 = this.destroyedItemsNetCache.iterator();
            int var4 = 0;
            while (var3.hasNext() && var4 < var1) {
                var2[var4++] = (Integer)var3.next();
                var3.remove();
            }
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(var2));
        }
        if (!this.loadedChunks.isEmpty()) {
            ArrayList<Chunk> var6 = new ArrayList<Chunk>();
            Iterator var7 = this.loadedChunks.iterator();
            ArrayList var8 = new ArrayList();
            while (var7.hasNext() && var6.size() < S26PacketMapChunkBulk.func_149258_c()) {
                ChunkCoordIntPair var9 = (ChunkCoordIntPair)var7.next();
                if (var9 != null) {
                    Chunk var5;
                    if (!this.worldObj.blockExists(var9.chunkXPos << 4, 0, var9.chunkZPos << 4) || !(var5 = this.worldObj.getChunkFromChunkCoords(var9.chunkXPos, var9.chunkZPos)).func_150802_k()) continue;
                    var6.add(var5);
                    var8.addAll(((WorldServer)this.worldObj).func_147486_a(var9.chunkXPos * 16, 0, var9.chunkZPos * 16, var9.chunkXPos * 16 + 16, 256, var9.chunkZPos * 16 + 16));
                    var7.remove();
                    continue;
                }
                var7.remove();
            }
            if (!var6.isEmpty()) {
                this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(var6));
                for (TileEntity var10 : var8) {
                    this.func_147097_b(var10);
                }
                for (Chunk var5 : var6) {
                    this.getServerForPlayer().getEntityTracker().func_85172_a(this, var5);
                }
            }
        }
        if (this.field_143005_bX > 0 && this.mcServer.func_143007_ar() > 0 && MinecraftServer.getSystemTimeMillis() - this.field_143005_bX > (long)(this.mcServer.func_143007_ar() * 1000 * 60)) {
            this.playerNetServerHandler.kickPlayerFromServer("You have been idle for too long!");
        }
    }

    public void onUpdateEntity() {
        try {
            super.onUpdate();
            int var1 = 0;
            while (var1 < this.inventory.getSizeInventory()) {
                Packet var8;
                ItemStack var6 = this.inventory.getStackInSlot(var1);
                if (var6 != null && var6.getItem().isMap() && (var8 = ((ItemMapBase)var6.getItem()).func_150911_c(var6, this.worldObj, this)) != null) {
                    this.playerNetServerHandler.sendPacket(var8);
                }
                ++var1;
            }
            if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0f != this.wasHungry) {
                this.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                boolean bl = this.wasHungry = this.foodStats.getSaturationLevel() == 0.0f;
            }
            if (this.getHealth() + this.getAbsorptionAmount() != this.field_130068_bO) {
                this.field_130068_bO = this.getHealth() + this.getAbsorptionAmount();
                Collection var5 = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.health);
                for (ScoreObjective var9 : var5) {
                    this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var9).func_96651_a(Arrays.asList(this));
                }
            }
            if (this.experienceTotal != this.lastExperience) {
                this.lastExperience = this.experienceTotal;
                this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
            }
            if (this.ticksExisted % 20 * 5 == 0 && !this.func_147099_x().hasAchievementUnlocked(AchievementList.field_150961_L)) {
                this.func_147098_j();
            }
        }
        catch (Throwable var4) {
            CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
            CrashReportCategory var3 = var2.makeCategory("Player being ticked");
            this.addEntityCrashInfo(var3);
            throw new ReportedException(var2);
        }
    }

    protected void func_147098_j() {
        BiomeGenBase var1 = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        if (var1 != null) {
            String var2 = var1.biomeName;
            JsonSerializableSet var3 = (JsonSerializableSet)this.func_147099_x().func_150870_b(AchievementList.field_150961_L);
            if (var3 == null) {
                var3 = (JsonSerializableSet)this.func_147099_x().func_150872_a(AchievementList.field_150961_L, new JsonSerializableSet());
            }
            var3.add((Object)var2);
            if (this.func_147099_x().canUnlockAchievement(AchievementList.field_150961_L) && var3.size() == BiomeGenBase.field_150597_n.size()) {
                HashSet var4 = Sets.newHashSet((Iterable)BiomeGenBase.field_150597_n);
                Iterator var5 = var3.iterator();
                while (var5.hasNext()) {
                    String var6 = (String)var5.next();
                    Iterator var7 = var4.iterator();
                    while (var7.hasNext()) {
                        BiomeGenBase var8 = (BiomeGenBase)var7.next();
                        if (!var8.biomeName.equals(var6)) continue;
                        var7.remove();
                    }
                    if (var4.isEmpty()) break;
                }
                if (var4.isEmpty()) {
                    this.triggerAchievement(AchievementList.field_150961_L);
                }
            }
        }
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        this.mcServer.getConfigurationManager().func_148539_a(this.func_110142_aN().func_151521_b());
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        Collection var2 = this.worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount);
        for (ScoreObjective var4 : var2) {
            Score var5 = this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var4);
            var5.func_96648_a();
        }
        EntityLivingBase var6 = this.func_94060_bK();
        if (var6 != null) {
            int var7 = EntityList.getEntityID(var6);
            EntityList.EntityEggInfo var8 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(var7);
            if (var8 != null) {
                this.addStat(var8.field_151513_e, 1);
            }
            var6.addToPlayerScore(this, this.scoreValue);
        }
        this.addStat(StatList.deathsStat, 1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean var3;
        if (this.isEntityInvulnerable()) {
            return false;
        }
        boolean bl = var3 = this.mcServer.isDedicatedServer() && this.mcServer.isPVPEnabled() && "fall".equals(par1DamageSource.damageType);
        if (!var3 && this.field_147101_bU > 0 && par1DamageSource != DamageSource.outOfWorld) {
            return false;
        }
        if (par1DamageSource instanceof EntityDamageSource) {
            Entity var4 = par1DamageSource.getEntity();
            if (var4 instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var4)) {
                return false;
            }
            if (var4 instanceof EntityArrow) {
                EntityArrow var5 = (EntityArrow)var4;
                if (var5.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var5.shootingEntity)) {
                    return false;
                }
            }
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    public boolean canAttackPlayer(EntityPlayer par1EntityPlayer) {
        return !this.mcServer.isPVPEnabled() ? false : super.canAttackPlayer(par1EntityPlayer);
    }

    @Override
    public void travelToDimension(int par1) {
        if (this.dimension == 1 && par1 == 1) {
            this.triggerAchievement(AchievementList.theEnd2);
            this.worldObj.removeEntity(this);
            this.playerConqueredTheEnd = true;
            this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0f));
        } else {
            if (this.dimension == 0 && par1 == 1) {
                this.triggerAchievement(AchievementList.theEnd);
                ChunkCoordinates var2 = this.mcServer.worldServerForDimension(par1).getEntrancePortalLocation();
                if (var2 != null) {
                    this.playerNetServerHandler.setPlayerLocation(var2.posX, var2.posY, var2.posZ, 0.0f, 0.0f);
                }
                par1 = 1;
            } else {
                this.triggerAchievement(AchievementList.portal);
            }
            this.mcServer.getConfigurationManager().transferPlayerToDimension(this, par1);
            this.lastExperience = -1;
            this.lastHealth = -1.0f;
            this.lastFoodLevel = -1;
        }
    }

    private void func_147097_b(TileEntity p_147097_1_) {
        Packet var2;
        if (p_147097_1_ != null && (var2 = p_147097_1_.getDescriptionPacket()) != null) {
            this.playerNetServerHandler.sendPacket(var2);
        }
    }

    @Override
    public void onItemPickup(Entity par1Entity, int par2) {
        super.onItemPickup(par1Entity, par2);
        this.openContainer.detectAndSendChanges();
    }

    @Override
    public EntityPlayer.EnumStatus sleepInBedAt(int par1, int par2, int par3) {
        EntityPlayer.EnumStatus var4 = super.sleepInBedAt(par1, par2, par3);
        if (var4 == EntityPlayer.EnumStatus.OK) {
            S0APacketUseBed var5 = new S0APacketUseBed(this, par1, par2, par3);
            this.getServerForPlayer().getEntityTracker().func_151247_a(this, var5);
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacket(var5);
        }
        return var4;
    }

    @Override
    public void wakeUpPlayer(boolean par1, boolean par2, boolean par3) {
        if (this.isPlayerSleeping()) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
        }
        super.wakeUpPlayer(par1, par2, par3);
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }

    @Override
    public void mountEntity(Entity par1Entity) {
        super.mountEntity(par1Entity);
        this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, this.ridingEntity));
        this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }

    @Override
    protected void updateFallState(double par1, boolean par3) {
    }

    public void handleFalling(double par1, boolean par3) {
        super.updateFallState(par1, par3);
    }

    @Override
    public void func_146100_a(TileEntity p_146100_1_) {
        if (p_146100_1_ instanceof TileEntitySign) {
            ((TileEntitySign)p_146100_1_).func_145912_a(this);
            this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(p_146100_1_.field_145851_c, p_146100_1_.field_145848_d, p_146100_1_.field_145849_e));
        }
    }

    private void getNextWindowId() {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }

    @Override
    public void displayGUIWorkbench(int par1, int par2, int par3) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 1, "Crafting", 9, true));
        this.openContainer = new ContainerWorkbench(this.inventory, this.worldObj, par1, par2, par3);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 4, par4Str == null ? "" : par4Str, 9, par4Str != null));
        this.openContainer = new ContainerEnchantment(this.inventory, this.worldObj, par1, par2, par3);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void displayGUIAnvil(int par1, int par2, int par3) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 8, "Repairing", 9, true));
        this.openContainer = new ContainerRepair(this.inventory, this.worldObj, par1, par2, par3, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void displayGUIChest(IInventory par1IInventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 0, par1IInventory.getInventoryName(), par1IInventory.getSizeInventory(), par1IInventory.isInventoryNameLocalized()));
        this.openContainer = new ContainerChest(this.inventory, par1IInventory);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void func_146093_a(TileEntityHopper p_146093_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 9, p_146093_1_.getInventoryName(), p_146093_1_.getSizeInventory(), p_146093_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, p_146093_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 9, par1EntityMinecartHopper.getInventoryName(), par1EntityMinecartHopper.getSizeInventory(), par1EntityMinecartHopper.isInventoryNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, par1EntityMinecartHopper);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void func_146101_a(TileEntityFurnace p_146101_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 2, p_146101_1_.getInventoryName(), p_146101_1_.getSizeInventory(), p_146101_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerFurnace(this.inventory, p_146101_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void func_146102_a(TileEntityDispenser p_146102_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, p_146102_1_ instanceof TileEntityDropper ? 10 : 3, p_146102_1_.getInventoryName(), p_146102_1_.getSizeInventory(), p_146102_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerDispenser(this.inventory, p_146102_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void func_146098_a(TileEntityBrewingStand p_146098_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 5, p_146098_1_.getInventoryName(), p_146098_1_.getSizeInventory(), p_146098_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerBrewingStand(this.inventory, p_146098_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void func_146104_a(TileEntityBeacon p_146104_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 7, p_146104_1_.getInventoryName(), p_146104_1_.getSizeInventory(), p_146104_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerBeacon(this.inventory, p_146104_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str) {
        this.getNextWindowId();
        this.openContainer = new ContainerMerchant(this.inventory, par1IMerchant, this.worldObj);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
        InventoryMerchant var3 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 6, par2Str == null ? "" : par2Str, var3.getSizeInventory(), par2Str != null));
        MerchantRecipeList var4 = par1IMerchant.getRecipes(this);
        if (var4 != null) {
            try {
                PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
                var5.writeInt(this.currentWindowId);
                var4.func_151391_a(var5);
                this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", var5));
            }
            catch (IOException var6) {
                logger.error("Couldn't send trade list", (Throwable)var6);
            }
        }
    }

    @Override
    public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 11, par2IInventory.getInventoryName(), par2IInventory.getSizeInventory(), par2IInventory.isInventoryNameLocalized(), par1EntityHorse.getEntityId()));
        this.openContainer = new ContainerHorseInventory(this.inventory, par2IInventory, par1EntityHorse);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }

    @Override
    public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack) {
        if (!(par1Container.getSlot(par2) instanceof SlotCrafting) && !this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(par1Container.windowId, par2, par3ItemStack));
        }
    }

    public void sendContainerToPlayer(Container par1Container) {
        this.sendContainerAndContentsToPlayer(par1Container, par1Container.getInventory());
    }

    @Override
    public void sendContainerAndContentsToPlayer(Container par1Container, List par2List) {
        this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(par1Container.windowId, par2List));
        this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
    }

    @Override
    public void sendProgressBarUpdate(Container par1Container, int par2, int par3) {
        this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(par1Container.windowId, par2, par3));
    }

    @Override
    public void closeScreen() {
        this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
        this.closeContainer();
    }

    public void updateHeldItem() {
        if (!this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }

    public void closeContainer() {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.inventoryContainer;
    }

    public void setEntityActionState(float par1, float par2, boolean par3, boolean par4) {
        if (this.ridingEntity != null) {
            if (par1 >= -1.0f && par1 <= 1.0f) {
                this.moveStrafing = par1;
            }
            if (par2 >= -1.0f && par2 <= 1.0f) {
                this.moveForward = par2;
            }
            this.isJumping = par3;
            this.setSneaking(par4);
        }
    }

    @Override
    public void addStat(StatBase par1StatBase, int par2) {
        if (par1StatBase != null) {
            this.field_147103_bO.func_150871_b(this, par1StatBase, par2);
            for (ScoreObjective var4 : this.getWorldScoreboard().func_96520_a(par1StatBase.func_150952_k())) {
                this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var4).func_96648_a();
            }
            if (this.field_147103_bO.func_150879_e()) {
                this.field_147103_bO.func_150876_a(this);
            }
        }
    }

    public void mountEntityAndWakeUp() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(this);
        }
        if (this.sleeping) {
            this.wakeUpPlayer(true, false, false);
        }
    }

    public void setPlayerHealthUpdated() {
        this.lastHealth = -1.0E8f;
    }

    @Override
    public void addChatComponentMessage(IChatComponent p_146105_1_) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(p_146105_1_));
    }

    @Override
    protected void onItemUseFinish() {
        this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, 9));
        super.onItemUseFinish();
    }

    @Override
    public void setItemInUse(ItemStack par1ItemStack, int par2) {
        super.setItemInUse(par1ItemStack, par2);
        if (par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
        }
    }

    @Override
    public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2) {
        super.clonePlayer(par1EntityPlayer, par2);
        this.lastExperience = -1;
        this.lastHealth = -1.0f;
        this.lastFoodLevel = -1;
        this.destroyedItemsNetCache.addAll(((EntityPlayerMP)par1EntityPlayer).destroyedItemsNetCache);
    }

    @Override
    protected void onNewPotionEffect(PotionEffect par1PotionEffect) {
        super.onNewPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), par1PotionEffect));
    }

    @Override
    protected void onChangedPotionEffect(PotionEffect par1PotionEffect, boolean par2) {
        super.onChangedPotionEffect(par1PotionEffect, par2);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), par1PotionEffect));
    }

    @Override
    protected void onFinishedPotionEffect(PotionEffect par1PotionEffect) {
        super.onFinishedPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(this.getEntityId(), par1PotionEffect));
    }

    @Override
    public void setPositionAndUpdate(double par1, double par3, double par5) {
        this.playerNetServerHandler.setPlayerLocation(par1, par3, par5, this.rotationYaw, this.rotationPitch);
    }

    @Override
    public void onCriticalHit(Entity par1Entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(par1Entity, 4));
    }

    @Override
    public void onEnchantmentCritical(Entity par1Entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(par1Entity, 5));
    }

    @Override
    public void sendPlayerAbilities() {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
        }
    }

    public WorldServer getServerForPlayer() {
        return (WorldServer)this.worldObj;
    }

    @Override
    public void setGameType(WorldSettings.GameType par1EnumGameType) {
        this.theItemInWorldManager.setGameType(par1EnumGameType);
        this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, par1EnumGameType.getID()));
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(p_145747_1_));
    }

    @Override
    public boolean canCommandSenderUseCommand(int par1, String par2Str) {
        return "seed".equals(par2Str) && !this.mcServer.isDedicatedServer() ? true : (!("tell".equals(par2Str) || "help".equals(par2Str) || "me".equals(par2Str)) ? (this.mcServer.getConfigurationManager().isPlayerOpped(this.getCommandSenderName()) ? this.mcServer.func_110455_j() >= par1 : false) : true);
    }

    public String getPlayerIP() {
        String var1 = this.playerNetServerHandler.netManager.getSocketAddress().toString();
        var1 = var1.substring(var1.indexOf("/") + 1);
        var1 = var1.substring(0, var1.indexOf(":"));
        return var1;
    }

    public void func_147100_a(C15PacketClientSettings p_147100_1_) {
        this.translator = p_147100_1_.func_149524_c();
        int var2 = 256 >> p_147100_1_.func_149521_d();
        if (var2 > 3 && var2 < 15) {
            this.renderDistance = var2;
        }
        this.chatVisibility = p_147100_1_.func_149523_e();
        this.chatColours = p_147100_1_.func_149520_f();
        if (this.mcServer.isSinglePlayer() && this.mcServer.getServerOwner().equals(this.getCommandSenderName())) {
            this.mcServer.func_147139_a(p_147100_1_.func_149518_g());
        }
        this.setHideCape(1, !p_147100_1_.func_149519_h());
    }

    public EntityPlayer.EnumChatVisibility func_147096_v() {
        return this.chatVisibility;
    }

    public void func_147095_a(String p_147095_1_) {
        this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|RPack", p_147095_1_.getBytes(Charsets.UTF_8)));
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ));
    }

    public void func_143004_u() {
        this.field_143005_bX = MinecraftServer.getSystemTimeMillis();
    }

    public StatisticsFile func_147099_x() {
        return this.field_147103_bO;
    }
}

