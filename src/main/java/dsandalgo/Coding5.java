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
import java.util.Set;
import java.util.Stack;

public class Coding5 {

    public static Map<String, Integer> ret = new HashMap<String, Integer>();

    public static void main(String args[]) {

        Coding5 ex = new Coding5();
        int[] nums1 = {1,2,3,0,0,0};
        int[] nums2 = {2,5,6};
        TreeNode root = ex.creaeAOneTree();


        int[] costs = {2,3,5};

        char[][] matrix = {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}};

        int[][] maxt = {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}};
        TreeNode root1 = ex.createANode(3);

        ListNode testNode = ex.createTestNode();

        int[] tempratures = {1,1,4,2,1,3};
        int[][] people= {{7,0}, {4,4}, {7,1}, {5,0}, {6,1}, {5,2}};
        int[] numsx = {0,4,4,0,4,4,4,0,2};

        char[][] board = {
                {'X','X','X','X'},
                {'X','O','O','X'},
                {'X','X','O','X'},
                {'X','O','X','X'}};
        //ex.solve(board);

        int[][] queries = {
                {1,0},
                {-3,1},{-4,0},{2,3}};
        int[] A = {1,3,1,3,2,3,0,3,0,1,2,2,2,-1,2,1,0,0,2,2,3,-3,1,2,2,0,1,3,1};
        //System.out.println(ex.findLHS(A));
        String[] queries1 = {"bba","abaaaaaa","aaaaaa","bbabbabaab","aba","aa","baab","bbbbbb","aab","bbabbaabb"};
        String[] words = {"aaabbb","aab","babbab","babbbb","b","bbbbbbbbab","a","bbbbbbbbbb","baaabbaab","aa"};
        System.out.println(ex.isStrobogrammatic("23"));
    }

    public boolean confusingNumber(int N) {
        String orgin = String.valueOf(N);
        StringBuilder sb = new StringBuilder();
        while (N != 0) {
            int num = N % 10;
            if (num == 2 || num == 3 || num == 4 || num == 5 || num == 7) {
                return false;
            }
            if (num == 6) {
                sb.append(9+"");
            } else {
                if (num == 9) {
                    sb.append(6+"");
                } else {
                    sb.append(num+"");
                }
            }
            N = N / 10;
        }
        boolean equ = sb.toString().equalsIgnoreCase(orgin);
        return !equ;
    }

    public boolean isStrobogrammatic(String numstr) {
        StringBuilder sb = new StringBuilder();
        for (int i=numstr.length() - 1; i>=0; i--) {
            char ch = numstr.charAt(i);
            if(ch != '0' && ch != '1' && ch != '6' && ch != '8' && ch != '9'){
                return false;
            }
            if(ch == '6' || ch == '9'){
                sb.append(ch == '6' ? '9' : '6');
            } else {
                sb.append(ch);
            }
        }
        if (sb.toString().equals(numstr)) {
            return true;
        }
        return false;
    }


    /**
     * Example 2:
     *
     * Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
     * Output: [1,2]
     * Explanation: On the first query only f("bbb") < f("aaaa").
     * On the second query both f("aaa") and f("aaaa") are both > f("cc").
     * https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/
     * @param queries
     * @param words
     * @return
     */
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] queriesFreq = new int[queries.length];
        for (int i=0; i<queriesFreq.length; i++) {
            queriesFreq[i] = findFreq(queries[i]);
        }
        int[] wordsFreq = new int[words.length];
        for (int i=0; i<wordsFreq.length; i++) {
            wordsFreq[i] = findFreq(words[i]);
        }
        Arrays.sort(wordsFreq);
        int[] ans = new int[queriesFreq.length];
        for (int i=0; i<ans.length; i++) {
            ans[i] = findPos(queriesFreq[i], wordsFreq);
        }
        return ans;
    }

    private int findPos(int val, int[] wordsFreq) {
        int low = 0, high = wordsFreq.length - 1;
        if (val < wordsFreq[low]) {
            return wordsFreq.length;
        }
        if (val > wordsFreq[high]) {
            return 0;
        }
        for (int i=0; i<wordsFreq.length; i++) {
            if (wordsFreq[i] > val) {
                return wordsFreq.length - i;
            }
        }
        return 0;
    }

    private int findFreq(String str) {
        int max = 0;
        int[] ret = new int[26];
        char[] arr = str.toCharArray();
        for (int i=0; i<arr.length; i++) {
            ret[arr[i]-'a']++;
        }
        for (int i=0; i<26; i++) {
            if (ret[i] != 0) {
                max = ret[i];
                break;
            }
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/greatest-common-divisor-of-strings/
     * @param str1
     * @param str2
     * @return
     */
    public String gcdOfStrings(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int len = Math.min(str1.length(), str2.length());
        for (int i=len; i>0; i--) {
            if (len1 % i == 0 && len2 % i == 0) {
                String cd1 = str1.substring(0, i);
                String cd2 = str2.substring(0, i);
                if (cd1.equals(cd2)) {
                    return cd1;
                }
            }
        }

        return "";
    }

    public String shortestCompletingWord(String licensePlate, String[] words) {
        int[] licenseAlpha = new int[26];
        char[] lichar = licensePlate.toCharArray();
        for (int i=0; i<lichar.length; i++) {
            if (Character.isLetter(lichar[i])) {
                licenseAlpha[Character.toLowerCase(lichar[i]) - 'a']++;
            }
        }
        int lenOfWord = Integer.MAX_VALUE;
        String ret = null;
        for (String word : words) {
            int[] wordAr = new int[26];
            char[] chArr = word.toCharArray();
            for (int i=0; i<chArr.length; i++) {
                wordAr[chArr[i] - 'a']++;
            }
            boolean isWordContainPlate = true;
            for (int i=0; i<26; i++) {
                if (licenseAlpha[i] > wordAr[i]) {
                    isWordContainPlate = false;
                    break;
                }
            }
            if (isWordContainPlate && word.length() < lenOfWord) {
                ret = word;
                lenOfWord = word.length();
            }
        }
        return ret;
    }

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

    public String[] uncommonFromSentences(String A, String B) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String[] arrA = A.split(" ");
        String[] arrB = B.split(" ");
        populateSet(map, arrA);
        populateSet(map, arrB);
        List<String> resList = new LinkedList<String>();
        for (Map.Entry entry : map.entrySet()) {
            if ((Integer)entry.getValue() > 1) {
                continue;
            } else {
                resList.add((String)entry.getKey());
            }
        }
        String[] res = new String[resList.size()];
        for (int i=0; i<resList.size(); i++) {
            res[i] = resList.get(i);
        }
        return res;
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

    public int findPairs(int[] nums, int k) {
        Map<Integer, LinkedList<Integer>> map = new HashMap<Integer, LinkedList<Integer>>();
        Arrays.sort(nums);
        Set<String> set = new HashSet<String>();
        for (int i=0; i<nums.length; i++) {
            map.putIfAbsent(nums[i], new LinkedList<Integer>());
            LinkedList<Integer> pos = map.get(nums[i]);
            pos.add(i);
            map.put(nums[i], pos);
        }
        int res = 0;
        for (int i=0; i<nums.length; i++) {
            if (k == 0) {
                if (map.get(nums[i]).size() >= 2){
                    if (!set.contains(nums[i]+"#"+nums[i])) {

                        res++;
                        set.add(nums[i]+"#"+nums[i]);
                    }
                }
            } else {
                if (map.containsKey(nums[i] + k)) {
                    if (!set.contains(nums[i]+"#"+(nums[i]+k))) {
                        res++;
                        set.add(nums[i]+"#"+(nums[i]+k));
                    }
                }
            }
        }
        return res;
    }

    public int maxWidthRamp(int[] A) {
        Stack<Integer> s = new Stack<Integer>();
        int res = 0, n = A.length;
        //Store the decreasing number's idx
        for (int i = 0; i < n; i++){
            if (s.empty() || A[s.peek()] > A[i]){
                s.add(i);
            }
        }
        //From end to start, check the diff between lower and higher, get the max.
        for (int i = n - 1; i > res; i--) {
            while (!s.empty() && A[s.peek()] <= A[i]) {
                res = Math.max(res, i - s.pop());
            }
        }
        return res;
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

    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        for (int i=0; i<m;i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] == 'O' && (i==0 || i== m-1 || j==0 || j==n-1)) {
                    solveHelper(board, i, j);
                }
            }

        }
    }

    private void solveHelper(char[][] board, int i, int j) {
        //check four directions recursively, and then set to '-'
        board[i][j] = '-';

    }

    public String longestWord(String[] words) {
        if (words == null || words.length == 0) {
            return null;
        }
        TrieNode root = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            insert(root, words[i]);
        }
        String ans = "";
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            boolean found = true;
            TrieNode node = root;
            for (int j = 0; j < word.length(); j++) {
                char cur = word.charAt(j);
                if (node.links[cur - 'a'] == null) {
                    found = false;
                    break;
                }
                node = node.links[cur - 'a'];
                if (!node.isEnd) {
                    found = false;
                    break;
                }
            }
            if (found) {
                if (ans.length() < word.length() || ans.length() == word.length() && ans.compareTo(word) > 0) {
                    ans = word;
                }
            }
        }
        return ans;
    }
    class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        TrieNode() {
            links = new TrieNode[26];
            isEnd = false;
        }
    }
    private void insert(TrieNode root, String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        TrieNode node = root;
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (node.links[cur - 'a'] == null) {
                node.links[cur - 'a'] = new TrieNode();
            }
            node = node.links[cur - 'a'];
        }
        node.isEnd = true;
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

    public ListNode createTestNode(){
        ListNode a = new ListNode(11);

        ListNode b = new ListNode(12);
        a.next = b;

        ListNode c = new ListNode(13);
        b.next = c;
//
//        ListNode d = new ListNode(14);
//        c.next = d;
//
//        ListNode e = new ListNode(15);
//        d.next = e;
        return a;
    }

    public static boolean wordPattern(String pattern, String str) {
        Map<Character, String> patternMap = new HashMap<Character, String>();
        HashMap<String, Character> hm2 = new HashMap<String, Character>();
        String[] strarr = str.split("\\s+");
        if (strarr.length != pattern.length()) {
            return false;
        }
        for (int i=0; i<pattern.length(); i++) {
            if (patternMap.containsKey(pattern.charAt(i))) {
                if (!patternMap.get(pattern.charAt(i)).equals(strarr[i])) {
                    return false;
                }
            } else {
                if (hm2.containsKey(strarr[i])) {
                    return false;
                }
                patternMap.put(pattern.charAt(i), strarr[i]);
                hm2.put(strarr[i], pattern.charAt(i));
            }
        }
        return true;
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

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode oddHead = head;
        ListNode evenHead = head.next;
        ListNode evenNext = head.next;
        while (oddHead != null && oddHead.next != null) {
            oddHead.next = oddHead.next.next;
            if (oddHead.next != null) {
                oddHead = oddHead.next;
            }
            if (oddHead != null) {
                evenNext.next = oddHead.next;
                evenNext = evenNext.next;
            }
        }
        oddHead.next = evenHead;
        return head;
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

    public void rotate(int[][] matrix) {

        Calendar c = Calendar.getInstance();
        c.set(2019, 10, 30);
        c.get(Calendar.DAY_OF_WEEK);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

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

    public static int[] dailyTemperatures(int[] T) {
        int[] ret = new int[T.length];
        for (int i = T.length - 1; i>=0; i--) {
            for (int j=i+1; j<T.length; j++) {
                if (T[j] > T[i]) {
                    ret[i] = j - i;
                    break;
                }
            }
        }
        return ret;
    }

    public static List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> subset = new ArrayList<Integer>();
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

    private TreeNode createANode(int val) {

        TreeNode root = new TreeNode(val);

        TreeNode node1 = new TreeNode(1);
        root.left = node1;

        TreeNode node4 = new TreeNode(4);
        root.right = node4;

        TreeNode node2 = new TreeNode(2);
        node1.right = node2;
        return root;
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

    public static int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
        int begin = 0, end = 0;
        for (int i=0; i<nums.length; i++) {
            if (begin != 0) {
                if (nums[i] >= min) {
                    min = nums[i];
                } else {
                    begin = i;
                }
            }

            if (nums[nums.length - 1 - i] <= max) {
                max = nums[nums.length - 1 - i];
            } else {
                end = nums.length - 1 - i;
            }
        }
        if (end - begin <= 0) {
            return 0;
        }
        return end - begin;
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

    public TreeNode creaeAOneTree(){
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(1);
        node1.right = node2;
        TreeNode node3 = new TreeNode(1);
        node2.right = node3;
        return node1;
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
        List<String> ret = new ArrayList<String>();
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
        for(String str : strs){
            int one = 0;
            int zero = 0;
            for(char c : str.toCharArray()){
                if(c == '1')
                    one++;
                else
                    zero++;
            }
            for(int i = m; i >= zero; i--){
                for(int j = n; j >= one; j--){
                    if(one <= j && zero <= i)
                        dp[i][j] = Math.max(dp[i][j],dp[i - zero][j - one] + 1);
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
                if (useThisOne > max); {
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

    public ListNode createList(){
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(0);
        head.next = node2;
        ListNode node3 = new ListNode(1);
        node2.next = node3;
//        ListNode node4 = new ListNode(3);
//        node3.next = node4;
//        ListNode node5 = new ListNode(3);
//        node4.next = node5;
//        ListNode node6 = new ListNode(1);
//        node5.next = node6;
        return head;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static int singleNumber(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 1) {
            return nums[0];
        }
        int ret = nums[0];
        for (int i=1; i<nums.length; i++) {
            ret = ret^nums[i];
        }
        return ret;
    }

    public TreeNode createTheRoot(){
        TreeNode root = new TreeNode(1);
        TreeNode rootleft = new TreeNode(3);
        TreeNode rootright = new TreeNode(6);
        root.left = rootleft;
        root.right = rootright;
        TreeNode rootleftleft = new TreeNode(2);
        rootleft.left = rootleftleft;
        TreeNode rootrightright = new TreeNode(8);
        rootright.right = rootrightright;
        return root;
    }

    private TreeNode head = null, prev = null;

    public TreeNode increasingBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        increasingBST(root.left);
        if (prev != null) {
            prev.right = root;
            prev.left = null;
        }
        if (head == null) {
            head = root;
        }
        prev = root;
        increasingBST(root.right);
        return head;
    }

    public static int nthUglyNumber(int n) {
        long[] dp = new long[n+1];
        dp[1] = 1;
        for (int i=2; i<n+1; i++) {
            long possibleNext = Long.MAX_VALUE;
            for (int j=i-1; j>0;j--) {
                if (dp[j]*2 > dp[i-1] && dp[j]*2 < possibleNext) {
                    possibleNext = dp[j]*2;
                }
                if (dp[j]*3 > dp[i-1] && dp[j]*3 < possibleNext) {
                    possibleNext = dp[j]*3;
                }
                if (dp[j]*5 > dp[i-1] && dp[j]*5 < possibleNext) {
                    possibleNext = dp[j]*5;
                }
            }
            dp[i] = possibleNext;
        }
        return (int)dp[n];
    }

    public static boolean canJump(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;
        for (int i=0; i<nums.length; i++) {
            for (int j=0; j<i; j++) {
                if (dp[j] == true && (i - j <= nums[j])) {
                    dp[i] = true;
                }
            }
        }
        return dp[nums.length - 1];
    }

    public TreeNode createSourceTree(){
        TreeNode root = new TreeNode(3);
        TreeNode rleft = new TreeNode(4);
        TreeNode rright = new TreeNode(5);
        root.left = rleft;
        root.right = rright;
        TreeNode rleftLeft = new TreeNode(1);
        TreeNode rleftRight = new TreeNode(2);
        rleft.left = rleftLeft;
        rleft.right = rleftRight;
        return root;
    }

    public TreeNode createTargetTree(){
        TreeNode root = new TreeNode(4);
        TreeNode rleft = new TreeNode(2);
        TreeNode rright = new TreeNode(2);
        root.left = rleft;
        root.right = rright;
        return root;
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ret = new ArrayList<Integer>();
        if (root == null) {
            return ret;
        }
        Stack<TreeNode> tree = new Stack<TreeNode>();
        tree.push(root);
        while (!tree.isEmpty()) {
            TreeNode node = tree.peek();
            ret.add(tree.pop().val);
            if (node.right != null) {
                tree.push(node.right);
            }
            if (node.left != null) {
                tree.push(node.left);
            }
        }
        return ret;
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] rets = new int[2];
        HashMap<Integer, Integer> positions = new HashMap<Integer, Integer>();
        for (int i=0; i<nums.length; i++) {
            if (!positions.containsKey(target - nums[i])) {
                positions.put(nums[i], i);
            } else {
                rets[0] = i;
                rets[1] = positions.get(target - nums[i]);
                break;
            }
        }
        return rets;
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
                dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }

    public static int uniquePathsWithObstacles(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        if (grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (m<=1 || n<=1) {
            return 1;
        }
        if (grid[0][0] == 1) {
            return 0;
        }
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i=1; i<m; i++) {
            if (grid[i][0] == 1) {
                dp[i][0] = 0;
            } else {
                if (dp[i - 1][0] == 0) {
                    dp[i][0] = 0;
                } else {
                    dp[i][0] = 1;
                }
            }
        }
        for (int i=1; i<n; i++) {
            if (grid[0][i] == 1) {
                dp[0][i] = 0;
            } else {
                if (dp[0][i-1] == 0) {
                    dp[0][i] = 0;
                } else {
                    dp[0][i] = 1;
                }
            }
        }
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                if (grid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[m-1][n-1];
    }

    public static int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i<cost.length + 1; i++) {
            dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);
        }
        return dp[cost.length];
    }

    public static int min(int a, int b) {
        if (a>b) {
            return b;
        } else {
            return a;
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low, mid);
                low ++;
                mid ++;
            } else {
                if (nums[mid] == 1) {
                    mid ++;
                } else {
                    if (nums[mid] == 2) {
                        swap(nums, mid, high);
                        high--;
                    }
                }
            }
        }
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
}
