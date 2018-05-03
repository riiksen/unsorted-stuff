/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class CmdSay
extends Command {
    public CmdSay() {
        super("say ", "[Msg]", "Sends a message to the server, eg. \".help\"");
    }

    @Override
    public boolean recieveCommand(String cmd) throws Exception {
        String[] args = cmd.split("say ");
        Resilience.getInstance().getInvoker().sendPacket(new C01PacketChatMessage(args[1]));
        return true;
    }
}

