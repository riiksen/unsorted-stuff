/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartChest
extends EntityMinecartContainer {
    private static final String __OBFID = "CL_00001671";

    public EntityMinecartChest(World par1World) {
        super(par1World);
    }

    public EntityMinecartChest(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    @Override
    public void killMinecart(DamageSource par1DamageSource) {
        super.killMinecart(par1DamageSource);
        this.func_145778_a(Item.getItemFromBlock(Blocks.chest), 1, 0.0f);
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public int getMinecartType() {
        return 1;
    }

    @Override
    public Block func_145817_o() {
        return Blocks.chest;
    }

    @Override
    public int getDefaultDisplayTileOffset() {
        return 8;
    }
}

