package dsandalgo;

import java.util.Arrays;

public class MyHashSet {

    private int[] _arr;
    /** Initialize your data structure here. */
    public MyHashSet() {
        _arr = new int[1000001];
        Arrays.fill(_arr, -1);
    }

    public void add(int key) {
        _arr[key] = 0;
    }

    public void remove(int key) {
        _arr[key] = -1;
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        if (_arr[key] == -1) {
            return false;
        } else {
            return true;
        }
    }
}
