/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.src;

import java.lang.reflect.Constructor;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;

public class ReflectorConstructor {
    private ReflectorClass reflectorClass = null;
    private Class[] parameterTypes = null;
    private boolean checked = false;
    private Constructor targetConstructor = null;

    public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
        this.reflectorClass = reflectorClass;
        this.parameterTypes = parameterTypes;
        Constructor c = this.getTargetConstructor();
    }

    public Constructor getTargetConstructor() {
        if (this.checked) {
            return this.targetConstructor;
        }
        this.checked = true;
        Class cls = this.reflectorClass.getTargetClass();
        if (cls == null) {
            return null;
        }
        this.targetConstructor = ReflectorConstructor.findConstructor(cls, this.parameterTypes);
        if (this.targetConstructor == null) {
            Config.dbg("(Reflector) Constructor not present: " + cls.getName() + ", params: " + Config.arrayToString(this.parameterTypes));
        }
        if (this.targetConstructor != null && !this.targetConstructor.isAccessible()) {
            this.targetConstructor.setAccessible(true);
        }
        return this.targetConstructor;
    }

    private static Constructor findConstructor(Class cls, Class[] paramTypes) {
        Constructor<?>[] cs = cls.getDeclaredConstructors();
        int i = 0;
        while (i < cs.length) {
            Constructor c = cs[i];
            Class[] types = c.getParameterTypes();
            if (Reflector.matchesTypes(paramTypes, types)) {
                return c;
            }
            ++i;
        }
        return null;
    }
}

