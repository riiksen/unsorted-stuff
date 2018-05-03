/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class NumberInvalidException
extends CommandException {
    private static final String __OBFID = "CL_00001188";

    public NumberInvalidException() {
        this("commands.generic.num.invalid", new Object[0]);
    }

    public /* varargs */ NumberInvalidException(String par1Str, Object ... par2ArrayOfObj) {
        super(par1Str, par2ArrayOfObj);
    }
}

