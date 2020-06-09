package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubSetExe {

    public static void main(String[] args) {
        SubSetExe exe = new SubSetExe();
        int[] nums = {4, 3, 2, 3, 5, 2, 1};
        exe.canPartitionKSubsets(nums, 4);
    }

    /**
     * https://leetcode.com/problems/subsets-ii/
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack2(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void backtrack2(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
            if (i>start && nums[i]==nums[i-1]) {
                continue;
            }
            tempList.add(nums[i]);
            backtrack2(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    /**
     * https://leetcode.com/problems/subsets/
     * Given a set of distinct integers, nums, return all possible subsets (the power set).
     *
     * Note: The solution set must not contain duplicate subsets.
     *
     * Example:
     *
     * Input: nums = [1,2,3]
     * Output:
     * [
     *   [3],
     *   [1],
     *   [2],
     *   [1,2,3],
     *   [1,3],
     *   [2,3],
     *   [1,2],
     *   []
     * ]
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
        list.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    //Solution 2: iterative solution, similar to BFS, or DP.
    public List<List<Integer>> subsets_2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int n : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> subset = new ArrayList<>(result.get(i));
                subset.add(n);
                result.add(subset);
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
     *
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
     *
     * Example 1:
     *
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     *
     * Note:
     *
     * 1 <= k <= len(nums) <= 16.
     * 0 < nums[i] < 100000
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % k != 0 || maxNum > sum / k) {
            return false;
        }
        return canPartitionDFS(nums, k, new boolean[nums.length], sum / k, 0, 0);
    }

    private boolean canPartitionDFS(int[] nums, int k, boolean[] visited, int target, int temp, int pos) {
        if (k == 0) {
            return true;
        }
        if (temp == target) {
            return canPartitionDFS(nums, k - 1, visited, target, 0, 0);
        }
        for (int i = pos; i < nums.length; i++) {
            if (!visited[i] && temp + nums[i] <= target) {
                visited[i] = true;
                //If next recursion worked, then no need to set the visited flag back.
                if (canPartitionDFS(nums, k, visited, target, temp + nums[i], i + 1)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        //If all the above 'return true' not going out of the recursion, return false.
        return false;
    }
}
