/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.XrayBlock;
import com.krispdev.resilience.utilities.XrayUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.src.Config;
import net.minecraft.src.ConnectedTextures;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.NaturalProperties;
import net.minecraft.src.NaturalTextures;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorMethod;
import net.minecraft.src.TextureUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderBlocks {
    public IBlockAccess blockAccess;
    public IIcon overrideBlockTexture;
    public boolean flipTexture;
    public boolean renderAllFaces;
    public static boolean fancyGrass = true;
    public static boolean cfgGrassFix = true;
    public boolean useInventoryTint = true;
    public boolean renderFromInside = false;
    public double renderMinX;
    public double renderMaxX;
    public double renderMinY;
    public double renderMaxY;
    public double renderMinZ;
    public double renderMaxZ;
    public boolean lockBlockBounds;
    public boolean partialRenderBounds;
    public final Minecraft minecraftRB;
    public int uvRotateEast;
    public int uvRotateWest;
    public int uvRotateSouth;
    public int uvRotateNorth;
    public int uvRotateTop;
    public int uvRotateBottom;
    public boolean enableAO;
    public float aoLightValueScratchXYZNNN;
    public float aoLightValueScratchXYNN;
    public float aoLightValueScratchXYZNNP;
    public float aoLightValueScratchYZNN;
    public float aoLightValueScratchYZNP;
    public float aoLightValueScratchXYZPNN;
    public float aoLightValueScratchXYPN;
    public float aoLightValueScratchXYZPNP;
    public float aoLightValueScratchXYZNPN;
    public float aoLightValueScratchXYNP;
    public float aoLightValueScratchXYZNPP;
    public float aoLightValueScratchYZPN;
    public float aoLightValueScratchXYZPPN;
    public float aoLightValueScratchXYPP;
    public float aoLightValueScratchYZPP;
    public float aoLightValueScratchXYZPPP;
    public float aoLightValueScratchXZNN;
    public float aoLightValueScratchXZPN;
    public float aoLightValueScratchXZNP;
    public float aoLightValueScratchXZPP;
    public int aoBrightnessXYZNNN;
    public int aoBrightnessXYNN;
    public int aoBrightnessXYZNNP;
    public int aoBrightnessYZNN;
    public int aoBrightnessYZNP;
    public int aoBrightnessXYZPNN;
    public int aoBrightnessXYPN;
    public int aoBrightnessXYZPNP;
    public int aoBrightnessXYZNPN;
    public int aoBrightnessXYNP;
    public int aoBrightnessXYZNPP;
    public int aoBrightnessYZPN;
    public int aoBrightnessXYZPPN;
    public int aoBrightnessXYPP;
    public int aoBrightnessYZPP;
    public int aoBrightnessXYZPPP;
    public int aoBrightnessXZNN;
    public int aoBrightnessXZPN;
    public int aoBrightnessXZNP;
    public int aoBrightnessXZPP;
    public int brightnessTopLeft;
    public int brightnessBottomLeft;
    public int brightnessBottomRight;
    public int brightnessTopRight;
    public float colorRedTopLeft;
    public float colorRedBottomLeft;
    public float colorRedBottomRight;
    public float colorRedTopRight;
    public float colorGreenTopLeft;
    public float colorGreenBottomLeft;
    public float colorGreenBottomRight;
    public float colorGreenTopRight;
    public float colorBlueTopLeft;
    public float colorBlueBottomLeft;
    public float colorBlueBottomRight;
    public float colorBlueTopRight;
    public static final String __OBFID = "CL_00000940";
    public boolean aoLightValuesCalculated;
    public float aoLightValueOpaque = 0.2f;
    public boolean betterSnowEnabled = true;

    public RenderBlocks(IBlockAccess par1IBlockAccess) {
        this.blockAccess = par1IBlockAccess;
        this.minecraftRB = Minecraft.getMinecraft();
        this.aoLightValueOpaque = 1.0f - Config.getAmbientOcclusionLevel() * 0.8f;
    }

    public RenderBlocks() {
        this.minecraftRB = Minecraft.getMinecraft();
    }

    public void setOverrideBlockTexture(IIcon p_147757_1_) {
        this.overrideBlockTexture = p_147757_1_;
    }

    public void clearOverrideBlockTexture() {
        this.overrideBlockTexture = null;
    }

    public boolean hasOverrideBlockTexture() {
        if (this.overrideBlockTexture != null) {
            return true;
        }
        return false;
    }

    public void setRenderFromInside(boolean p_147786_1_) {
        this.renderFromInside = p_147786_1_;
    }

    public void setRenderAllFaces(boolean p_147753_1_) {
        this.renderAllFaces = p_147753_1_;
    }

    public void setRenderBounds(double p_147782_1_, double p_147782_3_, double p_147782_5_, double p_147782_7_, double p_147782_9_, double p_147782_11_) {
        if (!this.lockBlockBounds) {
            this.renderMinX = p_147782_1_;
            this.renderMaxX = p_147782_7_;
            this.renderMinY = p_147782_3_;
            this.renderMaxY = p_147782_9_;
            this.renderMinZ = p_147782_5_;
            this.renderMaxZ = p_147782_11_;
            this.partialRenderBounds = this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0);
        }
    }

    public void setRenderBoundsFromBlock(Block p_147775_1_) {
        if (!this.lockBlockBounds) {
            this.renderMinX = p_147775_1_.getBlockBoundsMinX();
            this.renderMaxX = p_147775_1_.getBlockBoundsMaxX();
            this.renderMinY = p_147775_1_.getBlockBoundsMinY();
            this.renderMaxY = p_147775_1_.getBlockBoundsMaxY();
            this.renderMinZ = p_147775_1_.getBlockBoundsMinZ();
            this.renderMaxZ = p_147775_1_.getBlockBoundsMaxZ();
            this.partialRenderBounds = this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0);
        }
    }

    public void overrideBlockBounds(double p_147770_1_, double p_147770_3_, double p_147770_5_, double p_147770_7_, double p_147770_9_, double p_147770_11_) {
        this.renderMinX = p_147770_1_;
        this.renderMaxX = p_147770_7_;
        this.renderMinY = p_147770_3_;
        this.renderMaxY = p_147770_9_;
        this.renderMinZ = p_147770_5_;
        this.renderMaxZ = p_147770_11_;
        this.lockBlockBounds = true;
        this.partialRenderBounds = this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0);
    }

    public void unlockBlockBounds() {
        this.lockBlockBounds = false;
    }

    public void renderBlockUsingTexture(Block p_147792_1_, int p_147792_2_, int p_147792_3_, int p_147792_4_, IIcon p_147792_5_) {
        this.setOverrideBlockTexture(p_147792_5_);
        this.renderBlockByRenderType(p_147792_1_, p_147792_2_, p_147792_3_, p_147792_4_);
        this.clearOverrideBlockTexture();
    }

    public void renderBlockAllFaces(Block p_147769_1_, int p_147769_2_, int p_147769_3_, int p_147769_4_) {
        this.renderAllFaces = true;
        this.renderBlockByRenderType(p_147769_1_, p_147769_2_, p_147769_3_, p_147769_4_);
        this.renderAllFaces = false;
    }

    public boolean renderBlockByRenderType(Block par1Block, int par2, int par3, int par4) {
        int i = par1Block.getRenderType();
        if (i == -1) {
            return false;
        }
        if (Resilience.getInstance().getXrayUtils().xrayEnabled) {
            this.renderAllFaces = Resilience.getInstance().getXrayUtils().shouldRenderBlock(new XrayBlock(Resilience.getInstance().getInvoker().getIdFromBlock(par1Block)));
            if (!this.renderAllFaces) {
                return false;
            }
        }
        par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
        if (Config.isBetterSnow() && par1Block == Blocks.standing_sign && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        this.setRenderBoundsFromBlock(par1Block);
        switch (i) {
            case 0: {
                return this.renderStandardBlock(par1Block, par2, par3, par4);
            }
            case 1: {
                return this.renderCrossedSquares(par1Block, par2, par3, par4);
            }
            case 2: {
                return this.renderBlockTorch(par1Block, par2, par3, par4);
            }
            case 3: {
                return this.renderBlockFire((BlockFire)par1Block, par2, par3, par4);
            }
            case 4: {
                return this.renderBlockFluids(par1Block, par2, par3, par4);
            }
            case 5: {
                return this.renderBlockRedstoneWire(par1Block, par2, par3, par4);
            }
            case 6: {
                return this.renderBlockCrops(par1Block, par2, par3, par4);
            }
            case 7: {
                return this.renderBlockDoor(par1Block, par2, par3, par4);
            }
            case 8: {
                return this.renderBlockLadder(par1Block, par2, par3, par4);
            }
            case 9: {
                return this.renderBlockMinecartTrack((BlockRailBase)par1Block, par2, par3, par4);
            }
            case 10: {
                return this.renderBlockStairs((BlockStairs)par1Block, par2, par3, par4);
            }
            case 11: {
                return this.renderBlockFence((BlockFence)par1Block, par2, par3, par4);
            }
            case 12: {
                return this.renderBlockLever(par1Block, par2, par3, par4);
            }
            case 13: {
                return this.renderBlockCactus(par1Block, par2, par3, par4);
            }
            case 14: {
                return this.renderBlockBed(par1Block, par2, par3, par4);
            }
            case 15: {
                return this.renderBlockRepeater((BlockRedstoneRepeater)par1Block, par2, par3, par4);
            }
            case 16: {
                return this.renderPistonBase(par1Block, par2, par3, par4, false);
            }
            case 17: {
                return this.renderPistonExtension(par1Block, par2, par3, par4, true);
            }
            case 18: {
                return this.renderBlockPane((BlockPane)par1Block, par2, par3, par4);
            }
            case 19: {
                return this.renderBlockStem(par1Block, par2, par3, par4);
            }
            case 20: {
                return this.renderBlockVine(par1Block, par2, par3, par4);
            }
            case 21: {
                return this.renderBlockFenceGate((BlockFenceGate)par1Block, par2, par3, par4);
            }
            default: {
                if (Reflector.ModLoader.exists()) {
                    return Reflector.callBoolean(Reflector.ModLoader_renderWorldBlock, this, this.blockAccess, par2, par3, par4, par1Block, i);
                }
                if (Reflector.FMLRenderAccessLibrary.exists()) {
                    return Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderWorldBlock, this, this.blockAccess, par2, par3, par4, par1Block, i);
                }
                return false;
            }
            case 23: {
                return this.renderBlockLilyPad(par1Block, par2, par3, par4);
            }
            case 24: {
                return this.renderBlockCauldron((BlockCauldron)par1Block, par2, par3, par4);
            }
            case 25: {
                return this.renderBlockBrewingStand((BlockBrewingStand)par1Block, par2, par3, par4);
            }
            case 26: {
                return this.renderBlockEndPortalFrame((BlockEndPortalFrame)par1Block, par2, par3, par4);
            }
            case 27: {
                return this.renderBlockDragonEgg((BlockDragonEgg)par1Block, par2, par3, par4);
            }
            case 28: {
                return this.renderBlockCocoa((BlockCocoa)par1Block, par2, par3, par4);
            }
            case 29: {
                return this.renderBlockTripWireSource(par1Block, par2, par3, par4);
            }
            case 30: {
                return this.renderBlockTripWire(par1Block, par2, par3, par4);
            }
            case 31: {
                return this.renderBlockLog(par1Block, par2, par3, par4);
            }
            case 32: {
                return this.renderBlockWall((BlockWall)par1Block, par2, par3, par4);
            }
            case 33: {
                return this.renderBlockFlowerpot((BlockFlowerPot)par1Block, par2, par3, par4);
            }
            case 34: {
                return this.renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);
            }
            case 35: {
                return this.renderBlockAnvil((BlockAnvil)par1Block, par2, par3, par4);
            }
            case 36: {
                return this.renderBlockRedstoneDiode((BlockRedstoneDiode)par1Block, par2, par3, par4);
            }
            case 37: {
                return this.renderBlockRedstoneComparator((BlockRedstoneComparator)par1Block, par2, par3, par4);
            }
            case 38: {
                return this.renderBlockHopper((BlockHopper)par1Block, par2, par3, par4);
            }
            case 39: {
                return this.renderBlockQuartz(par1Block, par2, par3, par4);
            }
            case 40: {
                return this.renderBlockDoublePlant((BlockDoublePlant)par1Block, par2, par3, par4);
            }
            case 41: 
        }
        return this.renderBlockStainedGlassPane(par1Block, par2, par3, par4);
    }

    public boolean renderBlockEndPortalFrame(BlockEndPortalFrame p_147743_1_, int p_147743_2_, int p_147743_3_, int p_147743_4_) {
        int var5 = this.blockAccess.getBlockMetadata(p_147743_2_, p_147743_3_, p_147743_4_);
        int var6 = var5 & 3;
        if (var6 == 0) {
            this.uvRotateTop = 3;
        } else if (var6 == 3) {
            this.uvRotateTop = 1;
        } else if (var6 == 1) {
            this.uvRotateTop = 2;
        }
        if (!BlockEndPortalFrame.func_150020_b(var5)) {
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);
            this.renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
            this.uvRotateTop = 0;
            return true;
        }
        this.renderAllFaces = true;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);
        this.renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
        this.setOverrideBlockTexture(p_147743_1_.func_150021_e());
        this.setRenderBounds(0.25, 0.8125, 0.25, 0.75, 1.0, 0.75);
        this.renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
        this.renderAllFaces = false;
        this.clearOverrideBlockTexture();
        this.uvRotateTop = 0;
        return true;
    }

    public boolean renderBlockBed(Block p_147773_1_, int p_147773_2_, int p_147773_3_, int p_147773_4_) {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(p_147773_2_, p_147773_3_, p_147773_4_);
        int var7 = BlockBed.func_149895_l(var6);
        boolean var8 = BlockBed.func_149975_b(var6);
        if (Reflector.ForgeBlock_getBedDirection.exists()) {
            var7 = Reflector.callInt(p_147773_1_, Reflector.ForgeBlock_getBedDirection, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_);
        }
        if (Reflector.ForgeBlock_isBedFoot.exists()) {
            var8 = Reflector.callBoolean(p_147773_1_, Reflector.ForgeBlock_isBedFoot, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_);
        }
        float var9 = 0.5f;
        float var10 = 1.0f;
        float var11 = 0.8f;
        float var12 = 0.6f;
        int var25 = p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_);
        var5.setBrightness(var25);
        var5.setColorOpaque_F(var9, var9, var9);
        IIcon var26 = this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 0);
        if (this.overrideBlockTexture != null) {
            var26 = this.overrideBlockTexture;
        }
        double var27 = var26.getMinU();
        double var29 = var26.getMaxU();
        double var31 = var26.getMinV();
        double var33 = var26.getMaxV();
        double var35 = (double)p_147773_2_ + this.renderMinX;
        double var37 = (double)p_147773_2_ + this.renderMaxX;
        double var39 = (double)p_147773_3_ + this.renderMinY + 0.1875;
        double var41 = (double)p_147773_4_ + this.renderMinZ;
        double var43 = (double)p_147773_4_ + this.renderMaxZ;
        var5.addVertexWithUV(var35, var39, var43, var27, var33);
        var5.addVertexWithUV(var35, var39, var41, var27, var31);
        var5.addVertexWithUV(var37, var39, var41, var29, var31);
        var5.addVertexWithUV(var37, var39, var43, var29, var33);
        var5.setBrightness(p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_ + 1, p_147773_4_));
        var5.setColorOpaque_F(var10, var10, var10);
        var26 = this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 1);
        if (this.overrideBlockTexture != null) {
            var26 = this.overrideBlockTexture;
        }
        var27 = var26.getMinU();
        var29 = var26.getMaxU();
        var31 = var26.getMinV();
        var33 = var26.getMaxV();
        var35 = var27;
        var37 = var29;
        var39 = var31;
        var41 = var31;
        var43 = var27;
        double var45 = var29;
        double var47 = var33;
        double var49 = var33;
        if (var7 == 0) {
            var37 = var27;
            var39 = var33;
            var43 = var29;
            var49 = var31;
        } else if (var7 == 2) {
            var35 = var29;
            var41 = var33;
            var45 = var27;
            var47 = var31;
        } else if (var7 == 3) {
            var35 = var29;
            var41 = var33;
            var45 = var27;
            var47 = var31;
            var37 = var27;
            var39 = var33;
            var43 = var29;
            var49 = var31;
        }
        double var51 = (double)p_147773_2_ + this.renderMinX;
        double var53 = (double)p_147773_2_ + this.renderMaxX;
        double var55 = (double)p_147773_3_ + this.renderMaxY;
        double var57 = (double)p_147773_4_ + this.renderMinZ;
        double var59 = (double)p_147773_4_ + this.renderMaxZ;
        var5.addVertexWithUV(var53, var55, var59, var43, var47);
        var5.addVertexWithUV(var53, var55, var57, var35, var39);
        var5.addVertexWithUV(var51, var55, var57, var37, var41);
        var5.addVertexWithUV(var51, var55, var59, var45, var49);
        int var61 = Direction.directionToFacing[var7];
        if (var8) {
            var61 = Direction.directionToFacing[Direction.rotateOpposite[var7]];
        }
        int var62 = 4;
        switch (var7) {
            case 0: {
                var62 = 5;
                break;
            }
            case 1: {
                var62 = 3;
            }
            default: {
                break;
            }
            case 3: {
                var62 = 2;
            }
        }
        if (var61 != 2 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ - 1, 2))) {
            var5.setBrightness(this.renderMinZ > 0.0 ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ - 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = var62 == 2;
            this.renderFaceZNeg(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 2));
        }
        if (var61 != 3 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ + 1, 3))) {
            var5.setBrightness(this.renderMaxZ < 1.0 ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ + 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = var62 == 3;
            this.renderFaceZPos(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 3));
        }
        if (var61 != 4 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_ - 1, p_147773_3_, p_147773_4_, 4))) {
            var5.setBrightness(this.renderMinZ > 0.0 ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_ - 1, p_147773_3_, p_147773_4_));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = var62 == 4;
            this.renderFaceXNeg(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 4));
        }
        if (var61 != 5 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_ + 1, p_147773_3_, p_147773_4_, 5))) {
            var5.setBrightness(this.renderMaxZ < 1.0 ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_ + 1, p_147773_3_, p_147773_4_));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = var62 == 5;
            this.renderFaceXPos(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 5));
        }
        this.flipTexture = false;
        return true;
    }

    public boolean renderBlockBrewingStand(BlockBrewingStand p_147741_1_, int p_147741_2_, int p_147741_3_, int p_147741_4_) {
        this.setRenderBounds(0.4375, 0.0, 0.4375, 0.5625, 0.875, 0.5625);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.setOverrideBlockTexture(p_147741_1_.func_149959_e());
        this.renderAllFaces = true;
        this.setRenderBounds(0.5625, 0.0, 0.3125, 0.9375, 0.125, 0.6875);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.setRenderBounds(0.125, 0.0, 0.0625, 0.5, 0.125, 0.4375);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.setRenderBounds(0.125, 0.0, 0.5625, 0.5, 0.125, 0.9375);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.renderAllFaces = false;
        this.clearOverrideBlockTexture();
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147741_1_.getBlockBrightness(this.blockAccess, p_147741_2_, p_147741_3_, p_147741_4_));
        int var6 = p_147741_1_.colorMultiplier(this.blockAccess, p_147741_2_, p_147741_3_, p_147741_4_);
        float var7 = (float)(var6 >> 16 & 255) / 255.0f;
        float var8 = (float)(var6 >> 8 & 255) / 255.0f;
        float var9 = (float)(var6 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var32 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            float var31 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var32;
            var8 = var31;
            var9 = var12;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        IIcon var321 = this.getBlockIconFromSideAndMetadata(p_147741_1_, 0, 0);
        if (this.hasOverrideBlockTexture()) {
            var321 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var321 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_, -1, var321);
        }
        double var311 = var321.getMinV();
        double var13 = var321.getMaxV();
        int var15 = this.blockAccess.getBlockMetadata(p_147741_2_, p_147741_3_, p_147741_4_);
        int var16 = 0;
        while (var16 < 3) {
            double var17 = (double)var16 * 3.141592653589793 * 2.0 / 3.0 + 1.5707963267948966;
            double var19 = var321.getInterpolatedU(8.0);
            double var21 = var321.getMaxU();
            if ((var15 & 1 << var16) != 0) {
                var21 = var321.getMinU();
            }
            double var23 = (double)p_147741_2_ + 0.5;
            double var25 = (double)p_147741_2_ + 0.5 + Math.sin(var17) * 8.0 / 16.0;
            double var27 = (double)p_147741_4_ + 0.5;
            double var29 = (double)p_147741_4_ + 0.5 + Math.cos(var17) * 8.0 / 16.0;
            var5.addVertexWithUV(var23, p_147741_3_ + 1, var27, var19, var311);
            var5.addVertexWithUV(var23, p_147741_3_ + 0, var27, var19, var13);
            var5.addVertexWithUV(var25, p_147741_3_ + 0, var29, var21, var13);
            var5.addVertexWithUV(var25, p_147741_3_ + 1, var29, var21, var311);
            var5.addVertexWithUV(var25, p_147741_3_ + 1, var29, var21, var311);
            var5.addVertexWithUV(var25, p_147741_3_ + 0, var29, var21, var13);
            var5.addVertexWithUV(var23, p_147741_3_ + 0, var27, var19, var13);
            var5.addVertexWithUV(var23, p_147741_3_ + 1, var27, var19, var311);
            ++var16;
        }
        p_147741_1_.setBlockBoundsForItemRender();
        return true;
    }

    public boolean renderBlockCauldron(BlockCauldron p_147785_1_, int p_147785_2_, int p_147785_3_, int p_147785_4_) {
        float var11;
        this.renderStandardBlock(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_);
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147785_1_.getBlockBrightness(this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_));
        int var6 = p_147785_1_.colorMultiplier(this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_);
        float var7 = (float)(var6 >> 16 & 255) / 255.0f;
        float var8 = (float)(var6 >> 8 & 255) / 255.0f;
        float var9 = (float)(var6 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var15 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            float var16 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var15;
            var8 = var11;
            var9 = var16;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        IIcon var151 = p_147785_1_.getBlockTextureFromSide(2);
        var11 = 0.125f;
        this.renderFaceXPos(p_147785_1_, (float)p_147785_2_ - 1.0f + var11, p_147785_3_, p_147785_4_, var151);
        this.renderFaceXNeg(p_147785_1_, (float)p_147785_2_ + 1.0f - var11, p_147785_3_, p_147785_4_, var151);
        this.renderFaceZPos(p_147785_1_, p_147785_2_, p_147785_3_, (float)p_147785_4_ - 1.0f + var11, var151);
        this.renderFaceZNeg(p_147785_1_, p_147785_2_, p_147785_3_, (float)p_147785_4_ + 1.0f - var11, var151);
        IIcon var161 = BlockCauldron.func_150026_e("inner");
        this.renderFaceYPos(p_147785_1_, p_147785_2_, (float)p_147785_3_ - 1.0f + 0.25f, p_147785_4_, var161);
        this.renderFaceYNeg(p_147785_1_, p_147785_2_, (float)p_147785_3_ + 1.0f - 0.75f, p_147785_4_, var161);
        int var13 = this.blockAccess.getBlockMetadata(p_147785_2_, p_147785_3_, p_147785_4_);
        if (var13 > 0) {
            IIcon var14 = BlockLiquid.func_149803_e("water_still");
            int wc = CustomColorizer.getFluidColor(Blocks.water, this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_);
            float wr = (float)(wc >> 16 & 255) / 255.0f;
            float wg = (float)(wc >> 8 & 255) / 255.0f;
            float wb = (float)(wc & 255) / 255.0f;
            var5.setColorOpaque_F(wr, wg, wb);
            this.renderFaceYPos(p_147785_1_, p_147785_2_, (float)p_147785_3_ - 1.0f + BlockCauldron.func_150025_c(var13), p_147785_4_, var14);
        }
        return true;
    }

    public boolean renderBlockFlowerpot(BlockFlowerPot p_147752_1_, int p_147752_2_, int p_147752_3_, int p_147752_4_) {
        float var11;
        this.renderStandardBlock(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_);
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147752_1_.getBlockBrightness(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_));
        int var6 = p_147752_1_.colorMultiplier(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_);
        IIcon var7 = this.getBlockIconFromSide(p_147752_1_, 0);
        float var8 = (float)(var6 >> 16 & 255) / 255.0f;
        float var9 = (float)(var6 >> 8 & 255) / 255.0f;
        float var10 = (float)(var6 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            float var21 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            float var22 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var21;
            var10 = var22;
        }
        var5.setColorOpaque_F(var8, var9, var10);
        var11 = 0.1865f;
        this.renderFaceXPos(p_147752_1_, (float)p_147752_2_ - 0.5f + var11, p_147752_3_, p_147752_4_, var7);
        this.renderFaceXNeg(p_147752_1_, (float)p_147752_2_ + 0.5f - var11, p_147752_3_, p_147752_4_, var7);
        this.renderFaceZPos(p_147752_1_, p_147752_2_, p_147752_3_, (float)p_147752_4_ - 0.5f + var11, var7);
        this.renderFaceZNeg(p_147752_1_, p_147752_2_, p_147752_3_, (float)p_147752_4_ + 0.5f - var11, var7);
        this.renderFaceYPos(p_147752_1_, p_147752_2_, (float)p_147752_3_ - 0.5f + var11 + 0.1875f, p_147752_4_, this.getBlockIcon(Blocks.dirt));
        TileEntity var211 = this.blockAccess.getTileEntity(p_147752_2_, p_147752_3_, p_147752_4_);
        if (var211 != null && var211 instanceof TileEntityFlowerPot) {
            Item var221 = ((TileEntityFlowerPot)var211).func_145965_a();
            int var14 = ((TileEntityFlowerPot)var211).func_145966_b();
            if (var221 instanceof ItemBlock) {
                Block var15 = Block.getBlockFromItem(var221);
                int var16 = var15.getRenderType();
                float var17 = 0.0f;
                float var18 = 4.0f;
                float var19 = 0.0f;
                var5.addTranslation(var17 / 16.0f, var18 / 16.0f, var19 / 16.0f);
                var6 = var15.colorMultiplier(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_);
                if (var6 != 16777215) {
                    var8 = (float)(var6 >> 16 & 255) / 255.0f;
                    var9 = (float)(var6 >> 8 & 255) / 255.0f;
                    var10 = (float)(var6 & 255) / 255.0f;
                    var5.setColorOpaque_F(var8, var9, var10);
                }
                if (var16 == 1) {
                    this.drawCrossedSquares(this.getBlockIconFromSideAndMetadata(var15, 0, var14), p_147752_2_, p_147752_3_, p_147752_4_, 0.75f);
                } else if (var16 == 13) {
                    this.renderAllFaces = true;
                    float var20 = 0.125f;
                    this.setRenderBounds(0.5f - var20, 0.0, 0.5f - var20, 0.5f + var20, 0.25, 0.5f + var20);
                    this.renderStandardBlock(var15, p_147752_2_, p_147752_3_, p_147752_4_);
                    this.setRenderBounds(0.5f - var20, 0.25, 0.5f - var20, 0.5f + var20, 0.5, 0.5f + var20);
                    this.renderStandardBlock(var15, p_147752_2_, p_147752_3_, p_147752_4_);
                    this.setRenderBounds(0.5f - var20, 0.5, 0.5f - var20, 0.5f + var20, 0.75, 0.5f + var20);
                    this.renderStandardBlock(var15, p_147752_2_, p_147752_3_, p_147752_4_);
                    this.renderAllFaces = false;
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                }
                var5.addTranslation((- var17) / 16.0f, (- var18) / 16.0f, (- var19) / 16.0f);
            }
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147752_2_, p_147752_3_, p_147752_4_)) {
            this.renderSnow(p_147752_2_, p_147752_3_, p_147752_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }

    public boolean renderBlockAnvil(BlockAnvil p_147725_1_, int p_147725_2_, int p_147725_3_, int p_147725_4_) {
        return this.renderBlockAnvilMetadata(p_147725_1_, p_147725_2_, p_147725_3_, p_147725_4_, this.blockAccess.getBlockMetadata(p_147725_2_, p_147725_3_, p_147725_4_));
    }

    public boolean renderBlockAnvilMetadata(BlockAnvil p_147780_1_, int p_147780_2_, int p_147780_3_, int p_147780_4_, int p_147780_5_) {
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147780_1_.getBlockBrightness(this.blockAccess, p_147780_2_, p_147780_3_, p_147780_4_));
        int var7 = p_147780_1_.colorMultiplier(this.blockAccess, p_147780_2_, p_147780_3_, p_147780_4_);
        float var8 = (float)(var7 >> 16 & 255) / 255.0f;
        float var9 = (float)(var7 >> 8 & 255) / 255.0f;
        float var10 = (float)(var7 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        return this.renderBlockAnvilOrient(p_147780_1_, p_147780_2_, p_147780_3_, p_147780_4_, p_147780_5_, false);
    }

    public boolean renderBlockAnvilOrient(BlockAnvil p_147728_1_, int p_147728_2_, int p_147728_3_, int p_147728_4_, int p_147728_5_, boolean p_147728_6_) {
        int var7 = p_147728_6_ ? 0 : p_147728_5_ & 3;
        boolean var8 = false;
        float var9 = 0.0f;
        switch (var7) {
            case 0: {
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                break;
            }
            case 1: {
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                var8 = true;
                break;
            }
            case 2: {
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                break;
            }
            case 3: {
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                var8 = true;
            }
        }
        var9 = this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 0, var9, 0.75f, 0.25f, 0.75f, var8, p_147728_6_, p_147728_5_);
        var9 = this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 1, var9, 0.5f, 0.0625f, 0.625f, var8, p_147728_6_, p_147728_5_);
        var9 = this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 2, var9, 0.25f, 0.3125f, 0.5f, var8, p_147728_6_, p_147728_5_);
        this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 3, var9, 0.625f, 0.375f, 1.0f, var8, p_147728_6_, p_147728_5_);
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return true;
    }

    public float renderBlockAnvilRotate(BlockAnvil p_147737_1_, int p_147737_2_, int p_147737_3_, int p_147737_4_, int p_147737_5_, float p_147737_6_, float p_147737_7_, float p_147737_8_, float p_147737_9_, boolean p_147737_10_, boolean p_147737_11_, int p_147737_12_) {
        if (p_147737_10_) {
            float var14 = p_147737_7_;
            p_147737_7_ = p_147737_9_;
            p_147737_9_ = var14;
        }
        p_147737_1_.field_149833_b = p_147737_5_;
        this.setRenderBounds(0.5f - p_147737_7_, p_147737_6_, 0.5f - p_147737_9_, 0.5f + (p_147737_7_ /= 2.0f), p_147737_6_ + p_147737_8_, 0.5f + (p_147737_9_ /= 2.0f));
        if (p_147737_11_) {
            Tessellator var141 = Tessellator.instance;
            var141.startDrawingQuads();
            var141.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 0, p_147737_12_));
            var141.draw();
            var141.startDrawingQuads();
            var141.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 1, p_147737_12_));
            var141.draw();
            var141.startDrawingQuads();
            var141.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 2, p_147737_12_));
            var141.draw();
            var141.startDrawingQuads();
            var141.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 3, p_147737_12_));
            var141.draw();
            var141.startDrawingQuads();
            var141.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 4, p_147737_12_));
            var141.draw();
            var141.startDrawingQuads();
            var141.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 5, p_147737_12_));
            var141.draw();
        } else {
            this.renderStandardBlock(p_147737_1_, p_147737_2_, p_147737_3_, p_147737_4_);
        }
        return p_147737_6_ + p_147737_8_;
    }

    public boolean renderBlockTorch(Block p_147791_1_, int p_147791_2_, int p_147791_3_, int p_147791_4_) {
        int var5 = this.blockAccess.getBlockMetadata(p_147791_2_, p_147791_3_, p_147791_4_);
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147791_1_.getBlockBrightness(this.blockAccess, p_147791_2_, p_147791_3_, p_147791_4_));
        var6.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        double var7 = 0.4000000059604645;
        double var9 = 0.5 - var7;
        double var11 = 0.20000000298023224;
        if (var5 == 1) {
            this.renderTorchAtAngle(p_147791_1_, (double)p_147791_2_ - var9, (double)p_147791_3_ + var11, p_147791_4_, - var7, 0.0, 0);
        } else if (var5 == 2) {
            this.renderTorchAtAngle(p_147791_1_, (double)p_147791_2_ + var9, (double)p_147791_3_ + var11, p_147791_4_, var7, 0.0, 0);
        } else if (var5 == 3) {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_, (double)p_147791_3_ + var11, (double)p_147791_4_ - var9, 0.0, - var7, 0);
        } else if (var5 == 4) {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_, (double)p_147791_3_ + var11, (double)p_147791_4_ + var9, 0.0, var7, 0);
        } else {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_, p_147791_3_, p_147791_4_, 0.0, 0.0, 0);
            if (p_147791_1_ != Blocks.torch && Config.isBetterSnow() && this.hasSnowNeighbours(p_147791_2_, p_147791_3_, p_147791_4_)) {
                this.renderSnow(p_147791_2_, p_147791_3_, p_147791_4_, Blocks.snow_layer.getBlockBoundsMaxY());
            }
        }
        return true;
    }

    public boolean renderBlockRepeater(BlockRedstoneRepeater p_147759_1_, int p_147759_2_, int p_147759_3_, int p_147759_4_) {
        int var5 = this.blockAccess.getBlockMetadata(p_147759_2_, p_147759_3_, p_147759_4_);
        int var6 = var5 & 3;
        int var7 = (var5 & 12) >> 2;
        Tessellator var8 = Tessellator.instance;
        var8.setBrightness(p_147759_1_.getBlockBrightness(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        double var9 = -0.1875;
        boolean var11 = p_147759_1_.func_149910_g(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_, var5);
        double var12 = 0.0;
        double var14 = 0.0;
        double var16 = 0.0;
        double var18 = 0.0;
        switch (var6) {
            case 0: {
                var18 = -0.3125;
                var14 = BlockRedstoneRepeater.field_149973_b[var7];
                break;
            }
            case 1: {
                var16 = 0.3125;
                var12 = - BlockRedstoneRepeater.field_149973_b[var7];
                break;
            }
            case 2: {
                var18 = 0.3125;
                var14 = - BlockRedstoneRepeater.field_149973_b[var7];
                break;
            }
            case 3: {
                var16 = -0.3125;
                var12 = BlockRedstoneRepeater.field_149973_b[var7];
            }
        }
        if (!var11) {
            this.renderTorchAtAngle(p_147759_1_, (double)p_147759_2_ + var12, (double)p_147759_3_ + var9, (double)p_147759_4_ + var14, 0.0, 0.0, 0);
        } else {
            IIcon var20 = this.getBlockIcon(Blocks.bedrock);
            this.setOverrideBlockTexture(var20);
            float var21 = 2.0f;
            float var22 = 14.0f;
            float var23 = 7.0f;
            float var24 = 9.0f;
            switch (var6) {
                case 1: 
                case 3: {
                    var21 = 7.0f;
                    var22 = 9.0f;
                    var23 = 2.0f;
                    var24 = 14.0f;
                }
            }
            this.setRenderBounds(var21 / 16.0f + (float)var12, 0.125, var23 / 16.0f + (float)var14, var22 / 16.0f + (float)var12, 0.25, var24 / 16.0f + (float)var14);
            double var25 = var20.getInterpolatedU(var21);
            double var27 = var20.getInterpolatedV(var23);
            double var29 = var20.getInterpolatedU(var22);
            double var31 = var20.getInterpolatedV(var24);
            var8.addVertexWithUV((double)((float)p_147759_2_ + var21 / 16.0f) + var12, (float)p_147759_3_ + 0.25f, (double)((float)p_147759_4_ + var23 / 16.0f) + var14, var25, var27);
            var8.addVertexWithUV((double)((float)p_147759_2_ + var21 / 16.0f) + var12, (float)p_147759_3_ + 0.25f, (double)((float)p_147759_4_ + var24 / 16.0f) + var14, var25, var31);
            var8.addVertexWithUV((double)((float)p_147759_2_ + var22 / 16.0f) + var12, (float)p_147759_3_ + 0.25f, (double)((float)p_147759_4_ + var24 / 16.0f) + var14, var29, var31);
            var8.addVertexWithUV((double)((float)p_147759_2_ + var22 / 16.0f) + var12, (float)p_147759_3_ + 0.25f, (double)((float)p_147759_4_ + var23 / 16.0f) + var14, var29, var27);
            this.renderStandardBlock(p_147759_1_, p_147759_2_, p_147759_3_, p_147759_4_);
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
            this.clearOverrideBlockTexture();
        }
        var8.setBrightness(p_147759_1_.getBlockBrightness(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        this.renderTorchAtAngle(p_147759_1_, (double)p_147759_2_ + var16, (double)p_147759_3_ + var9, (double)p_147759_4_ + var18, 0.0, 0.0, 0);
        this.renderBlockRedstoneDiode(p_147759_1_, p_147759_2_, p_147759_3_, p_147759_4_);
        return true;
    }

    public boolean renderBlockRedstoneComparator(BlockRedstoneComparator p_147781_1_, int p_147781_2_, int p_147781_3_, int p_147781_4_) {
        IIcon var18;
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147781_1_.getBlockBrightness(this.blockAccess, p_147781_2_, p_147781_3_, p_147781_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        int var6 = this.blockAccess.getBlockMetadata(p_147781_2_, p_147781_3_, p_147781_4_);
        int var7 = var6 & 3;
        double var8 = 0.0;
        double var10 = -0.1875;
        double var12 = 0.0;
        double var14 = 0.0;
        double var16 = 0.0;
        if (p_147781_1_.func_149969_d(var6)) {
            var18 = Blocks.redstone_torch.getBlockTextureFromSide(0);
        } else {
            var10 -= 0.1875;
            var18 = Blocks.unlit_redstone_torch.getBlockTextureFromSide(0);
        }
        switch (var7) {
            case 0: {
                var12 = -0.3125;
                var16 = 1.0;
                break;
            }
            case 1: {
                var8 = 0.3125;
                var14 = -1.0;
                break;
            }
            case 2: {
                var12 = 0.3125;
                var16 = -1.0;
                break;
            }
            case 3: {
                var8 = -0.3125;
                var14 = 1.0;
            }
        }
        this.renderTorchAtAngle(p_147781_1_, (double)p_147781_2_ + 0.25 * var14 + 0.1875 * var16, (float)p_147781_3_ - 0.1875f, (double)p_147781_4_ + 0.25 * var16 + 0.1875 * var14, 0.0, 0.0, var6);
        this.renderTorchAtAngle(p_147781_1_, (double)p_147781_2_ + 0.25 * var14 + -0.1875 * var16, (float)p_147781_3_ - 0.1875f, (double)p_147781_4_ + 0.25 * var16 + -0.1875 * var14, 0.0, 0.0, var6);
        this.setOverrideBlockTexture(var18);
        this.renderTorchAtAngle(p_147781_1_, (double)p_147781_2_ + var8, (double)p_147781_3_ + var10, (double)p_147781_4_ + var12, 0.0, 0.0, var6);
        this.clearOverrideBlockTexture();
        this.renderBlockRedstoneDiodeMetadata(p_147781_1_, p_147781_2_, p_147781_3_, p_147781_4_, var7);
        return true;
    }

    public boolean renderBlockRedstoneDiode(BlockRedstoneDiode p_147748_1_, int p_147748_2_, int p_147748_3_, int p_147748_4_) {
        Tessellator var5 = Tessellator.instance;
        this.renderBlockRedstoneDiodeMetadata(p_147748_1_, p_147748_2_, p_147748_3_, p_147748_4_, this.blockAccess.getBlockMetadata(p_147748_2_, p_147748_3_, p_147748_4_) & 3);
        return true;
    }

    public void renderBlockRedstoneDiodeMetadata(BlockRedstoneDiode p_147732_1_, int p_147732_2_, int p_147732_3_, int p_147732_4_, int p_147732_5_) {
        this.renderStandardBlock(p_147732_1_, p_147732_2_, p_147732_3_, p_147732_4_);
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147732_1_.getBlockBrightness(this.blockAccess, p_147732_2_, p_147732_3_, p_147732_4_));
        var6.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        int var7 = this.blockAccess.getBlockMetadata(p_147732_2_, p_147732_3_, p_147732_4_);
        IIcon var8 = this.getBlockIconFromSideAndMetadata(p_147732_1_, 1, var7);
        double var9 = var8.getMinU();
        double var11 = var8.getMaxU();
        double var13 = var8.getMinV();
        double var15 = var8.getMaxV();
        double var17 = 0.125;
        double var19 = p_147732_2_ + 1;
        double var21 = p_147732_2_ + 1;
        double var23 = p_147732_2_ + 0;
        double var25 = p_147732_2_ + 0;
        double var27 = p_147732_4_ + 0;
        double var29 = p_147732_4_ + 1;
        double var31 = p_147732_4_ + 1;
        double var33 = p_147732_4_ + 0;
        double var35 = (double)p_147732_3_ + var17;
        if (p_147732_5_ == 2) {
            var19 = var21 = (double)(p_147732_2_ + 0);
            var23 = var25 = (double)(p_147732_2_ + 1);
            var27 = var33 = (double)(p_147732_4_ + 1);
            var29 = var31 = (double)(p_147732_4_ + 0);
        } else if (p_147732_5_ == 3) {
            var19 = var25 = (double)(p_147732_2_ + 0);
            var21 = var23 = (double)(p_147732_2_ + 1);
            var27 = var29 = (double)(p_147732_4_ + 0);
            var31 = var33 = (double)(p_147732_4_ + 1);
        } else if (p_147732_5_ == 1) {
            var19 = var25 = (double)(p_147732_2_ + 1);
            var21 = var23 = (double)(p_147732_2_ + 0);
            var27 = var29 = (double)(p_147732_4_ + 1);
            var31 = var33 = (double)(p_147732_4_ + 0);
        }
        var6.addVertexWithUV(var25, var35, var33, var9, var13);
        var6.addVertexWithUV(var23, var35, var31, var9, var15);
        var6.addVertexWithUV(var21, var35, var29, var11, var15);
        var6.addVertexWithUV(var19, var35, var27, var11, var13);
    }

    public void renderPistonBaseAllFaces(Block p_147804_1_, int p_147804_2_, int p_147804_3_, int p_147804_4_) {
        this.renderAllFaces = true;
        this.renderPistonBase(p_147804_1_, p_147804_2_, p_147804_3_, p_147804_4_, true);
        this.renderAllFaces = false;
    }

    public boolean renderPistonBase(Block p_147731_1_, int p_147731_2_, int p_147731_3_, int p_147731_4_, boolean p_147731_5_) {
        int var6 = this.blockAccess.getBlockMetadata(p_147731_2_, p_147731_3_, p_147731_4_);
        boolean var7 = p_147731_5_ || (var6 & 8) != 0;
        int var8 = BlockPistonBase.func_150076_b(var6);
        float var9 = 0.25f;
        if (var7) {
            switch (var8) {
                case 0: {
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;
                    this.setRenderBounds(0.0, 0.25, 0.0, 1.0, 1.0, 1.0);
                    break;
                }
                case 1: {
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);
                    break;
                }
                case 2: {
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    this.setRenderBounds(0.0, 0.0, 0.25, 1.0, 1.0, 1.0);
                    break;
                }
                case 3: {
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.75);
                    break;
                }
                case 4: {
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    this.setRenderBounds(0.25, 0.0, 0.0, 1.0, 1.0, 1.0);
                    break;
                }
                case 5: {
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
                    this.setRenderBounds(0.0, 0.0, 0.0, 0.75, 1.0, 1.0);
                }
            }
            ((BlockPistonBase)p_147731_1_).func_150070_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
            this.renderStandardBlock(p_147731_1_, p_147731_2_, p_147731_3_, p_147731_4_);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            ((BlockPistonBase)p_147731_1_).func_150070_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
        } else {
            switch (var8) {
                case 0: {
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;
                }
                default: {
                    break;
                }
                case 2: {
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    break;
                }
                case 3: {
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    break;
                }
                case 4: {
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    break;
                }
                case 5: {
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
                }
            }
            this.renderStandardBlock(p_147731_1_, p_147731_2_, p_147731_3_, p_147731_4_);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
        }
        return true;
    }

    public void renderPistonRodUD(double p_147763_1_, double p_147763_3_, double p_147763_5_, double p_147763_7_, double p_147763_9_, double p_147763_11_, float p_147763_13_, double p_147763_14_) {
        IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        Tessellator var17 = Tessellator.instance;
        double var18 = var16.getMinU();
        double var20 = var16.getMinV();
        double var22 = var16.getInterpolatedU(p_147763_14_);
        double var24 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(p_147763_13_, p_147763_13_, p_147763_13_);
        var17.addVertexWithUV(p_147763_1_, p_147763_7_, p_147763_9_, var22, var20);
        var17.addVertexWithUV(p_147763_1_, p_147763_5_, p_147763_9_, var18, var20);
        var17.addVertexWithUV(p_147763_3_, p_147763_5_, p_147763_11_, var18, var24);
        var17.addVertexWithUV(p_147763_3_, p_147763_7_, p_147763_11_, var22, var24);
    }

    public void renderPistonRodSN(double p_147789_1_, double p_147789_3_, double p_147789_5_, double p_147789_7_, double p_147789_9_, double p_147789_11_, float p_147789_13_, double p_147789_14_) {
        IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        Tessellator var17 = Tessellator.instance;
        double var18 = var16.getMinU();
        double var20 = var16.getMinV();
        double var22 = var16.getInterpolatedU(p_147789_14_);
        double var24 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(p_147789_13_, p_147789_13_, p_147789_13_);
        var17.addVertexWithUV(p_147789_1_, p_147789_5_, p_147789_11_, var22, var20);
        var17.addVertexWithUV(p_147789_1_, p_147789_5_, p_147789_9_, var18, var20);
        var17.addVertexWithUV(p_147789_3_, p_147789_7_, p_147789_9_, var18, var24);
        var17.addVertexWithUV(p_147789_3_, p_147789_7_, p_147789_11_, var22, var24);
    }

    public void renderPistonRodEW(double p_147738_1_, double p_147738_3_, double p_147738_5_, double p_147738_7_, double p_147738_9_, double p_147738_11_, float p_147738_13_, double p_147738_14_) {
        IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        Tessellator var17 = Tessellator.instance;
        double var18 = var16.getMinU();
        double var20 = var16.getMinV();
        double var22 = var16.getInterpolatedU(p_147738_14_);
        double var24 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(p_147738_13_, p_147738_13_, p_147738_13_);
        var17.addVertexWithUV(p_147738_3_, p_147738_5_, p_147738_9_, var22, var20);
        var17.addVertexWithUV(p_147738_1_, p_147738_5_, p_147738_9_, var18, var20);
        var17.addVertexWithUV(p_147738_1_, p_147738_7_, p_147738_11_, var18, var24);
        var17.addVertexWithUV(p_147738_3_, p_147738_7_, p_147738_11_, var22, var24);
    }

    public void renderPistonExtensionAllFaces(Block p_147750_1_, int p_147750_2_, int p_147750_3_, int p_147750_4_, boolean p_147750_5_) {
        this.renderAllFaces = true;
        this.renderPistonExtension(p_147750_1_, p_147750_2_, p_147750_3_, p_147750_4_, p_147750_5_);
        this.renderAllFaces = false;
    }

    public boolean renderPistonExtension(Block p_147809_1_, int p_147809_2_, int p_147809_3_, int p_147809_4_, boolean p_147809_5_) {
        int var6 = this.blockAccess.getBlockMetadata(p_147809_2_, p_147809_3_, p_147809_4_);
        int var7 = BlockPistonExtension.func_150085_b(var6);
        float var8 = 0.25f;
        float var9 = 0.375f;
        float var10 = 0.625f;
        float var11 = p_147809_5_ ? 1.0f : 0.5f;
        double var12 = p_147809_5_ ? 16.0 : 8.0;
        switch (var7) {
            case 0: {
                this.uvRotateEast = 3;
                this.uvRotateWest = 3;
                this.uvRotateSouth = 3;
                this.uvRotateNorth = 3;
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodUD((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ + 0.25f, (float)p_147809_3_ + 0.25f + var11, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.625f, 0.8f, var12);
                this.renderPistonRodUD((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ + 0.25f, (float)p_147809_3_ + 0.25f + var11, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.375f, 0.8f, var12);
                this.renderPistonRodUD((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ + 0.25f, (float)p_147809_3_ + 0.25f + var11, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.625f, 0.6f, var12);
                this.renderPistonRodUD((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ + 0.25f, (float)p_147809_3_ + 0.25f + var11, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.375f, 0.6f, var12);
                break;
            }
            case 1: {
                this.setRenderBounds(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodUD((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ - 0.25f + 1.0f - var11, (float)p_147809_3_ - 0.25f + 1.0f, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.625f, 0.8f, var12);
                this.renderPistonRodUD((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ - 0.25f + 1.0f - var11, (float)p_147809_3_ - 0.25f + 1.0f, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.375f, 0.8f, var12);
                this.renderPistonRodUD((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ - 0.25f + 1.0f - var11, (float)p_147809_3_ - 0.25f + 1.0f, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.625f, 0.6f, var12);
                this.renderPistonRodUD((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ - 0.25f + 1.0f - var11, (float)p_147809_3_ - 0.25f + 1.0f, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.375f, 0.6f, var12);
                break;
            }
            case 2: {
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.25);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodSN((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ + 0.25f, (float)p_147809_4_ + 0.25f + var11, 0.6f, var12);
                this.renderPistonRodSN((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ + 0.25f, (float)p_147809_4_ + 0.25f + var11, 0.6f, var12);
                this.renderPistonRodSN((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ + 0.25f, (float)p_147809_4_ + 0.25f + var11, 0.5f, var12);
                this.renderPistonRodSN((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ + 0.25f, (float)p_147809_4_ + 0.25f + var11, 1.0f, var12);
                break;
            }
            case 3: {
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                this.setRenderBounds(0.0, 0.0, 0.75, 1.0, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodSN((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ - 0.25f + 1.0f - var11, (float)p_147809_4_ - 0.25f + 1.0f, 0.6f, var12);
                this.renderPistonRodSN((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ - 0.25f + 1.0f - var11, (float)p_147809_4_ - 0.25f + 1.0f, 0.6f, var12);
                this.renderPistonRodSN((float)p_147809_2_ + 0.375f, (float)p_147809_2_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ - 0.25f + 1.0f - var11, (float)p_147809_4_ - 0.25f + 1.0f, 0.5f, var12);
                this.renderPistonRodSN((float)p_147809_2_ + 0.625f, (float)p_147809_2_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ - 0.25f + 1.0f - var11, (float)p_147809_4_ - 0.25f + 1.0f, 1.0f, var12);
                break;
            }
            case 4: {
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                this.setRenderBounds(0.0, 0.0, 0.0, 0.25, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodEW((float)p_147809_2_ + 0.25f, (float)p_147809_2_ + 0.25f + var11, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.375f, 0.5f, var12);
                this.renderPistonRodEW((float)p_147809_2_ + 0.25f, (float)p_147809_2_ + 0.25f + var11, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.625f, 1.0f, var12);
                this.renderPistonRodEW((float)p_147809_2_ + 0.25f, (float)p_147809_2_ + 0.25f + var11, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.375f, 0.6f, var12);
                this.renderPistonRodEW((float)p_147809_2_ + 0.25f, (float)p_147809_2_ + 0.25f + var11, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.625f, 0.6f, var12);
                break;
            }
            case 5: {
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                this.setRenderBounds(0.75, 0.0, 0.0, 1.0, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodEW((float)p_147809_2_ - 0.25f + 1.0f - var11, (float)p_147809_2_ - 0.25f + 1.0f, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.375f, 0.5f, var12);
                this.renderPistonRodEW((float)p_147809_2_ - 0.25f + 1.0f - var11, (float)p_147809_2_ - 0.25f + 1.0f, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.625f, 1.0f, var12);
                this.renderPistonRodEW((float)p_147809_2_ - 0.25f + 1.0f - var11, (float)p_147809_2_ - 0.25f + 1.0f, (float)p_147809_3_ + 0.375f, (float)p_147809_3_ + 0.625f, (float)p_147809_4_ + 0.375f, (float)p_147809_4_ + 0.375f, 0.6f, var12);
                this.renderPistonRodEW((float)p_147809_2_ - 0.25f + 1.0f - var11, (float)p_147809_2_ - 0.25f + 1.0f, (float)p_147809_3_ + 0.625f, (float)p_147809_3_ + 0.375f, (float)p_147809_4_ + 0.625f, (float)p_147809_4_ + 0.625f, 0.6f, var12);
            }
        }
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return true;
    }

    public boolean renderBlockLever(Block p_147790_1_, int p_147790_2_, int p_147790_3_, int p_147790_4_) {
        int var5 = this.blockAccess.getBlockMetadata(p_147790_2_, p_147790_3_, p_147790_4_);
        int var6 = var5 & 7;
        boolean var7 = (var5 & 8) > 0;
        Tessellator var8 = Tessellator.instance;
        boolean var9 = this.hasOverrideBlockTexture();
        if (!var9) {
            this.setOverrideBlockTexture(this.getBlockIcon(Blocks.cobblestone));
        }
        float var10 = 0.25f;
        float var11 = 0.1875f;
        float var12 = 0.1875f;
        if (var6 == 5) {
            this.setRenderBounds(0.5f - var11, 0.0, 0.5f - var10, 0.5f + var11, var12, 0.5f + var10);
        } else if (var6 == 6) {
            this.setRenderBounds(0.5f - var10, 0.0, 0.5f - var11, 0.5f + var10, var12, 0.5f + var11);
        } else if (var6 == 4) {
            this.setRenderBounds(0.5f - var11, 0.5f - var10, 1.0f - var12, 0.5f + var11, 0.5f + var10, 1.0);
        } else if (var6 == 3) {
            this.setRenderBounds(0.5f - var11, 0.5f - var10, 0.0, 0.5f + var11, 0.5f + var10, var12);
        } else if (var6 == 2) {
            this.setRenderBounds(1.0f - var12, 0.5f - var10, 0.5f - var11, 1.0, 0.5f + var10, 0.5f + var11);
        } else if (var6 == 1) {
            this.setRenderBounds(0.0, 0.5f - var10, 0.5f - var11, var12, 0.5f + var10, 0.5f + var11);
        } else if (var6 == 0) {
            this.setRenderBounds(0.5f - var10, 1.0f - var12, 0.5f - var11, 0.5f + var10, 1.0, 0.5f + var11);
        } else if (var6 == 7) {
            this.setRenderBounds(0.5f - var11, 1.0f - var12, 0.5f - var10, 0.5f + var11, 1.0, 0.5f + var10);
        }
        this.renderStandardBlock(p_147790_1_, p_147790_2_, p_147790_3_, p_147790_4_);
        if (!var9) {
            this.clearOverrideBlockTexture();
        }
        var8.setBrightness(p_147790_1_.getBlockBrightness(this.blockAccess, p_147790_2_, p_147790_3_, p_147790_4_));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        IIcon var13 = this.getBlockIconFromSide(p_147790_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var13 = this.overrideBlockTexture;
        }
        double var14 = var13.getMinU();
        double var16 = var13.getMinV();
        double var18 = var13.getMaxU();
        double var20 = var13.getMaxV();
        Vec3[] var22 = new Vec3[8];
        float var23 = 0.0625f;
        float var24 = 0.0625f;
        float var25 = 0.625f;
        var22[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var23, 0.0, - var24);
        var22[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, 0.0, - var24);
        var22[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, 0.0, var24);
        var22[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var23, 0.0, var24);
        var22[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var23, var25, - var24);
        var22[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, var25, - var24);
        var22[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, var25, var24);
        var22[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var23, var25, var24);
        int var31 = 0;
        while (var31 < 8) {
            if (var7) {
                var22[var31].zCoord -= 0.0625;
                var22[var31].rotateAroundX(0.69813174f);
            } else {
                var22[var31].zCoord += 0.0625;
                var22[var31].rotateAroundX(-0.69813174f);
            }
            if (var6 == 0 || var6 == 7) {
                var22[var31].rotateAroundZ(3.1415927f);
            }
            if (var6 == 6 || var6 == 0) {
                var22[var31].rotateAroundY(1.5707964f);
            }
            if (var6 > 0 && var6 < 5) {
                var22[var31].yCoord -= 0.375;
                var22[var31].rotateAroundX(1.5707964f);
                if (var6 == 4) {
                    var22[var31].rotateAroundY(0.0f);
                }
                if (var6 == 3) {
                    var22[var31].rotateAroundY(3.1415927f);
                }
                if (var6 == 2) {
                    var22[var31].rotateAroundY(1.5707964f);
                }
                if (var6 == 1) {
                    var22[var31].rotateAroundY(-1.5707964f);
                }
                var22[var31].xCoord += (double)p_147790_2_ + 0.5;
                var22[var31].yCoord += (double)((float)p_147790_3_ + 0.5f);
                var22[var31].zCoord += (double)p_147790_4_ + 0.5;
            } else if (var6 != 0 && var6 != 7) {
                var22[var31].xCoord += (double)p_147790_2_ + 0.5;
                var22[var31].yCoord += (double)((float)p_147790_3_ + 0.125f);
                var22[var31].zCoord += (double)p_147790_4_ + 0.5;
            } else {
                var22[var31].xCoord += (double)p_147790_2_ + 0.5;
                var22[var31].yCoord += (double)((float)p_147790_3_ + 0.875f);
                var22[var31].zCoord += (double)p_147790_4_ + 0.5;
            }
            ++var31;
        }
        Vec3 var311 = null;
        Vec3 var27 = null;
        Vec3 var28 = null;
        Vec3 var29 = null;
        int var30 = 0;
        while (var30 < 6) {
            if (var30 == 0) {
                var14 = var13.getInterpolatedU(7.0);
                var16 = var13.getInterpolatedV(6.0);
                var18 = var13.getInterpolatedU(9.0);
                var20 = var13.getInterpolatedV(8.0);
            } else if (var30 == 2) {
                var14 = var13.getInterpolatedU(7.0);
                var16 = var13.getInterpolatedV(6.0);
                var18 = var13.getInterpolatedU(9.0);
                var20 = var13.getMaxV();
            }
            if (var30 == 0) {
                var311 = var22[0];
                var27 = var22[1];
                var28 = var22[2];
                var29 = var22[3];
            } else if (var30 == 1) {
                var311 = var22[7];
                var27 = var22[6];
                var28 = var22[5];
                var29 = var22[4];
            } else if (var30 == 2) {
                var311 = var22[1];
                var27 = var22[0];
                var28 = var22[4];
                var29 = var22[5];
            } else if (var30 == 3) {
                var311 = var22[2];
                var27 = var22[1];
                var28 = var22[5];
                var29 = var22[6];
            } else if (var30 == 4) {
                var311 = var22[3];
                var27 = var22[2];
                var28 = var22[6];
                var29 = var22[7];
            } else if (var30 == 5) {
                var311 = var22[0];
                var27 = var22[3];
                var28 = var22[7];
                var29 = var22[4];
            }
            var8.addVertexWithUV(var311.xCoord, var311.yCoord, var311.zCoord, var14, var20);
            var8.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, var18, var20);
            var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var18, var16);
            var8.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var14, var16);
            ++var30;
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147790_2_, p_147790_3_, p_147790_4_)) {
            this.renderSnow(p_147790_2_, p_147790_3_, p_147790_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }

    public boolean renderBlockTripWireSource(Block p_147723_1_, int p_147723_2_, int p_147723_3_, int p_147723_4_) {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(p_147723_2_, p_147723_3_, p_147723_4_);
        int var7 = var6 & 3;
        boolean var8 = (var6 & 4) == 4;
        boolean var9 = (var6 & 8) == 8;
        boolean var10 = !World.doesBlockHaveSolidTopSurface(this.blockAccess, p_147723_2_, p_147723_3_ - 1, p_147723_4_);
        boolean var11 = this.hasOverrideBlockTexture();
        if (!var11) {
            this.setOverrideBlockTexture(this.getBlockIcon(Blocks.planks));
        }
        float var12 = 0.25f;
        float var13 = 0.125f;
        float var14 = 0.125f;
        float var15 = 0.3f - var12;
        float var16 = 0.3f + var12;
        if (var7 == 2) {
            this.setRenderBounds(0.5f - var13, var15, 1.0f - var14, 0.5f + var13, var16, 1.0);
        } else if (var7 == 0) {
            this.setRenderBounds(0.5f - var13, var15, 0.0, 0.5f + var13, var16, var14);
        } else if (var7 == 1) {
            this.setRenderBounds(1.0f - var14, var15, 0.5f - var13, 1.0, var16, 0.5f + var13);
        } else if (var7 == 3) {
            this.setRenderBounds(0.0, var15, 0.5f - var13, var14, var16, 0.5f + var13);
        }
        this.renderStandardBlock(p_147723_1_, p_147723_2_, p_147723_3_, p_147723_4_);
        if (!var11) {
            this.clearOverrideBlockTexture();
        }
        var5.setBrightness(p_147723_1_.getBlockBrightness(this.blockAccess, p_147723_2_, p_147723_3_, p_147723_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        IIcon var17 = this.getBlockIconFromSide(p_147723_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var17 = this.overrideBlockTexture;
        }
        double var18 = var17.getMinU();
        double var20 = var17.getMinV();
        double var22 = var17.getMaxU();
        double var24 = var17.getMaxV();
        Vec3[] var26 = new Vec3[8];
        float var27 = 0.046875f;
        float var28 = 0.046875f;
        float var29 = 0.3125f;
        var26[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var27, 0.0, - var28);
        var26[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, 0.0, - var28);
        var26[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, 0.0, var28);
        var26[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var27, 0.0, var28);
        var26[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var27, var29, - var28);
        var26[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, var29, - var28);
        var26[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, var29, var28);
        var26[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var27, var29, var28);
        int var60 = 0;
        while (var60 < 8) {
            var26[var60].zCoord += 0.0625;
            if (var9) {
                var26[var60].rotateAroundX(0.5235988f);
                var26[var60].yCoord -= 0.4375;
            } else if (var8) {
                var26[var60].rotateAroundX(0.08726647f);
                var26[var60].yCoord -= 0.4375;
            } else {
                var26[var60].rotateAroundX(-0.69813174f);
                var26[var60].yCoord -= 0.375;
            }
            var26[var60].rotateAroundX(1.5707964f);
            if (var7 == 2) {
                var26[var60].rotateAroundY(0.0f);
            }
            if (var7 == 0) {
                var26[var60].rotateAroundY(3.1415927f);
            }
            if (var7 == 1) {
                var26[var60].rotateAroundY(1.5707964f);
            }
            if (var7 == 3) {
                var26[var60].rotateAroundY(-1.5707964f);
            }
            var26[var60].xCoord += (double)p_147723_2_ + 0.5;
            var26[var60].yCoord += (double)((float)p_147723_3_ + 0.3125f);
            var26[var60].zCoord += (double)p_147723_4_ + 0.5;
            ++var60;
        }
        Vec3 var601 = null;
        Vec3 var31 = null;
        Vec3 var32 = null;
        Vec3 var33 = null;
        int var34 = 7;
        int var35 = 9;
        int var36 = 9;
        int var37 = 16;
        int var61 = 0;
        while (var61 < 6) {
            if (var61 == 0) {
                var601 = var26[0];
                var31 = var26[1];
                var32 = var26[2];
                var33 = var26[3];
                var18 = var17.getInterpolatedU(var34);
                var20 = var17.getInterpolatedV(var36);
                var22 = var17.getInterpolatedU(var35);
                var24 = var17.getInterpolatedV(var36 + 2);
            } else if (var61 == 1) {
                var601 = var26[7];
                var31 = var26[6];
                var32 = var26[5];
                var33 = var26[4];
            } else if (var61 == 2) {
                var601 = var26[1];
                var31 = var26[0];
                var32 = var26[4];
                var33 = var26[5];
                var18 = var17.getInterpolatedU(var34);
                var20 = var17.getInterpolatedV(var36);
                var22 = var17.getInterpolatedU(var35);
                var24 = var17.getInterpolatedV(var37);
            } else if (var61 == 3) {
                var601 = var26[2];
                var31 = var26[1];
                var32 = var26[5];
                var33 = var26[6];
            } else if (var61 == 4) {
                var601 = var26[3];
                var31 = var26[2];
                var32 = var26[6];
                var33 = var26[7];
            } else if (var61 == 5) {
                var601 = var26[0];
                var31 = var26[3];
                var32 = var26[7];
                var33 = var26[4];
            }
            var5.addVertexWithUV(var601.xCoord, var601.yCoord, var601.zCoord, var18, var24);
            var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, var22, var24);
            var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var22, var20);
            var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var18, var20);
            ++var61;
        }
        float var611 = 0.09375f;
        float var39 = 0.09375f;
        float var40 = 0.03125f;
        var26[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var611, 0.0, - var39);
        var26[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, 0.0, - var39);
        var26[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, 0.0, var39);
        var26[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var611, 0.0, var39);
        var26[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var611, var40, - var39);
        var26[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, var40, - var39);
        var26[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, var40, var39);
        var26[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(- var611, var40, var39);
        int var62 = 0;
        while (var62 < 8) {
            var26[var62].zCoord += 0.21875;
            if (var9) {
                var26[var62].yCoord -= 0.09375;
                var26[var62].zCoord -= 0.1625;
                var26[var62].rotateAroundX(0.0f);
            } else if (var8) {
                var26[var62].yCoord += 0.015625;
                var26[var62].zCoord -= 0.171875;
                var26[var62].rotateAroundX(0.17453294f);
            } else {
                var26[var62].rotateAroundX(0.87266463f);
            }
            if (var7 == 2) {
                var26[var62].rotateAroundY(0.0f);
            }
            if (var7 == 0) {
                var26[var62].rotateAroundY(3.1415927f);
            }
            if (var7 == 1) {
                var26[var62].rotateAroundY(1.5707964f);
            }
            if (var7 == 3) {
                var26[var62].rotateAroundY(-1.5707964f);
            }
            var26[var62].xCoord += (double)p_147723_2_ + 0.5;
            var26[var62].yCoord += (double)((float)p_147723_3_ + 0.3125f);
            var26[var62].zCoord += (double)p_147723_4_ + 0.5;
            ++var62;
        }
        int var621 = 5;
        int var42 = 11;
        int var43 = 3;
        int var44 = 9;
        int var63 = 0;
        while (var63 < 6) {
            if (var63 == 0) {
                var601 = var26[0];
                var31 = var26[1];
                var32 = var26[2];
                var33 = var26[3];
                var18 = var17.getInterpolatedU(var621);
                var20 = var17.getInterpolatedV(var43);
                var22 = var17.getInterpolatedU(var42);
                var24 = var17.getInterpolatedV(var44);
            } else if (var63 == 1) {
                var601 = var26[7];
                var31 = var26[6];
                var32 = var26[5];
                var33 = var26[4];
            } else if (var63 == 2) {
                var601 = var26[1];
                var31 = var26[0];
                var32 = var26[4];
                var33 = var26[5];
                var18 = var17.getInterpolatedU(var621);
                var20 = var17.getInterpolatedV(var43);
                var22 = var17.getInterpolatedU(var42);
                var24 = var17.getInterpolatedV(var43 + 2);
            } else if (var63 == 3) {
                var601 = var26[2];
                var31 = var26[1];
                var32 = var26[5];
                var33 = var26[6];
            } else if (var63 == 4) {
                var601 = var26[3];
                var31 = var26[2];
                var32 = var26[6];
                var33 = var26[7];
            } else if (var63 == 5) {
                var601 = var26[0];
                var31 = var26[3];
                var32 = var26[7];
                var33 = var26[4];
            }
            var5.addVertexWithUV(var601.xCoord, var601.yCoord, var601.zCoord, var18, var24);
            var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, var22, var24);
            var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var22, var20);
            var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var18, var20);
            ++var63;
        }
        if (var8) {
            double var631 = var26[0].yCoord;
            float var47 = 0.03125f;
            float var48 = 0.5f - var47 / 2.0f;
            float var49 = var48 + var47;
            double var50 = var17.getMinU();
            double var52 = var17.getInterpolatedV(var8 ? 2.0 : 0.0);
            double var54 = var17.getMaxU();
            double var56 = var17.getInterpolatedV(var8 ? 4.0 : 2.0);
            double var58 = (double)(var10 ? 3.5f : 1.5f) / 16.0;
            var5.setColorOpaque_F(0.75f, 0.75f, 0.75f);
            if (var7 == 2) {
                var5.addVertexWithUV((float)p_147723_2_ + var48, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.25, var50, var52);
                var5.addVertexWithUV((float)p_147723_2_ + var49, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.25, var50, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var49, (double)p_147723_3_ + var58, p_147723_4_, var54, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var48, (double)p_147723_3_ + var58, p_147723_4_, var54, var52);
                var5.addVertexWithUV((float)p_147723_2_ + var48, var631, (double)p_147723_4_ + 0.5, var50, var52);
                var5.addVertexWithUV((float)p_147723_2_ + var49, var631, (double)p_147723_4_ + 0.5, var50, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var49, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.25, var54, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var48, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.25, var54, var52);
            } else if (var7 == 0) {
                var5.addVertexWithUV((float)p_147723_2_ + var48, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.75, var50, var52);
                var5.addVertexWithUV((float)p_147723_2_ + var49, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.75, var50, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var49, var631, (double)p_147723_4_ + 0.5, var54, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var48, var631, (double)p_147723_4_ + 0.5, var54, var52);
                var5.addVertexWithUV((float)p_147723_2_ + var48, (double)p_147723_3_ + var58, p_147723_4_ + 1, var50, var52);
                var5.addVertexWithUV((float)p_147723_2_ + var49, (double)p_147723_3_ + var58, p_147723_4_ + 1, var50, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var49, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.75, var54, var56);
                var5.addVertexWithUV((float)p_147723_2_ + var48, (double)p_147723_3_ + var58, (double)p_147723_4_ + 0.75, var54, var52);
            } else if (var7 == 1) {
                var5.addVertexWithUV(p_147723_2_, (double)p_147723_3_ + var58, (float)p_147723_4_ + var49, var50, var56);
                var5.addVertexWithUV((double)p_147723_2_ + 0.25, (double)p_147723_3_ + var58, (float)p_147723_4_ + var49, var54, var56);
                var5.addVertexWithUV((double)p_147723_2_ + 0.25, (double)p_147723_3_ + var58, (float)p_147723_4_ + var48, var54, var52);
                var5.addVertexWithUV(p_147723_2_, (double)p_147723_3_ + var58, (float)p_147723_4_ + var48, var50, var52);
                var5.addVertexWithUV((double)p_147723_2_ + 0.25, (double)p_147723_3_ + var58, (float)p_147723_4_ + var49, var50, var56);
                var5.addVertexWithUV((double)p_147723_2_ + 0.5, var631, (float)p_147723_4_ + var49, var54, var56);
                var5.addVertexWithUV((double)p_147723_2_ + 0.5, var631, (float)p_147723_4_ + var48, var54, var52);
                var5.addVertexWithUV((double)p_147723_2_ + 0.25, (double)p_147723_3_ + var58, (float)p_147723_4_ + var48, var50, var52);
            } else {
                var5.addVertexWithUV((double)p_147723_2_ + 0.5, var631, (float)p_147723_4_ + var49, var50, var56);
                var5.addVertexWithUV((double)p_147723_2_ + 0.75, (double)p_147723_3_ + var58, (float)p_147723_4_ + var49, var54, var56);
                var5.addVertexWithUV((double)p_147723_2_ + 0.75, (double)p_147723_3_ + var58, (float)p_147723_4_ + var48, var54, var52);
                var5.addVertexWithUV((double)p_147723_2_ + 0.5, var631, (float)p_147723_4_ + var48, var50, var52);
                var5.addVertexWithUV((double)p_147723_2_ + 0.75, (double)p_147723_3_ + var58, (float)p_147723_4_ + var49, var50, var56);
                var5.addVertexWithUV(p_147723_2_ + 1, (double)p_147723_3_ + var58, (float)p_147723_4_ + var49, var54, var56);
                var5.addVertexWithUV(p_147723_2_ + 1, (double)p_147723_3_ + var58, (float)p_147723_4_ + var48, var54, var52);
                var5.addVertexWithUV((double)p_147723_2_ + 0.75, (double)p_147723_3_ + var58, (float)p_147723_4_ + var48, var50, var52);
            }
        }
        return true;
    }

    public boolean renderBlockTripWire(Block p_147756_1_, int p_147756_2_, int p_147756_3_, int p_147756_4_) {
        boolean var9;
        Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147756_1_, 0);
        int var7 = this.blockAccess.getBlockMetadata(p_147756_2_, p_147756_3_, p_147756_4_);
        boolean var8 = (var7 & 4) == 4;
        boolean bl = var9 = (var7 & 2) == 2;
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        var5.setBrightness(p_147756_1_.getBlockBrightness(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        double var10 = var6.getMinU();
        double var12 = var6.getInterpolatedV(var8 ? 2.0 : 0.0);
        double var14 = var6.getMaxU();
        double var16 = var6.getInterpolatedV(var8 ? 4.0 : 2.0);
        double var18 = (double)(var9 ? 3.5f : 1.5f) / 16.0;
        boolean var20 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 1);
        boolean var21 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 3);
        boolean var22 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 2);
        boolean var23 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 0);
        float var24 = 0.03125f;
        float var25 = 0.5f - var24 / 2.0f;
        float var26 = var25 + var24;
        if (!(var22 || var21 || var23 || var20)) {
            var22 = true;
            var23 = true;
        }
        if (var22) {
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var10, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, p_147756_4_, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, p_147756_4_, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, p_147756_4_, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, p_147756_4_, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var10, var12);
        }
        if (var22 || var23 && !var21 && !var20) {
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var10, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.25, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var10, var12);
        }
        if (var23 || var22 && !var21 && !var20) {
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var10, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.5, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var10, var12);
        }
        if (var23) {
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, p_147756_4_ + 1, var10, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, p_147756_4_ + 1, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var14, var12);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, (double)p_147756_4_ + 0.75, var14, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var26, (double)p_147756_3_ + var18, p_147756_4_ + 1, var10, var16);
            var5.addVertexWithUV((float)p_147756_2_ + var25, (double)p_147756_3_ + var18, p_147756_4_ + 1, var10, var12);
        }
        if (var20) {
            var5.addVertexWithUV(p_147756_2_, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV(p_147756_2_, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV(p_147756_2_, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV(p_147756_2_, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
        }
        if (var20 || var21 && !var22 && !var23) {
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.25, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
        }
        if (var21 || var20 && !var22 && !var23) {
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.5, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
        }
        if (var21) {
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
            var5.addVertexWithUV(p_147756_2_ + 1, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV(p_147756_2_ + 1, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var10, var12);
            var5.addVertexWithUV(p_147756_2_ + 1, (double)p_147756_3_ + var18, (float)p_147756_4_ + var25, var14, var12);
            var5.addVertexWithUV(p_147756_2_ + 1, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var14, var16);
            var5.addVertexWithUV((double)p_147756_2_ + 0.75, (double)p_147756_3_ + var18, (float)p_147756_4_ + var26, var10, var16);
        }
        return true;
    }

    public boolean renderBlockFire(BlockFire p_147801_1_, int p_147801_2_, int p_147801_3_, int p_147801_4_) {
        Tessellator var5 = Tessellator.instance;
        IIcon var6 = p_147801_1_.func_149840_c(0);
        IIcon var7 = p_147801_1_.func_149840_c(1);
        IIcon var8 = var6;
        if (this.hasOverrideBlockTexture()) {
            var8 = this.overrideBlockTexture;
        }
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        var5.setBrightness(p_147801_1_.getBlockBrightness(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_));
        double var9 = var8.getMinU();
        double var11 = var8.getMinV();
        double var13 = var8.getMaxU();
        double var15 = var8.getMaxV();
        float var17 = 1.4f;
        if (!World.doesBlockHaveSolidTopSurface(this.blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_) && !Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_)) {
            double var20;
            float var36 = 0.2f;
            float var19 = 0.0625f;
            if ((p_147801_2_ + p_147801_3_ + p_147801_4_ & 1) == 1) {
                var9 = var7.getMinU();
                var11 = var7.getMinV();
                var13 = var7.getMaxU();
                var15 = var7.getMaxV();
            }
            if ((p_147801_2_ / 2 + p_147801_3_ / 2 + p_147801_4_ / 2 & 1) == 1) {
                var20 = var13;
                var13 = var9;
                var9 = var20;
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_ - 1, p_147801_3_, p_147801_4_)) {
                var5.addVertexWithUV((float)p_147801_2_ + var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1, var13, var15);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var9, var15);
                var5.addVertexWithUV((float)p_147801_2_ + var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
                var5.addVertexWithUV((float)p_147801_2_ + var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var9, var15);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1, var13, var15);
                var5.addVertexWithUV((float)p_147801_2_ + var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_ + 1, p_147801_3_, p_147801_4_)) {
                var5.addVertexWithUV((float)(p_147801_2_ + 1) - var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var9, var15);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1, var13, var15);
                var5.addVertexWithUV((float)(p_147801_2_ + 1) - var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
                var5.addVertexWithUV((float)(p_147801_2_ + 1) - var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1, var13, var15);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var9, var15);
                var5.addVertexWithUV((float)(p_147801_2_ + 1) - var36, (float)p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ - 1)) {
                var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17 + var19, (float)p_147801_4_ + var36, var13, var11);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var13, var15);
                var5.addVertexWithUV(p_147801_2_ + 1, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var9, var15);
                var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17 + var19, (float)p_147801_4_ + var36, var9, var11);
                var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17 + var19, (float)p_147801_4_ + var36, var9, var11);
                var5.addVertexWithUV(p_147801_2_ + 1, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var9, var15);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 0, var13, var15);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17 + var19, (float)p_147801_4_ + var36, var13, var11);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ + 1)) {
                var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17 + var19, (float)(p_147801_4_ + 1) - var36, var9, var11);
                var5.addVertexWithUV(p_147801_2_ + 1, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1 - 0, var9, var15);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1 - 0, var13, var15);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17 + var19, (float)(p_147801_4_ + 1) - var36, var13, var11);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17 + var19, (float)(p_147801_4_ + 1) - var36, var13, var11);
                var5.addVertexWithUV(p_147801_2_ + 0, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1 - 0, var13, var15);
                var5.addVertexWithUV(p_147801_2_ + 1, (float)(p_147801_3_ + 0) + var19, p_147801_4_ + 1 - 0, var9, var15);
                var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17 + var19, (float)(p_147801_4_ + 1) - var36, var9, var11);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_ + 1, p_147801_4_)) {
                var20 = (double)p_147801_2_ + 0.5 + 0.5;
                double var22 = (double)p_147801_2_ + 0.5 - 0.5;
                double var24 = (double)p_147801_4_ + 0.5 + 0.5;
                double var26 = (double)p_147801_4_ + 0.5 - 0.5;
                double var28 = (double)p_147801_2_ + 0.5 - 0.5;
                double var30 = (double)p_147801_2_ + 0.5 + 0.5;
                double var32 = (double)p_147801_4_ + 0.5 - 0.5;
                double var34 = (double)p_147801_4_ + 0.5 + 0.5;
                var9 = var6.getMinU();
                var11 = var6.getMinV();
                var13 = var6.getMaxU();
                var15 = var6.getMaxV();
                var17 = -0.2f;
                if ((p_147801_2_ + ++p_147801_3_ + p_147801_4_ & 1) == 0) {
                    var5.addVertexWithUV(var28, (float)p_147801_3_ + var17, p_147801_4_ + 0, var13, var11);
                    var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 0, var13, var15);
                    var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 1, var9, var15);
                    var5.addVertexWithUV(var28, (float)p_147801_3_ + var17, p_147801_4_ + 1, var9, var11);
                    var9 = var7.getMinU();
                    var11 = var7.getMinV();
                    var13 = var7.getMaxU();
                    var15 = var7.getMaxV();
                    var5.addVertexWithUV(var30, (float)p_147801_3_ + var17, p_147801_4_ + 1, var13, var11);
                    var5.addVertexWithUV(var22, p_147801_3_ + 0, p_147801_4_ + 1, var13, var15);
                    var5.addVertexWithUV(var22, p_147801_3_ + 0, p_147801_4_ + 0, var9, var15);
                    var5.addVertexWithUV(var30, (float)p_147801_3_ + var17, p_147801_4_ + 0, var9, var11);
                } else {
                    var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17, var34, var13, var11);
                    var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var26, var13, var15);
                    var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var26, var9, var15);
                    var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17, var34, var9, var11);
                    var9 = var7.getMinU();
                    var11 = var7.getMinV();
                    var13 = var7.getMaxU();
                    var15 = var7.getMaxV();
                    var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17, var32, var13, var11);
                    var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var24, var13, var15);
                    var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var24, var9, var15);
                    var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17, var32, var9, var11);
                }
            }
        } else {
            double var18 = (double)p_147801_2_ + 0.5 + 0.2;
            double var20 = (double)p_147801_2_ + 0.5 - 0.2;
            double var22 = (double)p_147801_4_ + 0.5 + 0.2;
            double var24 = (double)p_147801_4_ + 0.5 - 0.2;
            double var26 = (double)p_147801_2_ + 0.5 - 0.3;
            double var28 = (double)p_147801_2_ + 0.5 + 0.3;
            double var30 = (double)p_147801_4_ + 0.5 - 0.3;
            double var32 = (double)p_147801_4_ + 0.5 + 0.3;
            var5.addVertexWithUV(var26, (float)p_147801_3_ + var17, p_147801_4_ + 1, var13, var11);
            var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 1, var13, var15);
            var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 0, var9, var15);
            var5.addVertexWithUV(var26, (float)p_147801_3_ + var17, p_147801_4_ + 0, var9, var11);
            var5.addVertexWithUV(var28, (float)p_147801_3_ + var17, p_147801_4_ + 0, var13, var11);
            var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 0, var13, var15);
            var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 1, var9, var15);
            var5.addVertexWithUV(var28, (float)p_147801_3_ + var17, p_147801_4_ + 1, var9, var11);
            var9 = var7.getMinU();
            var11 = var7.getMinV();
            var13 = var7.getMaxU();
            var15 = var7.getMaxV();
            var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17, var32, var13, var11);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var24, var13, var15);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var24, var9, var15);
            var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17, var32, var9, var11);
            var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17, var30, var13, var11);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var22, var13, var15);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var22, var9, var15);
            var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17, var30, var9, var11);
            var18 = (double)p_147801_2_ + 0.5 - 0.5;
            var20 = (double)p_147801_2_ + 0.5 + 0.5;
            var22 = (double)p_147801_4_ + 0.5 - 0.5;
            var24 = (double)p_147801_4_ + 0.5 + 0.5;
            var26 = (double)p_147801_2_ + 0.5 - 0.4;
            var28 = (double)p_147801_2_ + 0.5 + 0.4;
            var30 = (double)p_147801_4_ + 0.5 - 0.4;
            var32 = (double)p_147801_4_ + 0.5 + 0.4;
            var5.addVertexWithUV(var26, (float)p_147801_3_ + var17, p_147801_4_ + 0, var9, var11);
            var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 0, var9, var15);
            var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 1, var13, var15);
            var5.addVertexWithUV(var26, (float)p_147801_3_ + var17, p_147801_4_ + 1, var13, var11);
            var5.addVertexWithUV(var28, (float)p_147801_3_ + var17, p_147801_4_ + 1, var9, var11);
            var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 1, var9, var15);
            var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 0, var13, var15);
            var5.addVertexWithUV(var28, (float)p_147801_3_ + var17, p_147801_4_ + 0, var13, var11);
            var9 = var6.getMinU();
            var11 = var6.getMinV();
            var13 = var6.getMaxU();
            var15 = var6.getMaxV();
            var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17, var32, var9, var11);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var24, var9, var15);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var24, var13, var15);
            var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17, var32, var13, var11);
            var5.addVertexWithUV(p_147801_2_ + 1, (float)p_147801_3_ + var17, var30, var9, var11);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var22, var9, var15);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var22, var13, var15);
            var5.addVertexWithUV(p_147801_2_ + 0, (float)p_147801_3_ + var17, var30, var13, var11);
        }
        return true;
    }

    public boolean renderBlockRedstoneWire(Block p_147788_1_, int p_147788_2_, int p_147788_3_, int p_147788_4_) {
        int rsColor;
        boolean var22;
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(p_147788_2_, p_147788_3_, p_147788_4_);
        IIcon var7 = BlockRedstoneWire.func_150173_e("cross");
        IIcon var8 = BlockRedstoneWire.func_150173_e("line");
        IIcon var9 = BlockRedstoneWire.func_150173_e("cross_overlay");
        IIcon var10 = BlockRedstoneWire.func_150173_e("line_overlay");
        var5.setBrightness(p_147788_1_.getBlockBrightness(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_));
        float var11 = (float)var6 / 15.0f;
        float var12 = var11 * 0.6f + 0.4f;
        if (var6 == 0) {
            var12 = 0.3f;
        }
        float var13 = var11 * var11 * 0.7f - 0.5f;
        float var14 = var11 * var11 * 0.6f - 0.7f;
        if (var13 < 0.0f) {
            var13 = 0.0f;
        }
        if (var14 < 0.0f) {
            var14 = 0.0f;
        }
        if ((rsColor = CustomColorizer.getRedstoneColor(var6)) != -1) {
            int var15 = rsColor >> 16 & 255;
            int green = rsColor >> 8 & 255;
            int var17 = rsColor & 255;
            var12 = (float)var15 / 255.0f;
            var13 = (float)green / 255.0f;
            var14 = (float)var17 / 255.0f;
        }
        var5.setColorOpaque_F(var12, var13, var14);
        double var151 = 0.015625;
        double var171 = 0.015625;
        boolean var19 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_, p_147788_4_, 1) || !this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_ - 1, p_147788_4_, -1);
        boolean var20 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_, p_147788_4_, 3) || !this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_ - 1, p_147788_4_, -1);
        boolean var21 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_ - 1, 2) || !this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ - 1, p_147788_4_ - 1, -1);
        boolean bl = var22 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_ + 1, 0) || !this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ - 1, p_147788_4_ + 1, -1);
        if (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_).isBlockNormalCube()) {
            if (this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_ + 1, p_147788_4_, -1)) {
                var19 = true;
            }
            if (this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_ + 1, p_147788_4_, -1)) {
                var20 = true;
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ + 1, p_147788_4_ - 1, -1)) {
                var21 = true;
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ + 1, p_147788_4_ + 1, -1)) {
                var22 = true;
            }
        }
        float var23 = p_147788_2_ + 0;
        float var24 = p_147788_2_ + 1;
        float var25 = p_147788_4_ + 0;
        float var26 = p_147788_4_ + 1;
        boolean var27 = false;
        if ((var19 || var20) && !var21 && !var22) {
            var27 = true;
        }
        if ((var21 || var22) && !var20 && !var19) {
            var27 = true;
        }
        if (!var27) {
            int var33 = 0;
            int var29 = 0;
            int var30 = 16;
            int var31 = 16;
            boolean var32 = true;
            if (!var19) {
                var23 += 0.3125f;
            }
            if (!var19) {
                var33 += 5;
            }
            if (!var20) {
                var24 -= 0.3125f;
            }
            if (!var20) {
                var30 -= 5;
            }
            if (!var21) {
                var25 += 0.3125f;
            }
            if (!var21) {
                var29 += 5;
            }
            if (!var22) {
                var26 -= 0.3125f;
            }
            if (!var22) {
                var31 -= 5;
            }
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var26, var7.getInterpolatedU(var30), var7.getInterpolatedV(var31));
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var25, var7.getInterpolatedU(var30), var7.getInterpolatedV(var29));
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var25, var7.getInterpolatedU(var33), var7.getInterpolatedV(var29));
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var26, var7.getInterpolatedU(var33), var7.getInterpolatedV(var31));
            var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var26, var9.getInterpolatedU(var30), var9.getInterpolatedV(var31));
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var25, var9.getInterpolatedU(var30), var9.getInterpolatedV(var29));
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var25, var9.getInterpolatedU(var33), var9.getInterpolatedV(var29));
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var26, var9.getInterpolatedU(var33), var9.getInterpolatedV(var31));
        } else if (var27) {
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var26, var8.getMaxU(), var8.getMaxV());
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var25, var8.getMaxU(), var8.getMinV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var25, var8.getMinU(), var8.getMinV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var26, var8.getMinU(), var8.getMaxV());
            var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var26, var10.getMaxU(), var10.getMaxV());
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var25, var10.getMaxU(), var10.getMinV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var25, var10.getMinU(), var10.getMinV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var26, var10.getMinU(), var10.getMaxV());
        } else {
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var26, var8.getMaxU(), var8.getMaxV());
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var25, var8.getMinU(), var8.getMaxV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var25, var8.getMinU(), var8.getMinV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var26, var8.getMaxU(), var8.getMinV());
            var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var26, var10.getMaxU(), var10.getMaxV());
            var5.addVertexWithUV(var24, (double)p_147788_3_ + 0.015625, var25, var10.getMinU(), var10.getMaxV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var25, var10.getMinU(), var10.getMinV());
            var5.addVertexWithUV(var23, (double)p_147788_3_ + 0.015625, var26, var10.getMaxU(), var10.getMinV());
        }
        if (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_).isBlockNormalCube()) {
            float var331 = 0.021875f;
            if (this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_ + 1, p_147788_4_) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 1, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var8.getMinU(), var8.getMinV());
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 0, var8.getMaxU(), var8.getMaxV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 1, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var10.getMinU(), var10.getMinV());
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV((double)p_147788_2_ + 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 0, var10.getMaxU(), var10.getMaxV());
            }
            if (this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_ + 1, p_147788_4_) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 1, var8.getMaxU(), var8.getMaxV());
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 0, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var8.getMinU(), var8.getMinV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 1, var10.getMaxU(), var10.getMaxV());
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, (float)(p_147788_3_ + 1) + 0.021875f, p_147788_4_ + 0, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV((double)(p_147788_2_ + 1) - 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var10.getMinU(), var10.getMinV());
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_ - 1) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, (double)p_147788_4_ + 0.015625, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1, (float)(p_147788_3_ + 1) + 0.021875f, (double)p_147788_4_ + 0.015625, var8.getMaxU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, (float)(p_147788_3_ + 1) + 0.021875f, (double)p_147788_4_ + 0.015625, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, (double)p_147788_4_ + 0.015625, var8.getMinU(), var8.getMinV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, (double)p_147788_4_ + 0.015625, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1, (float)(p_147788_3_ + 1) + 0.021875f, (double)p_147788_4_ + 0.015625, var10.getMaxU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, (float)(p_147788_3_ + 1) + 0.021875f, (double)p_147788_4_ + 0.015625, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, (double)p_147788_4_ + 0.015625, var10.getMinU(), var10.getMinV());
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_ + 1) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV(p_147788_2_ + 1, (float)(p_147788_3_ + 1) + 0.021875f, (double)(p_147788_4_ + 1) - 0.015625, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, (double)(p_147788_4_ + 1) - 0.015625, var8.getMinU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, (double)(p_147788_4_ + 1) - 0.015625, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, (float)(p_147788_3_ + 1) + 0.021875f, (double)(p_147788_4_ + 1) - 0.015625, var8.getMaxU(), var8.getMaxV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV(p_147788_2_ + 1, (float)(p_147788_3_ + 1) + 0.021875f, (double)(p_147788_4_ + 1) - 0.015625, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, (double)(p_147788_4_ + 1) - 0.015625, var10.getMinU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, (double)(p_147788_4_ + 1) - 0.015625, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, (float)(p_147788_3_ + 1) + 0.021875f, (double)(p_147788_4_ + 1) - 0.015625, var10.getMaxU(), var10.getMaxV());
            }
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147788_2_, p_147788_3_, p_147788_4_)) {
            this.renderSnow(p_147788_2_, p_147788_3_, p_147788_4_, 0.01);
        }
        return true;
    }

    public boolean renderBlockMinecartTrack(BlockRailBase p_147766_1_, int p_147766_2_, int p_147766_3_, int p_147766_4_) {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(p_147766_2_, p_147766_3_, p_147766_4_);
        IIcon var7 = this.getBlockIconFromSideAndMetadata(p_147766_1_, 0, var6);
        if (this.hasOverrideBlockTexture()) {
            var7 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var7 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147766_1_, p_147766_2_, p_147766_3_, p_147766_4_, 1, var7);
        }
        if (p_147766_1_.func_150050_e()) {
            var6 &= 7;
        }
        var5.setBrightness(p_147766_1_.getBlockBrightness(this.blockAccess, p_147766_2_, p_147766_3_, p_147766_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        double var8 = var7.getMinU();
        double var10 = var7.getMinV();
        double var12 = var7.getMaxU();
        double var14 = var7.getMaxV();
        double var16 = 0.0625;
        double var18 = p_147766_2_ + 1;
        double var20 = p_147766_2_ + 1;
        double var22 = p_147766_2_ + 0;
        double var24 = p_147766_2_ + 0;
        double var26 = p_147766_4_ + 0;
        double var28 = p_147766_4_ + 1;
        double var30 = p_147766_4_ + 1;
        double var32 = p_147766_4_ + 0;
        double var34 = (double)p_147766_3_ + var16;
        double var36 = (double)p_147766_3_ + var16;
        double var38 = (double)p_147766_3_ + var16;
        double var40 = (double)p_147766_3_ + var16;
        if (var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7) {
            if (var6 == 8) {
                var18 = var20 = (double)(p_147766_2_ + 0);
                var22 = var24 = (double)(p_147766_2_ + 1);
                var26 = var32 = (double)(p_147766_4_ + 1);
                var28 = var30 = (double)(p_147766_4_ + 0);
            } else if (var6 == 9) {
                var18 = var24 = (double)(p_147766_2_ + 0);
                var20 = var22 = (double)(p_147766_2_ + 1);
                var26 = var28 = (double)(p_147766_4_ + 0);
                var30 = var32 = (double)(p_147766_4_ + 1);
            }
        } else {
            var18 = var24 = (double)(p_147766_2_ + 1);
            var20 = var22 = (double)(p_147766_2_ + 0);
            var26 = var28 = (double)(p_147766_4_ + 1);
            var30 = var32 = (double)(p_147766_4_ + 0);
        }
        if (var6 != 2 && var6 != 4) {
            if (var6 == 3 || var6 == 5) {
                var36 += 1.0;
                var38 += 1.0;
            }
        } else {
            var34 += 1.0;
            var40 += 1.0;
        }
        var5.addVertexWithUV(var18, var34, var26, var12, var10);
        var5.addVertexWithUV(var20, var36, var28, var12, var14);
        var5.addVertexWithUV(var22, var38, var30, var8, var14);
        var5.addVertexWithUV(var24, var40, var32, var8, var10);
        var5.addVertexWithUV(var24, var40, var32, var8, var10);
        var5.addVertexWithUV(var22, var38, var30, var8, var14);
        var5.addVertexWithUV(var20, var36, var28, var12, var14);
        var5.addVertexWithUV(var18, var34, var26, var12, var10);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147766_2_, p_147766_3_, p_147766_4_)) {
            this.renderSnow(p_147766_2_, p_147766_3_, p_147766_4_, 0.05);
        }
        return true;
    }

    public boolean renderBlockLadder(Block p_147794_1_, int p_147794_2_, int p_147794_3_, int p_147794_4_) {
        Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147794_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        int var15 = this.blockAccess.getBlockMetadata(p_147794_2_, p_147794_3_, p_147794_4_);
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147794_1_, p_147794_2_, p_147794_3_, p_147794_4_, var15, var6);
        }
        var5.setBrightness(p_147794_1_.getBlockBrightness(this.blockAccess, p_147794_2_, p_147794_3_, p_147794_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        double var7 = var6.getMinU();
        double var9 = var6.getMinV();
        double var11 = var6.getMaxU();
        double var13 = var6.getMaxV();
        double var16 = 0.0;
        double var18 = 0.05000000074505806;
        if (var15 == 5) {
            var5.addVertexWithUV((double)p_147794_2_ + var18, (double)(p_147794_3_ + 1) + var16, (double)(p_147794_4_ + 1) + var16, var7, var9);
            var5.addVertexWithUV((double)p_147794_2_ + var18, (double)(p_147794_3_ + 0) - var16, (double)(p_147794_4_ + 1) + var16, var7, var13);
            var5.addVertexWithUV((double)p_147794_2_ + var18, (double)(p_147794_3_ + 0) - var16, (double)(p_147794_4_ + 0) - var16, var11, var13);
            var5.addVertexWithUV((double)p_147794_2_ + var18, (double)(p_147794_3_ + 1) + var16, (double)(p_147794_4_ + 0) - var16, var11, var9);
        }
        if (var15 == 4) {
            var5.addVertexWithUV((double)(p_147794_2_ + 1) - var18, (double)(p_147794_3_ + 0) - var16, (double)(p_147794_4_ + 1) + var16, var11, var13);
            var5.addVertexWithUV((double)(p_147794_2_ + 1) - var18, (double)(p_147794_3_ + 1) + var16, (double)(p_147794_4_ + 1) + var16, var11, var9);
            var5.addVertexWithUV((double)(p_147794_2_ + 1) - var18, (double)(p_147794_3_ + 1) + var16, (double)(p_147794_4_ + 0) - var16, var7, var9);
            var5.addVertexWithUV((double)(p_147794_2_ + 1) - var18, (double)(p_147794_3_ + 0) - var16, (double)(p_147794_4_ + 0) - var16, var7, var13);
        }
        if (var15 == 3) {
            var5.addVertexWithUV((double)(p_147794_2_ + 1) + var16, (double)(p_147794_3_ + 0) - var16, (double)p_147794_4_ + var18, var11, var13);
            var5.addVertexWithUV((double)(p_147794_2_ + 1) + var16, (double)(p_147794_3_ + 1) + var16, (double)p_147794_4_ + var18, var11, var9);
            var5.addVertexWithUV((double)(p_147794_2_ + 0) - var16, (double)(p_147794_3_ + 1) + var16, (double)p_147794_4_ + var18, var7, var9);
            var5.addVertexWithUV((double)(p_147794_2_ + 0) - var16, (double)(p_147794_3_ + 0) - var16, (double)p_147794_4_ + var18, var7, var13);
        }
        if (var15 == 2) {
            var5.addVertexWithUV((double)(p_147794_2_ + 1) + var16, (double)(p_147794_3_ + 1) + var16, (double)(p_147794_4_ + 1) - var18, var7, var9);
            var5.addVertexWithUV((double)(p_147794_2_ + 1) + var16, (double)(p_147794_3_ + 0) - var16, (double)(p_147794_4_ + 1) - var18, var7, var13);
            var5.addVertexWithUV((double)(p_147794_2_ + 0) - var16, (double)(p_147794_3_ + 0) - var16, (double)(p_147794_4_ + 1) - var18, var11, var13);
            var5.addVertexWithUV((double)(p_147794_2_ + 0) - var16, (double)(p_147794_3_ + 1) + var16, (double)(p_147794_4_ + 1) - var18, var11, var9);
        }
        return true;
    }

    public boolean renderBlockVine(Block p_147726_1_, int p_147726_2_, int p_147726_3_, int p_147726_4_) {
        Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147726_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        int var17 = this.blockAccess.getBlockMetadata(p_147726_2_, p_147726_3_, p_147726_4_);
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            int var7 = 0;
            if ((var17 & 1) != 0) {
                var7 = 2;
            } else if ((var17 & 2) != 0) {
                var7 = 5;
            } else if ((var17 & 4) != 0) {
                var7 = 3;
            } else if ((var17 & 8) != 0) {
                var7 = 4;
            }
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147726_1_, p_147726_2_, p_147726_3_, p_147726_4_, var7, var6);
        }
        var5.setBrightness(p_147726_1_.getBlockBrightness(this.blockAccess, p_147726_2_, p_147726_3_, p_147726_4_));
        int var71 = CustomColorizer.getColorMultiplier(p_147726_1_, this.blockAccess, p_147726_2_, p_147726_3_, p_147726_4_);
        float var8 = (float)(var71 >> 16 & 255) / 255.0f;
        float var9 = (float)(var71 >> 8 & 255) / 255.0f;
        float var10 = (float)(var71 & 255) / 255.0f;
        var5.setColorOpaque_F(var8, var9, var10);
        double var18 = var6.getMinU();
        double var19 = var6.getMinV();
        double var11 = var6.getMaxU();
        double var13 = var6.getMaxV();
        double var15 = 0.05000000074505806;
        if ((var17 & 2) != 0) {
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 1, var18, var19);
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 1, var18, var13);
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 0, var11, var13);
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 0, var11, var19);
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 0, var11, var19);
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 0, var11, var13);
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 1, var18, var13);
            var5.addVertexWithUV((double)p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 1, var18, var19);
        }
        if ((var17 & 8) != 0) {
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 0, p_147726_4_ + 1, var11, var13);
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 1, p_147726_4_ + 1, var11, var19);
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 1, p_147726_4_ + 0, var18, var19);
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 0, p_147726_4_ + 0, var18, var13);
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 0, p_147726_4_ + 0, var18, var13);
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 1, p_147726_4_ + 0, var18, var19);
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 1, p_147726_4_ + 1, var11, var19);
            var5.addVertexWithUV((double)(p_147726_2_ + 1) - var15, p_147726_3_ + 0, p_147726_4_ + 1, var11, var13);
        }
        if ((var17 & 4) != 0) {
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, (double)p_147726_4_ + var15, var11, var13);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, (double)p_147726_4_ + var15, var11, var19);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, (double)p_147726_4_ + var15, var18, var19);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, (double)p_147726_4_ + var15, var18, var13);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, (double)p_147726_4_ + var15, var18, var13);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, (double)p_147726_4_ + var15, var18, var19);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, (double)p_147726_4_ + var15, var11, var19);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, (double)p_147726_4_ + var15, var11, var13);
        }
        if ((var17 & 1) != 0) {
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, (double)(p_147726_4_ + 1) - var15, var18, var19);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, (double)(p_147726_4_ + 1) - var15, var18, var13);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, (double)(p_147726_4_ + 1) - var15, var11, var13);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, (double)(p_147726_4_ + 1) - var15, var11, var19);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, (double)(p_147726_4_ + 1) - var15, var11, var19);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, (double)(p_147726_4_ + 1) - var15, var11, var13);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, (double)(p_147726_4_ + 1) - var15, var18, var13);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, (double)(p_147726_4_ + 1) - var15, var18, var19);
        }
        if (this.blockAccess.getBlock(p_147726_2_, p_147726_3_ + 1, p_147726_4_).isBlockNormalCube()) {
            var5.addVertexWithUV(p_147726_2_ + 1, (double)(p_147726_3_ + 1) - var15, p_147726_4_ + 0, var18, var19);
            var5.addVertexWithUV(p_147726_2_ + 1, (double)(p_147726_3_ + 1) - var15, p_147726_4_ + 1, var18, var13);
            var5.addVertexWithUV(p_147726_2_ + 0, (double)(p_147726_3_ + 1) - var15, p_147726_4_ + 1, var11, var13);
            var5.addVertexWithUV(p_147726_2_ + 0, (double)(p_147726_3_ + 1) - var15, p_147726_4_ + 0, var11, var19);
        }
        return true;
    }

    public boolean renderBlockStainedGlassPane(Block block, int x, int y, int z) {
        IIcon iconGlassPaneTop1;
        IIcon iconGlass1;
        int var5 = this.blockAccess.getHeight();
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(block.getBlockBrightness(this.blockAccess, x, y, z));
        int var7 = block.colorMultiplier(this.blockAccess, x, y, z);
        float var8 = (float)(var7 >> 16 & 255) / 255.0f;
        float var9 = (float)(var7 >> 8 & 255) / 255.0f;
        float var10 = (float)(var7 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float isStainedGlass = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            float iconGlass = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            float iconGlassPaneTop = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = isStainedGlass;
            var9 = iconGlass;
            var10 = iconGlassPaneTop;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        boolean isStainedGlass1 = block instanceof BlockStainedGlassPane;
        int metadata = 0;
        if (this.hasOverrideBlockTexture()) {
            iconGlass1 = this.overrideBlockTexture;
            iconGlassPaneTop1 = this.overrideBlockTexture;
        } else {
            metadata = this.blockAccess.getBlockMetadata(x, y, z);
            iconGlass1 = this.getBlockIconFromSideAndMetadata(block, 0, metadata);
            iconGlassPaneTop1 = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150104_b(metadata) : ((BlockPane)block).func_150097_e();
        }
        IIcon iconZ = iconGlass1;
        boolean drawTop = true;
        boolean drawBottom = true;
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            IIcon gMinU = ConnectedTextures.getConnectedTexture(this.blockAccess, block, x, y, z, 4, iconGlass1);
            IIcon iz = ConnectedTextures.getConnectedTexture(this.blockAccess, block, x, y, z, 3, iconGlass1);
            if (gMinU != iconGlass1 || iz != iconGlass1) {
                BlockPane gHalf7U = (BlockPane)block;
                drawTop = this.blockAccess.getBlock(x, y + 1, z) != block || this.blockAccess.getBlockMetadata(x, y + 1, z) != metadata;
                drawBottom = this.blockAccess.getBlock(x, y - 1, z) != block || this.blockAccess.getBlockMetadata(x, y - 1, z) != metadata;
            }
            iconGlass1 = gMinU;
            iconZ = iz;
        }
        double gMinU1 = iconGlass1.getMinU();
        double gHalf7U1 = iconGlass1.getInterpolatedU(7.0);
        double gHalf9U = iconGlass1.getInterpolatedU(9.0);
        double gMaxU = iconGlass1.getMaxU();
        double gMinV = iconGlass1.getMinV();
        double gMaxV = iconGlass1.getMaxV();
        double gMinUz = iconZ.getMinU();
        double gHalf7Uz = iconZ.getInterpolatedU(7.0);
        double gHalf9Uz = iconZ.getInterpolatedU(9.0);
        double gMaxUz = iconZ.getMaxU();
        double gMinVz = iconZ.getMinV();
        double gMaxVz = iconZ.getMaxV();
        double var26 = iconGlassPaneTop1.getInterpolatedU(7.0);
        double var28 = iconGlassPaneTop1.getInterpolatedU(9.0);
        double var30 = iconGlassPaneTop1.getMinV();
        double var32 = iconGlassPaneTop1.getMaxV();
        double var34 = iconGlassPaneTop1.getInterpolatedV(7.0);
        double var36 = iconGlassPaneTop1.getInterpolatedV(9.0);
        double x0 = x;
        double x1 = x + 1;
        double z0 = z;
        double z1 = z + 1;
        double xHalfMin = (double)x + 0.5 - 0.0625;
        double xHalfMax = (double)x + 0.5 + 0.0625;
        double zHalfMin = (double)z + 0.5 - 0.0625;
        double zHalfMax = (double)z + 0.5 + 0.0625;
        boolean connZNeg = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z - 1)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z - 1));
        boolean connZPos = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z + 1)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z + 1));
        boolean connXNeg = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x - 1, y, z)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x - 1, y, z));
        boolean connXPos = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x + 1, y, z)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x + 1, y, z));
        double var58 = 0.001;
        double var60 = 0.999;
        double var62 = 0.001;
        boolean disconnected = !connZNeg && !connZPos && !connXNeg && !connXPos;
        double yTop = (double)y + 0.999;
        double yBottom = (double)y + 0.001;
        if (!drawTop) {
            yTop = y + 1;
        }
        if (!drawBottom) {
            yBottom = y;
        }
        if (!connXNeg && !disconnected) {
            if (!connZNeg && !connZPos) {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
            }
        } else if (connXNeg && connXPos) {
            if (!connZNeg) {
                var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
            } else {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
                var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
            }
            if (!connZPos) {
                var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
            } else {
                var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
            }
            if (drawTop) {
                var6.addVertexWithUV(x0, yTop, zHalfMax, var28, var30);
                var6.addVertexWithUV(x1, yTop, zHalfMax, var28, var32);
                var6.addVertexWithUV(x1, yTop, zHalfMin, var26, var32);
                var6.addVertexWithUV(x0, yTop, zHalfMin, var26, var30);
            }
            if (drawBottom) {
                var6.addVertexWithUV(x1, yBottom, zHalfMax, var26, var32);
                var6.addVertexWithUV(x0, yBottom, zHalfMax, var26, var30);
                var6.addVertexWithUV(x0, yBottom, zHalfMin, var28, var30);
                var6.addVertexWithUV(x1, yBottom, zHalfMin, var28, var32);
            }
        } else {
            if (!connZNeg && !disconnected) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
            } else {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
            }
            if (!connZPos && !disconnected) {
                var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
            } else {
                var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
            }
            if (drawTop) {
                var6.addVertexWithUV(x0, yTop, zHalfMax, var28, var30);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var28, var34);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var26, var34);
                var6.addVertexWithUV(x0, yTop, zHalfMin, var26, var30);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var26, var34);
                var6.addVertexWithUV(x0, yBottom, zHalfMax, var26, var30);
                var6.addVertexWithUV(x0, yBottom, zHalfMin, var28, var30);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var28, var34);
            }
        }
        if ((connXPos || disconnected) && !connXNeg) {
            if (!connZPos && !disconnected) {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
            } else {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
            }
            if (!connZNeg && !disconnected) {
                var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
            } else {
                var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var28, var36);
                var6.addVertexWithUV(x1, yTop, zHalfMax, var28, var30);
                var6.addVertexWithUV(x1, yTop, zHalfMin, var26, var30);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var26, var36);
            }
            if (drawBottom) {
                var6.addVertexWithUV(x1, yBottom, zHalfMax, var26, var32);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var26, var36);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var28, var36);
                var6.addVertexWithUV(x1, yBottom, zHalfMin, var28, var32);
            }
        } else if (!(connXPos || connZNeg || connZPos)) {
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf7U1, gMinV);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf7U1, gMaxV);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9U, gMaxV);
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9U, gMinV);
        }
        if (!connZNeg && !disconnected) {
            if (!connXPos && !connXNeg) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
            }
        } else if (connZNeg && connZPos) {
            if (!connXNeg) {
                var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
            } else {
                var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
            }
            if (!connXPos) {
                var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
            } else {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U1, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U1, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
                var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, z0, var28, var30);
                var6.addVertexWithUV(xHalfMin, yTop, z0, var26, var30);
                var6.addVertexWithUV(xHalfMin, yTop, z1, var26, var32);
                var6.addVertexWithUV(xHalfMax, yTop, z1, var28, var32);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, z0, var26, var30);
                var6.addVertexWithUV(xHalfMax, yBottom, z0, var28, var30);
                var6.addVertexWithUV(xHalfMax, yBottom, z1, var28, var32);
                var6.addVertexWithUV(xHalfMin, yBottom, z1, var26, var32);
            }
        } else {
            if (!connXNeg && !disconnected) {
                var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
            } else {
                var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
            }
            if (!connXPos && !disconnected) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
            } else {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U1, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U1, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, z0, var28, var30);
                var6.addVertexWithUV(xHalfMin, yTop, z0, var26, var30);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var26, var34);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var28, var34);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, z0, var26, var30);
                var6.addVertexWithUV(xHalfMax, yBottom, z0, var28, var30);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var28, var34);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var26, var34);
            }
        }
        if ((connZPos || disconnected) && !connZNeg) {
            if (!connXNeg && !disconnected) {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
            } else {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
            }
            if (!connXPos && !disconnected) {
                var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U1, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U1, gMinV);
            } else {
                var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var28, var36);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var26, var36);
                var6.addVertexWithUV(xHalfMin, yTop, z1, var26, var32);
                var6.addVertexWithUV(xHalfMax, yTop, z1, var28, var32);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var26, var36);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var28, var36);
                var6.addVertexWithUV(xHalfMax, yBottom, z1, var28, var32);
                var6.addVertexWithUV(xHalfMin, yBottom, z1, var26, var32);
            }
        } else if (!(connZPos || connXPos || connXNeg)) {
            var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
            var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
        }
        if (drawTop) {
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var28, var34);
            var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var26, var34);
            var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var26, var36);
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var28, var36);
        }
        if (drawBottom) {
            var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var26, var34);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var28, var34);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var28, var36);
            var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var26, var36);
        }
        if (disconnected) {
            var6.addVertexWithUV(x0, yTop, zHalfMin, gHalf7U1, gMinV);
            var6.addVertexWithUV(x0, yBottom, zHalfMin, gHalf7U1, gMaxV);
            var6.addVertexWithUV(x0, yBottom, zHalfMax, gHalf9U, gMaxV);
            var6.addVertexWithUV(x0, yTop, zHalfMax, gHalf9U, gMinV);
            var6.addVertexWithUV(x1, yTop, zHalfMax, gHalf7U1, gMinV);
            var6.addVertexWithUV(x1, yBottom, zHalfMax, gHalf7U1, gMaxV);
            var6.addVertexWithUV(x1, yBottom, zHalfMin, gHalf9U, gMaxV);
            var6.addVertexWithUV(x1, yTop, zHalfMin, gHalf9U, gMinV);
            var6.addVertexWithUV(xHalfMax, yTop, z0, gHalf9Uz, gMinVz);
            var6.addVertexWithUV(xHalfMax, yBottom, z0, gHalf9Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMin, yBottom, z0, gHalf7Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMin, yTop, z0, gHalf7Uz, gMinVz);
            var6.addVertexWithUV(xHalfMin, yTop, z1, gHalf7Uz, gMinVz);
            var6.addVertexWithUV(xHalfMin, yBottom, z1, gHalf7Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yBottom, z1, gHalf9Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yTop, z1, gHalf9Uz, gMinVz);
        }
        return true;
    }

    public boolean renderBlockPane(BlockPane p_147767_1_, int p_147767_2_, int p_147767_3_, int p_147767_4_) {
        IIcon var631;
        IIcon var641;
        int var5 = this.blockAccess.getHeight();
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147767_1_.getBlockBrightness(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_));
        int var7 = p_147767_1_.colorMultiplier(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_);
        float var8 = (float)(var7 >> 16 & 255) / 255.0f;
        float var9 = (float)(var7 >> 8 & 255) / 255.0f;
        float var10 = (float)(var7 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var64 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            float var63 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            float kr = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var64;
            var9 = var63;
            var10 = kr;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        if (this.hasOverrideBlockTexture()) {
            var631 = this.overrideBlockTexture;
            var641 = this.overrideBlockTexture;
        } else {
            int kr2 = this.blockAccess.getBlockMetadata(p_147767_2_, p_147767_3_, p_147767_4_);
            var631 = this.getBlockIconFromSideAndMetadata(p_147767_1_, 0, kr2);
            var641 = p_147767_1_.func_150097_e();
        }
        IIcon kr1 = var631;
        IIcon kz = var631;
        IIcon kzr = var631;
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var631 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 2, var631);
            kr1 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 3, kr1);
            kz = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 4, kz);
            kzr = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 5, kzr);
        }
        double var65 = var631.getMinU();
        double var15 = var631.getInterpolatedU(8.0);
        double var17 = var631.getMaxU();
        double var19 = var631.getMinV();
        double var21 = var631.getMaxV();
        double dr = kr1.getMinU();
        double d1r = kr1.getInterpolatedU(8.0);
        double d2r = kr1.getMaxU();
        double d3r = kr1.getMinV();
        double d4r = kr1.getMaxV();
        double dz = kz.getMinU();
        double d1z = kz.getInterpolatedU(8.0);
        double d2z = kz.getMaxU();
        double d3z = kz.getMinV();
        double d4z = kz.getMaxV();
        double dzr = kzr.getMinU();
        double d1zr = kzr.getInterpolatedU(8.0);
        double d2zr = kzr.getMaxU();
        double d3zr = kzr.getMinV();
        double d4zr = kzr.getMaxV();
        double var23 = var641.getInterpolatedU(7.0);
        double var25 = var641.getInterpolatedU(9.0);
        double var27 = var641.getMinV();
        double var29 = var641.getInterpolatedV(8.0);
        double var31 = var641.getMaxV();
        double var33 = p_147767_2_;
        double var35 = (double)p_147767_2_ + 0.5;
        double var37 = p_147767_2_ + 1;
        double var39 = p_147767_4_;
        double var41 = (double)p_147767_4_ + 0.5;
        double var43 = p_147767_4_ + 1;
        double var45 = (double)p_147767_2_ + 0.5 - 0.0625;
        double var47 = (double)p_147767_2_ + 0.5 + 0.0625;
        double var49 = (double)p_147767_4_ + 0.5 - 0.0625;
        double var51 = (double)p_147767_4_ + 0.5 + 0.0625;
        boolean var53 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ - 1));
        boolean var54 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ + 1));
        boolean var55 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_ - 1, p_147767_3_, p_147767_4_));
        boolean var56 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_ + 1, p_147767_3_, p_147767_4_));
        boolean var57 = p_147767_1_.shouldSideBeRendered(this.blockAccess, p_147767_2_, p_147767_3_ + 1, p_147767_4_, 1);
        boolean var58 = p_147767_1_.shouldSideBeRendered(this.blockAccess, p_147767_2_, p_147767_3_ - 1, p_147767_4_, 0);
        double var59 = 0.01;
        double var61 = 0.005;
        if ((!var55 || !var56) && (var55 || var56 || var53 || var54)) {
            if (var55 && !var56) {
                var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, var65, var19);
                var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, var65, var21);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, var15, var21);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, var15, var19);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1r, d3r);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1r, d4r);
                var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, d2r, d4r);
                var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, d2r, d3r);
                if (!var54 && !var53) {
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var23, var27);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var23, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var25, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var25, var27);
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var23, var27);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var23, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var25, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var25, var27);
                }
                if (var57 || p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ + 1, p_147767_4_)) {
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                }
                if (var58 || p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ - 1, p_147767_4_)) {
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var29);
                }
            } else if (!var55 && var56) {
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, var15, var19);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, var15, var21);
                var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, var17, var21);
                var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, var17, var19);
                var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, dr, d3r);
                var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, dr, d4r);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1r, d4r);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1r, d3r);
                if (!var54 && !var53) {
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var23, var27);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var23, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var25, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var25, var27);
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var23, var27);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var23, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var25, var31);
                    var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var25, var27);
                }
                if (var57 || p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ + 1, p_147767_4_)) {
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var27);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var27);
                }
                if (var58 || p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ - 1, p_147767_4_)) {
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var27);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var49, var23, var27);
                }
            }
        } else {
            var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, var65, var19);
            var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, var65, var21);
            var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, var17, var21);
            var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, var17, var19);
            var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, dr, d3r);
            var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, dr, d4r);
            var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, d2r, d4r);
            var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, d2r, d3r);
            if (var57) {
                var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var31);
                var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var27);
                var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var27);
                var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var31);
                var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var31);
                var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var27);
                var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var27);
                var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var31);
            } else {
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ + 1, p_147767_4_)) {
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var33, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                }
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ + 1, p_147767_4_)) {
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var27);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var37, (double)(p_147767_3_ + 1) + 0.01, var49, var23, var27);
                }
            }
            if (var58) {
                var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var51, var25, var31);
                var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var51, var25, var27);
                var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var49, var23, var27);
                var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var49, var23, var31);
                var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var51, var25, var31);
                var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var51, var25, var27);
                var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var49, var23, var27);
                var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var49, var23, var31);
            } else {
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ - 1, p_147767_4_)) {
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var51, var25, var31);
                    var6.addVertexWithUV(var33, (double)p_147767_3_ - 0.01, var49, var23, var31);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var29);
                }
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ - 1, p_147767_4_)) {
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var27);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var51, var25, var27);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var51, var25, var29);
                    var6.addVertexWithUV(var35, (double)p_147767_3_ - 0.01, var49, var23, var29);
                    var6.addVertexWithUV(var37, (double)p_147767_3_ - 0.01, var49, var23, var27);
                }
            }
        }
        if ((!var53 || !var54) && (var55 || var56 || var53 || var54)) {
            if (var53 && !var54) {
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, dz, d3z);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, dz, d4z);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1z, d4z);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1z, d3z);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1zr, d3zr);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1zr, d4zr);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, d2zr, d4zr);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, d2zr, d3zr);
                if (!var56 && !var55) {
                    var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var23, var27);
                    var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var23, var31);
                    var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var25, var31);
                    var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var25, var27);
                    var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var23, var27);
                    var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var23, var31);
                    var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var25, var31);
                    var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var25, var27);
                }
                if (var57 || p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ - 1)) {
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var39, var25, var27);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var39, var23, var27);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var27);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var39, var25, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var39, var23, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var27);
                }
                if (var58 || p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ - 1)) {
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var39, var25, var27);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var39, var23, var27);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var25, var27);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var39, var25, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var39, var23, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var23, var27);
                }
            } else if (!var53 && var54) {
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1z, d3z);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1z, d4z);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, d2z, d4z);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, d2z, d3z);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, dzr, d3zr);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, dzr, d4zr);
                var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1zr, d4zr);
                var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1zr, d3zr);
                if (!var56 && !var55) {
                    var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var23, var27);
                    var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var23, var31);
                    var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var25, var31);
                    var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var25, var27);
                    var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var23, var27);
                    var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var23, var31);
                    var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var25, var31);
                    var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var25, var27);
                }
                if (var57 || p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ + 1)) {
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var43, var23, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var43, var25, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var43, var23, var29);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var43, var25, var29);
                }
                if (var58 || p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ + 1)) {
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var43, var23, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var43, var25, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var43, var23, var29);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var23, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var25, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var43, var25, var29);
                }
            }
        } else {
            var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, dzr, d3zr);
            var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, dzr, d4zr);
            var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, d2zr, d4zr);
            var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, d2zr, d3zr);
            var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, dz, d3z);
            var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, dz, d4z);
            var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, d2z, d4z);
            var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, d2z, d3z);
            if (var57) {
                var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var43, var25, var31);
                var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var39, var25, var27);
                var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var39, var23, var27);
                var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var43, var23, var31);
                var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var39, var25, var31);
                var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var43, var25, var27);
                var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var43, var23, var27);
                var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var39, var23, var31);
            } else {
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ - 1)) {
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var39, var25, var27);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var39, var23, var27);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var27);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var39, var25, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var39, var23, var29);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var27);
                }
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ + 1)) {
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var43, var23, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var43, var25, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var43, var23, var29);
                    var6.addVertexWithUV(var45, (double)(p_147767_3_ + 1) + 0.005, var41, var23, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var41, var25, var31);
                    var6.addVertexWithUV(var47, (double)(p_147767_3_ + 1) + 0.005, var43, var25, var29);
                }
            }
            if (var58) {
                var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var43, var25, var31);
                var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var39, var25, var27);
                var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var39, var23, var27);
                var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var43, var23, var31);
                var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var39, var25, var31);
                var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var43, var25, var27);
                var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var43, var23, var27);
                var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var39, var23, var31);
            } else {
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ - 1)) {
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var39, var25, var27);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var39, var23, var27);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var25, var27);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var39, var25, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var39, var23, var29);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var23, var27);
                }
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ + 1)) {
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var23, var29);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var43, var23, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var43, var25, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var25, var29);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var43, var23, var29);
                    var6.addVertexWithUV(var45, (double)p_147767_3_ - 0.005, var41, var23, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var41, var25, var31);
                    var6.addVertexWithUV(var47, (double)p_147767_3_ - 0.005, var43, var25, var29);
                }
            }
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147767_2_, p_147767_3_, p_147767_4_)) {
            this.renderSnow(p_147767_2_, p_147767_3_, p_147767_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }

    public boolean renderCrossedSquares(Block p_147746_1_, int p_147746_2_, int p_147746_3_, int p_147746_4_) {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147746_1_.getBlockBrightness(this.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_));
        int var6 = CustomColorizer.getColorMultiplier(p_147746_1_, this.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_);
        float var7 = (float)(var6 >> 16 & 255) / 255.0f;
        float var8 = (float)(var6 >> 8 & 255) / 255.0f;
        float var9 = (float)(var6 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var18 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            float var19 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var18;
            var8 = var11;
            var9 = var19;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        double var181 = p_147746_2_;
        double var191 = p_147746_3_;
        double var14 = p_147746_4_;
        if (p_147746_1_ == Blocks.tallgrass) {
            long var16 = (long)(p_147746_2_ * 3129871) ^ (long)p_147746_4_ * 116129781 ^ (long)p_147746_3_;
            var16 = var16 * var16 * 42317861 + var16 * 11;
            var181 += ((double)((float)(var16 >> 16 & 15) / 15.0f) - 0.5) * 0.5;
            var191 += ((double)((float)(var16 >> 20 & 15) / 15.0f) - 1.0) * 0.2;
            var14 += ((double)((float)(var16 >> 24 & 15) / 15.0f) - 0.5) * 0.5;
        } else if (p_147746_1_ == Blocks.red_flower || p_147746_1_ == Blocks.yellow_flower) {
            long var16 = (long)(p_147746_2_ * 3129871) ^ (long)p_147746_4_ * 116129781 ^ (long)p_147746_3_;
            var16 = var16 * var16 * 42317861 + var16 * 11;
            var181 += ((double)((float)(var16 >> 16 & 15) / 15.0f) - 0.5) * 0.3;
            var14 += ((double)((float)(var16 >> 24 & 15) / 15.0f) - 0.5) * 0.3;
        }
        IIcon var20 = this.getBlockIconFromSideAndMetadata(p_147746_1_, 0, this.blockAccess.getBlockMetadata(p_147746_2_, p_147746_3_, p_147746_4_));
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var20 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147746_1_, p_147746_2_, p_147746_3_, p_147746_4_, 2, var20);
        }
        this.drawCrossedSquares(var20, var181, var191, var14, 1.0f);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147746_2_, p_147746_3_, p_147746_4_)) {
            this.renderSnow(p_147746_2_, p_147746_3_, p_147746_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }

    public boolean renderBlockDoublePlant(BlockDoublePlant p_147774_1_, int p_147774_2_, int p_147774_3_, int p_147774_4_) {
        int var60;
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147774_1_.getBlockBrightness(this.blockAccess, p_147774_2_, p_147774_3_, p_147774_4_));
        int var6 = CustomColorizer.getColorMultiplier(p_147774_1_, this.blockAccess, p_147774_2_, p_147774_3_, p_147774_4_);
        float var7 = (float)(var6 >> 16 & 255) / 255.0f;
        float var8 = (float)(var6 >> 8 & 255) / 255.0f;
        float var9 = (float)(var6 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var58 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            float var59 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var58;
            var8 = var11;
            var9 = var59;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        long var581 = (long)(p_147774_2_ * 3129871) ^ (long)p_147774_4_ * 116129781;
        var581 = var581 * var581 * 42317861 + var581 * 11;
        double var591 = p_147774_2_;
        double var14 = p_147774_3_;
        double var16 = p_147774_4_;
        var591 += ((double)((float)(var581 >> 16 & 15) / 15.0f) - 0.5) * 0.3;
        var16 += ((double)((float)(var581 >> 24 & 15) / 15.0f) - 0.5) * 0.3;
        int var18 = this.blockAccess.getBlockMetadata(p_147774_2_, p_147774_3_, p_147774_4_);
        boolean var19 = false;
        boolean var20 = BlockDoublePlant.func_149887_c(var18);
        if (var20) {
            if (this.blockAccess.getBlock(p_147774_2_, p_147774_3_ - 1, p_147774_4_) != p_147774_1_) {
                return false;
            }
            var60 = BlockDoublePlant.func_149890_d(this.blockAccess.getBlockMetadata(p_147774_2_, p_147774_3_ - 1, p_147774_4_));
        } else {
            var60 = BlockDoublePlant.func_149890_d(var18);
        }
        IIcon var21 = p_147774_1_.func_149888_a(var20, var60);
        this.drawCrossedSquares(var21, var591, var14, var16, 1.0f);
        if (var20 && var60 == 0) {
            IIcon var22 = p_147774_1_.field_149891_b[0];
            double var23 = Math.cos((double)var581 * 0.8) * 3.141592653589793 * 0.1;
            double var25 = Math.cos(var23);
            double var27 = Math.sin(var23);
            double var29 = var22.getMinU();
            double var31 = var22.getMinV();
            double var33 = var22.getMaxU();
            double var35 = var22.getMaxV();
            double var37 = 0.3;
            double var39 = -0.05;
            double var41 = 0.5 + 0.3 * var25 - 0.5 * var27;
            double var43 = 0.5 + 0.5 * var25 + 0.3 * var27;
            double var45 = 0.5 + 0.3 * var25 + 0.5 * var27;
            double var47 = 0.5 + -0.5 * var25 + 0.3 * var27;
            double var49 = 0.5 + -0.05 * var25 + 0.5 * var27;
            double var51 = 0.5 + -0.5 * var25 + -0.05 * var27;
            double var53 = 0.5 + -0.05 * var25 - 0.5 * var27;
            double var55 = 0.5 + 0.5 * var25 + -0.05 * var27;
            var5.addVertexWithUV(var591 + var49, var14 + 1.0, var16 + var51, var29, var35);
            var5.addVertexWithUV(var591 + var53, var14 + 1.0, var16 + var55, var33, var35);
            var5.addVertexWithUV(var591 + var41, var14 + 0.0, var16 + var43, var33, var31);
            var5.addVertexWithUV(var591 + var45, var14 + 0.0, var16 + var47, var29, var31);
            IIcon var57 = p_147774_1_.field_149891_b[1];
            var29 = var57.getMinU();
            var31 = var57.getMinV();
            var33 = var57.getMaxU();
            var35 = var57.getMaxV();
            var5.addVertexWithUV(var591 + var53, var14 + 1.0, var16 + var55, var29, var35);
            var5.addVertexWithUV(var591 + var49, var14 + 1.0, var16 + var51, var33, var35);
            var5.addVertexWithUV(var591 + var45, var14 + 0.0, var16 + var47, var33, var31);
            var5.addVertexWithUV(var591 + var41, var14 + 0.0, var16 + var43, var29, var31);
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147774_2_, p_147774_3_, p_147774_4_)) {
            this.renderSnow(p_147774_2_, p_147774_3_, p_147774_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }

    public boolean renderBlockStem(Block p_147724_1_, int p_147724_2_, int p_147724_3_, int p_147724_4_) {
        BlockStem var5 = (BlockStem)p_147724_1_;
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(var5.getBlockBrightness(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_));
        int var7 = CustomColorizer.getStemColorMultiplier(var5, this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
        float var8 = (float)(var7 >> 16 & 255) / 255.0f;
        float var9 = (float)(var7 >> 8 & 255) / 255.0f;
        float var10 = (float)(var7 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var14 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var14;
            var9 = var12;
            var10 = var13;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        var5.setBlockBoundsBasedOnState(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
        int var141 = var5.func_149873_e(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
        if (var141 < 0) {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), this.renderMaxY, p_147724_2_, (float)p_147724_3_ - 0.0625f, p_147724_4_);
        } else {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), 0.5, p_147724_2_, (float)p_147724_3_ - 0.0625f, p_147724_4_);
            this.renderBlockStemBig(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), var141, this.renderMaxY, p_147724_2_, (float)p_147724_3_ - 0.0625f, p_147724_4_);
        }
        return true;
    }

    public boolean renderBlockCrops(Block p_147796_1_, int p_147796_2_, int p_147796_3_, int p_147796_4_) {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147796_1_.getBlockBrightness(this.blockAccess, p_147796_2_, p_147796_3_, p_147796_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        this.renderBlockCropsImpl(p_147796_1_, this.blockAccess.getBlockMetadata(p_147796_2_, p_147796_3_, p_147796_4_), p_147796_2_, (float)p_147796_3_ - 0.0625f, p_147796_4_);
        return true;
    }

    public void renderTorchAtAngle(Block p_147747_1_, double p_147747_2_, double p_147747_4_, double p_147747_6_, double p_147747_8_, double p_147747_10_, int p_147747_12_) {
        Tessellator var13 = Tessellator.instance;
        IIcon var14 = this.getBlockIconFromSideAndMetadata(p_147747_1_, 0, p_147747_12_);
        if (this.hasOverrideBlockTexture()) {
            var14 = this.overrideBlockTexture;
        }
        double var15 = var14.getMinU();
        double var17 = var14.getMinV();
        double var19 = var14.getMaxU();
        double var21 = var14.getMaxV();
        double var23 = var14.getInterpolatedU(7.0);
        double var25 = var14.getInterpolatedV(6.0);
        double var27 = var14.getInterpolatedU(9.0);
        double var29 = var14.getInterpolatedV(8.0);
        double var31 = var14.getInterpolatedU(7.0);
        double var33 = var14.getInterpolatedV(13.0);
        double var35 = var14.getInterpolatedU(9.0);
        double var37 = var14.getInterpolatedV(15.0);
        double var39 = (p_147747_2_ += 0.5) - 0.5;
        double var41 = p_147747_2_ + 0.5;
        double var43 = (p_147747_6_ += 0.5) - 0.5;
        double var45 = p_147747_6_ + 0.5;
        double var47 = 0.0625;
        double var49 = 0.625;
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var49) - var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0 - var49) - var47, var23, var25);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var49) - var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0 - var49) + var47, var23, var29);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var49) + var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0 - var49) + var47, var27, var29);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var49) + var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0 - var49) - var47, var27, var25);
        var13.addVertexWithUV(p_147747_2_ + var47 + p_147747_8_, p_147747_4_, p_147747_6_ - var47 + p_147747_10_, var35, var33);
        var13.addVertexWithUV(p_147747_2_ + var47 + p_147747_8_, p_147747_4_, p_147747_6_ + var47 + p_147747_10_, var35, var37);
        var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_, p_147747_6_ + var47 + p_147747_10_, var31, var37);
        var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_, p_147747_6_ - var47 + p_147747_10_, var31, var33);
        var13.addVertexWithUV(p_147747_2_ - var47, p_147747_4_ + 1.0, var43, var15, var17);
        var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_ + 0.0, var43 + p_147747_10_, var15, var21);
        var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_ + 0.0, var45 + p_147747_10_, var19, var21);
        var13.addVertexWithUV(p_147747_2_ - var47, p_147747_4_ + 1.0, var45, var19, var17);
        var13.addVertexWithUV(p_147747_2_ + var47, p_147747_4_ + 1.0, var45, var15, var17);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ + var47, p_147747_4_ + 0.0, var45 + p_147747_10_, var15, var21);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ + var47, p_147747_4_ + 0.0, var43 + p_147747_10_, var19, var21);
        var13.addVertexWithUV(p_147747_2_ + var47, p_147747_4_ + 1.0, var43, var19, var17);
        var13.addVertexWithUV(var39, p_147747_4_ + 1.0, p_147747_6_ + var47, var15, var17);
        var13.addVertexWithUV(var39 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ + var47 + p_147747_10_, var15, var21);
        var13.addVertexWithUV(var41 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ + var47 + p_147747_10_, var19, var21);
        var13.addVertexWithUV(var41, p_147747_4_ + 1.0, p_147747_6_ + var47, var19, var17);
        var13.addVertexWithUV(var41, p_147747_4_ + 1.0, p_147747_6_ - var47, var15, var17);
        var13.addVertexWithUV(var41 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ - var47 + p_147747_10_, var15, var21);
        var13.addVertexWithUV(var39 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ - var47 + p_147747_10_, var19, var21);
        var13.addVertexWithUV(var39, p_147747_4_ + 1.0, p_147747_6_ - var47, var19, var17);
    }

    public void drawCrossedSquares(IIcon p_147765_1_, double p_147765_2_, double p_147765_4_, double p_147765_6_, float p_147765_8_) {
        Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147765_1_ = this.overrideBlockTexture;
        }
        double var10 = p_147765_1_.getMinU();
        double var12 = p_147765_1_.getMinV();
        double var14 = p_147765_1_.getMaxU();
        double var16 = p_147765_1_.getMaxV();
        double var18 = 0.45 * (double)p_147765_8_;
        double var20 = p_147765_2_ + 0.5 - var18;
        double var22 = p_147765_2_ + 0.5 + var18;
        double var24 = p_147765_6_ + 0.5 - var18;
        double var26 = p_147765_6_ + 0.5 + var18;
        var9.addVertexWithUV(var20, p_147765_4_ + (double)p_147765_8_, var24, var10, var12);
        var9.addVertexWithUV(var20, p_147765_4_ + 0.0, var24, var10, var16);
        var9.addVertexWithUV(var22, p_147765_4_ + 0.0, var26, var14, var16);
        var9.addVertexWithUV(var22, p_147765_4_ + (double)p_147765_8_, var26, var14, var12);
        var9.addVertexWithUV(var22, p_147765_4_ + (double)p_147765_8_, var26, var10, var12);
        var9.addVertexWithUV(var22, p_147765_4_ + 0.0, var26, var10, var16);
        var9.addVertexWithUV(var20, p_147765_4_ + 0.0, var24, var14, var16);
        var9.addVertexWithUV(var20, p_147765_4_ + (double)p_147765_8_, var24, var14, var12);
        var9.addVertexWithUV(var20, p_147765_4_ + (double)p_147765_8_, var26, var10, var12);
        var9.addVertexWithUV(var20, p_147765_4_ + 0.0, var26, var10, var16);
        var9.addVertexWithUV(var22, p_147765_4_ + 0.0, var24, var14, var16);
        var9.addVertexWithUV(var22, p_147765_4_ + (double)p_147765_8_, var24, var14, var12);
        var9.addVertexWithUV(var22, p_147765_4_ + (double)p_147765_8_, var24, var10, var12);
        var9.addVertexWithUV(var22, p_147765_4_ + 0.0, var24, var10, var16);
        var9.addVertexWithUV(var20, p_147765_4_ + 0.0, var26, var14, var16);
        var9.addVertexWithUV(var20, p_147765_4_ + (double)p_147765_8_, var26, var14, var12);
    }

    public void renderBlockStemSmall(Block p_147730_1_, int p_147730_2_, double p_147730_3_, double p_147730_5_, double p_147730_7_, double p_147730_9_) {
        Tessellator var11 = Tessellator.instance;
        IIcon var12 = this.getBlockIconFromSideAndMetadata(p_147730_1_, 0, p_147730_2_);
        if (this.hasOverrideBlockTexture()) {
            var12 = this.overrideBlockTexture;
        }
        double var13 = var12.getMinU();
        double var15 = var12.getMinV();
        double var17 = var12.getMaxU();
        double var19 = var12.getInterpolatedV(p_147730_3_ * 16.0);
        double var21 = p_147730_5_ + 0.5 - 0.44999998807907104;
        double var23 = p_147730_5_ + 0.5 + 0.44999998807907104;
        double var25 = p_147730_9_ + 0.5 - 0.44999998807907104;
        double var27 = p_147730_9_ + 0.5 + 0.44999998807907104;
        var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var25, var13, var15);
        var11.addVertexWithUV(var21, p_147730_7_ + 0.0, var25, var13, var19);
        var11.addVertexWithUV(var23, p_147730_7_ + 0.0, var27, var17, var19);
        var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var27, var17, var15);
        var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var27, var17, var15);
        var11.addVertexWithUV(var23, p_147730_7_ + 0.0, var27, var17, var19);
        var11.addVertexWithUV(var21, p_147730_7_ + 0.0, var25, var13, var19);
        var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var25, var13, var15);
        var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var27, var13, var15);
        var11.addVertexWithUV(var21, p_147730_7_ + 0.0, var27, var13, var19);
        var11.addVertexWithUV(var23, p_147730_7_ + 0.0, var25, var17, var19);
        var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var25, var17, var15);
        var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var25, var17, var15);
        var11.addVertexWithUV(var23, p_147730_7_ + 0.0, var25, var17, var19);
        var11.addVertexWithUV(var21, p_147730_7_ + 0.0, var27, var13, var19);
        var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var27, var13, var15);
    }

    public boolean renderBlockLilyPad(Block p_147783_1_, int p_147783_2_, int p_147783_3_, int p_147783_4_) {
        Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147783_1_, 1);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147783_1_, p_147783_2_, p_147783_3_, p_147783_4_, 1, var6);
        }
        float var7 = 0.015625f;
        double var8 = var6.getMinU();
        double var10 = var6.getMinV();
        double var12 = var6.getMaxU();
        double var14 = var6.getMaxV();
        long var16 = (long)(p_147783_2_ * 3129871) ^ (long)p_147783_4_ * 116129781 ^ (long)p_147783_3_;
        var16 = var16 * var16 * 42317861 + var16 * 11;
        int var18 = (int)(var16 >> 16 & 3);
        var5.setBrightness(p_147783_1_.getBlockBrightness(this.blockAccess, p_147783_2_, p_147783_3_, p_147783_4_));
        float var19 = (float)p_147783_2_ + 0.5f;
        float var20 = (float)p_147783_4_ + 0.5f;
        float var21 = (float)(var18 & 1) * 0.5f * (float)(1 - var18 / 2 % 2 * 2);
        float var22 = (float)(var18 + 1 & 1) * 0.5f * (float)(1 - (var18 + 1) / 2 % 2 * 2);
        int col = CustomColorizer.getLilypadColor();
        var5.setColorOpaque_I(col);
        var5.addVertexWithUV(var19 + var21 - var22, (float)p_147783_3_ + var7, var20 + var21 + var22, var8, var10);
        var5.addVertexWithUV(var19 + var21 + var22, (float)p_147783_3_ + var7, var20 - var21 + var22, var12, var10);
        var5.addVertexWithUV(var19 - var21 + var22, (float)p_147783_3_ + var7, var20 - var21 - var22, var12, var14);
        var5.addVertexWithUV(var19 - var21 - var22, (float)p_147783_3_ + var7, var20 + var21 - var22, var8, var14);
        var5.setColorOpaque_I((col & 16711422) >> 1);
        var5.addVertexWithUV(var19 - var21 - var22, (float)p_147783_3_ + var7, var20 + var21 - var22, var8, var14);
        var5.addVertexWithUV(var19 - var21 + var22, (float)p_147783_3_ + var7, var20 - var21 - var22, var12, var14);
        var5.addVertexWithUV(var19 + var21 + var22, (float)p_147783_3_ + var7, var20 - var21 + var22, var12, var10);
        var5.addVertexWithUV(var19 + var21 - var22, (float)p_147783_3_ + var7, var20 + var21 + var22, var8, var10);
        return true;
    }

    public void renderBlockStemBig(BlockStem p_147740_1_, int p_147740_2_, int p_147740_3_, double p_147740_4_, double p_147740_6_, double p_147740_8_, double p_147740_10_) {
        Tessellator var12 = Tessellator.instance;
        IIcon var13 = p_147740_1_.func_149872_i();
        if (this.hasOverrideBlockTexture()) {
            var13 = this.overrideBlockTexture;
        }
        double var14 = var13.getMinU();
        double var16 = var13.getMinV();
        double var18 = var13.getMaxU();
        double var20 = var13.getMaxV();
        double var22 = p_147740_6_ + 0.5 - 0.5;
        double var24 = p_147740_6_ + 0.5 + 0.5;
        double var26 = p_147740_10_ + 0.5 - 0.5;
        double var28 = p_147740_10_ + 0.5 + 0.5;
        double var30 = p_147740_6_ + 0.5;
        double var32 = p_147740_10_ + 0.5;
        if ((p_147740_3_ + 1) / 2 % 2 == 1) {
            double var34 = var18;
            var18 = var14;
            var14 = var34;
        }
        if (p_147740_3_ < 2) {
            var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var32, var14, var16);
            var12.addVertexWithUV(var22, p_147740_8_ + 0.0, var32, var14, var20);
            var12.addVertexWithUV(var24, p_147740_8_ + 0.0, var32, var18, var20);
            var12.addVertexWithUV(var24, p_147740_8_ + p_147740_4_, var32, var18, var16);
            var12.addVertexWithUV(var24, p_147740_8_ + p_147740_4_, var32, var18, var16);
            var12.addVertexWithUV(var24, p_147740_8_ + 0.0, var32, var18, var20);
            var12.addVertexWithUV(var22, p_147740_8_ + 0.0, var32, var14, var20);
            var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var32, var14, var16);
        } else {
            var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var28, var14, var16);
            var12.addVertexWithUV(var30, p_147740_8_ + 0.0, var28, var14, var20);
            var12.addVertexWithUV(var30, p_147740_8_ + 0.0, var26, var18, var20);
            var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var26, var18, var16);
            var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var26, var18, var16);
            var12.addVertexWithUV(var30, p_147740_8_ + 0.0, var26, var18, var20);
            var12.addVertexWithUV(var30, p_147740_8_ + 0.0, var28, var14, var20);
            var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var28, var14, var16);
        }
    }

    public void renderBlockCropsImpl(Block p_147795_1_, int p_147795_2_, double p_147795_3_, double p_147795_5_, double p_147795_7_) {
        Tessellator var9 = Tessellator.instance;
        IIcon var10 = this.getBlockIconFromSideAndMetadata(p_147795_1_, 0, p_147795_2_);
        if (this.hasOverrideBlockTexture()) {
            var10 = this.overrideBlockTexture;
        }
        double var11 = var10.getMinU();
        double var13 = var10.getMinV();
        double var15 = var10.getMaxU();
        double var17 = var10.getMaxV();
        double var19 = p_147795_3_ + 0.5 - 0.25;
        double var21 = p_147795_3_ + 0.5 + 0.25;
        double var23 = p_147795_7_ + 0.5 - 0.5;
        double var25 = p_147795_7_ + 0.5 + 0.5;
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var23, var11, var13);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var23, var11, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var25, var15, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var25, var15, var13);
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var25, var11, var13);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var25, var11, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var23, var15, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var23, var15, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var25, var11, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var25, var11, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var23, var15, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var23, var15, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var23, var11, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var23, var11, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var25, var15, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var25, var15, var13);
        var19 = p_147795_3_ + 0.5 - 0.5;
        var21 = p_147795_3_ + 0.5 + 0.5;
        var23 = p_147795_7_ + 0.5 - 0.25;
        var25 = p_147795_7_ + 0.5 + 0.25;
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var23, var11, var13);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var23, var11, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var23, var15, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var23, var15, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var23, var11, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var23, var11, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var23, var15, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var23, var15, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var25, var11, var13);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var25, var11, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var25, var15, var17);
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var25, var15, var13);
        var9.addVertexWithUV(var19, p_147795_5_ + 1.0, var25, var11, var13);
        var9.addVertexWithUV(var19, p_147795_5_ + 0.0, var25, var11, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 0.0, var25, var15, var17);
        var9.addVertexWithUV(var21, p_147795_5_ + 1.0, var25, var15, var13);
    }

    public boolean renderBlockFluids(Block p_147721_1_, int p_147721_2_, int p_147721_3_, int p_147721_4_) {
        double var41;
        float var52;
        float var54;
        double var49;
        double var47;
        double var45;
        double var39;
        double var43;
        float var53;
        Tessellator var5 = Tessellator.instance;
        int var6 = CustomColorizer.getFluidColor(p_147721_1_, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_);
        float var7 = (float)(var6 >> 16 & 255) / 255.0f;
        float var8 = (float)(var6 >> 8 & 255) / 255.0f;
        float var9 = (float)(var6 & 255) / 255.0f;
        boolean var10 = p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_ + 1, p_147721_4_, 1);
        boolean var11 = p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_, 0);
        boolean[] var12 = new boolean[]{p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ - 1, 2), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ + 1, 3), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_ - 1, p_147721_3_, p_147721_4_, 4), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_ + 1, p_147721_3_, p_147721_4_, 5)};
        if (!(var10 || var11 || var12[0] || var12[1] || var12[2] || var12[3])) {
            return false;
        }
        boolean var13 = false;
        float var14 = 0.5f;
        float var15 = 1.0f;
        float var16 = 0.8f;
        float var17 = 0.6f;
        double var18 = 0.0;
        double var20 = 1.0;
        Material var22 = p_147721_1_.getMaterial();
        int var23 = this.blockAccess.getBlockMetadata(p_147721_2_, p_147721_3_, p_147721_4_);
        double var24 = this.getFluidHeight(p_147721_2_, p_147721_3_, p_147721_4_, var22);
        double var26 = this.getFluidHeight(p_147721_2_, p_147721_3_, p_147721_4_ + 1, var22);
        double var28 = this.getFluidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_ + 1, var22);
        double var30 = this.getFluidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_, var22);
        double var32 = 0.0010000000474974513;
        if (this.renderAllFaces || var10) {
            double var37;
            double var51;
            var13 = true;
            IIcon var57 = this.getBlockIconFromSideAndMetadata(p_147721_1_, 1, var23);
            float var58 = (float)BlockLiquid.func_149802_a(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, var22);
            if (var58 > -999.0f) {
                var57 = this.getBlockIconFromSideAndMetadata(p_147721_1_, 2, var23);
            }
            var24 -= var32;
            var26 -= var32;
            var28 -= var32;
            var30 -= var32;
            if (var58 < -999.0f) {
                var39 = var57.getInterpolatedU(0.0);
                var45 = var57.getInterpolatedV(0.0);
                var37 = var39;
                var47 = var57.getInterpolatedV(16.0);
                var41 = var57.getInterpolatedU(16.0);
                var51 = var47;
                var43 = var41;
                var49 = var45;
            } else {
                var52 = MathHelper.sin(var58) * 0.25f;
                var53 = MathHelper.cos(var58) * 0.25f;
                var54 = 8.0f;
                var39 = var57.getInterpolatedU(8.0f + (- var53 - var52) * 16.0f);
                var45 = var57.getInterpolatedV(8.0f + (- var53 + var52) * 16.0f);
                var37 = var57.getInterpolatedU(8.0f + (- var53 + var52) * 16.0f);
                var47 = var57.getInterpolatedV(8.0f + (var53 + var52) * 16.0f);
                var41 = var57.getInterpolatedU(8.0f + (var53 + var52) * 16.0f);
                var51 = var57.getInterpolatedV(8.0f + (var53 - var52) * 16.0f);
                var43 = var57.getInterpolatedU(8.0f + (var53 - var52) * 16.0f);
                var49 = var57.getInterpolatedV(8.0f + (- var53 - var52) * 16.0f);
            }
            var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_));
            var5.setColorOpaque_F(var15 * var7, var15 * var8, var15 * var9);
            double var56 = 3.90625E-5;
            var5.addVertexWithUV(p_147721_2_ + 0, (double)p_147721_3_ + var24, p_147721_4_ + 0, var39 + var56, var45 + var56);
            var5.addVertexWithUV(p_147721_2_ + 0, (double)p_147721_3_ + var26, p_147721_4_ + 1, var37 + var56, var47 - var56);
            var5.addVertexWithUV(p_147721_2_ + 1, (double)p_147721_3_ + var28, p_147721_4_ + 1, var41 - var56, var51 - var56);
            var5.addVertexWithUV(p_147721_2_ + 1, (double)p_147721_3_ + var30, p_147721_4_ + 0, var43 - var56, var49 + var56);
        }
        if (this.renderAllFaces || var11) {
            var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_));
            var5.setColorOpaque_F(var14 * var7, var14 * var8, var14 * var9);
            this.renderFaceYNeg(p_147721_1_, p_147721_2_, (double)p_147721_3_ + var32, p_147721_4_, this.getBlockIconFromSide(p_147721_1_, 0));
            var13 = true;
        }
        int var571 = 0;
        while (var571 < 4) {
            int var581 = p_147721_2_;
            int var591 = p_147721_4_;
            if (var571 == 0) {
                var591 = p_147721_4_ - 1;
            }
            if (var571 == 1) {
                ++var591;
            }
            if (var571 == 2) {
                var581 = p_147721_2_ - 1;
            }
            if (var571 == 3) {
                ++var581;
            }
            IIcon var59 = this.getBlockIconFromSideAndMetadata(p_147721_1_, var571 + 2, var23);
            if (this.renderAllFaces || var12[var571]) {
                if (var571 == 0) {
                    var39 = var24;
                    var41 = var30;
                    var43 = p_147721_2_;
                    var47 = p_147721_2_ + 1;
                    var45 = (double)p_147721_4_ + var32;
                    var49 = (double)p_147721_4_ + var32;
                } else if (var571 == 1) {
                    var39 = var28;
                    var41 = var26;
                    var43 = p_147721_2_ + 1;
                    var47 = p_147721_2_;
                    var45 = (double)(p_147721_4_ + 1) - var32;
                    var49 = (double)(p_147721_4_ + 1) - var32;
                } else if (var571 == 2) {
                    var39 = var26;
                    var41 = var24;
                    var43 = (double)p_147721_2_ + var32;
                    var47 = (double)p_147721_2_ + var32;
                    var45 = p_147721_4_ + 1;
                    var49 = p_147721_4_;
                } else {
                    var39 = var30;
                    var41 = var28;
                    var43 = (double)(p_147721_2_ + 1) - var32;
                    var47 = (double)(p_147721_2_ + 1) - var32;
                    var45 = p_147721_4_;
                    var49 = p_147721_4_ + 1;
                }
                var13 = true;
                float var60 = var59.getInterpolatedU(0.0);
                var52 = var59.getInterpolatedU(8.0);
                var53 = var59.getInterpolatedV((1.0 - var39) * 16.0 * 0.5);
                var54 = var59.getInterpolatedV((1.0 - var41) * 16.0 * 0.5);
                float var55 = var59.getInterpolatedV(8.0);
                var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, var581, p_147721_3_, var591));
                float var61 = 1.0f;
                var5.setColorOpaque_F(var15 * var61 * var7, var15 * var61 * var8, var15 * (var61 *= var571 < 2 ? var16 : var17) * var9);
                var5.addVertexWithUV(var43, (double)p_147721_3_ + var39, var45, var60, var53);
                var5.addVertexWithUV(var47, (double)p_147721_3_ + var41, var49, var52, var54);
                var5.addVertexWithUV(var47, p_147721_3_ + 0, var49, var52, var55);
                var5.addVertexWithUV(var43, p_147721_3_ + 0, var45, var60, var55);
            }
            ++var571;
        }
        this.renderMinY = var18;
        this.renderMaxY = var20;
        return var13;
    }

    public float getFluidHeight(int p_147729_1_, int p_147729_2_, int p_147729_3_, Material p_147729_4_) {
        int var5 = 0;
        float var6 = 0.0f;
        int var7 = 0;
        while (var7 < 4) {
            int var8 = p_147729_1_ - (var7 & 1);
            int var10 = p_147729_3_ - (var7 >> 1 & 1);
            if (this.blockAccess.getBlock(var8, p_147729_2_ + 1, var10).getMaterial() == p_147729_4_) {
                return 1.0f;
            }
            Material var11 = this.blockAccess.getBlock(var8, p_147729_2_, var10).getMaterial();
            if (var11 == p_147729_4_) {
                int var12 = this.blockAccess.getBlockMetadata(var8, p_147729_2_, var10);
                if (var12 >= 8 || var12 == 0) {
                    var6 += BlockLiquid.func_149801_b(var12) * 10.0f;
                    var5 += 10;
                }
                var6 += BlockLiquid.func_149801_b(var12);
                ++var5;
            } else if (!var11.isSolid()) {
                var6 += 1.0f;
                ++var5;
            }
            ++var7;
        }
        return 1.0f - var6 / (float)var5;
    }

    public void renderBlockSandFalling(Block p_147749_1_, World p_147749_2_, int p_147749_3_, int p_147749_4_, int p_147749_5_, int p_147749_6_) {
        float var7 = 0.5f;
        float var8 = 1.0f;
        float var9 = 0.8f;
        float var10 = 0.6f;
        Tessellator var11 = Tessellator.instance;
        var11.startDrawingQuads();
        var11.setBrightness(p_147749_1_.getBlockBrightness(p_147749_2_, p_147749_3_, p_147749_4_, p_147749_5_));
        var11.setColorOpaque_F(var7, var7, var7);
        this.renderFaceYNeg(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 0, p_147749_6_));
        var11.setColorOpaque_F(var8, var8, var8);
        this.renderFaceYPos(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 1, p_147749_6_));
        var11.setColorOpaque_F(var9, var9, var9);
        this.renderFaceZNeg(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 2, p_147749_6_));
        var11.setColorOpaque_F(var9, var9, var9);
        this.renderFaceZPos(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 3, p_147749_6_));
        var11.setColorOpaque_F(var10, var10, var10);
        this.renderFaceXNeg(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 4, p_147749_6_));
        var11.setColorOpaque_F(var10, var10, var10);
        this.renderFaceXPos(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 5, p_147749_6_));
        var11.draw();
    }

    public boolean renderStandardBlock(Block p_147784_1_, int p_147784_2_, int p_147784_3_, int p_147784_4_) {
        int var5 = CustomColorizer.getColorMultiplier(p_147784_1_, this.blockAccess, p_147784_2_, p_147784_3_, p_147784_4_);
        float var6 = (float)(var5 >> 16 & 255) / 255.0f;
        float var7 = (float)(var5 >> 8 & 255) / 255.0f;
        float var8 = (float)(var5 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var9 = (var6 * 30.0f + var7 * 59.0f + var8 * 11.0f) / 100.0f;
            float var10 = (var6 * 30.0f + var7 * 70.0f) / 100.0f;
            float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }
        return Minecraft.isAmbientOcclusionEnabled() && p_147784_1_.getLightValue() == 0 ? (this.partialRenderBounds ? this.renderStandardBlockWithAmbientOcclusionPartial(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8) : this.renderStandardBlockWithAmbientOcclusion(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8)) : this.renderStandardBlockWithColorMultiplier(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8);
    }

    public boolean renderBlockLog(Block p_147742_1_, int p_147742_2_, int p_147742_3_, int p_147742_4_) {
        int var5 = this.blockAccess.getBlockMetadata(p_147742_2_, p_147742_3_, p_147742_4_);
        int var6 = var5 & 12;
        if (var6 == 4) {
            this.uvRotateEast = 2;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 2;
        } else if (var6 == 8) {
            this.uvRotateSouth = 2;
            this.uvRotateNorth = 1;
            this.uvRotateTop = 3;
            this.uvRotateBottom = 3;
        }
        boolean var7 = this.renderStandardBlock(p_147742_1_, p_147742_2_, p_147742_3_, p_147742_4_);
        this.uvRotateSouth = 0;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return var7;
    }

    public boolean renderBlockQuartz(Block p_147779_1_, int p_147779_2_, int p_147779_3_, int p_147779_4_) {
        int var5 = this.blockAccess.getBlockMetadata(p_147779_2_, p_147779_3_, p_147779_4_);
        if (var5 == 3) {
            this.uvRotateEast = 2;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 2;
        } else if (var5 == 4) {
            this.uvRotateSouth = 2;
            this.uvRotateNorth = 1;
            this.uvRotateTop = 3;
            this.uvRotateBottom = 3;
        }
        boolean var6 = this.renderStandardBlock(p_147779_1_, p_147779_2_, p_147779_3_, p_147779_4_);
        this.uvRotateSouth = 0;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return var6;
    }

    public boolean renderStandardBlockWithAmbientOcclusion(Block p_147751_1_, int p_147751_2_, int p_147751_3_, int p_147751_4_, float p_147751_5_, float p_147751_6_, float p_147751_7_) {
        boolean var17;
        float var21;
        boolean var18;
        boolean var19;
        int var20;
        boolean var16;
        IIcon var22;
        this.enableAO = true;
        boolean defaultTexture = Tessellator.instance.defaultTexture;
        boolean betterGrass = Config.isBetterGrass() && defaultTexture;
        boolean simpleAO = p_147751_1_ == Blocks.glass;
        boolean var8 = false;
        float var9 = 0.0f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        float var12 = 0.0f;
        boolean var13 = true;
        int var14 = -1;
        Tessellator var15 = Tessellator.instance;
        var15.setBrightness(983055);
        if (p_147751_1_ == Blocks.grass) {
            var13 = false;
        } else if (this.hasOverrideBlockTexture()) {
            var13 = false;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_, 0)) {
            if (this.renderMinY <= 0.0) {
                --p_147751_3_;
            }
            this.aoBrightnessXYNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessYZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoBrightnessXYPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (this.renderMinY <= 0.0) {
                ++p_147751_3_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            var20 = var14;
            if (this.renderMinY <= 0.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var12 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0f;
            var11 = (var21 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0f;
            var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var21 + this.aoLightValueScratchYZNN) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                this.brightnessBottomRight = this.brightnessBottomLeft = var20;
                this.brightnessTopRight = this.brightnessBottomLeft;
                this.brightnessTopLeft = this.brightnessBottomLeft;
            }
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.5f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.5f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.5f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.5f;
                this.colorRedBottomRight = 0.5f;
                this.colorRedBottomLeft = 0.5f;
                this.colorRedTopLeft = 0.5f;
                this.colorGreenTopRight = 0.5f;
                this.colorGreenBottomRight = 0.5f;
                this.colorGreenBottomLeft = 0.5f;
                this.colorGreenTopLeft = 0.5f;
                this.colorBlueTopRight = 0.5f;
                this.colorBlueBottomRight = 0.5f;
                this.colorBlueBottomLeft = 0.5f;
                this.colorBlueTopLeft = 0.5f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 0));
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_, 1)) {
            if (this.renderMaxY >= 1.0) {
                ++p_147751_3_;
            }
            this.aoBrightnessXYNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessXYPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessYZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (this.renderMaxY >= 1.0) {
                --p_147751_3_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            var20 = var14;
            if (this.renderMaxY >= 1.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var21) / 4.0f;
            var9 = (this.aoLightValueScratchYZPP + var21 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0f;
            var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0f;
            var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var20);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                this.brightnessBottomRight = this.brightnessBottomLeft = var20;
                this.brightnessTopRight = this.brightnessBottomLeft;
                this.brightnessTopLeft = this.brightnessBottomLeft;
            }
            this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 1));
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1, 2)) {
            if (this.renderMinZ <= 0.0) {
                --p_147751_4_;
            }
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessXZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessYZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (this.renderMinZ <= 0.0) {
                ++p_147751_4_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            var20 = var14;
            if (this.renderMinZ <= 0.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_, p_147751_4_ - 1).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            var9 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0f;
            var11 = (this.aoLightValueScratchYZNN + var21 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0f;
            var12 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var21) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                this.brightnessBottomRight = this.brightnessBottomLeft = var20;
                this.brightnessTopRight = this.brightnessBottomLeft;
                this.brightnessTopLeft = this.brightnessBottomLeft;
            }
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.8f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.8f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.8f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.8f;
                this.colorRedBottomRight = 0.8f;
                this.colorRedBottomLeft = 0.8f;
                this.colorRedTopLeft = 0.8f;
                this.colorGreenTopRight = 0.8f;
                this.colorGreenBottomRight = 0.8f;
                this.colorGreenBottomLeft = 0.8f;
                this.colorGreenTopLeft = 0.8f;
                this.colorBlueTopRight = 0.8f;
                this.colorBlueBottomRight = 0.8f;
                this.colorBlueBottomLeft = 0.8f;
                this.colorBlueTopLeft = 0.8f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 2);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 2, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceZNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceZNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1, 3)) {
            if (this.renderMaxZ >= 1.0) {
                ++p_147751_4_;
            }
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessXZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessYZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (this.renderMaxZ >= 1.0) {
                --p_147751_4_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            var20 = var14;
            if (this.renderMaxZ >= 1.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_, p_147751_4_ + 1).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            var9 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var21 + this.aoLightValueScratchYZPP) / 4.0f;
            var12 = (var21 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            var11 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0f;
            var10 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var21) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                this.brightnessBottomRight = this.brightnessBottomLeft = var20;
                this.brightnessTopRight = this.brightnessBottomLeft;
                this.brightnessTopLeft = this.brightnessBottomLeft;
            }
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.8f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.8f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.8f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.8f;
                this.colorRedBottomRight = 0.8f;
                this.colorRedBottomLeft = 0.8f;
                this.colorRedTopLeft = 0.8f;
                this.colorGreenTopRight = 0.8f;
                this.colorGreenBottomRight = 0.8f;
                this.colorGreenBottomLeft = 0.8f;
                this.colorGreenTopLeft = 0.8f;
                this.colorBlueTopRight = 0.8f;
                this.colorBlueBottomRight = 0.8f;
                this.colorBlueBottomLeft = 0.8f;
                this.colorBlueTopLeft = 0.8f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 3);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 3, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceZPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceZPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_, 4)) {
            if (this.renderMinX <= 0.0) {
                --p_147751_2_;
            }
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXYNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessXZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessXZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoBrightnessXYNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            var16 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
                this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
            }
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
                this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
                this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
                this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
            }
            if (this.renderMinX <= 0.0) {
                ++p_147751_2_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            var20 = var14;
            if (this.renderMinX <= 0.0 || !this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            var12 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var21 + this.aoLightValueScratchXZNP) / 4.0f;
            var9 = (var21 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0f;
            var10 = (this.aoLightValueScratchXZNN + var21 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0f;
            var11 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var21) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                this.brightnessBottomRight = this.brightnessBottomLeft = var20;
                this.brightnessTopRight = this.brightnessBottomLeft;
                this.brightnessTopLeft = this.brightnessBottomLeft;
            }
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.6f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.6f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.6f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.6f;
                this.colorRedBottomRight = 0.6f;
                this.colorRedBottomLeft = 0.6f;
                this.colorRedTopLeft = 0.6f;
                this.colorGreenTopRight = 0.6f;
                this.colorGreenBottomRight = 0.6f;
                this.colorGreenBottomLeft = 0.6f;
                this.colorGreenTopLeft = 0.6f;
                this.colorBlueTopRight = 0.6f;
                this.colorBlueBottomRight = 0.6f;
                this.colorBlueBottomLeft = 0.6f;
                this.colorBlueTopLeft = 0.6f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 4);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 4, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceXNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceXNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_, 5)) {
            if (this.renderMaxX >= 1.0) {
                ++p_147751_2_;
            }
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXYPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessXZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessXZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoBrightnessXYPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
                this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
                this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
                this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
                this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
            }
            if (this.renderMaxX >= 1.0) {
                --p_147751_2_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            var20 = var14;
            if (this.renderMaxX >= 1.0 || !this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            var9 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var21 + this.aoLightValueScratchXZPP) / 4.0f;
            var10 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var21) / 4.0f;
            var11 = (this.aoLightValueScratchXZPN + var21 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0f;
            var12 = (var21 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                this.brightnessBottomRight = this.brightnessBottomLeft = var20;
                this.brightnessTopRight = this.brightnessBottomLeft;
                this.brightnessTopLeft = this.brightnessBottomLeft;
            }
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.6f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.6f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.6f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.6f;
                this.colorRedBottomRight = 0.6f;
                this.colorRedBottomLeft = 0.6f;
                this.colorRedTopLeft = 0.6f;
                this.colorGreenTopRight = 0.6f;
                this.colorGreenBottomRight = 0.6f;
                this.colorGreenBottomLeft = 0.6f;
                this.colorGreenTopLeft = 0.6f;
                this.colorBlueTopRight = 0.6f;
                this.colorBlueBottomRight = 0.6f;
                this.colorBlueBottomLeft = 0.6f;
                this.colorBlueTopLeft = 0.6f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 5);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 5, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceXPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceXPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        this.enableAO = false;
        return var8;
    }

    public boolean renderStandardBlockWithAmbientOcclusionPartial(Block p_147808_1_, int p_147808_2_, int p_147808_3_, int p_147808_4_, float p_147808_5_, float p_147808_6_, float p_147808_7_) {
        float var25;
        boolean var17;
        int var28;
        int var26;
        int var20;
        boolean var16;
        int var27;
        int var29;
        float var21;
        float var22;
        IIcon var30;
        float var23;
        boolean var18;
        boolean var19;
        float var24;
        this.enableAO = true;
        boolean var8 = false;
        float var9 = 0.0f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        float var12 = 0.0f;
        boolean var13 = true;
        int var14 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_);
        Tessellator var15 = Tessellator.instance;
        var15.setBrightness(983055);
        if (p_147808_1_ == Blocks.grass) {
            var13 = false;
        } else if (this.hasOverrideBlockTexture()) {
            var13 = false;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_, 0)) {
            if (this.renderMinY <= 0.0) {
                --p_147808_3_;
            }
            this.aoBrightnessXYNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessYZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoBrightnessXYPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (this.renderMinY <= 0.0) {
                ++p_147808_3_;
            }
            var20 = var14;
            if (this.renderMinY <= 0.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var12 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0f;
            var11 = (var21 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0f;
            var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var21 + this.aoLightValueScratchYZNN) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var20);
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.5f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.5f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.5f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.5f;
                this.colorRedBottomRight = 0.5f;
                this.colorRedBottomLeft = 0.5f;
                this.colorRedTopLeft = 0.5f;
                this.colorGreenTopRight = 0.5f;
                this.colorGreenBottomRight = 0.5f;
                this.colorGreenBottomLeft = 0.5f;
                this.colorGreenTopLeft = 0.5f;
                this.colorBlueTopRight = 0.5f;
                this.colorBlueBottomRight = 0.5f;
                this.colorBlueBottomLeft = 0.5f;
                this.colorBlueTopLeft = 0.5f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 0));
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_, 1)) {
            if (this.renderMaxY >= 1.0) {
                ++p_147808_3_;
            }
            this.aoBrightnessXYNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessXYPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessYZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (this.renderMaxY >= 1.0) {
                --p_147808_3_;
            }
            var20 = var14;
            if (this.renderMaxY >= 1.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var21) / 4.0f;
            var9 = (this.aoLightValueScratchYZPP + var21 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0f;
            var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0f;
            var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var20);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 1));
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1, 2)) {
            if (this.renderMinZ <= 0.0) {
                --p_147808_4_;
            }
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessXZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessYZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (this.renderMinZ <= 0.0) {
                ++p_147808_4_;
            }
            var20 = var14;
            if (this.renderMinZ <= 0.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_, p_147808_4_ - 1).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            var22 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            var23 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0f;
            var24 = (this.aoLightValueScratchYZNN + var21 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0f;
            var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var21) / 4.0f;
            var9 = (float)((double)var22 * this.renderMaxY * (1.0 - this.renderMinX) + (double)var23 * this.renderMaxY * this.renderMinX + (double)var24 * (1.0 - this.renderMaxY) * this.renderMinX + (double)var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            var10 = (float)((double)var22 * this.renderMaxY * (1.0 - this.renderMaxX) + (double)var23 * this.renderMaxY * this.renderMaxX + (double)var24 * (1.0 - this.renderMaxY) * this.renderMaxX + (double)var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            var11 = (float)((double)var22 * this.renderMinY * (1.0 - this.renderMaxX) + (double)var23 * this.renderMinY * this.renderMaxX + (double)var24 * (1.0 - this.renderMinY) * this.renderMaxX + (double)var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            var12 = (float)((double)var22 * this.renderMinY * (1.0 - this.renderMinX) + (double)var23 * this.renderMinY * this.renderMinX + (double)var24 * (1.0 - this.renderMinY) * this.renderMinX + (double)var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            var26 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            var27 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var20);
            var28 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var20);
            var29 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0 - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0 - this.renderMaxY) * this.renderMinX, (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0 - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0 - this.renderMaxY) * this.renderMaxX, (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            this.brightnessBottomRight = this.mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0 - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0 - this.renderMinY) * this.renderMaxX, (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            this.brightnessTopRight = this.mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0 - this.renderMinX), this.renderMinY * this.renderMinX, (1.0 - this.renderMinY) * this.renderMinX, (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.8f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.8f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.8f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.8f;
                this.colorRedBottomRight = 0.8f;
                this.colorRedBottomLeft = 0.8f;
                this.colorRedTopLeft = 0.8f;
                this.colorGreenTopRight = 0.8f;
                this.colorGreenBottomRight = 0.8f;
                this.colorGreenBottomLeft = 0.8f;
                this.colorGreenTopLeft = 0.8f;
                this.colorBlueTopRight = 0.8f;
                this.colorBlueBottomRight = 0.8f;
                this.colorBlueBottomLeft = 0.8f;
                this.colorBlueTopLeft = 0.8f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 2);
            this.renderFaceZNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceZNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1, 3)) {
            if (this.renderMaxZ >= 1.0) {
                ++p_147808_4_;
            }
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessXZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessYZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (this.renderMaxZ >= 1.0) {
                --p_147808_4_;
            }
            var20 = var14;
            if (this.renderMaxZ >= 1.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_, p_147808_4_ + 1).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            var22 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var21 + this.aoLightValueScratchYZPP) / 4.0f;
            var23 = (var21 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            var24 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0f;
            var25 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var9 = (float)((double)var22 * this.renderMaxY * (1.0 - this.renderMinX) + (double)var23 * this.renderMaxY * this.renderMinX + (double)var24 * (1.0 - this.renderMaxY) * this.renderMinX + (double)var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            var10 = (float)((double)var22 * this.renderMinY * (1.0 - this.renderMinX) + (double)var23 * this.renderMinY * this.renderMinX + (double)var24 * (1.0 - this.renderMinY) * this.renderMinX + (double)var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            var11 = (float)((double)var22 * this.renderMinY * (1.0 - this.renderMaxX) + (double)var23 * this.renderMinY * this.renderMaxX + (double)var24 * (1.0 - this.renderMinY) * this.renderMaxX + (double)var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            var12 = (float)((double)var22 * this.renderMaxY * (1.0 - this.renderMaxX) + (double)var23 * this.renderMaxY * this.renderMaxX + (double)var24 * (1.0 - this.renderMaxY) * this.renderMaxX + (double)var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            var26 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var20);
            var27 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var20);
            var28 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            var29 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0 - this.renderMinX), (1.0 - this.renderMaxY) * (1.0 - this.renderMinX), (1.0 - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0 - this.renderMinX), (1.0 - this.renderMinY) * (1.0 - this.renderMinX), (1.0 - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
            this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0 - this.renderMaxX), (1.0 - this.renderMinY) * (1.0 - this.renderMaxX), (1.0 - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
            this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0 - this.renderMaxX), (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX), (1.0 - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.8f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.8f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.8f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.8f;
                this.colorRedBottomRight = 0.8f;
                this.colorRedBottomLeft = 0.8f;
                this.colorRedTopLeft = 0.8f;
                this.colorGreenTopRight = 0.8f;
                this.colorGreenBottomRight = 0.8f;
                this.colorGreenBottomLeft = 0.8f;
                this.colorGreenTopLeft = 0.8f;
                this.colorBlueTopRight = 0.8f;
                this.colorBlueBottomRight = 0.8f;
                this.colorBlueBottomLeft = 0.8f;
                this.colorBlueTopLeft = 0.8f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 3);
            this.renderFaceZPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceZPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_, 4)) {
            if (this.renderMinX <= 0.0) {
                --p_147808_2_;
            }
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXYNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessXZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessXZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoBrightnessXYNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            var16 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
                this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
            }
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
                this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
                this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
                this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
            }
            if (this.renderMinX <= 0.0) {
                ++p_147808_2_;
            }
            var20 = var14;
            if (this.renderMinX <= 0.0 || !this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            var22 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var21 + this.aoLightValueScratchXZNP) / 4.0f;
            var23 = (var21 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0f;
            var24 = (this.aoLightValueScratchXZNN + var21 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0f;
            var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var21) / 4.0f;
            var9 = (float)((double)var23 * this.renderMaxY * this.renderMaxZ + (double)var24 * this.renderMaxY * (1.0 - this.renderMaxZ) + (double)var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ) + (double)var22 * (1.0 - this.renderMaxY) * this.renderMaxZ);
            var10 = (float)((double)var23 * this.renderMaxY * this.renderMinZ + (double)var24 * this.renderMaxY * (1.0 - this.renderMinZ) + (double)var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ) + (double)var22 * (1.0 - this.renderMaxY) * this.renderMinZ);
            var11 = (float)((double)var23 * this.renderMinY * this.renderMinZ + (double)var24 * this.renderMinY * (1.0 - this.renderMinZ) + (double)var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinZ) + (double)var22 * (1.0 - this.renderMinY) * this.renderMinZ);
            var12 = (float)((double)var23 * this.renderMinY * this.renderMaxZ + (double)var24 * this.renderMinY * (1.0 - this.renderMaxZ) + (double)var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ) + (double)var22 * (1.0 - this.renderMinY) * this.renderMaxZ);
            var26 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
            var27 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
            var28 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
            var29 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0 - this.renderMaxZ), (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ), (1.0 - this.renderMaxY) * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0 - this.renderMinZ), (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ), (1.0 - this.renderMaxY) * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0 - this.renderMinZ), (1.0 - this.renderMinY) * (1.0 - this.renderMinZ), (1.0 - this.renderMinY) * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0 - this.renderMaxZ), (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ), (1.0 - this.renderMinY) * this.renderMaxZ);
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.6f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.6f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.6f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.6f;
                this.colorRedBottomRight = 0.6f;
                this.colorRedBottomLeft = 0.6f;
                this.colorRedTopLeft = 0.6f;
                this.colorGreenTopRight = 0.6f;
                this.colorGreenBottomRight = 0.6f;
                this.colorGreenBottomLeft = 0.6f;
                this.colorGreenTopLeft = 0.6f;
                this.colorBlueTopRight = 0.6f;
                this.colorBlueBottomRight = 0.6f;
                this.colorBlueBottomLeft = 0.6f;
                this.colorBlueTopLeft = 0.6f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 4);
            this.renderFaceXNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceXNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_, 5)) {
            if (this.renderMaxX >= 1.0) {
                ++p_147808_2_;
            }
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXYPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessXZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessXZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoBrightnessXYPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            var17 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            var18 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            var19 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
                this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
                this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
                this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
                this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
            }
            if (this.renderMaxX >= 1.0) {
                --p_147808_2_;
            }
            var20 = var14;
            if (this.renderMaxX >= 1.0 || !this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            }
            var21 = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            var22 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var21 + this.aoLightValueScratchXZPP) / 4.0f;
            var23 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var21) / 4.0f;
            var24 = (this.aoLightValueScratchXZPN + var21 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0f;
            var25 = (var21 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            var9 = (float)((double)var22 * (1.0 - this.renderMinY) * this.renderMaxZ + (double)var23 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ) + (double)var24 * this.renderMinY * (1.0 - this.renderMaxZ) + (double)var25 * this.renderMinY * this.renderMaxZ);
            var10 = (float)((double)var22 * (1.0 - this.renderMinY) * this.renderMinZ + (double)var23 * (1.0 - this.renderMinY) * (1.0 - this.renderMinZ) + (double)var24 * this.renderMinY * (1.0 - this.renderMinZ) + (double)var25 * this.renderMinY * this.renderMinZ);
            var11 = (float)((double)var22 * (1.0 - this.renderMaxY) * this.renderMinZ + (double)var23 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ) + (double)var24 * this.renderMaxY * (1.0 - this.renderMinZ) + (double)var25 * this.renderMaxY * this.renderMinZ);
            var12 = (float)((double)var22 * (1.0 - this.renderMaxY) * this.renderMaxZ + (double)var23 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ) + (double)var24 * this.renderMaxY * (1.0 - this.renderMaxZ) + (double)var25 * this.renderMaxY * this.renderMaxZ);
            var26 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            var27 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var20);
            var28 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var20);
            var29 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMinY) * this.renderMaxZ, (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ), this.renderMinY * (1.0 - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMinY) * this.renderMinZ, (1.0 - this.renderMinY) * (1.0 - this.renderMinZ), this.renderMinY * (1.0 - this.renderMinZ), this.renderMinY * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMaxY) * this.renderMinZ, (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ), this.renderMaxY * (1.0 - this.renderMinZ), this.renderMaxY * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMaxY) * this.renderMaxZ, (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ), this.renderMaxY * (1.0 - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);
            if (var13) {
                this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.6f;
                this.colorRedBottomLeft = this.colorRedTopRight;
                this.colorRedTopLeft = this.colorRedTopRight;
                this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.6f;
                this.colorGreenBottomLeft = this.colorGreenTopRight;
                this.colorGreenTopLeft = this.colorGreenTopRight;
                this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.6f;
                this.colorBlueBottomLeft = this.colorBlueTopRight;
                this.colorBlueTopLeft = this.colorBlueTopRight;
            } else {
                this.colorRedTopRight = 0.6f;
                this.colorRedBottomRight = 0.6f;
                this.colorRedBottomLeft = 0.6f;
                this.colorRedTopLeft = 0.6f;
                this.colorGreenTopRight = 0.6f;
                this.colorGreenBottomRight = 0.6f;
                this.colorGreenBottomLeft = 0.6f;
                this.colorGreenTopLeft = 0.6f;
                this.colorBlueTopRight = 0.6f;
                this.colorBlueBottomRight = 0.6f;
                this.colorBlueBottomLeft = 0.6f;
                this.colorBlueTopLeft = 0.6f;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 5);
            this.renderFaceXPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceXPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        this.enableAO = false;
        return var8;
    }

    public int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_) {
        if (p_147778_1_ == 0) {
            p_147778_1_ = p_147778_4_;
        }
        if (p_147778_2_ == 0) {
            p_147778_2_ = p_147778_4_;
        }
        if (p_147778_3_ == 0) {
            p_147778_3_ = p_147778_4_;
        }
        return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 16711935;
    }

    public int mixAoBrightness(int p_147727_1_, int p_147727_2_, int p_147727_3_, int p_147727_4_, double p_147727_5_, double p_147727_7_, double p_147727_9_, double p_147727_11_) {
        int var13 = (int)((double)(p_147727_1_ >> 16 & 255) * p_147727_5_ + (double)(p_147727_2_ >> 16 & 255) * p_147727_7_ + (double)(p_147727_3_ >> 16 & 255) * p_147727_9_ + (double)(p_147727_4_ >> 16 & 255) * p_147727_11_) & 255;
        int var14 = (int)((double)(p_147727_1_ & 255) * p_147727_5_ + (double)(p_147727_2_ & 255) * p_147727_7_ + (double)(p_147727_3_ & 255) * p_147727_9_ + (double)(p_147727_4_ & 255) * p_147727_11_) & 255;
        return var13 << 16 | var14;
    }

    public boolean renderStandardBlockWithColorMultiplier(Block p_147736_1_, int p_147736_2_, int p_147736_3_, int p_147736_4_, float p_147736_5_, float p_147736_6_, float p_147736_7_) {
        float var22;
        float var13;
        IIcon var271;
        float var19;
        float var27;
        float var25;
        this.enableAO = false;
        boolean defaultTexture = Tessellator.instance.defaultTexture;
        boolean betterGrass = Config.isBetterGrass() && defaultTexture;
        Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        int var26 = -1;
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_, 0)) {
            if (var26 < 0) {
                var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            var13 = var27 = 0.5f;
            var19 = var27;
            var22 = var27;
            if (p_147736_1_ != Blocks.grass) {
                var13 = var27 * p_147736_5_;
                var19 = var27 * p_147736_6_;
                var22 = var27 * p_147736_7_;
            }
            var8.setBrightness(this.renderMinY > 0.0 ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_));
            var8.setColorOpaque_F(var13, var19, var22);
            this.renderFaceYNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 0));
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_, 1)) {
            if (var26 < 0) {
                var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            var27 = 1.0f;
            var13 = var27 * p_147736_5_;
            var19 = var27 * p_147736_6_;
            var22 = var27 * p_147736_7_;
            var8.setBrightness(this.renderMaxY < 1.0 ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_));
            var8.setColorOpaque_F(var13, var19, var22);
            this.renderFaceYPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 1));
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1, 2)) {
            if (var26 < 0) {
                var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            var19 = var13 = 0.8f;
            var22 = var13;
            var25 = var13;
            if (p_147736_1_ != Blocks.grass) {
                var19 = var13 * p_147736_5_;
                var22 = var13 * p_147736_6_;
                var25 = var13 * p_147736_7_;
            }
            var8.setBrightness(this.renderMinZ > 0.0 ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1));
            var8.setColorOpaque_F(var19, var22, var25);
            var271 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
            if (betterGrass) {
                if ((var271 == TextureUtils.iconGrassSide || var271 == TextureUtils.iconMyceliumSide) && (var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2, var271)) == TextureUtils.iconGrassTop) {
                    var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                }
                if (var271 == TextureUtils.iconGrassSideSnowed) {
                    var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
                }
            }
            this.renderFaceZNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
            if (defaultTexture && fancyGrass && var271 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                this.renderFaceZNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1, 3)) {
            if (var26 < 0) {
                var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            var19 = var13 = 0.8f;
            var22 = var13;
            var25 = var13;
            if (p_147736_1_ != Blocks.grass) {
                var19 = var13 * p_147736_5_;
                var22 = var13 * p_147736_6_;
                var25 = var13 * p_147736_7_;
            }
            var8.setBrightness(this.renderMaxZ < 1.0 ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1));
            var8.setColorOpaque_F(var19, var22, var25);
            var271 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
            if (betterGrass) {
                if ((var271 == TextureUtils.iconGrassSide || var271 == TextureUtils.iconMyceliumSide) && (var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3, var271)) == TextureUtils.iconGrassTop) {
                    var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                }
                if (var271 == TextureUtils.iconGrassSideSnowed) {
                    var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
                }
            }
            this.renderFaceZPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
            if (defaultTexture && fancyGrass && var271 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                this.renderFaceZPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_, 4)) {
            if (var26 < 0) {
                var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            var19 = var13 = 0.6f;
            var22 = var13;
            var25 = var13;
            if (p_147736_1_ != Blocks.grass) {
                var19 = var13 * p_147736_5_;
                var22 = var13 * p_147736_6_;
                var25 = var13 * p_147736_7_;
            }
            var8.setBrightness(this.renderMinX > 0.0 ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_));
            var8.setColorOpaque_F(var19, var22, var25);
            var271 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
            if (betterGrass) {
                if ((var271 == TextureUtils.iconGrassSide || var271 == TextureUtils.iconMyceliumSide) && (var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4, var271)) == TextureUtils.iconGrassTop) {
                    var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                }
                if (var271 == TextureUtils.iconGrassSideSnowed) {
                    var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
                }
            }
            this.renderFaceXNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
            if (defaultTexture && fancyGrass && var271 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                this.renderFaceXNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_, 5)) {
            if (var26 < 0) {
                var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            var19 = var13 = 0.6f;
            var22 = var13;
            var25 = var13;
            if (p_147736_1_ != Blocks.grass) {
                var19 = var13 * p_147736_5_;
                var22 = var13 * p_147736_6_;
                var25 = var13 * p_147736_7_;
            }
            var8.setBrightness(this.renderMaxX < 1.0 ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_));
            var8.setColorOpaque_F(var19, var22, var25);
            var271 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
            if (betterGrass) {
                if ((var271 == TextureUtils.iconGrassSide || var271 == TextureUtils.iconMyceliumSide) && (var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5, var271)) == TextureUtils.iconGrassTop) {
                    var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                }
                if (var271 == TextureUtils.iconGrassSideSnowed) {
                    var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
                }
            }
            this.renderFaceXPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
            if (defaultTexture && fancyGrass && var271 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
                this.renderFaceXPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        return var9;
    }

    public boolean renderBlockCocoa(BlockCocoa p_147772_1_, int p_147772_2_, int p_147772_3_, int p_147772_4_) {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147772_1_.getBlockBrightness(this.blockAccess, p_147772_2_, p_147772_3_, p_147772_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        int var6 = this.blockAccess.getBlockMetadata(p_147772_2_, p_147772_3_, p_147772_4_);
        int var7 = BlockDirectional.func_149895_l(var6);
        int var8 = BlockCocoa.func_149987_c(var6);
        IIcon var9 = p_147772_1_.func_149988_b(var8);
        int var10 = 4 + var8 * 2;
        int var11 = 5 + var8 * 2;
        double var12 = 15.0 - (double)var10;
        double var14 = 15.0;
        double var16 = 4.0;
        double var18 = 4.0 + (double)var11;
        double var20 = var9.getInterpolatedU(var12);
        double var22 = var9.getInterpolatedU(var14);
        double var24 = var9.getInterpolatedV(var16);
        double var26 = var9.getInterpolatedV(var18);
        double var28 = 0.0;
        double var30 = 0.0;
        switch (var7) {
            case 0: {
                var28 = 8.0 - (double)(var10 / 2);
                var30 = 15.0 - (double)var10;
                break;
            }
            case 1: {
                var28 = 1.0;
                var30 = 8.0 - (double)(var10 / 2);
                break;
            }
            case 2: {
                var28 = 8.0 - (double)(var10 / 2);
                var30 = 1.0;
                break;
            }
            case 3: {
                var28 = 15.0 - (double)var10;
                var30 = 8.0 - (double)(var10 / 2);
            }
        }
        double var32 = (double)p_147772_2_ + var28 / 16.0;
        double var34 = (double)p_147772_2_ + (var28 + (double)var10) / 16.0;
        double var36 = (double)p_147772_3_ + (12.0 - (double)var11) / 16.0;
        double var38 = (double)p_147772_3_ + 0.75;
        double var40 = (double)p_147772_4_ + var30 / 16.0;
        double var42 = (double)p_147772_4_ + (var30 + (double)var10) / 16.0;
        var5.addVertexWithUV(var32, var36, var40, var20, var26);
        var5.addVertexWithUV(var32, var36, var42, var22, var26);
        var5.addVertexWithUV(var32, var38, var42, var22, var24);
        var5.addVertexWithUV(var32, var38, var40, var20, var24);
        var5.addVertexWithUV(var34, var36, var42, var20, var26);
        var5.addVertexWithUV(var34, var36, var40, var22, var26);
        var5.addVertexWithUV(var34, var38, var40, var22, var24);
        var5.addVertexWithUV(var34, var38, var42, var20, var24);
        var5.addVertexWithUV(var34, var36, var40, var20, var26);
        var5.addVertexWithUV(var32, var36, var40, var22, var26);
        var5.addVertexWithUV(var32, var38, var40, var22, var24);
        var5.addVertexWithUV(var34, var38, var40, var20, var24);
        var5.addVertexWithUV(var32, var36, var42, var20, var26);
        var5.addVertexWithUV(var34, var36, var42, var22, var26);
        var5.addVertexWithUV(var34, var38, var42, var22, var24);
        var5.addVertexWithUV(var32, var38, var42, var20, var24);
        int var44 = var10;
        if (var8 >= 2) {
            var44 = var10 - 1;
        }
        var20 = var9.getMinU();
        var22 = var9.getInterpolatedU(var44);
        var24 = var9.getMinV();
        var26 = var9.getInterpolatedV(var44);
        var5.addVertexWithUV(var32, var38, var42, var20, var26);
        var5.addVertexWithUV(var34, var38, var42, var22, var26);
        var5.addVertexWithUV(var34, var38, var40, var22, var24);
        var5.addVertexWithUV(var32, var38, var40, var20, var24);
        var5.addVertexWithUV(var32, var36, var40, var20, var24);
        var5.addVertexWithUV(var34, var36, var40, var22, var24);
        var5.addVertexWithUV(var34, var36, var42, var22, var26);
        var5.addVertexWithUV(var32, var36, var42, var20, var26);
        var20 = var9.getInterpolatedU(12.0);
        var22 = var9.getMaxU();
        var24 = var9.getMinV();
        var26 = var9.getInterpolatedV(4.0);
        var28 = 8.0;
        var30 = 0.0;
        switch (var7) {
            case 0: {
                var28 = 8.0;
                var30 = 12.0;
                double var45 = var20;
                var20 = var22;
                var22 = var45;
                break;
            }
            case 1: {
                var28 = 0.0;
                var30 = 8.0;
                break;
            }
            case 2: {
                var28 = 8.0;
                var30 = 0.0;
                break;
            }
            case 3: {
                var28 = 12.0;
                var30 = 8.0;
                double var45 = var20;
                var20 = var22;
                var22 = var45;
            }
        }
        var32 = (double)p_147772_2_ + var28 / 16.0;
        var34 = (double)p_147772_2_ + (var28 + 4.0) / 16.0;
        var36 = (double)p_147772_3_ + 0.75;
        var38 = (double)p_147772_3_ + 1.0;
        var40 = (double)p_147772_4_ + var30 / 16.0;
        var42 = (double)p_147772_4_ + (var30 + 4.0) / 16.0;
        if (var7 != 2 && var7 != 0) {
            if (var7 == 1 || var7 == 3) {
                var5.addVertexWithUV(var34, var36, var40, var20, var26);
                var5.addVertexWithUV(var32, var36, var40, var22, var26);
                var5.addVertexWithUV(var32, var38, var40, var22, var24);
                var5.addVertexWithUV(var34, var38, var40, var20, var24);
                var5.addVertexWithUV(var32, var36, var40, var22, var26);
                var5.addVertexWithUV(var34, var36, var40, var20, var26);
                var5.addVertexWithUV(var34, var38, var40, var20, var24);
                var5.addVertexWithUV(var32, var38, var40, var22, var24);
            }
        } else {
            var5.addVertexWithUV(var32, var36, var40, var22, var26);
            var5.addVertexWithUV(var32, var36, var42, var20, var26);
            var5.addVertexWithUV(var32, var38, var42, var20, var24);
            var5.addVertexWithUV(var32, var38, var40, var22, var24);
            var5.addVertexWithUV(var32, var36, var42, var20, var26);
            var5.addVertexWithUV(var32, var36, var40, var22, var26);
            var5.addVertexWithUV(var32, var38, var40, var22, var24);
            var5.addVertexWithUV(var32, var38, var42, var20, var24);
        }
        return true;
    }

    public boolean renderBlockBeacon(BlockBeacon p_147797_1_, int p_147797_2_, int p_147797_3_, int p_147797_4_) {
        float var5 = 0.1875f;
        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.glass));
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        this.renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
        this.renderAllFaces = true;
        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.obsidian));
        this.setRenderBounds(0.125, 0.0062500000931322575, 0.125, 0.875, var5, 0.875);
        this.renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
        IIcon iconBeacon = this.getBlockIcon(Blocks.beacon);
        if (Config.isConnectedTextures()) {
            iconBeacon = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_, -1, iconBeacon);
        }
        this.setOverrideBlockTexture(iconBeacon);
        this.setRenderBounds(0.1875, var5, 0.1875, 0.8125, 0.875, 0.8125);
        this.renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
        this.renderAllFaces = false;
        this.clearOverrideBlockTexture();
        return true;
    }

    public boolean renderBlockCactus(Block p_147755_1_, int p_147755_2_, int p_147755_3_, int p_147755_4_) {
        int var5 = p_147755_1_.colorMultiplier(this.blockAccess, p_147755_2_, p_147755_3_, p_147755_4_);
        float var6 = (float)(var5 >> 16 & 255) / 255.0f;
        float var7 = (float)(var5 >> 8 & 255) / 255.0f;
        float var8 = (float)(var5 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var9 = (var6 * 30.0f + var7 * 59.0f + var8 * 11.0f) / 100.0f;
            float var10 = (var6 * 30.0f + var7 * 70.0f) / 100.0f;
            float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }
        return this.renderBlockCactusImpl(p_147755_1_, p_147755_2_, p_147755_3_, p_147755_4_, var6, var7, var8);
    }

    public boolean renderBlockCactusImpl(Block p_147754_1_, int p_147754_2_, int p_147754_3_, int p_147754_4_, float p_147754_5_, float p_147754_6_, float p_147754_7_) {
        Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        float var10 = 0.5f;
        float var11 = 1.0f;
        float var12 = 0.8f;
        float var13 = 0.6f;
        float var14 = var10 * p_147754_5_;
        float var15 = var11 * p_147754_5_;
        float var16 = var12 * p_147754_5_;
        float var17 = var13 * p_147754_5_;
        float var18 = var10 * p_147754_6_;
        float var19 = var11 * p_147754_6_;
        float var20 = var12 * p_147754_6_;
        float var21 = var13 * p_147754_6_;
        float var22 = var10 * p_147754_7_;
        float var23 = var11 * p_147754_7_;
        float var24 = var12 * p_147754_7_;
        float var25 = var13 * p_147754_7_;
        float var26 = 0.0625f;
        int var27 = p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_);
        if (this.renderAllFaces || p_147754_1_.shouldSideBeRendered(this.blockAccess, p_147754_2_, p_147754_3_ - 1, p_147754_4_, 0)) {
            var8.setBrightness(this.renderMinY > 0.0 ? var27 : p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_ - 1, p_147754_4_));
            var8.setColorOpaque_F(var14, var18, var22);
            this.renderFaceYNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 0));
        }
        if (this.renderAllFaces || p_147754_1_.shouldSideBeRendered(this.blockAccess, p_147754_2_, p_147754_3_ + 1, p_147754_4_, 1)) {
            var8.setBrightness(this.renderMaxY < 1.0 ? var27 : p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_ + 1, p_147754_4_));
            var8.setColorOpaque_F(var15, var19, var23);
            this.renderFaceYPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 1));
        }
        var8.setBrightness(var27);
        var8.setColorOpaque_F(var16, var20, var24);
        var8.addTranslation(0.0f, 0.0f, var26);
        this.renderFaceZNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 2));
        var8.addTranslation(0.0f, 0.0f, - var26);
        var8.addTranslation(0.0f, 0.0f, - var26);
        this.renderFaceZPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 3));
        var8.addTranslation(0.0f, 0.0f, var26);
        var8.setColorOpaque_F(var17, var21, var25);
        var8.addTranslation(var26, 0.0f, 0.0f);
        this.renderFaceXNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 4));
        var8.addTranslation(- var26, 0.0f, 0.0f);
        var8.addTranslation(- var26, 0.0f, 0.0f);
        this.renderFaceXPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 5));
        var8.addTranslation(var26, 0.0f, 0.0f);
        return true;
    }

    public boolean renderBlockFence(BlockFence p_147735_1_, int p_147735_2_, int p_147735_3_, int p_147735_4_) {
        float var19;
        boolean var5 = false;
        float var6 = 0.375f;
        float var7 = 0.625f;
        this.setRenderBounds(var6, 0.0, var6, var7, 1.0, var7);
        this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
        var5 = true;
        boolean var8 = false;
        boolean var9 = false;
        if (p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ - 1, p_147735_3_, p_147735_4_) || p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ + 1, p_147735_3_, p_147735_4_)) {
            var8 = true;
        }
        if (p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ - 1) || p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ + 1)) {
            var9 = true;
        }
        boolean var10 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ - 1, p_147735_3_, p_147735_4_);
        boolean var11 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ + 1, p_147735_3_, p_147735_4_);
        boolean var12 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ - 1);
        boolean var13 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ + 1);
        if (!var8 && !var9) {
            var8 = true;
        }
        var6 = 0.4375f;
        var7 = 0.5625f;
        float var14 = 0.75f;
        float var15 = 0.9375f;
        float var16 = var10 ? 0.0f : var6;
        float var17 = var11 ? 1.0f : var7;
        float var18 = var12 ? 0.0f : var6;
        float f = var19 = var13 ? 1.0f : var7;
        if (var8) {
            this.setRenderBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        if (var9) {
            this.setRenderBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        var14 = 0.375f;
        var15 = 0.5625f;
        if (var8) {
            this.setRenderBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        if (var9) {
            this.setRenderBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        p_147735_1_.setBlockBoundsBasedOnState(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147735_2_, p_147735_3_, p_147735_4_)) {
            this.renderSnow(p_147735_2_, p_147735_3_, p_147735_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return var5;
    }

    public boolean renderBlockWall(BlockWall p_147807_1_, int p_147807_2_, int p_147807_3_, int p_147807_4_) {
        boolean var5 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_ - 1, p_147807_3_, p_147807_4_);
        boolean var6 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_ + 1, p_147807_3_, p_147807_4_);
        boolean var7 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_ - 1);
        boolean var8 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_ + 1);
        boolean var9 = var7 && var8 && !var5 && !var6;
        boolean var10 = !var7 && !var8 && var5 && var6;
        boolean var11 = this.blockAccess.isAirBlock(p_147807_2_, p_147807_3_ + 1, p_147807_4_);
        if ((var9 || var10) && var11) {
            if (var9) {
                this.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.8125, 1.0);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            } else {
                this.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
        } else {
            this.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
            this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            if (var5) {
                this.setRenderBounds(0.0, 0.0, 0.3125, 0.25, 0.8125, 0.6875);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
            if (var6) {
                this.setRenderBounds(0.75, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
            if (var7) {
                this.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.8125, 0.25);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
            if (var8) {
                this.setRenderBounds(0.3125, 0.0, 0.75, 0.6875, 0.8125, 1.0);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
        }
        p_147807_1_.setBlockBoundsBasedOnState(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147807_2_, p_147807_3_, p_147807_4_)) {
            this.renderSnow(p_147807_2_, p_147807_3_, p_147807_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }

    public boolean renderBlockDragonEgg(BlockDragonEgg p_147802_1_, int p_147802_2_, int p_147802_3_, int p_147802_4_) {
        boolean var5 = false;
        int var6 = 0;
        int var7 = 0;
        while (var7 < 8) {
            int var8 = 0;
            int var9 = 1;
            if (var7 == 0) {
                var8 = 2;
            }
            if (var7 == 1) {
                var8 = 3;
            }
            if (var7 == 2) {
                var8 = 4;
            }
            if (var7 == 3) {
                var8 = 5;
                var9 = 2;
            }
            if (var7 == 4) {
                var8 = 6;
                var9 = 3;
            }
            if (var7 == 5) {
                var8 = 7;
                var9 = 5;
            }
            if (var7 == 6) {
                var8 = 6;
                var9 = 2;
            }
            if (var7 == 7) {
                var8 = 3;
            }
            float var10 = (float)var8 / 16.0f;
            float var11 = 1.0f - (float)var6 / 16.0f;
            float var12 = 1.0f - (float)(var6 + var9) / 16.0f;
            var6 += var9;
            this.setRenderBounds(0.5f - var10, var12, 0.5f - var10, 0.5f + var10, var11, 0.5f + var10);
            this.renderStandardBlock(p_147802_1_, p_147802_2_, p_147802_3_, p_147802_4_);
            ++var7;
        }
        var5 = true;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return var5;
    }

    public boolean renderBlockFenceGate(BlockFenceGate p_147776_1_, int p_147776_2_, int p_147776_3_, int p_147776_4_) {
        float var18;
        float var17;
        float var16;
        float var15;
        boolean var5 = true;
        int var6 = this.blockAccess.getBlockMetadata(p_147776_2_, p_147776_3_, p_147776_4_);
        boolean var7 = BlockFenceGate.isFenceGateOpen(var6);
        int var8 = BlockDirectional.func_149895_l(var6);
        float var9 = 0.375f;
        float var10 = 0.5625f;
        float var11 = 0.75f;
        float var12 = 0.9375f;
        float var13 = 0.3125f;
        float var14 = 1.0f;
        if ((var8 == 2 || var8 == 0) && this.blockAccess.getBlock(p_147776_2_ - 1, p_147776_3_, p_147776_4_) == Blocks.cobblestone_wall && this.blockAccess.getBlock(p_147776_2_ + 1, p_147776_3_, p_147776_4_) == Blocks.cobblestone_wall || (var8 == 3 || var8 == 1) && this.blockAccess.getBlock(p_147776_2_, p_147776_3_, p_147776_4_ - 1) == Blocks.cobblestone_wall && this.blockAccess.getBlock(p_147776_2_, p_147776_3_, p_147776_4_ + 1) == Blocks.cobblestone_wall) {
            var9 -= 0.1875f;
            var10 -= 0.1875f;
            var11 -= 0.1875f;
            var12 -= 0.1875f;
            var13 -= 0.1875f;
            var14 -= 0.1875f;
        }
        this.renderAllFaces = true;
        if (var8 != 3 && var8 != 1) {
            var15 = 0.0f;
            var16 = 0.125f;
            var17 = 0.4375f;
            var18 = 0.5625f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.875f;
            var16 = 1.0f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
        } else {
            this.uvRotateTop = 1;
            var15 = 0.4375f;
            var16 = 0.5625f;
            var17 = 0.0f;
            var18 = 0.125f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.875f;
            var18 = 1.0f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.uvRotateTop = 0;
        }
        if (var7) {
            if (var8 == 2 || var8 == 0) {
                this.uvRotateTop = 1;
            }
            if (var8 == 3) {
                var15 = 0.0f;
                var16 = 0.125f;
                var17 = 0.875f;
                var18 = 1.0f;
                float var19 = 0.5625f;
                float var20 = 0.8125f;
                float var21 = 0.9375f;
                this.setRenderBounds(0.8125, var9, 0.0, 0.9375, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.8125, var9, 0.875, 0.9375, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var9, 0.0, 0.8125, var10, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var9, 0.875, 0.8125, var10, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var11, 0.0, 0.8125, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var11, 0.875, 0.8125, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            } else if (var8 == 1) {
                var15 = 0.0f;
                var16 = 0.125f;
                var17 = 0.875f;
                var18 = 1.0f;
                float var19 = 0.0625f;
                float var20 = 0.1875f;
                float var21 = 0.4375f;
                this.setRenderBounds(0.0625, var9, 0.0, 0.1875, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0625, var9, 0.875, 0.1875, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var9, 0.0, 0.4375, var10, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var9, 0.875, 0.4375, var10, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var11, 0.0, 0.4375, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var11, 0.875, 0.4375, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            } else if (var8 == 0) {
                var15 = 0.0f;
                var16 = 0.125f;
                var17 = 0.875f;
                var18 = 1.0f;
                float var19 = 0.5625f;
                float var20 = 0.8125f;
                float var21 = 0.9375f;
                this.setRenderBounds(0.0, var9, 0.8125, 0.125, var12, 0.9375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.8125, 1.0, var12, 0.9375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var9, 0.5625, 0.125, var10, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.5625, 1.0, var10, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var11, 0.5625, 0.125, var12, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var11, 0.5625, 1.0, var12, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            } else if (var8 == 2) {
                var15 = 0.0f;
                var16 = 0.125f;
                var17 = 0.875f;
                var18 = 1.0f;
                float var19 = 0.0625f;
                float var20 = 0.1875f;
                float var21 = 0.4375f;
                this.setRenderBounds(0.0, var9, 0.0625, 0.125, var12, 0.1875);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.0625, 1.0, var12, 0.1875);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var9, 0.1875, 0.125, var10, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.1875, 1.0, var10, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var11, 0.1875, 0.125, var12, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var11, 0.1875, 1.0, var12, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            }
        } else if (var8 != 3 && var8 != 1) {
            var15 = 0.375f;
            var16 = 0.5f;
            var17 = 0.4375f;
            var18 = 0.5625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.5f;
            var16 = 0.625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.625f;
            var16 = 0.875f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.125f;
            var16 = 0.375f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
        } else {
            this.uvRotateTop = 1;
            var15 = 0.4375f;
            var16 = 0.5625f;
            var17 = 0.375f;
            var18 = 0.5f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.5f;
            var18 = 0.625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.625f;
            var18 = 0.875f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.125f;
            var18 = 0.375f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147776_2_, p_147776_3_, p_147776_4_)) {
            this.renderSnow(p_147776_2_, p_147776_3_, p_147776_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        this.renderAllFaces = false;
        this.uvRotateTop = 0;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return var5;
    }

    public boolean renderBlockHopper(BlockHopper p_147803_1_, int p_147803_2_, int p_147803_3_, int p_147803_4_) {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147803_1_.getBlockBrightness(this.blockAccess, p_147803_2_, p_147803_3_, p_147803_4_));
        int var6 = p_147803_1_.colorMultiplier(this.blockAccess, p_147803_2_, p_147803_3_, p_147803_4_);
        float var7 = (float)(var6 >> 16 & 255) / 255.0f;
        float var8 = (float)(var6 >> 8 & 255) / 255.0f;
        float var9 = (float)(var6 & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var10;
            var8 = var11;
            var9 = var12;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        return this.renderBlockHopperMetadata(p_147803_1_, p_147803_2_, p_147803_3_, p_147803_4_, this.blockAccess.getBlockMetadata(p_147803_2_, p_147803_3_, p_147803_4_), false);
    }

    public boolean renderBlockHopperMetadata(BlockHopper p_147799_1_, int p_147799_2_, int p_147799_3_, int p_147799_4_, int p_147799_5_, boolean p_147799_6_) {
        float var13;
        Tessellator var7 = Tessellator.instance;
        int var8 = BlockHopper.func_149918_b(p_147799_5_);
        double var9 = 0.625;
        this.setRenderBounds(0.0, var9, 0.0, 1.0, 1.0, 1.0);
        if (p_147799_6_) {
            var7.startDrawingQuads();
            var7.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 0, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 1, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 2, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 3, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 4, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 5, p_147799_5_));
            var7.draw();
        } else {
            this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
        }
        if (!p_147799_6_) {
            var7.setBrightness(p_147799_1_.getBlockBrightness(this.blockAccess, p_147799_2_, p_147799_3_, p_147799_4_));
            int var24 = p_147799_1_.colorMultiplier(this.blockAccess, p_147799_2_, p_147799_3_, p_147799_4_);
            float var25 = (float)(var24 >> 16 & 255) / 255.0f;
            var13 = (float)(var24 >> 8 & 255) / 255.0f;
            float var26 = (float)(var24 & 255) / 255.0f;
            if (EntityRenderer.anaglyphEnable) {
                float var15 = (var25 * 30.0f + var13 * 59.0f + var26 * 11.0f) / 100.0f;
                float var27 = (var25 * 30.0f + var13 * 70.0f) / 100.0f;
                float var17 = (var25 * 30.0f + var26 * 70.0f) / 100.0f;
                var25 = var15;
                var13 = var27;
                var26 = var17;
            }
            var7.setColorOpaque_F(var25, var13, var26);
        }
        IIcon var241 = BlockHopper.func_149916_e("hopper_outside");
        IIcon var251 = BlockHopper.func_149916_e("hopper_inside");
        var13 = 0.125f;
        if (p_147799_6_) {
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147799_1_, -1.0f + var13, 0.0, 0.0, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147799_1_, 1.0f - var13, 0.0, 0.0, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147799_1_, 0.0, 0.0, -1.0f + var13, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147799_1_, 0.0, 0.0, 1.0f - var13, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147799_1_, 0.0, -1.0 + var9, 0.0, var251);
            var7.draw();
        } else {
            this.renderFaceXPos(p_147799_1_, (float)p_147799_2_ - 1.0f + var13, p_147799_3_, p_147799_4_, var241);
            this.renderFaceXNeg(p_147799_1_, (float)p_147799_2_ + 1.0f - var13, p_147799_3_, p_147799_4_, var241);
            this.renderFaceZPos(p_147799_1_, p_147799_2_, p_147799_3_, (float)p_147799_4_ - 1.0f + var13, var241);
            this.renderFaceZNeg(p_147799_1_, p_147799_2_, p_147799_3_, (float)p_147799_4_ + 1.0f - var13, var241);
            this.renderFaceYPos(p_147799_1_, p_147799_2_, (double)((float)p_147799_3_ - 1.0f) + var9, p_147799_4_, var251);
        }
        this.setOverrideBlockTexture(var241);
        double var261 = 0.25;
        double var271 = 0.25;
        this.setRenderBounds(var261, var271, var261, 1.0 - var261, var9 - 0.002, 1.0 - var261);
        if (p_147799_6_) {
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147799_1_, 0.0, 0.0, 0.0, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147799_1_, 0.0, 0.0, 0.0, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147799_1_, 0.0, 0.0, 0.0, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147799_1_, 0.0, 0.0, 0.0, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147799_1_, 0.0, 0.0, 0.0, var241);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147799_1_, 0.0, 0.0, 0.0, var241);
            var7.draw();
        } else {
            this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
        }
        if (!p_147799_6_) {
            double var20 = 0.375;
            double var22 = 0.25;
            this.setOverrideBlockTexture(var241);
            if (var8 == 0) {
                this.setRenderBounds(var20, 0.0, var20, 1.0 - var20, 0.25, 1.0 - var20);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 2) {
                this.setRenderBounds(var20, var271, 0.0, 1.0 - var20, var271 + var22, var261);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 3) {
                this.setRenderBounds(var20, var271, 1.0 - var261, 1.0 - var20, var271 + var22, 1.0);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 4) {
                this.setRenderBounds(0.0, var271, var20, var261, var271 + var22, 1.0 - var20);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 5) {
                this.setRenderBounds(1.0 - var261, var271, var20, 1.0, var271 + var22, 1.0 - var20);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
        }
        this.clearOverrideBlockTexture();
        return true;
    }

    public boolean renderBlockStairs(BlockStairs p_147722_1_, int p_147722_2_, int p_147722_3_, int p_147722_4_) {
        p_147722_1_.func_150147_e(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_);
        this.setRenderBoundsFromBlock(p_147722_1_);
        this.renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
        boolean var5 = p_147722_1_.func_150145_f(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_);
        this.setRenderBoundsFromBlock(p_147722_1_);
        this.renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
        if (var5 && p_147722_1_.func_150144_g(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_)) {
            this.setRenderBoundsFromBlock(p_147722_1_);
            this.renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
        }
        return true;
    }

    public boolean renderBlockDoor(Block p_147760_1_, int p_147760_2_, int p_147760_3_, int p_147760_4_) {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(p_147760_2_, p_147760_3_, p_147760_4_);
        if ((var6 & 8) != 0 ? this.blockAccess.getBlock(p_147760_2_, p_147760_3_ - 1, p_147760_4_) != p_147760_1_ : this.blockAccess.getBlock(p_147760_2_, p_147760_3_ + 1, p_147760_4_) != p_147760_1_) {
            return false;
        }
        boolean var7 = false;
        float var8 = 0.5f;
        float var9 = 1.0f;
        float var10 = 0.8f;
        float var11 = 0.6f;
        int var12 = p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_);
        var5.setBrightness(this.renderMinY > 0.0 ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_ - 1, p_147760_4_));
        var5.setColorOpaque_F(var8, var8, var8);
        this.renderFaceYNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 0));
        var7 = true;
        var5.setBrightness(this.renderMaxY < 1.0 ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_ + 1, p_147760_4_));
        var5.setColorOpaque_F(var9, var9, var9);
        this.renderFaceYPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 1));
        var7 = true;
        var5.setBrightness(this.renderMinZ > 0.0 ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ - 1));
        var5.setColorOpaque_F(var10, var10, var10);
        IIcon var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 2);
        this.renderFaceZNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness(this.renderMaxZ < 1.0 ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ + 1));
        var5.setColorOpaque_F(var10, var10, var10);
        var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 3);
        this.renderFaceZPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness(this.renderMinX > 0.0 ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_ - 1, p_147760_3_, p_147760_4_));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 4);
        this.renderFaceXNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness(this.renderMaxX < 1.0 ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_ + 1, p_147760_3_, p_147760_4_));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 5);
        this.renderFaceXPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        return var7;
    }

    public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_) {
        double var26;
        double var18;
        NaturalProperties var10;
        Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147768_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147768_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147768_1_, (int)p_147768_2_, (int)p_147768_4_, (int)p_147768_6_, 0, p_147768_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0 && (var10 = NaturalTextures.getNaturalProperties(p_147768_8_)) != null) {
            int rand = Config.getRandom((int)p_147768_2_, (int)p_147768_4_, (int)p_147768_6_, 0);
            if (var10.rotation > 1) {
                this.uvRotateBottom = rand & 3;
            }
            if (var10.rotation == 2) {
                this.uvRotateBottom = this.uvRotateBottom / 2 * 3;
            }
            if (var10.flip) {
                this.flipTexture = (rand & 4) != 0;
            }
            uvRotateSet = true;
        }
        double var101 = p_147768_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var12 = p_147768_8_.getInterpolatedU(this.renderMaxX * 16.0);
        double var14 = p_147768_8_.getInterpolatedV(this.renderMinZ * 16.0);
        double var16 = p_147768_8_.getInterpolatedV(this.renderMaxZ * 16.0);
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var101 = p_147768_8_.getMinU();
            var12 = p_147768_8_.getMaxU();
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var14 = p_147768_8_.getMinV();
            var16 = p_147768_8_.getMaxV();
        }
        if (this.flipTexture) {
            var18 = var101;
            var101 = var12;
            var12 = var18;
        }
        var18 = var12;
        double var20 = var101;
        double var22 = var14;
        double var24 = var16;
        if (this.uvRotateBottom == 2) {
            var101 = p_147768_8_.getInterpolatedU(this.renderMinZ * 16.0);
            var14 = p_147768_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            var12 = p_147768_8_.getInterpolatedU(this.renderMaxZ * 16.0);
            var16 = p_147768_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var22 = var14;
            var24 = var16;
            var18 = var101;
            var20 = var12;
            var14 = var16;
            var16 = var22;
        } else if (this.uvRotateBottom == 1) {
            var101 = p_147768_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var14 = p_147768_8_.getInterpolatedV(this.renderMinX * 16.0);
            var12 = p_147768_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var16 = p_147768_8_.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var101 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
        } else if (this.uvRotateBottom == 3) {
            var101 = p_147768_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147768_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var14 = p_147768_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var16 = p_147768_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var22 = var14;
            var24 = var16;
        }
        if (uvRotateSet) {
            this.uvRotateBottom = 0;
            this.flipTexture = false;
        }
        var26 = p_147768_2_ + this.renderMinX;
        double var28 = p_147768_2_ + this.renderMaxX;
        double var30 = p_147768_4_ + this.renderMinY;
        double var32 = p_147768_6_ + this.renderMinZ;
        double var34 = p_147768_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var26 = p_147768_2_ + this.renderMaxX;
            var28 = p_147768_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var26, var30, var34, var20, var24);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var26, var30, var32, var101, var14);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var28, var30, var32, var18, var22);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var28, var30, var34, var12, var16);
        } else {
            var9.addVertexWithUV(var26, var30, var34, var20, var24);
            var9.addVertexWithUV(var26, var30, var32, var101, var14);
            var9.addVertexWithUV(var28, var30, var32, var18, var22);
            var9.addVertexWithUV(var28, var30, var34, var12, var16);
        }
    }

    public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_) {
        double var26;
        double var18;
        NaturalProperties var10;
        Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147806_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147806_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147806_1_, (int)p_147806_2_, (int)p_147806_4_, (int)p_147806_6_, 1, p_147806_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0 && (var10 = NaturalTextures.getNaturalProperties(p_147806_8_)) != null) {
            int rand = Config.getRandom((int)p_147806_2_, (int)p_147806_4_, (int)p_147806_6_, 1);
            if (var10.rotation > 1) {
                this.uvRotateTop = rand & 3;
            }
            if (var10.rotation == 2) {
                this.uvRotateTop = this.uvRotateTop / 2 * 3;
            }
            if (var10.flip) {
                this.flipTexture = (rand & 4) != 0;
            }
            uvRotateSet = true;
        }
        double var101 = p_147806_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var12 = p_147806_8_.getInterpolatedU(this.renderMaxX * 16.0);
        double var14 = p_147806_8_.getInterpolatedV(this.renderMinZ * 16.0);
        double var16 = p_147806_8_.getInterpolatedV(this.renderMaxZ * 16.0);
        if (this.flipTexture) {
            var18 = var101;
            var101 = var12;
            var12 = var18;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var101 = p_147806_8_.getMinU();
            var12 = p_147806_8_.getMaxU();
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var14 = p_147806_8_.getMinV();
            var16 = p_147806_8_.getMaxV();
        }
        var18 = var12;
        double var20 = var101;
        double var22 = var14;
        double var24 = var16;
        if (this.uvRotateTop == 1) {
            var101 = p_147806_8_.getInterpolatedU(this.renderMinZ * 16.0);
            var14 = p_147806_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            var12 = p_147806_8_.getInterpolatedU(this.renderMaxZ * 16.0);
            var16 = p_147806_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var22 = var14;
            var24 = var16;
            var18 = var101;
            var20 = var12;
            var14 = var16;
            var16 = var22;
        } else if (this.uvRotateTop == 2) {
            var101 = p_147806_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var14 = p_147806_8_.getInterpolatedV(this.renderMinX * 16.0);
            var12 = p_147806_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var16 = p_147806_8_.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var101 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
        } else if (this.uvRotateTop == 3) {
            var101 = p_147806_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147806_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var14 = p_147806_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var16 = p_147806_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var22 = var14;
            var24 = var16;
        }
        if (uvRotateSet) {
            this.uvRotateTop = 0;
            this.flipTexture = false;
        }
        var26 = p_147806_2_ + this.renderMinX;
        double var28 = p_147806_2_ + this.renderMaxX;
        double var30 = p_147806_4_ + this.renderMaxY;
        double var32 = p_147806_6_ + this.renderMinZ;
        double var34 = p_147806_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var26 = p_147806_2_ + this.renderMaxX;
            var28 = p_147806_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var28, var30, var34, var12, var16);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var28, var30, var32, var18, var22);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var26, var30, var32, var101, var14);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var26, var30, var34, var20, var24);
        } else {
            var9.addVertexWithUV(var28, var30, var34, var12, var16);
            var9.addVertexWithUV(var28, var30, var32, var18, var22);
            var9.addVertexWithUV(var26, var30, var32, var101, var14);
            var9.addVertexWithUV(var26, var30, var34, var20, var24);
        }
    }

    public void renderFaceZNeg(Block p_147761_1_, double p_147761_2_, double p_147761_4_, double p_147761_6_, IIcon p_147761_8_) {
        double var26;
        double var18;
        NaturalProperties var10;
        Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147761_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147761_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147761_1_, (int)p_147761_2_, (int)p_147761_4_, (int)p_147761_6_, 2, p_147761_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0 && (var10 = NaturalTextures.getNaturalProperties(p_147761_8_)) != null) {
            int rand = Config.getRandom((int)p_147761_2_, (int)p_147761_4_, (int)p_147761_6_, 2);
            if (var10.rotation > 1) {
                this.uvRotateEast = rand & 3;
            }
            if (var10.rotation == 2) {
                this.uvRotateEast = this.uvRotateEast / 2 * 3;
            }
            if (var10.flip) {
                this.flipTexture = (rand & 4) != 0;
            }
            uvRotateSet = true;
        }
        double var101 = p_147761_8_.getInterpolatedU(this.renderMaxX * 16.0);
        double var12 = p_147761_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var14 = p_147761_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = p_147761_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            var18 = var101;
            var101 = var12;
            var12 = var18;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var101 = p_147761_8_.getMinU();
            var12 = p_147761_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var14 = p_147761_8_.getMinV();
            var16 = p_147761_8_.getMaxV();
        }
        var18 = var12;
        double var20 = var101;
        double var22 = var14;
        double var24 = var16;
        if (this.uvRotateEast == 2) {
            var101 = p_147761_8_.getInterpolatedU(this.renderMinY * 16.0);
            var14 = p_147761_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            var12 = p_147761_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var16 = p_147761_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var22 = var14;
            var24 = var16;
            var18 = var101;
            var20 = var12;
            var14 = var16;
            var16 = var22;
        } else if (this.uvRotateEast == 1) {
            var101 = p_147761_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var14 = p_147761_8_.getInterpolatedV(this.renderMaxX * 16.0);
            var12 = p_147761_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = p_147761_8_.getInterpolatedV(this.renderMinX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var101 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
        } else if (this.uvRotateEast == 3) {
            var101 = p_147761_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147761_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var14 = p_147761_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = p_147761_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var22 = var14;
            var24 = var16;
        }
        if (uvRotateSet) {
            this.uvRotateEast = 0;
            this.flipTexture = false;
        }
        var26 = p_147761_2_ + this.renderMinX;
        double var28 = p_147761_2_ + this.renderMaxX;
        double var30 = p_147761_4_ + this.renderMinY;
        double var32 = p_147761_4_ + this.renderMaxY;
        double var34 = p_147761_6_ + this.renderMinZ;
        if (this.renderFromInside) {
            var26 = p_147761_2_ + this.renderMaxX;
            var28 = p_147761_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var26, var32, var34, var18, var22);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var28, var32, var34, var101, var14);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var28, var30, var34, var20, var24);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var26, var30, var34, var12, var16);
        } else {
            var9.addVertexWithUV(var26, var32, var34, var18, var22);
            var9.addVertexWithUV(var28, var32, var34, var101, var14);
            var9.addVertexWithUV(var28, var30, var34, var20, var24);
            var9.addVertexWithUV(var26, var30, var34, var12, var16);
        }
    }

    public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, IIcon p_147734_8_) {
        double var26;
        double var18;
        NaturalProperties var10;
        Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147734_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147734_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147734_1_, (int)p_147734_2_, (int)p_147734_4_, (int)p_147734_6_, 3, p_147734_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0 && (var10 = NaturalTextures.getNaturalProperties(p_147734_8_)) != null) {
            int rand = Config.getRandom((int)p_147734_2_, (int)p_147734_4_, (int)p_147734_6_, 3);
            if (var10.rotation > 1) {
                this.uvRotateWest = rand & 3;
            }
            if (var10.rotation == 2) {
                this.uvRotateWest = this.uvRotateWest / 2 * 3;
            }
            if (var10.flip) {
                this.flipTexture = (rand & 4) != 0;
            }
            uvRotateSet = true;
        }
        double var101 = p_147734_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var12 = p_147734_8_.getInterpolatedU(this.renderMaxX * 16.0);
        double var14 = p_147734_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = p_147734_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            var18 = var101;
            var101 = var12;
            var12 = var18;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var101 = p_147734_8_.getMinU();
            var12 = p_147734_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var14 = p_147734_8_.getMinV();
            var16 = p_147734_8_.getMaxV();
        }
        var18 = var12;
        double var20 = var101;
        double var22 = var14;
        double var24 = var16;
        if (this.uvRotateWest == 1) {
            var101 = p_147734_8_.getInterpolatedU(this.renderMinY * 16.0);
            var16 = p_147734_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            var12 = p_147734_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var14 = p_147734_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var22 = var14;
            var24 = var16;
            var18 = var101;
            var20 = var12;
            var14 = var16;
            var16 = var22;
        } else if (this.uvRotateWest == 2) {
            var101 = p_147734_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var14 = p_147734_8_.getInterpolatedV(this.renderMinX * 16.0);
            var12 = p_147734_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = p_147734_8_.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var101 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
        } else if (this.uvRotateWest == 3) {
            var101 = p_147734_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147734_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var14 = p_147734_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = p_147734_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var22 = var14;
            var24 = var16;
        }
        if (uvRotateSet) {
            this.uvRotateWest = 0;
            this.flipTexture = false;
        }
        var26 = p_147734_2_ + this.renderMinX;
        double var28 = p_147734_2_ + this.renderMaxX;
        double var30 = p_147734_4_ + this.renderMinY;
        double var32 = p_147734_4_ + this.renderMaxY;
        double var34 = p_147734_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var26 = p_147734_2_ + this.renderMaxX;
            var28 = p_147734_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var26, var32, var34, var101, var14);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var26, var30, var34, var20, var24);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var28, var30, var34, var12, var16);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var28, var32, var34, var18, var22);
        } else {
            var9.addVertexWithUV(var26, var32, var34, var101, var14);
            var9.addVertexWithUV(var26, var30, var34, var20, var24);
            var9.addVertexWithUV(var28, var30, var34, var12, var16);
            var9.addVertexWithUV(var28, var32, var34, var18, var22);
        }
    }

    public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, IIcon p_147798_8_) {
        double var26;
        double var18;
        NaturalProperties var10;
        Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147798_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147798_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147798_1_, (int)p_147798_2_, (int)p_147798_4_, (int)p_147798_6_, 4, p_147798_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0 && (var10 = NaturalTextures.getNaturalProperties(p_147798_8_)) != null) {
            int rand = Config.getRandom((int)p_147798_2_, (int)p_147798_4_, (int)p_147798_6_, 4);
            if (var10.rotation > 1) {
                this.uvRotateNorth = rand & 3;
            }
            if (var10.rotation == 2) {
                this.uvRotateNorth = this.uvRotateNorth / 2 * 3;
            }
            if (var10.flip) {
                this.flipTexture = (rand & 4) != 0;
            }
            uvRotateSet = true;
        }
        double var101 = p_147798_8_.getInterpolatedU(this.renderMinZ * 16.0);
        double var12 = p_147798_8_.getInterpolatedU(this.renderMaxZ * 16.0);
        double var14 = p_147798_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = p_147798_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            var18 = var101;
            var101 = var12;
            var12 = var18;
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var101 = p_147798_8_.getMinU();
            var12 = p_147798_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var14 = p_147798_8_.getMinV();
            var16 = p_147798_8_.getMaxV();
        }
        var18 = var12;
        double var20 = var101;
        double var22 = var14;
        double var24 = var16;
        if (this.uvRotateNorth == 1) {
            var101 = p_147798_8_.getInterpolatedU(this.renderMinY * 16.0);
            var14 = p_147798_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            var12 = p_147798_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var16 = p_147798_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var22 = var14;
            var24 = var16;
            var18 = var101;
            var20 = var12;
            var14 = var16;
            var16 = var22;
        } else if (this.uvRotateNorth == 2) {
            var101 = p_147798_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var14 = p_147798_8_.getInterpolatedV(this.renderMinZ * 16.0);
            var12 = p_147798_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = p_147798_8_.getInterpolatedV(this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var101 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
        } else if (this.uvRotateNorth == 3) {
            var101 = p_147798_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var12 = p_147798_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var14 = p_147798_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = p_147798_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var22 = var14;
            var24 = var16;
        }
        if (uvRotateSet) {
            this.uvRotateNorth = 0;
            this.flipTexture = false;
        }
        var26 = p_147798_2_ + this.renderMinX;
        double var28 = p_147798_4_ + this.renderMinY;
        double var30 = p_147798_4_ + this.renderMaxY;
        double var32 = p_147798_6_ + this.renderMinZ;
        double var34 = p_147798_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var32 = p_147798_6_ + this.renderMaxZ;
            var34 = p_147798_6_ + this.renderMinZ;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var26, var30, var34, var18, var22);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var26, var30, var32, var101, var14);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var26, var28, var32, var20, var24);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var26, var28, var34, var12, var16);
        } else {
            var9.addVertexWithUV(var26, var30, var34, var18, var22);
            var9.addVertexWithUV(var26, var30, var32, var101, var14);
            var9.addVertexWithUV(var26, var28, var32, var20, var24);
            var9.addVertexWithUV(var26, var28, var34, var12, var16);
        }
    }

    public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, IIcon p_147764_8_) {
        double var26;
        double var18;
        NaturalProperties var10;
        Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147764_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147764_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147764_1_, (int)p_147764_2_, (int)p_147764_4_, (int)p_147764_6_, 5, p_147764_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0 && (var10 = NaturalTextures.getNaturalProperties(p_147764_8_)) != null) {
            int rand = Config.getRandom((int)p_147764_2_, (int)p_147764_4_, (int)p_147764_6_, 5);
            if (var10.rotation > 1) {
                this.uvRotateSouth = rand & 3;
            }
            if (var10.rotation == 2) {
                this.uvRotateSouth = this.uvRotateSouth / 2 * 3;
            }
            if (var10.flip) {
                this.flipTexture = (rand & 4) != 0;
            }
            uvRotateSet = true;
        }
        double var101 = p_147764_8_.getInterpolatedU(this.renderMaxZ * 16.0);
        double var12 = p_147764_8_.getInterpolatedU(this.renderMinZ * 16.0);
        double var14 = p_147764_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var16 = p_147764_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            var18 = var101;
            var101 = var12;
            var12 = var18;
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var101 = p_147764_8_.getMinU();
            var12 = p_147764_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var14 = p_147764_8_.getMinV();
            var16 = p_147764_8_.getMaxV();
        }
        var18 = var12;
        double var20 = var101;
        double var22 = var14;
        double var24 = var16;
        if (this.uvRotateSouth == 2) {
            var101 = p_147764_8_.getInterpolatedU(this.renderMinY * 16.0);
            var14 = p_147764_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var12 = p_147764_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var16 = p_147764_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var22 = var14;
            var24 = var16;
            var18 = var101;
            var20 = var12;
            var14 = var16;
            var16 = var22;
        } else if (this.uvRotateSouth == 1) {
            var101 = p_147764_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var14 = p_147764_8_.getInterpolatedV(this.renderMaxZ * 16.0);
            var12 = p_147764_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var16 = p_147764_8_.getInterpolatedV(this.renderMinZ * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var101 = var12;
            var12 = var20;
            var22 = var16;
            var24 = var14;
        } else if (this.uvRotateSouth == 3) {
            var101 = p_147764_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var12 = p_147764_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var14 = p_147764_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var16 = p_147764_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                var26 = var101;
                var101 = var12;
                var12 = var26;
            }
            var18 = var12;
            var20 = var101;
            var22 = var14;
            var24 = var16;
        }
        if (uvRotateSet) {
            this.uvRotateSouth = 0;
            this.flipTexture = false;
        }
        var26 = p_147764_2_ + this.renderMaxX;
        double var28 = p_147764_4_ + this.renderMinY;
        double var30 = p_147764_4_ + this.renderMaxY;
        double var32 = p_147764_6_ + this.renderMinZ;
        double var34 = p_147764_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var32 = p_147764_6_ + this.renderMaxZ;
            var34 = p_147764_6_ + this.renderMinZ;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var26, var28, var34, var20, var24);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var26, var28, var32, var12, var16);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var26, var30, var32, var18, var22);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var26, var30, var34, var101, var14);
        } else {
            var9.addVertexWithUV(var26, var28, var34, var20, var24);
            var9.addVertexWithUV(var26, var28, var32, var12, var16);
            var9.addVertexWithUV(var26, var30, var32, var18, var22);
            var9.addVertexWithUV(var26, var30, var34, var101, var14);
        }
    }

    public void renderBlockAsItem(Block p_147800_1_, int p_147800_2_, float p_147800_3_) {
        float var7;
        float var9;
        float var8;
        int var6;
        boolean var5;
        Tessellator var4 = Tessellator.instance;
        boolean bl = var5 = p_147800_1_ == Blocks.grass;
        if (p_147800_1_ == Blocks.dispenser || p_147800_1_ == Blocks.dropper || p_147800_1_ == Blocks.furnace) {
            p_147800_2_ = 3;
        }
        if (this.useInventoryTint) {
            var6 = p_147800_1_.getRenderColor(p_147800_2_);
            if (var5) {
                var6 = 16777215;
            }
            var7 = (float)(var6 >> 16 & 255) / 255.0f;
            var8 = (float)(var6 >> 8 & 255) / 255.0f;
            var9 = (float)(var6 & 255) / 255.0f;
            GL11.glColor4f((float)(var7 * p_147800_3_), (float)(var8 * p_147800_3_), (float)(var9 * p_147800_3_), (float)1.0f);
        }
        var6 = p_147800_1_.getRenderType();
        this.setRenderBoundsFromBlock(p_147800_1_);
        if (var6 != 0 && var6 != 31 && var6 != 39 && var6 != 16 && var6 != 26) {
            if (var6 == 1) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                IIcon var171 = this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_);
                this.drawCrossedSquares(var171, -0.5, -0.5, -0.5, 1.0f);
                var4.draw();
            } else if (var6 == 19) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                p_147800_1_.setBlockBoundsForItemRender();
                this.renderBlockStemSmall(p_147800_1_, p_147800_2_, this.renderMaxY, -0.5, -0.5, -0.5);
                var4.draw();
            } else if (var6 == 23) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                p_147800_1_.setBlockBoundsForItemRender();
                var4.draw();
            } else if (var6 == 13) {
                p_147800_1_.setBlockBoundsForItemRender();
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                var7 = 0.0625f;
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 1.0f, 0.0f);
                this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 0.0f, -1.0f);
                var4.addTranslation(0.0f, 0.0f, var7);
                this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                var4.addTranslation(0.0f, 0.0f, - var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 0.0f, 1.0f);
                var4.addTranslation(0.0f, 0.0f, - var7);
                this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                var4.addTranslation(0.0f, 0.0f, var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(-1.0f, 0.0f, 0.0f);
                var4.addTranslation(var7, 0.0f, 0.0f);
                this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                var4.addTranslation(- var7, 0.0f, 0.0f);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(1.0f, 0.0f, 0.0f);
                var4.addTranslation(- var7, 0.0f, 0.0f);
                this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                var4.addTranslation(var7, 0.0f, 0.0f);
                var4.draw();
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
            } else if (var6 == 22) {
                GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                TileEntityRendererChestHelper.instance.func_147715_a(p_147800_1_, p_147800_2_, p_147800_3_);
                GL11.glEnable((int)32826);
            } else if (var6 == 6) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderBlockCropsImpl(p_147800_1_, p_147800_2_, -0.5, -0.5, -0.5);
                var4.draw();
            } else if (var6 == 2) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderTorchAtAngle(p_147800_1_, -0.5, -0.5, -0.5, 0.0, 0.0, 0);
                var4.draw();
            } else if (var6 == 10) {
                int var14 = 0;
                while (var14 < 2) {
                    if (var14 == 0) {
                        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
                    }
                    if (var14 == 1) {
                        this.setRenderBounds(0.0, 0.0, 0.5, 1.0, 0.5, 1.0);
                    }
                    GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                    var4.draw();
                    GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                    ++var14;
                }
            } else if (var6 == 27) {
                int var14 = 0;
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                var4.startDrawingQuads();
                int var181 = 0;
                while (var181 < 8) {
                    int var17 = 0;
                    int var18 = 1;
                    if (var181 == 0) {
                        var17 = 2;
                    }
                    if (var181 == 1) {
                        var17 = 3;
                    }
                    if (var181 == 2) {
                        var17 = 4;
                    }
                    if (var181 == 3) {
                        var17 = 5;
                        var18 = 2;
                    }
                    if (var181 == 4) {
                        var17 = 6;
                        var18 = 3;
                    }
                    if (var181 == 5) {
                        var17 = 7;
                        var18 = 5;
                    }
                    if (var181 == 6) {
                        var17 = 6;
                        var18 = 2;
                    }
                    if (var181 == 7) {
                        var17 = 3;
                    }
                    float var11 = (float)var17 / 16.0f;
                    float var12 = 1.0f - (float)var14 / 16.0f;
                    float var13 = 1.0f - (float)(var14 + var18) / 16.0f;
                    var14 += var18;
                    this.setRenderBounds(0.5f - var11, var13, 0.5f - var11, 0.5f + var11, var12, 0.5f + var11);
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                    ++var181;
                }
                var4.draw();
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            } else if (var6 == 11) {
                int var14 = 0;
                while (var14 < 4) {
                    var8 = 0.125f;
                    if (var14 == 0) {
                        this.setRenderBounds(0.5f - var8, 0.0, 0.0, 0.5f + var8, 1.0, var8 * 2.0f);
                    }
                    if (var14 == 1) {
                        this.setRenderBounds(0.5f - var8, 0.0, 1.0f - var8 * 2.0f, 0.5f + var8, 1.0, 1.0);
                    }
                    var8 = 0.0625f;
                    if (var14 == 2) {
                        this.setRenderBounds(0.5f - var8, 1.0f - var8 * 3.0f, (- var8) * 2.0f, 0.5f + var8, 1.0f - var8, 1.0f + var8 * 2.0f);
                    }
                    if (var14 == 3) {
                        this.setRenderBounds(0.5f - var8, 0.5f - var8 * 3.0f, (- var8) * 2.0f, 0.5f + var8, 0.5f - var8, 1.0f + var8 * 2.0f);
                    }
                    GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                    var4.draw();
                    GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                    ++var14;
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            } else if (var6 == 21) {
                int var14 = 0;
                while (var14 < 3) {
                    var8 = 0.0625f;
                    if (var14 == 0) {
                        this.setRenderBounds(0.5f - var8, 0.30000001192092896, 0.0, 0.5f + var8, 1.0, var8 * 2.0f);
                    }
                    if (var14 == 1) {
                        this.setRenderBounds(0.5f - var8, 0.30000001192092896, 1.0f - var8 * 2.0f, 0.5f + var8, 1.0, 1.0);
                    }
                    var8 = 0.0625f;
                    if (var14 == 2) {
                        this.setRenderBounds(0.5f - var8, 0.5, 0.0, 0.5f + var8, 1.0f - var8, 1.0);
                    }
                    GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                    var4.draw();
                    GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                    ++var14;
                }
            } else if (var6 == 32) {
                int var14 = 0;
                while (var14 < 2) {
                    if (var14 == 0) {
                        this.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                    }
                    if (var14 == 1) {
                        this.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
                    }
                    GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
                    var4.draw();
                    GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                    ++var14;
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            } else if (var6 == 35) {
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                this.renderBlockAnvilOrient((BlockAnvil)p_147800_1_, 0, 0, 0, p_147800_2_ << 2, true);
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
            } else if (var6 == 34) {
                int var14 = 0;
                while (var14 < 3) {
                    if (var14 == 0) {
                        this.setRenderBounds(0.125, 0.0, 0.125, 0.875, 0.1875, 0.875);
                        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.obsidian));
                    } else if (var14 == 1) {
                        this.setRenderBounds(0.1875, 0.1875, 0.1875, 0.8125, 0.875, 0.8125);
                        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.beacon));
                    } else if (var14 == 2) {
                        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.glass));
                    }
                    GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
                    var4.draw();
                    GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                    ++var14;
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                this.clearOverrideBlockTexture();
            } else if (var6 == 38) {
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                this.renderBlockHopperMetadata((BlockHopper)p_147800_1_, 0, 0, 0, 0, true);
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
            } else if (Reflector.ModLoader.exists()) {
                Reflector.callVoid(Reflector.ModLoader_renderInvBlock, this, p_147800_1_, p_147800_2_, var6);
            } else if (Reflector.FMLRenderAccessLibrary.exists()) {
                Reflector.callVoid(Reflector.FMLRenderAccessLibrary_renderInventoryBlock, this, p_147800_1_, p_147800_2_, var6);
            }
        } else {
            if (var6 == 16) {
                p_147800_2_ = 1;
            }
            p_147800_1_.setBlockBoundsForItemRender();
            this.setRenderBoundsFromBlock(p_147800_1_);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
            var4.startDrawingQuads();
            var4.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
            var4.draw();
            if (var5 && this.useInventoryTint) {
                int var14 = p_147800_1_.getRenderColor(p_147800_2_);
                var8 = (float)(var14 >> 16 & 255) / 255.0f;
                var9 = (float)(var14 >> 8 & 255) / 255.0f;
                float var10 = (float)(var14 & 255) / 255.0f;
                GL11.glColor4f((float)(var8 * p_147800_3_), (float)(var9 * p_147800_3_), (float)(var10 * p_147800_3_), (float)1.0f);
            }
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
            var4.draw();
            if (var5 && this.useInventoryTint) {
                GL11.glColor4f((float)p_147800_3_, (float)p_147800_3_, (float)p_147800_3_, (float)1.0f);
            }
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
            var4.draw();
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        }
    }

    public static boolean renderItemIn3d(int par0) {
        switch (par0) {
            case -1: {
                return false;
            }
            case 0: 
            case 10: 
            case 11: 
            case 13: 
            case 16: 
            case 21: 
            case 22: 
            case 26: 
            case 27: 
            case 31: 
            case 32: 
            case 34: 
            case 35: 
            case 39: {
                return true;
            }
        }
        return Reflector.ModLoader.exists() ? Reflector.callBoolean(Reflector.ModLoader_renderBlockIsItemFull3D, par0) : (Reflector.FMLRenderAccessLibrary.exists() ? Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderItemAsFull3DBlock, par0) : false);
    }

    public IIcon getBlockIcon(Block p_147793_1_, IBlockAccess p_147793_2_, int p_147793_3_, int p_147793_4_, int p_147793_5_, int p_147793_6_) {
        return this.getIconSafe(p_147793_1_.getIcon(p_147793_2_, p_147793_3_, p_147793_4_, p_147793_5_, p_147793_6_));
    }

    public IIcon getBlockIconFromSideAndMetadata(Block p_147787_1_, int p_147787_2_, int p_147787_3_) {
        return this.getIconSafe(p_147787_1_.getIcon(p_147787_2_, p_147787_3_));
    }

    public IIcon getBlockIconFromSide(Block p_147777_1_, int p_147777_2_) {
        return this.getIconSafe(p_147777_1_.getBlockTextureFromSide(p_147777_2_));
    }

    public IIcon getBlockIcon(Block p_147745_1_) {
        return this.getIconSafe(p_147745_1_.getBlockTextureFromSide(1));
    }

    public IIcon getIconSafe(IIcon p_147758_1_) {
        if (p_147758_1_ == null) {
            p_147758_1_ = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        return p_147758_1_;
    }

    private float getAmbientOcclusionLightValue(int i, int j, int k) {
        Block block = this.blockAccess.getBlock(i, j, k);
        return block.isBlockNormalCube() ? this.aoLightValueOpaque : 1.0f;
    }

    private IIcon fixAoSideGrassTexture(IIcon tex, int x, int y, int z, int side, float f, float f1, float f2) {
        if ((tex == TextureUtils.iconGrassSide || tex == TextureUtils.iconMyceliumSide) && (tex = Config.getSideGrassTexture(this.blockAccess, x, y, z, side, tex)) == TextureUtils.iconGrassTop) {
            this.colorRedTopLeft *= f;
            this.colorRedBottomLeft *= f;
            this.colorRedBottomRight *= f;
            this.colorRedTopRight *= f;
            this.colorGreenTopLeft *= f1;
            this.colorGreenBottomLeft *= f1;
            this.colorGreenBottomRight *= f1;
            this.colorGreenTopRight *= f1;
            this.colorBlueTopLeft *= f2;
            this.colorBlueBottomLeft *= f2;
            this.colorBlueBottomRight *= f2;
            this.colorBlueTopRight *= f2;
        }
        if (tex == TextureUtils.iconGrassSideSnowed) {
            tex = Config.getSideSnowGrassTexture(this.blockAccess, x, y, z, side);
        }
        return tex;
    }

    private boolean hasSnowNeighbours(int x, int y, int z) {
        Block blockSnow = Blocks.snow_layer;
        return this.blockAccess.getBlock(x - 1, y, z) != blockSnow && this.blockAccess.getBlock(x + 1, y, z) != blockSnow && this.blockAccess.getBlock(x, y, z - 1) != blockSnow && this.blockAccess.getBlock(x, y, z + 1) != blockSnow ? false : this.blockAccess.getBlock(x, y - 1, z).isOpaqueCube();
    }

    private void renderSnow(int x, int y, int z, double maxY) {
        if (this.betterSnowEnabled) {
            this.setRenderBoundsFromBlock(Blocks.snow_layer);
            this.renderMaxY = maxY;
            this.renderStandardBlock(Blocks.snow_layer, x, y, z);
        }
    }
}

