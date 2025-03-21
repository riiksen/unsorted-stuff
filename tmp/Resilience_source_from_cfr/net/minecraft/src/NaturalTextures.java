/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.src.Config;
import net.minecraft.src.NaturalProperties;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class NaturalTextures {
    private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

    public static void update() {
        propertiesByIndex = new NaturalProperties[0];
        if (Config.isNaturalTextures()) {
            String fileName = "optifine/natural.properties";
            try {
                ResourceLocation e = new ResourceLocation(fileName);
                if (!Config.hasResource(e)) {
                    Config.dbg("NaturalTextures: configuration \"" + fileName + "\" not found");
                    propertiesByIndex = NaturalTextures.makeDefaultProperties();
                    return;
                }
                InputStream in = Config.getResourceStream(e);
                ArrayList<NaturalProperties> list = new ArrayList<NaturalProperties>(256);
                String configStr = Config.readInputStream(in);
                in.close();
                String[] configLines = Config.tokenize(configStr, "\n\r");
                Config.dbg("Natural Textures: Parsing configuration \"" + fileName + "\"");
                int i = 0;
                while (i < configLines.length) {
                    String line = configLines[i].trim();
                    if (!line.startsWith("#")) {
                        String[] strs = Config.tokenize(line, "=");
                        if (strs.length != 2) {
                            Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
                        } else {
                            String key = strs[0].trim();
                            String type = strs[1].trim();
                            TextureAtlasSprite ts = TextureMap.textureMapBlocks.getIconSafe(key);
                            if (ts == null) {
                                Config.warn("Natural Textures: Texture not found: \"" + fileName + "\" line: " + line);
                            } else {
                                int tileNum = ts.getIndexInMap();
                                if (tileNum < 0) {
                                    Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
                                } else {
                                    NaturalProperties props = new NaturalProperties(type);
                                    if (props.isValid()) {
                                        while (list.size() <= tileNum) {
                                            list.add(null);
                                        }
                                        list.set(tileNum, props);
                                        Config.dbg("NaturalTextures: " + key + " = " + type);
                                    }
                                }
                            }
                        }
                    }
                    ++i;
                }
                propertiesByIndex = list.toArray(new NaturalProperties[list.size()]);
            }
            catch (FileNotFoundException var15) {
                Config.warn("NaturalTextures: configuration \"" + fileName + "\" not found");
                propertiesByIndex = NaturalTextures.makeDefaultProperties();
                return;
            }
            catch (Exception var16) {
                var16.printStackTrace();
            }
        }
    }

    public static NaturalProperties getNaturalProperties(IIcon icon) {
        if (!(icon instanceof TextureAtlasSprite)) {
            return null;
        }
        TextureAtlasSprite ts = (TextureAtlasSprite)icon;
        int tileNum = ts.getIndexInMap();
        if (tileNum >= 0 && tileNum < propertiesByIndex.length) {
            NaturalProperties props = propertiesByIndex[tileNum];
            return props;
        }
        return null;
    }

    private static NaturalProperties[] makeDefaultProperties() {
        Config.dbg("NaturalTextures: Checking default configuration.");
        ArrayList propsList = new ArrayList();
        NaturalTextures.setIconProperties(propsList, "grass_top", "4F");
        NaturalTextures.setIconProperties(propsList, "stone", "2F");
        NaturalTextures.setIconProperties(propsList, "dirt", "4F");
        NaturalTextures.setIconProperties(propsList, "grass_side", "F");
        NaturalTextures.setIconProperties(propsList, "grass_side_overlay", "F");
        NaturalTextures.setIconProperties(propsList, "stone_slab_top", "F");
        NaturalTextures.setIconProperties(propsList, "bedrock", "2F");
        NaturalTextures.setIconProperties(propsList, "sand", "4F");
        NaturalTextures.setIconProperties(propsList, "gravel", "2");
        NaturalTextures.setIconProperties(propsList, "log_oak", "2F");
        NaturalTextures.setIconProperties(propsList, "log_oak_top", "4F");
        NaturalTextures.setIconProperties(propsList, "gold_ore", "2F");
        NaturalTextures.setIconProperties(propsList, "iron_ore", "2F");
        NaturalTextures.setIconProperties(propsList, "coal_ore", "2F");
        NaturalTextures.setIconProperties(propsList, "diamond_ore", "2F");
        NaturalTextures.setIconProperties(propsList, "redstone_ore", "2F");
        NaturalTextures.setIconProperties(propsList, "lapis_ore", "2F");
        NaturalTextures.setIconProperties(propsList, "obsidian", "4F");
        NaturalTextures.setIconProperties(propsList, "leaves_oak", "2F");
        NaturalTextures.setIconProperties(propsList, "leaves_oak_opaque", "2F");
        NaturalTextures.setIconProperties(propsList, "leaves_jungle", "2");
        NaturalTextures.setIconProperties(propsList, "leaves_jungle_opaque", "2");
        NaturalTextures.setIconProperties(propsList, "snow", "4F");
        NaturalTextures.setIconProperties(propsList, "grass_side_snowed", "F");
        NaturalTextures.setIconProperties(propsList, "cactus_side", "2F");
        NaturalTextures.setIconProperties(propsList, "clay", "4F");
        NaturalTextures.setIconProperties(propsList, "mycelium_side", "F");
        NaturalTextures.setIconProperties(propsList, "mycelium_top", "4F");
        NaturalTextures.setIconProperties(propsList, "farmland_wet", "2F");
        NaturalTextures.setIconProperties(propsList, "farmland_dry", "2F");
        NaturalTextures.setIconProperties(propsList, "netherrack", "4F");
        NaturalTextures.setIconProperties(propsList, "soul_sand", "4F");
        NaturalTextures.setIconProperties(propsList, "glowstone", "4");
        NaturalTextures.setIconProperties(propsList, "log_spruce", "2F");
        NaturalTextures.setIconProperties(propsList, "log_birch", "F");
        NaturalTextures.setIconProperties(propsList, "leaves_spruce", "2F");
        NaturalTextures.setIconProperties(propsList, "leaves_spruce_opaque", "2F");
        NaturalTextures.setIconProperties(propsList, "log_jungle", "2F");
        NaturalTextures.setIconProperties(propsList, "end_stone", "4");
        NaturalTextures.setIconProperties(propsList, "sandstone_top", "4");
        NaturalTextures.setIconProperties(propsList, "sandstone_bottom", "4F");
        NaturalTextures.setIconProperties(propsList, "redstone_lamp_on", "4F");
        NaturalProperties[] terrainProps = propsList.toArray(new NaturalProperties[propsList.size()]);
        return terrainProps;
    }

    private static void setIconProperties(List propsList, String iconName, String propStr) {
        TextureMap terrainMap = TextureMap.textureMapBlocks;
        TextureAtlasSprite icon = terrainMap.getIconSafe(iconName);
        if (icon == null) {
            Config.warn("*** NaturalProperties: Icon not found: " + iconName + " ***");
        } else if (!(icon instanceof TextureAtlasSprite)) {
            Config.warn("*** NaturalProperties: Icon is not IconStitched: " + iconName + ": " + icon.getClass().getName() + " ***");
        } else {
            TextureAtlasSprite ts = icon;
            int index = ts.getIndexInMap();
            if (index < 0) {
                Config.warn("*** NaturalProperties: Invalid index for icon: " + iconName + ": " + index + " ***");
            } else if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + iconName + ".png"))) {
                while (index >= propsList.size()) {
                    propsList.add(null);
                }
                NaturalProperties props = new NaturalProperties(propStr);
                propsList.set(index, props);
                Config.dbg("NaturalTextures: " + iconName + " = " + propStr);
            }
        }
    }
}

