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
    
