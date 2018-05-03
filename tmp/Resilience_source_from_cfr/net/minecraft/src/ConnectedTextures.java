/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.src.Config;
import net.minecraft.src.ConnectedProperties;
import net.minecraft.src.ResourceUtils;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

public class ConnectedTextures {
    private static ConnectedProperties[][] blockProperties = null;
    private static ConnectedProperties[][] tileProperties = null;
    private static boolean multipass = false;
    private static final int BOTTOM = 0;
    private static final int TOP = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;
    private static final int NORTH = 4;
    private static final int SOUTH = 5;
    private static final int Y_NEG = 0;
    private static final int Y_POS = 1;
    private static final int Z_NEG = 2;
    private static final int Z_POS = 3;
    private static final int X_NEG = 4;
    private static final int X_POS = 5;
    private static final int Y_AXIS = 0;
    private static final int Z_AXIS = 1;
    private static final int X_AXIS = 2;
    private static final String[] propSuffixes = new String[]{"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final int[] ctmIndexes;

    static {
        int[] arrn = new int[64];
        arrn[1] = 1;
        arrn[2] = 2;
        arrn[3] = 3;
        arrn[4] = 4;
        arrn[5] = 5;
        arrn[6] = 6;
        arrn[7] = 7;
        arrn[8] = 8;
        arrn[9] = 9;
        arrn[10] = 10;
        arrn[11] = 11;
        arrn[16] = 12;
        arrn[17] = 13;
        arrn[18] = 14;
        arrn[19] = 15;
        arrn[20] = 16;
        arrn[21] = 17;
        arrn[22] = 18;
        arrn[23] = 19;
        arrn[24] = 20;
        arrn[25] = 21;
        arrn[26] = 22;
        arrn[27] = 23;
        arrn[32] = 24;
        arrn[33] = 25;
        arrn[34] = 26;
        arrn[35] = 27;
        arrn[36] = 28;
        arrn[37] = 29;
        arrn[38] = 30;
        arrn[39] = 31;
        arrn[40] = 32;
        arrn[41] = 33;
        arrn[42] = 34;
        arrn[43] = 35;
        arrn[48] = 36;
        arrn[49] = 37;
        arrn[50] = 38;
        arrn[51] = 39;
        arrn[52] = 40;
        arrn[53] = 41;
        arrn[54] = 42;
        arrn[55] = 43;
        arrn[56] = 44;
        arrn[57] = 45;
        arrn[58] = 46;
        ctmIndexes = arrn;
    }

    public static IIcon getConnectedTexture(IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon) {
        if (blockAccess == null) {
            return icon;
        }
        IIcon newIcon = ConnectedTextures.getConnectedTextureSingle(blockAccess, block, x, y, z, side, icon, true);
        if (!multipass) {
            return newIcon;
        }
        if (newIcon == icon) {
            return newIcon;
        }
        IIcon mpIcon = newIcon;
        int i = 0;
        while (i < 3) {
            IIcon newMpIcon = ConnectedTextures.getConnectedTextureSingle(blockAccess, block, x, y, z, side, mpIcon, false);
            if (newMpIcon == mpIcon) break;
            mpIcon = newMpIcon;
            ++i;
        }
        return mpIcon;
    }

    public static IIcon getConnectedTextureSingle(IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, boolean checkBlocks) {
        int blockId1;
        ConnectedProperties[] cps1;
        ConnectedProperties[] blockId;
        if (!(icon instanceof TextureAtlasSprite)) {
            return icon;
        }
        TextureAtlasSprite ts = (TextureAtlasSprite)icon;
        int iconId = ts.getIndexInMap();
        int metadata = -1;
        if (tileProperties != null && Tessellator.instance.defaultTexture && iconId >= 0 && iconId < tileProperties.length && (blockId = tileProperties[iconId]) != null) {
            IIcon cps;
            if (metadata < 0) {
                metadata = blockAccess.getBlockMetadata(x, y, z);
            }
            if ((cps = ConnectedTextures.getConnectedTexture(blockId, blockAccess, block, x, y, z, side, (IIcon)ts, metadata)) != null) {
                return cps;
            }
        }
        if (blockProperties != null && checkBlocks && (blockId1 = Block.getIdFromBlock(block)) >= 0 && blockId1 < blockProperties.length && (cps1 = blockProperties[blockId1]) != null) {
            IIcon newIcon;
            if (metadata < 0) {
                metadata = blockAccess.getBlockMetadata(x, y, z);
            }
            if ((newIcon = ConnectedTextures.getConnectedTexture(cps1, blockAccess, block, x, y, z, side, (IIcon)ts, metadata)) != null) {
                return newIcon;
            }
        }
        return icon;
    }

    public static ConnectedProperties getConnectedProperties(IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon) {
        int blockId1;
        ConnectedProperties[] blockId;
        ConnectedProperties[] cps1;
        if (blockAccess == null) {
            return null;
        }
        if (!(icon instanceof TextureAtlasSprite)) {
            return null;
        }
        TextureAtlasSprite ts = (TextureAtlasSprite)icon;
        int iconId = ts.getIndexInMap();
        int metadata = -1;
        if (tileProperties != null && Tessellator.instance.defaultTexture && iconId >= 0 && iconId < tileProperties.length && (blockId = tileProperties[iconId]) != null) {
            ConnectedProperties cps;
            if (metadata < 0) {
                metadata = blockAccess.getBlockMetadata(x, y, z);
            }
            if ((cps = ConnectedTextures.getConnectedProperties(blockId, blockAccess, block, x, y, z, side, ts, metadata)) != null) {
                return cps;
            }
        }
        if (blockProperties != null && (blockId1 = Block.getIdFromBlock(block)) >= 0 && blockId1 < blockProperties.length && (cps1 = blockProperties[blockId1]) != null) {
            ConnectedProperties cp;
            if (metadata < 0) {
                metadata = blockAccess.getBlockMetadata(x, y, z);
            }
            if ((cp = ConnectedTextures.getConnectedProperties(cps1, blockAccess, block, x, y, z, side, ts, metadata)) != null) {
                return cp;
            }
        }
        return null;
    }

    private static IIcon getConnectedTexture(ConnectedProperties[] cps, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata) {
        int i = 0;
        while (i < cps.length) {
            IIcon newIcon;
            ConnectedProperties cp = cps[i];
            if (cp != null && (newIcon = ConnectedTextures.getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata)) != null) {
                return newIcon;
            }
            ++i;
        }
        return null;
    }

    private static ConnectedProperties getConnectedProperties(ConnectedProperties[] cps, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata) {
        int i = 0;
        while (i < cps.length) {
            IIcon newIcon;
            ConnectedProperties cp = cps[i];
            if (cp != null && (newIcon = ConnectedTextures.getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata)) != null) {
                return cp;
            }
            ++i;
        }
        return null;
    }

    private static IIcon getConnectedTexture(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata) {
        if (y >= cp.minHeight && y <= cp.maxHeight) {
            int mds;
            if (cp.biomes != null) {
                BiomeGenBase vertAxis = blockAccess.getBiomeGenForCoords(x, z);
                boolean metadataCheck = false;
                mds = 0;
                while (mds < cp.biomes.length) {
                    BiomeGenBase metadataFound = cp.biomes[mds];
                    if (vertAxis == metadataFound) {
                        metadataCheck = true;
                        break;
                    }
                    ++mds;
                }
                if (!metadataCheck) {
                    return null;
                }
            }
            int var14 = 0;
            int var15 = metadata;
            if (block instanceof BlockRotatedPillar) {
                var14 = ConnectedTextures.getWoodAxis(side, metadata);
                var15 = metadata & 3;
            }
            if (block instanceof BlockQuartz) {
                var14 = ConnectedTextures.getQuartzAxis(side, metadata);
                if (var15 > 2) {
                    var15 = 2;
                }
            }
            if (side >= 0 && cp.faces != 63) {
                mds = side;
                if (var14 != 0) {
                    mds = ConnectedTextures.fixSideByAxis(side, var14);
                }
                if ((1 << mds & cp.faces) == 0) {
                    return null;
                }
            }
            if (cp.metadatas != null) {
                int[] var17 = cp.metadatas;
                boolean var16 = false;
                int i = 0;
                while (i < var17.length) {
                    if (var17[i] == var15) {
                        var16 = true;
                        break;
                    }
                    ++i;
                }
                if (!var16) {
                    return null;
                }
            }
            switch (cp.method) {
                case 1: {
                    return ConnectedTextures.getConnectedTextureCtm(cp, blockAccess, block, x, y, z, side, icon, metadata);
                }
                case 2: {
                    return ConnectedTextures.getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, var14, side, icon, metadata);
                }
                case 3: {
                    return ConnectedTextures.getConnectedTextureTop(cp, blockAccess, block, x, y, z, var14, side, icon, metadata);
                }
                case 4: {
                    return ConnectedTextures.getConnectedTextureRandom(cp, x, y, z, side);
                }
                case 5: {
                    return ConnectedTextures.getConnectedTextureRepeat(cp, x, y, z, side);
                }
                case 6: {
                    return ConnectedTextures.getConnectedTextureVertical(cp, blockAccess, block, x, y, z, var14, side, icon, metadata);
                }
                case 7: {
                    return ConnectedTextures.getConnectedTextureFixed(cp);
                }
                case 8: {
                    return ConnectedTextures.getConnectedTextureHorizontalVertical(cp, blockAccess, block, x, y, z, var14, side, icon, metadata);
                }
                case 9: {
                    return ConnectedTextures.getConnectedTextureVerticalHorizontal(cp, blockAccess, block, x, y, z, var14, side, icon, metadata);
                }
            }
            return null;
        }
        return null;
    }

    private static int fixSideByAxis(int side, int vertAxis) {
        switch (vertAxis) {
            case 0: {
                return side;
            }
            case 1: {
                switch (side) {
                    case 0: {
                        return 2;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        return 0;
                    }
                }
                return side;
            }
            case 2: {
                switch (side) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    default: {
                        return side;
                    }
                    case 4: {
                        return 1;
                    }
                    case 5: 
                }
                return 0;
            }
        }
        return side;
    }

    private static int getWoodAxis(int side, int metadata) {
        int orient = (metadata & 12) >> 2;
        switch (orient) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
        }
        return 0;
    }

    private static int getQuartzAxis(int side, int metadata) {
        switch (metadata) {
            case 3: {
                return 2;
            }
            case 4: {
                return 1;
            }
        }
        return 0;
    }

    private static IIcon getConnectedTextureRandom(ConnectedProperties cp, int x, int y, int z, int side) {
        if (cp.tileIcons.length == 1) {
            return cp.tileIcons[0];
        }
        int face = side / cp.symmetry * cp.symmetry;
        int rand = Config.getRandom(x, y, z, face) & Integer.MAX_VALUE;
        int index = 0;
        if (cp.weights == null) {
            index = rand % cp.tileIcons.length;
        } else {
            int randWeight = rand % cp.sumAllWeights;
            int[] sumWeights = cp.sumWeights;
            int i = 0;
            while (i < sumWeights.length) {
                if (randWeight < sumWeights[i]) {
                    index = i;
                    break;
                }
                ++i;
            }
        }
        return cp.tileIcons[index];
    }

    private static IIcon getConnectedTextureFixed(ConnectedProperties cp) {
        return cp.tileIcons[0];
    }

    private static IIcon getConnectedTextureRepeat(ConnectedProperties cp, int x, int y, int z, int side) {
        if (cp.tileIcons.length == 1) {
            return cp.tileIcons[0];
        }
        int nx = 0;
        int ny = 0;
        switch (side) {
            case 0: {
                nx = x;
                ny = z;
                break;
            }
            case 1: {
                nx = x;
                ny = z;
                break;
            }
            case 2: {
                nx = - x - 1;
                ny = - y;
                break;
            }
            case 3: {
                nx = x;
                ny = - y;
                break;
            }
            case 4: {
                nx = z;
                ny = - y;
                break;
            }
            case 5: {
                nx = - z - 1;
                ny = - y;
            }
        }
        ny %= cp.height;
        if ((nx %= cp.width) < 0) {
            nx += cp.width;
        }
        if (ny < 0) {
            ny += cp.height;
        }
        int index = ny * cp.width + nx;
        return cp.tileIcons[index];
    }

    private static IIcon getConnectedTextureCtm(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata) {
        boolean[] borders = new boolean[6];
        switch (side) {
            case 0: 
            case 1: {
                borders[0] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                break;
            }
            case 2: {
                borders[0] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 3: {
                borders[0] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 4: {
                borders[0] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 5: {
                borders[0] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
            }
        }
        int index = 0;
        if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
            index = 3;
        } else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 1;
        } else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 12;
        } else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 36;
        } else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 2;
        } else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 24;
        } else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 15;
        } else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 39;
        } else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 13;
        } else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 37;
        } else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 25;
        } else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 27;
        } else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 38;
        } else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 14;
        } else if (borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 26;
        }
        if (index == 0) {
            return cp.tileIcons[index];
        }
        if (!Config.isConnectedTexturesFancy()) {
            return cp.tileIcons[index];
        }
        boolean[] edges = new boolean[6];
        switch (side) {
            case 0: 
            case 1: {
                edges[0] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z + 1, side, icon, metadata);
                edges[1] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z + 1, side, icon, metadata);
                edges[2] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z - 1, side, icon, metadata);
                edges[3] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z - 1, side, icon, metadata);
                break;
            }
            case 2: {
                edges[0] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata);
                edges[1] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata);
                edges[2] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata);
                edges[3] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata);
                break;
            }
            case 3: {
                edges[0] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata);
                edges[1] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata);
                edges[2] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata);
                edges[3] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata);
                break;
            }
            case 4: {
                edges[0] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata);
                edges[1] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata);
                edges[2] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata);
                edges[3] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata);
                break;
            }
            case 5: {
                edges[0] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata);
                edges[1] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata);
                edges[2] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata);
                edges[3] = !ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata);
            }
        }
        if (index == 13 && edges[0]) {
            index = 4;
        } else if (index == 15 && edges[1]) {
            index = 5;
        } else if (index == 37 && edges[2]) {
            index = 16;
        } else if (index == 39 && edges[3]) {
            index = 17;
        } else if (index == 14 && edges[0] && edges[1]) {
            index = 7;
        } else if (index == 25 && edges[0] && edges[2]) {
            index = 6;
        } else if (index == 27 && edges[3] && edges[1]) {
            index = 19;
        } else if (index == 38 && edges[3] && edges[2]) {
            index = 18;
        } else if (index == 14 && !edges[0] && edges[1]) {
            index = 31;
        } else if (index == 25 && edges[0] && !edges[2]) {
            index = 30;
        } else if (index == 27 && !edges[3] && edges[1]) {
            index = 41;
        } else if (index == 38 && edges[3] && !edges[2]) {
            index = 40;
        } else if (index == 14 && edges[0] && !edges[1]) {
            index = 29;
        } else if (index == 25 && !edges[0] && edges[2]) {
            index = 28;
        } else if (index == 27 && edges[3] && !edges[1]) {
            index = 43;
        } else if (index == 38 && !edges[3] && edges[2]) {
            index = 42;
        } else if (index == 26 && edges[0] && edges[1] && edges[2] && edges[3]) {
            index = 46;
        } else if (index == 26 && !edges[0] && edges[1] && edges[2] && edges[3]) {
            index = 9;
        } else if (index == 26 && edges[0] && !edges[1] && edges[2] && edges[3]) {
            index = 21;
        } else if (index == 26 && edges[0] && edges[1] && !edges[2] && edges[3]) {
            index = 8;
        } else if (index == 26 && edges[0] && edges[1] && edges[2] && !edges[3]) {
            index = 20;
        } else if (index == 26 && edges[0] && edges[1] && !edges[2] && !edges[3]) {
            index = 11;
        } else if (index == 26 && !edges[0] && !edges[1] && edges[2] && edges[3]) {
            index = 22;
        } else if (index == 26 && !edges[0] && edges[1] && !edges[2] && edges[3]) {
            index = 23;
        } else if (index == 26 && edges[0] && !edges[1] && edges[2] && !edges[3]) {
            index = 10;
        } else if (index == 26 && edges[0] && !edges[1] && !edges[2] && edges[3]) {
            index = 34;
        } else if (index == 26 && !edges[0] && edges[1] && edges[2] && !edges[3]) {
            index = 35;
        } else if (index == 26 && edges[0] && !edges[1] && !edges[2] && !edges[3]) {
            index = 32;
        } else if (index == 26 && !edges[0] && edges[1] && !edges[2] && !edges[3]) {
            index = 33;
        } else if (index == 26 && !edges[0] && !edges[1] && edges[2] && !edges[3]) {
            index = 44;
        } else if (index == 26 && !edges[0] && !edges[1] && !edges[2] && edges[3]) {
            index = 45;
        }
        return cp.tileIcons[index];
    }

    private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata) {
        Block neighbourBlock = iblockaccess.getBlock(x, y, z);
        if (cp.connect == 2) {
            if (neighbourBlock == null) {
                return false;
            }
            IIcon neighbourIcon = side >= 0 ? neighbourBlock.getIcon(side, metadata) : neighbourBlock.getIcon(1, metadata);
            if (neighbourIcon == icon) {
                return true;
            }
            return false;
        }
        return cp.connect == 3 ? (neighbourBlock == null ? false : neighbourBlock.getMaterial() == block.getMaterial()) : neighbourBlock == block && iblockaccess.getBlockMetadata(x, y, z) == metadata;
    }

    private static IIcon getConnectedTextureHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int vertAxis, int side, IIcon icon, int metadata) {
        boolean left = false;
        boolean right = false;
        block0 : switch (vertAxis) {
            case 0: {
                switch (side) {
                    case 0: 
                    case 1: {
                        return null;
                    }
                    case 2: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                        break block0;
                    }
                    case 3: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                        break block0;
                    }
                    case 4: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                        break block0;
                    }
                    case 5: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                    }
                }
                break;
            }
            case 1: {
                switch (side) {
                    case 0: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                        break block0;
                    }
                    case 1: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                        break block0;
                    }
                    case 2: 
                    case 3: {
                        return null;
                    }
                    case 4: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                        break block0;
                    }
                    case 5: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                    }
                }
                break;
            }
            case 2: {
                switch (side) {
                    case 0: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                        break block0;
                    }
                    case 1: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                        break block0;
                    }
                    case 2: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                        break block0;
                    }
                    case 3: {
                        left = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                        break block0;
                    }
                    case 4: 
                    case 5: {
                        return null;
                    }
                }
            }
        }
        boolean index = true;
        int index1 = left ? (right ? 1 : 2) : (right ? 0 : 3);
        return cp.tileIcons[index1];
    }

    private static IIcon getConnectedTextureVertical(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int vertAxis, int side, IIcon icon, int metadata) {
        boolean bottom = false;
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                bottom = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
                top = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                bottom = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
                top = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                bottom = ConnectedTextures.isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
                top = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
            }
        }
        boolean index = true;
        int index1 = bottom ? (top ? 1 : 2) : (top ? 0 : 3);
        return cp.tileIcons[index1];
    }

    private static IIcon getConnectedTextureHorizontalVertical(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int vertAxis, int side, IIcon icon, int metadata) {
        IIcon[] tileIcons = cp.tileIcons;
        IIcon iconH = ConnectedTextures.getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
            return iconH;
        }
        IIcon iconV = ConnectedTextures.getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        return iconV == tileIcons[0] ? tileIcons[4] : (iconV == tileIcons[1] ? tileIcons[5] : (iconV == tileIcons[2] ? tileIcons[6] : iconV));
    }

    private static IIcon getConnectedTextureVerticalHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int vertAxis, int side, IIcon icon, int metadata) {
        IIcon[] tileIcons = cp.tileIcons;
        IIcon iconV = ConnectedTextures.getConnectedTextureVertical(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
            return iconV;
        }
        IIcon iconH = ConnectedTextures.getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, vertAxis, side, icon, metadata);
        return iconH == tileIcons[0] ? tileIcons[4] : (iconH == tileIcons[1] ? tileIcons[5] : (iconH == tileIcons[2] ? tileIcons[6] : iconH));
    }

    private static IIcon getConnectedTextureTop(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int vertAxis, int side, IIcon icon, int metadata) {
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                top = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                top = ConnectedTextures.isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                top = ConnectedTextures.isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
            }
        }
        return top ? cp.tileIcons[0] : null;
    }

    public static void updateIcons(TextureMap textureMap) {
        blockProperties = null;
        tileProperties = null;
        IResourcePack[] rps = Config.getResourcePacks();
        int i = rps.length - 1;
        while (i >= 0) {
            IResourcePack rp = rps[i];
            ConnectedTextures.updateIcons(textureMap, rp);
            --i;
        }
        ConnectedTextures.updateIcons(textureMap, Config.getDefaultResourcePack());
    }

    public static void updateIcons(TextureMap textureMap, IResourcePack rp) {
        Object[] names = ConnectedTextures.collectFiles(rp, "mcpatcher/ctm/", ".properties");
        Arrays.sort(names);
        List tileList = ConnectedTextures.makePropertyList(tileProperties);
        List blockList = ConnectedTextures.makePropertyList(blockProperties);
        int i = 0;
        while (i < names.length) {
            Object name = names[i];
            Config.dbg("ConnectedTextures: " + (String)name);
            try {
                ResourceLocation e = new ResourceLocation((String)name);
                InputStream in = rp.getInputStream(e);
                if (in == null) {
                    Config.warn("ConnectedTextures file not found: " + (String)name);
                } else {
                    Properties props = new Properties();
                    props.load(in);
                    ConnectedProperties cp = new ConnectedProperties(props, (String)name);
                    if (cp.isValid((String)name)) {
                        cp.updateIcons(textureMap);
                        ConnectedTextures.addToTileList(cp, tileList);
                        ConnectedTextures.addToBlockList(cp, blockList);
                    }
                }
            }
            catch (FileNotFoundException var11) {
                Config.warn("ConnectedTextures file not found: " + (String)name);
            }
            catch (IOException var12) {
                var12.printStackTrace();
            }
            ++i;
        }
        blockProperties = ConnectedTextures.propertyListToArray(blockList);
        tileProperties = ConnectedTextures.propertyListToArray(tileList);
        multipass = ConnectedTextures.detectMultipass();
        Config.dbg("Multipass connected textures: " + multipass);
    }

    private static List makePropertyList(ConnectedProperties[][] propsArr) {
        ArrayList<Object> list = new ArrayList<Object>();
        if (propsArr != null) {
            int i = 0;
            while (i < propsArr.length) {
                ConnectedProperties[] props = propsArr[i];
                ArrayList<ConnectedProperties> propList = null;
                if (props != null) {
                    propList = new ArrayList<ConnectedProperties>(Arrays.asList(props));
                }
                list.add(propList);
                ++i;
            }
        }
        return list;
    }

    private static boolean detectMultipass() {
        ConnectedProperties[] matchIconSet;
        ArrayList<ConnectedProperties> propList = new ArrayList<ConnectedProperties>();
        int props = 0;
        while (props < tileProperties.length) {
            matchIconSet = tileProperties[props];
            if (matchIconSet != null) {
                propList.addAll(Arrays.asList(matchIconSet));
            }
            ++props;
        }
        props = 0;
        while (props < blockProperties.length) {
            matchIconSet = blockProperties[props];
            if (matchIconSet != null) {
                propList.addAll(Arrays.asList(matchIconSet));
            }
            ++props;
        }
        ConnectedProperties[] var6 = propList.toArray(new ConnectedProperties[propList.size()]);
        HashSet<IIcon> var7 = new HashSet<IIcon>();
        HashSet<IIcon> tileIconSet = new HashSet<IIcon>();
        int i = 0;
        while (i < var6.length) {
            ConnectedProperties cp = var6[i];
            if (cp.matchTileIcons != null) {
                var7.addAll(Arrays.asList(cp.matchTileIcons));
            }
            if (cp.tileIcons != null) {
                tileIconSet.addAll(Arrays.asList(cp.tileIcons));
            }
            ++i;
        }
        var7.retainAll(tileIconSet);
        return !var7.isEmpty();
    }

    private static ConnectedProperties[][] propertyListToArray(List list) {
        ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
        int i = 0;
        while (i < list.size()) {
            List subList = (List)list.get(i);
            if (subList != null) {
                ConnectedProperties[] subArr = subList.toArray(new ConnectedProperties[subList.size()]);
                propArr[i] = subArr;
            }
            ++i;
        }
        return propArr;
    }

    private static void addToTileList(ConnectedProperties cp, List tileList) {
        if (cp.matchTileIcons != null) {
            int i = 0;
            while (i < cp.matchTileIcons.length) {
                IIcon icon = cp.matchTileIcons[i];
                if (!(icon instanceof TextureAtlasSprite)) {
                    Config.warn("IIcon is not TextureAtlasSprite: " + icon + ", name: " + icon.getIconName());
                } else {
                    TextureAtlasSprite ts = (TextureAtlasSprite)icon;
                    int tileId = ts.getIndexInMap();
                    if (tileId < 0) {
                        Config.warn("Invalid tile ID: " + tileId + ", icon: " + ts.getIconName());
                    } else {
                        ConnectedTextures.addToList(cp, tileList, tileId);
                    }
                }
                ++i;
            }
        }
    }

    private static void addToBlockList(ConnectedProperties cp, List blockList) {
        if (cp.matchBlocks != null) {
            int i = 0;
            while (i < cp.matchBlocks.length) {
                int blockId = cp.matchBlocks[i];
                if (blockId < 0) {
                    Config.warn("Invalid block ID: " + blockId);
                } else {
                    ConnectedTextures.addToList(cp, blockList, blockId);
                }
                ++i;
            }
        }
    }

    private static void addToList(ConnectedProperties cp, List list, int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        ArrayList<ConnectedProperties> subList = (ArrayList<ConnectedProperties>)list.get(id);
        if (subList == null) {
            subList = new ArrayList<ConnectedProperties>();
            list.set(id, subList);
        }
        subList.add(cp);
    }

    private static String[] collectFiles(IResourcePack rp, String prefix, String suffix) {
        if (rp instanceof DefaultResourcePack) {
            return ConnectedTextures.collectFilesDefault(rp);
        }
        if (!(rp instanceof AbstractResourcePack)) {
            return new String[0];
        }
        AbstractResourcePack arp = (AbstractResourcePack)rp;
        File tpFile = ResourceUtils.getResourcePackFile(arp);
        return tpFile == null ? new String[]{} : (tpFile.isDirectory() ? ConnectedTextures.collectFilesFolder(tpFile, "", prefix, suffix) : (tpFile.isFile() ? ConnectedTextures.collectFilesZIP(tpFile, prefix, suffix) : new String[]{}));
    }

    private static String[] collectFilesDefault(IResourcePack rp) {
        ArrayList<String> list = new ArrayList<String>();
        String[] names = ConnectedTextures.getDefaultCtmPaths();
        int nameArr = 0;
        while (nameArr < names.length) {
            String name = names[nameArr];
            ResourceLocation loc = new ResourceLocation(name);
            if (rp.resourceExists(loc)) {
                list.add(name);
            }
            ++nameArr;
        }
        String[] var6 = list.toArray(new String[list.size()]);
        return var6;
    }

    private static String[] getDefaultCtmPaths() {
        ArrayList<String> list = new ArrayList<String>();
        String defPath = "mcpatcher/ctm/default/";
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
            list.add(String.valueOf(defPath) + "glass.properties");
            list.add(String.valueOf(defPath) + "glasspane.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
            list.add(String.valueOf(defPath) + "bookshelf.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
            list.add(String.valueOf(defPath) + "sandstone.properties");
        }
        String[] colors = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
        int paths = 0;
        while (paths < colors.length) {
            String color = colors[paths];
            if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + color + ".png"))) {
                list.add(String.valueOf(defPath) + paths + "_glass_" + color + "/glass_" + color + ".properties");
                list.add(String.valueOf(defPath) + paths + "_glass_" + color + "/glass_pane_" + color + ".properties");
            }
            ++paths;
        }
        String[] var5 = list.toArray(new String[list.size()]);
        return var5;
    }

    private static String[] collectFilesFolder(File tpFile, String basePath, String prefix, String suffix) {
        ArrayList<String> list = new ArrayList<String>();
        String prefixAssets = "assets/minecraft/";
        File[] files = tpFile.listFiles();
        if (files == null) {
            return new String[0];
        }
        int names = 0;
        while (names < files.length) {
            String dirPath;
            File file = files[names];
            if (file.isFile()) {
                dirPath = String.valueOf(basePath) + file.getName();
                if (dirPath.startsWith(prefixAssets) && (dirPath = dirPath.substring(prefixAssets.length())).startsWith(prefix) && dirPath.endsWith(suffix)) {
                    list.add(dirPath);
                }
            } else if (file.isDirectory()) {
                dirPath = String.valueOf(basePath) + file.getName() + "/";
                String[] names1 = ConnectedTextures.collectFilesFolder(file, dirPath, prefix, suffix);
                int n = 0;
                while (n < names1.length) {
                    String name = names1[n];
                    list.add(name);
                    ++n;
                }
            }
            ++names;
        }
        String[] var13 = list.toArray(new String[list.size()]);
        return var13;
    }

    private static String[] collectFilesZIP(File tpFile, String prefix, String suffix) {
        ArrayList<String> list = new ArrayList<String>();
        String prefixAssets = "assets/minecraft/";
        try {
            ZipFile e = new ZipFile(tpFile);
            Enumeration<? extends ZipEntry> en = e.entries();
            while (en.hasMoreElements()) {
                ZipEntry names = en.nextElement();
                String name = names.getName();
                if (!name.startsWith(prefixAssets) || !(name = name.substring(prefixAssets.length())).startsWith(prefix) || !name.endsWith(suffix)) continue;
                list.add(name);
            }
            e.close();
            String[] names1 = list.toArray(new String[list.size()]);
            return names1;
        }
        catch (IOException var9) {
            var9.printStackTrace();
            return new String[0];
        }
    }

    public static int getPaneTextureIndex(boolean linkP, boolean linkN, boolean linkYp, boolean linkYn) {
        return linkN && linkP ? (linkYp ? (linkYn ? 34 : 50) : (linkYn ? 18 : 2)) : (linkN && !linkP ? (linkYp ? (linkYn ? 35 : 51) : (linkYn ? 19 : 3)) : (!linkN && linkP ? (linkYp ? (linkYn ? 33 : 49) : (linkYn ? 17 : 1)) : (linkYp ? (linkYn ? 32 : 48) : (linkYn ? 16 : 0))));
    }

    public static int getReversePaneTextureIndex(int texNum) {
        int col = texNum % 16;
        return col == 1 ? texNum + 2 : (col == 3 ? texNum - 2 : texNum);
    }

    public static IIcon getCtmTexture(ConnectedProperties cp, int ctmIndex, IIcon icon) {
        if (cp.method != 1) {
            return icon;
        }
        if (ctmIndex >= 0 && ctmIndex < ctmIndexes.length) {
            int index = ctmIndexes[ctmIndex];
            IIcon[] ctmIcons = cp.tileIcons;
            return index >= 0 && index < ctmIcons.length ? ctmIcons[index] : icon;
        }
        return icon;
    }
}

