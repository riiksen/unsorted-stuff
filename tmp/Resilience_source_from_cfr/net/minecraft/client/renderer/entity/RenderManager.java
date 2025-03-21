/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderBat;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderCaveSpider;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderGiantZombie;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderLeashKnot;
import net.minecraft.client.renderer.entity.RenderLightningBolt;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RenderMinecartMobSpawner;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.client.renderer.entity.RenderPainting;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.entity.RenderTntMinecart;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderManager {
    private Map entityRenderMap = new HashMap();
    public static RenderManager instance = new RenderManager();
    private FontRenderer fontRenderer;
    public static double renderPosX;
    public static double renderPosY;
    public static double renderPosZ;
    public TextureManager renderEngine;
    public ItemRenderer itemRenderer;
    public World worldObj;
    public EntityLivingBase livingPlayer;
    public Entity field_147941_i;
    public float playerViewY;
    public float playerViewX;
    public GameSettings options;
    public double viewerPosX;
    public double viewerPosY;
    public double viewerPosZ;
    public static boolean field_85095_o;
    private static final String __OBFID = "CL_00000991";

    private RenderManager() {
        this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider());
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider());
        this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5f), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7f));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(new ModelWolf(), new ModelWolf(), 0.5f));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3f));
        this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(new ModelOcelot(), 0.4f));
        this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish());
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
        this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman());
        this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan());
        this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton());
        this.entityRenderMap.put(EntityWitch.class, new RenderWitch());
        this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze());
        this.entityRenderMap.put(EntityZombie.class, new RenderZombie());
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25f));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube());
        this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
        this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast());
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7f));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager());
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem());
        this.entityRenderMap.put(EntityBat.class, new RenderBat());
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon());
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal());
        this.entityRenderMap.put(EntityWither.class, new RenderWither());
        this.entityRenderMap.put(Entity.class, new RenderEntity());
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame());
        this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot());
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(Items.snowball));
        this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(Items.ender_pearl));
        this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(Items.ender_eye));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(Items.egg));
        this.entityRenderMap.put(EntityPotion.class, new RenderSnowball(Items.potionitem, 16384));
        this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(Items.experience_bottle));
        this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(Items.fireworks));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(2.0f));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(0.5f));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull());
        this.entityRenderMap.put(EntityItem.class, new RenderItem());
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb());
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
        this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock());
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart());
        this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner());
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish());
        this.entityRenderMap.put(EntityHorse.class, new RenderHorse(new ModelHorse(), 0.75f));
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt());
        for (Render var2 : this.entityRenderMap.values()) {
            var2.setRenderManager(this);
        }
    }

    public Render getEntityClassRenderObject(Class par1Class) {
        Render var2 = (Render)this.entityRenderMap.get(par1Class);
        if (var2 == null && par1Class != Entity.class) {
            var2 = this.getEntityClassRenderObject(par1Class.getSuperclass());
            this.entityRenderMap.put(par1Class, var2);
        }
        return var2;
    }

    public Render getEntityRenderObject(Entity par1Entity) {
        return this.getEntityClassRenderObject(par1Entity.getClass());
    }

    public void func_147938_a(World p_147938_1_, TextureManager p_147938_2_, FontRenderer p_147938_3_, EntityLivingBase p_147938_4_, Entity p_147938_5_, GameSettings p_147938_6_, float p_147938_7_) {
        this.worldObj = p_147938_1_;
        this.renderEngine = p_147938_2_;
        this.options = p_147938_6_;
        this.livingPlayer = p_147938_4_;
        this.field_147941_i = p_147938_5_;
        this.fontRenderer = p_147938_3_;
        if (p_147938_4_.isPlayerSleeping()) {
            Block var8 = p_147938_1_.getBlock(MathHelper.floor_double(p_147938_4_.posX), MathHelper.floor_double(p_147938_4_.posY), MathHelper.floor_double(p_147938_4_.posZ));
            if (var8 == Blocks.bed) {
                int var9 = p_147938_1_.getBlockMetadata(MathHelper.floor_double(p_147938_4_.posX), MathHelper.floor_double(p_147938_4_.posY), MathHelper.floor_double(p_147938_4_.posZ));
                int var10 = var9 & 3;
                this.playerViewY = var10 * 90 + 180;
                this.playerViewX = 0.0f;
            }
        } else {
            this.playerViewY = p_147938_4_.prevRotationYaw + (p_147938_4_.rotationYaw - p_147938_4_.prevRotationYaw) * p_147938_7_;
            this.playerViewX = p_147938_4_.prevRotationPitch + (p_147938_4_.rotationPitch - p_147938_4_.prevRotationPitch) * p_147938_7_;
        }
        if (p_147938_6_.thirdPersonView == 2) {
            this.playerViewY += 180.0f;
        }
        this.viewerPosX = p_147938_4_.lastTickPosX + (p_147938_4_.posX - p_147938_4_.lastTickPosX) * (double)p_147938_7_;
        this.viewerPosY = p_147938_4_.lastTickPosY + (p_147938_4_.posY - p_147938_4_.lastTickPosY) * (double)p_147938_7_;
        this.viewerPosZ = p_147938_4_.lastTickPosZ + (p_147938_4_.posZ - p_147938_4_.lastTickPosZ) * (double)p_147938_7_;
    }

    public boolean func_147937_a(Entity p_147937_1_, float p_147937_2_) {
        return this.func_147936_a(p_147937_1_, p_147937_2_, false);
    }

    public boolean func_147936_a(Entity p_147936_1_, float p_147936_2_, boolean p_147936_3_) {
        if (p_147936_1_.ticksExisted == 0) {
            p_147936_1_.lastTickPosX = p_147936_1_.posX;
            p_147936_1_.lastTickPosY = p_147936_1_.posY;
            p_147936_1_.lastTickPosZ = p_147936_1_.posZ;
        }
        double var4 = p_147936_1_.lastTickPosX + (p_147936_1_.posX - p_147936_1_.lastTickPosX) * (double)p_147936_2_;
        double var6 = p_147936_1_.lastTickPosY + (p_147936_1_.posY - p_147936_1_.lastTickPosY) * (double)p_147936_2_;
        double var8 = p_147936_1_.lastTickPosZ + (p_147936_1_.posZ - p_147936_1_.lastTickPosZ) * (double)p_147936_2_;
        float var10 = p_147936_1_.prevRotationYaw + (p_147936_1_.rotationYaw - p_147936_1_.prevRotationYaw) * p_147936_2_;
        int var11 = p_147936_1_.getBrightnessForRender(p_147936_2_);
        if (p_147936_1_.isBurning()) {
            var11 = 15728880;
        }
        int var12 = var11 % 65536;
        int var13 = var11 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var12 / 1.0f, (float)var13 / 1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        return this.func_147939_a(p_147936_1_, var4 - renderPosX, var6 - renderPosY, var8 - renderPosZ, var10, p_147936_2_, p_147936_3_);
    }

    public boolean func_147940_a(Entity p_147940_1_, double p_147940_2_, double p_147940_4_, double p_147940_6_, float p_147940_8_, float p_147940_9_) {
        return this.func_147939_a(p_147940_1_, p_147940_2_, p_147940_4_, p_147940_6_, p_147940_8_, p_147940_9_, false);
    }

    public boolean func_147939_a(Entity p_147939_1_, double p_147939_2_, double p_147939_4_, double p_147939_6_, float p_147939_8_, float p_147939_9_, boolean p_147939_10_) {
        Render var11 = null;
        try {
            var11 = this.getEntityRenderObject(p_147939_1_);
            if (var11 != null && this.renderEngine != null) {
                if (!var11.func_147905_a() || p_147939_10_) {
                    try {
                        var11.doRender(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                    }
                    catch (Throwable var18) {
                        throw new ReportedException(CrashReport.makeCrashReport(var18, "Rendering entity in world"));
                    }
                    try {
                        var11.doRenderShadowAndFire(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                    }
                    catch (Throwable var17) {
                        throw new ReportedException(CrashReport.makeCrashReport(var17, "Post-rendering entity in world"));
                    }
                    if (field_85095_o && !p_147939_1_.isInvisible() && !p_147939_10_) {
                        try {
                            this.func_85094_b(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                        }
                        catch (Throwable var16) {
                            throw new ReportedException(CrashReport.makeCrashReport(var16, "Rendering entity hitbox in world"));
                        }
                    }
                }
            } else if (this.renderEngine != null) {
                return false;
            }
            return true;
        }
        catch (Throwable var19) {
            CrashReport var13 = CrashReport.makeCrashReport(var19, "Rendering entity in world");
            CrashReportCategory var14 = var13.makeCategory("Entity being rendered");
            p_147939_1_.addEntityCrashInfo(var14);
            CrashReportCategory var15 = var13.makeCategory("Renderer details");
            var15.addCrashSection("Assigned renderer", var11);
            var15.addCrashSection("Location", CrashReportCategory.func_85074_a(p_147939_2_, p_147939_4_, p_147939_6_));
            var15.addCrashSection("Rotation", Float.valueOf(p_147939_8_));
            var15.addCrashSection("Delta", Float.valueOf(p_147939_9_));
            throw new ReportedException(var13);
        }
    }

    private void func_85094_b(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3042);
        float var10 = par1Entity.width / 2.0f;
        AxisAlignedBB var11 = AxisAlignedBB.getAABBPool().getAABB(par2 - (double)var10, par4, par6 - (double)var10, par2 + (double)var10, par4 + (double)par1Entity.height, par6 + (double)var10);
        RenderGlobal.drawOutlinedBoundingBox(var11, 16777215);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)3042);
        GL11.glDepthMask((boolean)true);
    }

    public void set(World par1World) {
        this.worldObj = par1World;
    }

    public double getDistanceToCamera(double par1, double par3, double par5) {
        double var7 = par1 - this.viewerPosX;
        double var9 = par3 - this.viewerPosY;
        double var11 = par5 - this.viewerPosZ;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public void updateIcons(IIconRegister par1IconRegister) {
        for (Render var3 : this.entityRenderMap.values()) {
            var3.updateIcons(par1IconRegister);
        }
    }
}

