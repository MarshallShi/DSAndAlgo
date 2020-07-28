package dsandalgo.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class RollingHashExe {

    public static void main(String[] args) {
        RollingHashExe exe = new RollingHashExe();
        System.out.println(exe.longestPrefix("blablabla"));
    }

    /**
     * https://leetcode.com/problems/longest-duplicate-substring/
     *
     * Given a string S, consider all duplicated substrings: (contiguous) substrings of S that occur 2 or more times.  (The occurrences may overlap.)
     *
     * Return any duplicated substring that has the longest possible length.  (If S does not have a duplicated substring, the answer is "".)
     *
     *
     *
     * Example 1:
     *
     * Input: "banana"
     * Output: "ana"
     *
     * Example 2:
     *
     * Input: "abcd"
     * Output: ""
     *
     *
     * Note:
     *
     * 2 <= S.length <= 10^5
     * S consists of lowercase English letters.
     *
     * @param S
     * @return
     */
    //https://leetcode.com/problems/longest-duplicate-substring/discuss/327643/Step-by-step-to-understand-the-binary-search-solution
    public String longestDupSubstring(String S) {
        // edge case
        if (S == null) {
            return null;
        }
        // binary search the max length
        int min = 0;
        int max = S.length() - 1;
        int mid;
        while (min < max - 1) {
            mid = (min + max) / 2;
            if (searchForLength(S, mid) != null) {
                min = mid;
            } else {
                max = mid - 1;
            }
        }
        String str = searchForLength(S, max);
        if (str != null) {
            return str;
        } else {
            return searchForLength(S, min);
        }
    }

    private String searchForLength(String str, int len) {
        // rolling hash method
        if (len == 0) {
            return "";
        } else if (len >= str.length()) {
            return null;
        }
        Map<Long, List<Integer>> map = new HashMap<>();    // hashcode -> list of all starting idx with identical hash
        long p = (1 << 31) - 1;  // prime number
        long base = 256;
        long hash = 0;
        for (int i = 0; i < len; ++i) {
            hash = (hash * base + str.charAt(i)) % p;
        }
        long multiplier = 1;
        for (int i = 1; i < len; ++i) {
            multiplier = (multiplier * base) % p;
        }
        // first substring
        List<Integer> equalHashIdx = new ArrayList<>();
        equalHashIdx.add(0);
        map.put(hash, equalHashIdx);
        // other substrings
        int from = 0;
        int to = len;
        while (to < str.length()) {
            hash = ((hash + p - multiplier * str.charAt(from++) % p) * base + str.charAt(to++)) % p;
            equalHashIdx = map.get(hash);
            if (equalHashIdx == null) {
                equalHashIdx = new ArrayList<>();
                map.put(hash, equalHashIdx);
            } else {
                for (int i0: equalHashIdx) {
                    if (str.substring(from, to).equals(str.substring(i0, i0 + len))) {
                        return str.substring(i0, i0 + len);
                    }
                }
            }
            equalHashIdx.add(from);
        }
        return null;
    }


    /**
     * https://leetcode.com/problems/longest-happy-prefix/
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
