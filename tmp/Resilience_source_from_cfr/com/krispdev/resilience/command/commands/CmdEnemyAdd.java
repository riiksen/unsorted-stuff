/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.EnemyManager;
import com.krispdev.resilience.relations.Friend;
import java.util.ArrayList;

public class CmdEnemyAdd
extends Command {
    public CmdEnemyAdd() {
        super("enemy add ", "[Username]", "Adds an enemy to the enemy list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("add ");
        for (Friend friend : Friend.friendList) {
            if (!friend.getName().equalsIgnoreCase(args[1])) continue;
            Friend.friendList.remove(Friend.friendList.indexOf(friend));
            Resilience.getInstance().getFileManager().saveFriends(new String[0]);
            break;
        }
        if (!EnemyManager.isEnemy(args[1])) {
            Enemy.enemyList.add(new Enemy(args[1]));
            Resilience.getInstance().getFileManager().saveEnemies(new String[0]);
            Resilience.getInstance().getLogger().infoChat("User added to the enemy list");
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("\u00a7cError! \u00a7fUser already on the enemy list");
        return true;
    }
}

