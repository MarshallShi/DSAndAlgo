package dsandalgo.unionfind;

/**
 * For LC 128: Longest Consecutive Sequence
 */
public class UFForLongestConsecutiveSequence {

    int[] father;

    UFForLongestConsecutiveSequence(int n) {
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
