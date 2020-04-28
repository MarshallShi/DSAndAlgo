How to get GCD for two integer:

    public int gcd(int a, int b){
        if (b == 0) {
            return a;
        }
        return gcd(b, a%b);
    }

These are classic statistical properties for MLE / MAP in point estimation.
mean minimizes total distance for euclidian distance
median minimzes total distance for absolute deviation
mode minimizes distance for indicator function

https://leetcode.com/problems/best-meeting-point/
For this question, since it is using Manhattan distance, we can apply the theory where median is the shortest distance 
sum of deviation, so always pick from two low and high end.
    
    public int minTotalDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> xPosList = new ArrayList<>(m);
        List<Integer> yPosList = new ArrayList<>(n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    xPosList.add(i);
                    yPosList.add(j);
                }
            }
        }
        return getMinTotalDisInALine(xPosList) + getMinTotalDisInALine(yPosList);
    }

    private int getMinTotalDisInALine(List<Integer> list) {
        int ret = 0;
        Collections.sort(list);
        int low = 0;
        int high = list.size() - 1;
        while (low < high) {
            ret += list.get(high) - list.get(low);
            high--;
            low++;
        }
        return ret;
    }

https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/
Similar question here need to apply median theory.

    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = nums.length-1;
        int count = 0;
        while(i < j){
            count += nums[j]-nums[i];
            i++;
            j--;
        }
        return count;
    }
    
    public int minMoves2(int[] A) {
        int sum = 0, median = quickselect(A, A.length/2+1, 0, A.length-1);
        for (int i=0;i<A.length;i++) sum += Math.abs(A[i] - median);
        return sum;
    }
    
    public int quickselect(int[] A, int k, int start, int end) {
        int l = start, r = end, pivot = A[(l+r)/2];
        while (l<=r) {
            while (A[l] < pivot) l++;
            while (A[r] > pivot) r--;
            if (l>=r) break;
            swap(A, l++, r--);
        }
        if (l-start+1 > k) return quickselect(A, k, start, l-1);
        if (l-start+1 == k && l==r) return A[l];
        return quickselect(A, k-r+start-1, r+1, end);
    }
    
    public void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

//Similar questions: LC 215

Math Questions

Max Points on a Line
https://leetcode.com/problems/max-points-on-a-line/
Solution: calculate the deltaX and deltaY for each two points, store in a map, track the max duplicate.
Trick is the application of GCD.

    public int maxPoints(int[][] points) {
        if (points == null) return 0;
        int solution = 0;
        for (int i = 0; i < points.length; i++){
            Map<String, Integer> map = new HashMap<>();
            int duplicate = 0;
            int max = 0;
            for (int j = i + 1; j < points.length; j++){
                int deltaX = points[j][0] - points[i][0];
                int deltaY = points[j][1] - points[i][1];
                
                if (deltaX == 0 && deltaY == 0){
                    duplicate++;
                    continue;
                }
                
                int gcd = gcd(deltaX, deltaY);
                int dX = deltaX / gcd;
                int dY = deltaY / gcd;
                
                map.put(dX + "," + dY, map.getOrDefault(dX + "," + dY, 0) + 1);
                max = Math.max(max, map.get(dX + "," + dY));
            }
            solution = Math.max(solution, max + duplicate + 1);
        }
        return solution;
    }
    
    public int gcd(int a, int b){
        if (b == 0)
            return a;
        return gcd(b, a%b);
    }