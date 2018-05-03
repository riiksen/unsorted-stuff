/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.relations.Friend;
import java.util.ArrayList;

public class CmdFriendDel
extends Command {
    public CmdFriendDel() {
        super("friend del ", "[Username/Alias]", "Deletes the specified user from the friend list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("del ");
        for (Friend friend : Friend.friendList) {
            if (!friend.getName().trim().equalsIgnoreCase(args[1]) && !friend.getAlias().trim().equalsIgnoreCase(args[1])) continue;
            Friend.friendList.remove(Friend.friendList.indexOf(friend));
            Resilience.getInstance().getLogger().infoChat("User deleted from the friend list");
            return true;
        }
        Resilience.getInstance().getLogger().warningChat("User not found");
        return true;
    }
}

