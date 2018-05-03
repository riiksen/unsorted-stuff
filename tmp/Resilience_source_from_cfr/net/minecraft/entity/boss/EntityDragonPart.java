/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDragonPart
extends Entity {
    public final IEntityMultiPart entityDragonObj;
    public final String field_146032_b;
    private static final String __OBFID = "CL_00001657";

    public EntityDragonPart(IEntityMultiPart par1IEntityMultiPart, String par2Str, float par3, float par4) {
        super(par1IEntityMultiPart.func_82194_d());
        this.setSize(par3, par4);
        this.entityDragonObj = par1IEntityMultiPart;
        this.field_146032_b = par2Str;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        return this.isEntityInvulnerable() ? false : this.entityDragonObj.attackEntityFromPart(this, par1DamageSource, par2);
    }

    @Override
    public boolean isEntityEqual(Entity par1Entity) {
        if (this != par1Entity && this.entityDragonObj != par1Entity) {
            return false;
        }
        return true;
    }
}

