/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.GuiAnimationSettingsOF;
import net.minecraft.src.GuiDetailSettingsOF;
import net.minecraft.src.GuiOtherSettingsOF;
import net.minecraft.src.GuiPerformanceSettingsOF;
import net.minecraft.src.GuiQualitySettingsOF;

public class GuiVideoSettings
extends GuiScreen {
    private GuiScreen field_146498_f;
    protected String field_146500_a = "Video Settings";
    private GameSettings field_146499_g;
    private boolean is64bit;
    private static GameSettings.Options[] field_146502_i = new GameSettings.Options[]{GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.ADVANCED_OPENGL, GameSettings.Options.GAMMA, GameSettings.Options.CHUNK_LOADING, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START, GameSettings.Options.USE_SERVER_TEXTURES};
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0;
    private static final String __OBFID = "CL_00000718";

    public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
        this.field_146498_f = par1GuiScreen;
        this.field_146499_g = par2GameSettings;
    }

    @Override
    public void initGui() {
        String[] var1;
        int x;
        this.field_146500_a = I18n.format("options.videoTitle", new Object[0]);
        this.buttonList.clear();
        this.is64bit = false;
        String[] var2 = var1 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        int var3 = var1.length;
        int var8 = 0;
        while (var8 < var3) {
            String var9 = var2[var8];
            String var10 = System.getProperty(var9);
            if (var10 != null && var10.contains("64")) {
                this.is64bit = true;
                break;
            }
            ++var8;
        }
        boolean var12 = false;
        boolean var111 = !this.is64bit;
        GameSettings.Options[] var13 = field_146502_i;
        int var14 = var13.length;
        boolean var11 = false;
        int var15 = 0;
        while (var15 < var14) {
            GameSettings.Options y = var13[var15];
            x = this.width / 2 - 155 + var15 % 2 * 160;
            int y1 = this.height / 6 + 21 * (var15 / 2) - 10;
            if (y.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(y.returnEnumOrdinal(), x, y1, y));
            } else {
                this.buttonList.add(new GuiOptionButton(y.returnEnumOrdinal(), x, y1, y, this.field_146499_g.getKeyBinding(y)));
            }
            ++var15;
        }
        int var16 = this.height / 6 + 21 * (var15 / 2) - 10;
        boolean var17 = false;
        x = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(202, x, var16, "Quality..."));
        x = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(201, x, var16 += 21, "Details..."));
        x = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(212, x, var16, "Performance..."));
        x = this.width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(211, x, var16 += 21, "Animations..."));
        x = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(222, x, var16, "Other..."));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            int var2 = this.field_146499_g.guiScale;
            if (par1GuiButton.id < 200 && par1GuiButton instanceof GuiOptionButton) {
                this.field_146499_g.setOptionValue(((GuiOptionButton)par1GuiButton).func_146136_c(), 1);
                par1GuiButton.displayString = this.field_146499_g.getKeyBinding(GameSettings.Options.getEnumOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146498_f);
            }
            if (this.field_146499_g.guiScale != var2) {
                ScaledResolution scr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                int var4 = scr.getScaledWidth();
                int var5 = scr.getScaledHeight();
                this.setWorldAndResolution(this.mc, var4, var5);
            }
            if (par1GuiButton.id == 201) {
                this.mc.gameSettings.saveOptions();
                GuiDetailSettingsOF scr1 = new GuiDetailSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr1);
            }
            if (par1GuiButton.id == 202) {
                this.mc.gameSettings.saveOptions();
                GuiQualitySettingsOF scr2 = new GuiQualitySettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr2);
            }
            if (par1GuiButton.id == 211) {
                this.mc.gameSettings.saveOptions();
                GuiAnimationSettingsOF scr3 = new GuiAnimationSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr3);
            }
            if (par1GuiButton.id == 212) {
                this.mc.gameSettings.saveOptions();
                GuiPerformanceSettingsOF scr4 = new GuiPerformanceSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr4);
            }
            if (par1GuiButton.id == 222) {
                this.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF scr5 = new GuiOtherSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr5);
            }
            if (par1GuiButton.id == GameSettings.Options.AO_LEVEL.ordinal()) {
                return;
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float z) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146500_a, this.width / 2, this.is64bit ? 20 : 5, 16777215);
        if (this.is64bit || this.field_146499_g.renderDistanceChunks > 8) {
            // empty if block
        }
        super.drawScreen(x, y, z);
        if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
            int activateDelay = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + (long)activateDelay) {
                int x1 = this.width / 2 - 150;
                int y1 = this.height / 6 - 5;
                if (y <= y1 + 98) {
                    y1 += 105;
                }
                int x2 = x1 + 150 + 150;
                int y2 = y1 + 84 + 10;
                GuiButton btn = this.getSelectedButton(x, y);
                if (btn != null) {
                    String s = this.getButtonName(btn.displayString);
                    String[] lines = this.getTooltipLines(s);
                    if (lines == null) {
                        return;
                    }
                    this.drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
                    int i = 0;
                    while (i < lines.length) {
                        String line = lines[i];
                        this.fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11, 14540253);
                        ++i;
                    }
                }
            }
        } else {
            this.lastMouseX = x;
            this.lastMouseY = y;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private String[] getTooltipLines(String btnName) {
        String[] arrstring;
        if (btnName.equals("Graphics")) {
            String[] arrstring2 = new String[5];
            arrstring2[0] = "Visual quality";
            arrstring2[1] = "  Fast  - lower quality, faster";
            arrstring2[2] = "  Fancy - higher quality, slower";
            arrstring2[3] = "Changes the appearance of clouds, leaves, water,";
            arrstring = arrstring2;
            arrstring2[4] = "shadows and grass sides.";
        } else if (btnName.equals("Render Distance")) {
            String[] arrstring3 = new String[8];
            arrstring3[0] = "Visible distance";
            arrstring3[1] = "  2 Tiny - 32m (fastest)";
            arrstring3[2] = "  4 Short - 64m (faster)";
            arrstring3[3] = "  8 Normal - 128m";
            arrstring3[4] = "  16 Far - 256m (slower)";
            arrstring3[5] = "  32 Extreme - 512m (slowest!)";
            arrstring3[6] = "The Extreme view distance is very resource demanding!";
            arrstring = arrstring3;
            arrstring3[7] = "Values over 16 Far are only effective in local worlds.";
        } else if (btnName.equals("Smooth Lighting")) {
            String[] arrstring4 = new String[4];
            arrstring4[0] = "Smooth lighting";
            arrstring4[1] = "  OFF - no smooth lighting (faster)";
            arrstring4[2] = "  Minimum - simple smooth lighting (slower)";
            arrstring = arrstring4;
            arrstring4[3] = "  Maximum - complex smooth lighting (slowest)";
        } else if (btnName.equals("Smooth Lighting Level")) {
            String[] arrstring5 = new String[4];
            arrstring5[0] = "Smooth lighting level";
            arrstring5[1] = "  OFF - no smooth lighting (faster)";
            arrstring5[2] = "  1% - light smooth lighting (slower)";
            arrstring = arrstring5;
            arrstring5[3] = "  100% - dark smooth lighting (slower)";
        } else if (btnName.equals("Max Framerate")) {
            String[] arrstring6 = new String[6];
            arrstring6[0] = "Max framerate";
            arrstring6[1] = "  VSync - limit to monitor framerate (60, 30, 20)";
            arrstring6[2] = "  5-255 - variable";
            arrstring6[3] = "  Unlimited - no limit (fastest)";
            arrstring6[4] = "The framerate limit decreases the FPS even if";
            arrstring = arrstring6;
            arrstring6[5] = "the limit value is not reached.";
        } else if (btnName.equals("View Bobbing")) {
            String[] arrstring7 = new String[2];
            arrstring7[0] = "More realistic movement.";
            arrstring = arrstring7;
            arrstring7[1] = "When using mipmaps set it to OFF for best results.";
        } else if (btnName.equals("GUI Scale")) {
            String[] arrstring8 = new String[2];
            arrstring8[0] = "GUI Scale";
            arrstring = arrstring8;
            arrstring8[1] = "Smaller GUI might be faster";
        } else if (btnName.equals("Server Textures")) {
            String[] arrstring9 = new String[2];
            arrstring9[0] = "Server textures";
            arrstring = arrstring9;
            arrstring9[1] = "Use the resource pack recommended by the server";
        } else if (btnName.equals("Advanced OpenGL")) {
            String[] arrstring10 = new String[6];
            arrstring10[0] = "Detect and render only visible geometry";
            arrstring10[1] = "  OFF - all geometry is rendered (slower)";
            arrstring10[2] = "  Fast - only visible geometry is rendered (fastest)";
            arrstring10[3] = "  Fancy - conservative, avoids visual artifacts (faster)";
            arrstring10[4] = "The option is available only if it is supported by the ";
            arrstring = arrstring10;
            arrstring10[5] = "graphic card.";
        } else if (btnName.equals("Fog")) {
            String[] arrstring11 = new String[6];
            arrstring11[0] = "Fog type";
            arrstring11[1] = "  Fast - faster fog";
            arrstring11[2] = "  Fancy - slower fog, looks better";
            arrstring11[3] = "  OFF - no fog, fastest";
            arrstring11[4] = "The fancy fog is available only if it is supported by the ";
            arrstring = arrstring11;
            arrstring11[5] = "graphic card.";
        } else if (btnName.equals("Fog Start")) {
            String[] arrstring12 = new String[4];
            arrstring12[0] = "Fog start";
            arrstring12[1] = "  0.2 - the fog starts near the player";
            arrstring12[2] = "  0.8 - the fog starts far from the player";
            arrstring = arrstring12;
            arrstring12[3] = "This option usually does not affect the performance.";
        } else if (btnName.equals("Brightness")) {
            String[] arrstring13 = new String[5];
            arrstring13[0] = "Increases the brightness of darker objects";
            arrstring13[1] = "  OFF - standard brightness";
            arrstring13[2] = "  100% - maximum brightness for darker objects";
            arrstring13[3] = "This options does not change the brightness of ";
            arrstring = arrstring13;
            arrstring13[4] = "fully black objects";
        } else if (btnName.equals("Chunk Loading")) {
            String[] arrstring14 = new String[8];
            arrstring14[0] = "Chunk Loading";
            arrstring14[1] = "  Default - unstable FPS when loading chunks";
            arrstring14[2] = "  Smooth - stable FPS";
            arrstring14[3] = "  Multi-Core - stable FPS, 3x faster world loading";
            arrstring14[4] = "Smooth and Multi-Core remove the stuttering and ";
            arrstring14[5] = "freezes caused by chunk loading.";
            arrstring14[6] = "Multi-Core can speed up 3x the world loading and";
            arrstring = arrstring14;
            arrstring14[7] = "increase FPS by using a second CPU core.";
        } else {
            arrstring = null;
        }
        return arrstring;
    }

    private String getButtonName(String displayString) {
        int pos = displayString.indexOf(58);
        return pos < 0 ? displayString : displayString.substring(0, pos);
    }

    private GuiButton getSelectedButton(int i, int j) {
        int k = 0;
        while (k < this.buttonList.size()) {
            boolean flag;
            GuiButton btn = (GuiButton)this.buttonList.get(k);
            boolean bl = flag = i >= btn.field_146128_h && j >= btn.field_146129_i && i < btn.field_146128_h + btn.field_146120_f && j < btn.field_146129_i + btn.field_146121_g;
            if (flag) {
                return btn;
            }
            ++k;
        }
        return null;
    }

    public static int getButtonWidth(GuiButton btn) {
        return btn.field_146120_f;
    }

    public static int getButtonHeight(GuiButton btn) {
        return btn.field_146121_g;
    }
}

