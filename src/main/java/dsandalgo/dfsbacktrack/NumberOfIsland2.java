package dsandalgo.dfsbacktrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode.com/problems/number-of-distinct-islands-ii/
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.)
 * You may assume all four edges of the grid are surrounded by water.
 *
 * Count the number of distinct islands. An island is considered to be the same as another if they have the same shape, or have the same shape after rotation
 * (90, 180, or 270 degrees only) or reflection (left/right direction or up/down direction).
 *
 * Example 1:
 * 11000
 * 10000
 * 00001
 * 00011
 * Given the above grid map, return 1.
 *
 * Notice that:
 * 11
 * 1
 * and
 *  1
 * 11
 * are considered same island shapes. Because if we make a 180 degrees clockwise rotation on the first island, then two islands will have the same shapes.
 * Example 2:
 * 11100
 * 10001
 * 01001
 * 01110
 * Given the above grid map, return 2.
 *
 * Here are the two distinct islands:
 * 111
 * 1
 * and
 * 1
 * 1
 *
 * Notice that:
 * 111
 * 1
 * and
 * 1
 * 111
 * are considered same island shapes. Because if we flip the first array in the up/down direction, then they have the same shapes.
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class NumberOfIsland2 {

    int[][] r90  = {{0,-1}, {1,0}};//rotate 90 degrees
    int[][] r180 = {{-1,0}, {0,-1}};
    int[][] r270 = {{0,1}, {-1,0}};
    int[][] updownR = {{1,0},{0,-1}};//up-down mirror
    int[][] leftRightR = {{-1,0},{0,1}};//left right mirror
    int[][] mirror45 ={{0,1},{1,0}};
    int[][] mirror135 ={{0,-1},{-1,0}};
    int[][][] transforms = {r90,r180,r270,updownR,leftRightR,mirror45,mirror135};
    int[][] dir = {{0,-1},{0,1},{-1, 0},{1, 0}};
    int m =0, n =0;
    public int numDistinctIslands2(int[][] grid) {
        if(grid==null || grid.length==0 || grid[0]==null || grid[0].length==0) {
            return 0;
        }
        m = grid.length;
        n = grid[0].length;
        Set<String> allIslands = new HashSet<>();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    List<int[]> island = new ArrayList<>();
                    buildIsland(grid, i, j, island);
                    String normalString = normalizeIsland(island);
                    allIslands.add(normalString);
                }
            }
        }
        return allIslands.size();
    }

    private void buildIsland(int[][] grid, int i, int j, List<int[]> island){
        island.add(new int[]{i,j});
        grid[i][j] = 0;
        for(int k=0;k<4;k++){
            int ni = i+dir[k][0];
            int nj = j+dir[k][1];
            if(ni<0 || ni>=m || nj<0 || nj>=n || grid[ni][nj] ==0) continue;
            buildIsland(grid, ni, nj, island);
        }
    }

    private String normalizeIsland(List<int[]> island){
        List<String> codedIsland = new ArrayList<>();
        codedIsland.add(encodeIsland(island));
        for(int[][] transform: transforms){
            List<int[]> newIsland = new ArrayList<>();
            for(int[] cell : island){
                newIsland.add(transform(transform, cell));
            }

            codedIsland.add(encodeIsland(newIsland));
        }
        Collections.sort(codedIsland);
        return codedIsland.get(0);
    }

    private int[] transform(int[][] matrix, int[] point){
        int[] res = new int[matrix.length];
        for(int i = 0;i<matrix.length;i++){
            for(int j =0; j< matrix[i].length;j++){
                res[i]+= (matrix[i][j]*point[j]);
            }
        }
        return res;
    }

    String encodeIsland(List<int[]> island){
        Collections.sort(island, (a,b)->{//sort the cells from up->down and left->right
            if (a[0]==b[0]) {
                return a[1]-b[1];
            } else {
                return a[0]-b[0];
            }
        });
        StringBuilder sb = new StringBuilder();
        int anchorX = island.get(0)[0];
        int anchorY = island.get(0)[1];
        for (int[] cell: island){
            sb.append(cell[0]-anchorX).append(":").append(cell[1]-anchorY).append("_");//"x:y_"
        }
        return sb.toString();
    }
}
