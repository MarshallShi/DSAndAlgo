package dsandalgo.dfsbacktrack;

public class RecursiveSolution {

    public static void main(String[] args) {
        RecursiveSolution exe = new RecursiveSolution();
        //System.out.println(exe.minSteps(3));
    }

    /**
     * https://leetcode.com/problems/2-keys-keyboard/
     *
     * Initially on a notepad only one character 'A' is present. You can perform two operations on this notepad for each step:
     *
     * Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
     * Paste: You can paste the characters which are copied last time.
     *
     *
     * Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted.
     * Output the minimum number of steps to get n 'A'.
     *
     * Example 1:
     *
     * Input: 3
     * Output: 3
     * Explanation:
     * Intitally, we have one character 'A'.
     * In step 1, we use Copy All operation.
     * In step 2, we use Paste operation to get 'AA'.
     * In step 3, we use Paste operation to get 'AAA'.
     *
     *
     * Note:
     *
     * The n will be in the range [1, 1000].
     *
     * @param n
     * @return
     */
    public int minSteps(int n) {
        int res = 0;
        for(int i=2;i<=n;i++){
            while(n%i == 0){
                res = res + i;
                n = n/i;
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/broken-calculator/
     *
     * On a broken calculator that has a number showing on its display, we can perform two operations:
     *
     * Double: Multiply the number on the display by 2, or;
     * Decrement: Subtract 1 from the number on the display.
     * Initially, the calculator is displaying the number X.
     *
     * Return the minimum number of operations needed to display the number Y.
     *
     * Example 1:
     *
     * Input: X = 2, Y = 3
     * Output: 2
     * Explanation: Use double operation and then decrement operation {2 -> 4 -> 3}.
     * Example 2:
     *
     * Input: X = 5, Y = 8
     * Output: 2
     * Explanation: Use decrement and then double {5 -> 4 -> 8}.
     * Example 3:
     *
     * Input: X = 3, Y = 10
     * Output: 3
     * Explanation:  Use double, decrement and double {3 -> 6 -> 5 -> 10}.
     * Example 4:
     *
     * Input: X = 1024, Y = 1
     * Output: 1023
     * Explanation: Use decrement operations 1023 times.
     *
     *
     * Note:
     *
     * 1 <= X <= 10^9
     * 1 <= Y <= 10^9
     *
     * @param X
     * @param Y
     * @return
     */
    public int brokenCalc(int X, int Y) {
        //The base condition is x>=y, then all steps is to do -1 these times.
        if (X >= Y) {
            return X-Y;
        }
        if ((Y&1) == 0) {
            //even y.
            return 1 + brokenCalc(X, Y/2);
        } else {
            //odd y.
            return 1 + brokenCalc(X, Y+1);
        }
    }
}
