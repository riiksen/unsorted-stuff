/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.boss;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityWither
extends EntityMob
implements IBossDisplayData,
IRangedAttackMob {
    private float[] field_82220_d = new float[2];
    private float[] field_82221_e = new float[2];
    private float[] field_82217_f = new float[2];
    private float[] field_82218_g = new float[2];
    private int[] field_82223_h = new int[2];
    private int[] field_82224_i = new int[2];
    private int field_82222_j;
    private static final IEntitySelector attackEntitySelector = new IEntitySelector(){
        private static final String __OBFID = "CL_00001662";

        @Override
        public boolean isEntityApplicable(Entity par1Entity) {
            if (par1Entity instanceof EntityLivingBase && ((EntityLivingBase)par1Entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                return true;
            }
            return false;
        }
    };
    private static final String __OBFID = "CL_00001661";

    public EntityWither(World par1World) {
        super(par1World);
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 4.0f);
        this.isImmuneToFire = true;
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 40, 20.0f));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, attackEntitySelector));
        this.experienceValue = 50;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(0));
        this.dataWatcher.addObject(19, new Integer(0));
        this.dataWatcher.addObject(20, new Integer(0));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Invul", this.func_82212_n());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.func_82215_s(par1NBTTagCompound.getInteger("Invul"));
    }

    @Override
    public float getShadowSize() {
        return this.height / 8.0f;
    }

    @Override
    protected String getLivingSound() {
        return "mob.wither.idle";
    }

    @Override
    protected String getHurtSound() {
        return "mob.wither.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.wither.death";
    }

    @Override
    public void onLivingUpdate() {
        double var6;
        double var8;
        double var4;
        Entity var1;
        this.motionY *= 0.6000000238418579;
        if (!this.worldObj.isClient && this.getWatchedTargetId(0) > 0 && (var1 = this.worldObj.getEntityByID(this.getWatchedTargetId(0))) != null) {
            double var2;
            if (this.posY < var1.posY || !this.isArmored() && this.posY < var1.posY + 5.0) {
                if (this.motionY < 0.0) {
                    this.motionY = 0.0;
                }
                this.motionY += (0.5 - this.motionY) * 0.6000000238418579;
            }
            if ((var6 = (var2 = var1.posX - this.posX) * var2 + (var4 = var1.posZ - this.posZ) * var4) > 9.0) {
                var8 = MathHelper.sqrt_double(var6);
                this.motionX += (var2 / var8 * 0.5 - this.motionX) * 0.6000000238418579;
                this.motionZ += (var4 / var8 * 0.5 - this.motionZ) * 0.6000000238418579;
            }
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806) {
            this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776f - 90.0f;
        }
        super.onLivingUpdate();
        int var20 = 0;
        while (var20 < 2) {
            this.field_82218_g[var20] = this.field_82221_e[var20];
            this.field_82217_f[var20] = this.field_82220_d[var20];
            ++var20;
        }
        var20 = 0;
        while (var20 < 2) {
            int var21 = this.getWatchedTargetId(var20 + 1);
            Entity var3 = null;
            if (var21 > 0) {
                var3 = this.worldObj.getEntityByID(var21);
            }
            if (var3 != null) {
                var4 = this.func_82214_u(var20 + 1);
                var6 = this.func_82208_v(var20 + 1);
                var8 = this.func_82213_w(var20 + 1);
                double var10 = var3.posX - var4;
                double var12 = var3.posY + (double)var3.getEyeHeight() - var6;
                double var14 = var3.posZ - var8;
                double var16 = MathHelper.sqrt_double(var10 * var10 + var14 * var14);
                float var18 = (float)(Math.atan2(var14, var10) * 180.0 / 3.141592653589793) - 90.0f;
                float var19 = (float)(- Math.atan2(var12, var16) * 180.0 / 3.141592653589793);
                this.field_82220_d[var20] = this.func_82204_b(this.field_82220_d[var20], var19, 40.0f);
                this.field_82221_e[var20] = this.func_82204_b(this.field_82221_e[var20], var18, 10.0f);
            } else {
                this.field_82221_e[var20] = this.func_82204_b(this.field_82221_e[var20], this.renderYawOffset, 10.0f);
            }
            ++var20;
        }
        boolean var22 = this.isArmored();
        int var21 = 0;
        while (var21 < 3) {
            double var23 = this.func_82214_u(var21);
            double var5 = this.func_82208_v(var21);
            double var7 = this.func_82213_w(var21);
            this.worldObj.spawnParticle("smoke", var23 + this.rand.nextGaussian() * 0.30000001192092896, var5 + this.rand.nextGaussian() * 0.30000001192092896, var7 + this.rand.nextGaussian() * 0.30000001192092896, 0.0, 0.0, 0.0);
            if (var22 && this.worldObj.rand.nextInt(4) == 0) {
                this.worldObj.spawnParticle("mobSpell", var23 + this.rand.nextGaussian() * 0.30000001192092896, var5 + this.rand.nextGaussian() * 0.30000001192092896, var7 + this.rand.nextGaussian() * 0.30000001192092896, 0.699999988079071, 0.699999988079071, 0.5);
            }
            ++var21;
        }
        if (this.func_82212_n() > 0) {
            var21 = 0;
            while (var21 < 3) {
                this.worldObj.spawnParticle("mobSpell", this.posX + this.rand.nextGaussian() * 1.0, this.posY + (double)(this.rand.nextFloat() * 3.3f), this.posZ + this.rand.nextGaussian() * 1.0, 0.699999988079071, 0.699999988079071, 0.8999999761581421);
                ++var21;
            }
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.func_82212_n() > 0) {
            int var1 = this.func_82212_n() - 1;
            if (var1 <= 0) {
                this.worldObj.newExplosion(this, this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, 7.0f, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                this.worldObj.playBroadcastSound(1013, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
            }
            this.func_82215_s(var1);
            if (this.ticksExisted % 10 == 0) {
                this.heal(10.0f);
            }
        } else {
            int var12;
            super.updateAITasks();
            int var1 = 1;
            while (var1 < 3) {
                if (this.ticksExisted >= this.field_82223_h[var1 - 1]) {
                    this.field_82223_h[var1 - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
                    if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                        int var10001 = var1 - 1;
                        int var10003 = this.field_82224_i[var1 - 1];
                        this.field_82224_i[var10001] = this.field_82224_i[var1 - 1] + 1;
                        if (var10003 > 15) {
                            float var2 = 10.0f;
                            float var3 = 5.0f;
                            double var4 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - (double)var2, this.posX + (double)var2);
                            double var6 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - (double)var3, this.posY + (double)var3);
                            double var8 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - (double)var2, this.posZ + (double)var2);
                            this.func_82209_a(var1 + 1, var4, var6, var8, true);
                            this.field_82224_i[var1 - 1] = 0;
                        }
                    }
                    if ((var12 = this.getWatchedTargetId(var1)) > 0) {
                        Entity var14 = this.worldObj.getEntityByID(var12);
                        if (var14 != null && var14.isEntityAlive() && this.getDistanceSqToEntity(var14) <= 900.0 && this.canEntityBeSeen(var14)) {
                            this.func_82216_a(var1 + 1, (EntityLivingBase)var14);
                            this.field_82223_h[var1 - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                            this.field_82224_i[var1 - 1] = 0;
                        } else {
                            this.func_82211_c(var1, 0);
                        }
                    } else {
                        List var13 = this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 8.0, 20.0), attackEntitySelector);
                        int var16 = 0;
                        while (var16 < 10 && !var13.isEmpty()) {
                            EntityLivingBase var5 = (EntityLivingBase)var13.get(this.rand.nextInt(var13.size()));
                            if (var5 != this && var5.isEntityAlive() && this.canEntityBeSeen(var5)) {
                                if (var5 instanceof EntityPlayer) {
                                    if (((EntityPlayer)var5).capabilities.disableDamage) break;
                                    this.func_82211_c(var1, var5.getEntityId());
                                    break;
                                }
                                this.func_82211_c(var1, var5.getEntityId());
                                break;
                            }
                            var13.remove(var5);
                            ++var16;
                        }
                    }
                }
                ++var1;
            }
            if (this.getAttackTarget() != null) {
                this.func_82211_c(0, this.getAttackTarget().getEntityId());
            } else {
                this.func_82211_c(0, 0);
            }
            if (this.field_82222_j > 0) {
                --this.field_82222_j;
                if (this.field_82222_j == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    var1 = MathHelper.floor_double(this.posY);
                    var12 = MathHelper.floor_double(this.posX);
                    int var15 = MathHelper.floor_double(this.posZ);
                    boolean var18 = false;
                    int var17 = -1;
                    while (var17 <= 1) {
                        int var19 = -1;
                        while (var19 <= 1) {
                            int var7 = 0;
                            while (var7 <= 3) {
                                int var20 = var12 + var17;
                                int var9 = var1 + var7;
                                int var10 = var15 + var19;
                                Block var11 = this.worldObj.getBlock(var20, var9, var10);
                                if (var11.getMaterial() != Material.air && var11 != Blocks.bedrock && var11 != Blocks.end_portal && var11 != Blocks.end_portal_frame && var11 != Blocks.command_block) {
                                    var18 = this.worldObj.func_147480_a(var20, var9, var10, true) || var18;
                                }
                                ++var7;
                            }
                            ++var19;
                        }
                        ++var17;
                    }
                    if (var18) {
                        this.worldObj.playAuxSFXAtEntity(null, 1012, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    }
                }
            }
            if (this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
        }
    }

    public void func_82206_m() {
        this.func_82215_s(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }

    @Override
    public void setInWeb() {
    }

    @Override
    public int getTotalArmorValue() {
        return 4;
    }

    private double func_82214_u(int par1) {
        if (par1 <= 0) {
            return this.posX;
        }
        float var2 = (this.renderYawOffset + (float)(180 * (par1 - 1))) / 180.0f * 3.1415927f;
        float var3 = MathHelper.cos(var2);
        return this.posX + (double)var3 * 1.3;
    }

    private double func_82208_v(int par1) {
        return par1 <= 0 ? this.posY + 3.0 : this.posY + 2.2;
    }

    private double func_82213_w(int par1) {
        if (par1 <= 0) {
            return this.posZ;
        }
        float var2 = (this.renderYawOffset + (float)(180 * (par1 - 1))) / 180.0f * 3.1415927f;
        float var3 = MathHelper.sin(var2);
        return this.posZ + (double)var3 * 1.3;
    }

    private float func_82204_b(float par1, float par2, float par3) {
        float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
        if (var4 > par3) {
            var4 = par3;
        }
        if (var4 < - par3) {
            var4 = - par3;
        }
        return par1 + var4;
    }

    private void func_82216_a(int par1, EntityLivingBase par2EntityLivingBase) {
        this.func_82209_a(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() * 0.5, par2EntityLivingBase.posZ, par1 == 0 && this.rand.nextFloat() < 0.001f);
    }

    private void func_82209_a(int par1, double par2, double par4, double par6, boolean par8) {
        this.worldObj.playAuxSFXAtEntity(null, 1014, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        double var9 = this.func_82214_u(par1);
        double var11 = this.func_82208_v(par1);
        double var13 = this.func_82213_w(par1);
        double var15 = par2 - var9;
        double var17 = par4 - var11;
        double var19 = par6 - var13;
        EntityWitherSkull var21 = new EntityWitherSkull(this.worldObj, this, var15, var17, var19);
        if (par8) {
            var21.setInvulnerable(true);
        }
        var21.posY = var11;
        var21.posX = var9;
        var21.posZ = var13;
        this.worldObj.spawnEntityInWorld(var21);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
        this.func_82216_a(0, par1EntityLivingBase);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity var3;
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (par1DamageSource == DamageSource.drown) {
            return false;
        }
        if (this.func_82212_n() > 0) {
            return false;
        }
        if (this.isArmored() && (var3 = par1DamageSource.getSourceOfDamage()) instanceof EntityArrow) {
            return false;
        }
        var3 = par1DamageSource.getEntity();
        if (var3 != null && !(var3 instanceof EntityPlayer) && var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getCreatureAttribute() == this.getCreatureAttribute()) {
            return false;
        }
        if (this.field_82222_j <= 0) {
            this.field_82222_j = 20;
        }
        int var4 = 0;
        while (var4 < this.field_82224_i.length) {
            int[] arrn = this.field_82224_i;
            int n = var4++;
            arrn[n] = arrn[n] + 3;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        this.func_145779_a(Items.nether_star, 1);
        if (!this.worldObj.isClient) {
            for (EntityPlayer var4 : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50.0, 100.0, 50.0))) {
                var4.triggerAchievement(AchievementList.field_150964_J);
            }
        }
    }

    @Override
    public void despawnEntity() {
        this.entityAge = 0;
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return 15728880;
    }

    @Override
    protected void fall(float par1) {
    }

    @Override
    public void addPotionEffect(PotionEffect par1PotionEffect) {
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }

    public float func_82207_a(int par1) {
        return this.field_82221_e[par1];
    }

    public float func_82210_r(int par1) {
        return this.field_82220_d[par1];
    }

    public int func_82212_n() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void func_82215_s(int par1) {
        this.dataWatcher.updateObject(20, par1);
    }

    public int getWatchedTargetId(int par1) {
        return this.dataWatcher.getWatchableObjectInt(17 + par1);
    }

    public void func_82211_c(int par1, int par2) {
        this.dataWatcher.updateObject(17 + par1, par2);
    }

    public boolean isArmored() {
        if (this.getHealth() <= this.getMaxHealth() / 2.0f) {
            return true;
        }
        return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public void mountEntity(Entity par1Entity) {
        this.ridingEntity = null;
    }

}

