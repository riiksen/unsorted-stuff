/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui.mco;

import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.mco.ScreenWithCallback;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.mco.WorldTemplateList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenMcoWorldTemplate
extends GuiScreen {
    private static final AtomicInteger field_146958_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private final ScreenWithCallback field_146954_g;
    private WorldTemplate field_146959_h;
    private List field_146960_i = Collections.emptyList();
    private SelectionList field_146957_r;
    private int field_146956_s = -1;
    private GuiButton field_146955_t;
    private static final String __OBFID = "CL_00000786";

    public GuiScreenMcoWorldTemplate(ScreenWithCallback par1ScreenWithCallback, WorldTemplate par2WorldTemplate) {
        this.field_146954_g = par1ScreenWithCallback;
        this.field_146959_h = par2WorldTemplate;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.field_146957_r = new SelectionList();
        new Thread("MCO World Creator #" + field_146958_a.incrementAndGet()){
            private static final String __OBFID = "CL_00000787";

            @Override
            public void run() {
                Session var1 = GuiScreenMcoWorldTemplate.this.mc.getSession();
                McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
                try {
                    GuiScreenMcoWorldTemplate.access$7(GuiScreenMcoWorldTemplate.this, var2.func_148693_e().field_148782_a);
                }
                catch (ExceptionMcoService var4) {
                    logger.error("Couldn't fetch templates");
                }
            }
        }.start();
        this.func_146952_h();
    }

    private void func_146952_h() {
        this.buttonList.add(new GuiButton(0, this.width / 2 + 6, this.height - 52, 153, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_146955_t = new GuiButton(1, this.width / 2 - 154, this.height - 52, 153, 20, I18n.format("mco.template.button.select", new Object[0]));
        this.buttonList.add(this.field_146955_t);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 1) {
                this.func_146947_i();
            } else if (p_146284_1_.id == 0) {
                this.field_146954_g.func_146735_a(null);
                this.mc.displayGuiScreen(this.field_146954_g);
            } else {
                this.field_146957_r.func_148357_a(p_146284_1_);
            }
        }
    }

    private void func_146947_i() {
        if (this.field_146956_s >= 0 && this.field_146956_s < this.field_146960_i.size()) {
            this.field_146954_g.func_146735_a(this.field_146960_i.get(this.field_146956_s));
            this.mc.displayGuiScreen(this.field_146954_g);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.field_146957_r.func_148350_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.template.title", new Object[0]), this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    static /* synthetic */ void access$2(GuiScreenMcoWorldTemplate guiScreenMcoWorldTemplate, int n) {
        guiScreenMcoWorldTemplate.field_146956_s = n;
    }

    static /* synthetic */ void access$3(GuiScreenMcoWorldTemplate guiScreenMcoWorldTemplate, WorldTemplate worldTemplate) {
        guiScreenMcoWorldTemplate.field_146959_h = worldTemplate;
    }

    static /* synthetic */ void access$7(GuiScreenMcoWorldTemplate guiScreenMcoWorldTemplate, List list) {
        guiScreenMcoWorldTemplate.field_146960_i = list;
    }

    class SelectionList
    extends GuiScreenSelectLocation {
        private static final String __OBFID = "CL_00000788";

        public SelectionList() {
            super(GuiScreenMcoWorldTemplate.this.mc, GuiScreenMcoWorldTemplate.this.width, GuiScreenMcoWorldTemplate.this.height, 32, GuiScreenMcoWorldTemplate.this.height - 64, 36);
        }

        @Override
        protected int func_148355_a() {
            return GuiScreenMcoWorldTemplate.this.field_146960_i.size() + 1;
        }

        @Override
        protected void func_148352_a(int p_148352_1_, boolean p_148352_2_) {
            if (p_148352_1_ < GuiScreenMcoWorldTemplate.this.field_146960_i.size()) {
                GuiScreenMcoWorldTemplate.access$2(GuiScreenMcoWorldTemplate.this, p_148352_1_);
                GuiScreenMcoWorldTemplate.access$3(GuiScreenMcoWorldTemplate.this, null);
            }
        }

        @Override
        protected boolean func_148356_a(int p_148356_1_) {
            return GuiScreenMcoWorldTemplate.this.field_146960_i.size() == 0 ? false : (p_148356_1_ >= GuiScreenMcoWorldTemplate.this.field_146960_i.size() ? false : (GuiScreenMcoWorldTemplate.this.field_146959_h != null ? GuiScreenMcoWorldTemplate.access$4((GuiScreenMcoWorldTemplate)GuiScreenMcoWorldTemplate.this).field_148785_b.equals(((WorldTemplate)GuiScreenMcoWorldTemplate.access$1((GuiScreenMcoWorldTemplate)GuiScreenMcoWorldTemplate.this).get((int)p_148356_1_)).field_148785_b) : p_148356_1_ == GuiScreenMcoWorldTemplate.this.field_146956_s));
        }

        @Override
        protected boolean func_148349_b(int p_148349_1_) {
            return false;
        }

        @Override
        protected int func_148351_b() {
            return this.func_148355_a() * 36;
        }

        @Override
        protected void func_148358_c() {
            GuiScreenMcoWorldTemplate.this.drawDefaultBackground();
        }

        @Override
        protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_) {
            if (p_148348_1_ < GuiScreenMcoWorldTemplate.this.field_146960_i.size()) {
                this.func_148387_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
            }
        }

        private void func_148387_b(int p_148387_1_, int p_148387_2_, int p_148387_3_, int p_148387_4_, Tessellator p_148387_5_) {
            WorldTemplate var6 = (WorldTemplate)GuiScreenMcoWorldTemplate.this.field_146960_i.get(p_148387_1_);
            GuiScreenMcoWorldTemplate.this.drawString(GuiScreenMcoWorldTemplate.this.fontRendererObj, var6.field_148785_b, p_148387_2_ + 2, p_148387_3_ + 1, 16777215);
            GuiScreenMcoWorldTemplate.this.drawString(GuiScreenMcoWorldTemplate.this.fontRendererObj, var6.field_148784_d, p_148387_2_ + 2, p_148387_3_ + 12, 7105644);
            GuiScreenMcoWorldTemplate.this.drawString(GuiScreenMcoWorldTemplate.this.fontRendererObj, var6.field_148786_c, p_148387_2_ + 2 + 207 - GuiScreenMcoWorldTemplate.this.fontRendererObj.getStringWidth(var6.field_148786_c), p_148387_3_ + 1, 5000268);
        }
    }

}

