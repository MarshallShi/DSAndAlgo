package dsandalgo.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HardDPExe {

    public static void main(String[] args) {
        HardDPExe exe = new HardDPExe();
        String[] strs = {"with", "example", "science"};
        //System.out.println(exe.countPalindromicSubsequences("abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba"));
    }



    /**
     * https://leetcode.com/problems/stickers-to-spell-word/
     * We are given N different types of stickers. Each sticker has a lowercase English word on it.
     * You would like to spell out the given target string by cutting individual letters from your collection of stickers and rearranging them.
     * You can use each sticker more than once if you want, and you have infinite quantities of each sticker.
     * What is the minimum number of stickers that you need to spell out the target? If the task is impossible, return -1.
     *
     * Example 1:
     * Input:
     * ["with", "example", "science"], "thehat"
     * Output:
     * 3
     * Explanation:
     * We can use 2 "with" stickers, and 1 "example" sticker.
     * After cutting and rearrange the letters of those stickers, we can form the target "thehat".
     * Also, this is the minimum number of stickers necessary to form the target string.
     *
     * Example 2:
     * Input:
     * ["notice", "possible"], "basicbasic"
     * Output:
     * -1
     * Explanation:
     * We can't form the target "basicbasic" from cutting letters from the given stickers.
     *
     * Note:
     * stickers has length in the range [1, 50].
     * stickers consists of lowercase English words (without apostrophes).
     * target has length in the range [1, 15], and consists of lowercase English letters.
     * In all test cases, all words were chosen randomly from the 1000 most common US English words, and the target was chosen as a concatenation of two random words.
     * The time limit may be more challenging than usual. It is expected that a 50 sticker test case can be solved within 35ms on average.
     */
    //DFS + Memo. Try to use every stick, and check if the rest of target can be formed.
    public int minStickers(String[] stickers, String target) {
        int m = stickers.length;
        int[][] mp = new int[m][26];
        Map<String, Integer> cache = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (char c : stickers[i].toCharArray()) {
                mp[i][c-'a']++;
            }
        }
        cache.put("", 0);
        return minStickersHelper(cache, mp, target);
    }
    private int minStickersHelper(Map<String, Integer> cache, int[][] mp, String target) {
        if (cache.containsKey(target)) {
            return cache.get(target);
        }
        int ans = Integer.MAX_VALUE, n = mp.length;
        int[] tar = new int[26];
        for (char c : target.toCharArray()) {
            tar[c-'a']++;
        }
        // try every sticker
        for (int i = 0; i < n; i++) {
            // optimization
            if (mp[i][target.charAt(0) - 'a'] == 0) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            // apply a sticker on every character a-z
            for (int j = 0; j < 26; j++) {
                if (tar[j] > 0 ) {
                    for (int k = 0; k < Math.max(0, tar[j]-mp[i][j]); k++) {
                        sb.append((char)('a' + j));
                    }
                }
            }
            String s = sb.toString();
            int tmp = minStickersHelper(cache, mp, s);
            if (tmp != -1) {
                ans = Math.min(ans, 1 + tmp);
            }
        }
        cache.put(target, ans == Integer.MAX_VALUE ? -1 : ans);
        return cache.get(target);
    }

    /**
     * https://leetcode.com/problems/count-vowels-permutation/
     * @param n
     * @return
     */
    public int countVowelPermutation(int n) {
        long MOD = 1000000007L;
        long[][] dp = new long[5][n];
        dp[0][0] = 1; //end with 'a'
        dp[1][0] = 1; //end with 'e'
        dp[2][0] = 1; //end with 'i'
        dp[3][0] = 1; //end with 'o'
        dp[4][0] = 1; //end with 'u'
        for (int i=1; i<n; i++) {
            dp[0][i] = (dp[1][i-1] + dp[4][i-1] + dp[2][i-1])%MOD;
            dp[1][i] = (dp[0][i-1] + dp[2][i-1])%MOD;
            dp[2][i] = (dp[1][i-1] + dp[3][i-1])%MOD;
            dp[3][i] = dp[2][i-1]%MOD;
            dp[4][i] = (dp[2][i-1] + dp[3][i-1])%MOD;
        }
        return (int)((dp[0][n-1] + dp[1][n-1] + dp[2][n-1] + dp[3][n-1] + dp[4][n-1])%MOD);
    }

    /**
     * https://leetcode.com/problems/smallest-rectangle-enclosing-black-pixels/
     * An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
     * The black pixels are connected, i.e., there is only one black region.
     * Pixels are connected horizontally and vertically. Given the location (x, y) of one of the black pixels,
     * return the area of the smallest (axis-aligned) rectangle that encloses all black pixels.
     *
     * Example:
     *
     * Input:
     * [
     *   "0010",
     *   "0110",
     *   "0100"
     * ]
     * and x = 0, y = 2
     *
     * Output: 6
     */
    private int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
    public int minArea(char[][] image, int x, int y) {
        if (image == null || image.length == 0 || image[0].length == 0) {
            return 0;
        }
        minAreaDFS(image, x, y);
        return(maxX - minX + 1) * (maxY - minY + 1);
    }
    private void minAreaDFS(char[][] image, int x, int y){
        int m = image.length, n = image[0].length;
        if (x < 0 || y < 0 || x >= m || y >= n || image[x][y] == '0') {
            return;
        }
        image[x][y] = '0';
        minX = Math.min(minX, x);
        maxX = Math.max(maxX, x);
        minY = Math.min(minY, y);
        maxY = Math.max(maxY, y);
        minAreaDFS(image, x + 1, y);
        minAreaDFS(image, x - 1, y);
        minAreaDFS(image, x, y - 1);
        minAreaDFS(image, x, y + 1);
    }

    /**
     * https://leetcode.com/problems/jump-game-v/
     * Given an array of integers arr and an integer d. In one step you can jump from index i to index:
     *
     * i + x where: i + x < arr.length and 0 < x <= d.
     * i - x where: i - x >= 0 and 0 < x <= d.
     * In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for
     * all indices k between i and j (More formally min(i, j) < k < max(i, j)).
     *
     * You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.
     *
     * Notice that you can not jump outside of the array at any time.
     *
     * Example 1:
     * Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
     * Output: 4
     * Explanation: You can start at index 10. You can jump 10 --> 8 --> 6 --> 7 as shown.
     * Note that if you start at index 6 you can only jump to index 7. You cannot jump to index 5 because 13 > 9.
     * You cannot jump to index 4 because index 5 is between index 4 and 6 and 13 > 9.
     * Similarly You cannot jump from index 3 to index 2 or index 1.
     *
     * Example 2:
     * Input: arr = [3,3,3,3,3], d = 3
     * Output: 1
     * Explanation: You can start at any index. You always cannot jump to any index.
     *
     * Example 3:
     * Input: arr = [7,6,5,4,3,2,1], d = 1
     * Output: 7
     * Explanation: Start at index 0. You can visit all the indicies.
     *
     * Example 4:
     * Input: arr = [7,1,7,1,7,1], d = 2
     * Output: 2
     *
     * Example 5:
     * Input: arr = [66], d = 1
     * Output: 1
     *
     * Constraints:
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 10^5
     * 1 <= d <= arr.length
     */
    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        int cache[] = new int[n];
        int res = 1;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, maxJumpsDFS(arr, n, d, i, cache));
        }
        return res;
    }
    private int maxJumpsDFS(int[] arr, int n, int d, int i, int[] cache) {
        if (cache[i] != 0) {
            return cache[i];
        }
        int res = 1;
        for (int j = i + 1; j <= Math.min(i + d, n - 1) && arr[j] < arr[i]; j++) {
            res = Math.max(res, 1 + maxJumpsDFS(arr, n, d, j, cache));
        }
        for (int j = i - 1; j >= Math.max(i - d, 0) && arr[j] < arr[i]; j--) {
            res = Math.max(res, 1 + maxJumpsDFS(arr, n, d, j, cache));
        }
        cache[i] = res;
        return res;
    }

    /**
     * https://leetcode.com/problems/maximum-profit-in-job-scheduling/
     * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of profit[i].
     *
     * You're given the startTime , endTime and profit arrays, you need to output the maximum profit you can take such that
     * there are no 2 jobs in the subset with overlapping time range.
     *
     * If you choose a job that ends at time X you will be able to start another job that starts at time X.
     *
     * Example 1:
     * Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
     * Output: 120
     * Explanation: The subset chosen is the first and fourth job.
     * Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.
     *
     * Example 2:
     * Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
     * Output: 150
     * Explanation: The subset chosen is the first, fourth and fifth job.
     * Profit obtained 150 = 20 + 70 + 60.
     *
     * Example 3:
     * Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
     * Output: 6
     *
     * Constraints:
     * 1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
     * 1 <= startTime[i] < endTime[i] <= 10^9
     * 1 <= profit[i] <= 10^4
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int[][] items = new int[startTime.length][3];
        for (int i = 0; i < startTime.length; i++) {
            items[i] = new int[] {startTime[i], endTime[i], profit[i]};
        }
        // sort by endTime
        Arrays.sort(items, (a1, a2)->a1[1] - a2[1]);
        List<Integer> dpEndTime = new ArrayList<>();
        List<Integer> dpProfit = new ArrayList<>();
        // dynamic build up these two in the loop to track current max.
        dpEndTime.add(0);
        dpProfit.add(0);
        for (int[] item : items) {
            int start = item[0], end = item[1], seProfit = item[2];
            // find previous endTime index
            int prevIdx = Collections.binarySearch(dpEndTime, start + 1);
            if (prevIdx < 0) {
                prevIdx = -prevIdx - 1;
            }
            prevIdx--;
            //current max profit : max(use current, not use current).
            int currProfit = dpProfit.get(prevIdx) + seProfit, maxProfit = dpProfit.get(dpProfit.size() - 1);
            if (currProfit > maxProfit) {
                dpProfit.add(currProfit);
                dpEndTime.add(end);
            }
        }
        return dpProfit.get(dpProfit.size() - 1);
    }

    /**
     * https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/
     * You want to schedule a list of jobs in d days. Jobs are dependent
     * (i.e To work on the i-th job, you have to finish all the jobs j where 0 <= j < i).
     *
     * You have to finish at least one task every day. The difficulty of a job schedule is the
     * sum of difficulties of each day of the d days. The difficulty of a day is the maximum difficulty of a job done in that day.
     *
     * Given an array of integers jobDifficulty and an integer d. The difficulty of the i-th job is jobDifficulty[i].
     *
     * Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.
     *
     * Example 1:
     * Input: jobDifficulty = [6,5,4,3,2,1], d = 2
     * Output: 7
     * Explanation: First day you can finish the first 5 jobs, total difficulty = 6.
     * Second day you can finish the last job, total difficulty = 1.
     * The difficulty of the schedule = 6 + 1 = 7
     *
     * Example 2:
     * Input: jobDifficulty = [9,9,9], d = 4
     * Output: -1
     * Explanation: If you finish a job per day you will still have a free day. you cannot find a schedule for the given jobs.
     *
     * Example 3:
     * Input: jobDifficulty = [1,1,1], d = 3
     * Output: 3
     * Explanation: The schedule is one job per day. total difficulty will be 3.
     *
     * Example 4:
     * Input: jobDifficulty = [7,1,7,1,7,1], d = 3
     * Output: 15
     *
     * Example 5:
     * Input: jobDifficulty = [11,111,22,222,33,333,44,444], d = 6
     * Output: 843
     *
     * Constraints:
     * 1 <= jobDifficulty.length <= 300
     * 0 <= jobDifficulty[i] <= 1000
     * 1 <= d <= 10
     */
    public int minDifficulty(int[] A, int D) {
        int n = A.length, inf = Integer.MAX_VALUE, maxd;
        if (n < D) return -1;
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; --i) {
            dp[i] = Math.max(dp[i + 1], A[i]);
        }
        for (int d = 2; d <= D; ++d) {
            for (int i = 0; i <= n - d; ++i) {
                maxd = 0;
                dp[i] = inf;
                for (int j = i; j <= n - d; ++j) {
                    maxd = Math.max(maxd, A[j]);
                    dp[i] = Math.min(dp[i], maxd + dp[j + 1]);
                }
            }
        }
        return dp[0];
    }

    public int minDifficulty_topDown(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        if (n < d) {
            return -1;
        }
        int[][] cache = new int[n][d+1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(cache[i], -1);
        }
        return minDifficultyHelper(jobDifficulty, n, 0, d, cache);
    }
    private int minDifficultyHelper(int[] arr, int n, int i, int d, int[][] cache) {
        if (cache[i][d] != -1) {
            return cache[i][d];
        }
        if (d == 1) {
            int max = 0;
            while (i < n) {
                max = Math.max(max, arr[i++]);
            }
            return max;
        }
        int res = Integer.MAX_VALUE, maxDifficulty = 0;
        for (int j = i; j < n - d + 1; j++) {
            maxDifficulty = Math.max(arr[j], maxDifficulty);
            res = Math.min(res, maxDifficulty + minDifficultyHelper(arr, n, j + 1, d - 1, cache));
        }
        cache[i][d] = res;
        return res;
    }

    /**
     * https://leetcode.com/problems/palindrome-partitioning-iii/
     * You are given a string s containing lowercase letters and an integer k. You need to :
     *
     * First, change some characters of s to other lowercase English letters.
     * Then divide s into k non-empty disjoint substrings such that each substring is palindrome.
     * Return the minimal number of characters that you need to change to divide the string.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "abc", k = 2
     * Output: 1
     * Explanation: You can split the string into "ab" and "c", and change 1 character in "ab" to make it palindrome.
     * Example 2:
     *
     * Input: s = "aabbc", k = 3
     * Output: 0
     * Explanation: You can split the string into "aa", "bb" and "c", all of them are palindrome.
     * Example 3:
     *
     * Input: s = "leetcode", k = 8
     * Output: 0
     *
     *
     * Constraints:
     *
     * 1 <= k <= s.length <= 100.
     * s only contains lowercase English letters.
     */
    private int[][] cache;
    private int[][] memoPP;
    public int palindromePartition(String s, int k) {
        cache = new int[s.length()][s.length()];
        memoPP = new int[101][101];
        for (int i=0; i<cache.length; i++) {
            Arrays.fill(cache[i],  -1);
        }
        for (int i=0; i<cache.length; i++) {
            cache[i][i] = 0;
        }
        for (int i=0; i<memoPP.length; i++) {
            Arrays.fill(memoPP[i],  -1);
        }
        return palindromePartitionHelper(s, k, 0, 0);
    }

    private int palindromePartitionHelper(String s, int k, int kth, int pos) {
        if (memoPP[pos][kth] != -1) {
            return memoPP[pos][kth];
        }
        if (k == kth + 1 && pos <= s.length() - 1) {
            return getPalin(s, pos, s.length() - 1);
        }
        int curMin = Integer.MAX_VALUE;
        for (int i=pos; i<s.length(); i++) {
            int fix = getPalin(s, pos, i);
            int rest = palindromePartitionHelper(s, k, kth+1, i+1);
            if (rest != Integer.MAX_VALUE) {
                curMin = Math.min(curMin, fix + rest);
            }
        }
        memoPP[pos][kth] = curMin;
        return curMin;
    }

    private int getPalin(String s, int start, int end){
        if (start == end) {
            return 0;
        } else {
            if (cache[start][end] == -1) {
                int counter = 0;
                while (start < end) {
                    if (s.charAt(start) != s.charAt(end)) {
                        counter++;
                    }
                    start++;
                    end--;
                }
                cache[start][end] = counter;
            }
            return cache[start][end];
        }
    }

    /**
     * https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/
     *
     * You have a keyboard layout as shown above in the XY plane, where each English uppercase letter is located at some coordinate,
     * for example, the letter A is located at coordinate (0,0), the letter B is located at coordinate (0,1),
     * the letter P is located at coordinate (2,3) and the letter Z is located at coordinate (4,1).
     *
     * Given the string word, return the minimum total distance to type such string using only two fingers.
     * The distance between coordinates (x1,y1) and (x2,y2) is |x1 - x2| + |y1 - y2|.
     *
     * Note that the initial positions of your two fingers are considered free so don't count towards your total distance,
     * also your two fingers do not have to start at the first letter or the first two letters.
     *
     * Example 1:
     * Input: word = "CAKE"
     * Output: 3
     * Explanation:
     * Using two fingers, one optimal way to type "CAKE" is:
     * Finger 1 on letter 'C' -> cost = 0
     * Finger 1 on letter 'A' -> cost = Distance from letter 'C' to letter 'A' = 2
     * Finger 2 on letter 'K' -> cost = 0
     * Finger 2 on letter 'E' -> cost = Distance from letter 'K' to letter 'E' = 1
     * Total distance = 3
     *
     * Example 2:
     * Input: word = "HAPPY"
     * Output: 6
     * Explanation:
     * Using two fingers, one optimal way to type "HAPPY" is:
     * Finger 1 on letter 'H' -> cost = 0
     * Finger 1 on letter 'A' -> cost = Distance from letter 'H' to letter 'A' = 2
     * Finger 2 on letter 'P' -> cost = 0
     * Finger 2 on letter 'P' -> cost = Distance from letter 'P' to letter 'P' = 0
     * Finger 1 on letter 'Y' -> cost = Distance from letter 'A' to letter 'Y' = 4
     * Total distance = 6
     *
     * Example 3:
     * Input: word = "NEW"
     * Output: 3
     *
     * Example 4:
     * Input: word = "YEAR"
     * Output: 7
     *
     * Constraints:
     * 2 <= word.length <= 300
     * Each word[i] is an English uppercase letter.
     */
    public int minimumDistance(String word) {
        //c1 and c2 represent last char finger1 and finger2 is pointing, start with null.
        return minDist(word, 0, null, null);
    }

    private int[][][] memo = new int[27][27][301];

    private int minDist(String word, int pos, Character c1, Character c2) {
        if (pos >= word.length()) {
            return 0;
        }
        int idx1 = c1 == null ? 0 : c1 - 'A' + 1;
        int idx2 = c2 == null ? 0 : c2 - 'A' + 1;
        if (memo[idx1][idx2][pos] == 0) {
            char curTargetChar = word.charAt(pos);
            int usingFinger1 = getDist(c1, curTargetChar) + minDist(word,pos+1, curTargetChar, c2);
            int usingFinger2 = getDist(c2, curTargetChar) + minDist(word,pos+1, c1, curTargetChar);
            memo[idx1][idx2][pos] = Math.min(usingFinger1, usingFinger2);
        }
        return memo[idx1][idx2][pos];
    }

    private int getDist(Character c1, Character c2) {
        //To cover the starting position where dist is 0.
        if (c1 == null || c2 == null) {
            return 0;
        }
        int d1 = c1 - 'A', d2 = c2 - 'A';
        int x1 = d1 / 6, y1 = d1 % 6;
        int x2 = d2 / 6, y2 = d2 % 6;
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}
