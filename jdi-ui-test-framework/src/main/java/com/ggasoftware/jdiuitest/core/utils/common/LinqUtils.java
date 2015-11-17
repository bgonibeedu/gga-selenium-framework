/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
package com.ggasoftware.jdiuitest.core.utils.common;


import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JActionT;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTT;
import com.ggasoftware.jdiuitest.core.utils.map.MapArray;
import com.ggasoftware.jdiuitest.core.utils.pairs.Pair;

import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by roman.i on 30.09.2014.
 */
public class LinqUtils {
    private LinqUtils() {
    }

    public static <T> List<T> copyList(Iterable<T> list) {
        List<T> result = new ArrayList<>();
        for (T el : list)
            result.add(el);
        return result;
    }

    public static <T, TR> List<TR> select(Iterable<T> list, JFuncTT<T, TR> func) {
        if (list == null)
            throw new RuntimeException("Can't do select. Collection is Null");
        try {
            List<TR> result = new ArrayList<>();
            for (T el : list)
                result.add(func.invoke(el));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do select. Exception: " + ex.getMessage());
        }
    }

    public static <T, TR> List<TR> select(T[] array, JFuncTT<T, TR> func) {
        return select(asList(array), func);
    }

    public static <K, V, R> List<R> selectMap(Map<K, V> map, JFuncTT<Map.Entry<K, V>, R> func) {
        if (map == null)
            throw new RuntimeException("Can't do selectMap. Collection is Null");
        try {
            List<R> result = new ArrayList<>();
            for (Map.Entry<K, V> el : map.entrySet())
                result.add(func.invoke(el));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do select. Exception: " + ex.getMessage());
        }
    }

    public static <K, V, TR> Map<K, TR> select(Map<K, V> map, JFuncTT<V, TR> func) {
        if (map == null)
            throw new RuntimeException("Can't do select. Collection is Null");
        try {
            Map<K, TR> result = new HashMap<>();
            for (Map.Entry<K, V> el : map.entrySet())
                result.put(el.getKey(), func.invoke(el.getValue()));
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do select. Exception: " + ex.getMessage());
        }
    }

    public static <T> List<T> where(Iterable<T> list, JFuncTT<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do where. Collection is Null");
        try {
            List<T> result = new ArrayList<>();
            for (T el : list)
                if (func.invoke(el))
                    result.add(el);
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do where. Exception: " + ex.getMessage());
        }
    }

    public static <T> List<T> where(T[] list, JFuncTT<T, Boolean> func) {
        return where(asList(list), func);
    }

    public static <K, V> Map<K, V> where(Map<K, V> map, JFuncTT<Map.Entry<K, V>, Boolean> func) {
        if (map == null)
            throw new RuntimeException("Can't do where. Collection is Null");
        try {
            Map<K, V> result = new HashMap<>();
            for (Map.Entry<K, V> el : map.entrySet())
                if (func.invoke(el))
                    result.put(el.getKey(), el.getValue());
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do where. Exception: " + ex.getMessage());
        }
    }

    public static <T> void foreach(Iterable<T> list, JActionT<T> action) {
        if (list == null)
            throw new RuntimeException("Can't do foreach. Collection is Null");
        try {
            for (T el : list)
                action.invoke(el);
        } catch (Exception ex) {
            throw new RuntimeException("Can't do foreach. Exception: " + ex.getMessage());
        }
    }

    public static <T> void foreach(T[] list, JActionT<T> action) {
        foreach(asList(list), action);
    }

    public static <K, V> void foreach(Map<K, V> map, JActionT<Map.Entry<K, V>> action) {
        if (map == null)
            throw new RuntimeException("Can't do foreach. Collection is Null");
        try {
            for (Map.Entry<K, V> entry : map.entrySet())
                action.invoke(entry);
        } catch (Exception ex) {
            throw new RuntimeException("Can't do foreach. Exception: " + ex.getMessage());
        }
    }

    public static <T> T first(Iterable<T> list) {
        if (list == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        for (T el : list)
            return el;
        return null;
    }

    public static <T> T first(T[] list) {
        return first(asList(list));
    }

    public static <K, V> V first(Map<K, V> map) {
        if (map == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        for (Map.Entry<K, V> el : map.entrySet())
            return el.getValue();
        return null;
    }

    public static <T> T first(Iterable<T> list, JFuncTT<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        try {
            for (T el : list)
                if (func.invoke(el))
                    return el;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do first. Exception: " + ex.getMessage());
        }
        return null;
    }

    public static <T> boolean any(Iterable<T> list, JFuncTT<T, Boolean> func) {
        return first(list, func) == null;
    }

    public static <T> int firstIndex(List<T> list, JFuncTT<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do firstIndex. Collection is Null");
        try {
            for (int i = 0; i < list.size(); i++)
                if (func.invoke(list.get(i)))
                    return i;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do firstIndex. Exception: " + ex.getMessage());
        }
        return -1;
    }

    public static <T> int firstIndex(T[] array, JFuncTT<T, Boolean> func) {
        if (array == null)
            throw new RuntimeException("Can't do firstIndex. Collection is Null");
        try {
            for (int i = 0; i < array.length; i++)
                if (func.invoke(array[i]))
                    return i;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do firstIndex. Exception: " + ex.getMessage());
        }
        return -1;
    }

    public static <T> T first(T[] list, JFuncTT<T, Boolean> func) {
        return first(asList(list), func);
    }

    public static <K, V> V first(Map<K, V> map, JFuncTT<K, Boolean> func) {
        if (map == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        try {
            for (Map.Entry<K, V> el : map.entrySet())
                if (func.invoke(el.getKey()))
                    return el.getValue();
        } catch (Exception ex) {
            throw new RuntimeException("Can't do first. Exception: " + ex.getMessage());
        }
        return null;
    }

    public static <K, V> V first(MapArray<K, V> map, JFuncTT<K, Boolean> func) {
        if (map == null)
            throw new RuntimeException("Can't do first. Collection is Null");
        try {
            for (Pair<K, V> pair : map.pairs)
                if (func.invoke(pair.key))
                    return pair.value;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do first. Exception: " + ex.getMessage());
        }
        return null;
    }

    public static <T> T last(Iterable<T> list) {
        if (list == null)
            throw new RuntimeException("Can't do last. Collection is Null");
        T result = null;
        for (T el : list)
            result = el;
        return result;
    }

    public static <T> T last(T[] list) {
        return last(asList(list));
    }

    public static <T> T last(Iterable<T> list, JFuncTT<T, Boolean> func) {
        if (list == null)
            throw new RuntimeException("Can't do last. Collection is Null");
        T result = null;
        try {
            for (T el : list)
                if (func.invoke(el))
                    result = el;
        } catch (Exception ex) {
            throw new RuntimeException("Can't do last. Exception: " + ex.getMessage());
        }
        return result;
    }

    public static <T> T last(T[] list, JFuncTT<T, Boolean> func) {
        return last(asList(list), func);
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null)
            throw new RuntimeException("Can't do toStringArray. Collection is Null");
        return collection.toArray(new String[collection.size()]);
    }

    public static int[] toIntArray(Collection<Integer> collection) {
        if (collection == null)
            throw new RuntimeException("Can't do toIntArray. Collection is Null");
        int[] result = new int[collection.size()];
        int i = 0;
        for (Integer el : collection)
            result[i++] = el;
        return result;
    }

    public static int index(String[] array, String value) {
        if (array == null)
            throw new RuntimeException("Can't do index. Collection is Null");
        for (int i = 0; i < array.length; i++)
            if (array[i].equals(value))
                return i;
        return -1;
    }

    public static <T> List<T> listCopy(List<T> array, int from, Class<T> clazz) {
        return listCopy(array, from, array.size() - 1, clazz);
    }

    public static <T> List<T> listCopy(List<T> list, int from, int to, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (int i = from; i <= to; i++)
            result.add(list.get(i));
        return result;
    }

    public static <T> List<T> selectMany(List<T> list, JFuncTT<T, List<T>> func) {
        List<T> result = new ArrayList<>();
        for (T el : list)
            result.addAll(func.invoke(el));
        return result;
    }

    public static <T> List<T> selectManyArray(List<T> list, JFuncTT<T, T[]> func) {
        List<T> result = new ArrayList<>();
        for (T el : list)
            result.addAll(asList(func.invoke(el)));
        return result;
    }

}