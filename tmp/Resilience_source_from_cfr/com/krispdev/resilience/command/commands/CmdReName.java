/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.item.ItemStack;

public class CmdReName
extends Command {
    public CmdReName() {
        super("rename ", "[Name]", "Renames your current item to a huge string in creative");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (Resilience.getInstance().getInvoker().isInCreativeMode()) {
            String[] args = cmd.split("name ");
            ItemStack item = Resilience.getInstance().getInvoker().getCurrentItem();
            Resilience.getInstance().getInvoker().setStackDisplayName(item, args[1]);
            int i = 0;
            while (i < 100) {
                Resilience.getInstance().getInvoker().setStackDisplayName(item, Resilience.getInstance().getInvoker().getItemDisplayName(item).concat(args[1]));
                ++i;
            }
            Resilience.getInstance().getLogger().infoChat("Renamed your current item");
            return true;
        }
        Resilience.getInstance().getLogger().infoChat("Error! You must be in creative");
        return false;
    }
}

