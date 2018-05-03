/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube
extends EntitySlime {
    private static final String __OBFID = "CL_00001691";

    public EntityMagmaCube(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
            return true;
        }
        return false;
    }

    @Override
    public int getTotalArmorValue() {
        return this.getSlimeSize() * 3;
    }

    @Override
    public int getBrightnessForRender(float par1) {
        return 15728880;
    }

    @Override
    public float getBrightness(float par1) {
        return 1.0f;
    }

    @Override
    protected String getSlimeParticle() {
        return "flame";
    }

    @Override
    protected EntitySlime createInstance() {
        return new EntityMagmaCube(this.worldObj);
    }

    @Override
    protected Item func_146068_u() {
        return Items.magma_cream;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        Item var3 = this.func_146068_u();
        if (var3 != null && this.getSlimeSize() > 1) {
            int var4 = this.rand.nextInt(4) - 2;
            if (par2 > 0) {
                var4 += this.rand.nextInt(par2 + 1);
            }
            int var5 = 0;
            while (var5 < var4) {
                this.func_145779_a(var3, 1);
                ++var5;
            }
        }
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }

    @Override
    protected void alterSquishAmount() {
        this.squishAmount *= 0.9f;
    }

    @Override
    protected void jump() {
        this.motionY = 0.42f + (float)this.getSlimeSize() * 0.1f;
        this.isAirBorne = true;
    }

    @Override
    protected void fall(float par1) {
    }

    @Override
    protected boolean canDamagePlayer() {
        return true;
    }

    @Override
    protected int getAttackStrength() {
        return super.getAttackStrength() + 2;
    }

    @Override
    protected String getJumpSound() {
        return this.getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    @Override
    public boolean handleLavaMovement() {
        return false;
    }

    @Override
    protected boolean makesSoundOnLand() {
        return true;
    }
}

