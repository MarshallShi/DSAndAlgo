package dsandalgo.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashMapExe {

    public static void main(String[] args) {
        HashMapExe exe = new HashMapExe();

        int[] groupSizes = {2,1,3,3,3,2};
        String[] input = {"root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"};
        exe.findDuplicate(input);
    }

    /**
     * https://leetcode.com/problems/flip-columns-for-maximum-number-of-equal-rows/
     * Given a matrix consisting of 0s and 1s, we may choose any number of columns in the matrix
     * and flip every cell in that column.  Flipping a cell changes the value of that cell from 0 to 1 or from 1 to 0.
     *
     * Return the maximum number of rows that have all values equal after some number of flips.
     * Example 1:
     *
     * Input: [[0,1],[1,1]]
     * Output: 1
     * Explanation: After flipping no values, 1 row has all values equal.
     *
     * Example 2:
     *
     * Input: [[0,1],[1,0]]
     * Output: 2
     * Explanation: After flipping values in the first column, both rows have equal values.
     *
     * Example 3:
     *
     * Input: [[0,0,0],[0,0,1],[1,1,0]]
     * Output: 2
     * Explanation: After flipping values in the first two columns, the last two rows have equal values.
     *
     *
     * Note:
     *
     * 1 <= matrix.length <= 300
     * 1 <= matrix[i].length <= 300
     * All matrix[i].length's are equal
     * matrix[i][j] is 0 or 1
     * @param matrix
     * @return
     */
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        Map<String, Integer> map = new HashMap<>();
        for (int[] row : matrix) {
            //each row, if the pattern is the same, either same or opposite, will potentially be the same after flip via column.
            //find out the max number of same pattern occurrence
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (int r : row) {
                sb1.append(r);
                sb2.append(1 - r);
            }
            String str1 = sb1.toString();
            String str2 = sb2.toString();
            map.put(str1, map.getOrDefault(str1,0) + 1);
            map.put(str2, map.getOrDefault(str2,0) + 1);
        }
        int res = 0;
        for(int val : map.values()) {
            res = Math.max(res, val);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/find-duplicate-file-in-system/
     *
     * https://leetcode.com/problems/find-duplicate-file-in-system/discuss/104120/Follow-up-questions-discussion
     *
     * @param paths
     * @return
     */
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : paths) {
            String[] fileInfoStr = str.split(" ");
            if (fileInfoStr != null && fileInfoStr.length > 0) {
                String folder = fileInfoStr[0];
                for (int i=1; i<fileInfoStr.length; i++) {
                    String oneFile = fileInfoStr[i];
                    String fName = oneFile.substring(0, oneFile.indexOf("("));
                    String fContent = oneFile.substring(oneFile.indexOf("(")+1, oneFile.indexOf(")"));
                    if (map.containsKey(fContent)) {
                        map.get(fContent).add(folder + "/" + fName);
                    } else {
                        List<String> list = new ArrayList<String>();
                        list.add(folder + "/" + fName);
                        map.put(fContent, list);
                    }
                }
            }
        }
        List<List<String>> ret = new ArrayList<List<String>>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                ret.add(entry.getValue());
            }
        }
        return ret;
    }


    /**
     * https://leetcode.com/problems/isomorphic-strings/
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        if (s == null && t == null) {
            return true;
        }
        if (s.length() == 0 && t.length() == 0) {
            return true;
        }
        Map<Character,Character> map = new HashMap<Character,Character>();
        Map<Character,Character> reverseMap = new HashMap<Character,Character>();
        for (int i=0; i<s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                if (t.charAt(i) != map.get(s.charAt(i))) {
                    return false;
                }
            } else {
                map.put(s.charAt(i), t.charAt(i));
            }
            if (reverseMap.containsKey(t.charAt(i))) {
                if (s.charAt(i) != reverseMap.get(t.charAt(i))) {
                    return false;
                }
            } else {
                reverseMap.put(t.charAt(i), s.charAt(i));
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/bulls-and-cows/
     *
     * You are playing the following Bulls and Cows game with your friend: You write down a number and ask
     * your friend to guess what the number is. Each time your friend makes a guess, you provide a hint that indicates
     * how many digits in said guess match your secret number exactly in both digit and position (called "bulls") and
     * how many digits match the secret number but locate in the wrong position (called "cows"). Your friend will use
     * successive guesses and hints to eventually derive the secret number.
     *
     * Write a function to return a hint according to the secret number and friend's guess, use A to indicate the bulls
     * and B to indicate the cows.
     *
     * Please note that both secret number and friend's guess may contain duplicate digits.
     *
     * Example 1:
     *
     * Input: secret = "1807", guess = "7810"
     *
     * Output: "1A3B"
     *
     * Explanation: 1 bull and 3 cows. The bull is 8, the cows are 0, 1 and 7.
     * Example 2:
     *
     * Input: secret = "1123", guess = "0111"
     *
     * Output: "1A1B"
     *
     * Explanation: The 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow.
     *
     * @param secret
     * @param guess
     * @return
     */
    public String getHint(String secret, String guess) {
        int[] countMatching = new int[10];
        int[] countSAll = new int[10];
        int[] countGAll = new int[10];
        int countBull = 0, countCow = 0;
        for (int i=0; i<secret.length(); i++) {
            countSAll[secret.charAt(i) - '0']++;
            countGAll[guess.charAt(i) - '0']++;
            if (secret.charAt(i) == guess.charAt(i)) {
                countMatching[guess.charAt(i) - '0']++;
                countBull++;
            }
        }
        for (int i=0; i<10; i++) {
            if (countGAll[i] > 0 && countSAll[i] > 0) {
                if (countMatching[i] > 0) {
                    countCow = countCow + Math.max(0, Math.min(countGAll[i], countSAll[i]) - countMatching[i]);
                } else {
                    countCow = countCow + Math.min(countGAll[i], countSAll[i]);
                }
            }
        }
        return countBull + "A" + countCow + "B";
    }

    /**
     * https://leetcode.com/problems/group-the-people-given-the-group-size-they-belong-to/
     *
     * @param groupSizes
     * @return
     */
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        Map<Integer, List<Integer>> map = new HashMap<Integer,List<Integer>>();
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i=0; i<groupSizes.length; i++) {
            if (map.containsKey(groupSizes[i])) {
                map.get(groupSizes[i]).add(i);
            } else {
                map.put(groupSizes[i], new ArrayList<Integer>());
                map.get(groupSizes[i]).add(i);
            }
            if (map.get(groupSizes[i]).size() == groupSizes[i]) {
                ret.add(new ArrayList<Integer>(map.get(groupSizes[i])));
                map.put(groupSizes[i], new ArrayList<Integer>());
            }
        }
        return ret;
    }
}
