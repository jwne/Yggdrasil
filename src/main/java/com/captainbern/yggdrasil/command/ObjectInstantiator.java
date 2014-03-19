/*
 * WorldEdit
 * Copyright (C) 2010 sk89q <http://www.sk89q.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package com.captainbern.yggdrasil.command;

import com.captainbern.yggdrasil.internal.Yggdrasil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectInstantiator {

    private Object[] argInstances;
    private Class[] argClasses;

    public ObjectInstantiator(Object... args) {
        this.argInstances = args;
        this.argClasses = new Class[args.length];

        for(int i = 0; i < args.length; i++) {
            argClasses[i] = argInstances[i].getClass();
        }
    }

    public Object instantiate(Class<?> clazz) {
        try {
            Constructor constructor = clazz.getConstructor(this.argClasses);
            constructor.setAccessible(true);
            return constructor.newInstance(this.argInstances);
        } catch (InvocationTargetException e) {
            Yggdrasil.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            Yggdrasil.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            Yggdrasil.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            Yggdrasil.LOGGER_REFLECTION.warning("Failed to instantiate commands class: " + clazz.getName());
            e.printStackTrace();
            return null;
        }
    }
}
