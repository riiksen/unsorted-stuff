/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.biome;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.world.biome.BiomeGenEnd;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.biome.BiomeGenHills;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.biome.BiomeGenMushroomIsland;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.biome.BiomeGenSavanna;
import net.minecraft.world.biome.BiomeGenSnow;
import net.minecraft.world.biome.BiomeGenStoneBeach;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.biome.BiomeGenTaiga;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BiomeGenBase {
    private static final Logger logger = LogManager.getLogger();
    protected static final Height field_150596_a = new Height(0.1f, 0.2f);
    protected static final Height field_150594_b = new Height(-0.5f, 0.0f);
    protected static final Height field_150595_c = new Height(-1.0f, 0.1f);
    protected static final Height field_150592_d = new Height(-1.8f, 0.1f);
    protected static final Height field_150593_e = new Height(0.125f, 0.05f);
    protected static final Height field_150590_f = new Height(0.2f, 0.2f);
    protected static final Height field_150591_g = new Height(0.45f, 0.3f);
    protected static final Height field_150602_h = new Height(1.5f, 0.025f);
    protected static final Height field_150603_i = new Height(1.0f, 0.5f);
    protected static final Height field_150600_j = new Height(0.0f, 0.025f);
    protected static final Height field_150601_k = new Height(0.1f, 0.8f);
    protected static final Height field_150598_l = new Height(0.2f, 0.3f);
    protected static final Height field_150599_m = new Height(-0.2f, 0.1f);
    private static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
    public static final Set field_150597_n = Sets.newHashSet();
    public static final BiomeGenBase ocean = new BiomeGenOcean(0).setColor(112).setBiomeName("Ocean").func_150570_a(field_150595_c);
    public static final BiomeGenBase plains = new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains");
    public static final BiomeGenBase desert = new BiomeGenDesert(2).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).func_150570_a(field_150593_e);
    public static final BiomeGenBase extremeHills = new BiomeGenHills(3, false).setColor(6316128).setBiomeName("Extreme Hills").func_150570_a(field_150603_i).setTemperatureRainfall(0.2f, 0.3f);
    public static final BiomeGenBase forest = new BiomeGenForest(4, 0).setColor(353825).setBiomeName("Forest");
    public static final BiomeGenBase taiga = new BiomeGenTaiga(5, 0).setColor(747097).setBiomeName("Taiga").func_76733_a(5159473).setTemperatureRainfall(0.25f, 0.8f).func_150570_a(field_150590_f);
    public static final BiomeGenBase swampland = new BiomeGenSwamp(6).setColor(522674).setBiomeName("Swampland").func_76733_a(9154376).func_150570_a(field_150599_m).setTemperatureRainfall(0.8f, 0.9f);
    public static final BiomeGenBase river = new BiomeGenRiver(7).setColor(255).setBiomeName("River").func_150570_a(field_150594_b);
    public static final BiomeGenBase hell = new BiomeGenHell(8).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0f, 0.0f);
    public static final BiomeGenBase sky = new BiomeGenEnd(9).setColor(8421631).setBiomeName("Sky").setDisableRain();
    public static final BiomeGenBase frozenOcean = new BiomeGenOcean(10).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().func_150570_a(field_150595_c).setTemperatureRainfall(0.0f, 0.5f);
    public static final BiomeGenBase frozenRiver = new BiomeGenRiver(11).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().func_150570_a(field_150594_b).setTemperatureRainfall(0.0f, 0.5f);
    public static final BiomeGenBase icePlains = new BiomeGenSnow(12, false).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).func_150570_a(field_150593_e);
    public static final BiomeGenBase iceMountains = new BiomeGenSnow(13, false).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().func_150570_a(field_150591_g).setTemperatureRainfall(0.0f, 0.5f);
    public static final BiomeGenBase mushroomIsland = new BiomeGenMushroomIsland(14).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9f, 1.0f).func_150570_a(field_150598_l);
    public static final BiomeGenBase mushroomIslandShore = new BiomeGenMushroomIsland(15).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9f, 1.0f).func_150570_a(field_150600_j);
    public static final BiomeGenBase beach = new BiomeGenBeach(16).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8f, 0.4f).func_150570_a(field_150600_j);
    public static final BiomeGenBase desertHills = new BiomeGenDesert(17).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0f, 0.0f).func_150570_a(field_150591_g);
    public static final BiomeGenBase forestHills = new BiomeGenForest(18, 0).setColor(2250012).setBiomeName("ForestHills").func_150570_a(field_150591_g);
    public static final BiomeGenBase taigaHills = new BiomeGenTaiga(19, 0).setColor(1456435).setBiomeName("TaigaHills").func_76733_a(5159473).setTemperatureRainfall(0.25f, 0.8f).func_150570_a(field_150591_g);
    public static final BiomeGenBase extremeHillsEdge = new BiomeGenHills(20, true).setColor(7501978).setBiomeName("Extreme Hills Edge").func_150570_a(field_150603_i.func_150775_a()).setTemperatureRainfall(0.2f, 0.3f);
    public static final BiomeGenBase jungle = new BiomeGenJungle(21, false).setColor(5470985).setBiomeName("Jungle").func_76733_a(5470985).setTemperatureRainfall(0.95f, 0.9f);
    public static final BiomeGenBase jungleHills = new BiomeGenJungle(22, false).setColor(2900485).setBiomeName("JungleHills").func_76733_a(5470985).setTemperatureRainfall(0.95f, 0.9f).func_150570_a(field_150591_g);
    public static final BiomeGenBase field_150574_L = new BiomeGenJungle(23, true).setColor(6458135).setBiomeName("JungleEdge").func_76733_a(5470985).setTemperatureRainfall(0.95f, 0.8f);
    public static final BiomeGenBase field_150575_M = new BiomeGenOcean(24).setColor(48).setBiomeName("Deep Ocean").func_150570_a(field_150592_d);
    public static final BiomeGenBase field_150576_N = new BiomeGenStoneBeach(25).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2f, 0.3f).func_150570_a(field_150601_k);
    public static final BiomeGenBase field_150577_O = new BiomeGenBeach(26).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05f, 0.3f).func_150570_a(field_150600_j).setEnableSnow();
    public static final BiomeGenBase field_150583_P = new BiomeGenForest(27, 2).setBiomeName("Birch Forest").setColor(3175492);
    public static final BiomeGenBase field_150582_Q = new BiomeGenForest(28, 2).setBiomeName("Birch Forest Hills").setColor(2055986).func_150570_a(field_150591_g);
    public static final BiomeGenBase field_150585_R = new BiomeGenForest(29, 3).setColor(4215066).setBiomeName("Roofed Forest");
    public static final BiomeGenBase field_150584_S = new BiomeGenTaiga(30, 0).setColor(3233098).setBiomeName("Cold Taiga").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).func_150570_a(field_150590_f).func_150563_c(16777215);
    public static final BiomeGenBase field_150579_T = new BiomeGenTaiga(31, 0).setColor(2375478).setBiomeName("Cold Taiga Hills").func_76733_a(5159473).setEnableSnow().setTemperatureRainfall(-0.5f, 0.4f).func_150570_a(field_150591_g).func_150563_c(16777215);
    public static final BiomeGenBase field_150578_U = new BiomeGenTaiga(32, 1).setColor(5858897).setBiomeName("Mega Taiga").func_76733_a(5159473).setTemperatureRainfall(0.3f, 0.8f).func_150570_a(field_150590_f);
    public static final BiomeGenBase field_150581_V = new BiomeGenTaiga(33, 1).setColor(4542270).setBiomeName("Mega Taiga Hills").func_76733_a(5159473).setTemperatureRainfall(0.3f, 0.8f).func_150570_a(field_150591_g);
    public static final BiomeGenBase field_150580_W = new BiomeGenHills(34, true).setColor(5271632).setBiomeName("Extreme Hills+").func_150570_a(field_150603_i).setTemperatureRainfall(0.2f, 0.3f);
    public static final BiomeGenBase field_150588_X = new BiomeGenSavanna(35).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2f, 0.0f).setDisableRain().func_150570_a(field_150593_e);
    public static final BiomeGenBase field_150587_Y = new BiomeGenSavanna(36).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0f, 0.0f).setDisableRain().func_150570_a(field_150602_h);
    public static final BiomeGenBase field_150589_Z = new BiomeGenMesa(37, false, false).setColor(14238997).setBiomeName("Mesa");
    public static final BiomeGenBase field_150607_aa = new BiomeGenMesa(38, false, true).setColor(11573093).setBiomeName("Mesa Plateau F").func_150570_a(field_150602_h);
    public static final BiomeGenBase field_150608_ab = new BiomeGenMesa(39, false, false).setColor(13274213).setBiomeName("Mesa Plateau").func_150570_a(field_150602_h);
    protected static final NoiseGeneratorPerlin field_150605_ac;
    protected static final NoiseGeneratorPerlin field_150606_ad;
    protected static final WorldGenDoublePlant field_150610_ae;
    public String biomeName;
    public int color;
    public int field_150609_ah;
    public Block topBlock = Blocks.grass;
    public int field_150604_aj = 0;
    public Block fillerBlock = Blocks.dirt;
    public int field_76754_C = 5169201;
    public float minHeight;
    public float maxHeight;
    public float temperature;
    public float rainfall;
    public int waterColorMultiplier;
    public BiomeDecorator theBiomeDecorator;
    protected List spawnableMonsterList;
    protected List spawnableCreatureList;
    protected List spawnableWaterCreatureList;
    protected List spawnableCaveCreatureList;
    protected boolean enableSnow;
    protected boolean enableRain;
    public final int biomeID;
    protected WorldGenTrees worldGeneratorTrees;
    protected WorldGenBigTree worldGeneratorBigTree;
    protected WorldGenSwamp worldGeneratorSwamp;
    private static final String __OBFID = "CL_00000158";

    static {
        plains.func_150566_k();
        desert.func_150566_k();
        forest.func_150566_k();
        taiga.func_150566_k();
        swampland.func_150566_k();
        icePlains.func_150566_k();
        jungle.func_150566_k();
        field_150574_L.func_150566_k();
        field_150584_S.func_150566_k();
        field_150588_X.func_150566_k();
        field_150587_Y.func_150566_k();
        field_150589_Z.func_150566_k();
        field_150607_aa.func_150566_k();
        field_150608_ab.func_150566_k();
        field_150583_P.func_150566_k();
        field_150582_Q.func_150566_k();
        field_150585_R.func_150566_k();
        field_150578_U.func_150566_k();
        extremeHills.func_150566_k();
        field_150580_W.func_150566_k();
        BiomeGenBase.biomeList[BiomeGenBase.field_150581_V.biomeID + 128] = biomeList[BiomeGenBase.field_150578_U.biomeID + 128];
        BiomeGenBase[] var0 = biomeList;
        int var1 = var0.length;
        int var2 = 0;
        while (var2 < var1) {
            BiomeGenBase var3 = var0[var2];
            if (var3 != null && var3.biomeID < 128) {
                field_150597_n.add(var3);
            }
            ++var2;
        }
        field_150597_n.remove(hell);
        field_150597_n.remove(sky);
        field_150605_ac = new NoiseGeneratorPerlin(new Random(1234), 1);
        field_150606_ad = new NoiseGeneratorPerlin(new Random(2345), 1);
        field_150610_ae = new WorldGenDoublePlant();
    }

    protected BiomeGenBase(int par1) {
        this.minHeight = BiomeGenBase.field_150596_a.field_150777_a;
        this.maxHeight = BiomeGenBase.field_150596_a.field_150776_b;
        this.temperature = 0.5f;
        this.rainfall = 0.5f;
        this.waterColorMultiplier = 16777215;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        this.enableRain = true;
        this.worldGeneratorTrees = new WorldGenTrees(false);
        this.worldGeneratorBigTree = new WorldGenBigTree(false);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        this.biomeID = par1;
        BiomeGenBase.biomeList[par1] = this;
        this.theBiomeDecorator = this.createBiomeDecorator();
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 5, 1, 1));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
    }

    protected BiomeDecorator createBiomeDecorator() {
        return new BiomeDecorator();
    }

    protected BiomeGenBase setTemperatureRainfall(float par1, float par2) {
        if (par1 > 0.1f && par1 < 0.2f) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        this.temperature = par1;
        this.rainfall = par2;
        return this;
    }

    protected final BiomeGenBase func_150570_a(Height p_150570_1_) {
        this.minHeight = p_150570_1_.field_150777_a;
        this.maxHeight = p_150570_1_.field_150776_b;
        return this;
    }

    protected BiomeGenBase setDisableRain() {
        this.enableRain = false;
        return this;
    }

    public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
        return p_150567_1_.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees;
    }

    public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
        return new WorldGenTallGrass(Blocks.tallgrass, 1);
    }

    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_) {
        return p_150572_1_.nextInt(3) > 0 ? BlockFlower.field_149858_b[0] : BlockFlower.field_149859_a[0];
    }

    protected BiomeGenBase setEnableSnow() {
        this.enableSnow = true;
        return this;
    }

    protected BiomeGenBase setBiomeName(String par1Str) {
        this.biomeName = par1Str;
        return this;
    }

    protected BiomeGenBase func_76733_a(int par1) {
        this.field_76754_C = par1;
        return this;
    }

    protected BiomeGenBase setColor(int par1) {
        this.func_150557_a(par1, false);
        return this;
    }

    protected BiomeGenBase func_150563_c(int p_150563_1_) {
        this.field_150609_ah = p_150563_1_;
        return this;
    }

    protected BiomeGenBase func_150557_a(int p_150557_1_, boolean p_150557_2_) {
        this.color = p_150557_1_;
        this.field_150609_ah = p_150557_2_ ? (p_150557_1_ & 16711422) >> 1 : p_150557_1_;
        return this;
    }

    public int getSkyColorByTemp(float par1) {
        if ((par1 /= 3.0f) < -1.0f) {
            par1 = -1.0f;
        }
        if (par1 > 1.0f) {
            par1 = 1.0f;
        }
        return Color.getHSBColor(0.62222224f - par1 * 0.05f, 0.5f + par1 * 0.1f, 1.0f).getRGB();
    }

    public List getSpawnableList(EnumCreatureType par1EnumCreatureType) {
        return par1EnumCreatureType == EnumCreatureType.monster ? this.spawnableMonsterList : (par1EnumCreatureType == EnumCreatureType.creature ? this.spawnableCreatureList : (par1EnumCreatureType == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : (par1EnumCreatureType == EnumCreatureType.ambient ? this.spawnableCaveCreatureList : null)));
    }

    public boolean getEnableSnow() {
        return this.func_150559_j();
    }

    public boolean canSpawnLightningBolt() {
        return this.func_150559_j() ? false : this.enableRain;
    }

    public boolean isHighHumidity() {
        if (this.rainfall > 0.85f) {
            return true;
        }
        return false;
    }

    public float getSpawningChance() {
        return 0.1f;
    }

    public final int getIntRainfall() {
        return (int)(this.rainfall * 65536.0f);
    }

    public final float getFloatRainfall() {
        return this.rainfall;
    }

    public final float getFloatTemperature(int p_150564_1_, int p_150564_2_, int p_150564_3_) {
        if (p_150564_2_ > 64) {
            float var4 = (float)field_150605_ac.func_151601_a((double)p_150564_1_ * 1.0 / 8.0, (double)p_150564_3_ * 1.0 / 8.0) * 4.0f;
            return this.temperature - (var4 + (float)p_150564_2_ - 64.0f) * 0.05f / 30.0f;
        }
        return this.temperature;
    }

    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        this.theBiomeDecorator.func_150512_a(par1World, par2Random, this, par3, par4);
    }

    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_) {
        double var4 = MathHelper.clamp_float(this.getFloatTemperature(p_150558_1_, p_150558_2_, p_150558_3_), 0.0f, 1.0f);
        double var6 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f);
        return ColorizerGrass.getGrassColor(var4, var6);
    }

    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
        double var4 = MathHelper.clamp_float(this.getFloatTemperature(p_150571_1_, p_150571_2_, p_150571_3_), 0.0f, 1.0f);
        double var6 = MathHelper.clamp_float(this.getFloatRainfall(), 0.0f, 1.0f);
        return ColorizerFoliage.getFoliageColor(var4, var6);
    }

    public boolean func_150559_j() {
        return this.enableSnow;
    }

    public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }

    public final void func_150560_b(World p_150560_1_, Random p_150560_2_, Block[] p_150560_3_, byte[] p_150560_4_, int p_150560_5_, int p_150560_6_, double p_150560_7_) {
        boolean var9 = true;
        Block var10 = this.topBlock;
        byte var11 = (byte)(this.field_150604_aj & 255);
        Block var12 = this.fillerBlock;
        int var13 = -1;
        int var14 = (int)(p_150560_7_ / 3.0 + 3.0 + p_150560_2_.nextDouble() * 0.25);
        int var15 = p_150560_5_ & 15;
        int var16 = p_150560_6_ & 15;
        int var17 = p_150560_3_.length / 256;
        int var18 = 255;
        while (var18 >= 0) {
            int var19 = (var16 * 16 + var15) * var17 + var18;
            if (var18 <= 0 + p_150560_2_.nextInt(5)) {
                p_150560_3_[var19] = Blocks.bedrock;
            } else {
                Block var20 = p_150560_3_[var19];
                if (var20 != null && var20.getMaterial() != Material.air) {
                    if (var20 == Blocks.stone) {
                        if (var13 == -1) {
                            if (var14 <= 0) {
                                var10 = null;
                                var11 = 0;
                                var12 = Blocks.stone;
                            } else if (var18 >= 59 && var18 <= 64) {
                                var10 = this.topBlock;
                                var11 = (byte)(this.field_150604_aj & 255);
                                var12 = this.fillerBlock;
                            }
                            if (var18 < 63 && (var10 == null || var10.getMaterial() == Material.air)) {
                                if (this.getFloatTemperature(p_150560_5_, var18, p_150560_6_) < 0.15f) {
                                    var10 = Blocks.ice;
                                    var11 = 0;
                                } else {
                                    var10 = Blocks.water;
                                    var11 = 0;
                                }
                            }
                            var13 = var14;
                            if (var18 >= 62) {
                                p_150560_3_[var19] = var10;
                                p_150560_4_[var19] = var11;
                            } else if (var18 < 56 - var14) {
                                var10 = null;
                                var12 = Blocks.stone;
                                p_150560_3_[var19] = Blocks.gravel;
                            } else {
                                p_150560_3_[var19] = var12;
                            }
                        } else if (var13 > 0) {
                            p_150560_3_[var19] = var12;
                            if (--var13 == 0 && var12 == Blocks.sand) {
                                var13 = p_150560_2_.nextInt(4) + Math.max(0, var18 - 63);
                                var12 = Blocks.sandstone;
                            }
                        }
                    }
                } else {
                    var13 = -1;
                }
            }
            --var18;
        }
    }

    protected BiomeGenBase func_150566_k() {
        return new BiomeGenMutated(this.biomeID + 128, this);
    }

    public Class func_150562_l() {
        return this.getClass();
    }

    public boolean func_150569_a(BiomeGenBase p_150569_1_) {
        return p_150569_1_ == this ? true : (p_150569_1_ == null ? false : this.func_150562_l() == p_150569_1_.func_150562_l());
    }

    public TempCategory func_150561_m() {
        return (double)this.temperature < 0.2 ? TempCategory.COLD : ((double)this.temperature < 1.0 ? TempCategory.MEDIUM : TempCategory.WARM);
    }

    public static BiomeGenBase[] getBiomeGenArray() {
        return biomeList;
    }

    public static BiomeGenBase func_150568_d(int p_150568_0_) {
        if (p_150568_0_ >= 0 && p_150568_0_ <= biomeList.length) {
            return biomeList[p_150568_0_];
        }
        logger.warn("Biome ID is out of bounds: " + p_150568_0_ + ", defaulting to 0 (Ocean)");
        return ocean;
    }

    public static class Height {
        public float field_150777_a;
        public float field_150776_b;
        private static final String __OBFID = "CL_00000159";

        public Height(float p_i45371_1_, float p_i45371_2_) {
            this.field_150777_a = p_i45371_1_;
            this.field_150776_b = p_i45371_2_;
        }

        public Height func_150775_a() {
            return new Height(this.field_150777_a * 0.8f, this.field_150776_b * 0.6f);
        }
    }

    public static class SpawnListEntry
    extends WeightedRandom.Item {
        public Class entityClass;
        public int minGroupCount;
        public int maxGroupCount;
        private static final String __OBFID = "CL_00000161";

        public SpawnListEntry(Class par1Class, int par2, int par3, int par4) {
            super(par2);
            this.entityClass = par1Class;
            this.minGroupCount = par3;
            this.maxGroupCount = par4;
        }

        public String toString() {
            return String.valueOf(this.entityClass.getSimpleName()) + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
        }
    }

    public static enum TempCategory {
        OCEAN("OCEAN", 0),
        COLD("COLD", 1),
        MEDIUM("MEDIUM", 2),
        WARM("WARM", 3);
        
        private static final TempCategory[] $VALUES;
        private static final String __OBFID = "CL_00000160";

        static {
            $VALUES = new TempCategory[]{OCEAN, COLD, MEDIUM, WARM};
        }

        private TempCategory(String p_i45372_1_, int p_i45372_2_, String string2, int n2) {
        }
    }

}

