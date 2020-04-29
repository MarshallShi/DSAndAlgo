package dsandalgo.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class StackExe {

    /**
     * Stack TODO:
     * 1130. Minimum Cost Tree From Leaf Values
     * 907. Sum of Subarray Minimums
     * 901. Online Stock Span
     * 856. Score of Parentheses
     * 503. Next Greater Element II
     * 496. Next Greater Element I
     * 84. Largest Rectangle in Histogram
     * 42. Trapping Rain Water
     */

    public static void main(String[] args) {
        StackExe exe = new StackExe();
        int[] a = {2,2,2};
        System.out.println(exe.longestValidParentheses(")()())"));
    }

    /**
     * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
     * @param S
     * @return
     */
    public String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<Character>();
        for (int i=0; i< S.length(); i++){
            if(!stack.isEmpty() && stack.peek() == S.charAt(i)) {
                while(!stack.isEmpty() && stack.peek() == S.charAt(i)) {
                    stack.pop();
                }
            } else {
                stack.push(S.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    /**
     * https://leetcode.com/problems/remove-outermost-parentheses/
     * @param S
     * @return
     */
    public String removeOuterParentheses(String S) {
        String ret = "";
        int skipIdx = 0;
        int numberOfLeft = 0;
        for (int i = 0; i < S.length(); i++) {
            if (i != skipIdx) {
                if (S.charAt(i) == '(') {
                    ret = ret + S.charAt(i);
                    numberOfLeft++;
                } else {
                    //char is the ), reset.
                    if (numberOfLeft == 0) {
                        skipIdx = i + 1;
                    } else {
                        ret = ret + S.charAt(i);
                        numberOfLeft--;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/exclusive-time-of-functions/
     *
     * On a single threaded CPU, we execute some functions.  Each function has a unique id between 0 and N-1.
     *
     * We store logs in timestamp order that describe when a function is entered or exited.
     *
     * Each log is a string with this format: "{function_id}:{"start" | "end"}:{timestamp}".  For example,
     * "0:start:3" means the function with id 0 started at the beginning of timestamp 3.
     * "1:end:2" means the function with id 1 ended at the end of timestamp 2.
     *
     * A function's exclusive time is the number of units of time spent in this function.  Note that this does not include any recursive calls to child functions.
     *
     * The CPU is single threaded which means that only one function is being executed at a given time unit.
     *
     * Return the exclusive time of each function, sorted by their function id.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input:
     * n = 2
     * logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
     * Output: [3, 4]
     * Explanation:
     * Function 0 starts at the beginning of time 0, then it executes 2 units of time and reaches the end of time 1.
     * Now function 1 starts at the beginning of time 2, executes 4 units of time and ends at time 5.
     * Function 0 is running again at the beginning of time 6, and also ends at the end of time 6, thus executing for 1 unit of time.
     * So function 0 spends 2 + 1 = 3 units of total time executing, and function 1 spends 4 units of total time executing.
     * @param n
     * @param logs
     * @return
     */
    //stack based solution: push all the previous unfinished function id into the stack, when a new function start, or end, update the sum of total for
    //the stack top function id.
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();//store id, not timestamp
        int prev = 0;//store timestamp
        for (String log : logs){
            String[] strs = log.split(":");
            int id = Integer.parseInt(strs[0]);
            int curr = Integer.parseInt(strs[2]);
            if (strs[1].equals("start")){
                if (!stack.isEmpty()){
                    res[stack.peek()] += curr - prev;
                }
                stack.push(id);
                prev = curr;
            }else{
                res[stack.pop()] += curr - prev + 1;
                prev = curr + 1;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/decode-string/
     * @param s
     * @return
     */
    public String decodeString(String s) {
        if (s == null || s.length() ==0) {
            return s;
        }
        Stack<String> stack = new Stack<String>();
        String ret = "";
        int i = 0;
        while (i<s.length()) {
            char ch = s.charAt(i);
            int chAsInt = Character.isDigit(ch) ? Integer.parseInt(ch + "") : 0;
            if (chAsInt > 0 ) {
                String numStr = ""+ chAsInt;
                while (i+1 < s.length() && s.charAt(i+1) != '[') {
                    numStr = numStr +s.charAt(i+1);
                    i++;
                }
                stack.push(""+ numStr);
            } else {
                if (ch != ']') {
                    stack.push(""+ ch);
                } else {
                    String toRepeat = "";
                    while (!stack.peek().equals("[")) {
                        toRepeat = stack.pop() + toRepeat;
                    }
                    stack.pop();
                    int kTimes = Integer.parseInt(stack.pop());
                    String tempRest = toRepeat;
                    while (kTimes > 1) {
                        tempRest = tempRest + toRepeat;
                        kTimes--;
                    }
                    if (stack.isEmpty() && i == s.length() - 1) {
                        return tempRest;
                    } else {
                        for (int j=0; j<tempRest.length(); j++) {
                            stack.push(""+tempRest.charAt(j));
                        }
                    }
                }
            }
            i++;
        }
        while (!stack.isEmpty()) {
            ret = stack.pop() + ret;
        }
        return ret;
    }

    public String decodeString_recursive(String s) {
        Deque<Character> queue = new LinkedList<>();
        for (char c : s.toCharArray()) queue.offer(c);
        return helper(queue);
    }

    public String helper(Deque<Character> queue) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (!queue.isEmpty()) {
            char c = queue.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else {
                if (c == '[') {
                    String sub = helper(queue);
                    for (int i = 0; i < num; i++) {
                        sb.append(sub);
                    }
                    num = 0;
                } else {
                    if (c == ']') {
                        break;
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
     * Given a string s of '(' , ')' and lowercase English characters.
     *
     * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.
     *
     * Formally, a parentheses string is valid if and only if:
     *
     * It is the empty string, contains only lowercase characters, or
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     *
     *
     * Example 1:
     *
     * Input: s = "lee(t(c)o)de)"
     * Output: "lee(t(c)o)de"
     * Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
     * Example 2:
     *
     * Input: s = "a)b(c)d"
     * Output: "ab(c)d"
     * Example 3:
     *
     * Input: s = "))(("
     * Output: ""
     * Explanation: An empty string is also valid.
     * Example 4:
     *
     * Input: s = "(a(b(c)d)"
     * Output: "a(b(c)d)"
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is one of  '(' , ')' and lowercase English letters.
     */
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < sb.length(); ++i) {
            if (sb.charAt(i) == '(') {
                st.push(i);
            }
            if (sb.charAt(i) == ')') {
                if (!st.empty()) {
                    st.pop();
                } else {
                    sb.setCharAt(i, '*');
                }
            }
        }
        while (!st.empty()) {
            sb.setCharAt(st.pop(), '*');
        }
        return sb.toString().replaceAll("\\*", "");
    }

    //Solution 2: just count the open and close.
    public String minRemoveToMakeValid_count(String s) {
        int open = 0;
        int close = 0;
        for (int i = 0; i < s.length(); i++)  if (s.charAt(i) == ')') close++;

        StringBuilder sb = new StringBuilder();

        for (char c: s.toCharArray()) {
            if (c == '(') {
                if (open == close) continue;
                open++;
            } else if (c == ')') {
                close--;
                if (open == 0) continue;
                open--;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * https://leetcode.com/problems/longest-valid-parentheses/
     * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
     *
     * Example 1:
     *
     * Input: "(()"
     * Output: 2
     * Explanation: The longest valid parentheses substring is "()"
     * Example 2:
     *
     * Input: ")()())"
     * Output: 4
     * Explanation: The longest valid parentheses substring is "()()"
     */
    //we only update the result (max) when we find a "pair".
    //If we find a pair. We throw this pair away and see how big the gap is between current and previous invalid.
    //EX: "( )( )"
    //stack: -1, 0,
    //when we get to index 1 ")", the peek is "(" so we pop it out and see what's before "(".
    //In this example it's -1. So the gap is "current_index" - (-1) = 2.
    //The idea only update the result (max) when we find a "pair" and push -1 to stack first covered all edge cases.
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')' && stack.size() > 1 && s.charAt(stack.peek()) == '(') {
                stack.pop();
                result = Math.max(result, i - stack.peek());
            } else {
                stack.push(i);
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/number-of-valid-subarrays/
     * Given an array A of integers, return the number of non-empty continuous subarrays that satisfy the following condition:
     *
     * The leftmost element of the subarray is not larger than other elements in the subarray.
     *
     * Example 1:
     * Input: [1,4,2,5,3]
     * Output: 11
     * Explanation: There are 11 valid subarrays: [1],[4],[2],[5],[3],[1,4],[2,5],[1,4,2],[2,5,3],[1,4,2,5],[1,4,2,5,3].
     * Example 2:
     * Input: [3,2,1]
     * Output: 3
     * Explanation: The 3 valid subarrays are: [3],[2],[1].
     * Example 3:
     * Input: [2,2,2]
     * Output: 6
     * Explanation: There are 6 valid subarrays: [2],[2],[2],[2,2],[2,2],[2,2,2].
     *
     * Note:
     * 1 <= A.length <= 50000
     * 0 <= A[i] <= 100000
     */
    public int validSubarrays(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            //Get the right next number
            int num = nums[i];
            while (!stack.isEmpty() && nums[stack.peek()] > num) {
                res[stack.pop()] = i;
            }
            stack.push(i);
        }
        int ret = 0;
        for (int i=0; i<res.length; i++) {
            if (res[i] != -1) {
                ret += res[i] - i;
            } else {
                ret += res.length - i;
            }
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/number-of-atoms/
     * Given a chemical formula (given as a string), return the count of each atom.
     *
     * An atomic element always starts with an uppercase character, then zero or more lowercase letters,
     * representing the name.
     *
     * 1 or more digits representing the count of that element may follow if the count is greater than 1.
     * If the count is 1, no digits will follow. For example, H2O and H2O2 are possible, but H1O2 is impossible.
     *
     * Two formulas concatenated together produce another formula. For example, H2O2He3Mg4 is also a formula.
     *
     * A formula placed in parentheses, and a count (optionally added) is also a formula.
     * For example, (H2O2) and (H2O2)3 are formulas.
     *
     * Given a formula, output the count of all elements as a string in the following form:
     * the first name (in sorted order), followed by its count (if that count is more than 1),
     * followed by the second name (in sorted order), followed by its count (if that count is more than 1), and so on.
     *
     * Example 1:
     * Input:
     * formula = "H2O"
     * Output: "H2O"
     * Explanation:
     * The count of elements are {'H': 2, 'O': 1}.
     * Example 2:
     * Input:
     * formula = "Mg(OH)2"
     * Output: "H2MgO2"
     * Explanation:
     * The count of elements are {'H': 2, 'Mg': 1, 'O': 2}.
     * Example 3:
     * Input:
     * formula = "K4(ON(SO3)2)2"
     * Output: "K4N2O14S4"
     * Explanation:
     * The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 4}.
     * Note:
     *
     * All atom names consist of lowercase letters, except for the first character which is uppercase.
     * The length of formula will be in the range [1, 1000].
     * formula will only consist of letters, digits, and round parentheses, and is a valid formula as defined in the problem.
     */
    private Map<String, Integer> count = new HashMap<>();
    public String countOfAtoms(String formula) {
        countOfAtomsHelper(formula, 1);
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            pq.offer(entry);
        }
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> en = pq.poll();
            sb.append(en.getKey());
            if (en.getValue() > 1) {
                sb.append(en.getValue());
            }
        }
        return sb.toString();
    }
    private void countOfAtomsHelper(String formula, int times) {
        for (int i=0; i<formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                int c = 1, ending = 0;
                for (int j=i+1; j<formula.length(); j++) {
                    if (formula.charAt(j) == '(') {
                        c++;
                    }
                    if (formula.charAt(j) == ')') {
                        c--;
                    }
                    if (c == 0) {
                        ending = j;
                        break;
                    }
                }
                String subString = formula.substring(i+1, ending);
                int numIdx = ending+1;
                while (numIdx<formula.length() && Character.isDigit(formula.charAt(numIdx))) {
                    numIdx++;
                }
                if (numIdx >= ending+1 && ending+1 < formula.length()) {
                    int nTimes = Integer.parseInt(formula.substring(ending+1, numIdx));
                    countOfAtomsHelper(subString, nTimes*times);
                    i = numIdx-1;
                } else {
                    countOfAtomsHelper(subString, 1*times);
                    i = ending;
                }
            } else {
                if (Character.isUpperCase(formula.charAt(i))) {
                    int ending = i+1;
                    while (ending < formula.length() && Character.isLowerCase(formula.charAt(ending))) {
                        ending++;
                    }
                    String atomKey = formula.substring(i, ending);
                    if (ending<formula.length() && Character.isDigit(formula.charAt(ending))) {
                        int numEnd = ending + 1;
                        while (numEnd < formula.length() && Character.isDigit(formula.charAt(numEnd))) {
                            numEnd++;
                        }
                        int ntimes = Integer.parseInt(formula.substring(ending, numEnd));
                        count.put(atomKey, count.getOrDefault(atomKey, 0) + ntimes*times);
                        i = numEnd-1;
                    } else {
                        count.put(atomKey, count.getOrDefault(atomKey, 0) + 1*times);
                        i = ending-1;
                    }
                }
            }
        }
    }

    /**
     * https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
     * @param text
     * @return
     */
    public String smallestSubsequence(String text) {
        int[] count = new int[26];
        int[] inUse = new int[26];
        for (int i = 0; i < text.length(); i++) {
            count[text.charAt(i) - 'a']++;
        }
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            count[ch - 'a']--;
            if (inUse[ch - 'a'] == 1) {
                continue;
            }
            while (!stack.isEmpty() && count[stack.peek() - 'a']!=0 && stack.peek() > ch) {
                inUse[stack.pop() - 'a']--;
            }
            stack.push(ch);
            inUse[ch -'a']++;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }
	
    /**
     * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
     *
     * Given a string s, a k duplicate removal consists of choosing k adjacent and equal letters from s
     * and removing them causing the left and the right side of the deleted substring to concatenate together.
     *
     * We repeatedly make k duplicate removals on s until we no longer can.
     *
     * Return the final string after all such duplicate removals have been made.
     *
     * It is guaranteed that the answer is unique.
     *
     * Example 1:
     *
     * Input: s = "abcd", k = 2
     * Output: "abcd"
     * Explanation: There's nothing to delete.
     *
     * Example 2:
     *
     * Input: s = "deeedbbcccbdaa", k = 3
     * Output: "aa"
     * Explanation:
     * First delete "eee" and "ccc", get "ddbbbdaa"
     * Then delete "bbb", get "dddaa"
     * Finally delete "ddd", get "aa"
     *
     * Example 3:
     *
     * Input: s = "pbbcggttciiippooaais", k = 2
     * Output: "ps"
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * 2 <= k <= 10^4
     * s only contains lower case English letters.
     *
     */
    class CharCount{
        public char ch;
        public int count;
        public CharCount(char _ch, int _c) {
            ch = _ch;
            count = _c;
        }
    }
    public String removeDuplicates(String s, int k) {
        Stack<CharCount> stack = new Stack<CharCount>();
        for (int i=0; i<s.length(); i++) {
            if (stack.isEmpty()) {
                stack.push(new CharCount(s.charAt(i), 1));
            } else {
                CharCount cc = stack.peek();
                if (cc.ch == s.charAt(i) && cc.count + 1 == k) {
                    for (int j=k-1; j>0; j--) {
                        stack.pop();
                    }
                } else {
                    if (cc.ch == s.charAt(i)) {
                        stack.push(new CharCount(cc.ch, cc.count + 1));
                    } else {
                        stack.push(new CharCount(s.charAt(i), 1));
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop().ch);
        }
        return sb.reverse().toString();
    }

    /**
     * https://leetcode.com/problems/score-of-parentheses/
     *
     * Given a balanced parentheses string S, compute the score of the string based on the following rule:
     *
     * () has score 1
     * AB has score A + B, where A and B are balanced parentheses strings.
     * (A) has score 2 * A, where A is a balanced parentheses string.
     *
     *
     * Example 1:
     *
     * Input: "()"
     * Output: 1
     *
     * Example 2:
     *
     * Input: "(())"
     * Output: 2
     *
     * Example 3:
     *
     * Input: "()()"
     * Output: 2
     *
     * Example 4:
     *
     * Input: "(()(()))"
     * Output: 6
     *
     *
     * Note:
     *
     * S is a balanced parentheses string, containing only ( and ).
     * 2 <= S.length <= 50
     * @param s
     * @return
     */
    //https://leetcode.com/problems/score-of-parentheses/discuss/141777/C%2B%2BJavaPython-O(1)-Space
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(-1);
            } else {
                int cur = 0;
                while (stack.peek() != -1) {
                    cur += stack.pop();
                }
                stack.pop();
                stack.push(cur == 0 ? 1 : cur * 2);
            }
        }
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }

    /**
     * https://leetcode.com/problems/longest-absolute-file-path/
     *
     * @param input
     * @return
     */
    public int lengthLongestPath(String input) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0); // "dummy" length
        int maxLen = 0;
        String[] tokens = input.split("\n");
        for (String s : tokens) {
            int lev = s.lastIndexOf("\t") + 1; // number of "\t"
            while (lev+1 < stack.size()) {
                stack.pop(); // find parent
            }
            int len = stack.peek() + s.length() - lev + 1; // remove "/t", add"/"
            stack.push(len);
            // check if it is file
            if (s.contains(".")) {
                maxLen = Math.max(maxLen, len-1);
            }
        }
        return maxLen;
    }

    /**
     * https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/
     * You are given a string s that consists of lower case English letters and brackets.
     *
     * Reverse the strings in each pair of matching parentheses, starting from the innermost one.
     *
     * Your result should not contain any brackets.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "(abcd)"
     * Output: "dcba"
     * Example 4:
     *
     * Input: s = "a(bcdefghijkl(mno)p)q"
     * Output: "apmnolkjihgfedcbq"
     *
     * @param s
     * @return
     */
    public String reverseParentheses(String s) {
        char[] arr = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();
        int j = 0, firstIdx = -1;
        while (arr[j] != '(') {
            sb.append(arr[j]);
            j++;
        }
        if (j != arr.length) {
            firstIdx = j;
        }
        for (int i = j; i<arr.length; i++) {
            if (arr[i] != ')') {
                stack.push(arr[i]);
            } else {
                int poppoedChars = 0;
                while (!stack.isEmpty()) {
                    if (stack.peek() != '(') {
                        sb.append(stack.pop());
                        poppoedChars++;
                    } else {
                        stack.pop();
                        if (!stack.isEmpty() && stack.size() > firstIdx) {
                            for (int k = poppoedChars; k>0; k--) {
                                stack.push(sb.charAt(sb.length() - k));
                            }
                            sb.setLength(sb.length() - poppoedChars);
                        }
                        break;
                    }
                }
            }
        }
        StringBuilder temp = new StringBuilder();
        while (!stack.isEmpty()){
            temp.append(stack.pop());
        }
        sb.append(temp.reverse());
        return sb.toString();
    }


    /**
     * https://leetcode.com/problems/simplify-path/
     *
     * Example 1:
     *
     * Input: "/home/"
     * Output: "/home"
     * Explanation: Note that there is no trailing slash after the last directory name.
     * Example 2:
     *
     * Input: "/../"
     * Output: "/"
     * Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.
     * Example 3:
     *
     * Input: "/home//foo/"
     * Output: "/home/foo"
     * Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.
     * Example 4:
     *
     * Input: "/a/./b/../../c/"
     * Output: "/c"
     * Example 5:
     *
     * Input: "/a/../../b/../c//.//"
     * Output: "/c"
     * Example 6:
     *
     * Input: "/a//b////c/d//././/.."
     * Output: "/a/b/c"
     *
     * @param path
     * @return
     */
    public String simplifyPath(String path) {
        if (path == null) {
            return null;
        }
        String[] str = path.split("/");
        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack<String>();
        for (String s : str) {
            if (!stack.isEmpty() && "..".equals(s)) {
                stack.pop();
            }
            if ("".equals(s) || ".".equals(s) || "..".equals(s)) {
                continue;
            }
            stack.push(s);
        }
        for (String valid : stack) {
            sb.append("/" + valid);
        }
        if (sb.length() == 0) {
            return "/";
        } else {
            return sb.toString();
        }
    }

    public String decodeAtIndex(String S, int K) {
        if (S == null || S.length() ==0) {
            return S;
        }
        Stack<Character> stack = new Stack<Character>();
        int counter = 0;
        for (int i=0; i<S.length(); i++) {
            char ch = S.charAt(i);
            int chAsInt = Character.isDigit(ch) ? Integer.parseInt(ch + "") : 0;
            if (chAsInt > 0 ) {
                //For numbers, need to get the while string.
                if (!stack.isEmpty()) {
                    String toRepeat = "";
                    while (!stack.isEmpty()) {
                        toRepeat = stack.pop().toString() + toRepeat;
                    }
                    counter = 0;
                    for (int j = 0; j<chAsInt; j++) {
                        for (int k=0; k<toRepeat.length(); k++) {
                            stack.push(toRepeat.charAt(k));
                            counter++;
                            if (counter == K) {
                                return toRepeat.charAt(k) + "";
                            }
                        }
                    }
                }
            } else {
                stack.push(ch);
                counter++;
                if (counter == K) {
                    return ch + "";
                }
            }
        }
        return "";
    }


    /**
     * https://leetcode.com/problems/valid-parentheses/
     * @param s
     * @return
     */
    private char[][] brackets = {{'(',')'},{'{','}'},{'[',']'}};
    private boolean isOpen(char ch){
        for (char[] bras : brackets) {
            if (bras[0] == ch) return true;
        }
        return false;
    }
    private boolean isMatching(char ch1, char ch2){
        for (char[] bras : brackets) {
            if (bras[0] == ch1 && bras[1] == ch2) return true;
        }
        return false;
    }
    public boolean isValid(String s) {
        if (s == null  || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 != 0) {
            return false;
        }
        if (!isOpen(s.charAt(0))) {
            return false;
        }
        Stack<Character> stack = new Stack<Character>();
        for (int i=0; i<s.length(); i++) {
            if (isOpen(s.charAt(i))) {
                stack.push(s.charAt(i));
            } else {
                if (s.isEmpty() || !isMatching(stack.pop(), s.charAt(i))) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
