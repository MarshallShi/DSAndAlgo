package dsandalgo.twopointers;

import java.util.Arrays;

public class TwoPointers {

    public static void main(String[] args) {

    }

    /**
     * https://leetcode.com/problems/minimum-window-subsequence/
     * Given strings S and T, find the minimum (contiguous) substring W of S, so that T is a subsequence of W.
     *
     * If there is no such window in S that covers all characters in T, return the empty string "".
     * If there are multiple such minimum-length windows, return the one with the left-most starting index.
     *
     * Example 1:
     *
     * Input:
     * S = "abcdebdde", T = "bde"
     * Output: "bcde"
     * Explanation:
     * "bcde" is the answer because it occurs before "bdde" which has the same length.
     * "deb" is not a smaller window because the elements of T in the window must occur in order.
     *
     *
     * Note:
     *
     * All the strings in the input will only contain lowercase letters.
     * The length of S will be in the range [1, 20000].
     * The length of T will be in the range [1, 100].
     */
    public String minWindow(String S, String T) {
        char[] s = S.toCharArray(), t = T.toCharArray();
        int sindex = 0, tindex = 0, slen = s.length, tlen = t.length, start = -1, len = slen;
        while (sindex < slen) {
            if (s[sindex] == t[tindex]) {
                if (++tindex == tlen) {
                    //check feasibility from left to right of T
                    int end = sindex+1;
                    //check optimization from right to left of T
                    while (--tindex >= 0) {
                        while (s[sindex--] != t[tindex]);
                    }
                    ++sindex;
                    ++tindex;
                    //record the current smallest candidate
                    if(end - sindex < len) {
                        len = end - sindex;
                        start = sindex;
                    }
                }
            }
            ++sindex;
        }
        return start == -1 ? "" : S.substring(start, start + len);
    }

    /**
     * https://leetcode.com/problems/push-dominoes/
     * There are N dominoes in a line, and we place each domino vertically upright.
     *
     * In the beginning, we simultaneously push some of the dominoes either to the left or to the right.
     *
     * After each second, each domino that is falling to the left pushes the adjacent domino on the left.
     * Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.
     * When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.
     * For the purposes of this question, we will consider that a falling domino expends no additional force to a falling
     * or already fallen domino.
     * Given a string "S" representing the initial state. S[i] = 'L', if the i-th domino has been pushed to the left;
     * S[i] = 'R', if the i-th domino has been pushed to the right; S[i] = '.', if the i-th domino has not been pushed.
     *
     * Return a string representing the final state.
     *
     * Example 1:
     * Input: ".L.R...LR..L.."
     * Output: "LL.RR.LLRRLL.."
     *
     * Example 2:
     * Input: "RR.L"
     * Output: "RR.L"
     * Explanation: The first domino expends no additional force on the second domino.
     *
     * Note:
     * 0 <= N <= 10^5
     * String dominoes contains only 'L', 'R' and '.'
     */
    public String pushDominoes(String dominoes) {
        char[] a = dominoes.toCharArray();
        int L = -1, R = -1;//positions of last seen L and R
        for (int i = 0; i <= dominoes.length(); i++) {
            if (i == a.length || a[i] == 'R') {
                if (R > L) {//R..R, turn all to R
                    while (R < i) {
                        a[R++] = 'R';
                    }
                }
                R = i;
            } else if (a[i] == 'L') {
                if (L > R || (R == -1 && L == -1)) {//L..L, turn all to L
                    while (++L < i) {
                        a[L] = 'L';
                    }
                } else { //R...L
                    L = i;
                    int lo = R + 1, hi = L - 1;
                    while (lo < hi) { //one in the middle stays '.'
                        a[lo++] = 'R';
                        a[hi--] = 'L';
                    }
                }
            }
        }
        return new String(a);
    }
	
    /**
     * https://leetcode.com/problems/fruit-into-baskets/
     * In a row of trees, the i-th tree produces fruit with type tree[i].
     *
     * You start at any tree of your choice, then repeatedly perform the following steps:
     *
     * Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
     * Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
     * Note that you do not have any choice after the initial choice of starting tree: you must perform step 1,
     * then step 2, then back to step 1, then step 2, and so on until you stop.
     *
     * You have two baskets, and each basket can carry any quantity of fruit, but you want each basket to only
     * carry one type of fruit each.
     *
     * What is the total amount of fruit you can collect with this procedure?
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: 3
     * Explanation: We can collect [1,2,1].
     *
     * Example 2:
     * Input: [0,1,2,2]
     * Output: 3
     * Explanation: We can collect [1,2,2].
     * If we started at the first tree, we would only collect [0, 1].
     *
     * Example 3:
     * Input: [1,2,3,2,2]
     * Output: 4
     * Explanation: We can collect [2,3,2,2].
     * If we started at the first tree, we would only collect [1, 2].
     *
     * Example 4:
     * Input: [3,3,3,1,2,1,1,2,3,3,4]
     * Output: 5
     * Explanation: We can collect [1,2,1,1,2].
     * If we started at the first tree or the eighth tree, we would only collect 4 fruits.
     *
     * Note:
     * 1 <= tree.length <= 40000
     * 0 <= tree[i] < tree.length
     */
    public int totalFruit(int[] tree) {
        int[] count = new int[tree.length];
        int i = 0;
        int num = 0;
        int res = 0;
        for (int j = 0; j < tree.length; j++) {
            if (count[tree[j]] == 0) {
                num++;
            }
            count[tree[j]]++;
            while (num > 2 && i < tree.length) {
                count[tree[i]]--;
                if (count[tree[i]] == 0){
                    num--;
                }
                i++;
            }
            res = Math.max(res, j - i + 1);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/valid-triangle-number/
     *
     * Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array
     * that can make triangles if we take them as side lengths of a triangle.
     *
     * Example 1:
     * Input: [2,2,3,4]
     * Output: 3
     *
     * Explanation:
     * Valid combinations are:
     * 2,3,4 (using the first 2)
     * 2,3,4 (using the second 2)
     * 2,2,3
     *
     * Note:
     * The length of the given array won't exceed 1000.
     * The integers in the given array are in the range of [0, 1000].
     *
     * @param nums
     * @return
     */
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        //Keep in mind we are searching for a+b>c where c is the longest edge.
        //So we can fix the longest in the triplet, do two pointer search for other two valid shorter edges.
        for (int i=nums.length-1; i>1; i--) {
            int low = 0, high = i - 1;
            while (low < high) {
                if (nums[low] + nums[high] > nums[i]) {
                    count = count + high - low;
                    high--;
                } else {
                    low++;
                }
            }
        }
        return count;
    }
}
