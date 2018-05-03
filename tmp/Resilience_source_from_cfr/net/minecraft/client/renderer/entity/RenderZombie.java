/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderZombie
extends RenderBiped {
    private static final ResourceLocation zombiePigmanTextures = new ResourceLocation("textures/entity/zombie_pigman.png");
    private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    private ModelBiped field_82434_o;
    private ModelZombieVillager zombieVillagerModel;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    private int field_82431_q = 1;
    private static final String __OBFID = "CL_00001037";

    public RenderZombie() {
        super(new ModelZombie(), 0.5f, 1.0f);
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
    }

    @Override
    protected void func_82421_b() {
        this.field_82423_g = new ModelZombie(1.0f, true);
        this.field_82425_h = new ModelZombie(0.5f, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
        this.field_82436_m = new ModelZombieVillager(1.0f, 0.0f, true);
        this.field_82433_n = new ModelZombieVillager(0.5f, 0.0f, true);
    }

    protected int shouldRenderPass(EntityZombie par1EntityZombie, int par2, float par3) {
        this.func_82427_a(par1EntityZombie);
        return super.shouldRenderPass(par1EntityZombie, par2, par3);
    }

    public void doRender(EntityZombie par1EntityZombie, double par2, double par4, double par6, float par8, float par9) {
        this.func_82427_a(par1EntityZombie);
        super.doRender(par1EntityZombie, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityZombie par1EntityZombie) {
        return par1EntityZombie instanceof EntityPigZombie ? zombiePigmanTextures : (par1EntityZombie.isVillager() ? zombieVillagerTextures : zombieTextures);
    }

    protected void renderEquippedItems(EntityZombie par1EntityZombie, float par2) {
        this.func_82427_a(par1EntityZombie);
        super.renderEquippedItems(par1EntityZombie, par2);
    }

    private void func_82427_a(EntityZombie par1EntityZombie) {
        if (par1EntityZombie.isVillager()) {
            if (this.field_82431_q != this.zombieVillagerModel.func_82897_a()) {
                this.zombieVillagerModel = new ModelZombieVillager();
                this.field_82431_q = this.zombieVillagerModel.func_82897_a();
                this.field_82436_m = new ModelZombieVillager(1.0f, 0.0f, true);
                this.field_82433_n = new ModelZombieVillager(0.5f, 0.0f, true);
            }
            this.mainModel = this.zombieVillagerModel;
            this.field_82423_g = this.field_82436_m;
            this.field_82425_h = this.field_82433_n;
        } else {
            this.mainModel = this.field_82434_o;
            this.field_82423_g = this.field_82437_k;
            this.field_82425_h = this.field_82435_l;
        }
        this.modelBipedMain = (ModelBiped)this.mainModel;
    }

    protected void rotateCorpse(EntityZombie par1EntityZombie, float par2, float par3, float par4) {
        if (par1EntityZombie.isConverting()) {
            par3 += (float)(Math.cos((double)par1EntityZombie.ticksExisted * 3.25) * 3.141592653589793 * 0.25);
        }
        super.rotateCorpse(par1EntityZombie, par2, par3, par4);
    }

    @Override
    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {
        this.renderEquippedItems((EntityZombie)par1EntityLiving, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving) {
        return this.getEntityTexture((EntityZombie)par1EntityLiving);
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityZombie)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3) {
        return this.shouldRenderPass((EntityZombie)par1EntityLiving, par2, par3);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.shouldRenderPass((EntityZombie)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.renderEquippedItems((EntityZombie)par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        this.rotateCorpse((EntityZombie)par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityZombie)par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getEntityTexture((EntityZombie)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.doRender((EntityZombie)par1Entity, par2, par4, par6, par8, par9);
    }
}

