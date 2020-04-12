From naive recursion to DP.

Decode ways
https://leetcode.com/problems/decode-ways/

    //Naive: Recursion O(2^n)
    int numDecodings_1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return numDecodings(0, s);
    }

    int numDecodings(int p, String s) {
        int n = s.length();
        if (p == n) return 1;
        if (s.charAt(p) == '0') return 0;
        int res = numDecodings(p + 1, s);
        if (p < n - 1 && (s.charAt(p) == '1' || (s.charAt(p) == '2' && s.charAt(p + 1) < '7'))) {
            res += numDecodings(p + 2, s);
        }
        return res;
    }

    //Solution 2: Memoization O(n)
    public int numDecodings_2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] mem = new int[s.length() + 1];
        Arrays.fill(mem, -1);
        return numDecodings(0, s, mem);
    }

    private int numDecodings(int p, String s, int[] mem) {
        if (mem[p] > -1) {
            return mem[p];
        }
        int n = s.length();
        if (p == n) {
            mem[p] = 1;
            return 1;
        }
        if (s.charAt(p) == '0') {
            mem[p] = 0;
            return 0;
        }
        int res = numDecodings(p + 1, s);
        if (p < n - 1 && (s.charAt(p) == '1' || (s.charAt(p) == '2' && s.charAt(p + 1) < '7'))) {
            res += numDecodings(p + 2, s);
        }
        mem[p] = res;
        return res;
    }

    //Solution 3: dp O(n) time and space, this can be converted from #2 with copy and paste.
    public int numDecodings_3(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1];
                if (i < n - 1 && (s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) < '7')) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return n == 0 ? 0 : dp[0];
    }

    //Solution 4: dp constant space
    public int numDecodings_4(String s) {
        int n = s.length();
        int p = 1; //simulate dp[i+1]
        int pp = 0; //simulate dp[i+2]
        for (int i = n - 1; i >= 0; i--) {
            int cur = s.charAt(i) == '0' ? 0 : p;
            if (i < n - 1 && (s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) < '7')) {
                cur += pp;
            }
            pp = p;
            p = cur;
        }
        return n == 0 ? 0 : p;
    }
    

Edit Distance
https://leetcode.com/problems/edit-distance/
    
    /**
     * Recursive solution.
     * For each poisition, check three subproblem:
     * 1. insert
     * 2. delete
     * 3. replace
     * We only modify the first string since no matter which one we choose, the result is the same. 
     * Got TLE since we recursively solve the same subproblem several times.
     * Appromixately O(len1 ^ 3) time in the worst case.
     * Need to optimize it using cache, which is the idea of dynamic programming. 
     * The key point is to find out the subproblem we have solved duplicately and cache the recursion.
     * Noticed that each subproblem is specificed by i and j pointer, so we can cache the result of these subproblems. 
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null || word1.length() == 0) return word2.length();
        if (word2 == null || word2.length() == 0) return word1.length();
        return minDistanceDFS(word1, word2, 0, 0);
    }
    
    private int minDistanceDFS(String s1, String s2, int i, int j) {
        //If one of the string's pointer have reached the end of it
        if (s1.length() == i) {
            return s2.length() - j;
        }
        if (s2.length() == j) {
            return s1.length() - i;
        }
        
        int res;
        //If current poisition is the same.
        if (s1.charAt(i) == s2.charAt(j)) {
            res = minDistanceDFS(s1, s2, i + 1, j + 1);
        } else {
            //Case1: insert
            int insert = minDistanceDFS(s1, s2, i, j + 1);
            //Case2: delete
            int delete = minDistanceDFS(s1, s2, i + 1, j);
            //Case3: replace
            int replace = minDistanceDFS(s1, s2, i + 1, j + 1);
            res = Math.min(Math.min(insert, delete), replace) + 1;
        }
        
        return res;
    }


    /**
     * Optimization using dynamic programming
     * Top-down solution
     * O(len1 * len2) time, O(len1 * len2) space
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) return -1;
        if (word1.length() == 0) return word2.length();
        if (word2.length() == 0) return word1.length();
        
        char[] c1 = word1.toCharArray();
        char[] c2 = word2.toCharArray();
        
        int[][] cache = new int[c1.length][c2.length];
        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c2.length; j++) {
                cache[i][j] = -1;
            }
        }
        
        return minDistanceDFS(c1, c2, 0, 0, cache);
    }
    
    private int minDistanceDFS(char[] c1, char[] c2, int i, int j, int[][] cache) {
        if (c1.length == i) return c2.length - j;
        if (c2.length == j) return c1.length - i;
        
        if (cache[i][j] != -1) {
            return cache[i][j];
        }
        
        if (c1[i] == c2[j]) {
            cache[i][j] = minDistanceDFS(c1, c2, i + 1, j + 1, cache);
        } else {
            //Case1: insert
            int insert = minDistanceDFS(c1, c2, i, j + 1, cache);
            //Case2: delete
            int delete = minDistanceDFS(c1, c2, i + 1, j, cache);
            //Case3: replace
            int replace = minDistanceDFS(c1, c2, i + 1, j + 1, cache);
            
            cache[i][j] = Math.min(Math.min(insert, delete), replace) + 1;
        }
        
        return cache[i][j];
    }
    
    /**
     * Bottom-up approach
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) return -1;
        if (word1.length() == 0) return word2.length();
        if (word2.length() == 0) return word1.length();
        
        char[] c1 = word1.toCharArray();
        char[] c2 = word2.toCharArray();
        
        int[][] matched = new int[c1.length + 1][c2.length + 1];
        //matched[length of c1 already been matched][length of c2 already been matched]
        
        for (int i = 0; i <= c1.length; i++) {
            matched[i][0] = i;
        }
        for (int j = 0; j <= c2.length; j++) {
            matched[0][j] = j;
        }
        
        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c2.length; j++) {
                if (c1[i] == c2[j]) {
                    matched[i + 1][j + 1] = matched[i][j];
                } else {
                    matched[i + 1][j + 1] = Math.min(Math.min(matched[i][j + 1], matched[i + 1][j]), matched[i][j]) + 1;
                    //Since it is bottom up, we are considering in the ascending order of indexes.
                    //Insert means plus 1 to j, delete means plus 1 to i, replace means plus 1 to both i and j. 
                    //above sequence is delete, insert and replace. 
                }
            }
        }
        
        return matched[c1.length][c2.length];
    }

Split Array Largest Sum    
https://leetcode.com/problems/split-array-largest-sum/
***Partition***

    
    //Top down with memo
    public int splitArray(int[] nums, int m) {
        int[] sum = new int[nums.length];
        for (int i = 0;i < nums.length; i++) {
            sum[i] = nums[i] + (i > 0 ? sum[i-1] : 0);
        }
        int[][] cache = new int[1001][51];
        return splitArrayHelper(nums, m, sum, nums.length, 0, cache);
    }

    public int splitArrayHelper(int[] A, int k, int[] sum, int len, int s, int[][] cache) {
        if (cache[s][k] != 0) {
            return cache[s][k];
        }
        if (k == 1) {
            return (sum[len-1] - sum[s] + A[s]);
        }
        int num = Integer.MAX_VALUE;
        for (int i = s; i + k <= len ; i++) {
            num = Math.min(num, Math.max(sum[i] - sum[s] + A[s], splitArrayHelper(A, k-1, sum, len, i+1, cache)));
        }
        cache[s][k] = num;
        return num;
    }

    //Bottom up
    public int splitArray(int[] nums, int m) {
        if (nums == null) {
            return -1;
        }

        int[][] dp = new int[m][nums.length];
        dp[0][0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[0][i] = nums[i] + dp[0][i - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = i; j < nums.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k < j; k++) {
                    min = Math.min(min, Math.max(dp[i - 1][k], dp[0][j] - dp[0][k]));
                }
                dp[i][j] = min;
            }
        }
        return dp[m - 1][nums.length - 1];
    }
    
Burst Balloons
https://leetcode.com/problems/burst-balloons/
General trick:
1. ***Reverse thinking***. From the last balloon to first. If bursting last one, no other balloon affect the state.
2. ***Partition*** the array for left part and right part once we try use i's balloon as last one.

    
    //Method 1: Top down
    public int maxCoins(int[] iNums) {
        int[] nums = new int[iNums.length + 2];
        int n = 1;
        for (int x : iNums) {
            if (x > 0) {
                nums[n++] = x;
            }
        }
        //introduce two new leftMost and rightMost, both be 1.
        nums[0] = nums[n++] = 1;
        int[][] memo = new int[n][n];
        return burst(memo, nums, 0, n - 1);
    }

    private int burst(int[][] memo, int[] nums, int left, int right) {
        //The left and right are remaining which was the faked one, return 0.
        if (left + 1 == right) {
            return 0;
        }
        if (memo[left][right] > 0) {
            return memo[left][right];
        }
        int ans = 0;
        for (int i = left + 1; i < right; ++i) {
            //current result = burst current + burst left + burst right.
            ans = Math.max(ans, nums[left] * nums[i] * nums[right] + burst(memo, nums, left, i) + burst(memo, nums, i, right));
        }
        memo[left][right] = ans;
        return ans;
    }

    //Method 2: bottom up
    public int maxCoins_bottomUp(int[] iNums) {
        int[] nums = new int[iNums.length + 2];
        int n = 1;
        for (int x : iNums) {
            if (x > 0) {
                nums[n++] = x;
            }
        }
        nums[0] = nums[n++] = 1;
        int[][] dp = new int[n][n];
        for (int k = 2; k < n; ++k) {
            for (int left = 0; left < n - k; ++left) {
                int right = left + k;
                for (int i = left + 1; i < right; ++i) {
                    dp[left][right] = Math.max(dp[left][right],
                            nums[left] * nums[i] * nums[right] + dp[left][i] + dp[i][right]);
                }
            }
        }
        return dp[0][n - 1];
    }    
    
    


