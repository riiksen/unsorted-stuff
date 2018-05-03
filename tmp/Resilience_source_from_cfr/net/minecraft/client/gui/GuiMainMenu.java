/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.Charsets
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Project
 */
package net.minecraft.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOnlineServers;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.ExceptionRetryCall;
import net.minecraft.client.mco.GuiScreenClientOutdated;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu
extends GuiScreen {
    private static final AtomicInteger field_146973_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random rand = new Random();
    private float updateCounter;
    private String splashText;
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private boolean field_96141_q;
    private static boolean field_96140_r;
    private static boolean field_96139_s;
    private final Object field_104025_t;
    private String field_92025_p;
    private String field_146972_A;
    private String field_104024_v;
    private static final ResourceLocation splashTexts;
    private static final ResourceLocation minecraftTitleTextures;
    private static final ResourceLocation[] titlePanoramaPaths;
    public static final String field_96138_a;
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation field_110351_G;
    private GuiButton minecraftRealmsButton;
    private static final String __OBFID = "CL_00001154";

    static {
        splashTexts = new ResourceLocation("texts/splashes.txt");
        minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
        titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
        field_96138_a = "Please click " + (Object)((Object)EnumChatFormatting.UNDERLINE) + "here" + (Object)((Object)EnumChatFormatting.RESET) + " for more information.";
    }

    public GuiMainMenu() {
        block18 : {
            BufferedReader var1;
            this.field_96141_q = true;
            this.field_104025_t = new Object();
            this.field_146972_A = field_96138_a;
            this.splashText = "missingno";
            var1 = null;
            try {
                try {
                    String var3;
                    ArrayList<String> var2 = new ArrayList<String>();
                    var1 = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
                    while ((var3 = var1.readLine()) != null) {
                        if ((var3 = var3.trim()).isEmpty()) continue;
                        var2.add(var3);
                    }
                    if (!var2.isEmpty()) {
                        do {
                            this.splashText = (String)var2.get(rand.nextInt(var2.size()));
                        } while (this.splashText.hashCode() == 125780783);
                    }
                }
                catch (IOException var2) {
                    if (var1 != null) {
                        try {
                            var1.close();
                        }
                        catch (IOException iOException) {}
                    }
                    break block18;
                }
            }
            catch (Throwable throwable) {
                if (var1 != null) {
                    try {
                        var1.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                throw throwable;
            }
            if (var1 != null) {
                try {
                    var1.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        this.updateCounter = rand.nextFloat();
        this.field_92025_p = "";
        if (!OpenGlHelper.openGL21) {
            this.field_92025_p = "Old graphics card detected; this may prevent you from";
            this.field_146972_A = "playing in the far future as OpenGL 2.1 will be required.";
            this.field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char par1, int par2) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.field_110351_G = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());
        if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
            this.splashText = "Happy birthday, ez!";
        } else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
            this.splashText = "Happy birthday, Notch!";
        } else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        boolean var2 = true;
        int var3 = this.height / 4 + 48;
        if (this.mc.isDemo()) {
            this.addDemoButtons(var3, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(var3, 24);
        }
        this.func_130020_g();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, var3 + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var3 + 72 + 12));
        Object var4 = this.field_104025_t;
        Object object = this.field_104025_t;
        synchronized (object) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.field_92025_p);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.field_146972_A);
            int var5 = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - var5) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get((int)0)).field_146129_i - 24;
            this.field_92020_v = this.field_92022_t + var5;
            this.field_92019_w = this.field_92021_u + 24;
        }
    }

    protected void func_130020_g() {
        if (this.field_96141_q) {
            if (!field_96140_r) {
                field_96140_r = true;
                new Thread("MCO Availability Checker #" + field_146973_f.incrementAndGet()){
                    private static final String __OBFID = "CL_00001155";

                    @Override
                    public void run() {
                        Session var1 = GuiMainMenu.this.mc.getSession();
                        McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.9", Minecraft.getMinecraft().getProxy());
                        boolean var3 = false;
                        int var4 = 0;
                        while (var4 < 3) {
                            try {
                                Boolean var5 = var2.func_148687_b();
                                if (var5.booleanValue()) {
                                    GuiMainMenu.this.func_130022_h();
                                }
                                GuiMainMenu.access$2(var5);
                            }
                            catch (ExceptionRetryCall var7) {
                                var3 = true;
                            }
                            catch (ExceptionMcoService var8) {
                                logger.error("Couldn't connect to Realms");
                            }
                            catch (IOException var9) {
                                logger.error("Couldn't parse response connecting to Realms");
                            }
                            if (!var3) break;
                            try {
                                Thread.sleep(10000);
                            }
                            catch (InterruptedException var6) {
                                Thread.currentThread().interrupt();
                            }
                            ++var4;
                        }
                    }
                }.start();
            } else if (field_96139_s) {
                this.func_130022_h();
            }
        }
    }

    private void func_130022_h() {
        this.minecraftRealmsButton.field_146125_m = true;
    }

    protected void addSingleplayerMultiplayerButtons(int par1, int par2) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, par1, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, par1 + par2 * 1, I18n.format("menu.multiplayer", new Object[0])));
        this.minecraftRealmsButton = new GuiButton(14, this.width / 2 - 100, par1 + par2 * 2, I18n.format("menu.online", new Object[0]));
        this.buttonList.add(this.minecraftRealmsButton);
    }

    private void addDemoButtons(int par1, int par2) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, par1, I18n.format("menu.playdemo", new Object[0])));
        this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, par1 + par2 * 1, I18n.format("menu.resetdemo", new Object[0]));
        this.buttonList.add(this.buttonResetDemo);
        ISaveFormat var3 = this.mc.getSaveLoader();
        WorldInfo var4 = var3.getWorldInfo("Demo_World");
        if (var4 == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        WorldInfo var3;
        ISaveFormat var2;
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (p_146284_1_.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (p_146284_1_.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (p_146284_1_.id == 14) {
            this.func_140005_i();
        }
        if (p_146284_1_.id == 4) {
            this.mc.shutdown();
        }
        if (p_146284_1_.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (p_146284_1_.id == 12 && (var3 = (var2 = this.mc.getSaveLoader()).getWorldInfo("Demo_World")) != null) {
            GuiYesNo var4 = GuiSelectWorld.func_146623_a(this, var3.getWorldName(), 12);
            this.mc.displayGuiScreen(var4);
        }
    }

    protected void func_140005_i() {
        Session var1 = this.mc.getSession();
        McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.9", Minecraft.getMinecraft().getProxy());
        try {
            if (var2.func_148695_c().booleanValue()) {
                this.mc.displayGuiScreen(new GuiScreenClientOutdated(this));
            } else {
                this.mc.displayGuiScreen(new GuiScreenOnlineServers(this));
            }
        }
        catch (ExceptionMcoService var4) {
            logger.error("Couldn't connect to realms");
        }
        catch (IOException var5) {
            logger.error("Couldn't connect to realms");
        }
    }

    @Override
    public void confirmClicked(boolean par1, int par2) {
        if (par1 && par2 == 12) {
            ISaveFormat var6 = this.mc.getSaveLoader();
            var6.flushCache();
            var6.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (par2 == 13) {
            if (par1) {
                try {
                    Class var3 = Class.forName("java.awt.Desktop");
                    Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    var3.getMethod("browse", URI.class).invoke(var4, new URI(this.field_104024_v));
                }
                catch (Throwable var5) {
                    logger.error("Couldn't open link", var5);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }

    private void drawPanorama(int par1, int par2, float par3) {
        Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode((int)5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Project.gluPerspective((float)120.0f, (float)1.0f, (float)0.05f, (float)10.0f);
        GL11.glMatrixMode((int)5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)2884);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        int var5 = 8;
        int var6 = 0;
        while (var6 < var5 * var5) {
            GL11.glPushMatrix();
            float var7 = ((float)(var6 % var5) / (float)var5 - 0.5f) / 64.0f;
            float var8 = ((float)(var6 / var5) / (float)var5 - 0.5f) / 64.0f;
            float var9 = 0.0f;
            GL11.glTranslatef((float)var7, (float)var8, (float)var9);
            GL11.glRotatef((float)(MathHelper.sin(((float)this.panoramaTimer + par3) / 400.0f) * 25.0f + 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)((- (float)this.panoramaTimer + par3) * 0.1f), (float)0.0f, (float)1.0f, (float)0.0f);
            int var10 = 0;
            while (var10 < 6) {
                GL11.glPushMatrix();
                if (var10 == 1) {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (var10 == 2) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (var10 == 3) {
                    GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (var10 == 4) {
                    GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                }
                if (var10 == 5) {
                    GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                }
                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[var10]);
                var4.startDrawingQuads();
                var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
                float var11 = 0.0f;
                var4.addVertexWithUV(-1.0, -1.0, 1.0, 0.0f + var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, -1.0, 1.0, 1.0f - var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, 1.0, 1.0, 1.0f - var11, 1.0f - var11);
                var4.addVertexWithUV(-1.0, 1.0, 1.0, 0.0f + var11, 1.0f - var11);
                var4.draw();
                GL11.glPopMatrix();
                ++var10;
            }
            GL11.glPopMatrix();
            GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
            ++var6;
        }
        var4.setTranslation(0.0, 0.0, 0.0);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glMatrixMode((int)5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode((int)5888);
        GL11.glPopMatrix();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2929);
    }

    private void rotateAndBlurSkybox(float par1) {
        this.mc.getTextureManager().bindTexture(this.field_110351_G);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glCopyTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)0, (int)0, (int)256, (int)256);
        GL11.glEnable((int)3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        GL11.glDisable((int)3008);
        int var3 = 3;
        int var4 = 0;
        while (var4 < var3) {
            var2.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f / (float)(var4 + 1));
            int var5 = this.width;
            int var6 = this.height;
            float var7 = (float)(var4 - var3 / 2) / 256.0f;
            var2.addVertexWithUV(var5, var6, this.zLevel, 0.0f + var7, 1.0);
            var2.addVertexWithUV(var5, 0.0, this.zLevel, 1.0f + var7, 1.0);
            var2.addVertexWithUV(0.0, 0.0, this.zLevel, 1.0f + var7, 0.0);
            var2.addVertexWithUV(0.0, var6, this.zLevel, 0.0f + var7, 0.0);
            ++var4;
        }
        var2.draw();
        GL11.glEnable((int)3008);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
    }

    private void renderSkybox(int par1, int par2, float par3) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GL11.glViewport((int)0, (int)0, (int)256, (int)256);
        this.drawPanorama(par1, par2, par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        float var5 = this.width > this.height ? 120.0f / (float)this.width : 120.0f / (float)this.height;
        float var6 = (float)this.height * var5 / 256.0f;
        float var7 = (float)this.width * var5 / 256.0f;
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int var8 = this.width;
        int var9 = this.height;
        var4.addVertexWithUV(0.0, var9, this.zLevel, 0.5f - var6, 0.5f + var7);
        var4.addVertexWithUV(var8, var9, this.zLevel, 0.5f - var6, 0.5f - var7);
        var4.addVertexWithUV(var8, 0.0, this.zLevel, 0.5f + var6, 0.5f - var7);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.5f + var6, 0.5f + var7);
        var4.draw();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        GL11.glDisable((int)3008);
        this.renderSkybox(par1, par2, par3);
        GL11.glEnable((int)3008);
        Tessellator var4 = Tessellator.instance;
        int var5 = 274;
        int var6 = this.width / 2 - var5 / 2;
        int var7 = 30;
        this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if ((double)this.updateCounter < 1.0E-4) {
            this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 99, 44);
            this.drawTexturedModalRect(var6 + 99, var7 + 0, 129, 0, 27, 44);
            this.drawTexturedModalRect(var6 + 99 + 26, var7 + 0, 126, 0, 3, 44);
            this.drawTexturedModalRect(var6 + 99 + 26 + 3, var7 + 0, 99, 0, 26, 44);
            this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        } else {
            this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 155, 44);
            this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        }
        var4.setColorOpaque_I(-1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), (float)70.0f, (float)0.0f);
        GL11.glRotatef((float)-20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float var8 = 1.8f - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000) / 1000.0f * 3.1415927f * 2.0f) * 0.1f);
        var8 = var8 * 100.0f / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
        GL11.glScalef((float)var8, (float)var8, (float)var8);
        this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
        GL11.glPopMatrix();
        String var9 = "Minecraft 1.7.9";
        if (this.mc.isDemo()) {
            var9 = String.valueOf(var9) + " Demo";
        }
        this.drawString(this.fontRendererObj, var9, 2, this.height - 10, -1);
        String var10 = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRendererObj, var10, this.width - this.fontRendererObj.getStringWidth(var10) - 2, this.height - 10, -1);
        if (this.field_92025_p != null && this.field_92025_p.length() > 0) {
            GuiMainMenu.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.field_146972_A, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get((int)0)).field_146129_i - 12, -1);
        }
        super.drawScreen(par1, par2, par3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        Object var4 = this.field_104025_t;
        Object object = this.field_104025_t;
        synchronized (object) {
            if (this.field_92025_p.length() > 0 && par1 >= this.field_92022_t && par1 <= this.field_92020_v && par2 >= this.field_92021_u && par2 <= this.field_92019_w) {
                GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, this.field_104024_v, 13, true);
                var5.func_146358_g();
                this.mc.displayGuiScreen(var5);
            }
        }
    }

    static /* synthetic */ boolean access$1() {
        return field_96139_s;
    }

    static /* synthetic */ void access$2(boolean bl) {
        field_96139_s = bl;
    }

}

