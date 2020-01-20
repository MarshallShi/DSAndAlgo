package dsandalgo.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GreedyExe{

    public static void main(String[] args) {
        GreedyExe exe = new GreedyExe();

        int[] values = {5,4,3,2,1};
        int[] labels = {1,3,3,3,2};
        System.out.println(exe.largestValsFromLabels(values, labels, 3, 2));
    }

    /**
     * https://leetcode.com/problems/largest-values-from-labels/
     *
     * We have a set of items: the i-th item has value values[i] and label labels[i].
     *
     * Then, we choose a subset S of these items, such that:
     *
     * |S| <= num_wanted
     * For every label L, the number of items in S with label L is <= use_limit.
     * Return the largest possible sum of the subset S.
     *
     * Example 1:
     *
     * Input: values = [5,4,3,2,1], labels = [1,1,2,2,3], num_wanted = 3, use_limit = 1
     * Output: 9
     * Explanation: The subset chosen is the first, third, and fifth item
     *
     * Example 2:
     *
     * Input: values = [5,4,3,2,1], labels = [1,3,3,3,2], num_wanted = 3, use_limit = 2
     * Output: 12
     * Explanation: The subset chosen is the first, second, and third item.
     *
     * Example 3:
     *
     * Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 1
     * Output: 16
     * Explanation: The subset chosen is the first and fourth item.
     *
     * Example 4:
     *
     * Input: values = [9,8,8,7,6], labels = [0,0,0,1,1], num_wanted = 3, use_limit = 2
     * Output: 24
     * Explanation: The subset chosen is the first, second, and fourth item.
     *
     *
     * Note:
     *
     * 1 <= values.length == labels.length <= 20000
     * 0 <= values[i], labels[i] <= 20000
     * 1 <= num_wanted, use_limit <= values.length
     * @param values
     * @param labels
     * @param num_wanted
     * @param use_limit
     * @return
     */
    public int largestValsFromLabels(int[] values, int[] labels, int num_wanted, int use_limit) {
        int[][] valuesAndLabels = new int[values.length][2];
        for (int i=0; i<values.length; i++) {
            valuesAndLabels[i][0] = values[i];
            valuesAndLabels[i][1] = labels[i];
        }
        Arrays.sort(valuesAndLabels, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });
        int ret = 0;
        Map<Integer, Integer> map = new HashMap<Integer,Integer>();
        int j = 0;
        for (int i=0; i<num_wanted && j<values.length;) {
            map.putIfAbsent(valuesAndLabels[j][1], 0);
            if (map.get(valuesAndLabels[j][1]) < use_limit) {
                map.put(valuesAndLabels[j][1], map.get(valuesAndLabels[j][1]) + 1);
                ret = ret + valuesAndLabels[j][0];
                i++;
            }
            j++;
        }
        return ret;
    }

    /**
     * https://leetcode.com/problems/walking-robot-simulation/
     * A robot on an infinite grid starts at point (0, 0) and faces north.  The robot can receive one of three possible types of commands:
     *
     * -2: turn left 90 degrees
     * -1: turn right 90 degrees
     * 1 <= x <= 9: move forward x units
     * Some of the grid squares are obstacles.
     *
     * The i-th obstacle is at grid point (obstacles[i][0], obstacles[i][1])
     *
     * If the robot would try to move onto them, the robot stays on the previous grid square instead (but still continues following the rest of the route.)
     *
     * Return the square of the maximum Euclidean distance that the robot will be from the origin.
     *
     *
     *
     * Example 1:
     *
     * Input: commands = [4,-1,3], obstacles = []
     * Output: 25
     * Explanation: robot will go to (3, 4)
     * Example 2:
     *
     * Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
     * Output: 65
     * Explanation: robot will be stuck at (1, 4) before turning left and going to (1, 8)
     *
     *
     * Note:
     *
     * 0 <= commands.length <= 10000
     * 0 <= obstacles.length <= 10000
     * -30000 <= obstacle[i][0] <= 30000
     * -30000 <= obstacle[i][1] <= 30000
     * The answer is guaranteed to be less than 2 ^ 31.
     *
     * @param commands
     * @param obstacles
     * @return
     */

    public int robotSim(int[] commands, int[][] obstacles) {
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        Set<String> obstaclesRowToCols = new HashSet<String>();
        for (int[] obstacle : obstacles) {
            obstaclesRowToCols.add(obstacle[0] + " " + obstacle[1]);
        }

        /**
         * How do we represent absolute orientations given only relative turning directions(i.e., left or right)?
         *
         * We define direction indicates the absolute orientation as below:
         *
         * North, direction = 0, directions[direction] = {0, 1}
         * East,  direction = 1, directions[direction] = {1, 0}
         * South, direction = 2, directions[direction] = {0, -1}
         * West,  direction = 3, directions[direction] = {-1, 0}
         *
         * direction will increase by one when we turn right,
         * and will decrease by one when we turn left.
         */
        int x = 0, y = 0, direction = 1, maxDistSquare = 0;
        for (int i = 0; i < commands.length; i++) {
            if (commands[i] == -2) {
                direction--;
                if (direction < 0) {
                    direction += 4;
                }
            } else if (commands[i] == -1) {
                direction++;
                direction %= 4;
            } else {
                int step = 0;
                while (step < commands[i] && (!obstaclesRowToCols.contains((x + directions[direction][0]) + " " + (y + directions[direction][1])))) {
                    x += directions[direction][0];
                    y += directions[direction][1];
                    step++;
                }
            }
            maxDistSquare = Math.max(maxDistSquare, x * x + y * y);
        }

        return maxDistSquare;
    }

    /**
     * Input: [5,5,10,10,20]
     * Output: false
     * Explanation:
     * From the first two customers in order, we collect two $5 bills.
     * For the next two customers in order, we collect a $10 bill and give back a $5 bill.
     * For the last customer, we can't give change of $15 back because we only have two $10 bills.
     * Since not every customer received correct change, the answer is false.
     *
     * https://leetcode.com/problems/lemonade-change/
     * @param bills
     * @return
     */
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0, twenty = 0;
        for (int i=0; i<bills.length; i++) {
            if (bills[i] == 5) {
                five++;
            } else {
                if (bills[i] == 10) {
                    five--;
                    ten++;
                } else {
                    if (bills[i] == 20) {
                        if (ten > 0) {
                            ten--;
                            five--;
                        } else {
                            five = five - 3;
                        }
                    }
                }
            }
            if (five < 0) {
                return false;
            }
        }
        return true;
    }
}
