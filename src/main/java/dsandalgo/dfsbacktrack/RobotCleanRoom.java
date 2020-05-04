package dsandalgo.dfsbacktrack;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/robot-room-cleaner/
 *
 * Given a robot cleaner in a room modeled as a grid.
 *
 * Each cell in the grid can be empty or blocked.
 *
 * The robot cleaner with 4 given APIs can move forward, turn left or turn right. Each turn it made is 90 degrees.
 *
 * When it tries to move into a blocked cell, its bumper sensor detects the obstacle and it stays on the current cell.
 *
 * Design an algorithm to clean the entire room using only the 4 given APIs shown below.
 *
 * interface Robot {
 *   // returns true if next cell is open and robot moves into the cell.
 *   // returns false if next cell is obstacle and robot stays on the current cell.
 *   boolean move();
 *
 *   // Robot will stay on the same cell after calling turnLeft/turnRight.
 *   // Each turn will be 90 degrees.
 *   void turnLeft();
 *   void turnRight();
 *
 *   // Clean the current cell.
 *   void clean();
 * }
 * Example:
 *
 * Input:
 * room = [
 *   [1,1,1,1,1,0,1,1],
 *   [1,1,1,1,1,0,1,1],
 *   [1,0,1,1,1,1,1,1],
 *   [0,0,0,1,0,0,0,0],
 *   [1,1,1,1,1,1,1,1]
 * ],
 * row = 1,
 * col = 3
 *
 * Explanation:
 * All grids in the room are marked by either 0 or 1.
 * 0 means the cell is blocked, while 1 means the cell is accessible.
 * The robot initially starts at the position of row=1, col=3.
 * From the top left corner, its position is one row below and three columns right.
 * Notes:
 *
 * The input is only given to initialize the room and the robot's position internally. You must solve this problem "blindfolded".
 * In other words, you must control the robot using only the mentioned 4 APIs, without knowing the room layout and the initial robot's position.
 * The robot's initial position will always be in an accessible cell.
 * The initial direction of the robot will be facing up.
 * All accessible cells are connected, which means the all cells marked as 1 will be accessible by the robot.
 * Assume all four edges of the grid are all surrounded by wall.
 */
public class RobotCleanRoom {

    interface Robot {
        public boolean move();

        public void turnLeft();

        public void turnRight();

        public void clean();
    }

    public void cleanRoom(Robot robot) {
        Set<String> visited = new HashSet<>();
        cleanRoomDFS(robot, visited, 0, 0, 0);
    }

    int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private void cleanRoomDFS(Robot robot, Set<String> visited, int x, int y, int arrow) {
        String path = x + "-" + y;
        if (visited.contains(path)) {
            return;
        }
        visited.add(path);
        robot.clean();
        //explore the four direction each time.
        for (int i = 0; i < 4; i++) {
            //if move is successful, we can clean and backtrack.
            if (robot.move()) {
                //go all the way till cannot move, then back one step
                int nx = x + dir[arrow][0];
                int ny = y + dir[arrow][1];
                //recursively clean the next position
                cleanRoomDFS(robot, visited, nx, ny, arrow);
                //back to previous position.
                robot.turnLeft();
                robot.turnLeft();
                robot.move();
                robot.turnLeft();
                robot.turnLeft();
            }
            //Move to next direction using API.
            robot.turnRight();
            //Also track the move in the internal state
            arrow = (arrow + 1) % 4;
        }
    }

}
