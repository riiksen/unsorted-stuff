/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.src.RandomMobs;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class TextureManager
implements ITickable,
IResourceManagerReloadListener {
    private static final Logger logger = LogManager.getLogger();
    private final Map mapTextureObjects = Maps.newHashMap();
    private final Map mapResourceLocations = Maps.newHashMap();
    private final List listTickables = Lists.newArrayList();
    private final Map mapTextureCounters = Maps.newHashMap();
    private IResourceManager theResourceManager;
    private static final String __OBFID = "CL_00001064";

    public TextureManager(IResourceManager par1ResourceManager) {
        this.theResourceManager = par1ResourceManager;
    }

    public void bindTexture(ResourceLocation par1ResourceLocation) {
        ITextureObject var2;
        if (Config.isRandomMobs()) {
            par1ResourceLocation = RandomMobs.getTextureLocation(par1ResourceLocation);
        }
        if ((var2 = (ITextureObject)this.mapTextureObjects.get(par1ResourceLocation)) == null) {
            var2 = new SimpleTexture(par1ResourceLocation);
            this.loadTexture(par1ResourceLocation, var2);
        }
        TextureUtil.bindTexture(var2.getGlTextureId());
    }

    public ResourceLocation getResourceLocation(int par1) {
        return (ResourceLocation)this.mapResourceLocations.get(par1);
    }

    public boolean loadTextureMap(ResourceLocation par1ResourceLocation, TextureMap par2TextureMap) {
        if (this.loadTickableTexture(par1ResourceLocation, par2TextureMap)) {
            this.mapResourceLocations.put(par2TextureMap.getTextureType(), par1ResourceLocation);
            return true;
        }
        return false;
    }

    public boolean loadTickableTexture(ResourceLocation par1ResourceLocation, ITickableTextureObject par2TickableTextureObject) {
        if (this.loadTexture(par1ResourceLocation, par2TickableTextureObject)) {
            this.listTickables.add(par2TickableTextureObject);
            return true;
        }
        return false;
    }

    public boolean loadTexture(ResourceLocation par1ResourceLocation, final ITextureObject par2TextureObject) {
        boolean var3 = true;
        ITextureObject par2TextureObject2 = par2TextureObject;
        try {
            par2TextureObject.loadTexture(this.theResourceManager);
        }
        catch (IOException var8) {
            logger.warn("Failed to load texture: " + par1ResourceLocation, (Throwable)var8);
            par2TextureObject2 = TextureUtil.missingTexture;
            this.mapTextureObjects.put(par1ResourceLocation, par2TextureObject2);
            var3 = false;
        }
        catch (Throwable var9) {
            CrashReport var5 = CrashReport.makeCrashReport(var9, "Registering texture");
            CrashReportCategory var6 = var5.makeCategory("Resource location being registered");
            var6.addCrashSection("Resource location", par1ResourceLocation);
            var6.addCrashSectionCallable("Texture object class", new Callable(){
                private static final String __OBFID = "CL_00001065";

                public String call() {
                    return par2TextureObject.getClass().getName();
                }
            });
            throw new ReportedException(var5);
        }
        this.mapTextureObjects.put(par1ResourceLocation, par2TextureObject2);
        return var3;
    }

    public ITextureObject getTexture(ResourceLocation par1ResourceLocation) {
        return (ITextureObject)this.mapTextureObjects.get(par1ResourceLocation);
    }

    public ResourceLocation getDynamicTextureLocation(String par1Str, DynamicTexture par2DynamicTexture) {
        Integer var3 = (Integer)this.mapTextureCounters.get(par1Str);
        var3 = var3 == null ? Integer.valueOf(1) : Integer.valueOf(var3 + 1);
        this.mapTextureCounters.put(par1Str, var3);
        ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", par1Str, var3));
        this.loadTexture(var4, par2DynamicTexture);
        return var4;
    }

    @Override
    public void tick() {
        for (ITickable var2 : this.listTickables) {
            var2.tick();
        }
    }

    public void func_147645_c(ResourceLocation p_147645_1_) {
        ITextureObject var2 = this.getTexture(p_147645_1_);
        if (var2 != null) {
            TextureUtil.deleteTexture(var2.getGlTextureId());
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager par1ResourceManager) {
        Config.dbg("*** Reloading textures ***");
        Config.log("Resource packs: " + Config.getResourcePackNames());
        Iterator it = this.mapTextureObjects.keySet().iterator();
        while (it.hasNext()) {
            ResourceLocation var2 = (ResourceLocation)it.next();
            if (!var2.getResourcePath().startsWith("mcpatcher/")) continue;
            ITextureObject var3 = (ITextureObject)this.mapTextureObjects.get(var2);
            int glTexId = var3.getGlTextureId();
            if (glTexId > 0) {
                GL11.glDeleteTextures((int)glTexId);
            }
            it.remove();
        }
        for (Map.Entry var31 : this.mapTextureObjects.entrySet()) {
            this.loadTexture((ResourceLocation)var31.getKey(), (ITextureObject)var31.getValue());
        }
    }

}

