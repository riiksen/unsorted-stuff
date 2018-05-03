/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class SyntaxErrorException
extends CommandException {
    private static final String __OBFID = "CL_00001189";

    public SyntaxErrorException() {
        this("commands.generic.snytax", new Object[0]);
    }

    public /* varargs */ SyntaxErrorException(String par1Str, Object ... par2ArrayOfObj) {
        super(par1Str, par2ArrayOfObj);
    }
}

