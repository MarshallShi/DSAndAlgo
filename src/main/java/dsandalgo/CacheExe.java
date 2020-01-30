package dsandalgo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class CacheExe {

    /**
     * https://leetcode.com/problems/prison-cells-after-n-days/
     *
     *
     * @param cells
     * @param N
     * @return
     */
    public int[] prisonAfterNDays(int[] cells, int N) {
        //For the TLE, as the cells are 8 digits 0 and 1, there will be looping happening if N is too big, hence caching and also moding N.
        Map<Integer,int[]> map = new HashMap<Integer,int[]>();
        boolean isLooping = false;

        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });

        int headKey = 0;
        for (int i=1; i<=N; i++) {
            int currKey = 0;
            for (int k=0; k<8; k++) {
                currKey = currKey * 2 + cells[k];
            }
            if (map.containsKey(currKey)) {
                if (!isLooping) {
                    isLooping = true;
                    headKey = currKey;
                    N = N - i;
                    i = 0;
                } else if (currKey == headKey && isLooping) {
                    N = (N-i) % (i);
                    i = 0;
                }
                cells = map.get(currKey);
            } else {
                int[] nextCells = new int[cells.length];
                for (int j=1; j<7; j++){
                    if (cells[j-1] == cells[j+1]) {
                        nextCells[j] = 1;
                    }
                }
                map.put(currKey, nextCells);
                cells = nextCells;
            }
        }
        return cells;
    }

    public int[] prisonAfterNDays_TLE(int[] cells, int N) {
        int[] ret = new int[cells.length];
        for (int j=0; j<N; j++) {
            ret = new int[cells.length];
            for (int i=0; i<cells.length; i++) {
                if (i == 0 || i == cells.length - 1) {
                    if (cells[i] == 1) {
                        ret[i] = 0;
                    }
                } else {
                    ret[i] = cells[i-1] == cells[i+1] ? 1 : 0;
                }
            }
            cells = ret;
        }
        return ret;
    }
}
