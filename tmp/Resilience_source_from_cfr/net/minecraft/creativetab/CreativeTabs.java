/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.creativetab;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryNamespaced;

public abstract class CreativeTabs {
    public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
    public static final CreativeTabs tabBlock = new CreativeTabs(0, "buildingBlocks"){
        private static final String __OBFID = "CL_00000010";

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.brick_block);
        }
    };
    public static final CreativeTabs tabDecorations = new CreativeTabs(1, "decorations"){
        private static final String __OBFID = "CL_00000011";

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.double_plant);
        }

        @Override
        public int func_151243_f() {
            return 5;
        }
    };
    public static final CreativeTabs tabRedstone = new CreativeTabs(2, "redstone"){
        private static final String __OBFID = "CL_00000012";

        @Override
        public Item getTabIconItem() {
            return Items.redstone;
        }
    };
    public static final CreativeTabs tabTransport = new CreativeTabs(3, "transportation"){
        private static final String __OBFID = "CL_00000014";

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.golden_rail);
        }
    };
    public static final CreativeTabs tabMisc = new CreativeTabs(4, "misc"){
        private static final String __OBFID = "CL_00000015";

        @Override
        public Item getTabIconItem() {
            return Items.lava_bucket;
        }
    }.func_111229_a(EnumEnchantmentType.all);
    public static final CreativeTabs tabAllSearch = new CreativeTabs(5, "search"){
        private static final String __OBFID = "CL_00000016";

        @Override
        public Item getTabIconItem() {
            return Items.compass;
        }
    }.setBackgroundImageName("item_search.png");
    public static final CreativeTabs tabFood = new CreativeTabs(6, "food"){
        private static final String __OBFID = "CL_00000017";

        @Override
        public Item getTabIconItem() {
            return Items.apple;
        }
    };
    public static final CreativeTabs tabTools = new CreativeTabs(7, "tools"){
        private static final String __OBFID = "CL_00000018";

        @Override
        public Item getTabIconItem() {
            return Items.iron_axe;
        }
    }.func_111229_a(EnumEnchantmentType.digger, EnumEnchantmentType.fishing_rod, EnumEnchantmentType.breakable);
    public static final CreativeTabs tabCombat = new CreativeTabs(8, "combat"){
        private static final String __OBFID = "CL_00000007";

        @Override
        public Item getTabIconItem() {
            return Items.golden_sword;
        }
    }.func_111229_a(EnumEnchantmentType.armor, EnumEnchantmentType.armor_feet, EnumEnchantmentType.armor_head, EnumEnchantmentType.armor_legs, EnumEnchantmentType.armor_torso, EnumEnchantmentType.bow, EnumEnchantmentType.weapon);
    public static final CreativeTabs tabBrewing = new CreativeTabs(9, "brewing"){
        private static final String __OBFID = "CL_00000008";

        @Override
        public Item getTabIconItem() {
            return Items.potionitem;
        }
    };
    public static final CreativeTabs tabMaterials = new CreativeTabs(10, "materials"){
        private static final String __OBFID = "CL_00000009";

        @Override
        public Item getTabIconItem() {
            return Items.stick;
        }
    };
    public static final CreativeTabs tabInventory = new CreativeTabs(11, "inventory"){
        private static final String __OBFID = "CL_00000006";

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.chest);
        }
    }.setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
    private final int tabIndex;
    private final String tabLabel;
    private String backgroundImageName = "items.png";
    private boolean hasScrollbar = true;
    private boolean drawTitle = true;
    private EnumEnchantmentType[] field_111230_s;
    private ItemStack field_151245_t;
    private static final String __OBFID = "CL_00000005";

    public CreativeTabs(int par1, String par2Str) {
        this.tabIndex = par1;
        this.tabLabel = par2Str;
        CreativeTabs.creativeTabArray[par1] = this;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public String getTabLabel() {
        return this.tabLabel;
    }

    public String getTranslatedTabLabel() {
        return "itemGroup." + this.getTabLabel();
    }

    public ItemStack getIconItemStack() {
        if (this.field_151245_t == null) {
            this.field_151245_t = new ItemStack(this.getTabIconItem(), 1, this.func_151243_f());
        }
        return this.field_151245_t;
    }

    public abstract Item getTabIconItem();

    public int func_151243_f() {
        return 0;
    }

    public String getBackgroundImageName() {
        return this.backgroundImageName;
    }

    public CreativeTabs setBackgroundImageName(String par1Str) {
        this.backgroundImageName = par1Str;
        return this;
    }

    public boolean drawInForegroundOfTab() {
        return this.drawTitle;
    }

    public CreativeTabs setNoTitle() {
        this.drawTitle = false;
        return this;
    }

    public boolean shouldHidePlayerInventory() {
        return this.hasScrollbar;
    }

    public CreativeTabs setNoScrollbar() {
        this.hasScrollbar = false;
        return this;
    }

    public int getTabColumn() {
        return this.tabIndex % 6;
    }

    public boolean isTabInFirstRow() {
        if (this.tabIndex < 6) {
            return true;
        }
        return false;
    }

    public EnumEnchantmentType[] func_111225_m() {
        return this.field_111230_s;
    }

    public /* varargs */ CreativeTabs func_111229_a(EnumEnchantmentType ... par1ArrayOfEnumEnchantmentType) {
        this.field_111230_s = par1ArrayOfEnumEnchantmentType;
        return this;
    }

    public boolean func_111226_a(EnumEnchantmentType par1EnumEnchantmentType) {
        if (this.field_111230_s == null) {
            return false;
        }
        EnumEnchantmentType[] var2 = this.field_111230_s;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            EnumEnchantmentType var5 = var2[var4];
            if (var5 == par1EnumEnchantmentType) {
                return true;
            }
            ++var4;
        }
        return false;
    }

    public void displayAllReleventItems(List par1List) {
        for (Item var3 : Item.itemRegistry) {
            if (var3 == null || var3.getCreativeTab() != this) continue;
            var3.getSubItems(var3, this, par1List);
        }
        if (this.func_111225_m() != null) {
            this.addEnchantmentBooksToList(par1List, this.func_111225_m());
        }
    }

    public /* varargs */ void addEnchantmentBooksToList(List par1List, EnumEnchantmentType ... par2ArrayOfEnumEnchantmentType) {
        Enchantment[] var3 = Enchantment.enchantmentsList;
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            Enchantment var6 = var3[var5];
            if (var6 != null && var6.type != null) {
                boolean var7 = false;
                int var8 = 0;
                while (var8 < par2ArrayOfEnumEnchantmentType.length && !var7) {
                    if (var6.type == par2ArrayOfEnumEnchantmentType[var8]) {
                        var7 = true;
                    }
                    ++var8;
                }
                if (var7) {
                    par1List.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var6, var6.getMaxLevel())));
                }
            }
            ++var5;
        }
    }

}

