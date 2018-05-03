/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.game.InventoryUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ModuleAutoArmour
extends DefaultModule {
    int[] ids = new int[]{298, 299, 300, 301, 314, 315, 316, 317, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313};
    int[] bootIds = new int[]{313, 309, 305, 317, 301};
    int[] pantIds = new int[]{312, 308, 304, 316, 300};
    int[] chestIds = new int[]{311, 307, 303, 315, 299};
    int[] helmIds = new int[]{310, 306, 302, 314, 298};
    private InventoryUtils utils = new InventoryUtils();
    private int prevSlot = -1;
    private Timer timer = new Timer();

    public ModuleAutoArmour() {
        super("AutoArmor", 0);
        this.setCategory(ModuleCategory.COMBAT);
        this.setDescription("Automatically puts on armor when your old armor breaks");
    }

    @Override
    public void onUpdate(EventOnUpdate event) {
        int id;
        int slot;
        int invSlot;
        int[] arrn;
        boolean inv;
        int n;
        int n2;
        int newSlot;
        boolean hot;
        if (!this.timer.hasTimePassed(170)) {
            return;
        }
        this.timer.reset();
        if (this.prevSlot != -1) {
            this.invoker.setInvSlot(this.prevSlot);
            this.prevSlot = -1;
        }
        boolean boots = true;
        boolean pants = true;
        boolean shirt = true;
        boolean helm = true;
        int i = 0;
        while (i < this.invoker.getArmourInventory().length) {
            if (i == 0 && this.invoker.getArmourInventory()[i] == null) {
                boots = false;
            }
            if (i == 1 && this.invoker.getArmourInventory()[i] == null) {
                pants = false;
            }
            if (i == 2 && this.invoker.getArmourInventory()[i] == null) {
                shirt = false;
            }
            if (i == 3 && this.invoker.getArmourInventory()[i] == null) {
                helm = false;
            }
            ++i;
        }
        if (!boots) {
            hot = false;
            inv = false;
            slot = -1;
            arrn = this.bootIds;
            n2 = arrn.length;
            n = 0;
            while (n < n2) {
                id = arrn[n];
                invSlot = this.utils.getSlotOfInvItem(id);
                if (invSlot != -1) {
                    inv = true;
                    slot = invSlot;
                    break;
                }
                newSlot = this.utils.getSlotOfHotbarItem(id);
                if (newSlot != -1) {
                    hot = true;
                    slot = newSlot;
                    break;
                }
                ++n;
            }
            if (slot != -1 && inv) {
                this.utils.click(slot, 1);
            } else if (slot != -1 && hot) {
                this.prevSlot = this.invoker.getCurInvSlot();
                this.invoker.setInvSlot(slot);
                this.invoker.sendUseItem(this.invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
            }
        }
        if (!pants) {
            hot = false;
            inv = false;
            slot = -1;
            arrn = this.pantIds;
            n2 = arrn.length;
            n = 0;
            while (n < n2) {
                id = arrn[n];
                invSlot = this.utils.getSlotOfInvItem(id);
                if (invSlot != -1) {
                    inv = true;
                    slot = invSlot;
                    break;
                }
                newSlot = this.utils.getSlotOfHotbarItem(id);
                if (newSlot != -1) {
                    hot = true;
                    slot = newSlot;
                    break;
                }
                ++n;
            }
            if (slot != -1 && inv) {
                this.utils.click(slot, 1);
            } else if (slot != -1 && hot) {
                this.prevSlot = this.invoker.getCurInvSlot();
                this.invoker.setInvSlot(slot);
                this.invoker.sendUseItem(this.invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
            }
        }
        if (!shirt) {
            hot = false;
            inv = false;
            slot = -1;
            arrn = this.chestIds;
            n2 = arrn.length;
            n = 0;
            while (n < n2) {
                id = arrn[n];
                invSlot = this.utils.getSlotOfInvItem(id);
                if (invSlot != -1) {
                    inv = true;
                    slot = invSlot;
                    break;
                }
                newSlot = this.utils.getSlotOfHotbarItem(id);
                if (newSlot != -1) {
                    hot = true;
                    slot = newSlot;
                    break;
                }
                ++n;
            }
            if (slot != -1 && inv) {
                this.utils.click(slot, 1);
            } else if (slot != -1 && hot) {
                this.prevSlot = this.invoker.getCurInvSlot();
                this.invoker.setInvSlot(slot);
                this.invoker.sendUseItem(this.invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
            }
        }
        if (!helm) {
            hot = false;
            inv = false;
            slot = -1;
            arrn = this.helmIds;
            n2 = arrn.length;
            n = 0;
            while (n < n2) {
                id = arrn[n];
                invSlot = this.utils.getSlotOfInvItem(id);
                if (invSlot != -1) {
                    inv = true;
                    slot = invSlot;
                    break;
                }
                newSlot = this.utils.getSlotOfHotbarItem(id);
                if (newSlot != -1) {
                    hot = true;
                    slot = newSlot;
                    break;
                }
                ++n;
            }
            if (slot != -1 && inv) {
                this.utils.click(slot, 1);
            } else if (slot != -1 && hot) {
                this.prevSlot = this.invoker.getCurInvSlot();
                this.invoker.setInvSlot(slot);
                this.invoker.sendUseItem(this.invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
            }
        }
    }

    @Override
    public void onEnable() {
        Resilience.getInstance().getEventManager().register(this);
    }

    @Override
    public void onDisable() {
        Resilience.getInstance().getEventManager().unregister(this);
    }
}

