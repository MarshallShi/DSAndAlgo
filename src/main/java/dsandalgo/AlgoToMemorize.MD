Sorting:
The implementations are as below:

quick sort

    class Solution {
        public List<Integer> sortArray(int[] nums) {
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            quickSort(nums, 0, nums.length - 1);
            for (int i : nums) res.add(i);
            return res;
        }
        private void quickSort(int[] nums, int l, int r) {
            if (l >= r) return;
            int mid = partition(nums, l, r);
            quickSort(nums, l, mid);
            quickSort(nums, mid + 1, r);
        }
        private int partition(int[] nums, int l, int r) {
            int pivot = nums[l];
            while (l < r) {
                while (l < r && nums[r] >= pivot) r--;
                nums[l] = nums[r];
                while (l < r && nums[l] <= pivot) l++;
                nums[r] = nums[l];
            }
            nums[l] = pivot;
            return l;
        }
    }

top-down merge sort

    class Solution {
        public List<Integer> sortArray(int[] nums) {
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            mergeSort(nums, 0, nums.length - 1);
            for (int i : nums) res.add(i);
            return res;
        }
        private void mergeSort(int[] nums, int l, int r) {
            if (l >= r) return;
            int mid = l + (r - l) / 2;
            mergeSort(nums, l, mid);
            mergeSort(nums, mid + 1, r);
            merge(nums, l, r);
        }
        private void merge(int[] nums, int l, int r) {
            int mid = l + (r - l) / 2;
            int[] tmp = new int[r - l + 1];
            int i = l, j = mid + 1, k = 0;
            while (i <= mid || j <= r) {
                if (i > mid || j <= r && nums[i] > nums[j]) {
                    tmp[k++] = nums[j++];
                } else {
                    tmp[k++] = nums[i++];
                }
            }
            System.arraycopy(tmp, 0, nums, l, r - l + 1);
        }
    }

bottom-up merge sort

    class Solution {
        public List<Integer> sortArray(int[] nums) {
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            mergeSort2(nums);
            for (int i : nums) res.add(i);
            return res;
        }
        private void mergeSort2(int[] nums) {
            for (int size = 1; size < nums.length; size *= 2) {
                for (int i = 0; i < nums.length - size; i += 2 * size) {
                    int mid = i + size - 1;
                    int end = Math.min(i + 2 * size - 1, nums.length - 1);
                    merge2(nums, i, mid, end);
                }
            }
        }
        private void merge2(int[] nums, int l, int mid, int r) {
            int[] tmp = new int[r - l + 1];
            int i = l, j = mid + 1, k = 0;
            while (i <= mid || j <= r) {
                if (i > mid || j <= r && nums[i] > nums[j]) {
                    tmp[k++] = nums[j++];
                } else {
                    tmp[k++] = nums[i++];
                }
            }
            System.arraycopy(tmp, 0, nums, l, r - l + 1);
        }
    }

heap sort
    
    class Solution {
        public List<Integer> sortArray(int[] nums) {
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            heapSort(nums);
            for (int i : nums) res.add(i);
            return res;
        }
        private void heapSort(int[] nums) {
            for (int i = nums.length / 2 - 1; i >= 0; i--) {
                heapify(nums, i, nums.length - 1);
            }
            for (int i = nums.length - 1; i >= 1; i--) {
                swap(nums, 0, i);
                heapify(nums, 0, i - 1);
            }
        }
        private void heapify(int[] nums, int i, int end) {
            while (i <= end) {
                int l = 2 * i + 1, r = 2 * i + 2;
                int maxIndex = i;
                if (l <= end && nums[l] > nums[maxIndex]) maxIndex = l;
                if (r <= end && nums[r] > nums[maxIndex]) maxIndex = r;
                if (maxIndex == i) break;
                swap(nums, i, maxIndex);
                i = maxIndex;
            }
        }
        private void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }

selection sort

    class Solution {
        public List<Integer> sortArray(int[] nums) {
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            selectionSort(nums);
            for (int i : nums) res.add(i);
            return res;
        }
        private void selectionSort(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                int minIndex = i;
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] < nums[minIndex]) minIndex = j;
                }
                if (minIndex != i) swap(nums, i, minIndex);
            }
        }
        private void swap(int[] nums, int i, int j) {
            nums[i] = nums[i] ^ nums[j];
            nums[j] = nums[i] ^ nums[j];
            nums[i] = nums[i] ^ nums[j];
        }
    }

insertion sort
    
    class Solution {
        public List<Integer> sortArray(int[] nums) {
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            insertionSort(nums);
            for (int i : nums) res.add(i);
            return res;
        }
        private void insertionSort(int[] nums) {
            for (int i = 1; i < nums.length; i++) {
                for (int j = i; j >= 1; j--) {
                    if (nums[j] >= nums[j - 1]) break;
                    swap(nums, j, j - 1);
                }
            }
        }
        private void swap(int[] nums, int i, int j) {
            nums[i] = nums[i] ^ nums[j];
            nums[j] = nums[i] ^ nums[j];
            nums[i] = nums[i] ^ nums[j];
        }
    }

bubble sort
    
    class Solution {
        public List<Integer> sortArray(int[] nums) {
            List<Integer> res = new ArrayList<>();
            if (nums == null || nums.length == 0) return res;
            bubbleSort(nums);
            for (int i : nums) res.add(i);
            return res;
        }
        private void bubbleSort(int[] nums) {
            for (int k = nums.length - 1; k >= 1; k--) {
                for (int i = 0; i < k; i++) {
                    if (nums[i] > nums[i + 1]) swap(nums, i, i + 1);
                }
            }
        }
        private void swap(int[] nums, int i, int j) {
            nums[i] = nums[i] ^ nums[j];
            nums[j] = nums[i] ^ nums[j];
            nums[i] = nums[i] ^ nums[j];
        }
    }


Merge Sort: (time O(nlogn), space O(n))
Divide the array to recursively to left part and right part, sort them recursively, then merge the left sorted and right sorted.
Divide, Conquer, Merge

    private void merge(int arr[], int low, int mid, int high) {
        // Find sizes of two sub arrays to be merged
        int leftLen = mid - low + 1;
        int rightLen = high - mid;
        /* Create temp arrays */
        int[] leftArr = new int[leftLen];
        int[] rightArr = new int[rightLen];
        /*Copy data to temp arrays*/
        for (int i = 0; i < leftLen; ++i) leftArr[i] = arr[low + i];
        for (int j = 0; j < rightLen; ++j) rightArr[j] = arr[mid + 1 + j];
        /* Merge the temp arrays */
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
        // Initial index of merged subarry array
        int k = low;
        while (i < leftLen && j < rightLen) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        /* Copy remaining elements of L[] if any */
        while (i < leftLen) arr[k++] = leftArr[i++];
        /* Copy remaining elements of R[] if any */
        while (j < rightLen) arr[k++] = rightArr[j++];
    }

    private void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;
            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }


Array:

Maximum Sum SubArray (Kadane's algorithm) (Largest Sum Contigous SubArray)
https://leetcode.com/problems/maximum-subarray/

    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int curSum = nums[0], maxSum = nums[0];
        for (int i = 1; i < n; ++i) {
            //Pick the max of current element or so far sum
            curSum = Math.max(nums[i], curSum + nums[i]);
            maxSum = Math.max(maxSum, curSum);
        }
        return maxSum;
    }

2D version of Kadane's algorithm
https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/


LinkedList Floyd's cycle detection algo

https://leetcode.com/problems/linked-list-cycle-ii/

    //Solution: use Floyd's cycle detection algo.
    //Start with two pointers with 2 steps each and 1 step each. (fast and slow).
    //If they met at one node, keep the slow as it, but reinit the fast to the head and restart, but this time both move 1 step each.
    //The meeting point is the starting point of the cycle.
    //Mathematical proof: let l be the length of cycle, k be the length of point where slow and fast first met, m be the length 
    //from head to the cycle start point, p and q be the number of cycles the two points already travelled in the cycle.
    //we have: for slow: distance = m + p*l + k; for fast: distance = m + q*l + k.
    //then we have: m + q*l + k = 2 * (m + p*l + k) --> m + k = (q - 2p)*l.  --> so if the two pointer both travel from head and current meeting point for m distance, 
    //they'll meet at the cycle starting point.
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow){
                ListNode slow2 = head;
                while (slow2 != slow){
                    slow = slow.next;
                    slow2 = slow2.next;
                }
                return slow;
            }
        }
        return null;
    }

String

Trie (Prefix tree)
https://leetcode.com/problems/implement-trie-prefix-tree/
https://leetcode.com/problems/add-and-search-word-data-structure-design/
https://leetcode.com/problems/word-search-ii/

Graph:

Dijkstra’s Algorithm (Shortest path algo)
1. Set all the node’s distances to infinity and add them to an unexplored set
2. Set the starting node’s distance to 0
3. Repeat the following:
A) Look for the node with the lowest distance, let this be the current node
B) Remove it from the unexplored set
C) For each of the nodes adjacent to this node…
If it is not walkable, ignore it. Otherwise do the following.
Calculate a potential new distance based on the current node’s distance plus the distance to the adjacent node you are at.
If the potential distance is less than the adjacent node’s current distance, then set the adjacent node’s distance to the potential new distance and set the adjacent node’s parent to the current node
D) Stop when you:
Remove the end node from the unexplored set, in which case the path has been found, or
Fail to find the end node, and the unexplored set is empty. In this case, there is no path.

    // Dijkstra’s Algorithm
    // Set each node's position to infinity
    for each node in the graph
        set the node's distance to infinity
        set the node's parent to none
    // Create an unexplored set
    let the unexploredSet equal a set of all the nodes
    while the unexploredSet is not empty
        // Get the current node
        let the currentNode equal the node with the smallest distance
        remove the currentNode from the unexploredSet
        // Check completed
        if currentNode's position is your goal
            Congratz! You've found the end! Backtrack to get path
        // Get all the neighbors
        for each neighbor (still in unexploredSet) to the currentNode
        
        // Calculate the new distance
        let newDist equal currentNode's dist plus distance between
            the currentNode and the neighbor
        // Check to see if the new distance is better
        if newDist is less than currentNode's distance
            set neighbor's distance to newDist
            set neighbor's parent to currentNode
            
https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/
https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/
https://leetcode.com/problems/network-delay-time/
https://leetcode.com/problems/cheapest-flights-within-k-stops/
https://leetcode.com/problems/word-ladder-ii/
https://leetcode.com/problems/jump-game-ii/


Topological sorting

Minimal spanning tree (Union Find)


Fisher-Yates shuffle
https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        Random r = new Random(); 
        for (int i = arr.length - 1; i > 0; i--) { 
            int j = r.nextInt(i+1); 
            int temp = arr[i]; 
            arr[i] = arr[j]; 
            arr[j] = temp; 
        }
        return arr;
    }
    
    
Eulerian Path/Circuit algorithm (Hierholzer's algorithm) | Graph Theory
https://www.youtube.com/watch?v=8MpoO2zA2l4
https://leetcode.com/problems/reconstruct-itinerary/
The algo will do dfs, but trick is when the node get added to the result. In a backtrack way, 
only when there is no further available next node, add it to the result, but always add to the first.

    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> flights = new HashMap<>();
        LinkedList<String> path = new LinkedList<>();
        for (List<String> ticket : tickets) {
            flights.putIfAbsent(ticket.get(0), new PriorityQueue<>());
            flights.get(ticket.get(0)).add(ticket.get(1));
        }
        findItineraryDFS("JFK", flights, path);
        return path;
    }

    private void findItineraryDFS(String departure, Map<String, PriorityQueue<String>> flights, LinkedList<String> path) {
        PriorityQueue<String> arrivals = flights.get(departure);
        while (arrivals != null && !arrivals.isEmpty()) {
            findItineraryDFS(arrivals.poll(), flights, path);
        }
        path.addFirst(departure);
    }
