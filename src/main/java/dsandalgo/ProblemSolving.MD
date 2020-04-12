
Optimal Account Balancing
https://leetcode.com/problems/optimal-account-balancing/
(1) How to analyse the question?
Each person will have income and outgoing, so track the net, if we want to settle one person, pay him or he should pay someone.
Use different sign for different type of settle.
(2) Rule out all the settle amount 0 person, as they will not be participate in the final settle transactions.
(3) Get the list of amount, a list of positive and negative amount. Let's try all possibility and get the min value.
This is a typical DFS backtracking problem.

    public int minTransfers(int[][] transactions) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] t : transactions) {
            map.put(t[0], map.getOrDefault(t[0], 0) + t[2]);
            map.put(t[1], map.getOrDefault(t[1], 0) - t[2]);
        }
        List<Integer> list = new ArrayList();
        for (int v : map.values()) {
            if (v != 0) {
                list.add(v);
            }
        }
        return settleBacktrack(0, list);
    }

    private int settleBacktrack(int pos, List<Integer> list) {
        if (pos == list.size()) {
            return 0;
        }
        int cur = list.get(pos);
        if (cur == 0) {
            return settleBacktrack(pos + 1, list);
        }
        int min = Integer.MAX_VALUE;
        for (int i = pos + 1; i < list.size(); i++) {
            int next = list.get(i);
            if (cur * next < 0) {
                list.set(i, cur + next);
                min = Math.min(min, 1 + settleBacktrack(pos + 1, list));
                list.set(i, next);
                if (cur + next == 0) break;
            }
        }
        return min;
    }

Trapping Rain Water II    
https://leetcode.com/problems/trapping-rain-water-ii/
(Priority Queue + BFS)
To hold any sort of water over a grid element, you need one of the elements inside the grid to lie at a lower point and the lowest boundary of 
this low point dictates how much water it can hold. So we go ahead and put the boundary of the grid in a priority queue.
    
    class Cell {
        int row;
        int col;
        int height;

        public Cell(int row, int col, int height) {
            this.row = row;
            this.col = col;
            this.height = height;
        }
    }

    //Intuition: the border will determine the amout, so keep borders as boundary in the Priority Queue, and keep updating it.
    //Start from the boundary, BFS the grid
    public int trapRainWater(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return 0;
        }
        
        PriorityQueue<Cell> pq = new PriorityQueue<>(new Comparator<Cell>() {
            public int compare(Cell a, Cell b) {
                return a.height - b.height;
            }
        });

        int m = heights.length;
        int n = heights[0].length;
        boolean[][] visited = new boolean[m][n];

        // Initially, add all the Cells which are on borders to the PQ.
        for (int i = 0; i < m; i++) {
            visited[i][0] = true;
            visited[i][n - 1] = true;
            pq.offer(new Cell(i, 0, heights[i][0]));
            pq.offer(new Cell(i, n - 1, heights[i][n - 1]));
        }

        for (int i = 0; i < n; i++) {
            visited[0][i] = true;
            visited[m - 1][i] = true;
            pq.offer(new Cell(0, i, heights[0][i]));
            pq.offer(new Cell(m - 1, i, heights[m - 1][i]));
        }

        // from the borders, pick the shortest cell visited and check its neighbors:
        // if the neighbor is shorter, collect the water it can trap.
        // if the neighbor is higher, no water to collect.
        // in both cases, add the higher into the border cells in the PQ.
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int res = 0;
        while (!pq.isEmpty()) {
            Cell cur = pq.poll();
            for (int[] dir : dirs) {
                int nx = cur.row + dir[0];
                int ny = cur.col + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    int nextHeight = heights[nx][ny];
                    if (cur.height > nextHeight) {
                        //if the neighbor is shorter, collect the water it can trap.
                        res += cur.height - nextHeight;
                        pq.offer(new Cell(nx, ny, cur.height));
                    } else {
                        //if the neighbor is higher, no water to collect.
                        pq.offer(new Cell(nx, ny, nextHeight));
                    }
                }
            }
        }
        return res;
    }
    
Find the celebrity
https://leetcode.com/problems/find-the-celebrity/
Only a knows b, b can't know a. So once we have a result of knows(), we rule out a, and b will be candidate.
Two pass solution: 1. go through each person to find the candidate; 2. based on candidate, make sure there is no other such candidate.

    public int findCelebrity(int n) {
        int candidate = 0;
        // one pass to find the potential candidate, it has to come from the know relation.
        // a knows b, a is not but b is potential, reverse thinking
        for (int i = 1; i < n; i++) {
            if (knows(candidate, i)) {
                candidate = i;
            }
        }
        //make sure there is no other i have the same celebrity feature.
        for (int i = 0; i < n; i++) {
            if (i != candidate && (knows(candidate, i) || !knows(i, candidate))) {
                return -1;
            }
        }
        return candidate;
    }
    
