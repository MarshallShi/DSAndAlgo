package dsandalgo.dfsbacktrack;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TheMazeExe {

    public static void main(String[] args) {
        TheMazeExe exe = new TheMazeExe();
        int[][] maze = {{0,0,0,0,0},{1,1,0,0,1},{0,0,0,0,0},{0,1,0,0,1},{0,1,0,0,0}};
        int[] ball = {4,3};
        int[] holl = {0,1};
        System.out.println(exe.findShortestWay(maze, ball, holl));
    }

    /**
     * https://leetcode.com/problems/the-maze-iii/
     *
     * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up (u), down (d), left (l) or right (r),
     * but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction. There is also a hole in this maze.
     * The ball will drop into the hole if it rolls on to the hole.
     *
     * Given the ball position, the hole position and the maze, find out how the ball could drop into the hole by moving the shortest distance.
     * The distance is defined by the number of empty spaces traveled by the ball from the start position (excluded) to the hole (included).
     * Output the moving directions by using 'u', 'd', 'l' and 'r'. Since there could be several different shortest ways, you should output the
     * lexicographically smallest way. If the ball cannot reach the hole, output "impossible".
     *
     * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the borders of the maze
     * are all walls. The ball and the hole coordinates are represented by row and column indexes.
     *
     * Example 1:
     *
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 0 0 0
     * 1 1 0 0 1
     * 0 0 0 0 0
     * 0 1 0 0 1
     * 0 1 0 0 0
     *
     * Input 2: ball coordinate (rowBall, colBall) = (4, 3)
     * Input 3: hole coordinate (rowHole, colHole) = (0, 1)
     *
     * Output: "lul"
     *
     * Explanation: There are two shortest ways for the ball to drop into the hole.
     * The first way is left -> up -> left, represented by "lul".
     * The second way is up -> left, represented by 'ul'.
     * Both ways have shortest distance 6, but the first way is lexicographically smaller because 'l' < 'u'. So the output is "lul".
     *
     * Example 2:
     *
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 0 0 0
     * 1 1 0 0 1
     * 0 0 0 0 0
     * 0 1 0 0 1
     * 0 1 0 0 0
     *
     * Input 2: ball coordinate (rowBall, colBall) = (4, 3)
     * Input 3: hole coordinate (rowHole, colHole) = (3, 0)
     *
     * Output: "impossible"
     *
     * Explanation: The ball cannot reach the hole.
     *
     * Note:
     *
     * There is only one ball and one hole in the maze.
     * Both the ball and hole exist on an empty space, and they will not be at the same position initially.
     * The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
     * The maze contains at least 2 empty spaces, and the width and the height of the maze won't exceed 30.
     */

    class Point implements Comparable<Point> {
        int x, y, l;
        String s;

        public Point(int _x, int _y) {
            x = _x;
            y = _y;
            l = Integer.MAX_VALUE;
            s = "";
        }

        public Point(int _x, int _y, int _l, String _s) {
            x = _x;
            y = _y;
            l = _l;
            s = _s;
        }

        public int compareTo(Point p) {
            //based on question, pick shorted length first
            //then the direction in lexicographical order.
            return l == p.l ? s.compareTo(p.s) : l - p.l;
        }
    }

    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length, n = maze[0].length;
        Point[][] points = new Point[m][n];
        for (int i = 0; i < m * n; i++) {
            points[i / n][i % n] = new Point(i / n, i % n);
        }
        int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        String[] ds = new String[]{"u", "r", "d", "l"};
        PriorityQueue<Point> pq = new PriorityQueue<>(); // using priority queue
        pq.offer(new Point(ball[0], ball[1], 0, ""));
        while (!pq.isEmpty()) {
            Point p = pq.poll();
            //On this point, check if we need to go through it again.
            if (points[p.x][p.y].compareTo(p) <= 0) {
                continue;
            }
            points[p.x][p.y] = p;
            for (int i = 0; i < 4; i++) {
                int xx = p.x, yy = p.y, l = p.l;
                while (xx >= 0 && xx < m && yy >= 0 && yy < n && maze[xx][yy] == 0 && (xx != hole[0] || yy != hole[1])) {
                    xx += dir[i][0];
                    yy += dir[i][1];
                    l++;
                }
                // check the hole
                if (xx != hole[0] || yy != hole[1]) {
                    xx -= dir[i][0];
                    yy -= dir[i][1];
                    l--;
                }
                pq.offer(new Point(xx, yy, l, p.s + ds[i]));
            }
        }
        return points[hole[0]][hole[1]].l == Integer.MAX_VALUE ? "impossible" : points[hole[0]][hole[1]].s;
    }

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
        return hasPathDFS(maze, start, destination, visited);
    }

    private boolean hasPathDFS(int[][] maze, int[] p, int[] destination, boolean[][] visited) {
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
            if (hasPathDFS(maze, new int[]{ row, col }, destination, visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(int[][] maze, int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && maze[row][col] != 1;
    }

    public boolean hasPath_BFS(int[][] maze, int[] start, int[] destination) {
        int[][] dirs = new int[][]{ {-1, 0}, {1, 0}, {0, 1}, {0, -1} };
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Queue<int[]> q = new LinkedList<>();
        q.offer(start);
        visited[start[0]][start[1]] = true;
        while (!q.isEmpty()) {
            int s = q.size();
            for (int i=0; i<s; i++) {
                int[] pos = q.poll();
                for (int[] dir : dirs) {
                    int row = pos[0];
                    int col = pos[1];
                    while (isValid(maze, row + dir[0], col + dir[1])) {
                        row += dir[0];
                        col += dir[1];
                    }
                    if (row == destination[0] && col == destination[1]) {
                        return true;
                    }
                    if (!visited[row][col]) {
                        q.offer(new int[]{row, col});
                        visited[row][col] = true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * https://leetcode.com/problems/the-maze-ii/
     *
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
    class Point1 {
        int x, y, l;

        public Point1(int _x, int _y, int _l) {
            x = _x;
            y = _y;
            l = _l;
        }
    }

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int m = maze.length, n = maze[0].length;
        int[][] length = new int[m][n]; // record length
        for (int i = 0; i < m * n; i++) {
            //Prefill with max value
            length[i / n][i % n] = Integer.MAX_VALUE;
        }
        int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        PriorityQueue<Point1> q = new PriorityQueue<Point1>((o1, o2) -> o1.l - o2.l); // using priority queue
        q.offer(new Point1(start[0], start[1], 0));
        while (!q.isEmpty()) {
            Point1 p = q.poll();
            // if we have already found a route shorter, skip this one.
            if (length[p.x][p.y] <= p.l) {
                continue;
            }
            length[p.x][p.y] = p.l;

            //Find the next valid point.
            for (int i = 0; i < 4; i++) {
                int xx = p.x, yy = p.y, l = p.l;
                while (xx >= 0 && xx < m && yy >= 0 && yy < n && maze[xx][yy] == 0) {
                    xx = xx + dir[i][0];
                    yy = yy + dir[i][1];
                    l++;
                }
                xx = xx - dir[i][0];
                yy = yy - dir[i][1];
                l--;
                q.offer(new Point1(xx, yy, l));
            }
        }
        return length[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : length[destination[0]][destination[1]];
    }

}
