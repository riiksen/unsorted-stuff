/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityBeacon
extends TileEntity
implements IInventory {
    public static final Potion[][] field_146009_a = new Potion[][]{{Potion.moveSpeed, Potion.digSpeed}, {Potion.resistance, Potion.jump}, {Potion.damageBoost}, {Potion.regeneration}};
    private long field_146016_i;
    private float field_146014_j;
    private boolean field_146015_k;
    private int field_146012_l = -1;
    private int field_146013_m;
    private int field_146010_n;
    private ItemStack field_146011_o;
    private String field_146008_p;
    private static final String __OBFID = "CL_00000339";

    @Override
    public void updateEntity() {
        if (this.worldObj.getTotalWorldTime() % 80 == 0) {
            this.func_146003_y();
            this.func_146000_x();
        }
    }

    private void func_146000_x() {
        if (this.field_146015_k && this.field_146012_l > 0 && !this.worldObj.isClient && this.field_146013_m > 0) {
            double var1 = this.field_146012_l * 10 + 10;
            int var3 = 0;
            if (this.field_146012_l >= 4 && this.field_146013_m == this.field_146010_n) {
                var3 = 1;
            }
            AxisAlignedBB var4 = AxisAlignedBB.getAABBPool().getAABB(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c + 1, this.field_145848_d + 1, this.field_145849_e + 1).expand(var1, var1, var1);
            var4.maxY = this.worldObj.getHeight();
            List var5 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, var4);
            for (EntityPlayer var7 : var5) {
                var7.addPotionEffect(new PotionEffect(this.field_146013_m, 180, var3, true));
            }
            if (this.field_146012_l >= 4 && this.field_146013_m != this.field_146010_n && this.field_146010_n > 0) {
                for (EntityPlayer var7 : var5) {
                    var7.addPotionEffect(new PotionEffect(this.field_146010_n, 180, 0, true));
                }
            }
        }
    }

    private void func_146003_y() {
        int var1 = this.field_146012_l;
        if (!this.worldObj.canBlockSeeTheSky(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)) {
            this.field_146015_k = false;
            this.field_146012_l = 0;
        } else {
            this.field_146015_k = true;
            this.field_146012_l = 0;
            int var2 = 1;
            while (var2 <= 4) {
                int var3 = this.field_145848_d - var2;
                if (var3 < 0) break;
                boolean var4 = true;
                int var5 = this.field_145851_c - var2;
                while (var5 <= this.field_145851_c + var2 && var4) {
                    int var6 = this.field_145849_e - var2;
                    while (var6 <= this.field_145849_e + var2) {
                        Block var7 = this.worldObj.getBlock(var5, var3, var6);
                        if (var7 != Blocks.emerald_block && var7 != Blocks.gold_block && var7 != Blocks.diamond_block && var7 != Blocks.iron_block) {
                            var4 = false;
                            break;
                        }
                        ++var6;
                    }
                    ++var5;
                }
                if (!var4) break;
                this.field_146012_l = var2++;
            }
            if (this.field_146012_l == 0) {
                this.field_146015_k = false;
            }
        }
        if (!this.worldObj.isClient && this.field_146012_l == 4 && var1 < this.field_146012_l) {
            for (EntityPlayer var9 : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d - 4, this.field_145849_e).expand(10.0, 5.0, 10.0))) {
                var9.triggerAchievement(AchievementList.field_150965_K);
            }
        }
    }

    public float func_146002_i() {
        if (!this.field_146015_k) {
            return 0.0f;
        }
        int var1 = (int)(this.worldObj.getTotalWorldTime() - this.field_146016_i);
        this.field_146016_i = this.worldObj.getTotalWorldTime();
        if (var1 > 1) {
            this.field_146014_j -= (float)var1 / 40.0f;
            if (this.field_146014_j < 0.0f) {
                this.field_146014_j = 0.0f;
            }
        }
        this.field_146014_j += 0.025f;
        if (this.field_146014_j > 1.0f) {
            this.field_146014_j = 1.0f;
        }
        return this.field_146014_j;
    }

    public int func_146007_j() {
        return this.field_146013_m;
    }

    public int func_146006_k() {
        return this.field_146010_n;
    }

    public int func_145998_l() {
        return this.field_146012_l;
    }

    public void func_146005_c(int p_146005_1_) {
        this.field_146012_l = p_146005_1_;
    }

    public void func_146001_d(int p_146001_1_) {
        this.field_146013_m = 0;
        int var2 = 0;
        while (var2 < this.field_146012_l && var2 < 3) {
            Potion[] var3 = field_146009_a[var2];
            int var4 = var3.length;
            int var5 = 0;
            while (var5 < var4) {
                Potion var6 = var3[var5];
                if (var6.id == p_146001_1_) {
                    this.field_146013_m = p_146001_1_;
                    return;
                }
                ++var5;
            }
            ++var2;
        }
    }

    public void func_146004_e(int p_146004_1_) {
        this.field_146010_n = 0;
        if (this.field_146012_l >= 4) {
            int var2 = 0;
            while (var2 < 4) {
                Potion[] var3 = field_146009_a[var2];
                int var4 = var3.length;
                int var5 = 0;
                while (var5 < var4) {
                    Potion var6 = var3[var5];
                    if (var6.id == p_146004_1_) {
                        this.field_146010_n = p_146004_1_;
                        return;
                    }
                    ++var5;
                }
                ++var2;
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 3, var1);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.field_146013_m = p_145839_1_.getInteger("Primary");
        this.field_146010_n = p_145839_1_.getInteger("Secondary");
        this.field_146012_l = p_145839_1_.getInteger("Levels");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("Primary", this.field_146013_m);
        p_145841_1_.setInteger("Secondary", this.field_146010_n);
        p_145841_1_.setInteger("Levels", this.field_146012_l);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return par1 == 0 ? this.field_146011_o : null;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (par1 == 0 && this.field_146011_o != null) {
            if (par2 >= this.field_146011_o.stackSize) {
                ItemStack var3 = this.field_146011_o;
                this.field_146011_o = null;
                return var3;
            }
            this.field_146011_o.stackSize -= par2;
            return new ItemStack(this.field_146011_o.getItem(), par2, this.field_146011_o.getItemDamage());
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (par1 == 0 && this.field_146011_o != null) {
            ItemStack var2 = this.field_146011_o;
            this.field_146011_o = null;
            return var2;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        if (par1 == 0) {
            this.field_146011_o = par2ItemStack;
        }
    }

    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_146008_p : "container.beacon";
    }

    @Override
    public boolean isInventoryNameLocalized() {
        if (this.field_146008_p != null && this.field_146008_p.length() > 0) {
            return true;
        }
        return false;
    }

    public void func_145999_a(String p_145999_1_) {
        this.field_146008_p = p_145999_1_;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this ? false : par1EntityPlayer.getDistanceSq((double)this.field_145851_c + 0.5, (double)this.field_145848_d + 0.5, (double)this.field_145849_e + 0.5) <= 64.0;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        if (par2ItemStack.getItem() != Items.emerald && par2ItemStack.getItem() != Items.diamond && par2ItemStack.getItem() != Items.gold_ingot && par2ItemStack.getItem() != Items.iron_ingot) {
            return false;
        }
        return true;
    }
}

