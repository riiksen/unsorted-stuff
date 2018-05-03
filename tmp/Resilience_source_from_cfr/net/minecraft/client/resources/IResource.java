/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.resources;

import java.io.InputStream;
import net.minecraft.client.resources.data.IMetadataSection;

public interface IResource {
    public InputStream getInputStream();

    public boolean hasMetadata();

    public IMetadataSection getMetadata(String var1);
}

