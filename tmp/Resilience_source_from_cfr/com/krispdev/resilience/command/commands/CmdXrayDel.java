/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.utilities.XrayBlock;
import com.krispdev.resilience.utilities.XrayUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;

public class CmdXrayDel
extends Command {
    public CmdXrayDel() {
        super("xray del ", " [Block ID]", "Deletes the specified block from the xray list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (!cmd.startsWith("xray del")) {
            return false;
        }
        String[] args = cmd.split("del ");
        int i = 0;
        while (i < Resilience.getInstance().getXrayUtils().xrayBlocks.size()) {
            if (Resilience.getInstance().getXrayUtils().xrayBlocks.get(i).getId() == Integer.parseInt(args[1])) {
                Resilience.getInstance().getXrayUtils().xrayBlocks.remove(i);
                if (Resilience.getInstance().getXrayUtils().xrayEnabled) {
                    Resilience.getInstance().getInvoker().loadRenderers();
                }
                Resilience.getInstance().getLogger().info("Removed block " + args[1] + " from the xray list");
                Resilience.getInstance().getFileManager().saveXrayBlocks(new String[0]);
                return true;
            }
            ++i;
        }
        Resilience.getInstance().getLogger().warningChat("No block called " + args[1] + " found on the xray list!");
        return true;
    }
}

