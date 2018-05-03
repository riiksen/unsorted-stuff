/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package com.krispdev.resilience.module.modules.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.value.Value;
import com.krispdev.resilience.utilities.value.values.StringValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ModuleFreecam
extends DefaultModule {
    private EntityOtherPlayerMP entity = null;
    private double freecamX;
    private double freecamY;
    private double freecamZ;
    private double freecamPitch;
    private double freecamYaw;

    public ModuleFreecam() {
        super("Freecam", 47);
        this.setCategory(ModuleCategory.PLAYER);
        this.setDescription("Allows you to walk outside of your body and fly through walls");
        this.setSave(false);
    }

    @Override
    public void onEnable() {
        try {
            Resilience.getInstance().getValues().freecamEnabled = true;
            Resilience.getInstance().getEventManager().register(this);
            this.freecamX = this.invoker.getPosX();
            this.freecamY = this.invoker.getPosY();
            this.freecamZ = this.invoker.getPosZ();
            this.freecamPitch = this.invoker.getRotationPitch();
            this.freecamYaw = this.invoker.getRotationYaw();
            this.entity = new EntityOtherPlayerMP(Resilience.getInstance().getWrapper().getWorld(), new GameProfile("", Resilience.getInstance().getValues().nameProtectEnabled ? Resilience.getInstance().getValues().nameProtectAlias.getValue() : this.invoker.getSessionUsername()));
            this.invoker.setPositionAndRotation(this.entity, this.invoker.getPosX(), this.invoker.getPosY() - (double)this.invoker.getEntityHeight(Resilience.getInstance().getWrapper().getPlayer()) + 0.17, this.invoker.getPosZ(), this.invoker.getRotationYaw(), this.invoker.getRotationPitch());
            this.invoker.copyInventory(this.entity, Resilience.getInstance().getWrapper().getPlayer());
            this.invoker.addEntityToWorld(this.entity, 69);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void onValueChange(EventValueChange event) {
        if (event.getValue() == Resilience.getInstance().getValues().nameProtectAlias) {
            this.invoker.setGameProfile(new GameProfile("", Resilience.getInstance().getValues().nameProtectAlias.getValue()), this.entity);
        }
    }

    @Override
    public void onDisable() {
        try {
            Resilience.getInstance().getValues().freecamEnabled = false;
            Resilience.getInstance().getEventManager().unregister(this);
            this.invoker.removeEntityFromWorld(69);
            this.invoker.setPositionAndRotation(Resilience.getInstance().getWrapper().getPlayer(), this.freecamX, this.freecamY, this.freecamZ, (float)this.freecamYaw, (float)this.freecamPitch);
            this.invoker.setNoClip(false);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        if (this.entity != null) {
            if (Resilience.getInstance().getValues().flightEnabled) {
                this.invoker.setNoClip(true);
                this.invoker.setOnGround(false);
            } else {
                this.invoker.setNoClip(false);
            }
        }
    }
}

