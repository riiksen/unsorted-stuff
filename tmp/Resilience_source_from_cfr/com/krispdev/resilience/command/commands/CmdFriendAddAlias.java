/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;
import java.util.ArrayList;

public class CmdFriendAddAlias
extends Command {
    public CmdFriendAddAlias() {
        super("friend add ", "[Username] [Alias]", "Adds a friend to the friend list with the specified alias");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split(" ");
        if (args.length <= 3) {
            return false;
        }
        for (Enemy enemy : Enemy.enemyList) {
            if (!enemy.getName().equalsIgnoreCase(args[2])) continue;
            Enemy.enemyList.remove(Enemy.enemyList.indexOf(enemy));
            Resilience.getInstance().getFileManager().saveEnemies(new String[0]);
            break;
        }
        if (!FriendManager.isFriend(args[2])) {
            Friend.friendList.add(new Friend(args[2], args[3]));
            Resilience.getInstance().getFileManager().saveFriends(new String[0]);
            Resilience.getInstance().getLogger().infoChat("User added to the friend list");
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("User already on the friend list");
        return true;
    }
}

