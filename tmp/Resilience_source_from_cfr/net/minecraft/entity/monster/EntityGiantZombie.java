/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityGiantZombie
extends EntityMob {
    private static final String __OBFID = "CL_00001690";

    public EntityGiantZombie(World par1World) {
        super(par1World);
        this.yOffset *= 6.0f;
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0);
    }

    @Override
    public float getBlockPathWeight(int par1, int par2, int par3) {
        return this.worldObj.getLightBrightness(par1, par2, par3) - 0.5f;
    }
}

