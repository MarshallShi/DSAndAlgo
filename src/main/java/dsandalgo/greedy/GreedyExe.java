package dsandalgo.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class GreedyExe{

    public static void main(String[] args) {
        GreedyExe exe = new GreedyExe();

        int[] values = {5,4,3,2,1};
        int[] labels = {1,3,3,3,2};
        //System.out.println(exe.largestValsFromLabels(values, labels, 3, 2));

        int[] tempArr = {3,2,2,1};
        System.out.println(exe.numRescueBoats(tempArr, 3));
    }

    /**
     * https://leetcode.com/problems/longest-chunked-palindrome-decomposition/
     * Return the largest possible k such that there exists a_1, a_2, ..., a_k such that:
     *
     * Each a_i is a non-empty string;
     * Their concatenation a_1 + a_2 + ... + a_k is equal to text;
     * For all 1 <= i <= k,  a_i = a_{k+1 - i}.
     *
     *
     * Example 1:
     *
     * Input: text = "ghiabcdefhelloadamhelloabcdefghi"
     * Output: 7
     * Explanation: We can split the string on "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)".
     * Example 2:
     *
     * Input: text = "merchant"
     * Output: 1
     * Explanation: We can split the string on "(merchant)".
     * Example 3:
     *
     * Input: text = "antaprezatepzapreanta"
     * Output: 11
     * Explanation: We can split the string on "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)".
     * Example 4:
     *
     * Input: text = "aaa"
     * Output: 3
     * Explanation: We can split the string on "(a)(a)(a)".
     *
     *
     * Constraints:
     *
     * text consists only of lowercase English characters.
     * 1 <= text.length <= 1000
     */
    public int longestDecomposition(String S) {
        int res = 0, n = S.length();
        String l = "", r = "";
        for (int i = 0; i < n; ++i) {
            l = l + S.charAt(i);
            r = S.charAt(n - i - 1) + r;
            if (l.equals(r)) {
                ++res;
                l = "";
                r = "";
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/three-equal-parts/
     * Given an array A of 0s and 1s, divide the array into 3 non-empty parts such that all of these parts represent
     * the same binary value.
     *
     * If it is possible, return any [i, j] with i+1 < j, such that:
     *
     * A[0], A[1], ..., A[i] is the first part;
     * A[i+1], A[i+2], ..., A[j-1] is the second part, and
     * A[j], A[j+1], ..., A[A.length - 1] is the third part.
     * All three parts have equal binary value.
     * If it is not possible, return [-1, -1].
     *
     * Note that the entire part is used when considering what binary value it represents.  For example,
     * [1,1,0] represents 6 in decimal, not 3.  Also, leading zeros are allowed, so [0,1,1] and [1,1] represent the same value.
     *
     * Example 1:
     * Input: [1,0,1,0,1]
     * Output: [0,3]
     *
     * Example 2:
     * Input: [1,1,0,1,1]
     * Output: [-1,-1]
     *
     * Note:
     * 3 <= A.length <= 30000
     * A[i] == 0 or A[i] == 1
     */
    //1.count how many ones (if num%3!=0 return [-1,-1])
    //2.search from right side to left, until we found num/3 1s. This index is not final answer, but it defines patten of 1s
    //3.from feft, ignore leading 0s, and then match the pattern found in step 2, to get the first EndIndex
    //4.do another matching to found second EndIndex
    public int[] threeEqualParts(int[] A) {
        int numOne = 0;
        for (int i: A) {
            if (i == 1) {
                numOne++;
            }
        }
        int[] res = {-1, -1};
        if (numOne == 0) {
            return new int[]{0,2}; // special case
        }
        if (numOne % 3 != 0) {
            return res;
        }
        int thirdPartStartingIndex = 0;
        int count = 0;
        for (int i = A.length - 1; i >= 0; --i){
            if (A[i] == 1){
                if (++count == numOne / 3){
                    thirdPartStartingIndex = i;
                    break;
                }
            }
        }
        int firstPartEndIndex = findNextEndIndexAndCompare(A, 0, thirdPartStartingIndex);
        if (firstPartEndIndex < 0) {
            return res;
        }
        int secondPartEndIndex = findNextEndIndexAndCompare(A, firstPartEndIndex + 1, thirdPartStartingIndex);
        if (secondPartEndIndex < 0) {
            return res;
        }
        return new int[]{firstPartEndIndex, secondPartEndIndex+1};
    }


    /** the implementation idea is similar to find last k node in a list
     *  in the sense that pacer is a pacer
     *  when the pacer reaches the end, the end for the current part has been anchored
     *  Note: we also do the comparing for the two parts of interest
     *
     * @param A
     * @param start
     * @param pacer
     * @return
     */
    private int findNextEndIndexAndCompare(int[] A, int start, int pacer){
        while (A[start] == 0) {
            start++;
        }
        while (pacer < A.length){
            if (A[start] != A[pacer]) {
                return -1;
            }
            start++;
            pacer++;
        }
        return start - 1;
    }

    /**
     * https://leetcode.com/problems/beautiful-arrangement-ii/
     * Given two integers n and k, you need to construct a list which contains n different positive integers ranging from 1 to n
     * and obeys the following requirement:
     * Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|] has exactly
     * k distinct integers.
     *
     * If there are multiple answers, print any of them.
     *
     * Example 1:
     * Input: n = 3, k = 1
     * Output: [1, 2, 3]
     * Explanation: The [1, 2, 3] has three different positive integers ranging from 1 to 3, and the [1, 1] has exactly 1
     * distinct integer: 1.
     * Example 2:
     * Input: n = 3, k = 2
     * Output: [1, 3, 2]
     * Explanation: The [1, 3, 2] has three different positive integers ranging from 1 to 3, and the [2, 1] has exactly 2
     * distinct integers: 1 and 2.
     */
    //When k = n-1, a valid construction is [1, n, 2, n-1, 3, n-2, ....]. One way to see this is, we need to have a difference
    // of n-1, which means we need 1 and n adjacent; then, we need a difference of n-2, etc.
    //This leads to the following idea: we will put [1, 2, ...., n-k-1] first, and then we have N = k+1 adjacent numbers left,
    // of which we want k different differences. This is just the answer above translated by n-k-1:
    // we'll put [n-k, n, n-k+1, n-1, ....] after.
    public int[] constructArray(int n, int k) {
        int[] result = new int[n];
        int left = 1, right = n;
        for (int i=0; i<n; i++){
            result[i] = k%2 !=0 ? left++ : right--;
            if (k>1) {
                k--;
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/previous-permutation-with-one-swap/
     * @param A
     * @return
     */
    public int[] prevPermOpt1(int[] A) {
        if (A == null || A.length < 2) {
            return A;
        }
        int preHigher = -1, toSwap = -1;
        for (int i=1; i<A.length; i++) {
            if (A[i] < A[i-1]) {
                preHigher = i-1;
                toSwap = i;
            } else {
                if (A[i] > A[i - 1] && preHigher != -1 && toSwap != -1) {
                    if (A[i] < A[preHigher] && A[i] > A[toSwap]) {
                        toSwap = i;
                    }
                }
            }
        }
        if (preHigher == -1){
            return A;
        } else {
            int temp = A[preHigher];
            A[preHigher] = A[toSwap];
            A[toSwap] = temp;
            return A;
        }
    }
	
    /**
     * https://leetcode.com/problems/dota2-senate/
     */
    //Greedy strategy is to ban the next opponent right after current
    public String predictPartyVictory(String senate) {
        int r = 0, d = 0, start = 0;
        char[] arr = senate.toCharArray();
        for (char c : arr) {
            if (c == 'R') {
                r++;
            } else {
                d++;
            }
        }
        while (r > 0 && d > 0) {
            while (arr[start] != 'R' && arr[start] != 'D') {
                //avoid arr idx overflow
                start = (start + 1) % arr.length;
            }
            char ban = 'R';
            if (arr[start] == 'R') {
                ban = 'D';
                d--;
            } else {
                r--;
            }
            int idx = (start + 1) % arr.length;
            while (arr[idx] != ban) {
                idx = (idx + 1) % arr.length;
            }
            arr[idx] = ' ';
            start = (start + 1) % arr.length;
        }
        return d == 0 ? "Radiant" : "Dire";
    }

    /**
     * https://leetcode.com/problems/advantage-shuffle/
     * Given two arrays A and B of equal size, the advantage of A with respect to B is the number
     * of indices i for which A[i] > B[i].
     *
     * Return any permutation of A that maximizes its advantage with respect to B.
     *
     * Example 1:
     * Input: A = [2,7,11,15], B = [1,10,4,11]
     * Output: [2,11,7,15]
     *
     * Example 2:
     * Input: A = [12,24,8,32], B = [13,25,32,11]
     * Output: [24,32,8,12]
     *
     * Note:
     * 1 <= A.length = B.length <= 10000
     * 0 <= A[i] <= 10^9
     * 0 <= B[i] <= 10^9
     */
    //GREEDY: sort A, also for B, always try to satisfy from big to small as big is harder.
    //
    public int[] advantageCount(int[] A, int[] B) {
        Arrays.sort(A);
        int n = A.length;
        int[] res = new int[n];
        PriorityQueue<int[]> pq= new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] a, int[] b){
                return b[0]-a[0];
            }
        });
        for (int i=0; i<n; i++) {
            pq.add(new int[]{B[i], i});
        }
        int lo=0, hi=n-1;
        while (!pq.isEmpty()) {
            int[] cur= pq.poll();
            int idx = cur[1], val = cur[0];
            //if we can use A's highest to satisfy B's highest, assign.
            if (A[hi] > val) {
                res[idx] = A[hi--];
            } else {
                //else we use A's lowest to match with B's highest.
                res[idx] = A[lo++];
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/bag-of-tokens/
     * @param tokens
     * @param P
     * @return
     */
    public int bagOfTokensScore(int[] tokens, int P) {
        Arrays.sort(tokens);
        int low = 0, high = tokens.length - 1;
        int ans = Integer.MIN_VALUE;
        int points = 0;

        while (low <= high) {
            if (P >= tokens[low]) {
                points++;
                ans = Math.max(ans, points);
                P = P - tokens[low];
                low++;
            } else {
                if (points > 0) {
                    P = P + tokens[high];
                    points--;
                    high--;
                } else {
                    break;
                }
            }
        }
        if (ans == Integer.MIN_VALUE) {
            return 0;
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/reconstruct-a-2-row-binary-matrix/
     * @param upper
     * @param lower
     * @param colsum
     * @return
     */
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        ret.add(new ArrayList<Integer>());
        ret.add(new ArrayList<Integer>());
        boolean invalid = false;
        for (int i=0; i<colsum.length; i++) {
            if (colsum[i] == 0) {
                ret.get(0).add(0);
                ret.get(1).add(0);
                continue;
            }
            if (colsum[i] == 2) {
                ret.get(0).add(1);
                ret.get(1).add(1);
                upper--;
                lower--;
                continue;
            }
            if (colsum[i] == 1) {
                if (upper > lower) {
                    ret.get(0).add(1);
                    ret.get(1).add(0);
                    upper--;
                } else {
                    ret.get(0).add(0);
                    ret.get(1).add(1);
                    lower--;
                }
            }
            if (upper < 0 || lower < 0) {
                invalid = true;
                break;
            }
        }
        if (invalid) {
            return new ArrayList<List<Integer>>();
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/boats-to-save-people/
     *
     * The i-th person has weight people[i], and each boat can carry a maximum weight of limit.
     * Each boat carries at most 2 people at the same time, provided the sum of the weight of those people is at most limit.
     * Return the minimum number of boats to carry every given person.  (It is guaranteed each person can be carried by a boat.)
     *
     * Example 1:
     * Input: people = [1,2], limit = 3
     * Output: 1
     * Explanation: 1 boat (1, 2)
     *
     * Example 2:
     * Input: people = [3,2,2,1], limit = 3
     * Output: 3
     * Explanation: 3 boats (1, 2), (2) and (3)
     *
     * Example 3:
     * Input: people = [3,5,3,4], limit = 5
     * Output: 4
     * Explanation: 4 boats (3), (3), (4), (5)
     * Note:
     *
     * 1 <= people.length <= 50000
     * 1 <= people[i] <= limit <= 30000
     */
    //Tow pointers + Greedy.
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int i = 0, j = people.length - 1;
        while (i < j) {
            if (people[i] + people[j] <= limit) {
                ++i;
                j--;
            } else {
                j--;
            }
        }
        if (i == j) {
            return people.length - j;
        }
        return people.length - 1 - j;
    }

    /**
     * https://leetcode.com/problems/minimum-swaps-to-make-strings-equal/
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
