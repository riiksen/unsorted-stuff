/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FoodStats {
    private int foodLevel = 20;
    private float foodSaturationLevel = 5.0f;
    private float foodExhaustionLevel;
    private int foodTimer;
    private int prevFoodLevel = 20;
    private static final String __OBFID = "CL_00001729";

    public void addStats(int par1, float par2) {
        this.foodLevel = Math.min(par1 + this.foodLevel, 20);
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)par1 * par2 * 2.0f, (float)this.foodLevel);
    }

    public void func_151686_a(ItemFood p_151686_1_, ItemStack p_151686_2_) {
        this.addStats(p_151686_1_.func_150905_g(p_151686_2_), p_151686_1_.func_150906_h(p_151686_2_));
    }

    public void onUpdate(EntityPlayer par1EntityPlayer) {
        EnumDifficulty var2 = par1EntityPlayer.worldObj.difficultySetting;
        this.prevFoodLevel = this.foodLevel;
        if (this.foodExhaustionLevel > 4.0f) {
            this.foodExhaustionLevel -= 4.0f;
            if (this.foodSaturationLevel > 0.0f) {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0f, 0.0f);
            } else if (var2 != EnumDifficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }
        if (par1EntityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.foodLevel >= 18 && par1EntityPlayer.shouldHeal()) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                par1EntityPlayer.heal(1.0f);
                this.addExhaustion(3.0f);
                this.foodTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                if (par1EntityPlayer.getHealth() > 10.0f || var2 == EnumDifficulty.HARD || par1EntityPlayer.getHealth() > 1.0f && var2 == EnumDifficulty.NORMAL) {
                    par1EntityPlayer.attackEntityFrom(DamageSource.starve, 1.0f);
                }
                this.foodTimer = 0;
            }
        } else {
            this.foodTimer = 0;
        }
    }

    public void readNBT(NBTTagCompound par1NBTTagCompound) {
        if (par1NBTTagCompound.func_150297_b("foodLevel", 99)) {
            this.foodLevel = par1NBTTagCompound.getInteger("foodLevel");
            this.foodTimer = par1NBTTagCompound.getInteger("foodTickTimer");
            this.foodSaturationLevel = par1NBTTagCompound.getFloat("foodSaturationLevel");
            this.foodExhaustionLevel = par1NBTTagCompound.getFloat("foodExhaustionLevel");
        }
    }

    public void writeNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("foodLevel", this.foodLevel);
        par1NBTTagCompound.setInteger("foodTickTimer", this.foodTimer);
        par1NBTTagCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
        par1NBTTagCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public int getPrevFoodLevel() {
        return this.prevFoodLevel;
    }

    public boolean needFood() {
        if (this.foodLevel < 20) {
            return true;
        }
        return false;
    }

    public void addExhaustion(float par1) {
        this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + par1, 40.0f);
    }

    public float getSaturationLevel() {
        return this.foodSaturationLevel;
    }

    public void setFoodLevel(int par1) {
        this.foodLevel = par1;
    }

    public void setFoodSaturationLevel(float par1) {
        this.foodSaturationLevel = par1;
    }
}

