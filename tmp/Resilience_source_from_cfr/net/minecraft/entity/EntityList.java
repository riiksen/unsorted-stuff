/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityList {
    private static final Logger logger = LogManager.getLogger();
    private static Map stringToClassMapping = new HashMap();
    private static Map classToStringMapping = new HashMap();
    private static Map IDtoClassMapping = new HashMap();
    private static Map classToIDMapping = new HashMap();
    private static Map stringToIDMapping = new HashMap();
    public static HashMap entityEggs = new LinkedHashMap();
    private static final String __OBFID = "CL_00001538";

    static {
        EntityList.addMapping(EntityItem.class, "Item", 1);
        EntityList.addMapping(EntityXPOrb.class, "XPOrb", 2);
        EntityList.addMapping(EntityLeashKnot.class, "LeashKnot", 8);
        EntityList.addMapping(EntityPainting.class, "Painting", 9);
        EntityList.addMapping(EntityArrow.class, "Arrow", 10);
        EntityList.addMapping(EntitySnowball.class, "Snowball", 11);
        EntityList.addMapping(EntityLargeFireball.class, "Fireball", 12);
        EntityList.addMapping(EntitySmallFireball.class, "SmallFireball", 13);
        EntityList.addMapping(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        EntityList.addMapping(EntityEnderEye.class, "EyeOfEnderSignal", 15);
        EntityList.addMapping(EntityPotion.class, "ThrownPotion", 16);
        EntityList.addMapping(EntityExpBottle.class, "ThrownExpBottle", 17);
        EntityList.addMapping(EntityItemFrame.class, "ItemFrame", 18);
        EntityList.addMapping(EntityWitherSkull.class, "WitherSkull", 19);
        EntityList.addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
        EntityList.addMapping(EntityFallingBlock.class, "FallingSand", 21);
        EntityList.addMapping(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
        EntityList.addMapping(EntityBoat.class, "Boat", 41);
        EntityList.addMapping(EntityMinecartEmpty.class, "MinecartRideable", 42);
        EntityList.addMapping(EntityMinecartChest.class, "MinecartChest", 43);
        EntityList.addMapping(EntityMinecartFurnace.class, "MinecartFurnace", 44);
        EntityList.addMapping(EntityMinecartTNT.class, "MinecartTNT", 45);
        EntityList.addMapping(EntityMinecartHopper.class, "MinecartHopper", 46);
        EntityList.addMapping(EntityMinecartMobSpawner.class, "MinecartSpawner", 47);
        EntityList.addMapping(EntityMinecartCommandBlock.class, "MinecartCommandBlock", 40);
        EntityList.addMapping(EntityLiving.class, "Mob", 48);
        EntityList.addMapping(EntityMob.class, "Monster", 49);
        EntityList.addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
        EntityList.addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        EntityList.addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        EntityList.addMapping(EntityGiantZombie.class, "Giant", 53);
        EntityList.addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        EntityList.addMapping(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        EntityList.addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        EntityList.addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        EntityList.addMapping(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        EntityList.addMapping(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        EntityList.addMapping(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        EntityList.addMapping(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        EntityList.addMapping(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        EntityList.addMapping(EntityDragon.class, "EnderDragon", 63);
        EntityList.addMapping(EntityWither.class, "WitherBoss", 64);
        EntityList.addMapping(EntityBat.class, "Bat", 65, 4996656, 986895);
        EntityList.addMapping(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        EntityList.addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
        EntityList.addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        EntityList.addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
        EntityList.addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        EntityList.addMapping(EntitySquid.class, "Squid", 94, 2243405, 7375001);
        EntityList.addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        EntityList.addMapping(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
        EntityList.addMapping(EntitySnowman.class, "SnowMan", 97);
        EntityList.addMapping(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        EntityList.addMapping(EntityIronGolem.class, "VillagerGolem", 99);
        EntityList.addMapping(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
        EntityList.addMapping(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        EntityList.addMapping(EntityEnderCrystal.class, "EnderCrystal", 200);
    }

    private static void addMapping(Class par0Class, String par1Str, int par2) {
        if (stringToClassMapping.containsKey(par1Str)) {
            throw new IllegalArgumentException("ID is already registered: " + par1Str);
        }
        if (IDtoClassMapping.containsKey(par2)) {
            throw new IllegalArgumentException("ID is already registered: " + par2);
        }
        stringToClassMapping.put(par1Str, par0Class);
        classToStringMapping.put(par0Class, par1Str);
        IDtoClassMapping.put(par2, par0Class);
        classToIDMapping.put(par0Class, par2);
        stringToIDMapping.put(par1Str, par2);
    }

    private static void addMapping(Class par0Class, String par1Str, int par2, int par3, int par4) {
        EntityList.addMapping(par0Class, par1Str, par2);
        entityEggs.put(par2, new EntityEggInfo(par2, par3, par4));
    }

    public static Entity createEntityByName(String par0Str, World par1World) {
        Entity var2 = null;
        try {
            Class var3 = (Class)stringToClassMapping.get(par0Str);
            if (var3 != null) {
                var2 = (Entity)var3.getConstructor(World.class).newInstance(par1World);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        return var2;
    }

    public static Entity createEntityFromNBT(NBTTagCompound par0NBTTagCompound, World par1World) {
        Entity var2 = null;
        if ("Minecart".equals(par0NBTTagCompound.getString("id"))) {
            switch (par0NBTTagCompound.getInteger("Type")) {
                case 0: {
                    par0NBTTagCompound.setString("id", "MinecartRideable");
                    break;
                }
                case 1: {
                    par0NBTTagCompound.setString("id", "MinecartChest");
                    break;
                }
                case 2: {
                    par0NBTTagCompound.setString("id", "MinecartFurnace");
                }
            }
            par0NBTTagCompound.removeTag("Type");
        }
        try {
            Class var3 = (Class)stringToClassMapping.get(par0NBTTagCompound.getString("id"));
            if (var3 != null) {
                var2 = (Entity)var3.getConstructor(World.class).newInstance(par1World);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        if (var2 != null) {
            var2.readFromNBT(par0NBTTagCompound);
        } else {
            logger.warn("Skipping Entity with id " + par0NBTTagCompound.getString("id"));
        }
        return var2;
    }

    public static Entity createEntityByID(int par0, World par1World) {
        Entity var2 = null;
        try {
            Class var3 = EntityList.getClassFromID(par0);
            if (var3 != null) {
                var2 = (Entity)var3.getConstructor(World.class).newInstance(par1World);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
        if (var2 == null) {
            logger.warn("Skipping Entity with id " + par0);
        }
        return var2;
    }

    public static int getEntityID(Entity par0Entity) {
        Class var1 = par0Entity.getClass();
        return classToIDMapping.containsKey(var1) ? (Integer)classToIDMapping.get(var1) : 0;
    }

    public static Class getClassFromID(int par0) {
        return (Class)IDtoClassMapping.get(par0);
    }

    public static String getEntityString(Entity par0Entity) {
        return (String)classToStringMapping.get(par0Entity.getClass());
    }

    public static String getStringFromID(int par0) {
        Class var1 = EntityList.getClassFromID(par0);
        return var1 != null ? (String)classToStringMapping.get(var1) : null;
    }

    public static void func_151514_a() {
    }

    public static Set func_151515_b() {
        return Collections.unmodifiableSet(stringToIDMapping.keySet());
    }

    public static class EntityEggInfo {
        public final int spawnedID;
        public final int primaryColor;
        public final int secondaryColor;
        public final StatBase field_151512_d;
        public final StatBase field_151513_e;
        private static final String __OBFID = "CL_00001539";

        public EntityEggInfo(int par1, int par2, int par3) {
            this.spawnedID = par1;
            this.primaryColor = par2;
            this.secondaryColor = par3;
            this.field_151512_d = StatList.func_151182_a(this);
            this.field_151513_e = StatList.func_151176_b(this);
        }
    }

}

