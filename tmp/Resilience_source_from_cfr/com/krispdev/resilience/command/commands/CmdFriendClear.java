/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.relations.Friend;
import java.util.ArrayList;

public class CmdFriendClear
extends Command {
    public CmdFriendClear() {
        super("friends clear", "", "Clears the friend list");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        Friend.friendList.clear();
        Resilience.getInstance().getFileManager().saveFriends(new String[0]);
        Resilience.getInstance().getLogger().infoChat("Cleared the friend list");
        return true;
    }
}

