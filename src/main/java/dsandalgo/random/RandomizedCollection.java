package dsandalgo.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RandomizedCollection {

    private List<Integer> data;
    private Map<Integer, Set<Integer>> map;

    /** Initialize your data structure here. */
    public RandomizedCollection() {
        data = new ArrayList<Integer>();
        map = new HashMap<Integer, Set<Integer>>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        boolean ret = false;
        if (!map.containsKey(val)) {
            ret = true;
        }
        data.add(val);
        map.putIfAbsent(val, new HashSet<Integer>());
        map.get(val).add(data.size() - 1);
        return ret;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (map.containsKey(val)) {
            Set<Integer> set = map.get(val);
            int loc = data.size() - 1;
            if (!set.contains(data.size() - 1)) {
                Object[] arr = set.toArray();
                loc = (int)arr[0];
                int temp = data.get(data.size() - 1);
                data.set(loc, temp);
                map.get(temp).remove(data.size() - 1);
                map.get(temp).add(loc);
            }
            map.get(val).remove(loc);
            if (map.get(val).size() == 0) {
                map.remove(val);
            }
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
        RandomizedCollection exe = new RandomizedCollection();
        System.out.println(exe.insert(1));
        System.out.println(exe.insert(1));
        System.out.println(exe.insert(1));
        System.out.println(exe.insert(1));
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
