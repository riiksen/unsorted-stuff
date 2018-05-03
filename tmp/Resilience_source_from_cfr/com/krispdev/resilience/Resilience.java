/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience;

import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventGameShutdown;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.irc.MainIRCBot;
import com.krispdev.resilience.irc.MainIRCManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.irc.extern.BotManager;
import com.krispdev.resilience.online.irc.extern.PublicBot;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.XrayUtils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.utilities.value.values.StringValue;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.Random;

public class Resilience {
    private static Resilience instance = null;
    private ResilienceLogger logger = null;
    private Wrapper wrapper = null;
    private MethodInvoker invoker = null;
    private ModuleManager moduleManager = null;
    private EventManager eventManager = null;
    private ClickGui clickGui = null;
    private FileManager fileManager = null;
    private Values values = null;
    private XrayUtils xrayUtils = null;
    private BotManager ircManager = null;
    private MainIRCManager chatIRCManager = null;
    private TTFRenderer panelTitleFont = null;
    private TTFRenderer buttonExtraFont = null;
    private TTFRenderer standardFont = null;
    private TTFRenderer modListFont = null;
    private TTFRenderer radarFont = null;
    private TTFRenderer chatFont = null;
    private TTFRenderer waypointFont = null;
    private TTFRenderer largeFont = null;
    private TTFRenderer xLargeFont = null;
    private String name = "Resilience";
    private String version = "1.6 Release";
    private String author = "Krisp_";
    private boolean isFirstTime = false;
    private boolean enabled = true;
    private boolean isFirstTimeOnline = false;
    private boolean newAccount = false;

    public void start() {
        this.getModuleManager().instantiateModules();
        this.getValues().initValues();
        try {
            this.getFileManager().init();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        Command.instantiateCommands();
        Utils.initDonators();
        this.getModuleManager().setModuleState("Target Players", true);
        this.getModuleManager().setModuleState("Target Mobs", true);
        this.getModuleManager().setModuleState("Target Animals", true);
        EventGameShutdown forClassLoadingPurposes = new EventGameShutdown(0);
        new Thread(){

            @Override
            public void run() {
                String channel = Utils.getSiteContent("http://resilience.krispdev.com/IRCChannel");
                if (channel != "" && channel != "ERR") {
                    Resilience.getInstance().getValues().ircChannel = channel;
                }
            }
        }.start();
        Random rand = new Random();
        this.getValues().userChannel = "#USER" + Math.abs(rand.nextLong());
        this.getIRCManager().bot.joinChannel(this.getValues().userChannel);
    }

    public static Resilience getInstance() {
        if (instance == null) {
            instance = new Resilience();
        }
        return instance;
    }

    public ResilienceLogger getLogger() {
        if (this.logger == null) {
            this.logger = new ResilienceLogger();
        }
        return this.logger;
    }

    public Wrapper getWrapper() {
        if (this.wrapper == null) {
            this.wrapper = new Wrapper();
        }
        return this.wrapper;
    }

    public MethodInvoker getInvoker() {
        if (this.invoker == null) {
            this.invoker = new MethodInvoker();
        }
        return this.invoker;
    }

    public ModuleManager getModuleManager() {
        if (this.moduleManager == null) {
            this.moduleManager = new ModuleManager();
        }
        return this.moduleManager;
    }

    public EventManager getEventManager() {
        if (this.eventManager == null) {
            this.eventManager = new EventManager();
        }
        return this.eventManager;
    }

    public ClickGui getClickGui() {
        if (this.clickGui == null) {
            this.clickGui = new ClickGui();
        }
        return this.clickGui;
    }

    public FileManager getFileManager() {
        if (this.fileManager == null) {
            this.fileManager = new FileManager();
        }
        return this.fileManager;
    }

    public Values getValues() {
        if (this.values == null) {
            this.values = new Values();
        }
        return this.values;
    }

    public XrayUtils getXrayUtils() {
        if (this.xrayUtils == null) {
            this.xrayUtils = new XrayUtils();
        }
        return this.xrayUtils;
    }

    public BotManager getIRCManager() {
        if (this.ircManager == null) {
            this.ircManager = new BotManager(this.getInvoker().getSessionUsername());
            new Thread(this.ircManager).start();
        }
        return this.ircManager;
    }

    public MainIRCManager getIRCChatManager() {
        if (this.chatIRCManager == null) {
            this.chatIRCManager = new MainIRCManager(this.getInvoker().getSessionUsername());
            new Thread(this.chatIRCManager).start();
            new Thread(){

                @Override
                public void run() {
                    Resilience.getInstance().getIRCChatManager().bot.joinChannel(Resilience.getInstance().getValues().ircChannel);
                }
            }.start();
        }
        return this.chatIRCManager;
    }

    public TTFRenderer getStandardFont() {
        if (this.standardFont == null) {
            this.standardFont = new TTFRenderer("Arial", 0, 18);
        }
        return this.standardFont;
    }

    public TTFRenderer getPanelTitleFont() {
        if (this.panelTitleFont == null) {
            this.panelTitleFont = new TTFRenderer("Arial", 0, 23);
        }
        return this.panelTitleFont;
    }

    public TTFRenderer getButtonExtraFont() {
        if (this.buttonExtraFont == null) {
            this.buttonExtraFont = new TTFRenderer("Arial", 0, 16);
        }
        return this.buttonExtraFont;
    }

    public TTFRenderer getModListFont() {
        if (this.modListFont == null) {
            this.modListFont = new TTFRenderer("Arial", 0, 20);
        }
        return this.modListFont;
    }

    public TTFRenderer getRadarFont() {
        if (this.radarFont == null) {
            this.radarFont = new TTFRenderer("Arial", 0, 10);
        }
        return this.radarFont;
    }

    public TTFRenderer getChatFont() {
        if (this.chatFont == null) {
            this.chatFont = new TTFRenderer("Arial", 0, 19);
        }
        return this.chatFont;
    }

    public TTFRenderer getWaypointFont() {
        if (this.waypointFont == null) {
            this.waypointFont = new TTFRenderer("Arial", 0, 40);
        }
        return this.waypointFont;
    }

    public TTFRenderer getLargeFont() {
        if (this.largeFont == null) {
            this.largeFont = new TTFRenderer("Arial", 0, 30);
        }
        return this.largeFont;
    }

    public TTFRenderer getLargeItalicFont() {
        if (this.largeFont == null) {
            this.largeFont = new TTFRenderer("Arial", 2, 30);
        }
        return this.largeFont;
    }

    public TTFRenderer getXLargeFont() {
        if (this.xLargeFont == null) {
            this.xLargeFont = new TTFRenderer("Arial", 0, 45);
        }
        return this.xLargeFont;
    }

    public String getCmdPrefix() {
        return this.getValues().cmdPrefix.getValue();
    }

    public String getIRCPrefix() {
        return this.getValues().ircPrefix.getValue();
    }

    public String getVersion() {
        return this.version;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getName() {
        return this.name;
    }

    public boolean isFirstTime() {
        return this.isFirstTime;
    }

    public void setFirstTime() {
        this.isFirstTime = true;
    }

    public boolean isFirstTimeOnline() {
        return this.isFirstTimeOnline;
    }

    public void setFirstTimeOnline() {
        this.isFirstTimeOnline = true;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    public void setCmdPrefix(String prefix) {
        this.getValues().cmdPrefix.setValue(prefix);
    }

    public void setIRCPrefix(String prefix) {
        this.getValues().ircPrefix.setValue(prefix);
    }

    public void setNewAccount() {
        this.newAccount = true;
    }

    public boolean isNewAccount() {
        return this.newAccount;
    }

}

