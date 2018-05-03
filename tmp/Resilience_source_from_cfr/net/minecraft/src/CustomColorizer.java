/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;

public class CustomColorizer {
    private static int[] grassColors = null;
    private static int[] waterColors = null;
    private static int[] foliageColors = null;
    private static int[] foliagePineColors = null;
    private static int[] foliageBirchColors = null;
    private static int[] swampFoliageColors = null;
    private static int[] swampGrassColors = null;
    private static int[][] blockPalettes = null;
    private static int[][] paletteColors = null;
    private static int[] skyColors = null;
    private static int[] fogColors = null;
    private static int[] underwaterColors = null;
    private static float[][][] lightMapsColorsRgb = null;
    private static int[] lightMapsHeight = null;
    private static float[][] sunRgbs = new float[16][3];
    private static float[][] torchRgbs = new float[16][3];
    private static int[] redstoneColors = null;
    private static int[] stemColors = null;
    private static int[] myceliumParticleColors = null;
    private static boolean useDefaultColorMultiplier = true;
    private static int particleWaterColor = -1;
    private static int particlePortalColor = -1;
    private static int lilyPadColor = -1;
    private static Vec3 fogColorNether = null;
    private static Vec3 fogColorEnd = null;
    private static Vec3 skyColorEnd = null;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    private static Random random = new Random();

    public static void update() {
        grassColors = null;
        waterColors = null;
        foliageColors = null;
        foliageBirchColors = null;
        foliagePineColors = null;
        swampGrassColors = null;
        swampFoliageColors = null;
        skyColors = null;
        fogColors = null;
        underwaterColors = null;
        redstoneColors = null;
        stemColors = null;
        myceliumParticleColors = null;
        lightMapsColorsRgb = null;
        lightMapsHeight = null;
        lilyPadColor = -1;
        particleWaterColor = -1;
        particlePortalColor = -1;
        fogColorNether = null;
        fogColorEnd = null;
        skyColorEnd = null;
        blockPalettes = null;
        paletteColors = null;
        useDefaultColorMultiplier = true;
        String mcpColormap = "mcpatcher/colormap/";
        grassColors = CustomColorizer.getCustomColors("textures/colormap/grass.png", 65536);
        foliageColors = CustomColorizer.getCustomColors("textures/colormap/foliage.png", 65536);
        String[] waterPaths = new String[]{"water.png", "watercolorX.png"};
        waterColors = CustomColorizer.getCustomColors(mcpColormap, waterPaths, 65536);
        if (Config.isCustomColors()) {
            String[] pinePaths = new String[]{"pine.png", "pinecolor.png"};
            foliagePineColors = CustomColorizer.getCustomColors(mcpColormap, pinePaths, 65536);
            String[] birchPaths = new String[]{"birch.png", "birchcolor.png"};
            foliageBirchColors = CustomColorizer.getCustomColors(mcpColormap, birchPaths, 65536);
            String[] swampGrassPaths = new String[]{"swampgrass.png", "swampgrasscolor.png"};
            swampGrassColors = CustomColorizer.getCustomColors(mcpColormap, swampGrassPaths, 65536);
            String[] swampFoliagePaths = new String[]{"swampfoliage.png", "swampfoliagecolor.png"};
            swampFoliageColors = CustomColorizer.getCustomColors(mcpColormap, swampFoliagePaths, 65536);
            String[] sky0Paths = new String[]{"sky0.png", "skycolor0.png"};
            skyColors = CustomColorizer.getCustomColors(mcpColormap, sky0Paths, 65536);
            String[] fog0Paths = new String[]{"fog0.png", "fogcolor0.png"};
            fogColors = CustomColorizer.getCustomColors(mcpColormap, fog0Paths, 65536);
            String[] underwaterPaths = new String[]{"underwater.png", "underwatercolor.png"};
            underwaterColors = CustomColorizer.getCustomColors(mcpColormap, underwaterPaths, 65536);
            String[] redstonePaths = new String[]{"redstone.png", "redstonecolor.png"};
            redstoneColors = CustomColorizer.getCustomColors(mcpColormap, redstonePaths, 16);
            String[] stemPaths = new String[]{"stem.png", "stemcolor.png"};
            stemColors = CustomColorizer.getCustomColors(mcpColormap, stemPaths, 8);
            String[] myceliumPaths = new String[]{"myceliumparticle.png", "myceliumparticlecolor.png"};
            myceliumParticleColors = CustomColorizer.getCustomColors(mcpColormap, myceliumPaths, -1);
            int[][] lightMapsColors = new int[3][];
            lightMapsColorsRgb = new float[3][][];
            lightMapsHeight = new int[3];
            int i = 0;
            while (i < lightMapsColors.length) {
                String path = "mcpatcher/lightmap/world" + (i - 1) + ".png";
                lightMapsColors[i] = CustomColorizer.getCustomColors(path, -1);
                if (lightMapsColors[i] != null) {
                    CustomColorizer.lightMapsColorsRgb[i] = CustomColorizer.toRgb(lightMapsColors[i]);
                }
                CustomColorizer.lightMapsHeight[i] = CustomColorizer.getTextureHeight(path, 32);
                ++i;
            }
            CustomColorizer.readColorProperties("mcpatcher/color.properties");
            CustomColorizer.updateUseDefaultColorMultiplier();
        }
    }

    private static int getTextureHeight(String path, int defHeight) {
        try {
            InputStream e = Config.getResourceStream(new ResourceLocation(path));
            if (e == null) {
                return defHeight;
            }
            BufferedImage bi = ImageIO.read(e);
            return bi == null ? defHeight : bi.getHeight();
        }
        catch (IOException var4) {
            return defHeight;
        }
    }

    private static float[][] toRgb(int[] cols) {
        float[][] colsRgb = new float[cols.length][3];
        int i = 0;
        while (i < cols.length) {
            int col = cols[i];
            float rf = (float)(col >> 16 & 255) / 255.0f;
            float gf = (float)(col >> 8 & 255) / 255.0f;
            float bf = (float)(col & 255) / 255.0f;
            float[] colRgb = colsRgb[i];
            colRgb[0] = rf;
            colRgb[1] = gf;
            colRgb[2] = bf;
            ++i;
        }
        return colsRgb;
    }

    private static void readColorProperties(String fileName) {
        try {
            ResourceLocation e = new ResourceLocation(fileName);
            InputStream in = Config.getResourceStream(e);
            if (in == null) {
                return;
            }
            Config.log("Loading " + fileName);
            Properties props = new Properties();
            props.load(in);
            lilyPadColor = CustomColorizer.readColor(props, "lilypad");
            particleWaterColor = CustomColorizer.readColor(props, new String[]{"particle.water", "drop.water"});
            particlePortalColor = CustomColorizer.readColor(props, "particle.portal");
            fogColorNether = CustomColorizer.readColorVec3(props, "fog.nether");
            fogColorEnd = CustomColorizer.readColorVec3(props, "fog.end");
            skyColorEnd = CustomColorizer.readColorVec3(props, "sky.end");
            CustomColorizer.readCustomPalettes(props, fileName);
        }
        catch (FileNotFoundException var4) {
            return;
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void readCustomPalettes(Properties props, String fileName) {
        CustomColorizer.blockPalettes = new int[256][1];
        palettePrefix = 0;
        while (palettePrefix < 256) {
            CustomColorizer.blockPalettes[palettePrefix][0] = -1;
            ++palettePrefix;
        }
        var18 = "palette.block.";
        map = new HashMap<String, String>();
        keys = props.keySet();
        for (String i : keys) {
            name = props.getProperty(i);
            if (!i.startsWith(var18)) continue;
            map.put(i, name);
        }
        var19 = map.keySet().toArray(new String[map.size()]);
        CustomColorizer.paletteColors = new int[var19.length][];
        var20 = 0;
        while (var20 < var19.length) {
            name = var19[var20];
            value = props.getProperty(name);
            Config.log("Block palette: " + name + " = " + value);
            path = name.substring(var18.length());
            basePath = TextureUtils.getBasePath(fileName);
            path = TextureUtils.fixResourcePath(path, basePath);
            colors = CustomColorizer.getCustomColors(path, 65536);
            CustomColorizer.paletteColors[var20] = colors;
            indexStrs = Config.tokenize(value, " ,;");
            ix = 0;
            while (ix < indexStrs.length) {
                blockStr = indexStrs[ix];
                metadata = -1;
                if (!blockStr.contains(":")) ** GOTO lbl-1000
                blockIndex = Config.tokenize(blockStr, ":");
                blockStr = blockIndex[0];
                metadataStr = blockIndex[1];
                metadata = Config.parseInt(metadataStr, -1);
                if (metadata < 0 || metadata > 15) {
                    Config.log("Invalid block metadata: " + blockStr + " in palette: " + name);
                } else if ((var21 = Config.parseInt(blockStr, -1)) >= 0 && var21 <= 255) {
                    if (var21 != Block.getIdFromBlock(Blocks.grass) && var21 != Block.getIdFromBlock(Blocks.tallgrass) && var21 != Block.getIdFromBlock(Blocks.leaves) && var21 != Block.getIdFromBlock(Blocks.vine)) {
                        if (metadata == -1) {
                            CustomColorizer.blockPalettes[var21][0] = var20;
                        } else {
                            if (CustomColorizer.blockPalettes[var21].length < 16) {
                                CustomColorizer.blockPalettes[var21] = new int[16];
                                Arrays.fill(CustomColorizer.blockPalettes[var21], -1);
                            }
                            CustomColorizer.blockPalettes[var21][metadata] = var20;
                        }
                    }
                } else {
                    Config.log("Invalid block index: " + var21 + " in palette: " + name);
                }
                ++ix;
            }
            ++var20;
        }
    }

    private static int readColor(Properties props, String[] names) {
        int i = 0;
        while (i < names.length) {
            String name = names[i];
            int col = CustomColorizer.readColor(props, name);
            if (col >= 0) {
                return col;
            }
            ++i;
        }
        return -1;
    }

    private static int readColor(Properties props, String name) {
        String str = props.getProperty(name);
        if (str == null) {
            return -1;
        }
        try {
            int e = Integer.parseInt(str, 16) & 16777215;
            Config.log("Custom color: " + name + " = " + str);
            return e;
        }
        catch (NumberFormatException var4) {
            Config.log("Invalid custom color: " + name + " = " + str);
            return -1;
        }
    }

    private static Vec3 readColorVec3(Properties props, String name) {
        int col = CustomColorizer.readColor(props, name);
        if (col < 0) {
            return null;
        }
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        return Vec3.createVectorHelper(redF, greenF, blueF);
    }

    private static int[] getCustomColors(String basePath, String[] paths, int length) {
        int i = 0;
        while (i < paths.length) {
            String path = paths[i];
            path = String.valueOf(basePath) + path;
            int[] cols = CustomColorizer.getCustomColors(path, length);
            if (cols != null) {
                return cols;
            }
            ++i;
        }
        return null;
    }

    private static int[] getCustomColors(String path, int length) {
        int[] colors;
        block8 : {
            block7 : {
                ResourceLocation e;
                block6 : {
                    e = new ResourceLocation(path);
                    InputStream in = Config.getResourceStream(e);
                    if (in != null) break block6;
                    return null;
                }
                colors = TextureUtil.readImageData(Config.getResourceManager(), e);
                if (colors != null) break block7;
                return null;
            }
            if (length <= 0 || colors.length == length) break block8;
            Config.log("Invalid custom colors length: " + colors.length + ", path: " + path);
            return null;
        }
        try {
            Config.log("Loading custom colors: " + path);
            return colors;
        }
        catch (FileNotFoundException var5) {
            return null;
        }
        catch (IOException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static void updateUseDefaultColorMultiplier() {
        useDefaultColorMultiplier = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes();
    }

    public static int getColorMultiplier(Block block, IBlockAccess blockAccess, int x, int y, int z) {
        int metadata;
        if (useDefaultColorMultiplier) {
            return block.colorMultiplier(blockAccess, x, y, z);
        }
        int[] colors = null;
        int[] swampColors = null;
        if (blockPalettes != null) {
            int useSwampColors = Block.getIdFromBlock(block);
            if (useSwampColors >= 0 && useSwampColors < 256) {
                int type1;
                int[] smoothColors = blockPalettes[useSwampColors];
                boolean type = true;
                if (smoothColors.length > 1) {
                    metadata = blockAccess.getBlockMetadata(x, y, z);
                    type1 = smoothColors[metadata];
                } else {
                    type1 = smoothColors[0];
                }
                if (type1 >= 0) {
                    colors = paletteColors[type1];
                }
            }
            if (colors != null) {
                if (Config.isSmoothBiomes()) {
                    return CustomColorizer.getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, colors, 0, 0);
                }
                return CustomColorizer.getCustomColor(colors, blockAccess, x, y, z);
            }
        }
        boolean useSwampColors1 = Config.isSwampColors();
        boolean smoothColors1 = false;
        int type2 = 0;
        metadata = 0;
        if (block != Blocks.grass && block != Blocks.tallgrass) {
            if (block == Blocks.leaves) {
                type2 = 2;
                smoothColors1 = Config.isSmoothBiomes();
                metadata = blockAccess.getBlockMetadata(x, y, z);
                if ((metadata & 3) == 1) {
                    colors = foliagePineColors;
                } else if ((metadata & 3) == 2) {
                    colors = foliageBirchColors;
                } else {
                    colors = foliageColors;
                    swampColors = useSwampColors1 ? swampFoliageColors : colors;
                }
            } else if (block == Blocks.vine) {
                type2 = 2;
                smoothColors1 = Config.isSmoothBiomes();
                colors = foliageColors;
                swampColors = useSwampColors1 ? swampFoliageColors : colors;
            }
        } else {
            type2 = 1;
            smoothColors1 = Config.isSmoothBiomes();
            colors = grassColors;
            swampColors = useSwampColors1 ? swampGrassColors : colors;
        }
        if (smoothColors1) {
            return CustomColorizer.getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, swampColors, type2, metadata);
        }
        if (swampColors != colors && blockAccess.getBiomeGenForCoords(x, z) == BiomeGenBase.swampland) {
            colors = swampColors;
        }
        return colors != null ? CustomColorizer.getCustomColor(colors, blockAccess, x, y, z) : block.colorMultiplier(blockAccess, x, y, z);
    }

    private static int getSmoothColorMultiplier(Block block, IBlockAccess blockAccess, int x, int y, int z, int[] colors, int[] swampColors, int type, int metadata) {
        int g;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int r = x - 1;
        while (r <= x + 1) {
            g = z - 1;
            while (g <= z + 1) {
                int var17;
                int[] b = colors;
                if (swampColors != colors && blockAccess.getBiomeGenForCoords(r, g) == BiomeGenBase.swampland) {
                    b = swampColors;
                }
                boolean col = false;
                if (b == null) {
                    switch (type) {
                        case 1: {
                            var17 = blockAccess.getBiomeGenForCoords(r, g).getBiomeGrassColor(x, y, z);
                            break;
                        }
                        case 2: {
                            if ((metadata & 3) == 1) {
                                var17 = ColorizerFoliage.getFoliageColorPine();
                                break;
                            }
                            if ((metadata & 3) == 2) {
                                var17 = ColorizerFoliage.getFoliageColorBirch();
                                break;
                            }
                            var17 = blockAccess.getBiomeGenForCoords(r, g).getBiomeFoliageColor(x, y, z);
                            break;
                        }
                        default: {
                            var17 = block.colorMultiplier(blockAccess, r, y, g);
                            break;
                        }
                    }
                } else {
                    var17 = CustomColorizer.getCustomColor(b, blockAccess, r, y, g);
                }
                sumRed += var17 >> 16 & 255;
                sumGreen += var17 >> 8 & 255;
                sumBlue += var17 & 255;
                ++g;
            }
            ++r;
        }
        r = sumRed / 9;
        g = sumGreen / 9;
        int var16 = sumBlue / 9;
        return r << 16 | g << 8 | var16;
    }

    public static int getFluidColor(Block block, IBlockAccess blockAccess, int x, int y, int z) {
        return block.getMaterial() != Material.water ? block.colorMultiplier(blockAccess, x, y, z) : (waterColors != null ? (Config.isSmoothBiomes() ? CustomColorizer.getSmoothColor(waterColors, blockAccess, x, y, z, 3, 1) : CustomColorizer.getCustomColor(waterColors, blockAccess, x, y, z)) : (!Config.isSwampColors() ? 16777215 : block.colorMultiplier(blockAccess, x, y, z)));
    }

    private static int getCustomColor(int[] colors, IBlockAccess blockAccess, int x, int y, int z) {
        BiomeGenBase bgb = blockAccess.getBiomeGenForCoords(x, z);
        double temperature = MathHelper.clamp_float(bgb.getFloatTemperature(x, y, z), 0.0f, 1.0f);
        double rainfall = MathHelper.clamp_float(bgb.getFloatRainfall(), 0.0f, 1.0f);
        int cx = (int)((1.0 - temperature) * 255.0);
        int cy = (int)((1.0 - (rainfall *= temperature)) * 255.0);
        return colors[cy << 8 | cx] & 16777215;
    }

    public static void updatePortalFX(EntityFX fx) {
        if (particlePortalColor >= 0) {
            int col = particlePortalColor;
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    public static void updateMyceliumFX(EntityFX fx) {
        if (myceliumParticleColors != null) {
            int col = myceliumParticleColors[random.nextInt(myceliumParticleColors.length)];
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    public static void updateReddustFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z) {
        int level;
        int col;
        if (redstoneColors != null && (col = CustomColorizer.getRedstoneColor(level = blockAccess.getBlockMetadata((int)x, (int)y, (int)z))) != -1) {
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    public static int getRedstoneColor(int level) {
        return redstoneColors == null ? -1 : (level >= 0 && level <= 15 ? redstoneColors[level] & 16777215 : -1);
    }

    public static void updateWaterFX(EntityFX fx, IBlockAccess blockAccess) {
        if (waterColors != null) {
            int x = (int)fx.posX;
            int y = (int)fx.posY;
            int z = (int)fx.posZ;
            int col = CustomColorizer.getFluidColor(Blocks.water, blockAccess, x, y, z);
            int red = col >> 16 & 255;
            int green = col >> 8 & 255;
            int blue = col & 255;
            float redF = (float)red / 255.0f;
            float greenF = (float)green / 255.0f;
            float blueF = (float)blue / 255.0f;
            if (particleWaterColor >= 0) {
                int redDrop = particleWaterColor >> 16 & 255;
                int greenDrop = particleWaterColor >> 8 & 255;
                int blueDrop = particleWaterColor & 255;
                redF *= (float)redDrop / 255.0f;
                greenF *= (float)greenDrop / 255.0f;
                blueF *= (float)blueDrop / 255.0f;
            }
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }

    public static int getLilypadColor() {
        return lilyPadColor < 0 ? Blocks.waterlily.getBlockColor() : lilyPadColor;
    }

    public static Vec3 getFogColorNether(Vec3 col) {
        return fogColorNether == null ? col : fogColorNether;
    }

    public static Vec3 getFogColorEnd(Vec3 col) {
        return fogColorEnd == null ? col : fogColorEnd;
    }

    public static Vec3 getSkyColorEnd(Vec3 col) {
        return skyColorEnd == null ? col : skyColorEnd;
    }

    public static Vec3 getSkyColor(Vec3 skyColor3d, IBlockAccess blockAccess, double x, double y, double z) {
        if (skyColors == null) {
            return skyColor3d;
        }
        int col = CustomColorizer.getSmoothColor(skyColors, blockAccess, x, y, z, 10, 1);
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        float cRed = (float)skyColor3d.xCoord / 0.5f;
        float cGreen = (float)skyColor3d.yCoord / 0.66275f;
        float cBlue = (float)skyColor3d.zCoord;
        return Vec3.createVectorHelper(redF *= cRed, greenF *= cGreen, blueF *= cBlue);
    }

    public static Vec3 getFogColor(Vec3 fogColor3d, IBlockAccess blockAccess, double x, double y, double z) {
        if (fogColors == null) {
            return fogColor3d;
        }
        int col = CustomColorizer.getSmoothColor(fogColors, blockAccess, x, y, z, 10, 1);
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        float cRed = (float)fogColor3d.xCoord / 0.753f;
        float cGreen = (float)fogColor3d.yCoord / 0.8471f;
        float cBlue = (float)fogColor3d.zCoord;
        return Vec3.createVectorHelper(redF *= cRed, greenF *= cGreen, blueF *= cBlue);
    }

    public static Vec3 getUnderwaterColor(IBlockAccess blockAccess, double x, double y, double z) {
        if (underwaterColors == null) {
            return null;
        }
        int col = CustomColorizer.getSmoothColor(underwaterColors, blockAccess, x, y, z, 10, 1);
        int red = col >> 16 & 255;
        int green = col >> 8 & 255;
        int blue = col & 255;
        float redF = (float)red / 255.0f;
        float greenF = (float)green / 255.0f;
        float blueF = (float)blue / 255.0f;
        return Vec3.createVectorHelper(redF, greenF, blueF);
    }

    public static int getSmoothColor(int[] colors, IBlockAccess blockAccess, double x, double y, double z, int samples, int step) {
        int g;
        int b;
        if (colors == null) {
            return -1;
        }
        int x0 = (int)Math.floor(x);
        int y0 = (int)Math.floor(y);
        int z0 = (int)Math.floor(z);
        int n = samples * step / 2;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = 0;
        int r = x0 - n;
        while (r <= x0 + n) {
            g = z0 - n;
            while (g <= z0 + n) {
                b = CustomColorizer.getCustomColor(colors, blockAccess, r, y0, g);
                sumRed += b >> 16 & 255;
                sumGreen += b >> 8 & 255;
                sumBlue += b & 255;
                ++count;
                g += step;
            }
            r += step;
        }
        r = sumRed / count;
        g = sumGreen / count;
        b = sumBlue / count;
        return r << 16 | g << 8 | b;
    }

    public static int mixColors(int c1, int c2, float w1) {
        if (w1 <= 0.0f) {
            return c2;
        }
        if (w1 >= 1.0f) {
            return c1;
        }
        float w2 = 1.0f - w1;
        int r1 = c1 >> 16 & 255;
        int g1 = c1 >> 8 & 255;
        int b1 = c1 & 255;
        int r2 = c2 >> 16 & 255;
        int g2 = c2 >> 8 & 255;
        int b2 = c2 & 255;
        int r = (int)((float)r1 * w1 + (float)r2 * w2);
        int g = (int)((float)g1 * w1 + (float)g2 * w2);
        int b = (int)((float)b1 * w1 + (float)b2 * w2);
        return r << 16 | g << 8 | b;
    }

    private static int averageColor(int c1, int c2) {
        int r1 = c1 >> 16 & 255;
        int g1 = c1 >> 8 & 255;
        int b1 = c1 & 255;
        int r2 = c2 >> 16 & 255;
        int g2 = c2 >> 8 & 255;
        int b2 = c2 & 255;
        int r = (r1 + r2) / 2;
        int g = (g1 + g2) / 2;
        int b = (b1 + b2) / 2;
        return r << 16 | g << 8 | b;
    }

    public static int getStemColorMultiplier(BlockStem blockStem, IBlockAccess blockAccess, int x, int y, int z) {
        if (stemColors == null) {
            return blockStem.colorMultiplier(blockAccess, x, y, z);
        }
        int level = blockAccess.getBlockMetadata(x, y, z);
        if (level < 0) {
            level = 0;
        }
        if (level >= stemColors.length) {
            level = stemColors.length - 1;
        }
        return stemColors[level];
    }

    public static boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision) {
        if (world == null) {
            return false;
        }
        if (lightMapsColorsRgb == null) {
            return false;
        }
        if (!Config.isCustomColors()) {
            return false;
        }
        int worldType = world.provider.dimensionId;
        if (worldType >= -1 && worldType <= 1) {
            int lightMapIndex = worldType + 1;
            float[][] lightMapRgb = lightMapsColorsRgb[lightMapIndex];
            if (lightMapRgb == null) {
                return false;
            }
            int height = lightMapsHeight[lightMapIndex];
            if (nightvision && height < 64) {
                return false;
            }
            int width = lightMapRgb.length / height;
            if (width < 16) {
                Config.warn("Invalid lightmap width: " + width + " for: /environment/lightmap" + worldType + ".png");
                CustomColorizer.lightMapsColorsRgb[lightMapIndex] = null;
                return false;
            }
            int startIndex = 0;
            if (nightvision) {
                startIndex = width * 16 * 2;
            }
            float sun = 1.1666666f * (world.getSunBrightness(1.0f) - 0.2f);
            if (world.lastLightningBolt > 0) {
                sun = 1.0f;
            }
            sun = Config.limitTo1(sun);
            float sunX = sun * (float)(width - 1);
            float torchX = Config.limitTo1(torchFlickerX + 0.5f) * (float)(width - 1);
            float gamma = Config.limitTo1(Config.getGameSettings().gammaSetting);
            boolean hasGamma = gamma > 1.0E-4f;
            CustomColorizer.getLightMapColumn(lightMapRgb, sunX, startIndex, width, sunRgbs);
            CustomColorizer.getLightMapColumn(lightMapRgb, torchX, startIndex + 16 * width, width, torchRgbs);
            float[] rgb = new float[3];
            int is = 0;
            while (is < 16) {
                int it = 0;
                while (it < 16) {
                    int r = 0;
                    while (r < 3) {
                        float g = Config.limitTo1(sunRgbs[is][r] + torchRgbs[it][r]);
                        if (hasGamma) {
                            float b = 1.0f - g;
                            b = 1.0f - b * b * b * b;
                            g = gamma * b + (1.0f - gamma) * g;
                        }
                        rgb[r] = g;
                        ++r;
                    }
                    r = (int)(rgb[0] * 255.0f);
                    int var22 = (int)(rgb[1] * 255.0f);
                    int var21 = (int)(rgb[2] * 255.0f);
                    lmColors[is * 16 + it] = -16777216 | r << 16 | var22 << 8 | var21;
                    ++it;
                }
                ++is;
            }
            return true;
        }
        return false;
    }

    private static void getLightMapColumn(float[][] origMap, float x, int offset, int width, float[][] colRgb) {
        int xHigh;
        int xLow = (int)Math.floor(x);
        if (xLow == (xHigh = (int)Math.ceil(x))) {
            int var14 = 0;
            while (var14 < 16) {
                float[] var15 = origMap[offset + var14 * width + xLow];
                float[] var16 = colRgb[var14];
                int var17 = 0;
                while (var17 < 3) {
                    var16[var17] = var15[var17];
                    ++var17;
                }
                ++var14;
            }
        } else {
            float dLow = 1.0f - (x - (float)xLow);
            float dHigh = 1.0f - ((float)xHigh - x);
            int y = 0;
            while (y < 16) {
                float[] rgbLow = origMap[offset + y * width + xLow];
                float[] rgbHigh = origMap[offset + y * width + xHigh];
                float[] rgb = colRgb[y];
                int i = 0;
                while (i < 3) {
                    rgb[i] = rgbLow[i] * dLow + rgbHigh[i] * dHigh;
                    ++i;
                }
                ++y;
            }
        }
    }

    public static Vec3 getWorldFogColor(Vec3 fogVec, WorldClient world, float partialTicks) {
        int worldType = world.provider.dimensionId;
        switch (worldType) {
            case -1: {
                fogVec = CustomColorizer.getFogColorNether(fogVec);
                break;
            }
            case 0: {
                Minecraft mc = Minecraft.getMinecraft();
                fogVec = CustomColorizer.getFogColor(fogVec, mc.theWorld, mc.renderViewEntity.posX, mc.renderViewEntity.posY + 1.0, mc.renderViewEntity.posZ);
                break;
            }
            case 1: {
                fogVec = CustomColorizer.getFogColorEnd(fogVec);
            }
        }
        return fogVec;
    }

    public static Vec3 getWorldSkyColor(Vec3 skyVec, WorldClient world, EntityLivingBase renderViewEntity, float partialTicks) {
        int worldType = world.provider.dimensionId;
        switch (worldType) {
            case 0: {
                Minecraft mc = Minecraft.getMinecraft();
                skyVec = CustomColorizer.getSkyColor(skyVec, mc.theWorld, mc.renderViewEntity.posX, mc.renderViewEntity.posY + 1.0, mc.renderViewEntity.posZ);
                break;
            }
            case 1: {
                skyVec = CustomColorizer.getSkyColorEnd(skyVec);
            }
        }
        return skyVec;
    }
}

