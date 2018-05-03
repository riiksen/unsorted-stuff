/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.resources.data;

import net.minecraft.client.resources.data.IMetadataSection;

public class FontMetadataSection
implements IMetadataSection {
    private final float[] charWidths;
    private final float[] charLefts;
    private final float[] charSpacings;
    private static final String __OBFID = "CL_00001108";

    public FontMetadataSection(float[] par1ArrayOfFloat, float[] par2ArrayOfFloat, float[] par3ArrayOfFloat) {
        this.charWidths = par1ArrayOfFloat;
        this.charLefts = par2ArrayOfFloat;
        this.charSpacings = par3ArrayOfFloat;
    }
}

