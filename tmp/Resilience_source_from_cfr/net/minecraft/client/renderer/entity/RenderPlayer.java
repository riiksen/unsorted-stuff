/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPlayer
extends RendererLivingEntity {
    private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String __OBFID = "CL_00001020";

    public RenderPlayer() {
        super(new ModelBiped(0.0f), 0.5f);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0f);
        this.modelArmor = new ModelBiped(0.5f);
    }

    protected int shouldRenderPass(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3) {
        Item var5;
        ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);
        if (var4 != null && (var5 = var4.getItem()) instanceof ItemArmor) {
            ItemArmor var6 = (ItemArmor)var5;
            this.bindTexture(RenderBiped.func_110857_a(var6, par2));
            ModelBiped var7 = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
            var7.bipedHead.showModel = par2 == 0;
            var7.bipedHeadwear.showModel = par2 == 0;
            var7.bipedBody.showModel = par2 == 1 || par2 == 2;
            var7.bipedRightArm.showModel = par2 == 1;
            var7.bipedLeftArm.showModel = par2 == 1;
            var7.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
            var7.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
            this.setRenderPassModel(var7);
            var7.onGround = this.mainModel.onGround;
            var7.isRiding = this.mainModel.isRiding;
            var7.isChild = this.mainModel.isChild;
            if (var6.getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH) {
                int var8 = var6.getColor(var4);
                float var9 = (float)(var8 >> 16 & 255) / 255.0f;
                float var10 = (float)(var8 >> 8 & 255) / 255.0f;
                float var11 = (float)(var8 & 255) / 255.0f;
                GL11.glColor3f((float)var9, (float)var10, (float)var11);
                if (var4.isItemEnchanted()) {
                    return 31;
                }
                return 16;
            }
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            if (var4.isItemEnchanted()) {
                return 15;
            }
            return 1;
        }
        return -1;
    }

    protected void func_82408_c(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3) {
        Item var5;
        ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);
        if (var4 != null && (var5 = var4.getItem()) instanceof ItemArmor) {
            this.bindTexture(RenderBiped.func_110858_a((ItemArmor)var5, par2, "overlay"));
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public void doRender(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, float par8, float par9) {
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        ItemStack var10 = par1AbstractClientPlayer.inventory.getCurrentItem();
        this.modelBipedMain.heldItemRight = var10 != null ? 1 : 0;
        this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight;
        this.modelArmorChestplate.heldItemRight = this.modelBipedMain.heldItemRight;
        if (var10 != null && par1AbstractClientPlayer.getItemInUseCount() > 0) {
            EnumAction var11 = var10.getItemUseAction();
            if (var11 == EnumAction.block) {
                this.modelBipedMain.heldItemRight = 3;
                this.modelArmor.heldItemRight = 3;
                this.modelArmorChestplate.heldItemRight = 3;
            } else if (var11 == EnumAction.bow) {
                this.modelBipedMain.aimedBow = true;
                this.modelArmor.aimedBow = true;
                this.modelArmorChestplate.aimedBow = true;
            }
        }
        this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1AbstractClientPlayer.isSneaking();
        this.modelArmorChestplate.isSneak = this.modelBipedMain.isSneak;
        double var13 = par4 - (double)par1AbstractClientPlayer.yOffset;
        if (par1AbstractClientPlayer.isSneaking() && !(par1AbstractClientPlayer instanceof EntityPlayerSP)) {
            var13 -= 0.125;
        }
        super.doRender(par1AbstractClientPlayer, par2, var13, par6, par8, par9);
        this.modelBipedMain.aimedBow = false;
        this.modelArmor.aimedBow = false;
        this.modelArmorChestplate.aimedBow = false;
        this.modelBipedMain.isSneak = false;
        this.modelArmor.isSneak = false;
        this.modelArmorChestplate.isSneak = false;
        this.modelBipedMain.heldItemRight = 0;
        this.modelArmor.heldItemRight = 0;
        this.modelArmorChestplate.heldItemRight = 0;
    }

    protected ResourceLocation getEntityTexture(AbstractClientPlayer par1AbstractClientPlayer) {
        return par1AbstractClientPlayer.getLocationSkin();
    }

    protected void renderEquippedItems(AbstractClientPlayer par1AbstractClientPlayer, float par2) {
        boolean var21;
        float var11;
        ItemStack var23;
        float var7;
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        super.renderEquippedItems(par1AbstractClientPlayer, par2);
        super.renderArrowsStuckInEntity(par1AbstractClientPlayer, par2);
        ItemStack var3 = par1AbstractClientPlayer.inventory.armorItemInSlot(3);
        if (var3 != null) {
            float var4;
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            if (var3.getItem() instanceof ItemBlock) {
                if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType())) {
                    var4 = 0.625f;
                    GL11.glTranslatef((float)0.0f, (float)-0.25f, (float)0.0f);
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glScalef((float)var4, (float)(- var4), (float)(- var4));
                }
                this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var3, 0);
            } else if (var3.getItem() == Items.skull) {
                var4 = 1.0625f;
                GL11.glScalef((float)var4, (float)(- var4), (float)(- var4));
                String var5 = "";
                if (var3.hasTagCompound() && var3.getTagCompound().func_150297_b("SkullOwner", 8)) {
                    var5 = var3.getTagCompound().getString("SkullOwner");
                }
                TileEntitySkullRenderer.field_147536_b.func_147530_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, var3.getItemDamage(), var5);
            }
            GL11.glPopMatrix();
        }
        if (par1AbstractClientPlayer.getCommandSenderName().equals("deadmau5") && par1AbstractClientPlayer.getTextureSkin().isTextureUploaded()) {
            this.bindTexture(par1AbstractClientPlayer.getLocationSkin());
            int var20 = 0;
            while (var20 < 2) {
                float var22 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
                float var6 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
                GL11.glPushMatrix();
                GL11.glRotatef((float)var22, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)var6, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)(0.375f * (float)(var20 * 2 - 1)), (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)-0.375f, (float)0.0f);
                GL11.glRotatef((float)(- var6), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(- var22), (float)0.0f, (float)1.0f, (float)0.0f);
                var7 = 1.3333334f;
                GL11.glScalef((float)var7, (float)var7, (float)var7);
                this.modelBipedMain.renderEars(0.0625f);
                GL11.glPopMatrix();
                ++var20;
            }
        }
        if ((var21 = par1AbstractClientPlayer.getTextureCape().isTextureUploaded()) && !par1AbstractClientPlayer.isInvisible() && !par1AbstractClientPlayer.getHideCape()) {
            this.bindTexture(par1AbstractClientPlayer.getLocationCape());
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)0.125f);
            double var25 = par1AbstractClientPlayer.field_71091_bM + (par1AbstractClientPlayer.field_71094_bP - par1AbstractClientPlayer.field_71091_bM) * (double)par2 - (par1AbstractClientPlayer.prevPosX + (par1AbstractClientPlayer.posX - par1AbstractClientPlayer.prevPosX) * (double)par2);
            double var26 = par1AbstractClientPlayer.field_71096_bN + (par1AbstractClientPlayer.field_71095_bQ - par1AbstractClientPlayer.field_71096_bN) * (double)par2 - (par1AbstractClientPlayer.prevPosY + (par1AbstractClientPlayer.posY - par1AbstractClientPlayer.prevPosY) * (double)par2);
            double var9 = par1AbstractClientPlayer.field_71097_bO + (par1AbstractClientPlayer.field_71085_bR - par1AbstractClientPlayer.field_71097_bO) * (double)par2 - (par1AbstractClientPlayer.prevPosZ + (par1AbstractClientPlayer.posZ - par1AbstractClientPlayer.prevPosZ) * (double)par2);
            var11 = par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2;
            double var12 = MathHelper.sin(var11 * 3.1415927f / 180.0f);
            double var14 = - MathHelper.cos(var11 * 3.1415927f / 180.0f);
            float var16 = (float)var26 * 10.0f;
            if (var16 < -6.0f) {
                var16 = -6.0f;
            }
            if (var16 > 32.0f) {
                var16 = 32.0f;
            }
            float var17 = (float)(var25 * var12 + var9 * var14) * 100.0f;
            float var18 = (float)(var25 * var14 - var9 * var12) * 100.0f;
            if (var17 < 0.0f) {
                var17 = 0.0f;
            }
            float var19 = par1AbstractClientPlayer.prevCameraYaw + (par1AbstractClientPlayer.cameraYaw - par1AbstractClientPlayer.prevCameraYaw) * par2;
            var16 += MathHelper.sin((par1AbstractClientPlayer.prevDistanceWalkedModified + (par1AbstractClientPlayer.distanceWalkedModified - par1AbstractClientPlayer.prevDistanceWalkedModified) * par2) * 6.0f) * 32.0f * var19;
            if (par1AbstractClientPlayer.isSneaking()) {
                var16 += 25.0f;
            }
            GL11.glRotatef((float)(6.0f + var17 / 2.0f + var16), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(var18 / 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)((- var18) / 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.modelBipedMain.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
        if ((var23 = par1AbstractClientPlayer.inventory.getCurrentItem()) != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef((float)-0.0625f, (float)0.4375f, (float)0.0625f);
            if (par1AbstractClientPlayer.fishEntity != null) {
                var23 = new ItemStack(Items.stick);
            }
            EnumAction var24 = null;
            if (par1AbstractClientPlayer.getItemInUseCount() > 0) {
                var24 = var23.getItemUseAction();
            }
            if (var23.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var23.getItem()).getRenderType())) {
                var7 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)(- var7), (float)(- var7), (float)(var7 *= 0.75f));
            } else if (var23.getItem() == Items.bow) {
                var7 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)0.125f, (float)0.3125f);
                GL11.glRotatef((float)-20.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var7, (float)(- var7), (float)var7);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (var23.getItem().isFull3D()) {
                var7 = 0.625f;
                if (var23.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                if (par1AbstractClientPlayer.getItemInUseCount() > 0 && var24 == EnumAction.block) {
                    GL11.glTranslatef((float)0.05f, (float)0.0f, (float)-0.1f);
                    GL11.glRotatef((float)-50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)-10.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glRotatef((float)-60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                }
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
                GL11.glScalef((float)var7, (float)(- var7), (float)var7);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                var7 = 0.375f;
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                GL11.glScalef((float)var7, (float)var7, (float)var7);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            if (var23.getItem().requiresMultipleRenderPasses()) {
                int var28 = 0;
                while (var28 <= 1) {
                    int var8 = var23.getItem().getColorFromItemStack(var23, var28);
                    float var29 = (float)(var8 >> 16 & 255) / 255.0f;
                    float var10 = (float)(var8 >> 8 & 255) / 255.0f;
                    var11 = (float)(var8 & 255) / 255.0f;
                    GL11.glColor4f((float)var29, (float)var10, (float)var11, (float)1.0f);
                    this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var23, var28);
                    ++var28;
                }
            } else {
                int var28 = var23.getItem().getColorFromItemStack(var23, 0);
                float var27 = (float)(var28 >> 16 & 255) / 255.0f;
                float var29 = (float)(var28 >> 8 & 255) / 255.0f;
                float var10 = (float)(var28 & 255) / 255.0f;
                GL11.glColor4f((float)var27, (float)var29, (float)var10, (float)1.0f);
                this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var23, 0);
            }
            GL11.glPopMatrix();
        }
    }

    protected void preRenderCallback(AbstractClientPlayer par1AbstractClientPlayer, float par2) {
        float var3 = 0.9375f;
        GL11.glScalef((float)var3, (float)var3, (float)var3);
    }

    protected void func_96449_a(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10) {
        ScoreObjective var13;
        Scoreboard var12;
        if (par10 < 100.0 && (var13 = (var12 = par1AbstractClientPlayer.getWorldScoreboard()).func_96539_a(2)) != null) {
            Score var14 = var12.func_96529_a(par1AbstractClientPlayer.getCommandSenderName(), var13);
            if (par1AbstractClientPlayer.isPlayerSleeping()) {
                this.func_147906_a(par1AbstractClientPlayer, String.valueOf(var14.getScorePoints()) + " " + var13.getDisplayName(), par2, par4 - 1.5, par6, 64);
            } else {
                this.func_147906_a(par1AbstractClientPlayer, String.valueOf(var14.getScorePoints()) + " " + var13.getDisplayName(), par2, par4, par6, 64);
            }
            par4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15f * par9);
        }
        super.func_96449_a(par1AbstractClientPlayer, par2, par4, par6, par8Str, par9, par10);
    }

    public void renderFirstPersonArm(EntityPlayer par1EntityPlayer) {
        float var2 = 1.0f;
        GL11.glColor3f((float)var2, (float)var2, (float)var2);
        this.modelBipedMain.onGround = 0.0f;
        this.modelBipedMain.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, par1EntityPlayer);
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }

    protected void renderLivingAt(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6) {
        if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping()) {
            super.renderLivingAt(par1AbstractClientPlayer, par2 + (double)par1AbstractClientPlayer.field_71079_bU, par4 + (double)par1AbstractClientPlayer.field_71082_cx, par6 + (double)par1AbstractClientPlayer.field_71089_bV);
        } else {
            super.renderLivingAt(par1AbstractClientPlayer, par2, par4, par6);
        }
    }

    protected void rotateCorpse(AbstractClientPlayer par1AbstractClientPlayer, float par2, float par3, float par4) {
        if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping()) {
            GL11.glRotatef((float)par1AbstractClientPlayer.getBedOrientationInDegrees(), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.getDeathMaxRotation(par1AbstractClientPlayer), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        } else {
            super.rotateCorpse(par1AbstractClientPlayer, par2, par3, par4);
        }
    }

    @Override
    protected void func_96449_a(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, String par8Str, float par9, double par10) {
        this.func_96449_a((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8Str, par9, par10);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderCallback((AbstractClientPlayer)par1EntityLivingBase, par2);
    }

    @Override
    protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        this.func_82408_c((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((AbstractClientPlayer)par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        this.rotateCorpse((AbstractClientPlayer)par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
        this.renderLivingAt((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6);
    }

    @Override
    public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((AbstractClientPlayer)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((AbstractClientPlayer)par1Entity, par2, par4, par6, par8, par9);
    }
}

