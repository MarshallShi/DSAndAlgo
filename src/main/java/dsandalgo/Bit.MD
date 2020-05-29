
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
    
K-th Symbol in Grammar
https://leetcode.com/problems/k-th-symbol-in-grammar/

    public int kthGrammar(int N, int K) {
        return Integer.bitCount(K - 1) & 1;
    }

https://leetcode.com/problems/sum-of-two-integers/

    public int getSum(int a, int b) {
        /*
         * Keep adding until we have no carry left
         */
        while (b != 0) {
            /*
             * Take note of what positions will need a carry, we will left shift this below
             * and make b hold it. Remember: a carry is not applied where it is
             * discovered...it is applied 1 position to the left of where it was born
             */
            int carry = a & b;
            /*
             * a's job is to keep the sum we are going to be working on, '^' does bit
             * addition, see explanation below to fully understand this
             */
            a = a ^ b;
            /*
             * b will house the carry from the operation, we left shift by 1 because in the
             * next iteration we will add against the carry
             */
            b = carry << 1;
        }
        /*
         * Return a, it was used to house the running sum we were working on the whole
         * time
         */
        return a;
    }
    
Cinema Seat Allocation
https://leetcode.com/problems/cinema-seat-allocation/
Each seat can be either empty or seated, so naturally we can use a bit map to represent the state of the row.
    
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> graph = new HashMap<>();
        for (int[] reserved : reservedSeats) {
            int row = reserved[0];
            int col = reserved[1];
            graph.put(row, graph.getOrDefault(row, 0) | (1 << col)); // create a bit vector of reserved seats
        }
        int max = 0;
        for (int row : graph.keySet()) {
            int reserved = graph.get(row);
            int cnt = 0;
            if ((reserved &  60) == 0) cnt += 1; // check if seats 2,3,4,5 are available
            if ((reserved & 960) == 0) cnt += 1; // check if seats 6,7,8,9 are available
            if ((reserved & 240) == 0 && cnt == 0) cnt = 1; // check if seats 4,5,6,7 are available
            max += cnt;
        }
        return max + 2 * (n - graph.size());
    }