/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.Charsets
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.krispdev.resilience.hooks.HookEntityClientPlayerMP;
import java.nio.charset.Charset;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.io.Charsets;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiRepair
extends GuiContainer
implements ICrafting {
    private static final ResourceLocation field_147093_u = new ResourceLocation("textures/gui/container/anvil.png");
    private ContainerRepair field_147092_v;
    private GuiTextField field_147091_w;
    private InventoryPlayer field_147094_x;
    private static final String __OBFID = "CL_00000738";

    public GuiRepair(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
        super(new ContainerRepair(par1InventoryPlayer, par2World, par3, par4, par5, Minecraft.getMinecraft().thePlayer));
        this.field_147094_x = par1InventoryPlayer;
        this.field_147092_v = (ContainerRepair)this.field_147002_h;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents((boolean)true);
        int var1 = (this.width - this.field_146999_f) / 2;
        int var2 = (this.height - this.field_147000_g) / 2;
        this.field_147091_w = new GuiTextField(this.fontRendererObj, var1 + 62, var2 + 24, 103, 12);
        this.field_147091_w.func_146193_g(-1);
        this.field_147091_w.func_146204_h(-1);
        this.field_147091_w.func_146185_a(false);
        this.field_147091_w.func_146203_f(40);
        this.field_147002_h.removeCraftingFromCrafters(this);
        this.field_147002_h.addCraftingToCrafters(this);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents((boolean)false);
        this.field_147002_h.removeCraftingFromCrafters(this);
    }

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3042);
        this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60.0f, 6.0f, 4210752);
        if (this.field_147092_v.maximumCost > 0) {
            int var3 = 8453920;
            boolean var4 = true;
            String var5 = I18n.format("container.repair.cost", this.field_147092_v.maximumCost);
            if (this.field_147092_v.maximumCost >= 40 && !this.mc.thePlayer.capabilities.isCreativeMode) {
                var5 = I18n.format("container.repair.expensive", new Object[0]);
                var3 = 16736352;
            } else if (!this.field_147092_v.getSlot(2).getHasStack()) {
                var4 = false;
            } else if (!this.field_147092_v.getSlot(2).canTakeStack(this.field_147094_x.player)) {
                var3 = 16736352;
            }
            if (var4) {
                int var6 = -16777216 | (var3 & 16579836) >> 2 | var3 & -16777216;
                int var7 = this.field_146999_f - 8 - this.fontRendererObj.getStringWidth(var5);
                int var8 = 67;
                if (this.fontRendererObj.getUnicodeFlag()) {
                    GuiRepair.drawRect(var7 - 3, var8 - 2, this.field_146999_f - 7, var8 + 10, -16777216);
                    GuiRepair.drawRect(var7 - 2, var8 - 1, this.field_146999_f - 8, var8 + 9, -12895429);
                } else {
                    this.fontRendererObj.drawString(var5, var7, var8 + 1, var6);
                    this.fontRendererObj.drawString(var5, var7 + 1, var8, var6);
                    this.fontRendererObj.drawString(var5, var7 + 1, var8 + 1, var6);
                }
                this.fontRendererObj.drawString(var5, var7, var8, var3);
            }
        }
        GL11.glEnable((int)2896);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (this.field_147091_w.textboxKeyTyped(par1, par2)) {
            this.func_147090_g();
        } else {
            super.keyTyped(par1, par2);
        }
    }

    private void func_147090_g() {
        String var1 = this.field_147091_w.getText();
        Slot var2 = this.field_147092_v.getSlot(0);
        if (var2 != null && var2.getHasStack() && !var2.getStack().hasDisplayName() && var1.equals(var2.getStack().getDisplayName())) {
            var1 = "";
        }
        this.field_147092_v.updateItemName(var1);
        this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|ItemName", var1.getBytes(Charsets.UTF_8)));
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_147091_w.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3042);
        this.field_147091_w.drawTextBox();
    }

    @Override
    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(field_147093_u);
        int var4 = (this.width - this.field_146999_f) / 2;
        int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        this.drawTexturedModalRect(var4 + 59, var5 + 20, 0, this.field_147000_g + (this.field_147092_v.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
        if ((this.field_147092_v.getSlot(0).getHasStack() || this.field_147092_v.getSlot(1).getHasStack()) && !this.field_147092_v.getSlot(2).getHasStack()) {
            this.drawTexturedModalRect(var4 + 99, var5 + 45, this.field_146999_f, 0, 28, 21);
        }
    }

    @Override
    public void sendContainerAndContentsToPlayer(Container par1Container, List par2List) {
        this.sendSlotContents(par1Container, 0, par1Container.getSlot(0).getStack());
    }

    @Override
    public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack) {
        if (par2 == 0) {
            this.field_147091_w.setText(par3ItemStack == null ? "" : par3ItemStack.getDisplayName());
            this.field_147091_w.func_146184_c(par3ItemStack != null);
            if (par3ItemStack != null) {
                this.func_147090_g();
            }
        }
    }

    @Override
    public void sendProgressBarUpdate(Container par1Container, int par2, int par3) {
    }
}

