How to get GCD for two integer:

    public int gcd(int a, int b){
        if (b == 0) {
            return a;
        }
        return gcd(b, a%b);
    }



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