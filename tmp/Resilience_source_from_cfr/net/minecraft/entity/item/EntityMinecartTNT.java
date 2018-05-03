/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT
extends EntityMinecart {
    private int minecartTNTFuse = -1;
    private static final String __OBFID = "CL_00001680";

    public EntityMinecartTNT(World par1World) {
        super(par1World);
    }

    public EntityMinecartTNT(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    public int getMinecartType() {
        return 3;
    }

    @Override
    public Block func_145817_o() {
        return Blocks.tnt;
    }

    @Override
    public void onUpdate() {
        double var1;
        super.onUpdate();
        if (this.minecartTNTFuse > 0) {
            --this.minecartTNTFuse;
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        } else if (this.minecartTNTFuse == 0) {
            this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }
        if (this.isCollidedHorizontally && (var1 = this.motionX * this.motionX + this.motionZ * this.motionZ) >= 0.009999999776482582) {
            this.explodeCart(var1);
        }
    }

    @Override
    public void killMinecart(DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        double var2 = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (!par1DamageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0f);
        }
        if (par1DamageSource.isFireDamage() || par1DamageSource.isExplosion() || var2 >= 0.009999999776482582) {
            this.explodeCart(var2);
        }
    }

    protected void explodeCart(double par1) {
        if (!this.worldObj.isClient) {
            double var3 = Math.sqrt(par1);
            if (var3 > 5.0) {
                var3 = 5.0;
            }
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0 + this.rand.nextDouble() * 1.5 * var3), true);
            this.setDead();
        }
    }

    @Override
    protected void fall(float par1) {
        if (par1 >= 3.0f) {
            float var2 = par1 / 10.0f;
            this.explodeCart(var2 * var2);
        }
        super.fall(par1);
    }

    @Override
    public void onActivatorRailPass(int par1, int par2, int par3, boolean par4) {
        if (par4 && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        if (par1 == 10) {
            this.ignite();
        } else {
            super.handleHealthUpdate(par1);
        }
    }

    public void ignite() {
        this.minecartTNTFuse = 80;
        if (!this.worldObj.isClient) {
            this.worldObj.setEntityState(this, 10);
            this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0f, 1.0f);
        }
    }

    public int func_94104_d() {
        return this.minecartTNTFuse;
    }

    public boolean isIgnited() {
        if (this.minecartTNTFuse > -1) {
            return true;
        }
        return false;
    }

    @Override
    public float func_145772_a(Explosion p_145772_1_, World p_145772_2_, int p_145772_3_, int p_145772_4_, int p_145772_5_, Block p_145772_6_) {
        return this.isIgnited() && (BlockRailBase.func_150051_a(p_145772_6_) || BlockRailBase.func_150049_b_(p_145772_2_, p_145772_3_, p_145772_4_ + 1, p_145772_5_)) ? 0.0f : super.func_145772_a(p_145772_1_, p_145772_2_, p_145772_3_, p_145772_4_, p_145772_5_, p_145772_6_);
    }

    @Override
    public boolean func_145774_a(Explosion p_145774_1_, World p_145774_2_, int p_145774_3_, int p_145774_4_, int p_145774_5_, Block p_145774_6_, float p_145774_7_) {
        return this.isIgnited() && (BlockRailBase.func_150051_a(p_145774_6_) || BlockRailBase.func_150049_b_(p_145774_2_, p_145774_3_, p_145774_4_ + 1, p_145774_5_)) ? false : super.func_145774_a(p_145774_1_, p_145774_2_, p_145774_3_, p_145774_4_, p_145774_5_, p_145774_6_, p_145774_7_);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.func_150297_b("TNTFuse", 99)) {
            this.minecartTNTFuse = par1NBTTagCompound.getInteger("TNTFuse");
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
    }
}

