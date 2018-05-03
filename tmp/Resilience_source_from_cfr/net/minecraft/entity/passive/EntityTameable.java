/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.World;

public abstract class EntityTameable
extends EntityAnimal
implements IEntityOwnable {
    protected EntityAISit aiSit;
    private static final String __OBFID = "CL_00001561";

    public EntityTameable(World par1World) {
        super(par1World);
        this.aiSit = new EntityAISit(this);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf(0));
        this.dataWatcher.addObject(17, "");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.getOwnerName() == null) {
            par1NBTTagCompound.setString("Owner", "");
        } else {
            par1NBTTagCompound.setString("Owner", this.getOwnerName());
        }
        par1NBTTagCompound.setBoolean("Sitting", this.isSitting());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        String var2 = par1NBTTagCompound.getString("Owner");
        if (var2.length() > 0) {
            this.setOwner(var2);
            this.setTamed(true);
        }
        this.aiSit.setSitting(par1NBTTagCompound.getBoolean("Sitting"));
        this.setSitting(par1NBTTagCompound.getBoolean("Sitting"));
    }

    protected void playTameEffect(boolean par1) {
        String var2 = "heart";
        if (!par1) {
            var2 = "smoke";
        }
        int var3 = 0;
        while (var3 < 7) {
            double var4 = this.rand.nextGaussian() * 0.02;
            double var6 = this.rand.nextGaussian() * 0.02;
            double var8 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(var2, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var4, var6, var8);
            ++var3;
        }
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 7) {
            this.playTameEffect(true);
        } else if (par1 == 6) {
            this.playTameEffect(false);
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    public boolean isTamed() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 4) != 0) {
            return true;
        }
        return false;
    }

    public void setTamed(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 4)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -5)));
        }
    }

    public boolean isSitting() {
        if ((this.dataWatcher.getWatchableObjectByte(16) & 1) != 0) {
            return true;
        }
        return false;
    }

    public void setSitting(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (par1) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
        }
    }

    @Override
    public String getOwnerName() {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    public void setOwner(String par1Str) {
        this.dataWatcher.updateObject(17, par1Str);
    }

    @Override
    public EntityLivingBase getOwner() {
        return this.worldObj.getPlayerEntityByName(this.getOwnerName());
    }

    public EntityAISit func_70907_r() {
        return this.aiSit;
    }

    public boolean func_142018_a(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase) {
        return true;
    }

    @Override
    public Team getTeam() {
        EntityLivingBase var1;
        if (this.isTamed() && (var1 = this.getOwner()) != null) {
            return var1.getTeam();
        }
        return super.getTeam();
    }

    @Override
    public boolean isOnSameTeam(EntityLivingBase par1EntityLivingBase) {
        if (this.isTamed()) {
            EntityLivingBase var2 = this.getOwner();
            if (par1EntityLivingBase == var2) {
                return true;
            }
            if (var2 != null) {
                return var2.isOnSameTeam(par1EntityLivingBase);
            }
        }
        return super.isOnSameTeam(par1EntityLivingBase);
    }
}

