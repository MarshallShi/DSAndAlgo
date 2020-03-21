package dsandalgo.unionfind;

/**
 * https://leetcode.com/problems/longest-consecutive-sequence/
 *
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
 *
 * Your algorithm should run in O(n) complexity.
 *
 * Example:
 *
 * Input: [100, 4, 200, 1, 3, 2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 */
public class LongestConsecutiveSequence {

    int[] father;

    LongestConsecutiveSequence(int n) {
        father = new int[n];
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
    }
    public void union(int idx1, int idx2) {
        int find1 = find(idx1);
        int find2 = find(idx2);
        if(find1 != find2) {
            father[find1] = find2;
        }
    }
    public int find (int idx) {
        if (father[idx] == idx) {
            return idx;
        }
        father[idx] = find(father[idx]);
        return father[idx];
    }

    public int maxUnion() {
        int[] counter = new int[father.length];
        int max = Integer.MIN_VALUE;
        for (int i=0; i<counter.length; i++) {
            int j = i;
            while (father[j] != j) {
                j = father[j];
            }
            counter[father[j]]++;
            max = Math.max(max, counter[father[j]]);
        }
        return max;
    }
}
