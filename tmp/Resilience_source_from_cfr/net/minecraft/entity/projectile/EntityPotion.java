/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPotion
extends EntityThrowable {
    private ItemStack potionDamage;
    private static final String __OBFID = "CL_00001727";

    public EntityPotion(World par1World) {
        super(par1World);
    }

    public EntityPotion(World par1World, EntityLivingBase par2EntityLivingBase, int par3) {
        this(par1World, par2EntityLivingBase, new ItemStack(Items.potionitem, 1, par3));
    }

    public EntityPotion(World par1World, EntityLivingBase par2EntityLivingBase, ItemStack par3ItemStack) {
        super(par1World, par2EntityLivingBase);
        this.potionDamage = par3ItemStack;
    }

    public EntityPotion(World par1World, double par2, double par4, double par6, int par8) {
        this(par1World, par2, par4, par6, new ItemStack(Items.potionitem, 1, par8));
    }

    public EntityPotion(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
        super(par1World, par2, par4, par6);
        this.potionDamage = par8ItemStack;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.05f;
    }

    @Override
    protected float func_70182_d() {
        return 0.5f;
    }

    @Override
    protected float func_70183_g() {
        return -20.0f;
    }

    public void setPotionDamage(int par1) {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
        }
        this.potionDamage.setItemDamage(par1);
    }

    public int getPotionDamage() {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
        }
        return this.potionDamage.getItemDamage();
    }

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
        if (!this.worldObj.isClient) {
            List var4;
            AxisAlignedBB var3;
            List var2 = Items.potionitem.getEffects(this.potionDamage);
            if (var2 != null && !var2.isEmpty() && (var4 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3 = this.boundingBox.expand(4.0, 2.0, 4.0))) != null && !var4.isEmpty()) {
                for (EntityLivingBase var6 : var4) {
                    double var7 = this.getDistanceSqToEntity(var6);
                    if (var7 >= 16.0) continue;
                    double var9 = 1.0 - Math.sqrt(var7) / 4.0;
                    if (var6 == par1MovingObjectPosition.entityHit) {
                        var9 = 1.0;
                    }
                    for (PotionEffect var12 : var2) {
                        int var13 = var12.getPotionID();
                        if (Potion.potionTypes[var13].isInstant()) {
                            Potion.potionTypes[var13].affectEntity(this.getThrower(), var6, var12.getAmplifier(), var9);
                            continue;
                        }
                        int var14 = (int)(var9 * (double)var12.getDuration() + 0.5);
                        if (var14 <= 20) continue;
                        var6.addPotionEffect(new PotionEffect(var13, var14, var12.getAmplifier()));
                    }
                }
            }
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), this.getPotionDamage());
            this.setDead();
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.func_150297_b("Potion", 10)) {
            this.potionDamage = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("Potion"));
        } else {
            this.setPotionDamage(par1NBTTagCompound.getInteger("potionValue"));
        }
        if (this.potionDamage == null) {
            this.setDead();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.potionDamage != null) {
            par1NBTTagCompound.setTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
        }
    }
}

