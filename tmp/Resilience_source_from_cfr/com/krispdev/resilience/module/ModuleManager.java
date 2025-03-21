/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.modules.DefaultModule;
import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<DefaultModule> moduleList = new ArrayList();

    public void instantiateModules() {
        this.add("combat.ModuleAimbot");
        this.add("movement.ModuleAntiAFK");
        this.add("combat.ModuleAutoArmour");
        this.add("player.ModuleAntiBlindness");
        this.add("combat.ModuleAutoBlock");
        this.add("misc.ModuleAutoChestSteal");
        this.add("misc.ModuleClientTips");
        this.add("misc.ModuleAutoEat");
        this.add("misc.ModuleAutoFish");
        this.add("movement.ModuleAutoJump");
        this.add("misc.ModuleAutoMine");
        this.add("combat.ModuleAutoQuit");
        this.add("player.ModuleAutoRespawn");
        this.add("combat.ModuleAutoSoup");
        this.add("combat.ModuleAutoSword");
        this.add("misc.ModuleAutoTool");
        this.add("player.ModuleAutoTPAccept");
        this.add("movement.ModuleAutoWalk");
        this.add("combat.ModuleBowAimbot");
        this.add("render.ModuleBreadcrumbs");
        this.add("render.ModuleBrightness");
        this.add("world.ModuleCaveFinder");
        this.add("render.ModuleChestESP");
        this.add("combat.ModuleClickAimbot");
        this.add("world.ModuleClickNuker");
        this.add("gui.ModuleConsole");
        this.add("combat.ModuleCriticals");
        this.add("misc.ModuleEnabledMods");
        this.add("combat.ModuleFastBow");
        this.add("world.ModuleFastBreak");
        this.add("misc.ModuleFastEat");
        this.add("movement.ModuleFastLadder");
        this.add("world.ModuleFastPlace");
        this.add("movement.ModuleFlight");
        this.add("combat.ModuleForceField");
        this.add("player.ModuleFreecam");
        this.add("movement.ModuleGlide");
        this.add("gui.ModuleGui");
        this.add("movement.ModuleHighJump");
        this.add("movement.ModuleInfiniteJump");
        this.add("misc.ModuleIRC");
        this.add("movement.ModuleJesus");
        this.add("combat.ModuleKillAura");
        this.add("misc.ModuleMiddleClickFriends");
        this.add("render.ModuleMobESP");
        this.add("render.ModuleNames");
        this.add("player.ModuleNameProtect");
        this.add("player.ModuleAntiNausea");
        this.add("misc.ModuleNiceChat");
        this.add("movement.ModuleNoFall");
        this.add("render.ModuleNoFireEffect");
        this.add("combat.ModuleNoHurtcam");
        this.add("combat.ModuleNoKnockback");
        this.add("movement.ModuleNoSlowdown");
        this.add("world.ModuleNuker");
        this.add("movement.ModulePhaze");
        this.add("render.ModulePlayerESP");
        this.add("render.ModulePotionEffects");
        this.add("render.ModuleProphuntESP");
        this.add("combat.ModuleRegen");
        this.add("player.ModuleRetard");
        this.add("movement.ModuleSafeWalk");
        this.add("render.ModuleSearch");
        this.add("movement.ModuleSneak");
        this.add("movement.ModuleSpider");
        this.add("movement.ModuleSprint");
        this.add("movement.ModuleStep");
        this.add("player.ModuleTimer");
        this.add("render.ModuleTracers");
        this.add("render.ModuleTrack");
        this.add("render.ModuleTrajectories");
        this.add("combat.ModuleTriggerBot");
        this.add("misc.ModuleTwerk");
        this.add("render.ModuleWaypoints");
        this.add("world.ModuleWireFrame");
        this.add("world.ModuleXray");
        this.add("combat.modes.ModulePlayers");
        this.add("combat.modes.ModuleMobs");
        this.add("combat.modes.ModuleAnimals");
        this.add("combat.modes.ModuleInvisibles");
        this.add("combat.modes.ModulePropBlocks");
        this.add("combat.modes.ModuleSafeMode");
        Resilience.getInstance().getLogger().info("Loaded " + this.moduleList.size() + " modules!");
    }

    private void add(String moduleName) {
        try {
            Class clazz = Class.forName("com.krispdev.resilience.module.modules." + moduleName);
            if (clazz.getSuperclass() == DefaultModule.class) {
                DefaultModule module = (DefaultModule)clazz.newInstance();
                Resilience.getInstance().getLogger().info("Loaded module " + module.getName());
                this.moduleList.add(module);
            }
        }
        catch (ClassNotFoundException e) {
            Resilience.getInstance().getLogger().warning("Error loading module: " + moduleName);
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            Resilience.getInstance().getLogger().warning("Error loading module: " + moduleName);
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            Resilience.getInstance().getLogger().warning("Error loading module: " + moduleName);
            e.printStackTrace();
        }
    }

    public void setModuleState(String modName, boolean state) {
        for (DefaultModule mod : this.moduleList) {
            if (!mod.getName().equalsIgnoreCase(modName.trim())) continue;
            mod.setEnabled(state);
            return;
        }
        Resilience.getInstance().getLogger().warning("Module not found when attempting to " + (state ? "enable" : "disable") + " it: " + modName);
    }
}

