package dsandalgo.string;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringHardExe {

    public static void main(String[] args) {
        StringHardExe exe = new StringHardExe();
        System.out.println(exe.parseBoolExpr("|(&(t,f,t),!(t))"));
    }

    /**
     * https://leetcode.com/problems/parsing-a-boolean-expression/
     * Return the result of evaluating a given boolean expression, represented as a string.
     *
     * An expression can either be:
     *
     * "t", evaluating to True;
     * "f", evaluating to False;
     * "!(expr)", evaluating to the logical NOT of the inner expression expr;
     * "&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
     * "|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...
     *
     *
     * Example 1:
     *
     * Input: expression = "!(f)"
     * Output: true
     * Example 2:
     *
     * Input: expression = "|(f,t)"
     * Output: true
     * Example 3:
     *
     * Input: expression = "&(t,f)"
     * Output: false
     * Example 4:
     *
     * Input: expression = "|(&(t,f,t),!(t))"
     * Output: false
     *
     *
     * Constraints:
     *
     * 1 <= expression.length <= 20000
     * expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
     * expression is a valid expression representing a boolean, as given in the description.
     */
    public boolean parseBoolExpr(String expression) {
        if (expression.equals("t")) {
            return true;
        }
        if (expression.equals("f")) {
            return false;
        }
        char[] arr = expression.toCharArray();
        char op = arr[0];
        boolean result = false;
        if (op != '|') {
            result = true;
        }
        int count = 0;
        for (int i = 1, pre = 2; i < arr.length; i++) {
            char c = arr[i];
            if (c == '(') {
                count++;
            }
            if (c == ')') {
                count--;
            }
            //Recursive call when met end of () or a comma ,.
            if (c == ',' && count == 1 || c == ')' && count == 0) {
                boolean next = parseBoolExpr(expression.substring(pre, i));
                pre = i + 1;
                if (op == '|') {
                    result |= next;
                } else {
                    if (op == '&') {
                        result &= next;
                    } else {
                        result = !next;
                    }
                }
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/brace-expansion-ii/
     * Under a grammar given below, strings can represent a set of lowercase words.  Let's use R(expr) to denote the set of words the expression represents.
     *
     * Grammar can best be understood through simple examples:
     *
     * Single letters represent a singleton set containing that word.
     * R("a") = {"a"}
     * R("w") = {"w"}
     * When we take a comma delimited list of 2 or more expressions, we take the union of possibilities.
     * R("{a,b,c}") = {"a","b","c"}
     * R("{{a,b},{b,c}}") = {"a","b","c"} (notice the final set only contains each word at most once)
     * When we concatenate two expressions, we take the set of possible concatenations between two words where the first word
     * comes from the first expression and the second word comes from the second expression.
     * R("{a,b}{c,d}") = {"ac","ad","bc","bd"}
     * R("a{b,c}{d,e}f{g,h}") = {"abdfg", "abdfh", "abefg", "abefh", "acdfg", "acdfh", "acefg", "acefh"}
     * Formally, the 3 rules for our grammar:
     *
     * For every lowercase letter x, we have R(x) = {x}
     * For expressions e_1, e_2, ... , e_k with k >= 2, we have R({e_1,e_2,...}) = R(e_1) ∪ R(e_2) ∪ ...
     * For expressions e_1 and e_2, we have R(e_1 + e_2) = {a + b for (a, b) in R(e_1) × R(e_2)}, where + denotes
     * concatenation, and × denotes the cartesian product.
     * Given an expression representing a set of words under the given grammar, return the sorted list of words that the expression represents.
     *
     * Example 1:
     * Input: "{a,b}{c,{d,e}}"
     * Output: ["ac","ad","ae","bc","bd","be"]
     *
     * Example 2:
     * Input: "{{a,z},a{b,c},{ab,z}}"
     * Output: ["a","ab","ac","z"]
     * Explanation: Each distinct word is written only once in the final answer.
     *
     * Constraints:
     * 1 <= expression.length <= 60
     * expression[i] consists of '{', '}', ','or lowercase English letters.
     * The given expression represents a set of words based on the grammar given in the description.
     */
    public List<String> braceExpansionII(String expression) {
        return null;
    }

    /**
     * https://leetcode.com/problems/last-substring-in-lexicographical-order/
     *
     * @param s
     * @return
     */
    /*
    First store indexes of the highest character.
    Then find second highest character.
    Iterate through the list until list has 1 element.
    We prune index1 at each iteration if there is another index2 = index1 + shift. This is used to handle strings like 'aaaaa.....aaa' or 'abababababab'.
    Its each to see that index1 will never be an answer.
     */
    public String lastSubstring(String s) {
        char highest = '@';
        Set<Integer> indexes = null;
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > highest) {
                highest = s.charAt(i);
                indexes = new HashSet<Integer>();
            }
            if (s.charAt(i) == highest) {
                indexes.add(i);
            }
        }
        int shift = 1;
        while(indexes.size() > 1) {
            char shiftHighest = '@';
            Set<Integer> nextLevel = null;
            Set<Integer> toDelete = new HashSet<>();
            for(int ind : indexes) {
                int newIndex = ind + shift;
                if(newIndex < s.length()){
                    if(s.charAt(newIndex) > shiftHighest) {
                        shiftHighest = s.charAt(newIndex);
                        nextLevel = new HashSet<>();
                    }
                    if(s.charAt(newIndex) == shiftHighest) {
                        nextLevel.add(ind);
                    }
                    if(indexes.contains(newIndex)) {
                        toDelete.add(newIndex);
                    }
                }
            }
            for(int del : toDelete) {
                nextLevel.remove(del);
            }
            indexes = nextLevel;
            shift++;
        }
        return s.substring(indexes.iterator().next());
    }

    public String lastSubstring_slow(String s) {
        int largestCharacter = s.charAt(0);
        boolean allCharsSame = true;
        for(char c : s.toCharArray()){
            if(c != largestCharacter) {
                allCharsSame = false;
            }
            largestCharacter = Math.max(largestCharacter, c);
        }
        if(allCharsSame) {
            return s;
        }
        String result = "";
        for(int i = 0; i < s.length(); i++){
            if (s.charAt(i) == largestCharacter && s.substring(i).compareTo(result) > 0) {
                result = s.substring(i);
            }
        }
        return result;
    }

    public String lastSubstring_2(String s) {
        int n = s.length();
        //k is the len when we have two candidates
        //i is the first candidate start position, j is the second one (can not be candidate)
        int i = 0, j= 1, k = 0;
        while (i < n && j < n && k < n) {
            if (i + k >= n || j + k >= n) {
                break;
            }
            // they have same start point, then increase the length
            if (s.charAt(i + k) == s.charAt(j + k)) {
                k++;
            } else {
                // now two candidates are different, then which one is larger
                if (s.charAt(i + k) < s.charAt(j + k)) {
                    i = i + k + 1; // j becomes the candidate, i need move forward
                } else {
                    j = j + k + 1; // i becomes the candidate
                }
                if (i == j) {
                    j++; // potational i, j stay at the same position, j move forward(i also can move)
                }
                k = 0; //reset the len
            }
        }
        int l = Math.min(i, j);
        return s.substring(l);
    }
}
