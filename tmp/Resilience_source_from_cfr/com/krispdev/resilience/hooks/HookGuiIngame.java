/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.krispdev.resilience.hooks;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.screens.ValuePanel;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class HookGuiIngame
extends GuiIngame {
    private int arrayListCount = 0;
    private boolean go = true;
    public boolean display = false;
    private EntityPlayer toView;
    private MethodInvoker invoker = Resilience.getInstance().getInvoker();
    private Wrapper wrapper = Resilience.getInstance().getWrapper();
    private boolean once = true;
    private int ticks = 0;

    public HookGuiIngame(Minecraft minecraft) {
        super(minecraft);
    }

    @Override
    public void renderGameOverlay(float par1, boolean par2, int par3, int par4) {
        boolean wasExtended = false;
        ++this.ticks;
        if (this.ticks == 5) {
            wasExtended = Resilience.getInstance().getClickGui().values.isExtended();
            Resilience.getInstance().getClickGui().values.setExtended(true);
            Resilience.getInstance().getModuleManager().setModuleState("GUI", true);
        }
        if (this.ticks == 20) {
            Resilience.getInstance().getClickGui().values.setExtended(wasExtended);
        }
        if (this.once && !Resilience.getInstance().isFirstTime()) {
            this.once = false;
            Resilience.getInstance().getLogger().infoChat("Want to know how to chat in the IRC? Put the \"@\" sign before your message!");
        }
        if (this.display) {
            this.invoker.displayScreen(new GuiInventory(this.toView));
            this.display = false;
        }
        if (this.go) {
            if (Resilience.getInstance().isFirstTime()) {
                Resilience.getInstance().getLogger().infoChat("Welcome to " + Resilience.getInstance().getName() + "!");
                Resilience.getInstance().getLogger().infoChat("To open the GUI hit the \"Right Shift\" key, and to open the console hit the \"Minus\" key (\"-\").");
                Resilience.getInstance().getLogger().infoChat("Remember to right click a button to get tons of great options :D");
                Resilience.getInstance().getLogger().infoChat("To chat in the IRC, type \"@\" before the message.");
                Resilience.getInstance().getLogger().infoChat("Enjoy! - Krisp");
            }
            this.go = false;
        }
        int prevArrayListCount = this.arrayListCount;
        this.arrayListCount = 0;
        ScaledResolution var5 = new ScaledResolution(this.wrapper.getGameSettings(), this.invoker.getDisplayWidth(), this.invoker.getDisplayHeight());
        int var6 = this.invoker.getScaledWidth(var5);
        int var7 = this.invoker.getScaledHeight(var5);
        FontRenderer var8 = this.wrapper.getFontRenderer();
        this.invoker.setupOverlayRendering();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2896);
        if (Resilience.getInstance().isEnabled()) {
            for (DefaultPanel panel : ClickGui.windows) {
                if (!panel.isPinned() || this.invoker.getCurrentScreen() instanceof ClickGui) continue;
                GL11.glPushMatrix();
                GL11.glDisable((int)2896);
                panel.draw(-1, -1);
                GL11.glPopMatrix();
            }
            int width = 0;
            if (Resilience.getInstance().getValues().enabledModsEnabled) {
                for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
                    int realWidth;
                    if (!mod.isEnabled() || mod.getCategory() == ModuleCategory.GUI || !mod.isVisible() || (realWidth = (int)Resilience.getInstance().getModListFont().getWidth(mod.getDisplayName())) <= width) continue;
                    width = realWidth + 5;
                }
            }
            if (Resilience.getInstance().getValues().enabledModsEnabled) {
                GL11.glPushMatrix();
                GL11.glDisable((int)2896);
                if (prevArrayListCount > 0) {
                    HookGuiIngame.drawRect(0, 0, width, prevArrayListCount * 12 + 4, -2013265920);
                }
                GL11.glPopMatrix();
            }
            if (Resilience.getInstance().getValues().enabledModsEnabled) {
                for (DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList) {
                    if (!mod.isEnabled() || mod.getCategory() == ModuleCategory.GUI || !mod.isVisible()) continue;
                    GL11.glPushMatrix();
                    GL11.glDisable((int)2896);
                    Resilience.getInstance().getModListFont().drawString(mod.getDisplayName(), 2.0f, this.arrayListCount * 12 + 2, -6638593);
                    GL11.glPopMatrix();
                    ++this.arrayListCount;
                }
            }
            if (Resilience.getInstance().getValues().noFireEffectEnabled && this.invoker.isBurning()) {
                Resilience.getInstance().getWrapper().getFontRenderer().drawStringWithShadow("You're on fire", this.invoker.getDisplayWidth() / 2 - Resilience.getInstance().getWrapper().getFontRenderer().getStringWidth("You're on fire") - 4, this.invoker.getDisplayHeight() / 2 - Resilience.getInstance().getWrapper().getFontRenderer().FONT_HEIGHT - 4, -65536);
            }
            if (Resilience.getInstance().getValues().potionEffectsEnabled) {
                this.renderPotions();
            }
        }
        super.renderGameOverlay(par1, par2, par3, par4);
    }

    private void renderPotions() {
        GL11.glPushMatrix();
        int var1 = Resilience.getInstance().getValues().enabledModsEnabled ? 76 : 1;
        int var2 = 0;
        boolean var3 = true;
        Collection var4 = this.wrapper.getPlayer().getActivePotionEffects();
        if (!var4.isEmpty()) {
            ResourceLocation rL = new ResourceLocation("textures/gui/container/inventory.png");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)2896);
            int var5 = 33;
            if (var4.size() > 5) {
                var5 = 132 / (var4.size() - 1);
            }
            for (PotionEffect var7 : this.wrapper.getPlayer().getActivePotionEffects()) {
                Potion var8 = Potion.potionTypes[var7.getPotionID()];
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.wrapper.getMinecraft().getTextureManager().bindTexture(rL);
                if (var8.hasStatusIcon()) {
                    int var9 = var8.getStatusIconIndex();
                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }
                String var11 = I18n.format(var8.getName(), new Object[0]);
                if (var7.getAmplifier() == 1) {
                    var11 = String.valueOf(var11) + " II";
                } else if (var7.getAmplifier() == 2) {
                    var11 = String.valueOf(var11) + " III";
                } else if (var7.getAmplifier() == 3) {
                    var11 = String.valueOf(var11) + " IV";
                }
                this.wrapper.getFontRenderer().drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
                String var10 = Potion.getDurationString(var7);
                this.wrapper.getFontRenderer().drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
                var2 += var5;
            }
        }
        GL11.glPopMatrix();
    }

    public void displayInv(EntityPlayer e) {
        this.toView = e;
        this.display = true;
    }
}

