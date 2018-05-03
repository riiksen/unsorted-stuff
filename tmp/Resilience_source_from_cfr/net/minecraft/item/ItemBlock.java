/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBlock
extends Item {
    protected final Block field_150939_a;
    private IIcon field_150938_b;
    private static final String __OBFID = "CL_00001772";

    public ItemBlock(Block p_i45328_1_) {
        this.field_150939_a = p_i45328_1_;
    }

    @Override
    public ItemBlock setUnlocalizedName(String p_150937_1_) {
        super.setUnlocalizedName(p_150937_1_);
        return this;
    }

    @Override
    public int getSpriteNumber() {
        return this.field_150939_a.getItemIconName() != null ? 1 : 0;
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return this.field_150938_b != null ? this.field_150938_b : this.field_150939_a.getBlockTextureFromSide(1);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        Block var11 = par3World.getBlock(par4, par5, par6);
        if (var11 == Blocks.snow_layer && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1) {
            par7 = 1;
        } else if (var11 != Blocks.vine && var11 != Blocks.tallgrass && var11 != Blocks.deadbush) {
            if (par7 == 0) {
                --par5;
            }
            if (par7 == 1) {
                ++par5;
            }
            if (par7 == 2) {
                --par6;
            }
            if (par7 == 3) {
                ++par6;
            }
            if (par7 == 4) {
                --par4;
            }
            if (par7 == 5) {
                ++par4;
            }
        }
        if (par1ItemStack.stackSize == 0) {
            return false;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (par5 == 255 && this.field_150939_a.getMaterial().isSolid()) {
            return false;
        }
        if (par3World.canPlaceEntityOnSide(this.field_150939_a, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {
            int var12 = this.getMetadata(par1ItemStack.getItemDamage());
            int var13 = this.field_150939_a.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var12);
            if (par3World.setBlock(par4, par5, par6, this.field_150939_a, var13, 3)) {
                if (par3World.getBlock(par4, par5, par6) == this.field_150939_a) {
                    this.field_150939_a.onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
                    this.field_150939_a.onPostBlockPlaced(par3World, par4, par5, par6, var13);
                }
                par3World.playSoundEffect((float)par4 + 0.5f, (float)par5 + 0.5f, (float)par6 + 0.5f, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150939_a.stepSound.func_150494_d() * 0.8f);
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }

    public boolean func_150936_a(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_) {
        Block var8 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
        if (var8 == Blocks.snow_layer) {
            p_150936_5_ = 1;
        } else if (var8 != Blocks.vine && var8 != Blocks.tallgrass && var8 != Blocks.deadbush) {
            if (p_150936_5_ == 0) {
                --p_150936_3_;
            }
            if (p_150936_5_ == 1) {
                ++p_150936_3_;
            }
            if (p_150936_5_ == 2) {
                --p_150936_4_;
            }
            if (p_150936_5_ == 3) {
                ++p_150936_4_;
            }
            if (p_150936_5_ == 4) {
                --p_150936_2_;
            }
            if (p_150936_5_ == 5) {
                ++p_150936_2_;
            }
        }
        return p_150936_1_.canPlaceEntityOnSide(this.field_150939_a, p_150936_2_, p_150936_3_, p_150936_4_, false, p_150936_5_, null, p_150936_7_);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return this.field_150939_a.getUnlocalizedName();
    }

    @Override
    public String getUnlocalizedName() {
        return this.field_150939_a.getUnlocalizedName();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return this.field_150939_a.getCreativeTabToDisplayOn();
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
        this.field_150939_a.getSubBlocks(p_150895_1_, p_150895_2_, p_150895_3_);
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        String var2 = this.field_150939_a.getItemIconName();
        if (var2 != null) {
            this.field_150938_b = par1IconRegister.registerIcon(var2);
        }
    }
}

