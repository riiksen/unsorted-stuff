/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class CommandNotFoundException
extends CommandException {
    private static final String __OBFID = "CL_00001191";

    public CommandNotFoundException() {
        this("commands.generic.notFound", new Object[0]);
    }

    public /* varargs */ CommandNotFoundException(String par1Str, Object ... par2ArrayOfObj) {
        super(par1Str, par2ArrayOfObj);
    }
}

