package dsandalgo.hashmap;

public class RollingHanshExe {

    /**
     * https://leetcode.com/problems/longest-chunked-palindrome-decomposition/
     * @param text
     * @return
     */
    public int longestDecomposition(String text) {
        int count = 0;
        int i = 0, j = text.length() - 1;
        long h1 = 0, h2 = 0;
        long base1 = 26, base2 = 1;

        while (i <= j) {
            char c1 = text.charAt(i), c2 = text.charAt(j);
            h1 = h1 * base1 + (c1 - 'a' + 1);
            h2 = h2 + (c2 - 'a' + 1) * base2;
            base2 *= base1;
            if (h1 == h2) {
                if (i == j) count += 1;
                else count += 2;
                h1 = 0;
                h2 = 0;
                base2 = 1;
            }
            i++;
            j--;
        }
        if (h1 != 0) count++;
        return count;
    }
}
