package dsandalgo.dp;

public class HardDPExe {

    public static void main(String[] args) {
        HardDPExe exe = new HardDPExe();
        System.out.println(exe.minDistance("intention", "execution"));
    }


    /**
     * https://leetcode.com/problems/edit-distance/
     *
     * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
     *
     * You have the following 3 operations permitted on a word:
     *
     * Insert a character
     * Delete a character
     * Replace a character
     *
     * Example 1:
     *
     * Input: word1 = "horse", word2 = "ros"
     * Output: 3
     * Explanation:
     * horse -> rorse (replace 'h' with 'r')
     * rorse -> rose (remove 'r')
     * rose -> ros (remove 'e')
     * Example 2:
     *
     * Input: word1 = "intention", word2 = "execution"
     * Output: 5
     * Explanation:
     * intention -> inention (remove 't')
     * inention -> enention (replace 'i' with 'e')
     * enention -> exention (replace 'n' with 'x')
     * exention -> exection (replace 'n' with 'c')
     * exection -> execution (insert 'u')
     *
     */

    /*
        Let following be the function definition :-

        f(i, j) := minimum cost (or steps) required to convert first i characters of word1 to first j characters of word2

        Case 1: word1[i] == word2[j], i.e. the ith the jth character matches.
        f(i, j) = f(i - 1, j - 1)

        Case 2: word1[i] != word2[j], then we must either insert, delete or replace, whichever is cheaper
        f(i, j) = 1 + min { f(i, j - 1), f(i - 1, j), f(i - 1, j - 1) }
        f(i, j - 1) represents insert operation
        f(i - 1, j) represents delete operation
        f(i - 1, j - 1) represents replace operation
    */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] cost = new int[m + 1][n + 1];
        //Base cases, all of them should be just the number of characters.
        for (int i = 0; i <= m; i++) {
            cost[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            cost[0][i] = i;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    //Handle case 1.
                    cost[i + 1][j + 1] = cost[i][j];
                } else {
                    //Handle case 2.
                    cost[i + 1][j + 1] = 1 + Math.min(cost[i][j], Math.min(cost[i][j + 1], cost[i + 1][j]));
                }
            }
        }
        return cost[m][n];
    }
}
