/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class CmdEnchant
extends Command {
    public CmdEnchant() {
        super("enchant", "", "Forces max enchantments on an item in creative");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (Resilience.getInstance().getInvoker().isInCreativeMode()) {
            ItemStack item = Resilience.getInstance().getInvoker().getCurrentItem();
            if (item != null) {
                Enchantment[] arrenchantment = Resilience.getInstance().getInvoker().getEnchantList();
                int n = arrenchantment.length;
                int n2 = 0;
                while (n2 < n) {
                    Enchantment e = arrenchantment[n2];
                    if (e != null) {
                        Resilience.getInstance().getInvoker().addEnchantment(item, e, 127);
                    }
                    ++n2;
                }
                Resilience.getInstance().getLogger().infoChat("Enchanted your " + item.getDisplayName());
                return true;
            }
            Resilience.getInstance().getLogger().warningChat("Error! No item in hand found");
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("Error! Player must be in creative mode");
        return true;
    }
}

