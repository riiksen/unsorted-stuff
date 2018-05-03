/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Util;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class EntityPlayer
extends EntityLivingBase
implements ICommandSender {
    public InventoryPlayer inventory;
    private InventoryEnderChest theInventoryEnderChest;
    public Container inventoryContainer;
    public Container openContainer;
    protected FoodStats foodStats;
    protected int flyToggleTimer;
    public float prevCameraYaw;
    public float cameraYaw;
    public int xpCooldown;
    public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;
    protected boolean sleeping;
    public ChunkCoordinates playerLocation;
    private int sleepTimer;
    public float field_71079_bU;
    public float field_71082_cx;
    public float field_71089_bV;
    private ChunkCoordinates spawnChunk;
    private boolean spawnForced;
    private ChunkCoordinates startMinecartRidingCoordinate;
    public PlayerCapabilities capabilities;
    public int experienceLevel;
    public int experienceTotal;
    public float experience;
    private ItemStack itemInUse;
    private int itemInUseCount;
    protected float speedOnGround;
    protected float speedInAir;
    private int field_82249_h;
    public GameProfile field_146106_i;
    public EntityFishHook fishEntity;
    private static final String __OBFID = "CL_00001711";

    public EntityPlayer(World p_i45324_1_, GameProfile p_i45324_2_) {
        super(p_i45324_1_);
        this.inventory = new InventoryPlayer(this);
        this.theInventoryEnderChest = new InventoryEnderChest();
        this.foodStats = new FoodStats();
        this.capabilities = new PlayerCapabilities();
        this.speedOnGround = 0.1f;
        this.speedInAir = 0.02f;
        this.entityUniqueID = EntityPlayer.func_146094_a(p_i45324_2_);
        this.field_146106_i = p_i45324_2_;
        this.openContainer = this.inventoryContainer = new ContainerPlayer(this.inventory, !p_i45324_1_.isClient, this);
        this.yOffset = 1.62f;
        ChunkCoordinates var3 = p_i45324_1_.getSpawnPoint();
        this.setLocationAndAngles((double)var3.posX + 0.5, var3.posY + 1, (double)var3.posZ + 0.5, 0.0f, 0.0f);
        this.field_70741_aB = 180.0f;
        this.fireResistance = 20;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf(0));
        this.dataWatcher.addObject(17, Float.valueOf(0.0f));
        this.dataWatcher.addObject(18, 0);
    }

    public ItemStack getItemInUse() {
        return this.itemInUse;
    }

    public int getItemInUseCount() {
        return this.itemInUseCount;
    }

    public boolean isUsingItem() {
        if (this.itemInUse != null) {
            return true;
        }
        return false;
    }

    public int getItemInUseDuration() {
        return this.isUsingItem() ? this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount : 0;
    }

    public void stopUsingItem() {
        if (this.itemInUse != null) {
            this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
        }
        this.clearItemInUse();
    }

    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = 0;
        if (!this.worldObj.isClient) {
            this.setEating(false);
        }
    }

    public boolean isBlocking() {
        if (this.isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.block) {
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate() {
        if (this.itemInUse != null) {
            ItemStack var1 = this.inventory.getCurrentItem();
            if (var1 == this.itemInUse) {
                if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
                    this.updateItemUse(var1, 5);
                }
                if (--this.itemInUseCount == 0 && !this.worldObj.isClient) {
                    this.onItemUseFinish();
                }
            } else {
                this.clearItemInUse();
            }
        }
        if (this.xpCooldown > 0) {
            --this.xpCooldown;
        }
        if (this.isPlayerSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.worldObj.isClient) {
                if (!this.isInBed()) {
                    this.wakeUpPlayer(true, true, false);
                } else if (this.worldObj.isDaytime()) {
                    this.wakeUpPlayer(false, true, true);
                }
            }
        } else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        super.onUpdate();
        if (!this.worldObj.isClient && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        if (this.isBurning() && this.capabilities.disableDamage) {
            this.extinguish();
        }
        this.field_71091_bM = this.field_71094_bP;
        this.field_71096_bN = this.field_71095_bQ;
        this.field_71097_bO = this.field_71085_bR;
        double var9 = this.posX - this.field_71094_bP;
        double var3 = this.posY - this.field_71095_bQ;
        double var5 = this.posZ - this.field_71085_bR;
        double var7 = 10.0;
        if (var9 > var7) {
            this.field_71091_bM = this.field_71094_bP = this.posX;
        }
        if (var5 > var7) {
            this.field_71097_bO = this.field_71085_bR = this.posZ;
        }
        if (var3 > var7) {
            this.field_71096_bN = this.field_71095_bQ = this.posY;
        }
        if (var9 < - var7) {
            this.field_71091_bM = this.field_71094_bP = this.posX;
        }
        if (var5 < - var7) {
            this.field_71097_bO = this.field_71085_bR = this.posZ;
        }
        if (var3 < - var7) {
            this.field_71096_bN = this.field_71095_bQ = this.posY;
        }
        this.field_71094_bP += var9 * 0.25;
        this.field_71085_bR += var5 * 0.25;
        this.field_71095_bQ += var3 * 0.25;
        if (this.ridingEntity == null) {
            this.startMinecartRidingCoordinate = null;
        }
        if (!this.worldObj.isClient) {
            this.foodStats.onUpdate(this);
            this.addStat(StatList.minutesPlayedStat, 1);
        }
    }

    @Override
    public int getMaxInPortalTime() {
        return this.capabilities.disableDamage ? 0 : 80;
    }

    @Override
    protected String getSwimSound() {
        return "game.player.swim";
    }

    @Override
    protected String getSplashSound() {
        return "game.player.swim.splash";
    }

    @Override
    public int getPortalCooldown() {
        return 10;
    }

    @Override
    public void playSound(String par1Str, float par2, float par3) {
        this.worldObj.playSoundToNearExcept(this, par1Str, par2, par3);
    }

    protected void updateItemUse(ItemStack par1ItemStack, int par2) {
        if (par1ItemStack.getItemUseAction() == EnumAction.drink) {
            this.playSound("random.drink", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (par1ItemStack.getItemUseAction() == EnumAction.eat) {
            int var3 = 0;
            while (var3 < par2) {
                Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool(((double)this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                var4.rotateAroundX((- this.rotationPitch) * 3.1415927f / 180.0f);
                var4.rotateAroundY((- this.rotationYaw) * 3.1415927f / 180.0f);
                Vec3 var5 = this.worldObj.getWorldVec3Pool().getVecFromPool(((double)this.rand.nextFloat() - 0.5) * 0.3, (double)(- this.rand.nextFloat()) * 0.6 - 0.3, 0.6);
                var5.rotateAroundX((- this.rotationPitch) * 3.1415927f / 180.0f);
                var5.rotateAroundY((- this.rotationYaw) * 3.1415927f / 180.0f);
                var5 = var5.addVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
                String var6 = "iconcrack_" + Item.getIdFromItem(par1ItemStack.getItem());
                if (par1ItemStack.getHasSubtypes()) {
                    var6 = String.valueOf(var6) + "_" + par1ItemStack.getItemDamage();
                }
                this.worldObj.spawnParticle(var6, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05, var4.zCoord);
                ++var3;
            }
            this.playSound("random.eat", 0.5f + 0.5f * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }

    protected void onItemUseFinish() {
        if (this.itemInUse != null) {
            this.updateItemUse(this.itemInUse, 16);
            int var1 = this.itemInUse.stackSize;
            ItemStack var2 = this.itemInUse.onFoodEaten(this.worldObj, this);
            if (var2 != this.itemInUse || var2 != null && var2.stackSize != var1) {
                this.inventory.mainInventory[this.inventory.currentItem] = var2;
                if (var2.stackSize == 0) {
                    this.inventory.mainInventory[this.inventory.currentItem] = null;
                }
            }
            this.clearItemInUse();
        }
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 9) {
            this.onItemUseFinish();
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    @Override
    protected boolean isMovementBlocked() {
        if (this.getHealth() > 0.0f && !this.isPlayerSleeping()) {
            return false;
        }
        return true;
    }

    protected void closeScreen() {
        this.openContainer = this.inventoryContainer;
    }

    @Override
    public void mountEntity(Entity par1Entity) {
        if (this.ridingEntity != null && par1Entity == null) {
            if (!this.worldObj.isClient) {
                this.dismountEntity(this.ridingEntity);
            }
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        } else {
            super.mountEntity(par1Entity);
        }
    }

    @Override
    public void updateRidden() {
        if (!this.worldObj.isClient && this.isSneaking()) {
            this.mountEntity(null);
            this.setSneaking(false);
        } else {
            double var1 = this.posX;
            double var3 = this.posY;
            double var5 = this.posZ;
            float var7 = this.rotationYaw;
            float var8 = this.rotationPitch;
            super.updateRidden();
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0.0f;
            this.addMountedMovementStat(this.posX - var1, this.posY - var3, this.posZ - var5);
            if (this.ridingEntity instanceof EntityPig) {
                this.rotationPitch = var8;
                this.rotationYaw = var7;
                this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
            }
        }
    }

    @Override
    public void preparePlayerToSpawn() {
        this.yOffset = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = 0;
    }

    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.updateArmSwingProgress();
    }

    @Override
    public void onLivingUpdate() {
        if (this.flyToggleTimer > 0) {
            --this.flyToggleTimer;
        }
        if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getHealth() < this.getMaxHealth() && this.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.ticksExisted % 20 * 12 == 0) {
            this.heal(1.0f);
        }
        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (!this.worldObj.isClient) {
            var1.setBaseValue(this.capabilities.getWalkSpeed());
        }
        this.jumpMovementFactor = this.speedInAir;
        if (this.isSprinting()) {
            this.jumpMovementFactor = (float)((double)this.jumpMovementFactor + (double)this.speedInAir * 0.3);
        }
        this.setAIMoveSpeed((float)var1.getAttributeValue());
        float var2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var3 = (float)Math.atan((- this.motionY) * 0.20000000298023224) * 15.0f;
        if (var2 > 0.1f) {
            var2 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            var2 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            var3 = 0.0f;
        }
        this.cameraYaw += (var2 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (var3 - this.cameraPitch) * 0.8f;
        if (this.getHealth() > 0.0f) {
            AxisAlignedBB var4 = null;
            var4 = this.ridingEntity != null && !this.ridingEntity.isDead ? this.boundingBox.func_111270_a(this.ridingEntity.boundingBox).expand(1.0, 0.0, 1.0) : this.boundingBox.expand(1.0, 0.5, 1.0);
            List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, var4);
            if (var5 != null) {
                int var6 = 0;
                while (var6 < var5.size()) {
                    Entity var7 = (Entity)var5.get(var6);
                    if (!var7.isDead) {
                        this.collideWithPlayer(var7);
                    }
                    ++var6;
                }
            }
        }
    }

    private void collideWithPlayer(Entity par1Entity) {
        par1Entity.onCollideWithPlayer(this);
    }

    public int getScore() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void setScore(int par1) {
        this.dataWatcher.updateObject(18, par1);
    }

    public void addScore(int par1) {
        int var2 = this.getScore();
        this.dataWatcher.updateObject(18, var2 + par1);
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612;
        if (this.getCommandSenderName().equals("Notch")) {
            this.func_146097_a(new ItemStack(Items.apple, 1), true, false);
        }
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        if (par1DamageSource != null) {
            this.motionX = (- MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f)) * 0.1f;
            this.motionZ = (- MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f)) * 0.1f;
        } else {
            this.motionZ = 0.0;
            this.motionX = 0.0;
        }
        this.yOffset = 0.1f;
        this.addStat(StatList.deathsStat, 1);
    }

    @Override
    protected String getHurtSound() {
        return "game.player.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "game.player.die";
    }

    @Override
    public void addToPlayerScore(Entity par1Entity, int par2) {
        this.addScore(par2);
        Collection var3 = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.totalKillCount);
        if (par1Entity instanceof EntityPlayer) {
            this.addStat(StatList.playerKillsStat, 1);
            var3.addAll(this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.playerKillCount));
        } else {
            this.addStat(StatList.mobKillsStat, 1);
        }
        for (ScoreObjective var5 : var3) {
            Score var6 = this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var5);
            var6.func_96648_a();
        }
    }

    public EntityItem dropOneItem(boolean par1) {
        return this.func_146097_a(this.inventory.decrStackSize(this.inventory.currentItem, par1 && this.inventory.getCurrentItem() != null ? this.inventory.getCurrentItem().stackSize : 1), false, true);
    }

    public EntityItem dropPlayerItemWithRandomChoice(ItemStack par1ItemStack, boolean par2) {
        return this.func_146097_a(par1ItemStack, false, false);
    }

    public EntityItem func_146097_a(ItemStack p_146097_1_, boolean p_146097_2_, boolean p_146097_3_) {
        if (p_146097_1_ == null) {
            return null;
        }
        if (p_146097_1_.stackSize == 0) {
            return null;
        }
        EntityItem var4 = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896 + (double)this.getEyeHeight(), this.posZ, p_146097_1_);
        var4.delayBeforeCanPickup = 40;
        if (p_146097_3_) {
            var4.func_145799_b(this.getCommandSenderName());
        }
        float var5 = 0.1f;
        if (p_146097_2_) {
            float var6 = this.rand.nextFloat() * 0.5f;
            float var7 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            var4.motionX = (- MathHelper.sin(var7)) * var6;
            var4.motionZ = MathHelper.cos(var7) * var6;
            var4.motionY = 0.20000000298023224;
        } else {
            var5 = 0.3f;
            var4.motionX = (- MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f)) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var5;
            var4.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var5;
            var4.motionY = (- MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f)) * var5 + 0.1f;
            var5 = 0.02f;
            float var6 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            var4.motionX += Math.cos(var6) * (double)(var5 *= this.rand.nextFloat());
            var4.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
            var4.motionZ += Math.sin(var6) * (double)var5;
        }
        this.joinEntityItemWithWorld(var4);
        this.addStat(StatList.dropStat, 1);
        return var4;
    }

    protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {
        this.worldObj.spawnEntityInWorld(par1EntityItem);
    }

    public float getCurrentPlayerStrVsBlock(Block p_146096_1_, boolean p_146096_2_) {
        float var3 = this.inventory.func_146023_a(p_146096_1_);
        if (var3 > 1.0f) {
            int var4 = EnchantmentHelper.getEfficiencyModifier(this);
            ItemStack var5 = this.inventory.getCurrentItem();
            if (var4 > 0 && var5 != null) {
                float var6 = var4 * var4 + 1;
                var3 = !var5.func_150998_b(p_146096_1_) && var3 <= 1.0f ? (var3 += var6 * 0.08f) : (var3 += var6);
            }
        }
        if (this.isPotionActive(Potion.digSpeed)) {
            var3 *= 1.0f + (float)(this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (this.isPotionActive(Potion.digSlowdown)) {
            var3 *= 1.0f - (float)(this.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2f;
        }
        if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            var3 /= 5.0f;
        }
        if (!this.onGround) {
            var3 /= 5.0f;
        }
        return var3;
    }

    public boolean canHarvestBlock(Block p_146099_1_) {
        return this.inventory.func_146025_b(p_146099_1_);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.entityUniqueID = EntityPlayer.func_146094_a(this.field_146106_i);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Inventory", 10);
        this.inventory.readFromNBT(var2);
        this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
        this.sleeping = par1NBTTagCompound.getBoolean("Sleeping");
        this.sleepTimer = par1NBTTagCompound.getShort("SleepTimer");
        this.experience = par1NBTTagCompound.getFloat("XpP");
        this.experienceLevel = par1NBTTagCompound.getInteger("XpLevel");
        this.experienceTotal = par1NBTTagCompound.getInteger("XpTotal");
        this.setScore(par1NBTTagCompound.getInteger("Score"));
        if (this.sleeping) {
            this.playerLocation = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.wakeUpPlayer(true, true, false);
        }
        if (par1NBTTagCompound.func_150297_b("SpawnX", 99) && par1NBTTagCompound.func_150297_b("SpawnY", 99) && par1NBTTagCompound.func_150297_b("SpawnZ", 99)) {
            this.spawnChunk = new ChunkCoordinates(par1NBTTagCompound.getInteger("SpawnX"), par1NBTTagCompound.getInteger("SpawnY"), par1NBTTagCompound.getInteger("SpawnZ"));
            this.spawnForced = par1NBTTagCompound.getBoolean("SpawnForced");
        }
        this.foodStats.readNBT(par1NBTTagCompound);
        this.capabilities.readCapabilitiesFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.func_150297_b("EnderItems", 9)) {
            NBTTagList var3 = par1NBTTagCompound.getTagList("EnderItems", 10);
            this.theInventoryEnderChest.loadInventoryFromNBT(var3);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        par1NBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
        par1NBTTagCompound.setBoolean("Sleeping", this.sleeping);
        par1NBTTagCompound.setShort("SleepTimer", (short)this.sleepTimer);
        par1NBTTagCompound.setFloat("XpP", this.experience);
        par1NBTTagCompound.setInteger("XpLevel", this.experienceLevel);
        par1NBTTagCompound.setInteger("XpTotal", this.experienceTotal);
        par1NBTTagCompound.setInteger("Score", this.getScore());
        if (this.spawnChunk != null) {
            par1NBTTagCompound.setInteger("SpawnX", this.spawnChunk.posX);
            par1NBTTagCompound.setInteger("SpawnY", this.spawnChunk.posY);
            par1NBTTagCompound.setInteger("SpawnZ", this.spawnChunk.posZ);
            par1NBTTagCompound.setBoolean("SpawnForced", this.spawnForced);
        }
        this.foodStats.writeNBT(par1NBTTagCompound);
        this.capabilities.writeCapabilitiesToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
    }

    public void displayGUIChest(IInventory par1IInventory) {
    }

    public void func_146093_a(TileEntityHopper p_146093_1_) {
    }

    public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper) {
    }

    public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory) {
    }

    public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str) {
    }

    public void displayGUIAnvil(int par1, int par2, int par3) {
    }

    public void displayGUIWorkbench(int par1, int par2, int par3) {
    }

    @Override
    public float getEyeHeight() {
        return 0.12f;
    }

    protected void resetHeight() {
        this.yOffset = 1.62f;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.capabilities.disableDamage && !par1DamageSource.canHarmInCreative()) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        if (this.isPlayerSleeping() && !this.worldObj.isClient) {
            this.wakeUpPlayer(true, true, false);
        }
        if (par1DamageSource.isDifficultyScaled()) {
            if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
                par2 = 0.0f;
            }
            if (this.worldObj.difficultySetting == EnumDifficulty.EASY) {
                par2 = par2 / 2.0f + 1.0f;
            }
            if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                par2 = par2 * 3.0f / 2.0f;
            }
        }
        if (par2 == 0.0f) {
            return false;
        }
        Entity var3 = par1DamageSource.getEntity();
        if (var3 instanceof EntityArrow && ((EntityArrow)var3).shootingEntity != null) {
            var3 = ((EntityArrow)var3).shootingEntity;
        }
        this.addStat(StatList.damageTakenStat, Math.round(par2 * 10.0f));
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public boolean canAttackPlayer(EntityPlayer par1EntityPlayer) {
        Team var2 = this.getTeam();
        Team var3 = par1EntityPlayer.getTeam();
        return var2 == null ? true : (!var2.isSameTeam(var3) ? true : var2.getAllowFriendlyFire());
    }

    @Override
    protected void damageArmor(float par1) {
        this.inventory.damageArmor(par1);
    }

    @Override
    public int getTotalArmorValue() {
        return this.inventory.getTotalArmorValue();
    }

    public float getArmorVisibility() {
        int var1 = 0;
        ItemStack[] var2 = this.inventory.armorInventory;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            ItemStack var5 = var2[var4];
            if (var5 != null) {
                ++var1;
            }
            ++var4;
        }
        return (float)var1 / (float)this.inventory.armorInventory.length;
    }

    @Override
    protected void damageEntity(DamageSource par1DamageSource, float par2) {
        if (!this.isEntityInvulnerable()) {
            if (!par1DamageSource.isUnblockable() && this.isBlocking() && par2 > 0.0f) {
                par2 = (1.0f + par2) * 0.5f;
            }
            par2 = this.applyArmorCalculations(par1DamageSource, par2);
            float var3 = par2 = this.applyPotionDamageCalculations(par1DamageSource, par2);
            par2 = Math.max(par2 - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (var3 - par2));
            if (par2 != 0.0f) {
                this.addExhaustion(par1DamageSource.getHungerDamage());
                float var4 = this.getHealth();
                this.setHealth(this.getHealth() - par2);
                this.func_110142_aN().func_94547_a(par1DamageSource, var4, par2);
            }
        }
    }

    public void func_146101_a(TileEntityFurnace p_146101_1_) {
    }

    public void func_146102_a(TileEntityDispenser p_146102_1_) {
    }

    public void func_146100_a(TileEntity p_146100_1_) {
    }

    public void func_146095_a(CommandBlockLogic p_146095_1_) {
    }

    public void func_146098_a(TileEntityBrewingStand p_146098_1_) {
    }

    public void func_146104_a(TileEntityBeacon p_146104_1_) {
    }

    public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str) {
    }

    public void displayGUIBook(ItemStack par1ItemStack) {
    }

    public boolean interactWith(Entity par1Entity) {
        ItemStack var3;
        ItemStack var2 = this.getCurrentEquippedItem();
        ItemStack itemStack = var3 = var2 != null ? var2.copy() : null;
        if (!par1Entity.interactFirst(this)) {
            if (var2 != null && par1Entity instanceof EntityLivingBase) {
                if (this.capabilities.isCreativeMode) {
                    var2 = var3;
                }
                if (var2.interactWithEntity(this, (EntityLivingBase)par1Entity)) {
                    if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                        this.destroyCurrentEquippedItem();
                    }
                    return true;
                }
            }
            return false;
        }
        if (var2 != null && var2 == this.getCurrentEquippedItem()) {
            if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                this.destroyCurrentEquippedItem();
            } else if (var2.stackSize < var3.stackSize && this.capabilities.isCreativeMode) {
                var2.stackSize = var3.stackSize;
            }
        }
        return true;
    }

    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }

    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }

    @Override
    public double getYOffset() {
        return this.yOffset - 0.5f;
    }

    public void attackTargetEntityWithCurrentItem(Entity par1Entity) {
        if (par1Entity.canAttackWithItem() && !par1Entity.hitByEntity(this)) {
            float var2 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            int var3 = 0;
            float var4 = 0.0f;
            if (par1Entity instanceof EntityLivingBase) {
                var4 = EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
                var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
            }
            if (this.isSprinting()) {
                ++var3;
            }
            if (var2 > 0.0f || var4 > 0.0f) {
                boolean var8;
                boolean var5;
                boolean bl = var5 = this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && par1Entity instanceof EntityLivingBase;
                if (var5 && var2 > 0.0f) {
                    var2 *= 1.5f;
                }
                var2 += var4;
                boolean var6 = false;
                int var7 = EnchantmentHelper.getFireAspectModifier(this);
                if (par1Entity instanceof EntityLivingBase && var7 > 0 && !par1Entity.isBurning()) {
                    var6 = true;
                    par1Entity.setFire(1);
                }
                if (var8 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(this), var2)) {
                    IEntityMultiPart var11;
                    if (var3 > 0) {
                        par1Entity.addVelocity((- MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)) * (float)var3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * (float)var3 * 0.5f);
                        this.motionX *= 0.6;
                        this.motionZ *= 0.6;
                        this.setSprinting(false);
                    }
                    if (var5) {
                        this.onCriticalHit(par1Entity);
                    }
                    if (var4 > 0.0f) {
                        this.onEnchantmentCritical(par1Entity);
                    }
                    if (var2 >= 18.0f) {
                        this.triggerAchievement(AchievementList.overkill);
                    }
                    this.setLastAttacker(par1Entity);
                    if (par1Entity instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a((EntityLivingBase)par1Entity, this);
                    }
                    EnchantmentHelper.func_151385_b(this, par1Entity);
                    ItemStack var9 = this.getCurrentEquippedItem();
                    Entity var10 = par1Entity;
                    if (par1Entity instanceof EntityDragonPart && (var11 = ((EntityDragonPart)par1Entity).entityDragonObj) != null && var11 instanceof EntityLivingBase) {
                        var10 = (EntityLivingBase)((Object)var11);
                    }
                    if (var9 != null && var10 instanceof EntityLivingBase) {
                        var9.hitEntity((EntityLivingBase)var10, this);
                        if (var9.stackSize <= 0) {
                            this.destroyCurrentEquippedItem();
                        }
                    }
                    if (par1Entity instanceof EntityLivingBase) {
                        this.addStat(StatList.damageDealtStat, Math.round(var2 * 10.0f));
                        if (var7 > 0) {
                            par1Entity.setFire(var7 * 4);
                        }
                    }
                    this.addExhaustion(0.3f);
                } else if (var6) {
                    par1Entity.extinguish();
                }
            }
        }
    }

    public void onCriticalHit(Entity par1Entity) {
    }

    public void onEnchantmentCritical(Entity par1Entity) {
    }

    public void respawnPlayer() {
    }

    @Override
    public void setDead() {
        super.setDead();
        this.inventoryContainer.onContainerClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onContainerClosed(this);
        }
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (!this.sleeping && super.isEntityInsideOpaqueBlock()) {
            return true;
        }
        return false;
    }

    public GameProfile getGameProfile() {
        return this.field_146106_i;
    }

    public EnumStatus sleepInBedAt(int par1, int par2, int par3) {
        if (!this.worldObj.isClient) {
            if (this.isPlayerSleeping() || !this.isEntityAlive()) {
                return EnumStatus.OTHER_PROBLEM;
            }
            if (!this.worldObj.provider.isSurfaceWorld()) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }
            if (this.worldObj.isDaytime()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.posX - (double)par1) > 3.0 || Math.abs(this.posY - (double)par2) > 2.0 || Math.abs(this.posZ - (double)par3) > 3.0) {
                return EnumStatus.TOO_FAR_AWAY;
            }
            double var4 = 8.0;
            double var6 = 5.0;
            List var8 = this.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().getAABB((double)par1 - var4, (double)par2 - var6, (double)par3 - var4, (double)par1 + var4, (double)par2 + var6, (double)par3 + var4));
            if (!var8.isEmpty()) {
                return EnumStatus.NOT_SAFE;
            }
        }
        if (this.isRiding()) {
            this.mountEntity(null);
        }
        this.setSize(0.2f, 0.2f);
        this.yOffset = 0.2f;
        if (this.worldObj.blockExists(par1, par2, par3)) {
            int var9 = this.worldObj.getBlockMetadata(par1, par2, par3);
            int var5 = BlockBed.func_149895_l(var9);
            float var10 = 0.5f;
            float var7 = 0.5f;
            switch (var5) {
                case 0: {
                    var7 = 0.9f;
                    break;
                }
                case 1: {
                    var10 = 0.1f;
                    break;
                }
                case 2: {
                    var7 = 0.1f;
                    break;
                }
                case 3: {
                    var10 = 0.9f;
                }
            }
            this.func_71013_b(var5);
            this.setPosition((float)par1 + var10, (float)par2 + 0.9375f, (float)par3 + var7);
        } else {
            this.setPosition((float)par1 + 0.5f, (float)par2 + 0.9375f, (float)par3 + 0.5f);
        }
        this.sleeping = true;
        this.sleepTimer = 0;
        this.playerLocation = new ChunkCoordinates(par1, par2, par3);
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.motionX = 0.0;
        if (!this.worldObj.isClient) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        return EnumStatus.OK;
    }

    private void func_71013_b(int par1) {
        this.field_71079_bU = 0.0f;
        this.field_71089_bV = 0.0f;
        switch (par1) {
            case 0: {
                this.field_71089_bV = -1.8f;
                break;
            }
            case 1: {
                this.field_71079_bU = 1.8f;
                break;
            }
            case 2: {
                this.field_71089_bV = 1.8f;
                break;
            }
            case 3: {
                this.field_71079_bU = -1.8f;
            }
        }
    }

    public void wakeUpPlayer(boolean par1, boolean par2, boolean par3) {
        this.setSize(0.6f, 1.8f);
        this.resetHeight();
        ChunkCoordinates var4 = this.playerLocation;
        ChunkCoordinates var5 = this.playerLocation;
        if (var4 != null && this.worldObj.getBlock(var4.posX, var4.posY, var4.posZ) == Blocks.bed) {
            BlockBed.func_149979_a(this.worldObj, var4.posX, var4.posY, var4.posZ, false);
            var5 = BlockBed.func_149977_a(this.worldObj, var4.posX, var4.posY, var4.posZ, 0);
            if (var5 == null) {
                var5 = new ChunkCoordinates(var4.posX, var4.posY + 1, var4.posZ);
            }
            this.setPosition((float)var5.posX + 0.5f, (float)var5.posY + this.yOffset + 0.1f, (float)var5.posZ + 0.5f);
        }
        this.sleeping = false;
        if (!this.worldObj.isClient && par2) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        this.sleepTimer = par1 ? 0 : 100;
        if (par3) {
            this.setSpawnChunk(this.playerLocation, false);
        }
    }

    private boolean isInBed() {
        if (this.worldObj.getBlock(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ) == Blocks.bed) {
            return true;
        }
        return false;
    }

    public static ChunkCoordinates verifyRespawnCoordinates(World par0World, ChunkCoordinates par1ChunkCoordinates, boolean par2) {
        IChunkProvider var3 = par0World.getChunkProvider();
        var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
        var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
        var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
        var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
        if (par0World.getBlock(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ) == Blocks.bed) {
            ChunkCoordinates var8 = BlockBed.func_149977_a(par0World, par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ, 0);
            return var8;
        }
        Material var4 = par0World.getBlock(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ).getMaterial();
        Material var5 = par0World.getBlock(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY + 1, par1ChunkCoordinates.posZ).getMaterial();
        boolean var6 = !var4.isSolid() && !var4.isLiquid();
        boolean var7 = !var5.isSolid() && !var5.isLiquid();
        return par2 && var6 && var7 ? par1ChunkCoordinates : null;
    }

    public float getBedOrientationInDegrees() {
        if (this.playerLocation != null) {
            int var1 = this.worldObj.getBlockMetadata(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ);
            int var2 = BlockBed.func_149895_l(var1);
            switch (var2) {
                case 0: {
                    return 90.0f;
                }
                case 1: {
                    return 0.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }

    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }

    public boolean isPlayerFullyAsleep() {
        if (this.sleeping && this.sleepTimer >= 100) {
            return true;
        }
        return false;
    }

    public int getSleepTimer() {
        return this.sleepTimer;
    }

    protected boolean getHideCape(int par1) {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1 << par1) != 0) {
            return true;
        }
        return false;
    }

    protected void setHideCape(int par1, boolean par2) {
        byte var3 = this.dataWatcher.getWatchableObjectByte(16);
        if (par2) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var3 | 1 << par1)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var3 & ~ (1 << par1))));
        }
    }

    public void addChatComponentMessage(IChatComponent p_146105_1_) {
    }

    public ChunkCoordinates getBedLocation() {
        return this.spawnChunk;
    }

    public boolean isSpawnForced() {
        return this.spawnForced;
    }

    public void setSpawnChunk(ChunkCoordinates par1ChunkCoordinates, boolean par2) {
        if (par1ChunkCoordinates != null) {
            this.spawnChunk = new ChunkCoordinates(par1ChunkCoordinates);
            this.spawnForced = par2;
        } else {
            this.spawnChunk = null;
            this.spawnForced = false;
        }
    }

    public void triggerAchievement(StatBase par1StatBase) {
        this.addStat(par1StatBase, 1);
    }

    public void addStat(StatBase par1StatBase, int par2) {
    }

    @Override
    public void jump() {
        super.jump();
        this.addStat(StatList.jumpStat, 1);
        if (this.isSprinting()) {
            this.addExhaustion(0.8f);
        } else {
            this.addExhaustion(0.2f);
        }
    }

    @Override
    public void moveEntityWithHeading(float par1, float par2) {
        double var3 = this.posX;
        double var5 = this.posY;
        double var7 = this.posZ;
        if (this.capabilities.isFlying && this.ridingEntity == null) {
            double var9 = this.motionY;
            float var11 = this.jumpMovementFactor;
            this.jumpMovementFactor = this.capabilities.getFlySpeed();
            super.moveEntityWithHeading(par1, par2);
            this.motionY = var9 * 0.6;
            this.jumpMovementFactor = var11;
        } else {
            super.moveEntityWithHeading(par1, par2);
        }
        this.addMovementStat(this.posX - var3, this.posY - var5, this.posZ - var7);
    }

    @Override
    public float getAIMoveSpeed() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
    }

    public void addMovementStat(double par1, double par3, double par5) {
        if (this.ridingEntity == null) {
            if (this.isInsideOfMaterial(Material.water)) {
                int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceDoveStat, var7);
                    this.addExhaustion(0.015f * (float)var7 * 0.01f);
                }
            } else if (this.isInWater()) {
                int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceSwumStat, var7);
                    this.addExhaustion(0.015f * (float)var7 * 0.01f);
                }
            } else if (this.isOnLadder()) {
                if (par3 > 0.0) {
                    this.addStat(StatList.distanceClimbedStat, (int)Math.round(par3 * 100.0));
                }
            } else if (this.onGround) {
                int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceWalkedStat, var7);
                    if (this.isSprinting()) {
                        this.addExhaustion(0.099999994f * (float)var7 * 0.01f);
                    } else {
                        this.addExhaustion(0.01f * (float)var7 * 0.01f);
                    }
                }
            } else {
                int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0f);
                if (var7 > 25) {
                    this.addStat(StatList.distanceFlownStat, var7);
                }
            }
        }
    }

    private void addMountedMovementStat(double par1, double par3, double par5) {
        int var7;
        if (this.ridingEntity != null && (var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0f)) > 0) {
            if (this.ridingEntity instanceof EntityMinecart) {
                this.addStat(StatList.distanceByMinecartStat, var7);
                if (this.startMinecartRidingCoordinate == null) {
                    this.startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
                } else if ((double)this.startMinecartRidingCoordinate.getDistanceSquared(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0) {
                    this.addStat(AchievementList.onARail, 1);
                }
            } else if (this.ridingEntity instanceof EntityBoat) {
                this.addStat(StatList.distanceByBoatStat, var7);
            } else if (this.ridingEntity instanceof EntityPig) {
                this.addStat(StatList.distanceByPigStat, var7);
            } else if (this.ridingEntity instanceof EntityHorse) {
                this.addStat(StatList.field_151185_q, var7);
            }
        }
    }

    @Override
    protected void fall(float par1) {
        if (!this.capabilities.allowFlying) {
            if (par1 >= 2.0f) {
                this.addStat(StatList.distanceFallenStat, (int)Math.round((double)par1 * 100.0));
            }
            super.fall(par1);
        }
    }

    @Override
    protected String func_146067_o(int p_146067_1_) {
        return p_146067_1_ > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }

    @Override
    public void onKillEntity(EntityLivingBase par1EntityLivingBase) {
        int var2;
        EntityList.EntityEggInfo var3;
        if (par1EntityLivingBase instanceof IMob) {
            this.triggerAchievement(AchievementList.killEnemy);
        }
        if ((var3 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(var2 = EntityList.getEntityID(par1EntityLivingBase))) != null) {
            this.addStat(var3.field_151512_d, 1);
        }
    }

    @Override
    public void setInWeb() {
        if (!this.capabilities.isFlying) {
            super.setInWeb();
        }
    }

    @Override
    public IIcon getItemIcon(ItemStack par1ItemStack, int par2) {
        IIcon var3 = super.getItemIcon(par1ItemStack, par2);
        if (par1ItemStack.getItem() == Items.fishing_rod && this.fishEntity != null) {
            var3 = Items.fishing_rod.func_94597_g();
        } else {
            if (par1ItemStack.getItem().requiresMultipleRenderPasses()) {
                return par1ItemStack.getItem().getIconFromDamageForRenderPass(par1ItemStack.getItemDamage(), par2);
            }
            if (this.itemInUse != null && par1ItemStack.getItem() == Items.bow) {
                int var4 = par1ItemStack.getMaxItemUseDuration() - this.itemInUseCount;
                if (var4 >= 18) {
                    return Items.bow.getItemIconForUseDuration(2);
                }
                if (var4 > 13) {
                    return Items.bow.getItemIconForUseDuration(1);
                }
                if (var4 > 0) {
                    return Items.bow.getItemIconForUseDuration(0);
                }
            }
        }
        return var3;
    }

    public ItemStack getCurrentArmor(int par1) {
        return this.inventory.armorItemInSlot(par1);
    }

    public void addExperience(int par1) {
        this.addScore(par1);
        int var2 = Integer.MAX_VALUE - this.experienceTotal;
        if (par1 > var2) {
            par1 = var2;
        }
        this.experience += (float)par1 / (float)this.xpBarCap();
        this.experienceTotal += par1;
        while (this.experience >= 1.0f) {
            this.experience = (this.experience - 1.0f) * (float)this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience /= (float)this.xpBarCap();
        }
    }

    public void addExperienceLevel(int par1) {
        this.experienceLevel += par1;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        if (par1 > 0 && this.experienceLevel % 5 == 0 && (float)this.field_82249_h < (float)this.ticksExisted - 100.0f) {
            float var2 = this.experienceLevel > 30 ? 1.0f : (float)this.experienceLevel / 30.0f;
            this.worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75f, 1.0f);
            this.field_82249_h = this.ticksExisted;
        }
    }

    public int xpBarCap() {
        return this.experienceLevel >= 30 ? 62 + (this.experienceLevel - 30) * 7 : (this.experienceLevel >= 15 ? 17 + (this.experienceLevel - 15) * 3 : 17);
    }

    public void addExhaustion(float par1) {
        if (!this.capabilities.disableDamage && !this.worldObj.isClient) {
            this.foodStats.addExhaustion(par1);
        }
    }

    public FoodStats getFoodStats() {
        return this.foodStats;
    }

    public boolean canEat(boolean par1) {
        if ((par1 || this.foodStats.needFood()) && !this.capabilities.disableDamage) {
            return true;
        }
        return false;
    }

    public boolean shouldHeal() {
        if (this.getHealth() > 0.0f && this.getHealth() < this.getMaxHealth()) {
            return true;
        }
        return false;
    }

    public void setItemInUse(ItemStack par1ItemStack, int par2) {
        if (par1ItemStack != this.itemInUse) {
            this.itemInUse = par1ItemStack;
            this.itemInUseCount = par2;
            if (!this.worldObj.isClient) {
                this.setEating(true);
            }
        }
    }

    public boolean isCurrentToolAdventureModeExempt(int par1, int par2, int par3) {
        if (this.capabilities.allowEdit) {
            return true;
        }
        Block var4 = this.worldObj.getBlock(par1, par2, par3);
        if (var4.getMaterial() != Material.air) {
            ItemStack var5;
            if (var4.getMaterial().isAdventureModeExempt()) {
                return true;
            }
            if (this.getCurrentEquippedItem() != null && ((var5 = this.getCurrentEquippedItem()).func_150998_b(var4) || var5.func_150997_a(var4) > 1.0f)) {
                return true;
            }
        }
        return false;
    }

    public boolean canPlayerEdit(int par1, int par2, int par3, int par4, ItemStack par5ItemStack) {
        return this.capabilities.allowEdit ? true : (par5ItemStack != null ? par5ItemStack.canEditBlocks() : false);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer) {
        if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            return 0;
        }
        int var2 = this.experienceLevel * 7;
        return var2 > 100 ? 100 : var2;
    }

    @Override
    protected boolean isPlayer() {
        return true;
    }

    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return true;
    }

    public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2) {
        if (par2) {
            this.inventory.copyInventory(par1EntityPlayer.inventory);
            this.setHealth(par1EntityPlayer.getHealth());
            this.foodStats = par1EntityPlayer.foodStats;
            this.experienceLevel = par1EntityPlayer.experienceLevel;
            this.experienceTotal = par1EntityPlayer.experienceTotal;
            this.experience = par1EntityPlayer.experience;
            this.setScore(par1EntityPlayer.getScore());
            this.teleportDirection = par1EntityPlayer.teleportDirection;
        } else if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.copyInventory(par1EntityPlayer.inventory);
            this.experienceLevel = par1EntityPlayer.experienceLevel;
            this.experienceTotal = par1EntityPlayer.experienceTotal;
            this.experience = par1EntityPlayer.experience;
            this.setScore(par1EntityPlayer.getScore());
        }
        this.theInventoryEnderChest = par1EntityPlayer.theInventoryEnderChest;
    }

    @Override
    protected boolean canTriggerWalking() {
        return !this.capabilities.isFlying;
    }

    public void sendPlayerAbilities() {
    }

    public void setGameType(WorldSettings.GameType par1EnumGameType) {
    }

    @Override
    public String getCommandSenderName() {
        return this.field_146106_i.getName();
    }

    @Override
    public World getEntityWorld() {
        return this.worldObj;
    }

    public InventoryEnderChest getInventoryEnderChest() {
        return this.theInventoryEnderChest;
    }

    @Override
    public ItemStack getEquipmentInSlot(int par1) {
        return par1 == 0 ? this.inventory.getCurrentItem() : this.inventory.armorInventory[par1 - 1];
    }

    @Override
    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }

    @Override
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
        this.inventory.armorInventory[par1] = par2ItemStack;
    }

    @Override
    public boolean isInvisibleToPlayer(EntityPlayer par1EntityPlayer) {
        if (!this.isInvisible()) {
            return false;
        }
        Team var2 = this.getTeam();
        if (var2 != null && par1EntityPlayer != null && par1EntityPlayer.getTeam() == var2 && var2.func_98297_h()) {
            return false;
        }
        return true;
    }

    @Override
    public ItemStack[] getLastActiveItems() {
        return this.inventory.armorInventory;
    }

    public boolean getHideCape() {
        return this.getHideCape(1);
    }

    @Override
    public boolean isPushedByWater() {
        return !this.capabilities.isFlying;
    }

    public Scoreboard getWorldScoreboard() {
        return this.worldObj.getScoreboard();
    }

    @Override
    public Team getTeam() {
        return this.getWorldScoreboard().getPlayersTeam(this.getCommandSenderName());
    }

    @Override
    public IChatComponent func_145748_c_() {
        ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getCommandSenderName()));
        var1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getCommandSenderName() + " "));
        return var1;
    }

    @Override
    public void setAbsorptionAmount(float par1) {
        if (par1 < 0.0f) {
            par1 = 0.0f;
        }
        this.getDataWatcher().updateObject(17, Float.valueOf(par1));
    }

    @Override
    public float getAbsorptionAmount() {
        return this.getDataWatcher().getWatchableObjectFloat(17);
    }

    public static UUID func_146094_a(GameProfile p_146094_0_) {
        UUID var1 = Util.tryGetUUIDFromString(p_146094_0_.getId());
        if (var1 == null) {
            var1 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_146094_0_.getName()).getBytes(Charsets.UTF_8));
        }
        return var1;
    }

    public static enum EnumChatVisibility {
        FULL("FULL", 0, 0, "options.chat.visibility.full"),
        SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"),
        HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
        
        private static final EnumChatVisibility[] field_151432_d;
        private final int chatVisibility;
        private final String resourceKey;
        private static final EnumChatVisibility[] $VALUES;
        private static final String __OBFID = "CL_00001714";

        static {
            field_151432_d = new EnumChatVisibility[EnumChatVisibility.values().length];
            $VALUES = new EnumChatVisibility[]{FULL, SYSTEM, HIDDEN};
            EnumChatVisibility[] var0 = EnumChatVisibility.values();
            int var1 = var0.length;
            int var2 = 0;
            while (var2 < var1) {
                EnumChatVisibility var3;
                EnumChatVisibility.field_151432_d[var3.chatVisibility] = var3 = var0[var2];
                ++var2;
            }
        }

        private EnumChatVisibility(String p_i45323_1_, int p_i45323_2_, String p_i45323_3_, int p_i45323_4_, int n2, String string2) {
            this.chatVisibility = p_i45323_3_;
            this.resourceKey = (String)p_i45323_4_;
        }

        public int getChatVisibility() {
            return this.chatVisibility;
        }

        public static EnumChatVisibility getEnumChatVisibility(int p_151426_0_) {
            return field_151432_d[p_151426_0_ % field_151432_d.length];
        }

        public String getResourceKey() {
            return this.resourceKey;
        }
    }

    public static enum EnumStatus {
        OK("OK", 0),
        NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1),
        NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2),
        TOO_FAR_AWAY("TOO_FAR_AWAY", 3),
        OTHER_PROBLEM("OTHER_PROBLEM", 4),
        NOT_SAFE("NOT_SAFE", 5);
        
        private static final EnumStatus[] $VALUES;
        private static final String __OBFID = "CL_00001712";

        static {
            $VALUES = new EnumStatus[]{OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE};
        }

        private EnumStatus(String par1Str, int par2, String string2, int n2) {
        }
    }

}

