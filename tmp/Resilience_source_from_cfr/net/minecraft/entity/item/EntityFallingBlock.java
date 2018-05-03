/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFallingBlock
extends Entity {
    private Block field_145811_e;
    public int field_145814_a;
    public int field_145812_b;
    public boolean field_145813_c = true;
    private boolean field_145808_f;
    private boolean field_145809_g;
    private int field_145815_h = 40;
    private float field_145816_i = 2.0f;
    public NBTTagCompound field_145810_d;
    private static final String __OBFID = "CL_00001668";

    public EntityFallingBlock(World par1World) {
        super(par1World);
    }

    public EntityFallingBlock(World p_i45318_1_, double p_i45318_2_, double p_i45318_4_, double p_i45318_6_, Block p_i45318_8_) {
        this(p_i45318_1_, p_i45318_2_, p_i45318_4_, p_i45318_6_, p_i45318_8_, 0);
    }

    public EntityFallingBlock(World p_i45319_1_, double p_i45319_2_, double p_i45319_4_, double p_i45319_6_, Block p_i45319_8_, int p_i45319_9_) {
        super(p_i45319_1_);
        this.field_145811_e = p_i45319_8_;
        this.field_145814_a = p_i45319_9_;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(p_i45319_2_, p_i45319_4_, p_i45319_6_);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = p_i45319_2_;
        this.prevPosY = p_i45319_4_;
        this.prevPosZ = p_i45319_6_;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        if (this.field_145811_e.getMaterial() == Material.air) {
            this.setDead();
        } else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.field_145812_b;
            this.motionY -= 0.03999999910593033;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (!this.worldObj.isClient) {
                int var1 = MathHelper.floor_double(this.posX);
                int var2 = MathHelper.floor_double(this.posY);
                int var3 = MathHelper.floor_double(this.posZ);
                if (this.field_145812_b == 1) {
                    if (this.worldObj.getBlock(var1, var2, var3) != this.field_145811_e) {
                        this.setDead();
                        return;
                    }
                    this.worldObj.setBlockToAir(var1, var2, var3);
                }
                if (this.onGround) {
                    this.motionX *= 0.699999988079071;
                    this.motionZ *= 0.699999988079071;
                    this.motionY *= -0.5;
                    if (this.worldObj.getBlock(var1, var2, var3) != Blocks.piston_extension) {
                        this.setDead();
                        if (!this.field_145808_f && this.worldObj.canPlaceEntityOnSide(this.field_145811_e, var1, var2, var3, true, 1, null, null) && !BlockFalling.func_149831_e(this.worldObj, var1, var2 - 1, var3) && this.worldObj.setBlock(var1, var2, var3, this.field_145811_e, this.field_145814_a, 3)) {
                            TileEntity var4;
                            if (this.field_145811_e instanceof BlockFalling) {
                                ((BlockFalling)this.field_145811_e).func_149828_a(this.worldObj, var1, var2, var3, this.field_145814_a);
                            }
                            if (this.field_145810_d != null && this.field_145811_e instanceof ITileEntityProvider && (var4 = this.worldObj.getTileEntity(var1, var2, var3)) != null) {
                                NBTTagCompound var5 = new NBTTagCompound();
                                var4.writeToNBT(var5);
                                for (String var7 : this.field_145810_d.func_150296_c()) {
                                    NBTBase var8 = this.field_145810_d.getTag(var7);
                                    if (var7.equals("x") || var7.equals("y") || var7.equals("z")) continue;
                                    var5.setTag(var7, var8.copy());
                                }
                                var4.readFromNBT(var5);
                                var4.onInventoryChanged();
                            }
                        } else if (this.field_145813_c && !this.field_145808_f) {
                            this.entityDropItem(new ItemStack(this.field_145811_e, 1, this.field_145811_e.damageDropped(this.field_145814_a)), 0.0f);
                        }
                    }
                } else if (this.field_145812_b > 100 && !this.worldObj.isClient && (var2 < 1 || var2 > 256) || this.field_145812_b > 600) {
                    if (this.field_145813_c) {
                        this.entityDropItem(new ItemStack(this.field_145811_e, 1, this.field_145811_e.damageDropped(this.field_145814_a)), 0.0f);
                    }
                    this.setDead();
                }
            }
        }
    }

    @Override
    protected void fall(float par1) {
        int var2;
        if (this.field_145809_g && (var2 = MathHelper.ceiling_float_int(par1 - 1.0f)) > 0) {
            ArrayList var3 = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
            boolean var4 = this.field_145811_e == Blocks.anvil;
            DamageSource var5 = var4 ? DamageSource.anvil : DamageSource.fallingBlock;
            for (Entity var7 : var3) {
                var7.attackEntityFrom(var5, Math.min(MathHelper.floor_float((float)var2 * this.field_145816_i), this.field_145815_h));
            }
            if (var4 && (double)this.rand.nextFloat() < 0.05000000074505806 + (double)var2 * 0.05) {
                int var8 = this.field_145814_a >> 2;
                int var9 = this.field_145814_a & 3;
                if (++var8 > 2) {
                    this.field_145808_f = true;
                } else {
                    this.field_145814_a = var9 | var8 << 2;
                }
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setByte("Tile", (byte)Block.getIdFromBlock(this.field_145811_e));
        par1NBTTagCompound.setInteger("TileID", Block.getIdFromBlock(this.field_145811_e));
        par1NBTTagCompound.setByte("Data", (byte)this.field_145814_a);
        par1NBTTagCompound.setByte("Time", (byte)this.field_145812_b);
        par1NBTTagCompound.setBoolean("DropItem", this.field_145813_c);
        par1NBTTagCompound.setBoolean("HurtEntities", this.field_145809_g);
        par1NBTTagCompound.setFloat("FallHurtAmount", this.field_145816_i);
        par1NBTTagCompound.setInteger("FallHurtMax", this.field_145815_h);
        if (this.field_145810_d != null) {
            par1NBTTagCompound.setTag("TileEntityData", this.field_145810_d);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.field_145811_e = par1NBTTagCompound.func_150297_b("TileID", 99) ? Block.getBlockById(par1NBTTagCompound.getInteger("TileID")) : Block.getBlockById(par1NBTTagCompound.getByte("Tile") & 255);
        this.field_145814_a = par1NBTTagCompound.getByte("Data") & 255;
        this.field_145812_b = par1NBTTagCompound.getByte("Time") & 255;
        if (par1NBTTagCompound.func_150297_b("HurtEntities", 99)) {
            this.field_145809_g = par1NBTTagCompound.getBoolean("HurtEntities");
            this.field_145816_i = par1NBTTagCompound.getFloat("FallHurtAmount");
            this.field_145815_h = par1NBTTagCompound.getInteger("FallHurtMax");
        } else if (this.field_145811_e == Blocks.anvil) {
            this.field_145809_g = true;
        }
        if (par1NBTTagCompound.func_150297_b("DropItem", 99)) {
            this.field_145813_c = par1NBTTagCompound.getBoolean("DropItem");
        }
        if (par1NBTTagCompound.func_150297_b("TileEntityData", 10)) {
            this.field_145810_d = par1NBTTagCompound.getCompoundTag("TileEntityData");
        }
        if (this.field_145811_e.getMaterial() == Material.air) {
            this.field_145811_e = Blocks.sand;
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public World func_145807_e() {
        return this.worldObj;
    }

    public void func_145806_a(boolean p_145806_1_) {
        this.field_145809_g = p_145806_1_;
    }

    @Override
    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    public void addEntityCrashInfo(CrashReportCategory par1CrashReportCategory) {
        super.addEntityCrashInfo(par1CrashReportCategory);
        par1CrashReportCategory.addCrashSection("Immitating block ID", Block.getIdFromBlock(this.field_145811_e));
        par1CrashReportCategory.addCrashSection("Immitating block data", this.field_145814_a);
    }

    public Block func_145805_f() {
        return this.field_145811_e;
    }
}

