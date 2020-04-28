package dsandalgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Coding1 {

    public static void main(String[] args){
        Coding1 test = new Coding1();
        List<String> dict = new ArrayList<String>();
        dict.add("cat");
        dict.add("bat");
        dict.add("rat");
        String sentence = "the cattle was rattled by the battery";
        int[] nums = {2,7,13,19};
        String[] emails = {"test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"};
        //int[][] matrix = {{1,   3,  5,  7}, {10, 11, 16, 20}, {23, 30, 34, 50}};
        int[][] matrix = {{}};
        //System.out.println(test.minWindow("ADOBECODEBANC", "ABC"));
    }

    public int maxNumberOfBalloons(String text) {
        Map<Character, Integer> chCounterMap = new HashMap<Character, Integer>();
        String bal = "balloon";
        for (int i=0; i<bal.length();i++) {
            chCounterMap.put(bal.charAt(i), 0);
        }
        for (int j=0; j<text.length(); j++) {
            if (chCounterMap.containsKey(text.charAt(j))) {
                chCounterMap.put(text.charAt(j), chCounterMap.get(text.charAt(j)) + 1);
            }
        }
        int min = text.length();
        min = Math.min(min, chCounterMap.get('a'));
        min = Math.min(min, chCounterMap.get('b'));
        min = Math.min(min, chCounterMap.get('n'));
        min = Math.min(min, chCounterMap.get('l')/2);
        min = Math.min(min, chCounterMap.get('o')/2);
        return min;
    }

    public int numUniqueEmails(String[] emails) {
        int counter = 0;
        Map<String, List<String>> domainWithLocalNames = new HashMap<String, List<String>>();
        for (String email : emails) {
            String[] emailParts = email.split("@");
            String localName = emailParts[0];
            String domainName = emailParts[1];
            if (localName.indexOf(".") != -1) {
                localName = localName.replace(".", "");
            }
            if (localName.indexOf("+") != -1) {
                localName = localName.substring(0, localName.indexOf("+"));
            }
            if (domainWithLocalNames.containsKey(domainName)) {
                List<String> curLocalNameList = domainWithLocalNames.get(emailParts[1]);
                if (!curLocalNameList.contains(localName)) {
                    counter++;
                    curLocalNameList.add(localName);
                }
            } else {
                List<String> newLocalList = new ArrayList<String>();
                newLocalList.add(localName);
                counter++;
                domainWithLocalNames.put(domainName, newLocalList);
            }
        }
        return counter;
    }

    private List<List<Integer>> ret = new ArrayList<List<Integer>>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtrack(new ArrayList<Integer>(), candidates, target,0);
        return ret;
    }

    public void backtrack(List<Integer> temp, int[] candidates, int target, int start){
        if (target < 0) {
            return;
        }
        if (target == 0) {
            List<Integer> retList = new ArrayList<Integer>();
            for (Integer val : temp) {
                retList.add(val);
            }
            ret.add(retList);
            return;
        }
        for (int i=start; i<candidates.length; i++) {
            temp.add(candidates[i]);
            backtrack(temp, candidates, target - candidates[i], i);
            temp.remove(Integer.valueOf(candidates[i]));
        }
    }

    public boolean find132pattern(int[] nums) {
        Stack<Integer> stack = null;
        for (int i=0; i<nums.length - 2; i++) {
            stack = new Stack<Integer>();
            for (int j=i+1; j<nums.length; j++) {
                if (nums[j] <= nums[i]) {
                    continue;
                } else {
                    if (stack.isEmpty()) {
                        stack.push(nums[j]);
                    } else {
                        if (stack.peek() < nums[j]) {
                            stack.pop();
                            stack.push(nums[j]);
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String reverseWords(String s) {
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i=words.length - 1; i>=0; i--) {
            String temp = words[i].trim();
            if (temp.length() > 0) {
                sb.append(temp);
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0){
            return 0;
        }
        if (citations[0] >= citations.length) {
            return citations.length;
        }
        if (citations[citations.length - 1] == 0) {
            return 0;
        }
        int low = 0, high = citations.length - 1;
        while(low<high) {
            int mid = low + (high - low)/2;
            if (citations[mid] >= citations.length) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        int counter = citations.length - low;
        for (int i = low-1; i>=0; i--) {
            if (citations[i] != 0) {
                counter = counter + 1;
            }
            if (counter > i) {
                return i+1;
            }
        }
        return 0;
    }

    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 1) {
            return 0;
        }
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        int ret = 0;
        for (int i=0; i<nums.length; i++) {
            int low = i+1, high = nums.length - 1;
            while (low < high) {
                int temp = nums[i] + nums[low] + nums[high];
                if (temp == target) {
                    return target;
                } else {
                    if (min > Math.abs(target - temp)) {
                        min = Math.abs(target - temp);
                        ret = temp;
                    }
                    if (temp > target) {
                        high--;
                    } else {
                        low++;
                    }
                }
            }
        }
        return ret;
    }

    public ListNode createNode(){
        ListNode root = new ListNode(1);
        ListNode node2 = new ListNode(2);
        root.next = node2;
        ListNode node3 = new ListNode(3);
        node2.next = node3;
        ListNode node4 = new ListNode(4);
        node3.next = node4;
        ListNode node5 = new ListNode(5);
        node4.next = node5;
        return root;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode rotateRight(ListNode head, int k) {
        ListNode slow = head;
        ListNode fast = head;
        if (head == null) {
            return null;
        }
        int len = 1;
        while (fast.next != null) {
            len++;
            fast = fast.next;
        }
        int newK = k % len;
        int i = 1;
        fast = head;
        while (i<=newK) {
            fast = fast.next;
            i++;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        ListNode newHead = slow.next;
        slow.next = null;
        ListNode move = newHead;
        while (move.next != null) {
            move = move.next;
        }
        move.next = head;
        return newHead;
    }

    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 2) {
            return nums.length;
        }
        int slow = 0, fast = 0;
        while (fast<nums.length) {
            if (slow<2 || nums[fast] > nums[slow-2]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    private TrieNode root;
    /** Initialize your data structure here. */
    public Coding1() {
        root = new TrieNode();
    }

    public String replaceWords(List<String> dict, String sentence) {
        String[] words = sentence.split(" ");
        TrieNode1 root = new TrieNode1();
        for (String str : dict) {
            addWord(root, str);
        }
        for(int i=0; i<words.length; i++) {
            String rootWord = findRoot(root, words[i]);
            if (rootWord != null) {
                words[i] = rootWord;
            }
         }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            stringBuilder.append(words[i] + " ");
        }
        return stringBuilder.toString().trim();
    }

    public String findRoot(TrieNode1 root, String word) {
        TrieNode1 node = root;
        for (int i=0; i<word.length();i++) {
            char curChar = word.charAt(i);
            if (node.links[curChar - 'a'] != null) {
                node = node.links[curChar - 'a'];
                if (node.text != null) {
                    return node.text;
                }
            } else {
                return null;
            }
        }
        return node.text;
    }

    public void addWord(TrieNode1 root, String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        TrieNode1 node = root;
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            if (node.links[cur - 'a'] == null) {
                node.links[cur - 'a'] = new TrieNode1();
            }
            node = node.links[cur - 'a'];
        }
        node.text = word;
    }

    class TrieNode1 {
        TrieNode1[] links;
        String text;
        TrieNode1() {
            links = new TrieNode1[26];
            text = null;
        }
    }
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            if (node.links[cur - 'a'] == null) {
                node.links[cur - 'a'] = new TrieNode();
            }
            node = node.links[cur - 'a'];
        }
        node.isEnd = true;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        TrieNode node = root;
        char[] arr = word.toCharArray();
        if (match(arr, 0, root)) {
            return true;
        }
        return false;
    }

    private boolean match(char[] chs, int k, TrieNode node) {
        if (k == chs.length) {
            return node.isEnd;
        }
        if (chs[k] != '.') {
            return node.links[chs[k] - 'a'] != null && match(chs, k + 1, node.links[chs[k] - 'a']);
        } else {
            for (int i = 0; i < node.links.length; i++) {
                if (node.links[i] != null) {
                    if (match(chs, k + 1, node.links[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        TrieNode() {
            links = new TrieNode[26];
            isEnd = false;
        }
    }
}
