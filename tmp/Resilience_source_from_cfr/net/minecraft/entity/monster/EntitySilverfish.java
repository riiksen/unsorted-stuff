/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.ImmutablePair
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class EntitySilverfish
extends EntityMob {
    private int allySummonCooldown;
    private static final String __OBFID = "CL_00001696";

    public EntitySilverfish(World par1World) {
        super(par1World);
        this.setSize(0.3f, 0.7f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected Entity findPlayerToAttack() {
        double var1 = 8.0;
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, var1);
    }

    @Override
    protected String getLivingSound() {
        return "mob.silverfish.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.allySummonCooldown <= 0 && (par1DamageSource instanceof EntityDamageSource || par1DamageSource == DamageSource.magic)) {
            this.allySummonCooldown = 20;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    protected void attackEntity(Entity par1Entity, float par2) {
        if (this.attackTime <= 0 && par2 < 1.2f && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        }
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.silverfish.step", 0.15f, 1.0f);
    }

    @Override
    protected Item func_146068_u() {
        return Item.getItemById(0);
    }

    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }

    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        if (!this.worldObj.isClient) {
            int var1;
            int var3;
            int var2;
            int var6;
            if (this.allySummonCooldown > 0) {
                --this.allySummonCooldown;
                if (this.allySummonCooldown == 0) {
                    var1 = MathHelper.floor_double(this.posX);
                    var2 = MathHelper.floor_double(this.posY);
                    var3 = MathHelper.floor_double(this.posZ);
                    boolean var4 = false;
                    int var5 = 0;
                    while (!var4 && var5 <= 5 && var5 >= -5) {
                        var6 = 0;
                        while (!var4 && var6 <= 10 && var6 >= -10) {
                            int var7 = 0;
                            while (!var4 && var7 <= 10 && var7 >= -10) {
                                if (this.worldObj.getBlock(var1 + var6, var2 + var5, var3 + var7) == Blocks.monster_egg) {
                                    if (!this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                                        int var8 = this.worldObj.getBlockMetadata(var1 + var6, var2 + var5, var3 + var7);
                                        ImmutablePair var9 = BlockSilverfish.func_150197_b(var8);
                                        this.worldObj.setBlock(var1 + var6, var2 + var5, var3 + var7, (Block)var9.getLeft(), (Integer)var9.getRight(), 3);
                                    } else {
                                        this.worldObj.func_147480_a(var1 + var6, var2 + var5, var3 + var7, false);
                                    }
                                    Blocks.monster_egg.onBlockDestroyedByPlayer(this.worldObj, var1 + var6, var2 + var5, var3 + var7, 0);
                                    if (this.rand.nextBoolean()) {
                                        var4 = true;
                                        break;
                                    }
                                }
                                int n = var7 = var7 <= 0 ? 1 - var7 : 0 - var7;
                            }
                            int n = var6 = var6 <= 0 ? 1 - var6 : 0 - var6;
                        }
                        int n = var5 = var5 <= 0 ? 1 - var5 : 0 - var5;
                    }
                }
            }
            if (this.entityToAttack == null && !this.hasPath()) {
                var1 = MathHelper.floor_double(this.posX);
                var2 = MathHelper.floor_double(this.posY + 0.5);
                var3 = MathHelper.floor_double(this.posZ);
                int var10 = this.rand.nextInt(6);
                Block var11 = this.worldObj.getBlock(var1 + Facing.offsetsXForSide[var10], var2 + Facing.offsetsYForSide[var10], var3 + Facing.offsetsZForSide[var10]);
                var6 = this.worldObj.getBlockMetadata(var1 + Facing.offsetsXForSide[var10], var2 + Facing.offsetsYForSide[var10], var3 + Facing.offsetsZForSide[var10]);
                if (BlockSilverfish.func_150196_a(var11)) {
                    this.worldObj.setBlock(var1 + Facing.offsetsXForSide[var10], var2 + Facing.offsetsYForSide[var10], var3 + Facing.offsetsZForSide[var10], Blocks.monster_egg, BlockSilverfish.func_150195_a(var11, var6), 3);
                    this.spawnExplosionParticle();
                    this.setDead();
                } else {
                    this.updateWanderPath();
                }
            } else if (this.entityToAttack != null && !this.hasPath()) {
                this.entityToAttack = null;
            }
        }
    }

    @Override
    public float getBlockPathWeight(int par1, int par2, int par3) {
        return this.worldObj.getBlock(par1, par2 - 1, par3) == Blocks.stone ? 10.0f : super.getBlockPathWeight(par1, par2, par3);
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 5.0);
            if (var1 == null) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}

