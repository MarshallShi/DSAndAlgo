package dsandalgo.dfsbacktrack;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TheMazeExe {

    /**
     * https://leetcode.com/problems/the-maze/
     * There is a ball in a maze with empty spaces and walls.
     * The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
     * When the ball stops, it could choose the next direction.
     *
     * Given the ball's start position, the destination and the maze, determine whether the ball could stop at the destination.
     *
     * The maze is represented by a binary 2D array.
     * 1 means the wall and
     * 0 means the empty space.
     *
     * You may assume that the borders of the maze are all walls.
     *
     * The start and destination coordinates are represented by row and column indexes.
     *
     *
     *
     * Example 1:
     *
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 1 0 0
     * 0 0 0 0 0
     * 0 0 0 1 0
     * 1 1 0 1 1
     * 0 0 0 0 0
     *
     * Input 2: start coordinate (rowStart, colStart) = (0, 4)
     * Input 3: destination coordinate (rowDest, colDest) = (4, 4)
     *
     * Output: true
     *
     * Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.
     *
     * Example 2:
     *
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 1 0 0
     * 0 0 0 0 0
     * 0 0 0 1 0
     * 1 1 0 1 1
     * 0 0 0 0 0
     *
     * Input 2: start coordinate (rowStart, colStart) = (0, 4)
     * Input 3: destination coordinate (rowDest, colDest) = (3, 2)
     *
     * Output: false
     *
     * Explanation: There is no way for the ball to stop at the destination.
     *
     *
     *
     * Note:
     *
     * There is only one ball and one destination in the maze.
     * Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
     * The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
     * The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
     *
     * @param maze
     * @param start
     * @param destination
     * @return
     */
    int[][] dirs = new int[][]{ {-1, 0}, {1, 0}, {0, 1}, {0, -1} };

    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        return dfs(maze, start, destination, visited);
    }

    private boolean dfs(int[][] maze, int[] p, int[] destination, boolean[][] visited) {
        if (visited[p[0]][p[1]]) {
            return false;
        }
        if (p[0] == destination[0] && p[1] == destination[1]) {
            return true;
        }
        visited[p[0]][p[1]] = true;
        for (int i = 0; i < dirs.length; i++) {
            int[] d = dirs[i];
            int row = p[0];
            int col = p[1];
            while (isValid(maze, row + d[0], col + d[1])) {
                row += d[0];
                col += d[1];
            }
            if (dfs(maze, new int[]{ row, col }, destination, visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(int[][] maze, int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && maze[row][col] != 1;
    }

    /**
     * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up, down, left or right,
     * but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.
     *
     * Given the ball's start position, the destination and the maze, find the shortest distance for the ball to stop at the destination. The distance
     * is defined by the number of empty spaces traveled by the ball from the start position (excluded) to the destination (included).
     * If the ball cannot stop at the destination, return -1.
     *
     * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the borders of the maze are
     * all walls. The start and destination coordinates are represented by row and column indexes.
     *
     * Example 1:
     *
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 1 0 0
     * 0 0 0 0 0
     * 0 0 0 1 0
     * 1 1 0 1 1
     * 0 0 0 0 0
     *
     * Input 2: start coordinate (rowStart, colStart) = (0, 4)
     * Input 3: destination coordinate (rowDest, colDest) = (4, 4)
     *
     * Output: 12
     *
     * Explanation: One shortest way is : left -> down -> left -> down -> right -> down -> right.
     *              The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
     *
     * Example 2:
     *
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 1 0 0
     * 0 0 0 0 0
     * 0 0 0 1 0
     * 1 1 0 1 1
     * 0 0 0 0 0
     *
     * Input 2: start coordinate (rowStart, colStart) = (0, 4)
     * Input 3: destination coordinate (rowDest, colDest) = (3, 2)
     *
     * Output: -1
     *
     * Explanation: There is no way for the ball to stop at the destination.
     * Note:
     *
     * There is only one ball and one destination in the maze.
     * Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
     * The given maze does not contain border (like the red rectangle in the example pictures),
     * but you could assume the border of the maze are all walls.
     * The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
     */
    class Point {
        int x,y,l;
        public Point(int _x, int _y, int _l) {x=_x;y=_y;l=_l;}
    }
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int m = maze.length, n = maze[0].length;
        int[][] length=new int[m][n]; // record length
        for (int i=0; i<m*n; i++) {
            //Prefill with max value
            length[i/n][i%n] = Integer.MAX_VALUE;
        }
        int[][] dir=new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
        PriorityQueue<Point> q = new PriorityQueue<Point>((o1, o2)->o1.l-o2.l); // using priority queue
        q.offer(new Point(start[0], start[1], 0));
        while (!q.isEmpty()) {
            Point p = q.poll();
            if (length[p.x][p.y] <= p.l) {
                continue; // if we have already found a route shorter, skip this one.
            }
            length[p.x][p.y]=p.l;
            //Find the next valid point.
            for (int i=0; i<4; i++) {
                int xx = p.x, yy = p.y, l = p.l;
                while (xx>=0 && xx<m && yy>=0 && yy<n && maze[xx][yy]==0) {
                    xx = xx + dir[i][0];
                    yy = yy + dir[i][1];
                    l++;
                }
                xx = xx - dir[i][0];
                yy = yy - dir[i][1];
                l--;
                q.offer(new Point(xx, yy, l));
            }
        }
        return length[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : length[destination[0]][destination[1]];
    }

    public static void main(String[] args) {
        TheMazeExe exe = new TheMazeExe();
        int[][] maze = {{0,0,1,0,0},{0,0,0,0,0},{0,0,0,1,0},{1,1,0,1,1},{0,0,0,0,0}};
        int[] start = {0,4};
        int[] target = {4,4};
        System.out.println(exe.shortestDistance(maze, start, target));
    }
}
