package dsandalgo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Misc {

    public static Map<String, Integer> ret = new HashMap<String, Integer>();

    public int findLHS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i=0; i<nums.length; i++) {
            map.putIfAbsent(nums[i],0);
            map.put(nums[i],map.get(nums[i]) + 1);
        }
        if (map.size() == 1) {
            return 0;
        }
        int ret = 0;
        for (Map.Entry entry : map.entrySet()) {
            if (map.containsKey((Integer)entry.getKey() + 1)) {
                ret = Math.max(ret, (Integer)entry.getValue() + map.get((Integer)entry.getKey() + 1));
            }
            if (map.containsKey((Integer)entry.getKey() - 1)) {
                ret = Math.max(ret, (Integer)entry.getValue() + map.get((Integer)entry.getKey() - 1));
            }
        }
        return ret;
    }

    private void populateSet(Map<String, Integer> set, String[] arr) {
        for (int i=0; i<arr.length; i++) {
            set.putIfAbsent(arr[i], 0);
            set.put(arr[i], set.get(arr[i]) + 1);
        }
    }

    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int areaOfSqrA = (C-A) * (D-B);
        int areaOfSqrB = (G-E) * (H-F);

        int left = Math.max(A, E);
        int right = Math.min(G, C);
        int bottom = Math.max(F, B);
        int top = Math.min(D, H);

        //If overlap
        int overlap = 0;
        if(right > left && top > bottom) {
            overlap = (right - left) * (top - bottom);
        }

        return areaOfSqrA + areaOfSqrB - overlap;
    }

    public boolean canReorderDoubled(int[] A) {
        Arrays.sort(A);
        Map<Integer, LinkedList<Integer>> map = new HashMap<Integer, LinkedList<Integer>>();
        for (int i=0; i<A.length; i++) {
            map.putIfAbsent(A[i], new LinkedList<Integer>());
            map.get(A[i]).add(i);
        }
        Set<Integer> visited = new HashSet<Integer>();
        for (int i=0; i<A.length; i++) {
            if (!visited.contains(i)) {
                visited.add(i);
                if (A[i] < 0) {
                    if (!map.containsKey(A[i]/2)) {
                        return false;
                    } else {
                        visited.add(map.get(A[i]/2).getFirst());
                        map.get(A[i]/2).removeFirst();
                        if (map.get(A[i]/2).size() == 0) {
                            map.remove(A[i]/2);
                        }
                    }
                } else {
                    if (!map.containsKey(A[i]*2)) {
                        return false;
                    } else {
                        if (A[i] == 0) {
                            visited.add(map.get(A[i]*2).getLast());
                            map.get(A[i]*2).removeLast();
                            if (map.get(A[i]*2).size() == 0) {
                                map.remove(A[i]*2);
                            }
                        } else {
                            visited.add(map.get(A[i]*2).getFirst());
                            map.get(A[i]*2).removeFirst();
                            if (map.get(A[i]*2).size() == 0) {
                                map.remove(A[i]*2);
                            }
                        }
                    }
                }
                if (!map.containsKey(A[i])) {
                    return false;
                }
                map.get(A[i]).removeFirst();
                if (map.get(A[i]).size() == 0) {
                    map.remove(A[i]);
                }
            }
        }
        if (map.size() == 0) {
            return true;
        }
        return false;
    }

    public static int removeElement(int[] nums, int val) {
        int low = 0, high = nums.length-1;
        int counter = 0, temp = 0;
        while (low<high) {
            if (nums[low] == val) {
                counter++;
                temp = nums[high];
                nums[high] = nums[low];
                nums[low] = temp;
                high--;
            } else {
                low++;
            }
            if (high>0 && low<nums.length-1) {
                if (nums[high] == val) {
                    counter++;
                    high--;
                } else {
                    temp = nums[low];
                    nums[low] = nums[high];
                    nums[high] = temp;
                    low++;
                }
            }

        }
        return nums.length - counter;
    }

    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low)/2;
            if (nums[mid] == target) {
                return mid;
            } else {
                if (nums[mid] > target ) {
                    if (target >= nums[low] ) {
                        high = mid-1;
                    } else {
                        low = mid+1;
                    }
                } else {
                    if (target > nums[high] ) {
                        low = mid+1;
                    } else {
                        high = mid-1;
                    }
                }
            }
        }
        return -1;
    }

    public static List<Integer> findWords(int x, int y, int bound) {
        Set<Integer> result = new HashSet<Integer>();
        for (int a = 1; a < bound; a *= x) {
            for (int b = 1; a + b <= bound; b *= y) {
                result.add(a + b);
                if (y == 1) {
                    break;
                }
            }
            if (x == 1) {
                break;
            }
        }
        return new ArrayList<Integer>(result);
    }

    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (root.left != null) {
            stack.push(root.left);
            root = root.left;
        }
        while (!stack.isEmpty()) {
            TreeNode visit = stack.pop();
            if (k==1) {
                return visit.val;
            }
            k--;
            if (visit.right!=null) {
                stack.push(visit.right);
                TreeNode temp = visit.right;
                while (temp.left != null) {
                    stack.push(temp.left);
                    temp = temp.left;
                }
            }
        }
        return -1;
    }

    public static List<String> commonChars(String[] A) {
        List<String> ret = new ArrayList<String>();
        if (A == null || A.length == 0) {
            return ret;
        }
        int[] letterCounterArr = new int[26];
        for (int j=0; j<A[0].length();j++) {
            int idx = A[0].charAt(j) - 'a';
            letterCounterArr[idx] = letterCounterArr[idx] + 1;
        }
        for (int i=1; i<A.length; i++) {
            int[] temp = new int[26];
            for (int j=0; j<A[i].length();j++) {
                int idx = A[i].charAt(j) - 'a';
                temp[idx] = temp[idx] + 1;
            }
            for (int j=0; j<temp.length;j++) {
                if (temp[j] < letterCounterArr[j]) {
                    letterCounterArr[j] = temp[j];
                }
            }
        }
        for (int k=0; k<letterCounterArr.length; k++){
            if (letterCounterArr[k] > 0) {
                for (int m=0; m<letterCounterArr[k]; m++) {
                    ret.add(Character.toString((char)('a' + k)));
                }
            }
        }

        Calendar c = Calendar.getInstance();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse("2019-1-1");
            c.setTime(date);
            int day = c.get(Calendar.DAY_OF_WEEK);
            System.out.println(day);
        } catch (ParseException e) {
        }

        return ret;
    }

    public static int findDuplicate(int[] nums) {
        if (nums!=null && nums.length > 1) {
            int slow = nums[0];
            int fast = nums[nums[0]];
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[nums[fast]];
            }
            fast = 0;
            while (fast != slow) {
                fast = nums[fast];
                slow = nums[slow];
            }
            return slow;
        }
        return -1;
    }

    public static int[][] reconstructQueue(int[][] people) {
        if (people == null || people.length == 0 || people[0].length == 0)
            return new int[0][0];

        Arrays.sort(people, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if (b[0] == a[0]) return a[1] - b[1];
                return b[0] - a[0];
            }
        });

        int n = people.length;
        ArrayList<int[]> tmp = new ArrayList<int[]>();
        for (int i = 0; i < n; i++)
            tmp.add(people[i][1], new int[]{people[i][0], people[i][1]});

        int[][] res = new int[people.length][2];
        int i = 0;
        for (int[] k : tmp) {
            res[i][0] = k[0];
            res[i++][1] = k[1];
        }

        return res;
    }

    public static List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> subset = new ArrayList<>();
        int i;
        if (nums == null || nums.length == 0) {
            return subset;
        }
        Arrays.sort(nums);
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        for (i=0; i<nums.length; i++) {
            List<Integer> oneResult = new ArrayList<Integer>();
            oneResult.add(nums[i]);
            results.add(oneResult);
        }

        int maxIdx = 0;
        int max = Integer.MIN_VALUE;
        for (i=0; i<nums.length;i++) {
            int currentMax = results.get(i).size();
            int idx = -1;
            for (int j=i-1;j>=0;j--) {
                if (nums[i] % nums[j] == 0) {
                    if (1 + results.get(j).size() > currentMax) {
                        currentMax = 1 + results.get(j).size();
                        idx = j;
                    }
                }
            }
            if (idx != -1) {
                copyFromIdx(results, idx, i, nums);
            }
            if (results.get(i).size() > max) {
                max = results.get(i).size();
                maxIdx = i;
            }
        }
        return results.get(maxIdx);
    }

    public static void copyFromIdx(List<List<Integer>> results, int idx, int i, int[] nums){
        List<Integer> temp = results.get(idx);
        List<Integer> newResultAtI = new ArrayList<Integer>();
        for (int j=0; j<temp.size(); j++) {
            newResultAtI.add(temp.get(j));
        }
        newResultAtI.add(nums[i]);
        results.set(i,newResultAtI);
    }

    public static int getSum(int a, int b) {
        int carrier = a & b;
        int ret = a^b;
        while (carrier != 0) {
            int tempCarrier = carrier << 1;
            carrier = ret&tempCarrier;
            ret = ret^tempCarrier;
        }
        return ret;
    }

    public static int hammingWeight(int n) {
        int counter = 0;
        while (n!=0) {
            if ((n & 1) == 1) {
                counter++;
            }
            n = n>>1;
        }
        return counter;
    }

    public List<List<String>> printTree(TreeNode root) {
        int level = getLevel(root);
        int total = 1;
        for (int i=0; i<level; i++) {
            total = 2*total;
        }
        total--;
        List<List<String>> ret = new ArrayList<List<String>>();
        for (int i=0; i<level; i++) {
            ret.add(createDefault(total));
        }
        helper(root, ret, 0, 0, total - 1);
        return ret;
    }

    public void helper(TreeNode node, List<List<String>> ret, int level, int low, int high) {
        if (node == null) {
            return;
        }
        List curLevel = ret.get(level);
        int curIdx = (low+(high-low))/2;
        curLevel.set(curIdx, node.val+"");
        int nextLevel = level + 1;
        helper(node.left, ret, nextLevel,low, curIdx-1);
        helper(node.right, ret, nextLevel,curIdx+1, high);
    }

    public List<String> createDefault(int total){
        List<String> ret = new ArrayList<String>();
        for (int i = 0; i<total; i++) {
            ret.add("");
        }
        return ret;
    }

    public int getLevel(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null  && root.right == null) {
            return 1;
        }
        return 1 + Math.max(getLevel(root.left), getLevel(root.right));
    }

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int low, int high) {
        if (low > high) {
            return null;
        }
        int idx = findMaxIdxFromArr(nums, low, high);
        TreeNode root = new TreeNode(nums[idx]);
        root.left = helper(nums, low, idx - 1);
        root.right = helper(nums, idx + 1, high);
        return root;
    }

    public int findMaxIdxFromArr(int[] arr, int low, int high) {
        int max = Integer.MIN_VALUE;
        int j = 0;
        for(int i=low; i<=high;i++) {
            if (arr[i] > max) {
                max = arr[i];
                j = i;
            }
        }
        return j;
    }

    public TreeNode trimBST(TreeNode root, int L, int R) {
        TreeNode newRoot = getNewRoot(root, L, R);
        TreeNode node = newRoot;
        trimHelper(node, L, R);
        return newRoot;
    }

    public void trimHelper(TreeNode node, int L, int R) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            if (node.left.val < L) {
                node.left = node.left.right;
            }
            if (node.left != null && node.left.val < L) {
                node.left = null;
            } else {
                trimHelper(node.left, L, R);
            }
        }
        if (node.right != null) {
            if (node.right.val > R) {
                node.right = node.right.left;
            }
            if (node.right != null && node.right.val > R) {
                node.right = null;
            } else {
                trimHelper(node.right, L, R);
            }
        }
    }

    public TreeNode getNewRoot(TreeNode root, int L, int R) {
        if (root == null) {
            return null;
        }
        if (root.val < L) {
            return getNewRoot(root.right, L, R);
        } else {
            if (root.val > R) {
                return getNewRoot(root.left, L, R);
            } else {
                return root;
            }
        }
    }

    public static int uniqueMorseRepresentations(String[] words) {
        if (words == null || words.length == 0) {
            return 0;
        }
        Map<Character, String> morseMap = new HashMap<Character, String>();
        String[] allMorseCode = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        for (int i=0; i<allMorseCode.length; i++) {
            morseMap.put((char)('a' + i),allMorseCode[i]);
        }
        Set<String> set = new HashSet<String>();
        int counter = 0;
        for (String word : words) {
            if (word != null) {
                String morseCodeOfWord = "";
                for (int j=0; j<word.length(); j++) {
                    morseCodeOfWord = morseCodeOfWord + morseMap.get(word.charAt(j));
                }
                if (!set.contains(morseCodeOfWord)) {
                    counter++;
                    set.add(morseCodeOfWord);
                }
            }
        }
        return counter;
    }

    public static int balancedStringSplit(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int counter = 0, i = 0, tempCounterL = 0, tempCounterR = 0;
        while (i<s.length()) {
            if (s.charAt(i) == 'R') {
                tempCounterL++;
            }
            if (s.charAt(i) == 'L') {
                tempCounterR++;
            }
            if (tempCounterL == tempCounterR) {
                counter++;
                tempCounterL = 0;
                tempCounterR = 0;
            }
            i++;
        }
        return counter;
    }

    public static String defangIPaddr(String address) {
        if (address == null) {
            return null;
        }
        return address.replaceAll("\\.", "[.]");
    }

    public static int numTilings(int N) {
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 2;
        }
        long[] dp = new long[N+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        int MOD = (int)Math.pow(10, 9) + 7;
        for (int i=3; i<N+1; i++) {
            dp[i] = 2*dp[i-1] + dp[i-3];
            dp[i] = dp[i] % MOD;
        }
        return (int)dp[N];
    }

    public static int numJewelsInStones(String J, String S) {
        if (J == null || S == null) {
            return 0;
        }
        int i, counter = 0;
        Set<String> jMap = new HashSet<String>();
        for (i=0; i<J.length(); i++) {
            jMap.add(J.charAt(i)+"");
        }
        for (i=0; i<S.length(); i++) {
            if (jMap.contains(S.charAt(i)+"")) {
                counter++;
            }
        }
        return counter;
    }

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int fromRoot = helper(root);
        int fromLeft = helper(root.left);
        int fromRight = helper(root.right);
        return Math.max(fromRoot, Math.max(fromLeft, fromRight));
    }

    public int helper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left != null && node.right != null) {
            if (node.left.val == node.val && node.right.val == node.val) {
                return 2 + Math.max(helper(node.left),helper(node.right));
            } else {
                int left = 0;
                if (node.val == node.left.val) {
                    left = 1 + helper(node.left);
                }
                int right = 0;
                if (node.val == node.right.val) {
                    right = 1 + helper(node.right);
                }
                return Math.max(left, right);
            }
        }
        if (node.left != null  && node.right == null){
            if (node.val == node.left.val) {
                return 1 + helper(node.left);
            }
        }
        if (node.left == null  && node.right != null){
            if (node.val == node.right.val) {
                return 1 + helper(node.right);
            }
        }
        return 0;
    }

    public static int maxProfit(int[] prices) {
        int minPos = 0, i = 0;
        int delta = Integer.MIN_VALUE;
        for (i=0; i<prices.length; i++) {
            if (i+1 <prices.length && prices[i] < prices[i+1]) {
                minPos = i;
                break;
            }
        }
        for (i=minPos+1; i<prices.length; i++) {
            if (prices[i] > prices[minPos] && prices[i] - prices[minPos] > delta) {
                delta = prices[i] - prices[minPos];
            } else if (prices[i] < prices[minPos]){
                minPos = i;
            }
        }
        if (delta == Integer.MIN_VALUE) {
            return 0;
        }
        return delta;
    }

    public List<String> fizzBuzz(int n) {
        List<String> ret = new ArrayList<>();
        for (int i=1; i<=n; i++) {
            String str = i + "";
            if (i >= 15 && i % 15 ==0) {
                str = "FizzBuzz";
            } else {
                if (i >= 3 && i % 3 == 0) {
                    str = "Fizz";
                }
                if (i >= 5 && i % 5 == 0) {
                    str = "Buzz";
                }
            }
            ret.add(str);
        }
        return ret;
    }

    public static int findMaxDP(String[] strs, int m, int n){
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int one = 0;
            int zero = 0;
            for (char c : str.toCharArray()) {
                if (c == '1')
                    one++;
                else
                    zero++;
            }
            for (int i = m; i >= zero; i--) {
                for (int j = n; j >= one; j--) {
                    if (one <= j && zero <= i)
                        dp[i][j] = Math.max(dp[i][j], dp[i - zero][j - one] + 1);
                }
            }
        }
        return dp[m][n];
    }

    public static int helper(List<String> list, int m, int n) {
        if (m == 0 && n == 0) {
            return 0;
        }
        int max = 0;
        for (String one : list) {
            int numOf1s = getNumOf01s(one,'1');
            int numOf0s = getNumOf01s(one,'0');
            if (numOf1s <= n && numOf0s <= m) {
                int useThisOne = 1 + helper(copyList(list, one), m-numOf0s, n-numOf1s);
                if (useThisOne > max) {
                    max = useThisOne;
                }
            }
        }
        return max;
    }

    public static List<String> copyList(List<String> list, String toRemove) {
        List<String> newList = new ArrayList<String>();
        for (String org : list) {
            if (!org.endsWith(toRemove)) {
                newList.add(org);
            }
        }
        return newList;
    }

    public static int getNumOf01s(String input, char zeorOrOne) {
        int counter = 0;
        for (int i=0; i<input.length(); i++) {
            if (input.charAt(i) == zeorOrOne) {
                counter++;
            }
        }
        return counter;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }


    public static int minSumInGrid(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        if (grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int sum = 0;
        if (m<=1 || n<=1) {
            for (int i=0; i<m; i++) {
                for (int j=0; j<n; j++) {
                    sum = sum + grid[i][j];
                }
            }
            return sum;
        }
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i=1; i<m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int i=1; i<n; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }

    public static String findLCS(String[] strs){
        boolean sameAtPositionI = true;
        String ret = "";
        int cursor = 0;
        String firstStr = strs[0];
        while (sameAtPositionI) {
            boolean exitLoop = false;
            boolean allMatching = true;
            for (int i=0; i<strs.length; i++) {
                if (strs[i].length() > cursor) {
                    if (strs[i].charAt(cursor) != firstStr.charAt(cursor)) {
                        allMatching = false;
                    }
                } else {
                    exitLoop = true;
                    break;
                }
            }
            if (exitLoop) {
                break;
            }
            if (allMatching) {
                ret = ret + firstStr.charAt(cursor);
                cursor++;
            } else {
                sameAtPositionI = false;
            }
        }
        return ret;
    }

    public boolean checkPossibility(int[] nums) {
        int counter = 0;
        for (int i=1; i<nums.length; i++) {
            if (nums[i-1] > nums[i]) {
                if (i-2 >= 0 && nums[i-2] > nums[i]) {
                    return false;
                }
                counter++;
            }
            if (counter == 2) {
                break;
            }
        }
        if (counter == 2) {
            return false;
        } else {
            return true;
        }
    }

    public int maxDistance(int[][] grid) {
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<int[]>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 1) {
                    int[] toAdd = {i, j};
                    queue.offer(toAdd);
                    visited[i][j] = true;
                }
            }
        }
        int level = -1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0; i<size; i++) {
                int[] pos = queue.poll();
                for (int j=0; j<directions.length; j++) {
                    int px = pos[0] + directions[j][0];
                    int py = pos[1] + directions[j][1];
                    if (px >= 0 && px < m && py >= 0 && py < n && !visited[px][py]) {
                        visited[px][py] = true;
                        int[] toAdd = {px, py};
                        queue.offer(toAdd);
                    }
                }
            }
            level++;
        }
        return level <= 0 ? -1 : level;
    }

    public String getPermutation(int n, int k) {
        List<Integer> num = new LinkedList<Integer>();
        for (int i = 1; i <= n; i++) num.add(i);
        int[] fact = new int[n];  // factorial
        fact[0] = 1;
        for (int i = 1; i < n; i++) fact[i] = i*fact[i-1];
        k = k-1;
        StringBuilder sb = new StringBuilder();
        for (int i = n; i > 0; i--){
            int ind = k/fact[i-1];
            k = k%fact[i-1];
            sb.append(num.get(ind));
            num.remove(ind);
        }
        return sb.toString();
    }

    public int dominantIndex(int[] nums) {
        int max = Integer.MIN_VALUE;
        int idx = -1;
        for (int i=0; i<nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
                idx = i;
            }
            max = Math.max(max, nums[i]);
        }
        for (int i=0; i<nums.length; i++) {
            if (max != nums[i] && max < 2*nums[i]) {
                return -1;
            }
        }
        return idx;
    }

    public int jump(int[] nums) {
        Map<Integer, Integer> cachedResult = new HashMap<Integer, Integer>();
        return helper(0, nums, cachedResult);
    }

    public int helper(int idx, int[] nums, Map<Integer, Integer> cachedResult) {
        if (idx == nums.length - 1) {
            return 0;
        }
        int cur = nums[idx];
        int min = Integer.MAX_VALUE;
        if (cur == 0) {
            return min;
        }
        for (int i=1; i<=cur; i++) {
            if (idx + i < nums.length) {
                if (!cachedResult.containsKey(idx+i)) {
                    cachedResult.put(idx+i, helper(idx+i, nums, cachedResult));
                }
                min = Math.min(min, cachedResult.get(idx+i));
            }
        }
        if (min == Integer.MAX_VALUE) {
            return min;
        }
        return 1 + min;
    }

    public String longestDupSubstring(String S) {
        int start = 0, maxLen = 0;
        String res = "";
        while (start < S.length()) {
            int slow = start;
            int fast = start + 1;
            int counter = 0;
            while (fast < S.length()) {
                if (S.charAt(slow) == S.charAt(fast)) {
                    counter++;
                    slow++;
                } else {
                    if (counter > maxLen) {
                        res = S.substring(start,start + counter);
                        maxLen = counter;
                    }
                    counter = 0;
                    slow = start;
                }
                fast++;
            }
            if (counter > maxLen) {
                res = S.substring(start,start + counter);
                maxLen = counter;
            }
            start++;
        }
        return res;
    }

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int len = A.length;
        Map<Integer, Integer> sumCount = new HashMap<>();
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                int tempSum = A[i] + B[j];
                if (sumCount.containsKey(Integer.valueOf(tempSum))) {
                    sumCount.put(Integer.valueOf(tempSum), sumCount.get(Integer.valueOf(tempSum)) + 1);
                } else {
                    sumCount.put(Integer.valueOf(tempSum), 1);
                }
            }
        }
        int res = 0;
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                int tempSum = C[i] + D[j];
                if (sumCount.containsKey(Integer.valueOf(0 - tempSum))) {
                    res = res + sumCount.get(Integer.valueOf(0 - tempSum));
                }
            }
        }
        return res;
    }

    public int[] findErrorNums(int[] nums) {
        int numsSum = 0;
        boolean dupFound = false;
        int duplicatedNum = 0;
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<nums.length; i++) {
            numsSum = numsSum + nums[i];
            if (!dupFound) {
                if (set.contains(Integer.valueOf(nums[i]))) {
                    duplicatedNum = nums[i];
                    dupFound = true;
                } else {
                    set.add(Integer.valueOf(nums[i]));
                }
            }
        }
        int missingNum = (nums.length * (nums.length + 1)/2) - (numsSum - duplicatedNum);
        int[] ret = {duplicatedNum, missingNum};
        return ret;
    }

    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        int i = 0;
        for (i=0; i<list1.length; i++) {
            map.put(list1[i], i);
        }
        int minIdxSum = Integer.MAX_VALUE;
        List<String> results = new ArrayList<>();
        for (i=0; i<list2.length; i++) {
            if (map.containsKey(list2[i])) {
                int tempSum = map.get(list2[i]) + i;
                if (tempSum < minIdxSum) {
                    results = new ArrayList<>();
                    results.add(list2[i]);
                    minIdxSum = tempSum;
                } else {
                    if (tempSum == minIdxSum) {
                        results.add(list2[i]);
                    }
                }
            }
        }
        Object[] array = results.toArray();
        return (String[])array;
    }

    public int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++) {
            map.putIfAbsent(s.charAt(i), 0);
            map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
        }
        boolean haveSingle = false;
        int totalResult = 0;
        for (Map.Entry entry : map.entrySet()) {
            int counter = (Integer)entry.getValue();
            if (counter%2 == 0) {
                totalResult = totalResult + counter;
            } else {
                haveSingle = true;
                totalResult = totalResult + counter - 1;
            }
        }
        if (haveSingle) {
            return totalResult + 1;
        }
        return totalResult;
    }

    public int[] searchRange(int[] nums, int target) {
        int[] ret = new int[2];
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                high = mid;
            } else {
                if (nums[mid] < target) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
        }
        if (nums[low] == target) {
            ret[0] = low;
            high = nums.length - 1;
            while (low < high) {
                int mid = 1 + low + (high - low) / 2;
                if (nums[mid] > target) {
                    high = mid - 1;
                } else {
                    low = mid;
                }
            }
            ret[1] = high;
        } else {
            ret[0] = -1;
            ret[1] = -1;
        }
        return ret;
    }

    public class StartAndEnd{
        public int start;
        public int end;
        public int counter;
        public StartAndEnd(int start, int end){
            this.start = start;
            this.end = end;
            this.counter = 1;
        }
    }
    public int findShortestSubArray(int[] nums) {
        int degree = 1;
        List<Integer> highestDegreeNumbers = new ArrayList<Integer>();
        Map<Integer, StartAndEnd> map = new HashMap<Integer, StartAndEnd>();
        for (int i=0; i<nums.length; i++) {
            StartAndEnd obj = null;
            if (map.containsKey(nums[i])) {
                obj = map.get(nums[i]);
                obj.counter = obj.counter + 1;
                obj.end = i;
                if (degree < obj.counter) {
                    highestDegreeNumbers = new ArrayList<Integer>();
                    highestDegreeNumbers.add(nums[i]);
                    degree = obj.counter;
                } else {
                    if (degree == obj.counter) {
                        highestDegreeNumbers.add(nums[i]);
                    }
                }
                map.put(nums[i], obj);
            } else {
                obj = new StartAndEnd(i, i);
                map.put(nums[i], obj);
            }
        }
        if (degree == 1) {
            return 1;
        }
        int res = Integer.MAX_VALUE;
        for (Integer val : highestDegreeNumbers) {
            res = Math.min(res, map.get(val).end - map.get(val).start + 1);
        }
        return res;
    }

    public int[][] updateMatrix(int[][] matrix) {
        int[][] directions = {{0,1}, {1,0}, {-1,0}, {0,-1}};
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        LinkedList<int[]> queue = new LinkedList<int[]>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (matrix[i][j] == 1) {
                    for (int k=0; k<directions.length; k++) {
                        int newX = i+directions[k][0];
                        int newY = j+directions[k][1];
                        if (newX < m && newX>=0 && newY < n && newY>=0){
                            if (matrix[newX][newY] == 0) {
                                int[] pos = new int[2];
                                pos[0] = i;
                                pos[1] = j;
                                visited[i][j] = true;
                                queue.add(pos);
                                break;
                            }
                        }
                    }
                }
            }
        }
        int[][] res = new int[m][n];
        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            int s = queue.size();
            for (int i=0; i<s; i++) {
                int[] pos = queue.pop();
                matrix[pos[0]][pos[1]] = 0;
                res[pos[0]][pos[1]] = level;
            }
            for (int i=0; i<m; i++) {
                for (int j=0; j<n; j++) {
                    if (matrix[i][j] == 1) {
                        for (int k=0; k<directions.length; k++) {
                            int newX = i+directions[k][0];
                            int newY = j+directions[k][1];
                            if (newX < m && newX>=0 && newY < n && newY>=0){
                                if (matrix[newX][newY] == 0) {
                                    int[] pos = new int[2];
                                    pos[0] = i;
                                    pos[1] = j;
                                    queue.add(pos);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public int hammingDistance(int x, int y) {
        int z = x^y;
        int counter = 0;
        while(z!=0) {
            z = z&(z-1);
            counter++;
        }
        return  counter;
    }

    /**
     * Input: ["5","2","C","D","+"]
     * Output: 30
     * @param ops
     * @return
     */
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<Integer>();
        for (String str : ops) {
            if ("+".equals(str)) {
                int val1 = 0, val2 = 0;
                boolean haveVal1 = false;
                if (!stack.isEmpty()) {
                    val1 = stack.pop();
                    haveVal1 = true;
                }
                if (!stack.isEmpty()) {
                    val2 = stack.peek();
                }
                int sum = val1 + val2;
                if (haveVal1) {
                    stack.push(val1);
                }
                stack.push(sum);
            } else {
                if ("C".equals(str)) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else {
                    if ("D".equals(str)) {
                        if (!stack.isEmpty()) {
                            stack.push(2 * stack.peek());
                        }
                    } else {
                        stack.push(Integer.parseInt(str));
                    }
                }
            }
        }
        int res = 0;
        while (!stack.isEmpty()) {
            res = res + stack.pop();
        }
        return res;
    }

    public String[] findOcurrences(String text, String first, String second) {
        String[] allWords = text.split(" ");
        List<Integer> firstIndexes = new LinkedList<Integer>();
        List<Integer> secondIndexes = new LinkedList<Integer>();
        List<String> result = new LinkedList<String>();
        for (int i=0; i<allWords.length; i++) {
            if (allWords[i].equals(first)) {
                firstIndexes.add(i);
            }
            if (allWords[i].equals(second)) {
                secondIndexes.add(i);
            }
        }
        for (int i=0; i<firstIndexes.size(); i++) {
            int secondIdx = Integer.valueOf(firstIndexes.get(i) + 1);
            if (secondIndexes.contains(secondIdx)) {
                if (secondIdx + 1 < allWords.length) {
                    result.add(allWords[secondIdx + 1]);
                }
            }
        }
        String[] strs = new String[result.size()];
        result.toArray(strs);
        return strs;
    }

    public int[] shortestToChar(String s, char c) {
        int n = s.length();
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == c) {
                continue;
            }
            dist[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < n-1; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                continue;
            } else {
                dist[i + 1] = Math.min(dist[i+1], dist[i] + 1);
            }
        }
        for (int i = n-1; i > 0; i--) {
            dist[i-1] = Math.min(dist[i-1], dist[i] + 1);
        }
        return dist;
    }

    public int smallestRangeI(int[] A, int K) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i=0; i<A.length; i++) {
            min = Math.min(min, A[i]);
            max = Math.max(max, A[i]);
        }
        return Math.max(0, (max - min - 2*K));
    }
}
