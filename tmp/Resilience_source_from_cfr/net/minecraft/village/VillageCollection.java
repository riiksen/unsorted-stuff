/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class VillageCollection
extends WorldSavedData {
    private World worldObj;
    private final List villagerPositionsList = new ArrayList();
    private final List newDoors = new ArrayList();
    private final List villageList = new ArrayList();
    private int tickCounter;
    private static final String __OBFID = "CL_00001635";

    public VillageCollection(String par1Str) {
        super(par1Str);
    }

    public VillageCollection(World par1World) {
        super("villages");
        this.worldObj = par1World;
        this.markDirty();
    }

    public void func_82566_a(World par1World) {
        this.worldObj = par1World;
        for (Village var3 : this.villageList) {
            var3.func_82691_a(par1World);
        }
    }

    public void addVillagerPosition(int par1, int par2, int par3) {
        if (this.villagerPositionsList.size() <= 64 && !this.isVillagerPositionPresent(par1, par2, par3)) {
            this.villagerPositionsList.add(new ChunkCoordinates(par1, par2, par3));
        }
    }

    public void tick() {
        ++this.tickCounter;
        for (Village var2 : this.villageList) {
            var2.tick(this.tickCounter);
        }
        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition();
        this.addNewDoorsToVillageOrCreateVillage();
        if (this.tickCounter % 400 == 0) {
            this.markDirty();
        }
    }

    private void removeAnnihilatedVillages() {
        Iterator var1 = this.villageList.iterator();
        while (var1.hasNext()) {
            Village var2 = (Village)var1.next();
            if (!var2.isAnnihilated()) continue;
            var1.remove();
            this.markDirty();
        }
    }

    public List getVillageList() {
        return this.villageList;
    }

    public Village findNearestVillage(int par1, int par2, int par3, int par4) {
        Village var5 = null;
        float var6 = Float.MAX_VALUE;
        for (Village var8 : this.villageList) {
            float var10;
            float var9 = var8.getCenter().getDistanceSquared(par1, par2, par3);
            if (var9 >= var6 || var9 > (var10 = (float)(par4 + var8.getVillageRadius())) * var10) continue;
            var5 = var8;
            var6 = var9;
        }
        return var5;
    }

    private void dropOldestVillagerPosition() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.addUnassignedWoodenDoorsAroundToNewDoorsList((ChunkCoordinates)this.villagerPositionsList.remove(0));
        }
    }

    private void addNewDoorsToVillageOrCreateVillage() {
        int var1 = 0;
        while (var1 < this.newDoors.size()) {
            VillageDoorInfo var2 = (VillageDoorInfo)this.newDoors.get(var1);
            boolean var3 = false;
            for (Village var5 : this.villageList) {
                int var7;
                int var6 = (int)var5.getCenter().getDistanceSquared(var2.posX, var2.posY, var2.posZ);
                if (var6 > (var7 = 32 + var5.getVillageRadius()) * var7) continue;
                var5.addVillageDoorInfo(var2);
                var3 = true;
                break;
            }
            if (!var3) {
                Village var8 = new Village(this.worldObj);
                var8.addVillageDoorInfo(var2);
                this.villageList.add(var8);
                this.markDirty();
            }
            ++var1;
        }
        this.newDoors.clear();
    }

    private void addUnassignedWoodenDoorsAroundToNewDoorsList(ChunkCoordinates par1ChunkCoordinates) {
        int var2 = 16;
        int var3 = 4;
        int var4 = 16;
        int var5 = par1ChunkCoordinates.posX - var2;
        while (var5 < par1ChunkCoordinates.posX + var2) {
            int var6 = par1ChunkCoordinates.posY - var3;
            while (var6 < par1ChunkCoordinates.posY + var3) {
                int var7 = par1ChunkCoordinates.posZ - var4;
                while (var7 < par1ChunkCoordinates.posZ + var4) {
                    if (this.isWoodenDoorAt(var5, var6, var7)) {
                        VillageDoorInfo var8 = this.getVillageDoorAt(var5, var6, var7);
                        if (var8 == null) {
                            this.addDoorToNewListIfAppropriate(var5, var6, var7);
                        } else {
                            var8.lastActivityTimestamp = this.tickCounter;
                        }
                    }
                    ++var7;
                }
                ++var6;
            }
            ++var5;
        }
    }

    private VillageDoorInfo getVillageDoorAt(int par1, int par2, int par3) {
        VillageDoorInfo var5;
        Iterator var4 = this.newDoors.iterator();
        do {
            if (!var4.hasNext()) {
                Village var7;
                VillageDoorInfo var6;
                var4 = this.villageList.iterator();
                do {
                    if (var4.hasNext()) continue;
                    return null;
                } while ((var6 = (var7 = (Village)var4.next()).getVillageDoorAt(par1, par2, par3)) == null);
                return var6;
            }
            var5 = (VillageDoorInfo)var4.next();
        } while (var5.posX != par1 || var5.posZ != par3 || Math.abs(var5.posY - par2) > 1);
        return var5;
    }

    private void addDoorToNewListIfAppropriate(int par1, int par2, int par3) {
        int var4 = ((BlockDoor)Blocks.wooden_door).func_150013_e(this.worldObj, par1, par2, par3);
        if (var4 != 0 && var4 != 2) {
            int var5 = 0;
            int var6 = -5;
            while (var6 < 0) {
                if (this.worldObj.canBlockSeeTheSky(par1, par2, par3 + var6)) {
                    --var5;
                }
                ++var6;
            }
            var6 = 1;
            while (var6 <= 5) {
                if (this.worldObj.canBlockSeeTheSky(par1, par2, par3 + var6)) {
                    ++var5;
                }
                ++var6;
            }
            if (var5 != 0) {
                this.newDoors.add(new VillageDoorInfo(par1, par2, par3, 0, var5 > 0 ? -2 : 2, this.tickCounter));
            }
        } else {
            int var5 = 0;
            int var6 = -5;
            while (var6 < 0) {
                if (this.worldObj.canBlockSeeTheSky(par1 + var6, par2, par3)) {
                    --var5;
                }
                ++var6;
            }
            var6 = 1;
            while (var6 <= 5) {
                if (this.worldObj.canBlockSeeTheSky(par1 + var6, par2, par3)) {
                    ++var5;
                }
                ++var6;
            }
            if (var5 != 0) {
                this.newDoors.add(new VillageDoorInfo(par1, par2, par3, var5 > 0 ? -2 : 2, 0, this.tickCounter));
            }
        }
    }

    private boolean isVillagerPositionPresent(int par1, int par2, int par3) {
        Iterator var4 = this.villagerPositionsList.iterator();
        do {
            if (!var4.hasNext()) {
                return false;
            }
            ChunkCoordinates var5 = (ChunkCoordinates)var4.next();
        } while (var5.posX != par1 || var5.posY != par2 || var5.posZ != par3);
        return true;
    }

    private boolean isWoodenDoorAt(int par1, int par2, int par3) {
        if (this.worldObj.getBlock(par1, par2, par3) == Blocks.wooden_door) {
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.tickCounter = par1NBTTagCompound.getInteger("Tick");
        NBTTagList var2 = par1NBTTagCompound.getTagList("Villages", 10);
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            Village var5 = new Village();
            var5.readVillageDataFromNBT(var4);
            this.villageList.add(var5);
            ++var3;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("Tick", this.tickCounter);
        NBTTagList var2 = new NBTTagList();
        for (Village var4 : this.villageList) {
            NBTTagCompound var5 = new NBTTagCompound();
            var4.writeVillageDataToNBT(var5);
            var2.appendTag(var5);
        }
        par1NBTTagCompound.setTag("Villages", var2);
    }
}

