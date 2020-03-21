package dsandalgo.unionfind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionFindHardExe {

    public static void main(String[] args) {
        UnionFindHardExe exe = new UnionFindHardExe();
        int[] wells = {1,2,2};
        int[][] pipes = {{1,2,1},{2,3,1}};
        //System.out.println(exe.minCostToSupplyWater(3, wells, pipes));
    }


    /**
     * https://leetcode.com/problems/similar-string-groups/
     * Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y. Also two strings X and Y are similar if they are equal.
     *
     * For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar, but "star" is not similar to "tars", "rats", or "arts".
     *
     * Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that "tars" and "arts" are in the same group even though they are not similar.  Formally, each group is such that a word is in the group if and only if it is similar to at least one other word in the group.
     *
     * We are given a list A of strings.  Every string in A is an anagram of every other string in A.  How many groups are there?
     *
     *
     *
     * Example 1:
     *
     * Input: A = ["tars","rats","arts","star"]
     * Output: 2
     *
     *
     * Constraints:
     *
     * 1 <= A.length <= 2000
     * 1 <= A[i].length <= 1000
     * A.length * A[i].length <= 20000
     * All words in A consist of lowercase letters only.
     * All words in A have the same length and are anagrams of each other.
     * The judging time limit has been increased for this question.
     */
    public int numSimilarGroups(String[] A) {
        UnionFindSimilarGroups ufsg = new UnionFindSimilarGroups(A.length);
        for (int i=0; i<A.length-1; i++) {
            for (int j=i+1; j<A.length; j++) {
                if (isSimilarGroup(A[i], A[j])) {
                    ufsg.union(i, j);
                }
            }
        }
        return ufsg.size;
    }

    private boolean isSimilarGroup(String s, String t){
        int res = 0;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                res++;
            }
            if (res > 2) {
                return false;
            }
        }
        return true;
    }

    class UnionFindSimilarGroups {
        public int size;
        public int[] parent;
        public UnionFindSimilarGroups(int size) {
            this.size = size;
            this.parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            int parB = find(b);
            int parA = find(a);
            if (parB != parA) {
                parent[parB] = parent[parA];
                size--;
            }
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }

    /**
     * https://leetcode.com/problems/largest-component-size-by-common-factor/
     * Given a non-empty array of unique positive integers A, consider the following graph:
     * There are A.length nodes, labelled A[0] to A[A.length - 1];
     * There is an edge between A[i] and A[j] if and only if A[i] and A[j] share a common factor greater than 1.
     * Return the size of the largest connected component in the graph.
     *
     * Example 1:
     * Input: [4,6,15,35]
     * Output: 4
     *
     * Example 2:
     * Input: [20,50,9,63]
     * Output: 2
     *
     * Example 3:
     * Input: [2,3,6,7,4,12,21,39]
     * Output: 8
     *
     * Note:
     *
     * 1 <= A.length <= 20000
     * 1 <= A[i] <= 100000
     */
    //Time complexity: O(N*sqrt(Max val of A[i]))
    public int largestComponentSize(int[] A) {
        int N = A.length;
        // key is the lowest possible factor, val is the node index
        Map<Integer, Integer> map = new HashMap<>();
        UFComponentSize uf = new UFComponentSize(N);
        for (int i = 0; i < N; i++) {
            int a = A[i];
            for (int j = 2; j * j <= a; j++) {
                if (a % j == 0) {
                    if (!map.containsKey(j)) {
                        //this means that no index has claimed the factor yet
                        map.put(j, i);
                    } else {
                        //this means that one index already claimed, so union that one with current
                        uf.union(i, map.get(j));
                    }
                    if (!map.containsKey(a/j)) {
                        map.put(a/j, i);
                    } else {
                        uf.union(i, map.get(a/j));
                    }
                }
            }
            //a could be factor too. Don't miss this
            if (!map.containsKey(a)) {
                map.put(a, i);
            } else {
                uf.union(i, map.get(a));
            }
        }
        return uf.max;
    }

    class UFComponentSize {
        int[] parent;
        int[] size;
        int max;
        public UFComponentSize(int N){
            parent = new int[N];
            size = new int[N];
            max = 1;
            for (int i = 0; i < N; i++){
                parent[i] = i;
                size[i] = 1;
            }
        }
        public int find(int x){
            if (x == parent[x]) {
                return x;
            }
            return parent[x] = find(parent[x]);
        }
        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY){
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
                max = Math.max(max, size[rootY]);
            }
        }
    }

    /**
     * https://leetcode.com/problems/redundant-connection-ii/
     * In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for
     * which all other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which has no parents.
     *
     * The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N),
     * with one additional directed edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.
     *
     * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents a directed
     * edge connecting nodes u and v, where u is a parent of child v.
     *
     * Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes. If there are multiple answers,
     * return the answer that occurs last in the given 2D-array.
     *
     * Example 1:
     * Input: [[1,2], [1,3], [2,3]]
     * Output: [2,3]
     * Explanation: The given directed graph will be like this:
     *   1
     *  / \
     * v   v
     * 2-->3
     * Example 2:
     * Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
     * Output: [4,1]
     * Explanation: The given directed graph will be like this:
     * 5 <- 1 -> 2
     *      ^    |
     *      |    v
     *      4 <- 3
     * Note:
     * The size of the input 2D-array will be between 3 and 1000.
     * Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
     */
    //https://leetcode.com/problems/redundant-connection-ii/discuss/278105/topic
    private int[] anc;//union found
    private int[] parent;// record the father of every node to find the one with 2 fathers
    public int[] findRedundantDirectedConnection(int[][] edges) {
        anc = new int[edges.length+1];
        parent = new int[edges.length+1];
        int[] edge1 = null;
        int[] edge2 = null;
        int[] edgeCauseCircle=null;
        for (int[] pair : edges) {
            int u = pair[0];
            int v = pair[1];
            if (anc[u] == 0) anc[u] = u;
            if (anc[v] == 0) anc[v] = v;//init the union-find set
            if (parent[v] != 0) {
                // node v already has a father, so we just skip the union of this edge and check if there will be a circle
                edge1 = new int[]{parent[v],v};
                edge2 = pair;
            } else {
                parent[v] = u;
                int ancU = find(u);
                int ancV = find(v);
                if(ancU != ancV){
                    anc[ancV] = ancU;
                } else {
                    //meet a circle
                    edgeCauseCircle = pair;
                }
            }
        }
        if (edge1 != null && edge2 != null) {
            return edgeCauseCircle == null ? edge2 : edge1;
        } else {
            return edgeCauseCircle;
        }
    }
    private int find(int node){
        if (anc[node] == node) return node;
        anc[node] = find(anc[node]);
        return anc[node];
    }

    /**
     * https://leetcode.com/problems/smallest-string-with-swaps/
     * You are given a string s, and an array of pairs of indices in the string pairs where
     * pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.
     *
     * You can swap the characters at any pair of indices in the given pairs any number of times.
     *
     * Return the lexicographically smallest string that s can be changed to after using the swaps.
     *
     * Example 1:
     * Input: s = "dcab", pairs = [[0,3],[1,2]]
     * Output: "bacd"
     * Explaination:
     * Swap s[0] and s[3], s = "bcad"
     * Swap s[1] and s[2], s = "bacd"
     *
     * Example 2:
     * Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
     * Output: "abcd"
     * Explaination:
     * Swap s[0] and s[3], s = "bcad"
     * Swap s[0] and s[2], s = "acbd"
     * Swap s[1] and s[2], s = "abcd"
     *
     * Example 3:
     * Input: s = "cba", pairs = [[0,1],[1,2]]
     * Output: "abc"
     * Explaination:
     * Swap s[0] and s[1], s = "bca"
     * Swap s[1] and s[2], s = "bac"
     * Swap s[0] and s[1], s = "abc"
     *
     * Constraints:
     * 1 <= s.length <= 10^5
     * 0 <= pairs.length <= 10^5
     * 0 <= pairs[i][0], pairs[i][1] < s.length
     * s only contains lower case English letters.
     *
     */
    //Trick: If a group of characters is interconnected by swap pairs, you can freely rearrange characters within that group.
    //In this example, we have two group of interconnected characters, so we can make rearrangements to achieve the
    // smallest string as shown in the picture below.
    //Identify groups using union-find. For each group, collect all its characters in a string.
    //Note that in the disjoined set ds, the find operation returns the parent index (p). We use that index to identify
    // the group, and put all group characters into m[p].
    //Sort the string, then put the rearranged characters back to their respective positions in the group.
    public String smallestStringWithSwaps(String s, List<List<Integer>> swaps) {
        int N = s.length();
        UFSmallestStringWithSwaps uf = new UFSmallestStringWithSwaps(N);
        for (List<Integer> swap : swaps) {
            uf.union(swap.get(0), swap.get(1));
        }
        Map<Integer, List<Character>> graphs = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int head = uf.find(i);
            List<Character> characters = graphs.computeIfAbsent(head, (dummy) -> new ArrayList<>());
            characters.add(s.charAt(i));
        }
        for (List<Character> characters : graphs.values()) {
            Collections.sort(characters);
        }
        StringBuilder sb = new StringBuilder(N);
        for (int i = 0; i < N; i++) {
            List<Character> characters = graphs.get(uf.find(i));
            char currentMin = characters.remove(0);
            sb.append(currentMin);
        }
        return sb.toString();
    }

    class UFSmallestStringWithSwaps {
        public int[] size;
        public int[] parent;
        UFSmallestStringWithSwaps(int count) {
            size = new int[count];
            parent = new int[count];
            for (int i = 0; i < count; i++) {
                size[i] = 1;
                parent[i] = i;
            }
        }
        int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        int union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) {
                return size[pRoot];
            }
            if (size[pRoot] > size[qRoot]) {
                parent[qRoot] = pRoot;
                size[pRoot] += size[qRoot];
                return size[pRoot];
            } else {
                parent[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
                return size[qRoot];
            }
        }
    }

    /**
     * https://leetcode.com/problems/couples-holding-hands/
     *
     * @param row
     * @return
     */
    //https://leetcode.com/problems/couples-holding-hands/discuss/160104/Union-find-understand-in-60-seconds-beats-99.6
    public int minSwapsCouples(int[] row) {
        int N = row.length / 2;
        int swap = 0;
        int[] groups = new int[row.length];
        for(int group = 0; group < N; group++) {
            groups[group*2] = group;
            groups[group*2+1] = group;
        }
        // Since number of seats are even and all couples want to hold hands
        // if i is the group number, 0 <= i < N, each couple should seat at positions of 2*i and 2*i+1
        for(int group = 0; group < N; group++) {
            int person1 = row[2*group];
            int person2 = row[2*group+1];
            //tricky: the couple id of the person.
            int couple1 = person1 / 2;
            int couple2 = person2 / 2;
            if (couple1 != couple2) {
                // if these two person are not couples (each person from different couples)
                // if we do swap people from two couples for the first time, this swap is counted
                // if we want to swap people from the same two couples for the second time, we don't need to
                // do anything since the first swap already exchange the 2 people to meet their love partners.
                if (findGroup(groups, person1) != findGroup(groups, person2)) {
                    swap++;
                    // union couples into same group
                    union(groups, person1, person2);
                }
            }
        }
        return swap;
    }
    private int findGroup(int[] groups, int person) {
        return groups[person];
    }
    private void union(int[] groups, int person1, int person2) {
        int group = groups[person1];
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == group) groups[i] = groups[person2];
        }
    }
}
