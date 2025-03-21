/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;

public class Village {
    private World worldObj;
    private final List villageDoorInfoList = new ArrayList();
    private final ChunkCoordinates centerHelper = new ChunkCoordinates(0, 0, 0);
    private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
    private int villageRadius;
    private int lastAddDoorTimestamp;
    private int tickCounter;
    private int numVillagers;
    private int noBreedTicks;
    private TreeMap playerReputation = new TreeMap();
    private List villageAgressors = new ArrayList();
    private int numIronGolems;
    private static final String __OBFID = "CL_00001631";

    public Village() {
    }

    public Village(World par1World) {
        this.worldObj = par1World;
    }

    public void func_82691_a(World par1World) {
        this.worldObj = par1World;
    }

    public void tick(int par1) {
        Vec3 var3;
        int var2;
        this.tickCounter = par1;
        this.removeDeadAndOutOfRangeDoors();
        this.removeDeadAndOldAgressors();
        if (par1 % 20 == 0) {
            this.updateNumVillagers();
        }
        if (par1 % 30 == 0) {
            this.updateNumIronGolems();
        }
        if (this.numIronGolems < (var2 = this.numVillagers / 10) && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0 && (var3 = this.tryGetIronGolemSpawningLocation(MathHelper.floor_float(this.center.posX), MathHelper.floor_float(this.center.posY), MathHelper.floor_float(this.center.posZ), 2, 4, 2)) != null) {
            EntityIronGolem var4 = new EntityIronGolem(this.worldObj);
            var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
            this.worldObj.spawnEntityInWorld(var4);
            ++this.numIronGolems;
        }
    }

    private Vec3 tryGetIronGolemSpawningLocation(int par1, int par2, int par3, int par4, int par5, int par6) {
        int var7 = 0;
        while (var7 < 10) {
            int var9;
            int var10;
            int var8 = par1 + this.worldObj.rand.nextInt(16) - 8;
            if (this.isInRange(var8, var9 = par2 + this.worldObj.rand.nextInt(6) - 3, var10 = par3 + this.worldObj.rand.nextInt(16) - 8) && this.isValidIronGolemSpawningLocation(var8, var9, var10, par4, par5, par6)) {
                return this.worldObj.getWorldVec3Pool().getVecFromPool(var8, var9, var10);
            }
            ++var7;
        }
        return null;
    }

    private boolean isValidIronGolemSpawningLocation(int par1, int par2, int par3, int par4, int par5, int par6) {
        if (!World.doesBlockHaveSolidTopSurface(this.worldObj, par1, par2 - 1, par3)) {
            return false;
        }
        int var7 = par1 - par4 / 2;
        int var8 = par3 - par6 / 2;
        int var9 = var7;
        while (var9 < var7 + par4) {
            int var10 = par2;
            while (var10 < par2 + par5) {
                int var11 = var8;
                while (var11 < var8 + par6) {
                    if (this.worldObj.getBlock(var9, var10, var11).isNormalCube()) {
                        return false;
                    }
                    ++var11;
                }
                ++var10;
            }
            ++var9;
        }
        return true;
    }

    private void updateNumIronGolems() {
        List var1 = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, AxisAlignedBB.getAABBPool().getAABB(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
        this.numIronGolems = var1.size();
    }

    private void updateNumVillagers() {
        List var1 = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getAABBPool().getAABB(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
        this.numVillagers = var1.size();
        if (this.numVillagers == 0) {
            this.playerReputation.clear();
        }
    }

    public ChunkCoordinates getCenter() {
        return this.center;
    }

    public int getVillageRadius() {
        return this.villageRadius;
    }

    public int getNumVillageDoors() {
        return this.villageDoorInfoList.size();
    }

    public int getTicksSinceLastDoorAdding() {
        return this.tickCounter - this.lastAddDoorTimestamp;
    }

    public int getNumVillagers() {
        return this.numVillagers;
    }

    public boolean isInRange(int par1, int par2, int par3) {
        if (this.center.getDistanceSquared(par1, par2, par3) < (float)(this.villageRadius * this.villageRadius)) {
            return true;
        }
        return false;
    }

    public List getVillageDoorInfoList() {
        return this.villageDoorInfoList;
    }

    public VillageDoorInfo findNearestDoor(int par1, int par2, int par3) {
        VillageDoorInfo var4 = null;
        int var5 = Integer.MAX_VALUE;
        for (VillageDoorInfo var7 : this.villageDoorInfoList) {
            int var8 = var7.getDistanceSquared(par1, par2, par3);
            if (var8 >= var5) continue;
            var4 = var7;
            var5 = var8;
        }
        return var4;
    }

    public VillageDoorInfo findNearestDoorUnrestricted(int par1, int par2, int par3) {
        VillageDoorInfo var4 = null;
        int var5 = Integer.MAX_VALUE;
        for (VillageDoorInfo var7 : this.villageDoorInfoList) {
            int var8 = var7.getDistanceSquared(par1, par2, par3);
            var8 = var8 > 256 ? (var8 *= 1000) : var7.getDoorOpeningRestrictionCounter();
            if (var8 >= var5) continue;
            var4 = var7;
            var5 = var8;
        }
        return var4;
    }

    public VillageDoorInfo getVillageDoorAt(int par1, int par2, int par3) {
        VillageDoorInfo var5;
        if (this.center.getDistanceSquared(par1, par2, par3) > (float)(this.villageRadius * this.villageRadius)) {
            return null;
        }
        Iterator var4 = this.villageDoorInfoList.iterator();
        do {
            if (!var4.hasNext()) {
                return null;
            }
            var5 = (VillageDoorInfo)var4.next();
        } while (var5.posX != par1 || var5.posZ != par3 || Math.abs(var5.posY - par2) > 1);
        return var5;
    }

    public void addVillageDoorInfo(VillageDoorInfo par1VillageDoorInfo) {
        this.villageDoorInfoList.add(par1VillageDoorInfo);
        this.centerHelper.posX += par1VillageDoorInfo.posX;
        this.centerHelper.posY += par1VillageDoorInfo.posY;
        this.centerHelper.posZ += par1VillageDoorInfo.posZ;
        this.updateVillageRadiusAndCenter();
        this.lastAddDoorTimestamp = par1VillageDoorInfo.lastActivityTimestamp;
    }

    public boolean isAnnihilated() {
        return this.villageDoorInfoList.isEmpty();
    }

    public void addOrRenewAgressor(EntityLivingBase par1EntityLivingBase) {
        Iterator var2 = this.villageAgressors.iterator();
        do {
            if (!var2.hasNext()) {
                this.villageAgressors.add(new VillageAgressor(par1EntityLivingBase, this.tickCounter));
                return;
            }
            VillageAgressor var3 = (VillageAgressor)var2.next();
        } while (var3.agressor != par1EntityLivingBase);
        var3.agressionTime = this.tickCounter;
    }

    public EntityLivingBase findNearestVillageAggressor(EntityLivingBase par1EntityLivingBase) {
        double var2 = Double.MAX_VALUE;
        VillageAgressor var4 = null;
        int var5 = 0;
        while (var5 < this.villageAgressors.size()) {
            VillageAgressor var6 = (VillageAgressor)this.villageAgressors.get(var5);
            double var7 = var6.agressor.getDistanceSqToEntity(par1EntityLivingBase);
            if (var7 <= var2) {
                var4 = var6;
                var2 = var7;
            }
            ++var5;
        }
        return var4 != null ? var4.agressor : null;
    }

    public EntityPlayer func_82685_c(EntityLivingBase par1EntityLivingBase) {
        double var2 = Double.MAX_VALUE;
        EntityPlayer var4 = null;
        for (String var6 : this.playerReputation.keySet()) {
            EntityPlayer var7;
            double var8;
            if (!this.isPlayerReputationTooLow(var6) || (var7 = this.worldObj.getPlayerEntityByName(var6)) == null || (var8 = var7.getDistanceSqToEntity(par1EntityLivingBase)) > var2) continue;
            var4 = var7;
            var2 = var8;
        }
        return var4;
    }

    private void removeDeadAndOldAgressors() {
        Iterator var1 = this.villageAgressors.iterator();
        while (var1.hasNext()) {
            VillageAgressor var2 = (VillageAgressor)var1.next();
            if (var2.agressor.isEntityAlive() && Math.abs(this.tickCounter - var2.agressionTime) <= 300) continue;
            var1.remove();
        }
    }

    private void removeDeadAndOutOfRangeDoors() {
        boolean var1 = false;
        boolean var2 = this.worldObj.rand.nextInt(50) == 0;
        Iterator var3 = this.villageDoorInfoList.iterator();
        while (var3.hasNext()) {
            VillageDoorInfo var4 = (VillageDoorInfo)var3.next();
            if (var2) {
                var4.resetDoorOpeningRestrictionCounter();
            }
            if (this.isBlockDoor(var4.posX, var4.posY, var4.posZ) && Math.abs(this.tickCounter - var4.lastActivityTimestamp) <= 1200) continue;
            this.centerHelper.posX -= var4.posX;
            this.centerHelper.posY -= var4.posY;
            this.centerHelper.posZ -= var4.posZ;
            var1 = true;
            var4.isDetachedFromVillageFlag = true;
            var3.remove();
        }
        if (var1) {
            this.updateVillageRadiusAndCenter();
        }
    }

    private boolean isBlockDoor(int par1, int par2, int par3) {
        if (this.worldObj.getBlock(par1, par2, par3) == Blocks.wooden_door) {
            return true;
        }
        return false;
    }

    private void updateVillageRadiusAndCenter() {
        int var1 = this.villageDoorInfoList.size();
        if (var1 == 0) {
            this.center.set(0, 0, 0);
            this.villageRadius = 0;
        } else {
            this.center.set(this.centerHelper.posX / var1, this.centerHelper.posY / var1, this.centerHelper.posZ / var1);
            int var2 = 0;
            for (VillageDoorInfo var4 : this.villageDoorInfoList) {
                var2 = Math.max(var4.getDistanceSquared(this.center.posX, this.center.posY, this.center.posZ), var2);
            }
            this.villageRadius = Math.max(32, (int)Math.sqrt(var2) + 1);
        }
    }

    public int getReputationForPlayer(String par1Str) {
        Integer var2 = (Integer)this.playerReputation.get(par1Str);
        return var2 != null ? var2 : 0;
    }

    public int setReputationForPlayer(String par1Str, int par2) {
        int var3 = this.getReputationForPlayer(par1Str);
        int var4 = MathHelper.clamp_int(var3 + par2, -30, 10);
        this.playerReputation.put(par1Str, var4);
        return var4;
    }

    public boolean isPlayerReputationTooLow(String par1Str) {
        if (this.getReputationForPlayer(par1Str) <= -15) {
            return true;
        }
        return false;
    }

    public void readVillageDataFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.numVillagers = par1NBTTagCompound.getInteger("PopSize");
        this.villageRadius = par1NBTTagCompound.getInteger("Radius");
        this.numIronGolems = par1NBTTagCompound.getInteger("Golems");
        this.lastAddDoorTimestamp = par1NBTTagCompound.getInteger("Stable");
        this.tickCounter = par1NBTTagCompound.getInteger("Tick");
        this.noBreedTicks = par1NBTTagCompound.getInteger("MTick");
        this.center.posX = par1NBTTagCompound.getInteger("CX");
        this.center.posY = par1NBTTagCompound.getInteger("CY");
        this.center.posZ = par1NBTTagCompound.getInteger("CZ");
        this.centerHelper.posX = par1NBTTagCompound.getInteger("ACX");
        this.centerHelper.posY = par1NBTTagCompound.getInteger("ACY");
        this.centerHelper.posZ = par1NBTTagCompound.getInteger("ACZ");
        NBTTagList var2 = par1NBTTagCompound.getTagList("Doors", 10);
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            VillageDoorInfo var5 = new VillageDoorInfo(var4.getInteger("X"), var4.getInteger("Y"), var4.getInteger("Z"), var4.getInteger("IDX"), var4.getInteger("IDZ"), var4.getInteger("TS"));
            this.villageDoorInfoList.add(var5);
            ++var3;
        }
        NBTTagList var6 = par1NBTTagCompound.getTagList("Players", 10);
        int var7 = 0;
        while (var7 < var6.tagCount()) {
            NBTTagCompound var8 = var6.getCompoundTagAt(var7);
            this.playerReputation.put(var8.getString("Name"), var8.getInteger("S"));
            ++var7;
        }
    }

    public void writeVillageDataToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("PopSize", this.numVillagers);
        par1NBTTagCompound.setInteger("Radius", this.villageRadius);
        par1NBTTagCompound.setInteger("Golems", this.numIronGolems);
        par1NBTTagCompound.setInteger("Stable", this.lastAddDoorTimestamp);
        par1NBTTagCompound.setInteger("Tick", this.tickCounter);
        par1NBTTagCompound.setInteger("MTick", this.noBreedTicks);
        par1NBTTagCompound.setInteger("CX", this.center.posX);
        par1NBTTagCompound.setInteger("CY", this.center.posY);
        par1NBTTagCompound.setInteger("CZ", this.center.posZ);
        par1NBTTagCompound.setInteger("ACX", this.centerHelper.posX);
        par1NBTTagCompound.setInteger("ACY", this.centerHelper.posY);
        par1NBTTagCompound.setInteger("ACZ", this.centerHelper.posZ);
        NBTTagList var2 = new NBTTagList();
        for (VillageDoorInfo var4 : this.villageDoorInfoList) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setInteger("X", var4.posX);
            var5.setInteger("Y", var4.posY);
            var5.setInteger("Z", var4.posZ);
            var5.setInteger("IDX", var4.insideDirectionX);
            var5.setInteger("IDZ", var4.insideDirectionZ);
            var5.setInteger("TS", var4.lastActivityTimestamp);
            var2.appendTag(var5);
        }
        par1NBTTagCompound.setTag("Doors", var2);
        NBTTagList var7 = new NBTTagList();
        for (String var9 : this.playerReputation.keySet()) {
            NBTTagCompound var6 = new NBTTagCompound();
            var6.setString("Name", var9);
            var6.setInteger("S", (Integer)this.playerReputation.get(var9));
            var7.appendTag(var6);
        }
        par1NBTTagCompound.setTag("Players", var7);
    }

    public void endMatingSeason() {
        this.noBreedTicks = this.tickCounter;
    }

    public boolean isMatingSeason() {
        if (this.noBreedTicks != 0 && this.tickCounter - this.noBreedTicks < 3600) {
            return false;
        }
        return true;
    }

    public void setDefaultPlayerReputation(int par1) {
        for (String var3 : this.playerReputation.keySet()) {
            this.setReputationForPlayer(var3, par1);
        }
    }

    class VillageAgressor {
        public EntityLivingBase agressor;
        public int agressionTime;
        private static final String __OBFID = "CL_00001632";

        VillageAgressor(EntityLivingBase par2EntityLivingBase, int par3) {
            this.agressor = par2EntityLivingBase;
            this.agressionTime = par3;
        }
    }

}

