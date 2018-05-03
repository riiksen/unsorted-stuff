/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntitySnowman
extends EntityGolem
implements IRangedAttackMob {
    private static final String __OBFID = "CL_00001650";

    public EntitySnowman(World par1World) {
        super(par1World);
        this.setSize(0.4f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        if (this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) > 1.0f) {
            this.attackEntityFrom(DamageSource.onFire, 1.0f);
        }
        int var4 = 0;
        while (var4 < 4) {
            var1 = MathHelper.floor_double(this.posX + (double)((float)(var4 % 2 * 2 - 1) * 0.25f));
            if (this.worldObj.getBlock(var1, var2 = MathHelper.floor_double(this.posY), var3 = MathHelper.floor_double(this.posZ + (double)((float)(var4 / 2 % 2 * 2 - 1) * 0.25f))).getMaterial() == Material.air && this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) < 0.8f && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, var1, var2, var3)) {
                this.worldObj.setBlock(var1, var2, var3, Blocks.snow_layer);
            }
            ++var4;
        }
    }

    @Override
    protected Item func_146068_u() {
        return Items.snowball;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(16);
        int var4 = 0;
        while (var4 < var3) {
            this.func_145779_a(Items.snowball, 1);
            ++var4;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
        EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
        double var4 = par1EntityLivingBase.posX - this.posX;
        double var6 = par1EntityLivingBase.posY + (double)par1EntityLivingBase.getEyeHeight() - 1.100000023841858 - var3.posY;
        double var8 = par1EntityLivingBase.posZ - this.posZ;
        float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.2f;
        var3.setThrowableHeading(var4, var6 + (double)var10, var8, 1.6f, 12.0f);
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(var3);
    }
}

