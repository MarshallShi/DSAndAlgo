package dsandalgo.string;

import java.util.HashSet;

public class RollingHashExe {

    public static void main(String[] args) {
        RollingHashExe exe = new RollingHashExe();
        System.out.println(exe.longestPrefix("blablabla"));
    }

    /**
     * https://leetcode.com/problems/longest-happy-prefix/
     * @param s
     * @return
     */
    public String longestPrefix(String s) {
        long hashLow = 0, hashHigh = 0, mul = 1, len = 0, mod = 1000000007;
        //i: from low to high; j: from high to low.
        //Compute their rolling hash respectively.
        for (int i = 0, j = s.length() - 1; j > 0 && i < s.length() - 1; ++i, --j) {
            //low to high: always multiply a number, then plus the current char.
            int lo = s.charAt(i) - 'a';
            hashLow = (hashLow * 26 + lo) % mod;

            //high to low: use hi to mulitply current mul then plus existing hash.
            int hi = s.charAt(j) - 'a';
            hashHigh = (hashHigh + mul * hi) % mod;
            //iterative mulitply to avoid overflow.
            mul = mul * 26 % mod;

            //better to do a compare to avoid collision due to the mod.
            if (hashLow == hashHigh) {
                len = i + 1;
            }
        }
        return s.substring(0, (int)len);
    }

    /**
     * https://leetcode.com/problems/distinct-echo-substrings/
     * Return the number of distinct non-empty substrings of text that can be written as the concatenation of some string with itself (i.e. it can be written as a + a where a is some string).
     *
     *
     *
     * Example 1:
     *
     * Input: text = "abcabcabc"
     * Output: 3
     * Explanation: The 3 substrings are "abcabc", "bcabca" and "cabcab".
     * Example 2:
     *
     * Input: text = "leetcodeleetcode"
     * Output: 2
     * Explanation: The 2 substrings are "ee" and "leetcodeleetcode".
     *
     *
     * Constraints:
     *
     * 1 <= text.length <= 2000
     * text has only lowercase English letters.
     */
    long BASE = 29L, MOD = 1000000007L;
    public int distinctEchoSubstrings(String str) {
        HashSet<Long> set = new HashSet<>();
        int n = str.length();
        long[] hash = new long[n + 1]; // hash[i] is hash value from str[0..i]
        long[] pow = new long[n + 1]; // pow[i] = BASE^i
        pow[0] = 1;
        for (int i = 1; i <= n; i++) {
            hash[i] = (hash[i - 1] * BASE + str.charAt(i - 1)) % MOD;
            pow[i] = pow[i - 1] * BASE % MOD;
        }
        for (int i = 0; i < n; i++) {
            for (int len = 2; i + len <= n; len += 2) {
                int mid = i + len / 2;
                long hash1 = getHash(i, mid, hash, pow);
                long hash2 = getHash(mid, i + len, hash, pow);
                if (hash1 == hash2) set.add(hash1);
            }
        }
        return set.size();
    }

    private long getHash(int l, int r, long[] hash, long[] pow) {
        return (hash[r] - hash[l] * pow[r - l] % MOD + MOD) % MOD;
    }
}
