/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.utilities.game;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.relations.EnemyManager;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.io.PrintStream;
import java.util.List;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;

public class EntityUtils {
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();

    public Entity getClosestEntity(Entity from, boolean players, boolean mobs, boolean animals, boolean invisibles, boolean propBlocks) {
        Entity prevEntity = null;
        for (Object o : this.invoker.getEntityList()) {
            if (o instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase)o;
                if (entity == null || this.isThePlayer(entity)) continue;
                if (prevEntity == null) {
                    if (this.isEntityFriend(entity)) continue;
                    prevEntity = entity;
                } else {
                    if (this.isEntityFriend(entity)) continue;
                    if (entity instanceof EntityOtherPlayerMP && players) {
                        if (!invisibles && this.invoker.isInvisible(entity)) continue;
                        if (this.isCloser(entity, prevEntity, 2.0f)) {
                            prevEntity = entity;
                        }
                    }
                    if (entity instanceof EntityMob && !(entity instanceof EntityHorse) && !(entity instanceof EntityAnimal) && mobs && this.isCloser(entity, prevEntity, 1.0f)) {
                        prevEntity = entity;
                    }
                    if ((entity instanceof EntityAnimal || entity instanceof EntityHorse) && animals && this.isCloser(entity, prevEntity, 0.0f)) {
                        prevEntity = entity;
                    }
                }
            }
            if (!(o instanceof EntityFallingBlock) || !propBlocks) continue;
            System.out.println(prevEntity + " because " + propBlocks);
            EntityFallingBlock entity = (EntityFallingBlock)o;
            if (entity == null || !this.isCloser(entity, prevEntity, 0.0f)) continue;
            prevEntity = entity;
        }
        if (prevEntity != null) {
            return prevEntity;
        }
        return null;
    }

    public boolean isCloser(Entity now, Entity first, float error) {
        if (first.getClass().isAssignableFrom(now.getClass())) {
            if (this.invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), now) < this.invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), first)) {
                return true;
            }
            return false;
        }
        if (this.invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), now) < this.invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), first) + error) {
            return true;
        }
        return false;
    }

    public boolean canHit(Entity e) {
        if (e != null && !this.invoker.isEntityDead(e) && this.invoker.canEntityBeSeen(e) && this.isWithinRange(6.0f, e)) {
            return true;
        }
        return false;
    }

    public boolean canHit(Entity e, float range) {
        if (e != null && !this.invoker.isEntityDead(e) && this.invoker.canEntityBeSeen(e) && this.isWithinRange(range, e)) {
            return true;
        }
        return false;
    }

    public boolean isWithinRange(float range, Entity e) {
        if (this.invoker.getDistanceToEntity(e, Resilience.getInstance().getWrapper().getPlayer()) <= range) {
            return true;
        }
        return false;
    }

    public void hitEntity(Entity e) {
        this.invoker.attackEntity(e);
        this.invoker.swingItem();
    }

    public void hitEntity(Entity e, boolean block) {
        this.invoker.attackEntity(e);
        EventOnClick eventClick = new EventOnClick(0);
        eventClick.onEvent();
        this.invoker.swingItem();
        if (block && this.invoker.getCurrentItem().getItem() instanceof ItemSword) {
            this.invoker.useItemRightClick(this.invoker.getCurrentItem());
        }
    }

    public boolean isThePlayer(Entity e) {
        if (e == null || Resilience.getInstance().getWrapper().getPlayer() == null) {
            return false;
        }
        if (e != Resilience.getInstance().getWrapper().getPlayer()) {
            return false;
        }
        return true;
    }

    public boolean isEntityFriend(Entity e) {
        if (e instanceof EntityOtherPlayerMP) {
            EntityOtherPlayerMP player = (EntityOtherPlayerMP)e;
            return FriendManager.isFriend(this.invoker.getPlayerName(player));
        }
        return false;
    }

    public boolean isEntityEnemy(Entity e) {
        if (e instanceof EntityOtherPlayerMP) {
            EntityOtherPlayerMP player = (EntityOtherPlayerMP)e;
            return EnemyManager.isEnemy(this.invoker.getPlayerName(player));
        }
        return false;
    }

    public void faceEntity(Entity e) {
        double var6;
        double var4 = this.invoker.getPosX(e) - this.invoker.getPosX();
        double var8 = this.invoker.getPosZ(e) - this.invoker.getPosZ();
        if (e instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)e;
            var6 = this.invoker.getPosY(entity) + (double)this.invoker.getEyeHeight(entity) - (this.invoker.getPosY() + (double)this.invoker.getEyeHeight());
        } else {
            var6 = (this.invoker.getBoundboxMinY(e) + this.invoker.getBoundboxMaxY(e)) / 2.0 - (this.invoker.getPosY() + (double)this.invoker.getEyeHeight());
        }
        double var14 = Utils.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float)(- Math.atan2(var6, var14) * 180.0 / 3.141592653589793);
        this.invoker.setRotationPitch(var13);
        this.invoker.setRotationYaw(var12);
    }

    public boolean withinRotation(Entity e, float rotation) {
        double var6;
        double var4 = this.invoker.getPosX(e) - this.invoker.getPosX();
        double var8 = this.invoker.getPosZ(e) - this.invoker.getPosZ();
        if (e instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)e;
            var6 = this.invoker.getPosY(entity) + (double)this.invoker.getEyeHeight(entity) - (this.invoker.getPosY() + (double)this.invoker.getEyeHeight());
        } else {
            var6 = (this.invoker.getBoundboxMinY(e) + this.invoker.getBoundboxMaxY(e)) / 2.0 - (this.invoker.getPosY() + (double)this.invoker.getEyeHeight());
        }
        double var14 = Utils.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float)(- Math.atan2(var6, var14) * 180.0 / 3.141592653589793);
        float totalRotation = MathHelper.wrapAngleTo180_float(this.invoker.getRotationYaw() - var12);
        if (Math.abs(totalRotation) <= rotation) {
            return true;
        }
        return false;
    }

    public boolean isEntityDead(Entity entity) {
        return entity.isDead;
    }
}

