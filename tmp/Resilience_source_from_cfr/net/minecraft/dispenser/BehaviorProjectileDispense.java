/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense
extends BehaviorDefaultDispenseItem {
    private static final String __OBFID = "CL_00001394";

    @Override
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        World var3 = par1IBlockSource.getWorld();
        IPosition var4 = BlockDispenser.func_149939_a(par1IBlockSource);
        EnumFacing var5 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
        IProjectile var6 = this.getProjectileEntity(var3, var4);
        var6.setThrowableHeading(var5.getFrontOffsetX(), (float)var5.getFrontOffsetY() + 0.1f, var5.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        var3.spawnEntityInWorld((Entity)((Object)var6));
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }

    @Override
    protected void playDispenseSound(IBlockSource par1IBlockSource) {
        par1IBlockSource.getWorld().playAuxSFX(1002, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
    }

    protected abstract IProjectile getProjectileEntity(World var1, IPosition var2);

    protected float func_82498_a() {
        return 6.0f;
    }

    protected float func_82500_b() {
        return 1.1f;
    }
}

