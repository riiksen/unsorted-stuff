/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.block;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.XrayBlock;
import com.krispdev.resilience.utilities.XrayUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockHardenedClay;
import net.minecraft.block.BlockHay;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockMelon;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockNote;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailDetector;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.BlockWood;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block {
    public static final RegistryNamespaced blockRegistry = new RegistryNamespacedDefaultedByKey("air");
    private CreativeTabs displayOnCreativeTab;
    protected String textureName;
    public static final SoundType soundTypeStone = new SoundType("stone", 1.0f, 1.0f);
    public static final SoundType soundTypeWood = new SoundType("wood", 1.0f, 1.0f);
    public static final SoundType soundTypeGravel = new SoundType("gravel", 1.0f, 1.0f);
    public static final SoundType soundTypeGrass = new SoundType("grass", 1.0f, 1.0f);
    public static final SoundType soundTypePiston = new SoundType("stone", 1.0f, 1.0f);
    public static final SoundType soundTypeMetal = new SoundType("stone", 1.0f, 1.5f);
    public static final SoundType soundTypeGlass = new SoundType("stone", 1.0f, 1.0f){
        private static final String __OBFID = "CL_00000200";

        @Override
        public String func_150495_a() {
            return "dig.glass";
        }

        @Override
        public String func_150496_b() {
            return "step.stone";
        }
    };
    public static final SoundType soundTypeCloth = new SoundType("cloth", 1.0f, 1.0f);
    public static final SoundType field_149776_m = new SoundType("sand", 1.0f, 1.0f);
    public static final SoundType soundTypeSnow = new SoundType("snow", 1.0f, 1.0f);
    public static final SoundType soundTypeLadder = new SoundType("ladder", 1.0f, 1.0f){
        private static final String __OBFID = "CL_00000201";

        @Override
        public String func_150495_a() {
            return "dig.wood";
        }
    };
    public static final SoundType soundTypeAnvil = new SoundType("anvil", 0.3f, 1.0f){
        private static final String __OBFID = "CL_00000202";

        @Override
        public String func_150495_a() {
            return "dig.stone";
        }

        @Override
        public String func_150496_b() {
            return "random.anvil_land";
        }
    };
    protected boolean opaque;
    protected int lightOpacity;
    protected boolean canBlockGrass;
    protected int lightValue;
    protected boolean field_149783_u;
    protected float blockHardness;
    protected float blockResistance;
    protected boolean field_149791_x = true;
    protected boolean enableStats = true;
    protected boolean needsRandomTick;
    protected boolean isBlockContainer;
    protected double field_149759_B;
    protected double field_149760_C;
    protected double field_149754_D;
    protected double field_149755_E;
    protected double field_149756_F;
    protected double field_149757_G;
    public SoundType stepSound = soundTypeStone;
    public float blockParticleGravity = 1.0f;
    protected final Material blockMaterial;
    public float slipperiness = 0.6f;
    private String unlocalizedNameBlock;
    protected IIcon blockIcon;
    private static final String __OBFID = "CL_00000199";

    public static int getIdFromBlock(Block p_149682_0_) {
        return blockRegistry.getIDForObject(p_149682_0_);
    }

    public static Block getBlockById(int p_149729_0_) {
        return (Block)blockRegistry.getObjectForID(p_149729_0_);
    }

    public static Block getBlockFromItem(Item p_149634_0_) {
        return Block.getBlockById(Item.getIdFromItem(p_149634_0_));
    }

    public static Block getBlockFromName(String p_149684_0_) {
        if (blockRegistry.containsKey(p_149684_0_)) {
            return (Block)blockRegistry.getObject(p_149684_0_);
        }
        try {
            return (Block)blockRegistry.getObjectForID(Integer.parseInt(p_149684_0_));
        }
        catch (NumberFormatException var2) {
            return null;
        }
    }

    public boolean func_149730_j() {
        return this.opaque;
    }

    public int getLightOpacity() {
        return this.lightOpacity;
    }

    public boolean getCanBlockGrass() {
        return this.canBlockGrass;
    }

    public int getLightValue() {
        return this.lightValue;
    }

    public boolean func_149710_n() {
        return this.field_149783_u;
    }

    public Material getMaterial() {
        return this.blockMaterial;
    }

    public MapColor getMapColor(int p_149728_1_) {
        return this.getMaterial().getMaterialMapColor();
    }

    public static void registerBlocks() {
        blockRegistry.addObject(0, "air", new BlockAir().setBlockName("air"));
        blockRegistry.addObject(1, "stone", new BlockStone().setHardness(1.5f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("stone").setBlockTextureName("stone"));
        blockRegistry.addObject(2, "grass", new BlockGrass().setHardness(0.6f).setStepSound(soundTypeGrass).setBlockName("grass").setBlockTextureName("grass"));
        blockRegistry.addObject(3, "dirt", new BlockDirt().setHardness(0.5f).setStepSound(soundTypeGravel).setBlockName("dirt").setBlockTextureName("dirt"));
        Block var0 = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("stonebrick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("cobblestone");
        blockRegistry.addObject(4, "cobblestone", var0);
        Block var1 = new BlockWood().setHardness(2.0f).setResistance(5.0f).setStepSound(soundTypeWood).setBlockName("wood").setBlockTextureName("planks");
        blockRegistry.addObject(5, "planks", var1);
        blockRegistry.addObject(6, "sapling", new BlockSapling().setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("sapling").setBlockTextureName("sapling"));
        blockRegistry.addObject(7, "bedrock", new Block(Material.rock).setBlockUnbreakable().setResistance(6000000.0f).setStepSound(soundTypePiston).setBlockName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("bedrock"));
        blockRegistry.addObject(8, "flowing_water", new BlockDynamicLiquid(Material.water).setHardness(100.0f).setLightOpacity(3).setBlockName("water").disableStats().setBlockTextureName("water_flow"));
        blockRegistry.addObject(9, "water", new BlockStaticLiquid(Material.water).setHardness(100.0f).setLightOpacity(3).setBlockName("water").disableStats().setBlockTextureName("water_still"));
        blockRegistry.addObject(10, "flowing_lava", new BlockDynamicLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setBlockName("lava").disableStats().setBlockTextureName("lava_flow"));
        blockRegistry.addObject(11, "lava", new BlockStaticLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setBlockName("lava").disableStats().setBlockTextureName("lava_still"));
        blockRegistry.addObject(12, "sand", new BlockSand().setHardness(0.5f).setStepSound(field_149776_m).setBlockName("sand").setBlockTextureName("sand"));
        blockRegistry.addObject(13, "gravel", new BlockGravel().setHardness(0.6f).setStepSound(soundTypeGravel).setBlockName("gravel").setBlockTextureName("gravel"));
        blockRegistry.addObject(14, "gold_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreGold").setBlockTextureName("gold_ore"));
        blockRegistry.addObject(15, "iron_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreIron").setBlockTextureName("iron_ore"));
        blockRegistry.addObject(16, "coal_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreCoal").setBlockTextureName("coal_ore"));
        blockRegistry.addObject(17, "log", new BlockOldLog().setBlockName("log").setBlockTextureName("log"));
        blockRegistry.addObject(18, "leaves", new BlockOldLeaf().setBlockName("leaves").setBlockTextureName("leaves"));
        blockRegistry.addObject(19, "sponge", new BlockSponge().setHardness(0.6f).setStepSound(soundTypeGrass).setBlockName("sponge").setBlockTextureName("sponge"));
        blockRegistry.addObject(20, "glass", new BlockGlass(Material.glass, false).setHardness(0.3f).setStepSound(soundTypeGlass).setBlockName("glass").setBlockTextureName("glass"));
        blockRegistry.addObject(21, "lapis_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreLapis").setBlockTextureName("lapis_ore"));
        blockRegistry.addObject(22, "lapis_block", new BlockCompressed(MapColor.field_151652_H).setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("blockLapis").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("lapis_block"));
        blockRegistry.addObject(23, "dispenser", new BlockDispenser().setHardness(3.5f).setStepSound(soundTypePiston).setBlockName("dispenser").setBlockTextureName("dispenser"));
        Block var2 = new BlockSandStone().setStepSound(soundTypePiston).setHardness(0.8f).setBlockName("sandStone").setBlockTextureName("sandstone");
        blockRegistry.addObject(24, "sandstone", var2);
        blockRegistry.addObject(25, "noteblock", new BlockNote().setHardness(0.8f).setBlockName("musicBlock").setBlockTextureName("noteblock"));
        blockRegistry.addObject(26, "bed", new BlockBed().setHardness(0.2f).setBlockName("bed").disableStats().setBlockTextureName("bed"));
        blockRegistry.addObject(27, "golden_rail", new BlockRailPowered().setHardness(0.7f).setStepSound(soundTypeMetal).setBlockName("goldenRail").setBlockTextureName("rail_golden"));
        blockRegistry.addObject(28, "detector_rail", new BlockRailDetector().setHardness(0.7f).setStepSound(soundTypeMetal).setBlockName("detectorRail").setBlockTextureName("rail_detector"));
        blockRegistry.addObject(29, "sticky_piston", new BlockPistonBase(true).setBlockName("pistonStickyBase"));
        blockRegistry.addObject(30, "web", new BlockWeb().setLightOpacity(1).setHardness(4.0f).setBlockName("web").setBlockTextureName("web"));
        blockRegistry.addObject(31, "tallgrass", new BlockTallGrass().setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("tallgrass"));
        blockRegistry.addObject(32, "deadbush", new BlockDeadBush().setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("deadbush").setBlockTextureName("deadbush"));
        blockRegistry.addObject(33, "piston", new BlockPistonBase(false).setBlockName("pistonBase"));
        blockRegistry.addObject(34, "piston_head", new BlockPistonExtension());
        blockRegistry.addObject(35, "wool", new BlockColored(Material.cloth).setHardness(0.8f).setStepSound(soundTypeCloth).setBlockName("cloth").setBlockTextureName("wool_colored"));
        blockRegistry.addObject(36, "piston_extension", new BlockPistonMoving());
        blockRegistry.addObject(37, "yellow_flower", new BlockFlower(0).setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("flower1").setBlockTextureName("flower_dandelion"));
        blockRegistry.addObject(38, "red_flower", new BlockFlower(1).setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("flower2").setBlockTextureName("flower_rose"));
        blockRegistry.addObject(39, "brown_mushroom", new BlockMushroom().setHardness(0.0f).setStepSound(soundTypeGrass).setLightLevel(0.125f).setBlockName("mushroom").setBlockTextureName("mushroom_brown"));
        blockRegistry.addObject(40, "red_mushroom", new BlockMushroom().setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("mushroom").setBlockTextureName("mushroom_red"));
        blockRegistry.addObject(41, "gold_block", new BlockCompressed(MapColor.field_151647_F).setHardness(3.0f).setResistance(10.0f).setStepSound(soundTypeMetal).setBlockName("blockGold").setBlockTextureName("gold_block"));
        blockRegistry.addObject(42, "iron_block", new BlockCompressed(MapColor.field_151668_h).setHardness(5.0f).setResistance(10.0f).setStepSound(soundTypeMetal).setBlockName("blockIron").setBlockTextureName("iron_block"));
        blockRegistry.addObject(43, "double_stone_slab", new BlockStoneSlab(true).setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("stoneSlab"));
        blockRegistry.addObject(44, "stone_slab", new BlockStoneSlab(false).setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("stoneSlab"));
        Block var3 = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("brick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("brick");
        blockRegistry.addObject(45, "brick_block", var3);
        blockRegistry.addObject(46, "tnt", new BlockTNT().setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("tnt").setBlockTextureName("tnt"));
        blockRegistry.addObject(47, "bookshelf", new BlockBookshelf().setHardness(1.5f).setStepSound(soundTypeWood).setBlockName("bookshelf").setBlockTextureName("bookshelf"));
        blockRegistry.addObject(48, "mossy_cobblestone", new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("cobblestone_mossy"));
        blockRegistry.addObject(49, "obsidian", new BlockObsidian().setHardness(50.0f).setResistance(2000.0f).setStepSound(soundTypePiston).setBlockName("obsidian").setBlockTextureName("obsidian"));
        blockRegistry.addObject(50, "torch", new BlockTorch().setHardness(0.0f).setLightLevel(0.9375f).setStepSound(soundTypeWood).setBlockName("torch").setBlockTextureName("torch_on"));
        blockRegistry.addObject(51, "fire", new BlockFire().setHardness(0.0f).setLightLevel(1.0f).setStepSound(soundTypeWood).setBlockName("fire").disableStats().setBlockTextureName("fire"));
        blockRegistry.addObject(52, "mob_spawner", new BlockMobSpawner().setHardness(5.0f).setStepSound(soundTypeMetal).setBlockName("mobSpawner").disableStats().setBlockTextureName("mob_spawner"));
        blockRegistry.addObject(53, "oak_stairs", new BlockStairs(var1, 0).setBlockName("stairsWood"));
        blockRegistry.addObject(54, "chest", new BlockChest(0).setHardness(2.5f).setStepSound(soundTypeWood).setBlockName("chest"));
        blockRegistry.addObject(55, "redstone_wire", new BlockRedstoneWire().setHardness(0.0f).setStepSound(soundTypeStone).setBlockName("redstoneDust").disableStats().setBlockTextureName("redstone_dust"));
        blockRegistry.addObject(56, "diamond_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreDiamond").setBlockTextureName("diamond_ore"));
        blockRegistry.addObject(57, "diamond_block", new BlockCompressed(MapColor.field_151648_G).setHardness(5.0f).setResistance(10.0f).setStepSound(soundTypeMetal).setBlockName("blockDiamond").setBlockTextureName("diamond_block"));
        blockRegistry.addObject(58, "crafting_table", new BlockWorkbench().setHardness(2.5f).setStepSound(soundTypeWood).setBlockName("workbench").setBlockTextureName("crafting_table"));
        blockRegistry.addObject(59, "wheat", new BlockCrops().setBlockName("crops").setBlockTextureName("wheat"));
        Block var4 = new BlockFarmland().setHardness(0.6f).setStepSound(soundTypeGravel).setBlockName("farmland").setBlockTextureName("farmland");
        blockRegistry.addObject(60, "farmland", var4);
        blockRegistry.addObject(61, "furnace", new BlockFurnace(false).setHardness(3.5f).setStepSound(soundTypePiston).setBlockName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
        blockRegistry.addObject(62, "lit_furnace", new BlockFurnace(true).setHardness(3.5f).setStepSound(soundTypePiston).setLightLevel(0.875f).setBlockName("furnace"));
        blockRegistry.addObject(63, "standing_sign", new BlockSign(TileEntitySign.class, true).setHardness(1.0f).setStepSound(soundTypeWood).setBlockName("sign").disableStats());
        blockRegistry.addObject(64, "wooden_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(soundTypeWood).setBlockName("doorWood").disableStats().setBlockTextureName("door_wood"));
        blockRegistry.addObject(65, "ladder", new BlockLadder().setHardness(0.4f).setStepSound(soundTypeLadder).setBlockName("ladder").setBlockTextureName("ladder"));
        blockRegistry.addObject(66, "rail", new BlockRail().setHardness(0.7f).setStepSound(soundTypeMetal).setBlockName("rail").setBlockTextureName("rail_normal"));
        blockRegistry.addObject(67, "stone_stairs", new BlockStairs(var0, 0).setBlockName("stairsStone"));
        blockRegistry.addObject(68, "wall_sign", new BlockSign(TileEntitySign.class, false).setHardness(1.0f).setStepSound(soundTypeWood).setBlockName("sign").disableStats());
        blockRegistry.addObject(69, "lever", new BlockLever().setHardness(0.5f).setStepSound(soundTypeWood).setBlockName("lever").setBlockTextureName("lever"));
        blockRegistry.addObject(70, "stone_pressure_plate", new BlockPressurePlate("stone", Material.rock, BlockPressurePlate.Sensitivity.mobs).setHardness(0.5f).setStepSound(soundTypePiston).setBlockName("pressurePlate"));
        blockRegistry.addObject(71, "iron_door", new BlockDoor(Material.iron).setHardness(5.0f).setStepSound(soundTypeMetal).setBlockName("doorIron").disableStats().setBlockTextureName("door_iron"));
        blockRegistry.addObject(72, "wooden_pressure_plate", new BlockPressurePlate("planks_oak", Material.wood, BlockPressurePlate.Sensitivity.everything).setHardness(0.5f).setStepSound(soundTypeWood).setBlockName("pressurePlate"));
        blockRegistry.addObject(73, "redstone_ore", new BlockRedstoneOre(false).setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("redstone_ore"));
        blockRegistry.addObject(74, "lit_redstone_ore", new BlockRedstoneOre(true).setLightLevel(0.625f).setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreRedstone").setBlockTextureName("redstone_ore"));
        blockRegistry.addObject(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).setHardness(0.0f).setStepSound(soundTypeWood).setBlockName("notGate").setBlockTextureName("redstone_torch_off"));
        blockRegistry.addObject(76, "redstone_torch", new BlockRedstoneTorch(true).setHardness(0.0f).setLightLevel(0.5f).setStepSound(soundTypeWood).setBlockName("notGate").setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName("redstone_torch_on"));
        blockRegistry.addObject(77, "stone_button", new BlockButtonStone().setHardness(0.5f).setStepSound(soundTypePiston).setBlockName("button"));
        blockRegistry.addObject(78, "snow_layer", new BlockSnow().setHardness(0.1f).setStepSound(soundTypeSnow).setBlockName("snow").setLightOpacity(0).setBlockTextureName("snow"));
        blockRegistry.addObject(79, "ice", new BlockIce().setHardness(0.5f).setLightOpacity(3).setStepSound(soundTypeGlass).setBlockName("ice").setBlockTextureName("ice"));
        blockRegistry.addObject(80, "snow", new BlockSnowBlock().setHardness(0.2f).setStepSound(soundTypeSnow).setBlockName("snow").setBlockTextureName("snow"));
        blockRegistry.addObject(81, "cactus", new BlockCactus().setHardness(0.4f).setStepSound(soundTypeCloth).setBlockName("cactus").setBlockTextureName("cactus"));
        blockRegistry.addObject(82, "clay", new BlockClay().setHardness(0.6f).setStepSound(soundTypeGravel).setBlockName("clay").setBlockTextureName("clay"));
        blockRegistry.addObject(83, "reeds", new BlockReed().setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("reeds").disableStats().setBlockTextureName("reeds"));
        blockRegistry.addObject(84, "jukebox", new BlockJukebox().setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("jukebox").setBlockTextureName("jukebox"));
        blockRegistry.addObject(85, "fence", new BlockFence("planks_oak", Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(soundTypeWood).setBlockName("fence"));
        Block var5 = new BlockPumpkin(false).setHardness(1.0f).setStepSound(soundTypeWood).setBlockName("pumpkin").setBlockTextureName("pumpkin");
        blockRegistry.addObject(86, "pumpkin", var5);
        blockRegistry.addObject(87, "netherrack", new BlockNetherrack().setHardness(0.4f).setStepSound(soundTypePiston).setBlockName("hellrock").setBlockTextureName("netherrack"));
        blockRegistry.addObject(88, "soul_sand", new BlockSoulSand().setHardness(0.5f).setStepSound(field_149776_m).setBlockName("hellsand").setBlockTextureName("soul_sand"));
        blockRegistry.addObject(89, "glowstone", new BlockGlowstone(Material.glass).setHardness(0.3f).setStepSound(soundTypeGlass).setLightLevel(1.0f).setBlockName("lightgem").setBlockTextureName("glowstone"));
        blockRegistry.addObject(90, "portal", new BlockPortal().setHardness(-1.0f).setStepSound(soundTypeGlass).setLightLevel(0.75f).setBlockName("portal").setBlockTextureName("portal"));
        blockRegistry.addObject(91, "lit_pumpkin", new BlockPumpkin(true).setHardness(1.0f).setStepSound(soundTypeWood).setLightLevel(1.0f).setBlockName("litpumpkin").setBlockTextureName("pumpkin"));
        blockRegistry.addObject(92, "cake", new BlockCake().setHardness(0.5f).setStepSound(soundTypeCloth).setBlockName("cake").disableStats().setBlockTextureName("cake"));
        blockRegistry.addObject(93, "unpowered_repeater", new BlockRedstoneRepeater(false).setHardness(0.0f).setStepSound(soundTypeWood).setBlockName("diode").disableStats().setBlockTextureName("repeater_off"));
        blockRegistry.addObject(94, "powered_repeater", new BlockRedstoneRepeater(true).setHardness(0.0f).setLightLevel(0.625f).setStepSound(soundTypeWood).setBlockName("diode").disableStats().setBlockTextureName("repeater_on"));
        blockRegistry.addObject(95, "stained_glass", new BlockStainedGlass(Material.glass).setHardness(0.3f).setStepSound(soundTypeGlass).setBlockName("stainedGlass").setBlockTextureName("glass"));
        blockRegistry.addObject(96, "trapdoor", new BlockTrapDoor(Material.wood).setHardness(3.0f).setStepSound(soundTypeWood).setBlockName("trapdoor").disableStats().setBlockTextureName("trapdoor"));
        blockRegistry.addObject(97, "monster_egg", new BlockSilverfish().setHardness(0.75f).setBlockName("monsterStoneEgg"));
        Block var6 = new BlockStoneBrick().setHardness(1.5f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("stonebricksmooth").setBlockTextureName("stonebrick");
        blockRegistry.addObject(98, "stonebrick", var6);
        blockRegistry.addObject(99, "brown_mushroom_block", new BlockHugeMushroom(Material.wood, 0).setHardness(0.2f).setStepSound(soundTypeWood).setBlockName("mushroom").setBlockTextureName("mushroom_block"));
        blockRegistry.addObject(100, "red_mushroom_block", new BlockHugeMushroom(Material.wood, 1).setHardness(0.2f).setStepSound(soundTypeWood).setBlockName("mushroom").setBlockTextureName("mushroom_block"));
        blockRegistry.addObject(101, "iron_bars", new BlockPane("iron_bars", "iron_bars", Material.iron, true).setHardness(5.0f).setResistance(10.0f).setStepSound(soundTypeMetal).setBlockName("fenceIron"));
        blockRegistry.addObject(102, "glass_pane", new BlockPane("glass", "glass_pane_top", Material.glass, false).setHardness(0.3f).setStepSound(soundTypeGlass).setBlockName("thinGlass"));
        Block var7 = new BlockMelon().setHardness(1.0f).setStepSound(soundTypeWood).setBlockName("melon").setBlockTextureName("melon");
        blockRegistry.addObject(103, "melon_block", var7);
        blockRegistry.addObject(104, "pumpkin_stem", new BlockStem(var5).setHardness(0.0f).setStepSound(soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("pumpkin_stem"));
        blockRegistry.addObject(105, "melon_stem", new BlockStem(var7).setHardness(0.0f).setStepSound(soundTypeWood).setBlockName("pumpkinStem").setBlockTextureName("melon_stem"));
        blockRegistry.addObject(106, "vine", new BlockVine().setHardness(0.2f).setStepSound(soundTypeGrass).setBlockName("vine").setBlockTextureName("vine"));
        blockRegistry.addObject(107, "fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(soundTypeWood).setBlockName("fenceGate"));
        blockRegistry.addObject(108, "brick_stairs", new BlockStairs(var3, 0).setBlockName("stairsBrick"));
        blockRegistry.addObject(109, "stone_brick_stairs", new BlockStairs(var6, 0).setBlockName("stairsStoneBrickSmooth"));
        blockRegistry.addObject(110, "mycelium", new BlockMycelium().setHardness(0.6f).setStepSound(soundTypeGrass).setBlockName("mycel").setBlockTextureName("mycelium"));
        blockRegistry.addObject(111, "waterlily", new BlockLilyPad().setHardness(0.0f).setStepSound(soundTypeGrass).setBlockName("waterlily").setBlockTextureName("waterlily"));
        Block var8 = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("netherBrick").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("nether_brick");
        blockRegistry.addObject(112, "nether_brick", var8);
        blockRegistry.addObject(113, "nether_brick_fence", new BlockFence("nether_brick", Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("netherFence"));
        blockRegistry.addObject(114, "nether_brick_stairs", new BlockStairs(var8, 0).setBlockName("stairsNetherBrick"));
        blockRegistry.addObject(115, "nether_wart", new BlockNetherWart().setBlockName("netherStalk").setBlockTextureName("nether_wart"));
        blockRegistry.addObject(116, "enchanting_table", new BlockEnchantmentTable().setHardness(5.0f).setResistance(2000.0f).setBlockName("enchantmentTable").setBlockTextureName("enchanting_table"));
        blockRegistry.addObject(117, "brewing_stand", new BlockBrewingStand().setHardness(0.5f).setLightLevel(0.125f).setBlockName("brewingStand").setBlockTextureName("brewing_stand"));
        blockRegistry.addObject(118, "cauldron", new BlockCauldron().setHardness(2.0f).setBlockName("cauldron").setBlockTextureName("cauldron"));
        blockRegistry.addObject(119, "end_portal", new BlockEndPortal(Material.Portal).setHardness(-1.0f).setResistance(6000000.0f));
        blockRegistry.addObject(120, "end_portal_frame", new BlockEndPortalFrame().setStepSound(soundTypeGlass).setLightLevel(0.125f).setHardness(-1.0f).setBlockName("endPortalFrame").setResistance(6000000.0f).setCreativeTab(CreativeTabs.tabDecorations).setBlockTextureName("endframe"));
        blockRegistry.addObject(121, "end_stone", new Block(Material.rock).setHardness(3.0f).setResistance(15.0f).setStepSound(soundTypePiston).setBlockName("whiteStone").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("end_stone"));
        blockRegistry.addObject(122, "dragon_egg", new BlockDragonEgg().setHardness(3.0f).setResistance(15.0f).setStepSound(soundTypePiston).setLightLevel(0.125f).setBlockName("dragonEgg").setBlockTextureName("dragon_egg"));
        blockRegistry.addObject(123, "redstone_lamp", new BlockRedstoneLight(false).setHardness(0.3f).setStepSound(soundTypeGlass).setBlockName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName("redstone_lamp_off"));
        blockRegistry.addObject(124, "lit_redstone_lamp", new BlockRedstoneLight(true).setHardness(0.3f).setStepSound(soundTypeGlass).setBlockName("redstoneLight").setBlockTextureName("redstone_lamp_on"));
        blockRegistry.addObject(125, "double_wooden_slab", new BlockWoodSlab(true).setHardness(2.0f).setResistance(5.0f).setStepSound(soundTypeWood).setBlockName("woodSlab"));
        blockRegistry.addObject(126, "wooden_slab", new BlockWoodSlab(false).setHardness(2.0f).setResistance(5.0f).setStepSound(soundTypeWood).setBlockName("woodSlab"));
        blockRegistry.addObject(127, "cocoa", new BlockCocoa().setHardness(0.2f).setResistance(5.0f).setStepSound(soundTypeWood).setBlockName("cocoa").setBlockTextureName("cocoa"));
        blockRegistry.addObject(128, "sandstone_stairs", new BlockStairs(var2, 0).setBlockName("stairsSandStone"));
        blockRegistry.addObject(129, "emerald_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("oreEmerald").setBlockTextureName("emerald_ore"));
        blockRegistry.addObject(130, "ender_chest", new BlockEnderChest().setHardness(22.5f).setResistance(1000.0f).setStepSound(soundTypePiston).setBlockName("enderChest").setLightLevel(0.5f));
        blockRegistry.addObject(131, "tripwire_hook", new BlockTripWireHook().setBlockName("tripWireSource").setBlockTextureName("trip_wire_source"));
        blockRegistry.addObject(132, "tripwire", new BlockTripWire().setBlockName("tripWire").setBlockTextureName("trip_wire"));
        blockRegistry.addObject(133, "emerald_block", new BlockCompressed(MapColor.field_151653_I).setHardness(5.0f).setResistance(10.0f).setStepSound(soundTypeMetal).setBlockName("blockEmerald").setBlockTextureName("emerald_block"));
        blockRegistry.addObject(134, "spruce_stairs", new BlockStairs(var1, 1).setBlockName("stairsWoodSpruce"));
        blockRegistry.addObject(135, "birch_stairs", new BlockStairs(var1, 2).setBlockName("stairsWoodBirch"));
        blockRegistry.addObject(136, "jungle_stairs", new BlockStairs(var1, 3).setBlockName("stairsWoodJungle"));
        blockRegistry.addObject(137, "command_block", new BlockCommandBlock().setBlockUnbreakable().setResistance(6000000.0f).setBlockName("commandBlock").setBlockTextureName("command_block"));
        blockRegistry.addObject(138, "beacon", new BlockBeacon().setBlockName("beacon").setLightLevel(1.0f).setBlockTextureName("beacon"));
        blockRegistry.addObject(139, "cobblestone_wall", new BlockWall(var0).setBlockName("cobbleWall"));
        blockRegistry.addObject(140, "flower_pot", new BlockFlowerPot().setHardness(0.0f).setStepSound(soundTypeStone).setBlockName("flowerPot").setBlockTextureName("flower_pot"));
        blockRegistry.addObject(141, "carrots", new BlockCarrot().setBlockName("carrots").setBlockTextureName("carrots"));
        blockRegistry.addObject(142, "potatoes", new BlockPotato().setBlockName("potatoes").setBlockTextureName("potatoes"));
        blockRegistry.addObject(143, "wooden_button", new BlockButtonWood().setHardness(0.5f).setStepSound(soundTypeWood).setBlockName("button"));
        blockRegistry.addObject(144, "skull", new BlockSkull().setHardness(1.0f).setStepSound(soundTypePiston).setBlockName("skull").setBlockTextureName("skull"));
        blockRegistry.addObject(145, "anvil", new BlockAnvil().setHardness(5.0f).setStepSound(soundTypeAnvil).setResistance(2000.0f).setBlockName("anvil"));
        blockRegistry.addObject(146, "trapped_chest", new BlockChest(1).setHardness(2.5f).setStepSound(soundTypeWood).setBlockName("chestTrap"));
        blockRegistry.addObject(147, "light_weighted_pressure_plate", new BlockPressurePlateWeighted("gold_block", Material.iron, 15).setHardness(0.5f).setStepSound(soundTypeWood).setBlockName("weightedPlate_light"));
        blockRegistry.addObject(148, "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted("iron_block", Material.iron, 150).setHardness(0.5f).setStepSound(soundTypeWood).setBlockName("weightedPlate_heavy"));
        blockRegistry.addObject(149, "unpowered_comparator", new BlockRedstoneComparator(false).setHardness(0.0f).setStepSound(soundTypeWood).setBlockName("comparator").disableStats().setBlockTextureName("comparator_off"));
        blockRegistry.addObject(150, "powered_comparator", new BlockRedstoneComparator(true).setHardness(0.0f).setLightLevel(0.625f).setStepSound(soundTypeWood).setBlockName("comparator").disableStats().setBlockTextureName("comparator_on"));
        blockRegistry.addObject(151, "daylight_detector", new BlockDaylightDetector().setHardness(0.2f).setStepSound(soundTypeWood).setBlockName("daylightDetector").setBlockTextureName("daylight_detector"));
        blockRegistry.addObject(152, "redstone_block", new BlockCompressedPowered(MapColor.field_151656_f).setHardness(5.0f).setResistance(10.0f).setStepSound(soundTypeMetal).setBlockName("blockRedstone").setBlockTextureName("redstone_block"));
        blockRegistry.addObject(153, "quartz_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(soundTypePiston).setBlockName("netherquartz").setBlockTextureName("quartz_ore"));
        blockRegistry.addObject(154, "hopper", new BlockHopper().setHardness(3.0f).setResistance(8.0f).setStepSound(soundTypeWood).setBlockName("hopper").setBlockTextureName("hopper"));
        Block var9 = new BlockQuartz().setStepSound(soundTypePiston).setHardness(0.8f).setBlockName("quartzBlock").setBlockTextureName("quartz_block");
        blockRegistry.addObject(155, "quartz_block", var9);
        blockRegistry.addObject(156, "quartz_stairs", new BlockStairs(var9, 0).setBlockName("stairsQuartz"));
        blockRegistry.addObject(157, "activator_rail", new BlockRailPowered().setHardness(0.7f).setStepSound(soundTypeMetal).setBlockName("activatorRail").setBlockTextureName("rail_activator"));
        blockRegistry.addObject(158, "dropper", new BlockDropper().setHardness(3.5f).setStepSound(soundTypePiston).setBlockName("dropper").setBlockTextureName("dropper"));
        blockRegistry.addObject(159, "stained_hardened_clay", new BlockColored(Material.rock).setHardness(1.25f).setResistance(7.0f).setStepSound(soundTypePiston).setBlockName("clayHardenedStained").setBlockTextureName("hardened_clay_stained"));
        blockRegistry.addObject(160, "stained_glass_pane", new BlockStainedGlassPane().setHardness(0.3f).setStepSound(soundTypeGlass).setBlockName("thinStainedGlass").setBlockTextureName("glass"));
        blockRegistry.addObject(161, "leaves2", new BlockNewLeaf().setBlockName("leaves").setBlockTextureName("leaves"));
        blockRegistry.addObject(162, "log2", new BlockNewLog().setBlockName("log").setBlockTextureName("log"));
        blockRegistry.addObject(163, "acacia_stairs", new BlockStairs(var1, 4).setBlockName("stairsWoodAcacia"));
        blockRegistry.addObject(164, "dark_oak_stairs", new BlockStairs(var1, 5).setBlockName("stairsWoodDarkOak"));
        blockRegistry.addObject(170, "hay_block", new BlockHay().setHardness(0.5f).setStepSound(soundTypeGrass).setBlockName("hayBlock").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("hay_block"));
        blockRegistry.addObject(171, "carpet", new BlockCarpet().setHardness(0.1f).setStepSound(soundTypeCloth).setBlockName("woolCarpet").setLightOpacity(0));
        blockRegistry.addObject(172, "hardened_clay", new BlockHardenedClay().setHardness(1.25f).setResistance(7.0f).setStepSound(soundTypePiston).setBlockName("clayHardened").setBlockTextureName("hardened_clay"));
        blockRegistry.addObject(173, "coal_block", new Block(Material.rock).setHardness(5.0f).setResistance(10.0f).setStepSound(soundTypePiston).setBlockName("blockCoal").setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("coal_block"));
        blockRegistry.addObject(174, "packed_ice", new BlockPackedIce().setHardness(0.5f).setStepSound(soundTypeGlass).setBlockName("icePacked").setBlockTextureName("ice_packed"));
        blockRegistry.addObject(175, "double_plant", new BlockDoublePlant());
        for (Block var11 : blockRegistry) {
            boolean var17;
            if (var11.blockMaterial == Material.air) {
                var11.field_149783_u = false;
                continue;
            }
            boolean var12 = false;
            boolean var13 = var11.getRenderType() == 10;
            boolean var14 = var11 instanceof BlockSlab;
            boolean var15 = var11 == var4;
            boolean var16 = var11.canBlockGrass;
            boolean bl = var17 = var11.lightOpacity == 0;
            if (var13 || var14 || var15 || var16 || var17) {
                var12 = true;
            }
            var11.field_149783_u = var12;
        }
    }

    protected Block(Material p_i45394_1_) {
        this.blockMaterial = p_i45394_1_;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.opaque = this.isOpaqueCube();
        this.lightOpacity = this.isOpaqueCube() ? 255 : 0;
        this.canBlockGrass = !p_i45394_1_.getCanBlockGrass();
    }

    protected Block setStepSound(SoundType p_149672_1_) {
        this.stepSound = p_149672_1_;
        return this;
    }

    public Block setLightOpacity(int p_149713_1_) {
        this.lightOpacity = p_149713_1_;
        return this;
    }

    protected Block setLightLevel(float p_149715_1_) {
        this.lightValue = (int)(15.0f * p_149715_1_);
        return this;
    }

    protected Block setResistance(float p_149752_1_) {
        this.blockResistance = p_149752_1_ * 3.0f;
        return this;
    }

    public boolean isBlockNormalCube() {
        if (this.blockMaterial.blocksMovement() && this.renderAsNormalBlock()) {
            return true;
        }
        return false;
    }

    public boolean isNormalCube() {
        if (this.blockMaterial.isOpaque() && this.renderAsNormalBlock() && !this.canProvidePower()) {
            return true;
        }
        return false;
    }

    public boolean renderAsNormalBlock() {
        return Resilience.getInstance().getXrayUtils().xrayEnabled ? Resilience.getInstance().getXrayUtils().shouldRenderBlock(new XrayBlock(Resilience.getInstance().getInvoker().getIdFromBlock(this))) : true;
    }

    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
        return !this.blockMaterial.blocksMovement();
    }

    public int getRenderType() {
        return 0;
    }

    protected Block setHardness(float p_149711_1_) {
        this.blockHardness = p_149711_1_;
        if (this.blockResistance < p_149711_1_ * 5.0f) {
            this.blockResistance = p_149711_1_ * 5.0f;
        }
        return this;
    }

    protected Block setBlockUnbreakable() {
        this.setHardness(-1.0f);
        return this;
    }

    public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_) {
        return this.blockHardness;
    }

    protected Block setTickRandomly(boolean p_149675_1_) {
        this.needsRandomTick = p_149675_1_;
        return this;
    }

    public boolean getTickRandomly() {
        return this.needsRandomTick;
    }

    public boolean hasTileEntity() {
        return this.isBlockContainer;
    }

    protected final void setBlockBounds(float p_149676_1_, float p_149676_2_, float p_149676_3_, float p_149676_4_, float p_149676_5_, float p_149676_6_) {
        this.field_149759_B = p_149676_1_;
        this.field_149760_C = p_149676_2_;
        this.field_149754_D = p_149676_3_;
        this.field_149755_E = p_149676_4_;
        this.field_149756_F = p_149676_5_;
        this.field_149757_G = p_149676_6_;
    }

    public int getBlockBrightness(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
        if (Resilience.getInstance().getXrayUtils().xrayEnabled || Resilience.getInstance().getValues().caveFinderEnabled) {
            return 100;
        }
        Block var5 = p_149677_1_.getBlock(p_149677_2_, p_149677_3_, p_149677_4_);
        int var6 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, var5.getLightValue());
        if (var6 == 0 && var5 instanceof BlockSlab) {
            var5 = p_149677_1_.getBlock(p_149677_2_, --p_149677_3_, p_149677_4_);
            return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, var5.getLightValue());
        }
        return var6;
    }

    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return p_149646_5_ == 0 && this.field_149760_C > 0.0 ? true : (p_149646_5_ == 1 && this.field_149756_F < 1.0 ? true : (p_149646_5_ == 2 && this.field_149754_D > 0.0 ? true : (p_149646_5_ == 3 && this.field_149757_G < 1.0 ? true : (p_149646_5_ == 4 && this.field_149759_B > 0.0 ? true : (p_149646_5_ == 5 && this.field_149755_E < 1.0 ? true : !p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_).isOpaqueCube())))));
    }

    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
        return p_149747_1_.getBlock(p_149747_2_, p_149747_3_, p_149747_4_).getMaterial().isSolid();
    }

    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_) {
        return this.getIcon(p_149673_5_, p_149673_1_.getBlockMetadata(p_149673_2_, p_149673_3_, p_149673_4_));
    }

    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return this.blockIcon;
    }

    public final IIcon getBlockTextureFromSide(int p_149733_1_) {
        return this.getIcon(p_149733_1_, 0);
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
        return AxisAlignedBB.getAABBPool().getAABB((double)p_149633_2_ + this.field_149759_B, (double)p_149633_3_ + this.field_149760_C, (double)p_149633_4_ + this.field_149754_D, (double)p_149633_2_ + this.field_149755_E, (double)p_149633_3_ + this.field_149756_F, (double)p_149633_4_ + this.field_149757_G);
    }

    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        AxisAlignedBB var8 = this.getCollisionBoundingBoxFromPool(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
        if (var8 != null && p_149743_5_.intersectsWith(var8)) {
            p_149743_6_.add(var8);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return AxisAlignedBB.getAABBPool().getAABB((double)p_149668_2_ + this.field_149759_B, (double)p_149668_3_ + this.field_149760_C, (double)p_149668_4_ + this.field_149754_D, (double)p_149668_2_ + this.field_149755_E, (double)p_149668_3_ + this.field_149756_F, (double)p_149668_4_ + this.field_149757_G);
    }

    public boolean isOpaqueCube() {
        return true;
    }

    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
        return this.isCollidable();
    }

    public boolean isCollidable() {
        return true;
    }

    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
    }

    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
    }

    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {
    }

    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
    }

    public int func_149738_a(World p_149738_1_) {
        return 10;
    }

    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
    }

    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
    }

    public int quantityDropped(Random p_149745_1_) {
        return 1;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(this);
    }

    public float getPlayerRelativeBlockHardness(EntityPlayer p_149737_1_, World p_149737_2_, int p_149737_3_, int p_149737_4_, int p_149737_5_) {
        float var6 = this.getBlockHardness(p_149737_2_, p_149737_3_, p_149737_4_, p_149737_5_);
        return var6 < 0.0f ? 0.0f : (!p_149737_1_.canHarvestBlock(this) ? p_149737_1_.getCurrentPlayerStrVsBlock(this, false) / var6 / 100.0f : p_149737_1_.getCurrentPlayerStrVsBlock(this, true) / var6 / 30.0f);
    }

    public final void dropBlockAsItem(World p_149697_1_, int p_149697_2_, int p_149697_3_, int p_149697_4_, int p_149697_5_, int p_149697_6_) {
        this.dropBlockAsItemWithChance(p_149697_1_, p_149697_2_, p_149697_3_, p_149697_4_, p_149697_5_, 1.0f, p_149697_6_);
    }

    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            int var8 = this.quantityDroppedWithBonus(p_149690_7_, p_149690_1_.rand);
            int var9 = 0;
            while (var9 < var8) {
                Item var10;
                if (p_149690_1_.rand.nextFloat() <= p_149690_6_ && (var10 = this.getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_)) != null) {
                    this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var10, 1, this.damageDropped(p_149690_5_)));
                }
                ++var9;
            }
        }
    }

    protected void dropBlockAsItem_do(World p_149642_1_, int p_149642_2_, int p_149642_3_, int p_149642_4_, ItemStack p_149642_5_) {
        if (!p_149642_1_.isClient && p_149642_1_.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            float var6 = 0.7f;
            double var7 = (double)(p_149642_1_.rand.nextFloat() * var6) + (double)(1.0f - var6) * 0.5;
            double var9 = (double)(p_149642_1_.rand.nextFloat() * var6) + (double)(1.0f - var6) * 0.5;
            double var11 = (double)(p_149642_1_.rand.nextFloat() * var6) + (double)(1.0f - var6) * 0.5;
            EntityItem var13 = new EntityItem(p_149642_1_, (double)p_149642_2_ + var7, (double)p_149642_3_ + var9, (double)p_149642_4_ + var11, p_149642_5_);
            var13.delayBeforeCanPickup = 10;
            p_149642_1_.spawnEntityInWorld(var13);
        }
    }

    protected void dropXpOnBlockBreak(World p_149657_1_, int p_149657_2_, int p_149657_3_, int p_149657_4_, int p_149657_5_) {
        if (!p_149657_1_.isClient) {
            while (p_149657_5_ > 0) {
                int var6 = EntityXPOrb.getXPSplit(p_149657_5_);
                p_149657_5_ -= var6;
                p_149657_1_.spawnEntityInWorld(new EntityXPOrb(p_149657_1_, (double)p_149657_2_ + 0.5, (double)p_149657_3_ + 0.5, (double)p_149657_4_ + 0.5, var6));
            }
        }
    }

    public int damageDropped(int p_149692_1_) {
        return 0;
    }

    public float getExplosionResistance(Entity p_149638_1_) {
        return this.blockResistance / 5.0f;
    }

    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_) {
        this.setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
        p_149731_5_ = p_149731_5_.addVector(- p_149731_2_, - p_149731_3_, - p_149731_4_);
        p_149731_6_ = p_149731_6_.addVector(- p_149731_2_, - p_149731_3_, - p_149731_4_);
        Vec3 var7 = p_149731_5_.getIntermediateWithXValue(p_149731_6_, this.field_149759_B);
        Vec3 var8 = p_149731_5_.getIntermediateWithXValue(p_149731_6_, this.field_149755_E);
        Vec3 var9 = p_149731_5_.getIntermediateWithYValue(p_149731_6_, this.field_149760_C);
        Vec3 var10 = p_149731_5_.getIntermediateWithYValue(p_149731_6_, this.field_149756_F);
        Vec3 var11 = p_149731_5_.getIntermediateWithZValue(p_149731_6_, this.field_149754_D);
        Vec3 var12 = p_149731_5_.getIntermediateWithZValue(p_149731_6_, this.field_149757_G);
        if (!this.isVecInsideYZBounds(var7)) {
            var7 = null;
        }
        if (!this.isVecInsideYZBounds(var8)) {
            var8 = null;
        }
        if (!this.isVecInsideXZBounds(var9)) {
            var9 = null;
        }
        if (!this.isVecInsideXZBounds(var10)) {
            var10 = null;
        }
        if (!this.isVecInsideXYBounds(var11)) {
            var11 = null;
        }
        if (!this.isVecInsideXYBounds(var12)) {
            var12 = null;
        }
        Vec3 var13 = null;
        if (var7 != null && (var13 == null || p_149731_5_.squareDistanceTo(var7) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var7;
        }
        if (var8 != null && (var13 == null || p_149731_5_.squareDistanceTo(var8) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var8;
        }
        if (var9 != null && (var13 == null || p_149731_5_.squareDistanceTo(var9) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var9;
        }
        if (var10 != null && (var13 == null || p_149731_5_.squareDistanceTo(var10) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var10;
        }
        if (var11 != null && (var13 == null || p_149731_5_.squareDistanceTo(var11) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var11;
        }
        if (var12 != null && (var13 == null || p_149731_5_.squareDistanceTo(var12) < p_149731_5_.squareDistanceTo(var13))) {
            var13 = var12;
        }
        if (var13 == null) {
            return null;
        }
        int var14 = -1;
        if (var13 == var7) {
            var14 = 4;
        }
        if (var13 == var8) {
            var14 = 5;
        }
        if (var13 == var9) {
            var14 = 0;
        }
        if (var13 == var10) {
            var14 = 1;
        }
        if (var13 == var11) {
            var14 = 2;
        }
        if (var13 == var12) {
            var14 = 3;
        }
        return new MovingObjectPosition(p_149731_2_, p_149731_3_, p_149731_4_, var14, var13.addVector(p_149731_2_, p_149731_3_, p_149731_4_));
    }

    private boolean isVecInsideYZBounds(Vec3 p_149654_1_) {
        return p_149654_1_ == null ? false : p_149654_1_.yCoord >= this.field_149760_C && p_149654_1_.yCoord <= this.field_149756_F && p_149654_1_.zCoord >= this.field_149754_D && p_149654_1_.zCoord <= this.field_149757_G;
    }

    private boolean isVecInsideXZBounds(Vec3 p_149687_1_) {
        return p_149687_1_ == null ? false : p_149687_1_.xCoord >= this.field_149759_B && p_149687_1_.xCoord <= this.field_149755_E && p_149687_1_.zCoord >= this.field_149754_D && p_149687_1_.zCoord <= this.field_149757_G;
    }

    private boolean isVecInsideXYBounds(Vec3 p_149661_1_) {
        return p_149661_1_ == null ? false : p_149661_1_.xCoord >= this.field_149759_B && p_149661_1_.xCoord <= this.field_149755_E && p_149661_1_.yCoord >= this.field_149760_C && p_149661_1_.yCoord <= this.field_149756_F;
    }

    public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
    }

    public int getRenderBlockPass() {
        int pass = 0;
        if (Resilience.getInstance().getXrayUtils().xrayEnabled) {
            pass = Resilience.getInstance().getXrayUtils().shouldRenderBlock(new XrayBlock(Resilience.getInstance().getInvoker().getIdFromBlock(this))) ? 0 : 1;
            return pass;
        }
        if (Resilience.getInstance().getValues().caveFinderEnabled) {
            pass = Resilience.getInstance().getInvoker().getIdFromBlock(this) == 1 ? 0 : -1;
        }
        return pass;
    }

    public boolean canReplace(World p_149705_1_, int p_149705_2_, int p_149705_3_, int p_149705_4_, int p_149705_5_, ItemStack p_149705_6_) {
        return this.canPlaceBlockOnSide(p_149705_1_, p_149705_2_, p_149705_3_, p_149705_4_, p_149705_5_);
    }

    public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_) {
        return this.canPlaceBlockAt(p_149707_1_, p_149707_2_, p_149707_3_, p_149707_4_);
    }

    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
        return p_149742_1_.getBlock((int)p_149742_2_, (int)p_149742_3_, (int)p_149742_4_).blockMaterial.isReplaceable();
    }

    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        return false;
    }

    public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_) {
    }

    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
        return p_149660_9_;
    }

    public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {
    }

    public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_) {
    }

    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
    }

    public final double getBlockBoundsMinX() {
        return this.field_149759_B;
    }

    public final double getBlockBoundsMaxX() {
        return this.field_149755_E;
    }

    public final double getBlockBoundsMinY() {
        return this.field_149760_C;
    }

    public final double getBlockBoundsMaxY() {
        return this.field_149756_F;
    }

    public final double getBlockBoundsMinZ() {
        return this.field_149754_D;
    }

    public final double getBlockBoundsMaxZ() {
        return this.field_149757_G;
    }

    public int getBlockColor() {
        return 16777215;
    }

    public int getRenderColor(int p_149741_1_) {
        return 16777215;
    }

    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        return 16777215;
    }

    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_) {
        return 0;
    }

    public boolean canProvidePower() {
        return false;
    }

    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
    }

    public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_) {
        return 0;
    }

    public void setBlockBoundsForItemRender() {
    }

    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_) {
        p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
        p_149636_2_.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(p_149636_2_)) {
            ItemStack var8 = this.createStackedBlock(p_149636_6_);
            if (var8 != null) {
                this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, var8);
            }
        } else {
            int var7 = EnchantmentHelper.getFortuneModifier(p_149636_2_);
            this.dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, var7);
        }
    }

    protected boolean canSilkHarvest() {
        if (this.renderAsNormalBlock() && !this.isBlockContainer) {
            return true;
        }
        return false;
    }

    protected ItemStack createStackedBlock(int p_149644_1_) {
        int var2 = 0;
        Item var3 = Item.getItemFromBlock(this);
        if (var3 != null && var3.getHasSubtypes()) {
            var2 = p_149644_1_;
        }
        return new ItemStack(var3, 1, var2);
    }

    public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
        return this.quantityDropped(p_149679_2_);
    }

    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        return true;
    }

    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
    }

    public void onPostBlockPlaced(World p_149714_1_, int p_149714_2_, int p_149714_3_, int p_149714_4_, int p_149714_5_) {
    }

    public Block setBlockName(String p_149663_1_) {
        this.unlocalizedNameBlock = p_149663_1_;
        return this;
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + ".name");
    }

    public String getUnlocalizedName() {
        return "tile." + this.unlocalizedNameBlock;
    }

    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_) {
        return false;
    }

    public boolean getEnableStats() {
        return this.enableStats;
    }

    protected Block disableStats() {
        this.enableStats = false;
        return this;
    }

    public int getMobilityFlag() {
        return this.blockMaterial.getMaterialMobility();
    }

    public float getAmbientOcclusionLightValue() {
        return this.isBlockNormalCube() ? 0.2f : 1.0f;
    }

    public void onFallenUpon(World p_149746_1_, int p_149746_2_, int p_149746_3_, int p_149746_4_, Entity p_149746_5_, float p_149746_6_) {
    }

    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return Item.getItemFromBlock(this);
    }

    public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
        return this.damageDropped(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_));
    }

    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }

    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.displayOnCreativeTab;
    }

    public Block setCreativeTab(CreativeTabs p_149647_1_) {
        this.displayOnCreativeTab = p_149647_1_;
        return this;
    }

    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
    }

    public void onBlockPreDestroy(World p_149725_1_, int p_149725_2_, int p_149725_3_, int p_149725_4_, int p_149725_5_) {
    }

    public void fillWithRain(World p_149639_1_, int p_149639_2_, int p_149639_3_, int p_149639_4_) {
    }

    public boolean isFlowerPot() {
        return false;
    }

    public boolean func_149698_L() {
        return true;
    }

    public boolean canDropFromExplosion(Explosion p_149659_1_) {
        return true;
    }

    public boolean func_149667_c(Block p_149667_1_) {
        if (this == p_149667_1_) {
            return true;
        }
        return false;
    }

    public static boolean isEqualTo(Block p_149680_0_, Block p_149680_1_) {
        return p_149680_0_ != null && p_149680_1_ != null ? (p_149680_0_ == p_149680_1_ ? true : p_149680_0_.func_149667_c(p_149680_1_)) : false;
    }

    public boolean hasComparatorInputOverride() {
        return false;
    }

    public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_) {
        return 0;
    }

    protected Block setBlockTextureName(String p_149658_1_) {
        this.textureName = p_149658_1_;
        return this;
    }

    protected String getTextureName() {
        return this.textureName == null ? "MISSING_ICON_BLOCK_" + Block.getIdFromBlock(this) + "_" + this.unlocalizedNameBlock : this.textureName;
    }

    public IIcon func_149735_b(int p_149735_1_, int p_149735_2_) {
        return this.getIcon(p_149735_1_, p_149735_2_);
    }

    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
    }

    public String getItemIconName() {
        return null;
    }

    public static class SoundType {
        public final String field_150501_a;
        public final float field_150499_b;
        public final float field_150500_c;
        private static final String __OBFID = "CL_00000203";

        public SoundType(String p_i45393_1_, float p_i45393_2_, float p_i45393_3_) {
            this.field_150501_a = p_i45393_1_;
            this.field_150499_b = p_i45393_2_;
            this.field_150500_c = p_i45393_3_;
        }

        public float func_150497_c() {
            return this.field_150499_b;
        }

        public float func_150494_d() {
            return this.field_150500_c;
        }

        public String func_150495_a() {
            return "dig." + this.field_150501_a;
        }

        public String func_150498_e() {
            return "step." + this.field_150501_a;
        }

        public String func_150496_b() {
            return this.func_150495_a();
        }
    }

}

