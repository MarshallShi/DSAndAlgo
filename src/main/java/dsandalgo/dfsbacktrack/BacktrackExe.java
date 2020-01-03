package dsandalgo.dfsbacktrack;

import java.util.HashSet;
import java.util.Set;

public class BacktrackExe {

    public static void main(String[] args) {
        BacktrackExe backtrack = new BacktrackExe();

        int[][] nums = {
                {9,9,4},{6,6,8},{2,1,1}
        };
        System.out.println(backtrack.numTilePossibilities("AAB"));

    }

    /**
     * https://leetcode.com/problems/letter-tile-possibilities/
     *
     * You have a set of tiles, where each tile has one letter tiles[i] printed on it.
     * Return the number of possible non-empty sequences of letters you can make.
     *
     * Example 1:
     *
     * Input: "AAB"
     * Output: 8
     * Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
     *
     * Example 2:
     *
     * Input: "AAABBC"
     * Output: 188
     *
     * @param tiles
     * @return
     */
    public int numTilePossibilities(String tiles) {
        int n = tiles.length();
        Set<String> set = new HashSet();
        boolean[] visited = new boolean[n];
        backtrackHelper(new StringBuilder(), tiles, visited, set);
        return set.size();
    }
    private void backtrackHelper(StringBuilder sb, String tiles, boolean[] visited, Set set){
        if (sb.length()>0) {
            set.add(sb.toString());
        }
        if (sb.length()>=tiles.length()) {
            return;
        }
        for (int i=0; i<tiles.length(); i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            int len = sb.length();
            backtrackHelper(sb.append(tiles.charAt(i)), tiles, visited, set);
            sb.setLength(len);
            visited[i] = false;
        }
    }
}
