
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
    
Remove Duplicate Letters
https://leetcode.com/problems/remove-duplicate-letters/
Tricky problem. These two questions are the same in essence. Both need the tricky to apply the monotone stack, also need a visited or inuse array
to track if a char is already seen before.

    public String removeDuplicateLetters(String sr) {
        int[] res = new int[26]; //will contain number of occurences of character (i+'a')
        boolean[] visited = new boolean[26]; //will contain if character (i+'a') is present in current result Stack
        char[] ch = sr.toCharArray();
        for(char c : ch){  //count number of occurences of character
            res[c - 'a']++;
        }
        Stack<Character> stack = new Stack<>(); // answer stack
        for(char s : ch){
            int index = s - 'a';
            //decrement number of characters remaining in the string to be analysed, will use this info later.
            res[index]--;
            //if character is already present in stack, dont bother
            if (visited[index]) {
                continue;
            }
            //we do the swap of char position in the stack.
            //if current character is smaller than last character in stack which occurs later in the string again
            //it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
            while(!stack.isEmpty() && s < stack.peek() && res[stack.peek() - 'a']!=0){
                char popped = stack.pop();
                visited[popped - 'a']=false;
            }
            //add current character and mark it as visited
            stack.push(s);
            visited[index]=true;
        }

        StringBuilder sb = new StringBuilder();
        //pop character from stack and build answer string from back
        while(!stack.isEmpty()){
            sb.insert(0,stack.pop());
        }
        return sb.toString();
    }

Shortest Palindrome
https://leetcode.com/problems/shortest-palindrome/
By analysing the problem, it can be converted to "find the longest palindrome from index 0".
Hence the solution will be using two pointer, to gradually move from both side. O(n2)
Related question: https://leetcode.com/problems/longest-palindromic-substring/

    public String shortestPalindrome(String s) {
        int i = 0, end = s.length() - 1, j = end; char chs[] = s.toCharArray();
        while (i < j) {
            if (chs[i] == chs[j]) {
                i++; j--;
            } else {
                //Reset the first index back to 0!!
                i = 0; end--; j = end;
            }
        }
        return new StringBuilder(s.substring(end+1)).reverse().toString() + s;
    }

Candy Crush  
https://leetcode.com/problems/candy-crush/
Carefully perform the "crush" and "gravity" steps. In the crush step, flag each candy that should be removed, 
then go through and crush each flagged candy. In the gravity step, collect the candy in each column and then 
rewrite the column appropriately. Do these steps repeatedly until there's no work left to do.

Path With Maximum Minimum Value
https://leetcode.com/problems/path-with-maximum-minimum-value/
Solution 1: DFS + Binary Search.
Solution 2: Priority Queue, use greedy approach, each time add higher value to the queue, track the visited position.
Solution 3: Union Find, sort the values, iterate from highest value to lowest, during the traverse, we union the new 
max value to its neighours if the neighours are already visited. Once we union it and the start and end cell become
connected, that's the value we can return.

    public int maximumMinimumPath(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        MaxMinPathUF uf = new MaxMinPathUF(m*n);
        List<int[]> data = new ArrayList<>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                data.add(new int[]{i, j, A[i][j]});
            }
        }
        Collections.sort(data, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[2] - o1[2];
            }
        });
        boolean[][] visited = new boolean[m][n];
        final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int i=0; i<data.size(); i++) {
            int[] max = data.get(i);
            visited[max[0]][max[1]] = true;
            for (int[] dir : dirs) {
                int nextX = max[0] + dir[0];
                int nextY = max[1] + dir[1];
                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && visited[nextX][nextY]) {
                    uf.union(max[0]*n + max[1], nextX*n + nextY);
                    if (uf.find(0) == uf.find(m*n-1)) {
                        return max[2];
                    }
                }
            }
        }
        return 0;
    }
    class MaxMinPathUF {
        int[] parent;
        public MaxMinPathUF(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
        }
        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        public void union(int x, int y) {
            int rx = find(x);
            int ry = find(y);
            if (rx != ry) parent[ry] = rx;
        }
    }

Maximum Width Ramp
https://leetcode.com/problems/maximum-width-ramp/
We need the store for each element, what is the right hand max value, based on this observation:
Solution 1: Monotone stack to keep track the decreasing number's idx
Solution 2: Use an additional equivalent array.
Both end up with a two pointer to track the max length of a candidate i, j pair.

    public int maxWidthRamp(int[] A) {
        Stack<Integer> s = new Stack<>();
        int res = 0, n = A.length;
        //Store the decreasing number's idx
        for (int i = 0; i < n; i++){
            if (s.empty() || A[s.peek()] > A[i]){
                s.add(i);
            }
        }
        //From end to start, check the diff between lower and higher, get the max.
        for (int i = n - 1; i > res; i--) {
            while (!s.empty() && A[s.peek()] <= A[i]) {
                res = Math.max(res, i - s.pop());
            }
        }
        return res;
    }

    //Solution based on two pointer
    public int maxWidthRamp_1(int[] A) {
        int n = A.length;
        int[] rMax = new int[n];
        rMax[n - 1] = A[n - 1];
        //from i's index, the max value from the right part of the array.
        for (int i = n - 2; i >= 0; i--) {
            rMax[i] = Math.max(rMax[i + 1], A[i]);
        }
        //two pointers to loop through the original array
        int left = 0, right = 0;
        int ans = 0;
        while (right < n) {
            while (left < right && A[left] > rMax[right]) {
                left++;
            }
            ans = Math.max(ans, right - left);
            right++;
        }
        return ans;
    }