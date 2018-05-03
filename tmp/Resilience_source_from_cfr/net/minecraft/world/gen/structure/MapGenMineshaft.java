/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenMineshaft
extends MapGenStructure {
    private double field_82673_e = 0.004;
    private static final String __OBFID = "CL_00000443";

    public MapGenMineshaft() {
    }

    @Override
    public String func_143025_a() {
        return "Mineshaft";
    }

    public MapGenMineshaft(Map par1Map) {
        for (Map.Entry var3 : par1Map.entrySet()) {
            if (!((String)var3.getKey()).equals("chance")) continue;
            this.field_82673_e = MathHelper.parseDoubleWithDefault((String)var3.getValue(), this.field_82673_e);
        }
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int par1, int par2) {
        if (this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2))) {
            return true;
        }
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int par1, int par2) {
        return new StructureMineshaftStart(this.worldObj, this.rand, par1, par2);
    }
}

