/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.buttons.CheckBox;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiFirstTime
extends GuiScreen {
    private CheckBox loadEnabledMods;
    private CheckBox loadGuiSettings;
    private CheckBox loadKeybinds;
    private CheckBox loadXrayBlocks;
    private CheckBox loadFriends;
    private CheckBox loadEnemies;
    private CheckBox loadConfigs;
    private CheckBox loadWaypoints;
    private CheckBox loadMacros;
    private CheckBox useGlobalCustomButtons;

    @Override
    public void initGui() {
        CheckBox.checkBoxList.clear();
        this.buttonList.clear();
        this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth() - 58, Resilience.getInstance().getInvoker().getHeight() - 28, 50.0f, 20.0f, "Done"));
        this.loadEnabledMods = new CheckBox(8.0f, 64.0f, Resilience.getInstance().getFileManager().loadEnabledMods);
        this.loadGuiSettings = new CheckBox(8.0f, 78.0f, Resilience.getInstance().getFileManager().loadGui);
        this.loadKeybinds = new CheckBox(8.0f, 92.0f, Resilience.getInstance().getFileManager().loadKeybinds);
        this.loadXrayBlocks = new CheckBox(8.0f, 106.0f, Resilience.getInstance().getFileManager().loadXrayBlocks);
        this.loadFriends = new CheckBox(8.0f, 120.0f, Resilience.getInstance().getFileManager().loadFriends);
        this.loadEnemies = new CheckBox(8.0f, 134.0f, Resilience.getInstance().getFileManager().loadEnemies);
        this.loadConfigs = new CheckBox(8.0f, 148.0f, Resilience.getInstance().getFileManager().loadConfigs);
        this.loadWaypoints = new CheckBox(8.0f, 162.0f, Resilience.getInstance().getFileManager().loadWaypoints);
        this.loadMacros = new CheckBox(8.0f, 176.0f, Resilience.getInstance().getFileManager().loadMacros);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GuiFirstTime.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), -14671840);
        for (CheckBox box : CheckBox.checkBoxList) {
            box.draw(i, j);
        }
        Resilience.getInstance().getPanelTitleFont().drawCenteredString("Welcome to Resilience!", Resilience.getInstance().getInvoker().getWidth() / 2, 8.0f, -16755201);
        Resilience.getInstance().getStandardFont().drawCenteredString("Since this is your first time, please configure your client.", Resilience.getInstance().getInvoker().getWidth() / 2, 26.0f, -1);
        Resilience.getInstance().getStandardFont().drawCenteredString("You can change any of these settings in the client options at any time.", Resilience.getInstance().getInvoker().getWidth() / 2, 36.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Enabled Mods", 20.0f, 62.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load GUI Settings", 20.0f, 76.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Keybinds", 20.0f, 90.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Xray Blocks", 20.0f, 104.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Friends", 20.0f, 118.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Enemies", 20.0f, 132.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Configs", 20.0f, 146.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Waypoints", 20.0f, 160.0f, -1);
        Resilience.getInstance().getStandardFont().drawString("Load Macros", 20.0f, 174.0f, -1);
        super.drawScreen(i, j, f);
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        this.mc.displayGuiScreen(new HookGuiMainMenu());
    }

    @Override
    public void mouseClicked(int i, int j, int k) {
        for (CheckBox box : CheckBox.checkBoxList) {
            box.clicked(i, j);
        }
        Resilience.getInstance().getFileManager().loadEnabledMods = this.loadEnabledMods.isChecked();
        Resilience.getInstance().getFileManager().loadGui = this.loadGuiSettings.isChecked();
        Resilience.getInstance().getFileManager().loadKeybinds = this.loadKeybinds.isChecked();
        Resilience.getInstance().getFileManager().loadXrayBlocks = this.loadXrayBlocks.isChecked();
        Resilience.getInstance().getFileManager().loadFriends = this.loadFriends.isChecked();
        Resilience.getInstance().getFileManager().loadEnemies = this.loadEnemies.isChecked();
        Resilience.getInstance().getFileManager().loadConfigs = this.loadConfigs.isChecked();
        Resilience.getInstance().getFileManager().loadWaypoints = this.loadWaypoints.isChecked();
        Resilience.getInstance().getFileManager().saveConfigs(new String[0]);
        super.mouseClicked(i, j, k);
    }
}

