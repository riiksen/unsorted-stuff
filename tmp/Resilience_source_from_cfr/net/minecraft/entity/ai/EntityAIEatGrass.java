/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityAIEatGrass
extends EntityAIBase {
    private EntityLiving field_151500_b;
    private World field_151501_c;
    int field_151502_a;
    private static final String __OBFID = "CL_00001582";

    public EntityAIEatGrass(EntityLiving p_i45314_1_) {
        this.field_151500_b = p_i45314_1_;
        this.field_151501_c = p_i45314_1_.worldObj;
        this.setMutexBits(7);
    }

    @Override
    public boolean shouldExecute() {
        int var3;
        int var2;
        if (this.field_151500_b.getRNG().nextInt(this.field_151500_b.isChild() ? 50 : 1000) != 0) {
            return false;
        }
        int var1 = MathHelper.floor_double(this.field_151500_b.posX);
        return this.field_151501_c.getBlock(var1, var2 = MathHelper.floor_double(this.field_151500_b.posY), var3 = MathHelper.floor_double(this.field_151500_b.posZ)) == Blocks.tallgrass && this.field_151501_c.getBlockMetadata(var1, var2, var3) == 1 ? true : this.field_151501_c.getBlock(var1, var2 - 1, var3) == Blocks.grass;
    }

    @Override
    public void startExecuting() {
        this.field_151502_a = 40;
        this.field_151501_c.setEntityState(this.field_151500_b, 10);
        this.field_151500_b.getNavigator().clearPathEntity();
    }

    @Override
    public void resetTask() {
        this.field_151502_a = 0;
    }

    @Override
    public boolean continueExecuting() {
        if (this.field_151502_a > 0) {
            return true;
        }
        return false;
    }

    public int func_151499_f() {
        return this.field_151502_a;
    }

    @Override
    public void updateTask() {
        this.field_151502_a = Math.max(0, this.field_151502_a - 1);
        if (this.field_151502_a == 4) {
            int var3;
            int var2;
            int var1 = MathHelper.floor_double(this.field_151500_b.posX);
            if (this.field_151501_c.getBlock(var1, var2 = MathHelper.floor_double(this.field_151500_b.posY), var3 = MathHelper.floor_double(this.field_151500_b.posZ)) == Blocks.tallgrass) {
                if (this.field_151501_c.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.field_151501_c.func_147480_a(var1, var2, var3, false);
                }
                this.field_151500_b.eatGrassBonus();
            } else if (this.field_151501_c.getBlock(var1, var2 - 1, var3) == Blocks.grass) {
                if (this.field_151501_c.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.field_151501_c.playAuxSFX(2001, var1, var2 - 1, var3, Block.getIdFromBlock(Blocks.grass));
                    this.field_151501_c.setBlock(var1, var2 - 1, var3, Blocks.dirt, 0, 2);
                }
                this.field_151500_b.eatGrassBonus();
            }
        }
    }
}

