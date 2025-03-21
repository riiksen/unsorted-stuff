/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.tileentity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntity {
    private static final Logger logger = LogManager.getLogger();
    private static Map nameToClassMap = new HashMap();
    private static Map classToNameMap = new HashMap();
    protected World worldObj;
    public int field_145851_c;
    public int field_145848_d;
    public int field_145849_e;
    protected boolean tileEntityInvalid;
    public int blockMetadata = -1;
    public Block blockType;
    private static final String __OBFID = "CL_00000340";

    static {
        TileEntity.func_145826_a(TileEntityFurnace.class, "Furnace");
        TileEntity.func_145826_a(TileEntityChest.class, "Chest");
        TileEntity.func_145826_a(TileEntityEnderChest.class, "EnderChest");
        TileEntity.func_145826_a(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
        TileEntity.func_145826_a(TileEntityDispenser.class, "Trap");
        TileEntity.func_145826_a(TileEntityDropper.class, "Dropper");
        TileEntity.func_145826_a(TileEntitySign.class, "Sign");
        TileEntity.func_145826_a(TileEntityMobSpawner.class, "MobSpawner");
        TileEntity.func_145826_a(TileEntityNote.class, "Music");
        TileEntity.func_145826_a(TileEntityPiston.class, "Piston");
        TileEntity.func_145826_a(TileEntityBrewingStand.class, "Cauldron");
        TileEntity.func_145826_a(TileEntityEnchantmentTable.class, "EnchantTable");
        TileEntity.func_145826_a(TileEntityEndPortal.class, "Airportal");
        TileEntity.func_145826_a(TileEntityCommandBlock.class, "Control");
        TileEntity.func_145826_a(TileEntityBeacon.class, "Beacon");
        TileEntity.func_145826_a(TileEntitySkull.class, "Skull");
        TileEntity.func_145826_a(TileEntityDaylightDetector.class, "DLDetector");
        TileEntity.func_145826_a(TileEntityHopper.class, "Hopper");
        TileEntity.func_145826_a(TileEntityComparator.class, "Comparator");
        TileEntity.func_145826_a(TileEntityFlowerPot.class, "FlowerPot");
    }

    private static void func_145826_a(Class p_145826_0_, String p_145826_1_) {
        if (nameToClassMap.containsKey(p_145826_1_)) {
            throw new IllegalArgumentException("Duplicate id: " + p_145826_1_);
        }
        nameToClassMap.put(p_145826_1_, p_145826_0_);
        classToNameMap.put(p_145826_0_, p_145826_1_);
    }

    public World getWorldObj() {
        return this.worldObj;
    }

    public void setWorldObj(World p_145834_1_) {
        this.worldObj = p_145834_1_;
    }

    public boolean hasWorldObj() {
        if (this.worldObj != null) {
            return true;
        }
        return false;
    }

    public void readFromNBT(NBTTagCompound p_145839_1_) {
        this.field_145851_c = p_145839_1_.getInteger("x");
        this.field_145848_d = p_145839_1_.getInteger("y");
        this.field_145849_e = p_145839_1_.getInteger("z");
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        String var2 = (String)classToNameMap.get(this.getClass());
        if (var2 == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        p_145841_1_.setString("id", var2);
        p_145841_1_.setInteger("x", this.field_145851_c);
        p_145841_1_.setInteger("y", this.field_145848_d);
        p_145841_1_.setInteger("z", this.field_145849_e);
    }

    public void updateEntity() {
    }

    public static TileEntity createAndLoadEntity(NBTTagCompound p_145827_0_) {
        TileEntity var1 = null;
        try {
            Class var2 = (Class)nameToClassMap.get(p_145827_0_.getString("id"));
            if (var2 != null) {
                var1 = (TileEntity)var2.newInstance();
            }
        }
        catch (Exception var3) {
            var3.printStackTrace();
        }
        if (var1 != null) {
            var1.readFromNBT(p_145827_0_);
        } else {
            logger.warn("Skipping BlockEntity with id " + p_145827_0_.getString("id"));
        }
        return var1;
    }

    public int getBlockMetadata() {
        if (this.blockMetadata == -1) {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.field_145851_c, this.field_145848_d, this.field_145849_e);
        }
        return this.blockMetadata;
    }

    public void onInventoryChanged() {
        if (this.worldObj != null) {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.field_145851_c, this.field_145848_d, this.field_145849_e);
            this.worldObj.func_147476_b(this.field_145851_c, this.field_145848_d, this.field_145849_e, this);
            if (this.getBlockType() != Blocks.air) {
                this.worldObj.func_147453_f(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType());
            }
        }
    }

    public double getDistanceFrom(double p_145835_1_, double p_145835_3_, double p_145835_5_) {
        double var7 = (double)this.field_145851_c + 0.5 - p_145835_1_;
        double var9 = (double)this.field_145848_d + 0.5 - p_145835_3_;
        double var11 = (double)this.field_145849_e + 0.5 - p_145835_5_;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public double getMaxRenderDistanceSquared() {
        return 4096.0;
    }

    public Block getBlockType() {
        if (this.blockType == null) {
            this.blockType = this.worldObj.getBlock(this.field_145851_c, this.field_145848_d, this.field_145849_e);
        }
        return this.blockType;
    }

    public Packet getDescriptionPacket() {
        return null;
    }

    public boolean isInvalid() {
        return this.tileEntityInvalid;
    }

    public void invalidate() {
        this.tileEntityInvalid = true;
    }

    public void validate() {
        this.tileEntityInvalid = false;
    }

    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
        return false;
    }

    public void updateContainingBlockInfo() {
        this.blockType = null;
        this.blockMetadata = -1;
    }

    public void func_145828_a(CrashReportCategory p_145828_1_) {
        p_145828_1_.addCrashSectionCallable("Name", new Callable(){
            private static final String __OBFID = "CL_00000341";

            public String call() {
                return String.valueOf((String)classToNameMap.get(TileEntity.this.getClass())) + " // " + TileEntity.this.getClass().getCanonicalName();
            }
        });
        CrashReportCategory.func_147153_a(p_145828_1_, this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType(), this.getBlockMetadata());
        p_145828_1_.addCrashSectionCallable("Actual block type", new Callable(){
            private static final String __OBFID = "CL_00000343";

            public String call() {
                int var1 = Block.getIdFromBlock(TileEntity.this.worldObj.getBlock(TileEntity.this.field_145851_c, TileEntity.this.field_145848_d, TileEntity.this.field_145849_e));
                try {
                    return String.format("ID #%d (%s // %s)", var1, Block.getBlockById(var1).getUnlocalizedName(), Block.getBlockById(var1).getClass().getCanonicalName());
                }
                catch (Throwable var3) {
                    return "ID #" + var1;
                }
            }
        });
        p_145828_1_.addCrashSectionCallable("Actual block data value", new Callable(){
            private static final String __OBFID = "CL_00000344";

            public String call() {
                int var1 = TileEntity.this.worldObj.getBlockMetadata(TileEntity.this.field_145851_c, TileEntity.this.field_145848_d, TileEntity.this.field_145849_e);
                if (var1 < 0) {
                    return "Unknown? (Got " + var1 + ")";
                }
                String var2 = String.format("%4s", Integer.toBinaryString(var1)).replace(" ", "0");
                return String.format("%1$d / 0x%1$X / 0b%2$s", var1, var2);
            }
        });
    }

}

