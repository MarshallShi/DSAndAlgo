package dsandalgo.array;

public class GasStation {

    /**
     * https://leetcode.com/problems/gas-station/
     * There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
     *
     * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.
     *
     * Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.
     *
     * Note:
     *
     * If there exists a solution, it is guaranteed to be unique.
     * Both input arrays are non-empty and have the same length.
     * Each element in the input arrays is a non-negative integer.
     * Example 1:
     *
     * Input:
     * gas  = [1,2,3,4,5]
     * cost = [3,4,5,1,2]
     *
     * Output: 3
     *
     * Explanation:
     * Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
     * Travel to station 4. Your tank = 4 - 1 + 5 = 8
     * Travel to station 0. Your tank = 8 - 2 + 1 = 7
     * Travel to station 1. Your tank = 7 - 3 + 2 = 6
     * Travel to station 2. Your tank = 6 - 4 + 3 = 5
     * Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
     * Therefore, return 3 as the starting index.
     * Example 2:
     *
     * Input:
     * gas  = [2,3,4]
     * cost = [3,4,3]
     *
     * Output: -1
     *
     * Explanation:
     * You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
     * Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
     * Travel to station 0. Your tank = 4 - 3 + 2 = 3
     * Travel to station 1. Your tank = 3 - 3 + 3 = 3
     * You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
     * Therefore, you can't travel around the circuit once no matter where you start.
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int total_tank = 0;
        int curr_tank = 0;
        int starting_station = 0;
        for (int i = 0; i < n; ++i) {
            total_tank += gas[i] - cost[i];
            curr_tank += gas[i] - cost[i];
            // If one couldn't get here,
            if (curr_tank < 0) {
                // Pick up the next station as the starting one.
                starting_station = i + 1;
                // Start with an empty tank.
                curr_tank = 0;
            }
        }
        return total_tank >= 0 ? starting_station : -1;
    }

    public int canCompleteCircuit_bruteforce(int[] gas, int[] cost) {
        for (int startIdx=0; startIdx<gas.length; startIdx++) {
            int steps = 0;
            int remainingGas = 0;
            boolean canContinue = true;
            while (steps < gas.length) {
                int idx = (startIdx + steps) % gas.length;
                if(gas[idx] + remainingGas < cost[idx]) {
                    canContinue = false;
                    break;
                } else {
                    remainingGas = remainingGas + gas[idx] - cost[idx];
                    steps++;
                }
            }
            if (canContinue) {
                return startIdx;
            }
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/super-washing-machines/
     * You have n super washing machines on a line. Initially, each washing machine has some dresses or is empty.
     *
     * For each move, you could choose any m (1 ≤ m ≤ n) washing machines, and pass one dress of each washing machine to one of its adjacent washing machines at the same time .
     *
     * Given an integer array representing the number of dresses in each washing machine from left to right on the line, you should find the minimum number of moves to make all the washing machines have the same number of dresses. If it is not possible to do it, return -1.
     *
     * Example1
     *
     * Input: [1,0,5]
     *
     * Output: 3
     *
     * Explanation:
     * 1st move:    1     0 <-- 5    =>    1     1     4
     * 2nd move:    1 <-- 1 <-- 4    =>    2     1     3
     * 3rd move:    2     1 <-- 3    =>    2     2     2
     * Example2
     *
     * Input: [0,3,0]
     *
     * Output: 2
     *
     * Explanation:
     * 1st move:    0 <-- 3     0    =>    1     2     0
     * 2nd move:    1     2 --> 0    =>    1     1     1
     * Example3
     *
     * Input: [0,2,0]
     *
     * Output: -1
     *
     * Explanation:
     * It's impossible to make all the three washing machines have the same number of dresses.
     * Note:
     * The range of n is [1, 10000].
     * The range of dresses number in a super washing machine is [0, 1e5].
     */
    public int findMinMoves(int[] machines) {
        int n = machines.length, dressTotal = 0;
        for (int m : machines) dressTotal += m;
        if (dressTotal % n != 0) return -1;

        int dressPerMachine = dressTotal / n;
        // Change the number of dresses in the machines to
        // the number of dresses to be removed from this machine
        // (could be negative)
        for (int i = 0; i < n; i++) machines[i] -= dressPerMachine;

        // currSum is a number of dresses to move at this point,
        // maxSum is a max number of dresses to move at this point or before,
        // m is number of dresses to move out from the current machine.
        int currSum = 0, maxSum = 0, tmpRes = 0, res = 0;
        for (int m : machines) {
            currSum += m;
            maxSum = Math.max(maxSum, Math.abs(currSum));
            tmpRes = Math.max(maxSum, m);
            res = Math.max(res, tmpRes);
        }
        return res;
    }

    public int findMinMoves_same(int[] machines) {
        int total = 0;
        for (int load : machines) {
            total += load;
        }
        if (total%machines.length != 0) {
            return -1;
        }
        int avg = total/machines.length, cnt = 0, max = 0;
        for (int load: machines) {
            int diff = load - avg;
            cnt += diff; //load - avg is "gain/lose"
            max = Math.max(Math.max(max, Math.abs(cnt)), diff);
        }
        return max;
    }
}
