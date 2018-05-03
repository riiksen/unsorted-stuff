/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAIFollowParent
extends EntityAIBase {
    EntityAnimal childAnimal;
    EntityAnimal parentAnimal;
    double field_75347_c;
    private int field_75345_d;
    private static final String __OBFID = "CL_00001586";

    public EntityAIFollowParent(EntityAnimal par1EntityAnimal, double par2) {
        this.childAnimal = par1EntityAnimal;
        this.field_75347_c = par2;
    }

    @Override
    public boolean shouldExecute() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }
        List var1 = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.boundingBox.expand(8.0, 4.0, 8.0));
        EntityAnimal var2 = null;
        double var3 = Double.MAX_VALUE;
        for (EntityAnimal var6 : var1) {
            double var7;
            if (var6.getGrowingAge() < 0 || (var7 = this.childAnimal.getDistanceSqToEntity(var6)) > var3) continue;
            var3 = var7;
            var2 = var6;
        }
        if (var2 == null) {
            return false;
        }
        if (var3 < 9.0) {
            return false;
        }
        this.parentAnimal = var2;
        return true;
    }

    @Override
    public boolean continueExecuting() {
        if (!this.parentAnimal.isEntityAlive()) {
            return false;
        }
        double var1 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
        if (var1 >= 9.0 && var1 <= 256.0) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.field_75345_d = 0;
    }

    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }

    @Override
    public void updateTask() {
        if (--this.field_75345_d <= 0) {
            this.field_75345_d = 10;
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.field_75347_c);
        }
    }
}

