package dsandalgo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class SortingExe {

    public static void main(String[] args) {
        SortingExe exe = new SortingExe();
        int[] input = {1,2,3};

        int[] difficulty = {85,47,57};
        int[] profit = {24,66,99};
        int[] worker = {1,0,2,3,4};
        System.out.println(exe.maxChunksToSorted(worker));
    }

    /**
     * https://leetcode.com/problems/rank-teams-by-votes/
     * In a special ranking system, each voter gives a rank from highest to lowest to all teams participated
     * in the competition.
     *
     * The ordering of teams is decided by who received the most position-one votes. If two or more teams tie
     * in the first position, we consider the second position to resolve the conflict, if they tie again, we
     * continue this process until the ties are resolved. If two or more teams are still tied after considering
     * all positions, we rank them alphabetically based on their team letter.
     *
     * Given an array of strings votes which is the votes of all voters in the ranking systems. Sort all teams
     * according to the ranking system described above.
     *
     * Return a string of all teams sorted by the ranking system.
     *
     * Example 1:
     * Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
     * Output: "ACB"
     * Explanation: Team A was ranked first place by 5 voters. No other team was voted as first place so
     * team A is the first team.
     * Team B was ranked second by 2 voters and was ranked third by 3 voters.
     * Team C was ranked second by 3 voters and was ranked third by 2 voters.
     * As most of the voters ranked C second, team C is the second team and team B is the third.
     *
     * Example 2:
     * Input: votes = ["WXYZ","XYZW"]
     * Output: "XWYZ"
     * Explanation: X is the winner due to tie-breaking rule. X has same votes as W for the first position but X has one vote as second position while W doesn't have any votes as second position.
     *
     * Example 3:
     * Input: votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
     * Output: "ZMNAGUEDSJYLBOPHRQICWFXTVK"
     * Explanation: Only one voter so his votes are used for the ranking.
     *
     * Example 4:
     * Input: votes = ["BCA","CAB","CBA","ABC","ACB","BAC"]
     * Output: "ABC"
     * Explanation:
     * Team A was ranked first by 2 voters, second by 2 voters and third by 2 voters.
     * Team B was ranked first by 2 voters, second by 2 voters and third by 2 voters.
     * Team C was ranked first by 2 voters, second by 2 voters and third by 2 voters.
     * There is a tie and we rank teams ascending by their IDs.
     *
     * Example 5:
     * Input: votes = ["M","M","M","M"]
     * Output: "M"
     * Explanation: Only team M in the competition so it has the first rank.
     *
     * Constraints:
     * 1 <= votes.length <= 1000
     * 1 <= votes[i].length <= 26
     * votes[i].length == votes[j].length for 0 <= i, j < votes.length.
     * votes[i][j] is an English upper-case letter.
     * All characters of votes[i] are unique.
     * All the characters that occur in votes[0] also occur in votes[j] where 1 <= j < votes.length.
     */
    class ValPair{
        public int idx;
        public int[] theRank;
        ValPair(int _idx, int[] _rank){
            this.idx = _idx;
            this.theRank = _rank;
        }
    }
    public String rankTeams(String[] votes) {
        int teams = votes[0].length();
        int[][] rankCount = new int[26][teams];
        for (int i=0; i<votes.length; i++) {
            for (int j=0; j<votes[i].length(); j++) {
                rankCount[votes[i].charAt(j) - 'A'][j]++;
            }
        }
        PriorityQueue<ValPair> pq = new PriorityQueue<>(new Comparator<ValPair>() {
            @Override
            public int compare(ValPair o1, ValPair o2) {
                int[] r1 = o1.theRank;
                int[] r2 = o2.theRank;
                for (int i=0; i<teams; i++) {
                    if (r1[i] == r2[i]) {
                        continue;
                    }
                    return r2[i] - r1[i];
                }
                return 0;
            }
        });
        for (int i=0; i<rankCount.length; i++) {
            pq.offer(new ValPair(i, rankCount[i]));
        }
        String ret = "";
        int t = 0;
        while (!pq.isEmpty() && t<teams) {
            ret = ret + (char)('A' + pq.poll().idx);
            t++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/card-flipping-game/
     * @param fronts
     * @param backs
     * @return
     */
    public int flipgame(int[] fronts, int[] backs) {
        Set<Integer> set = new HashSet<>();
        int n = fronts.length;
        for (int i = 0; i < n; i++) {
            if (fronts[i] == backs[i]) {
                set.add(fronts[i]);
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (!set.contains(backs[i])) {
                min = Math.min(min, backs[i]);
            }
            if (!set.contains(fronts[i])) {
                min = Math.min(min, fronts[i]);
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * https://leetcode.com/problems/max-chunks-to-make-sorted/
     *
     * Given an array arr that is a permutation of [0, 1, ..., arr.length - 1], we split the array into some
     * number of "chunks" (partitions), and individually sort each chunk.  After concatenating them, the result
     * equals the sorted array.
     *
     * What is the most number of chunks we could have made?
     *
     * Example 1:
     *
     * Input: arr = [4,3,2,1,0]
     * Output: 1
     * Explanation:
     * Splitting into two or more chunks will not return the required result.
     * For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
     * Example 2:
     *
     * Input: arr = [1,0,2,3,4]
     * Output: 4
     * Explanation:
     * We can split into two chunks, such as [1, 0], [2, 3, 4].
     * However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
     * Note:
     *
     * arr will have length in range [1, 10].
     * arr[i] will be a permutation of [0, 1, ..., arr.length - 1].
     */
    public int maxChunksToSorted(int[] arr) {
        boolean[] seen = new boolean[10];
        int count = 0, chunkStart = 0;
        for (int i=0; i<arr.length; i++) {
            seen[arr[i]] = true;
            int k = i;
            boolean formChunk = true;
            for (int j=chunkStart; j<=k; j++) {
                if (!seen[j]) {
                    formChunk = false;
                    break;
                }
            }
            if (formChunk) {
                count++;
                chunkStart = i+1;
            }
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/longest-mountain-in-array/
     *
     * Let's call any (contiguous) subarray B (of A) a mountain if the following properties hold:
     *
     * B.length >= 3
     * There exists some 0 < i < B.length - 1 such that B[0] < B[1] < ... B[i-1] < B[i] > B[i+1] > ... > B[B.length - 1]
     * (Note that B could be any subarray of A, including the entire array A.)
     *
     * Given an array A of integers, return the length of the longest mountain.
     *
     * Return 0 if there is no mountain.
     *
     * Example 1:
     *
     * Input: [2,1,4,7,3,2,5]
     * Output: 5
     * Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
     * Example 2:
     *
     * Input: [2,2,2]
     * Output: 0
     * Explanation: There is no mountain.
     * Note:
     *
     * 0 <= A.length <= 10000
     * 0 <= A[i] <= 10000
     * Follow up:
     *
     * Can you solve it using only one pass?
     * Can you solve it in O(1) space?
     *
     * @param A
     * @return
     */
    public int longestMountain(int[] A) {
        int N = A.length, res = 0;
        int[] up = new int[N], down = new int[N];
        for (int i = N - 2; i >= 0; --i) {
            if (A[i] > A[i + 1]) {
                down[i] = down[i + 1] + 1;
            }
        }
        for (int i = 0; i < N; ++i) {
            if (i > 0 && A[i] > A[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
            if (up[i] > 0 && down[i] > 0) {
                res = Math.max(res, up[i] + down[i] + 1);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/decrease-elements-to-make-array-zigzag/
     * Given an array nums of integers, a move consists of choosing any element and decreasing it by 1.
     *
     * An array A is a zigzag array if either:
     *
     * Every even-indexed element is greater than adjacent elements, ie. A[0] > A[1] < A[2] > A[3] < A[4] > ...
     * OR, every odd-indexed element is greater than adjacent elements, ie. A[0] < A[1] > A[2] < A[3] > A[4] < ...
     * Return the minimum number of moves to transform the given array nums into a zigzag array.
     *
     * Example 1:
     * Input: nums = [1,2,3]
     * Output: 2
     * Explanation: We can decrease 2 to 0 or 3 to 1.
     *
     * Example 2:
     * Input: nums = [9,6,1,6,2]
     * Output: 4
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     *
     */
    public int movesToMakeZigzag(int[] nums) {

        int[] aaa = {3,2,-1,-2};
        int[] aab = {-2,-1,2,3};
        int low = 0, high = 3;
        while (low < high) {
            int mid = low + (high - low)/2;
            if (aaa[mid] < 0) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        System.out.println(low);
        int id = Arrays.binarySearch(aab, 0);


        int res[] = new int[2],  n = nums.length, left, right;
        for (int i = 0; i < n; ++i) {
            left = i > 0 ? nums[i - 1] : 1001;
            right = i + 1 < n ? nums[i + 1] : 1001;
            res[i % 2] += Math.max(0, nums[i] - Math.min(left, right) + 1);
        }
        return Math.min(res[0], res[1]);
    }

    /**
     * https://leetcode.com/problems/global-and-local-inversions/
     * @param A
     * @return
     */
    public boolean isIdealPermutation(int[] A) {
        for (int i = 0; i < A.length; i++) {
            if (Math.abs(i - A[i]) > 1)
                return false;
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/corporate-flight-bookings/
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        TreeMap<Integer, Integer> start = new TreeMap<>();
        TreeMap<Integer, Integer> end = new TreeMap<>();
        for (int i=0; i<bookings.length; i++) {
            start.put(bookings[i][0], start.getOrDefault(bookings[i][0], 0) + bookings[i][2]);
            end.put(bookings[i][1], end.getOrDefault(bookings[i][1], 0) + bookings[i][2]);
        }
        int rolling = 0;
        int[] res = new int[n];
        for (int i=1; i<=n; i++) {
            if (start.containsKey(i)) {
                rolling += start.get(i);
            }
            res[i-1] = rolling;
            if (end.containsKey(i)) {
                rolling -= end.get(i);
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/most-profit-assigning-work/
     */
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();
        // in case two jobs have same difficulty but different profit, we want to count
        // the higher profit
        for (int i = 0; i < difficulty.length; i++) {
            tmap.put(difficulty[i], Math.max(profit[i], tmap.getOrDefault(difficulty[i], 0)));
        }

        int max = 0, res = 0;
        // maximum profit at this difficulty or below in case
        // lower difficulty job offers higher profit
        for (Integer key : tmap.keySet()) {
            max = Math.max(tmap.get(key), max);
            tmap.put(key, max);
        }

        Map.Entry<Integer, Integer> entry = null;
        for (int i = 0; i < worker.length; i++) {
            if (tmap.containsKey(worker[i])) {
                res += tmap.get(worker[i]);
            } else {
                entry = tmap.floorEntry(worker[i]);
                if (entry != null) {
                    res += entry.getValue();
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/distant-barcodes/
     *
     * In a warehouse, there is a row of barcodes, where the i-th barcode is barcodes[i].
     *
     * Rearrange the barcodes so that no two adjacent barcodes are equal.  You may return any
     * answer, and it is guaranteed an answer exists.
     *
     * Example 1:
     * Input: [1,1,1,2,2,2]
     * Output: [2,1,2,1,2,1]
     *
     * Example 2:
     * Input: [1,1,1,1,2,2,3,3]
     * Output: [1,3,1,3,2,1,2,1]
     *
     * Note:
     *
     * 1 <= barcodes.length <= 10000
     * 1 <= barcodes[i] <= 10000
     */
    public int[] rearrangeBarcodes(int[] barcodes) {
        if (barcodes == null || barcodes.length == 0) {
            return barcodes;
        }
        //count the freq of numbers.
        Map<Integer, Integer> counter = new HashMap<>();
        int max_fre = 0;
        for (int num : barcodes) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
            if (counter.get(num) > max_fre) {
                max_fre = counter.get(num);
            }
        }
        //bucket sort.
        List<Integer>[] buckets = new ArrayList[max_fre + 1];
        for (int num : counter.keySet()) {
            int c = counter.get(num);
            if (buckets[c] == null) {
                buckets[c] = new ArrayList<>();
            }
            buckets[c].add(num);
        }

        //push data into the result array, first do the most freq number pushed
        int index = 0;
        int[] res = new int[barcodes.length];
        for (int i = max_fre; i >= 1; i--) {
            List<Integer> b = buckets[i];
            if (b == null) continue;
            for (int num : b) {
                int j = i;
                while (j > 0) {
                    res[index] = num;
                    index = index + 2 < barcodes.length ? index + 2 : 1;
                    j--;
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/sort-the-matrix-diagonally/
     * Given a m * n matrix mat of integers, sort it diagonally in ascending order from the top-left to the bottom-right then return the sorted array.
     * Example 1:
     * Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
     * Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
     * Constraints:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 100
     * 1 <= mat[i][j] <= 100
     * @param A
     * @return
     */
    //The trick is to use the fact that same diagonal will have the same diff of i and j.
    //Use that and push the values to the same PQ.
    public int[][] diagonalSort(int[][] A) {
        int m = A.length, n = A[0].length;
        Map<Integer, PriorityQueue<Integer>> d = new HashMap<Integer, PriorityQueue<Integer>>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                d.putIfAbsent(i - j, new PriorityQueue<Integer>());
                d.get(i - j).offer(A[i][j]);
            }
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                A[i][j] = d.get(i - j).poll();
            }
        }
        return A;
    }

    /**
     * https://leetcode.com/problems/next-greater-element-iii/
     *
     * @param n
     * @return
     */
    /**
     * Algo:
     * I) Traverse the given number from rightmost digit, keep traversing till you find a digit which is smaller than the previously traversed digit.
     * For example, if the input number is “534976”, we stop at 4 because 4 is smaller than next digit 9. If we do not find such a digit, then output is “Not Possible”.
     *
     * II) Now search the right side of above found digit ‘d’ for the smallest digit greater than ‘d’. For “534976″, the right side of 4 contains “976”. The smallest digit greater than 4 is 6.
     *
     * III) Swap the above found two digits, we get 536974 in above example.
     *
     * IV) Now sort all digits from position next to ‘d’ to the end of number. The number that we get after sorting is the output. For above example, we sort digits in bold 536974. We get “536479” which is the next greater number for input 534976.
     */
    public int nextGreaterElement(int n) {
        char[] number = (n + "").toCharArray();
        int i, j;
        // 1) Start from the right most digit and find the first digit that is smaller than the digit next to it.
        for (i = number.length-1; i > 0; i--){
            if (number[i-1] < number[i]){
                break;
            }
        }
        // If no such digit is found, its the edge case 1.
        if (i == 0) {
            return -1;
        }

        // 2) Find the smallest digit on right side of (i-1)'th digit that is greater than number[i-1]
        int x = number[i-1], smallest = i;
        for (j = i+1; j < number.length; j++) {
            if (number[j] > x && number[j] <= number[smallest]) {
                smallest = j;
            }
        }

        // 3) Swap the above found smallest digit with number[i-1]
        char temp = number[i-1];
        number[i-1] = number[smallest];
        number[smallest] = temp;

        // 4) Sort the digits after (i-1) in ascending order
        Arrays.sort(number, i, number.length);

        long val = Long.parseLong(new String(number));
        return (val <= Integer.MAX_VALUE) ? (int) val : -1;
    }

    /**
     * https://leetcode.com/problems/3sum-smaller/
     *
     * @param nums
     * @param target
     * @return
     */
    //To achieve n2 time, we need to sort first, and apply two pointer to avoid n3.
    //Tip in the counter increment...
    public int threeSumSmaller(int[] nums, int target) {
        int counter = 0;
        Arrays.sort(nums);
        for (int i=0; i<nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[i] + nums[right] < target) {
                    //all r in (l, r] will also satisfy the condition
                    counter = counter + right - left;
                    left = left + 1;
                } else {
                    right = right - 1;
                }
            }
        }
        return counter;
    }

    /**
     * https://leetcode.com/problems/pancake-sorting/
     * Given an array A, we can perform a pancake flip: We choose some positive integer k <= A.length,
     * then reverse the order of the first k elements of A.  We want to perform zero or more pancake flips
     * (doing them one after another in succession) to sort the array A.
     *
     * Return the k-values corresponding to a sequence of pancake flips that sort A.  Any valid answer
     * that sorts the array within 10 * A.length flips will be judged as correct.
     *
     * Example 1:
     *
     * Input: [3,2,4,1]
     * Output: [4,2,4,3]
     * Explanation:
     * We perform 4 pancake flips, with k values 4, 2, 4, and 3.
     * Starting state: A = [3, 2, 4, 1]
     * After 1st flip (k=4): A = [1, 4, 2, 3]
     * After 2nd flip (k=2): A = [4, 1, 2, 3]
     * After 3rd flip (k=4): A = [3, 2, 1, 4]
     * After 4th flip (k=3): A = [1, 2, 3, 4], which is sorted.
     *
     *
     * Example 2:
     *
     * Input: [1,2,3]
     * Output: []
     * Explanation: The input is already sorted, so there is no need to flip anything.
     * Note that other answers, such as [3, 3], would also be accepted.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 100
     * A[i] is a permutation of [1, 2, ..., A.length]
     *
     * @param A
     * @return
     */
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> result = new ArrayList<>();
        int n = A.length, largest = n;
        for (int i = 0; i < n; i++) {
            int index = find(A, largest);
            flip(A, index);
            flip(A, largest - 1);
            result.add(index + 1);
            result.add(largest--);
        }
        return result;
    }
    private int find(int[] A, int target) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                return i;
            }
        }
        return -1;
    }
    private void flip(int[] A, int index) {
        int i = 0, j = index;
        while (i < j) {
            int temp = A[i];
            A[i++] = A[j];
            A[j--] = temp;
        }
    }

    /**
     * https://leetcode.com/problems/reveal-cards-in-increasing-order/
     *
     * In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.
     *
     * Initially, all the cards start face down (unrevealed) in one deck.
     *
     * Now, you do the following steps repeatedly, until all cards are revealed:
     *
     * Take the top card of the deck, reveal it, and take it out of the deck.
     * If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
     * If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
     * Return an ordering of the deck that would reveal the cards in increasing order.
     *
     * The first entry in the answer is considered to be the top of the deck.
     *
     *
     *
     * Example 1:
     *
     * Input: [17,13,11,2,3,5,7]
     * Output: [2,13,3,11,5,17,7]
     * Explanation:
     * We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
     * After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
     * We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
     * We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
     * We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
     * We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
     * We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
     * We reveal 13, and move 17 to the bottom.  The deck is now [17].
     * We reveal 17.
     * Since all the cards revealed are in increasing order, the answer is correct.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 1000
     * 1 <= A[i] <= 10^6
     * A[i] != A[j] for all i != j
     *
     * @param deck
     * @return
     */
    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        int[] ret = new int[deck.length];
        for (int i=deck.length - 1; i>=0; i--) {
            ret[i] = deck[i];
            if (i != deck.length - 1) {
                int back = ret[deck.length - 1];
                for (int k=deck.length - 1; k>i; k--) {
                    ret[k] = ret[k-1];
                }
                ret[i+1] = back;
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/
     *
     *
     * here is the straightforward proof for this problem
     * lets start with two points:
     * a--------------------------------b
     * the smallest moves is any point between a and b, and the number of moves is b-a
     * if we addd another two points
     * a--------c----------d------------b
     * what's the minimum moves to make sure c and d make the smallest number of moves?
     * it the same logic as a and b, which is ANY point between c and d.
     * if eventually their value is between a and c or b and d, we can only make sure a c move the least, but not for c d
     * so, just define two pointers and calculate the distance for each pair, add to result.
     *
     * @param nums
     * @return
     */
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = nums.length-1;
        int count = 0;
        while(i < j){
            count += nums[j]-nums[i];
            i++;
            j--;
        }
        return count;
    }

    /**
     * https://leetcode.com/problems/increasing-triplet-subsequence/
     * @param nums
     * @return
     */
    public boolean increasingTriplet(int[] nums) {
        int small = Integer.MAX_VALUE, medium = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) {
                small = n; // update small if n is smaller than both
            } else {
                if (n <= medium) {
                    medium = n;  // update big only if greater than small but smaller than big
                } else {
                    return true; // return if you find a number bigger than both
                }
            }
        }
        return false;
    }


}
