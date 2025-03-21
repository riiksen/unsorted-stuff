/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.wrappers;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import com.krispdev.resilience.hooks.HookGuiIngame;
import com.krispdev.resilience.hooks.HookPlayerControllerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;

public class Wrapper {
    public Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public FontRenderer getFontRenderer() {
        return this.getMinecraft().fontRenderer;
    }

    public PlayerControllerMP getPlayerController() {
        return this.getMinecraft().playerController;
    }

    public EntityClientPlayerMP getPlayer() {
        return this.getMinecraft().thePlayer;
    }

    public WorldClient getWorld() {
        return this.getMinecraft().theWorld;
    }

    public GameSettings getGameSettings() {
        return this.getMinecraft().gameSettings;
    }

    public TextureManager getRenderEngine() {
        return this.getMinecraft().renderEngine;
    }

    public Session getSession() {
        return this.getMinecraft().session;
    }

    public EntityRenderer getEntityRenderer() {
        return this.getMinecraft().entityRenderer;
    }

    public HookGuiIngame getInGameGui() {
        return this.getMinecraft().ingameGUI;
    }
}

