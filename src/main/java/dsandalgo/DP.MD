Important DP problem pattern

Distinct ways
https://leetcode.com/problems/decode-ways/
https://leetcode.com/problems/fibonacci-number/
https://leetcode.com/problems/climbing-stairs/
https://leetcode.com/problems/unique-paths/
Solution: define the state, clearly these state only depending on previous state, so the O(n) space and reduce to O(1).


Min Max in path
https://leetcode.com/problems/minimum-path-sum/
https://leetcode.com/problems/paint-house/


Knapsack problem


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

