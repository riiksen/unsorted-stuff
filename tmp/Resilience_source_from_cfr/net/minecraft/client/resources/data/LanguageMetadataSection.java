/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.data.IMetadataSection;

public class LanguageMetadataSection
implements IMetadataSection {
    private final Collection languages;
    private static final String __OBFID = "CL_00001110";

    public LanguageMetadataSection(Collection par1Collection) {
        this.languages = par1Collection;
    }

    public Collection getLanguages() {
        return this.languages;
    }
}

