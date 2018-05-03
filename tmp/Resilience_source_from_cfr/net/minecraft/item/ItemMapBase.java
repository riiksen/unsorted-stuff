/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ItemMapBase
extends Item {
    private static final String __OBFID = "CL_00000004";

    @Override
    public boolean isMap() {
        return true;
    }

    public Packet func_150911_c(ItemStack p_150911_1_, World p_150911_2_, EntityPlayer p_150911_3_) {
        return null;
    }
}

