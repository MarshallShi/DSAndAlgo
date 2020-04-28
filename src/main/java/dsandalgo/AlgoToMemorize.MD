Sorting:


Quick Sort:



Merge Sort:



Array:

Maximum Sum SubArray (Kadane's algorithm) (Largest Sum Contigous SubArray)
https://leetcode.com/problems/maximum-subarray/

    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int curSum = nums[0], maxSum = nums[0];
        for (int i = 1; i < n; ++i) {
            //Pick the max of current element or so far sum
            curSum = Math.max(nums[i], curSum + nums[i]);
            maxSum = Math.max(maxSum, curSum);
        }
        return maxSum;
    }

2D version of Kadane's algorithm
https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/

String

Trie (Prefix tree)
https://leetcode.com/problems/implement-trie-prefix-tree/
https://leetcode.com/problems/add-and-search-word-data-structure-design/
https://leetcode.com/problems/word-search-ii/

Graph:

Shortest path algo
1. Dijkstra

Topological sorting

Minimal spanning tree (Union Find)
