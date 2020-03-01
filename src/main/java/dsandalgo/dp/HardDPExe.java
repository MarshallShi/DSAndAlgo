package dsandalgo.dp;

import java.util.Arrays;

public class HardDPExe {

    public static void main(String[] args) {
        HardDPExe exe = new HardDPExe();
        System.out.println(exe.palindromePartition("abc", 2));
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
