/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui.mco;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate;
import net.minecraft.client.gui.mco.ScreenWithCallback;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenCreateOnlineWorld
extends ScreenWithCallback {
    private static final Logger logger = LogManager.getLogger();
    private GuiScreen field_146758_f;
    private GuiTextField field_146760_g;
    private String field_146766_h;
    private static int field_146767_i = 0;
    private static int field_146764_r = 1;
    private static int field_146763_s = 2;
    private boolean field_146762_t;
    private String field_146761_u = "You must enter a name!";
    private WorldTemplate field_146759_v;
    private static final String __OBFID = "CL_00000776";

    public GuiScreenCreateOnlineWorld(GuiScreen par1GuiScreen) {
        this.buttonList = Collections.synchronizedList(new ArrayList());
        this.field_146758_f = par1GuiScreen;
    }

    @Override
    public void updateScreen() {
        this.field_146760_g.updateCursorCounter();
        this.field_146766_h = this.field_146760_g.getText();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(field_146767_i, this.width / 2 - 100, this.height / 4 + 120 + 17, 97, 20, I18n.format("mco.create.world", new Object[0])));
        this.buttonList.add(new GuiButton(field_146764_r, this.width / 2 + 5, this.height / 4 + 120 + 17, 95, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_146760_g = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 65, 200, 20);
        this.field_146760_g.setFocused(true);
        if (this.field_146766_h != null) {
            this.field_146760_g.setText(this.field_146766_h);
        }
        if (this.field_146759_v == null) {
            this.buttonList.add(new GuiButton(field_146763_s, this.width / 2 - 100, 107, 200, 20, I18n.format("mco.template.default.name", new Object[0])));
        } else {
            this.buttonList.add(new GuiButton(field_146763_s, this.width / 2 - 100, 107, 200, 20, String.valueOf(I18n.format("mco.template.name", new Object[0])) + ": " + this.field_146759_v.field_148785_b));
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == field_146764_r) {
                this.mc.displayGuiScreen(this.field_146758_f);
            } else if (p_146284_1_.id == field_146767_i) {
                this.func_146757_h();
            } else if (p_146284_1_.id == field_146763_s) {
                this.mc.displayGuiScreen(new GuiScreenMcoWorldTemplate(this, this.field_146759_v));
            }
        }
    }

    private void func_146757_h() {
        if (this.func_146753_i()) {
            TaskWorldCreation var1 = new TaskWorldCreation(this.field_146760_g.getText(), this.field_146759_v);
            GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.mc, this.field_146758_f, var1);
            var2.func_146902_g();
            this.mc.displayGuiScreen(var2);
        }
    }

    private boolean func_146753_i() {
        this.field_146762_t = this.field_146760_g.getText() == null || this.field_146760_g.getText().trim().equals("");
        return !this.field_146762_t;
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.field_146760_g.textboxKeyTyped(par1, par2);
        if (par2 == 15) {
            this.field_146760_g.setFocused(!this.field_146760_g.isFocused());
        }
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_146760_g.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.selectServer.create", new Object[0]), this.width / 2, 11, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.name", new Object[0]), this.width / 2 - 100, 52, 10526880);
        if (this.field_146762_t) {
            this.drawCenteredString(this.fontRendererObj, this.field_146761_u, this.width / 2, 167, 16711680);
        }
        this.field_146760_g.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    public void func_146735_a(WorldTemplate p_146756_1_) {
        this.field_146759_v = p_146756_1_;
    }

    @Override
    public void func_146735_a(Object p_146735_1_) {
        this.func_146735_a((WorldTemplate)p_146735_1_);
    }

    class TaskWorldCreation
    extends TaskLongRunning {
        private final String field_148427_c;
        private final WorldTemplate field_148426_d;
        private static final String __OBFID = "CL_00000777";

        public TaskWorldCreation(String p_i45036_2_, WorldTemplate p_i45036_3_) {
            this.field_148427_c = p_i45036_2_;
            this.field_148426_d = p_i45036_3_;
        }

        @Override
        public void run() {
            String var1 = I18n.format("mco.create.world.wait", new Object[0]);
            this.func_148417_b(var1);
            Session var2 = GuiScreenCreateOnlineWorld.this.mc.getSession();
            McoClient var3 = new McoClient(var2.getSessionID(), var2.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
            try {
                if (this.field_148426_d != null) {
                    var3.func_148707_a(this.field_148427_c, this.field_148426_d.field_148787_a);
                } else {
                    var3.func_148707_a(this.field_148427_c, "-1");
                }
                GuiScreenCreateOnlineWorld.this.mc.displayGuiScreen(GuiScreenCreateOnlineWorld.this.field_146758_f);
            }
            catch (ExceptionMcoService var5) {
                logger.error("Couldn't create world");
                this.func_148416_a(var5.toString());
            }
            catch (UnsupportedEncodingException var6) {
                logger.error("Couldn't create world");
                this.func_148416_a(var6.getLocalizedMessage());
            }
            catch (Exception var8) {
                logger.error("Could not create world");
                this.func_148416_a(var8.getLocalizedMessage());
            }
        }
    }

}

