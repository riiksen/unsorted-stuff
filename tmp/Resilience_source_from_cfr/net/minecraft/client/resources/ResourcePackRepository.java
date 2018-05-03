/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.commons.io.IOUtils;

public class ResourcePackRepository {
    protected static final FileFilter resourcePackFilter = new FileFilter(){
        private static final String __OBFID = "CL_00001088";

        @Override
        public boolean accept(File par1File) {
            boolean var3;
            boolean var2 = par1File.isFile() && par1File.getName().endsWith(".zip");
            boolean bl = var3 = par1File.isDirectory() && new File(par1File, "pack.mcmeta").isFile();
            if (!var2 && !var3) {
                return false;
            }
            return true;
        }
    };
    private final File dirResourcepacks;
    public final IResourcePack rprDefaultResourcePack;
    private final File field_148534_e;
    public final IMetadataSerializer rprMetadataSerializer;
    private IResourcePack field_148532_f;
    private boolean field_148533_g;
    private List repositoryEntriesAll = Lists.newArrayList();
    private List repositoryEntries = Lists.newArrayList();
    private static final String __OBFID = "CL_00001087";

    public ResourcePackRepository(File p_i45101_1_, File p_i45101_2_, IResourcePack p_i45101_3_, IMetadataSerializer p_i45101_4_, GameSettings p_i45101_5_) {
        this.dirResourcepacks = p_i45101_1_;
        this.field_148534_e = p_i45101_2_;
        this.rprDefaultResourcePack = p_i45101_3_;
        this.rprMetadataSerializer = p_i45101_4_;
        this.fixDirResourcepacks();
        this.updateRepositoryEntriesAll();
        block0 : for (String var7 : p_i45101_5_.resourcePacks) {
            for (Entry var9 : this.repositoryEntriesAll) {
                if (!var9.getResourcePackName().equals(var7)) continue;
                this.repositoryEntries.add(var9);
                continue block0;
            }
        }
    }

    private void fixDirResourcepacks() {
        if (!this.dirResourcepacks.isDirectory()) {
            this.dirResourcepacks.delete();
            this.dirResourcepacks.mkdirs();
        }
    }

    private List getResourcePackFiles() {
        return this.dirResourcepacks.isDirectory() ? Arrays.asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.emptyList();
    }

    public void updateRepositoryEntriesAll() {
        ArrayList var1 = Lists.newArrayList();
        for (File var3 : this.getResourcePackFiles()) {
            Entry var4 = new Entry(var3, null);
            if (!this.repositoryEntriesAll.contains(var4)) {
                try {
                    var4.updateResourcePack();
                    var1.add(var4);
                }
                catch (Exception var6) {
                    var1.remove(var4);
                }
                continue;
            }
            int var5 = this.repositoryEntriesAll.indexOf(var4);
            if (var5 <= -1 || var5 >= this.repositoryEntriesAll.size()) continue;
            var1.add(this.repositoryEntriesAll.get(var5));
        }
        this.repositoryEntriesAll.removeAll(var1);
        for (Entry var7 : this.repositoryEntriesAll) {
            var7.closeResourcePack();
        }
        this.repositoryEntriesAll = var1;
    }

    public List getRepositoryEntriesAll() {
        return ImmutableList.copyOf((Collection)this.repositoryEntriesAll);
    }

    public List getRepositoryEntries() {
        return ImmutableList.copyOf((Collection)this.repositoryEntries);
    }

    public void func_148527_a(List p_148527_1_) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(p_148527_1_);
    }

    public File getDirResourcepacks() {
        return this.dirResourcepacks;
    }

    public void func_148526_a(String p_148526_1_) {
        String var2 = p_148526_1_.substring(p_148526_1_.lastIndexOf("/") + 1);
        if (var2.contains("?")) {
            var2 = var2.substring(0, var2.indexOf("?"));
        }
        if (var2.endsWith(".zip")) {
            File var3 = new File(this.field_148534_e, var2.replaceAll("\\W", ""));
            this.func_148529_f();
            this.func_148528_a(p_148526_1_, var3);
        }
    }

    private void func_148528_a(String p_148528_1_, File p_148528_2_) {
        HashMap var3 = Maps.newHashMap();
        GuiScreenWorking var4 = new GuiScreenWorking();
        var3.put("X-Minecraft-Username", Minecraft.getMinecraft().getSession().getUsername());
        var3.put("X-Minecraft-UUID", Minecraft.getMinecraft().getSession().getPlayerID());
        var3.put("X-Minecraft-Version", "1.7.2");
        this.field_148533_g = true;
        Minecraft.getMinecraft().displayGuiScreen(var4);
        HttpUtil.func_151223_a(p_148528_2_, p_148528_1_, new HttpUtil.DownloadListener(){
            private static final String __OBFID = "CL_00001089";

            @Override
            public void func_148522_a(File p_148522_1_) {
                if (ResourcePackRepository.this.field_148533_g) {
                    ResourcePackRepository.access$1(ResourcePackRepository.this, false);
                    ResourcePackRepository.access$2(ResourcePackRepository.this, new FileResourcePack(p_148522_1_));
                    Minecraft.getMinecraft().scheduleResourcesRefresh();
                }
            }
        }, var3, 52428800, var4, Minecraft.getMinecraft().getProxy());
    }

    public IResourcePack func_148530_e() {
        return this.field_148532_f;
    }

    public void func_148529_f() {
        this.field_148532_f = null;
        this.field_148533_g = false;
    }

    static /* synthetic */ void access$1(ResourcePackRepository resourcePackRepository, boolean bl) {
        resourcePackRepository.field_148533_g = bl;
    }

    static /* synthetic */ void access$2(ResourcePackRepository resourcePackRepository, IResourcePack iResourcePack) {
        resourcePackRepository.field_148532_f = iResourcePack;
    }

    public class Entry {
        private final File resourcePackFile;
        private IResourcePack reResourcePack;
        private PackMetadataSection rePackMetadataSection;
        private BufferedImage texturePackIcon;
        private ResourceLocation locationTexturePackIcon;
        private static final String __OBFID = "CL_00001090";

        private Entry(File par2File) {
            this.resourcePackFile = par2File;
        }

        public void updateResourcePack() throws IOException {
            this.reResourcePack = this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile);
            this.rePackMetadataSection = (PackMetadataSection)this.reResourcePack.getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
            try {
                this.texturePackIcon = this.reResourcePack.getPackImage();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (this.texturePackIcon == null) {
                this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
            }
            this.closeResourcePack();
        }

        public void bindTexturePackIcon(TextureManager par1TextureManager) {
            if (this.locationTexturePackIcon == null) {
                this.locationTexturePackIcon = par1TextureManager.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
            }
            par1TextureManager.bindTexture(this.locationTexturePackIcon);
        }

        public void closeResourcePack() {
            if (this.reResourcePack instanceof Closeable) {
                IOUtils.closeQuietly((Closeable)((Closeable)((Object)this.reResourcePack)));
            }
        }

        public IResourcePack getResourcePack() {
            return this.reResourcePack;
        }

        public String getResourcePackName() {
            return this.reResourcePack.getPackName();
        }

        public String getTexturePackDescription() {
            return this.rePackMetadataSection == null ? (Object)((Object)EnumChatFormatting.RED) + "Invalid pack.mcmeta (or missing 'pack' section)" : this.rePackMetadataSection.getPackDescription();
        }

        public boolean equals(Object par1Obj) {
            return this == par1Obj ? true : (par1Obj instanceof Entry ? this.toString().equals(par1Obj.toString()) : false);
        }

        public int hashCode() {
            return this.toString().hashCode();
        }

        public String toString() {
            Object[] arrobject = new Object[3];
            arrobject[0] = this.resourcePackFile.getName();
            arrobject[1] = this.resourcePackFile.isDirectory() ? "folder" : "zip";
            arrobject[2] = this.resourcePackFile.lastModified();
            return String.format("%s:%s:%d", arrobject);
        }

        Entry(File par2File, Object par3ResourcePackRepositoryFilter) {
            this(par2File);
        }
    }

}

