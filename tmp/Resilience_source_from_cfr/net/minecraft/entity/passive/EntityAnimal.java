/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAnimal
extends EntityAgeable
implements IAnimals {
    private int inLove;
    private int breeding;
    private EntityPlayer field_146084_br;
    private static final String __OBFID = "CL_00001638";

    public EntityAnimal(World par1World) {
        super(par1World);
    }

    @Override
    protected void updateAITick() {
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        super.updateAITick();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        if (this.inLove > 0) {
            --this.inLove;
            String var1 = "heart";
            if (this.inLove % 10 == 0) {
                double var2 = this.rand.nextGaussian() * 0.02;
                double var4 = this.rand.nextGaussian() * 0.02;
                double var6 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle(var1, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var2, var4, var6);
            }
        } else {
            this.breeding = 0;
        }
    }

    @Override
    protected void attackEntity(Entity par1Entity, float par2) {
        if (par1Entity instanceof EntityPlayer) {
            EntityPlayer var7;
            if (par2 < 3.0f) {
                double var3 = par1Entity.posX - this.posX;
                double var5 = par1Entity.posZ - this.posZ;
                this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0 / 3.141592653589793) - 90.0f;
                this.hasAttacked = true;
            }
            if ((var7 = (EntityPlayer)par1Entity).getCurrentEquippedItem() == null || !this.isBreedingItem(var7.getCurrentEquippedItem())) {
                this.entityToAttack = null;
            }
        } else if (par1Entity instanceof EntityAnimal) {
            EntityAnimal var8 = (EntityAnimal)par1Entity;
            if (this.getGrowingAge() > 0 && var8.getGrowingAge() < 0) {
                if ((double)par2 < 2.5) {
                    this.hasAttacked = true;
                }
            } else if (this.inLove > 0 && var8.inLove > 0) {
                if (var8.entityToAttack == null) {
                    var8.entityToAttack = this;
                }
                if (var8.entityToAttack == this && (double)par2 < 3.5) {
                    ++var8.inLove;
                    ++this.inLove;
                    ++this.breeding;
                    if (this.breeding % 4 == 0) {
                        this.worldObj.spawnParticle("heart", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, 0.0, 0.0, 0.0);
                    }
                    if (this.breeding == 60) {
                        this.procreate((EntityAnimal)par1Entity);
                    }
                } else {
                    this.breeding = 0;
                }
            } else {
                this.breeding = 0;
                this.entityToAttack = null;
            }
        }
    }

    private void procreate(EntityAnimal par1EntityAnimal) {
        EntityAgeable var2 = this.createChild(par1EntityAnimal);
        if (var2 != null) {
            if (this.field_146084_br == null && par1EntityAnimal.func_146083_cb() != null) {
                this.field_146084_br = par1EntityAnimal.func_146083_cb();
            }
            if (this.field_146084_br != null) {
                this.field_146084_br.triggerAchievement(StatList.field_151186_x);
                if (this instanceof EntityCow) {
                    this.field_146084_br.triggerAchievement(AchievementList.field_150962_H);
                }
            }
            this.setGrowingAge(6000);
            par1EntityAnimal.setGrowingAge(6000);
            this.inLove = 0;
            this.breeding = 0;
            this.entityToAttack = null;
            par1EntityAnimal.entityToAttack = null;
            par1EntityAnimal.breeding = 0;
            par1EntityAnimal.inLove = 0;
            var2.setGrowingAge(-24000);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            int var3 = 0;
            while (var3 < 7) {
                double var4 = this.rand.nextGaussian() * 0.02;
                double var6 = this.rand.nextGaussian() * 0.02;
                double var8 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("heart", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var4, var6, var8);
                ++var3;
            }
            this.worldObj.spawnEntityInWorld(var2);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        IAttributeInstance var3;
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.fleeingTick = 60;
        if (!this.isAIEnabled() && (var3 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)).getModifier(field_110179_h) == null) {
            var3.applyModifier(field_110181_i);
        }
        this.entityToAttack = null;
        this.inLove = 0;
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    public float getBlockPathWeight(int par1, int par2, int par3) {
        return this.worldObj.getBlock(par1, par2 - 1, par3) == Blocks.grass ? 10.0f : this.worldObj.getLightBrightness(par1, par2, par3) - 0.5f;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("InLove", this.inLove);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.inLove = par1NBTTagCompound.getInteger("InLove");
    }

    @Override
    protected Entity findPlayerToAttack() {
        block8 : {
            float var1;
            block9 : {
                block7 : {
                    if (this.fleeingTick > 0) {
                        return null;
                    }
                    var1 = 8.0f;
                    if (this.inLove <= 0) break block7;
                    List var2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(var1, var1, var1));
                    int var3 = 0;
                    while (var3 < var2.size()) {
                        EntityAnimal var4 = (EntityAnimal)var2.get(var3);
                        if (var4 != this && var4.inLove > 0) {
                            return var4;
                        }
                        ++var3;
                    }
                    break block8;
                }
                if (this.getGrowingAge() != 0) break block9;
                List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(var1, var1, var1));
                int var3 = 0;
                while (var3 < var2.size()) {
                    EntityPlayer var5 = (EntityPlayer)var2.get(var3);
                    if (var5.getCurrentEquippedItem() != null && this.isBreedingItem(var5.getCurrentEquippedItem())) {
                        return var5;
                    }
                    ++var3;
                }
                break block8;
            }
            if (this.getGrowingAge() <= 0) break block8;
            List var2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(var1, var1, var1));
            int var3 = 0;
            while (var3 < var2.size()) {
                EntityAnimal var4 = (EntityAnimal)var2.get(var3);
                if (var4 != this && var4.getGrowingAge() < 0) {
                    return var4;
                }
                ++var3;
            }
        }
        return null;
    }

    @Override
    public boolean getCanSpawnHere() {
        int var3;
        int var2;
        int var1 = MathHelper.floor_double(this.posX);
        if (this.worldObj.getBlock(var1, (var2 = MathHelper.floor_double(this.boundingBox.minY)) - 1, var3 = MathHelper.floor_double(this.posZ)) == Blocks.grass && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8 && super.getCanSpawnHere()) {
            return true;
        }
        return false;
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer) {
        return 1 + this.worldObj.rand.nextInt(3);
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        if (par1ItemStack.getItem() == Items.wheat) {
            return true;
        }
        return false;
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && this.isBreedingItem(var2) && this.getGrowingAge() == 0 && this.inLove <= 0) {
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                --var2.stackSize;
                if (var2.stackSize <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
                }
            }
            this.func_146082_f(par1EntityPlayer);
            return true;
        }
        return super.interact(par1EntityPlayer);
    }

    public void func_146082_f(EntityPlayer p_146082_1_) {
        this.inLove = 600;
        this.field_146084_br = p_146082_1_;
        this.entityToAttack = null;
        this.worldObj.setEntityState(this, 18);
    }

    public EntityPlayer func_146083_cb() {
        return this.field_146084_br;
    }

    public boolean isInLove() {
        if (this.inLove > 0) {
            return true;
        }
        return false;
    }

    public void resetInLove() {
        this.inLove = 0;
    }

    public boolean canMateWith(EntityAnimal par1EntityAnimal) {
        return par1EntityAnimal == this ? false : (par1EntityAnimal.getClass() != this.getClass() ? false : this.isInLove() && par1EntityAnimal.isInLove());
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 18) {
            int var2 = 0;
            while (var2 < 7) {
                double var3 = this.rand.nextGaussian() * 0.02;
                double var5 = this.rand.nextGaussian() * 0.02;
                double var7 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("heart", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var3, var5, var7);
                ++var2;
            }
        } else {
            super.handleHealthUpdate(par1);
        }
    }
}

