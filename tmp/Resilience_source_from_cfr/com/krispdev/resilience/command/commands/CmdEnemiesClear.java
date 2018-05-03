/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.relations.Enemy;
import java.util.ArrayList;

public class CmdEnemiesClear
extends Command {
    public CmdEnemiesClear() {
        super("enemies clear", "", "Clears the enemy list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        Enemy.enemyList.clear();
        Resilience.getInstance().getLogger().infoChat("Cleared the enemy list");
        return true;
    }
}

