/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundListSerializer;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler
implements IResourceManagerReloadListener,
IUpdatePlayerListBox {
    private static final Logger logger = LogManager.getLogger();
    private static final Gson field_147699_c = new GsonBuilder().registerTypeAdapter(SoundList.class, (Object)new SoundListSerializer()).create();
    private static final ParameterizedType field_147696_d = new ParameterizedType(){
        private static final String __OBFID = "CL_00001148";

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{String.class, SoundList.class};
        }

        @Override
        public Type getRawType() {
            return Map.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    };
    public static final SoundPoolEntry field_147700_a = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
    private final SoundRegistry field_147697_e = new SoundRegistry();
    private final SoundManager field_147694_f;
    private final IResourceManager field_147695_g;
    private static final String __OBFID = "CL_00001147";

    public SoundHandler(IResourceManager p_i45122_1_, GameSettings p_i45122_2_) {
        this.field_147695_g = p_i45122_1_;
        this.field_147694_f = new SoundManager(this, p_i45122_2_);
    }

    @Override
    public void onResourceManagerReload(IResourceManager par1ResourceManager) {
        this.field_147694_f.func_148596_a();
        this.field_147697_e.func_148763_c();
        for (String var3 : par1ResourceManager.getResourceDomains()) {
            try {
                List var4 = par1ResourceManager.getAllResources(new ResourceLocation(var3, "sounds.json"));
                int var5 = var4.size() - 1;
                while (var5 >= 0) {
                    IResource var6 = (IResource)var4.get(var5);
                    try {
                        Map var7 = (Map)field_147699_c.fromJson((Reader)new InputStreamReader(var6.getInputStream()), (Type)field_147696_d);
                        for (Map.Entry var9 : var7.entrySet()) {
                            this.func_147693_a(new ResourceLocation(var3, (String)var9.getKey()), (SoundList)var9.getValue());
                        }
                    }
                    catch (RuntimeException var10) {
                        logger.warn("Invalid sounds.json", (Throwable)var10);
                    }
                    --var5;
                }
            }
            catch (IOException var4) {
                // empty catch block
            }
        }
    }

    private void func_147693_a(ResourceLocation p_147693_1_, SoundList p_147693_2_) {
        SoundEventAccessorComposite var3;
        if (this.field_147697_e.containsKey(p_147693_1_) && !p_147693_2_.func_148574_b()) {
            var3 = (SoundEventAccessorComposite)this.field_147697_e.getObject(p_147693_1_);
        } else {
            var3 = new SoundEventAccessorComposite(p_147693_1_, 1.0, 1.0, p_147693_2_.func_148573_c());
            this.field_147697_e.func_148762_a(var3);
        }
        block7 : for (SoundList.SoundEntry var5 : p_147693_2_.func_148570_a()) {
            ISoundEventAccessor var9;
            String var6 = var5.func_148556_a();
            ResourceLocation var7 = new ResourceLocation(var6);
            String var8 = var6.contains(":") ? var7.getResourceDomain() : p_147693_1_.getResourceDomain();
            switch (SwitchType.field_148765_a[var5.func_148563_e().ordinal()]) {
                case 1: {
                    ResourceLocation var10 = new ResourceLocation(var8, "sounds/" + var7.getResourcePath() + ".ogg");
                    try {
                        this.field_147695_g.getResource(var10);
                    }
                    catch (FileNotFoundException var12) {
                        logger.warn("File {} does not exist, cannot add it to event {}", new Object[]{var10, p_147693_1_});
                        continue block7;
                    }
                    catch (IOException var13) {
                        logger.warn("Could not load sound file " + var10 + ", cannot add it to event " + p_147693_1_, (Throwable)var13);
                        continue block7;
                    }
                    var9 = new SoundEventAccessor(new SoundPoolEntry(var10, var5.func_148560_c(), var5.func_148558_b(), var5.func_148552_f()), var5.func_148555_d());
                    break;
                }
                case 2: {
                    var9 = new ISoundEventAccessor(var8, var5){
                        final ResourceLocation field_148726_a;
                        private static final String __OBFID = "CL_00001149";
                        {
                            this.field_148726_a = new ResourceLocation(string, soundEntry.func_148556_a());
                        }

                        @Override
                        public int func_148721_a() {
                            SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.this.field_147697_e.getObject(this.field_148726_a);
                            return var1 == null ? 0 : var1.func_148721_a();
                        }

                        @Override
                        public SoundPoolEntry func_148720_g() {
                            SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.this.field_147697_e.getObject(this.field_148726_a);
                            return var1 == null ? SoundHandler.field_147700_a : var1.func_148720_g();
                        }
                    };
                    break;
                }
                default: {
                    throw new IllegalStateException("IN YOU FACE");
                }
            }
            var3.func_148727_a(var9);
        }
    }

    public SoundEventAccessorComposite func_147680_a(ResourceLocation p_147680_1_) {
        return (SoundEventAccessorComposite)this.field_147697_e.getObject(p_147680_1_);
    }

    public void playSound(ISound p_147682_1_) {
        this.field_147694_f.func_148611_c(p_147682_1_);
    }

    public void playDelayedSound(ISound p_147681_1_, int p_147681_2_) {
        this.field_147694_f.func_148599_a(p_147681_1_, p_147681_2_);
    }

    public void func_147691_a(EntityPlayer p_147691_1_, float p_147691_2_) {
        this.field_147694_f.func_148615_a(p_147691_1_, p_147691_2_);
    }

    public void func_147689_b() {
        this.field_147694_f.func_148610_e();
    }

    public void func_147690_c() {
        this.field_147694_f.func_148614_c();
    }

    public void func_147685_d() {
        this.field_147694_f.func_148613_b();
    }

    @Override
    public void update() {
        this.field_147694_f.func_148605_d();
    }

    public void func_147687_e() {
        this.field_147694_f.func_148604_f();
    }

    public void setSoundLevel(SoundCategory p_147684_1_, float p_147684_2_) {
        if (p_147684_1_ == SoundCategory.MASTER && p_147684_2_ <= 0.0f) {
            this.func_147690_c();
        }
        this.field_147694_f.func_148601_a(p_147684_1_, p_147684_2_);
    }

    public void func_147683_b(ISound p_147683_1_) {
        this.field_147694_f.func_148602_b(p_147683_1_);
    }

    public /* varargs */ SoundEventAccessorComposite func_147686_a(SoundCategory ... p_147686_1_) {
        ArrayList var2 = Lists.newArrayList();
        for (ResourceLocation var4 : this.field_147697_e.getKeys()) {
            SoundEventAccessorComposite var5 = (SoundEventAccessorComposite)this.field_147697_e.getObject(var4);
            if (!ArrayUtils.contains((Object[])p_147686_1_, (Object)((Object)var5.func_148728_d()))) continue;
            var2.add(var5);
        }
        if (var2.isEmpty()) {
            return null;
        }
        return (SoundEventAccessorComposite)var2.get(new Random().nextInt(var2.size()));
    }

    public boolean func_147692_c(ISound p_147692_1_) {
        return this.field_147694_f.func_148597_a(p_147692_1_);
    }

    static final class SwitchType {
        static final int[] field_148765_a = new int[SoundList.SoundEntry.Type.values().length];
        private static final String __OBFID = "CL_00001150";

        static {
            try {
                SwitchType.field_148765_a[SoundList.SoundEntry.Type.FILE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchType.field_148765_a[SoundList.SoundEntry.Type.SOUND_EVENT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchType() {
        }
    }

}

