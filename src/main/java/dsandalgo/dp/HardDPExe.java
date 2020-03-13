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
        int[] arr1 = {1,5,3,6,7};
        int[] arr2 = {1,3,2,4};
        //System.out.println(exe.digitsCount(1, 1, 13));
    }

    /**
     * https://leetcode.com/problems/valid-permutations-for-di-sequence/
     * We are given S, a length n string of characters from the set {'D', 'I'}.
     * (These letters stand for "decreasing" and "increasing".)
     *
     * A valid permutation is a permutation P[0], P[1], ..., P[n] of integers {0, 1, ..., n}, such that for all i:
     *
     * If S[i] == 'D', then P[i] > P[i+1], and;
     * If S[i] == 'I', then P[i] < P[i+1].
     * How many valid permutations are there?  Since the answer may be large, return your answer modulo 10^9 + 7.
     *
     *
     * Example 1:
     * Input: "DID"
     * Output: 5
     * Explanation:
     * The 5 valid permutations of (0, 1, 2, 3) are:
     * (1, 0, 3, 2)
     * (2, 0, 3, 1)
     * (2, 1, 3, 0)
     * (3, 0, 2, 1)
     * (3, 1, 2, 0)
     *
     * Note:
     * 1 <= S.length <= 200
     * S consists only of characters from the set {'D', 'I'}.
     */
    //Given a string DI-seq S, let dp[i][j] represents the number of permutation of number 0, 1, ... , i,
    //satisfying DI-rule S.substr(0, i), and ending with digit j.
    //if(S[i-1] == 'D')
    //   dp[i][j] = dp[i-1][j] + dp[i-1][j+1] + ... + dp[i-1][i-1]
    //if(S[i-1] == 'I')
    //   dp[i][j] = dp[i-1][0] + dp[i-1][1] + ... + dp[i-1][j-1]
    public int numPermsDISequence(String S) {
        int n = S.length(), mod = (int)1e9 + 7;
        int[][] dp = new int[n + 1][n + 1];
        for (int j = 0; j <= n; j++) {
            dp[0][j] = 1;
        }
        for (int i = 0; i < n; i++) {
            if (S.charAt(i) == 'I') {
                for (int j = 0, cur = 0; j < n - i; j++) {
                    dp[i + 1][j] = cur = (cur + dp[i][j]) % mod;
                }
            } else {
                for (int j = n - i - 1, cur = 0; j >= 0; j--) {
                    dp[i + 1][j] = cur = (cur + dp[i][j + 1]) % mod;
                }
            }
        }
        return dp[n][0];
    }

    /**
     * https://leetcode.com/problems/valid-palindrome-iii/
     * Given a string s and an integer k, find out if the given string is a K-Palindrome or not.
     *
     * A string is K-Palindrome if it can be transformed into a palindrome by removing at most k characters from it.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "abcdeca", k = 2
     * Output: true
     * Explanation: Remove 'b' and 'e' characters.
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s has only lowercase English letters.
     * 1 <= k <= s.length
     */
    public boolean isValidPalindrome(String s, int k) {
        int longestPalin = 0;
        int n = s.length();
        if (n <= 1) {
            longestPalin = n;
        } else {
            if (n == 2) {
                if (s.charAt(0) == s.charAt(1)) {
                    longestPalin = 2;
                } else {
                    longestPalin = 1;
                }
            } else {
                int[][] cache = new int[s.length()][s.length()];
                for (int i=0; i<cache.length; i++) {
                    Arrays.fill(cache[i], -1);
                }
                longestPalin = isValidPalindromeDFS(s, 0, s.length()-1, cache);
            }
        }
        if (s.length() - longestPalin <= k) {
            return true;
        }
        return false;
    }

    private int isValidPalindromeDFS(String s, int start, int end, int[][] cache){
        if (start > end) {
            return 0;
        }
        if (start == end) {
            return 1;
        }
        if (cache[start][end] != -1) {
            return cache[start][end];
        }
        int temp = 0;
        if (s.charAt(start) == s.charAt(end)) {
            temp = 2 + isValidPalindromeDFS(s, start+1, end-1, cache);
        } else {
            temp = Math.max(isValidPalindromeDFS(s, start+1, end, cache), isValidPalindromeDFS(s, start, end-1, cache));
        }
        cache[start][end] = temp;
        return temp;
    }

    /**
     * https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
     *
     * In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.
     *
     * Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.
     *
     * Return the result as a list of indices representing the starting position of each interval (0-indexed).
     * If there are multiple answers, return the lexicographically smallest one.
     *
     * Example:
     *
     * Input: [1,2,1,2,6,7,5,1], 2
     * Output: [0, 3, 5]
     * Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
     * We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
     *
     *
     * Note:
     *
     * nums.length will be between 1 and 20000.
     * nums[i] will be between 1 and 65535.
     * k will be between 1 and floor(nums.length / 3).
     * @param nums
     * @param k
     * @return
     */
    //Trick: to find for each index, what's the max k sub array before or after it.
    //When we go through each k sub array, then we can concatenate the result for maximum.
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length, maxsum = 0;
        int[] presum = new int[n+1], posLeft = new int[n], posRight = new int[n], ans = new int[3];
        for (int i = 0; i < n; i++) {
            presum[i+1] = presum[i] + nums[i];
        }
        // DP for starting index of the left max sum interval
        for (int i = k, tot = presum[k] - presum[0]; i < n; i++) {
            if (presum[i+1]-presum[i+1-k] > tot) {
                posLeft[i] = i+1-k;
                tot = presum[i+1]-presum[i+1-k];
            } else {
                posLeft[i] = posLeft[i-1];
            }
        }
        // DP for starting index of the right max sum interval
        // caution: the condition is ">= tot" for right interval, and "> tot" for left interval
        posRight[n-k] = n-k;
        for (int i = n-k-1, tot = presum[n]-presum[n-k]; i >= 0; i--) {
            if (presum[i+k]-presum[i] >= tot) {
                posRight[i] = i;
                tot = presum[i+k]-presum[i];
            }
            else
                posRight[i] = posRight[i+1];
        }
        // test all possible middle interval
        for (int i = k; i <= n-2*k; i++) {
            int l = posLeft[i-1], r = posRight[i+k];
            int tot = (presum[i+k]-presum[i]) + (presum[l+k]-presum[l]) + (presum[r+k]-presum[r]);
            if (tot > maxsum) {
                maxsum = tot;
                ans[0] = l; ans[1] = i; ans[2] = r;
            }
        }
        return ans;
    }

    /**
     * https://leetcode.com/problems/make-array-strictly-increasing/
     * Given two integer arrays arr1 and arr2, return the minimum number of operations (possibly zero) needed to make arr1 strictly increasing.
     *
     * In one operation, you can choose two indices 0 <= i < arr1.length and 0 <= j < arr2.length and do the assignment arr1[i] = arr2[j].
     *
     * If there is no way to make arr1 strictly increasing, return -1.
     *
     * Example 1:
     *
     * Input: arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
     * Output: 1
     * Explanation: Replace 5 with 2, then arr1 = [1, 2, 3, 6, 7].
     * Example 2:
     *
     * Input: arr1 = [1,5,3,6,7], arr2 = [4,3,1]
     * Output: 2
     * Explanation: Replace 5 with 3 and then replace 3 with 4. arr1 = [1, 3, 4, 6, 7].
     * Example 3:
     *
     * Input: arr1 = [1,5,3,6,7], arr2 = [1,6,3,3]
     * Output: -1
     * Explanation: You can't make arr1 strictly increasing.
     *
     *
     * Constraints:
     *
     * 1 <= arr1.length, arr2.length <= 2000
     * 0 <= arr1[i], arr2[i] <= 10^9
     */
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        int[] sorted_deduped_arr2 = Arrays.stream(arr2).distinct().sorted().toArray();
        int res = makeArrayIncreasingDFS(arr1, sorted_deduped_arr2, 0, Integer.MIN_VALUE, new HashMap<String, Integer>());
        return res == Integer.MAX_VALUE ? -1 : res;
    }
    private int makeArrayIncreasingDFS(int[] arr1, int[] arr2, int idx, int prev, Map<String, Integer> memo) {
        String key = idx + "," + prev;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        if (idx >= arr1.length) {
            return 0;
        }
        int j = Arrays.binarySearch(arr2, prev);
        j = j < 0 ? -(j + 1) : j + 1;
        //Below is do one operation.
        int resultDropCur = Integer.MAX_VALUE;
        if (j < arr2.length) {
            resultDropCur = makeArrayIncreasingDFS(arr1, arr2, idx + 1, arr2[j], memo);
        }
        if (resultDropCur != Integer.MAX_VALUE) {
            resultDropCur++;
        }
        //Below is keep cur number, move on to next.
        int resultKeepCur = Integer.MAX_VALUE;
        if (prev < arr1[idx]) {
            //only this case can move on.
            resultKeepCur =  makeArrayIncreasingDFS(arr1, arr2, idx + 1, arr1[idx], memo);
        }
        memo.put(key, Math.min(resultDropCur, resultKeepCur));
        return memo.get(key);
    }

    /**
     * https://leetcode.com/problems/smallest-sufficient-team/
     * In a project, you have a list of required skills req_skills, and a list of people.
     * The i-th person people[i] contains a list of skills that person has.
     *
     * Consider a sufficient team: a set of people such that for every required skill in req_skills,
     * there is at least one person in the team who has that skill.  We can represent these teams by the index of each person: for example,
     * team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].
     *
     * Return any sufficient team of the smallest possible size, represented by the index of each person.
     *
     * You may return the answer in any order.  It is guaranteed an answer exists.
     *
     *
     * Example 1:
     * Input: req_skills = ["java","nodejs","reactjs"], people = [["java"],["nodejs"],["nodejs","reactjs"]]
     * Output: [0,2]
     *
     * Example 2:
     * Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"],
     * people = [["algorithms","math","java"],["algorithms","math","reactjs"],["java","csharp","aws"],["reactjs","csharp"],["csharp","math"],["aws","java"]]
     * Output: [1,2]
     *
     * Constraints:
     * 1 <= req_skills.length <= 16
     * 1 <= people.length <= 60
     * 1 <= people[i].length, req_skills[i].length, people[i][j].length <= 16
     * Elements of req_skills and people[i] are (respectively) distinct.
     * req_skills[i][j], people[i][j][k] are lowercase English letters.
     * Every skill in people[i] is a skill in req_skills.
     * It is guaranteed a sufficient team exists.
     */
    private List<Integer> ans = new ArrayList<Integer>();
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        Map<String, Integer> skillIndex = new HashMap<>();
        for (int i=0; i<req_skills.length; i++) {
            skillIndex.put(req_skills[i], i);
        }
        int target = (int)Math.pow(2, req_skills.length) - 1;
        int[] pe = new int[people.size()];
        for (int i = 0; i < pe.length; i++) {
            for (String p : people.get(i)) {
                int skill = skillIndex.get(p);
                pe[i] += 1 << skill;
            }
        }
        smallestSufficientTeamDFS(pe, 0, new ArrayList<Integer>(), target);

        int[] ret = new int[ans.size()];
        for (int i=0; i<ans.size(); i++) {
            ret[i] = ans.get(i);
        }
        return ret;
    }

    private void smallestSufficientTeamDFS(int[] pe, int state, ArrayList<Integer> temp, int target) {
        if (state == target) {
            if (ans.size() == 0 || ans.size() > temp.size()) {
                ans = new ArrayList<Integer>(temp);
            }
            return;
        }
        if (ans.size() != 0 && temp.size() >= ans.size()) return;

        int zeroBit = 0;
        while (((state>>zeroBit)&1) == 1) {
            zeroBit++;
        }
        for (int i = 0; i < pe.length; i++) {
            int per = pe[i];
            if (((per>>zeroBit)&1) == 1) {
                temp.add(i);
                smallestSufficientTeamDFS(pe,state|per, temp, target);
                temp.remove(temp.size() - 1);
            }
        }
    }

    /**
     * https://leetcode.com/problems/maximum-vacation-days/
     * LeetCode wants to give one of its best employees the option to travel among N cities to collect algorithm problems.
     * But all work and no play makes Jack a dull boy, you could take vacations in some particular cities and weeks.
     * Your job is to schedule the traveling to maximize the number of vacation days you could take, but there are certain rules and restrictions you need to follow.
     *
     * Rules and restrictions:
     * You can only travel among N cities, represented by indexes from 0 to N-1. Initially, you are in the city indexed 0 on Monday.
     * The cities are connected by flights. The flights are represented as a N*N matrix (not necessary symmetrical),
     * called flights representing the airline status from the city i to the city j. If there is no flight from the city i to the city j,
     * flights[i][j] = 0; Otherwise, flights[i][j] = 1. Also, flights[i][i] = 0 for all i.
     * You totally have K weeks (each week has 7 days) to travel. You can only take flights at most once per day and can only take flights
     * on each week's Monday morning. Since flight time is so short, we don't consider the impact of flight time.
     * For each city, you can only have restricted vacation days in different weeks, given an N*K matrix called days representing this
     * relationship. For the value of days[i][j], it represents the maximum days you could take vacation in the city i in the week j.
     * You're given the flights matrix and days matrix, and you need to output the maximum vacation days you could take during K weeks.
     *
     * Example 1:
     * Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[1,3,1],[6,0,3],[3,3,3]]
     * Output: 12
     * Explanation:
     * Ans = 6 + 3 + 3 = 12.
     *
     * One of the best strategies is:
     * 1st week : fly from city 0 to city 1 on Monday, and play 6 days and work 1 day.
     * (Although you start at city 0, we could also fly to and start at other cities since it is Monday.)
     * 2nd week : fly from city 1 to city 2 on Monday, and play 3 days and work 4 days.
     * 3rd week : stay at city 2, and play 3 days and work 4 days.
     * Example 2:
     * Input:flights = [[0,0,0],[0,0,0],[0,0,0]], days = [[1,1,1],[7,7,7],[7,7,7]]
     * Output: 3
     * Explanation:
     * Ans = 1 + 1 + 1 = 3.
     *
     * Since there is no flights enable you to move to another city, you have to stay at city 0 for the whole 3 weeks.
     * For each week, you only have one day to play and six days to work.
     * So the maximum number of vacation days is 3.
     * Example 3:
     * Input:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[7,0,0],[0,7,0],[0,0,7]]
     * Output: 21
     * Explanation:
     * Ans = 7 + 7 + 7 = 21
     *
     * One of the best strategies is:
     * 1st week : stay at city 0, and play 7 days.
     * 2nd week : fly from city 0 to city 1 on Monday, and play 7 days.
     * 3rd week : fly from city 1 to city 2 on Monday, and play 7 days.
     * Note:
     * N and K are positive integers, which are in the range of [1, 100].
     * In the matrix flights, all the values are integers in the range of [0, 1].
     * In the matrix days, all the values are integers in the range [0, 7].
     * You could stay at a city beyond the number of vacation days, but you should work on the extra days, which won't be counted as vacation days.
     * If you fly from the city A to the city B and take the vacation on that day, the deduction towards vacation days will count towards the
     * vacation days of city B in that week.
     * We don't consider the impact of flight hours towards the calculation of vacation days.
     */
    //Semi graph traverse
    public int maxVacationDays(int[][] flights, int[][] days) {
        int totalCities = days.length, totalWeeks = days[0].length;
        int[][]memo = new int[totalCities][totalWeeks];
        return maxVacationDaysDFS(flights, days, 0, 0, totalCities, totalWeeks, memo);
    }
    private int maxVacationDaysDFS(int[][] flights, int[][] days, int curWeek, int curPos, int totalCities, int totalWeeks, int[][] memo){
        if (curWeek == totalWeeks) {
            return 0;
        }
        if (memo[curPos][curWeek] != 0) {
            return memo[curPos][curWeek];
        }
        int res = 0;
        for (int i = 0; i < totalCities; i++) {
            if (curPos == i || flights[curPos][i] == 1) {
                res = Math.max(res, days[i][curWeek] + maxVacationDaysDFS(flights, days, curWeek + 1, i, totalCities, totalWeeks, memo));
            }
        }
        memo[curPos][curWeek] = res;
        return res;
    }

    /**
     * You are installing a billboard and want it to have the largest height.  The billboard will have two steel supports,
     * one on each side.  Each steel support must be an equal height.
     *
     * You have a collection of rods which can be welded together.  For example, if you have rods of lengths 1, 2, and 3,
     * you can weld them together to make a support of length 6.
     *
     * Return the largest possible height of your billboard installation.  If you cannot support the billboard, return 0.
     *
     * Example 1:
     * Input: [1,2,3,6]
     * Output: 6
     * Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.
     *
     * Example 2:
     * Input: [1,2,3,4,5,6]
     * Output: 10
     * Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.
     *
     * Example 3:
     * Input: [1,2]
     * Output: 0
     * Explanation: The billboard cannot be supported, so we return 0.
     *
     * Note:
     * 0 <= rods.length <= 20
     * 1 <= rods[i] <= 1000
     * The sum of rods is at most 5000.
     * @param rods
     * @return
     */
    public int tallestBillboard(int[] rods) {
        int midSum = 0;
        for (int i=0; i<rods.length; i++) {
            midSum += rods[i];
        }
        return tallestBillboardDFS(rods, 0, 0, 0, midSum/2, new HashMap());
    }

    private int tallestBillboardDFS(int[] rods, int i, int s1, int s2, int mSum, Map<String, Integer> dp) {
        if (s1 > mSum || s2 > mSum) {
            return -1;
        }
        if (i == rods.length) {
            return s1 == s2 ? s1 : -1;
        }

        String dpKey = i + " " + Math.abs(s1 - s2);

        if (dp.containsKey(dpKey)) {
            return dp.get(dpKey) == -1 ? -1 : Math.max(s1, s2) + dp.get(dpKey);
        }

        int lBeam = tallestBillboardDFS(rods, i + 1, s1 + rods[i], s2, mSum, dp);
        int rBeam = tallestBillboardDFS(rods, i + 1, s1, s2 + rods[i], mSum, dp);
        int discard = tallestBillboardDFS(rods, i + 1, s1, s2, mSum, dp);
        int ans = Math.max(discard, Math.max(lBeam, rBeam));

        dp.put(dpKey, ans == -1 ? -1 : ans - Math.max(s1, s2));

        return ans;
    }

    /**
     * https://leetcode.com/problems/number-of-music-playlists/
     * Your music player contains N different songs and she wants to listen to L (not necessarily different) songs during your trip.
     * You create a playlist so that:
     *
     * Every song is played at least once
     * A song can only be played again only if K other songs have been played
     * Return the number of possible playlists.  As the answer can be very large, return it modulo 10^9 + 7.
     *
     * Example 1:
     * Input: N = 3, L = 3, K = 1
     * Output: 6
     * Explanation: There are 6 possible playlists. [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1].
     *
     * Example 2:
     * Input: N = 2, L = 3, K = 0
     * Output: 6
     * Explanation: There are 6 possible playlists. [1, 1, 2], [1, 2, 1], [2, 1, 1], [2, 2, 1], [2, 1, 2], [1, 2, 2]
     *
     * Example 3:
     * Input: N = 2, L = 3, K = 1
     * Output: 2
     * Explanation: There are 2 possible playlists. [1, 2, 1], [2, 1, 2]
     *
     * Note:
     * 0 <= K < N <= L <= 100
     */
    //F(N,L,K) = F(N - 1, L - 1, K) * N + F(N, L - 1, K) * (N - K)
    //    F(N - 1, L - 1, K)
    //    If only N - 1 in the L - 1 first songs.
    //    We need to put the rest one at the end of music list.
    //    Any song can be this last song, so there are N possible combinations.
    //
    //    F(N, L - 1, K)
    //    If already N in the L - 1 first songs.
    //    We can put any song at the end of music list,
    //    but it should be different from K last song.
    //    We have N - K choices.
    private long mod = (long)1e9 + 7;
    public int numMusicPlaylists(int N, int L, int K) {
        long[][] dp = new long[N + 1][L + 1];
        for (int i = K + 1; i <= N; ++i) {
            for (int j = i; j <= L; ++j) {
                if ((i == j) || (i == K + 1)) {
                    dp[i][j] = factorial(i);
                } else {
                    dp[i][j] = (dp[i - 1][j - 1] * i + dp[i][j - 1] * (i - K)) % mod;
                }
            }
        }
        return (int) dp[N][L];
    }
    private long factorial(int n) {
        return n > 0 ? (factorial(n - 1) * n % mod) : 1;
    }

    /**
     * https://leetcode.com/problems/student-attendance-record-ii/
     *
     * Given a positive integer n, return the number of all possible attendance records with length n, which will be regarded
     * as rewardable. The answer may be very large, return it after mod 109 + 7.
     *
     * A student attendance record is a string that only contains the following three characters:
     *
     * 'A' : Absent.
     * 'L' : Late.
     * 'P' : Present.
     * A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).
     *
     * Example 1:
     * Input: n = 2
     * Output: 8
     * Explanation:
     * There are 8 records with length 2 will be regarded as rewardable:
     * "PP" , "AP", "PA", "LP", "PL", "AL", "LA", "LL"
     * Only "AA" won't be regarded as rewardable owing to more than one absent times.
     * Note: The value of n won't exceed 100,000.
     * @param n
     * @return
     */
    //https://leetcode.com/problems/student-attendance-record-ii/discuss/101643/Share-my-O(n)-C%2B%2B-DP-solution-with-thinking-process-and-explanation
    //Trick: consider end with P and end with L first, then insert A into both sequence.
    public int checkRecord(int n) {
        int mod = 1000000007;
        long[] dpP = new long[n+1]; //end with P w/o A
        long[] dpL = new long[n+1]; //end with L w/o A
        dpP[0] = dpP[1] = dpL[1] = 1;
        for(int i = 2; i <= n; i++){
            dpP[i] = (dpP[i-1] + dpL[i-1]) % mod;
            dpL[i] = (dpP[i-1] + dpP[i-2]) % mod;
        }
        long res = (dpP[n] + dpL[n]) % mod;
        //insert A
        for(int i = 0; i < n; i++){
            long s = ((dpP[i] + dpL[i])%mod * (dpP[n-i-1] + dpL[n-i-1])%mod )% mod;
            res = (res + s) % mod;
        }
        return (int) res;
    }

//    private int res = 0;
//    private char[] chars = {'A', 'L', 'P'};
//    public int checkRecord(int n) {
//        StringBuilder sb = new StringBuilder();
//        checkRecordHelper(n, sb, 0);
//        return res;
//    }
//
//    private void checkRecordHelper(int n, StringBuilder sb, int pos) {
//        if (pos == n) {
//            res = (res + 1) % 1000000007;
//            return;
//        }
//        for (int i=0; i<3; i++) {
//            if (i==0 && sb.indexOf("A") != -1) {
//                continue;
//            }
//            if (i==1 && (sb.length() >= 2 && sb.charAt(sb.length() -1) == 'L' && sb.charAt(sb.length() - 2) == 'L')) {
//                continue;
//            }
//            sb.append(chars[i]);
//            checkRecordHelper(n, sb, pos+1);
//            sb.setLength(sb.length() - 1);
//        }
//    }

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
