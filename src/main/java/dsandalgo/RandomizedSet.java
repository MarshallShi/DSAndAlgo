package dsandalgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class RandomizedSet {

    private List<Integer> data;
    private Map<Integer, Integer> map;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        data = new ArrayList<Integer>();
        map = new HashMap<Integer, Integer>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        } else {
            data.add(val);
            map.put(val, data.size() - 1);
            return true;
        }
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (map.containsKey(val)) {
            int loc = map.get(val);
            if (loc != data.size() - 1) {
                int temp = data.get(data.size() - 1);
                data.set(loc, temp);
                map.put(temp, loc);
            }
            map.remove(val);
            data.remove(data.size() - 1);
            return true;
        } else {
            return false;
        }
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random r = new Random();
        return data.get(r.nextInt(data.size()));
    }

    public static void main(String[] args) {
        RandomizedSet exe = new RandomizedSet();
        System.out.println(exe.insert(1));
        System.out.println(exe.insert(2));
        System.out.println(exe.insert(3));
        System.out.println(exe.insert(4));
        System.out.println(exe.getRandom());
        System.out.println(exe.getRandom());
        System.out.println(exe.getRandom());
        System.out.println(exe.getRandom());
        System.out.println(exe.getRandom());
        System.out.println(exe.remove(3));
        System.out.println(exe.remove(3));
        System.out.println(exe.getRandom());
        System.out.println(exe.getRandom());
        System.out.println(exe.getRandom());
        System.out.println(exe.getRandom());
    }
}

