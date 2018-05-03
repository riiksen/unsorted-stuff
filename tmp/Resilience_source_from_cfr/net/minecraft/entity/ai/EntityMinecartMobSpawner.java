/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner
extends EntityMinecart {
    private final MobSpawnerBaseLogic mobSpawnerLogic;
    private static final String __OBFID = "CL_00001678";

    public EntityMinecartMobSpawner(World par1World) {
        super(par1World);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic(){
            private static final String __OBFID = "CL_00001679";

            @Override
            public void func_98267_a(int par1) {
                EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)par1);
            }

            @Override
            public World getSpawnerWorld() {
                return EntityMinecartMobSpawner.this.worldObj;
            }

            @Override
            public int getSpawnerX() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posX);
            }

            @Override
            public int getSpawnerY() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posY);
            }

            @Override
            public int getSpawnerZ() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posZ);
            }
        };
    }

    public EntityMinecartMobSpawner(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        this.mobSpawnerLogic = new ;
    }

    @Override
    public int getMinecartType() {
        return 4;
    }

    @Override
    public Block func_145817_o() {
        return Blocks.mob_spawner;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.readFromNBT(par1NBTTagCompound);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.writeToNBT(par1NBTTagCompound);
    }

    @Override
    public void handleHealthUpdate(byte par1) {
        this.mobSpawnerLogic.setDelayToMin(par1);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }

    public MobSpawnerBaseLogic func_98039_d() {
        return this.mobSpawnerLogic;
    }

}

