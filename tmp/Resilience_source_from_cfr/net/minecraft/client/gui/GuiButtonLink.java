/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui;

import java.lang.reflect.Method;
import java.net.URI;
import net.minecraft.client.gui.GuiButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiButtonLink
extends GuiButton {
    private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00000673";

    public GuiButtonLink(int par1, int par2, int par3, int par4, int par5, String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
    }

    public void func_146138_a(String p_146138_1_) {
        try {
            URI var2 = new URI(p_146138_1_);
            Class var3 = Class.forName("java.awt.Desktop");
            Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            var3.getMethod("browse", URI.class).invoke(var4, var2);
        }
        catch (Throwable var5) {
            logger.error("Couldn't open link", var5);
        }
    }
}

