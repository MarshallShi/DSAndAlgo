package dsandalgo.bit;

//https://leetcode.com/problems/single-number-ii/discuss/43295/Detailed-explanation-and-generalization-of-the-bitwise-operation-method-for-single-numbers

public class SingleNumberExe {

    //k = 2, p = 1
    /**
     * https://leetcode.com/problems/single-number/
     */
    public int singleNumber_k2_p1(int[] A) {
        int x1 = 0;

        for (int i : A) {
            x1 ^= i;
        }

        return x1;
    }

    //k = 3, p = 1
    /**
     * https://leetcode.com/problems/single-number-ii/
     */
    public int singleNumber_k3_p1(int[] A) {
        int x1 = 0, x2 = 0, mask = 0;

        for (int i : A) {
            x2 ^= x1 & i;
            x1 ^= i;
            mask = ~(x1 & x2);
            x2 &= mask;
            x1 &= mask;
        }

        return x1;  // p = 1, in binary form p = '01', then p1 = 1, so we should return x1;
        // if p = 2, in binary form p = '10', then p2 = 1, so we should return x2.
    }

    //k = 5, p = 3
    public int singleNumber_k5_p3(int[] A) {
        int x1 = 0, x2 = 0, x3  = 0, mask = 0;

        for (int i : A) {
            x3 ^= x2 & x1 & i;
            x2 ^= x1 & i;
            x1 ^= i;
            mask = ~(x1 & ~x2 & x3);
            x3 &= mask;
            x2 &= mask;
            x1 &= mask;
        }

        return x1;  // p = 3, in binary form p = '011', then p1 = p2 = 1, so we can
        // return either x1 or x2. But if p = 4, in binary form p = '100',
        // only p3 = 1, which implies we can only return x3.
    }

    /**
     * https://leetcode.com/problems/single-number-iii/
     *
     * Given an array of numbers nums, in which exactly two elements appear only once and all the other
     * elements appear exactly twice. Find the two elements that appear only once.
     *
     * Example:
     *
     * Input:  [1,2,1,3,2,5]
     * Output: [3,5]
     * Note:
     *
     * The order of the result is not important. So in the above example, [5, 3] is also correct.
     * Your algorithm should run in linear runtime complexity. Could you implement it using
     * only constant space complexity?
     *
     */
    //The two numbers that appear only once must differ at some bit, this is how we can distinguish between them.
    // Otherwise, they will be one of the duplicate numbers.
    //One important point is that by XORing all the numbers, we actually get the XOR of the two target numbers
    // (because XORing two duplicate numbers always results in 0).
    // Consider the XOR result of the two target numbers; if some bit of the XOR result is 1,
    // it means that the two target numbers differ at that location.
    //
    //Let’s say the at the ith bit, the two desired numbers differ from each other.
    // which means one number has bit i equaling: 0, the other number has bit i equaling 1.
    //
    //Thus, all the numbers can be partitioned into two groups according to their bits at location i.
    //the first group consists of all numbers whose bits at i is 0.
    //the second group consists of all numbers whose bits at i is 1.
    //
    //Notice that, if a duplicate number has bit i as 0, then, two copies of it will belong to the first group.
    // Similarly, if a duplicate number has bit i as 1, then, two copies of it will belong to the second group.
    //by XoRing all numbers in the first group, we can get the first number.
    //by XoRing all numbers in the second group, we can get the second number.
    public int[] singleNumber(int[] nums) {
        // Pass 1 :
        // Get the XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        // Get its last set bit
        diff &= -diff;

        // Pass 2 :
        int[] rets = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums){
            if ((num & diff) == 0) { // the bit is not set
                rets[0] ^= num;
            } else{// the bit is set
                rets[1] ^= num;
            }
        }
        return rets;
    }
}
