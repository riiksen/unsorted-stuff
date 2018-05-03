/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.gui.objects;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.objects.screens.ArmourStatusPanel;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.screens.GuiRadarPanel;
import com.krispdev.resilience.gui.objects.screens.InfoPanel;
import com.krispdev.resilience.gui.objects.screens.ModulePanel;
import com.krispdev.resilience.gui.objects.screens.TextRadarPanel;
import com.krispdev.resilience.gui.objects.screens.ValuePanel;
import com.krispdev.resilience.gui.objects.screens.XrayBlocksPanel;
import com.krispdev.resilience.gui.screens.IngameMenu;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui
extends GuiScreen {
    public final ModulePanel world = new ModulePanel("World", 120, 4, 228, 150, true, ModuleCategory.WORLD);
    public final ModulePanel combat = new ModulePanel("Combat", 4, 23, 114, 34, true, ModuleCategory.COMBAT);
    public final ModulePanel player = new ModulePanel("Player", 120, 42, 228, 51, true, ModuleCategory.PLAYER);
    public final ModulePanel guiPanel = new ModulePanel("GUI", 4, 61, 114, 68, true, ModuleCategory.GUI);
    public final ModulePanel movement = new ModulePanel("Movement", 4, 4, 114, 17, true, ModuleCategory.MOVEMENT);
    public final ModulePanel misc = new ModulePanel("Misc", 120, 23, 228, 34, true, ModuleCategory.MISC);
    public final ModulePanel render = new ModulePanel("Render", 4, 42, 114, 51, true, ModuleCategory.RENDER);
    public final ValuePanel values;
    public final TextRadarPanel textRadar;
    public final GuiRadarPanel guiRadar;
    public final ArmourStatusPanel armorStatus;
    public final ModulePanel combatOptions;
    public final InfoPanel info;
    public final XrayBlocksPanel xrayPanel;
    public static boolean hasRightClicked = false;
    public static boolean hasHovered = false;
    public static boolean hasOpened = false;
    public static boolean hasPinned = false;
    public static List<DefaultPanel> windows = new ArrayList<DefaultPanel>();

    public ClickGui() {
        this.values = new ValuePanel("Mod Values", 120, 61, 228, 68, true, Resilience.getInstance().getValues().numValues.toArray(new NumberValue[Resilience.getInstance().getValues().numValues.size()]));
        this.textRadar = new TextRadarPanel("Text Radar", 4, 80, 114, 85, true);
        this.guiRadar = new GuiRadarPanel("Gui Radar", 120, 80, 253, 85, true);
        this.armorStatus = new ArmourStatusPanel("Armor Status", 4, 99, 114, 102, true);
        this.combatOptions = new ModulePanel("Combat [More]", 120, 99, 228, 102, true, ModuleCategory.COMBAT_EXTENSION);
        this.info = new InfoPanel("Player Info", 4, 118, 114, 119, true);
        this.xrayPanel = new XrayBlocksPanel("Xray Blocks", 120, 118, 358, 119, true);
        Resilience.getInstance().getFileManager().loadGui();
    }

    public void focusWindow(DefaultPanel panel) {
        if (windows.contains(panel)) {
            windows.remove(windows.indexOf(panel));
            windows.add(windows.size(), panel);
            for (DefaultPanel window : windows) {
                window.setFocused(false);
            }
            panel.setFocused(true);
        }
    }

    @Override
    public void initGui() {
        Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, 4.0f, Resilience.getInstance().getInvoker().getHeight() - 24, 100.0f, 20.0f, "Resilience Menu"));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        for (DefaultPanel panel : windows) {
            panel.draw(i, j);
        }
        if (!hasRightClicked && Resilience.getInstance().isFirstTime()) {
            Utils.drawBetterRect(this.getWidth() / 2 - 125, this.getHeight() / 2 - 50, this.getWidth() / 2 + 125, this.getHeight() / 2 + 50, -587136767, -586939388);
            Resilience.getInstance().getPanelTitleFont().drawCenteredString("\u00a7bTutorial [1/2]", this.getWidth() / 2, this.getHeight() / 2 - 45, -1);
            Resilience.getInstance().getStandardFont().drawCenteredString("\u00a7bWelcome to Resilience!", this.getWidth() / 2, this.getHeight() / 2 - 25, -1);
            Resilience.getInstance().getStandardFont().drawCenteredString(String.valueOf(hasOpened ? "\u00a77" : "\u00a7b") + "To open a GUI panel hit the top right box.", this.getWidth() / 2, this.getHeight() / 2 - 13, -1);
            Resilience.getInstance().getStandardFont().drawCenteredString(String.valueOf(hasPinned ? "\u00a77" : "\u00a7b") + "To \"pin\" (so it's seen ingame) hit the top left box.", this.getWidth() / 2, this.getHeight() / 2 - 1, -1);
            Resilience.getInstance().getStandardFont().drawCenteredString("\u00a7cTo continue:", this.getWidth() / 2, this.getHeight() / 2 - 1 + 12, -1);
            Resilience.getInstance().getStandardFont().drawCenteredString("\u00a7bRight click a button to get info & customization on the mod!", this.getWidth() / 2, this.getHeight() / 2 - 1 + 24, -1);
        }
        super.drawScreen(i, j, f);
    }

    @Override
    public void mouseClicked(int i, int j, int k) {
        try {
            super.mouseClicked(i, j, k);
            for (DefaultPanel panel : windows) {
                if (!panel.onClick(i, j, k)) {
                    continue;
                }
                break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(GuiButton btn) {
        if (Resilience.getInstance().getInvoker().getId(btn) == 1) {
            Resilience.getInstance().getInvoker().displayScreen(new IngameMenu(this));
        }
    }

    @Override
    public void mouseMovedOrUp(int i, int j, int k) {
        for (DefaultPanel panel : windows) {
            panel.onMouseButtonUp(i, j, k);
        }
        Resilience.getInstance().getFileManager().saveGui(new String[0]);
        super.mouseMovedOrUp(i, j, k);
    }

    @Override
    public void keyTyped(char c, int i) {
        for (DefaultPanel panel : windows) {
            panel.keyTyped(c, i);
        }
        super.keyTyped(c, i);
    }

    @Override
    public void onGuiClosed() {
        Resilience.getInstance().getModuleManager().setModuleState("Gui", false);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

