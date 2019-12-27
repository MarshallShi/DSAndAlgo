package dsandalgo;

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
        System.out.println(exe.calculate_2("2+4-5 * 5 / 25+100"));
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
}
