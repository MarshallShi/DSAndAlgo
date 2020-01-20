package dsandalgo.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeMap {

    public static void main(String[] args) {
        TimeMap tm = new TimeMap();
        tm.set("key1", "value1", 5);
        tm.set("key1", "value2", 10);
        tm.set("key1", "value3", 15);
        tm.set("key1", "value4", 25);
        tm.set("key1", "value5", 35);

        tm.set("key2", "value6", 40);
        System.out.println(tm.get("key1", 6));
        System.out.println(tm.get("key1", 10));
        System.out.println(tm.get("key1", 26));
        System.out.println(tm.get("key3", 10));
    }

    Map<String, List<String>> dataValueMap;
    Map<String, List<Integer>> dataTimeMap;

    public TimeMap() {
        dataValueMap = new HashMap<String, List<String>>();
        dataTimeMap = new HashMap<String, List<Integer>>();
    }

    public void set(String key, String value, int timestamp) {
        dataValueMap.putIfAbsent(key, new ArrayList<String>());
        dataValueMap.get(key).add(value);
        dataTimeMap.putIfAbsent(key, new ArrayList<Integer>());
        dataTimeMap.get(key).add(timestamp);
    }

    public String get(String key, int timestamp) {
        if (dataValueMap.containsKey(key)) {
            List<Integer> list = dataTimeMap.get(key);
            List<String> valList = dataValueMap.get(key);
            int idx = Collections.binarySearch(list, timestamp);
            if (idx >= 0) {
                //found the same value
                return valList.get(idx);
            } else {
                //the insertion position
                int insertion = -1 * idx - 1;
                if (insertion > 0) {
                    return valList.get(insertion - 1);
                }
            }
        }
        return "";
    }
}

