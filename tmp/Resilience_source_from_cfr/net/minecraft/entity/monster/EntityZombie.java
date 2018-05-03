/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombie
extends EntityMob {
    protected static final IAttribute field_110186_bp = new RangedAttribute("zombie.spawnReinforcements", 0.0, 0.0, 1.0).setDescription("Spawn Reinforcements Chance");
    private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5, 1);
    private final EntityAIBreakDoor field_146075_bs;
    private int conversionTime;
    private boolean field_146076_bu;
    private float field_146074_bv;
    private float field_146073_bw;
    private static final String __OBFID = "CL_00001702";

    public EntityZombie(World par1World) {
        super(par1World);
        this.field_146075_bs = new EntityAIBreakDoor(this);
        this.field_146076_bu = false;
        this.field_146074_bv = -1.0f;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.setSize(0.6f, 1.8f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getAttributeMap().registerAttribute(field_110186_bp).setBaseValue(this.rand.nextDouble() * 0.10000000149011612);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, Byte.valueOf(0));
        this.getDataWatcher().addObject(13, Byte.valueOf(0));
        this.getDataWatcher().addObject(14, Byte.valueOf(0));
    }

    @Override
    public int getTotalArmorValue() {
        int var1 = super.getTotalArmorValue() + 2;
        if (var1 > 20) {
            var1 = 20;
        }
        return var1;
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    public boolean func_146072_bX() {
        return this.field_146076_bu;
    }

    public void func_146070_a(boolean p_146070_1_) {
        if (this.field_146076_bu != p_146070_1_) {
            this.field_146076_bu = p_146070_1_;
            if (p_146070_1_) {
                this.tasks.addTask(1, this.field_146075_bs);
            } else {
                this.tasks.removeTask(this.field_146075_bs);
            }
        }
    }

    @Override
    public boolean isChild() {
        if (this.getDataWatcher().getWatchableObjectByte(12) == 1) {
            return true;
        }
        return false;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer) {
        if (this.isChild()) {
            this.experienceValue = (int)((float)this.experienceValue * 2.5f);
        }
        return super.getExperiencePoints(par1EntityPlayer);
    }

    public void setChild(boolean par1) {
        this.getDataWatcher().updateObject(12, Byte.valueOf(par1 ? 1 : 0));
        if (this.worldObj != null && !this.worldObj.isClient) {
            IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var2.removeModifier(babySpeedBoostModifier);
            if (par1) {
                var2.applyModifier(babySpeedBoostModifier);
            }
        }
        this.func_146071_k(par1);
    }

    public boolean isVillager() {
        if (this.getDataWatcher().getWatchableObjectByte(13) == 1) {
            return true;
        }
        return false;
    }

    public void setVillager(boolean par1) {
        this.getDataWatcher().updateObject(13, Byte.valueOf(par1 ? 1 : 0));
    }

    @Override
    public void onLivingUpdate() {
        float var1;
        if (this.worldObj.isDaytime() && !this.worldObj.isClient && !this.isChild() && (var1 = this.getBrightness(1.0f)) > 0.5f && this.rand.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) {
            boolean var2 = true;
            ItemStack var3 = this.getEquipmentInSlot(4);
            if (var3 != null) {
                if (var3.isItemStackDamageable()) {
                    var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));
                    if (var3.getItemDamageForDisplay() >= var3.getMaxDamage()) {
                        this.renderBrokenItemStack(var3);
                        this.setCurrentItemOrArmor(4, null);
                    }
                }
                var2 = false;
            }
            if (var2) {
                this.setFire(8);
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (!super.attackEntityFrom(par1DamageSource, par2)) {
            return false;
        }
        EntityLivingBase var3 = this.getAttackTarget();
        if (var3 == null && this.getEntityToAttack() instanceof EntityLivingBase) {
            var3 = (EntityLivingBase)this.getEntityToAttack();
        }
        if (var3 == null && par1DamageSource.getEntity() instanceof EntityLivingBase) {
            var3 = (EntityLivingBase)par1DamageSource.getEntity();
        }
        if (var3 != null && this.worldObj.difficultySetting == EnumDifficulty.HARD && (double)this.rand.nextFloat() < this.getEntityAttribute(field_110186_bp).getAttributeValue()) {
            int var4 = MathHelper.floor_double(this.posX);
            int var5 = MathHelper.floor_double(this.posY);
            int var6 = MathHelper.floor_double(this.posZ);
            EntityZombie var7 = new EntityZombie(this.worldObj);
            int var8 = 0;
            while (var8 < 50) {
                int var11;
                int var10;
                int var9 = var4 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                if (World.doesBlockHaveSolidTopSurface(this.worldObj, var9, (var10 = var5 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1)) - 1, var11 = var6 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1)) && this.worldObj.getBlockLightValue(var9, var10, var11) < 10) {
                    var7.setPosition(var9, var10, var11);
                    if (this.worldObj.checkNoEntityCollision(var7.boundingBox) && this.worldObj.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(var7.boundingBox)) {
                        this.worldObj.spawnEntityInWorld(var7);
                        var7.setAttackTarget(var3);
                        var7.onSpawnWithEgg(null);
                        this.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806, 0));
                        var7.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806, 0));
                        break;
                    }
                }
                ++var8;
            }
        }
        return true;
    }

    @Override
    public void onUpdate() {
        if (!this.worldObj.isClient && this.isConverting()) {
            int var1 = this.getConversionTimeBoost();
            this.conversionTime -= var1;
            if (this.conversionTime <= 0) {
                this.convertToVillager();
            }
        }
        super.onUpdate();
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var2 = super.attackEntityAsMob(par1Entity);
        if (var2) {
            int var3 = this.worldObj.difficultySetting.getDifficultyId();
            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float)var3 * 0.3f) {
                par1Entity.setFire(2 * var3);
            }
        }
        return var2;
    }

    @Override
    protected String getLivingSound() {
        return "mob.zombie.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.zombie.death";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.zombie.step", 0.15f, 1.0f);
    }

    @Override
    protected Item func_146068_u() {
        return Items.rotten_flesh;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected void dropRareDrop(int par1) {
        switch (this.rand.nextInt(3)) {
            case 0: {
                this.func_145779_a(Items.iron_ingot, 1);
                break;
            }
            case 1: {
                this.func_145779_a(Items.carrot, 1);
                break;
            }
            case 2: {
                this.func_145779_a(Items.potato, 1);
            }
        }
    }

    @Override
    protected void addRandomArmor() {
        super.addRandomArmor();
        if (this.rand.nextFloat() < (this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.05f : 0.01f)) {
            int var1 = this.rand.nextInt(3);
            if (var1 == 0) {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
            } else {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.isChild()) {
            par1NBTTagCompound.setBoolean("IsBaby", true);
        }
        if (this.isVillager()) {
            par1NBTTagCompound.setBoolean("IsVillager", true);
        }
        par1NBTTagCompound.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
        par1NBTTagCompound.setBoolean("CanBreakDoors", this.func_146072_bX());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.getBoolean("IsBaby")) {
            this.setChild(true);
        }
        if (par1NBTTagCompound.getBoolean("IsVillager")) {
            this.setVillager(true);
        }
        if (par1NBTTagCompound.func_150297_b("ConversionTime", 99) && par1NBTTagCompound.getInteger("ConversionTime") > -1) {
            this.startConversion(par1NBTTagCompound.getInteger("ConversionTime"));
        }
        this.func_146070_a(par1NBTTagCompound.getBoolean("CanBreakDoors"));
    }

    @Override
    public void onKillEntity(EntityLivingBase par1EntityLivingBase) {
        super.onKillEntity(par1EntityLivingBase);
        if ((this.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.worldObj.difficultySetting == EnumDifficulty.HARD) && par1EntityLivingBase instanceof EntityVillager) {
            if (this.rand.nextBoolean()) {
                return;
            }
            EntityZombie var2 = new EntityZombie(this.worldObj);
            var2.copyLocationAndAnglesFrom(par1EntityLivingBase);
            this.worldObj.removeEntity(par1EntityLivingBase);
            var2.onSpawnWithEgg(null);
            var2.setVillager(true);
            if (par1EntityLivingBase.isChild()) {
                var2.setChild(true);
            }
            this.worldObj.spawnEntityInWorld(var2);
            this.worldObj.playAuxSFXAtEntity(null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        Calendar var6;
        IEntityLivingData par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
        float var2 = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * var2);
        if (par1EntityLivingData1 == null) {
            par1EntityLivingData1 = new GroupData(this.worldObj.rand.nextFloat() < 0.05f, this.worldObj.rand.nextFloat() < 0.05f, null);
        }
        if (par1EntityLivingData1 instanceof GroupData) {
            GroupData var3 = (GroupData)par1EntityLivingData1;
            if (var3.field_142046_b) {
                this.setVillager(true);
            }
            if (var3.field_142048_a) {
                this.setChild(true);
            }
        }
        this.func_146070_a(this.rand.nextFloat() < var2 * 0.1f);
        this.addRandomArmor();
        this.enchantEquipment();
        if (this.getEquipmentInSlot(4) == null && (var6 = this.worldObj.getCurrentDate()).get(2) + 1 == 10 && var6.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
            this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1f ? Blocks.lit_pumpkin : Blocks.pumpkin));
            this.equipmentDropChances[4] = 0.0f;
        }
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806, 0));
        double var7 = this.rand.nextDouble() * 1.5 * (double)this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        if (var7 > 1.0) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", var7, 2));
        }
        if (this.rand.nextFloat() < var2 * 0.05f) {
            this.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25 + 0.5, 0));
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0 + 1.0, 2));
            this.func_146070_a(true);
        }
        return par1EntityLivingData1;
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.getCurrentEquippedItem();
        if (var2 != null && var2.getItem() == Items.golden_apple && var2.getItemDamage() == 0 && this.isVillager() && this.isPotionActive(Potion.weakness)) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                --var2.stackSize;
            }
            if (var2.stackSize <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isClient) {
                this.startConversion(this.rand.nextInt(2401) + 3600);
            }
            return true;
        }
        return false;
    }

    protected void startConversion(int par1) {
        this.conversionTime = par1;
        this.getDataWatcher().updateObject(14, Byte.valueOf(1));
        this.removePotionEffect(Potion.weakness.id);
        this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, par1, Math.min(this.worldObj.difficultySetting.getDifficultyId() - 1, 0)));
        this.worldObj.setEntityState(this, 16);
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 16) {
            this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.zombie.remedy", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    @Override
    protected boolean canDespawn() {
        return !this.isConverting();
    }

    public boolean isConverting() {
        if (this.getDataWatcher().getWatchableObjectByte(14) == 1) {
            return true;
        }
        return false;
    }

    protected void convertToVillager() {
        EntityVillager var1 = new EntityVillager(this.worldObj);
        var1.copyLocationAndAnglesFrom(this);
        var1.onSpawnWithEgg(null);
        var1.setLookingForHome();
        if (this.isChild()) {
            var1.setGrowingAge(-24000);
        }
        this.worldObj.removeEntity(this);
        this.worldObj.spawnEntityInWorld(var1);
        var1.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
        this.worldObj.playAuxSFXAtEntity(null, 1017, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
    }

    protected int getConversionTimeBoost() {
        int var1 = 1;
        if (this.rand.nextFloat() < 0.01f) {
            int var2 = 0;
            int var3 = (int)this.posX - 4;
            while (var3 < (int)this.posX + 4 && var2 < 14) {
                int var4 = (int)this.posY - 4;
                while (var4 < (int)this.posY + 4 && var2 < 14) {
                    int var5 = (int)this.posZ - 4;
                    while (var5 < (int)this.posZ + 4 && var2 < 14) {
                        Block var6 = this.worldObj.getBlock(var3, var4, var5);
                        if (var6 == Blocks.iron_bars || var6 == Blocks.bed) {
                            if (this.rand.nextFloat() < 0.3f) {
                                ++var1;
                            }
                            ++var2;
                        }
                        ++var5;
                    }
                    ++var4;
                }
                ++var3;
            }
        }
        return var1;
    }

    public void func_146071_k(boolean p_146071_1_) {
        this.func_146069_a(p_146071_1_ ? 0.5f : 1.0f);
    }

    @Override
    protected final void setSize(float par1, float par2) {
        boolean var3 = this.field_146074_bv > 0.0f && this.field_146073_bw > 0.0f;
        this.field_146074_bv = par1;
        this.field_146073_bw = par2;
        if (!var3) {
            this.func_146069_a(1.0f);
        }
    }

    protected final void func_146069_a(float p_146069_1_) {
        super.setSize(this.field_146074_bv * p_146069_1_, this.field_146073_bw * p_146069_1_);
    }

    class GroupData
    implements IEntityLivingData {
        public boolean field_142048_a;
        public boolean field_142046_b;
        private static final String __OBFID = "CL_00001704";

        private GroupData(boolean par2, boolean par3) {
            this.field_142048_a = false;
            this.field_142046_b = false;
            this.field_142048_a = par2;
            this.field_142046_b = par3;
        }

        GroupData(boolean par2, boolean par3, Object par4EntityZombieINNER1) {
            this(par2, par3);
        }
    }

}

