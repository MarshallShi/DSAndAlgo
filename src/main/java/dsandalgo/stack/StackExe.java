package dsandalgo.stack;

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
        System.out.println(exe.reverseParentheses("f(ed(et(oc))el)w"));
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

    /**
     * Implement a basic calculator to evaluate a simple expression string.
     *
     * The expression string may contain open ( and closing parentheses ),
     * the plus + or minus sign -, non-negative integers and empty spaces .
     *
     * Example 1:
     *
     * Input: "1 + 1"
     * Output: 2
     *
     * https://leetcode.com/problems/basic-calculator/
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        int len = s.length(), sign = 1, result = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                int sum = s.charAt(i) - '0';
                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
                    sum = sum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                result += sum * sign;
            } else if (s.charAt(i) == '+')
                sign = 1;
            else if (s.charAt(i) == '-')
                sign = -1;
            else if (s.charAt(i) == '(') {
                //Key is to push the current result and reset to 0.
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (s.charAt(i) == ')') {
                result = result * stack.pop() + stack.pop();
            }

        }
        return result;
    }

    /**
     *
     * Implement a basic calculator to evaluate a simple expression string.
     *
     * The expression string contains only non-negative integers, +, -, *, / operators and empty spaces .
     * The integer division should truncate toward zero.
     *
     * Example 1:
     *
     * Input: "3+2*2"
     * Output: 7
     *
     * https://leetcode.com/problems/basic-calculator-ii/
     *
     * @param s
     * @return
     */
    public int calculate_2(String s) {
        int len = s.length(), sign = 1, result = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                int sum = s.charAt(i) - '0';
                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
                    sum = sum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                while (i+1 < len && Character.isSpaceChar(s.charAt(i+1))){
                    i++;
                }
                if (i+1 < len && (s.charAt(i+1) == '*' || s.charAt(i+1) == '/')) {
                    stack.push(result);
                    stack.push(sign);
                    result = sum;
                } else {
                    result = result + sum * sign;
                }
            } else if (s.charAt(i) == '+')
                sign = 1;
            else if (s.charAt(i) == '-')
                sign = -1;
            else if (s.charAt(i) == '*' || s.charAt(i) == '/') {
                char cur = s.charAt(i);
                while (!Character.isDigit(s.charAt(i))){
                    i++;
                }
                int sum = s.charAt(i) - '0';
                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
                    sum = sum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                if (cur == '*') {
                    result = result * sum;
                } else {
                    result = result / sum;
                }
                while (i+1 < len && Character.isSpaceChar(s.charAt(i+1))){
                    i++;
                }
                if ((i+1 < len && (s.charAt(i+1) != '*' && s.charAt(i+1) != '/')) || i+1 == len) {
                    result = result * stack.pop() + stack.pop();
                }
            }
        }
        return result;
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
