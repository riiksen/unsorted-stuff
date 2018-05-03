/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.demo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class DemoWorldManager
extends ItemInWorldManager {
    private boolean field_73105_c;
    private boolean demoTimeExpired;
    private int field_73104_e;
    private int field_73102_f;
    private static final String __OBFID = "CL_00001429";

    public DemoWorldManager(World par1World) {
        super(par1World);
    }

    @Override
    public void updateBlockRemoving() {
        super.updateBlockRemoving();
        ++this.field_73102_f;
        long var1 = this.theWorld.getTotalWorldTime();
        long var3 = var1 / 24000 + 1;
        if (!this.field_73105_c && this.field_73102_f > 20) {
            this.field_73105_c = true;
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 0.0f));
        }
        boolean bl = this.demoTimeExpired = var1 > 120500;
        if (this.demoTimeExpired) {
            ++this.field_73104_e;
        }
        if (var1 % 24000 == 500) {
            if (var3 <= 6) {
                this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day." + var3, new Object[0]));
            }
        } else if (var3 == 1) {
            if (var1 == 100) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 101.0f));
            } else if (var1 == 175) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 102.0f));
            } else if (var1 == 250) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 103.0f));
            }
        } else if (var3 == 5 && var1 % 24000 == 22000) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day.warning", new Object[0]));
        }
    }

    private void sendDemoReminder() {
        if (this.field_73104_e > 100) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.reminder", new Object[0]));
            this.field_73104_e = 0;
        }
    }

    @Override
    public void onBlockClicked(int par1, int par2, int par3, int par4) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
        } else {
            super.onBlockClicked(par1, par2, par3, par4);
        }
    }

    @Override
    public void uncheckedTryHarvestBlock(int par1, int par2, int par3) {
        if (!this.demoTimeExpired) {
            super.uncheckedTryHarvestBlock(par1, par2, par3);
        }
    }

    @Override
    public boolean tryHarvestBlock(int par1, int par2, int par3) {
        return this.demoTimeExpired ? false : super.tryHarvestBlock(par1, par2, par3);
    }

    @Override
    public boolean tryUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.tryUseItem(par1EntityPlayer, par2World, par3ItemStack);
    }

    @Override
    public boolean activateBlockOrUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.activateBlockOrUseItem(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
    }
}

