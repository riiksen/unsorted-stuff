/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui.inventory;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiChest
extends GuiContainer {
    private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
    private IInventory field_147016_v;
    private IInventory field_147015_w;
    private int field_147018_x;
    private static final String __OBFID = "CL_00000749";
    private int currentId = -1;

    public GuiChest(IInventory par1IInventory, IInventory par2IInventory) {
        super(new ContainerChest(par1IInventory, par2IInventory));
        this.field_147016_v = par1IInventory;
        this.field_147015_w = par2IInventory;
        this.field_146291_p = false;
        int var3 = 222;
        int var4 = var3 - 108;
        this.field_147018_x = par2IInventory.getSizeInventory() / 9;
        this.field_147000_g = var4 + this.field_147018_x * 18;
    }

    @Override
    public void initGui() {
        if (this.field_147015_w.getInventoryName().equals("container.chest") && Resilience.getInstance().isEnabled()) {
            Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(69, Resilience.getInstance().getInvoker().getWidth() / 2 - 52, (Resilience.getInstance().getInvoker().getHeight() - this.field_146999_f) / 2 - 20, 50.0f, 20.0f, "Steal"));
            Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(100, Resilience.getInstance().getInvoker().getWidth() / 2 + 2, (Resilience.getInstance().getInvoker().getHeight() - this.field_146999_f) / 2 - 20, 50.0f, 20.0f, "Store"));
        } else if (this.field_147015_w.getInventoryName().equals("container.chestDouble") && Resilience.getInstance().isEnabled()) {
            Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(69, Resilience.getInstance().getInvoker().getWidth() / 2 - 52, (Resilience.getInstance().getInvoker().getHeight() - this.field_146999_f) / 2 - 46, 50.0f, 20.0f, "Steal"));
            Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(100, Resilience.getInstance().getInvoker().getWidth() / 2 + 2, (Resilience.getInstance().getInvoker().getHeight() - this.field_146999_f) / 2 - 46, 50.0f, 20.0f, "Store"));
        }
        super.initGui();
        if (Resilience.getInstance().getValues().autoChestStealEnabled) {
            this.actionPerformed(new ResilienceButton(69, -100.0f, -100.0f, -100.0f, -100.0f, "AUTO-STEAL"));
        }
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        this.currentId = Resilience.getInstance().getInvoker().getId(btn);
        new Thread(){

            @Override
            public void run() {
                try {
                    if (GuiChest.this.currentId == 69) {
                        int slot = 0;
                        while (slot < GuiChest.this.field_147015_w.getSizeInventory()) {
                            Slot realSlot;
                            if (GuiChest.this.field_147002_h.inventorySlots.get(slot) != null && GuiChest.this.field_147002_h.inventorySlots.get(slot) instanceof Slot && (realSlot = (Slot)GuiChest.this.field_147002_h.inventorySlots.get(slot)).getHasStack()) {
                                Resilience.getInstance().getInvoker().clickWindow(GuiChest.this.field_147002_h.windowId, slot, 1, 0, Resilience.getInstance().getWrapper().getPlayer());
                                Thread.sleep(130);
                            }
                            ++slot;
                        }
                    } else if (GuiChest.this.currentId == 100) {
                        int slot = 0;
                        while (slot < GuiChest.this.field_147002_h.inventorySlots.size()) {
                            Slot realSlot;
                            if (GuiChest.this.field_147002_h.inventorySlots.get(slot) != null && GuiChest.this.field_147002_h.inventorySlots.get(slot) instanceof Slot && (realSlot = (Slot)GuiChest.this.field_147002_h.inventorySlots.get(slot)).getHasStack()) {
                                Resilience.getInstance().getInvoker().clickWindow(GuiChest.this.field_147002_h.windowId, slot, 1, 0, Resilience.getInstance().getWrapper().getPlayer());
                                Thread.sleep(130);
                            }
                            ++slot;
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(this.field_147015_w.isInventoryNameLocalized() ? this.field_147015_w.getInventoryName() : I18n.format(this.field_147015_w.getInventoryName(), new Object[0]), 8.0f, 6.0f, 4210752);
        this.fontRendererObj.drawString(this.field_147016_v.isInventoryNameLocalized() ? this.field_147016_v.getInventoryName() : I18n.format(this.field_147016_v.getInventoryName(), new Object[0]), 8.0f, this.field_147000_g - 96 + 2, 4210752);
    }

    @Override
    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_147017_u);
        int var4 = (this.width - this.field_146999_f) / 2;
        int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147018_x * 18 + 17);
        this.drawTexturedModalRect(var4, var5 + this.field_147018_x * 18 + 17, 0, 126, this.field_146999_f, 96);
    }

}

