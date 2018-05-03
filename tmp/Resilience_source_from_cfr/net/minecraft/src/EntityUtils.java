/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorField;
import net.minecraft.src.ReflectorMethod;

public class EntityUtils {
    private static ReflectorClass ForgeEntityLivingBase = new ReflectorClass(EntityLivingBase.class);
    private static ReflectorField ForgeEntityLivingBase_entityAge = new ReflectorField(ForgeEntityLivingBase, "entityAge");
    private static boolean directEntityAge = true;
    private static ReflectorClass ForgeEntityLiving = new ReflectorClass(EntityLiving.class);
    private static ReflectorMethod ForgeEntityLiving_despawnEntity = new ReflectorMethod(ForgeEntityLiving, "despawnEntity");
    private static boolean directDespawnEntity = true;

    public static int getEntityAge(EntityLivingBase elb) {
        block3 : {
            if (directEntityAge) {
                try {
                    return elb.entityAge;
                }
                catch (IllegalAccessError var2) {
                    directEntityAge = false;
                    if (ForgeEntityLivingBase_entityAge.exists()) break block3;
                    throw var2;
                }
            }
        }
        return (Integer)Reflector.getFieldValue(elb, ForgeEntityLivingBase_entityAge);
    }

    public static void setEntityAge(EntityLivingBase elb, int age) {
        block3 : {
            if (directEntityAge) {
                try {
                    elb.entityAge = age;
                    return;
                }
                catch (IllegalAccessError var3) {
                    directEntityAge = false;
                    if (ForgeEntityLivingBase_entityAge.exists()) break block3;
                    throw var3;
                }
            }
        }
        Reflector.setFieldValue(elb, ForgeEntityLivingBase_entityAge, age);
    }

    public static void despawnEntity(EntityLiving el) {
        block3 : {
            if (directDespawnEntity) {
                try {
                    el.despawnEntity();
                    return;
                }
                catch (IllegalAccessError var2) {
                    directDespawnEntity = false;
                    if (ForgeEntityLiving_despawnEntity.exists()) break block3;
                    throw var2;
                }
            }
        }
        Reflector.callVoid(el, ForgeEntityLiving_despawnEntity, new Object[0]);
    }
}

