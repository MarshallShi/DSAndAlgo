package dsandalgo.sorting.mergesort;

public class CountingInversion {

    /**
     * Finding "similarity" between two rankings. Given a sequence of n numbers 1..n (assume all numbers are distinct).
     * Define a measure that tells us how far this list is from being in ascending order.  The value should be 0 if a_1 < a_2 < ... < a_n and
     * should be higher as the list is more "out of order".
     * Define the number of inversion
     *     i, j form an inversion if a_i > a_j, that is, if the two elements a_i and a_j are "out of order".
     *
     * Comparing two rankings is counting the number of  inversion in the sequence a_1.. a_n.
     * Example
     * 2 4 1 3 5
     * 1 2 3 4 5
     *
     * The sequence 2, 4, 1, 3, 5 has three inversions (2,1), (4,1), (4,3).
     * Algorithm to count inversion
     * Use divide and conquer
     *
     * divide: size of sequence n to two lists of size n/2
     * conquer: count recursively two lists
     * combine:  this is a trick part (to do it in linear time)
     *
     * combine use merge-and-count. Suppose the two lists are A, B.  They are already sorted.  Produce an output list L from A, B while also counting the number of inversions, (a,b) where a is-in A, b is-in B and a > b.
     *
     * The idea is similar to "merge" in merge-sort. Merge two sorted lists into one output list, but we also count the inversion.
     *
     * Everytime a_i is appended to the output, no new inversions are encountered, since a_i is smaller than everything left in list B.  If b_j is appended to the output, then it is smaller than all the remaining items in A, we increase the number of count of inversions by the number of elements remaining in A.
     *
     * merge-and-count(A,B)
     *   ;  A,B two input lists (sorted)
     *   ;  C  output list
     *   ;  i,j current pointers to each list, start at beginning
     *   ;  a_i, b_j elements pointed by i, j
     *   ;  count number of inversion, initially 0
     *
     *   while A,B != empty
     *     append min(a_i,b_j) to C
     *     if b_j < a_i
     *        count += number of element remaining in A
     *        j++
     *     else
     *        i++
     *   ; now one list is empty
     *   append the remainder of the list to C
     *   return count, C
     *
     * With merge-and-count, we can design the count inversion algorithm as follows:
     *
     * sort-and-count(L)
     *   if L has one element return 0
     *   else
     *      divide L into A, B
     *      (rA, A) = sort-and-count(A)
     *      (rB, B) = sort-and-count(B)
     *      (r, L) = merge-and-count(A,B)
     *   return r = rA+rB+r, L
     *
     * T(n) = O(n lg n)
     */
}
