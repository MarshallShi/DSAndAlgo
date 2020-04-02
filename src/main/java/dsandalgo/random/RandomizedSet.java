package dsandalgo.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * https://leetcode.com/problems/insert-delete-getrandom-o1/
 *
 * Design a data structure that supports all following operations in average O(1) time.
 *
 * insert(val): Inserts an item val to the set if not already present.
 * remove(val): Removes an item val from the set if present.
 * getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
 * Example:
 *
 * // Init an empty set.
 * RandomizedSet randomSet = new RandomizedSet();
 *
 * // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 * randomSet.insert(1);
 *
 * // Returns false as 2 does not exist in the set.
 * randomSet.remove(2);
 *
 * // Inserts 2 to the set, returns true. Set now contains [1,2].
 * randomSet.insert(2);
 *
 * // getRandom should return either 1 or 2 randomly.
 * randomSet.getRandom();
 *
 * // Removes 1 from the set, returns true. Set now contains [2].
 * randomSet.remove(1);
 *
 * // 2 was already in the set, so return false.
 * randomSet.insert(2);
 *
 * // Since 2 is the only number in the set, getRandom always return 2.
 * randomSet.getRandom();
 *
 * Follow up:
 * How do you modify your code to allow duplicated number?
 *
 *
 */
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

