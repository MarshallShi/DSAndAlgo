package dsandalgo.othertree;

/**
 * https://leetcode.com/problems/range-sum-query-2d-mutable/
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1)
 * and lower right corner (row2, col2).
 *
 * Range Sum Query 2D
 * The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.
 *
 * Example:
 * Given matrix = [
 *   [3, 0, 1, 4, 2],
 *   [5, 6, 3, 2, 1],
 *   [1, 2, 0, 1, 5],
 *   [4, 1, 0, 1, 7],
 *   [1, 0, 3, 0, 5]
 * ]
 *
 * sumRegion(2, 1, 4, 3) -> 8
 * update(3, 2, 2)
 * sumRegion(2, 1, 4, 3) -> 10
 * Note:
 * The matrix is only modifiable by the update function.
 * You may assume the number of calls to update and sumRegion function is distributed evenly.
 * You may assume that row1 ≤ row2 and col1 ≤ col2.
 *
 */
public class NumMatrix {

    class Node{
        Node n11, n12, n21, n22;
        int sum;
        int row1, col1, row2, col2;
        public Node(int row1, int col1, int row2, int col2){
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }
    }

    Node root;
    int M, N;
    int[][] matrix;

    public NumMatrix(int[][] matrix) {
        if (matrix==null || matrix.length==0 || matrix[0].length==0) return;
        this.M = matrix.length;
        this.N = matrix[0].length;
        this.matrix = matrix;
        root = build(0,0,M-1,N-1);
    }

    private Node build(int row1, int col1, int row2, int col2){
        if (row1>row2 || col1>col2) return null;
        Node node = new Node(row1, col1, row2, col2);
        if (row1==row2 && col1 == col2){
            node.sum = matrix[row1][col1];
            return node;
        }
        int midRow = row1 + (row2 - row1)/2;
        int midCol = col1 + (col2 - col1) /2;
        node.n11 = build(row1, col1, midRow, midCol);
        node.n12 = build(row1, midCol+1, midRow, col2);
        node.n21 = build(midRow+1, col1, row2, midCol);
        node.n22 = build(midRow+1, midCol+1, row2, col2);
        node.sum += node.n11 == null? 0: node.n11.sum;
        node.sum += node.n12 == null? 0: node.n12.sum;
        node.sum += node.n21 == null? 0: node.n21.sum;
        node.sum += node.n22 == null? 0: node.n22.sum;
        return node;
    }

    public void update(int row, int col, int val) {
        if (M == 0 || N == 0) return;
        update(root, row, col, val-matrix[row][col]);
        matrix[row][col] = val;
    }

    private void update(Node node, int row, int col, int delta){
        if (node.row1 == node.row2 && node.col1 == node.col2) {
            node.sum += delta;
            return;
        }
        int midRow = node.row1 + (node.row2 - node.row1)/2;
        int midCol = node.col1 + (node.col2 - node.col1) /2;
        if (row <= midRow){//top
            if (col<=midCol) {
                update(node.n11, row, col, delta); //top-left
            } else {
                update(node.n12, row, col, delta); //top-right
            }
        } else {//bottom
            if (col <= midCol) {
                update(node.n21, row, col, delta); //bottom-left
            } else {
                update(node.n22, row, col, delta); //bottom-right
            }
        }
        node.sum += delta;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (M == 0 || N == 0) return 0;
        return sumRegion(root, row1, col1, row2, col2);
    }

    private int sumRegion(Node node, int row1, int col1, int row2, int col2) {
        if (node == null) return 0;
        if (node.row1>=row1 && node.row2<=row2 && node.col1>=col1 && node.col2<=col2) {
            return node.sum;
        }
        int res = 0;
        if (node.n11 != null && row1 <= node.n11.row2 && col1<= node.n11.col2) {
            res+= sumRegion(node.n11, row1, col1, row2, col2);
        }
        if (node.n12 != null && row1 <= node.n12.row2 && col2>= node.n12.col1) {
            res+= sumRegion(node.n12, row1, col1, row2, col2);
        }
        if (node.n21 != null && row2 >= node.n21.row1 && col1<= node.n21.col2) {
            res+= sumRegion(node.n21, row1, col1, row2, col2);
        }
        if (node.n22 != null && row2 >= node.n22.row1 && col2>= node.n22.col1) {
            res+= sumRegion(node.n22, row1, col1, row2, col2);
        }
        return res;
    }
}
