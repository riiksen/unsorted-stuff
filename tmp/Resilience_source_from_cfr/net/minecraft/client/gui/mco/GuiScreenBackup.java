/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui.mco;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.URI;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfigureWorld;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.mco.Backup;
import net.minecraft.client.mco.BackupList;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBackup
extends GuiScreen {
    private static final AtomicInteger field_146845_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private final GuiScreenConfigureWorld field_146842_g;
    private final long field_146846_h;
    private List field_146847_i = Collections.emptyList();
    private SelectionList field_146844_r;
    private int field_146843_s = -1;
    private static final String __OBFID = "CL_00000766";

    public GuiScreenBackup(GuiScreenConfigureWorld par1GuiScreenConfigureWorld, long par2) {
        this.field_146842_g = par1GuiScreenConfigureWorld;
        this.field_146846_h = par2;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.field_146844_r = new SelectionList();
        new Thread("MCO Backup Requester #" + field_146845_a.incrementAndGet()){
            private static final String __OBFID = "CL_00000767";

            @Override
            public void run() {
                Session var1 = GuiScreenBackup.this.mc.getSession();
                McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
                try {
                    GuiScreenBackup.access$9(GuiScreenBackup.this, var2.func_148704_d((long)GuiScreenBackup.access$1((GuiScreenBackup)GuiScreenBackup.this)).theBackupList);
                }
                catch (ExceptionMcoService var4) {
                    logger.error("Couldn't request backups", (Throwable)var4);
                }
            }
        }.start();
        this.func_146840_h();
    }

    private void func_146840_h() {
        this.buttonList.add(new GuiButton(2, this.width / 2 + 6, this.height - 52, 153, 20, I18n.format("mco.backup.button.download", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 154, this.height - 52, 153, 20, I18n.format("mco.backup.button.restore", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 74, this.height - 52 + 25, 153, 20, I18n.format("gui.back", new Object[0])));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 1) {
                this.func_146827_i();
            } else if (p_146284_1_.id == 0) {
                this.mc.displayGuiScreen(this.field_146842_g);
            } else if (p_146284_1_.id == 2) {
                this.func_146826_p();
            } else {
                this.field_146844_r.func_148357_a(p_146284_1_);
            }
        }
    }

    private void func_146827_i() {
        if (this.field_146843_s >= 0 && this.field_146843_s < this.field_146847_i.size()) {
            Date var1 = ((Backup)this.field_146847_i.get((int)this.field_146843_s)).field_148778_b;
            String var2 = DateFormat.getDateTimeInstance(3, 3).format(var1);
            String var3 = this.func_146829_a(System.currentTimeMillis() - var1.getTime());
            String var4 = String.valueOf(I18n.format("mco.configure.world.restore.question.line1", new Object[0])) + " '" + var2 + "' (" + var3 + ")";
            String var5 = I18n.format("mco.configure.world.restore.question.line2", new Object[0]);
            this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, var4, var5, 1));
        }
    }

    private void func_146826_p() {
        String var1 = I18n.format("mco.configure.world.restore.download.question.line1", new Object[0]);
        String var2 = I18n.format("mco.configure.world.restore.download.question.line2", new Object[0]);
        this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var1, var2, 2));
    }

    private void func_146821_q() {
        Session var1 = this.mc.getSession();
        McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        try {
            String var3 = var2.func_148708_h(this.field_146846_h);
            Clipboard var4 = Toolkit.getDefaultToolkit().getSystemClipboard();
            var4.setContents(new StringSelection(var3), null);
            this.func_146823_a(var3);
        }
        catch (ExceptionMcoService var5) {
            logger.error("Couldn't download world data");
        }
    }

    private void func_146823_a(String p_146823_1_) {
        try {
            URI var2 = new URI(p_146823_1_);
            Class var3 = Class.forName("java.awt.Desktop");
            Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            var3.getMethod("browse", URI.class).invoke(var4, var2);
        }
        catch (Throwable var5) {
            logger.error("Couldn't open link");
        }
    }

    @Override
    public void confirmClicked(boolean par1, int par2) {
        if (par1 && par2 == 1) {
            this.func_146839_r();
        } else if (par1 && par2 == 2) {
            this.func_146821_q();
        } else {
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_146839_r() {
        Backup var1 = (Backup)this.field_146847_i.get(this.field_146843_s);
        RestoreTask var2 = new RestoreTask(var1, null);
        GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.mc, this.field_146842_g, var2);
        var3.func_146902_g();
        this.mc.displayGuiScreen(var3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.field_146844_r.func_148350_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.backup.title", new Object[0]), this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    private String func_146829_a(Long p_146829_1_) {
        if (p_146829_1_ < 0) {
            return "right now";
        }
        long var2 = p_146829_1_ / 1000;
        if (var2 < 60) {
            return String.valueOf(var2 == 1 ? "1 second" : new StringBuilder(String.valueOf(var2)).append(" seconds").toString()) + " ago";
        }
        if (var2 < 3600) {
            long var4 = var2 / 60;
            return String.valueOf(var4 == 1 ? "1 minute" : new StringBuilder(String.valueOf(var4)).append(" minutes").toString()) + " ago";
        }
        if (var2 < 86400) {
            long var4 = var2 / 3600;
            return String.valueOf(var4 == 1 ? "1 hour" : new StringBuilder(String.valueOf(var4)).append(" hours").toString()) + " ago";
        }
        long var4 = var2 / 86400;
        return String.valueOf(var4 == 1 ? "1 day" : new StringBuilder(String.valueOf(var4)).append(" days").toString()) + " ago";
    }

    static /* synthetic */ void access$5(GuiScreenBackup guiScreenBackup, int n) {
        guiScreenBackup.field_146843_s = n;
    }

    static /* synthetic */ void access$9(GuiScreenBackup guiScreenBackup, List list) {
        guiScreenBackup.field_146847_i = list;
    }

    class RestoreTask
    extends TaskLongRunning {
        private final Backup field_148424_c;
        private static final String __OBFID = "CL_00000769";

        private RestoreTask(Backup par2Backup) {
            this.field_148424_c = par2Backup;
        }

        @Override
        public void run() {
            this.func_148417_b(I18n.format("mco.backup.restoring", new Object[0]));
            try {
                if (this.func_148418_c()) {
                    return;
                }
                Session var1 = GuiScreenBackup.this.mc.getSession();
                McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
                var2.func_148712_c(GuiScreenBackup.this.field_146846_h, this.field_148424_c.field_148780_a);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException var4) {
                    Thread.currentThread().interrupt();
                }
                if (this.func_148418_c()) {
                    return;
                }
                this.func_148413_b().displayGuiScreen(GuiScreenBackup.this.field_146842_g);
            }
            catch (ExceptionMcoService var5) {
                if (this.func_148418_c()) {
                    return;
                }
                logger.error("Couldn't restore backup");
                this.func_148416_a(var5.toString());
            }
            catch (Exception var6) {
                if (this.func_148418_c()) {
                    return;
                }
                logger.error("Couldn't restore backup");
                this.func_148416_a(var6.getLocalizedMessage());
            }
        }

        RestoreTask(Backup par2Backup, Object par3GuiScreenBackupDownloadThread) {
            this(par2Backup);
        }
    }

    class SelectionList
    extends GuiScreenSelectLocation {
        private static final String __OBFID = "CL_00000768";

        public SelectionList() {
            super(GuiScreenBackup.this.mc, GuiScreenBackup.this.width, GuiScreenBackup.this.height, 32, GuiScreenBackup.this.height - 64, 36);
        }

        @Override
        protected int func_148355_a() {
            return GuiScreenBackup.this.field_146847_i.size() + 1;
        }

        @Override
        protected void func_148352_a(int p_148352_1_, boolean p_148352_2_) {
            if (p_148352_1_ < GuiScreenBackup.this.field_146847_i.size()) {
                GuiScreenBackup.access$5(GuiScreenBackup.this, p_148352_1_);
            }
        }

        @Override
        protected boolean func_148356_a(int p_148356_1_) {
            if (p_148356_1_ == GuiScreenBackup.this.field_146843_s) {
                return true;
            }
            return false;
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
            GuiScreenBackup.this.drawDefaultBackground();
        }

        @Override
        protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_) {
            if (p_148348_1_ < GuiScreenBackup.this.field_146847_i.size()) {
                this.func_148385_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
            }
        }

        private void func_148385_b(int p_148385_1_, int p_148385_2_, int p_148385_3_, int p_148385_4_, Tessellator p_148385_5_) {
            Backup var6 = (Backup)GuiScreenBackup.this.field_146847_i.get(p_148385_1_);
            GuiScreenBackup.this.drawString(GuiScreenBackup.this.fontRendererObj, "Backup (" + GuiScreenBackup.this.func_146829_a(MinecraftServer.getSystemTimeMillis() - var6.field_148778_b.getTime()) + ")", p_148385_2_ + 2, p_148385_3_ + 1, 16777215);
            GuiScreenBackup.this.drawString(GuiScreenBackup.this.fontRendererObj, this.func_148384_a(var6.field_148778_b), p_148385_2_ + 2, p_148385_3_ + 12, 7105644);
        }

        private String func_148384_a(Date p_148384_1_) {
            return DateFormat.getDateTimeInstance(3, 3).format(p_148384_1_);
        }
    }

}

