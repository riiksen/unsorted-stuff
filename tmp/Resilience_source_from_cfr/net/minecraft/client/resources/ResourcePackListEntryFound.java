/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.resources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackRepository;

public class ResourcePackListEntryFound
extends ResourcePackListEntry {
    private final ResourcePackRepository.Entry field_148319_c;
    private static final String __OBFID = "CL_00000823";

    public ResourcePackListEntryFound(GuiScreenResourcePacks p_i45053_1_, ResourcePackRepository.Entry p_i45053_2_) {
        super(p_i45053_1_);
        this.field_148319_c = p_i45053_2_;
    }

    @Override
    protected void func_148313_c() {
        this.field_148319_c.bindTexturePackIcon(this.field_148317_a.getTextureManager());
    }

    @Override
    protected String func_148311_a() {
        return this.field_148319_c.getTexturePackDescription();
    }

    @Override
    protected String func_148312_b() {
        return this.field_148319_c.getResourcePackName();
    }

    public ResourcePackRepository.Entry func_148318_i() {
        return this.field_148319_c;
    }
}

