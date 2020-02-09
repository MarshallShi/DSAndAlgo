package dsandalgo.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GreedyExe{

    public static void main(String[] args) {
        GreedyExe exe = new GreedyExe();

        int[] values = {5,4,3,2,1};
        int[] labels = {1,3,3,3,2};
        //System.out.println(exe.largestValsFromLabels(values, labels, 3, 2));

        int[] tempArr = {3,2,1,2,3,4,3,4,5,9,10,11};
        System.out.println(exe.maxDepthAfterSplit("(()())"));
    }

    /**
     * https://leetcode.com/problems/minimum-swaps-to-make-strings-equal/
     *
     * @param s1
     * @param s2
     * @return
     */
    public int minimumSwap(String s1, String s2) {
        int x1 = 0; // number of 'x' in s1 (skip equal chars at same index)
        int y1 = 0; // number of 'y' in s1 (skip equal chars at same index)
        int x2 = 0; // number of 'x' in s2 (skip equal chars at same index)
        int y2 = 0; // number of 'y' in s2 (skip equal chars at same index)

        for(int i = 0; i < s1.length(); i ++){
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if(c1 == c2){ // skip chars that are equal at the same index in s1 and s2
                continue;
            }
            if(c1 == 'x'){
                x1 ++;
            }else{
                y1 ++;
            }
            if(c2 == 'x'){
                x2 ++;
            }else{
                y2 ++;
            }
        } // end for

        // After skip "c1 == c2", check the number of  'x' and 'y' left in s1 and s2.
        if((x1 + x2) % 2 != 0 || (y1 + y2) % 2 != 0){
            return -1; // if number of 'x' or 'y' is odd, we can not make s1 equals to s2
        }

        int swaps = x1 / 2 + y1 / 2 + (x1 % 2) * 2;
        // Cases to do 1 swap:
        // "xx" => x1 / 2 => how many pairs of 'x' we have ?
        // "yy" => y1 / 2 => how many pairs of 'y' we have ?
        //
        // Cases to do 2 swaps:
        // "xy" or "yx" =>  x1 % 2

        return swaps;
    }

    /**
     * vector<int> maxDepthAfterSplit(string seq) {
     *         vector<int> ans;
     *         int depth = 0;
     *         for(int i = 0; i < seq.length(); i++){
     *             if(seq[i] == '(') depth++;
     *             ans.push_back(depth%2);
     *             if(seq[i] == ')') depth--;
     *         }
     *         return ans;
     *     }```
     */

    /**
     * https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/
     *
     * A string is a valid parentheses string (denoted VPS) if and only if it consists of "(" and ")" characters only, and:
     *
     * It is the empty string, or
     * It can be written as AB (A concatenated with B), where A and B are VPS's, or
     * It can be written as (A), where A is a VPS.
     * We can similarly define the nesting depth depth(S) of any VPS S as follows:
     *
     * depth("") = 0
     * depth(A + B) = max(depth(A), depth(B)), where A and B are VPS's
     * depth("(" + A + ")") = 1 + depth(A), where A is a VPS.
     * For example,  "", "()()", and "()(()())" are VPS's (with nesting depths 0, 1, and 2), and ")(" and "(()" are not VPS's.
     *
     * Given a VPS seq, split it into two disjoint subsequences A and B, such that A and B are VPS's (and A.length + B.length = seq.length).
     *
     * Now choose any such A and B such that max(depth(A), depth(B)) is the minimum possible value.
     *
     * Return an answer array (of length seq.length) that encodes such a choice of A and B:  answer[i] = 0 if seq[i] is part of A, else answer[i] = 1.
     * Note that even though multiple answers may exist, you may return any of them.
     *
     *
     *
     * Example 1:
     *
     * Input: seq = "(()())"
     * Output: [0,1,1,1,1,0]
     *
     * Example 2:
     *
     * Input: seq = "()(())()"
     * Output: [0,0,0,1,1,0,1,1]
     *
     * @param seq
     * @return
     */
    //The trick is try to allocate each to two different group, but for each level, rotate evenly to two groups.
    public int[] maxDepthAfterSplit(String seq) {
        int[] res = new int[seq.length()];
        int level = 0;
        for (int i = 0; i < seq.length(); ++i) {
            if (seq.charAt(i) == '(') {
                level++;
                res[i] = level%2;
            } else {
                res[i] = level%2;
                level--;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/flower-planting-with-no-adjacent/
     *
     * You have N gardens, labelled 1 to N.  In each garden, you want to plant one of 4 types of flowers.
     *
     * paths[i] = [x, y] describes the existence of a bidirectional path from garden x to garden y.
     *
     * Also, there is no garden that has more than 3 paths coming into or leaving it.
     *
     * Your task is to choose a flower type for each garden such that, for any two gardens connected by a path, they have different types of flowers.
     *
     * Return any such a choice as an array answer, where answer[i] is the type of flower planted in the (i+1)-th garden.
     * The flower types are denoted 1, 2, 3, or 4.  It is guaranteed an answer exists.
     *
     *
     *
     * Example 1:
     *
     * Input: N = 3, paths = [[1,2],[2,3],[3,1]]
     * Output: [1,2,3]
     *
     *
     * Example 2:
     *
     * Input: N = 4, paths = [[1,2],[3,4]]
     * Output: [1,2,1,2]
     *
     *
     * Example 3:
     *
     * Input: N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
     * Output: [1,2,3,4]
     *
     *
     * Note:
     *
     * 1 <= N <= 10000
     * 0 <= paths.size <= 20000
     * No garden has 4 or more paths coming into or leaving it.
     * It is guaranteed an answer exists.
     * Accepted
     * @param N
     * @param paths
     * @return
     */
    public int[] gardenNoAdj(int N, int[][] paths) {
        //Create a graph
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        //... via adjacency list
        for (int i = 0; i < N; i++) graph.put(i, new HashSet<>());
        //Add the edges

        for (int[] path : paths){
            int x = path[0] - 1; //Due to 1-based indexing
            int y = path[1] - 1; //Due to 1-based indexing
            //Undirected edge
            graph.get(x).add(y);
            graph.get(y).add(x);
        }
        //Here is our solution vector where res[i] represents color of garden i+1
        int[] res = new int[N];

        //Now run graph painting algorithm

        //For each garden
        for (int i = 0; i < N; i++){
            int[] colors = new int[5]; //Use 5 instead of 4 so we can easily use 1-based indexing of the garden colors
            for (int nei : graph.get(i)){
                colors[res[nei]] = 1; //Mark the color as used if neighbor has used it before.
            }
            //Now just use a color that has not been used yet
            for (int c = 4; c >= 1; c--){
                if (colors[c] != 1) //colors[c] == 0 => the color has not been used yet,
                    res[i] = c; //so let's use that one
            }
        }

        return res;
    }


    /**
     * https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
     *
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into sets of k consecutive numbers
     * Return True if its possible otherwise return False.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [1,2,3,3,4,4,5,6], k = 4
     * Output: true
     * Explanation: Array can be divided into [1,2,3,4] and [3,4,5,6].
     * Example 2:
     *
     * Input: nums = [3,2,1,2,3,4,3,4,5,9,10,11], k = 3
     * Output: true
     * Explanation: Array can be divided into [1,2,3] , [2,3,4] , [3,4,5] and [9,10,11].
     * Example 3:
     *
     * Input: nums = [3,3,2,2,1,1], k = 3
     * Output: true
     * Example 4:
     *
     * Input: nums = [1,2,3,4], k = 3
     * Output: false
     * Explanation: Each array should be divided in subarrays of size 3.
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= nums.length
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean isPossibleDivide(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int i : nums) {
            freq.put(i, freq.getOrDefault(i,0) + 1);
        }
        for (int i : nums) {
            if (freq.get(i) == 0) {
                //The number has been all used, move on to next
                continue;
            } else {
                int j = 1;
                while (j < k) {
                    if (freq.containsKey(i+j)) {
                        if (freq.get(i+j) <= 0) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    freq.put(i+j, freq.get(i+j) - 1);
                    j++;
                }
            }
            freq.put(i, freq.get(i) - 1);
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/split-array-into-consecutive-subsequences/
     *
     * Given an array nums sorted in ascending order, return true if and only if you can split it into 1 or more subsequences such
     * that each subsequence consists of consecutive integers and has length at least 3.
     *
     *
     *
     * Example 1:
     *
     * Input: [1,2,3,3,4,5]
     * Output: True
     * Explanation:
     * You can split them into two consecutive subsequences :
     * 1, 2, 3
     * 3, 4, 5
     *
     * Example 2:
     *
     * Input: [1,2,3,3,4,4,5,5]
     * Output: True
     * Explanation:
     * You can split them into two consecutive subsequences :
     * 1, 2, 3, 4, 5
     * 3, 4, 5
     *
     * Example 3:
     *
     * Input: [1,2,3,4,4,5]
     * Output: False
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10000
     *
     * @param nums
     * @return
     */
    public boolean isPossible(int[] nums) {
        //We iterate through the array once to get the frequency of all the elements in the array
        //We iterate through the array once more and for each element we either see
        // if it can be appended to a previously constructed consecutive sequence or if it can be the start of a new consecutive sequence.
        // If neither are true, then we return false.

        //        eg: [1,2,3,4, 5]
        //// i =1
        //        we fall in 3 case "start of a new subsequence"
        //        we make 2, 3 freq 0
        //        and put <4, 1> in appendfreq, this mean I have 1 subsequence can continue from 4
        //
        ////i =2, 3
        //        we continue
        //
        ////i = 4
        //                we fall in 2 case since <4, 1> is in appendfreq
        //        now this subsequence should end in 5
        //        so we decreace <4, 1> to <4, 0> since we no longer have subsequence can continue from 4
        //        and we put <5, 1> in appendfreq since now we have a subsequence can continue from 5
        Map<Integer, Integer> freq = new HashMap<>(), appendfreq = new HashMap<>();
        for (int i : nums) {
            freq.put(i, freq.getOrDefault(i,0) + 1);
        }
        for (int i : nums) {
            if (freq.get(i) == 0) {
                //The number has been all used, move on to next
                continue;
            } else {
                if (appendfreq.getOrDefault(i,0) > 0) {
                    //this number can be the first of a new sequence already, decrease the freq from appendfreq, and add the next
                    appendfreq.put(i, appendfreq.get(i) - 1);
                    appendfreq.put(i+1, appendfreq.getOrDefault(i+1,0) + 1);
                } else {
                    if (freq.getOrDefault(i+1,0) > 0 && freq.getOrDefault(i+2,0) > 0) {
                        freq.put(i+1, freq.get(i+1) - 1);
                        freq.put(i+2, freq.get(i+2) - 1);
                        appendfreq.put(i+3, appendfreq.getOrDefault(i+3,0) + 1);
                    } else {
                        return false;
                    }
                }
            }
            freq.put(i, freq.get(i) - 1);
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/shortest-way-to-form-string/
     *
     * From any string, we can form a subsequence of that string by deleting some number of characters
     * (possibly no deletions).
     *
     * Given two strings source and target, return the minimum number of subsequences of source such that
     * their concatenation equals target. If the task is impossible, return -1.
     *
     *
     *
     * Example 1:
     *
     * Input: source = "abc", target = "abcbc"
     * Output: 2
     * Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".
     * Example 2:
     *
     * Input: source = "abc", target = "acdbc"
     * Output: -1
     * Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.
     * Example 3:
     *
     * Input: source = "xyz", target = "xzyxz"
     * Output: 3
     * Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
     *
     *
     * Constraints:
     *
     * Both the source and target strings consist of only lowercase English letters from "a"-"z".
     * The lengths of source and target string are between 1 and 1000.
     *
     * @param source
     * @param target
     * @return
     */
    //https://leetcode.com/problems/shortest-way-to-form-string/discuss/330938/Accept-is-not-enough-to-get-a-hire.-Interviewee-4-follow-up
    public int shortestWay(String source, String target) {
        char[] sourceCharArr = source.toCharArray(), targetArr = target.toCharArray();
        boolean[] sourceChar = new boolean[26];
        for (int i = 0; i < sourceCharArr.length; i++) {
            sourceChar[sourceCharArr[i] - 'a'] = true;
        }
        int j = 0, res = 1;
        for (int i = 0; i < targetArr.length; i++,j++) {
            if (!sourceChar[targetArr[i] - 'a']) {
                //If target contain any unexpected char, no way, return -1.
                return -1;
            }
            while (j < sourceCharArr.length && sourceCharArr[j] != targetArr[i]) {
                j++;
            }
            //Once source reaches the end, it is one sub-sequence required.
            if (j == sourceCharArr.length) {
                j = -1;
                res++;
                i--;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/largest-values-from-labels/
     *
     * We have a set of items: the i-th item has value values[i] and label labels[i].
     *
     * Then, we choose a subset S of these items, such that:
     *
     * |S| <= num_wanted
     * For every label L, the number of items in S with label L is <= use_limit.
     * Return the largest possible sum of the subset S.
     *
     * Example 1:
     *
     * Input: values = [5,4,3,2,1], labels = [1,1,2,2,3], num_wanted = 3, use_limit = 1
     * Output: 9
     * Explanation: The subset chosen is the first, third, and fifth item
     *
     * Example 2:
     *
     * Input: values = [5,4,3,2,1], labels = [1,3,3,3,2], num_wanted = 3, use_limit = 2
     * Output: 12
     * Explanation: The subset chosen is the first, second, and third item.
     *
     * Example 3:
     *
     * Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 1
     * Output: 16
     * Explanation: The subset chosen is the first and fourth item.
     *
     * Example 4:
     *
     * Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 2
     * Output: 24
     * Explanation: The subset chosen is the first, second, and fourth item.
     *
     *
     * Note:
     *
     * 1 <= values.length == labels.length <= 20000
     * 0 <= values[i], labels[i] <= 20000
     * 1 <= num_wanted, use_limit <= values.length
     * @param values
     * @param labels
     * @param num_wanted
     * @param use_limit
     * @return
     */
    public int largestValsFromLabels(int[] values, int[] labels, int num_wanted, int use_limit) {
        int[][] valuesAndLabels = new int[values.length][2];
        for (int i=0; i<values.length; i++) {
            valuesAndLabels[i][0] = values[i];
            valuesAndLabels[i][1] = labels[i];
        }
        Arrays.sort(valuesAndLabels, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });
        int ret = 0;
        Map<Integer, Integer> map = new HashMap<Integer,Integer>();
        int j = 0;
        for (int i=0; i<num_wanted && j<values.length;) {
            map.putIfAbsent(valuesAndLabels[j][1], 0);
            if (map.get(valuesAndLabels[j][1]) < use_limit) {
                map.put(valuesAndLabels[j][1], map.get(valuesAndLabels[j][1]) + 1);
                ret = ret + valuesAndLabels[j][0];
                i++;
            }
            j++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/walking-robot-simulation/
     * A robot on an infinite grid starts at point (0, 0) and faces north.  The robot can receive one of three possible types of commands:
     *
     * -2: turn left 90 degrees
     * -1: turn right 90 degrees
     * 1 <= x <= 9: move forward x units
     * Some of the grid squares are obstacles.
     *
     * The i-th obstacle is at grid point (obstacles[i][0], obstacles[i][1])
     *
     * If the robot would try to move onto them, the robot stays on the previous grid square instead (but still continues following the rest of the route.)
     *
     * Return the square of the maximum Euclidean distance that the robot will be from the origin.
     *
     *
     *
     * Example 1:
     *
     * Input: commands = [4,-1,3], obstacles = []
     * Output: 25
     * Explanation: robot will go to (3, 4)
     * Example 2:
     *
     * Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
     * Output: 65
     * Explanation: robot will be stuck at (1, 4) before turning left and going to (1, 8)
     *
     *
     * Note:
     *
     * 0 <= commands.length <= 10000
     * 0 <= obstacles.length <= 10000
     * -30000 <= obstacle[i][0] <= 30000
     * -30000 <= obstacle[i][1] <= 30000
     * The answer is guaranteed to be less than 2 ^ 31.
     *
     * @param commands
     * @param obstacles
     * @return
     */

    public int robotSim(int[] commands, int[][] obstacles) {
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        Set<String> obstaclesRowToCols = new HashSet<String>();
        for (int[] obstacle : obstacles) {
            obstaclesRowToCols.add(obstacle[0] + " " + obstacle[1]);
        }

        /**
         * How do we represent absolute orientations given only relative turning directions(i.e., left or right)?
         *
         * We define direction indicates the absolute orientation as below:
         *
         * North, direction = 0, directions[direction] = {0, 1}
         * East,  direction = 1, directions[direction] = {1, 0}
         * South, direction = 2, directions[direction] = {0, -1}
         * West,  direction = 3, directions[direction] = {-1, 0}
         *
         * direction will increase by one when we turn right,
         * and will decrease by one when we turn left.
         */
        int x = 0, y = 0, direction = 1, maxDistSquare = 0;
        for (int i = 0; i < commands.length; i++) {
            if (commands[i] == -2) {
                direction--;
                if (direction < 0) {
                    direction += 4;
                }
            } else if (commands[i] == -1) {
                direction++;
                direction %= 4;
            } else {
                int step = 0;
                while (step < commands[i] && (!obstaclesRowToCols.contains((x + directions[direction][0]) + " " + (y + directions[direction][1])))) {
                    x += directions[direction][0];
                    y += directions[direction][1];
                    step++;
                }
            }
            maxDistSquare = Math.max(maxDistSquare, x * x + y * y);
        }

        return maxDistSquare;
    }

    /**
     * Input: [5,5,10,10,20]
     * Output: false
     * Explanation:
     * From the first two customers in order, we collect two $5 bills.
     * For the next two customers in order, we collect a $10 bill and give back a $5 bill.
     * For the last customer, we can't give change of $15 back because we only have two $10 bills.
     * Since not every customer received correct change, the answer is false.
     *
     * https://leetcode.com/problems/lemonade-change/
     * @param bills
     * @return
     */
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0, twenty = 0;
        for (int i=0; i<bills.length; i++) {
            if (bills[i] == 5) {
                five++;
            } else {
                if (bills[i] == 10) {
                    five--;
                    ten++;
                } else {
                    if (bills[i] == 20) {
                        if (ten > 0) {
                            ten--;
                            five--;
                        } else {
                            five = five - 3;
                        }
                    }
                }
            }
            if (five < 0) {
                return false;
            }
        }
        return true;
    }
}
