/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAITasks {
    private static final Logger logger = LogManager.getLogger();
    private List taskEntries = new ArrayList();
    private List executingTaskEntries = new ArrayList();
    private final Profiler theProfiler;
    private int tickCount;
    private int tickRate = 3;
    private static final String __OBFID = "CL_00001588";

    public EntityAITasks(Profiler par1Profiler) {
        this.theProfiler = par1Profiler;
    }

    public void addTask(int par1, EntityAIBase par2EntityAIBase) {
        this.taskEntries.add(new EntityAITaskEntry(par1, par2EntityAIBase));
    }

    public void removeTask(EntityAIBase par1EntityAIBase) {
        Iterator var2 = this.taskEntries.iterator();
        while (var2.hasNext()) {
            EntityAITaskEntry var3 = (EntityAITaskEntry)var2.next();
            EntityAIBase var4 = var3.action;
            if (var4 != par1EntityAIBase) continue;
            if (this.executingTaskEntries.contains(var3)) {
                var4.resetTask();
                this.executingTaskEntries.remove(var3);
            }
            var2.remove();
        }
    }

    public void onUpdateTasks() {
        ArrayList<EntityAITaskEntry> var1 = new ArrayList<EntityAITaskEntry>();
        if (this.tickCount++ % this.tickRate == 0) {
            for (EntityAITaskEntry var3 : this.taskEntries) {
                boolean var4 = this.executingTaskEntries.contains(var3);
                if (var4) {
                    if (this.canUse(var3) && this.canContinue(var3)) continue;
                    var3.action.resetTask();
                    this.executingTaskEntries.remove(var3);
                }
                if (!this.canUse(var3) || !var3.action.shouldExecute()) continue;
                var1.add(var3);
                this.executingTaskEntries.add(var3);
            }
        } else {
            Iterator var2 = this.executingTaskEntries.iterator();
            while (var2.hasNext()) {
                EntityAITaskEntry var3;
                var3 = (EntityAITaskEntry)var2.next();
                if (var3.action.continueExecuting()) continue;
                var3.action.resetTask();
                var2.remove();
            }
        }
        this.theProfiler.startSection("goalStart");
        for (EntityAITaskEntry var3 : var1) {
            this.theProfiler.startSection(var3.action.getClass().getSimpleName());
            var3.action.startExecuting();
            this.theProfiler.endSection();
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("goalTick");
        for (EntityAITaskEntry var3 : this.executingTaskEntries) {
            var3.action.updateTask();
        }
        this.theProfiler.endSection();
    }

    private boolean canContinue(EntityAITaskEntry par1EntityAITaskEntry) {
        this.theProfiler.startSection("canContinue");
        boolean var2 = par1EntityAITaskEntry.action.continueExecuting();
        this.theProfiler.endSection();
        return var2;
    }

    private boolean canUse(EntityAITaskEntry par1EntityAITaskEntry) {
        this.theProfiler.startSection("canUse");
        for (EntityAITaskEntry var3 : this.taskEntries) {
            if (var3 == par1EntityAITaskEntry) continue;
            if (par1EntityAITaskEntry.priority >= var3.priority) {
                if (!this.executingTaskEntries.contains(var3) || this.areTasksCompatible(par1EntityAITaskEntry, var3)) continue;
                this.theProfiler.endSection();
                return false;
            }
            if (!this.executingTaskEntries.contains(var3) || var3.action.isInterruptible()) continue;
            this.theProfiler.endSection();
            return false;
        }
        this.theProfiler.endSection();
        return true;
    }

    private boolean areTasksCompatible(EntityAITaskEntry par1EntityAITaskEntry, EntityAITaskEntry par2EntityAITaskEntry) {
        if ((par1EntityAITaskEntry.action.getMutexBits() & par2EntityAITaskEntry.action.getMutexBits()) == 0) {
            return true;
        }
        return false;
    }

    class EntityAITaskEntry {
        public EntityAIBase action;
        public int priority;
        private static final String __OBFID = "CL_00001589";

        public EntityAITaskEntry(int par2, EntityAIBase par3EntityAIBase) {
            this.priority = par2;
            this.action = par3EntityAIBase;
        }
    }

}

