/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.mco;

import net.minecraft.client.mco.ExceptionMcoService;

public class ExceptionRetryCall
extends ExceptionMcoService {
    public final int field_148832_d;
    private static final String __OBFID = "CL_00001178";

    public ExceptionRetryCall(int par1) {
        super(503, "Retry operation", -1);
        this.field_148832_d = par1;
    }
}

