package dsandalgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Coding3 {

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        Coding3 code3 = new Coding3();
        int[][] edges = {{1,4},{3,4},{1,3},{1,2},{4,5}};

        int[][] grid = {
                {0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}};

        int[][] grid1 = {
                {0,0,1,0,0,0,0,1,0,0,0,0,0}};

        int[][] grid2 = {
                {1,1,0,0,0},
                {1,1,0,0,0},
                {0,0,0,1,1},
                {0,0,0,1,1}};
        int[] a = {4,3,2,6};

        //System.out.println(code3.ladderLength("hit", "cog", code3.buildWordList()));
        String[] arr = {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"};
        String[] queries = {"FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"};
        String pattern = "FoBa";
        List<String> wordDict = Arrays.asList(arr);
        //System.out.println(code3.wordBreak("aaaaaaaaaa",wordDict));
        //"121932631112635269"

        code3.camelMatch(queries, pattern);
    }

    /**
     * Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBa"
     * Output: [true,false,true,false,false]
     * Explanation:
     * "FooBar" can be generated like this "Fo" + "o" + "Ba" + "r".
     * "FootBall" can be generated like this "Fo" + "ot" + "Ba" + "ll".
     * @param queries
     * @param pattern
     * @return
     */
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> ret = new ArrayList<Boolean>();
        for (int i=0; i<queries.length; i++) {
            ret.add(matchHelper(queries[i], pattern));
        }
        return ret;
    }

    private boolean matchHelper(String query, String pattern){
        char[] patternArr = pattern.toCharArray();
        char[] queryArr = query.toCharArray();
        int j = 0;
        for (int i = 0; i < queryArr.length; i++) {
            if (j < patternArr.length && queryArr[i] == patternArr[j]) {
                j++;
            } else {
                if (Character.isUpperCase(queryArr[i])) {
                    return false;
                }
            }
        }
        return j == patternArr.length;
    }

    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i=0; i<nums.length-1; i++) {
            res[i+1] = res[i]*nums[i];
        }
        int right = 1;
        for (int i=nums.length-1; i>=0; i--) {
            res[i] = right * res[i];
            right = right * nums[i];
        }
        return res;
    }


    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> ret = new ArrayList<String>();
        Map<String, LinkedList<String>> cache = new HashMap<String, LinkedList<String>>();
        return wordBreakDFS(s, wordDict, cache);
    }

    public List<String> wordBreakDFS(String s, List<String> wordDict, Map<String, LinkedList<String>> cache){
        if (cache.containsKey(s)) {
            return cache.get(s);
        }
        LinkedList<String> res = new LinkedList<String>();
        if (s.length() == 0) {
            res.add("");
            return res;
        }
        for (String word : wordDict) {
            if (s.startsWith(word)) {
                List<String> sublist = wordBreakDFS(s.substring(word.length()), wordDict, cache);
                for (String sub : sublist) {
                    res.add(word + (sub.isEmpty() ? "" : " ") + sub);
                }
            }
        }
        cache.put(s, res);
        return res;
    }

    public String multiply(String num1, String num2) {
        List<List<Integer>> calc = new ArrayList<List<Integer>>();
        int startIdx = 0;
        for (int i = num2.length()-1; i>=0; i--) {
            int counter = 0;
            int carrier = 0;
            for (int j=num1.length() - 1; j>=0; j--) {
                if (calc.size() == startIdx + counter) {
                    calc.add(new ArrayList());
                }
                int val = (num2.charAt(i) - '0') * (num1.charAt(j) - '0');
                calc.get(startIdx + counter).add((val + carrier)%10);
                carrier = (val + carrier) / 10;
                counter++;
            }
            if (carrier != 0) {
                if (calc.size() == startIdx + counter) {
                    calc.add(new ArrayList());
                }
                calc.get(startIdx + counter).add(carrier);
            }
            startIdx++;
        }
        StringBuilder sb = new StringBuilder();
        int rtCarrier = 0;
        for (int i=0; i<calc.size(); i++) {
            int sumForCurIdx = rtCarrier;
            for (int j=0; j<calc.get(i).size(); j++) {
                sumForCurIdx = sumForCurIdx + calc.get(i).get(j);
            }
            sb.append((sumForCurIdx%10) + "");
            rtCarrier = sumForCurIdx/10;
        }
        if (rtCarrier != 0) {
            sb.append(rtCarrier);
        }
        while (sb.length() > 0 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }
        if (sb.length() == 0) {
            return "0";
        }
        return sb.reverse().toString();
    }

    public List<String> buildWordList(){
        List<String> wordList = new ArrayList<String>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        return wordList;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)){
            return 0;
        }
        Set<String> wordDict = new HashSet<String>(wordList);
        Set<String> reached = new HashSet<String>();
        reached.add(beginWord);
        wordDict.add(endWord);
        int distance = 1;
        while (!reached.contains(endWord)) {
            Set<String> toAdd = new HashSet<String>();
            for (String each : reached) {
                for (int i = 0; i < each.length(); i++) {
                    char[] chars = each.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        chars[i] = ch;
                        String word = new String(chars);
                        if (wordDict.contains(word)) {
                            toAdd.add(word);
                            wordDict.remove(word);
                        }
                    }
                }
            }
            distance++;
            if (toAdd.size() == 0) return 0;
            reached = toAdd;
        }
        return distance;
    }

    public int peakIndexInMountainArray(int[] A) {
        int low = 0, high = A.length - 1;
        while (low<high) {
            int mid = low + (high - low)/2;
            if (A[mid] < A[mid+1]) {
                low = mid + 1;
            } else {
                if (A[mid] > A[mid + 1]) {
                    high = mid;
                }
            }
        }
        return low;
    }

    public int repeatedNTimes(int[] A) {
        Set<Integer> map = new HashSet<Integer>();
        for (int i=0; i<A.length; i++) {
            if (map.contains(A[i])) {
                return A[i];
            } else {
                map.add(A[i]);
            }
        }
        return 0;
    }

    private TreeNode createInput(){
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(4);
        node1.left = node2;
        TreeNode node3 = new TreeNode(8);
        node1.right = node3;

        TreeNode node4 = new TreeNode(11);
        node2.left = node4;

        TreeNode node5 = new TreeNode(13);
        TreeNode node6 = new TreeNode(4);
        node3.left = node5;
        node3.right = node6;

        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(2);
        node4.left = node7;
        node4.right = node8;

        TreeNode node9 = new TreeNode(5);
        TreeNode node10 = new TreeNode(1);
        node6.left = node10;
        node6.left = node9;


        return node1;
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> pathSumPathList = new ArrayList<List<Integer>>();
        pathSumDFS(pathSumPathList, new ArrayList<Integer>(), root, sum);
        return pathSumPathList;
    }

    public void pathSumDFS(List<List<Integer>> pathSumPathList, List<Integer> temp, TreeNode node, int sum ){
        if (sum == node.val && node.left == null && node.right == null) {
            temp.add(node.val);
            pathSumPathList.add(new ArrayList<>(temp));
            temp.remove(temp.size()-1);
            return;
        }
        temp.add(node.val);
        if (node.left != null) {
            pathSumDFS(pathSumPathList, temp, node.left, sum - node.val);
        }
        if (node.right != null) {
            pathSumDFS(pathSumPathList, temp, node.right, sum - node.val);
        }
        temp.remove(temp.size()-1);
    }

    boolean[][] visited;
    int[][] fourDirection = {{0,1},{1,0},{-1,0},{0,-1}};
    class Position{
        int x;
        int y;
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        visited = new boolean[grid.length][grid[0].length];
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[i].length; j++) {
                if (grid[i][j] == 1 && visited[i][j] == false) {
                    res = Math.max(res, maxAreaFromHere(i, j, grid));
                }
            }
        }
        return res;
    }

    public int maxAreaFromHere(int x, int y, int[][] grid) {
        Position pos = new Position(x, y);
        Stack<Position> stack = new Stack<Position>();
        stack.push(pos);
        visited[x][y] = true;
        int counter = 0;
        while (!stack.isEmpty()) {
            Position cur = stack.pop();
            counter++;
            for (int i=0; i<fourDirection.length; i++) {
                int newx = cur.x + fourDirection[i][0];
                int newy = cur.y + fourDirection[i][1];
                if (newx>=0 && newx<grid.length && newy>=0 && newy<grid[0].length && grid[newx][newy] == 1 && visited[newx][newy] == false) {
                    stack.push(new Position(newx, newy));
                    visited[newx][newy] = true;
                }
            }
        }
        return counter;
    }

    /**
     * Example:
     *
     * A = [4, 3, 2, 6]
     *
     * F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
     * F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
     * F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
     * F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
     *
     * So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
     * @param A
     * @return
     */
    public int maxRotateFunction(int[] A) {
        int max = Integer.MIN_VALUE;
        for (int i=0; i<A.length; i++) {
            max = Math.max(calcFunction(A, i), max);
        }
        return max;
    }

    public int calcFunction(int[] A, int i) {
        int sum = 0;
        for (int j=0; j<A.length; j++) {
            if (i == 0) {
                sum = sum + j*A[j];
            } else {
                int idx = A.length - i + j;
                if (idx > A.length - 1) {
                    idx = j - i;
                }
                sum = sum + j*A[idx];
            }

        }
        return sum;
    }

    public boolean isPerfectSquare(int num) {
        if (num == 1) {
            return true;
        }
        long low = 1, high = num/2;
        while (low < high) {
            long mid = low + (high - low)/2;
            if (mid * mid == num) {
                return true;
            } else {
                if (mid * mid < num) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        if (low == high) {
            if (low * high == num) {
                return true;
            }
        }
        return false;
    }

    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        for (int i=A.length-1; i>1; i--) {
            if (A[i-2] + A[i-1] > A[i]) {
                return A[i] + A[i-1] + A[i-2];
            }
        }
        return 0;
    }

    public int[] findRedundantConnection(int[][] edges) {
        Map<Integer, Integer> parent = new HashMap<Integer, Integer>();
        int[] ret = new int[2];
        for (int i=0; i<edges.length; i++) {
            if (!parent.containsKey(edges[i][0])) {
                parent.put(edges[i][0], edges[i][0]);
            }
            if (!parent.containsKey(edges[i][1])) {
                parent.put(edges[i][1], edges[i][1]);
            }
            if (!connect(parent, edges[i][0], edges[i][1])) {
                ret = edges[i];
                break;
            }
        }
        return ret;
    }

    private boolean connect(Map<Integer, Integer> parent, Integer node1, Integer node2){
        Integer root1 = findRoot(parent, node1);
        Integer root2 = findRoot(parent, node2);
        if (root1 == root2) {
            return false;
        } else {
            parent.put(root2, root1);
        }
        return true;
    }

    private Integer findRoot(Map<Integer, Integer> parent, Integer node) {
        while (parent.get(node) != node) {
            node = parent.get(node);
        }
        return node;
    }
}
