/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.ICommandSender;

public interface IAdminCommand {
    public /* varargs */ void notifyAdmins(ICommandSender var1, int var2, String var3, Object ... var4);
}

