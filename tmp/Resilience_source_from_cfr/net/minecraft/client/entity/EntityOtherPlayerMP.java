/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.client.entity;

import com.krispdev.resilience.hooks.HookGuiIngame;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOtherPlayerMP
extends AbstractClientPlayer {
    private boolean isItemInUse;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPX;
    private double otherPlayerMPY;
    private double otherPlayerMPZ;
    private double otherPlayerMPYaw;
    private double otherPlayerMPPitch;
    private static final String __OBFID = "CL_00000939";

    public EntityOtherPlayerMP(World p_i45075_1_, GameProfile p_i45075_2_) {
        super(p_i45075_1_, p_i45075_2_);
        this.yOffset = 0.0f;
        this.stepHeight = 0.0f;
        this.noClip = true;
        this.field_71082_cx = 0.25f;
        this.renderDistanceWeight = 10.0;
    }

    @Override
    protected void resetHeight() {
        this.yOffset = 0.0f;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        return true;
    }

    @Override
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.otherPlayerMPX = par1;
        this.otherPlayerMPY = par3;
        this.otherPlayerMPZ = par5;
        this.otherPlayerMPYaw = par7;
        this.otherPlayerMPPitch = par8;
        this.otherPlayerMPPosRotationIncrements = par9;
    }

    @Override
    public void onUpdate() {
        this.field_71082_cx = 0.0f;
        super.onUpdate();
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double var1 = this.posX - this.prevPosX;
        double var3 = this.posZ - this.prevPosZ;
        float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0f;
        if (var5 > 1.0f) {
            var5 = 1.0f;
        }
        this.limbSwingAmount += (var5 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
        if (!this.isItemInUse && this.isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null) {
            ItemStack var6 = this.inventory.mainInventory[this.inventory.currentItem];
            this.setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], var6.getItem().getMaxItemUseDuration(var6));
            this.isItemInUse = true;
        } else if (this.isItemInUse && !this.isEating()) {
            this.clearItemInUse();
            this.isItemInUse = false;
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public void onLivingUpdate() {
        super.updateEntityActionState();
        if (this.otherPlayerMPPosRotationIncrements > 0) {
            double var1 = this.posX + (this.otherPlayerMPX - this.posX) / (double)this.otherPlayerMPPosRotationIncrements;
            double var3 = this.posY + (this.otherPlayerMPY - this.posY) / (double)this.otherPlayerMPPosRotationIncrements;
            double var5 = this.posZ + (this.otherPlayerMPZ - this.posZ) / (double)this.otherPlayerMPPosRotationIncrements;
            double var7 = this.otherPlayerMPYaw - (double)this.rotationYaw;
            while (var7 < -180.0) {
                var7 += 360.0;
            }
            while (var7 >= 180.0) {
                var7 -= 360.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.otherPlayerMPPitch - (double)this.rotationPitch) / (double)this.otherPlayerMPPosRotationIncrements);
            --this.otherPlayerMPPosRotationIncrements;
            this.setPosition(var1, var3, var5);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        this.prevCameraYaw = this.cameraYaw;
        float var9 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var2 = (float)Math.atan((- this.motionY) * 0.20000000298023224) * 15.0f;
        if (var9 > 0.1f) {
            var9 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            var9 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            var2 = 0.0f;
        }
        this.cameraYaw += (var9 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (var2 - this.cameraPitch) * 0.8f;
    }

    @Override
    public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
        if (par1 == 0) {
            this.inventory.mainInventory[this.inventory.currentItem] = par2ItemStack;
        } else {
            this.inventory.armorInventory[par1 - 1] = par2ItemStack;
        }
    }

    @Override
    public float getEyeHeight() {
        return 1.82f;
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(p_145747_1_);
    }

    @Override
    public boolean canCommandSenderUseCommand(int par1, String par2Str) {
        return false;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ + 0.5));
    }
}

