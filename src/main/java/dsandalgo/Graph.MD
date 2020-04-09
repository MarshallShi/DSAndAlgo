Topological Sorting

Course Schedule
https://leetcode.com/problems/course-schedule/description/

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<prerequisites.length; i++) {
            map.putIfAbsent(prerequisites[i][1], new ArrayList<Integer>());
            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegrees[prerequisites[i][0]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i=0; i<indegrees.length; i++) {
            if (indegrees[i] == 0) queue.add(i);
        }
        int counter = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            counter++;
            List<Integer> curChildren = map.get(cur);
            if (curChildren != null) {
                for (Integer child: curChildren) {
                    indegrees[child]--;
                    if (indegrees[child] == 0) {
                        queue.add(child);
                    }
                }
            }
        }
        return counter == numCourses;
    }
    
Course Schedule II
https://leetcode.com/problems/course-schedule-ii/

Alien Dictionary
https://leetcode.com/problems/alien-dictionary/description/

General template for topological sorting
