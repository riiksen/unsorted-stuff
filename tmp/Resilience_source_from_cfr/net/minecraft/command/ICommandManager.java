/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import java.util.List;
import java.util.Map;
import net.minecraft.command.ICommandSender;

public interface ICommandManager {
    public int executeCommand(ICommandSender var1, String var2);

    public List getPossibleCommands(ICommandSender var1, String var2);

    public List getPossibleCommands(ICommandSender var1);

    public Map getCommands();
}

