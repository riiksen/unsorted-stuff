/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import net.minecraft.crash.CrashReport;

public class ReportedException
extends RuntimeException {
    private final CrashReport theReportedExceptionCrashReport;
    private static final String __OBFID = "CL_00001579";

    public ReportedException(CrashReport par1CrashReport) {
        this.theReportedExceptionCrashReport = par1CrashReport;
    }

    public CrashReport getCrashReport() {
        return this.theReportedExceptionCrashReport;
    }

    @Override
    public Throwable getCause() {
        return this.theReportedExceptionCrashReport.getCrashCause();
    }

    @Override
    public String getMessage() {
        return this.theReportedExceptionCrashReport.getDescription();
    }
}

