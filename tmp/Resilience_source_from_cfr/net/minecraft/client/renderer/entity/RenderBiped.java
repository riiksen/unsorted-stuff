/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBiped
extends RenderLiving {
    protected ModelBiped modelBipedMain;
    protected float field_77070_b;
    protected ModelBiped field_82423_g;
    protected ModelBiped field_82425_h;
    private static final Map field_110859_k = Maps.newHashMap();
    private static final String[] bipedArmorFilenamePrefix = new String[]{"leather", "chainmail", "iron", "diamond", "gold"};
    private static final String __OBFID = "CL_00001001";

    public RenderBiped(ModelBiped par1ModelBiped, float par2) {
        this(par1ModelBiped, par2, 1.0f);
    }

    public RenderBiped(ModelBiped par1ModelBiped, float par2, float par3) {
        super(par1ModelBiped, par2);
        this.modelBipedMain = par1ModelBiped;
        this.field_77070_b = par3;
        this.func_82421_b();
    }

    protected void func_82421_b() {
        this.field_82423_g = new ModelBiped(1.0f);
        this.field_82425_h = new ModelBiped(0.5f);
    }

    public static ResourceLocation func_110857_a(ItemArmor par0ItemArmor, int par1) {
        return RenderBiped.func_110858_a(par0ItemArmor, par1, null);
    }

    public static ResourceLocation func_110858_a(ItemArmor par0ItemArmor, int par1, String par2Str) {
        Object[] arrobject = new Object[3];
        arrobject[0] = bipedArmorFilenamePrefix[par0ItemArmor.renderIndex];
        arrobject[1] = par1 == 2 ? 2 : 1;
        arrobject[2] = par2Str == null ? "" : String.format("_%s", par2Str);
        String var3 = String.format("textures/models/armor/%s_layer_%d%s.png", arrobject);
        ResourceLocation var4 = (ResourceLocation)field_110859_k.get(var3);
        if (var4 == null) {
            var4 = new ResourceLocation(var3);
            field_110859_k.put(var3, var4);
        }
        return var4;
    }

    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
        Item var5;
        ItemStack var4 = par1EntityLiving.func_130225_q(3 - par2);
        if (var4 != null && (var5 = var4.getItem()) instanceof ItemArmor) {
            ItemArmor var6 = (ItemArmor)var5;
            this.bindTexture(RenderBiped.func_110857_a(var6, par2));
            ModelBiped var7 = par2 == 2 ? this.field_82425_h : this.field_82423_g;
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

    protected void func_82408_c(EntityLiving par1EntityLivingBase, int par2, float par3) {
        Item var5;
        ItemStack var4 = par1EntityLivingBase.func_130225_q(3 - par2);
        if (var4 != null && (var5 = var4.getItem()) instanceof ItemArmor) {
            this.bindTexture(RenderBiped.func_110858_a((ItemArmor)var5, par2, "overlay"));
            float var6 = 1.0f;
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        ItemStack var10 = par1EntityLiving.getHeldItem();
        this.func_82420_a(par1EntityLiving, var10);
        double var11 = par4 - (double)par1EntityLiving.yOffset;
        if (par1EntityLiving.isSneaking()) {
            var11 -= 0.125;
        }
        super.doRender(par1EntityLiving, par2, var11, par6, par8, par9);
        this.modelBipedMain.aimedBow = false;
        this.field_82425_h.aimedBow = false;
        this.field_82423_g.aimedBow = false;
        this.modelBipedMain.isSneak = false;
        this.field_82425_h.isSneak = false;
        this.field_82423_g.isSneak = false;
        this.modelBipedMain.heldItemRight = 0;
        this.field_82425_h.heldItemRight = 0;
        this.field_82423_g.heldItemRight = 0;
    }

    protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) {
        return null;
    }

    protected void func_82420_a(EntityLiving par1EntityLiving, ItemStack par2ItemStack) {
        this.modelBipedMain.heldItemRight = par2ItemStack != null ? 1 : 0;
        this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight;
        this.field_82423_g.heldItemRight = this.modelBipedMain.heldItemRight;
        this.field_82425_h.isSneak = this.modelBipedMain.isSneak = par1EntityLiving.isSneaking();
        this.field_82423_g.isSneak = this.modelBipedMain.isSneak;
    }

    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {
        float var6;
        Item var5;
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        super.renderEquippedItems(par1EntityLiving, par2);
        ItemStack var3 = par1EntityLiving.getHeldItem();
        ItemStack var4 = par1EntityLiving.func_130225_q(3);
        if (var4 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            var5 = var4.getItem();
            if (var5 instanceof ItemBlock) {
                if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var5).getRenderType())) {
                    var6 = 0.625f;
                    GL11.glTranslatef((float)0.0f, (float)-0.25f, (float)0.0f);
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glScalef((float)var6, (float)(- var6), (float)(- var6));
                }
                this.renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 0);
            } else if (var5 == Items.skull) {
                var6 = 1.0625f;
                GL11.glScalef((float)var6, (float)(- var6), (float)(- var6));
                String var7 = "";
                if (var4.hasTagCompound() && var4.getTagCompound().func_150297_b("SkullOwner", 8)) {
                    var7 = var4.getTagCompound().getString("SkullOwner");
                }
                TileEntitySkullRenderer.field_147536_b.func_147530_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, var4.getItemDamage(), var7);
            }
            GL11.glPopMatrix();
        }
        if (var3 != null && var3.getItem() != null) {
            var5 = var3.getItem();
            GL11.glPushMatrix();
            if (this.mainModel.isChild) {
                var6 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.625f, (float)0.0f);
                GL11.glRotatef((float)-20.0f, (float)-1.0f, (float)0.0f, (float)0.0f);
                GL11.glScalef((float)var6, (float)var6, (float)var6);
            }
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef((float)-0.0625f, (float)0.4375f, (float)0.0625f);
            if (var5 instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var5).getRenderType())) {
                var6 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)(- var6), (float)(- var6), (float)(var6 *= 0.75f));
            } else if (var5 == Items.bow) {
                var6 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)0.125f, (float)0.3125f);
                GL11.glRotatef((float)-20.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)var6, (float)(- var6), (float)var6);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else if (var5.isFull3D()) {
                var6 = 0.625f;
                if (var5.shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                this.func_82422_c();
                GL11.glScalef((float)var6, (float)(- var6), (float)var6);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                var6 = 0.375f;
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                GL11.glScalef((float)var6, (float)var6, (float)var6);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            if (var3.getItem().requiresMultipleRenderPasses()) {
                int var12 = 0;
                while (var12 <= 1) {
                    int var11 = var3.getItem().getColorFromItemStack(var3, var12);
                    float var8 = (float)(var11 >> 16 & 255) / 255.0f;
                    float var9 = (float)(var11 >> 8 & 255) / 255.0f;
                    float var10 = (float)(var11 & 255) / 255.0f;
                    GL11.glColor4f((float)var8, (float)var9, (float)var10, (float)1.0f);
                    this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, var12);
                    ++var12;
                }
            } else {
                int var12 = var3.getItem().getColorFromItemStack(var3, 0);
                float var13 = (float)(var12 >> 16 & 255) / 255.0f;
                float var8 = (float)(var12 >> 8 & 255) / 255.0f;
                float var9 = (float)(var12 & 255) / 255.0f;
                GL11.glColor4f((float)var13, (float)var8, (float)var9, (float)1.0f);
                this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 0);
            }
            GL11.glPopMatrix();
        }
    }

    protected void func_82422_c() {
        GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
    }

    @Override
    protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        this.func_82408_c((EntityLiving)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityLiving)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityLiving)par1EntityLivingBase, par2);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityLiving)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
    }
}

