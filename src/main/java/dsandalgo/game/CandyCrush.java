package dsandalgo.game;

public class CandyCrush {


    /**
     * https://leetcode.com/problems/candy-crush/
     *
     * This question is about implementing a basic elimination algorithm for Candy Crush.
     *
     * Given a 2D integer array board representing the grid of candy, different positive integers board[i][j] represent different types of candies. A value of board[i][j] = 0 represents that the cell at position (i, j) is empty. The given board represents the state of the game following the player's move. Now, you need to restore the board to a stable state by crushing candies according to the following rules:
     *
     * If three or more candies of the same type are adjacent vertically or horizontally, "crush" them all at the same time - these positions become empty.
     * After crushing all candies simultaneously, if an empty space on the board has candies on top of itself, then these candies will drop until they hit a candy or bottom at the same time. (No new candies will drop outside the top boundary.)
     * After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
     * If there does not exist more candies that can be crushed (ie. the board is stable), then return the current board.
     * You need to perform the above rules until the board becomes stable, then return the current board.
     *
     * @param board
     * @return
     */
    public int[][] candyCrush(int[][] board) {
        int n = board.length;
        int m = board[0].length;
        boolean crash = true;
        //key is how to mark the cell once we found them, here is to mark it as negative of original value.
        //search the grid after each iteration, to see if there is more candy to crush.
        while (crash) {
            crash = false;
            //for column search.
            for (int j = 0; j < m; j++) {
                for (int i = 0; i < n - 2; i++) {
                    if (board[i][j] == 0) {
                        continue;
                    }
                    if (Math.abs(board[i][j]) == Math.abs(board[i + 1][j]) && Math.abs(board[i][j]) == Math.abs(board[i + 2][j])) {
                        crash = true;
                        int val = Math.abs(board[i][j]);
                        while (i < n && Math.abs(board[i][j]) == val) {
                            board[i][j] = -val;
                            i++;
                        }
                        i--;
                    }
                }
            }
            //for row search
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m - 2; j++) {
                    if (board[i][j] == 0) {
                        continue;
                    }
                    if (Math.abs(board[i][j]) == Math.abs(board[i][j + 1]) && Math.abs(board[i][j + 1]) == Math.abs(board[i][j + 2])) {
                        crash = true;
                        int val = Math.abs(board[i][j]);
                        while (j < m && Math.abs(board[i][j]) == val) {
                            board[i][j] = -val;
                            j++;
                        }
                        j--;
                    }
                }
            }
            //to move cells.
            for (int j = 0; j < m; j++) {
                int ind = n - 1;
                for (int i = n - 1; i >= 0; i--) {
                    if (board[i][j] > 0) {
                        board[ind--][j] = board[i][j];
                    }
                }
                while (ind >= 0) {
                    board[ind--][j] = 0;
                }
            }
        }
        return board;
    }
}
