/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleResource;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class FallbackResourceManager
implements IResourceManager {
    protected final List resourcePacks = new ArrayList();
    private final IMetadataSerializer frmMetadataSerializer;
    private static final String __OBFID = "CL_00001074";

    public FallbackResourceManager(IMetadataSerializer par1MetadataSerializer) {
        this.frmMetadataSerializer = par1MetadataSerializer;
    }

    public void addResourcePack(IResourcePack par1ResourcePack) {
        this.resourcePacks.add(par1ResourcePack);
    }

    @Override
    public Set getResourceDomains() {
        return null;
    }

    @Override
    public IResource getResource(ResourceLocation par1ResourceLocation) throws IOException {
        IResourcePack var2 = null;
        ResourceLocation var3 = FallbackResourceManager.getLocationMcmeta(par1ResourceLocation);
        int var4 = this.resourcePacks.size() - 1;
        while (var4 >= 0) {
            IResourcePack var5 = (IResourcePack)this.resourcePacks.get(var4);
            if (var2 == null && var5.resourceExists(var3)) {
                var2 = var5;
            }
            if (var5.resourceExists(par1ResourceLocation)) {
                InputStream var6 = null;
                if (var2 != null) {
                    var6 = var2.getInputStream(var3);
                }
                return new SimpleResource(par1ResourceLocation, var5.getInputStream(par1ResourceLocation), var6, this.frmMetadataSerializer);
            }
            --var4;
        }
        throw new FileNotFoundException(par1ResourceLocation.toString());
    }

    @Override
    public List getAllResources(ResourceLocation par1ResourceLocation) throws IOException {
        ArrayList var2 = Lists.newArrayList();
        ResourceLocation var3 = FallbackResourceManager.getLocationMcmeta(par1ResourceLocation);
        for (IResourcePack var5 : this.resourcePacks) {
            if (!var5.resourceExists(par1ResourceLocation)) continue;
            InputStream var6 = var5.resourceExists(var3) ? var5.getInputStream(var3) : null;
            var2.add(new SimpleResource(par1ResourceLocation, var5.getInputStream(par1ResourceLocation), var6, this.frmMetadataSerializer));
        }
        if (var2.isEmpty()) {
            throw new FileNotFoundException(par1ResourceLocation.toString());
        }
        return var2;
    }

    static ResourceLocation getLocationMcmeta(ResourceLocation par0ResourceLocation) {
        return new ResourceLocation(par0ResourceLocation.getResourceDomain(), String.valueOf(par0ResourceLocation.getResourcePath()) + ".mcmeta");
    }
}

