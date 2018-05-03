/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.IRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple
implements IRegistry {
    private static final Logger logger = LogManager.getLogger();
    protected final Map registryObjects;
    private static final String __OBFID = "CL_00001210";

    public RegistrySimple() {
        this.registryObjects = this.createUnderlyingMap();
    }

    protected Map createUnderlyingMap() {
        return Maps.newHashMap();
    }

    @Override
    public Object getObject(Object par1Obj) {
        return this.registryObjects.get(par1Obj);
    }

    @Override
    public void putObject(Object par1Obj, Object par2Obj) {
        if (this.registryObjects.containsKey(par1Obj)) {
            logger.warn("Adding duplicate key '" + par1Obj + "' to registry");
        }
        this.registryObjects.put(par1Obj, par2Obj);
    }

    public Set getKeys() {
        return Collections.unmodifiableSet(this.registryObjects.keySet());
    }

    public boolean containsKey(Object p_148741_1_) {
        return this.registryObjects.containsKey(p_148741_1_);
    }
}

