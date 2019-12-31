package dsandalgo.hashmap;

import java.util.Arrays;

public class MyHashMap {

    private int[] _arr;

    /** Initialize your data structure here. */
    public MyHashMap() {
        _arr = new int[1000001];
        Arrays.fill(_arr, -1);
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        _arr[key] = value;
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        return _arr[key];
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        _arr[key] = -1;
    }
}
