/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityEnderChest
extends TileEntity {
    public float field_145972_a;
    public float field_145975_i;
    public int field_145973_j;
    private int field_145974_k;
    private static final String __OBFID = "CL_00000355";

    @Override
    public void updateEntity() {
        double var4;
        super.updateEntity();
        if (++this.field_145974_k % 20 * 4 == 0) {
            this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, Blocks.ender_chest, 1, this.field_145973_j);
        }
        this.field_145975_i = this.field_145972_a;
        float var1 = 0.1f;
        if (this.field_145973_j > 0 && this.field_145972_a == 0.0f) {
            double var2 = (double)this.field_145851_c + 0.5;
            var4 = (double)this.field_145849_e + 0.5;
            this.worldObj.playSoundEffect(var2, (double)this.field_145848_d + 0.5, var4, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (this.field_145973_j == 0 && this.field_145972_a > 0.0f || this.field_145973_j > 0 && this.field_145972_a < 1.0f) {
            float var3;
            float var8 = this.field_145972_a;
            this.field_145972_a = this.field_145973_j > 0 ? (this.field_145972_a += var1) : (this.field_145972_a -= var1);
            if (this.field_145972_a > 1.0f) {
                this.field_145972_a = 1.0f;
            }
            if (this.field_145972_a < (var3 = 0.5f) && var8 >= var3) {
                var4 = (double)this.field_145851_c + 0.5;
                double var6 = (double)this.field_145849_e + 0.5;
                this.worldObj.playSoundEffect(var4, (double)this.field_145848_d + 0.5, var6, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.field_145972_a < 0.0f) {
                this.field_145972_a = 0.0f;
            }
        }
    }

    @Override
    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
        if (p_145842_1_ == 1) {
            this.field_145973_j = p_145842_2_;
            return true;
        }
        return super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }

    @Override
    public void invalidate() {
        this.updateContainingBlockInfo();
        super.invalidate();
    }

    public void func_145969_a() {
        ++this.field_145973_j;
        this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, Blocks.ender_chest, 1, this.field_145973_j);
    }

    public void func_145970_b() {
        --this.field_145973_j;
        this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, Blocks.ender_chest, 1, this.field_145973_j);
    }

    public boolean func_145971_a(EntityPlayer p_145971_1_) {
        return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this ? false : p_145971_1_.getDistanceSq((double)this.field_145851_c + 0.5, (double)this.field_145848_d + 0.5, (double)this.field_145849_e + 0.5) <= 64.0;
    }
}

