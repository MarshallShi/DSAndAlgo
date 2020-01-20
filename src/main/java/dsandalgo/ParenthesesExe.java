package dsandalgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParenthesesExe {

    public static void main(String[] args) {
        ParenthesesExe exe = new ParenthesesExe();
        System.out.println(exe.minRemoveToMakeValid("lee(t(c)o)de)"));
        System.out.println(exe.minRemoveToMakeValid("a)b(c)d"));
        System.out.println(exe.minRemoveToMakeValid("))(("));
        System.out.println(exe.minRemoveToMakeValid("(a(b(c)d)"));
    }

    /**
     * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
     *
     * @param s
     * @return
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
}
