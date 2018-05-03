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

public class CmdXrayClear
extends Command {
    public CmdXrayClear() {
        super("xray clear", "", "Clears the xray list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (!cmd.startsWith("xray clear")) {
            return false;
        }
        Resilience.getInstance().getXrayUtils().xrayBlocks.clear();
        Resilience.getInstance().getLogger().infoChat("Cleared the xray list");
        Resilience.getInstance().getFileManager().saveXrayBlocks(new String[0]);
        if (Resilience.getInstance().getXrayUtils().xrayEnabled) {
            Resilience.getInstance().getInvoker().loadRenderers();
        }
        return true;
    }
}

