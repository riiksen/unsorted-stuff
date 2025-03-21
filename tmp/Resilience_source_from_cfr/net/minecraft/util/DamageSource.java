/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.Explosion;

public class DamageSource {
    public static DamageSource inFire = new DamageSource("inFire").setFireDamage();
    public static DamageSource onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
    public static DamageSource lava = new DamageSource("lava").setFireDamage();
    public static DamageSource inWall = new DamageSource("inWall").setDamageBypassesArmor();
    public static DamageSource drown = new DamageSource("drown").setDamageBypassesArmor();
    public static DamageSource starve = new DamageSource("starve").setDamageBypassesArmor().setDamageIsAbsolute();
    public static DamageSource cactus = new DamageSource("cactus");
    public static DamageSource fall = new DamageSource("fall").setDamageBypassesArmor();
    public static DamageSource outOfWorld = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
    public static DamageSource generic = new DamageSource("generic").setDamageBypassesArmor();
    public static DamageSource magic = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
    public static DamageSource wither = new DamageSource("wither").setDamageBypassesArmor();
    public static DamageSource anvil = new DamageSource("anvil");
    public static DamageSource fallingBlock = new DamageSource("fallingBlock");
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    private boolean damageIsAbsolute;
    private float hungerDamage = 0.3f;
    private boolean fireDamage;
    private boolean projectile;
    private boolean difficultyScaled;
    private boolean magicDamage;
    private boolean explosion;
    public String damageType;
    private static final String __OBFID = "CL_00001521";

    public static DamageSource causeMobDamage(EntityLivingBase par0EntityLivingBase) {
        return new EntityDamageSource("mob", par0EntityLivingBase);
    }

    public static DamageSource causePlayerDamage(EntityPlayer par0EntityPlayer) {
        return new EntityDamageSource("player", par0EntityPlayer);
    }

    public static DamageSource causeArrowDamage(EntityArrow par0EntityArrow, Entity par1Entity) {
        return new EntityDamageSourceIndirect("arrow", par0EntityArrow, par1Entity).setProjectile();
    }

    public static DamageSource causeFireballDamage(EntityFireball par0EntityFireball, Entity par1Entity) {
        return par1Entity == null ? new EntityDamageSourceIndirect("onFire", par0EntityFireball, par0EntityFireball).setFireDamage().setProjectile() : new EntityDamageSourceIndirect("fireball", par0EntityFireball, par1Entity).setFireDamage().setProjectile();
    }

    public static DamageSource causeThrownDamage(Entity par0Entity, Entity par1Entity) {
        return new EntityDamageSourceIndirect("thrown", par0Entity, par1Entity).setProjectile();
    }

    public static DamageSource causeIndirectMagicDamage(Entity par0Entity, Entity par1Entity) {
        return new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity).setDamageBypassesArmor().setMagicDamage();
    }

    public static DamageSource causeThornsDamage(Entity par0Entity) {
        return new EntityDamageSource("thorns", par0Entity).setMagicDamage();
    }

    public static DamageSource setExplosionSource(Explosion par0Explosion) {
        return par0Explosion != null && par0Explosion.getExplosivePlacedBy() != null ? new EntityDamageSource("explosion.player", par0Explosion.getExplosivePlacedBy()).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }

    public boolean isProjectile() {
        return this.projectile;
    }

    public DamageSource setProjectile() {
        this.projectile = true;
        return this;
    }

    public boolean isExplosion() {
        return this.explosion;
    }

    public DamageSource setExplosion() {
        this.explosion = true;
        return this;
    }

    public boolean isUnblockable() {
        return this.isUnblockable;
    }

    public float getHungerDamage() {
        return this.hungerDamage;
    }

    public boolean canHarmInCreative() {
        return this.isDamageAllowedInCreativeMode;
    }

    public boolean isDamageAbsolute() {
        return this.damageIsAbsolute;
    }

    protected DamageSource(String par1Str) {
        this.damageType = par1Str;
    }

    public Entity getSourceOfDamage() {
        return this.getEntity();
    }

    public Entity getEntity() {
        return null;
    }

    protected DamageSource setDamageBypassesArmor() {
        this.isUnblockable = true;
        this.hungerDamage = 0.0f;
        return this;
    }

    protected DamageSource setDamageAllowedInCreativeMode() {
        this.isDamageAllowedInCreativeMode = true;
        return this;
    }

    protected DamageSource setDamageIsAbsolute() {
        this.damageIsAbsolute = true;
        this.hungerDamage = 0.0f;
        return this;
    }

    protected DamageSource setFireDamage() {
        this.fireDamage = true;
        return this;
    }

    public IChatComponent func_151519_b(EntityLivingBase p_151519_1_) {
        EntityLivingBase var2 = p_151519_1_.func_94060_bK();
        String var3 = "death.attack." + this.damageType;
        String var4 = String.valueOf(var3) + ".player";
        return var2 != null && StatCollector.canTranslate(var4) ? new ChatComponentTranslation(var4, p_151519_1_.func_145748_c_(), var2.func_145748_c_()) : new ChatComponentTranslation(var3, p_151519_1_.func_145748_c_());
    }

    public boolean isFireDamage() {
        return this.fireDamage;
    }

    public String getDamageType() {
        return this.damageType;
    }

    public DamageSource setDifficultyScaled() {
        this.difficultyScaled = true;
        return this;
    }

    public boolean isDifficultyScaled() {
        return this.difficultyScaled;
    }

    public boolean isMagicDamage() {
        return this.magicDamage;
    }

    public DamageSource setMagicDamage() {
        this.magicDamage = true;
        return this;
    }
}

