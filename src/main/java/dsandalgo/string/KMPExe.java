package dsandalgo.string;

public class KMPExe {

    /**
     * Slow method of pattern matching
     */
    public boolean hasSubstring(char[] text, char[] pattern){
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < text.length && j < pattern.length) {
            if (text[i] == pattern[j]) {
                i++;
                j++;
            } else {
                j = 0;
                k++;
                i = k;
            }
        }
        if (j == pattern.length) {
            return true;
        }
        return false;
    }

    /**
     * KMP algorithm of pattern matching.
     */
//    public int KMP(char[] text, char[] pattern) {
//        int lps[] = buildLPS(pattern);
//        int i = 0;
//        int j = 0;
//        while (i < text.length && j < pattern.length) {
//            if (text[i] == pattern[j]) {
//                i++;
//                j++;
//            } else {
//                if (j != 0) {
//                    j = lps[j - 1];
//                } else {
//                    i++;
//                }
//            }
//            if (j == pattern.length) {
//                return i;
//            }
//        }
//        return -1;
//    }

    /**
     * https://leetcode.com/problems/implement-strstr/
     * Implement strStr().
     *
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     *
     * Example 1:
     *
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     * Example 2:
     *
     * Input: haystack = "aaaaa", needle = "bba"
     * Output: -1
     * Clarification:
     *
     * What should we return when needle is an empty string? This is a great question to ask during an interview.
     *
     * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
     */
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.equalsIgnoreCase("")) {
            return 0;
        }
        char[] text = haystack.toCharArray();
        char[] pattern = needle.toCharArray();

        int lps[] = buildLPS(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length && j < pattern.length) {
            if (text[i] == pattern[j]) {
                i++;
                j++;
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
            if (j == pattern.length) {
                return i - j;
            }
        }
        return -1;
    }

    /**
     * Compute temporary array to maintain size of suffix which is same as prefix
     * Time/space complexity is O(size of pattern)
     */
    private int[] buildLPS(char[] pattern) {
        int[] lps = new int[pattern.length];
        int index = 0;
        for (int i = 1; i < pattern.length; ) {
            if (pattern[i] == pattern[index]) {
                lps[i] = index + 1;
                index++;
                i++;
            } else {
                if (index != 0) {
                    index = lps[index - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    public int strStr_bruteForce(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.equalsIgnoreCase("")) {
            return 0;
        }
        for (int i=0; i<haystack.length(); i++) {
            for (int j=0; j<needle.length(); j++) {
                if (i+j > haystack.length() - 1) {
                    break;
                }
                if (needle.charAt(j) != haystack.charAt(i+j)) {
                    break;
                }
                if (j == needle.length() - 1) {
                    return i;
                }
            }
        }
        return -1;
    }
}
