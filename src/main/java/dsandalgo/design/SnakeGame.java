package dsandalgo.design;

import java.util.LinkedList;

/**
 * https://leetcode.com/problems/design-snake-game/
 *
 * Design a Snake game that is played on a device with screen size = width x height. Play the game online if you are not familiar with the game.
 *
 * The snake is initially positioned at the top left corner (0,0) with length = 1 unit.
 *
 * You are given a list of food's positions in row-column order. When a snake eats the food, its length and the game's score both increase by 1.
 *
 * Each food appears one by one on the screen. For example, the second food will not appear until the first food was eaten by the snake.
 *
 * When a food does appear on the screen, it is guaranteed that it will not appear on a block occupied by the snake.
 *
 * Example:
 *
 * Given width = 3, height = 2, and food = [[1,2],[0,1]].
 *
 * Snake snake = new Snake(width, height, food);
 *
 * Initially the snake appears at position (0,0) and the food at (1,2).
 *
 * |S| | |
 * | | |F|
 *
 * snake.move("R"); -> Returns 0
 *
 * | |S| |
 * | | |F|
 *
 * snake.move("D"); -> Returns 0
 *
 * | | | |
 * | |S|F|
 *
 * snake.move("R"); -> Returns 1 (Snake eats the first food and right after that, the second food appears at (0,1) )
 *
 * | |F| |
 * | |S|S|
 *
 * snake.move("U"); -> Returns 1
 *
 * | |F|S|
 * | | |S|
 *
 * snake.move("L"); -> Returns 2 (Snake eats the second food)
 *
 * | |S|S|
 * | | |S|
 *
 * snake.move("U"); -> Returns -1 (Game over because snake collides with border)
 */
public class SnakeGame {

    int width;
    int height;
    int[][] food;

    int score = 0;
    Point cur;
    LinkedList<Point> snakeBody;

    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width=width;
        this.height=height;
        this.food=food;

        this.score =0;
        this.snakeBody = new LinkedList<>();
        snakeBody.addFirst(new Point(0,0));
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    /**
     * Trick: move the tail and add to the new head, if there is no food found, if food found, return the tail.
     * So we don't need to move every cell as part of the move!!!!!!
     */
    public int move(String direction) {
        // the new head is based on the current head
        // the tail is removed, but can be returned back if food is found
        Point head = snakeBody.getFirst();
        Point newHead = new Point(head.i, head.j);
        Point tail = snakeBody.removeLast();
        if (direction.equals("U")) {
            newHead.i--;
        } else {
            if (direction.equals("D")) {
                newHead.i++;
            } else {
                if (direction.equals("L")) {
                    newHead.j--;
                } else {
                    newHead.j++;
                }
            }
        }
        if (newHead.i < 0 || newHead.i == height || newHead.j < 0 || newHead.j == width || snakeContainPoint(newHead)) {
            return -1;
        }
        snakeBody.addFirst(newHead);
        if (score <food.length && food[score][0] == newHead.i && food[score][1] == newHead.j){
            snakeBody.addLast(tail); // return tail back
            score++;
        }
        return score;
    }

    private boolean snakeContainPoint(Point p){
        for (Point existingP : snakeBody) {
            if (existingP.isEqual(p)) {
                return true;
            }
        }
        return false;
    }

    class Point{
        int i;
        int j;
        Point(int i, int j){
            this.i=i;
            this.j=j;
        }
        boolean isEqual(Point _p){
            return _p.i == this.i && _p.j == this.j;
        }
    }

    public static void main(String[] args) {
        int[][] food = {{2,0},{0,0},{0,2},{0,1},{2,2},{0,1}};
        SnakeGame sg = new SnakeGame(3, 3, food);
        System.out.print(sg.move("D") + " ");
        System.out.print(sg.move("D") + " ");
        System.out.print(sg.move("R") + " ");
        System.out.print(sg.move("U") + " ");
        System.out.print(sg.move("U") + " ");
        System.out.print(sg.move("L") + " ");
        System.out.print(sg.move("D") + " ");
        System.out.print(sg.move("R") + " ");
        System.out.print(sg.move("R") + " ");
        System.out.print(sg.move("U") + " ");
        System.out.print(sg.move("L") + " ");
        System.out.print(sg.move("L") + " ");
        System.out.print(sg.move("D") + " ");
        System.out.print(sg.move("R") + " ");
        System.out.print(sg.move("U") + " ");
        //[null,0,1,1,1,1,2,2,2,2,3,4,4,4,4,-1]
    }
}
