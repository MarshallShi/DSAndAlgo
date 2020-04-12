Data Structure

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
    



