package dsandalgo.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapExe {

    public static void main(String[] args) {
        HashMapExe exe = new HashMapExe();

        int[] groupSizes = {2,1,3,3,3,2};
        exe.groupThePeople(groupSizes);
    }

    //https://leetcode.com/problems/group-the-people-given-the-group-size-they-belong-to/
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        Map<Integer, List<Integer>> map = new HashMap<Integer,List<Integer>>();
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i=0; i<groupSizes.length; i++) {
            if (map.containsKey(groupSizes[i])) {
                map.get(groupSizes[i]).add(i);
            } else {
                map.put(groupSizes[i], new ArrayList<Integer>());
                map.get(groupSizes[i]).add(i);
            }
            if (map.get(groupSizes[i]).size() == groupSizes[i]) {
                ret.add(new ArrayList<Integer>(map.get(groupSizes[i])));
                map.put(groupSizes[i], new ArrayList<Integer>());
            }
        }
        return ret;
    }
}
