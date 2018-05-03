/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IAnimals;

public interface IMob
extends IAnimals {
    public static final IEntitySelector mobSelector = new IEntitySelector(){
        private static final String __OBFID = "CL_00001688";

        @Override
        public boolean isEntityApplicable(Entity par1Entity) {
            return par1Entity instanceof IMob;
        }
    };

}

