/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;

public class EntityVillager
extends EntityAgeable
implements IMerchant,
INpc {
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;
    private EntityPlayer buyingPlayer;
    private MerchantRecipeList buyingList;
    private int timeUntilReset;
    private boolean needsInitilization;
    private int wealth;
    private String lastBuyingPlayer;
    private boolean isLookingForHome;
    private float field_82191_bN;
    private static final Map villagersSellingList = new HashMap();
    private static final Map blacksmithSellingList = new HashMap();
    private static final String __OBFID = "CL_00001707";

    static {
        villagersSellingList.put(Items.coal, new Tuple(16, 24));
        villagersSellingList.put(Items.iron_ingot, new Tuple(8, 10));
        villagersSellingList.put(Items.gold_ingot, new Tuple(8, 10));
        villagersSellingList.put(Items.diamond, new Tuple(4, 6));
        villagersSellingList.put(Items.paper, new Tuple(24, 36));
        villagersSellingList.put(Items.book, new Tuple(11, 13));
        villagersSellingList.put(Items.written_book, new Tuple(1, 1));
        villagersSellingList.put(Items.ender_pearl, new Tuple(3, 4));
        villagersSellingList.put(Items.ender_eye, new Tuple(2, 3));
        villagersSellingList.put(Items.porkchop, new Tuple(14, 18));
        villagersSellingList.put(Items.beef, new Tuple(14, 18));
        villagersSellingList.put(Items.chicken, new Tuple(14, 18));
        villagersSellingList.put(Items.cooked_fished, new Tuple(9, 13));
        villagersSellingList.put(Items.wheat_seeds, new Tuple(34, 48));
        villagersSellingList.put(Items.melon_seeds, new Tuple(30, 38));
        villagersSellingList.put(Items.pumpkin_seeds, new Tuple(30, 38));
        villagersSellingList.put(Items.wheat, new Tuple(18, 22));
        villagersSellingList.put(Item.getItemFromBlock(Blocks.wool), new Tuple(14, 22));
        villagersSellingList.put(Items.rotten_flesh, new Tuple(36, 64));
        blacksmithSellingList.put(Items.flint_and_steel, new Tuple(3, 4));
        blacksmithSellingList.put(Items.shears, new Tuple(3, 4));
        blacksmithSellingList.put(Items.iron_sword, new Tuple(7, 11));
        blacksmithSellingList.put(Items.diamond_sword, new Tuple(12, 14));
        blacksmithSellingList.put(Items.iron_axe, new Tuple(6, 8));
        blacksmithSellingList.put(Items.diamond_axe, new Tuple(9, 12));
        blacksmithSellingList.put(Items.iron_pickaxe, new Tuple(7, 9));
        blacksmithSellingList.put(Items.diamond_pickaxe, new Tuple(10, 12));
        blacksmithSellingList.put(Items.iron_shovel, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_shovel, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_hoe, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_hoe, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_boots, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_boots, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_helmet, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_helmet, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_chestplate, new Tuple(10, 14));
        blacksmithSellingList.put(Items.diamond_chestplate, new Tuple(16, 19));
        blacksmithSellingList.put(Items.iron_leggings, new Tuple(8, 10));
        blacksmithSellingList.put(Items.diamond_leggings, new Tuple(11, 14));
        blacksmithSellingList.put(Items.chainmail_boots, new Tuple(5, 7));
        blacksmithSellingList.put(Items.chainmail_helmet, new Tuple(5, 7));
        blacksmithSellingList.put(Items.chainmail_chestplate, new Tuple(11, 15));
        blacksmithSellingList.put(Items.chainmail_leggings, new Tuple(9, 11));
        blacksmithSellingList.put(Items.bread, new Tuple(-4, -2));
        blacksmithSellingList.put(Items.melon, new Tuple(-8, -4));
        blacksmithSellingList.put(Items.apple, new Tuple(-8, -4));
        blacksmithSellingList.put(Items.cookie, new Tuple(-10, -7));
        blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glass), new Tuple(-5, -3));
        blacksmithSellingList.put(Item.getItemFromBlock(Blocks.bookshelf), new Tuple(3, 4));
        blacksmithSellingList.put(Items.leather_chestplate, new Tuple(4, 5));
        blacksmithSellingList.put(Items.leather_boots, new Tuple(2, 4));
        blacksmithSellingList.put(Items.leather_helmet, new Tuple(2, 4));
        blacksmithSellingList.put(Items.leather_leggings, new Tuple(2, 4));
        blacksmithSellingList.put(Items.saddle, new Tuple(6, 8));
        blacksmithSellingList.put(Items.experience_bottle, new Tuple(-4, -1));
        blacksmithSellingList.put(Items.redstone, new Tuple(-4, -1));
        blacksmithSellingList.put(Items.compass, new Tuple(10, 12));
        blacksmithSellingList.put(Items.clock, new Tuple(10, 12));
        blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glowstone), new Tuple(-3, -1));
        blacksmithSellingList.put(Items.cooked_porkchop, new Tuple(-7, -5));
        blacksmithSellingList.put(Items.cooked_beef, new Tuple(-7, -5));
        blacksmithSellingList.put(Items.cooked_chicken, new Tuple(-8, -6));
        blacksmithSellingList.put(Items.ender_eye, new Tuple(7, 11));
        blacksmithSellingList.put(Items.arrow, new Tuple(-12, -8));
    }

    public EntityVillager(World par1World) {
        this(par1World, 0);
    }

    public EntityVillager(World par1World, int par2) {
        super(par1World);
        this.setProfession(par2);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0f, 0.6, 0.6));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(8, new EntityAIPlay(this, 0.32));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0f, 1.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0f, 0.02f));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void updateAITick() {
        if (--this.randomTickDivider <= 0) {
            this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
            if (this.villageObj == null) {
                this.detachHome();
            } else {
                ChunkCoordinates var1 = this.villageObj.getCenter();
                this.setHomeArea(var1.posX, var1.posY, var1.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6f));
                if (this.isLookingForHome) {
                    this.isLookingForHome = false;
                    this.villageObj.setDefaultPlayerReputation(5);
                }
            }
        }
        if (!this.isTrading() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.needsInitilization) {
                    if (this.buyingList.size() > 1) {
                        for (MerchantRecipe var2 : this.buyingList) {
                            if (!var2.isRecipeDisabled()) continue;
                            var2.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                        }
                    }
                    this.addDefaultEquipmentAndRecipies(1);
                    this.needsInitilization = false;
                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, 14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }
                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }
        super.updateAITick();
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        boolean var3;
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        boolean bl = var3 = var2 != null && var2.getItem() == Items.spawn_egg;
        if (!var3 && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.worldObj.isClient) {
                this.setCustomer(par1EntityPlayer);
                par1EntityPlayer.displayGUIMerchant(this, this.getCustomNameTag());
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Profession", this.getProfession());
        par1NBTTagCompound.setInteger("Riches", this.wealth);
        if (this.buyingList != null) {
            par1NBTTagCompound.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setProfession(par1NBTTagCompound.getInteger("Profession"));
        this.wealth = par1NBTTagCompound.getInteger("Riches");
        if (par1NBTTagCompound.func_150297_b("Offers", 10)) {
            NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(var2);
        }
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected String getLivingSound() {
        return this.isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    @Override
    protected String getHurtSound() {
        return "mob.villager.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.villager.death";
    }

    public void setProfession(int par1) {
        this.dataWatcher.updateObject(16, par1);
    }

    public int getProfession() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    public boolean isMating() {
        return this.isMating;
    }

    public void setMating(boolean par1) {
        this.isMating = par1;
    }

    public void setPlaying(boolean par1) {
        this.isPlaying = par1;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    @Override
    public void setRevengeTarget(EntityLivingBase par1EntityLivingBase) {
        super.setRevengeTarget(par1EntityLivingBase);
        if (this.villageObj != null && par1EntityLivingBase != null) {
            this.villageObj.addOrRenewAgressor(par1EntityLivingBase);
            if (par1EntityLivingBase instanceof EntityPlayer) {
                int var2 = -1;
                if (this.isChild()) {
                    var2 = -3;
                }
                this.villageObj.setReputationForPlayer(par1EntityLivingBase.getCommandSenderName(), var2);
                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, 13);
                }
            }
        }
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
        if (this.villageObj != null) {
            EntityPlayer var3;
            Entity var2 = par1DamageSource.getEntity();
            if (var2 != null) {
                if (var2 instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(var2.getCommandSenderName(), -2);
                } else if (var2 instanceof IMob) {
                    this.villageObj.endMatingSeason();
                }
            } else if (var2 == null && (var3 = this.worldObj.getClosestPlayerToEntity(this, 16.0)) != null) {
                this.villageObj.endMatingSeason();
            }
        }
        super.onDeath(par1DamageSource);
    }

    @Override
    public void setCustomer(EntityPlayer par1EntityPlayer) {
        this.buyingPlayer = par1EntityPlayer;
    }

    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }

    public boolean isTrading() {
        if (this.buyingPlayer != null) {
            return true;
        }
        return false;
    }

    @Override
    public void useRecipe(MerchantRecipe par1MerchantRecipe) {
        par1MerchantRecipe.incrementToolUses();
        this.livingSoundTime = - this.getTalkInterval();
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
        if (par1MerchantRecipe.hasSameIDsAs((MerchantRecipe)this.buyingList.get(this.buyingList.size() - 1))) {
            this.timeUntilReset = 40;
            this.needsInitilization = true;
            this.lastBuyingPlayer = this.buyingPlayer != null ? this.buyingPlayer.getCommandSenderName() : null;
        }
        if (par1MerchantRecipe.getItemToBuy().getItem() == Items.emerald) {
            this.wealth += par1MerchantRecipe.getItemToBuy().stackSize;
        }
    }

    @Override
    public void func_110297_a_(ItemStack par1ItemStack) {
        if (!this.worldObj.isClient && this.livingSoundTime > - this.getTalkInterval() + 20) {
            this.livingSoundTime = - this.getTalkInterval();
            if (par1ItemStack != null) {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            } else {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer) {
        if (this.buyingList == null) {
            this.addDefaultEquipmentAndRecipies(1);
        }
        return this.buyingList;
    }

    private float adjustProbability(float par1) {
        float var2 = par1 + this.field_82191_bN;
        return var2 > 0.9f ? 0.9f - (var2 - 0.9f) : var2;
    }

    private void addDefaultEquipmentAndRecipies(int par1) {
        this.field_82191_bN = this.buyingList != null ? MathHelper.sqrt_float(this.buyingList.size()) * 0.2f : 0.0f;
        MerchantRecipeList var2 = new MerchantRecipeList();
        switch (this.getProfession()) {
            int var6;
            case 0: {
                EntityVillager.func_146091_a(var2, Items.wheat, this.rand, this.adjustProbability(0.9f));
                EntityVillager.func_146091_a(var2, Item.getItemFromBlock(Blocks.wool), this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146091_a(var2, Items.chicken, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146091_a(var2, Items.cooked_fished, this.rand, this.adjustProbability(0.4f));
                EntityVillager.func_146089_b(var2, Items.bread, this.rand, this.adjustProbability(0.9f));
                EntityVillager.func_146089_b(var2, Items.melon, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.apple, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.cookie, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.shears, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.flint_and_steel, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.cooked_chicken, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.arrow, this.rand, this.adjustProbability(0.5f));
                if (this.rand.nextFloat() >= this.adjustProbability(0.5f)) break;
                var2.add(new MerchantRecipe(new ItemStack(Blocks.gravel, 10), new ItemStack(Items.emerald), new ItemStack(Items.flint, 4 + this.rand.nextInt(2), 0)));
                break;
            }
            case 1: {
                EntityVillager.func_146091_a(var2, Items.paper, this.rand, this.adjustProbability(0.8f));
                EntityVillager.func_146091_a(var2, Items.book, this.rand, this.adjustProbability(0.8f));
                EntityVillager.func_146091_a(var2, Items.written_book, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Item.getItemFromBlock(Blocks.bookshelf), this.rand, this.adjustProbability(0.8f));
                EntityVillager.func_146089_b(var2, Item.getItemFromBlock(Blocks.glass), this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.compass, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.clock, this.rand, this.adjustProbability(0.2f));
                if (this.rand.nextFloat() >= this.adjustProbability(0.07f)) break;
                Enchantment var8 = Enchantment.enchantmentsBookList[this.rand.nextInt(Enchantment.enchantmentsBookList.length)];
                int var10 = MathHelper.getRandomIntegerInRange(this.rand, var8.getMinLevel(), var8.getMaxLevel());
                ItemStack var11 = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var8, var10));
                var6 = 2 + this.rand.nextInt(5 + var10 * 10) + 3 * var10;
                var2.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, var6), var11));
                break;
            }
            case 2: {
                Item[] var3;
                EntityVillager.func_146089_b(var2, Items.ender_eye, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.experience_bottle, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.redstone, this.rand, this.adjustProbability(0.4f));
                EntityVillager.func_146089_b(var2, Item.getItemFromBlock(Blocks.glowstone), this.rand, this.adjustProbability(0.3f));
                Item[] var4 = var3 = new Item[]{Items.iron_sword, Items.diamond_sword, Items.iron_chestplate, Items.diamond_chestplate, Items.iron_axe, Items.diamond_axe, Items.iron_pickaxe, Items.diamond_pickaxe};
                int var5 = var3.length;
                for (var6 = 0; var6 < var5; ++var6) {
                    Item var7 = var4[var6];
                    if (this.rand.nextFloat() >= this.adjustProbability(0.05f)) continue;
                    var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Items.emerald, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(var7, 1, 0), 5 + this.rand.nextInt(15))));
                }
                break;
            }
            case 3: {
                EntityVillager.func_146091_a(var2, Items.coal, this.rand, this.adjustProbability(0.7f));
                EntityVillager.func_146091_a(var2, Items.iron_ingot, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146091_a(var2, Items.gold_ingot, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146091_a(var2, Items.diamond, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146089_b(var2, Items.iron_sword, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146089_b(var2, Items.diamond_sword, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146089_b(var2, Items.iron_axe, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.diamond_axe, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.iron_pickaxe, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146089_b(var2, Items.diamond_pickaxe, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146089_b(var2, Items.iron_shovel, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.diamond_shovel, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.iron_hoe, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.diamond_hoe, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.iron_boots, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.diamond_boots, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.iron_helmet, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.diamond_helmet, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.iron_chestplate, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.diamond_chestplate, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.iron_leggings, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.diamond_leggings, this.rand, this.adjustProbability(0.2f));
                EntityVillager.func_146089_b(var2, Items.chainmail_boots, this.rand, this.adjustProbability(0.1f));
                EntityVillager.func_146089_b(var2, Items.chainmail_helmet, this.rand, this.adjustProbability(0.1f));
                EntityVillager.func_146089_b(var2, Items.chainmail_chestplate, this.rand, this.adjustProbability(0.1f));
                EntityVillager.func_146089_b(var2, Items.chainmail_leggings, this.rand, this.adjustProbability(0.1f));
                break;
            }
            case 4: {
                EntityVillager.func_146091_a(var2, Items.coal, this.rand, this.adjustProbability(0.7f));
                EntityVillager.func_146091_a(var2, Items.porkchop, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146091_a(var2, Items.beef, this.rand, this.adjustProbability(0.5f));
                EntityVillager.func_146089_b(var2, Items.saddle, this.rand, this.adjustProbability(0.1f));
                EntityVillager.func_146089_b(var2, Items.leather_chestplate, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.leather_boots, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.leather_helmet, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.leather_leggings, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.cooked_porkchop, this.rand, this.adjustProbability(0.3f));
                EntityVillager.func_146089_b(var2, Items.cooked_beef, this.rand, this.adjustProbability(0.3f));
            }
        }
        if (var2.isEmpty()) {
            EntityVillager.func_146091_a(var2, Items.gold_ingot, this.rand, 1.0f);
        }
        Collections.shuffle(var2);
        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }
        int var9 = 0;
        while (var9 < par1 && var9 < var2.size()) {
            this.buyingList.addToListWithCheck((MerchantRecipe)var2.get(var9));
            ++var9;
        }
    }

    @Override
    public void setRecipes(MerchantRecipeList par1MerchantRecipeList) {
    }

    private static void func_146091_a(MerchantRecipeList p_146091_0_, Item p_146091_1_, Random p_146091_2_, float p_146091_3_) {
        if (p_146091_2_.nextFloat() < p_146091_3_) {
            p_146091_0_.add(new MerchantRecipe(EntityVillager.func_146088_a(p_146091_1_, p_146091_2_), Items.emerald));
        }
    }

    private static ItemStack func_146088_a(Item p_146088_0_, Random p_146088_1_) {
        return new ItemStack(p_146088_0_, EntityVillager.func_146092_b(p_146088_0_, p_146088_1_), 0);
    }

    private static int func_146092_b(Item p_146092_0_, Random p_146092_1_) {
        Tuple var2 = (Tuple)villagersSellingList.get(p_146092_0_);
        return var2 == null ? 1 : ((Integer)var2.getFirst() >= (Integer)var2.getSecond() ? (Integer)var2.getFirst() : (Integer)var2.getFirst() + p_146092_1_.nextInt((Integer)var2.getSecond() - (Integer)var2.getFirst()));
    }

    private static void func_146089_b(MerchantRecipeList p_146089_0_, Item p_146089_1_, Random p_146089_2_, float p_146089_3_) {
        if (p_146089_2_.nextFloat() < p_146089_3_) {
            ItemStack var5;
            ItemStack var6;
            int var4 = EntityVillager.func_146090_c(p_146089_1_, p_146089_2_);
            if (var4 < 0) {
                var5 = new ItemStack(Items.emerald, 1, 0);
                var6 = new ItemStack(p_146089_1_, - var4, 0);
            } else {
                var5 = new ItemStack(Items.emerald, var4, 0);
                var6 = new ItemStack(p_146089_1_, 1, 0);
            }
            p_146089_0_.add(new MerchantRecipe(var5, var6));
        }
    }

    private static int func_146090_c(Item p_146090_0_, Random p_146090_1_) {
        Tuple var2 = (Tuple)blacksmithSellingList.get(p_146090_0_);
        return var2 == null ? 1 : ((Integer)var2.getFirst() >= (Integer)var2.getSecond() ? (Integer)var2.getFirst() : (Integer)var2.getFirst() + p_146090_1_.nextInt((Integer)var2.getSecond() - (Integer)var2.getFirst()));
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 12) {
            this.generateRandomParticles("heart");
        } else if (par1 == 13) {
            this.generateRandomParticles("angryVillager");
        } else if (par1 == 14) {
            this.generateRandomParticles("happyVillager");
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    private void generateRandomParticles(String par1Str) {
        int var2 = 0;
        while (var2 < 5) {
            double var3 = this.rand.nextGaussian() * 0.02;
            double var5 = this.rand.nextGaussian() * 0.02;
            double var7 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(par1Str, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 1.0 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var3, var5, var7);
            ++var2;
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        this.setProfession(this.worldObj.rand.nextInt(5));
        return par1EntityLivingData;
    }

    public void setLookingForHome() {
        this.isLookingForHome = true;
    }

    @Override
    public EntityVillager createChild(EntityAgeable par1EntityAgeable) {
        EntityVillager var2 = new EntityVillager(this.worldObj);
        var2.onSpawnWithEgg(null);
        return var2;
    }

    @Override
    public boolean allowLeashing() {
        return false;
    }
}

