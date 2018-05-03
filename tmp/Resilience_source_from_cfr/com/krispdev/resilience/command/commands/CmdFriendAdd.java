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

public class CmdFriendAdd
extends Command {
    public CmdFriendAdd() {
        super("friend add ", "[Username]", "Adds a friend to the friend list");
    }

    @Override
    public boolean recieveCommand(String cmd) {
        String[] check = cmd.split(" ");
        if (check.length > 3) {
            return false;
        }
        String[] args = cmd.split("add ");
        for (Enemy enemy : Enemy.enemyList) {
            if (!enemy.getName().equalsIgnoreCase(args[1])) continue;
            Enemy.enemyList.remove(Enemy.enemyList.indexOf(enemy));
            Resilience.getInstance().getFileManager().saveEnemies(new String[0]);
            break;
        }
        if (!FriendManager.isFriend(args[1])) {
            Friend.friendList.add(new Friend(args[1], args[1]));
            Resilience.getInstance().getFileManager().saveFriends(new String[0]);
            Resilience.getInstance().getLogger().infoChat("Friend added to the friend list");
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("User already on the friend list");
        return true;
    }
}

