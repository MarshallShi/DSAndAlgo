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

Single Number
https://leetcode.com/problems/single-number/
Leverage the fact a^a = 0, the only number left will be the num^0, which is num.

    public int singleNumber(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 1) {
            return nums[0];
        }
        int ret = nums[0];
        for (int i=1; i<nums.length; i++) {
            ret = ret^nums[i];
        }
        return ret;
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
    
https://leetcode.com/problems/sqrtx/

    public int mySqrt(int x) {
        long ans = 0;
        long bit = 1l << 16;
        while (bit > 0) {
            ans |= bit;
            if (ans * ans > x) {
                ans ^= bit;
            }
            bit >>= 1;
        }
        return (int) ans;
    }
    