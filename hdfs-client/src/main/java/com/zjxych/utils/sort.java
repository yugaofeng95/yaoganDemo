package com.zjxych.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class sort {
    public static Map<String,byte[]> sortMap(Map<String,byte[]> map){
        if (map == null || map.isEmpty()) {

            return null;

        }
        Map<String, byte[]> sortMap = new TreeMap<String, byte[]>(

                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;

    }
}

