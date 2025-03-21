/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CombatEntry;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CombatTracker {
    private final List combatEntries = new ArrayList();
    private final EntityLivingBase fighter;
    private int field_94555_c;
    private boolean field_94552_d;
    private boolean field_94553_e;
    private String field_94551_f;
    private static final String __OBFID = "CL_00001520";

    public CombatTracker(EntityLivingBase par1EntityLivingBase) {
        this.fighter = par1EntityLivingBase;
    }

    public void func_94545_a() {
        this.func_94542_g();
        if (this.fighter.isOnLadder()) {
            Block var1 = this.fighter.worldObj.getBlock(MathHelper.floor_double(this.fighter.posX), MathHelper.floor_double(this.fighter.boundingBox.minY), MathHelper.floor_double(this.fighter.posZ));
            if (var1 == Blocks.ladder) {
                this.field_94551_f = "ladder";
            } else if (var1 == Blocks.vine) {
                this.field_94551_f = "vines";
            }
        } else if (this.fighter.isInWater()) {
            this.field_94551_f = "water";
        }
    }

    public void func_94547_a(DamageSource par1DamageSource, float par2, float par3) {
        this.func_94549_h();
        this.func_94545_a();
        CombatEntry var4 = new CombatEntry(par1DamageSource, this.fighter.ticksExisted, par2, par3, this.field_94551_f, this.fighter.fallDistance);
        this.combatEntries.add(var4);
        this.field_94555_c = this.fighter.ticksExisted;
        this.field_94553_e = true;
        this.field_94552_d |= var4.func_94559_f();
    }

    public IChatComponent func_151521_b() {
        IChatComponent var3;
        if (this.combatEntries.size() == 0) {
            return new ChatComponentTranslation("death.attack.generic", this.fighter.func_145748_c_());
        }
        CombatEntry var1 = this.func_94544_f();
        CombatEntry var2 = (CombatEntry)this.combatEntries.get(this.combatEntries.size() - 1);
        IChatComponent var4 = var2.func_151522_h();
        Entity var5 = var2.getDamageSrc().getEntity();
        if (var1 != null && var2.getDamageSrc() == DamageSource.fall) {
            IChatComponent var6 = var1.func_151522_h();
            if (var1.getDamageSrc() != DamageSource.fall && var1.getDamageSrc() != DamageSource.outOfWorld) {
                if (!(var6 == null || var4 != null && var6.equals(var4))) {
                    ItemStack var8;
                    Entity var9 = var1.getDamageSrc().getEntity();
                    ItemStack itemStack = var8 = var9 instanceof EntityLivingBase ? ((EntityLivingBase)var9).getHeldItem() : null;
                    var3 = var8 != null && var8.hasDisplayName() ? new ChatComponentTranslation("death.fell.assist.item", this.fighter.func_145748_c_(), var6, var8.func_151000_E()) : new ChatComponentTranslation("death.fell.assist", this.fighter.func_145748_c_(), var6);
                } else if (var4 != null) {
                    ItemStack var7;
                    ItemStack itemStack = var7 = var5 instanceof EntityLivingBase ? ((EntityLivingBase)var5).getHeldItem() : null;
                    var3 = var7 != null && var7.hasDisplayName() ? new ChatComponentTranslation("death.fell.finish.item", this.fighter.func_145748_c_(), var4, var7.func_151000_E()) : new ChatComponentTranslation("death.fell.finish", this.fighter.func_145748_c_(), var4);
                } else {
                    var3 = new ChatComponentTranslation("death.fell.killer", this.fighter.func_145748_c_());
                }
            } else {
                var3 = new ChatComponentTranslation("death.fell.accident." + this.func_94548_b(var1), this.fighter.func_145748_c_());
            }
        } else {
            var3 = var2.getDamageSrc().func_151519_b(this.fighter);
        }
        return var3;
    }

    public EntityLivingBase func_94550_c() {
        EntityLivingBase var1 = null;
        EntityPlayer var2 = null;
        float var3 = 0.0f;
        float var4 = 0.0f;
        for (CombatEntry var6 : this.combatEntries) {
            if (var6.getDamageSrc().getEntity() instanceof EntityPlayer && (var2 == null || var6.func_94563_c() > var4)) {
                var4 = var6.func_94563_c();
                var2 = (EntityPlayer)var6.getDamageSrc().getEntity();
            }
            if (!(var6.getDamageSrc().getEntity() instanceof EntityLivingBase) || var1 != null && var6.func_94563_c() <= var3) continue;
            var3 = var6.func_94563_c();
            var1 = (EntityLivingBase)var6.getDamageSrc().getEntity();
        }
        if (var2 != null && var4 >= var3 / 3.0f) {
            return var2;
        }
        return var1;
    }

    private CombatEntry func_94544_f() {
        CombatEntry var1 = null;
        CombatEntry var2 = null;
        int var3 = 0;
        float var4 = 0.0f;
        int var5 = 0;
        while (var5 < this.combatEntries.size()) {
            CombatEntry var7;
            CombatEntry var6 = (CombatEntry)this.combatEntries.get(var5);
            CombatEntry combatEntry = var7 = var5 > 0 ? (CombatEntry)this.combatEntries.get(var5 - 1) : null;
            if (!(var6.getDamageSrc() != DamageSource.fall && var6.getDamageSrc() != DamageSource.outOfWorld || var6.func_94561_i() <= 0.0f || var1 != null && var6.func_94561_i() <= var4)) {
                var1 = var5 > 0 ? var7 : var6;
                var4 = var6.func_94561_i();
            }
            if (var6.func_94562_g() != null && (var2 == null || var6.func_94563_c() > (float)var3)) {
                var2 = var6;
            }
            ++var5;
        }
        if (var4 > 5.0f && var1 != null) {
            return var1;
        }
        if (var3 > 5 && var2 != null) {
            return var2;
        }
        return null;
    }

    private String func_94548_b(CombatEntry par1CombatEntry) {
        return par1CombatEntry.func_94562_g() == null ? "generic" : par1CombatEntry.func_94562_g();
    }

    private void func_94542_g() {
        this.field_94551_f = null;
    }

    private void func_94549_h() {
        int var1;
        int n = var1 = this.field_94552_d ? 300 : 100;
        if (this.field_94553_e && this.fighter.ticksExisted - this.field_94555_c > var1) {
            this.combatEntries.clear();
            this.field_94553_e = false;
            this.field_94552_d = false;
        }
    }
}

