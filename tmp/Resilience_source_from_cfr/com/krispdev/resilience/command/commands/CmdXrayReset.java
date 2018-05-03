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

public class CmdXrayReset
extends Command {
    public CmdXrayReset() {
        super("xray reset", "", "Resets the xray list to default");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        if (!cmd.startsWith("xray reset")) {
            return false;
        }
        Resilience.getInstance().getXrayUtils().xrayBlocks.clear();
        Resilience.getInstance().getFileManager().saveXrayBlocks("56", "57", "14", "15", "16", "41", "42", "73", "74", "152", "173", "129", "133", "10", "11", "8", "9");
        Resilience.getInstance().getFileManager().loadXrayBlocks();
        Resilience.getInstance().getLogger().infoChat("Reset the xray list to default settings");
        if (Resilience.getInstance().getXrayUtils().xrayEnabled) {
            Resilience.getInstance().getInvoker().loadRenderers();
        }
        return true;
    }
}

