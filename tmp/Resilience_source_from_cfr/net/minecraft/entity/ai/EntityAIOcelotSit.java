/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class EntityAIOcelotSit
extends EntityAIBase {
    private final EntityOcelot field_151493_a;
    private final double field_151491_b;
    private int field_151492_c;
    private int field_151489_d;
    private int field_151490_e;
    private int field_151487_f;
    private int field_151488_g;
    private int field_151494_h;
    private static final String __OBFID = "CL_00001601";

    public EntityAIOcelotSit(EntityOcelot p_i45315_1_, double p_i45315_2_) {
        this.field_151493_a = p_i45315_1_;
        this.field_151491_b = p_i45315_2_;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute() {
        if (this.field_151493_a.isTamed() && !this.field_151493_a.isSitting() && this.field_151493_a.getRNG().nextDouble() <= 0.006500000134110451 && this.func_151485_f()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting() {
        if (this.field_151492_c <= this.field_151490_e && this.field_151489_d <= 60 && this.func_151486_a(this.field_151493_a.worldObj, this.field_151487_f, this.field_151488_g, this.field_151494_h)) {
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.field_151493_a.getNavigator().tryMoveToXYZ((double)this.field_151487_f + 0.5, this.field_151488_g + 1, (double)this.field_151494_h + 0.5, this.field_151491_b);
        this.field_151492_c = 0;
        this.field_151489_d = 0;
        this.field_151490_e = this.field_151493_a.getRNG().nextInt(this.field_151493_a.getRNG().nextInt(1200) + 1200) + 1200;
        this.field_151493_a.func_70907_r().setSitting(false);
    }

    @Override
    public void resetTask() {
        this.field_151493_a.setSitting(false);
    }

    @Override
    public void updateTask() {
        ++this.field_151492_c;
        this.field_151493_a.func_70907_r().setSitting(false);
        if (this.field_151493_a.getDistanceSq(this.field_151487_f, this.field_151488_g + 1, this.field_151494_h) > 1.0) {
            this.field_151493_a.setSitting(false);
            this.field_151493_a.getNavigator().tryMoveToXYZ((double)this.field_151487_f + 0.5, this.field_151488_g + 1, (double)this.field_151494_h + 0.5, this.field_151491_b);
            ++this.field_151489_d;
        } else if (!this.field_151493_a.isSitting()) {
            this.field_151493_a.setSitting(true);
        } else {
            --this.field_151489_d;
        }
    }

    private boolean func_151485_f() {
        int var1 = (int)this.field_151493_a.posY;
        double var2 = 2.147483647E9;
        int var4 = (int)this.field_151493_a.posX - 8;
        while ((double)var4 < this.field_151493_a.posX + 8.0) {
            int var5 = (int)this.field_151493_a.posZ - 8;
            while ((double)var5 < this.field_151493_a.posZ + 8.0) {
                double var6;
                if (this.func_151486_a(this.field_151493_a.worldObj, var4, var1, var5) && this.field_151493_a.worldObj.isAirBlock(var4, var1 + 1, var5) && (var6 = this.field_151493_a.getDistanceSq(var4, var1, var5)) < var2) {
                    this.field_151487_f = var4;
                    this.field_151488_g = var1;
                    this.field_151494_h = var5;
                    var2 = var6;
                }
                ++var5;
            }
            ++var4;
        }
        if (var2 < 2.147483647E9) {
            return true;
        }
        return false;
    }

    private boolean func_151486_a(World p_151486_1_, int p_151486_2_, int p_151486_3_, int p_151486_4_) {
        Block var5 = p_151486_1_.getBlock(p_151486_2_, p_151486_3_, p_151486_4_);
        int var6 = p_151486_1_.getBlockMetadata(p_151486_2_, p_151486_3_, p_151486_4_);
        if (var5 == Blocks.chest) {
            TileEntityChest var7 = (TileEntityChest)p_151486_1_.getTileEntity(p_151486_2_, p_151486_3_, p_151486_4_);
            if (var7.field_145987_o < 1) {
                return true;
            }
        } else {
            if (var5 == Blocks.lit_furnace) {
                return true;
            }
            if (var5 == Blocks.bed && !BlockBed.func_149975_b(var6)) {
                return true;
            }
        }
        return false;
    }
}

