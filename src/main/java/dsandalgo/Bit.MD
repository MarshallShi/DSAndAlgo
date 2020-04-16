Questions raised from Bit Manipulation.

Missing Number
https://leetcode.com/problems/missing-number/
Intuition is the number itself missing will reveal itself if we do XOR with the index i.

    public int missingNumber(int[] nums) {
        int xor = 0, i = 0;
        for (i = 0; i < nums.length; i++) {
            xor = xor ^ i ^ nums[i];
        }
        return xor ^ i;
    }

Divide Two Integers
https://leetcode.com/problems/divide-two-integers/
Under the restriction, we can only use add or subtract, also bit manipulation. 
    
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        int a = Math.abs(dividend);
        int b = Math.abs(divisor);
        int res = 0;
        while (a - b >= 0) {
            int x = 0;
            //increase the x, to multiply b till it is bigger than a.
            while (a - (b << 1 << x) >= 0) {
                x++;
            }
            res += 1 << x;
            //subtract the b << x in a, start over again.
            a -= b << x;
        }
        return (dividend >= 0) == (divisor >= 0) ? res : -res;
    }
    
