package dsandalgo.design;

import java.util.Random;

/**
 * https://leetcode.com/problems/design-skiplist/
 * Design a Skiplist without using any built-in libraries.
 *
 * A Skiplist is a data structure that takes O(log(n)) time to add, erase and search. Comparing with treap and red-black tree which has
 * the same function and performance, the code length of Skiplist can be comparatively short and the idea behind Skiplists are just simple linked lists.
 *
 * For example: we have a Skiplist containing [30,40,50,60,70,90] and we want to add 80 and 45 into it. The Skiplist works this way:
 *
 *
 * Artyom Kalinin [CC BY-SA 3.0], via Wikimedia Commons
 *
 * You can see there are many layers in the Skiplist. Each layer is a sorted linked list. With the help of the top layers, add ,
 * erase and search can be faster than O(n). It can be proven that the average time complexity for each operation is O(log(n)) and space complexity is O(n).
 *
 * To be specific, your design should include these functions:
 *
 * bool search(int target) : Return whether the target exists in the Skiplist or not.
 * void add(int num): Insert a value into the SkipList.
 * bool erase(int num): Remove a value in the Skiplist. If num does not exist in the Skiplist, do nothing and return false.
 * If there exists multiple num values, removing any one of them is fine.
 * See more about Skiplist : https://en.wikipedia.org/wiki/Skip_list
 *
 * Note that duplicates may exist in the Skiplist, your code needs to handle this situation.
 *
 *
 *
 * Example:
 *
 * Skiplist skiplist = new Skiplist();
 *
 * skiplist.add(1);
 * skiplist.add(2);
 * skiplist.add(3);
 * skiplist.search(0);   // return false.
 * skiplist.add(4);
 * skiplist.search(1);   // return true.
 * skiplist.erase(0);    // return false, 0 is not in skiplist.
 * skiplist.erase(1);    // return true.
 * skiplist.search(1);   // return false, 1 has already been erased.
 *
 *
 * Constraints:
 *
 * 0 <= num, target <= 20000
 * At most 50000 calls will be made to search, add, and erase.
 */

//https://opendatastructures.org/ods-java/4_2_SkiplistSSet_Efficient_.html

public class SkipList {

    class Node {
        int val;
        int count; // to handle duplicates
        int h; // highest level: [0,32]
        Node[] next;
        public Node(int a, int b) {
            val = a; h = b; count = 1;
            next = new Node[33];
        }
    }

    Node sentinel = new Node(Integer.MIN_VALUE, 32);
    int topLevel = 0;
    Node[] stack = new Node[33];
    Random rand = new Random();

    public SkipList() {
    }

    // find the predecessor
    private Node findPred(int num) {
        Node cur = sentinel;
        for (int r = topLevel; r >= 0; r--) {
            while (cur.next[r] != null && cur.next[r].val < num)  {
                cur = cur.next[r];
            }
            stack[r] = cur;
        }
        return cur;
    }

    public boolean search(int target) {
        Node pred = findPred(target);
        return pred.next[0] != null && pred.next[0].val == target;
    }

    public void add(int num) {
        Node pred = findPred(num);
        if (pred.next[0] != null && pred.next[0].val == num) {
            pred.next[0].count++;
            return;
        }
        Node w = new Node(num, Integer.numberOfTrailingZeros(rand.nextInt()));
        while (topLevel < w.h) {
            stack[++topLevel] = sentinel;
        }
        for (int i = 0; i <= w.h; i++) {
            w.next[i] = stack[i].next[i];
            stack[i].next[i] = w;
        }
    }

    public boolean erase(int num) {
        Node pred = findPred(num);
        if (pred.next[0] == null || pred.next[0].val != num) {
            return false;
        }
        boolean noNeedToRemove = --pred.next[0].count != 0;
        if (noNeedToRemove) {
            return true;
        }
        for (int r = topLevel; r >= 0; r--) {
            Node cur = stack[r];
            if (cur.next[r] != null && cur.next[r].val == num) {
                cur.next[r] = cur.next[r].next[r];
            }
            if (cur == sentinel && cur.next[r] == null) {
                topLevel--;
            }
        }
        return true;
    }
}
