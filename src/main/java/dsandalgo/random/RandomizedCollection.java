package dsandalgo.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
 * Design a data structure that supports all following operations in average O(1) time.
 *
 * Note: Duplicate elements are allowed.
 * insert(val): Inserts an item val to the collection.
 * remove(val): Removes an item val from the collection if present.
 * getRandom: Returns a random element from current collection of elements. The probability of each element being returned is linearly related to the number of same value the collection contains.
 * Example:
 *
 * // Init an empty collection.
 * RandomizedCollection collection = new RandomizedCollection();
 *
 * // Inserts 1 to the collection. Returns true as the collection did not contain 1.
 * collection.insert(1);
 *
 * // Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
 * collection.insert(1);
 *
 * // Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
 * collection.insert(2);
 *
 * // getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
 * collection.getRandom();
 *
 * // Removes 1 from the collection, returns true. Collection now contains [1,2].
 * collection.remove(1);
 *
 * // getRandom should return 1 and 2 both equally likely.
 * collection.getRandom();
 */
public class RandomizedCollection {

    //Use lst to store all the inserted data, allow duplicates.
    private List<Integer> lst;
    //Use map and set to store the indexes of the data.
    private Map<Integer, Set<Integer>> idx;
    private java.util.Random rand = new java.util.Random();
    /** Initialize your data structure here. */

    public RandomizedCollection() {
        lst = new ArrayList<Integer>();
        idx = new HashMap<Integer, Set<Integer>>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        if (!idx.containsKey(val)) {
            idx.put(val, new LinkedHashSet<Integer>());
        }
        idx.get(val).add(lst.size());
        lst.add(val);
        return idx.get(val).size() == 1;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    //Trick: use linkedhashset to get the first idx for the data
    //always remove the last element in both set and list that's the only O(1) allowed.
    public boolean remove(int val) {
        if (!idx.containsKey(val) || idx.get(val).size() == 0) {
            return false;
        }
        //Find the index to remove from the lst.
        int remove_idx = idx.get(val).iterator().next();
        idx.get(val).remove(remove_idx);
        //Can't remove directly because of the O(1) requirement, need to swap and remove the last element.
        int last = lst.get(lst.size() - 1);
        lst.set(remove_idx, last);
        idx.get(last).add(remove_idx);
        idx.get(last).remove(lst.size() - 1);
        lst.remove(lst.size() - 1);
        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        return lst.get(rand.nextInt(lst.size()));
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
