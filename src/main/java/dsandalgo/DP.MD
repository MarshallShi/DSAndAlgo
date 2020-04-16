Important DP problem pattern

Distinct ways
https://leetcode.com/problems/decode-ways/
https://leetcode.com/problems/fibonacci-number/
https://leetcode.com/problems/climbing-stairs/
https://leetcode.com/problems/unique-paths/
Solution: define the state, clearly these state only depending on previous state, so the O(n) space and reduce to O(1).

https://leetcode.com/problems/count-vowels-permutation/

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

Min Max in path
https://leetcode.com/problems/minimum-path-sum/
https://leetcode.com/problems/paint-house/

Sum
https://leetcode.com/problems/unique-paths/

Longest increasing subsequence
https://leetcode.com/problems/longest-increasing-subsequence/

Solution 1: use DP, O(n2)
Solution 2: binary search, O(n2)

    public int lengthOfLIS(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }
        int[] dp = new int[nums.length];
        for (int i=0; i<nums.length; i++) {
            dp[i] = 1;
        }
        int max = 0;
        for (int i = 1; i<nums.length; i++) {
            int cur = dp[i];
            for (int j=0; j<i; j++) {
                if (nums[i] > nums[j] && dp[j] + 1 > cur) {
                    cur = dp[j] + 1;
                }
            }
            dp[i] = cur;
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }
    
    public int lengthOfLIS(int[] nums) {
        //Patience sorting algo implementation.
        List<Integer> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            int pile = Collections.binarySearch(piles, num);
            if (pile < 0) {
                //~(bitwise compliment)Binary Ones Complement Operator is unary and has the effect of 'flipping' bits.
                pile = ~pile;
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
        }
        return piles.size();
    }

Knapsack problem

https://leetcode.com/problems/partition-equal-subset-sum/
It is an 0/1 knapsack problem, for each number, we can pick it or not. 
Let us assume dp[i][j] means whether the specific sum j can be gotten from the first i numbers. 
If we can pick such a series of numbers from 0-i whose sum is j, dp[i][j] is true, otherwise it is false.
Base case: dp[0][0] is true; (zero number consists of sum 0 is true)
Transition function: 
For each number, if we don't pick it, dp[i][j] = dp[i-1][j], 
which means if the first i-1 elements has made it to j, dp[i][j] would also make it to j (we can just ignore nums[i]). 
If we pick nums[i]. dp[i][j] = dp[i-1][j-nums[i]], 
which represents that j is composed of the current value nums[i] and the remaining composed of other previous numbers. 
Thus, the transition function is dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 == 1) return false;

        int target = sum / 2;
        boolean[][] dp = new boolean[nums.length][target + 1];
        // deal with the first row
        if (nums[0] <= target) dp[0][nums[0]] = true;

        // deal with the first col
        for (int i = 0; i < nums.length; i++) dp[i][0] = true;

        // deal with the rest
        for (int i = 1; i < nums.length; i++) {
            for (int j = 1; j < target + 1; j++) {
                if (j < nums[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

DP on String, with HashMap

Longest String Chain
https://leetcode.com/problems/longest-string-chain/
Sort the string by length first, then we can use a map as DP to store for each word, the longest str chain.

    public int longestStrChain(String[] words) {
        //dp map store for each word, the longest str chain.
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }
    
    

Word break
https://leetcode.com/problems/word-break/
https://leetcode.com/problems/concatenated-words/



Unique Binary Search Trees
https://leetcode.com/problems/unique-binary-search-trees/
Math solution: F(i, n) = G(i-1) * G(n-i)  (1 <= i <= n), assume any i, number of unique tree is G(i-1) * G(n-i). 
ie: left hand numbers for G(i-1) different sub trees, right hand numbers for G(n-i) different sub trees.

    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i=2; i<=n; i++) {
            int tempSum = 0;
            for (int j=1; j<=i; j++) {
                tempSum = tempSum + dp[j-1]*dp[i-j];
            }
            dp[i] = tempSum;
        }
        return dp[n];
    }

    public int numTrees_recursive(int n) {
        return numTreesHelper(1, n);
    }

    private int numTreesHelper(int lo, int hi) {
        if (lo >= hi) return 1;
        int total = 0;
        for (int i = lo; i <= hi; i++) {
            total += numTreesHelper(lo, i - 1) * numTreesHelper(i + 1, hi);
        }
        return total;
    }
    
    
    
String based DP

Interleaving String
https://leetcode.com/problems/interleaving-string/
NOTE: interleaving string mean s3's letter is comopsed by s1 and s2, while all letters maintain its original sequence in s1 and s2.
DFS + Memo.

    public boolean isInterleave(String s1, String s2, String s3) {
        char[] c1 = s1.toCharArray(), c2 = s2.toCharArray(), c3 = s3.toCharArray();
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length()) {
            return false;
        }
        return isInterleaveDFS(c1, c2, c3, 0, 0, 0, new boolean[m + 1][n + 1]);
    }

    private boolean isInterleaveDFS(char[] c1, char[] c2, char[] c3, int i, int j, int k, boolean[][] invalid) {
        if (invalid[i][j]) {
            return false;
        }
        if (k == c3.length) {
            return true;
        }
        boolean validFromC1 = false;
        if (i < c1.length && c1[i] == c3[k]) {
            validFromC1 = isInterleaveDFS(c1, c2, c3, i + 1, j, k + 1, invalid);
        }
        boolean validFromC2 = false;
        if (j < c2.length && c2[j] == c3[k]) {
            validFromC2 = isInterleaveDFS(c1, c2, c3, i, j + 1, k + 1, invalid);
        }
        boolean valid = validFromC1 || validFromC2;
        if (!valid) {
            invalid[i][j] = true;
        }
        return valid;
    }

Palindromic Substrings
https://leetcode.com/problems/palindromic-substrings/

    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length();
        int res = 0;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    res++;
                }
            }
        }
        return res;
    }

    //Solution 2: similar idea, but avoid the average O(n2)
    public int countSubstrings_2(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += extractPalindrome(s, i, i);//odd length
            count += extractPalindrome(s, i, i + 1);//even length
        }
        return count;
    }

    public int extractPalindrome(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && (s.charAt(left) == s.charAt(right))) {
            left--;
            right++;
            count++;
        }
        return count;
    }
    

Paint House
https://leetcode.com/problems/paint-house/
Solution 1: top down, with memo.
Solution 2: bottom up, with space optimization

    private int[][] costs;
    private Map<String, Integer> memo;

    //Solution 1
    public int minCost(int[][] costs) {
        if (costs.length == 0) {
            return 0;
        }
        this.costs = costs;
        this.memo = new HashMap<>();
        return Math.min(paintCost(0, 0), Math.min(paintCost(0, 1), paintCost(0, 2)));
    }

    private int paintCost(int n, int color) {
        if (memo.containsKey(getKey(n, color))) {
            return memo.get(getKey(n, color));   
        }
        int totalCost = costs[n][color];
        if (n == costs.length - 1) {
        } else if (color == 0) { // Red
            totalCost += Math.min(paintCost(n + 1, 1), paintCost(n + 1, 2));
        } else if (color == 1) { // Green
            totalCost += Math.min(paintCost(n + 1, 0), paintCost(n + 1, 2));
        } else { // Blue
            totalCost += Math.min(paintCost(n + 1, 0), paintCost(n + 1, 1));
        }        
        memo.put(getKey(n, color), totalCost);

        return totalCost;
    }

    private String getKey(int n, int color) {
        return String.valueOf(n) + " " + String.valueOf(color);
    }

    //Solution 1
    public int minCost=(int[][] costs) {
        for (int n = costs.length - 2; n >= 0; n--) {
            // Total cost of painting the nth house red.
            costs[n][0] += Math.min(costs[n + 1][1], costs[n + 1][2]);
            // Total cost of painting the nth house green.
            costs[n][1] += Math.min(costs[n + 1][0], costs[n + 1][2]);
            // Total cost of painting the nth house blue.
            costs[n][2] += Math.min(costs[n + 1][0], costs[n + 1][1]);
        }

        if (costs.length == 0) return 0;   

        return Math.min(Math.min(costs[0][0], costs[0][1]), costs[0][2]);
    }
    
Wildcard Matching
https://leetcode.com/problems/wildcard-matching/

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for (int i=1; i<=m; i++) {
            dp[i][0] = false;
        }
        for (int j=1; j<=n; j++) {
            if (p.charAt(j-1) == '*') {
                dp[0][j] = true;
            } else {
                break;
            }
        }
        for (int i=1; i<=m; i++) {
            for (int j=1; j<=n; j++) {
                if (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?') {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    if (p.charAt(j-1) == '*') {
                        dp[i][j] = dp[i-1][j] || dp[i][j-1];
                    }
                }
            }
        }
        return dp[m][n];
    }

Regular Expression Matching
https://leetcode.com/problems/regular-expression-matching/

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];

        dp[0][0] = true;

        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' && dp[0][i-1]) {
                dp[0][i+1] = true;
            }
        }
        for (int i = 0 ; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.') {
                    dp[i+1][j+1] = dp[i][j];
                } else {
                    if (p.charAt(j) == '*') {
                        if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                            dp[i+1][j+1] = dp[i+1][j-1];
                        } else {
                            dp[i+1][j+1] = (dp[i+1][j] || dp[i+1][j-1] || dp[i][j+1]);
                        }
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }    

Coin Change

https://leetcode.com/problems/coin-change/
Find the fewest coin to make up the total amount.

    public int coinChange(int[] coins, int amount) {
        //pre load dp with max value.
        int max = amount + 1;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i=1; i<=amount; i++) {
            for (int j=0; j<coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        //check if could not allocate right coins.
        return dp[amount] > amount ? -1 : dp[amount];
    }

https://leetcode.com/problems/coin-change-2/
Count how many different ways to make up the total amount
Difference with Complete Knapsack problem: for each coin, we can use as many times as we want.

Let's define dp[i][j] : the number of combinations to make up amount j by using the first i types of coins
State transition:
1). not using the ith coin, only using the first i-1 coins to make up amount j, then we have dp[i-1][j] ways.
2). using the ith coin, since we can use unlimited same coin, we need to know how many ways to make up amount j - coins[i-1] by using first i coins(including ith), which is dp[i][j-coins[i-1]]

    //2D array to store the state.
    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        dp[0][0] = 1;        
        for (int j = 1; j <= coins.length; j++) {
            dp[j][0] = 1;
            for (int i = 1; i <= amount; i++) {
                dp[j][i] = dp[j - 1][i];
                if (i - coins[j - 1] >= 0) {
                    dp[j][i] += dp[j][i - coins[j - 1]];
                }
            }
        }
        return dp[coins.length][amount];
    }
    
    //Optimize to 1D array
    public int change(int amount, int[] coins) {
        //dp represent for an amount, how many different ways
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 0; i < coins.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                if (j - coins[i] >= 0) {
                    dp[j] += dp[j - coins[i]];
                }
            }
        }
        return dp[amount];
    }


Maximum Profit in Job Scheduling
https://leetcode.com/problems/maximum-profit-in-job-scheduling/
This is a solution built on DP, used similar idea of Longest Increasing Subsequence.
1. Sort the input by the end time. 
2. Create a dp array to track till i' job, the max profit.
3. Loop through the jobs, update the max profit base on previous ending index's max profit and current profit.
4. Apply binary search to find the previous ending index as it is sorted in step 1.


    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int[][] jobs = new int[startTime.length][3];
        for (int i = 0; i < startTime.length; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        // sort by endTime
        Arrays.sort(jobs, (a1, a2) -> a1[1] - a2[1]);
        // dp array to track till i's job, the max profit.
        int[] dp = new int[jobs.length];
        dp[0] = jobs[0][2];
        for (int i = 1; i < jobs.length; i++) {
            int start = jobs[i][0], end = jobs[i][1], curProfit = jobs[i][2];
            // find previous endTime index
            int prevIdx = binarySearchJobs(jobs, i);
            // current max profit : max(use current, not use current).
            if (prevIdx != -1) {
                dp[i] = Math.max(dp[prevIdx] + curProfit, dp[i - 1]);
            } else {
                dp[i] = Math.max(curProfit, dp[i - 1]);
            }
        }
        return dp[dp.length - 1];
    }

    private int binarySearchJobs(int[][] jobs, int index) {
        int start = 0, end = index - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (jobs[mid][1] <= jobs[index][0]) {
                if (jobs[mid + 1][1] <= jobs[index][0]) {
                    start = mid + 1;
                } else {
                    return mid;
                }
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

Maximum Length of a Concatenated String with Unique Characters    
https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
DP + Bit
This is similar to Longest Increasing Subsequence.  We use bit to store the length after adding current.

    public int maxLength(List<String> arr) {
        List<Integer> dp = new ArrayList<>();
        dp.add(0);
        int res = 0;
        for (String s : arr) {
            int a = 0, dup = 0;
            for (char c : s.toCharArray()) {
                dup |= a & (1 << (c - 'a'));
                a |= 1 << (c - 'a');
            }
            if (dup > 0) {
                continue;
            }
            for (int i = dp.size() - 1; i >= 0; --i) {
                if ((dp.get(i) & a) > 0) {
                    continue;
                }
                dp.add(dp.get(i) | a);
                res = Math.max(res, Integer.bitCount(dp.get(i) | a));
            }
        }
        return res;
    }