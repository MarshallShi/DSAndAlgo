Huffman coding

Traveling Salesman Problem Dynamic Programming Held-Karp
Questions:
https://leetcode.com/problems/find-the-shortest-superstring/


Tail recursion
Tail recursion is a recursion where the recursive call is the final instruction in the recursion 
function. And there should be only one recursive call in the function.

https://leetcode.com/explore/learn/card/recursion-i/256/complexity-analysis/2374/

The benefit of having tail recursion is that it could avoid the accumulation of stack overheads 
during the recursive calls, since the system could reuse a fixed amount space in the stack for 
each recursive call. 

A tail recursion function can be executed as non-tail-recursion functions, i.e. with piles 
of call stacks, without impact on the result. 


Segment Trees



Fenwick Tree (Binary Index Tree)
Binary Indexed Tree also called Fenwick Tree provides a way to represent an array of numbers in an array, 
allowing prefix sums to be calculated efficiently. For example, an array [2, 3, -1, 0, 6] is given, 
then the prefix sum of first 3 elements [2, 3, -1] is 2 + 3 + -1 = 4. 
Calculating prefix sum efficiently is useful in various scenarios, especially when we do update in the array.

A simple question would be:
Given an array , and two types of operations are to be performed on it.
1). Change the value stored at an index i. (This is called a point update operation)
2). Find the sum of a prefix of length k. (This is called a range sum query)

Traditonally, we scan the whole array for solution, but unfortunately, the time required to calculate a 
prefix sum is proportional to the length of the array, so this will usually time out when large numbers 
of such intermingled operations are performed.

One efficient solution is to use segment tree that can perform both operations in O(logN) time.
Using binary Indexed tree also, we can perform both the tasks in O(logN) time. But then why to learn 
another data structure when segment tree can do the work for us. It’s because binary indexed trees 
require less space and are very easy to implement during programming contests (the total code is not 
more than 8-10 lines).




Suffix Trees



Suffix Arrays
Build the suffix array of the string, so we can use it for string pattern matching.
As the suffix array is sorted, we can apply binary search.
Suffix array is an extremely useful data structure, it can be used for a wide range of problems. 
Following are some famous problems where Suffix array can be used.
1) Pattern Searching
2) Finding the longest repeated substring
3) Finding the longest common substring
4) Finding the longest palindrome in a string
https://www.geeksforgeeks.org/suffix-array-set-1-introduction/
https://www.geeksforgeeks.org/suffix-array-set-2-a-nlognlogn-algorithm/

https://www.geeksforgeeks.org/pattern-searching-using-suffix-tree/
https://www.geeksforgeeks.org/suffix-tree-application-2-searching-all-patterns
https://www.geeksforgeeks.org/suffix-tree-application-4-build-linear-time-suffix-array

Ukkonen’s Suffix Tree Construction
https://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/

String pattern search Algo:

KMP Algorithm
Improve String search from O(mn) to O(m+n)
https://www.youtube.com/watch?v=GTJr8OvyEVQ&t=215s
http://jakeboxer.com/blog/2009/12/13/the-knuth-morris-pratt-algorithm-in-my-own-words/
https://leetcode.com/problems/implement-strstr/

    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.equalsIgnoreCase("")) {
            return 0;
        }
        char[] text = haystack.toCharArray();
        char[] pattern = needle.toCharArray();

        int lps[] = buildLPS(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length && j < pattern.length) {
            if (text[i] == pattern[j]) {
                i++;
                j++;
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
            if (j == pattern.length) {
                return i - j;
            }
        }
        return -1;
    }

    /**
     * Compute temporary array to maintain size of suffix which is same as prefix
     * Time/space complexity is O(size of pattern)
     */
    private int[] buildLPS(char[] pattern) {
        int[] lps = new int[pattern.length];
        int index = 0;
        for (int i = 1; i < pattern.length; ) {
            if (pattern[i] == pattern[index]) {
                lps[i] = index + 1;
                index++;
                i++;
            } else {
                if (index != 0) {
                    index = lps[index - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    
Rabin Karp Algorithm
Finite Automata based Algorithm
Boyer Moore Algorithm


Morris Traversal
An algo designed for traverse a tree without using Stack or Recursion.  (https://www.youtube.com/watch?v=wGXB9OWhPTg)

    //Manipulate the leaf node's empty left right node to create the link to next, remove it while visiting the current.
    public void inorder(TreeNode root) {
        TreeNode current = root;
        while (current != null) {
            //left is null then print the node and go to right
            if (current.left == null) {
                System.out.print(current.val + " ");
                current = current.right;
            } else {
                //find the predecessor.
                TreeNode predecessor = current.left;
                //To find predecessor keep going right till right node is not null or right node is not current.
                while (predecessor.right != current && predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                //if right node is null then go left after establishing link from predecessor to current.
                if (predecessor.right == null) {
                    predecessor.right = current;
                    current = current.left;
                } else { 
                    //left is already visit. Go rigth after visiting current.
                    predecessor.right = null;
                    System.out.print(current.val + " ");
                    current = current.right;
                }
            }
        }
    }
    

Boyer-Moore Majority Vote Algorithm
https://leetcode.com/problems/majority-element/
https://leetcode.com/problems/majority-element-ii/
https://www.geeksforgeeks.org/majority-element/
https://gregable.com/2013/10/majority-vote-algorithm-find-majority.html
The algo run O(n) time and O(1) space, compare to the HashMap based solution which is O(n) Auxiliary Space.
NOTE: it only works when the majority element exist, otherwise, need another step to verify if this is the majority.

    public int majorityElement(int[] nums) {
        int maj_index = 0, count = 1;
        int i;
        for (i = 1; i < nums.length; i++) {
            if (nums[maj_index] == nums[i]) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                maj_index = i;
                count = 1;
            }
        }
        return nums[maj_index];
    }
    
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums.length == 0) {
            return res;
        }
        //Majority Voting Algorithm
        int num1 = nums[0]; int num2 = nums[0]; int count1 = 1; int count2 = 0;
        //Pass 1: find the potential majority numbers, in this case more than n/3, would be two numbers.
        for (int val : nums) {
            if(val == num1) {
                count1++;
            } else {
                if (val == num2){
                    count2++;
                } else {
                    if (count1 == 0) {
                        num1 = val;
                        count1++;
                    } else {
                        if (count2 == 0) {
                            num2 = val;
                            count2++;
                        } else {
                            count1--;
                            count2--;
                        }
                    }
                }
            }
        }
        //Pass 2: verify the two numbers.
        count1 = 0;
        count2 = 0;
        for(int val : nums) {
            if(val == num1) {
                count1++;
            } else {
                if(val == num2){
                    count2++;
                }
            }
        }
        if(count1 > nums.length/3) {
            res.add(num1);
        }
        if(count2 > nums.length/3) {
            res.add(num2);
        }
        return res;
    }

Quick Select

Cycle detection algo (Floyd's cycle detection algo)
https://leetcode.com/problems/linked-list-cycle/

https://leetcode.com/problems/linked-list-cycle-ii/

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

https://leetcode.com/problems/find-the-duplicate-number/

    public int findDuplicate(int[] nums) {
        if (nums != null && nums.length > 1) {
            int slow = nums[0];
            int fast = nums[nums[0]];
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[nums[fast]];
            }
            fast = 0;
            while (fast != slow) {
                fast = nums[fast];
                slow = nums[slow];
            }
            return slow;
        }
        return -1;
    }
    
https://leetcode.com/problems/majority-element/
    
    public int majorityElement(int[] nums) {
        int maj_index = 0, count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[maj_index] == nums[i]) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                maj_index = i;
                count = 1;
            }
        }
        return nums[maj_index];
    }