package dsandalgo.string;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

//https://leetcode.com/problems/basic-calculator-iii/discuss/113592/Development-of-a-generic-solution-for-the-series-of-the-calculator-problems

public class ExpressionExe {
    
    public static void main(String[] args) {
        ExpressionExe exe = new ExpressionExe();
        //System.out.println(exe.evaluate("(mult 3 (add 2 3))"));

        System.out.println(exe.braceExpansionII("{{a,z},a{b,c},{ab,z}}"));
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
    //Break each cell, push the intermediate result to queue, until there is no {}, add to resultSet
    public List<String> braceExpansionII(String expression) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(expression);
        Set<String> resultSet = new HashSet<>();
        while (!queue.isEmpty()) {
            String str = queue.poll();
            if (str.indexOf('{') == -1) {
                resultSet.add(str);
                continue;
            }
            int i = 0, l = 0, r = 0;
            while (str.charAt(i) != '}') {
                if (str.charAt(i) == '{')
                    l = i;
                i++;
            }
            r = i;
            String before = str.substring(0, l);
            String after = str.substring(r+1);
            String[] strs = str.substring(l+1, r).split(",");
            StringBuilder sb = new StringBuilder();
            for (String ss : strs) {
                sb.setLength(0);
                queue.offer(sb.append(before).append(ss).append(after).toString());
            }
        }
        List<String> ans = new ArrayList<>(resultSet);
        Collections.sort(ans);
        return ans;
    }

    /**
     * https://leetcode.com/problems/tag-validator/
     * Given a string representing a code snippet, you need to implement a tag validator to parse the code and return whether it is valid.
     * A code snippet is valid if all the following rules hold:
     *
     * The code must be wrapped in a valid closed tag. Otherwise, the code is invalid.
     * A closed tag (not necessarily valid) has exactly the following format : <TAG_NAME>TAG_CONTENT</TAG_NAME>.
     * Among them, <TAG_NAME> is the start tag, and </TAG_NAME> is the end tag. The TAG_NAME in start and end tags should be the same.
     * A closed tag is valid if and only if the TAG_NAME and TAG_CONTENT are valid.
     * A valid TAG_NAME only contain upper-case letters, and has length in range [1,9]. Otherwise, the TAG_NAME is invalid.
     * A valid TAG_CONTENT may contain other valid closed tags, cdata and any characters (see note1) EXCEPT unmatched <, unmatched start
     * and end tag, and unmatched or closed tags with invalid TAG_NAME. Otherwise, the TAG_CONTENT is invalid.
     * A start tag is unmatched if no end tag exists with the same TAG_NAME, and vice versa. However, you also need to consider the issue
     * of unbalanced when tags are nested.
     * A < is unmatched if you cannot find a subsequent >. And when you find a < or </, all the subsequent characters until the
     * next > should be parsed as TAG_NAME (not necessarily valid).
     * The cdata has the following format : <![CDATA[CDATA_CONTENT]]>. The range of CDATA_CONTENT is defined as the characters
     * between <![CDATA[ and the first subsequent ]]>.
     * CDATA_CONTENT may contain any characters. The function of cdata is to forbid the validator to parse CDATA_CONTENT,
     * so even it has some characters that can be parsed as tag (no matter valid or invalid), you should treat it as regular characters.
     * Valid Code Examples:
     * Input: "<DIV>This is the first line <![CDATA[<div>]]></DIV>"
     *
     * Output: True
     *
     * Explanation:
     *
     * The code is wrapped in a closed tag : <DIV> and </DIV>.
     *
     * The TAG_NAME is valid, the TAG_CONTENT consists of some characters and cdata.
     *
     * Although CDATA_CONTENT has unmatched start tag with invalid TAG_NAME, it should be considered as plain text, not parsed as tag.
     *
     * So TAG_CONTENT is valid, and then the code is valid. Thus return true.
     *
     *
     * Input: "<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>"
     *
     * Output: True
     *
     * Explanation:
     *
     * We first separate the code into : start_tag|tag_content|end_tag.
     *
     * start_tag -> "<DIV>"
     *
     * end_tag -> "</DIV>"
     *
     * tag_content could also be separated into : text1|cdata|text2.
     *
     * text1 -> ">>  ![cdata[]] "
     *
     * cdata -> "<![CDATA[<div>]>]]>", where the CDATA_CONTENT is "<div>]>"
     *
     * text2 -> "]]>>]"
     *
     *
     * The reason why start_tag is NOT "<DIV>>>" is because of the rule 6.
     * The reason why cdata is NOT "<![CDATA[<div>]>]]>]]>" is because of the rule 7.
     * Invalid Code Examples:
     * Input: "<A>  <B> </A>   </B>"
     * Output: False
     * Explanation: Unbalanced. If "<A>" is closed, then "<B>" must be unmatched, and vice versa.
     *
     * Input: "<DIV>  div tag is not closed  <DIV>"
     * Output: False
     *
     * Input: "<DIV>  unmatched <  </DIV>"
     * Output: False
     *
     * Input: "<DIV> closed tags with invalid tag name  <b>123</b> </DIV>"
     * Output: False
     *
     * Input: "<DIV> unmatched tags with invalid tag name  </1234567890> and <CDATA[[]]>  </DIV>"
     * Output: False
     *
     * Input: "<DIV>  unmatched start tag <B>  and unmatched end tag </C>  </DIV>"
     * Output: False
     * Note:
     * For simplicity, you could assume the input code (including the any characters mentioned above) only contain letters, digits, '<','>','/','!','[',']' and ' '.
     */
    public boolean isValid(String code) {
        // TAG_NAME is pushed to stack
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < code.length();) {
            if (i > 0 && stack.isEmpty()) {
                // No TAG_NAME found in the beginning
                return false;
            }
            // <![CDATA[ begin for <![CDATA[CDATA_CONTENT]]>
            // CDATA_CONTENT may contain any characters and is not checked
            if (code.startsWith("<![CDATA[", i)) {
                int j = i + 9;
                i = code.indexOf("]]>", j);
                if (i < 0) //if not found "]]>"
                    return false;
                i += 3;
            } else {
                // </TAG_NAME> end
                if (code.startsWith("</", i)) {
                    int j = i + 2;
                    i = code.indexOf('>', j);
                    if (i < 0 || i == j || i - j > 9) return false;
                    for (int k = j; k < i; k++) {
                        // TAG_NAME only contain upper-case letters
                        if (!Character.isUpperCase(code.charAt(k))) return false;
                    }
                    String s = code.substring(j, i++);
                    // TAG_NAME doesn't match
                    if (stack.isEmpty() || !stack.pop().equals(s)) return false;
                } else {
                    // <TAG_NAME> begin
                    if (code.startsWith("<", i)){
                        int j = i + 1;
                        i = code.indexOf('>', j);
                        // i < 0  => No '>' found
                        // i == j => Instead of <TAG_NAME> just found <>
                        //  i - j > 9  => TAG_NAME has length in range [1,9]
                        if (i < 0 || i == j || i - j > 9) return false;
                        for (int k = j; k < i; k++){
                            //TAG_NAME only contain upper-case letters
                            if (!Character.isUpperCase(code.charAt(k))) return false;
                        }
                        String s = code.substring(j, i++);
                        stack.push(s);
                    } else {
                        // All other characters
                        i++;
                    }
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * https://leetcode.com/problems/parse-lisp-expression/
     * You are given a string expression representing a Lisp-like expression to return the integer value of.
     *
     * The syntax for these expressions is given as follows.
     *
     * An expression is either an integer, a let-expression, an add-expression, a mult-expression, or an assigned variable.
     * Expressions always evaluate to a single integer.
     * (An integer could be positive or negative.)
     * A let-expression takes the form (let v1 e1 v2 e2 ... vn en expr), where let is always the string "let", then there are
     * 1 or more pairs of alternating variables and expressions, meaning that the first variable v1 is assigned the value of
     * the expression e1, the second variable v2 is assigned the value of the expression e2, and so on sequentially; and then
     * the value of this let-expression is the value of the expression expr.
     * An add-expression takes the form (add e1 e2) where add is always the string "add", there are always two expressions e1,
     * e2, and this expression evaluates to the addition of the evaluation of e1 and the evaluation of e2.
     * A mult-expression takes the form (mult e1 e2) where mult is always the string "mult", there are always two expressions e1,
     * e2, and this expression evaluates to the multiplication of the evaluation of e1 and the evaluation of e2.
     * For the purposes of this question, we will use a smaller subset of variable names. A variable starts with a lowercase letter,
     * then zero or more lowercase letters or digits. Additionally for your convenience, the names "add", "let", or "mult" are
     * protected and will never be used as variable names.
     * Finally, there is the concept of scope. When an expression of a variable name is evaluated, within the context of that
     * evaluation, the innermost scope (in terms of parentheses) is checked first for the value of that variable, and then outer
     * scopes are checked sequentially. It is guaranteed that every expression is legal. Please see the examples for more details on scope.
     * Evaluation Examples:
     * Input: (add 1 2)
     * Output: 3
     *
     * Input: (mult 3 (add 2 3))
     * Output: 15
     *
     * Input: (let x 2 (mult x 5))
     * Output: 10
     *
     * Input: (let x 2 (mult x (let x 3 y 4 (add x y))))
     * Output: 14
     * Explanation: In the expression (add x y), when checking for the value of the variable x,
     * we check from the innermost scope to the outermost in the context of the variable we are trying to evaluate.
     * Since x = 3 is found first, the value of x is 3.
     *
     * Input: (let x 3 x 2 x)
     * Output: 2
     * Explanation: Assignment in let statements is processed sequentially.
     *
     * Input: (let x 1 y 2 x (add x y) (add x y))
     * Output: 5
     * Explanation: The first (add x y) evaluates as 3, and is assigned to x.
     * The second (add x y) evaluates as 3+2 = 5.
     *
     * Input: (let x 2 (add (let x 3 (let x 4 x)) x))
     * Output: 6
     * Explanation: Even though (let x 4 x) has a deeper scope, it is outside the context
     * of the final x in the add-expression.  That final x will equal 2.
     *
     * Input: (let a1 3 b2 (add a1 1) b2)
     * Output 4
     * Explanation: Variable names can contain digits after the first character.
     *
     * Note:
     *
     * The given string expression is well formatted: There are no leading or trailing spaces, there is only a single
     * space separating different components of the string, and no space between adjacent parentheses.
     * The expression is guaranteed to be legal and evaluate to an integer.
     * The length of expression is at most 2000. (It is also non-empty, as that would not be a legal expression.)
     * The answer and all intermediate calculations of that answer are guaranteed to fit in a 32-bit integer.
     */
    public int evaluate(String expression) {
        return eval(expression, new HashMap<>());
    }
    private int eval(String exp, Map<String, Integer> parent) {
        if (exp.charAt(0) != '(') {
            // just a number or a symbol
            if (Character.isDigit(exp.charAt(0)) || exp.charAt(0) == '-') {
                return Integer.parseInt(exp);
            }
            return parent.get(exp);
        }
        // create a new scope, add add all the previous values to it
        Map<String, Integer> map = new HashMap<>();
        map.putAll(parent);
        List<String> tokens = parse(exp.substring(exp.charAt(1) == 'm' ? 6 : 5, exp.length() - 1));
        if (exp.startsWith("(a")) {
            // add
            return eval(tokens.get(0), map) + eval(tokens.get(1), map);
        } else {
            if (exp.startsWith("(m")) {
                // mult
                return eval(tokens.get(0), map) * eval(tokens.get(1), map);
            } else {
                // let
                for (int i = 0; i < tokens.size() - 2; i += 2) {
                    map.put(tokens.get(i), eval(tokens.get(i + 1), map));
                }
                return eval(tokens.get(tokens.size() - 1), map);
            }
        }
    }
    private List<String> parse(String str) {
        // seperate the values between two parentheses
        List<String> res = new ArrayList<>();
        int par = 0;
        StringBuilder sb = new StringBuilder();
        for (char c: str.toCharArray()) {
            if (c == '(') par++;
            if (c == ')') par--;
            if (par == 0 && c == ' ') {
                res.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            res.add(new String(sb));
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/basic-calculator-iii/description/
     *
     * Implement a basic calculator to evaluate a simple expression string.
     *
     * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
     *
     * The expression string contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The integer division should truncate toward zero.
     *
     * You may assume that the given expression is always valid. All intermediate results will be in the range of [-2147483648, 2147483647].
     *
     * Some examples:
     *
     * "1 + 1" = 2
     * " 6-4 / 2 " = 4
     * "2*(5+5*2)/3+(6/2+8)" = 21
     * "(2+6* 3+5- (3*14/7+2)*5)+3"=-12
     *
     *
     * Note: Do not use the eval built-in library function.
     *
     * @param s
     * @return
     */
    //use recursive to cover the ().
    public int calculate_III(String s) {
        Queue<Character> tokens = new ArrayDeque<Character>();
        for(char c : s.toCharArray()){
            if (c != ' ') {
                tokens.offer(c);
            }
        }
        tokens.offer('+');
        return calculate(tokens);
    }
    private int calculate(Queue<Character> tokens){
        char preOp = '+';
        int num = 0, sum = 0, prev = 0;
        while(!tokens.isEmpty()){
            char c = tokens.poll();
            if('0' <= c && c <= '9') {
                num = num * 10 + c - '0';
            } else if (c == '(') {
                num = calculate(tokens);
            } else {
                switch (preOp){
                    case '+':
                        sum += prev;
                        prev = num;
                        break;
                    case '-':
                        sum += prev;
                        prev = -num;
                        break;
                    case '*':
                        prev *= num;
                        break;
                    case '/':
                        prev /= num;
                        break;
                }
                if (c == ')') {
                    break;
                }
                preOp = c;
                num = 0;
            }
        }
        return sum + prev;
    }

    public int calculate_iii_2(String s) {
        s = s.replaceAll(" ", "");
        Stack<Integer> stack = new Stack<>();
        char sign = '+';
        for (int i = 0; i < s.length(); ) {
            char c = s.charAt(i);
            if (c == '(') {
                // find the block and use the recursive to solve
                int open = 1;
                int j = i + 1;
                while (j < s.length() && open != 0) {
                    if (s.charAt(j) == '(') open++;
                    else if (s.charAt(j) == ')') open--;
                    j++;
                }
                int blockValue = calculate(s.substring(i + 1, j - 1));
                i = j;
                if (sign == '+') {
                    stack.push(blockValue);
                } else if (sign == '-') {
                    stack.push(-blockValue);
                } else if (sign == '*') {
                    stack.push(stack.pop() * blockValue);
                } else if (sign == '/') {
                    stack.push(stack.pop() / blockValue);
                }
            } else if (Character.isDigit(c)) {
                int j = i;
                int value = 0;
                while (j < s.length() && Character.isDigit(s.charAt(j))) {
                    value = 10 * value + (s.charAt(j) - '0');
                    j++;
                }
                i = j;
                if (sign == '+') {
                    stack.push(value);
                } else if (sign == '-') {
                    stack.push(-value);
                } else if (sign == '*') {
                    stack.push(stack.pop() * value);
                } else if (sign == '/') {
                    stack.push(stack.pop() / value);
                }
            } else {
                sign = c;
                i++;
            }
        }
        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }

    public int basicCalculatorIII(String s) {
        int l1 = 0, o1 = 1;
        int l2 = 1, o2 = 1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                int num = c - '0';

                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + (s.charAt(++i) - '0');
                }

                l2 = (o2 == 1 ? l2 * num : l2 / num);

            } else if (c == '(') {
                int j = i;

                for (int cnt = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '(') cnt++;
                    if (s.charAt(i) == ')') cnt--;
                    if (cnt == 0) break;
                }

                int num = basicCalculatorIII(s.substring(j + 1, i));

                l2 = (o2 == 1 ? l2 * num : l2 / num);

            } else if (c == '*' || c == '/') {
                o2 = (c == '*' ? 1 : -1);

            } else if (c == '+' || c == '-') {
                l1 = l1 + o1 * l2;
                o1 = (c == '+' ? 1 : -1);

                l2 = 1;
                o2 = 1;
            }
        }

        return (l1 + o1 * l2);
    }
    /**
     * Given an expression such as expression = "e + 8 - a + 5" and an evaluation map such as {"e": 1} (given in terms of
     * evalvars = ["e"] and evalints = [1]), return a list of tokens representing the simplified expression, such as ["-1*a","14"]
     *
     * An expression alternates chunks and symbols, with a space separating each chunk and symbol.
     * A chunk is either an expression in parentheses, a variable, or a non-negative integer.
     * A variable is a string of lowercase letters (not including digits.) Note that variables can be multiple letters, and note that
     * variables never have a leading coefficient or unary operator like "2x" or "-x".
     * Expressions are evaluated in the usual order: brackets first, then multiplication, then addition and subtraction. For example,
     * expression = "1 + 2 * 3" has an answer of ["7"].
     *
     * The format of the output is as follows:
     *
     * For each term of free variables with non-zero coefficient, we write the free variables within a term in sorted order lexicographically.
     * For example, we would never write a term like "b*a*c", only "a*b*c".
     * Terms have degree equal to the number of free variables being multiplied, counting multiplicity. (For example, "a*a*b*c" has degree 4.)
     * We write the largest degree terms of our answer first, breaking ties by lexicographic order ignoring the leading coefficient of the term.
     * The leading coefficient of the term is placed directly to the left with an asterisk separating it from the variables (if they exist.)
     * A leading coefficient of 1 is still printed.
     * An example of a well formatted answer is ["-2*a*a*a", "3*a*a*b", "3*b*b", "4*a", "5*c", "-6"]
     * Terms (including constant terms) with coefficient 0 are not included.  For example, an expression of "0" has an output of [].
     * Examples:
     *
     * Input: expression = "e + 8 - a + 5", evalvars = ["e"], evalints = [1]
     * Output: ["-1*a","14"]
     *
     * Input: expression = "e - 8 + temperature - pressure",
     * evalvars = ["e", "temperature"], evalints = [1, 12]
     * Output: ["-1*pressure","5"]
     *
     * Input: expression = "(e + 8) * (e - 8)", evalvars = [], evalints = []
     * Output: ["1*e*e","-64"]
     *
     * Input: expression = "7 - 7", evalvars = [], evalints = []
     * Output: []
     *
     * Input: expression = "a * b * c + b * a * c * 4", evalvars = [], evalints = []
     * Output: ["5*a*b*c"]
     *
     * Input: expression = "((a - b) * (b - c) + (c - a)) * ((a - b) + (b - c) * (c - a))",
     * evalvars = [], evalints = []
     * Output: ["-1*a*a*b*b","2*a*a*b*c","-1*a*a*c*c","1*a*b*b*b","-1*a*b*b*c","-1*a*b*c*c","1*a*c*c*c","-1*b*b*b*c","2*b*b*c*c","-1*b*c*c*c","2*a*a*b","-2*a*a*c","-2*a*b*b","2*a*c*c","1*b*b*b","-1*b*b*c","1*b*c*c","-1*c*c*c","-1*a*a","1*a*b","1*a*c","-1*b*c"]
     * Note:
     *
     * expression will have length in range [1, 250].
     * evalvars, evalints will have equal lengths in range [0, 100].
     */
    class Term implements Comparable<Term> {
        int coef = 1;
        List<String> vars = new LinkedList<>();

        public Term(int coef) {
            this.coef = coef;
        }

        public Term(String s) {
            vars.add(s);
        }

        public String varString() {
            Collections.sort(vars);
            return vars.isEmpty() ? "" : ("*" + String.join("*", vars));
        }

        public String toString() {
            return coef + varString();
        }

        public boolean equals(Term o) {
            return varString().equals(o.varString());
        }

        public int compareTo(Term t) {
            return vars.size() == t.vars.size() ? varString().compareTo(t.varString()) : t.vars.size() - vars.size();
        }

        public Term multi(Term t) {
            Term res = new Term(coef * t.coef);
            res.vars.addAll(vars);
            res.vars.addAll(t.vars);
            return res;
        }
    }

    class Sequence {
        List<Term> terms = new LinkedList<>();

        public Sequence() {
        }

        public Sequence(int n) {
            terms.add(new Term(n));
        }

        public Sequence(String s) {
            terms.add(new Term(s));
        }

        public Sequence(Term t) {
            terms.add(t);
        }

        public Sequence add(Sequence sq) {
            for (Term t2 : sq.terms) {
                if (t2.coef == 0) continue;
                boolean found = false;
                for (Term t1 : terms) {
                    if (t1.equals(t2)) {
                        t1.coef += t2.coef;
                        if (t1.coef == 0) terms.remove(t1);
                        found = true;
                        break;
                    }
                }
                if (!found) terms.add(t2);
            }
            return this;
        }

        public Sequence multi(Sequence sq) {
            Sequence res = new Sequence();
            for (Term t1 : terms)
                for (Term t2 : sq.terms)
                    res.add(new Sequence(t1.multi(t2)));
            return res;
        }
    }

    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        List<String> res = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < evalvars.length; i++) map.put(evalvars[i], evalints[i]);
        Sequence sq = helper(expression, map);
        Collections.sort(sq.terms);
        for (Term t : sq.terms)
            if (!t.toString().equals("0"))
                res.add(t.toString());
        return res;
    }

    private int idx;

    private Sequence helper(String e, Map<String, Integer> map) {
        Stack<Sequence> stack = new Stack<>();
        stack.push(new Sequence(0));
        int flag = 1;
        while (idx < e.length()) {
            char c = e.charAt(idx++);
            if (c == ' ') continue;
            else if (c == '(') {
                Sequence sq = helper(e, map);
                addToStack(stack, sq, flag);
            } else if (c == ')') break;
            else if (c == '+' || c == '-' || c == '*') flag = c == '+' ? 1 : c == '-' ? -1 : 0;
            else if (Character.isDigit(c)) {
                int coef = c - '0';
                while (idx < e.length() && Character.isDigit(e.charAt(idx))) coef = coef * 10 + e.charAt(idx++) - '0';
                addToStack(stack, new Sequence(coef), flag);
            } else {
                StringBuilder sb = new StringBuilder(c + "");
                while (idx < e.length() && Character.isLetter(e.charAt(idx))) sb.append(e.charAt(idx++));
                String var = sb.toString();
                if (map.containsKey(var)) addToStack(stack, new Sequence(map.get(var)), flag);
                else addToStack(stack, new Sequence(var), flag);
            }
        }
        Sequence res = new Sequence();
        while (!stack.isEmpty()) res.add(stack.pop());
        return res;
    }

    private void addToStack(Stack<Sequence> stack, Sequence sq, int flag) {
        if (flag == 0) stack.add(stack.pop().multi(sq));
        else stack.add(new Sequence(flag).multi(sq));
    }

    /**
     * https://leetcode.com/problems/basic-calculator-ii/description/
     *
     * Implement a basic calculator to evaluate a simple expression string.
     *
     * The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.
     *
     * Example 1:
     *
     * Input: "3+2*2"
     * Output: 7
     * Example 2:
     *
     * Input: " 3/2 "
     * Output: 1
     * Example 3:
     *
     * Input: " 3+5 / 2 "
     * Output: 5
     * Note:
     *
     * You may assume that the given expression is always valid.
     * Do not use the eval built-in library function.
     *
     * @param s
     * @return
     */
//    public int calculateII(String s) {
//        int len = s.length(), sign = 1, result = 0;
//        Stack<Integer> stack = new Stack<Integer>();
//        for (int i = 0; i < len; i++) {
//            if (Character.isDigit(s.charAt(i))) {
//                int sum = s.charAt(i) - '0';
//                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
//                    sum = sum * 10 + s.charAt(i + 1) - '0';
//                    i++;
//                }
//                while (i+1 < len && Character.isSpaceChar(s.charAt(i+1))){
//                    i++;
//                }
//                if (i+1 < len && (s.charAt(i+1) == '*' || s.charAt(i+1) == '/')) {
//                    stack.push(result);
//                    stack.push(sign);
//                    result = sum;
//                } else {
//                    result = result + sum * sign;
//                }
//            } else if (s.charAt(i) == '+')
//                sign = 1;
//            else if (s.charAt(i) == '-')
//                sign = -1;
//            else if (s.charAt(i) == '*' || s.charAt(i) == '/') {
//                char cur = s.charAt(i);
//                while (!Character.isDigit(s.charAt(i))){
//                    i++;
//                }
//                int sum = s.charAt(i) - '0';
//                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
//                    sum = sum * 10 + s.charAt(i + 1) - '0';
//                    i++;
//                }
//                if (cur == '*') {
//                    result = result * sum;
//                } else {
//                    result = result / sum;
//                }
//                while (i+1 < len && Character.isSpaceChar(s.charAt(i+1))){
//                    i++;
//                }
//                if ((i+1 < len && (s.charAt(i+1) != '*' && s.charAt(i+1) != '/')) || i+1 == len) {
//                    result = result * stack.pop() + stack.pop();
//                }
//            }
//        }
//        return result;
//    }

    public int calculateII(String s) {
        int len;
        if (s == null || (len = s.length()) == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char sign = '+';
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            }
            if ((!Character.isDigit(s.charAt(i)) && ' ' != s.charAt(i)) || i == len - 1) {
                if (sign == '-') {
                    stack.push(-num);
                }
                if (sign == '+') {
                    stack.push(num);
                }
                if (sign == '*') {
                    stack.push(stack.pop() * num);
                }
                if (sign == '/') {
                    stack.push(stack.pop() / num);
                }
                sign = s.charAt(i);
                //reset the number after push.
                num = 0;
            }
        }
        int re = 0;
        for (int i : stack) re += i;
        return re;
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
     * Implement a basic calculator to evaluate a simple expression string.
     *
     * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
     *
     * Example 1:
     *
     * Input: "1 + 1"
     * Output: 2
     * Example 2:
     *
     * Input: " 2-1 + 2 "
     * Output: 3
     * Example 3:
     *
     * Input: "(1+(4+5+2)-3)+(6+8)"
     * Output: 23
     * Note:
     * You may assume that the given expression is always valid.
     * Do not use the eval built-in library function.
     */
    public int calculate(String s) {
        int len = s.length(), sign = 1, result = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(s.charAt(i))) {
                int sum = s.charAt(i) - '0';
                while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
                    sum = sum * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                result += sum * sign;
            } else {
                if (s.charAt(i) == '+') {
                    sign = 1;
                } else {
                    if (s.charAt(i) == '-') {
                        sign = -1;
                    } else {
                        if (s.charAt(i) == '(') {
                            //Key is to push the current result and reset to 0.
                            stack.push(result);
                            stack.push(sign);
                            result = 0;
                            sign = 1;
                        } else {
                            if (s.charAt(i) == ')') {
                                result = result * stack.pop() + stack.pop();
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

}
