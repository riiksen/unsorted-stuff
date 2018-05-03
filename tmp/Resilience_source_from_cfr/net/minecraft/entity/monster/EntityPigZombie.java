/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.monster;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPigZombie
extends EntityZombie {
    private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier field_110190_br = new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.45, 0).setSaved(false);
    private int angerLevel;
    private int randomSoundDelay;
    private Entity field_110191_bu;
    private static final String __OBFID = "CL_00001693";

    public EntityPigZombie(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(field_110186_bp).setBaseValue(0.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }

    @Override
    protected boolean isAIEnabled() {
        return false;
    }

    @Override
    public void onUpdate() {
        if (this.field_110191_bu != this.entityToAttack && !this.worldObj.isClient) {
            IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var1.removeModifier(field_110190_br);
            if (this.entityToAttack != null) {
                var1.applyModifier(field_110190_br);
            }
        }
        this.field_110191_bu = this.entityToAttack;
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound("mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.onUpdate();
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
            return true;
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Anger", (short)this.angerLevel);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.angerLevel = par1NBTTagCompound.getShort("Anger");
    }

    @Override
    protected Entity findPlayerToAttack() {
        return this.angerLevel == 0 ? null : super.findPlayerToAttack();
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        Entity var3 = par1DamageSource.getEntity();
        if (var3 instanceof EntityPlayer) {
            List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            int var5 = 0;
            while (var5 < var4.size()) {
                Entity var6 = (Entity)var4.get(var5);
                if (var6 instanceof EntityPigZombie) {
                    EntityPigZombie var7 = (EntityPigZombie)var6;
                    var7.becomeAngryAt(var3);
                }
                ++var5;
            }
            this.becomeAngryAt(var3);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    private void becomeAngryAt(Entity par1Entity) {
        this.entityToAttack = par1Entity;
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
    }

    @Override
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }

    @Override
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(2 + par2);
        int var4 = 0;
        while (var4 < var3) {
            this.func_145779_a(Items.rotten_flesh, 1);
            ++var4;
        }
        var3 = this.rand.nextInt(2 + par2);
        var4 = 0;
        while (var4 < var3) {
            this.func_145779_a(Items.gold_nugget, 1);
            ++var4;
        }
    }

    @Override
    public boolean interact(EntityPlayer par1EntityPlayer) {
        return false;
    }

    @Override
    protected void dropRareDrop(int par1) {
        this.func_145779_a(Items.gold_ingot, 1);
    }

    @Override
    protected void addRandomArmor() {
        this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
        super.onSpawnWithEgg(par1EntityLivingData);
        this.setVillager(false);
        return par1EntityLivingData;
    }
}

