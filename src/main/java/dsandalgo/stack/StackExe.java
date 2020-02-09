package dsandalgo.stack;

import java.util.ArrayDeque;
import java.util.Deque;
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
        System.out.println(exe.removeDuplicates("pbbcggttciiippooaais", 2));
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
}
