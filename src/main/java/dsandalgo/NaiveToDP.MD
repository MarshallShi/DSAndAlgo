Important DP problem pattern

Example 1: 

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